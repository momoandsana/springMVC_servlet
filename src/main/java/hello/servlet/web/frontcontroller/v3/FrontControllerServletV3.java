package hello.servlet.web.frontcontroller.v3;


import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;


import hello.servlet.web.frontcontroller.v2.ControllerV2;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV3",urlPatterns = "/front-controller/v3/*")
// * 을 사용하면 하위의 모든 것의 요청이 오면 일단 여기로 호출될 수 있도록
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3>controllerMap=new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
        /*
         key 가 url, value 가 객체
         ControllerV1 controller = controllerMap.get(requestURI);
         나중에 요청 받는 url 을 통해 알맞은 컨트롤러 객체를 얻기 위해서
         */
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        String requestURI = request.getRequestURI(); // 1. 요청이 온 url 를 받아옴 http://localhost:8080/front-controller/v1/hello

        ControllerV3 controller = controllerMap.get(requestURI); // 2. 해당 url 에 맞는 컨트롤러를 찾아. 인터페이스로 꺼내기 때문에 일관성 있게 꺼낼 수 있다
        
        if(controller==null)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap
        Map<String, String> paramMap = createParamMap(request);

        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName();//논리 이름 new-form

        MyView view= viewResolver(viewName);// 논리이름을 받아서 물리이름으로 변환해주고 MyView 객체를 반환한다
        // 계층을 맞추기 위해 복잡한 함수는 cmd option m 으로 extract method 한다

        view.render(mv.getModel(),request,response);

    }

    private static MyView viewResolver(String viewName) { // 논리이름을 물리이름으로 변환
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String,String> paramMap=new HashMap<>();

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        /*
        request 안의 파라미터들을 모두 순회하면서 해당 파라미터의 이름과 그 값을 paramMap 에 넣는다->여기서 혼자 함수 복잡해서 함수를 뽑음 paramMap 코드랑 request 코드 같이 묶어서 extract method
        */
        return paramMap;
    }
}
