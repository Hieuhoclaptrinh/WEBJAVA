<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Phòng trọ" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="page-head">
    <h1>Danh sách phòng</h1>
    <a class="btn btn-primary" href="${ctx}/rooms/new">+ Thêm phòng</a>
</div>

<form class="filter-bar" method="get" action="${ctx}/rooms">
    <input type="text" name="keyword" placeholder="Mã phòng..." value="<c:out value='${param.keyword}'/>">
    <select name="status">
        <option value="">— Tất cả trạng thái —</option>
        <option value="AVAILABLE"   ${param.status == 'AVAILABLE'   ? 'selected' : ''}>Còn trống</option>
        <option value="OCCUPIED"    ${param.status == 'OCCUPIED'    ? 'selected' : ''}>Đang thuê</option>
        <option value="RESERVED"    ${param.status == 'RESERVED'    ? 'selected' : ''}>Đã giữ chỗ</option>
        <option value="MAINTENANCE" ${param.status == 'MAINTENANCE' ? 'selected' : ''}>Đang sửa</option>
    </select>
    <button type="submit" class="btn">Tìm</button>
</form>

<table class="table">
    <thead>
    <tr>
        <th>Mã phòng</th>
        <th>Loại</th>
        <th>Tầng</th>
        <th>Diện tích</th>
        <th>Giá / tháng</th>
        <th>Trạng thái</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${empty rooms}">
            <tr><td colspan="7" class="empty">Chưa có dữ liệu (RoomServlet chưa implement)</td></tr>
        </c:when>
        <c:otherwise>
            <c:forEach var="r" items="${rooms}">
                <tr>
                    <td><c:out value="${r.roomCode}"/></td>
                    <td><c:out value="${r.roomTypeName}"/></td>
                    <td><c:out value="${r.floorNo}"/></td>
                    <td><c:out value="${r.area}"/> m²</td>
                    <td><fmt:formatNumber value="${r.price}" type="number" maxFractionDigits="0"/> đ</td>
                    <td><span class="badge badge-${r.status}"><c:out value="${r.status}"/></span></td>
                    <td class="actions">
                        <a class="btn btn-sm" href="${ctx}/rooms/edit?id=${r.id}">Sửa</a>
                        <form method="post" action="${ctx}/rooms/delete" class="inline"
                              onsubmit="return confirm('Xóa phòng ${r.roomCode}?')">
                            <input type="hidden" name="id" value="${r.id}">
                            <button type="submit" class="btn btn-sm btn-danger">Xóa</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
