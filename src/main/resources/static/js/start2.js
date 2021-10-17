const main = document.querySelector("#main");
const qna = document.querySelector("#qna");
const result = document.querySelector("#result");

const endPoint = 3;
const select = [0, 0, 0];

function calResult(){
  console.log(select);
  var result = select.indexOf(Math.max(...select));
  console.log("2번테스트 calResult()");
  return result;
}

function setResult(){
  let point = calResult();
  // 결과 Index point에 맞는 key.인 name.을 set함
  const resultName = document.querySelector('.resultname');
  resultName.innerHTML = infoList[point].name;

  //이미지 처리
  var resultImg = document.createElement('img');
  const imgDiv = document.querySelector('#resultImg');
  var imgURL = '../img/image-' + point + '.png';
  resultImg.src = imgURL;
  resultImg.alt = point;
  resultImg.classList.add('img-fluid');
  imgDiv.appendChild(resultImg);

  // 이미지 설명 처리
  const resultDesc = document.querySelector('.resultDesc');
  resultDesc.innerHTML = infoList[point].desc;
  
  // 공유
  const obShareUrl = document.getElementById("ShareUrl");
  //주소 채워주기
  obShareUrl.value = window.document.location.href;
}

/**
 *  calResult().를 실행하는
 *  setResult()를 부르는
 *  goResult()
 */
function goResult(){
  qna.style.WebkitAnimation = "fadeOut 1s";
  qna.style.animation = "fadeOut 1s";
  setTimeout(() => {
    result.style.WebkitAnimation = "fadeIn 1s";
    result.style.animation = "fadeIn 1s";
    setTimeout(() => {
      qna.style.display = "none";
      result.style.display = "block"
    }, 450)})
    setResult();
}

function addAnswer(answerText, qIdx, idx){
  var a = document.querySelector('.answerBox');
  var answer = document.createElement('button');
  answer.classList.add('answerList');
  answer.classList.add('my-3');
  answer.classList.add('py-3');
  //answer.classList.add('mx-auto');
  answer.classList.add('fadeIn');

  a.appendChild(answer);
  answer.innerHTML = answerText; // idx.번째 답변에 setText

  // 답변 박스에 하나씩 EventListener 달기
  answer.addEventListener("click", function(){
    // answerList(3개) 모두 가져와서 다음으로 넘어가는 css 설정함
    var children = document.querySelectorAll('.answerList');
    for(let i = 0; i < children.length; i++){
      children[i].disabled = true;
      children[i].style.WebkitAnimation = "fadeOut 0.5s";
      children[i].style.animation = "fadeOut 0.5s";
    }
    //해당 Index. 의 타입 { answer: 'a. 바로 먼저 연락한다.', type: ['mouse', 'rabbit', 'tiger', 'monkey'] },
    // 타입의 길이를 구해 길이에 맞게  select 배열에 추가점수
    setTimeout(() => {
      // 해당 qna리스트 데이터의 [질문번호].[답변]
      var target = qnaList[qIdx].a[idx].type;
      for(let i = 0; i < target.length; i++){
        select[target[i]] += 1;
      }

      //클릭한 박스 사라지게 하기
      for(let i = 0; i < children.length; i++){
        children[i].style.display = 'none';
      }
      goNext(++qIdx);
    },450)
  }, false);
}
/**
 *   다음으로 진행 ( addAnswer 호출 )
 */
function goNext(qIdx){
  if(qIdx === endPoint){
    goResult();
    return;
  }
  // qIdx에 맞는 질문 세팅
  var q = document.querySelector('.qBox');
  q.innerHTML = qnaList[qIdx].q;
  for(let i in qnaList[qIdx].a){//qnaList.에서  qIdx.에 맞는 키 a의 밸류 배열을 하나씩 담는다.
    //console.log(i); // 0 , 1, 2가 찍힘
    addAnswer(qnaList[qIdx].a[i].answer, qIdx, i);
  }
  // 상태 바
  var status = document.querySelector('.statusBar');
  status.style.width = (100/endPoint) * (qIdx+1) + '%';
}

/**
 *   시작
 */
function begin(){
  main.style.WebkitAnimation = "fadeOut 1s";
  main.style.animation = "fadeOut 1s";
  setTimeout(() => {
    qna.style.WebkitAnimation = "fadeIn 1s";
    qna.style.animation = "fadeIn 1s";
    setTimeout(() => {
      main.style.display = "none";
      qna.style.display = "block"
    }, 450)
    let qIdx = 0;
    goNext(qIdx);
  }, 450);
}
