var rootUrl = "/teamC/webapi/books";

var bookId = location.search.substring( 1, location.search.length );
bookId = decodeURIComponent( bookId );
bookId = bookId.split('=')[1];

//console.log('displayAll start - userId:' + userId);

$(document).ready(function () {

	if (bookId === undefined)
		console.log("hage")
	else
		fillEditData(bookId);

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

		if (bookId === undefined)
			addBook();
		else
			updateBook(bookId);
		return false;
	})
});

function fillEditData(id){

	//bookテーブルからbookIdで情報をとってきてinputのvalueにつめる
	var rootUrlInit = rootUrl + '?bookIdParam='+ id;
	$.ajax({
		type : "GET",
		url : rootUrlInit,
		dataType : "json",
		success : function(json){
			console.log('通信に成功しました。')
			console.log(json);
			//inputにつめる
			$('#form-title').attr('value',json.title)
			$('#form-author').attr('value',json.author)
			$('#form-publisher').attr('value',json.publisher)
			$('#form-pubdate').attr('value',json.pubdate)
			$('#form-bookshelf').attr('value',json.shelf)
			$('<button>').text("貸出").attr("onclick", "rental("+ id +')')
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('社員データの追加に失敗しました');
		}
	});

}

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
			location.href ='./BookDetail.html'
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
			location.href ='./bookDetail.html'
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('社員データの更新に失敗しました');
		}
	})
}