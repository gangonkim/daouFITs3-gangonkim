        <%@ page import="org.example.servletex.vo.MemberVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>게시글 작성</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        .container { width: 50%; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; }
        input, textarea { width: 100%; padding: 10px; margin-top: 10px; }
        .btn { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; cursor: pointer; }
        .btn:hover { background-color: #45a049; }
    </style>
</head>
<body>
<div class="container">
    <h2>게시글 작성</h2>
    <%
        HttpSession sessionObj = request.getSession(false);
        String writer = null;
        if (sessionObj != null) {
            MemberVO vo = (MemberVO) sessionObj.getAttribute("member");
            writer = vo.getName();
        }
    %>
    <form action="boardInsertServlet" method="post">
        <input type="text" name="title" placeholder="제목" required><br>
        <textarea name="content" placeholder="내용 입력" rows="5" required></textarea><br>
        <input type="hidden" name="writer" value="<%=writer%>"> <!-- 로그인 연동 가능 -->
        <input type="submit" class="btn" value="등록">
        <a href="welcome.jsp" class="btn">취소</a>
    </form>
</div>
</body>
</html>
