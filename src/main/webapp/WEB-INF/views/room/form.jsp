<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Phòng trọ" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h1><c:out value="${empty room.id ? 'Thêm phòng' : 'Sửa phòng'}"/></h1>

<%-- enctype multipart/form-data de upload anh phong --%>
<form class="form" method="post" action="${ctx}/rooms/save" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${room.id}">

    <div class="form-row">
        <label for="roomCode">Mã phòng *</label>
        <input type="text" id="roomCode" name="roomCode" required
               value="<c:out value='${room.roomCode}'/>">
    </div>

    <div class="form-row">
        <label for="roomTypeId">Loại phòng *</label>
        <select id="roomTypeId" name="roomTypeId" required>
            <option value="">— Chọn loại phòng —</option>
            <c:forEach var="t" items="${roomTypes}">
                <option value="${t.id}" ${room.roomTypeId == t.id ? 'selected' : ''}>
                    <c:out value="${t.name}"/>
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-row">
        <label for="floorNo">Tầng</label>
        <input type="number" id="floorNo" name="floorNo" min="1" value="${room.floorNo}">
    </div>

    <div class="form-row">
        <label for="area">Diện tích (m²)</label>
        <input type="number" id="area" name="area" step="0.01" min="0" value="${room.area}">
    </div>

    <div class="form-row">
        <label for="price">Giá thuê / tháng *</label>
        <input type="number" id="price" name="price" step="1000" min="0" required value="${room.price}">
    </div>

    <div class="form-row">
        <label for="maxOccupants">Số người tối đa</label>
        <input type="number" id="maxOccupants" name="maxOccupants" min="1" value="${room.maxOccupants}">
    </div>

    <div class="form-row">
        <label for="status">Trạng thái</label>
        <select id="status" name="status">
            <option value="AVAILABLE"   ${room.status == 'AVAILABLE'   ? 'selected' : ''}>Còn trống</option>
            <option value="OCCUPIED"    ${room.status == 'OCCUPIED'    ? 'selected' : ''}>Đang thuê</option>
            <option value="RESERVED"    ${room.status == 'RESERVED'    ? 'selected' : ''}>Đã giữ chỗ</option>
            <option value="MAINTENANCE" ${room.status == 'MAINTENANCE' ? 'selected' : ''}>Đang sửa</option>
        </select>
    </div>

    <div class="form-row">
        <label for="image">Ảnh phòng</label>
        <input type="file" id="image" name="image" accept="image/*">
        <c:if test="${not empty room.imagePath}">
            <img class="thumb" src="${ctx}/${room.imagePath}" alt="Ảnh phòng">
        </c:if>
    </div>

    <div class="form-row">
        <label for="description">Mô tả</label>
        <textarea id="description" name="description" rows="3"><c:out value="${room.description}"/></textarea>
    </div>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">Lưu</button>
        <a class="btn" href="${ctx}/rooms">Hủy</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
