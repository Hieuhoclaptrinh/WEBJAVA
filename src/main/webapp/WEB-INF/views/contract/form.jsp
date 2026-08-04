<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Hợp đồng" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h1><c:out value="${empty contract.id ? 'Tạo hợp đồng' : 'Sửa hợp đồng'}"/></h1>

<form class="form" method="post" action="${ctx}/contracts/save">
    <input type="hidden" name="id" value="${contract.id}">

    <div class="form-row">
        <label for="roomId">Phòng *</label>
        <select id="roomId" name="roomId" required>
            <option value="">— Chọn phòng còn trống —</option>
            <c:forEach var="r" items="${availableRooms}">
                <option value="${r.id}" data-price="${r.price}"
                        ${contract.roomId == r.id ? 'selected' : ''}>
                    <c:out value="${r.roomCode}"/> — <c:out value="${r.roomTypeName}"/>
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-row">
        <label for="tenantId">Khách thuê đại diện *</label>
        <select id="tenantId" name="tenantId" required>
            <option value="">— Chọn khách thuê —</option>
            <c:forEach var="t" items="${tenants}">
                <option value="${t.id}" ${contract.tenantId == t.id ? 'selected' : ''}>
                    <c:out value="${t.fullName}"/> — <c:out value="${t.idCard}"/>
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-row">
        <label for="startDate">Ngày bắt đầu *</label>
        <input type="date" id="startDate" name="startDate" required value="${contract.startDate}">
    </div>

    <div class="form-row">
        <label for="endDate">Ngày kết thúc *</label>
        <input type="date" id="endDate" name="endDate" required value="${contract.endDate}">
    </div>

    <div class="form-row">
        <label for="monthlyPrice">Giá thuê / tháng *</label>
        <input type="number" id="monthlyPrice" name="monthlyPrice" step="1000" min="0" required
               value="${contract.monthlyPrice}">
    </div>

    <div class="form-row">
        <label for="deposit">Tiền cọc</label>
        <input type="number" id="deposit" name="deposit" step="1000" min="0" value="${contract.deposit}">
    </div>

    <div class="form-row">
        <label for="numOccupants">Số người ở</label>
        <input type="number" id="numOccupants" name="numOccupants" min="1" value="${contract.numOccupants}">
    </div>

    <div class="form-row">
        <label for="billingDay">Ngày chốt hóa đơn (1–28)</label>
        <input type="number" id="billingDay" name="billingDay" min="1" max="28"
               value="${empty contract.billingDay ? 1 : contract.billingDay}">
    </div>

    <div class="form-row">
        <label for="note">Ghi chú</label>
        <textarea id="note" name="note" rows="3"><c:out value="${contract.note}"/></textarea>
    </div>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">Lưu</button>
        <a class="btn" href="${ctx}/contracts">Hủy</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
