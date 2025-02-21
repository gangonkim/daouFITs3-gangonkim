<%@ page import="org.example.servletex.vo.MemberVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>게시글 작성</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 600px;
            padding: 30px;
            text-align: center;
        }

        h2 {
            color: #4CAF50;
            font-size: 28px;
            margin-bottom: 20px;
        }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 15px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            color: #333;
            background-color: #f9f9f9;
            box-sizing: border-box;
        }

        input[type="text"]:focus,
        textarea:focus {
            border-color: #4CAF50;
            outline: none;
        }

        .btn {
            background-color: #4CAF50;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .btn:hover {
            background-color: #45a049;
        }

        .btn-cancel {
            background-color: #f44336;
            margin-top: 10px;
        }

        .btn-cancel:hover {
            background-color: #e53935;
        }

        .form-footer {
            margin-top: 20px;
            font-size: 14px;
            color: #777;
        }

        .form-footer a {
            color: #4CAF50;
            text-decoration: none;
        }

        .form-footer a:hover {
            text-decoration: underline;
        }
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
        <a href="boardList.jsp" class="btn btn-cancel">취소</a>
    </form>
    <div class="form-footer">
        <p><a href="boardList.jsp">게시글 목록으로 돌아가기</a></p>
    </div>
</div>
</body>
</html>
