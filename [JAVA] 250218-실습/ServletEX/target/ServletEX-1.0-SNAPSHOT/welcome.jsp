<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="org.example.servletex.vo.MemberVO" %>
<%@ page import="org.example.servletex.service.BoardService" %>
<%@ page import="org.example.servletex.vo.BoardVO" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.servletex.mybatis.MybatisSessionFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
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
        .welcome-message {
            font-size: 18px;
            margin-bottom: 30px;
            color: #555;
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
        .board-container {
            margin-top: 30px;
            text-align: left;
        }
        .board-table {
            width: 100%;
            border-collapse: collapse;
        }
        .board-table th, .board-table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }
        .board-table th {
            background-color: #f2f2f2;
        }
        .board-table tr:hover {
            background-color: #f9f9f9;
        }
        .footer {
            margin-top: 40px;
            font-size: 12px;
            color: #888;
        }
    </style>
</head>
<body>
<div class="container">
    <%
        HttpSession sessionObj = request.getSession(false);
        if (sessionObj != null) {
            MemberVO vo = (MemberVO) sessionObj.getAttribute("member");
    %>
    <h2><%= vo.getName() %>님, 환영합니다!</h2>
    <a href="BookSearch.html" class="btn">도서검색</a>

    <div class="board-container">
        <h3>📌 게시판 목록</h3>
        <table class="board-table">
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
            </tr>
            <%
                SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
                BoardService boardService = new BoardService(factory);
                List<BoardVO> boardList = boardService.list();
                if (boardList != null && !boardList.isEmpty()) {
                    for (BoardVO board : boardList) {
            %>
            <tr>
                <td><%= board.getNo() %></td>
                <td><a href="boardDetail.jsp?no=<%= board.getNo() %>"><%= board.getTitle() %></a></td>
                <td><%= board.getWriter() %></td>
                <td><%= board.getWritedate() %></td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="4" style="text-align: center;">게시글이 없습니다.</td>
            </tr>
            <%
                }
            %>
        </table>
        <a href="boardWrite.jsp" class="btn">게시글 작성</a>
    </div>

    <%
    } else {
    %>
    <p>세션이 만료되었거나 로그인이 필요합니다.</p>
    <a href="login.jsp" class="btn">로그인 페이지로 이동</a>
    <%
        }
    %>
</div>
</body>
</html>
