<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Chi tiết hóa đơn" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="page-head">
    <h1>Hóa đơn <c:out value="${invoice.invoiceCode}" default="—"/></h1>
    <div>
        <a class="btn" href="${ctx}/invoices/pdf?id=${invoice.id}">Xuất PDF</a>
        <a class="btn" href="${ctx}/invoices">Về danh sách</a>
    </div>
</div>

<c:choose>
    <c:when test="${empty invoice}">
        <p class="empty">Chưa có dữ liệu (InvoiceServlet chưa implement).</p>
    </c:when>
    <c:otherwise>
        <dl class="detail-list">
            <dt>Hợp đồng</dt>   <dd><c:out value="${invoice.contractCode}"/></dd>
            <dt>Phòng</dt>      <dd><c:out value="${invoice.roomCode}"/></dd>
            <dt>Khách thuê</dt> <dd><c:out value="${invoice.tenantName}"/></dd>
            <dt>Kỳ</dt>         <dd>${invoice.periodMonth}/${invoice.periodYear}</dd>
            <dt>Ngày lập</dt>   <dd><c:out value="${invoice.issueDate}"/></dd>
            <dt>Hạn TT</dt>     <dd><c:out value="${invoice.dueDate}"/></dd>
            <dt>Trạng thái</dt> <dd><span class="badge badge-${invoice.status}"><c:out value="${invoice.status}"/></span></dd>
        </dl>

        <h2>Chi tiết dịch vụ</h2>
        <table class="table">
            <thead>
            <tr>
                <th>Dịch vụ</th><th>Chỉ số đầu</th><th>Chỉ số cuối</th>
                <th>Số lượng</th><th>Đơn giá</th><th>Thành tiền</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="d" items="${invoice.details}">
                <tr>
                    <td><c:out value="${d.serviceName}"/></td>
                    <td><c:out value="${d.oldIndex}"/></td>
                    <td><c:out value="${d.newIndex}"/></td>
                    <td><c:out value="${d.quantity}"/> <c:out value="${d.serviceUnit}"/></td>
                    <td><fmt:formatNumber value="${d.unitPrice}" maxFractionDigits="0"/> đ</td>
                    <td><fmt:formatNumber value="${d.amount}" maxFractionDigits="0"/> đ</td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr><th colspan="5">Tiền phòng</th>
                <th><fmt:formatNumber value="${invoice.roomCharge}" maxFractionDigits="0"/> đ</th></tr>
            <tr><th colspan="5">Tiền dịch vụ</th>
                <th><fmt:formatNumber value="${invoice.serviceTotal}" maxFractionDigits="0"/> đ</th></tr>
            <tr><th colspan="5">Giảm giá</th>
                <th>−<fmt:formatNumber value="${invoice.discount}" maxFractionDigits="0"/> đ</th></tr>
            <tr class="total"><th colspan="5">TỔNG CỘNG</th>
                <th><fmt:formatNumber value="${invoice.totalAmount}" maxFractionDigits="0"/> đ</th></tr>
            <tr><th colspan="5">Đã thu</th>
                <th><fmt:formatNumber value="${invoice.paidAmount}" maxFractionDigits="0"/> đ</th></tr>
            </tfoot>
        </table>

        <c:if test="${invoice.status != 'PAID' and invoice.status != 'CANCELLED'}">
            <h2>Ghi nhận thanh toán</h2>
            <form class="form" method="post" action="${ctx}/invoices/pay">
                <input type="hidden" name="id" value="${invoice.id}">
                <div class="form-row">
                    <label for="amount">Số tiền</label>
                    <input type="number" id="amount" name="amount" step="1000" min="0" required>
                </div>
                <div class="form-row">
                    <label for="method">Phương thức</label>
                    <select id="method" name="method">
                        <option value="CASH">Tiền mặt</option>
                        <option value="TRANSFER">Chuyển khoản</option>
                        <option value="MOMO">MoMo</option>
                        <option value="OTHER">Khác</option>
                    </select>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Ghi nhận</button>
                </div>
            </form>
        </c:if>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
