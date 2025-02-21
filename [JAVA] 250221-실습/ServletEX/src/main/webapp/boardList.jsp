<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="org.example.servletex.vo.MemberVO" %>
<%@ page import="org.example.servletex.service.BoardService" %>
<%@ page import="org.example.servletex.vo.BoardVO" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.servletex.mybatis.MybatisSessionFactory" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.example.servletex.service.BoardLikeService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #e3f2fd; /* Light blue background */
            color: #1e88e5; /* Blue text */
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            text-align: center;
            background-color: #ffffff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 1000px;
        }
        h2 {
            color: #1e88e5; /* Blue title */
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
            background-color: #1e88e5; /* Blue button */
            color: white;
            font-size: 16px;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #1565c0; /* Darker blue on hover */
        }
        .board-container {
            margin-top: 30px;
            text-align: left;
        }
        .board-card {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin-bottom: 20px;
            transition: box-shadow 0.3s;
        }
        .board-card:hover {
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
        }
        .board-title {
            font-size: 20px;
            font-weight: bold;
            color: #1e88e5; /* Blue title for boards */
            margin-bottom: 10px;
        }
        .board-meta {
            font-size: 14px;
            color: #888;
            margin-bottom: 10px;
        }
        .board-actions {
            display: flex;
            justify-content: space-between;
            width: 100%;
        }
        .board-actions .likes, .board-actions .views {
            font-size: 16px;
        }
        .search-container {
            margin-top: 20px;
        }
        .search-container input {
            padding: 12px;
            font-size: 16px;
            width: 300px; /* Larger search field */
            margin-right: 10px;
            border: 2px solid #1e88e5; /* Blue border */
            border-radius: 5px;
        }
        .search-container button {
            padding: 12px 20px;
            font-size: 16px;
            background-color: #1e88e5; /* Blue search button */
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .search-container button:hover {
            background-color: #1565c0; /* Darker blue on hover */
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

    <div class="board-container">
        <h3>📌 게시판 목록</h3>
        <%
            // Get the list of boards from the request
            List<BoardVO> boardList = (List<BoardVO>) request.getAttribute("boardList");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            if (boardList != null && !boardList.isEmpty()) {
                for (BoardVO board : boardList) {
                    String formattedDate = dateFormat.format(board.getWritedate());
                    SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
                    BoardLikeService boardLikeService = new BoardLikeService(factory);
                    int likecnt = boardLikeService.getLikeCount(board.getNo());
        %>
        <div class="board-card">
            <div class="board-title">
                <a href="boardView?no=<%= board.getNo() %>" style="color: #333; text-decoration: none;"><%= board.getTitle() %></a>
            </div>
            <div class="board-meta">
                작성자: <%= board.getWriter() %> | 작성일: <%= formattedDate %> | 조회수: <%= board.getReadcnt() %>
            </div>
            <div class="board-actions">
                <span class="likes">❤️ <%= likecnt %></span>
            </div>
        </div>
        <%
            }
        } else {
        %>
        <div class="board-card">
            <p style="text-align: center;">게시글이 없습니다.</p>
        </div>
        <%
            }
        %>
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
    <div class="search-container">
        <form action="boardSearch" method="post">
            <input type="text" name="search" placeholder="제목 또는 내용으로 검색" required>
            <button type="submit" class="btn">검색</button>
        </form>
    </div>
</div>
</body>
</html>
