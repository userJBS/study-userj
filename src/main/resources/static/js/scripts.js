// submit 클릭시 addAnswer 메서드 실행
$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
	e.preventDefault();// 버튼을 클릭해서 서버로 전송 되지 않게한다.
	console.log("click me");

	var queryString = $(".answer-write").serialize(); // 전송할 데이터를 불러온다.
	console.log("query : " + queryString); // 전송할 데이터 출력

	var url = $(".answer-write").attr("action"); // url 정보를 읽어 온다.
	console.log("url : " + url);

	$.ajax({
		type : 'post',
		url : url, // 폼에서 가져온 url 요청
		data : queryString, // 전송할 데이터
		dataType : 'json', // 데이터 타입 Json 방식
		error : onError, // 에러 처리
		success : onSuccess
	// 성공시 처리
	});

}

function onError() {

}

function onSuccess(data, status) {
	console.log(data);
	var answerTemplate = $("#answerTemplate").html();
	var template = answerTemplate.format(data.writer.userId,
			data.formattedCreateDate, data.contents, data.question.id, data.id);

	// $(".qna-comment-slipp-articles").append(template); 오름차순으로 출력해준다.
	$(".qna-comment-slipp-articles").prepend(template);

	// $(".answer-write textarea").val(""); 이렇게도 처리가능
	$("textarea[name=contents]").val("");
}

$("a.link-delete-article").click(deletAnswer);
// .link-delete-article 클래스 속성 값을 가진 녀석을 클릭시deletAnswer 메서드 실행
function deletAnswer(e) {
	e.preventDefault(); // 버튼 클릭시 넘어가는 기능 삭제

	var deleteBtn = $(this);
	var url = deleteBtn.attr("href");
	console.log("url : " + url);

	$.ajax({
		type : 'delete',
		url : url,
		dataType : 'json',
		error : function(xhr, status) {
			console.log("error");
		},
		success : function(data, status) {
			console.log(data);
			if (data.valid) {
				deleteBtn.closest("article").remove();
			} else {
				alert(data.errorMessage);
			}
		}
	});
}

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};