<%@ page import="org.example.servletex.service.BoardService" %>
<%@ page import="org.example.servletex.vo.BoardVO" %>
<%@ page import="org.example.servletex.mybatis.MybatisSessionFactory" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="org.example.servletex.vo.MemberVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>게시글 상세보기</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        .container { width: 50%; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; }
        .btn { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; cursor: pointer; }
        .btn:hover { background-color: #45a049; }
    </style>
</head>
<body>
<div class="container">
    <%
        String no = request.getParameter("no");
        SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
        BoardService boardService = new BoardService(factory);
        BoardVO board = boardService.detail(Integer.parseInt(no));

        // 세션에서 로그인한 사용자 정보를 가져오기
        HttpSession sessionObj = request.getSession(false);
        String loggedInUser = null;
        if (sessionObj != null) {
            MemberVO vo = (MemberVO) sessionObj.getAttribute("member");
            if (vo != null) {
                loggedInUser = vo.getName();  // 로그인한 사용자의 이름
            }
        }

        // 게시글 작성자와 로그인한 사용자가 같은지 비교
        boolean isWriter = (loggedInUser != null && loggedInUser.equals(board.getWriter()));
    %>
    <h2><%= board.getTitle() %></h2>
    <p><%= board.getText() %></p>
    <p><small>작성자: <%= board.getWriter() %> | 날짜: <%= board.getWritedate() %></small></p>

    <!-- 목록 버튼 -->
    <a href="welcome.jsp" class="btn">목록으로</a>

    <!-- 수정 및 삭제 버튼은 작성자와 로그인한 사용자가 같을 때만 표시 -->
    <% if (isWriter) { %>
    <a href="boardUpdate.jsp?no=<%= board.getNo() %>" class="btn">수정</a>
    <a href="boardDeleteServlet?no=<%= board.getNo() %>" class="btn">삭제</a>
    <% } else { %>
    <p>작성자만 수정 및 삭제가 가능합니다.</p>
    <% } %>
</div>
</body>
</html>
