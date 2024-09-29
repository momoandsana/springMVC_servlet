package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.FrontControllerServletV5;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV4 controller = (ControllerV4) handler;

        Map<String,String> paramMap=createParamMap(request);
        HashMap<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);
        // 여기까지는 원래 v4 컨트롤러가 해주던 역할

        ModelView mv = new ModelView(viewName);
        mv.setModel(model);
        /*
        여기는 v3 와 통일하기 위해 ModelView 형식을 만들어서 FrontControllerServletV5 에 반환한다
        원래 v4 는 뷰의 이름만 반환하고 model 의 주소값만 받아 내용을 저장하고, 나중에 다른 곳에서 꺼내 쓰는 스타일이다

         */

        return mv;
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
