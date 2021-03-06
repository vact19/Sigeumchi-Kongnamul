document.querySelector('#title').innerHTML="내향성 테스트";
document.querySelector(".desc").innerText="나의 성격을 알아봅시다. "
document.querySelector("#test_image").src="../img/image-3.png";
const qnaList = [
  {
    q: '1. 사람들과 어울리고 나면 재충전의 시간이 필요하다.',
    a: [
      { answer: 'a. 그렇다기보단 오히려 기운이 넘친다.'},
      { answer: 'b. 상황 따라 다른 것 같다.' },
      { answer: 'c. 맞다. 사람을 만나고 나면 나만의 시간이 필요하다,'},
    ]
  },
  {
    q: '2. 사람과 대화를 할때 듣는 쪽? 말하는 쪽?',
    a: [
      { answer: 'a. 주로 말하는 쪽이다.' },
      { answer: 'b. 상대방이 말이 없다면 내가 말하기를 시도한다.' },
      { answer: 'c. 나는 먼저 들어주는 것이 편하다.'},
    ]
  },
  {
    q: '3. 말과 글 중 더 표현하기 쉬운 수단은?',
    a: [
      { answer: 'a. 글보다는 말로 직접 전달하는 것이 편하다.' },
      { answer: 'b. 필요할 때만 글로 표현한다.'},
      { answer: 'c. 말로 표현하는게 잘 되지 않아 글로 전달하는 것이 더 편하다.'}
    ]
  },
  {
    q: '4. 친구랑 놀 때, 이상적인 멤버의 수는?',
    a: [
      { answer: 'a. 많으면 많을 수록 좋다.'},
      { answer: 'b. 네다섯명 정도?'},
      { answer: 'c. 두세명 정도가 마음이 편하다.'},
    ]
  },
  {
    q: '5. 황금같은 공휴일을 보내는 이상적인 방법은?',
    a: [
      { answer: 'a. 친구와 이미 약속을 잡아놓았고, 이제 나갈 일만 남았다.'},
      { answer: 'b. 나가 놀 때도 있지만 휴식이 필요할 땐 집콕을 한다.'},
      { answer: 'c. 늘 그랬듯이 집에서 나만의 시간을 보낸다.'},
    ]
  }
]

const infoList = [
  {
    name: '사람 때문에 사는, 내향성 0%의 외향인',
    desc: ' 외향맨 '
  },
  {
    name: ' 내향성 50% 반반맨 ',
    desc: ' 반반맨 '
  },
  {
    name: ' 나만의 시간이 좋은 내향인',
    desc: ' 내향맨 '
  }

]
