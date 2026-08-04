<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>403 — Không có quyền truy cập</title>
    <link rel="stylesheet" href="${ctx}/assets/css/style.css">
</head>
<body class="error-page">
<div class="error-card">
    <h1>403</h1>
    <p>Bạn không có quyền truy cập chức năng này.</p>
    <a class="btn btn-primary" href="${ctx}/dashboard">Về trang chủ</a>
</div>
</body>
</html>
