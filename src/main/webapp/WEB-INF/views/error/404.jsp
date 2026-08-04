<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>404 — Không tìm thấy trang</title>
    <link rel="stylesheet" href="${ctx}/assets/css/style.css">
</head>
<body class="error-page">
<div class="error-card">
    <h1>404</h1>
    <p>Không tìm thấy trang bạn yêu cầu.</p>
    <p class="muted"><c:out value="${requestScope['jakarta.servlet.error.request_uri']}"/></p>
    <a class="btn btn-primary" href="${ctx}/dashboard">Về trang chủ</a>
</div>
</body>
</html>
