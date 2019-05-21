'use strict'

var rootUrl =  "/teamC/webapi/categories";


$('saveCat').click(function(){

	var catName = $('catName').val();
	if(catName === ''){
		$('.error').text('は必須入力です。');
		return false;
	}else{
		$('.error').text('');
	}

	var catId = $('#catId').val()
	if(id=='')
		addCat
		else
			updateCat(catId)
			return false
})



function findAll(){
	console.log('findAll start.')
	$.ajax({
		type: "GET",
		url: rootUrl,
		dataType: "json",
		success: renderTable
	});
}

function findById(id){
	console.log('findById start - id:'+id);
	$.ajax({
		type : "GET",
		url : rootUrl +'/'+ id,
		dataTyoe :"json",
		success :function(data){
			console.log('findById success: ' + data.name);
			renderDetails(data);
		} 
	})
}

function addCat() {
	console.log('addCat start');
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: rootUrl,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR) {
			alert('カテゴリデータの追加に成功しました');
			$('#catId').val(data.id);
			findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('カテゴリデータの追加に失敗しました');
		}
	})
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