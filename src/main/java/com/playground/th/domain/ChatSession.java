<<<<<<< HEAD
package com.playground.th.domain;
=======

/*package com.playground.th.domain;
>>>>>>> 3f740089c2cae5fb0e3b1c5de607d49d319774bc

import lombok.Getter;
import lombok.Setter;

<<<<<<< HEAD
import javax.persistence.*;

@Entity
@Getter
@Setter
public class ChatSession {
    @Id @GeneratedValue
    Long id;
    String sessionId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "chatroom_id")
    ChatRoom chatRoom;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    Member member;

    //생성메서드
    static ChatSession makeSession(String sessionId,Member member,ChatRoom chatRoom){
        ChatSession chatSession = new ChatSession();
        chatSession.setChatRoom(chatRoom);
        chatSession.setSessionId(sessionId);
        chatSession.setMember(member);
        return chatSession;
    }

}
=======
import javax.persistence.Entity;

@Entity
@Getter @Setter
public class ChatSession {
    
}
*/
>>>>>>> 3f740089c2cae5fb0e3b1c5de607d49d319774bc
