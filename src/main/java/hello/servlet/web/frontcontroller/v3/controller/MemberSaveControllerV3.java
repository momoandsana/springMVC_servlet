package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository= MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        String username = paramMap.get("username"); // new-form.jsp 에서 name 이 username 인 input 이 있다
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelView mv = new ModelView("save-result");
        mv.getModel().put("member",member);
        /*
         멤버 객체에 username, age 를 넣고 그 맴버 객체를 member 라는 키를 가지고 맴버 객체를 value 로 넣는다
         request 에서 paramMap 으로 내용물을 넣어서 보낸 것을 받았음
         ModelView 의 형태로 반환한다
         해시맵인 model 을 받아서 거기다 <String,Object> 를 넣는다 여기서는 "member" 라는 키를 가진 member 객체를 넣는다
         */
        return mv;
    }
}
