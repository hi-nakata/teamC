'use strict'

var rootUrl = ;

findAll();


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
		var table = $('<table>').attr('border', 1);
		table.append(headerRow);
		$.each(data, function(index, post) {
			var row = $('<tr>');
			row.append($('<td>').text());
			row.append($('<td>').text());
			row.append($('<td>').append(
					$('<button>').text("編集").attr("type","button").attr("onclick", "findById("+post.id+')')
				));
			row.append($('<td>').append(
					$('<button>').text("削除").attr("type","button").attr("onclick", "deleteById("+post.id+')')
				));
			table.append(row);
		});

		$('#cate').append(table);
	}

}