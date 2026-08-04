<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${pageTitle}" default="Quản lý nhà trọ"/> — StudentRent</title>
    <link rel="stylesheet" href="${ctx}/assets/css/style.css">
</head>
<body>

<header class="topbar">
    <a class="brand" href="${ctx}/dashboard">🏠 StudentRent</a>
    <div class="topbar-right">
        <c:choose>
            <c:when test="${not empty sessionScope.currentUser}">
                <span class="user-chip">
                    <c:out value="${sessionScope.currentUser.fullName}"/>
                    <small>(<c:out value="${sessionScope.currentUser.role}"/>)</small>
                </span>
                <a class="btn btn-sm" href="${ctx}/logout">Đăng xuất</a>
            </c:when>
            <c:otherwise>
                <a class="btn btn-sm" href="${ctx}/login">Đăng nhập</a>
            </c:otherwise>
        </c:choose>
    </div>
</header>

<div class="layout">
    <nav class="sidebar">
        <ul>
            <li><a href="${ctx}/dashboard">Tổng quan</a></li>
            <li><a href="${ctx}/rooms">Phòng trọ</a></li>
            <li><a href="${ctx}/tenants">Khách thuê</a></li>
            <li><a href="${ctx}/contracts">Hợp đồng</a></li>
            <li><a href="${ctx}/invoices">Hóa đơn</a></li>
            <li><a href="${ctx}/reports">Báo cáo</a></li>
        </ul>
    </nav>

    <main class="content">
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error"><c:out value="${errorMessage}"/></div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success"><c:out value="${successMessage}"/></div>
        </c:if>
