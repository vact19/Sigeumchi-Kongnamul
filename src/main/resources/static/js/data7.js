document.querySelector('#title').innerHTML="방콕족 테스트";
document.querySelector(".desc").innerText=" 당신은 방콕족이신가요? "
document.querySelector("#test_image").src="../img/image-7.png";
/*
0 매우 양호
1 양호
2 주의
3 위험
*/
const qnaList = [
  {
    q: ' 1. 새로운 사람들과 만나도 불편함이 없다.  ',
    a: [
      { answer: 'a. 그렇다',type: [0]},
      { answer: 'b. 보통',type: [1,2]},
      { answer: 'c. 아니다',type: [2,3]},
    ]
  },
  {
    q: ' 2. 친구를 오랜만에 만나도 불편하다. ',
    a: [
      { answer: 'a. 그렇다',type: [3]},
      { answer: 'b. 보통',type: [1,2]},
      { answer: 'c. 아니다',type: [0]},
    ]
  },
  {
    q: '3. 상대방에게 말을 먼저 거는 것이 어렵다. ',
    a: [
      { answer: 'a. 그렇다',type: [3]},
      { answer: 'b. 보통',type: [1,2]},
      { answer: 'c. 아니다',type: [0,1]},
    ]
  },
  {
    q: '4. 사람들이 대화를 나눌 때 잘 끼지 못한다. ',
    a: [
      { answer: 'a. 그렇다',type: [3]},
      { answer: 'b. 보통',type: [1,2]},
      { answer: 'c. 아니다',type: [0,1]},
    ]
  },
  {
    q: '5. 혼자 있는게 좋다. ',
    a: [
      { answer: 'a. 그렇다',type: [2,3]},
      { answer: 'b. 보통',type: [1,2]},
      { answer: 'c. 아니다',type: [0]},
    ]
  },
  {
    q: '6. 사람들과 연락하는 것을 싫어한다. ',
    a: [
      { answer: 'a. 그렇다',type: [3]},
      { answer: 'b. 보통',type: [1,2]},
      { answer: 'c. 아니다',type: [0,1]},
    ]
  },
  {
    q: '7. 누군가와 같이 활동하는게 어렵다. ',
    a: [
      { answer: 'a. 그렇다',type: [2,3]},
      { answer: 'b. 보통',type: [1,2]},
      { answer: 'c. 아니다',type: [0]},
    ]
  },
  {
    q: '8. 지인에게 먼저 만나자고 하는 편이다. ',
    a: [
      { answer: 'a. 그렇다',type: [0]},
      { answer: 'b. 보통',type: [1,2]},
      { answer: 'c. 아니다',type: [2,3]},
    ]
  },
  {
    q: '9. 마지막으로 친구를 본게 한달 전이다. ',
    a: [
      { answer: 'a. 그렇다',type: [2,3]},
      { answer: 'b. 보통',type: [1,2]},
      { answer: 'c. 아니다',type: [0]},
    ]
  },
]
const infoList = [
  {
    name: ' 밖이 좋아! ',
    desc: ' 사람들과 어울려 다니는 걸 좋아하는 당신은 인싸! '
  },
  {
    name: ' 평범 ',
    desc: ' 집이든 밖이든 상관없어 '
  },
  {
    name: ' 약간 위험 ',
    desc: ' 사람들과 좀 더 자주 만나보세요. '
  },
  {
    name: ' 방콕족 ',
    desc: ' 밖에 좀 나가봐! '
  },

]
