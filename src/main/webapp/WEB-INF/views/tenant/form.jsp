<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Khách thuê" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h1><c:out value="${empty tenant.id ? 'Thêm khách thuê' : 'Sửa khách thuê'}"/></h1>

<form class="form" method="post" action="${ctx}/tenants/save" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${tenant.id}">

    <div class="form-row">
        <label for="fullName">Họ tên *</label>
        <input type="text" id="fullName" name="fullName" required
               value="<c:out value='${tenant.fullName}'/>">
    </div>

    <div class="form-row">
        <label for="idCard">CMND / CCCD *</label>
        <input type="text" id="idCard" name="idCard" required
               value="<c:out value='${tenant.idCard}'/>">
    </div>

    <div class="form-row">
        <label for="dateOfBirth">Ngày sinh</label>
        <input type="date" id="dateOfBirth" name="dateOfBirth" value="${tenant.dateOfBirth}">
    </div>

    <div class="form-row">
        <label for="gender">Giới tính</label>
        <select id="gender" name="gender">
            <option value="MALE"   ${tenant.gender == 'MALE'   ? 'selected' : ''}>Nam</option>
            <option value="FEMALE" ${tenant.gender == 'FEMALE' ? 'selected' : ''}>Nữ</option>
            <option value="OTHER"  ${tenant.gender == 'OTHER'  ? 'selected' : ''}>Khác</option>
        </select>
    </div>

    <div class="form-row">
        <label for="phone">Điện thoại *</label>
        <input type="tel" id="phone" name="phone" required value="<c:out value='${tenant.phone}'/>">
    </div>

    <div class="form-row">
        <label for="email">Email</label>
        <input type="email" id="email" name="email" value="<c:out value='${tenant.email}'/>">
    </div>

    <div class="form-row">
        <label for="hometown">Địa chỉ thường trú</label>
        <input type="text" id="hometown" name="hometown" value="<c:out value='${tenant.hometown}'/>">
    </div>

    <div class="form-row">
        <label for="school">Trường</label>
        <input type="text" id="school" name="school" value="<c:out value='${tenant.school}'/>">
    </div>

    <div class="form-row">
        <label for="studentCode">Mã sinh viên</label>
        <input type="text" id="studentCode" name="studentCode" value="<c:out value='${tenant.studentCode}'/>">
    </div>

    <div class="form-row">
        <label for="avatar">Ảnh</label>
        <input type="file" id="avatar" name="avatar" accept="image/*">
        <c:if test="${not empty tenant.avatarPath}">
            <img class="thumb" src="${ctx}/${tenant.avatarPath}" alt="Ảnh khách thuê">
        </c:if>
    </div>

    <div class="form-row">
        <label for="note">Ghi chú</label>
        <textarea id="note" name="note" rows="3"><c:out value="${tenant.note}"/></textarea>
    </div>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">Lưu</button>
        <a class="btn" href="${ctx}/tenants">Hủy</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
