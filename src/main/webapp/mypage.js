'use strict';

var rootUrl = "/java_s04/api/v1.1/rentals";

findById();

/** $('#saveExpense').click(function() {
	var name = $('#name').val();
	if (name === '') {
		$('.error').text('名前は必須入力です。');
		return false;
	} else {
		$('.error').text('');
	}

	var id = $('#expenseId').val()
	if (id == '')
		addExpense();
	else
		updateExpense(id);
	return false;
}) */

/** $('#newExpense').click(function() {
	renderDetails({});
});  */

 /** function findAll(){
	console.log('findAll start.')
	$.ajax({
		type: "GET",
		url: rootUrl,
		dataType: "json",
		success: renderTable
	});
}  */

function findById(id) {
	console.log('findByID start - id:'+id);
	$.ajax({
		type: "GET",
		url: rootUrl+'/'+id,
		dataType: "json",
		success: function(data) {
			console.log('findById success: ' + data.name);
			renderTable(data)
		}
	});
}

/** function addExpense() {
	console.log('addExpense start');
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: rootUrl,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR) {
			alert('経費データの追加に成功しました');
			$('#expenseId').val(data.id);
			findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('経費データの追加に失敗しました');
		}
	})
}  */

/** function updateExpense(id) {
	console.log('updateExpense start');
	$.ajax({
		type: "PUT",
		contentType: "application/json",
		url: rootUrl+'/'+id,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR) {
			alert('経費データの更新に成功しました');
			findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('経費データの更新に失敗しました');
		}
	})
}  */


/** function deleteById(id) {
	console.log('delete start - id:'+id);
	$.ajax({
		type: "DELETE",
		url: rootUrl+'/'+id,
		success: function() {
			findAll();
			$('#expenseId').val('');
			$('#date').val('');
			$('#name').val('');
			$('#title').val('');
			$('#money').val('');
		},
		error:function(XMLHttpRequest,textStatus, errorThrown){
			alert('データの通信に失敗');
		}
	});
}  */


function renderTable(data) {
	var headerRow = '<tr><th>ID</th><th>タイトル</th><th>返却予定日</th><th>ステータス</th></tr>';

	$('#rentals').children().remove();

	if (data.length === 0) {
		$('#rentals').append('<p>現在借りている本はありません。</p>')
	} else {
		var table = $('<table>').attr('border', 1);
		table.append(headerRow);
		$.each(data, function(index, expense) {
			var row = $('<tr>');
			row.append($('<td>').text(expense.id));
			row.append($('<td>').text(expense.date));
			row.append($('<td>').text(expense.name));
			row.append($('<td>').text(expense.title));
			row.append($('<td>').text(expense.money));
			row.append($('<td>').append(
					$('<button>').text("編集").attr("type","button").attr("onclick", "findById("+expense.id+')')
				));
			row.append($('<td>').append(
					$('<button>').text("削除").attr("type","button").attr("onclick", "deleteById("+expense.id+')')
				));
			table.append(row);
		});

		$('#expenses').append(table);
	}

}


/** function renderDetails(expense) {
	$('.error').text('');
	$('#expenseId').val(expense.id);
	$('#date').val(expense.date);
	$('#name').val(expense.name);
	$('#title').val(expense.title);
	$('#money').val(expense.money);
}
*/


function formToJSON() {
	var expenseId = $('#expenseId').val();
	return JSON.stringify({
		"id": (expenseId == "" ? 0 : expenseId),
		"date": $('#date').val(),
		"name": $('#name').val(),
		"title": $('#title').val(),
		"money": $('#money').val()
	});
}
