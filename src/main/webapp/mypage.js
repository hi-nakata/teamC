'use strict';

var rootUrl = "/teamC/webapi/rentals";

function displayRental(){

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

function displayHistory(){
	var userId = location.search.substring( 1, location.search.length );
	userId = decodeURIComponent( userId );
	userId = userId.split('=')[1];

	console.log('displayHistory start - userId:' + userId);

	$.ajax({
		type : "GET",
		url : rootUrl+ '/history/'+userId ,
		dataType : "json",
		success : function(json){
			console.log('通信に成功しました。')
			console.log(json);

			var headerRow='<tr><th>タイトル</th><th>返却日</th><th>レーティング</th>'
				+'<th>コメント</th><th></th></tr>';

			$('#history').children().remove();

			if(json.length === 0){
				$('#history').append('<p>現在データが存在していません。</p>')
			}else{
				var table = $('<table>');

				table.append(headerRow);

				$.each(json,function(index,rental){
					var row = $('<tr>')
					row.append($('<td>').text(rental.title));
					row.append($('<td>').text(rental.backDate));
					row.append($('<td>').text(rental.rating));
					row.append($('<td>').text(rental.comment));
					//row.append($('<input type="text" id="rating ">'));
					//row.append($('<input type="text" id="comment">'));

					row.append($('<td>').append(
							$('<button>').text("登録").attr("type","button").attr("onclick", "("+rental.bookId+')')
						));
					table.append(row);
					//Line通知
					sendLineNotify(rental);


				});
				$('#history').append(table);
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
			displayRental();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('返却処理に失敗しました');
		}
	})
}

function goBookDetail(bookId){
	//location.href ='./BookDetail.html?bookId='+bookId;
	var settedUrl = './BookDetail.html?bookId='+bookId;
	//新規Windowで表示
	//location.href = './BookDetail.html?bookId='+id;
	window.open(settedUrl);
}

function goBookSearch(){
	location.href ='./BookSearch.html'
}

function goMyPage(){
	var hogepiyo =localStorage.getItem("userId");
	location.href ='./MyPage.html?userId='+hogepiyo;
}

function hyoujiUserName(){
	$('#hoge').append(localStorage.getItem("userName"));
}

function sendLineNotify(data){
	console.log('らいん');
	var notify = { "value1" : data.title+"返せ"};
	var key = 'b-KSby48PR5DgiLcEXBh_B'
	var url ='https://maker.ifttt.com/trigger/book_alart/with/key/'+key;
//	$.post( url, notify )
//	.done(function( data ) {
//		console.log( data);
//	})
//	$.ajax({
//		  type: 'POST',
//		  url: url,
//		  data: notify,
////		  dataType: dataType
////		  success: success,
//		});
	$.ajax({
		type: "POST",
		data:notify,
		url: url,
	    xhrFields: {
	        withCredentials: true
	    },
	    //dataType: "jsonp",
		success: function() {
			console.log("dekita")
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('dame');
		}
	})

//	axios({
//		  url: url,
//		  method: 'POST',
//		  headers: {
//		    'Content-Type': 'application/x-www-form-urlencoded',
//		  },
//		  data: json
//		}).then(res => {
//		    //成功時の処理
//		    console.log(res)
//		  })
//		  .catch(err => {
//		    //エラー時の処理
//		    console.error
//		  })
	}


$(document).ready(function () {
	'use strict';

	// 初期表示用
	displayRental();
	//displayHistory();
	hyoujiUserName();

	$('#js-btn-search').click(goBookSearch);
	$('#js-btn-mypage').click(goMyPage);

});