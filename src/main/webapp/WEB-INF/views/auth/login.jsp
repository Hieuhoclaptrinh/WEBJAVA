<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đăng nhập — StudentRent</title>
    <link rel="stylesheet" href="${ctx}/assets/css/style.css">
</head>
<body class="login-page">

<form class="login-card" method="post" action="${ctx}/login">
    <h1>🏠 StudentRent</h1>
    <p class="muted">Hệ thống quản lý nhà trọ sinh viên</p>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error"><c:out value="${errorMessage}"/></div>
    </c:if>

    <label for="username">Tên đăng nhập</label>
    <input type="text" id="username" name="username" required autofocus
           value="<c:out value='${username}'/>">

    <label for="password">Mật khẩu</label>
    <input type="password" id="password" name="password" required>

    <button type="submit" class="btn btn-primary btn-block">Đăng nhập</button>
</form>

</body>
</html>
