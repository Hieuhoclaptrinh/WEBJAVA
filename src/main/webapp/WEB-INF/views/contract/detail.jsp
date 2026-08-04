<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Chi tiết hợp đồng" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="page-head">
    <h1>Hợp đồng <c:out value="${contract.contractCode}" default="—"/></h1>
    <div>
        <a class="btn" href="${ctx}/contracts/pdf?id=${contract.id}">Xuất PDF</a>
        <a class="btn" href="${ctx}/contracts">Về danh sách</a>
    </div>
</div>

<c:choose>
    <c:when test="${empty contract}">
        <p class="empty">Chưa có dữ liệu (ContractServlet chưa implement).</p>
    </c:when>
    <c:otherwise>
        <dl class="detail-list">
            <dt>Phòng</dt>           <dd><c:out value="${contract.roomCode}"/></dd>
            <dt>Khách thuê</dt>      <dd><c:out value="${contract.tenantName}"/></dd>
            <dt>Thời hạn</dt>        <dd><c:out value="${contract.startDate}"/> → <c:out value="${contract.endDate}"/></dd>
            <dt>Giá / tháng</dt>     <dd><fmt:formatNumber value="${contract.monthlyPrice}" maxFractionDigits="0"/> đ</dd>
            <dt>Tiền cọc</dt>        <dd><fmt:formatNumber value="${contract.deposit}" maxFractionDigits="0"/> đ</dd>
            <dt>Số người</dt>        <dd><c:out value="${contract.numOccupants}"/></dd>
            <dt>Ngày chốt HĐ</dt>    <dd><c:out value="${contract.billingDay}"/></dd>
            <dt>Trạng thái</dt>      <dd><span class="badge badge-${contract.status}"><c:out value="${contract.status}"/></span></dd>
            <dt>Ghi chú</dt>         <dd><c:out value="${contract.note}"/></dd>
        </dl>

        <c:if test="${contract.status == 'ACTIVE'}">
            <form method="post" action="${ctx}/contracts/terminate"
                  onsubmit="return confirm('Chấm dứt hợp đồng này?')">
                <input type="hidden" name="id" value="${contract.id}">
                <label for="terminatedAt">Ngày chấm dứt</label>
                <input type="date" id="terminatedAt" name="terminatedAt" required>
                <label for="reason">Lý do</label>
                <input type="text" id="reason" name="reason">
                <button type="submit" class="btn btn-danger">Chấm dứt hợp đồng</button>
            </form>
        </c:if>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
