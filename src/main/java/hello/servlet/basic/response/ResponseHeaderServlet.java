package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="responseHeaderServlet",urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //[status-line]
        response.setStatus(HttpServletResponse.SC_OK); // 200
//        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400

        //[response-headers]
        response.setHeader("Content-Type","text/plain;charset=utf-8");
        response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
        response.setHeader("Pragma","no-cache");
        response.setHeader("my-header","hello");

        //[Header 편의 메서드]
//      content(response);
//      cookie(response);
        redirect(response);

//        [message body]
        PrintWriter writer=response.getWriter(); // inputStream 도 가능
        writer.println("안녕하세요");
    }

    private void content(HttpServletResponse response) { // 더 쉽게 설정
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2
        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 자동 생성)
    }

    private void cookie(HttpServletResponse response) { // 더 쉽게 설정
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
        // 쿠키도 setHeader 로 가능하지만 이 방법을 선호
    }

    private void redirect(HttpServletResponse response) throws IOException {
            //Status Code 302
            //Location: /basic/hello-form.html
            //response.setStatus(HttpServletResponse.SC_FOUND); //302
            //response.setHeader("Location", "/basic/hello-form.html");
            response.sendRedirect("/basic/hello-form.html");
    }
}
