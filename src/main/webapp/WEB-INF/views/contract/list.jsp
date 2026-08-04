<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Hợp đồng" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="page-head">
    <h1>Danh sách hợp đồng</h1>
    <a class="btn btn-primary" href="${ctx}/contracts/new">+ Tạo hợp đồng</a>
</div>

<form class="filter-bar" method="get" action="${ctx}/contracts">
    <input type="text" name="keyword" placeholder="Mã HĐ, mã phòng, tên khách..."
           value="<c:out value='${param.keyword}'/>">
    <select name="status">
        <option value="">— Tất cả —</option>
        <option value="PENDING"    ${param.status == 'PENDING'    ? 'selected' : ''}>Chờ hiệu lực</option>
        <option value="ACTIVE"     ${param.status == 'ACTIVE'     ? 'selected' : ''}>Đang hiệu lực</option>
        <option value="EXPIRED"    ${param.status == 'EXPIRED'    ? 'selected' : ''}>Hết hạn</option>
        <option value="TERMINATED" ${param.status == 'TERMINATED' ? 'selected' : ''}>Đã chấm dứt</option>
    </select>
    <button type="submit" class="btn">Tìm</button>
</form>

<table class="table">
    <thead>
    <tr>
        <th>Mã HĐ</th>
        <th>Phòng</th>
        <th>Khách thuê</th>
        <th>Từ ngày</th>
        <th>Đến ngày</th>
        <th>Giá / tháng</th>
        <th>Trạng thái</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${empty contracts}">
            <tr><td colspan="8" class="empty">Chưa có dữ liệu (ContractServlet chưa implement)</td></tr>
        </c:when>
        <c:otherwise>
            <c:forEach var="ct" items="${contracts}">
                <tr>
                    <td><c:out value="${ct.contractCode}"/></td>
                    <td><c:out value="${ct.roomCode}"/></td>
                    <td><c:out value="${ct.tenantName}"/></td>
                    <td><c:out value="${ct.startDate}"/></td>
                    <td><c:out value="${ct.endDate}"/></td>
                    <td><fmt:formatNumber value="${ct.monthlyPrice}" type="number" maxFractionDigits="0"/> đ</td>
                    <td><span class="badge badge-${ct.status}"><c:out value="${ct.status}"/></span></td>
                    <td class="actions">
                        <a class="btn btn-sm" href="${ctx}/contracts/detail?id=${ct.id}">Xem</a>
                        <a class="btn btn-sm" href="${ctx}/contracts/pdf?id=${ct.id}">PDF</a>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
