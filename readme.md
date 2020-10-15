# 채팅 서버 

## 사용한 스택
    - spring boot
    - jpa
    - websocket
    - h2 database
   
## 목표 
    - WebSocket을 이용한 양방향 통신 이해
    - Redis 활용 해보기
    - spring security jwt를 이용한 토큰 검증
   
## 진행 현황
1. 채팅 서버 구축  (09/22) 
2. Stomp/ jpa를 활용한 구현 (09/29)

## URI
회원가입
    
    METHOD : POST
    URL : /member/join
    header: 
    body:  [1] String : email
           [2] String : password
           
    response: {
                  "result": 1,
                  "message": "회원가입 성공"
              }
로그인
    
    METHOD : POST
    URL : /member/login
    header: 
    body:  [1] String : email
           [2] String : password
           
    response: {
                  "result": 1,
                  "message": "로그인 성공"
              }
              
소모임 생성/(채팅방 생성)

    METHOD : POST
    URL : /add/team
    header: 
    body:  [1] String : token
           [2] String : name
           [3] String : desscription
           [4] String : city
           [5] String : street
           [6] String : category
           [7] String : maxMemberCount 
           
    response: {
                  "chatRoomId": "cf7006f6-29ac-46f2-bd0f-8ab3245dac5d",
                  "team": {
                      "token": "wonseok@naver.com",
                      "name": "첫번째 소모임",
                      "description": null,
                      "city": "서울시",
                      "street": "노원구",
                      "category": "스터디",
                      "maxMemberCount": 5
                  }
              }

모든 소모임방

    METHOD : GET
    URL : /all/teams
    header: 
    query:
           
    response: {
                  "result": 1,
                  "data": [
                      {
                          "name": "첫번째 소모임",
                          "content": "7팀",
                          "maxMemberCount": 5,
                          "location": {
                              "city": "서울시",
                              "street": "노원구"
                          },
                          "category": "스터디"
                      },
                      {
                          "name": "두번째 소모임",
                          "content": "두번째 입니다",
                          "maxMemberCount": 5,
                          "location": {
                              "city": "서울시",
                              "street": "노원구"
                          },
                          "category": "스터디"
                      }     
                  ]
              }


## refer
- https://daddyprogrammer.org/post/4077/spring-websocket-chatting/?amp
- https://bcho.tistory.com/1058
- https://m.blog.naver.com/PostView.nhn?blogId=ioi___s_s&logNo=221448388699&proxyReferer=https:%2F%2Fwww.google.com%2F
- https://supawer0728.github.io/2018/03/30/spring-websocket/
- https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte3.5:ptl:stmp
- https://spring.io/guides/gs/messaging-stomp-websocket/
- Redis을 이용한 캐싱
https://webisfree.com/2017-10-26/redis-%EB%A0%88%EB%94%94%EC%8A%A4%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%9C-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%EC%BA%90%EC%8B%B1%EC%84%9C%EB%B2%84-%EC%9A%B4%EC%98%81%ED%95%98%EA%B8%B0