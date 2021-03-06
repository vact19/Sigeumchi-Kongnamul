/*
0
1 양호
2 약간위험
3
*/
document.querySelector('#title').innerHTML="스마트폰 중독 테스트";
document.querySelector(".desc").innerText=" 우리는 스마트폰에 얼마나 의존할까요? "
document.querySelector("#test_image").src="../img/image-1.png";

const qnaList = [
  {
    q: ' 1. 화장실에 스마트폰을 가져가시는 편인가요?  ',
    a: [
      { answer: 'a. 굳이 가져가지 않는다',type: [0]},
      { answer: 'b. 필요할 때만 가져간다',type: [1,2]},
      { answer: 'c. 무조건 가져간다.',type: [2,3]},
    ]
  },
  {
    q: ' 2. 평소에 멍 때리는 시간이 존재하나요? ',
    a: [
      { answer: 'a. 아무것도 하지 않는 시간이 종종 있다',type: [0,1] },
      { answer: 'b. 폰을 보느라 그런 시간이 별로 없다',type: [2] },
      { answer: 'c 멍 때려본 적이 언젠지 기억 나지 않는다. ',type: [3]},
    ]
  },
  {
    q: '3. 밥 먹을 때 스마트폰을 보나요?',
    a: [
      { answer: 'a. 밥 먹는데만 집중하는 편이다..',type: [0] },
      { answer: 'b. 가끔 동영상이나 틀어놓는 정도이다.',type: [1,2] },
      { answer: 'c. 밥을 먹다가도 수시로 폰을 만지작거린다',type: [3]},
    ]
  },
  {
    q: '4. 스마트폰으로 눈 감고 "빨간 떡볶이 사주세요" 를 정확히 입력 가능한가요? ',
    a: [
      { answer: 'a. 시도해봤더니, 알아보기가 거의 불가능했다. ',type: [0] },
      { answer: 'b. 시도해봤더니, 완벽하진 않지만 알아볼 순 있는 문자였다.',type: [1,2] },
      { answer: 'c. 시도해봤더니, 정확히 치는 데 거의 문제가 없었다.',type: [3]},
    ]
  },
  {
    q: '5. 아침에 100%로 충전된 배터리! 자기 전에는 몇%쯤 되나요? ',
    a: [
      { answer: 'a. 50% 이상이다. ',type: [0,1] },
      { answer: 'b. 배터리를 거의 다 쓴 상태이다.',type: [2] },
      { answer: 'c. 이미 모든 배터리를 소모하고 새로 충전한 상태이다',type: [3]},
    ]
  },
  {
    q: '6. 스마트폰 없이 30분 명상, 가능한가요? ',
    a: [
      { answer: 'a. 전혀 문제 없다. 오히려 해보고 싶다. ',type: [0] },
      { answer: 'b. 가능할 것 같지만 별로 하고 싶지 않다.',type: [1,2] },
      { answer: 'c. 그런 것은 상상할 수도 없다.',type: [3]},
    ]
  },
  {
    q: '7. 하루의 시작과 끝을 스마트폰으로 시작하고 있지 않나요? ',
    a: [
      { answer: 'a. 스마트폰은 그저 알람시계일 뿐이다. ',type: [0, 1] },
      { answer: 'b. 생각해보니 자주 그랬던 것 같다.',type: [2] },
      { answer: 'c. 그건 나에게 당연하다.',type: [3]},
    ]
  },
]
const infoList = [
  {
    name: ' 스마트폰 중독 해방! ',
    desc: ' 4단계 중 1단계 위험 수준입니다. <br>  fdsfsdfs!<br>fsdfsdf!'
  },
  {
    name: ' 스마트폰 중독 양호 ',
    desc: ' 양호!! '
  },
  {
    name: ' 스마트폰 중독 주의!  ',
    desc: ' 조심해! '
  },
  {
    name: ' 스마트폰 중독 위험! ',
    desc: ' 많이위험 '
  },

]
