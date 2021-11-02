document.querySelector('#title').innerHTML="건강 테스트";
document.querySelector(".desc").innerText=" 건강관리, 잘 하고 계신가요? "
document.querySelector("#test_image").src="../img/image-6.png";
/*
0 심각
1 경고
2 보통
3 건강
*/
const qnaList = [
  {
    q: ' 1. 일주일에 운동을 몇시간 하시나요? ',
    a: [
      { answer: '3시간 초과',type: [3]},
      { answer: '1시간 이상',type: [1,2]},
      { answer: '1시간 미만',type: [0]},
    ]
  },
  {
    q: ' 2. 운동을 하는 이유는? ',
    a: [
      { answer: '살빼기 위해',type: [0,1] },
      { answer: '건강 유지를 위해',type: [2] },
      { answer: ' 활력적인 삶을 위해 ',type: [3]},
    ]
  },
  {
    q: '3. 아침을 드시나요?',
    a: [
      { answer: 'a. 간단하게라도 꼭 먹는다. ',type: [3] },
      { answer: 'b. 있으면 먹지만 귀찮으면 안먹는다. ',type: [1,2] },
      { answer: 'c. 점심을 많이 먹으면 된다. ',type: [0]},
    ]
  },
  {
    q: '4. 탄산음료, 정크푸드 등을 즐겨 먹나요? ',
    a: [
      { answer: 'a. 안먹는다. ',type: [3] },
      { answer: 'b. 누가 사주면 먹는다. ',type: [2] },
      { answer: 'c. 내돈내산 ',type: [0,1]},
    ]
  },
  {
    q: '5. 밖에 얼마나 자주 나가시나요? ',
    a: [
      { answer: 'a. 별로 필요성을 느끼지 못한다. ',type: [0,1] },
      { answer: 'b. 누가 부르면 나간다. ',type: [2] },
      { answer: 'c. 약속이 없더라도 나가는 편 ',type: [3] },
    ]
  },
  {
    q: '6. 수면시간이 어느정도 인가요? ',
    a: [
      { answer: 'a. 6시간 이상 ',type: [2,3] },
      { answer: 'b. 6시간 이하 ',type: [0,1] },
    ]
  },
]

const infoList = [
  {
    name: ' "심각" 수준입니다. ',
    desc: ' 잠시 밖에 나가서 걸어보는건 어떨까요? '
  },
  {
    name: ' "경고" 수준입니다. ',
    desc: ' 식단을 바꿔보시는걸 추천드려요! '
  },
  {
    name: ' "보통" 수준입니다.  ',
    desc: ' 꾸준히 운동하면 몸짱이 될지도...? '
  },
  {
    name: ' "건강" 수준입니다. ',
    desc: ' 자기관리의 달인! 대단해요. '
  },

]
