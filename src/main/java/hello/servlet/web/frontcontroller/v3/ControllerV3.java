package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {
    ModelView process(Map<String,String>paramMap);
    // 하위 버전들과 달리 서블릿 기술이 안 들어가 있음

}
