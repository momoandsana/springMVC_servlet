package hello.servlet.web.frontcontroller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class MyView {
    private String viewPath;

    public MyView(String viewPath)
    {
        this.viewPath=viewPath;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request,response);

//        response.sendRedirect(viewPath);  // 이렇게 하면 리다이렉션이 이루어짐
    }

    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        modelToRequestAttribute(model, request); // v3 에서는 request 를 통해 데이터를 전송하지 않고, model 을 통해 데이터를 전송한다
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request,response);
    }

    private static void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        model.forEach((key, value)-> request.setAttribute(key,value));
        // 모델에 담긴 정보를 다 꺼내서 request 에 넣어준다
        //jsp 는 request.getAttribute() 로 데이터를 조회하기 때문에
        // jsp 로 보내기 위해서는 다시 request 형식으로 보내줘야 한다
    }
}
