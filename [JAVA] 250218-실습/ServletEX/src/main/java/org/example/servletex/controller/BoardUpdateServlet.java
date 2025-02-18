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

@WebServlet("/boardUpdateServlet")
public class BoardUpdateServlet extends HttpServlet {
    SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
    private BoardService boardService = new BoardService(factory);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int no = Integer.parseInt(request.getParameter("no"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        BoardVO board = new BoardVO();
        board.setNo(no);
        board.setTitle(title);
        board.setText(content);

        int result = boardService.update(board);
        response.sendRedirect("boardDetail.jsp?no=" + no);
    }
}

