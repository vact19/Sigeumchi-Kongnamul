document.querySelector('#title').innerHTML="자기애 테스트";
document.querySelector(".desc").innerText=" 당신은 당신을 얼마나 사랑하나요? "
document.querySelector("#test_image").src="../img/image-8.png";
/*
자격지심,0
평범,1
자존감, 2
자만감, 3
*/

const qnaList = [
  {
    q: '1. 나는 거만한 편이라 생각한다. ',
    a: [
      { answer: 'a. 그렇다.',type: [3]},
      { answer: 'b. 아니다',type: [0,1,2]},
      { answer: 'c. 보통.',type: [2]},
    ]
  },
  {
    q: '2. 다른 사람보다 나는 뛰어나다. ',
    a: [
      { answer: 'a. 그렇다.',type: [2,3]},
      { answer: 'b. 아니다',type: [0]},
      { answer: 'c. 보통.',type: [1, 2]},
    ]
  },
  {
    q: '3. 다른 사람들이 날 따르기를 원한다. ',
    a: [
      { answer: 'a. 그렇다.',type: [3]},
      { answer: 'b. 아니다',type: [0,1]},
      { answer: 'c. 보통.',type: [2]},
    ]
  },
  {
    q: '4. 사람들의 칭찬이 부끄럽다. ',
    a: [
      { answer: 'a. 그렇다.',type: [0]},
      { answer: 'b. 아니다',type: [2,3]},
      { answer: 'c. 보통.',type: [1, 2]},
    ]
  },
  {
    q: '5. 나는 능력이 뛰어나다. ',
    a: [
      { answer: 'a. 그렇다.',type: [2,3]},
      { answer: 'b. 아니다',type: [0]},
      { answer: 'c. 보통.',type: [1]},
    ]
  },
  {
    q: '6. 거울을 보는 것을 좋아한다. ',
    a: [
      { answer: 'a. 그렇다.',type: [3]},
      { answer: 'b. 아니다',type: [0,1]},
      { answer: 'c. 보통.',type: [2]},
    ]
  },
]

const infoList = [
  {
    name: ' 자격지심 ',
    desc: ' 자신을 좀 더 사랑하는게 어떨까? '
  },
  {
    name: ' 평범 그자체 ',
    desc: ' 자신만의 특별함을 찾아봐 '
  },
  {
    name: ' 자존감 충만 ',
    desc: ' 자신을 긍정적으로 평가하는 건강한 마음! '
  },
  {
    name: ' 나르시시스트 ',
    desc: ' 너의 관점이 항상 옳지는 않을 수도 있어 '
  },
]