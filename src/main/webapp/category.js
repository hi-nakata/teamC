'use strict'

var rootUrl =  "/teamC/webapi/categories";




function findAll(){
	console.log('findAll start.')
	$.ajax({
		type: "GET",
		url: rootUrl,
		dataType: "json",
		success: renderTable
	});
}


function renderTable(data) {

	var headerRow = '<tr><th>ID</th><th>カテゴリ名</th></tr>';

	$('#cate').children().remove();

	if (data.length === 0) {
		$('#cate').append('<p>現在データが存在していません。</p>')
	} else {
		console.log(data);
		var table = $('<table>').attr('border', 1);
		table.append(headerRow);
		$.each(data, function(index, cate) {
			var row = $('<tr>');
			row.append($('<td>').text(cate.categoryId));
			row.append($('<td>').text(cate.categoryName));
			row.append($('<td>').append(
					$('<button>').text("編集").attr("type","button")
				));
			row.append($('<td>').append(
					$('<button>').text("削除").attr("type","button")
				));
			table.append(row);
		});

		$('#cate').append(table);
	}

}

$(document).ready(function () {
	'use strict';

	// 更新ボタンにイベント設定
	$('#searchBtn').bind('click',findAll);


	// 初期表示用
	findAll();

});