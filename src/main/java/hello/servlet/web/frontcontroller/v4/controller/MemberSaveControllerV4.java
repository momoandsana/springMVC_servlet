package hello.servlet.web.frontcontroller.v4.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import org.springframework.beans.factory.annotation.Configurable;

import javax.naming.ldap.Control;
import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {
    private final MemberRepository memberRepository=MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member=new Member(username,age);
        memberRepository.save(member);

        model.put("member",member);
        /*
        여기서 모델은 반환할 필요없이 model 이라는 맵에 넣어두고 나중에 다른 곳에서 꺼내서 쓸 수 있게 설계
        모델의 주소값만 받아서 값을 넣어두면 나중에 FrontControllerServletV4 에서 해당 데이터를 request 로 바꿔서 jsp 로 전송
         */
        return "save-result";
    }
}


