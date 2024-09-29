package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
    }
    /*
    handler 는 컨트롤러를 의미한다
   어댑터가 해당 컨트롤러를 처리할 수 있는지 판단하는 메서드
     */

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3) handler;

        Map<String,String> paramMap=createParamMap(request);
        ModelView mv = controller.process(paramMap);

        return mv;
    }
    /*
    어댑터는 실제 컨트롤러를 호출하고, 그 결과를 ModelView 로 반환한다
    실제 컨트롤러가 ModelView 를 반환하지 못하면, 어댑터가 ModelView 를 직접 생성해서라도 반환해야 한다
    이전에는 프론트 컨트롤러가 실제 컨트롤러를 호출했지만 이제는 이 어댑터를 통해서 실제 컨트롤러가 호출된다
    버전 3 에서는 원래 ModelView 를 반환하기 때문에 여기서도 ModelView 를 반환한다
     */

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
