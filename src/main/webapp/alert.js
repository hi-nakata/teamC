'use strict';

var rootUrl = "/teamC/webapi/rentals";

function displayAll(){

	console.log('displayAll start ');

	$.ajax({
		type : "GET",
		url : rootUrl,
		dataType : "json",
		success : function(json){
			console.log('通信に成功しました。')
			console.log(json);

			var headerRow='<tr><th>返却予定日</th><th>タイトル</th><th>借りた人</th>'
				+'<th>催促ステータス</th><th></th></tr>';

			$('#alerts').children().remove();

			if(json.length === 0){
				$('#alerts').append('<p>現在データが存在していません。</p>')
			}else{
				var table = $('<table>');

				table.append(headerRow);

				$.each(json,function(index,alert){
					var row = $('<tr>')
					row.append($('<td>').text(alert.dueDate));
					row.append($('<td>').text(alert.title));
					row.append($('<td>').text(alert.employeeName));
					row.append($('<td>').text(alert.alertStatus));
					row.append($('<td>').append(
							$('<button>').text("メール送信").attr("type","button").attr("onclick", "updateAlertStatus("+alert.bookId+')')
						));
					table.append(row);
					console.log(alert.alertStatus);
				});
				$('#alerts').append(table);
			}
		}
	});
}

function updateAlertStatus(bookId) {
	console.log('updateAlertStatus start - bookId:'+ bookId);
	$.ajax({
		type: "PUT",
		url: rootUrl+'/alertStatus/'+bookId,
		dataType: "json",
		success: function() {
			alert('催促しました');
			displayAll();
			//location.href ='./Alert.html'
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('催促処理に失敗しました');
		}
	})
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