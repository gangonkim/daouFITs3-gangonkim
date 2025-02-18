<%@ page import="org.example.servletex.service.BoardService" %>
<%@ page import="org.example.servletex.vo.BoardVO" %>
<%@ page import="org.example.servletex.mybatis.MybatisSessionFactory" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    int no = Integer.parseInt(request.getParameter("no"));
    SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
    BoardService boardService = new BoardService(factory);
    BoardVO board = boardService.detail(no);
%>
<html>
<head>
    <title>게시글 수정</title>
</head>
<body>
<h2>게시글 수정</h2>
<form action="boardUpdateServlet" method="post">
    <input type="hidden" name="no" value="<%= board.getNo() %>">
    <input type="text" name="title" value="<%= board.getTitle() %>" required><br>
    <textarea name="content" rows="5" required><%= board.getText() %></textarea><br>
    <input type="submit" value="수정">
</form>
</body>
</html>

