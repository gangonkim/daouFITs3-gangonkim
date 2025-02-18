package org.example.servletex.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.servletex.mybatis.MybatisSessionFactory;
import org.example.servletex.service.MemberService;
import org.example.servletex.vo.MemberVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {

    SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
    private static final long serialVersionUID = 1L;
    MemberService memberService = new MemberService(factory);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        // Retrieve form data
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        // Get books matching the keyword
        MemberVO vo = memberService.login(id, password);
        System.out.println(id);
        System.out.println(password);

        if (vo != null) {

            HttpSession session = request.getSession(); // 세션 생성
            session.setAttribute("member", vo);
            session.setMaxInactiveInterval(30 * 60); // 30분 유지
            //session.invalidate(); 세션 객체 무효화

            RequestDispatcher dispatcher = request.getRequestDispatcher("welcome.jsp");
            dispatcher.forward(request, response);
            //response.sendRedirect("welcome.jsp"); // 로그인 성공 후 이동
        } else {
            response.sendRedirect("index.jsp?error=1"); // 로그인 실패 시
        }
    }
}