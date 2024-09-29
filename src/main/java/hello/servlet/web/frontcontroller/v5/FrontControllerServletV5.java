package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.FrontControllerServletV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="frontControllerServletV5",urlPatterns="/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String,Object> handlerMappingMap=new HashMap<>(); // 아무 컨트롤러나 들어갈 수 있음
    private final List<MyHandlerAdapter> handlerAdapters=new ArrayList<>();

    public FrontControllerServletV5()
    {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap()
    {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
    }

    private void initHandlerAdapters()
    {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request); //uri 를 통해 알맞은 컨트롤러 핸들러를 찾는다

        if(handler==null)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler); // 핸들러 어댑터 찾아와

        ModelView mv = adapter.handle(request, response, handler);

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(),request,response);

    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if(adapter.supports(handler))
            {
                return adapter; // 핸들러에 맞는 알맞은 어댑터를 찾는다면 어댑터 반환
            }
        }
        throw new IllegalArgumentException("handler adapter 을 찾을 수 없습니다. handler = "+handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI= request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private static MyView viewResolver(String viewName) { // 논리 이름을 물리 이름으로 변환
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
