package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV1",urlPatterns = "/front-controller/v1/*")
// * 을 사용하면 하위의 모든 것의 요청이 오면 일단 여기로 호출될 수 있도록
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String,ControllerV1>controllerMap=new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI(); // uri 를 받아옴 http://localhost:8080/front-controller/v1/hello

        ControllerV1 controller = controllerMap.get(requestURI); // uri 로 컨트롤러를 찾아. 인터페이스로 꺼내기 때문에 일관성 있게 꺼낼 수 있다
        if(controller==null)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controller.process(request,response);

    }
}
