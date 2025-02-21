package org.example.servletex.controller;

import org.example.servletex.service.BoardLikeService;
import org.example.servletex.service.BoardService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.servletex.mybatis.MybatisSessionFactory;
import org.example.servletex.vo.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/toggleLike")
public class LikeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
    private final BoardLikeService likeService = new BoardLikeService(factory);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("member") == null) {
            response.getWriter().write("로그인이 필요합니다.");
            return;
        }

        MemberVO member = (MemberVO) session.getAttribute("member");
        String memberId = member.getId();
        int boardNo = Integer.parseInt(request.getParameter("boardno"));
        System.out.println("boardNo: " + boardNo);

        boolean isLiked = likeService.isLiked(memberId, boardNo);
        boolean success;
        if (isLiked) {
            success = likeService.removeLike(memberId, boardNo);
        } else {
            success = likeService.addLike(memberId, boardNo);
        }

        int likeCount = likeService.getLikeCount(boardNo);

        // JSON 응답 객체 생성
        PrintWriter out = response.getWriter();
        if (success) {
            // 'liked'와 'likeCount' 값을 JSON으로 응답
            out.write("{\"liked\": " + (!isLiked) + ", \"likeCount\": " + likeCount + "}");
        } else {
            out.write("{\"error\":\"좋아요 처리 실패\"}");
        }
//        PrintWriter out = response.getWriter();
//        if (success) {
//            out.write(String.valueOf(isLiked));
//        } else {
//            out.write("error");
//        }
    }

    // 페이지 요청 시 초기 좋아요 상태 반환
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("member") == null) {
            response.getWriter().write("{\"error\":\"로그인이 필요합니다.\"}");
            return;
        }

        MemberVO member = (MemberVO) session.getAttribute("member");
        String memberId = member.getId();
        int boardNo = Integer.parseInt(request.getParameter("boardno"));

        boolean isLiked = likeService.isLiked(memberId, boardNo);
        int likeCount = likeService.getLikeCount(boardNo);

        PrintWriter out = response.getWriter();
        out.write("{\"liked\": " + isLiked + ", \"likeCount\": " + likeCount + "}");
    }
}

