package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {
    ModelView process(Map<String,String>paramMap);
    /*
     하위 버전들과 달리 서블릿 기술이 안 들어가 있음
     정보를 request 로 주고 받지 않기 때문에 HttpServletResponse(서블릿 기술)이 필요가 없다
     대신 ModelView 에 정보를 담아서 주고 받는다
     */

}
