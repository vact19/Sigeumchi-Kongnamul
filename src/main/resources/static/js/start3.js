const main = document.querySelector("#main");
const qna = document.querySelector("#qna");
const result = document.querySelector("#result");

const endPoint = 5;
//const select = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
let select = 0;
//12`15
//16`19
//20`23
//24`27
//29`32
//32`36
function calResult(){
  console.log('결과를 계산합니다');
  console.log(select);
  let div = select
  let result=0;
  if(div<=3){ // 0, 1,2,3
    result=0
  } else if (div<=7){ //4,5,6,7
    result=1;
  } else {  //8,9,10
    result=2;
  }
  return result;
}

function setResult(){
  let point = calResult();
  console.log("cal 결과값은")
  console.log(point)

  const resultName = document.querySelector('.resultname');
  // 결과 Index point에 맞는 key.인 name.을 set함
  resultName.innerHTML = infoList[point].name;

  //이미지 처리
  let resultImg = document.createElement('img');
  const imgDiv = document.querySelector('#resultImg');
  let imgURL = '../img/image-'+point+'.png';
  console.log(imgURL);
  resultImg.src = imgURL;
  resultImg.alt = point;
  resultImg.classList.add('img-fluid');
  imgDiv.appendChild(resultImg);

  // 이미지 설명 처리
  const resultDesc = document.querySelector('.resultDesc');
  resultDesc.innerHTML = infoList[point].desc;

  const obShareUrl = document.getElementById("ShareUrl");
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
  const a = document.querySelector('.answerBox');
  const answer = document.createElement('button');
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
    let children = document.querySelectorAll('.answerList');
    for(let i = 0; i < children.length; i++){
      children[i].disabled = true;
      children[i].style.WebkitAnimation = "fadeOut 0.5s";
      children[i].style.animation = "fadeOut 0.5s";
    }

    //해당 Index. 의 타입 { answer: 'a. 바로 먼저 연락한다.', type: ['mouse', 'rabbit', 'tiger', 'monkey'] },
    // 타입의 길이를 구해 길이에 맞게  select 배열에 추가점수
    setTimeout(() => {
      select =  parseInt(select) + (parseInt(idx));
      console.log((parseInt(idx)+parseInt(1)));
      console.log(select);
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
  let q = document.querySelector('.qBox');
  q.innerHTML = qnaList[qIdx].q;

  for(let i in qnaList[qIdx].a){ //qnaList.에서  qIdx.에 맞는 키 a의 밸류 배열을 하나씩 담는다.
    //console.log(i); // 0 , 1, 2가 찍힘
    addAnswer(qnaList[qIdx].a[i].answer, qIdx, i);
  }
  // 상태 바
  let status = document.querySelector('.statusBar');
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
