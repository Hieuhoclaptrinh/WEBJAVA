<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Khách thuê" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="page-head">
    <h1>Danh sách khách thuê</h1>
    <a class="btn btn-primary" href="${ctx}/tenants/new">+ Thêm khách thuê</a>
</div>

<form class="filter-bar" method="get" action="${ctx}/tenants">
    <input type="text" name="keyword" placeholder="Tên, CCCD, SĐT, mã SV..."
           value="<c:out value='${param.keyword}'/>">
    <button type="submit" class="btn">Tìm</button>
</form>

<table class="table">
    <thead>
    <tr>
        <th>Họ tên</th>
        <th>CCCD</th>
        <th>Điện thoại</th>
        <th>Trường</th>
        <th>Mã SV</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${empty tenants}">
            <tr><td colspan="6" class="empty">Chưa có dữ liệu (TenantServlet chưa implement)</td></tr>
        </c:when>
        <c:otherwise>
            <c:forEach var="t" items="${tenants}">
                <tr>
                    <td><c:out value="${t.fullName}"/></td>
                    <td><c:out value="${t.idCard}"/></td>
                    <td><c:out value="${t.phone}"/></td>
                    <td><c:out value="${t.school}"/></td>
                    <td><c:out value="${t.studentCode}"/></td>
                    <td class="actions">
                        <a class="btn btn-sm" href="${ctx}/tenants/edit?id=${t.id}">Sửa</a>
                        <form method="post" action="${ctx}/tenants/delete" class="inline"
                              onsubmit="return confirm('Xóa khách thuê này?')">
                            <input type="hidden" name="id" value="${t.id}">
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
