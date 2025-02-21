package org.example.servletex.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.servletex.mybatis.MybatisSessionFactory;
import org.example.servletex.service.BoardService;
import org.example.servletex.service.CommentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/commentDeleteServlet")
public class CommentDeleteServlet extends HttpServlet {

    SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();

    private CommentService commentService = new CommentService(factory);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 클라이언트에서 전달받은 댓글 ID
        int commentId = Integer.parseInt(request.getParameter("commentId"));

        // 댓글 삭제 서비스 호출
        int result = commentService.deleteComment(commentId);

        // JSON 형식으로 응답 반환
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (result > 0) {
            out.write("{\"success\": true}");
        } else {
            out.write("{\"success\": false, \"message\": \"댓글 삭제 실패\"}");
        }
        out.flush();
    }
}
