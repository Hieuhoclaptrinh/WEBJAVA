<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Tổng quan" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h1>Tổng quan</h1>

<div class="stat-grid">
    <div class="stat-card">
        <span class="stat-label">Phòng trống</span>
        <span class="stat-value"><c:out value="${stats.availableRooms}" default="—"/></span>
    </div>
    <div class="stat-card">
        <span class="stat-label">Phòng đang thuê</span>
        <span class="stat-value"><c:out value="${stats.occupiedRooms}" default="—"/></span>
    </div>
    <div class="stat-card">
        <span class="stat-label">Khách thuê</span>
        <span class="stat-value"><c:out value="${stats.totalTenants}" default="—"/></span>
    </div>
    <div class="stat-card">
        <span class="stat-label">Hóa đơn chưa thu</span>
        <span class="stat-value"><c:out value="${stats.unpaidInvoices}" default="—"/></span>
    </div>
</div>

<section>
    <h2>Hợp đồng sắp hết hạn (30 ngày)</h2>
    <table class="table">
        <thead>
        <tr>
            <th>Mã HĐ</th>
            <th>Phòng</th>
            <th>Khách thuê</th>
            <th>Ngày hết hạn</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty expiringContracts}">
                <tr><td colspan="4" class="empty">Chưa có dữ liệu (DashboardServlet chưa implement)</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="ct" items="${expiringContracts}">
                    <tr>
                        <td><c:out value="${ct.contractCode}"/></td>
                        <td><c:out value="${ct.roomCode}"/></td>
                        <td><c:out value="${ct.tenantName}"/></td>
                        <td><c:out value="${ct.endDate}"/></td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</section>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
