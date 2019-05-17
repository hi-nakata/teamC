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

					var check = rental.rentalStatus;
					console.log("貸出ステータス:",check);


				/**	if(check == 1){

						$('<button>').text("貸出").atter("type","button").attr("disabled")
						$('<button>').text("詳細").attr("type","button")//.attr("onclick","ここにクリックしたときのfunctionを書く")
						table.append(row);
					}else{

						$('<button>').text("貸出").attr("type","button")//.atter("onclick","ここにクリックしたときのfunctionを書く")
					$('<button>').text("詳細").attr("type","button")//.attr("onclick","ここにクリックしたときのfunctionを書く")
						table.append(row);
					} */
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