'use strict';

var rootUrl = ;

/*すべての本を一覧表示する機能*/
function displayAll(){
	console.log('displayAll start.');
	$.ajax({
		type : "GET",
		url : rootUrl,
		dataType : "json",
		success : function(json){
			console.log('通信に成功しました。')

			var headerRow='<tr><th>タイトル</th><th>著者名</th><th>出版社</th><th>配架場所</th><th>ステータス</th>'
				+'<th>借りた人</th><th>返却予定日</th><th></th><th></th></tr>';

			$.('#searchedList').children().remove();

			if(json.length === 0){
				$.('#searchedList').append('<p>現在データが存在していません。</p>')
			}else{
			var table = $('<table>');
			table.append(headerRow);

			$.each(data,function(index,book)){
				var row = $('<tr>')
				row.append($('<tr>').text());

				if(a=0){

					$('<button>').text("貸出").atter("type","button").attr("disabled")
					$('<button>').text("詳細").attr("type","button").attr("onclick","ここにクリックしたときのfunctionを書く")
					table.append(row);
				}else{

					$('<button>').text("貸出").attr("type","button").atter("onclick","ここにクリックしたときのfunctionを書く")
					$('<button>').text("詳細").attr("type","button").attr("onclick","ここにクリックしたときのfunctionを書く")
					table.append(row);
				};
			}

			}
		}
	}


})
}
