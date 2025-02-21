package org.example.servletex.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.servletex.mybatis.MybatisSessionFactory;
import org.example.servletex.service.BoardService;
import org.example.servletex.service.MemberService;
import org.example.servletex.vo.BoardVO;
import org.example.servletex.vo.MemberVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/boardSearch")
public class BoardSearchServlet extends HttpServlet {

    SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
    private static final long serialVersionUID = 1L;
    MemberService memberService = new MemberService(factory);

    // ✅ GET 요청을 처리하는 doGet 추가
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        // 게시판 목록 불러오기
        BoardService boardService = new BoardService(factory);
        List<BoardVO> boardList = boardService.list();

        // 리스트를 request 속성으로 추가하여 JSP에서 활용할 수 있도록 함
        request.setAttribute("boardList", boardList);

        // boardList.jsp로 이동 (게시판 페이지)
        RequestDispatcher dispatcher = request.getRequestDispatcher("boardList.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        // Retrieve form data
        SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
        BoardService boardService = new BoardService(factory);
        List<BoardVO> boardList = boardService.searchList(request.getParameter("search"));

        // Add the search results to the request
        request.setAttribute("boardList", boardList);

        // Forward the request to the boardList.jsp to display the results
        RequestDispatcher dispatcher = request.getRequestDispatcher("boardList.jsp");
        dispatcher.forward(request, response);

    }
}