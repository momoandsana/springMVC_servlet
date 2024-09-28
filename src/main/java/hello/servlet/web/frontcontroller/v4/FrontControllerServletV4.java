package hello.servlet.web.frontcontroller.v4;


import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;

import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV4",urlPatterns = "/front-controller/v4/*")
// * 을 사용하면 하위의 모든 것의 요청이 오면 일단 여기로 호출될 수 있도록
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4>controllerMap=new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
        /*
         key 가 url, value 가 객체
         ControllerV1 controller = controllerMap.get(requestURI);
         나중에 요청 받는 url 을 통해 알맞은 컨트롤러 객체를 얻기 위해서
         */
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV4.service");

        String requestURI = request.getRequestURI(); // 1. 요청이 온 url 를 받아옴 http://localhost:8080/front-controller/v1/hello

        ControllerV4 controller = controllerMap.get(requestURI); // 2. 해당 url 에 맞는 컨트롤러를 찾아. 인터페이스로 꺼내기 때문에 일관성 있게 꺼낼 수 있다
        
        if(controller==null)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap
        Map<String, String> paramMap = createParamMap(request);
        Map<String,Object> model=new HashMap<>(); // 추가

        String viewName = controller.process(paramMap, model);// 논리 이름과 처리하고 생긴 결과물이 담긴 ModelView 를 받는다

        MyView view= viewResolver(viewName);// 논리 이름을 받아서 물리 이름으로 변환해주고 MyView 객체를 반환한다
        // 계층을 맞추기 위해 복잡한 함수는 cmd option m 으로 extract method 한다

        view.render(model,request,response); // 해당 물리 이름으로 컨트롤러가 처리한 결과물을 가지고 간다

    }

    private static MyView viewResolver(String viewName) { // 논리 이름을 물리 이름으로 변환
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
