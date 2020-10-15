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
    URL : BaseUrl/member/join
    header: 
    body:  [1] String : email
           [2] String : password
           
    response: {
                  "result": 1,
                  "message": "회원가입 성공"
              }
로그인

##refer
- https://daddyprogrammer.org/post/4077/spring-websocket-chatting/?amp
- https://bcho.tistory.com/1058
- https://m.blog.naver.com/PostView.nhn?blogId=ioi___s_s&logNo=221448388699&proxyReferer=https:%2F%2Fwww.google.com%2F
- https://supawer0728.github.io/2018/03/30/spring-websocket/
- https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte3.5:ptl:stmp
- https://spring.io/guides/gs/messaging-stomp-websocket/
- Redis을 이요한 캐싱
https://webisfree.com/2017-10-26/redis-%EB%A0%88%EB%94%94%EC%8A%A4%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%9C-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%EC%BA%90%EC%8B%B1%EC%84%9C%EB%B2%84-%EC%9A%B4%EC%98%81%ED%95%98%EA%B8%B0