<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>500 — Lỗi hệ thống</title>
    <link rel="stylesheet" href="${ctx}/assets/css/style.css">
</head>
<body class="error-page">
<div class="error-card">
    <h1>500</h1>
    <p>Đã xảy ra lỗi trong quá trình xử lý.</p>

    <%-- Chi tiet loi chi de xem khi phat trien. Tat khoi len production
         (che thanh <c:if test="false"> hoac xoa block nay). --%>
    <c:if test="${not empty pageContext.exception}">
        <details class="error-detail">
            <summary>Chi tiết lỗi (chỉ hiển thị khi dev)</summary>
            <p><strong><c:out value="${pageContext.exception['class'].name}"/></strong></p>
            <pre><c:out value="${pageContext.exception.message}"/></pre>
        </details>
    </c:if>

    <a class="btn btn-primary" href="${ctx}/dashboard">Về trang chủ</a>
</div>
</body>
</html>
