package hello.servlet.web.frontcontroller.v2;


import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v1.ControllerV1;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV2",urlPatterns = "/front-controller/v2/*")
// * 을 사용하면 하위의 모든 것의 요청이 오면 일단 여기로 호출될 수 있도록
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2>controllerMap=new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
        /*
         key 가 url, value 가 객체
         ControllerV1 controller = controllerMap.get(requestURI);
         나중에 요청 받는 url 을 통해 알맞은 컨트롤러 객체를 얻기 위해서
         */
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV2.service");

        String requestURI = request.getRequestURI(); // url 를 받아옴 http://localhost:8080/front-controller/v1/hello

        ControllerV2 controller = controllerMap.get(requestURI); // 해당 url 에 맞는 컨트롤러를 찾아. 인터페이스로 꺼내기 때문에 일관성 있게 꺼낼 수 있다
        if(controller==null)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyView view = controller.process(request, response); // MyView 객체가 주소룰 가지고 생성된다
        view.render(request,response);
        /*
        모든 컨트롤러들이 무조건 뷰를 반환해야 하기 때문에 엉뚱한 거는 에러처리
        이렇게 뷰로 받고 랜더링함으로써 공통 로직에 대한 코드를 많이 줄였다
         */

    }
}
