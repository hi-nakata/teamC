'use strict'

var rootUrl =  "/teamC/webapi/categories";


function resist(){
	var cateName = $('#cateName').val();
	if(cateName === ''){
		$('.error').text('カテゴリ名は必須入力です。');
		return false;
	}else{
		$('.error').text('');
	}

	var cateId = $('#cateId').val();
	if(cateId ==''){
		addCate();
	}else{
		updateCate(cateId);
		return false
}
}




function findAll(){
	console.log('findAll start.')
	$.ajax({
		type: "GET",
		url: rootUrl,
		dataType: "json",
		success: renderTable
	});
}

function findById(categoryId){
	console.log('findById start - id:'+categoryId);
	$.ajax({
		type : "GET",
		url : rootUrl +'/'+ categoryId,
		dataTyoe :"json",
		success :function(json){
			console.log('findById success: ');
			console.log(json);
			renderDetails(json);
		}
	})
}

function addCate() {
	console.log('addCate start');
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: rootUrl,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR) {
			alert('カテゴリデータの追加に成功しました');
			$('#cateId').val(data.categoryId);
			findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('カテゴリデータの追加に失敗しました');
		}
	})
}


function updateCate(id) {
	console.log('updatePost start');
	$.ajax({
		type: "PUT",
		contentType: "application/json",
		url: rootUrl+'/'+id,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR) {
			alert('カテゴリデータの更新に成功しました');
			findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('カテゴリデータの更新に失敗しました');
		}
	})
}

function deletedById(id) {
	console.log('delete start - id:'+id);
	$.ajax({
		type: "DELETE",
		url: rootUrl+'/'+id,
		success: function() {
			alert('カテゴリデータの削除に成功しました');
			findAll();
			$('#cateId').val('');
			$('#cateName').val('');
		}
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
					$('<button>').text("編集").attr("type","button").attr("onclick","findById("+cate.categoryId+')')));
			row.append($('<td>').append(
					$('<button>').text("削除").attr("type","button").attr("onclick","deletedById("+cate.categoryId+')')));
			table.append(row);
		});

		$('#cate').append(table);
	}

}


function renderDetails(cate) {
	$('.error').text('');
	$('#cateId').val(cate.categoryId);
	$('#cateName').val(cate.categoryName);
}

function formToJSON() {
	var cateId = $('#cateId').val();
	var cateName=$('#cateName').val();
	return JSON.stringify({
		"categoryId": (cateId == "" ? 0 : cateId),
		"categoryName": $('#cateName').val()
	});
}


$(document).ready(function () {
	'use strict';

	// 更新ボタンにイベント設定
	$('#searchBtn').bind('click',findAll);

	$('#saveCate').click(resist);

	$('#newCate').click(function() {
		renderDetails({});
	});





	// 初期表示用
	findAll();

});