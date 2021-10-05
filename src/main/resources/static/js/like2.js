function addLike(index){

        index++;
        let userList = document.getElementById('userList');
        let commentId = userList.rows[index].cells[0].innerText;  // 행값 index에 해당하는 댓글ID값
        let commentLikes = userList.rows[index].cells[3].innerText;  // 행값 index에 해당하는 댓글ID값

        let data ={
            commentId: commentId
        }

    /** json.stringify로 js 오브젝트를 문자열로 변환해 서버로 전송하고,
     *  서버에서 온 데이터를 json.parse로 js 오브젝트로 만든다.
     *   다음은 숫자 올라가는거 보이게 하기
    */
    // ajax를 이용 데이터를 json으로 변경하여 요청. 기본적으로 비동기 호출
        $.ajax({
            type:"POST",
            url:"/test/commentAddLike",
            data:JSON.stringify(data), // 자바스크립트 오브젝트인 let data를 Json 문자열로 전환해 body에 전달
            contentType: "application/json; charset=utf-8", // request body 데이터 타입
            dataType: "json",  // response를 어떤 식으로 받을 것인지.  json이 오면 자바스크립트 오브젝트로 변환됨
        }).done(function (resp){ // 자동 parse
            // 위 ajax절 결과가 정상일 시 실행, fail은 비정상 시 실행
            //alert(resp); // js Object 상태에서는 Object Object로 표시됨
            let qqq = JSON.stringify(resp);
            let www = JSON.parse(qqq);
            let eee = JSON.stringify(www);
            let  rrr = JSON.parse(eee);
            // rrr === resp. 여러번 파싱과 문자열화를 해도 같다

            //  "2는 좋아요 권한 없음 오류
            if("좋아요 권한 없음. 로그인 하세요"===rrr.rspLike){
                $("#CRUDandLikeError").text(rrr.rspLike);
                $("#CRUDandLikeError").show();
            } else if (resp.rspLike==="좋아요 정상 처리"){
                commentLikes++;
                userList.rows[index].cells[3].innerText = commentLikes;
            } else if(resp.rspLike==="좋아요는 한 번만"){
                alert("좋아요는 한 번만");
            }

        }).fail(function (error){
            alert(JSON.stringify(error));
        });

    }


// init을 실행해 대기시킴


// let indx = {
//     init:function (){
//         $(".btn-like").on("click", () => { // listener 생성. function(){} 사용하려면 this 바인딩 명시 필요
//             this.save(); // 클릭되면 save 실행
//         });
//     },
//
//     save: function (){
//         //alert('user의 save함수 호출됨');
//         let data ={
//             commentId: "1"
//         }
//
//         // ajax를 이용 데이터를 json으로 변경하여 요청. 기본적으로 비동기 호출
//         $.ajax({
//             type:"POST",
//             url:"/test/commentAddLike",
//             data:JSON.stringify(data), // 자바스크립트 오브젝트인 let data를 Json 문자열로 전환해 body에 전달
//             contentType: "application/json; charset=utf-8", // request body 데이터 타입
//             dataType: "json",  // response를 어떤 식으로 받을 것인지.  json이 오면 자바스크립트 오브젝트로 변환됨
//         }).done(function (resp){
//             // 위 ajax절 결과가 정상일 시 실행, fail은 비정상 시 실행
//             alert(" done 정상 실행");
//             alert(resp);
//         }).fail(function (error){
//             alert(JSON.stringify(error));
//         });
//
//     }
//
// }
// indx.init(); // 브라우저가 js를 해석할 때 index라는 오브젝트의
// // init을 실행해 대기시킴
