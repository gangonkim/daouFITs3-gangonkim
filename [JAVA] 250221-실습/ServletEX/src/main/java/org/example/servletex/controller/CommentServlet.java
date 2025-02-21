package org.example.servletex.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.servletex.mybatis.MybatisSessionFactory;
import org.example.servletex.service.BoardService;
import org.example.servletex.service.CommentService;
import org.example.servletex.vo.CommentVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/commentServlet")
public class CommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        CommentVO commentVO = new CommentVO();

        try {
            String boardno = request.getParameter("postId");
            String writer = request.getParameter("writer");
            String content = request.getParameter("commentText");
            System.out.println("CommentServlet");
            System.out.println(boardno);
            System.out.println(writer);
            System.out.println(content);

            SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
            CommentService commentService = new CommentService(factory);

            commentVO.setBoardno(boardno);
            commentVO.setWriter(writer);
            commentVO.setContent(content);
            // 댓글 저장
            commentService.addComment(commentVO);

            // JSON 응답 추가
            out.print("{\"status\": \"success\", \"message\": \"댓글이 등록되었습니다.\"}");
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            out.print("{\"status\": \"error\", \"message\": \"댓글 작성 중 오류가 발생했습니다.\"}");
            out.flush();
        }
    }
}


