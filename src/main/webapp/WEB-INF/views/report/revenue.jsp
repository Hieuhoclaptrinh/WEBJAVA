<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Báo cáo doanh thu" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="page-head">
    <h1>Báo cáo doanh thu <c:out value="${year}"/></h1>
    <a class="btn" href="${ctx}/reports/debt">Báo cáo công nợ</a>
</div>

<form class="filter-bar" method="get" action="${ctx}/reports">
    <label for="year">Năm</label>
    <input type="number" id="year" name="year" min="2000" max="2100" value="${year}">
    <button type="submit" class="btn">Xem</button>
</form>

<p>
    Tỷ lệ lấp phòng:
    <strong>
        <c:choose>
            <c:when test="${empty occupancyRate}">—</c:when>
            <c:otherwise><fmt:formatNumber value="${occupancyRate}" type="percent" maxFractionDigits="1"/></c:otherwise>
        </c:choose>
    </strong>
</p>

<table class="table">
    <thead>
    <tr><th>Tháng</th><th>Doanh thu đã thu</th></tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${empty revenueByMonth}">
            <tr><td colspan="2" class="empty">Chưa có dữ liệu (ReportServlet chưa implement)</td></tr>
        </c:when>
        <c:otherwise>
            <c:forEach var="e" items="${revenueByMonth}">
                <tr>
                    <td>Tháng ${e.key}</td>
                    <td><fmt:formatNumber value="${e.value}" maxFractionDigits="0"/> đ</td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
