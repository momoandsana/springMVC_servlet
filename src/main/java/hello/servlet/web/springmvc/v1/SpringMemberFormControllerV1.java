package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/*
이 어노테이션이 있으면 자동으로 스프링 빈으로 등록(안에 component 어노테이션이 있음)
RequestMappingHandlerMapping 으로 인식-> controller 이나 RequestMapping 어노테이션이 있으면 매핑 정보로 인식한다
controller 어노테이션 안에는 RequestMapping 과 component 가 포함되어 있다-> 스프링빈으로 인식+ 매핑 가능
*/
@Controller
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process()
    {
        return new ModelAndView("new-form");
    }
}
