package hello.servlet.web.servletmvc;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="mvcMemberServlet",urlPatterns = "/servlet-mvc/members/save")
public class MvcMemberSaveServlet extends HttpServlet {

    MemberRepository memberRepository=MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("MemberSaveServlet.service");
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        //Model 에 데이터를 보관한다
        request.setAttribute("member",member);
        /*
        현재는 다른 jsp 한테 요청을 하는 상황(서버 내부니까)
        그래서 response 가 아닌 request 에 담아서 데이터 전송
        최종적으로 클라이언트에 전송할 때는 response 객체에 담아서 전송(리다이렉트)
        리다이렉트 하는 경우에는 response 에 담아서 보낸다
        포워딩 하는 경우에는 request 에 담아서 보낸다
         */

        String viewPath="/WEB-INF/views/save-result.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request,response);// dispatcher 는 포워딩할 때 사용

    }
}
