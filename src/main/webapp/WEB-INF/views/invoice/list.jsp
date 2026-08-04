<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Hóa đơn" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="page-head">
    <h1>Danh sách hóa đơn</h1>
    <a class="btn btn-primary" href="${ctx}/invoices/new">+ Lập hóa đơn</a>
</div>

<form class="filter-bar" method="get" action="${ctx}/invoices">
    <input type="text" name="keyword" placeholder="Mã HĐ, phòng, khách..."
           value="<c:out value='${param.keyword}'/>">
    <input type="number" name="month" min="1" max="12" placeholder="Tháng" value="${param.month}">
    <input type="number" name="year" min="2000" max="2100" placeholder="Năm" value="${param.year}">
    <select name="status">
        <option value="">— Tất cả —</option>
        <option value="UNPAID"    ${param.status == 'UNPAID'    ? 'selected' : ''}>Chưa thu</option>
        <option value="PARTIAL"   ${param.status == 'PARTIAL'   ? 'selected' : ''}>Thu một phần</option>
        <option value="PAID"      ${param.status == 'PAID'      ? 'selected' : ''}>Đã thu</option>
        <option value="OVERDUE"   ${param.status == 'OVERDUE'   ? 'selected' : ''}>Quá hạn</option>
        <option value="CANCELLED" ${param.status == 'CANCELLED' ? 'selected' : ''}>Đã hủy</option>
    </select>
    <button type="submit" class="btn">Tìm</button>
</form>

<table class="table">
    <thead>
    <tr>
        <th>Mã hóa đơn</th>
        <th>Kỳ</th>
        <th>Phòng</th>
        <th>Khách thuê</th>
        <th>Tổng tiền</th>
        <th>Đã thu</th>
        <th>Hạn TT</th>
        <th>Trạng thái</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${empty invoices}">
            <tr><td colspan="9" class="empty">Chưa có dữ liệu (InvoiceServlet chưa implement)</td></tr>
        </c:when>
        <c:otherwise>
            <c:forEach var="inv" items="${invoices}">
                <tr>
                    <td><c:out value="${inv.invoiceCode}"/></td>
                    <td>${inv.periodMonth}/${inv.periodYear}</td>
                    <td><c:out value="${inv.roomCode}"/></td>
                    <td><c:out value="${inv.tenantName}"/></td>
                    <td><fmt:formatNumber value="${inv.totalAmount}" maxFractionDigits="0"/> đ</td>
                    <td><fmt:formatNumber value="${inv.paidAmount}" maxFractionDigits="0"/> đ</td>
                    <td><c:out value="${inv.dueDate}"/></td>
                    <td><span class="badge badge-${inv.status}"><c:out value="${inv.status}"/></span></td>
                    <td class="actions">
                        <a class="btn btn-sm" href="${ctx}/invoices/detail?id=${inv.id}">Xem</a>
                        <a class="btn btn-sm" href="${ctx}/invoices/pdf?id=${inv.id}">PDF</a>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
