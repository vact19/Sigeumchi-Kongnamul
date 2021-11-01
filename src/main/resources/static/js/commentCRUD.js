function addComment() {
    let form = document.getElementById("comment");

    let url = window.location.href+"/addComment";
    form.setAttribute("action", url)

    let name = document.getElementById("userName");
    form.submit();
}
    function showUpdateArea(index){ // 수정버튼을 눌렀을 때
    let form = document.getElementById("commentUpdate");
    form.removeAttribute("hidden"); // 가린거 드러내기
    let userList = document.getElementById('userList');
    /**
     * hidden 속성 엘리먼트에 행값 숨겨넣기
     */
    let indexElement = document.getElementById("commRow");
    indexElement.setAttribute("value", index);
}
    function updateComment(){
    // TextArea쪽에서 post form 가져옴
    let userList = document.getElementById('userList');
    let index = document.getElementById("commRow").value;
    index++;
    let commentId = userList.rows[index].cells[0].innerText;  // 행값 index에 해당하는 댓글ID값
    let content=document.getElementById("textBoxU").value;

    let data={
    commentId:commentId,
    content: content
}
    $.ajax({
    type: "POST",
    url: "/test/updateComment",
    data: JSON.stringify(data), // 자바스크립트 오브젝트인 let data를 Json 문자열로 전환해 body에 전달
    contentType: "application/json; charset=utf-8", // request body 데이터 타입
    dataType: "json",  // response를 어떤 식으로 받을 것인지.  json이 오면 자바스크립트 오브젝트로 변환됨
}).done(function (resp){ // 자동 parse
    // 위 ajax절 결과가 정상일 시 실행, fail은 비정상 시 실행
    if("수정 권한 없음" === resp.updateResult){
    $("#CRUDandLikeError").text(resp.updateResult);
    $("#CRUDandLikeError").show();
} else if("수정 정상 처리"===resp.updateResult){
    userList.rows[index].cells[2].innerHTML = content;
    document.getElementById("textBoxU").value='';
}
}).fail(function (error){
    alert(JSON.stringify(error));
});
}
    // function updateComment(){
    //     // TextArea쪽에서 post form 가져옴
    //     let form = document.getElementById("commentUpdate")
    //     let url = window.location.href+"/updateComment";
    //     let userList = document.getElementById('userList');
    //     let index = document.getElementById("commRow").value;
    //     index++;
    //     let commentId = userList.rows[index].cells[0].innerText;  // 행값 index에 해당하는 댓글ID값
    //
    //     let commId = document.getElementById("commId");
    //     commId.setAttribute("value", commentId);
    //     form.setAttribute("action", url);
    //
    //     form.submit();
    // }


    function deleteComment(index){
        /**  Form.과 Input.을 직접 만들어 제출. Input  type="hidden"에는 row Index
         */
        let form = document.createElement("form");
        let input = document.createElement("input");
        let url = window.location.href+"/deleteComment";
        let userList = document.getElementById('userList');
        index++;
        let commentId = userList.rows[index].cells[0].innerText;  // 행값 index에 해당하는 댓글ID값
        form.setAttribute("method", "post");
        form.setAttribute("action", url);
        input.setAttribute("type", "hidden");
        input.setAttribute("value", commentId); input.setAttribute("name", "commentId");
        form.appendChild(input);
        document.body.appendChild(form);
        alert("댓글을 삭제합니다.");

        form.submit();
    }