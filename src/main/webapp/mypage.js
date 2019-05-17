'use strict';

var rootUrl = "/teamC/webapi/rentals";

function displayAll(){

	var id  = "mirai_kako";
	 location.search.substring( 1, location.search.length );
	 parameter = decodeURIComponent( parameter );
	 parameter = parameter.split('=')[1];

	console.log('displayAll start - id:');

	$.ajax({
		type : "GET",
		url : rootUrl,
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
							$('<button>').text("返却").attr("type","button").attr("onclick", "findById("+rental.bookId+')')
						));
					row.append($('<td>').append(
							$('<button>').text("詳細").attr("type","button").attr("onclick", "deleteById("+rental.bookId+')')
						));
					table.append(row);
				});
				$('#rentals').append(table);
			}
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