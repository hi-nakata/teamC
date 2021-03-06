'use strict'

var rootUrl =  "/teamC/webapi/rentals";


function resist(){
	var historyName = $('#historyName').val();
	if(historyName === ''){
		$('.error').text('コメントは必須入力です。');
		return false;
	}else{
		$('.error').text('');
	}

	var historyId = $('#historyId').val();
	if(historyId ==''){
		addHistory();
	}else{
		updateHistory(historyId);
		return false
	}
}




function findAll(){
	//var userId =localStorage.getItem("userId");

	var userId = location.search.substring( 1, location.search.length );
	userId = decodeURIComponent( userId );
	userId = userId.split('=')[1];

	console.log('findAll start. userId :'+ userId);

	$.ajax({
		type: "GET",
		url: rootUrl+'/history/'+userId,
		dataType: "json",
		success: renderTable
	});
}

function findById(bookId){
	var userId = location.search.substring( 1, location.search.length );
	userId = decodeURIComponent( userId );
	userId = userId.split('=')[1];

	console.log('findById start - id:'+userId +'/'+bookId);
	$.ajax({
		type : "GET",
		url : rootUrl +'/bookId/'+ userId +'/'+bookId ,
		dataTyoe :"json",
		success :function(json){
			console.log('findById success: ');
			console.log(json);
			renderDetails(json);
		}
	})
}

function addHistory() {
	console.log('addHistory start');
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: rootUrl+'/history/',
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR) {
			alert('履歴の追加に成功しました');
			$('#historyId').val(data.historyId);
			findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('履歴の追加に失敗しました');
		}
	})
}


function updateHistory(id) {
	console.log('updateHistory start');
	$.ajax({
		type: "PUT",
		contentType: "application/json",
		url: rootUrl+'/history/'+id,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR) {
			alert('コメントの更新に成功しました');
			findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('コメントの更新に失敗しました');
		}
	})
}


function renderTable(data) {

	var headerRow = '<tr><th>ID</th><th>タイトル</th><th>コメント</th></tr>';

	$('#history').children().remove();

	if (data.length === 0) {
		$('#history').append('<p>現在データが存在していません。</p>')
	} else {
		console.log(data);
		var table = $('<table>').attr('border', 1);
		table.append(headerRow);
		$.each(data, function(index, history) {
			var row = $('<tr>');
			row.append($('<td>').text(history.bookId));
			row.append($('<td>').text(history.title));
			row.append($('<td>').text(history.comment));
			row.append($('<td>').append(
					$('<button>').text("編集").attr("type","button").attr("onclick","findById("+history.bookId+')')));
			table.append(row);
		});

		$('#history').append(table);
	}

}


function renderDetails(history) {
	$('.error').text('');
	$('#historyId').val(history.bookId);
	$('#historyTitle').val(history.title);
	$('#historyComment').val(history.comment);
}

function formToJSON() {
	var bookId = $('#historyId').val();
	var userId = location.search.substring( 1, location.search.length );
	userId = decodeURIComponent( userId );
	userId = userId.split('=')[1];
	return JSON.stringify({
		"bookId": (bookId == "" ? 0 : bookId),
		"title": $('#historyTitle').val(),
		"comment": $('#historyComment').val(),
		"userId": userId
	});
}


$(document).ready(function () {
	'use strict';

	// 更新ボタンにイベント設定
	$('#searchBtn').bind('click',findAll);

	$('#saveHistory').click(resist);

	$('#newHistory').click(function() {
		renderDetails({});
	});





	// 初期表示用
	findAll();

});