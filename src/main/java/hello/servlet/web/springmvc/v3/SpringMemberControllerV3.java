package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SpringMemberControllerV3 {
    private MemberRepository memberRepository=MemberRepository.getInstance();

    @GetMapping("/new-form")
    public String newForm()
    {
        return "new-form";
    }
    // 조회할 때는 get 방식


    @PostMapping("/save")
    public String save(
            @RequestParam("username") String username,
            @RequestParam("age") int age,
            Model model)
    {
        Member member=new Member(username,age);
        memberRepository.save(member);

        model.addAttribute("memebr",member);
        return "save-result";
    }
    //데이터 전송이 일어날 때는 post 사용

    @GetMapping
    public String members(Model model)
    {
        List<Member> members=memberRepository.findAll();
        model.addAttribute("members",members);
        return "members";
    }
}
