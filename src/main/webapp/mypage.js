'use strict';

var rootUrl = "/teamC/webapi/rentals";

function displayAll(){

	//var userId  = "mirai_kako";
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

			var headerRow='<tr><th>タイトル</th><th>返却予定日</th><th>ステータス</th>'
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
					row.append($('<td>').text(rental.rentalStatus));
					row.append($('<td>').append(
							$('<button>').text("返却").attr("type","button").attr("onclick", "deleteByBookId("+rental.bookId+')')
						));
					row.append($('<td>').append(
							$('<button>').text("詳細").attr("type","button").attr("onclick", "findByBookId("+rental.bookId+')')
						));
					table.append(row);
				});
				$('#rentals').append(table);
			}
		}
	});
}

function deleteByBookId(bookId) {
	console.log('delete start - bookId:'+bookId);
	$.ajax({
		type: "DELETE",
		url: rootUrl+'/'+bookId,
		success: function() {
			displayAll();
		},
		error:function(XMLHttpRequest,textStatus, errorThrown){
			alert('データの通信に失敗');
		}
	});
}

function goMypage(){
	location.href ='./MyPage.html'
}

function goBookSearch(){
	location.href ='./BookSearch.html'
}



$(document).ready(function () {
	'use strict';

	// 初期表示用
	displayAll();

	$('#js-btn-mypage').click(goMypage);
	$('#js-btn-search').click(goBookSearch);

});