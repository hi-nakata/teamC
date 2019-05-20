$(document).ready(function () {
	$('#form-btn-regist').click(function() {
		$('.error').children().remove();//error出力欄をリセット
		if ($('#name').val() === '') {
			$('.error').append('<div>名前は必須入力です。</div>');
		}
		if ($('#empId').val() === '') {
			$('.error').append('<div>社員IDは必須入力です。</div>');
		}
		if ($('#empId').val() === '') {
			$('.error').append('<div>社員IDは必須入力です。</div>');
		}
		if ($('#age').val() === '') {
			$('.error').append('<div>年齢は必須入力です。</div>');
		}
		if ($('#gender').val() === '') {
			$('.error').append('<div>性別として男性、女性いずれかを選択してください。</div>');
		}
		if ($('#zip').val() === '') {
			$('.error').append('<div>郵便番号は必須入力です。</div>');
		}
		if ($('#pref').val() === '') {
			$('.error').append('<div>都道府県は必須入力です。</div>');
		}
		if ($('#address').val() === '') {
			$('.error').append('<div>住所は必須入力です。</div>');
		}
		if ($('#postId').val() === '') {
			$('.error').append('<div>いずれかの部署を選択してください。</div>');
		}
		if ($('.error').children().length != 0) {
			return false;
		}

		var id = $('#id').val()//保存ボタンを押したときUrlパラメータにIDがなければ追加、あれば更新
		if (id === '')
			addBook();
		else
			updateBook(id);
		return false;
	})
});

function addBook() {
	console.log('addBook start');

	var fd = new FormData(document.getElementById("book-detail"));

	$.ajax({
		url : rootUrl,
		type : "POST",
		data : fd,
		contentType : false,
		processData : false,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			alert('社員データの追加に成功しました');
			location.href ='./bookDetail.html'
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('社員データの追加に失敗しました');
		}
	})
}

function updateBook(id) {
	console.log('updateEmployee start');

	var fd = new FormData(document.getElementById("book-detail"));

	$.ajax({
		url : rootUrl + '/' + id,
		type : "PUT",
		data : fd,
		contentType : false,
		processData : false,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			alert('社員データの更新に成功しました');
			findAll();
			renderDetails(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('社員データの更新に失敗しました');
		}
	})
}