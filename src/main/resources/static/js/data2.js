document.querySelector('#title').innerHTML="내게 맞는 웹 프로그래밍 언어";
document.querySelector(".desc").innerText="어떤 언어로 프로그래밍을 시작해 볼까요? "
document.querySelector("#test_image").src="../img/image-2.png";
/*
java,0
python,1
js 2
*/
const qnaList = [
  {
    q: '1. 내가 희망하는 기업의 규모는?',
    a: [
      { answer: 'a. 나는 스타트업이 좋다.',type: [1,2]},
      { answer: 'b. 서비스 규모가 큰 회사가 좋다..',type: [0]},
      { answer: 'c. 잘  모르겠다.',type: [0, 1, 2]},
    ]
  },
  {
    q: '2. 영어 독해에 자신 있나요? ',
    a: [
      { answer: 'a. 그정도는 문제 없어요',type: [1,2] },
      { answer: 'b. 아니요. 영어 울렁증이 있어요',type: [0] },
      { answer: 'c. 글쎄요? ',type: [0, 1, 2]},
    ]
  },
  {
    q: '3. 아래 문구 중 가장 끌리는 문구는?',
    a: [
      { answer: 'a. 높은 점유율을 바탕으로 한 방대한 자료들을 이용.',type: [0] },
      { answer: 'b. 한 언어로 프론트와 백엔드 양쪽에서 사용.',type: [2] },
      { answer: 'c. 간결한 문법, 이미 만들어져있는 다양한 기능들.',type: [1]},
    ]
  }
]
const infoList = [
  {
    name: ' 너는 자바 ',
    desc: ' 자바 '
  },
  {
    name: ' 너는 파이썬 ',
    desc: ' 파이썬설명 '
  },
  {
    name: ' 너는 자바스크립트 ',
    desc: ' 짜바스크립트 '
  }

]
