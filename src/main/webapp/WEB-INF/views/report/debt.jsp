<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Báo cáo công nợ" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="page-head">
    <h1>Báo cáo công nợ</h1>
    <a class="btn" href="${ctx}/reports">Báo cáo doanh thu</a>
</div>

<table class="table">
    <thead>
    <tr>
        <th>Mã hóa đơn</th><th>Phòng</th><th>Khách thuê</th>
        <th>Hạn TT</th><th>Số ngày trễ</th><th>Còn nợ</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${empty debts}">
            <tr><td colspan="6" class="empty">Chưa có dữ liệu (ReportServlet chưa implement)</td></tr>
        </c:when>
        <c:otherwise>
            <c:forEach var="d" items="${debts}">
                <tr>
                    <td><c:out value="${d.invoiceCode}"/></td>
                    <td><c:out value="${d.roomCode}"/></td>
                    <td><c:out value="${d.tenantName}"/></td>
                    <td><c:out value="${d.dueDate}"/></td>
                    <td><c:out value="${d.daysLate}"/></td>
                    <td><fmt:formatNumber value="${d.remaining}" maxFractionDigits="0"/> đ</td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
