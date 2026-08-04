<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Lập hóa đơn" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h1>Lập hóa đơn</h1>

<form class="form" method="post" action="${ctx}/invoices/save" id="invoiceForm">

    <div class="form-row">
        <label for="contractId">Hợp đồng *</label>
        <select id="contractId" name="contractId" required>
            <option value="">— Chọn hợp đồng đang hiệu lực —</option>
            <c:forEach var="ct" items="${activeContracts}">
                <option value="${ct.id}" data-price="${ct.monthlyPrice}"
                        data-occupants="${ct.numOccupants}">
                    <c:out value="${ct.contractCode}"/> — phòng <c:out value="${ct.roomCode}"/>
                    — <c:out value="${ct.tenantName}"/>
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-row">
        <label for="periodMonth">Kỳ (tháng / năm) *</label>
        <span>
            <input type="number" id="periodMonth" name="periodMonth" min="1" max="12" required
                   placeholder="Tháng" style="max-width:6rem">
            <input type="number" id="periodYear" name="periodYear" min="2000" max="2100" required
                   placeholder="Năm" style="max-width:8rem">
        </span>
    </div>

    <div class="form-row">
        <label for="issueDate">Ngày lập *</label>
        <input type="date" id="issueDate" name="issueDate" required>
    </div>

    <div class="form-row">
        <label for="dueDate">Hạn thanh toán *</label>
        <input type="date" id="dueDate" name="dueDate" required>
    </div>

    <div class="form-row">
        <label for="roomCharge">Tiền phòng *</label>
        <input type="number" id="roomCharge" name="roomCharge" step="1000" min="0" required>
    </div>

    <div class="form-row">
        <label for="discount">Giảm giá</label>
        <input type="number" id="discount" name="discount" step="1000" min="0" value="0">
    </div>

    <h2>Dịch vụ</h2>
    <%-- Chi so dau (oldIndex) cua dich vu METERED se duoc nap qua AJAX
         GET /invoices/prepare?contractId=..&month=..&year=..  (xem assets/js/main.js) --%>
    <table class="table" id="serviceTable">
        <thead>
        <tr>
            <th>Dịch vụ</th>
            <th>Đơn vị</th>
            <th>Chỉ số đầu</th>
            <th>Chỉ số cuối</th>
            <th>Số lượng</th>
            <th>Đơn giá</th>
            <th>Thành tiền</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty services}">
                <tr><td colspan="7" class="empty">Chưa nạp danh sách dịch vụ (InvoiceServlet chưa implement)</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="s" items="${services}">
                    <tr data-type="${s.type}">
                        <td>
                            <c:out value="${s.name}"/>
                            <input type="hidden" name="serviceId" value="${s.id}">
                        </td>
                        <td><c:out value="${s.unit}"/></td>
                        <td><input type="number" name="oldIndex" step="0.01" min="0"
                                   ${s.type != 'METERED' ? 'disabled' : ''}></td>
                        <td><input type="number" name="newIndex" step="0.01" min="0"
                                   ${s.type != 'METERED' ? 'disabled' : ''}></td>
                        <td><input type="number" name="quantity" step="0.01" min="0" value="1"></td>
                        <td><input type="number" name="unitPrice" step="1" min="0" value="${s.unitPrice}"></td>
                        <td class="amount">0</td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>

    <div class="form-row">
        <label for="note">Ghi chú</label>
        <textarea id="note" name="note" rows="2"></textarea>
    </div>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">Lập hóa đơn</button>
        <a class="btn" href="${ctx}/invoices">Hủy</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
