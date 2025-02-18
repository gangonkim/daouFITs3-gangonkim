<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>로그인</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .login-container {
            background-color: #fff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            text-align: center;
        }
        h2 {
            color: #333;
            font-size: 28px;
            margin-bottom: 30px;
        }
        .input-field {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }
        .submit-btn {
            width: 100%;
            padding: 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 18px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .submit-btn:hover {
            background-color: #45a049;
        }
        .error-message {
            color: red;
            font-size: 14px;
            margin-top: 20px;
        }
        .footer {
            font-size: 12px;
            color: #888;
            margin-top: 30px;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2>로그인</h2>
    <form action="login" method="post">
        아이디: <input type="text" name="id" class="input-field" required><br>
        비밀번호: <input type="password" name="password" class="input-field" required><br>
        <input type="submit" value="로그인" class="submit-btn">
    </form>

    <%-- 로그인 실패 시 메시지 표시 --%>
    <%
        String error = request.getParameter("error");
        if ("1".equals(error)) {
    %>
    <p class="error-message">로그인 실패! 아이디 또는 비밀번호를 확인하세요.</p>
    <%
        }
    %>

    <div class="footer">
        <p>&copy; 2025. All rights reserved.</p>
    </div>
</div>
</body>
</html>
