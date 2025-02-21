package org.example.servletex.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.servletex.mybatis.MybatisSessionFactory;
import org.example.servletex.service.BoardService;
import org.example.servletex.service.MemberService;
import org.example.servletex.vo.BoardVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/boardView")
public class BoardViewServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
    private final BoardService boardService = new BoardService(factory);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 게시글 번호 가져오기
        String noParam = request.getParameter("no");
        if (noParam == null) {
            response.sendRedirect("boardList.jsp");
            return;
        }

        int no = Integer.parseInt(noParam);

        // 조회수 증가
        boardService.increaseReadCount(no);

        // 게시글 정보 가져오기
        BoardVO board = boardService.detail(no);
        request.setAttribute("board", board);

        // 게시글 상세 페이지로 이동
        RequestDispatcher dispatcher = request.getRequestDispatcher("boardDetail.jsp");
        dispatcher.forward(request, response);
    }
}