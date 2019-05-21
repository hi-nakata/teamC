'use strict';

var rootUrl = "/teamC/webapi/rentals";

function displayAll(){

	var userId = location.search.substring( 1, location.search.length );
	userId = decodeURIComponent( userId );
	userId = userId.split('=')[1];

	console.log('displayAll start - userId:' + userId);

	$.ajax({
		type : "GET",
		url : rootUrl+ '/'+userId ,
		dataType : "json",
		success : function(json){
			console.log('通信に成功しました。')
			console.log(json);

			var headerRow='<tr><th>タイトル</th><th>返却予定日</th><th>残り日数</th>'
				+'<th></th><th></th></tr>';

			$('#rentals').children().remove();

			if(json.length === 0){
				$('#rentals').append('<p>現在データが存在していません。</p>')
			}else{
				var table = $('<table>');

				table.append(headerRow);

				$.each(json,function(index,rental){
					var row = $('<tr>')
					row.append($('<td>').text(rental.title));
					row.append($('<td>').text(rental.dueDate));

					var restDate = rental.restDate;
					console.log("残り日数:",restDate);

					if(restDate == 0){
						row.append($('<td class="today">').text("返却日です"));
					}else if(restDate < 0){
						row.append($('<td class="late">').text("延滞中です"));
					}else if(restDate <= 1){
						row.append($('<td class="one">').text("あと"+restDate+"日です"));
					}else if(restDate <=3){
						row.append($('<td class="three">').text("あと"+restDate+"日です"));
					}else{
						row.append($('<td>').text("あと"+restDate+"日です"));
					}


					row.append($('<td>').append(
							$('<button>').text("返却").attr("type","button").attr("onclick", "returnBook("+rental.bookId+')')
						));
					row.append($('<td>').append(
							$('<button>').text("詳細").attr("type","button").attr("onclick", "goBookDetail("+rental.bookId+')')
						));
					table.append(row);
				});
				$('#rentals').append(table);
			}
		}
	});
}

function returnBook(bookId) {
	console.log('updateRentalStatus start - bookId:'+ bookId);
	$.ajax({
		type: "PUT",
		url: rootUrl+'/rentalStatus/'+bookId,
		dataType: "json",
		success: function() {
			alert('返却しました');
			displayAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('返却処理に失敗しました');
		}
	})
}

function goBookDetail(bookId){
	location.href ='./BookDetail.html?bookId='+bookId;
}

function goBookSearch(){
	location.href ='./BookSearch.html'
}

$(document).ready(function () {
	'use strict';

	// 初期表示用
	displayAll();

	$('#js-btn-search').click(goBookSearch);

});