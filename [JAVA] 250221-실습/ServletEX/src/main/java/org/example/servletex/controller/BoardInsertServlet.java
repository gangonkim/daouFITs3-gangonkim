package org.example.servletex.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.servletex.mybatis.MybatisSessionFactory;
import org.example.servletex.service.BoardService;
import org.example.servletex.vo.BoardVO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/boardInsertServlet")
public class BoardInsertServlet extends HttpServlet {
    SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
    private BoardService boardService = new BoardService(factory);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String writer = request.getParameter("writer");

        System.out.println(title);
        System.out.println(content);
        System.out.println(writer);


        BoardVO board = new BoardVO();
        board.setNo(1);
        board.setTitle(title);
        board.setText(content);
        board.setWriter(writer);

        int result = boardService.insert(board);
        if (result > 0) {
            System.out.println("success");
            response.sendRedirect("boardSearch"); // 성공 시 게시판 목록으로 이동
        } else {
            response.sendRedirect("boardWrite.jsp?error=1"); // 실패 시 다시 작성 페이지로
        }
    }
}

