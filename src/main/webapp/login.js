var rootUrl = "/teamC/webapi/accounts";

function login(){
	var fd = new FormData(document.getElementById("loginForm"));
	console.log(fd)

	$.ajax({
		url : rootUrl,
		type : "POST",
		data : fd,
		contentType : false,
		processData : false,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			if(data==true){
				alert('loginに成功しました');
				location.href ='./MyPage.html?userId='+$('#js-input-loginId').val()
			}else{
				alert('loginに失敗しましたyo');
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('loginに失敗しました');
		}
	})
}

$(document).ready(function () {
	$('#js-btn-login').click(function(){
		login();
	})
});
