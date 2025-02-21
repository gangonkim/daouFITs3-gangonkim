package org.example.servletex.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.servletex.mybatis.MybatisSessionFactory;
import org.example.servletex.service.BoardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/boardDeleteServlet")
public class BoardDeleteServlet extends HttpServlet {

    SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();

    private BoardService boardService = new BoardService(factory);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int no = Integer.parseInt(request.getParameter("no"));

        int result = boardService.delete(no);
        response.sendRedirect("boardList.jsp"); // 삭제 후 목록으로 이동
    }
}
