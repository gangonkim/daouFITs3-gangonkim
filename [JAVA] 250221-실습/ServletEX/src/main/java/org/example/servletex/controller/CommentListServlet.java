package org.example.servletex.controller;

import com.google.gson.GsonBuilder;
import org.example.servletex.service.CommentService;
import org.example.servletex.vo.CommentVO;
import org.example.servletex.mybatis.MybatisSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import com.google.gson.Gson;

@WebServlet("/commentListServlet")
public class CommentListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 요청 파라미터에서 게시글 번호 가져오기
        int boardno = Integer.parseInt(req.getParameter("boardno"));

        // CommentService 초기화
        CommentService commentService = new CommentService(MybatisSessionFactory.getSqlSessionFactory());

        // 댓글 목록 조회
        List<CommentVO> comments = commentService.selectCommentsByPostId(boardno);

        // Timestamp를 "yyyy-MM-dd HH:mm:ss" 포맷으로 변환하여 JSON 직렬화 설정
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Timestamp.class, (com.google.gson.JsonSerializer<Timestamp>)
                        (timestamp, type, jsonSerializationContext) ->
                                new com.google.gson.JsonPrimitive(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp)))
                .setPrettyPrinting()
                .create();

        String jsonResponse = gson.toJson(comments);
        // 응답 설정
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // 응답 전송
        PrintWriter out = resp.getWriter();
        out.print(jsonResponse);
        out.flush();
    }
}
