'use strict';

var rootUrl = "/teamC/webapi/books";


var timer;

timer = setTimeout(function () {
	location.reload();
}, 30000);


/*すべての本を一覧表示する機能*/
function displayAll(){
	console.log('displayAll start.');

	var rootUrlInit = rootUrl+'/findAll';
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
			console.log('消した')

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
						row.append($('<td>').append($('<button>').text("貸出").attr("disabled","true").attr("onclick","tryRental("+book.id+')')));
						row.append($('<td>').append($('<button>').text("詳細").attr("type","button").attr("onclick","goDetail("+book.id+')')));
					}else{

						row.append($('<td>').append($('<button>').text("貸出").attr("type","button").attr("onclick","tryRental("+book.id+')')));
						row.append($('<td>').append($('<button>').text("詳細").attr("type","button").attr("onclick","goDetail("+book.id+')')));
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
					console.log("検索中");

					var id = book.id;
					console.log(id);
					if(check == 1){
						row.append($('<td>').append($('<button>').text("貸出").attr("disabled","true").attr("onclick","tryRental("+book.id+')')));
						row.append($('<td>').append($('<button>').text("詳細").attr("type","button").attr("onclick","goDetail("+book.id+')')));
					}else{

						row.append($('<td>').append($('<button>').text("貸出").attr("type","button").attr("onclick","tryRental("+book.id+')')));
						row.append($('<td>').append($('<button>').text("詳細").attr("type","button").attr("onclick","goDetail("+book.id+')')));
					}
					table.append(row)
				});

				$('#searchedList').append(table);
			}

			clearTimeout(timer);
		}
	})
}

//本の貸出を行う機能
function tryRental(id){
	console.log('tryRental start.');

	$.ajax({
		type : 'POST',
		url:  "/teamC/webapi/rentals"+"/"+id,
		dataType : "json" ,
		success : function(){
			alert('貸し出しました');
			displayAll();
		},error: function(jqXHR, textStatus, errorThrown){
			alert('貸出処理に失敗しました。')
		}

	})
}

//本の詳細ページに移動する機能
function goDetail(id){
	var t = id
	console.log(t);
	location.href = './BookDetail.html?bookId='+id;

}



$(document).ready(function () {
	'use strict';

	// 更新ボタンにイベント設定
	$('#searchBtn').bind('click',displayAll);

	$('#js-btn-search').click(findByParam);

	$('#js-btn-detail').click(goDetail);

	// 初期表示用
	displayAll();

});