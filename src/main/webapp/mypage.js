'use strict';

var rootUrl = "/teamC/webapi/rentals";

/*すべての本を一覧表示する機能*/
function displayAll(){
	console.log('displayAll start.');
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

$(document).ready(function () {
	'use strict';

	// 初期表示用
	displayAll();

	// 更新ボタンにイベント設定
	//$('#searchBtn').bind('click',displayAll);

});