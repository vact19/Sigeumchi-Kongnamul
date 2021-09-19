// let index = {
//     init:function (){
//         $("#btn-save").on("click", () => { // listener 생성. function(){} 사용하려면 this 바인딩 명시 필요
//             this.save(); // 클릭되면 save 실행
//         });
//     },
//
//     save: function (){
//         //alert('user의 save함수 호출됨');
//         let data ={
//             userid: $("#userid").val()
//         }
//
//         // ajax를 이용 데이터를 json으로 변경하여 요청. 기본적으로 비동기 호출
//         $.ajax({
//             type:"POST",
//             url:"/ajax",
//             data:JSON.stringify(data), // 자바스크립트 오브젝트인 let data를 Json 문자열로 전환해 body에 전달
//             contentType: "application/json; charset=utf-8", // request body 데이터 타입
//             dataType: "json",  // response를 어떤 식으로 받을 것인지.  json이 오면 자바스크립트 오브젝트로 변환됨
//         }).done(function (resp){
//             // 위 ajax절 결과가 정상일 시 실행, fail은 비정상 시 실행
//             alert(" done 정상 실행");
//             alert(resp.address);
//
//             $("#userName").val(resp.address) ;  //  val()은 값 리턴, val(...)은 값 설정
//             $("#btn-save").text(resp.address);
//
//
//         }).fail(function (error){
//             alert(JSON.stringify(error));
//         });
//
//     }
//
// }
//
// index.init(); // 브라우저가 js를 해석할 때 index라는 오브젝트의
// // init을 실행해 대기시킴
