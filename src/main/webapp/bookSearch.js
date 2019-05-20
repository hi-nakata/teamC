'use strict';

var rootUrl = "/teamC/webapi/books";






/*すべての本を一覧表示する機能*/
function displayAll(){
	console.log('displayAll start.');

	var rootUrlInit = rootUrl + '?titleParam=';
	$.ajax({
		type : "GET",
		url : rootUrlInit,
		dataType : "json",
		success : function(json){
			console.log('通信に成功しました。')
			console.log(json);

			var headerRow='<tr><th>タイトル</th><th>著者名</th><th>出版社</th><th>配架場所</th><th>ステータス</th>'
				+'<th>借りた人</th><th>返却予定日</th><th></th><th></th></tr>';

			$('#searchedList').children().remove();

			if(json.length === 0){
				$('#searchedList').append('<p>現在データが存在していません。</p>')
			}else{
				var table = $('<table>');

				table.append(headerRow);

				$.each(json,function(index,book){
					var row = $('<tr>')
					row.append($('<td>').text(book.title));
					row.append($('<td>').text(book.author));
					row.append($('<td>').text(book.publisher));
					row.append($('<td>').text(book.shelf));

					var check = book.rentalStatus;
					console.log("貸出ステータス:",check);

					if(check == 1){
						row.append($('<td>').text("貸出中"));
					}else{
						row.append($('<td>').text("在架中"));
					}

					row.append($('<td>').text(book.name));
					row.append($('<td>').text(book.dueDate));

					var check = book.rentalStatus;
					console.log("貸出ステータス:",check);


					if(check == 1){
						row.append($('<td>').append($('<button>').text("貸出").attr("disabled","true")));
						row.append($('<td>').append($('<button>').text("詳細").attr("type","button")));
						//.attr("onclick","ここにクリックしたときのfunctionを書く")
					}else{

						row.append($('<td>').append($('<button>').text("貸出").attr("type","button")));
						//.atter("onclick","ここにクリックしたときのfunctionを書く")
						row.append($('<td>').append($('<button>').text("詳細").attr("type","button")));
						//.attr("onclick","ここにクリックしたときのfunctionを書く")

					}
					table.append(row)
				});

				$('#searchedList').append(table);
			}
		}
	});
}

//本の検索をする機能
function findByParam(){
	console.log('findByParam start.')

	var urlWithParam = rootUrl + '?titleParam='+'%'+$('#titleParam').val()+'%';
	console.log('titleParam:',titleParam);
	$.ajax({
		type : "GET",
		url : urlWithParam,
		dataType : "json",
		success :  function(json){
			console.log('通信に成功しました。')
			console.log(json);

			var headerRow='<tr><th>タイトル</th><th>著者名</th><th>出版社</th><th>配架場所</th><th>ステータス</th>'
				+'<th>借りた人</th><th>返却予定日</th><th></th><th></th></tr>';

			$('#searchedList').children().remove();

			if(json.length === 0){
				$('#searchedList').append('<p>現在データが存在していません。</p>')
			}else{
				var table = $('<table>');

				table.append(headerRow);

				$.each(json,function(index,book){
					var row = $('<tr>')
					row.append($('<td>').text(book.title));
					row.append($('<td>').text(book.author));
					row.append($('<td>').text(book.publisher));
					row.append($('<td>').text(book.shelf));

					var check = book.rentalStatus;
					console.log("貸出ステータス:",check);

					if(check == 1){
						row.append($('<td>').text("貸出中"));
					}else{
						row.append($('<td>').text("在架中"));
					}

					row.append($('<td>').text(book.name));
					row.append($('<td>').text(book.dueDate));

					var check = book.rentalStatus;
					console.log("貸出ステータス:",check);
					console.log("検索中")


					if(check == 1){
						row.append($('<td>').append($('<button>').text("貸出").attr("disabled","true")));
						row.append($('<td>').append($('<button>').text("詳細").attr("type","button")));
						//.attr("onclick","ここにクリックしたときのfunctionを書く")
					}else{

						row.append($('<td>').append($('<button>').text("貸出").attr("type","button")));
						//.atter("onclick","ここにクリックしたときのfunctionを書く")
						row.append($('<td>').append($('<button>').text("詳細").attr("type","button")));
						//.attr("onclick","ここにクリックしたときのfunctionを書く")

					}
					table.append(row)
				});

				$('#searchedList').append(table);
			}
		}
	})
}

//本の詳細ページに移動する機能
function goDetail(bookId){
	location.href = './BookDetail.html?bookId='+bookId;

}

$(document).ready(function () {
	'use strict';

	// 更新ボタンにイベント設定
	$('#searchBtn').bind('click',displayAll);

	$('#js-btn-search').click(findByParam);

	// 初期表示用
	displayAll();

});