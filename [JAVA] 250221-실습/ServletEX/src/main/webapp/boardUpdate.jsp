<%@ page import="org.example.servletex.service.BoardService" %>
<%@ page import="org.example.servletex.vo.BoardVO" %>
<%@ page import="org.example.servletex.mybatis.MybatisSessionFactory" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="org.example.servletex.vo.MemberVO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>게시글 수정</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            text-align: center;
            background-color: #fff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 600px;
        }
        h2 {
            color: #4CAF50;
            font-size: 24px;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
            text-align: left;
        }
        input, textarea {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin: 10px;
            background-color: #4CAF50;
            color: white;
            font-size: 16px;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="container">
    <%
        int no = Integer.parseInt(request.getParameter("no"));
        SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
        BoardService boardService = new BoardService(factory);
        BoardVO board = boardService.detail(no);
    %>
    <h2>게시글 수정</h2>
    <form action="boardUpdateServlet" method="post">
        <input type="hidden" name="no" value="<%= board.getNo() %>">
        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" id="title" name="title" value="<%= board.getTitle() %>" required>
        </div>
        <div class="form-group">
            <label for="content">내용</label>
            <textarea id="content" name="content" rows="5" required><%= board.getText() %></textarea>
        </div>
        <input type="submit" class="btn" value="수정">
        <a href="boardList.jsp" class="btn">취소</a>
    </form>
</div>
</body>
</html>