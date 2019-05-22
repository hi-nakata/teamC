var rootUrl = "/teamC/webapi/books";

var bookId = location.search.substring( 1, location.search.length );
bookId = decodeURIComponent( bookId );
bookId = bookId.split('=')[1];

//console.log('displayAll start - userId:' + userId);

$(document).ready(function () {

	hyoujiUserName();

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
			$('#form-title').attr('value',json[0].title)
			$('#form-author').attr('value',json[0].author)
			$('#form-publisher').attr('value',json[0].publisher)
			$('#form-pubdate').attr('value',json[0].pubdate)
			$('#form-bookshelf').attr('value',json[0].shelf)
			$('#form-btn-rental').attr("onclick", "tryRental("+ id +')')

			renderSelectCategory(id);

			//貸出ボタンをつける
			if(json[0].rentalStatus==1){
				$('#div-btn-rental').append($('<td>').append($('<button>').text("貸出").attr("disabled","true").attr("onclick","tryRental("+json[0].id+')')));
			}else{
				$('#div-btn-rental').append($('<td>').append($('<button>').text("貸出").attr("onclick","tryRental("+json[0].id+')')));
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('社員データの追加に失敗しました');
		}
	});

}

var rootUrlCat =  "/teamC/webapi/categories/bookId/";
function renderSelectCategory(bookId){
	$.ajax({
		type : "GET",
		url : rootUrlCat+bookId,
		dataType : "json",
		success : function(json){
			//カテゴリselectの追加
			console.log(json);
			var pulldown = '<select name="form-category">'

			for(n=0;json.length-1;n++){
				if(json[n].bookId!=bookId){
					pulldown += '<option value='+json[n].categoryId+'>'+json[n].categoryName
			}else{
					pulldown += '<option value='+json[n].categoryId+' selected>'+json[n].categoryName
				}
			}
			pulldown += '</select>'
			$('js-select-category').append(pulldown);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('社員データの追加に失敗しました');
		}
	});
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
			if(data==null){
				alert('編集権限がありません');
			}else{
				console.log(data)
				alert('蔵書データを追加しました');
				location.href ='./BookDetail.html'
			}

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('蔵書データの追加に失敗しました');
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
			if(data==null){
				alert('編集権限がありません');
			}else{
				alert('蔵書データを更新しました');
				location.href ='./BookDetail.html'
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('蔵書データの更新に失敗しました');
		}
	})
}

function hyoujiUserName(){
	$('#hoge').append(localStorage.getItem("userName"));
}