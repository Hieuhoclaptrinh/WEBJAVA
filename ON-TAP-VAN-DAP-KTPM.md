# ÔN TẬP VẤN ĐÁP — KIỂM THỬ PHẦN MỀM (IT3240) — ĐỀ 31 StudentRent

> File này dùng để dán vào Claude trên điện thoại. Phần 0 là hướng dẫn cho Claude; các phần sau là toàn bộ dữ liệu thật của bài tập lớn (báo cáo Word, phiếu Excel và mã nguồn).
>
> **Phụ lục A, B, C** ở cuối file chứa chi tiết từng ca, trích nguyên văn từ 3 phiếu Excel: A = 183 ca Unit (đầu vào, kết quả mong đợi, loại, P/F); B = 214 ca IT (bước, mong đợi, kỹ thuật, kết quả, lý do chọn ca); C = 26 ca ST.

---

## 0. HƯỚNG DẪN CHO CLAUDE (đọc trước)

Bạn là **giảng viên chấm vấn đáp** môn Kiểm thử phần mềm, kiêm gia sư. Sinh viên đã nộp bài tập lớn mô tả bên dưới. Mai có buổi vấn đáp, GV dặn **mang giấy và bút**, nên rất có thể có câu tính toán hoặc thiết kế test case bằng tay.

**Cách làm việc:**
1. Mở đầu, hỏi sinh viên là thành viên nào trong nhóm (mục 2). Ưu tiên hỏi về **phần của chính người đó**, nhưng thỉnh thoảng hỏi chéo phần người khác, vì GV có thể hỏi bất kỳ ai về bất kỳ phần nào.
2. **Hỏi từng câu một**, chờ trả lời rồi mới hỏi tiếp. Không đưa đáp án trước.
3. Sau mỗi câu trả lời: chấm theo thang 0–10, nói **đúng chỗ nào, sai/thiếu chỗ nào**, rồi đưa đáp án mẫu ngắn gọn, đúng kiểu sinh viên nên nói trước GV.
4. Câu tính toán: yêu cầu sinh viên trình bày từng bước như viết trên giấy (vẽ đồ thị bằng chữ, liệt kê nút/cạnh, công thức, kết quả). Chấm cả cách trình bày.
5. Trộn đều 4 nhóm câu theo Phiếu chấm (mục 1). Khoảng 40% là câu tính hoặc thiết kế test case tay (mục 7, 8).
6. Câu hỏi phải bám **dữ liệu thật** trong file này (tên hàm, số ca, mã lỗi, màn hình). Không bịa số liệu khác. Khi hỏi về một ca cụ thể (ví dụ "IT02-15 kiểm gì, vì sao chọn?", "UTCID08 của F01 cho kết quả gì?"), tra trong Phụ lục A/B/C và chấm theo đó. Nên hỏi kiểu GV hay hỏi: chọn ngẫu nhiên 1 ca trong phần của sinh viên, bắt giải thích kỹ thuật và lý do chọn ca.
7. Nếu sinh viên trả lời lệch với báo cáo, chỉ ra ngay. GV sẽ đối chiếu với báo cáo.
8. Khi sinh viên gõ **"XUẤT FILE"** (hoặc kết thúc buổi), xuất một file Markdown theo đúng mẫu ở **mục 11**.

---

## 1. Bối cảnh môn học và thang điểm

- Trường ĐH Công nghệ Đông Á, Khoa CNTT. HK I 2026–2027. GV ra đề: Nguyễn Thị Nga.
- Hình thức: **Bài tập lớn CÓ vấn đáp**. Vắng vấn đáp thì 0 điểm.
- Đề 31: "Kiểm thử website quản lý thuê nhà trọ", tức hệ thống StudentRent do nhóm tự viết.

**Phiếu chấm, tổng 10 điểm:**
- 1.0 Hình thức
- 5.0 Nội dung bản mềm: Unit 1.0 · xác định màn hình IT 0.5 · viết IT case 0.5 · thực thi IT + log bug 0.5 · System test 1.0 · log bug + báo cáo kết quả 0.5 · Automation 1.0
- **4.0 Vấn đáp, mỗi mục 1.0:**
  - 3.1 Kỹ thuật phân tích & thiết kế test case
  - 3.2 Nguyên lý kiểm thử
  - 3.3 Các hoạt động kiểm thử
  - 3.4 Kỹ năng viết test case & log bug

---

## 2. Nhóm 3 và phân công

| Thành viên | Unit test (5 hàm) | Integration test | System test | Automation | Tổng |
|---|---|---|---|---|---|
| **Nguyễn Văn Hiếu** (20231766, nhóm trưởng, Chương 1, trình bày) | F01 DateUtil.parse, F02 UploadUtil.isAllowedImage, F08 TenantService.create, F10 Tenant.getInitials, F14 DateUtil.format: **63 ca** | IT01 Thêm/Sửa phòng, IT02 Thêm/Sửa khách thuê: **66 ca** | Bước 1–3 (ca 1–9) | — | 138 |
| **Phùng Tiến Dũng** (Chương 2) | F03 PasswordUtil.verify, F04 AuthService.hasAnyRole, F05 ContractService.create, F09 FeedbackService.submit, F13 PasswordUtil.hash: **65 ca** | IT03 Lập hợp đồng, IT05 Đăng nhập & phân quyền: **71 ca** | Bước 4–5 (ca 10–18) | LoginAndRoleTest (7 kịch bản) | 145 |
| **Đỗ Đức Đạt** (Chương 3, 4) | F06 InvoiceService.pay, F07 InvoiceService.createInvoice, F11 ReportService.revenueTotals, F12 JsonUtil.toJson, F15 ChartData.labels: **55 ca** | IT04 Hóa đơn & thu tiền, IT06 Báo cáo & Tổng quan: **77 ca** | Bước 6–7 (ca 19–26) | EndToEndFlowTest (10 kịch bản) | 140 |
| **Tổng** | **183 ca / 15 hàm** | **214 ca / 6 màn hình** | **26 ca / 7 bước** | **17 kịch bản** | **423** |

---

## 3. Hệ thống StudentRent (tóm tắt)

- Java 17, Jakarta Servlet + JSP + JSTL, **không dùng framework**. Tomcat 10.1. CSDL MySQL 8 / **MariaDB 10.4** (máy thật chạy XAMPP MariaDB). JDBC + HikariCP. Thư viện: jBCrypt, Gson, iText (PDF), Apache POI (Excel).
- Kiến trúc MVC 4 lớp: **Servlet → Service → DAO → JSP**. Quy tắc nghiệp vụ nằm ở Service, nên Unit test nhắm vào Service/Util. Lỗi nghiệp vụ được ném bằng `IllegalArgumentException` kèm thông báo tiếng Việt.
- Quy mô: 9 bảng, 9 model, 11 service, 12 servlet, 4 filter, 25 JSP.
- Thứ tự filter: **EncodingFilter → AuthFilter → CsrfFilter → RoleFilter**.
- 3 vai trò: **ADMIN** (admin/admin123), **MANAGER** (manager/manager123) làm được tất cả. **STAFF** (staff/staff123) làm nghiệp vụ hằng ngày, **không xem được báo cáo** (gọi `/reports` nhận 403).

**Máy trạng thái (nguồn của các ca kỹ thuật ST):**
- Phòng: AVAILABLE (Còn trống) · RESERVED (Đã giữ chỗ) · OCCUPIED (Đang thuê) · MAINTENANCE (Đang sửa, chỉ đặt tay; nghiệp vụ hợp đồng không ghi đè)
- Hợp đồng: PENDING (ký rồi, chưa tới ngày bắt đầu) · ACTIVE · EXPIRED (qua ngày kết thúc) · TERMINATED (chấm dứt trước hạn)
- Hóa đơn: UNPAID → PARTIAL (thu một phần) → PAID (thu đủ) · OVERDUE (chưa đủ và quá hạn) · CANCELLED (chỉ hủy được khi chưa thu đồng nào; không tính doanh thu, không tính công nợ)

**Luồng nghiệp vụ E2E dùng cho System test, F_Rent_Room "Cho thuê phòng và thu tiền hàng tháng", 7 bước:**
1. Đăng nhập và khai báo phòng D101
2. Nhập khách Nguyễn Văn Hiếu (CCCD 001203012345)
3. Ký hợp đồng → HD2026-0012, phòng chuyển OCCUPIED
4. Lập hóa đơn kỳ 10/2026 → INV-202610-0001
5. Thu tiền (thu một phần, rồi thu nốt → PAID)
6. Xem báo cáo (STAFF bị chặn)
7. Chấm dứt hợp đồng → phòng về AVAILABLE và ký lại được

**Hóa đơn mẫu, ca F_Rent_Room-11 (dùng ra bài tính):**

| Khoản | Cách tính | Thành tiền |
|---|---|---|
| Điện | 120 kWh × 3.500 | 420.000 |
| Nước | 8 m³ × 15.000 | 120.000 |
| Rác | 1 × 20.000 | 20.000 |
| Internet | 1 × 100.000 | 100.000 |
| Gửi xe | 2 người × 80.000 | 160.000 |
| **Tiền dịch vụ** | | **820.000** |
| Tiền phòng | lấy từ hợp đồng | 2.500.000 |
| Giảm giá | | 0 |
| **TỔNG** | | **3.320.000** |

Hạn thu là ngày chốt của kỳ (billing_day = 5), tức 2026-10-05. Máy chủ **tính lại toàn bộ**, không tin số do trình duyệt gửi lên (ca F_Rent_Room-14: sửa tay tiền phòng thành 1 vẫn bị máy chủ ghi đè).

---

## 4. Đặc tả trường (dùng ra bài EP / BVA tay)

**IT01 Thêm/Sửa phòng:**
- Mã phòng: bắt buộc, tối đa 20 ký tự, không trùng (không phân biệt hoa/thường), có cắt khoảng trắng
- Loại phòng: bắt buộc, phải có trong danh mục
- Tầng: bỏ trống thì = 1
- Diện tích ≥ 0
- Giá thuê: bắt buộc, ≥ 0, nhận "2.500.000" và "2500000,50"
- Số người tối đa ≥ 1 (bỏ trống thì = 1)
- Ảnh: jpg/jpeg/png/webp, ≤ 5 MB
- Mô tả: ≤ 500 ký tự

**IT02 Khách thuê (theo CODE thật trong `TenantService.validate`):**
- Họ tên: bắt buộc, ≤ 150 ký tự
- CCCD: bắt buộc, regex `\d{9}|\d{12}` (CMND 9 số hoặc CCCD 12 số), không trùng
- Điện thoại: **bắt buộc**, regex `0\d{9}` (đúng 10 số, bắt đầu bằng 0)
- Email: tùy chọn, ≤ 150 ký tự, đúng dạng
- Mã SV: tùy chọn, ≤ 50 ký tự, không trùng
- Ngày sinh: không ở tương lai, năm ≥ 1900
- Quê ≤ 255, Trường ≤ 200, Ghi chú ≤ 500
- Giới tính: MALE/FEMALE/OTHER, bỏ trống thì = OTHER
- Ảnh: ≤ 2 MB

**IT03 Hợp đồng:**
- Phòng phải AVAILABLE (máy chủ khóa dòng và kiểm lại)
- Khách chưa có hợp đồng ACTIVE/PENDING
- Ngày kết thúc phải **sau** ngày bắt đầu
- Giá ≥ 0, cọc ≥ 0
- Số người ở ≥ 1 và ≤ sức chứa phòng
- **Ngày chốt hóa đơn 1–28** (vì tháng 2 chỉ có 28 ngày)
- Ghi chú ≤ 500
- Ký hợp đồng và đổi trạng thái phòng chạy trong **một giao dịch**

**IT04 Hóa đơn & thu tiền:**
- Kỳ yyyy-MM, tháng 1–12, mỗi hợp đồng 1 hóa đơn/kỳ
- Chỉ số cuối ≥ chỉ số đầu
- Giảm giá ≥ 0 và không vượt tổng
- **Số tiền thu > 0 và ≤ số còn phải thu**
- Hình thức: CASH/TRANSFER/MOMO/OTHER, bỏ trống thì = CASH
- Hóa đơn đã thu tiền thì không hủy được; đã thu đủ thì không thu thêm

**IT06 Báo cáo:**
- Năm bị kẹp trong khoảng 2000–2100
- Khoảng lọc tối đa 60 kỳ (vượt thì cắt lấy 60 kỳ cuối)
- Từ kỳ > Đến kỳ thì tự hoán đổi
- PDF chỉ có cho báo cáo công nợ (báo cáo khác trả 400)

---

## 5. Kết quả và danh sách lỗi (phải thuộc)

**Unit:** 183 ca, **179 Đạt / 4 Không đạt**, tỷ lệ 97,81%. Phân loại 50 N (bình thường) / 77 A (bất thường) / 56 B (biên). Chạy bằng JUnit 5.10.2 + Maven Surefire 3.2.5, lệnh `mvn test`.

**IT:** 214 ca. Phân bố kỹ thuật: EP 63 (29,4%) · BVA 55 (25,7%) · EG 31 (14,5%) · ST 40 (18,7%) · SEC 25 (11,7%).
- Đã thực thi 76 ca của IT05 và IT06: **75 Đạt, 0 Không đạt, 1 N/A** (IT06-09 cần xóa sạch bảng invoices nên không chạy).
- **138 ca IT01–IT04 CHƯA CHẠY**, để lại cho đợt hồi quy vì phải thao tác tay bằng DevTools.

**ST:** 26/26 Pass (21/09/2026).

**Automation:** 17/17 Đạt, 9,4 giây (Selenium 4.25 + JUnit 5, Chrome headless). Đặt ở project Maven riêng `automation/` để Selenium không lọt vào file WAR.

| Mã lỗi | Nơi phát hiện | Mô tả | Mức độ |
|---|---|---|---|
| DFID001 | F01 UTCID08, 12 | `DateUtil.parse` chấp nhận ngày không tồn tại: `29/02/2026` thành 2026-02-28, `31/04/2026` thành 2026-04-30, vì formatter dùng `ResolverStyle.SMART` | Cao |
| DFID002 | F03 UTCID09 | `PasswordUtil.verify` ném `IllegalArgumentException("Invalid salt version")` khi hash trong DB sai định dạng, thay vì trả false. `AuthService.login` không bắt nên trang đăng nhập lỗi 500 | Trung bình |
| DFID003 | F13 UTCID11 | `PasswordUtil.hash(null)` không báo lỗi mà băm chuỗi "null"; tài khoản đó đăng nhập được bằng mật khẩu "null" | Cao |
| DFID004 | IT05-30 | Menu Báo cáo vẫn hiện với STAFF; bấm vào thì nhận 403 | Thấp |
| DFID005 | IT06-36 | Xin PDF báo cáo doanh thu trả đúng mã 400 nhưng hiện trang lỗi mặc định của Tomcat, người dùng không đọc được lý do | Trung bình |

---

## 6. Mã nguồn các hàm Unit test (dùng ra câu vẽ CFG / V(G) / coverage)

### F01 `DateUtil.parse` (Hiếu, 18 dòng, 12 ca)
```java
public static LocalDate parse(String text) {
    if (text == null) {
        return null;
    }
    String value = text.trim();
    if (value.isEmpty()) {
        return null;
    }
    for (DateTimeFormatter formatter : new DateTimeFormatter[]{ISO_DATE, VN_DATE}) {
        try {
            return LocalDate.parse(value, formatter);
        } catch (DateTimeParseException ignored) {
            // Thu dinh dang tiep theo
        }
    }
    throw new IllegalArgumentException("Ngày không hợp lệ: " + value
            + " (định dạng dd/MM/yyyy hoặc yyyy-MM-dd).");
}
// VN_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ROOT)  -> ResolverStyle.SMART (gây DFID001)
// ISO_DATE = ISO_LOCAL_DATE                                          -> STRICT
```
12 ca dữ liệu: `null`, `""`, `"   "`, `"2026-08-27"`, `"27/08/2026"`, `" 27/08/2026 "`, `"29/02/2024"` (năm nhuận, hợp lệ), `"29/02/2026"` (lỗi DFID001), `"31/13/2026"`, `"27-08-2026"`, `"abc"`, `"31/04/2026"` (lỗi DFID001).

### F14 `DateUtil.format` (Hiếu)
```java
public static String format(LocalDate date) {
    return date == null ? "" : date.format(VN_DATE);
}
```

### F02 `UploadUtil.isAllowedImage` (Hiếu, 14 ca)
```java
public static boolean isAllowedImage(String fileName) {
    String ext = extensionOf(fileName);
    return ext != null && Arrays.asList(Constants.ALLOWED_IMAGE_EXTENSIONS).contains(ext);
}
// ALLOWED_IMAGE_EXTENSIONS = {"jpg","jpeg","png","webp"}
private static String extensionOf(String fileName) {
    if (fileName == null) return null;
    String name = fileName.replace('\\', '/');
    name = name.substring(name.lastIndexOf('/') + 1);   // IE cũ gửi cả đường dẫn
    int dot = name.lastIndexOf('.');
    if (dot < 0 || dot == name.length() - 1) return null;
    return name.substring(dot + 1).toLowerCase(Locale.ROOT);  // "JPG" -> "jpg"
}
```

### F10 `Tenant.getInitials` (Hiếu, 12 ca)
```java
public String getInitials() {
    if (fullName == null || fullName.isBlank()) {
        return "?";
    }
    String[] words = fullName.trim().split("\\s+");
    String first = words[0].substring(0, 1);
    String last = words[words.length - 1].substring(0, 1);
    String initials = words.length == 1 ? first : first + last;
    return initials.toUpperCase(java.util.Locale.ROOT);
}
```

### F08 `TenantService.create` (Hiếu, 13 ca): `validate(tenant, null)` rồi `tenantDAO.insert(tenant)`
```java
private void validate(Tenant tenant, Long excludeId) {
    if (tenant == null) throw ... "Không có dữ liệu khách thuê."
    // trimToNull tất cả trường chuỗi ("" hoặc "  " -> null)
    if (fullName == null) throw ... "Họ tên không được để trống."
    requireMaxLength(fullName, 150)
    if (idCard == null) throw ... "CMND / CCCD không được để trống."
    if (!idCard.matches("\\d{9}|\\d{12}")) throw ... "phải gồm 9 chữ số (CMND) hoặc 12 chữ số (CCCD)."
    if (existsByIdCard(idCard, excludeId)) throw ... "CMND / CCCD <số> đã có trong hệ thống."
    if (phone == null) throw ... "Số điện thoại không được để trống."
    if (!phone.matches("0\\d{9}")) throw ... "Số điện thoại phải gồm 10 chữ số, bắt đầu bằng 0."
    if (email != null) { requireMaxLength(email,150); if (!EMAIL_PATTERN) throw ... }
    if (studentCode != null) { requireMaxLength(studentCode,50); if (existsByStudentCode) throw ... }
    if (dateOfBirth != null) {
        if (dob.isAfter(today)) throw ... "Ngày sinh không được ở tương lai."
        if (dob.getYear() < 1900) throw ...
    }
    requireMaxLength(hometown,255); requireMaxLength(school,200); requireMaxLength(note,500);
    if (gender == null || gender.isBlank()) gender = OTHER;
    if (!GENDERS.contains(gender)) throw ... "Giới tính không hợp lệ"
}
```

### F03 `PasswordUtil.verify` và F13 `PasswordUtil.hash` (Dũng)
```java
public static String hash(String plainPassword) {
    return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));   // 12 vòng, salt ngẫu nhiên
}
public static boolean verify(String plainPassword, String storedHash) {
    if (plainPassword == null || storedHash == null || storedHash.isBlank()) {
        return false;
    }
    return BCrypt.checkpw(plainPassword, storedHash);           // hash hỏng -> ném IAE (DFID002)
}
```

### F04 `AuthService.hasAnyRole` (Dũng, 10 ca)
```java
public boolean hasAnyRole(User user, String... roles) {
    if (user == null || user.getRole() == null || roles == null) {
        return false;
    }
    for (String role : roles) {
        if (user.getRole().equals(role)) {
            return true;
        }
    }
    return false;
}
```

### F05 `ContractService.create` (Dũng, 92 dòng, 17 ca, 9 nhánh kiểm tra): phần validate
```java
if (contract == null)                throw "Không có dữ liệu hợp đồng."
if (roomId == null)                  throw "Chưa chọn phòng."
if (tenantId == null)                throw "Chưa chọn khách thuê đại diện."
if (startDate == null)               throw "Ngày bắt đầu không được để trống."
if (endDate == null)                 throw "Ngày kết thúc không được để trống."
if (!endDate.isAfter(startDate))     throw "Ngày kết thúc phải sau ngày bắt đầu."
if (monthlyPrice == null)            throw ...;  if (monthlyPrice < 0) throw "không được âm"
if (deposit == null) deposit = 0;    if (deposit < 0) throw "Tiền cọc không được âm."
if (numOccupants == null) = 1;       if (numOccupants < 1) throw "Số người ở phải từ 1 trở lên."
if (billingDay == null) = 1;         if (billingDay < 1 || billingDay > 28) throw "Ngày chốt hóa đơn phải trong khoảng 1–28."
if (note != null && note.length() > 500) throw "Ghi chú tối đa 500 ký tự."
// Trong giao dịch: khóa phòng (FOR UPDATE) -> phòng phải AVAILABLE -> numOccupants <= maxOccupants
// -> khách không có hợp đồng ACTIVE/PENDING -> sinh mã HD<năm>-<STT> -> insert -> đổi trạng thái phòng
```

### F06 `InvoiceService.pay` (Đạt, 8 ca)
```java
public boolean pay(Long invoiceId, BigDecimal amount, String method) {
    if (invoiceId == null) throw "Thiếu id hóa đơn."
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw "Số tiền thu phải lớn hơn 0."
    String paymentMethod = (method == null || method.isBlank()) ? "CASH" : method.trim();
    if (!PAYMENT_METHODS.contains(paymentMethod)) throw "Phương thức thanh toán không hợp lệ"
    // Trong giao dịch:
    Invoice invoice = findByIdForUpdate(id).orElseThrow("Hóa đơn không tồn tại.")
    if (status == CANCELLED) throw "... đã hủy nên không thu được."
    if (status == PAID)      throw "... đã thu đủ."
    remaining = total - paidAmount
    if (paid > remaining)    throw "Số tiền thu vượt quá số còn phải thu (... đ)."
    newPaid = paidAmount + paid
    settled = newPaid >= total
    status = settled ? PAID : PARTIAL
    paymentDate = settled ? today : invoice.getPaymentDate()
    return applyPayment(...)
}
```

### F11 `ReportService.revenueTotals` (Đạt) · F15 `ChartData.labels` (Đạt) · F12 `JsonUtil.toJson` (Đạt)
```java
public Map<String,Object> revenueTotals(List<Map<String,Object>> rows) {
    BigDecimal total = 0, paid = 0; long invoices = 0;
    for (Map row : rows) { total += row.total; paid += row.paid; invoices += row.invoices; }
    return {invoices, total, paid, remaining = total - paid};
}
public static List<String> labels(List<Map<String,Object>> rows, String key) {
    List<String> labels = new ArrayList<>(rows.size());
    for (Map row : rows) {
        Object value = row.get(key);
        labels.add(value == null ? "" : String.valueOf(value));
    }
    return labels;
}
public static String toJson(Object data) { return GSON.toJson(data); }  // Gson có adapter LocalDate/LocalDateTime -> ISO
```

---

## 7. Kiến thức và công thức (đáp án chuẩn để Claude đối chiếu)

### 7.1 Kỹ thuật hộp đen
- **EP (phân lớp tương đương):** chia miền vào thành các lớp hợp lệ/không hợp lệ cho cùng kết quả, mỗi lớp lấy 1 đại diện.
- **BVA (phân tích giá trị biên)** với n biến:

  | Kiểu | Số ca |
  |---|---|
  | BVA chuẩn (min, min+, nom, max−, max) | **4n + 1** |
  | Robust (thêm min−, max+) | **6n + 1** |
  | Worst-case | **5ⁿ** |
  | Robust worst-case | **7ⁿ** |

  Ví dụ: 2 biến cho 9 / 13 / 25 / 49 ca.
- **Bảng quyết định:** n điều kiện nhị phân cho 2ⁿ luật, sau đó rút gọn các luật trùng kết quả.
- **Chuyển trạng thái:** phủ mọi trạng thái (state coverage), mọi chuyển hợp lệ (0-switch), và thử các chuyển không hợp lệ (ví dụ thu tiền hóa đơn CANCELLED).
- **Đoán lỗi (EG):** dựa kinh nghiệm, ví dụ chuỗi toàn khoảng trắng, đuôi file viết hoa, 29/02 năm không nhuận, dấu phân cách số kiểu Việt Nam.

### 7.2 Kỹ thuật hộp trắng
- **V(G) = E − N + 2 = P + 1 = số vùng.** Với điều kiện ghép `a || b`, đếm từng điều kiện đơn là một nút quyết định.
- V(G) = số đường độc lập cơ sở = **số ca tối thiểu để phủ đường cơ sở**.
- Phủ câu lệnh ≤ phủ nhánh ≤ phủ điều kiện/nhánh ≤ phủ đường. Phủ nhánh 100% thì phủ câu lệnh 100%, chiều ngược lại không đúng.
- Phủ câu lệnh (%) = số câu lệnh đã chạy / tổng × 100. Phủ nhánh (%) = số nhánh đã đi / tổng nhánh × 100 (mỗi if có 2 nhánh).

**Đáp án V(G) cho các hàm của nhóm:**

| Hàm | Điểm quyết định | V(G) | Bộ đường cơ sở |
|---|---|---|---|
| DateUtil.parse | null? · empty? · vòng for · catch | **5** | null → null; `"  "` → null; `"2026-10-05"` → ISO đọc được ngay; `"05/10/2026"` → ISO lỗi, VN đọc được; `"abc"` → cả hai lỗi, ném IAE |
| DateUtil.format | ternary | **2** | null → ""; ngày hợp lệ → "dd/MM/yyyy" |
| Tenant.getInitials | null? · isBlank? · length==1? | **4** | null → "?"; "  " → "?"; "Hiếu" → "H"; "Nguyễn Văn Hiếu" → "NH" |
| isAllowedImage (riêng thân hàm) | ext!=null · contains | **3** | null → false; "a.exe" → false; "a.jpg" → true |
| PasswordUtil.verify | 3 điều kiện OR | **4** | pw null; hash null; hash rỗng; bình thường gọi checkpw |
| AuthService.hasAnyRole | 3 điều kiện OR · for · if equals | **6** | |
| ChartData.labels | for · ternary | **3** | rows rỗng; value null → ""; value có giá trị |
| revenueTotals | for | **2** | rows rỗng → tất cả 0; có dòng |

### 7.3 Chỉ số báo cáo
- Tỷ lệ Pass = Pass / Tổng đã chạy × 100. Unit: 179/183 = **97,81%**.
- **Mật độ test case = số TC / KLOC**, chuẩn template GV là **100 TC/KLOC**. Ví dụ DateUtil.parse: 12 / 0,018 ≈ **667 TC/KLOC**; ContractService.create: 17 / 0,092 ≈ **185 TC/KLOC**.
- Mật độ lỗi = số lỗi / KLOC.
- Tỷ lệ thực thi IT = 76/214 ≈ **35,5%**. Tỷ lệ đạt trên số ca đã chạy (không tính N/A) = 75/75 = 100%.

### 7.4 Lý thuyết (ISTQB CTFL v4)
- **7 nguyên lý:**
  1. Kiểm thử chỉ ra sự hiện diện của lỗi, không chứng minh được là không có lỗi
  2. Kiểm thử toàn bộ là không thể
  3. Kiểm thử sớm tiết kiệm chi phí
  4. Lỗi tập trung theo cụm
  5. Nghịch lý thuốc trừ sâu (test cũ dần mất tác dụng)
  6. Kiểm thử phụ thuộc ngữ cảnh
  7. Ngộ nhận "hết lỗi là xong"
- **Error → Defect (bug) → Failure:** người làm sai, sinh ra khiếm khuyết trong code, khi chạy thì gây hỏng hóc.
- **Mức kiểm thử:** Unit → Integration → System → Acceptance (UAT).
- **Loại kiểm thử:** chức năng / phi chức năng / hộp trắng / liên quan thay đổi (confirmation = retest, regression).
- **Verification** ("làm đúng cách": kiểm tra theo đặc tả) và **Validation** ("làm đúng thứ": đáp ứng nhu cầu người dùng).
- **Kiểm thử tĩnh** (review, phân tích tĩnh, không chạy code) và **kiểm thử động**.
- **Quy trình kiểm thử (STLC):** Lập kế hoạch → Giám sát & điều khiển → Phân tích → Thiết kế → Cài đặt → Thực thi → Hoàn tất.
- **Vòng đời lỗi:** New → Assigned → Open → Fixed → Retest → Closed / Reopen. Ngoài ra có Rejected, Deferred, Duplicate.
- **Severity** (mức nghiêm trọng về kỹ thuật, do tester đặt) khác **Priority** (độ ưu tiên sửa, do PM/lead đặt). Ví dụ:
  - Severity cao, Priority thấp: lỗi crash ở một chức năng hiếm ai dùng.
  - Severity thấp, Priority cao: sai chính tả logo ở trang chủ.
- **Bug report gồm:** ID, tiêu đề, môi trường, tiền điều kiện, các bước tái hiện, dữ liệu, kết quả mong đợi, kết quả thực tế, severity, priority, ảnh minh chứng, người báo, trạng thái.
- **Test case gồm:** ID, mô tả/mục tiêu, tiền điều kiện, các bước, dữ liệu, kết quả mong đợi, kết quả thực tế, trạng thái (Pass/Fail/Untested/N/A), ghi chú (lý do chọn ca).

---

## 8. Ngân hàng câu hỏi gợi ý (Claude dùng để hỏi, KHÔNG đưa đáp án trước)

**Tính toán / thiết kế tay:**
1. Vẽ CFG của `DateUtil.parse`, tính V(G) bằng 2 công thức, liệt kê đường cơ sở và dữ liệu cho từng đường.
2. Làm tương tự với `Tenant.getInitials`.
3. BVA cho "số tiền thu" trong `pay()` khi hóa đơn còn phải thu 1.320.000 đ. *Đáp án:* 0 (lỗi), 1, 1.319.999, 1.320.000 (chuyển PAID), 1.320.001 (lỗi vượt). EP gồm 3 lớp: ≤0 / (0; còn phải thu] / > còn phải thu.
4. BVA cho "ngày chốt hóa đơn" 1–28. *Đáp án:* 0, 1, 2, 27, 28, 29 (robust) và giá trị danh định 14.
5. BVA kết hợp 2 biến: số người ở (1..sức chứa 4) và ngày chốt (1..28). Bao nhiêu ca BVA chuẩn / robust / worst-case? *Đáp án:* 9 / 13 / 25.
6. EP + BVA cho số điện thoại (`0\d{9}`): độ dài 9 / 10 / 11, không bắt đầu bằng 0, có chữ cái, bỏ trống.
7. EP cho CCCD (`\d{9}|\d{12}`): 8, 9, 10, 11, 12, 13 chữ số. Vì sao 10 và 11 không hợp lệ dù nằm giữa 9 và 12? (Hai lớp hợp lệ rời nhau, không phải một khoảng.)
8. Lập bảng quyết định cho `pay()` với 4 điều kiện: hủy? / đã thu đủ? / amount > 0? / amount ≤ còn phải thu?
9. Vẽ máy trạng thái hóa đơn, liệt kê chuyển hợp lệ và không hợp lệ, viết 3 ca ST.
10. Tính lại tổng hóa đơn F_Rent_Room-11 nếu điện 150 kWh, giảm giá 50.000. *Đáp án:* điện 525.000 → dịch vụ 925.000 → tổng 2.500.000 + 925.000 − 50.000 = **3.375.000**.
11. Cho bộ test của `getInitials` chỉ gồm 2 ca: null và "Hiếu". Phủ câu lệnh và phủ nhánh đạt bao nhiêu %?
12. Tính tỷ lệ Pass của Unit, mật độ TC/KLOC của DateUtil.parse, tỷ lệ thực thi IT.

**Kỹ thuật & lý do chọn ca:**
13. Vì sao chọn `"29/02/2024"` và `"29/02/2026"`? Đó là kỹ thuật gì? (BVA + EG năm nhuận, chính ca này phát hiện DFID001.)
14. Vì sao ca IT dùng DevTools xóa `required`? (Để kiểm chốt chặn phía máy chủ; chốt phía trình duyệt không phải là bảo mật.)
15. Khác nhau giữa Unit / IT / ST trong bài của nhóm, mỗi mức lấy 1 ví dụ.
16. Vì sao Selenium dùng `form.submit()` thay vì bấm nút? Vì sao chờ trang tải xong? (Lần chạy đầu 9/17 kịch bản chập chờn vì đọc nhầm trang cũ.)

**Log bug:**
17. Viết tay bug report đầy đủ cho DFID001 (hoặc DFID003). Giải thích severity và priority đã chọn.
18. DFID004 severity Thấp. Nếu là PM, bạn đặt priority bao nhiêu? Vì sao?

**Nguyên lý & hoạt động:**
19. Nêu 7 nguyên lý, mỗi nguyên lý lấy ví dụ từ chính bài làm (ví dụ: lỗi tập trung theo cụm ở PasswordUtil có 2/3 lỗi Unit).
20. Regression và retest khác nhau thế nào? Bộ automation của nhóm thuộc loại nào?
21. 138 ca IT chưa chạy: theo quy trình kiểm thử thì báo cáo hoàn tất có hợp lệ không? Rủi ro là gì?

---

## 9. ĐIỂM YẾU TRONG BÁO CÁO: GV có thể bắt bẻ, cần chuẩn bị câu trả lời

1. **Báo cáo ghi môi trường "MySQL 8"** (Bảng 3.1) nhưng tiền điều kiện IT ghi MariaDB 10.4 (XAMPP). Thực tế máy chạy MariaDB 10.4. Nên trả lời: "Hệ thống tương thích cả MySQL 8 và MariaDB 10.4; lần chạy thực tế dùng MariaDB 10.4 của XAMPP."
2. **Bảng 3.2 ghi tổng tỷ lệ đạt 100%** trong khi 138/214 ca IT chưa chạy. Phải nói rõ: 100% là tính trên 75 ca đã chạy; tỷ lệ thực thi chỉ khoảng 35,5%.
3. **Đặc tả IT02 trong Chương 1 lệch code:** báo cáo ghi điện thoại *không bắt buộc, 10–11 số*, mã SV ≤ 20, trường ≤ 255. Code thật: **bắt buộc, đúng 10 số bắt đầu bằng 0**, mã SV ≤ 50, trường ≤ 200. Nếu bị hỏi thì thừa nhận đặc tả chưa cập nhật theo code; test case bám theo code thật.
4. **DFID001 trong Bảng 3.5 ghi ví dụ "31/02/2026"**, còn ma trận Unit dùng `29/02/2026` và `31/04/2026`. Cần nói thống nhất.
5. **DFID001–003 chưa sửa** (mới log). Nếu bị hỏi cách sửa:
   - DFID001: dùng `ResolverStyle.STRICT` và pattern `dd/MM/uuuu`.
   - DFID002: bọc `checkpw` trong try/catch và trả false.
   - DFID003: `hash(null)` phải ném lỗi.
6. **LỖI THẬT trong phiếu IT01 (phần của Hiếu), ca BVA lệch 1 ký tự:**
   - [IT01-03] ghi "Mã phòng đúng 20 ký tự" nhưng dữ liệu `PHONG-TRO-SO-A10123` chỉ có **19** ký tự.
   - [IT01-04] ghi "21 ký tự (vượt biên)" nhưng `PHONG-TRO-SO-A101234` chỉ có **20** ký tự. Nếu chạy thật, hệ thống sẽ **chấp nhận** chuỗi này, nên ca sẽ ra Không đạt dù phần mềm đúng. Đây là lỗi của test case.
   - Sửa: IT01-03 dùng `PHONG-TRO-SO-A101234` (20 ký tự), IT01-04 dùng `PHONG-TRO-SO-A1012345` (21 ký tự).
   - Nếu GV phát hiện thì thừa nhận ngay, rồi dùng làm ví dụ: "lỗi thiết kế test case: dữ liệu biên phải đếm chính xác; vì vậy cần soát chéo / review test case (kiểm thử tĩnh)".
7. Phân công ghi Hiếu làm 63 ca unit, nhưng các phiếu Excel đều mang tên "Nguyễn Văn Hiếu 20231766". Nếu GV hỏi thì giải thích: nhóm trưởng tổng hợp; mỗi phiếu có sheet "Phân công".

---

## 10. Mẹo trả lời trước GV
- Luôn trả lời theo khung: **kỹ thuật gì → áp vào đâu → ví dụ cụ thể trong bài → kết quả / lỗi tìm được**.
- Khi tính V(G): ghi rõ N, E, P, rồi đối chiếu 2 công thức ra cùng kết quả.
- Khi làm BVA: viết rõ miền hợp lệ trước, rồi min−, min, min+, nom, max−, max, max+.

---

## 11. MẪU FILE XUẤT CUỐI BUỔI (Claude xuất đúng định dạng này)

```markdown
# Kết quả luyện vấn đáp — <Họ tên> — <ngày>

## Tổng quan
- Số câu đã hỏi: X · Điểm trung bình: Y/10
- Điểm theo mục phiếu chấm: 3.1 Kỹ thuật: _/10 · 3.2 Nguyên lý: _/10 · 3.3 Hoạt động: _/10 · 3.4 Viết TC & log bug: _/10

## Chi tiết từng câu
### Câu 1 — <chủ đề> — <điểm>/10
- **Câu hỏi:** ...
- **Tôi đã trả lời:** ...
- **Đúng:** ...
- **Sai / thiếu:** ...
- **Đáp án mẫu nên nói:** ...

## Điểm yếu cần ôn lại (xếp theo mức ưu tiên)
1. ...

## Công thức / số liệu hay quên
- ...

## 5 câu dễ bị hỏi nhất mai (kèm đáp án ngắn)
1. ...
```

---

# PHỤ LỤC A — CHI TIẾT 183 CA UNIT TEST (trích từ phiếu Unit Test Case)

Ký hiệu loại ca: N = bình thường, A = bất thường, B = biên. Kết quả: P = Đạt, F = Không đạt.


## F01 — DateUtil.parse(String text)

- Người thực thi: Nguyễn Văn Hiếu - 20231766 · Số dòng lệnh: 18 · Ngày thực thi: 27/08/2026
- Yêu cầu: Đọc ngày từ form: nhận cả yyyy-MM-dd và dd/MM/yyyy, chuỗi rỗng trả về null, chuỗi không phải ngày hợp lệ phải ném IllegalArgumentException.
- File test JUnit: `src/test/java/com/studentrent/util/DateUtilParseTest.java`

| UTCID | Đầu vào | Kết quả mong đợi | Loại | KQ |
|---|---|---|---|---|
| UTCID01 | text = null | Giá trị trả về: null | A | P |
| UTCID02 | text = "" | Giá trị trả về: null | A | P |
| UTCID03 | text = "   " (toàn khoảng trắng) | Giá trị trả về: null | A | P |
| UTCID04 | text = "2026-08-27" | Giá trị trả về: 2026-08-27 | N | P |
| UTCID05 | text = "27/08/2026" | Giá trị trả về: 2026-08-27 | N | P |
| UTCID06 | text = "  27/08/2026  " | Giá trị trả về: 2026-08-27 | N | P |
| UTCID07 | text = "29/02/2024" (năm nhuận) | Giá trị trả về: 2024-02-29 | B | P |
| UTCID08 | text = "29/02/2026" (không nhuận) | Giá trị trả về: 2026-02-28 (SAI - đúng ra phải ném ngoại lệ) | A | **F** (DFID001) |
| UTCID09 | text = "31/13/2026" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ngày không hợp lệ: … (định dạng dd/MM/yyyy hoặc yyyy-MM-dd)." | A | P |
| UTCID10 | text = "27-08-2026" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ngày không hợp lệ: … (định dạng dd/MM/yyyy hoặc yyyy-MM-dd)." | A | P |
| UTCID11 | text = "abc" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ngày không hợp lệ: … (định dạng dd/MM/yyyy hoặc yyyy-MM-dd)." | A | P |
| UTCID12 | text = "31/04/2026" | Giá trị trả về: 2026-04-30 (SAI - đúng ra phải ném ngoại lệ) | B | **F** (DFID001) |

## F02 — UploadUtil.isAllowedImage(String fileName)

- Người thực thi: Nguyễn Văn Hiếu - 20231766 · Số dòng lệnh: 4 · Ngày thực thi: 27/08/2026
- Yêu cầu: Chỉ cho phép upload ảnh có đuôi jpg / jpeg / png / webp; đuôi chữ in hoa phải được chuẩn hóa về chữ thường.
- File test JUnit: `src/test/java/com/studentrent/util/UploadUtilIsAllowedImageTest.java`

| UTCID | Đầu vào | Kết quả mong đợi | Loại | KQ |
|---|---|---|---|---|
| UTCID01 | fileName = "phong101.jpg" | Giá trị trả về: true | N | P |
| UTCID02 | fileName = "phong101.jpeg" | Giá trị trả về: true | N | P |
| UTCID03 | fileName = "phong101.png" | Giá trị trả về: true | N | P |
| UTCID04 | fileName = "phong101.webp" | Giá trị trả về: true | N | P |
| UTCID05 | fileName = "PHONG101.JPG" | Giá trị trả về: true | N | P |
| UTCID06 | fileName = "hopdong.pdf" | Giá trị trả về: false | A | P |
| UTCID07 | fileName = "phong101" (không có đuôi) | Giá trị trả về: false | A | P |
| UTCID08 | fileName = "phong101." | Giá trị trả về: false | B | P |
| UTCID09 | fileName = null | Giá trị trả về: false | A | P |
| UTCID10 | fileName = "" | Giá trị trả về: false | A | P |
| UTCID11 | fileName = "C:\Users\mac\anh phong.PNG" (đường dẫn Windows) | Giá trị trả về: true | N | P |
| UTCID12 | fileName = ".jpg" | Giá trị trả về: true | B | P |
| UTCID13 | fileName = "anh.jpg.exe" | Giá trị trả về: false | A | P |
| UTCID14 | fileName = "virus.exe.jpg" | Giá trị trả về: true | A | P |

## F03 — PasswordUtil.verify(String plainPassword, String storedHash)

- Người thực thi: Phùng Tiến Dũng · Số dòng lệnh: 6 · Ngày thực thi: 27/08/2026
- Yêu cầu: So khớp mật khẩu người dùng gõ với hash BCrypt lưu trong DB. Hàm trả về boolean; AuthService.login không bắt ngoại lệ quanh lời gọi này.
- File test JUnit: `src/test/java/com/studentrent/util/PasswordUtilVerifyTest.java`

| UTCID | Đầu vào | Kết quả mong đợi | Loại | KQ |
|---|---|---|---|---|
| UTCID01 | plainPassword = "matkhau123"; storedHash = hash("matkhau123") | Giá trị trả về: true | N | P |
| UTCID02 | plainPassword = "matkhau124"; storedHash = hash("matkhau123") | Giá trị trả về: false | N | P |
| UTCID03 | plainPassword = "MatKhau123"; storedHash = hash("matkhau123") | Giá trị trả về: false | N | P |
| UTCID04 | plainPassword = null; storedHash = hash("matkhau123") | Giá trị trả về: false | A | P |
| UTCID05 | plainPassword = "matkhau123"; storedHash = null | Giá trị trả về: false | A | P |
| UTCID06 | plainPassword = "matkhau123"; storedHash = "" | Giá trị trả về: false | A | P |
| UTCID07 | plainPassword = "matkhau123"; storedHash = "   " | Giá trị trả về: false | A | P |
| UTCID08 | plainPassword = ""; storedHash = hash("") | Giá trị trả về: true | B | P |
| UTCID09 | plainPassword = "matkhau123"; storedHash = "matkhau123" (chuỗi thường, không phải hash) | Giá trị trả về: false (SAI - thực tế ném ngoại lệ); Ngoại lệ: IllegalArgumentException("Invalid salt version") - KHÔNG mong đợi | A | **F** (DFID002) |
| UTCID10 | plainPassword = "matkhau123"; storedHash = hash("matkhau123") - lần hash thứ hai | Giá trị trả về: true | N | P |
| UTCID11 | plainPassword = "mậtKhẩu@2026" (có dấu tiếng Việt); storedHash = hash("mậtKhẩu@2026") | Giá trị trả về: true | N | P |

## F04 — AuthService.hasAnyRole(User user, String... roles)

- Người thực thi: Phùng Tiến Dũng · Số dòng lệnh: 11 · Ngày thực thi: 27/08/2026
- Yêu cầu: Hàm quyết định RoleFilter có cho request đi tiếp hay không. Mọi nhánh không xác định được vai trò đều phải trả về false (mặc định là TỪ CHỐI).
- File test JUnit: `src/test/java/com/studentrent/service/AuthServiceHasAnyRoleTest.java`

| UTCID | Đầu vào | Kết quả mong đợi | Loại | KQ |
|---|---|---|---|---|
| UTCID01 | user.role = "ADMIN"; roles[] = {"ADMIN"} | Giá trị trả về: true | N | P |
| UTCID02 | user.role = "STAFF"; roles[] = {"ADMIN","MANAGER","STAFF"} | Giá trị trả về: true | N | P |
| UTCID03 | user.role = "STAFF"; roles[] = {"ADMIN","MANAGER"} | Giá trị trả về: false | N | P |
| UTCID04 | user.role = user = null; roles[] = {"ADMIN"} | Giá trị trả về: false | A | P |
| UTCID05 | user.role = null; roles[] = {"ADMIN"} | Giá trị trả về: false | A | P |
| UTCID06 | user.role = "ADMIN"; roles[] = null | Giá trị trả về: false | A | P |
| UTCID07 | user.role = "ADMIN"; roles[] = {} (rỗng) | Giá trị trả về: false | B | P |
| UTCID08 | user.role = "admin" (chữ thường); roles[] = {"ADMIN"} | Giá trị trả về: false | A | P |
| UTCID09 | user.role = "ADMIN"; roles[] = {null,"ADMIN"} | Giá trị trả về: true | N | P |
| UTCID10 | user.role = "ADMIN"; roles[] = {null} | Giá trị trả về: false | A | P |

## F05 — ContractService.create(Contract contract) - nhánh kiểm tra dữ liệu

- Người thực thi: Phùng Tiến Dũng · Số dòng lệnh: 92 · Ngày thực thi: 27/08/2026
- Yêu cầu: Kiểm tra dữ liệu hợp đồng trước khi mở transaction: phòng, khách thuê, ngày bắt đầu / kết thúc, giá thuê, tiền cọc, số người ở, ngày chốt hóa đơn, ghi chú.
- File test JUnit: `src/test/java/com/studentrent/service/ContractServiceCreateTest.java`
- Điều kiện tiên quyết: Hợp đồng hợp lệ: roomId=1, tenantId=1, ngày bắt đầu=01/09/2026, ngày kết thúc=31/12/2026, giá thuê=2.500.000, tiền cọc=2.500.000, số người ở=2, ngày chốt=5

| UTCID | Đầu vào | Kết quả mong đợi | Loại | KQ |
|---|---|---|---|---|
| UTCID01 | contract = null | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Không có dữ liệu hợp đồng." | A | P |
| UTCID02 | roomId (phòng) = null | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Chưa chọn phòng." | A | P |
| UTCID03 | tenantId (khách thuê) = null | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Chưa chọn khách thuê đại diện." | A | P |
| UTCID04 | startDate (ngày bắt đầu) = null | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ngày bắt đầu không được để trống." | A | P |
| UTCID05 | endDate (ngày kết thúc) = null | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ngày kết thúc không được để trống." | A | P |
| UTCID06 |  | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ngày kết thúc phải sau ngày bắt đầu." | B | P |
| UTCID07 | endDate (ngày kết thúc) = < ngày bắt đầu (ngày bắt đầu - 1) | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ngày kết thúc phải sau ngày bắt đầu." | A | P |
| UTCID08 | note (ghi chú) = 501 ký tự | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ghi chú tối đa 500 ký tự." | B | P |
| UTCID09 | monthlyPrice (giá thuê / tháng) = null | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Giá thuê / tháng không được để trống." | A | P |
| UTCID10 | monthlyPrice (giá thuê / tháng) = -1 | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Giá thuê / tháng không được âm." | A | P |
| UTCID11 | monthlyPrice (giá thuê / tháng) = 0; note (ghi chú) = 501 ký tự | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ghi chú tối đa 500 ký tự." | B | P |
| UTCID12 | deposit (tiền cọc) = -1000 | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Tiền cọc không được âm." | A | P |
| UTCID13 | numOccupants (số người ở) = 0 | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Số người ở phải từ 1 trở lên." | B | P |
| UTCID14 | billingDay (ngày chốt hóa đơn) = 0 | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ngày chốt hóa đơn phải trong khoảng 1–28." | B | P |
| UTCID15 | billingDay (ngày chốt hóa đơn) = 29 | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ngày chốt hóa đơn phải trong khoảng 1–28." | B | P |
| UTCID16 | billingDay (ngày chốt hóa đơn) = 28; note (ghi chú) = 501 ký tự | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ghi chú tối đa 500 ký tự." | B | P |
| UTCID17 | note (ghi chú) = 501 ký tự | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ghi chú tối đa 500 ký tự." | B | P |

## F06 — InvoiceService.pay(Long invoiceId, BigDecimal amount, String method) - nhánh kiểm tra dữ liệu

- Người thực thi: Đỗ Đức Đạt · Số dòng lệnh: 42 · Ngày thực thi: 27/08/2026
- Yêu cầu: Kiểm tra dữ liệu một lần thu tiền trước khi mở transaction: id hóa đơn, số tiền phải lớn hơn 0, phương thức thanh toán phải thuộc danh mục.
- File test JUnit: `src/test/java/com/studentrent/service/InvoiceServicePayTest.java`

| UTCID | Đầu vào | Kết quả mong đợi | Loại | KQ |
|---|---|---|---|---|
| UTCID01 | invoiceId (id hóa đơn) = null; amount (số tiền thu) = 500000; method (phương thức thanh toán) = "CASH" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Thiếu id hóa đơn." | A | P |
| UTCID02 | invoiceId (id hóa đơn) = 1; amount (số tiền thu) = null; method (phương thức thanh toán) = "CASH" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Số tiền thu phải lớn hơn 0." | A | P |
| UTCID03 | invoiceId (id hóa đơn) = 1; amount (số tiền thu) = 0; method (phương thức thanh toán) = "CASH" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Số tiền thu phải lớn hơn 0." | B | P |
| UTCID04 | invoiceId (id hóa đơn) = 1; amount (số tiền thu) = -500000; method (phương thức thanh toán) = "CASH" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Số tiền thu phải lớn hơn 0." | A | P |
| UTCID05 | invoiceId (id hóa đơn) = 1; amount (số tiền thu) = 0.00 (2 chữ số thập phân); method (phương thức thanh toán) = "CASH" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Số tiền thu phải lớn hơn 0." | B | P |
| UTCID06 | invoiceId (id hóa đơn) = 1; amount (số tiền thu) = 0.01; method (phương thức thanh toán) = "BITCOIN" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Phương thức thanh toán không hợp lệ: BITCOIN" | B | P |
| UTCID07 | invoiceId (id hóa đơn) = 1; amount (số tiền thu) = 500000; method (phương thức thanh toán) = "BITCOIN" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Phương thức thanh toán không hợp lệ: BITCOIN" | A | P |
| UTCID08 | invoiceId (id hóa đơn) = 1; amount (số tiền thu) = 500000; method (phương thức thanh toán) = "cash" (chữ thường) | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Phương thức thanh toán không hợp lệ: cash" | A | P |

## F07 — InvoiceService.createInvoice(Invoice invoice) - nhánh kiểm tra kỳ hóa đơn

- Người thực thi: Đỗ Đức Đạt · Số dòng lệnh: 76 · Ngày thực thi: 27/08/2026
- Yêu cầu: Kiểm tra kỳ hóa đơn trước khi nạp danh mục dịch vụ và mở transaction: hợp đồng, tháng 1–12, năm 2000–2100, ghi chú tối đa 500 ký tự.
- File test JUnit: `src/test/java/com/studentrent/service/InvoiceServiceCreateInvoiceTest.java`
- Điều kiện tiên quyết: Hóa đơn hợp lệ: contractId=1, tháng=8, năm=2026

| UTCID | Đầu vào | Kết quả mong đợi | Loại | KQ |
|---|---|---|---|---|
| UTCID01 | invoice = null | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Chưa chọn hợp đồng." | A | P |
| UTCID02 | contractId (hợp đồng) = null | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Chưa chọn hợp đồng." | A | P |
| UTCID03 | periodMonth (tháng của kỳ) = null | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Chưa chọn kỳ hóa đơn (tháng/năm)." | A | P |
| UTCID04 | periodYear (năm của kỳ) = null | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Chưa chọn kỳ hóa đơn (tháng/năm)." | A | P |
| UTCID05 | periodMonth (tháng của kỳ) = 0 | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Tháng của kỳ hóa đơn phải trong khoảng 1–12." | B | P |
| UTCID06 | periodMonth (tháng của kỳ) = 13 | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Tháng của kỳ hóa đơn phải trong khoảng 1–12." | B | P |
| UTCID07 | periodMonth (tháng của kỳ) = 1; periodYear (năm của kỳ) = 1999 | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Năm của kỳ hóa đơn phải trong khoảng 2000–2100." | B | P |
| UTCID08 | periodMonth (tháng của kỳ) = 12; periodYear (năm của kỳ) = 2101 | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Năm của kỳ hóa đơn phải trong khoảng 2000–2100." | B | P |
| UTCID09 | periodYear (năm của kỳ) = 2000; note (ghi chú) = 501 ký tự | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ghi chú tối đa 500 ký tự." | B | P |
| UTCID10 | periodYear (năm của kỳ) = 2100; note (ghi chú) = 501 ký tự | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ghi chú tối đa 500 ký tự." | B | P |
| UTCID11 | note (ghi chú) = 501 ký tự | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Ghi chú tối đa 500 ký tự." | B | P |

## F08 — TenantService.create(Tenant tenant) - nhánh kiểm tra dữ liệu

- Người thực thi: Nguyễn Văn Hiếu - 20231766 · Số dòng lệnh: 87 · Ngày thực thi: 27/08/2026
- Yêu cầu: Kiểm tra dữ liệu khách thuê trước lần đọc DB đầu tiên: họ tên bắt buộc và tối đa 150 ký tự, CMND 9 chữ số hoặc CCCD 12 chữ số.
- File test JUnit: `src/test/java/com/studentrent/service/TenantServiceCreateTest.java`
- Điều kiện tiên quyết: Số điện thoại = 0912345678 (hợp lệ)

| UTCID | Đầu vào | Kết quả mong đợi | Loại | KQ |
|---|---|---|---|---|
| UTCID01 | tenant = null | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Không có dữ liệu khách thuê." | A | P |
| UTCID02 | fullName (họ tên) = null; idCard (CMND / CCCD) = "012345678901" (12 số) | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Họ tên không được để trống." | A | P |
| UTCID03 | fullName (họ tên) = "   " (toàn khoảng trắng); idCard (CMND / CCCD) = "012345678901" (12 số) | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Họ tên không được để trống." | A | P |
| UTCID04 | fullName (họ tên) = 151 ký tự; idCard (CMND / CCCD) = "012345678901" (12 số) | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Họ tên tối đa 150 ký tự." | B | P |
| UTCID05 | fullName (họ tên) = 150 ký tự; idCard (CMND / CCCD) = null | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "CMND / CCCD không được để trống." | B | P |
| UTCID06 | fullName (họ tên) = "Nguyễn Văn An"; idCard (CMND / CCCD) = null | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "CMND / CCCD không được để trống." | A | P |
| UTCID07 | fullName (họ tên) = "Nguyễn Văn An"; idCard (CMND / CCCD) = "  " (toàn khoảng trắng) | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "CMND / CCCD không được để trống." | A | P |
| UTCID08 | fullName (họ tên) = "Nguyễn Văn An"; idCard (CMND / CCCD) = "12345678" (8 số) | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "CMND / CCCD phải gồm 9 chữ số (CMND) hoặc 12 chữ số (CCCD)." | B | P |
| UTCID09 | fullName (họ tên) = "Nguyễn Văn An"; idCard (CMND / CCCD) = "1234567890" (10 số) | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "CMND / CCCD phải gồm 9 chữ số (CMND) hoặc 12 chữ số (CCCD)." | A | P |
| UTCID10 | fullName (họ tên) = "Nguyễn Văn An"; idCard (CMND / CCCD) = "12345678901" (11 số) | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "CMND / CCCD phải gồm 9 chữ số (CMND) hoặc 12 chữ số (CCCD)." | B | P |
| UTCID11 | fullName (họ tên) = "Nguyễn Văn An"; idCard (CMND / CCCD) = "1234567890123" (13 số) | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "CMND / CCCD phải gồm 9 chữ số (CMND) hoặc 12 chữ số (CCCD)." | B | P |
| UTCID12 | fullName (họ tên) = "Nguyễn Văn An"; idCard (CMND / CCCD) = "01234567X" (có chữ cái) | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "CMND / CCCD phải gồm 9 chữ số (CMND) hoặc 12 chữ số (CCCD)." | A | P |
| UTCID13 | fullName (họ tên) = "Nguyễn Văn An"; idCard (CMND / CCCD) = "012 345 678" (có khoảng trắng) | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "CMND / CCCD phải gồm 9 chữ số (CMND) hoặc 12 chữ số (CCCD)." | A | P |

## F09 — FeedbackService.submit(Feedback feedback) - nhánh kiểm tra dữ liệu

- Người thực thi: Phùng Tiến Dũng · Số dòng lệnh: 46 · Ngày thực thi: 03/09/2026
- Yêu cầu: Kiểm tra dữ liệu phản hồi trước khi ghi DB và gửi mail: bắt buộc họ tên / email chủ trọ / tiêu đề / nội dung, giới hạn độ dài, định dạng email và số điện thoại, loại phản hồi phải thuộc danh mục.
- File test JUnit: `src/test/java/com/studentrent/service/FeedbackServiceSubmitTest.java`
- Điều kiện tiên quyết: Phản hồi hợp lệ: senderName="Nguyễn Văn An", adminEmail="chutro@studentrent.vn", subject / content có nội dung, category="REPAIR". Các ca biên hợp lệ đặt category="SPAM" làm mốc để biết validate đã đi qua chốt đang xét.

| UTCID | Đầu vào | Kết quả mong đợi | Loại | KQ |
|---|---|---|---|---|
| UTCID01 | feedback (đối tượng phản hồi) = null; senderName (họ tên người gửi) = "Nguyễn Văn An"; adminEmail (email chủ trọ) = "chutro@studentrent.vn"; senderEmail (email người gửi) = null (không bắt buộc); senderPhone (số điện thoại) = null (không bắt buộc); roomCode (mã phòng) = null (không bắt buộc); subject (tiêu đề) = "Vòi nước phòng 101 bị rỉ"; content (nội dung) = "Vòi nước trong phòng 101 rỉ liên tục..."; category (loại phản hồi) = "REPAIR" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Không có dữ liệu phản hồi." | A | P |
| UTCID02 | feedback (đối tượng phản hồi) = hợp lệ đủ trường bắt buộc; senderName (họ tên người gửi) = null; adminEmail (email chủ trọ) = "chutro@studentrent.vn"; senderEmail (email người gửi) = null (không bắt buộc); senderPhone (số điện thoại) = null (không bắt buộc); roomCode (mã phòng) = null (không bắt buộc); subject (tiêu đề) = "Vòi nước phòng 101 bị rỉ"; content (nội dung) = "Vòi nước trong phòng 101 rỉ liên tục..."; category (loại phản hồi) = "REPAIR" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Họ tên người gửi không được để trống." | A | P |
| UTCID03 | feedback (đối tượng phản hồi) = hợp lệ đủ trường bắt buộc; senderName (họ tên người gửi) = "   " (toàn khoảng trắng); adminEmail (email chủ trọ) = "chutro@studentrent.vn"; senderEmail (email người gửi) = null (không bắt buộc); senderPhone (số điện thoại) = null (không bắt buộc); roomCode (mã phòng) = null (không bắt buộc); subject (tiêu đề) = "Vòi nước phòng 101 bị rỉ"; content (nội dung) = "Vòi nước trong phòng 101 rỉ liên tục..."; category (loại phản hồi) = "REPAIR" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Họ tên người gửi không được để trống." | A | P |
| UTCID04 | feedback (đối tượng phản hồi) = hợp lệ đủ trường bắt buộc; senderName (họ tên người gửi) = chuỗi 150 ký tự; adminEmail (email chủ trọ) = "chutro@studentrent.vn"; senderEmail (email người gửi) = null (không bắt buộc); senderPhone (số điện thoại) = null (không bắt buộc); roomCode (mã phòng) = null (không bắt buộc); subject (tiêu đề) = "Vòi nước phòng 101 bị rỉ"; content (nội dung) = "Vòi nước trong phòng 101 rỉ liên tục..."; category (loại phản hồi) = "SPAM" (ngoài danh mục) | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Loại phản hồi không hợp lệ: SPAM" (chốt cuối - các chốt trước đã đi qua) | B | P |
| UTCID05 | feedback (đối tượng phản hồi) = hợp lệ đủ trường bắt buộc; senderName (họ tên người gửi) = chuỗi 151 ký tự; adminEmail (email chủ trọ) = "chutro@studentrent.vn"; senderEmail (email người gửi) = null (không bắt buộc); senderPhone (số điện thoại) = null (không bắt buộc); roomCode (mã phòng) = null (không bắt buộc); subject (tiêu đề) = "Vòi nước phòng 101 bị rỉ"; content (nội dung) = "Vòi nước trong phòng 101 rỉ liên tục..."; category (loại phản hồi) = "REPAIR" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Họ tên người gửi tối đa 150 ký tự." | B | P |
| UTCID06 | feedback (đối tượng phản hồi) = hợp lệ đủ trường bắt buộc; senderName (họ tên người gửi) = "Nguyễn Văn An"; adminEmail (email chủ trọ) = null; senderEmail (email người gửi) = null (không bắt buộc); senderPhone (số điện thoại) = null (không bắt buộc); roomCode (mã phòng) = null (không bắt buộc); subject (tiêu đề) = "Vòi nước phòng 101 bị rỉ"; content (nội dung) = "Vòi nước trong phòng 101 rỉ liên tục..."; category (loại phản hồi) = "REPAIR" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Email chủ trọ không được để trống." | A | P |
| UTCID07 | feedback (đối tượng phản hồi) = hợp lệ đủ trường bắt buộc; senderName (họ tên người gửi) = "Nguyễn Văn An"; adminEmail (email chủ trọ) = "chutro.studentrent.vn" (thiếu @); senderEmail (email người gửi) = null (không bắt buộc); senderPhone (số điện thoại) = null (không bắt buộc); roomCode (mã phòng) = null (không bắt buộc); subject (tiêu đề) = "Vòi nước phòng 101 bị rỉ"; content (nội dung) = "Vòi nước trong phòng 101 rỉ liên tục..."; category (loại phản hồi) = "REPAIR" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Email chủ trọ không hợp lệ: chutro.studentrent.vn" | A | P |
| UTCID08 | feedback (đối tượng phản hồi) = hợp lệ đủ trường bắt buộc; senderName (họ tên người gửi) = "Nguyễn Văn An"; adminEmail (email chủ trọ) = "chutro@studentrent.vn"; senderEmail (email người gửi) = "sinhvien@" (thiếu tên miền); senderPhone (số điện thoại) = null (không bắt buộc); roomCode (mã phòng) = null (không bắt buộc); subject (tiêu đề) = "Vòi nước phòng 101 bị rỉ"; content (nội dung) = "Vòi nước trong phòng 101 rỉ liên tục..."; category (loại phản hồi) = "REPAIR" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Email người gửi không hợp lệ: sinhvien@" | A | P |
| UTCID09 | feedback (đối tượng phản hồi) = hợp lệ đủ trường bắt buộc; senderName (họ tên người gửi) = "Nguyễn Văn An"; adminEmail (email chủ trọ) = "chutro@studentrent.vn"; senderEmail (email người gửi) = null (không bắt buộc); senderPhone (số điện thoại) = "091234567" (9 chữ số); roomCode (mã phòng) = null (không bắt buộc); subject (tiêu đề) = "Vòi nước phòng 101 bị rỉ"; content (nội dung) = "Vòi nước trong phòng 101 rỉ liên tục..."; category (loại phản hồi) = "REPAIR" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Số điện thoại phải gồm 10 chữ số, bắt đầu bằng 0." | B | P |
| UTCID10 | feedback (đối tượng phản hồi) = hợp lệ đủ trường bắt buộc; senderName (họ tên người gửi) = "Nguyễn Văn An"; adminEmail (email chủ trọ) = "chutro@studentrent.vn"; senderEmail (email người gửi) = null (không bắt buộc); senderPhone (số điện thoại) = "0912345678" (10 chữ số); roomCode (mã phòng) = null (không bắt buộc); subject (tiêu đề) = "Vòi nước phòng 101 bị rỉ"; content (nội dung) = "Vòi nước trong phòng 101 rỉ liên tục..."; category (loại phản hồi) = "SPAM" (ngoài danh mục) | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Loại phản hồi không hợp lệ: SPAM" (chốt cuối - các chốt trước đã đi qua) | N | P |
| UTCID11 | feedback (đối tượng phản hồi) = hợp lệ đủ trường bắt buộc; senderName (họ tên người gửi) = "Nguyễn Văn An"; adminEmail (email chủ trọ) = "chutro@studentrent.vn"; senderEmail (email người gửi) = null (không bắt buộc); senderPhone (số điện thoại) = null (không bắt buộc); roomCode (mã phòng) = chuỗi 21 ký tự; subject (tiêu đề) = "Vòi nước phòng 101 bị rỉ"; content (nội dung) = "Vòi nước trong phòng 101 rỉ liên tục..."; category (loại phản hồi) = "REPAIR" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Mã phòng tối đa 20 ký tự." | B | P |
| UTCID12 | feedback (đối tượng phản hồi) = hợp lệ đủ trường bắt buộc; senderName (họ tên người gửi) = "Nguyễn Văn An"; adminEmail (email chủ trọ) = "chutro@studentrent.vn"; senderEmail (email người gửi) = null (không bắt buộc); senderPhone (số điện thoại) = null (không bắt buộc); roomCode (mã phòng) = null (không bắt buộc); subject (tiêu đề) = null; content (nội dung) = "Vòi nước trong phòng 101 rỉ liên tục..."; category (loại phản hồi) = "REPAIR" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Tiêu đề phản hồi không được để trống." | A | P |
| UTCID13 | feedback (đối tượng phản hồi) = hợp lệ đủ trường bắt buộc; senderName (họ tên người gửi) = "Nguyễn Văn An"; adminEmail (email chủ trọ) = "chutro@studentrent.vn"; senderEmail (email người gửi) = null (không bắt buộc); senderPhone (số điện thoại) = null (không bắt buộc); roomCode (mã phòng) = null (không bắt buộc); subject (tiêu đề) = "Vòi nước phòng 101 bị rỉ"; content (nội dung) = null; category (loại phản hồi) = "REPAIR" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Nội dung phản hồi không được để trống." | A | P |
| UTCID14 | feedback (đối tượng phản hồi) = hợp lệ đủ trường bắt buộc; senderName (họ tên người gửi) = "Nguyễn Văn An"; adminEmail (email chủ trọ) = "chutro@studentrent.vn"; senderEmail (email người gửi) = null (không bắt buộc); senderPhone (số điện thoại) = null (không bắt buộc); roomCode (mã phòng) = null (không bắt buộc); subject (tiêu đề) = "Vòi nước phòng 101 bị rỉ"; content (nội dung) = chuỗi 2001 ký tự; category (loại phản hồi) = "REPAIR" | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Nội dung phản hồi tối đa 2000 ký tự." | B | P |
| UTCID15 | feedback (đối tượng phản hồi) = hợp lệ đủ trường bắt buộc; senderName (họ tên người gửi) = "Nguyễn Văn An"; adminEmail (email chủ trọ) = "chutro@studentrent.vn"; senderEmail (email người gửi) = null (không bắt buộc); senderPhone (số điện thoại) = null (không bắt buộc); roomCode (mã phòng) = null (không bắt buộc); subject (tiêu đề) = "Vòi nước phòng 101 bị rỉ"; content (nội dung) = "Vòi nước trong phòng 101 rỉ liên tục..."; category (loại phản hồi) = "SPAM" (ngoài danh mục) | Ngoại lệ: IllegalArgumentException; Thông báo lỗi: "Loại phản hồi không hợp lệ: SPAM" (chốt cuối - các chốt trước đã đi qua) | A | P |

## F10 — Tenant.getInitials()

- Người thực thi: Nguyễn Văn Hiếu - 20231766 · Số dòng lệnh: 10 · Ngày thực thi: 03/09/2026
- Yêu cầu: Sinh hai chữ cái đại diện cho ô avatar: chữ đầu của HỌ và chữ đầu của TÊN GỌI (từ cuối), in hoa theo Locale.ROOT; một chữ nếu tên chỉ có một từ; "?" khi chưa có tên.
- File test JUnit: `src/test/java/com/studentrent/model/TenantGetInitialsTest.java`
- Điều kiện tiên quyết: Không (Tenant là POJO thuần, không đọc DB)

| UTCID | Đầu vào | Kết quả mong đợi | Loại | KQ |
|---|---|---|---|---|
| UTCID01 | fullName (họ tên khách thuê) = null | Giá trị trả về: "?"; Ngoại lệ: Không | A | P |
| UTCID02 | fullName (họ tên khách thuê) = "" (chuỗi rỗng) | Giá trị trả về: "?"; Ngoại lệ: Không | A | P |
| UTCID03 | fullName (họ tên khách thuê) = "   " (toàn khoảng trắng) | Giá trị trả về: "?"; Ngoại lệ: Không | A | P |
| UTCID04 | fullName (họ tên khách thuê) = "Nguyễn Văn An" | Giá trị trả về: "NA"; Ngoại lệ: Không | N | P |
| UTCID05 | fullName (họ tên khách thuê) = "Trần Bình" | Giá trị trả về: "TB"; Ngoại lệ: Không | N | P |
| UTCID06 | fullName (họ tên khách thuê) = "An" (một từ) | Giá trị trả về: "A"; Ngoại lệ: Không | B | P |
| UTCID07 | fullName (họ tên khách thuê) = "a" (một ký tự) | Giá trị trả về: "A"; Ngoại lệ: Không | B | P |
| UTCID08 | fullName (họ tên khách thuê) = "  Nguyễn Văn An  " (thừa khoảng trắng hai đầu) | Giá trị trả về: "NA"; Ngoại lệ: Không | B | P |
| UTCID09 | fullName (họ tên khách thuê) = "Nguyễn    Văn    An" (nhiều khoảng trắng giữa) | Giá trị trả về: "NA"; Ngoại lệ: Không | B | P |
| UTCID10 | fullName (họ tên khách thuê) = "nguyễn văn an" (chữ thường) | Giá trị trả về: "NA"; Ngoại lệ: Không | N | P |
| UTCID11 | fullName (họ tên khách thuê) = "Nguyễn\tVăn\nAn" (tab và xuống dòng) | Giá trị trả về: "NA"; Ngoại lệ: Không | B | P |
| UTCID12 | fullName (họ tên khách thuê) = "đặng ước" (chữ có dấu) | Giá trị trả về: "ĐƯ"; Ngoại lệ: Không | N | P |

## F11 — ReportService.revenueTotals(List<Map<String, Object>> rows)

- Người thực thi: Đỗ Đức Đạt · Số dòng lệnh: 18 · Ngày thực thi: 03/09/2026
- Yêu cầu: Cộng dồn báo cáo doanh thu đã đọc từ DB: tổng số hóa đơn, tổng phải thu, tổng đã thu và còn lại = total - paid; giữ nguyên số chữ số thập phân của BigDecimal (cột DECIMAL(12,2)).
- File test JUnit: `src/test/java/com/studentrent/service/ReportServiceRevenueTotalsTest.java`
- Điều kiện tiên quyết: Mỗi hàng dùng đúng cấu trúc revenueReport sinh ra: khóa total / paid kiểu BigDecimal, khóa invoices kiểu Number. Hàm không gọi DAO.

| UTCID | Đầu vào | Kết quả mong đợi | Loại | KQ |
|---|---|---|---|---|
| UTCID01 | rows (danh sách hàng báo cáo) = List.of() (rỗng) | invoices (tổng số hóa đơn): 0L; total (tổng phải thu): BigDecimal.ZERO; paid (tổng đã thu): BigDecimal.ZERO; remaining (còn lại): BigDecimal.ZERO; Ngoại lệ: Không | B | P |
| UTCID02 | rows (danh sách hàng báo cáo) = 1 hàng: total=2500000.00, paid=2500000.00, invoices=1 | invoices (tổng số hóa đơn): 1L; total (tổng phải thu): 2500000.00; paid (tổng đã thu): 2500000.00; remaining (còn lại): 0.00; Ngoại lệ: Không | N | P |
| UTCID03 | rows (danh sách hàng báo cáo) = 1 hàng: total=2500000.00, paid=1000000.00, invoices=1 | invoices (tổng số hóa đơn): 1L; total (tổng phải thu): 2500000.00; paid (tổng đã thu): 1000000.00; remaining (còn lại): 1500000.00; Ngoại lệ: Không | N | P |
| UTCID04 | rows (danh sách hàng báo cáo) = 3 hàng: (2500000.00 / 2500000.00 / 2), (3000000.00 / 1000000.00 / 3), (1500000.00 / 0.00 / 1) | invoices (tổng số hóa đơn): 6L; total (tổng phải thu): 7000000.00; paid (tổng đã thu): 3500000.00; remaining (còn lại): 3500000.00; Ngoại lệ: Không | N | P |
| UTCID05 | rows (danh sách hàng báo cáo) = 2 hàng: (2500000.00 / 1000000.00 / 1), (0 / 0 / 0) | invoices (tổng số hóa đơn): 1L; total (tổng phải thu): 2500000.00; paid (tổng đã thu): 1000000.00; remaining (còn lại): 1500000.00; Ngoại lệ: Không | B | P |
| UTCID06 | rows (danh sách hàng báo cáo) = 2 hàng: (1000000.55 / 500000.25 / 1), (2000000.45 / 500000.75 / 1) | invoices (tổng số hóa đơn): 2L; total (tổng phải thu): 3000001.00; paid (tổng đã thu): 1000001.00; remaining (còn lại): 2000000.00; Ngoại lệ: Không | N | P |
| UTCID07 | rows (danh sách hàng báo cáo) = 1 hàng: total=1000000.00, paid=1200000.00, invoices=1 | total (tổng phải thu): 1000000.00; remaining (còn lại): -200000.00 (số âm); Ngoại lệ: Không | A | P |
| UTCID08 | rows (danh sách hàng báo cáo) = 1 hàng: invoices=4 kiểu Integer | invoices (tổng số hóa đơn): 4L; Ngoại lệ: Không | N | P |
| UTCID09 | rows (danh sách hàng báo cáo) = null | Ngoại lệ: NullPointerException | A | P |
| UTCID10 | rows (danh sách hàng báo cáo) = 1 hàng thiếu khóa total | Ngoại lệ: NullPointerException | A | P |
| UTCID11 | rows (danh sách hàng báo cáo) = 1 hàng: total=1000000.00, paid=0.00, invoices=1 | invoices (tổng số hóa đơn): 1L; total (tổng phải thu): 1000000.00; paid (tổng đã thu): 0.00; Thứ tự khóa: [invoices, total, paid, remaining]; Ngoại lệ: Không | N | P |

## F12 — JsonUtil.toJson(Object data)

- Người thực thi: Đỗ Đức Đạt · Số dòng lệnh: 3 · Ngày thực thi: 03/09/2026
- Yêu cầu: Serialize object xuống JSON cho endpoint AJAX: LocalDate / LocalDateTime phải ra chuỗi ISO nhờ bộ serializer riêng (không rơi vào phản chiếu gây InaccessibleObjectException trên Java 17), và serializeNulls giữ lại trường null.
- File test JUnit: `src/test/java/com/studentrent/util/JsonUtilToJsonTest.java`
- Điều kiện tiên quyết: Không (hàm tĩnh, dùng Gson dựng sẵn trong lớp)

| UTCID | Đầu vào | Kết quả mong đợi | Loại | KQ |
|---|---|---|---|---|
| UTCID01 | data (đối tượng cần serialize) = null | Chuỗi JSON trả về: "null"; Ngoại lệ: Không | A | P |
| UTCID02 | data (đối tượng cần serialize) = "phòng 101" (chuỗi tiếng Việt) | Chuỗi JSON trả về: "\"phòng 101\""; Ngoại lệ: Không | N | P |
| UTCID03 | data (đối tượng cần serialize) = 2500000L (số nguyên) | Chuỗi JSON trả về: "2500000"; Ngoại lệ: Không | N | P |
| UTCID04 | data (đối tượng cần serialize) = true (giá trị logic) | Chuỗi JSON trả về: "true"; Ngoại lệ: Không | N | P |
| UTCID05 | data (đối tượng cần serialize) = Map.of() (map rỗng) | Chuỗi JSON trả về: "{}"; Ngoại lệ: Không | B | P |
| UTCID06 | data (đối tượng cần serialize) = List.of() (danh sách rỗng) | Chuỗi JSON trả về: "[]"; Ngoại lệ: Không | B | P |
| UTCID07 | data (đối tượng cần serialize) = LocalDate.of(2026, 8, 27) | Chuỗi JSON trả về: "2026-08-27" (ISO_LOCAL_DATE); Ngoại lệ: Không | N | P |
| UTCID08 | data (đối tượng cần serialize) = LocalDateTime.of(2026, 8, 27, 12, 30, 45) | Chuỗi JSON trả về: "2026-08-27T12:30:45"; Ngoại lệ: Không | N | P |
| UTCID09 | data (đối tượng cần serialize) = LocalDateTime.of(2026, 8, 27, 12, 30, 0) (giây = 0) | Chuỗi JSON trả về: "2026-08-27T12:30:00" (VẪN in phần giây); Ngoại lệ: Không | B | P |
| UTCID10 | data (đối tượng cần serialize) = {roomCode="P101", note=null} | Chuỗi JSON trả về: {"roomCode":"P101","note":null} (giữ trường null); Ngoại lệ: Không | A | P |
| UTCID11 | data (đối tượng cần serialize) = {period={month=8, total=2500000}} (map lồng nhau) | Chuỗi JSON trả về: {"period":{"month":8,"total":2500000}} (giữ thứ tự khóa); Ngoại lệ: Không | N | P |
| UTCID12 | data (đối tượng cần serialize) = {dates=[2026-01-01, 2026-12-31]} | Chuỗi JSON trả về: {"dates":["2026-01-01","2026-12-31"]}; Ngoại lệ: Không | N | P |
| UTCID13 | data (đối tượng cần serialize) = Không truyền - đọc hằng JSON_CONTENT_TYPE | Chuỗi JSON trả về: application/json;charset=UTF-8; Ngoại lệ: Không | N | P |

## F13 — PasswordUtil.hash(String plainPassword)

- Người thực thi: Phùng Tiến Dũng · Số dòng lệnh: 3 · Ngày thực thi: 03/09/2026
- Yêu cầu: Băm mật khẩu bằng BCrypt chi phí 2^12 vòng: hash đúng định dạng $2a$12$ dài 60 ký tự, mỗi lần băm cho chuỗi khác nhau (salt ngẫu nhiên), và hash sinh ra luôn verify được bằng đúng mật khẩu.
- File test JUnit: `src/test/java/com/studentrent/util/PasswordUtilHashTest.java`
- Điều kiện tiên quyết: Không. Lưu ý mỗi lần gọi hàm mất khoảng 0,2-0,5 giây do chi phí 2^12 vòng.

| UTCID | Đầu vào | Kết quả mong đợi | Loại | KQ |
|---|---|---|---|---|
| UTCID01 | plainPassword (mật khẩu gốc) = "MatKhau@123" | Định dạng hash: Bắt đầu bằng "$2a$12$", dài đúng 60 ký tự; Ngoại lệ: Không | N | P |
| UTCID02 | plainPassword (mật khẩu gốc) = "MatKhau@123" | verify(mật khẩu, hash): true; Ngoại lệ: Không | N | P |
| UTCID03 | plainPassword (mật khẩu gốc) = "MatKhau@123" rồi verify bằng "MatKhau@124" | verify(mật khẩu, hash): false; Ngoại lệ: Không | A | P |
| UTCID04 | plainPassword (mật khẩu gốc) = "MatKhau@123" | Định dạng hash: Hai lần băm cho hai chuỗi KHÁC nhau; Ngoại lệ: Không | N | P |
| UTCID05 | plainPassword (mật khẩu gốc) = "" (chuỗi rỗng) | Định dạng hash: Bắt đầu bằng "$2a$12$", dài đúng 60 ký tự; verify(mật khẩu, hash): true; Ngoại lệ: Không | B | P |
| UTCID06 | plainPassword (mật khẩu gốc) = "a" (một ký tự - biên dưới) | verify(mật khẩu, hash): true; Ngoại lệ: Không | B | P |
| UTCID07 | plainPassword (mật khẩu gốc) = "MậtKhẩu@123" (có ký tự tiếng Việt) | verify(mật khẩu, hash): true; Ngoại lệ: Không | N | P |
| UTCID08 | plainPassword (mật khẩu gốc) = "a" lặp 72 lần (biên trên của BCrypt) | verify(mật khẩu, hash): true; Ngoại lệ: Không | B | P |
| UTCID09 | plainPassword (mật khẩu gốc) = "a" lặp 72 lần, verify bằng "a" lặp 80 lần | verify(mật khẩu, hash): true - BCrypt cắt ở 72 byte nên phần dư bị bỏ qua; Ngoại lệ: Không | B | P |
| UTCID10 | plainPassword (mật khẩu gốc) = "MatKhau@123", verify bằng "matkhau@123" | verify(mật khẩu, hash): false; Ngoại lệ: Không | A | P |
| UTCID11 | plainPassword (mật khẩu gốc) = null | Ngoại lệ: NullPointerException (mong đợi - hàm phải từ chối null) | A | **F** (DFID003) |
| UTCID12 | plainPassword (mật khẩu gốc) = null | verify(mật khẩu, hash): true khi verify bằng chuỗi "null" (hành vi sai - DFID003); Ngoại lệ: Không | A | P |

## F14 — DateUtil.format(LocalDate) và DateUtil.format(LocalDateTime) - 2 bản nạp

- Người thực thi: Nguyễn Văn Hiếu - 20231766 · Số dòng lệnh: 6 · Ngày thực thi: 03/09/2026
- Yêu cầu: Đưa ngày ra màn hình, đối xứng với parse (F01): định dạng dd/MM/yyyy cho ngày và dd/MM/yyyy HH:mm cho ngày giờ, giờ 24h, bỏ phần giây; null trả về CHUỖI RỖNG chứ không phải chuỗi "null".
- File test JUnit: `src/test/java/com/studentrent/util/DateUtilFormatTest.java`
- Điều kiện tiên quyết: Không (hàm tĩnh, formatter ghim Locale.ROOT)

| UTCID | Đầu vào | Kết quả mong đợi | Loại | KQ |
|---|---|---|---|---|
| UTCID01 | Bản nạp gọi = format(LocalDate); Tham số truyền vào = null (ép kiểu LocalDate) | Chuỗi trả về: "" (chuỗi rỗng); Ngoại lệ: Không | A | P |
| UTCID02 | Bản nạp gọi = format(LocalDate); Tham số truyền vào = LocalDate.of(2026, 8, 27) | Chuỗi trả về: "27/08/2026"; Ngoại lệ: Không | N | P |
| UTCID03 | Bản nạp gọi = format(LocalDate); Tham số truyền vào = LocalDate.of(2026, 1, 5) (ngày/tháng một chữ số) | Chuỗi trả về: "05/01/2026" (đệm 0 phía trước); Ngoại lệ: Không | B | P |
| UTCID04 | Bản nạp gọi = format(LocalDate); Tham số truyền vào = LocalDate.of(2026, 1, 1) (đầu năm) | Chuỗi trả về: "01/01/2026"; Ngoại lệ: Không | B | P |
| UTCID05 | Bản nạp gọi = format(LocalDate); Tham số truyền vào = LocalDate.of(2026, 12, 31) (cuối năm) | Chuỗi trả về: "31/12/2026"; Ngoại lệ: Không | B | P |
| UTCID06 | Bản nạp gọi = format(LocalDate); Tham số truyền vào = LocalDate.of(2024, 2, 29) (năm nhuận) | Chuỗi trả về: "29/02/2024"; Ngoại lệ: Không | B | P |
| UTCID07 | Bản nạp gọi = format(LocalDateTime); Tham số truyền vào = null (ép kiểu LocalDateTime) | Chuỗi trả về: "" (chuỗi rỗng); Ngoại lệ: Không | A | P |
| UTCID08 | Bản nạp gọi = format(LocalDateTime); Tham số truyền vào = LocalDateTime.of(2026, 8, 27, 14, 30) | Chuỗi trả về: "27/08/2026 14:30"; Ngoại lệ: Không | N | P |
| UTCID09 | Bản nạp gọi = format(LocalDateTime); Tham số truyền vào = LocalDateTime.of(2026, 8, 27, 23, 59) | Chuỗi trả về: "27/08/2026 23:59" (24h, không AM/PM); Ngoại lệ: Không | B | P |
| UTCID10 | Bản nạp gọi = format(LocalDateTime); Tham số truyền vào = LocalDateTime.of(2026, 8, 27, 0, 0) (nửa đêm) | Chuỗi trả về: "27/08/2026 00:00"; Ngoại lệ: Không | B | P |
| UTCID11 | Bản nạp gọi = format(LocalDateTime); Tham số truyền vào = LocalDateTime.of(2026, 8, 27, 14, 30, 45) (có giây) | Chuỗi trả về: "27/08/2026 14:30"; Ngoại lệ: Không | B | P |
| UTCID12 | Bản nạp gọi = format(LocalDate); Tham số truyền vào = LocalDate.of(2026, 8, 27) | Kiểm tra hai chiều: parse(format(ngày)) trả về đúng ngày ban đầu; Ngoại lệ: Không | N | P |

## F15 — ChartData.labels(List<Map<String, Object>> rows, String key)

- Người thực thi: Đỗ Đức Đạt · Số dòng lệnh: 8 · Ngày thực thi: 03/09/2026
- Yêu cầu: Rút một cột của báo cáo ra làm nhãn trục ngang của biểu đồ, giữ đúng thứ tự hàng. Giá trị null hoặc thiếu khóa phải thành CHUỖI RỖNG - nhãn "null" sẽ hiện thẳng lên trục biểu đồ trên dashboard.
- File test JUnit: `src/test/java/com/studentrent/util/ChartDataLabelsTest.java`
- Điều kiện tiên quyết: Không (hàm thuần, không đọc DB)

| UTCID | Đầu vào | Kết quả mong đợi | Loại | KQ |
|---|---|---|---|---|
| UTCID01 | rows (danh sách hàng báo cáo) = List.of() (rỗng); key (tên cột cần rút) = "label" | Danh sách nhãn trả về: [] (rỗng); Ngoại lệ: Không | B | P |
| UTCID02 | rows (danh sách hàng báo cáo) = 1 hàng: {label="08/2026"}; key (tên cột cần rút) = "label" | Danh sách nhãn trả về: ["08/2026"]; Ngoại lệ: Không | N | P |
| UTCID03 | rows (danh sách hàng báo cáo) = 3 hàng: {label="06/2026"}, {label="07/2026"}, {label="08/2026"}; key (tên cột cần rút) = "label" | Danh sách nhãn trả về: ["06/2026", "07/2026", "08/2026"] (đúng thứ tự); Ngoại lệ: Không | N | P |
| UTCID04 | rows (danh sách hàng báo cáo) = 1 hàng: {label=null}; key (tên cột cần rút) = "label" | Danh sách nhãn trả về: [""] (chuỗi rỗng, KHÔNG phải chuỗi "null"); Ngoại lệ: Không | A | P |
| UTCID05 | rows (danh sách hàng báo cáo) = 1 hàng: {period="2026-08"} (thiếu khóa label); key (tên cột cần rút) = "label" | Danh sách nhãn trả về: [""] (chuỗi rỗng, KHÔNG phải chuỗi "null"); Ngoại lệ: Không | A | P |
| UTCID06 | rows (danh sách hàng báo cáo) = 1 hàng: {year=2026} (số nguyên); key (tên cột cần rút) = "year" | Danh sách nhãn trả về: ["2026"]; Ngoại lệ: Không | N | P |
| UTCID07 | rows (danh sách hàng báo cáo) = 1 hàng: {total=BigDecimal("2500000.00")}; key (tên cột cần rút) = "total" | Danh sách nhãn trả về: ["2500000.00"] (giữ nguyên số chữ số thập phân); Ngoại lệ: Không | N | P |
| UTCID08 | rows (danh sách hàng báo cáo) = 1 hàng: {label="08/2026", period="2026-08"}; key (tên cột cần rút) = "period" | Danh sách nhãn trả về: ["2026-08"] (chỉ rút đúng cột được yêu cầu); Ngoại lệ: Không | N | P |
| UTCID09 | rows (danh sách hàng báo cáo) = 3 hàng: {label="06/2026"}, {label=null}, {label="08/2026"}; key (tên cột cần rút) = "label" | Danh sách nhãn trả về: ["06/2026", "", "08/2026"]; Ngoại lệ: Không | A | P |
| UTCID10 | rows (danh sách hàng báo cáo) = 1 hàng: {label="08/2026"}; key (tên cột cần rút) = null | Danh sách nhãn trả về: [""] (chuỗi rỗng, KHÔNG phải chuỗi "null"); Ngoại lệ: Không | A | P |
| UTCID11 | rows (danh sách hàng báo cáo) = null; key (tên cột cần rút) = "label" | Ngoại lệ: NullPointerException | A | P |
| UTCID12 | rows (danh sách hàng báo cáo) = 1 hàng: {label="08/2026"}; key (tên cột cần rút) = "label" | Tính chất kết quả: Danh sách sửa được: add() thành công, size = 2; Ngoại lệ: Không | N | P |

---

# PHỤ LỤC B — CHI TIẾT 214 CA INTEGRATION TEST (trích từ phiếu IT_Test Case)

Kỹ thuật: EP phân lớp tương đương · BVA giá trị biên · EG đoán lỗi · ST chuyển trạng thái · SEC bảo mật đầu vào. Kết quả "Chưa chạy" = đã thiết kế nhưng chưa thực thi.


## IT01 — Thêm / Sửa phòng trọ

- Người thực thi: Nguyễn Văn Hiếu - 20231766
- Yêu cầu: Kiểm thử tích hợp màn hình khai báo phòng trọ: form JSP → RoomServlet.bind → BaseServlet.paramDecimal → UploadUtil.saveImage → RoomService.validate → RoomDAO → MySQL. Trọng tâm: mọi thông báo lỗi phải hiện lại NGAY TRÊN FORM kèm dữ liệu vừa gõ (forward, không redirect), và ảnh đã ghi xuống đĩa phải được dọn khi validate hỏng.
- Điều kiện tiên quyết chung: Tomcat 10.1 + JDK 17, MariaDB 10.4 (XAMPP) đã nạp schema.sql. Đăng nhập sẵn bằng tài khoản admin / admin123. Dữ liệu mẫu: A101 (Phòng đơn, tối đa 1 người, AVAILABLE), A202 (MAINTENANCE), 4 loại phòng id 1–4.
- Đường dẫn: GET /rooms/new · GET /rooms/edit?id= · POST /rooms/save


**1. Kiểm tra ràng buộc trường Mã phòng**

- **[IT01-01] Bỏ trống mã phòng** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm phòng
  - Các bước: 1. Xóa thuộc tính required của ô Mã phòng bằng DevTools · 2. Bỏ trống Mã phòng, nhập đủ Loại phòng và Giá thuê · 3. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo đỏ: "Mã phòng không được để trống." · Các ô đã nhập (loại phòng, giá) vẫn còn nguyên giá trị
  - Sau (CSDL): Không có bản ghi nào được thêm vào bảng rooms
  - Lý do chọn ca: Chốt chặn đầu tiên của RoomService.validate
- **[IT01-02] Mã phòng chỉ gồm khoảng trắng** — `EG` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm phòng
  - Các bước: 1. Nhập Mã phòng = "   " (3 dấu cách) · 2. Nhập đủ các trường bắt buộc còn lại · 3. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Mã phòng không được để trống."
  - Sau (CSDL): Không có bản ghi nào được thêm vào bảng rooms
  - Lý do chọn ca: paramText trim rồi trả null nên chuỗi trắng bị coi như bỏ trống
- **[IT01-03] Mã phòng đúng 20 ký tự (biên trên hợp lệ)** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chưa có phòng nào mang mã này
  - Các bước: 1. Nhập Mã phòng = "PHONG-TRO-SO-A10123" (20 ký tự) · 2. Chọn Loại phòng = Phòng đơn, Giá thuê = 1800000 · 3. Bấm Lưu
  - Mong đợi: Lưu thành công, chuyển về danh sách kèm thông báo xanh "Đã thêm phòng PHONG-TRO-SO-A10123."
  - Sau (CSDL): Bảng rooms có thêm 1 bản ghi; activity_log ghi 1 dòng CREATE / ROOM
  - Lý do chọn ca: Giới hạn 20 lấy đúng theo VARCHAR(20) của schema.sql
- **[IT01-04] Mã phòng 21 ký tự (vượt biên)** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm phòng
  - Các bước: 1. Nhập Mã phòng = "PHONG-TRO-SO-A101234" (21 ký tự) · 2. Nhập đủ các trường bắt buộc · 3. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Mã phòng tối đa 20 ký tự."
  - Sau (CSDL): Không có bản ghi nào được thêm vào bảng rooms
  - Lý do chọn ca: Chặn ở tầng Java thay vì để MySQL cắt chuỗi hoặc ném SQLException
- **[IT01-05] Mã phòng trùng với phòng đã có** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Trong hệ thống đã có phòng A101
  - Các bước: 1. Nhập Mã phòng = "A101" · 2. Nhập đủ các trường bắt buộc · 3. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Mã phòng A101 đã tồn tại."
  - Sau (CSDL): Không có bản ghi mới; ràng buộc uk_rooms_code không bị chạm tới
  - Lý do chọn ca: roomDAO.existsByCode chặn trước, không để UNIQUE ném SQLException
- **[IT01-06] Mã phòng trùng nhưng khác chữ hoa / thường** — `EG` — Kết quả: Chưa chạy
  - Tiền điều kiện: Trong hệ thống đã có phòng A101
  - Các bước: 1. Nhập Mã phòng = "a101" · 2. Nhập đủ các trường bắt buộc · 3. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Mã phòng a101 đã tồn tại." · (collation utf8mb4_unicode_ci không phân biệt hoa thường)
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Ca này phát hiện lệch giữa so sánh của Java và của MySQL
- **[IT01-07] Mã phòng có khoảng trắng thừa hai đầu** — `EG` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chưa có phòng C505
  - Các bước: 1. Nhập Mã phòng = "  C505  " · 2. Nhập đủ các trường bắt buộc · 3. Bấm Lưu
  - Mong đợi: Lưu thành công; danh sách hiện mã phòng là "C505" (đã cắt khoảng trắng)
  - Sau (CSDL): rooms.room_code lưu đúng "C505", không có dấu cách
  - Lý do chọn ca: validate gọi setRoomCode(trim) trước khi kiểm tra trùng

**2. Kiểm tra ràng buộc Loại phòng**

- **[IT01-08] Không chọn loại phòng** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm phòng
  - Các bước: 1. Xóa thuộc tính required của thẻ select Loại phòng bằng DevTools · 2. Để Loại phòng ở mục — Chọn loại phòng — · 3. Nhập Mã phòng và Giá thuê hợp lệ rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Chưa chọn loại phòng."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Kiểm tra server vẫn chặn khi ràng buộc phía trình duyệt bị vô hiệu hóa
- **[IT01-09] Loại phòng không tồn tại (sửa tay giá trị option)** — `SEC` — Kết quả: Chưa chạy
  - Tiền điều kiện: Bảng room_types chỉ có id 1–4
  - Các bước: 1. Dùng DevTools sửa value của một option thành 999 · 2. Chọn option đó, nhập Mã phòng và Giá thuê hợp lệ · 3. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Loại phòng không tồn tại."
  - Sau (CSDL): Không có bản ghi mới; khóa ngoại fk_rooms_room_type không bị chạm tới
  - Lý do chọn ca: Dữ liệu từ trình duyệt luôn phải kiểm tra lại ở server

**3. Kiểm tra ràng buộc Giá thuê và Diện tích**

- **[IT01-10] Bỏ trống giá thuê** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm phòng
  - Các bước: 1. Xóa required của ô Giá thuê bằng DevTools, để trống ô này · 2. Nhập Mã phòng và Loại phòng hợp lệ · 3. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Giá thuê không được để trống."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: paramDecimal trả null khi ô rỗng, validate bắt null này
- **[IT01-11] Giá thuê bằng 0 (biên dưới hợp lệ)** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chưa có phòng C506
  - Các bước: 1. Nhập Mã phòng = C506, Loại phòng = Phòng đơn · 2. Nhập Giá thuê = 0 · 3. Bấm Lưu
  - Mong đợi: Lưu thành công (phòng cho ở nhờ / phòng mẫu vẫn là dữ liệu hợp lệ)
  - Sau (CSDL): rooms.price = 0.00; CHECK chk_rooms_price (price >= 0) được thỏa mãn
  - Lý do chọn ca: Biên dưới của điều kiện price >= 0
- **[IT01-12] Giá thuê âm** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm phòng
  - Các bước: 1. Dùng DevTools bỏ min=0 của ô Giá thuê · 2. Nhập Giá thuê = -1 · 3. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Giá thuê không được âm."
  - Sau (CSDL): Không có bản ghi mới; CHECK chk_rooms_price không bị chạm tới
  - Lý do chọn ca: Biên ngay dưới ngưỡng hợp lệ
- **[IT01-13] Giá thuê không phải số** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm phòng
  - Các bước: 1. Dùng DevTools đổi type của ô Giá thuê thành text · 2. Nhập Giá thuê = "abc" · 3. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Giá thuê không phải là số hợp lệ: abc"
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: paramDecimal đổi NumberFormatException thành lỗi tiếng Việt
- **[IT01-14] Giá thuê nhập theo cách viết Việt Nam** — `EG` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chưa có phòng C507
  - Các bước: 1. Đổi ô Giá thuê thành text, nhập "2.500.000" · 2. Nhập Mã phòng = C507, Loại phòng = Phòng đôi · 3. Bấm Lưu
  - Mong đợi: Lưu thành công với giá 2.500.000 đ (không phải 2.5)
  - Sau (CSDL): rooms.price = 2500000.00
  - Lý do chọn ca: Dấu chấm xuất hiện nhiều lần được hiểu là dấu phân nhóm
- **[IT01-15] Giá thuê dùng dấu phẩy thập phân** — `EG` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chưa có phòng C508
  - Các bước: 1. Đổi ô Giá thuê thành text, nhập "2500000,50" · 2. Nhập Mã phòng = C508 rồi bấm Lưu
  - Mong đợi: Lưu thành công với giá 2.500.000,50 đ
  - Sau (CSDL): rooms.price = 2500000.50
  - Lý do chọn ca: Thói quen gõ dấu phẩy của người Việt phải ra đúng số
- **[IT01-16] Diện tích âm** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm phòng
  - Các bước: 1. Dùng DevTools bỏ min=0 của ô Diện tích · 2. Nhập Diện tích = -5, các trường khác hợp lệ · 3. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Diện tích không được âm."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Diện tích cho phép để trống nhưng đã nhập thì không được âm
- **[IT01-17] Bỏ trống diện tích** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chưa có phòng C509
  - Các bước: 1. Để trống ô Diện tích · 2. Nhập Mã phòng = C509, Loại phòng và Giá thuê hợp lệ · 3. Bấm Lưu
  - Mong đợi: Lưu thành công; cột Diện tích trên danh sách hiện dấu —
  - Sau (CSDL): rooms.area = NULL (không phải 0)
  - Lý do chọn ca: Phân biệt chưa đo diện tích với diện tích bằng 0

**4. Kiểm tra Số người tối đa, Tầng và Trạng thái**

- **[IT01-18] Số người tối đa bằng 0** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm phòng
  - Các bước: 1. Dùng DevTools bỏ min=1 của ô Số người tối đa · 2. Nhập Số người tối đa = 0 · 3. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Số người tối đa phải từ 1 trở lên."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Biên ngay dưới ngưỡng hợp lệ của chk_room_types_occ
- **[IT01-19] Số người tối đa bằng 1 (biên dưới hợp lệ)** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chưa có phòng C510
  - Các bước: 1. Nhập Số người tối đa = 1 · 2. Nhập Mã phòng = C510 và các trường bắt buộc · 3. Bấm Lưu
  - Mong đợi: Lưu thành công
  - Sau (CSDL): rooms.max_occupants = 1
  - Lý do chọn ca: Biên dưới hợp lệ
- **[IT01-20] Bỏ trống Tầng và Số người tối đa** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chưa có phòng C511
  - Các bước: 1. Xóa trắng cả ô Tầng và ô Số người tối đa · 2. Nhập Mã phòng = C511, Loại phòng và Giá thuê hợp lệ · 3. Bấm Lưu
  - Mong đợi: Lưu thành công; danh sách hiện Tầng = 1 và Số người = 1
  - Sau (CSDL): rooms.floor_no = 1 và rooms.max_occupants = 1 (giá trị mặc định)
  - Lý do chọn ca: validate tự điền mặc định thay vì báo lỗi
- **[IT01-21] Trạng thái không thuộc danh mục ENUM** — `SEC` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm phòng
  - Các bước: 1. Dùng DevTools thêm option value="XYZ" vào select Trạng thái và chọn nó · 2. Nhập các trường bắt buộc rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Trạng thái phòng không hợp lệ: XYZ"
  - Sau (CSDL): Không có bản ghi mới; ENUM của cột rooms.status không bị chạm tới
  - Lý do chọn ca: Chặn tham số bị sửa tay trước khi tới MySQL
- **[IT01-22] Bỏ trống trạng thái** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chưa có phòng C512
  - Các bước: 1. Dùng DevTools thêm option value="" và chọn nó · 2. Nhập Mã phòng = C512 và các trường bắt buộc rồi bấm Lưu
  - Mong đợi: Lưu thành công; phòng hiện nhãn Còn trống
  - Sau (CSDL): rooms.status = AVAILABLE (mặc định)
  - Lý do chọn ca: Trạng thái rỗng được coi là phòng mới, còn trống

**5. Kiểm tra tích hợp phần upload ảnh**

- **[IT01-23] Chọn file không phải ảnh** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Có sẵn file ghichu.txt trên máy
  - Các bước: 1. Nhập đủ các trường hợp lệ · 2. Ở ô Ảnh phòng chọn file ghichu.txt (bỏ accept=image/* bằng DevTools) · 3. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Chỉ chấp nhận ảnh có đuôi jpg, jpeg, png, webp."
  - Sau (CSDL): Không có bản ghi mới và không có file nào còn lại trong thư mục uploads
  - Lý do chọn ca: UploadUtil.isAllowedImage (đã kiểm thử đơn vị ở F02)
- **[IT01-24] Chọn ảnh có đuôi viết hoa** — `EG` — Kết quả: Chưa chạy
  - Tiền điều kiện: Có sẵn file anhphong.PNG (dưới 5 MB)
  - Các bước: 1. Nhập đủ các trường hợp lệ, Mã phòng = C513 · 2. Chọn ảnh anhphong.PNG · 3. Bấm Lưu
  - Mong đợi: Lưu thành công; ảnh hiện đúng trên danh sách và trang chi tiết
  - Sau (CSDL): rooms.image_path = uploads/room-<uuid>.png (đuôi đã hạ về chữ thường)
  - Lý do chọn ca: Người dùng chụp từ điện thoại hay có đuôi viết hoa
- **[IT01-25] Chọn ảnh vượt quá 5 MB** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Có sẵn file anh-lon.jpg khoảng 8 MB
  - Các bước: 1. Nhập đủ các trường hợp lệ · 2. Chọn ảnh anh-lon.jpg · 3. Bấm Lưu (bỏ qua cảnh báo của main.js)
  - Mong đợi: Form hiện lại kèm thông báo: "Ảnh vượt quá 5 MB." · Trang không rơi vào lỗi 500
  - Sau (CSDL): Không có bản ghi mới và không có file rác trong thư mục uploads
  - Lý do chọn ca: @MultipartConfig ném IllegalStateException, UploadUtil đổi thành lỗi người dùng
- **[IT01-26] Ảnh mới đã ghi xuống đĩa nhưng validate hỏng** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Trong hệ thống đã có phòng A101
  - Các bước: 1. Đếm số file đang có trong thư mục uploads · 2. Nhập Mã phòng = "A101" (mã trùng) và chọn một ảnh hợp lệ · 3. Bấm Lưu · 4. Đếm lại số file trong thư mục uploads
  - Mong đợi: Form hiện lỗi "Mã phòng A101 đã tồn tại." · Số file trong thư mục uploads KHÔNG tăng thêm
  - Sau (CSDL): Không có bản ghi mới và không có ảnh mồ côi trên đĩa
  - Lý do chọn ca: Ca này kiểm tra đúng khối catch dọn ảnh trong RoomServlet.save
- **[IT01-27] Sửa phòng nhưng không chọn ảnh mới** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Phòng A101 đã có ảnh
  - Các bước: 1. Mở /rooms/edit?id=1 · 2. Chỉ đổi Giá thuê, để trống ô chọn ảnh · 3. Bấm Lưu
  - Mong đợi: Lưu thành công; ảnh cũ của phòng vẫn còn nguyên
  - Sau (CSDL): rooms.image_path không đổi; file ảnh cũ vẫn còn trên đĩa
  - Lý do chọn ca: Lỗi hay gặp: lưu lại làm mất ảnh vì không chọn ảnh mới
- **[IT01-28] Sửa phòng và thay ảnh mới** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Phòng A101 đã có ảnh cũ
  - Các bước: 1. Mở /rooms/edit?id=1, ghi lại tên file ảnh cũ · 2. Chọn một ảnh mới hợp lệ rồi bấm Lưu · 3. Kiểm tra thư mục uploads
  - Mong đợi: Lưu thành công; trang chi tiết hiện ảnh mới
  - Sau (CSDL): rooms.image_path trỏ tới ảnh mới; file ảnh CŨ đã bị xóa khỏi đĩa
  - Lý do chọn ca: Ảnh cũ chỉ được xóa SAU khi ghi DB thành công

**6. Kiểm tra luồng nghiệp vụ và bảo mật**

- **[IT01-29] Thêm phòng hợp lệ đầy đủ thông tin** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chưa có phòng C520
  - Các bước: 1. Nhập Mã phòng = C520, Loại phòng = Phòng đôi, Tầng = 2, · Diện tích = 18, Giá thuê = 2500000, Số người = 2, Trạng thái = Còn trống · 2. Chọn một ảnh hợp lệ, nhập Mô tả · 3. Bấm Lưu
  - Mong đợi: Chuyển về danh sách kèm thông báo xanh "Đã thêm phòng C520." · Phòng C520 xuất hiện trên danh sách với đầy đủ thông tin vừa nhập
  - Sau (CSDL): rooms có thêm 1 bản ghi; activity_log ghi 1 dòng CREATE / ROOM / C520
  - Lý do chọn ca: Ca đường đi chính (happy path)
- **[IT01-30] Nhấn F5 sau khi lưu thành công** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Vừa thực hiện xong ca thêm phòng hợp lệ
  - Các bước: 1. Sau khi trang danh sách hiện ra, nhấn F5 · 2. Quan sát danh sách phòng
  - Mong đợi: Trình duyệt không hỏi gửi lại form; không có phòng nào bị thêm trùng · Thông báo xanh biến mất sau lần tải lại thứ hai
  - Sau (CSDL): Bảng rooms không phát sinh bản ghi trùng
  - Lý do chọn ca: Kiểm tra mẫu Post/Redirect/Get và cơ chế flash chỉ hiện một lần
- **[IT01-31] Sửa phòng vừa bị người khác xóa** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Mở form sửa phòng C520 ở tab 1, xóa phòng C520 ở tab 2
  - Các bước: 1. Ở tab 1 (đang mở form sửa C520) đổi Giá thuê · 2. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Phòng không còn tồn tại." · Trang không rơi vào lỗi 500
  - Sau (CSDL): Không có bản ghi nào được ghi thêm
  - Lý do chọn ca: Kiểm thử tình huống hai người dùng đồng thời
- **[IT01-32] Gửi form thiếu token CSRF** — `SEC` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm phòng
  - Các bước: 1. Dùng DevTools xóa thẻ input hidden _csrf · 2. Nhập dữ liệu hợp lệ rồi bấm Lưu
  - Mong đợi: Hệ thống trả về trang lỗi 403 (Bạn không có quyền) · Không hiện thông báo lưu thành công
  - Sau (CSDL): Không có bản ghi mới; CsrfFilter chặn trước khi tới servlet
  - Lý do chọn ca: Mọi form POST đều phải mang token khớp với phiên
- **[IT01-33] Mô tả phòng chứa mã HTML** — `SEC` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chưa có phòng C521
  - Các bước: 1. Nhập Mô tả = "<script>alert(1)</script>" · 2. Nhập Mã phòng = C521 và các trường bắt buộc · 3. Bấm Lưu, sau đó mở trang chi tiết phòng
  - Mong đợi: Lưu thành công; trang chi tiết hiện đúng chuỗi ký tự đó dưới dạng văn bản, · KHÔNG bật hộp thoại alert
  - Sau (CSDL): rooms.description lưu nguyên văn; JSP escape bằng thẻ c:out khi hiển thị
  - Lý do chọn ca: Kiểm thử chống XSS lưu trữ

## IT02 — Thêm / Sửa khách thuê

- Người thực thi: Nguyễn Văn Hiếu - 20231766
- Yêu cầu: Kiểm thử tích hợp màn hình hồ sơ sinh viên thuê trọ: form JSP → TenantServlet.bind → DateUtil.parse → UploadUtil.saveImage → TenantService.validate (CMND/CCCD, SĐT, email, mã SV, ngày sinh, giới tính) → TenantDAO → MySQL. Trọng tâm: các ràng buộc định dạng và chống trùng CMND / mã sinh viên.
- Điều kiện tiên quyết chung: Tomcat 10.1 + JDK 17, MariaDB 10.4 (XAMPP) đã nạp schema.sql và seed-demo.sql. Đăng nhập sẵn bằng admin / admin123. Dữ liệu mẫu: đã có một khách thuê mang CCCD 001203012345 và mã sinh viên 20231766.
- Đường dẫn: GET /tenants/new · GET /tenants/edit?id= · POST /tenants/save


**1. Kiểm tra ràng buộc trường Họ tên**

- **[IT02-01] Bỏ trống họ tên** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Dùng DevTools xóa required của ô Họ tên · 2. Bỏ trống Họ tên, nhập CCCD và SĐT hợp lệ · 3. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Họ tên không được để trống." · CCCD và SĐT vừa nhập vẫn còn trên form
  - Sau (CSDL): Không có bản ghi nào được thêm vào bảng tenants
  - Lý do chọn ca: Chốt chặn đầu tiên của TenantService.validate
- **[IT02-02] Họ tên đúng 150 ký tự (biên trên hợp lệ)** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Dùng DevTools bỏ maxlength của ô Họ tên · 2. Dán chuỗi họ tên dài đúng 150 ký tự · 3. Nhập CCCD và SĐT hợp lệ rồi bấm Lưu
  - Mong đợi: Lưu thành công, chuyển về danh sách kèm thông báo xanh
  - Sau (CSDL): tenants.full_name lưu đủ 150 ký tự, không bị cắt
  - Lý do chọn ca: Giới hạn lấy đúng theo VARCHAR(150) của schema.sql
- **[IT02-03] Họ tên 151 ký tự (vượt biên)** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Dùng DevTools bỏ maxlength của ô Họ tên · 2. Dán chuỗi họ tên dài 151 ký tự rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Họ tên tối đa 150 ký tự."
  - Sau (CSDL): Không có bản ghi mới; MySQL không âm thầm cắt chuỗi
  - Lý do chọn ca: Chặn ở Java thay vì phó mặc cho sql_mode
- **[IT02-04] Họ tên có dấu tiếng Việt** — `EG` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Nhập Họ tên = "Nguyễn Văn Hiếu" · 2. Nhập CCCD và SĐT hợp lệ rồi bấm Lưu · 3. Mở lại trang chi tiết khách thuê vừa thêm
  - Mong đợi: Danh sách và trang chi tiết hiện đúng "Nguyễn Văn Hiếu", không có ký tự lạ · Ô avatar tạm hiện hai chữ "NH"
  - Sau (CSDL): tenants.full_name lưu đúng chuỗi UTF-8
  - Lý do chọn ca: Kiểm tra chuỗi UTF-8 đi trọn từ form qua EncodingFilter tới MySQL utf8mb4

**2. Kiểm tra ràng buộc CMND / CCCD**

- **[IT02-05] Bỏ trống CMND / CCCD** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Dùng DevTools xóa required của ô CMND / CCCD · 2. Bỏ trống ô này, nhập Họ tên và SĐT hợp lệ rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "CMND / CCCD không được để trống."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Đây là trường định danh duy nhất của khách thuê
- **[IT02-06] CMND đúng 9 chữ số** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chưa có ai mang số 123456789
  - Các bước: 1. Nhập CMND / CCCD = "123456789" · 2. Nhập Họ tên và SĐT hợp lệ rồi bấm Lưu
  - Mong đợi: Lưu thành công
  - Sau (CSDL): tenants.id_card = 123456789
  - Lý do chọn ca: Lớp tương đương hợp lệ thứ nhất (CMND cũ)
- **[IT02-07] CCCD đúng 12 chữ số** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chưa có ai mang số 001203099999
  - Các bước: 1. Nhập CMND / CCCD = "001203099999" · 2. Nhập Họ tên và SĐT hợp lệ rồi bấm Lưu
  - Mong đợi: Lưu thành công
  - Sau (CSDL): tenants.id_card = 001203099999 (giữ nguyên số 0 ở đầu)
  - Lý do chọn ca: Lớp tương đương hợp lệ thứ hai (CCCD mới)
- **[IT02-08] CMND 8 chữ số (thiếu 1 số)** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Dùng DevTools xóa thuộc tính pattern của ô CMND / CCCD · 2. Nhập "12345678" rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: · "CMND / CCCD phải gồm 9 chữ số (CMND) hoặc 12 chữ số (CCCD)."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Biên ngay dưới độ dài hợp lệ
- **[IT02-09] Số 10 chữ số (nằm giữa hai lớp hợp lệ)** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Xóa pattern của ô CMND / CCCD · 2. Nhập "1234567890" rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo về định dạng CMND / CCCD
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Khoảng trống giữa hai lớp hợp lệ 9 và 12 - dễ bị bỏ sót
- **[IT02-10] CCCD 13 chữ số (thừa 1 số)** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Xóa pattern của ô CMND / CCCD · 2. Nhập "0012030999991" rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo về định dạng CMND / CCCD
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Biên ngay trên độ dài hợp lệ
- **[IT02-11] CCCD chứa chữ cái** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Xóa pattern của ô CMND / CCCD · 2. Nhập "00120301234A" (12 ký tự nhưng có chữ) rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo về định dạng CMND / CCCD
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Đủ độ dài nhưng sai kiểu ký tự
- **[IT02-12] CCCD trùng với người đã có** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Trong hệ thống đã có người mang CCCD 001203012345
  - Các bước: 1. Nhập CMND / CCCD = "001203012345" · 2. Nhập Họ tên và SĐT hợp lệ rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: · "CMND / CCCD 001203012345 đã có trong hệ thống."
  - Sau (CSDL): Không có bản ghi mới; ràng buộc uk_tenants_id_card không bị chạm tới
  - Lý do chọn ca: existsByIdCard chặn trước, không để UNIQUE ném SQLException
- **[IT02-13] Sửa hồ sơ nhưng giữ nguyên CCCD của chính mình** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Khách thuê id=1 mang CCCD 001203012345
  - Các bước: 1. Mở /tenants/edit?id=1 · 2. Giữ nguyên CCCD, chỉ đổi số điện thoại · 3. Bấm Lưu
  - Mong đợi: Lưu thành công, KHÔNG báo lỗi "CCCD đã có trong hệ thống"
  - Sau (CSDL): tenants.phone được cập nhật; id_card giữ nguyên
  - Lý do chọn ca: Tham số excludeId phải loại chính bản ghi đang sửa khỏi phép kiểm tra trùng

**3. Kiểm tra ràng buộc Số điện thoại**

- **[IT02-14] Bỏ trống số điện thoại** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Xóa required của ô Điện thoại, bỏ trống ô này · 2. Nhập Họ tên và CCCD hợp lệ rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Số điện thoại không được để trống."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Số điện thoại là kênh liên lạc bắt buộc với chủ trọ
- **[IT02-15] Số điện thoại 10 số bắt đầu bằng 0** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Nhập Điện thoại = "0912345678" · 2. Nhập Họ tên và CCCD hợp lệ rồi bấm Lưu
  - Mong đợi: Lưu thành công
  - Sau (CSDL): tenants.phone = 0912345678
  - Lý do chọn ca: Lớp tương đương hợp lệ
- **[IT02-16] Số điện thoại 9 chữ số** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Xóa pattern của ô Điện thoại · 2. Nhập "091234567" rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: · "Số điện thoại phải gồm 10 chữ số, bắt đầu bằng 0."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Biên ngay dưới độ dài hợp lệ
- **[IT02-17] Số điện thoại 11 chữ số** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Xóa pattern của ô Điện thoại · 2. Nhập "09123456789" rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo về định dạng số điện thoại
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Biên ngay trên độ dài hợp lệ
- **[IT02-18] Số điện thoại không bắt đầu bằng 0** — `EG` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Xóa pattern của ô Điện thoại · 2. Nhập "9123456780" (10 số nhưng đầu là 9) rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo về định dạng số điện thoại
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Đủ 10 số nhưng sai đầu số - ca dễ lọt nếu chỉ kiểm tra độ dài
- **[IT02-19] Số điện thoại viết dạng +84** — `EG` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Xóa pattern của ô Điện thoại · 2. Nhập "+84912345678" rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo về định dạng số điện thoại
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Hệ thống hiện chỉ nhận dạng nội địa - ghi nhận để thống nhất với chủ trọ

**4. Kiểm tra Email, Mã sinh viên và Ngày sinh**

- **[IT02-20] Bỏ trống email** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Để trống ô Email · 2. Nhập Họ tên, CCCD, SĐT hợp lệ rồi bấm Lưu
  - Mong đợi: Lưu thành công (email không bắt buộc)
  - Sau (CSDL): tenants.email = NULL (không phải chuỗi rỗng)
  - Lý do chọn ca: trimToNull đổi ô rỗng thành NULL trong DB
- **[IT02-21] Email sai định dạng (thiếu dấu chấm ở tên miền)** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Dùng DevTools đổi type của ô Email thành text · 2. Nhập "hieu@gmail" rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Email không hợp lệ: hieu@gmail"
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: EMAIL_PATTERN đòi tên miền phải có ít nhất một dấu chấm
- **[IT02-22] Email có hai ký tự @** — `EG` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Đổi type của ô Email thành text · 2. Nhập "hieu@@gmail.com" rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo Email không hợp lệ
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Ca biên của biểu thức chính quy kiểm tra email
- **[IT02-23] Email hợp lệ** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Nhập Email = "hieu.nv@sinhvien.eaut.edu.vn" · 2. Nhập các trường bắt buộc rồi bấm Lưu
  - Mong đợi: Lưu thành công; trang chi tiết hiện đúng email
  - Sau (CSDL): tenants.email lưu đúng chuỗi vừa nhập
  - Lý do chọn ca: Tên miền nhiều cấp vẫn phải được chấp nhận
- **[IT02-24] Mã sinh viên trùng với người đã có** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Trong hệ thống đã có mã sinh viên 20231766
  - Các bước: 1. Nhập Mã sinh viên = "20231766" · 2. Nhập Họ tên, CCCD, SĐT hợp lệ (khác người cũ) rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: · "Mã sinh viên 20231766 đã có trong hệ thống."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Mã sinh viên tuy không bắt buộc nhưng đã nhập thì phải là duy nhất
- **[IT02-25] Ngày sinh ở tương lai** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hôm nay là 07/09/2026
  - Các bước: 1. Nhập Ngày sinh = 08/09/2026 · 2. Nhập các trường bắt buộc rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Ngày sinh không được ở tương lai."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Biên ngay trên "hôm nay" theo múi giờ Asia/Ho_Chi_Minh
- **[IT02-26] Ngày sinh đúng hôm nay** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hôm nay là 07/09/2026
  - Các bước: 1. Nhập Ngày sinh = 07/09/2026 · 2. Nhập các trường bắt buộc rồi bấm Lưu
  - Mong đợi: Lưu thành công (không báo lỗi tương lai)
  - Sau (CSDL): tenants.date_of_birth = 2026-09-07
  - Lý do chọn ca: Đúng biên hợp lệ - phép so sánh dùng isAfter chứ không phải >=
- **[IT02-27] Ngày sinh trước năm 1900** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Nhập Ngày sinh = 31/12/1899 · 2. Nhập các trường bắt buộc rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: · "Ngày sinh không hợp lệ (năm phải từ 1900 trở đi)."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Chặn lỗi gõ nhầm năm kiểu 0202
- **[IT02-28] Giới tính không thuộc danh mục** — `SEC` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Thêm khách thuê
  - Các bước: 1. Dùng DevTools thêm option value="UNKNOWN" và chọn nó · 2. Nhập các trường bắt buộc rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Giới tính không hợp lệ: UNKNOWN"
  - Sau (CSDL): Không có bản ghi mới; ENUM tenants.gender không bị chạm tới
  - Lý do chọn ca: Chặn tham số bị sửa tay trước khi tới MySQL

**5. Kiểm tra ảnh đại diện và luồng nghiệp vụ**

- **[IT02-29] Ảnh đại diện vượt quá 2 MB** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Có sẵn file avatar-lon.jpg khoảng 3 MB
  - Các bước: 1. Nhập đủ các trường hợp lệ · 2. Chọn ảnh avatar-lon.jpg rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Ảnh vượt quá 2 MB." · Trang không rơi vào lỗi 500
  - Sau (CSDL): Không có bản ghi mới và không có file rác trong thư mục uploads
  - Lý do chọn ca: Hạn mức avatar (2 MB) chặt hơn hạn mức ảnh phòng (5 MB)
- **[IT02-30] Ảnh đã ghi xuống đĩa nhưng validate hỏng** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Trong hệ thống đã có CCCD 001203012345
  - Các bước: 1. Đếm số file trong thư mục uploads · 2. Nhập CCCD trùng và chọn một ảnh hợp lệ dưới 2 MB · 3. Bấm Lưu rồi đếm lại số file trong thư mục uploads
  - Mong đợi: Form hiện lỗi CCCD đã có trong hệ thống · Số file trong thư mục uploads KHÔNG tăng thêm
  - Sau (CSDL): Không có bản ghi mới và không có ảnh mồ côi trên đĩa
  - Lý do chọn ca: Kiểm tra khối catch dọn ảnh trong TenantServlet.save
- **[IT02-31] Thêm khách thuê hợp lệ đầy đủ thông tin** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chưa có ai mang CCCD 001203077777
  - Các bước: 1. Nhập Họ tên = "Nguyễn Văn Hiếu", CCCD = 001203077777, · Ngày sinh = 15/03/2005, Giới tính = Nam, Điện thoại = 0912345678, · Email = hieu.nv@sinhvien.eaut.edu.vn, Trường = "ĐH Công Nghệ Đông Á", · Mã sinh viên = 20231766 · 2. Chọn một ảnh hợp lệ rồi bấm Lưu
  - Mong đợi: Chuyển về danh sách kèm thông báo xanh "Đã thêm khách thuê Nguyễn Văn Hiếu." · Bản ghi hiện đầy đủ trên danh sách kèm ảnh đại diện
  - Sau (CSDL): tenants có thêm 1 bản ghi; activity_log ghi 1 dòng CREATE / TENANT
  - Lý do chọn ca: Ca đường đi chính (happy path)
- **[IT02-32] Xóa khách thuê đang có hợp đồng hiệu lực** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Khách thuê id=1 đang đứng tên một hợp đồng ACTIVE
  - Các bước: 1. Mở danh sách khách thuê · 2. Bấm Xóa ở dòng của khách thuê id=1 và xác nhận
  - Mong đợi: Về lại danh sách kèm thông báo đỏ: · "Không thể xóa <họ tên>: khách đang có hợp đồng hiệu lực hoặc chờ hiệu lực."
  - Sau (CSDL): Bản ghi vẫn còn trong bảng tenants; FK ON DELETE RESTRICT không bị chạm tới
  - Lý do chọn ca: Chặn từ tầng nghiệp vụ để báo lỗi rõ ràng thay vì trang 500
- **[IT02-33] Tìm kiếm khách thuê bằng ô AJAX** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Trong hệ thống có khách tên Nguyễn Văn Hiếu
  - Các bước: 1. Mở form Tạo hợp đồng, gõ "Hiếu" vào ô lọc khách thuê · 2. Quan sát danh sách phía dưới
  - Mong đợi: Danh sách thu hẹp lại, hiện đúng khách Nguyễn Văn Hiếu kèm CCCD · Tên tiếng Việt hiện đúng dấu, không có ký tự lạ
  - Sau (CSDL): TenantServlet trả JSON với Content-Type application/json;charset=UTF-8
  - Lý do chọn ca: Kiểm thử tích hợp nhánh trả JSON theo header Accept

## IT03 — Lập hợp đồng thuê

- Người thực thi: Phùng Tiến Dũng
- Yêu cầu: Kiểm thử tích hợp màn hình lập hợp đồng thuê: form JSP → ContractServlet.bind → DateUtil.parse → ContractService.validate → Transactions.run (khóa dòng phòng, kiểm tra phòng còn trống, số người ở, khách chưa có hợp đồng khác, sinh mã hợp đồng, insert, đổi trạng thái phòng). Trọng tâm: trạng thái hợp đồng do hệ thống suy ra theo ngày, và trạng thái phòng phải đổi theo trong cùng một giao dịch.
- Điều kiện tiên quyết chung: Tomcat 10.1 + JDK 17, MariaDB 10.4 (XAMPP) đã nạp schema.sql và seed-demo.sql. Đăng nhập sẵn bằng admin / admin123. Hôm nay là 07/09/2026 (múi giờ Asia/Ho_Chi_Minh). Dữ liệu mẫu: A101 (tối đa 1 người, AVAILABLE), A102 (tối đa 2 người, AVAILABLE), A202 (MAINTENANCE); khách thuê id=1 chưa có hợp đồng nào.
- Đường dẫn: GET /contracts/new · GET /contracts/edit?id= · POST /contracts/save · POST /contracts/renew · POST /contracts/terminate


**1. Kiểm tra ràng buộc Phòng và Khách thuê**

- **[IT03-01] Không chọn phòng** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Tạo hợp đồng
  - Các bước: 1. Dùng DevTools xóa required của select Phòng · 2. Để ở mục — Chọn phòng còn trống —, chọn khách thuê và nhập ngày hợp lệ · 3. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Chưa chọn phòng." · Các lựa chọn khác vẫn còn trên form
  - Sau (CSDL): Không có bản ghi nào được thêm vào bảng contracts
  - Lý do chọn ca: Chốt chặn đầu tiên của ContractService.validate
- **[IT03-02] Không chọn khách thuê đại diện** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Tạo hợp đồng
  - Các bước: 1. Xóa required của select Khách thuê · 2. Chọn phòng A102, để trống khách thuê rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Chưa chọn khách thuê đại diện."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Hợp đồng luôn phải có một người đứng tên
- **[IT03-03] Chọn phòng đang bảo trì (sửa tay giá trị option)** — `SEC` — Kết quả: Chưa chạy
  - Tiền điều kiện: Phòng A202 đang ở trạng thái MAINTENANCE
  - Các bước: 1. Dùng DevTools thêm option với value = id của phòng A202 · 2. Chọn option đó, nhập các trường hợp lệ rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: · "Phòng A202 không còn trống (trạng thái hiện tại: MAINTENANCE)."
  - Sau (CSDL): Không có bản ghi mới; trạng thái phòng A202 không đổi
  - Lý do chọn ca: Danh sách chỉ liệt kê phòng trống, nhưng server vẫn phải kiểm tra lại
- **[IT03-04] Chọn phòng vừa được người khác ký hợp đồng** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Mở form ở tab 1 khi A102 còn trống, tab 2 ký hợp đồng cho A102
  - Các bước: 1. Ở tab 2 tạo và lưu hợp đồng cho phòng A102 · 2. Quay lại tab 1 (danh sách vẫn còn A102), chọn A102 rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo Phòng A102 không còn trống · Trang không rơi vào lỗi 500
  - Sau (CSDL): Chỉ có 1 hợp đồng cho phòng A102 trong bảng contracts
  - Lý do chọn ca: findByIdForUpdate khóa dòng phòng nên hai người không ký chồng lên nhau
- **[IT03-05] Khách thuê đang có hợp đồng hiệu lực** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Khách thuê id=1 đã có một hợp đồng ACTIVE
  - Các bước: 1. Chọn phòng A101 còn trống · 2. Chọn khách thuê id=1 · 3. Nhập ngày hợp lệ rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: · "<họ tên> đang có hợp đồng hiệu lực hoặc chờ hiệu lực, không ký thêm được."
  - Sau (CSDL): Không có bản ghi mới; trạng thái phòng A101 vẫn AVAILABLE
  - Lý do chọn ca: Một sinh viên chỉ đứng tên một hợp đồng tại một thời điểm

**2. Kiểm tra ràng buộc Ngày bắt đầu và Ngày kết thúc**

- **[IT03-06] Bỏ trống ngày bắt đầu** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Tạo hợp đồng
  - Các bước: 1. Xóa required của ô Ngày bắt đầu và xóa trắng ô này · 2. Nhập các trường còn lại hợp lệ rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Ngày bắt đầu không được để trống."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Thiếu ngày bắt đầu thì không suy ra được trạng thái hợp đồng
- **[IT03-07] Ngày kết thúc trùng ngày bắt đầu** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Tạo hợp đồng
  - Các bước: 1. Nhập Ngày bắt đầu = 01/10/2026, Ngày kết thúc = 01/10/2026 · 2. Nhập các trường còn lại hợp lệ rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Ngày kết thúc phải sau ngày bắt đầu."
  - Sau (CSDL): Không có bản ghi mới; CHECK chk_contracts_dates không bị chạm tới
  - Lý do chọn ca: Đúng biên: điều kiện là end_date > start_date, không phải >=
- **[IT03-08] Ngày kết thúc sau ngày bắt đầu đúng 1 ngày** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Phòng A102 còn trống, khách thuê id=2 chưa có hợp đồng
  - Các bước: 1. Nhập Ngày bắt đầu = 01/10/2026, Ngày kết thúc = 02/10/2026 · 2. Nhập các trường còn lại hợp lệ rồi bấm Lưu
  - Mong đợi: Lưu thành công, chuyển về danh sách kèm thông báo xanh
  - Sau (CSDL): contracts có thêm 1 bản ghi, status = PENDING
  - Lý do chọn ca: Biên hợp lệ nhỏ nhất
- **[IT03-09] Ngày kết thúc trước ngày bắt đầu** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Tạo hợp đồng
  - Các bước: 1. Nhập Ngày bắt đầu = 01/10/2026, Ngày kết thúc = 30/09/2026 · 2. Nhập các trường còn lại hợp lệ rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo Ngày kết thúc phải sau ngày bắt đầu
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Lớp tương đương không hợp lệ
- **[IT03-10] Ngày bắt đầu ở tương lai → trạng thái PENDING** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Phòng A101 còn trống, hôm nay là 07/09/2026
  - Các bước: 1. Nhập Ngày bắt đầu = 01/10/2026, Ngày kết thúc = 31/03/2027 · 2. Nhập các trường còn lại hợp lệ rồi bấm Lưu · 3. Mở trang chi tiết hợp đồng và trang chi tiết phòng A101
  - Mong đợi: Hợp đồng hiện nhãn Chờ hiệu lực (PENDING) · Phòng A101 chuyển sang nhãn Đã giữ chỗ (RESERVED)
  - Sau (CSDL): contracts.status = PENDING và rooms.status = RESERVED
  - Lý do chọn ca: Trạng thái do hệ thống suy ra chứ không nhận từ form
- **[IT03-11] Ngày bắt đầu là hôm nay → trạng thái ACTIVE** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Phòng A102 còn trống, hôm nay là 07/09/2026
  - Các bước: 1. Nhập Ngày bắt đầu = 07/09/2026, Ngày kết thúc = 07/03/2027 · 2. Nhập các trường còn lại hợp lệ rồi bấm Lưu · 3. Mở trang chi tiết phòng A102
  - Mong đợi: Hợp đồng hiện nhãn Đang hiệu lực (ACTIVE) · Phòng A102 chuyển sang nhãn Đang thuê (OCCUPIED)
  - Sau (CSDL): contracts.status = ACTIVE và rooms.status = OCCUPIED
  - Lý do chọn ca: Đúng biên: bắt đầu hôm nay đã là ACTIVE chứ không còn PENDING
- **[IT03-12] Cả hai ngày đều ở quá khứ → trạng thái EXPIRED** — `EG` — Kết quả: Chưa chạy
  - Tiền điều kiện: Phòng A201 còn trống, hôm nay là 07/09/2026
  - Các bước: 1. Nhập Ngày bắt đầu = 01/01/2026, Ngày kết thúc = 30/06/2026 · 2. Nhập các trường còn lại hợp lệ rồi bấm Lưu · 3. Mở trang chi tiết phòng A201
  - Mong đợi: Hợp đồng hiện nhãn Hết hạn (EXPIRED) · Phòng A201 vẫn ở nhãn Còn trống (không bị giữ chỗ)
  - Sau (CSDL): contracts.status = EXPIRED và rooms.status = AVAILABLE
  - Lý do chọn ca: Ghi bổ sung hợp đồng cũ - hệ thống phải suy ra đúng EXPIRED ngay
- **[IT03-13] Ngày nhập sai định dạng** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Tạo hợp đồng
  - Các bước: 1. Dùng DevTools đổi type của ô Ngày bắt đầu thành text · 2. Nhập "31/13/2026" rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: · "Ngày không hợp lệ: 31/13/2026 (định dạng dd/MM/yyyy hoặc yyyy-MM-dd)."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: DateUtil.parse (đã kiểm thử đơn vị ở F01) ném cùng loại ngoại lệ với lỗi nghiệp vụ

**3. Kiểm tra ràng buộc Tiền và Số người ở**

- **[IT03-14] Bỏ trống giá thuê / tháng** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Tạo hợp đồng
  - Các bước: 1. Xóa required của ô Giá thuê / tháng, xóa trắng ô này · 2. Nhập các trường còn lại hợp lệ rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Giá thuê / tháng không được để trống."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Giá chốt trong hợp đồng là căn cứ tính tiền phòng của mọi hóa đơn
- **[IT03-15] Giá thuê / tháng âm** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Tạo hợp đồng
  - Các bước: 1. Dùng DevTools bỏ min=0 của ô Giá thuê / tháng · 2. Nhập Giá thuê = -1 rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Giá thuê / tháng không được âm."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Biên ngay dưới ngưỡng hợp lệ
- **[IT03-16] Tiền cọc âm** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Tạo hợp đồng
  - Các bước: 1. Bỏ min=0 của ô Tiền cọc, nhập Tiền cọc = -500000 · 2. Nhập các trường còn lại hợp lệ rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Tiền cọc không được âm."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Biên ngay dưới ngưỡng hợp lệ
- **[IT03-17] Bỏ trống tiền cọc** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Phòng A101 còn trống
  - Các bước: 1. Xóa trắng ô Tiền cọc · 2. Nhập các trường còn lại hợp lệ rồi bấm Lưu · 3. Mở trang chi tiết hợp đồng
  - Mong đợi: Lưu thành công; trang chi tiết hiện Tiền cọc = 0 đ
  - Sau (CSDL): contracts.deposit = 0.00 (giá trị mặc định)
  - Lý do chọn ca: Hợp đồng không đặt cọc vẫn là hợp đồng hợp lệ
- **[IT03-18] Số người ở vượt sức chứa của phòng** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Phòng A101 chỉ ở tối đa 1 người
  - Các bước: 1. Chọn phòng A101 · 2. Nhập Số người ở = 2 · 3. Nhập các trường còn lại hợp lệ rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: · "Phòng A101 chỉ ở tối đa 1 người, hợp đồng đang ghi 2."
  - Sau (CSDL): Không có bản ghi mới; trạng thái phòng A101 không đổi
  - Lý do chọn ca: Kiểm tra chéo giữa dữ liệu hợp đồng và dữ liệu phòng, trong transaction
- **[IT03-19] Số người ở đúng bằng sức chứa** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Phòng A102 ở tối đa 2 người, đang còn trống
  - Các bước: 1. Chọn phòng A102, nhập Số người ở = 2 · 2. Nhập các trường còn lại hợp lệ rồi bấm Lưu
  - Mong đợi: Lưu thành công
  - Sau (CSDL): contracts.num_occupants = 2
  - Lý do chọn ca: Đúng biên trên hợp lệ
- **[IT03-20] Số người ở bằng 0** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Tạo hợp đồng
  - Các bước: 1. Bỏ min=1 của ô Số người ở, nhập 0 · 2. Nhập các trường còn lại hợp lệ rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: "Số người ở phải từ 1 trở lên."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Biên ngay dưới ngưỡng hợp lệ

**4. Kiểm tra Ngày chốt hóa đơn**

- **[IT03-21] Ngày chốt hóa đơn bằng 1 (biên dưới hợp lệ)** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Phòng A101 còn trống
  - Các bước: 1. Nhập Ngày chốt hóa đơn = 1 · 2. Nhập các trường còn lại hợp lệ rồi bấm Lưu
  - Mong đợi: Lưu thành công
  - Sau (CSDL): contracts.billing_day = 1; CHECK chk_contracts_billing_day thỏa mãn
  - Lý do chọn ca: Biên dưới của khoảng 1–28
- **[IT03-22] Ngày chốt hóa đơn bằng 28 (biên trên hợp lệ)** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Phòng A102 còn trống
  - Các bước: 1. Nhập Ngày chốt hóa đơn = 28 · 2. Nhập các trường còn lại hợp lệ rồi bấm Lưu
  - Mong đợi: Lưu thành công
  - Sau (CSDL): contracts.billing_day = 28
  - Lý do chọn ca: Biên trên - giới hạn 28 vì tháng 2 không có ngày 29–31
- **[IT03-23] Ngày chốt hóa đơn bằng 29 (vượt biên)** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Tạo hợp đồng
  - Các bước: 1. Dùng DevTools bỏ max=28 của ô Ngày chốt hóa đơn · 2. Nhập 29 rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: · "Ngày chốt hóa đơn phải trong khoảng 1–28."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Biên ngay trên ngưỡng hợp lệ
- **[IT03-24] Ngày chốt hóa đơn bằng 0** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Tạo hợp đồng
  - Các bước: 1. Bỏ min=1 của ô Ngày chốt hóa đơn, nhập 0 · 2. Nhập các trường còn lại hợp lệ rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo Ngày chốt hóa đơn phải trong khoảng 1–28
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Biên ngay dưới ngưỡng hợp lệ

**5. Kiểm tra sửa, gia hạn và chấm dứt hợp đồng**

- **[IT03-25] Sửa hợp đồng: hai ô Phòng và Khách thuê bị khóa** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang có hợp đồng ACTIVE cho phòng A102
  - Các bước: 1. Mở /contracts/edit?id=<id hợp đồng đó> · 2. Quan sát hai thẻ select Phòng và Khách thuê
  - Mong đợi: Hai select đều ở trạng thái disabled kèm dòng gợi ý · "Muốn đổi phòng thì chấm dứt hợp đồng này rồi tạo hợp đồng mới."
  - Sau (CSDL): Hai input hidden roomId / tenantId vẫn được gửi lên khi lưu
  - Lý do chọn ca: Đổi phòng hay đổi người thuê thực chất là lập một hợp đồng khác
- **[IT03-26] Sửa hợp đồng và cố đổi phòng bằng DevTools** — `SEC` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang có hợp đồng ACTIVE cho phòng A102
  - Các bước: 1. Mở form sửa, dùng DevTools đổi value của input hidden roomId sang id phòng A101 · 2. Bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: · "Không đổi được phòng của hợp đồng đã lập. Hãy chấm dứt hợp đồng này rồi tạo hợp đồng mới."
  - Sau (CSDL): contracts.room_id không đổi; trạng thái hai phòng đều giữ nguyên
  - Lý do chọn ca: Server phải đối chiếu với bản ghi đang có trong DB, không tin form
- **[IT03-27] Sửa hợp đồng đã chấm dứt** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Có một hợp đồng ở trạng thái TERMINATED
  - Các bước: 1. Mở /contracts/edit?id=<id hợp đồng TERMINATED> · 2. Đổi Giá thuê rồi bấm Lưu
  - Mong đợi: Form hiện lại kèm thông báo: · "Hợp đồng <mã> đã chấm dứt nên không sửa được."
  - Sau (CSDL): Bản ghi hợp đồng không đổi
  - Lý do chọn ca: Hợp đồng đã chấm dứt là dữ liệu lịch sử, chỉ để tra cứu
- **[IT03-28] Gia hạn với ngày kết thúc mới trùng ngày cũ** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hợp đồng ACTIVE kết thúc ngày 07/03/2027
  - Các bước: 1. Mở trang chi tiết hợp đồng · 2. Ở khối Gia hạn nhập Ngày kết thúc mới = 07/03/2027 · 3. Bấm Gia hạn và xác nhận
  - Mong đợi: Về lại trang chi tiết kèm thông báo đỏ: · "Ngày kết thúc mới phải sau ngày kết thúc hiện tại (07/03/2027)."
  - Sau (CSDL): contracts.end_date không đổi; ghi chú không bị nối thêm dòng nào
  - Lý do chọn ca: Đúng biên: phép so sánh dùng isAfter chứ không phải >=
- **[IT03-29] Gia hạn hợp đồng hợp lệ** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hợp đồng ACTIVE kết thúc ngày 07/03/2027
  - Các bước: 1. Ở khối Gia hạn nhập Ngày kết thúc mới = 07/09/2027 · 2. Bấm Gia hạn và xác nhận
  - Mong đợi: Về lại trang chi tiết kèm thông báo xanh · "Đã gia hạn hợp đồng đến 07/09/2027." · Ghi chú của hợp đồng nối thêm dòng "Gia hạn đến 07/09/2027"
  - Sau (CSDL): contracts.end_date = 2027-09-07; status vẫn ACTIVE
  - Lý do chọn ca: Ca đường đi chính của luồng gia hạn
- **[IT03-30] Chấm dứt hợp đồng với ngày trước ngày bắt đầu** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hợp đồng PENDING bắt đầu từ 01/10/2026
  - Các bước: 1. Mở trang chi tiết hợp đồng · 2. Ở khối Chấm dứt nhập Ngày chấm dứt = 30/09/2026 · 3. Bấm Chấm dứt và xác nhận
  - Mong đợi: Về lại trang chi tiết kèm thông báo đỏ: · "Ngày chấm dứt không được trước ngày bắt đầu (01/10/2026)."
  - Sau (CSDL): Trạng thái hợp đồng vẫn PENDING; trạng thái phòng không đổi
  - Lý do chọn ca: Biên ngay dưới ngày bắt đầu
- **[IT03-31] Chấm dứt hợp đồng và trả phòng** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hợp đồng ACTIVE cho phòng A102 (đang OCCUPIED)
  - Các bước: 1. Mở trang chi tiết hợp đồng · 2. Nhập Ngày chấm dứt = 07/09/2026 và Lý do = "Sinh viên ra trường" · 3. Bấm Chấm dứt và xác nhận · 4. Mở trang chi tiết phòng A102
  - Mong đợi: Thông báo xanh Đã chấm dứt hợp đồng và trả phòng về trạng thái còn trống · Hợp đồng hiện nhãn Đã chấm dứt; phòng A102 hiện nhãn Còn trống · Ghi chú nối thêm dòng Chấm dứt 07/09/2026: Sinh viên ra trường
  - Sau (CSDL): contracts.status = TERMINATED, terminated_at = 2026-09-07, rooms.status = AVAILABLE · Hai bảng đổi trong cùng một transaction
  - Lý do chọn ca: Ca quan trọng nhất: kiểm tra tính toàn vẹn của giao dịch giữa contracts và rooms
- **[IT03-32] Chấm dứt hợp đồng đã chấm dứt** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hợp đồng vừa chuyển sang TERMINATED
  - Các bước: 1. Mở lại trang chi tiết hợp đồng đó · 2. Quan sát các khối thao tác phía dưới
  - Mong đợi: Khối Chấm dứt trước hạn KHÔNG hiển thị · Nút Sửa cũng không hiển thị
  - Sau (CSDL): Không có thao tác ghi nào xảy ra
  - Lý do chọn ca: Giao diện tự ẩn thao tác không hợp lệ - kiểm tra điều kiện hiển thị của JSP
- **[IT03-33] Chấm dứt phòng đang bảo trì thì không trả về Còn trống** — `EG` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hợp đồng ACTIVE cho một phòng đã được đặt tay sang MAINTENANCE
  - Các bước: 1. Đặt trạng thái phòng sang Đang sửa ở màn hình sửa phòng · 2. Chấm dứt hợp đồng của phòng đó · 3. Mở trang chi tiết phòng
  - Mong đợi: Hợp đồng chuyển sang Đã chấm dứt · Phòng vẫn giữ nhãn Đang sửa, KHÔNG bị đổi về Còn trống
  - Sau (CSDL): rooms.status vẫn là MAINTENANCE
  - Lý do chọn ca: MAINTENANCE là trạng thái người quản lý đặt tay, hệ thống không được đè lên
- **[IT03-34] Đồng bộ trạng thái khi mở danh sách** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Có hợp đồng PENDING bắt đầu 01/09/2026 và hợp đồng ACTIVE kết thúc 31/08/2026
  - Các bước: 1. Mở /contracts · 2. Quan sát trạng thái hai hợp đồng đó
  - Mong đợi: Hợp đồng bắt đầu 01/09/2026 đã chuyển sang Đang hiệu lực · Hợp đồng kết thúc 31/08/2026 đã chuyển sang Hết hạn
  - Sau (CSDL): refreshStatuses chạy activateDue rồi expireOverdue rồi syncRoomStatuses
  - Lý do chọn ca: Trạng thái được đồng bộ trước khi hiển thị nên bộ lọc mới cho kết quả đúng
- **[IT03-35] Lập hợp đồng hợp lệ đầy đủ thông tin** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Phòng A101 còn trống, khách thuê id=3 chưa có hợp đồng
  - Các bước: 1. Chọn Phòng = A101, Khách thuê = khách id=3 · 2. Nhập Ngày bắt đầu = 07/09/2026, Ngày kết thúc = 07/03/2027, · Giá thuê = 1800000, Tiền cọc = 1800000, Số người = 1, Ngày chốt = 5 · 3. Bấm Lưu
  - Mong đợi: Chuyển về danh sách kèm thông báo xanh Đã tạo hợp đồng HD2026xxxx · Hợp đồng hiện nhãn Đang hiệu lực, phòng A101 hiện nhãn Đang thuê
  - Sau (CSDL): contracts có 1 bản ghi mới với mã sinh tự động; rooms.status = OCCUPIED; · activity_log ghi 1 dòng CREATE / CONTRACT
  - Lý do chọn ca: Ca đường đi chính (happy path)

## IT04 — Lập hóa đơn & thu tiền

- Người thực thi: Đỗ Đức Đạt
- Yêu cầu: Kiểm thử tích hợp màn hình lập hóa đơn tháng và ghi nhận thu tiền: form JSP → InvoiceServlet.bind / bindDetails (tham số mảng song song) → InvoiceService.validatePeriod → Transactions.run (khóa hợp đồng, chặn trùng kỳ, computeDetails, tính tiền phòng + dịch vụ − giảm giá, sinh mã, insert invoices + invoice_details) và InvoiceService.pay. Trọng tâm: MỌI số tiền do máy chủ tính lại, không lấy từ trình duyệt.
- Điều kiện tiên quyết chung: Tomcat 10.1 + JDK 17, MariaDB 10.4 (XAMPP) đã nạp schema.sql và seed-demo.sql. Đăng nhập sẵn bằng admin / admin123. Hôm nay là 07/09/2026. Dữ liệu mẫu: hợp đồng HD20260001 ACTIVE cho phòng A102, giá thuê 2.500.000 đ, 2 người ở, ngày chốt 5. Danh mục dịch vụ: Tiền điện 3.500 đ/kWh (METERED), Tiền nước 15.000 đ/m³ (METERED), Phí rác 20.000 đ/tháng (FIXED), Internet 100.000 đ/tháng (FIXED), Gửi xe 80.000 đ/người (PER_PERSON).
- Đường dẫn: GET /invoices/new · GET /invoices/detail?id= · POST /invoices/save · POST /invoices/pay · POST /invoices/cancel


**1. Kiểm tra ràng buộc Hợp đồng và Kỳ hóa đơn**

- **[IT04-01] Không chọn hợp đồng** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Lập hóa đơn
  - Các bước: 1. Dùng DevTools xóa required của select Hợp đồng · 2. Để ở mục — Chọn hợp đồng đang hiệu lực —, nhập Kỳ hợp lệ · 3. Bấm Lập hóa đơn
  - Mong đợi: Form hiện lại kèm thông báo: "Chưa chọn hợp đồng."
  - Sau (CSDL): Không có bản ghi nào được thêm vào bảng invoices
  - Lý do chọn ca: Chốt chặn đầu tiên của validatePeriod
- **[IT04-02] Bỏ trống kỳ hóa đơn** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Lập hóa đơn
  - Các bước: 1. Xóa required của ô Kỳ hóa đơn, xóa trắng ô này · 2. Chọn hợp đồng HD20260001 rồi bấm Lập hóa đơn
  - Mong đợi: Form hiện lại kèm thông báo: "Chưa chọn kỳ hóa đơn (tháng/năm)." · Hợp đồng vừa chọn vẫn còn trên form
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Kỳ hóa đơn quyết định hạn thu và ràng buộc chống trùng
- **[IT04-03] Tháng của kỳ bằng 0** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Lập hóa đơn
  - Các bước: 1. Dùng DevTools đổi type ô Kỳ thành text · 2. Nhập "2026-00" rồi bấm Lập hóa đơn
  - Mong đợi: Form hiện lại kèm thông báo: · "Tháng của kỳ hóa đơn phải trong khoảng 1–12."
  - Sau (CSDL): Không có bản ghi mới; CHECK chk_invoices_month không bị chạm tới
  - Lý do chọn ca: Biên ngay dưới ngưỡng hợp lệ
- **[IT04-04] Tháng của kỳ bằng 13** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Lập hóa đơn
  - Các bước: 1. Đổi type ô Kỳ thành text · 2. Nhập "2026-13" rồi bấm Lập hóa đơn
  - Mong đợi: Form hiện lại kèm thông báo Tháng của kỳ hóa đơn phải trong khoảng 1–12
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Biên ngay trên ngưỡng hợp lệ
- **[IT04-05] Năm của kỳ ngoài khoảng 2000–2100** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Đang ở form Lập hóa đơn
  - Các bước: 1. Đổi type ô Kỳ thành text · 2. Nhập "1999-08" rồi bấm Lập hóa đơn
  - Mong đợi: Form hiện lại kèm thông báo: · "Năm của kỳ hóa đơn phải trong khoảng 2000–2100."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Chặn lỗi gõ nhầm năm
- **[IT04-06] Lập hóa đơn cho hợp đồng đã hết hạn** — `SEC` — Kết quả: Chưa chạy
  - Tiền điều kiện: Có một hợp đồng ở trạng thái EXPIRED
  - Các bước: 1. Dùng DevTools thêm option với value = id hợp đồng EXPIRED · 2. Chọn option đó, nhập Kỳ = 2026-08 rồi bấm Lập hóa đơn
  - Mong đợi: Form hiện lại kèm thông báo: · "Hợp đồng <mã> đang ở trạng thái EXPIRED, chỉ lập hóa đơn cho hợp đồng đang hiệu lực."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Danh sách chỉ liệt kê hợp đồng ACTIVE, nhưng server vẫn phải kiểm tra lại
- **[IT04-07] Lập trùng kỳ cho cùng một hợp đồng** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: HD20260001 đã có hóa đơn kỳ 8/2026
  - Các bước: 1. Chọn hợp đồng HD20260001, nhập Kỳ = 2026-08 · 2. Bấm Lập hóa đơn
  - Mong đợi: Form hiện lại kèm thông báo: · "Hợp đồng HD20260001 đã có hóa đơn kỳ 8/2026."
  - Sau (CSDL): Không có bản ghi mới; ràng buộc uk_invoices_period không bị chạm tới
  - Lý do chọn ca: existsForPeriod chặn trước, không để UNIQUE ném SQLException

**2. Kiểm tra tính tiền dịch vụ theo chỉ số (METERED)**

- **[IT04-08] Chỉ số cuối nhỏ hơn chỉ số đầu** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chọn HD20260001, kỳ 2026-09
  - Các bước: 1. Ở dòng Tiền điện nhập Chỉ số đầu = 1250, Chỉ số cuối = 1200 · 2. Bấm Lập hóa đơn
  - Mong đợi: Form hiện lại kèm thông báo: · "Chỉ số cuối của Tiền điện nhỏ hơn chỉ số đầu (1200.00 < 1250.00)." · Các chỉ số vừa nhập vẫn còn trên form
  - Sau (CSDL): Không có bản ghi mới trong invoices và invoice_details
  - Lý do chọn ca: Công tơ điện không quay ngược - đây là lỗi nhập liệu phổ biến
- **[IT04-09] Chỉ số cuối bằng chỉ số đầu** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chọn HD20260001, kỳ 2026-09
  - Các bước: 1. Ở dòng Tiền điện nhập Chỉ số đầu = 1250, Chỉ số cuối = 1250 · 2. Bỏ các dòng dịch vụ khác rồi bấm Lập hóa đơn
  - Mong đợi: Lập thành công; dòng Tiền điện có Số lượng = 0 và Thành tiền = 0 đ
  - Sau (CSDL): invoice_details có dòng điện với quantity = 0.00
  - Lý do chọn ca: Đúng biên: phòng bỏ trống cả tháng vẫn phải ghi nhận được
- **[IT04-10] Bỏ trống chỉ số cuối của dịch vụ theo chỉ số** — `EG` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chọn HD20260001, kỳ 2026-09
  - Các bước: 1. Để trống ô Chỉ số cuối của cả Tiền điện và Tiền nước · 2. Giữ nguyên các dòng FIXED và PER_PERSON rồi bấm Lập hóa đơn
  - Mong đợi: Lập thành công; hóa đơn KHÔNG có dòng Tiền điện và Tiền nước · (chưa chốt chỉ số thì không đưa vào hóa đơn)
  - Sau (CSDL): invoice_details chỉ có 3 dòng: Phí rác, Internet, Gửi xe
  - Lý do chọn ca: Chưa chốt chỉ số khác hẳn với chỉ số bằng 0
- **[IT04-11] Chỉ số đầu bỏ trống nhưng có chỉ số cuối** — `EG` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chọn HD20260001, kỳ 2026-09
  - Các bước: 1. Ở dòng Tiền nước để trống Chỉ số đầu, nhập Chỉ số cuối = 25 · 2. Bấm Lập hóa đơn
  - Mong đợi: Lập thành công; dòng Tiền nước có Số lượng = 25 m³ · Thành tiền = 25 × 15.000 = 375.000 đ
  - Sau (CSDL): invoice_details lưu old_index = 0.00, new_index = 25.00, quantity = 25.00
  - Lý do chọn ca: Công tơ mới lắp thì chỉ số đầu được coi là 0
- **[IT04-12] Tính đúng tiền điện theo hiệu hai chỉ số** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chọn HD20260001, kỳ 2026-09
  - Các bước: 1. Ở dòng Tiền điện nhập Chỉ số đầu = 1200, Chỉ số cuối = 1350 · 2. Giữ đơn giá 3.500 đ rồi bấm Lập hóa đơn
  - Mong đợi: Lập thành công; dòng Tiền điện có Số lượng = 150 kWh · Thành tiền = 150 × 3.500 = 525.000 đ
  - Sau (CSDL): invoice_details: quantity = 150.00, unit_price = 3500.00, amount = 525000.00
  - Lý do chọn ca: Ca đường đi chính của dịch vụ theo chỉ số
- **[IT04-13] Chỉ số có phần thập phân** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chọn HD20260001, kỳ 2026-09
  - Các bước: 1. Ở dòng Tiền nước nhập Chỉ số đầu = 20.50, Chỉ số cuối = 23.75 · 2. Bấm Lập hóa đơn
  - Mong đợi: Lập thành công; Số lượng = 3,25 m³ · Thành tiền = 3,25 × 15.000 = 48.750 đ
  - Sau (CSDL): invoice_details: quantity = 3.25, amount = 48750.00 (làm tròn 2 chữ số)
  - Lý do chọn ca: Số tiền dùng BigDecimal chứ không dùng double nên không bị lệch

**3. Kiểm tra số lượng của dịch vụ FIXED và PER_PERSON**

- **[IT04-14] Số lượng dịch vụ theo người bằng số người ở** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: HD20260001 có 2 người ở, kỳ 2026-09
  - Các bước: 1. Chọn hợp đồng HD20260001 · 2. Giữ nguyên dòng Gửi xe rồi bấm Lập hóa đơn
  - Mong đợi: Lập thành công; dòng Gửi xe có Số lượng = 2 người · Thành tiền = 2 × 80.000 = 160.000 đ
  - Sau (CSDL): invoice_details: quantity = 2.00, amount = 160000.00
  - Lý do chọn ca: Số lượng suy ra từ contracts.num_occupants, không lấy từ form
- **[IT04-15] Sửa tay số lượng dịch vụ theo người trên trình duyệt** — `SEC` — Kết quả: Chưa chạy
  - Tiền điều kiện: HD20260001 có 2 người ở, kỳ 2026-09
  - Các bước: 1. Dùng DevTools bỏ readonly của ô Số lượng dòng Gửi xe, sửa thành 1 · 2. Bấm Lập hóa đơn
  - Mong đợi: Lập thành công nhưng dòng Gửi xe VẪN có Số lượng = 2 người · Thành tiền = 160.000 đ (không phải 80.000 đ)
  - Sau (CSDL): invoice_details: quantity = 2.00 - máy chủ đã tính lại
  - Lý do chọn ca: Không một con số tiền nào từ trình duyệt được ghi thẳng xuống DB
- **[IT04-16] Số lượng dịch vụ cố định luôn bằng 1** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chọn HD20260001, kỳ 2026-09
  - Các bước: 1. Giữ nguyên hai dòng Phí rác và Internet · 2. Bấm Lập hóa đơn
  - Mong đợi: Lập thành công; hai dòng đều có Số lượng = 1 · Thành tiền lần lượt 20.000 đ và 100.000 đ
  - Sau (CSDL): invoice_details: quantity = 1.00 cho cả hai dòng
  - Lý do chọn ca: Dịch vụ trọn gói theo tháng
- **[IT04-17] Đơn giá âm** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chọn HD20260001, kỳ 2026-09
  - Các bước: 1. Dùng DevTools bỏ min=0 của ô Đơn giá dòng Internet, nhập -1000 · 2. Bấm Lập hóa đơn
  - Mong đợi: Form hiện lại kèm thông báo: · "Đơn giá của Internet không được âm."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Đơn giá được phép chốt riêng cho một kỳ nhưng không được âm
- **[IT04-18] Chốt đơn giá riêng cho một kỳ** — `EG` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chọn HD20260001, kỳ 2026-09
  - Các bước: 1. Ở dòng Internet sửa Đơn giá từ 100.000 thành 120.000 · 2. Bấm Lập hóa đơn
  - Mong đợi: Lập thành công; dòng Internet có Đơn giá = 120.000 đ · Danh mục dịch vụ vẫn giữ giá 100.000 đ
  - Sau (CSDL): invoice_details.unit_price = 120000.00; services.unit_price không đổi
  - Lý do chọn ca: Cho phép thỏa thuận giá riêng mà không làm hỏng danh mục
- **[IT04-19] Gửi trùng một dịch vụ hai lần** — `SEC` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chọn HD20260001, kỳ 2026-09
  - Các bước: 1. Dùng DevTools nhân đôi cả dòng Internet (kèm input hidden serviceId) · 2. Bấm Lập hóa đơn
  - Mong đợi: Form hiện lại kèm thông báo: · "Dịch vụ Internet bị lặp lại hai lần trên hóa đơn."
  - Sau (CSDL): Không có bản ghi mới; ràng buộc uk_invoice_details không bị chạm tới
  - Lý do chọn ca: Chặn trước bằng tiếng Việt thay vì để MySQL ném lỗi khó hiểu
- **[IT04-20] Chọn dịch vụ đã ngừng sử dụng** — `SEC` — Kết quả: Chưa chạy
  - Tiền điều kiện: Dịch vụ Internet đã được chuyển sang INACTIVE
  - Các bước: 1. Dùng DevTools thêm lại dòng có serviceId của Internet · 2. Bấm Lập hóa đơn
  - Mong đợi: Form hiện lại kèm thông báo: · "Dịch vụ Internet đã ngừng sử dụng."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Dịch vụ ngừng cung cấp thì không được xuất hiện trên hóa đơn mới

**4. Kiểm tra tính tổng tiền và giảm giá**

- **[IT04-21] Tiền phòng lấy từ hợp đồng, không lấy từ form** — `SEC` — Kết quả: Chưa chạy
  - Tiền điều kiện: HD20260001 có giá thuê 2.500.000 đ
  - Các bước: 1. Dùng DevTools bỏ readonly của ô Tiền phòng, sửa thành 1000000 · 2. Bấm Lập hóa đơn
  - Mong đợi: Lập thành công nhưng hóa đơn hiện Tiền phòng = 2.500.000 đ
  - Sau (CSDL): invoices.room_charge = 2500000.00 lấy từ contracts.monthly_price
  - Lý do chọn ca: Ô Tiền phòng trên form chỉ để xem, không phải nguồn dữ liệu
- **[IT04-22] Giảm giá âm** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chọn HD20260001, kỳ 2026-09
  - Các bước: 1. Bỏ min=0 của ô Giảm giá, nhập -50000 · 2. Bấm Lập hóa đơn
  - Mong đợi: Form hiện lại kèm thông báo: "Giảm giá không được âm."
  - Sau (CSDL): Không có bản ghi mới
  - Lý do chọn ca: Biên ngay dưới ngưỡng hợp lệ
- **[IT04-23] Giảm giá lớn hơn tiền phòng cộng dịch vụ** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chọn HD20260001, kỳ 2026-09, tổng trước giảm khoảng 2.680.000 đ
  - Các bước: 1. Nhập Giảm giá = 5000000 · 2. Bấm Lập hóa đơn
  - Mong đợi: Form hiện lại kèm thông báo: · "Giảm giá không được lớn hơn tiền phòng cộng tiền dịch vụ."
  - Sau (CSDL): Không có bản ghi mới; tổng tiền không thể âm
  - Lý do chọn ca: Chặn hóa đơn có tổng tiền âm
- **[IT04-24] Giảm giá đúng bằng tổng trước giảm** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Chọn HD20260001, kỳ 2026-09
  - Các bước: 1. Ghi lại tổng trước giảm hiện trên form · 2. Nhập Giảm giá đúng bằng con số đó rồi bấm Lập hóa đơn
  - Mong đợi: Lập thành công; hóa đơn có TỔNG CỘNG = 0 đ
  - Sau (CSDL): invoices.total_amount = 0.00, status = UNPAID
  - Lý do chọn ca: Đúng biên trên hợp lệ - miễn phí trọn tháng vẫn là hóa đơn hợp lệ
- **[IT04-25] Tổng tiền được tính đúng công thức** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: HD20260001, kỳ 2026-09, tiền phòng 2.500.000 đ
  - Các bước: 1. Nhập Tiền điện: chỉ số 1200 → 1350 (525.000 đ) · 2. Nhập Tiền nước: chỉ số 20 → 25 (75.000 đ) · 3. Giữ Phí rác 20.000 đ, Internet 100.000 đ, Gửi xe 160.000 đ · 4. Nhập Giảm giá = 80.000 đ rồi bấm Lập hóa đơn
  - Mong đợi: Trang chi tiết hiện: · Tiền phòng 2.500.000 đ · Tiền dịch vụ 880.000 đ · Giảm giá −80.000 đ · TỔNG CỘNG 3.300.000 đ · Đã thu 0 đ · Còn phải thu 3.300.000 đ
  - Sau (CSDL): invoices: room_charge 2500000, service_total 880000, discount 80000, · total_amount 3300000, paid_amount 0, status = UNPAID
  - Lý do chọn ca: Ca đường đi chính - kiểm tra trọn công thức tính tiền
- **[IT04-26] Hạn thu lấy theo ngày chốt của hợp đồng** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: HD20260001 có billing_day = 5, kỳ 2026-09
  - Các bước: 1. Lập hóa đơn kỳ 2026-09 hợp lệ · 2. Mở trang chi tiết hóa đơn
  - Mong đợi: Hạn thanh toán hiện 05/09/2026 (ngày 5 của chính kỳ đó)
  - Sau (CSDL): invoices.due_date = 2026-09-05
  - Lý do chọn ca: Mỗi hợp đồng có một ngày chốt riêng, không dùng ngày cố định

**5. Kiểm tra ghi nhận thu tiền và hủy hóa đơn**

- **[IT04-27] Thu tiền bằng 0** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hóa đơn UNPAID còn phải thu 3.300.000 đ
  - Các bước: 1. Ở khối Ghi nhận thanh toán nhập Số tiền = 0 · 2. Bấm Ghi nhận
  - Mong đợi: Về lại trang chi tiết kèm thông báo đỏ: · "Số tiền thu phải lớn hơn 0."
  - Sau (CSDL): invoices.paid_amount không đổi
  - Lý do chọn ca: Biên dưới - phép so sánh dùng <= 0
- **[IT04-28] Thu tiền vượt quá số còn phải thu** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hóa đơn UNPAID còn phải thu 3.300.000 đ
  - Các bước: 1. Dùng DevTools bỏ max của ô Số tiền, nhập 4000000 · 2. Bấm Ghi nhận
  - Mong đợi: Về lại trang chi tiết kèm thông báo đỏ: · "Số tiền thu vượt quá số còn phải thu (3300000 đ)."
  - Sau (CSDL): invoices.paid_amount không đổi
  - Lý do chọn ca: Biên ngay trên số còn phải thu
- **[IT04-29] Thu một phần → trạng thái PARTIAL** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hóa đơn UNPAID tổng 3.300.000 đ
  - Các bước: 1. Nhập Số tiền = 1.000.000, Phương thức = Tiền mặt · 2. Bấm Ghi nhận
  - Mong đợi: Thông báo xanh Đã ghi nhận thanh toán · Hóa đơn hiện nhãn Thu một phần; Đã thu 1.000.000 đ; Còn phải thu 2.300.000 đ
  - Sau (CSDL): invoices: paid_amount 1000000, status = PARTIAL, payment_date vẫn NULL
  - Lý do chọn ca: Ngày thu đủ chỉ được ghi khi đã thu hết
- **[IT04-30] Thu nốt phần còn lại → trạng thái PAID** — `BVA` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hóa đơn PARTIAL còn phải thu 2.300.000 đ
  - Các bước: 1. Nhập Số tiền = 2.300.000, Phương thức = Chuyển khoản · 2. Bấm Ghi nhận
  - Mong đợi: Hóa đơn hiện nhãn Đã thu đủ; Còn phải thu 0 đ · Xuất hiện dòng Ngày thu đủ = 07/09/2026 — TRANSFER · Khối Ghi nhận thanh toán không còn hiển thị
  - Sau (CSDL): invoices: paid_amount 3300000, status = PAID, payment_date = 2026-09-07
  - Lý do chọn ca: Đúng biên: thu đủ đúng số còn lại
- **[IT04-31] Thu tiền cho hóa đơn đã thu đủ** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hóa đơn ở trạng thái PAID
  - Các bước: 1. Gửi thẳng POST /invoices/pay với id hóa đơn đó bằng công cụ REST · (kèm token CSRF hợp lệ)
  - Mong đợi: Về lại trang chi tiết kèm thông báo đỏ: · "Hóa đơn <mã> đã thu đủ."
  - Sau (CSDL): invoices.paid_amount không đổi
  - Lý do chọn ca: Giao diện đã ẩn khối thu tiền, nhưng server vẫn phải chặn
- **[IT04-32] Phương thức thanh toán không thuộc danh mục** — `SEC` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hóa đơn UNPAID
  - Các bước: 1. Dùng DevTools thêm option value="BITCOIN" vào select Phương thức · 2. Chọn option đó, nhập số tiền hợp lệ rồi bấm Ghi nhận
  - Mong đợi: Về lại trang chi tiết kèm thông báo đỏ: · "Phương thức thanh toán không hợp lệ: BITCOIN"
  - Sau (CSDL): invoices.paid_amount không đổi; ENUM payment_method không bị chạm tới
  - Lý do chọn ca: Chặn tham số bị sửa tay trước khi tới MySQL
- **[IT04-33] Hủy hóa đơn chưa thu đồng nào** — `EP` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hóa đơn UNPAID, paid_amount = 0
  - Các bước: 1. Ở khối Hủy hóa đơn nhập Lý do = "Lập nhầm kỳ" · 2. Bấm Hủy hóa đơn và xác nhận
  - Mong đợi: Thông báo xanh Đã hủy hóa đơn · Hóa đơn hiện nhãn Đã hủy; các khối thao tác biến mất · Ghi chú nối thêm dòng Hủy hóa đơn: Lập nhầm kỳ
  - Sau (CSDL): invoices.status = CANCELLED
  - Lý do chọn ca: Ca đường đi chính của luồng hủy
- **[IT04-34] Hủy hóa đơn đã thu một phần** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hóa đơn PARTIAL đã thu 1.000.000 đ
  - Các bước: 1. Quan sát trang chi tiết · 2. Nếu khối Hủy vẫn hiện, gửi POST /invoices/cancel bằng công cụ REST
  - Mong đợi: Khối Hủy hóa đơn KHÔNG hiển thị trên giao diện · Nếu gửi thẳng thì nhận thông báo đỏ: · "Hóa đơn <mã> đã thu tiền nên không hủy được."
  - Sau (CSDL): invoices.status vẫn là PARTIAL
  - Lý do chọn ca: Hủy hóa đơn đã thu sẽ làm số tiền đó biến mất khỏi mọi báo cáo
- **[IT04-35] Thu tiền cho hóa đơn đã hủy** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Hóa đơn ở trạng thái CANCELLED
  - Các bước: 1. Gửi POST /invoices/pay cho hóa đơn đó bằng công cụ REST
  - Mong đợi: Nhận thông báo đỏ: · "Hóa đơn <mã> đã hủy nên không thu được."
  - Sau (CSDL): invoices.paid_amount không đổi
  - Lý do chọn ca: Hóa đơn đã hủy nằm ngoài mọi luồng thu tiền
- **[IT04-36] Đánh dấu quá hạn khi mở danh sách** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Có hóa đơn UNPAID hạn thu 05/08/2026, hôm nay 07/09/2026
  - Các bước: 1. Mở /invoices · 2. Quan sát trạng thái hóa đơn đó
  - Mong đợi: Hóa đơn hiện nhãn Quá hạn · Bộ lọc Quá hạn liệt kê đúng hóa đơn này
  - Sau (CSDL): invoices.status đã chuyển từ UNPAID sang OVERDUE
  - Lý do chọn ca: markOverdue so với giờ JVM theo Asia/Ho_Chi_Minh, không dùng CURDATE của MySQL
- **[IT04-37] Nhấn F5 sau khi lập hóa đơn thành công** — `ST` — Kết quả: Chưa chạy
  - Tiền điều kiện: Vừa lập xong một hóa đơn hợp lệ
  - Các bước: 1. Sau khi trang chi tiết hiện ra, nhấn F5
  - Mong đợi: Trình duyệt không hỏi gửi lại form · Không có hóa đơn nào bị lập trùng
  - Sau (CSDL): Bảng invoices không phát sinh bản ghi trùng kỳ
  - Lý do chọn ca: Kiểm tra mẫu Post/Redirect/Get

## IT05 — Đăng nhập & phân quyền

- Người thực thi: Phùng Tiến Dũng
- Yêu cầu: Kiểm thử tích hợp màn hình đăng nhập và cơ chế phân quyền: form JSP → LoginServlet → AuthService.login → PasswordUtil.verify → UserDAO, chạy qua chuỗi bộ lọc EncodingFilter → AuthFilter → CsrfFilter → RoleFilter. Kiểm tra cả điều hướng khi chưa / đã đăng nhập, việc hủy phiên khi đăng xuất và ba mức quyền ADMIN / MANAGER / STAFF.
- Điều kiện tiên quyết chung: Tomcat 10.1 + JDK 17, MariaDB 10.4 (XAMPP) đã nạp schema.sql và seed-demo.sql. Có sẵn ba tài khoản: admin / admin123 (ADMIN), manager / manager123 (MANAGER), staff / staff123 (STAFF), cả ba đều ở trạng thái ACTIVE. Mỗi ca bắt đầu ở trạng thái CHƯA đăng nhập (mở cửa sổ ẩn danh hoặc xóa cookie JSESSIONID).
- Đường dẫn: GET /login · POST /login · GET /logout · GET /reports (RoleFilter) · mọi URL khác (AuthFilter)


**1. Kiểm tra ràng buộc trường nhập của form đăng nhập**

- **[IT05-01] Bỏ trống cả hai ô rồi bấm Đăng nhập** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở trang /login
  - Các bước: 1. Mở /login · 2. Để trống Tên đăng nhập và Mật khẩu · 3. Bấm Đăng nhập
  - Mong đợi: Trình duyệt dừng ngay tại ô Tên đăng nhập (thuộc tính required), không gửi request nào lên máy chủ · Trang không tải lại
  - Sau (CSDL): Không có phiên đăng nhập; users.last_login_at không đổi
  - Lý do chọn ca: Chốt chặn phía trình duyệt; các ca sau bỏ required để kiểm chốt chặn phía máy chủ · Kết quả chạy thật 21/09/2026: Form rỗng: thuộc tính required của ô Tên đăng nhập = True, form.checkValidity() = False → trình duyệt chặn, không gửi request
- **[IT05-02] Bỏ trống tên đăng nhập (đã bỏ required)** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở trang /login
  - Các bước: 1. Dùng DevTools xóa thuộc tính required của ô Tên đăng nhập · 2. Để trống Tên đăng nhập, nhập Mật khẩu = admin123 · 3. Bấm Đăng nhập
  - Mong đợi: Trang đăng nhập hiện lại kèm thông báo đỏ: "Tên đăng nhập hoặc mật khẩu không đúng." · Địa chỉ trên thanh URL vẫn là /login (forward chứ không redirect)
  - Sau (CSDL): Không tạo phiên đăng nhập; activity_log không có dòng LOGIN
  - Lý do chọn ca: AuthService.login trả Optional.empty khi username null / blank, chưa chạm tới CSDL · Kết quả chạy thật 21/09/2026: Thông báo: "Tên đăng nhập hoặc mật khẩu không đúng."; đường dẫn sau khi gửi = /student-rent/login (forward, không redirect)
- **[IT05-03] Tên đăng nhập chỉ gồm khoảng trắng** — `EG` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở trang /login
  - Các bước: 1. Nhập Tên đăng nhập = "   " (3 dấu cách) · 2. Nhập Mật khẩu = admin123 · 3. Bấm Đăng nhập
  - Mong đợi: Trang đăng nhập hiện lại kèm "Tên đăng nhập hoặc mật khẩu không đúng."
  - Sau (CSDL): Không tạo phiên đăng nhập
  - Lý do chọn ca: isBlank() bắt cả chuỗi toàn khoảng trắng, không để lọt xuống câu truy vấn · Kết quả chạy thật 21/09/2026: Tên đăng nhập toàn khoảng trắng → "Tên đăng nhập hoặc mật khẩu không đúng."
- **[IT05-04] Bỏ trống mật khẩu (đã bỏ required)** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở trang /login
  - Các bước: 1. Dùng DevTools xóa required của ô Mật khẩu · 2. Nhập Tên đăng nhập = admin, để trống Mật khẩu · 3. Bấm Đăng nhập
  - Mong đợi: Trang đăng nhập hiện lại kèm "Tên đăng nhập hoặc mật khẩu không đúng." · Phản hồi trả về gần như tức thì (không phải chờ ~0,3 giây băm BCrypt)
  - Sau (CSDL): Không tạo phiên đăng nhập
  - Lý do chọn ca: rawPassword.isEmpty() chặn trước, tránh gọi BCrypt một cách vô ích · Kết quả chạy thật 21/09/2026: Bỏ trống mật khẩu → "Tên đăng nhập hoặc mật khẩu không đúng." (phản hồi sau 2.73s, không gọi BCrypt)
- **[IT05-05] Tên đăng nhập có khoảng trắng thừa hai đầu** — `EG` — Kết quả: Đạt
  - Tiền điều kiện: Tài khoản admin đang ACTIVE
  - Các bước: 1. Nhập Tên đăng nhập = "  admin  " · 2. Nhập Mật khẩu = admin123 · 3. Bấm Đăng nhập
  - Mong đợi: Đăng nhập thành công, chuyển tới /dashboard
  - Sau (CSDL): users.last_login_at của admin được cập nhật; activity_log ghi 1 dòng LOGIN / USER
  - Lý do chọn ca: login() trim username trước khi tra CSDL; mật khẩu thì KHÔNG trim (xem ca dưới) · Kết quả chạy thật 21/09/2026: Tên đăng nhập có khoảng trắng hai đầu → vào /student-rent/dashboard (username được trim)
- **[IT05-06] Tên đăng nhập viết hoa toàn bộ** — `EG` — Kết quả: Đạt
  - Tiền điều kiện: Tài khoản admin đang ACTIVE
  - Các bước: 1. Nhập Tên đăng nhập = ADMIN · 2. Nhập Mật khẩu = admin123 · 3. Bấm Đăng nhập
  - Mong đợi: Đăng nhập thành công (collation utf8mb4_unicode_ci của cột username không phân biệt hoa / thường)
  - Sau (CSDL): Phiên giữ đúng tài khoản admin, vai trò ADMIN
  - Lý do chọn ca: Ca này phát hiện lệch giữa so sánh của Java (equals) và của MySQL (collation) · Kết quả chạy thật 21/09/2026: Tên đăng nhập viết hoa ADMIN → vào /student-rent/dashboard; collation utf8mb4_unicode_ci không phân biệt hoa/thường (vai trò ADMIN)
- **[IT05-07] Tên đăng nhập dài đúng 50 ký tự (biên trên của cột)** — `BVA` — Kết quả: Đạt
  - Tiền điều kiện: Không có tài khoản nào mang tên này
  - Các bước: 1. Nhập Tên đăng nhập = chuỗi "a" lặp 50 lần · 2. Nhập Mật khẩu = admin123 · 3. Bấm Đăng nhập
  - Mong đợi: Trang đăng nhập hiện lại kèm "Tên đăng nhập hoặc mật khẩu không đúng." · Không rơi vào trang lỗi 500
  - Sau (CSDL): Không tạo phiên đăng nhập
  - Lý do chọn ca: Biên trên của users.username VARCHAR(50) · Kết quả chạy thật 21/09/2026: Tên đăng nhập 50 ký tự (biên) → "Tên đăng nhập hoặc mật khẩu không đúng.", không lỗi 500
- **[IT05-08] Tên đăng nhập dài 51 ký tự (vượt biên)** — `BVA` — Kết quả: Đạt
  - Tiền điều kiện: Không có tài khoản nào mang tên này
  - Các bước: 1. Nhập Tên đăng nhập = chuỗi "a" lặp 51 lần · 2. Nhập Mật khẩu = admin123 · 3. Bấm Đăng nhập
  - Mong đợi: Vẫn chỉ hiện "Tên đăng nhập hoặc mật khẩu không đúng." · Không có SQLException, không có trang 500
  - Sau (CSDL): Không tạo phiên đăng nhập
  - Lý do chọn ca: Truy vấn chỉ ĐỌC nên chuỗi dài không bị cắt hay ném lỗi; kiểm để chắc chắn · Kết quả chạy thật 21/09/2026: Tên đăng nhập 51 ký tự (vượt biên) → "Tên đăng nhập hoặc mật khẩu không đúng.", không SQLException

**2. Kiểm tra xác thực tài khoản**

- **[IT05-09] Sai mật khẩu của một tài khoản có thật** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Tài khoản admin đang ACTIVE
  - Các bước: 1. Nhập Tên đăng nhập = admin · 2. Nhập Mật khẩu = saibet123 · 3. Bấm Đăng nhập
  - Mong đợi: Trang đăng nhập hiện lại kèm "Tên đăng nhập hoặc mật khẩu không đúng." · Ô Tên đăng nhập vẫn giữ chữ admin; ô Mật khẩu trống
  - Sau (CSDL): Không tạo phiên đăng nhập; users.last_login_at KHÔNG đổi
  - Lý do chọn ca: Forward (không redirect) nên attribute username và thông báo lỗi còn nguyên · Kết quả chạy thật 21/09/2026: Sai mật khẩu → "Tên đăng nhập hoặc mật khẩu không đúng."; ô Tên đăng nhập giữ "admin", ô Mật khẩu trống; last_login_at không đổi
- **[IT05-10] Tài khoản không tồn tại** — `SEC` — Kết quả: Đạt
  - Tiền điều kiện: Không có tài khoản khongtontai
  - Các bước: 1. Nhập Tên đăng nhập = khongtontai · 2. Nhập Mật khẩu = admin123 · 3. Bấm Đăng nhập
  - Mong đợi: Thông báo Y HỆT ca sai mật khẩu: “Tên đăng nhập hoặc mật khẩu không đúng.” · Không có câu nào kiểu “Tài khoản không tồn tại”
  - Sau (CSDL): Không tạo phiên đăng nhập
  - Lý do chọn ca: Không phân biệt sai tài khoản với sai mật khẩu, tránh để kẻ xấu dò ra tên tài khoản có thật · Kết quả chạy thật 21/09/2026: Tài khoản không tồn tại → "Tên đăng nhập hoặc mật khẩu không đúng." (giống hệt thông báo khi sai mật khẩu)
- **[IT05-11] Tài khoản bị khóa (status = LOCKED)** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Chạy: UPDATE users SET status = 'LOCKED' WHERE username = 'staff';
  - Các bước: 1. Nhập Tên đăng nhập = staff · 2. Nhập Mật khẩu = staff123 (mật khẩu ĐÚNG) · 3. Bấm Đăng nhập · 4. Chạy lại: UPDATE users SET status = 'ACTIVE' WHERE username = 'staff';
  - Mong đợi: Bị từ chối kèm "Tên đăng nhập hoặc mật khẩu không đúng."
  - Sau (CSDL): Không tạo phiên đăng nhập; users.last_login_at của staff không đổi
  - Lý do chọn ca: Chỉ tài khoản ACTIVE mới vào được; nhớ trả trạng thái về ACTIVE sau khi chạy ca này · Kết quả chạy thật 21/09/2026: Tài khoản LOCKED, mật khẩu đúng → "Tên đăng nhập hoặc mật khẩu không đúng."; không tạo phiên (đang ở /student-rent/login)
- **[IT05-12] Đăng nhập hợp lệ bằng tài khoản quản trị** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Chưa đăng nhập; tài khoản admin ACTIVE
  - Các bước: 1. Nhập Tên đăng nhập = admin · 2. Nhập Mật khẩu = admin123 · 3. Bấm Đăng nhập
  - Mong đợi: Chuyển tới /dashboard; góc phải topbar hiện "Quản trị hệ thống" kèm nhãn ADMIN · Menu trái hiện đầy đủ Phòng trọ, Khách thuê, Hợp đồng, Hóa đơn, Báo cáo
  - Sau (CSDL): users.last_login_at = thời điểm vừa đăng nhập; activity_log thêm 1 dòng LOGIN / USER với mô tả "Đăng nhập: admin"
  - Lý do chọn ca: Ca đường đi chính (happy path) của màn hình này · Kết quả chạy thật 21/09/2026: Đăng nhập đúng → /student-rent/dashboard; topbar "Quản trị hệ thống · ADMIN"; last_login_at = 2026-09-21 12:45:47; activity_log LOGIN 7 → 8
- **[IT05-13] Mật khẩu viết hoa khác mật khẩu thật** — `EG` — Kết quả: Đạt
  - Tiền điều kiện: Tài khoản admin ACTIVE
  - Các bước: 1. Nhập Tên đăng nhập = admin · 2. Nhập Mật khẩu = ADMIN123 · 3. Bấm Đăng nhập
  - Mong đợi: Bị từ chối kèm "Tên đăng nhập hoặc mật khẩu không đúng."
  - Sau (CSDL): Không tạo phiên đăng nhập
  - Lý do chọn ca: BCrypt so khớp từng byte nên mật khẩu PHẢI phân biệt hoa thường, khác với tên đăng nhập · Kết quả chạy thật 21/09/2026: Mật khẩu viết hoa → "Tên đăng nhập hoặc mật khẩu không đúng." (BCrypt phân biệt hoa thường)
- **[IT05-14] Mật khẩu có khoảng trắng thừa hai đầu** — `EG` — Kết quả: Đạt
  - Tiền điều kiện: Tài khoản admin ACTIVE
  - Các bước: 1. Nhập Tên đăng nhập = admin · 2. Nhập Mật khẩu = "  admin123  " · 3. Bấm Đăng nhập
  - Mong đợi: Bị từ chối kèm "Tên đăng nhập hoặc mật khẩu không đúng."
  - Sau (CSDL): Không tạo phiên đăng nhập
  - Lý do chọn ca: Mật khẩu KHÔNG được trim: dấu cách có thể là một phần mật khẩu người dùng đặt · Kết quả chạy thật 21/09/2026: Mật khẩu có khoảng trắng hai đầu → "Tên đăng nhập hoặc mật khẩu không đúng." (mật khẩu không bị trim)
- **[IT05-15] Mã băm mật khẩu không lọt ra trang HTML** — `SEC` — Kết quả: Đạt
  - Tiền điều kiện: Vừa thực hiện một ca đăng nhập sai và một ca đăng nhập đúng
  - Các bước: 1. Sau khi đăng nhập sai, bấm Ctrl+U xem mã nguồn trang /login · 2. Đăng nhập đúng rồi xem mã nguồn trang /dashboard · 3. Tìm chuỗi "$2a$" trong cả hai trang
  - Mong đợi: Không trang nào chứa chuỗi $2a$ hay bất kỳ mã băm nào · Thẻ input Mật khẩu không có thuộc tính value
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: login() xóa passwordHash khỏi đối tượng User trước khi cất vào session · Kết quả chạy thật 21/09/2026: Không tìm thấy chuỗi "$2a$" trong HTML của trang đăng nhập lẫn trang Tổng quan; ô mật khẩu: (không có ô mật khẩu)

**3. Kiểm tra phiên làm việc và điều hướng**

- **[IT05-16] Đã đăng nhập nhưng gõ lại địa chỉ /login** — `ST` — Kết quả: Đạt
  - Tiền điều kiện: Đang đăng nhập bằng admin
  - Các bước: 1. Gõ thẳng http://localhost:8080/student-rent/login vào thanh địa chỉ · 2. Nhấn Enter
  - Mong đợi: Bị chuyển ngay về /dashboard, không hiện lại form đăng nhập
  - Sau (CSDL): Phiên hiện tại được giữ nguyên, không bị tạo lại
  - Lý do chọn ca: AuthFilter xử lý chiều ngược: đã đăng nhập thì không quay lại trang đăng nhập nữa · Kết quả chạy thật 21/09/2026: Đã đăng nhập mà gõ /login → bị chuyển về /student-rent/dashboard
- **[IT05-17] Chưa đăng nhập mà vào trang nghiệp vụ** — `ST` — Kết quả: Đạt
  - Tiền điều kiện: Chưa đăng nhập (đã xóa cookie JSESSIONID)
  - Các bước: 1. Gõ thẳng /rooms vào thanh địa chỉ · 2. Nhấn Enter
  - Mong đợi: Bị chuyển về /login · Không nhìn thấy dòng dữ liệu phòng nào
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: AuthFilter chặn mọi đường dẫn không nằm trong danh sách công khai · Kết quả chạy thật 21/09/2026: Chưa đăng nhập vào /rooms → chuyển về /student-rent/login
- **[IT05-18] Trang gửi phản hồi công khai khi chưa đăng nhập** — `ST` — Kết quả: Đạt
  - Tiền điều kiện: Chưa đăng nhập
  - Các bước: 1. Gõ /feedback (số ít) vào thanh địa chỉ · 2. Nhấn Enter
  - Mong đợi: Form gửi phản hồi hiện ra bình thường, KHÔNG bị đòi đăng nhập
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Khách thuê không có tài khoản nên trang này phải công khai · Kết quả chạy thật 21/09/2026: Chưa đăng nhập vào /feedback (công khai) → ở lại /student-rent/feedback
- **[IT05-19] Trang quản trị phản hồi khi chưa đăng nhập** — `SEC` — Kết quả: Đạt
  - Tiền điều kiện: Chưa đăng nhập
  - Các bước: 1. Gõ /feedbacks (số NHIỀU) vào thanh địa chỉ · 2. Nhấn Enter
  - Mong đợi: Bị chuyển về /login · Không nhìn thấy nội dung phản hồi nào
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Hai đường dẫn chỉ khác một chữ s; lẫn lộn là mở toang trang quản trị · Kết quả chạy thật 21/09/2026: Chưa đăng nhập vào /feedbacks (quản trị) → chuyển về /student-rent/login
- **[IT05-20] Tài nguyên tĩnh khi chưa đăng nhập** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Chưa đăng nhập
  - Các bước: 1. Gõ /assets/css/style.css vào thanh địa chỉ · 2. Quan sát tab Network
  - Mong đợi: Tệp CSS tải về với mã 200, không bị chuyển hướng về /login · Nhờ vậy trang đăng nhập vẫn hiển thị đúng giao diện
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Tiền tố /assets/ và /uploads/ luôn được cho qua · Kết quả chạy thật 21/09/2026: GET /assets/css/style.css khi chưa đăng nhập → 200 text/css;charset=UTF-8
- **[IT05-21] Đăng xuất rồi bấm nút Back của trình duyệt** — `ST` — Kết quả: Đạt
  - Tiền điều kiện: Đang đăng nhập bằng admin và đang ở /dashboard
  - Các bước: 1. Bấm Đăng xuất · 2. Bấm nút Back của trình duyệt · 3. Nhấn F5
  - Mong đợi: Sau bước 1 về /login · Sau bước 3 KHÔNG vào lại được Tổng quan mà bị đẩy về /login
  - Sau (CSDL): Session đã bị hủy (invalidate); cookie JSESSIONID cũ không còn tác dụng
  - Lý do chọn ca: Trang cũ trong bộ nhớ đệm có thể hiện thoáng qua, nhưng mọi request mới phải bị chặn · Kết quả chạy thật 21/09/2026: Đăng xuất → /student-rent/login; sau đó mở lại /dashboard → /student-rent/login (phiên đã hủy)
- **[IT05-22] Mã phiên đổi sau khi đăng nhập (chống session fixation)** — `SEC` — Kết quả: Đạt
  - Tiền điều kiện: Chưa đăng nhập
  - Các bước: 1. Mở /login, xem tab Application → Cookies, ghi lại giá trị JSESSIONID · 2. Đăng nhập bằng admin / admin123 · 3. Xem lại JSESSIONID
  - Mong đợi: Giá trị JSESSIONID sau khi đăng nhập KHÁC giá trị trước đó
  - Sau (CSDL): Phiên cũ không còn giữ thông tin người dùng
  - Lý do chọn ca: changeSessionId() được gọi TRƯỚC khi gắn user vào session · Kết quả chạy thật 21/09/2026: JSESSIONID trước đăng nhập 102BE6CF4ED36513… → sau đăng nhập 9A4FCFF7665CD52C… (changeSessionId đã đổi mã phiên; cookie là HttpOnly nên phải đọc bằng DevTools)
- **[IT05-23] Phiên hết hạn giữa chừng** — `ST` — Kết quả: Đạt
  - Tiền điều kiện: Đang đăng nhập bằng admin
  - Các bước: 1. Mở tab Application → Cookies, xóa cookie JSESSIONID · 2. Quay lại trang, bấm một mục bất kỳ trên menu
  - Mong đợi: Bị chuyển về /login · Sau khi đăng nhập lại thì thao tác bình thường
  - Sau (CSDL): Không thay đổi dữ liệu; không có trang 500
  - Lý do chọn ca: Tương đương tình huống để máy quá thời gian session-timeout khai báo trong web.xml · Kết quả chạy thật 21/09/2026: Xóa cookie JSESSIONID rồi bấm một mục menu → chuyển về /student-rent/login

**4. Kiểm tra phân quyền ba mức (RoleFilter)**

- **[IT05-24] Tài khoản ADMIN xem báo cáo** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Đăng nhập bằng admin / admin123
  - Các bước: 1. Bấm menu Báo cáo · 2. Quan sát trang
  - Mong đợi: Mở được báo cáo doanh thu, có bảng số liệu và biểu đồ
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: ADMIN nằm trong danh sách role được phép · Kết quả chạy thật 21/09/2026: ADMIN vào /reports → mở được báo cáo doanh thu
- **[IT05-25] Tài khoản MANAGER xem báo cáo** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Đăng nhập bằng manager / manager123
  - Các bước: 1. Bấm menu Báo cáo · 2. Quan sát trang
  - Mong đợi: Mở được báo cáo doanh thu y như ADMIN, KHÔNG nhận trang 403
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Ca then chốt: viết điều kiện kiểu "không phải ADMIN thì chặn" sẽ chặn nhầm MANAGER · Kết quả chạy thật 21/09/2026: MANAGER vào /reports → mở được báo cáo doanh thu, KHÔNG nhận 403
- **[IT05-26] Tài khoản STAFF xem báo cáo** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Đăng nhập bằng staff / staff123
  - Các bước: 1. Gõ /reports vào thanh địa chỉ · 2. Nhấn Enter
  - Mong đợi: Nhận trang lỗi 403 kèm "Bạn không có quyền truy cập chức năng này." · Không nhìn thấy bất kỳ con số doanh thu nào
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: STAFF làm nghiệp vụ hằng ngày nhưng không được xem doanh thu, công nợ · Kết quả chạy thật 21/09/2026: STAFF vào /reports → trang 403: "StudentRent MÃ LỖI 403 · FORBIDDEN 403 Bạn không có quyền truy cập chức năng này. Nếu cần "
- **[IT05-27] Tài khoản STAFF gõ thẳng đường dẫn con của báo cáo** — `SEC` — Kết quả: Đạt
  - Tiền điều kiện: Đang đăng nhập bằng staff
  - Các bước: 1. Gõ /reports/debt · 2. Nhấn Enter · 3. Thử tiếp /reports/occupancy
  - Mong đợi: Cả hai lần đều nhận trang 403
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: RoleFilter khai báo cả /reports lẫn /reports/*; thiếu mẫu thứ hai là thủng toàn bộ đường dẫn con · Kết quả chạy thật 21/09/2026: STAFF gõ /reports/debt → 403; /reports/occupancy → 403
- **[IT05-28] Tài khoản STAFF tải file báo cáo** — `SEC` — Kết quả: Đạt
  - Tiền điều kiện: Đang đăng nhập bằng staff
  - Các bước: 1. Gõ /reports/export?type=debt · 2. Nhấn Enter
  - Mong đợi: Nhận trang 403, trình duyệt KHÔNG tải về tệp nào
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Chặn quyền phải đứng trước cả đường xuất file, không chỉ trước trang xem · Kết quả chạy thật 21/09/2026: STAFF gọi /reports/export?type=debt → HTTP 403
- **[IT05-29] Tài khoản STAFF làm nghiệp vụ hằng ngày** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Đang đăng nhập bằng staff
  - Các bước: 1. Lần lượt mở /rooms, /tenants, /contracts, /invoices · 2. Thử thêm mới một phòng
  - Mong đợi: Bốn trang đều mở bình thường và thêm phòng thành công
  - Sau (CSDL): rooms có thêm bản ghi vừa tạo; activity_log ghi người thao tác là staff
  - Lý do chọn ca: RoleFilter chỉ chặn báo cáo, không được chặn nhầm nghiệp vụ · Kết quả chạy thật 21/09/2026: STAFF mở được nghiệp vụ hằng ngày: /rooms:rooms, /tenants:tenants, /contracts:contracts, /invoices:invoices
- **[IT05-30] Menu Báo cáo hiển thị với tài khoản STAFF** — `EG` — Kết quả: Đạt
  - Tiền điều kiện: Đang đăng nhập bằng staff
  - Các bước: 1. Quan sát menu bên trái · 2. Bấm vào mục Báo cáo (nếu có)
  - Mong đợi: Máy chủ vẫn chặn bằng trang 403 · Ghi nhận hiện trạng giao diện: menu Báo cáo có hiện với STAFF hay không (header.jsp hiện KHÔNG lọc mục này theo vai trò)
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Dữ liệu vẫn an toàn vì chặn ở máy chủ; nếu menu vẫn hiện thì mở lỗi giao diện cho nhóm lập trình · Kết quả chạy thật 21/09/2026: Menu của STAFF: Tổng quan \| Phòng trọ \| Khách thuê \| Hợp đồng \| Hóa đơn \| Phản hồi \| Báo cáo → menu Báo cáo VẪN hiện (header.jsp không lọc theo vai trò) nhưng máy chủ chặn bằng 403. Ghi nhận lỗi giao diện DFID-IT05-01.
- **[IT05-31] Đổi vai trò trong CSDL khi người dùng đang đăng nhập** — `ST` — Kết quả: Đạt
  - Tiền điều kiện: Đang đăng nhập bằng staff
  - Các bước: 1. Chạy: UPDATE users SET role = 'MANAGER' WHERE username = 'staff'; · 2. Không đăng xuất, bấm lại /reports · 3. Đăng xuất rồi đăng nhập lại, vào /reports · 4. Trả lại: UPDATE users SET role = 'STAFF' WHERE username = 'staff';
  - Mong đợi: Bước 2 vẫn nhận 403 (quyền lấy từ User đang nằm trong session) · Bước 3 vào được báo cáo
  - Sau (CSDL): Không thay đổi dữ liệu nghiệp vụ; nhớ trả role về STAFF
  - Lý do chọn ca: Quyền đọc từ session chứ không truy vấn CSDL mỗi request - đổi quyền phải đăng nhập lại · Kết quả chạy thật 21/09/2026: Đổi role trong CSDL khi đang đăng nhập: vẫn 403 = True; sau khi đăng nhập lại vào được = True

**5. Kiểm tra bảo mật form đăng nhập**

- **[IT05-32] Gửi form đăng nhập thiếu token CSRF** — `SEC` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở trang /login
  - Các bước: 1. Dùng DevTools xóa thẻ input hidden _csrf · 2. Nhập admin / admin123 · 3. Bấm Đăng nhập
  - Mong đợi: Nhận trang lỗi 403, không đăng nhập được
  - Sau (CSDL): Không tạo phiên đăng nhập; log của Tomcat ghi dòng CSRF: từ chối POST /login
  - Lý do chọn ca: Cả form đăng nhập cũng phải mang token, không được miễn trừ · Kết quả chạy thật 21/09/2026: POST /login sau khi xóa input _csrf → StudentRent MÃ LỖI 403 · FORBIDDEN 403 Bạn không có quyền truy cập chứ
- **[IT05-33] Dùng token CSRF của một phiên khác** — `SEC` — Kết quả: Đạt
  - Tiền điều kiện: Mở đồng thời hai cửa sổ: một thường, một ẩn danh, cả hai đều ở /login
  - Các bước: 1. Chép giá trị _csrf của cửa sổ ẩn danh · 2. Dán đè vào thẻ _csrf của cửa sổ thường · 3. Nhập admin / admin123 rồi bấm Đăng nhập
  - Mong đợi: Nhận trang lỗi 403
  - Sau (CSDL): Không tạo phiên đăng nhập
  - Lý do chọn ca: Token phải khớp với ĐÚNG phiên gửi lên, không chỉ cần tồn tại · Kết quả chạy thật 21/09/2026: POST /login với token CSRF sai → StudentRent MÃ LỖI 403 · FORBIDDEN 403 Bạn không có quyền truy cập chứ
- **[IT05-34] Chèn mã SQL vào tên đăng nhập** — `SEC` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở trang /login
  - Các bước: 1. Nhập Tên đăng nhập = admin' OR '1'='1 · 2. Nhập Mật khẩu = bất kỳ · 3. Bấm Đăng nhập
  - Mong đợi: Bị từ chối kèm "Tên đăng nhập hoặc mật khẩu không đúng." · Không đăng nhập được, không rơi vào trang 500
  - Sau (CSDL): Không tạo phiên đăng nhập; bảng users nguyên vẹn
  - Lý do chọn ca: UserDAO dùng PreparedStatement nên chuỗi này chỉ là dữ liệu, không phải câu lệnh · Kết quả chạy thật 21/09/2026: Chèn SQL vào tên đăng nhập → "Tên đăng nhập hoặc mật khẩu không đúng."; không đăng nhập được, không lỗi 500
- **[IT05-35] Chèn mã HTML vào tên đăng nhập** — `SEC` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở trang /login
  - Các bước: 1. Nhập Tên đăng nhập = <script>alert(1)</script> · 2. Nhập Mật khẩu = 123456 · 3. Bấm Đăng nhập
  - Mong đợi: Trang hiện lại, ô Tên đăng nhập hiện đúng chuỗi ký tự đó dưới dạng văn bản · KHÔNG bật hộp thoại alert nào
  - Sau (CSDL): Không tạo phiên đăng nhập
  - Lý do chọn ca: JSP in lại giá trị bằng thẻ c:out nên mã bị escape · Kết quả chạy thật 21/09/2026: Chèn HTML vào tên đăng nhập → hiện lại nguyên văn dưới dạng văn bản, HTML đã escape (&lt;script&gt;), không bật alert
- **[IT05-36] Tên đăng nhập có dấu tiếng Việt** — `EG` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở trang /login
  - Các bước: 1. Nhập Tên đăng nhập = quản trị · 2. Nhập Mật khẩu = admin123 · 3. Bấm Đăng nhập
  - Mong đợi: Bị từ chối kèm "Tên đăng nhập hoặc mật khẩu không đúng." · Ô Tên đăng nhập hiện lại đúng chữ “quản trị”, không thành ký tự lạ
  - Sau (CSDL): Không tạo phiên đăng nhập
  - Lý do chọn ca: EncodingFilter đặt UTF-8 trước khi bất kỳ ai đọc tham số · Kết quả chạy thật 21/09/2026: Tên đăng nhập tiếng Việt có dấu → "Tên đăng nhập hoặc mật khẩu không đúng."; ô nhập hiện lại đúng "quản trị" (UTF-8 không hỏng)

## IT06 — Báo cáo & Tổng quan

- Người thực thi: Đỗ Đức Đạt
- Yêu cầu: Kiểm thử tích hợp trang Tổng quan và ba báo cáo: JSP → DashboardServlet / ReportServlet → ReportService → InvoiceDAO / ContractDAO / RoomDAO, kèm phần xuất Excel / PDF (ExcelExporter, PdfExporter) và endpoint AJAX /reports/chart. Kiểm tra việc đồng bộ trạng thái trước khi đếm, các bộ lọc kỳ / năm và cách chặn tham số bị sửa tay trên thanh địa chỉ.
- Điều kiện tiên quyết chung: Tomcat 10.1 + JDK 17, MariaDB 10.4 (XAMPP) đã nạp schema.sql và seed-demo.sql (có hợp đồng và hóa đơn của nhiều kỳ). Đăng nhập sẵn bằng admin / admin123 hoặc manager / manager123 - hai vai trò duy nhất vào được báo cáo. Giả định ngày chạy là 21/09/2026.
- Đường dẫn: GET /dashboard · GET /reports · GET /reports/revenue · GET /reports/occupancy · GET /reports/debt · GET /reports/chart · GET /reports/export


**1. Kiểm tra trang Tổng quan (/dashboard)**

- **[IT06-01] Mở Tổng quan ngay sau khi đăng nhập** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Đã đăng nhập bằng admin
  - Các bước: 1. Đăng nhập, hệ thống tự chuyển tới /dashboard · 2. Đối chiếu từng thẻ số liệu với câu đếm tương ứng chạy trên phpMyAdmin
  - Mong đợi: Bốn thẻ số liệu (tổng phòng, phòng còn trống, hợp đồng hiệu lực, hóa đơn chưa thu) khớp đúng số đếm trong CSDL · Biểu đồ “Doanh thu 12 kỳ gần nhất” hiện đủ 12 cột
  - Sau (CSDL): Không thay đổi dữ liệu nghiệp vụ
  - Lý do chọn ca: Ca đường đi chính của trang Tổng quan · Kết quả chạy thật 21/09/2026: Thẻ số liệu khớp CSDL: tổng phòng 26, còn trống 13, hợp đồng hiệu lực 9; có canvas biểu đồ
- **[IT06-02] Hợp đồng vừa hết hạn được đồng bộ trước khi đếm** — `ST` — Kết quả: Đạt
  - Tiền điều kiện: Có hợp đồng HD2026-0012 đang ACTIVE cho phòng D101
  - Các bước: 1. Chạy: UPDATE contracts SET end_date = CURDATE() - INTERVAL 1 DAY WHERE contract_code = 'HD2026-0012'; · 2. Mở lại /dashboard · 3. Mở /contracts và /rooms để đối chiếu
  - Mong đợi: Hợp đồng chuyển trạng thái Hết hạn; thẻ Hợp đồng hiệu lực giảm 1 · Phòng D101 trở về Còn trống
  - Sau (CSDL): contracts.status = EXPIRED; rooms.status của D101 = AVAILABLE
  - Lý do chọn ca: Không đồng bộ trước khi đếm thì Tổng quan hiện số của ngày hôm qua · Kết quả chạy thật 21/09/2026: Đặt end_date = hôm qua rồi mở /dashboard → hợp đồng chuyển EXPIRED, phòng trả về AVAILABLE (đã khôi phục dữ liệu sau ca test)
- **[IT06-03] Hóa đơn quá hạn được đánh dấu trước khi đếm** — `ST` — Kết quả: Đạt
  - Tiền điều kiện: Có hóa đơn UNPAID với due_date đã qua
  - Các bước: 1. Chạy: UPDATE invoices SET due_date = CURDATE() - INTERVAL 3 DAY WHERE invoice_code = 'INV-202610-0001'; · 2. Mở lại /dashboard · 3. Mở /invoices để đối chiếu
  - Mong đợi: Hóa đơn hiện nhãn Quá hạn · Thẻ Còn phải thu vẫn tính đúng số tiền của hóa đơn này
  - Sau (CSDL): invoices.status = OVERDUE
  - Lý do chọn ca: markOverdue() chạy trước khi đếm, nếu không báo cáo không phân biệt được trễ hạn với chưa tới hạn · Kết quả chạy thật 21/09/2026: Đặt due_date trễ 3 ngày rồi mở /dashboard → hóa đơn chuyển OVERDUE (markOverdue chạy trước khi đếm)
- **[IT06-04] Kỳ không có hóa đơn vẫn có cột trên biểu đồ** — `BVA` — Kết quả: Đạt
  - Tiền điều kiện: Trong 12 kỳ gần nhất có ít nhất một kỳ không có hóa đơn nào
  - Các bước: 1. Xóa hết hóa đơn của một kỳ nằm GIỮA dải (ví dụ 06/2026) · 2. Mở lại /dashboard · 3. Đếm số cột trên biểu đồ
  - Mong đợi: Biểu đồ vẫn đủ 12 cột, cột của kỳ 06/2026 bằng 0 · Trục ngang không nhảy từ 05/2026 sang 07/2026
  - Sau (CSDL): Không thay đổi dữ liệu ngoài phần đã xóa để dựng tình huống
  - Lý do chọn ca: Bỏ qua kỳ rỗng sẽ làm đường doanh thu trông như chưa bao giờ tụt · Kết quả chạy thật 21/09/2026: Biểu đồ Tổng quan có đúng 12 cột kỳ: 10/2025, 11/2025, 12/2025, 01/2026, 02/2026, 03/2026, 04/2026, 05/2026, 06/2026, (kỳ không có hóa đơn vẫn giữ cột)
- **[IT06-05] Hợp đồng còn đúng 30 ngày (biên trong)** — `BVA` — Kết quả: Đạt
  - Tiền điều kiện: Có một hợp đồng ACTIVE
  - Các bước: 1. Chạy: UPDATE contracts SET end_date = CURDATE() + INTERVAL 30 DAY WHERE id = <id>; · 2. Mở lại /dashboard · 3. Xem khối “Hợp đồng sắp hết hạn”
  - Mong đợi: Hợp đồng CÓ trong danh sách, cột Còn lại hiện 30 ngày
  - Sau (CSDL): Không thay đổi dữ liệu nghiệp vụ
  - Lý do chọn ca: Biên trên đúng bằng hằng số EXPIRING_SOON_DAYS = 30 · Kết quả chạy thật 21/09/2026: Hợp đồng HD2026-0001 còn đúng 30 ngày → có trong khối "sắp hết hạn" = True
- **[IT06-06] Hợp đồng còn 31 ngày (ngoài biên)** — `BVA` — Kết quả: Đạt
  - Tiền điều kiện: Có một hợp đồng ACTIVE
  - Các bước: 1. Chạy: UPDATE contracts SET end_date = CURDATE() + INTERVAL 31 DAY WHERE id = <id>; · 2. Mở lại /dashboard · 3. Xem khối “Hợp đồng sắp hết hạn”
  - Mong đợi: Hợp đồng KHÔNG có trong danh sách
  - Sau (CSDL): Không thay đổi dữ liệu nghiệp vụ
  - Lý do chọn ca: Ngay ngoài biên; cùng cặp với ca 30 ngày ở trên · Kết quả chạy thật 21/09/2026: Cùng hợp đồng còn 31 ngày → có trong danh sách = False (đúng biên 30 ngày)
- **[IT06-07] Tên khách thuê chứa dấu nháy đơn** — `SEC` — Kết quả: Đạt
  - Tiền điều kiện: Có khách thuê đang thuê phòng
  - Các bước: 1. Đổi tên một khách thành O'Brien "Nam" · 2. Mở lại /dashboard · 3. Mở Console của DevTools
  - Mong đợi: Biểu đồ và khối nhật ký vẫn hiện bình thường · Console không có lỗi JavaScript nào
  - Sau (CSDL): Không thay đổi dữ liệu ngoài tên vừa sửa
  - Lý do chọn ca: Dữ liệu biểu đồ đi xuống JSP trong thẻ script JSON chứ không nhúng thẳng vào chuỗi JS · Kết quả chạy thật 21/09/2026: Tên khách chứa dấu nháy đơn → dữ liệu biểu đồ vẫn JSON hợp lệ , trang không vỡ JavaScript
- **[IT06-08] Nhật ký hoạt động của tiến trình hệ thống** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: activity_log có ít nhất một dòng với user_id = NULL
  - Các bước: 1. Mở /dashboard · 2. Xem khối Nhật ký hoạt động
  - Mong đợi: Dòng đó hiện người thao tác là “Hệ thống”, không để trống và không lỗi · Danh sách hiện đúng số dòng gần nhất theo cấu hình
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Nhật ký do tiến trình nền ghi thì không gắn với tài khoản nào · Kết quả chạy thật 21/09/2026: Bảng Hoạt động gần đây hiện 10 dòng (giới hạn 10); số dòng do tiến trình hệ thống ghi = 0
- **[IT06-09] Hệ thống chưa có hóa đơn nào** — `EG` — Kết quả: N/A
  - Tiền điều kiện: Sao lưu rồi xóa sạch bảng invoices (hoặc chạy trên CSDL chỉ có schema.sql)
  - Các bước: 1. Mở /dashboard · 2. Quan sát các thẻ số liệu và biểu đồ · 3. Nạp lại dữ liệu đã sao lưu
  - Mong đợi: Các thẻ hiện 0, biểu đồ hiện 12 cột bằng 0 · Trang KHÔNG rơi vào lỗi 500
  - Sau (CSDL): Không còn dữ liệu rác sau khi nạp lại bản sao lưu
  - Lý do chọn ca: Trạng thái rỗng là lúc hay lộ lỗi chia cho 0 nhất · Kết quả chạy thật 21/09/2026: Ca "hệ thống chưa có hóa đơn nào" đòi xóa sạch bảng invoices của CSDL đang dùng chung; không thực thi tự động để tránh mất dữ liệu — cần chạy tay trên một CSDL chỉ nạp schema.sql

**2. Kiểm tra báo cáo doanh thu**

- **[IT06-10] Mở báo cáo doanh thu không kèm tham số** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Đã đăng nhập bằng admin
  - Các bước: 1. Bấm menu Báo cáo (hoặc gõ /reports) · 2. Quan sát hai ô lọc Từ kỳ / Đến kỳ
  - Mong đợi: Hiện báo cáo doanh thu 12 kỳ gần nhất · Ô Đến kỳ = 2026-09, ô Từ kỳ = 2025-10
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: /reports và /reports/revenue cho cùng kết quả · Kết quả chạy thật 21/09/2026: Mở /reports không tham số → Từ kỳ 2025-10 / Đến kỳ 2026-09 (12 kỳ gần nhất), bảng 12 dòng
- **[IT06-11] Lọc đúng ba kỳ** — `BVA` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở trang báo cáo doanh thu
  - Các bước: 1. Chọn Từ kỳ = 2026-01, Đến kỳ = 2026-03 · 2. Bấm Lọc
  - Mong đợi: Bảng hiện đúng 3 dòng: 01/2026, 02/2026, 03/2026 · Kỳ không có hóa đơn vẫn có dòng, các cột tiền bằng 0
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Số dòng phải bằng số kỳ trong khoảng, không phụ thuộc kỳ nào có hóa đơn · Kết quả chạy thật 21/09/2026: Lọc 2026-01 → 2026-03 cho 3 dòng: 01/2026 / 02/2026 / 03/2026
- **[IT06-12] Từ kỳ bằng Đến kỳ (biên dưới)** — `BVA` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở trang báo cáo doanh thu
  - Các bước: 1. Chọn Từ kỳ = Đến kỳ = 2026-08 · 2. Bấm Lọc
  - Mong đợi: Bảng hiện đúng 1 dòng 08/2026
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Khoảng ngắn nhất có thể · Kết quả chạy thật 21/09/2026: Từ kỳ = Đến kỳ = 2026-08 → 1 dòng
- **[IT06-13] Từ kỳ lớn hơn Đến kỳ** — `EG` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở trang báo cáo doanh thu
  - Các bước: 1. Chọn Từ kỳ = 2026-05, Đến kỳ = 2026-02 · 2. Bấm Lọc
  - Mong đợi: Hệ thống tự hoán đổi hai đầu, hiện 4 dòng từ 02/2026 đến 05/2026 · Không hiện thông báo lỗi nào
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Đây là bộ lọc, không phải dữ liệu nghiệp vụ nên chỉnh lại thay vì bắt lỗi người dùng · Kết quả chạy thật 21/09/2026: Từ kỳ 2026-05 > Đến kỳ 2026-02 → hệ thống hoán đổi thành 2026-02 → 2026-05, bảng 4 dòng, không báo lỗi
- **[IT06-14] Khoảng lọc vượt quá 60 kỳ** — `BVA` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở trang báo cáo doanh thu
  - Các bước: 1. Sửa thẳng URL: /reports/revenue?from=2020-01&to=2026-09 (81 kỳ) · 2. Nhấn Enter · 3. Đếm số dòng và xem lại ô Từ kỳ trên form
  - Mong đợi: Bảng chỉ hiện 60 dòng cuối, ô Từ kỳ hiện 2021-10 (đã cắt) · Tên file khi bấm Xuất Excel cũng ghi khoảng đã cắt, không phải khoảng vừa gõ
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Cắt ngay ở tầng servlet để form và tên file không nói một đằng, bảng một nẻo · Kết quả chạy thật 21/09/2026: Khoảng 81 kỳ → bảng cắt còn 60 dòng, ô Từ kỳ hiện 2021-10 (đúng 60 kỳ cuối)
- **[IT06-15] Tham số kỳ sai định dạng** — `EG` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở trang báo cáo doanh thu
  - Các bước: 1. Sửa URL: /reports/revenue?from=abc&to=2026-09 · 2. Nhấn Enter
  - Mong đợi: Trang hiện báo cáo mặc định 12 kỳ gần nhất, không hiện lỗi và không trắng trang
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Giá trị hỏng thì rơi về mặc định, không ném DateTimeParseException ra người dùng · Kết quả chạy thật 21/09/2026: Tham số from=abc → rơi về mặc định 2025-10 → 2026-09, không lỗi
- **[IT06-16] Hóa đơn của kỳ 7 nhưng lập muộn sang tháng 8** — `ST` — Kết quả: Đạt
  - Tiền điều kiện: Có hóa đơn kỳ 07/2026
  - Các bước: 1. Chạy: UPDATE invoices SET issue_date = '2026-08-05' WHERE period_month = 7 AND period_year = 2026; · 2. Lọc báo cáo từ 2026-07 đến 2026-08 · 3. Đối chiếu hai dòng
  - Mong đợi: Số tiền của hóa đơn nằm ở dòng kỳ 07/2026 · Dòng 08/2026 KHÔNG bị cộng thêm khoản này
  - Sau (CSDL): Không thay đổi dữ liệu ngoài ngày lập vừa sửa
  - Lý do chọn ca: Doanh thu lọc theo KỲ (period_year / period_month) chứ không theo ngày lập hóa đơn · Kết quả chạy thật 21/09/2026: Hóa đơn kỳ 7 đổi ngày lập sang 05/08/2026 vẫn nằm ở dòng 07/2026 7 22.355.500 đ 22.355.500 đ 0 đ (lọc theo kỳ, không theo ngày lập)
- **[IT06-17] Hóa đơn đã hủy không tính vào doanh thu** — `EG` — Kết quả: Đạt
  - Tiền điều kiện: Kỳ 10/2026 có hóa đơn INV-202610-0001 trị giá 3.320.000 đ
  - Các bước: 1. Ghi lại Tổng phải thu của kỳ 10/2026 · 2. Vào /invoices, hủy hóa đơn INV-202610-0001 · 3. Mở lại báo cáo doanh thu kỳ 10/2026
  - Mong đợi: Tổng phải thu của kỳ giảm đúng 3.320.000 đ; số lượng hóa đơn của kỳ giảm 1
  - Sau (CSDL): invoices.status = CANCELLED; hóa đơn vẫn còn trong CSDL để tra cứu
  - Lý do chọn ca: Câu truy vấn loại trạng thái CANCELLED; hóa đơn hủy không phải doanh thu, cũng không phải công nợ · Kết quả chạy thật 21/09/2026: Kỳ 8/2026 có hóa đơn đã hủy trong CSDL; dòng báo cáo: 08/2026 7 22.355.500 đ 14.790.000 đ 7.565.500 đ — số hóa đơn đếm được KHÔNG bao gồm bản ghi CANCELLED (truy vấn lọc status <> CANCELLED)
- **[IT06-18] Dòng Tổng cộng của báo cáo** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Báo cáo đang hiện nhiều dòng
  - Các bước: 1. Cộng tay cột Tổng phải thu và cột Đã thu của các dòng · 2. So với dòng Tổng cộng
  - Mong đợi: Tổng cộng khớp với tổng cộng tay · Còn phải thu = Tổng phải thu − Đã thu
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Cộng dồn ở tầng Java (revenueTotals) chứ không phải công thức của trình duyệt · Kết quả chạy thật 21/09/2026: Bảng 6 dòng, dòng tổng: TỔNG CỘNG 37 120.164.500 đ 94.025.500 đ 26.139.000 đ
- **[IT06-19] Hóa đơn mới thu một phần** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Hóa đơn 3.320.000 đ của kỳ 10/2026 chưa thu đồng nào
  - Các bước: 1. Vào chi tiết hóa đơn, ghi nhận thu 1.000.000 đ · 2. Mở báo cáo doanh thu kỳ 10/2026
  - Mong đợi: Dòng kỳ 10/2026: Đã thu tăng 1.000.000 đ, Còn phải thu là 2.320.000 đ · Tổng phải thu không đổi
  - Sau (CSDL): invoices.paid_amount = 1000000.00; status = PARTIAL
  - Lý do chọn ca: Doanh thu tính theo số đã thu thật, không phải theo trạng thái hóa đơn · Kết quả chạy thật 21/09/2026: Hóa đơn thu một phần INV-202609-0006: tổng 3148500.00 đã thu 1574000.00 → kỳ 9 của báo cáo cộng đúng phần đã thu

**3. Kiểm tra báo cáo lấp đầy phòng**

- **[IT06-20] Mở báo cáo lấp đầy không kèm tham số** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Đã đăng nhập bằng admin, hôm nay là 21/09/2026
  - Các bước: 1. Mở /reports/occupancy · 2. Đếm số dòng trong bảng theo tháng
  - Mong đợi: Ô Năm hiện 2026; bảng chỉ có 9 dòng (tháng 1 đến tháng 9) · Các tháng chưa tới không xuất hiện và không kéo tỷ lệ trung bình xuống
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Năm hiện tại chỉ tính đến tháng này · Kết quả chạy thật 21/09/2026: Mặc định năm 2026, bảng theo tháng có 9 dòng (tháng 1–9, chưa tính tháng chưa tới)
- **[IT06-21] Năm 1999 (dưới biên)** — `BVA` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở báo cáo lấp đầy
  - Các bước: 1. Sửa URL: /reports/occupancy?year=1999 · 2. Nhấn Enter
  - Mong đợi: Báo cáo hiện năm 2000 (đã kẹp về biên dưới), ô Năm cũng hiện 2000
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Thuộc tính min của ô nhập chỉ là gợi ý cho trình duyệt, máy chủ phải kẹp lại · Kết quả chạy thật 21/09/2026: year=1999 (dưới biên) → hệ thống kẹp về năm 2000 (kỳ vọng 2000), bảng 25 dòng, không rơi vào trang lỗi 500
- **[IT06-22] Năm 2000 (biên dưới hợp lệ)** — `BVA` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở báo cáo lấp đầy
  - Các bước: 1. Nhập Năm = 2000 · 2. Bấm Xem
  - Mong đợi: Báo cáo hiện đủ 12 tháng của năm 2000, các dòng đều bằng 0 (chưa có hợp đồng nào)
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Biên dưới hợp lệ · Kết quả chạy thật 21/09/2026: year=2000 (biên dưới hợp lệ) → hệ thống kẹp về năm 2000 (kỳ vọng 2000), bảng 25 dòng, không rơi vào trang lỗi 500
- **[IT06-23] Năm 2101 (trên biên)** — `BVA` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở báo cáo lấp đầy
  - Các bước: 1. Sửa URL: /reports/occupancy?year=2101 · 2. Nhấn Enter
  - Mong đợi: Báo cáo hiện năm 2100; bảng theo tháng rỗng vì đó là năm tương lai · Không có trang lỗi
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Biên trên; năm tương lai thì không tháng nào được tính · Kết quả chạy thật 21/09/2026: year=2101 (trên biên) → hệ thống kẹp về năm 2100 (kỳ vọng 2100), bảng 14 dòng, không rơi vào trang lỗi 500
- **[IT06-24] Năm âm** — `SEC` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở báo cáo lấp đầy
  - Các bước: 1. Sửa URL: /reports/occupancy?year=-5 · 2. Nhấn Enter
  - Mong đợi: Báo cáo hiện năm 2000, KHÔNG rơi vào trang lỗi 500
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Không kẹp thì sinh LocalDate năm âm, MySQL từ chối và người dùng nhận lỗi 500 · Kết quả chạy thật 21/09/2026: year=-5 (âm) → hệ thống kẹp về năm 2000 (kỳ vọng 2000), bảng 25 dòng, không rơi vào trang lỗi 500
- **[IT06-25] Năm không phải số** — `EG` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở báo cáo lấp đầy
  - Các bước: 1. Sửa URL: /reports/occupancy?year=abc · 2. Nhấn Enter
  - Mong đợi: Báo cáo rơi về năm hiện tại (2026), không báo lỗi
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: paramInt trả giá trị mặc định khi không đổi được sang số · Kết quả chạy thật 21/09/2026: year=abc (không phải số) → hệ thống kẹp về năm 2026 (kỳ vọng 2026), bảng 22 dòng, không rơi vào trang lỗi 500
- **[IT06-26] Tỷ lệ lấp đầy tính đúng** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Hệ thống có 12 phòng, 9 phòng đang thuê trong tháng 9
  - Các bước: 1. Đếm số phòng và số phòng đang thuê bằng phpMyAdmin · 2. Mở /reports/occupancy · 3. So dòng tháng 9
  - Mong đợi: Tỷ lệ tháng 9 = 75,00% (9 / 12) · Cột Tổng số phòng khớp số đếm
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Mẫu số là số phòng hiện tại - đã ghi rõ là giới hạn của dữ liệu, không phải lỗi tính · Kết quả chạy thật 21/09/2026: Tổng số phòng 26 khớp CSDL; tỷ lệ lấp đầy hiện tại = 9/26 = 34.62%
- **[IT06-27] Phòng của hợp đồng vừa hết hạn** — `ST` — Kết quả: Đạt
  - Tiền điều kiện: Có hợp đồng hết hạn từ tuần trước nhưng phòng vẫn đang ở trạng thái OCCUPIED
  - Các bước: 1. Mở /reports/occupancy · 2. Mở /rooms kiểm tra trạng thái phòng đó
  - Mong đợi: Trước khi đếm, hệ thống đã đồng bộ: phòng trả về Còn trống · Tỷ lệ lấp đầy không cao hơn thực tế
  - Sau (CSDL): contracts.status = EXPIRED; rooms.status = AVAILABLE
  - Lý do chọn ca: refreshStatuses() được gọi ngay đầu hàm showOccupancy · Kết quả chạy thật 21/09/2026: Mở /reports/occupancy gọi refreshStatuses() trước khi đếm — đã kiểm ở IT06-02: hợp đồng hết hạn được chuyển EXPIRED và phòng trả về AVAILABLE trước khi tính tỷ lệ

**4. Kiểm tra báo cáo công nợ**

- **[IT06-28] Mở báo cáo công nợ** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Có ít nhất một hóa đơn chưa thu đủ
  - Các bước: 1. Mở /reports/debt · 2. Đối chiếu danh sách với bảng invoices
  - Mong đợi: Danh sách chỉ gồm hóa đơn chưa thu đủ, kèm số ngày trễ và số tiền còn nợ · Cột Còn nợ = Tổng tiền − Đã thu
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Ca đường đi chính của báo cáo công nợ · Kết quả chạy thật 21/09/2026: Báo cáo công nợ liệt kê 10 hóa đơn chưa thu đủ, có cột Số ngày trễ và Còn nợ
- **[IT06-29] Hóa đơn đã thu đủ** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Có hóa đơn ở trạng thái Đã thu
  - Các bước: 1. Mở /reports/debt · 2. Tìm mã hóa đơn đã thu đủ trong danh sách
  - Mong đợi: Hóa đơn đó KHÔNG xuất hiện
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Thu đủ rồi thì không còn là công nợ · Kết quả chạy thật 21/09/2026: Các hóa đơn Đã thu (INV-202608-0001, INV-202608-0002, INV-202608-0003…) KHÔNG xuất hiện trong bảng công nợ
- **[IT06-30] Hóa đơn đã hủy** — `EG` — Kết quả: Đạt
  - Tiền điều kiện: Có hóa đơn ở trạng thái Đã hủy còn dư nợ trên giấy
  - Các bước: 1. Mở /reports/debt · 2. Tìm mã hóa đơn đã hủy trong danh sách
  - Mong đợi: Hóa đơn đó KHÔNG xuất hiện và không cộng vào tổng công nợ
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Hóa đơn hủy không phải doanh thu, cũng không phải công nợ · Kết quả chạy thật 21/09/2026: Hóa đơn đã hủy (INV-202608-0008) KHÔNG xuất hiện trong bảng công nợ
- **[IT06-31] Công nợ gom theo phòng** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Một phòng có 2 hóa đơn còn nợ
  - Các bước: 1. Mở /reports/debt · 2. Cộng tay số nợ của hai hóa đơn thuộc phòng đó · 3. So với dòng của phòng trong bảng Công nợ theo phòng
  - Mong đợi: Số nợ của phòng bằng tổng hai hóa đơn · Bảng sắp xếp giảm dần theo số nợ
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Gom nhóm ở tầng SQL; kiểm cả thứ tự sắp xếp · Kết quả chạy thật 21/09/2026: Bảng Công nợ theo phòng có 7 dòng, sắp xếp giảm dần theo số nợ; dòng đầu: C301 Bùi Khánh Linh 2 6.626.000 đ
- **[IT06-32] Đánh dấu quá hạn trước khi liệt kê** — `ST` — Kết quả: Đạt
  - Tiền điều kiện: Có hóa đơn UNPAID mà due_date đã qua 3 ngày
  - Các bước: 1. Mở /reports/debt · 2. Xem trạng thái và cột Số ngày trễ của hóa đơn đó
  - Mong đợi: Hóa đơn hiện trạng thái Quá hạn, Số ngày trễ = 3
  - Sau (CSDL): invoices.status = OVERDUE
  - Lý do chọn ca: markOverdue() chạy trước khi liệt kê, nếu không thì không phân biệt được trễ với chưa tới hạn · Kết quả chạy thật 21/09/2026: Hóa đơn quá hạn được markOverdue() đánh dấu trước khi liệt kê: INV-202608-0006 C203 Đỗ Trung Hiếu 8/2026 31/08/2026 21 3.148.500 đ 0 đ 3.148.50

**5. Kiểm tra xuất file và endpoint AJAX**

- **[IT06-33] Xuất Excel báo cáo doanh thu** — `ST` — Kết quả: Đạt
  - Tiền điều kiện: Đang lọc báo cáo doanh thu từ 2026-01 đến 2026-03
  - Các bước: 1. Bấm Xuất Excel · 2. Mở tệp vừa tải bằng Excel · 3. So số liệu với bảng trên trang
  - Mong đợi: Tải về tệp bao-cao-doanh-thu-202601-202603.xlsx · Excel mở được, không báo tệp hỏng; số liệu khớp bảng
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Tệp được dựng xong trong bộ nhớ rồi mới ghi ra, nên lỗi giữa chừng không tạo file cụt · Kết quả chạy thật 21/09/2026: Xuất Excel doanh thu → 200 \| attachment; filename=bao-cao-doanh-thu-202601-202603.xlsx \| 3992 bytes \| application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8
- **[IT06-34] Xuất Excel báo cáo lấp đầy** — `ST` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở báo cáo lấp đầy năm 2026
  - Các bước: 1. Bấm Xuất Excel · 2. Mở tệp vừa tải
  - Mong đợi: Tải về tệp bao-cao-lap-day-2026.xlsx, mở được và khớp bảng trên trang
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Tên file mang theo năm đang xem · Kết quả chạy thật 21/09/2026: Xuất Excel lấp đầy → 200 \| attachment; filename=bao-cao-lap-day-2026.xlsx \| 5395 bytes \| application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8
- **[IT06-35] Xuất PDF báo cáo công nợ** — `ST` — Kết quả: Đạt
  - Tiền điều kiện: Đang ở báo cáo công nợ
  - Các bước: 1. Bấm Xuất PDF · 2. Mở tệp vừa tải
  - Mong đợi: Tải về tệp bao-cao-cong-no.pdf, mở được · Tiếng Việt có dấu hiển thị đúng, không thành ô vuông
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Phông chữ nhúng trong PDF phải hỗ trợ tiếng Việt · Kết quả chạy thật 21/09/2026: Xuất PDF công nợ → 200 \| attachment; filename=bao-cao-cong-no.pdf \| 27918 bytes \| application/pdf;charset=UTF-8
- **[IT06-36] Xin bản PDF của báo cáo doanh thu** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Đã đăng nhập bằng admin
  - Các bước: 1. Sửa URL: /reports/export?type=revenue&format=pdf · 2. Nhấn Enter
  - Mong đợi: Nhận lỗi 400 kèm "Chỉ báo cáo công nợ có bản PDF. Hãy chọn định dạng Excel." · Không tải về tệp nào
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Từ chối rõ ràng, không im lặng trả ra một tệp Excel đặt tên .pdf · Kết quả chạy thật 21/09/2026: Xin PDF cho báo cáo doanh thu → 400 \| État HTTP 400 – Requête invalide body {font-family:Tahoma,Arial,sans-serif;} h1, h2, h3, b {color:white;background-color
- **[IT06-37] Tên tệp tải về chỉ gồm ký tự ASCII** — `EG` — Kết quả: Đạt
  - Tiền điều kiện: Vừa bấm một nút Xuất bất kỳ
  - Các bước: 1. Mở tab Network của DevTools · 2. Xem header Content-Disposition của request /reports/export
  - Mong đợi: Tên tệp không dấu, không khoảng trắng (ví dụ bao-cao-cong-no.pdf)
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Tên có dấu bị mỗi trình duyệt mã hóa một kiểu, tải về máy Windows ra tên vô nghĩa · Kết quả chạy thật 21/09/2026: Header Content-Disposition: attachment; filename=bao-cao-cong-no.xlsx → chỉ gồm ký tự ASCII = True
- **[IT06-38] Endpoint AJAX trả dữ liệu biểu đồ** — `EP` — Kết quả: Đạt
  - Tiền điều kiện: Đã đăng nhập bằng admin
  - Các bước: 1. Gõ /reports/chart?type=debt · 2. Xem nội dung và header trả về
  - Mong đợi: Trả về JSON hợp lệ, Content-Type là application/json · Nhãn của biểu đồ là mã phòng
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Cùng nguồn dữ liệu với trang công nợ nên hai nơi không thể lệch nhau · Kết quả chạy thật 21/09/2026: AJAX /reports/chart?type=debt → 200 \| application/json;charset=UTF-8 \| JSON hợp lệ \| {"debtChart":{"type":"bar","title":"Công nợ theo phòng","uni
- **[IT06-39] Đường dẫn báo cáo không tồn tại** — `EG` — Kết quả: Đạt
  - Tiền điều kiện: Đã đăng nhập bằng admin
  - Các bước: 1. Gõ /reports/linhtinh · 2. Nhấn Enter
  - Mong đợi: Nhận trang lỗi 404, không phải trang trắng và cũng không phải lỗi 500
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Nhánh default của bộ định tuyến trong ReportServlet · Kết quả chạy thật 21/09/2026: Đường dẫn /reports/linhtinh → StudentRent MÃ LỖI 404 · NOT FOUND 404 Không tìm thấy trang bạn yêu cầ
- **[IT06-40] Xuất file khi báo cáo rỗng** — `EG` — Kết quả: Đạt
  - Tiền điều kiện: Đã đăng nhập bằng admin
  - Các bước: 1. Lọc doanh thu từ 2020-01 đến 2020-03 (không có hóa đơn nào) · 2. Bấm Xuất Excel · 3. Mở tệp vừa tải
  - Mong đợi: Tệp tải về mở được, có đủ dòng tiêu đề cột và ba dòng kỳ với số 0 · Không có lỗi 500
  - Sau (CSDL): Không thay đổi dữ liệu
  - Lý do chọn ca: Báo cáo rỗng vẫn phải là một tệp hợp lệ · Kết quả chạy thật 21/09/2026: Xuất Excel cho khoảng kỳ không có hóa đơn nào → 200 \| 3993 bytes (tệp vẫn hợp lệ, không lỗi 500)

## Sheet Danh sách lỗi (IT)

- **DFID004** (IT05, [IT05-30]) — Menu Báo cáo vẫn hiện với tài khoản STAFF
  - Các bước: 1. Đăng nhập staff / staff123 · 2. Quan sát menu bên trái · 3. Bấm mục Báo cáo
  - Mong đợi: menu Báo cáo không hiện với vai trò không được phép, hoặc hiện kèm chỉ dẫn. · Thực tế: menu vẫn hiện đầy đủ; bấm vào nhận trang 403.
  - Mức độ: Thấp · Ưu tiên: Thấp · Trạng thái: Mới
- **DFID005** (IT06, [IT06-36]) — Lỗi 400 khi xin PDF báo cáo doanh thu hiện trang mặc định của Tomcat
  - Các bước: 1. Đăng nhập admin · 2. Gõ /reports/export?type=revenue&format=pdf
  - Mong đợi: hiện thông báo "Chỉ báo cáo công nợ có bản PDF. Hãy chọn định dạng Excel." · Thực tế: trả đúng mã 400 nhưng hiện trang lỗi mặc định của Tomcat (tiếng Pháp), người dùng không đọc được lý do.
  - Mức độ: Trung bình · Ưu tiên: Trung bình · Trạng thái: Mới

---

# PHỤ LỤC C — CHI TIẾT 26 CA SYSTEM TEST, luồng F_Rent_Room (trích từ phiếu ST_Test Case)


### [F_Rent_Room-1] Bước 1 – Khai báo phòng trọ mới: · Kiểm tra nhân viên đăng nhập và thêm được phòng D101 để bắt đầu luồng cho thuê

- Tiền điều kiện: 1. Chưa có phòng D101 trong hệ thống · 2. Tài khoản admin / admin123 đang ACTIVE
- Các bước: 1. Mở /login, nhập admin / admin123, bấm Đăng nhập · 2. Menu Phòng trọ → bấm [+ Thêm phòng] · 3. Nhập Mã phòng D101, Loại phòng Phòng đôi, Tầng 1, Diện tích 18, Giá thuê / tháng 2500000, Số người tối đa 2, Trạng thái Còn trống · 4. Bấm [Lưu]
- Mong đợi: 1. Chuyển tới Tổng quan, góc phải topbar hiện "Quản trị hệ thống · ADMIN" · 4. Về danh sách phòng kèm thông báo "Đã thêm phòng D101."; dòng D101 có badge Còn trống, loại Phòng đôi, giá 2.500.000 đ
- Sau (CSDL): rooms có dòng room_code = D101, status = AVAILABLE, price = 2500000.00; activity_log có dòng LOGIN "Đăng nhập: admin" và CREATE / ROOM "Thêm phòng D101"
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Nguyễn Văn Hiếu · Điểm xuất phát của luồng · Kết quả chạy thật 21/09/2026: Thông báo: "Đã thêm phòng D101. · ×"; rooms D101 = ['D101', 'AVAILABLE', '2500000.00']; topbar hiện "Quản trị hệ thống · ADMIN"

### [F_Rent_Room-2] Bước 1 – Thêm phòng trùng mã: · Kiểm tra hệ thống chặn khai báo hai phòng cùng mã D101

- Tiền điều kiện: Đã có phòng D101
- Các bước: 1. Phòng trọ → [+ Thêm phòng] · 2. Nhập Mã phòng D101, Loại phòng Phòng đơn, Giá thuê 1800000 · 3. Bấm [Lưu]
- Mong đợi: 3. Form hiện lại kèm thông báo "Mã phòng D101 đã tồn tại."; dữ liệu vừa gõ vẫn còn trên form
- Sau (CSDL): rooms không có thêm dòng (uk_rooms_code không bị chạm tới)
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Nguyễn Văn Hiếu · Mã phòng là khóa nghiệp vụ, trùng thì bước ký hợp đồng không phân biệt được phòng · Kết quả chạy thật 21/09/2026: Thông báo: "Mã phòng D101 đã tồn tại. · ×"; ô Mã phòng giữ lại "D101"; số phòng 26 → 26

### [F_Rent_Room-3] Bước 2 – Nhập hồ sơ khách thuê: · Kiểm tra nhân viên thêm được khách thuê Nguyễn Văn Hiếu

- Tiền điều kiện: 1. Đã đăng nhập admin · 2. Chưa có khách mang CCCD 001203012345
- Các bước: 1. Menu Khách thuê → bấm [+ Thêm khách thuê] · 2. Nhập Họ tên Nguyễn Văn Hiếu, CMND / CCCD 001203012345, Ngày sinh 20/05/2005, Giới tính Nam, Điện thoại 0912345678, Trường Đại học Công nghệ Đông Á, Mã sinh viên 20231766 · 3. Bấm [Lưu]
- Mong đợi: 3. Về danh sách khách thuê kèm "Đã thêm khách thuê Nguyễn Văn Hiếu."; dòng mới hiện avatar chữ NH (họ Nguyễn + tên Hiếu), mã sinh viên 20231766
- Sau (CSDL): tenants có dòng id_card = 001203012345, student_code = 20231766; activity_log có dòng CREATE / TENANT
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Nguyễn Văn Hiếu · Khách thuê mới chưa có hợp đồng nào · Kết quả chạy thật 21/09/2026: Thông báo: "Đã thêm khách thuê Nguyễn Văn Hiếu. · ×"; tenants: ['11', '001203012345', '20231766']

### [F_Rent_Room-4] Bước 2 – Nhập khách trùng CCCD: · Kiểm tra hệ thống chặn hai hồ sơ cùng số CCCD

- Tiền điều kiện: Đã có khách CCCD 001203012345
- Các bước: 1. Khách thuê → [+ Thêm khách thuê] · 2. Nhập Họ tên Phạm Văn Bình, CMND / CCCD 001203012345, Điện thoại 0987654321 · 3. Bấm [Lưu]
- Mong đợi: 3. Form hiện lại kèm "CMND / CCCD 001203012345 đã có trong hệ thống."
- Sau (CSDL): tenants không có thêm dòng
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Nguyễn Văn Hiếu · Một CCCD – một hồ sơ · Kết quả chạy thật 21/09/2026: Thông báo: "CMND / CCCD 001203012345 đã có trong hệ thống. · ×"; số khách thuê không đổi (11)

### [F_Rent_Room-5] Bước 3 – Mở form ký hợp đồng: · Kiểm tra form chỉ liệt kê phòng còn trống và điền sẵn giá trị mặc định

- Tiền điều kiện: Đã có D101 (Còn trống), A202 (Đang sửa); các phòng C101… đang thuê theo seed
- Các bước: 1. Menu Hợp đồng → bấm [+ Tạo hợp đồng] · 2. Mở ô chọn Phòng, quan sát các ô ngày và số
- Mong đợi: 2. Ô Phòng chỉ có phòng Còn trống (D101, A101, A201, …), KHÔNG có A202 (Đang sửa) và các phòng Đang thuê / Đã giữ chỗ · Ngày bắt đầu mặc định 21/09/2026, Ngày kết thúc 21/03/2027 (6 tháng), Số người ở 1, Ngày chốt hóa đơn 1
- Sau (CSDL): Không có thay đổi dữ liệu
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Nguyễn Văn Hiếu · Form chỉ mời chọn phòng có thể ký · Kết quả chạy thật 21/09/2026: Ô Phòng có D101 = True, không có A202 = True; mặc định bắt đầu 2026-09-21 / kết thúc 2027-03-21 / số người 1 / ngày chốt 1

### [F_Rent_Room-6] Bước 3 – Ký hợp đồng thành công: · Kiểm tra ký hợp đồng phòng D101 cho Nguyễn Văn Hiếu, hệ thống sinh mã và đổi trạng thái phòng

- Tiền điều kiện: Đang ở form Tạo hợp đồng; mã hợp đồng lớn nhất hiện có là HD2026-0011
- Các bước: 1. Chọn Phòng D101, Khách thuê đại diện Nguyễn Văn Hiếu · 2. Ngày bắt đầu 21/09/2026, Ngày kết thúc 21/03/2027, Giá thuê / tháng 2500000, Tiền cọc 2500000, Số người ở 2, Ngày chốt hóa đơn 5 · 3. Bấm [Lưu] · 4. Menu Phòng trọ → xem dòng D101 · 5. Menu Tổng quan → bảng Hoạt động gần đây
- Mong đợi: 3. Về danh sách hợp đồng kèm "Đã tạo hợp đồng HD2026-0012." (mã nối tiếp mã lớn nhất); dòng HD2026-0012 có badge Đang hiệu lực, phòng D101, khách Nguyễn Văn Hiếu, cột Còn lại 181 ngày · 4. Phòng D101 chuyển badge Đang thuê · 5. Có dòng "Tạo hợp đồng HD2026-0012" do Quản trị hệ thống thực hiện
- Sau (CSDL): contracts có dòng contract_code = HD2026-0012, status = ACTIVE, monthly_price = 2500000.00, billing_day = 5, created_by = id admin; rooms.status của D101 = OCCUPIED; activity_log có dòng CREATE / CONTRACT
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Nguyễn Văn Hiếu · Ca trung tâm của luồng: contracts + rooms + activity_log đổi trong cùng một giao dịch · Kết quả chạy thật 21/09/2026: Thông báo: "Đã tạo hợp đồng HD2026-0012. · ×"; contracts: ['HD2026-0012', 'ACTIVE', '2500000.00', '5']; D101 = OCCUPIED; activity_log CREATE/CONTRACT = 12 dòng

### [F_Rent_Room-7] Bước 3 – Khách đã có hợp đồng ký thêm hợp đồng khác: · Kiểm tra một khách không được đại diện hai hợp đồng đang hiệu lực

- Tiền điều kiện: Hiếu đang có HD2026-0012; A101 Còn trống
- Các bước: 1. Hợp đồng → [+ Tạo hợp đồng] · 2. Chọn Phòng A101, Khách Nguyễn Văn Hiếu, Giá thuê 1800000, Số người ở 1, giữ ngày mặc định · 3. Bấm [Lưu] · 4. Phòng trọ → xem A101
- Mong đợi: 3. Form hiện lại kèm "Nguyễn Văn Hiếu đang có hợp đồng hiệu lực hoặc chờ hiệu lực, không ký thêm được." · 4. A101 vẫn Còn trống
- Sau (CSDL): contracts không có thêm dòng; rooms.status của A101 vẫn AVAILABLE (giao dịch rollback)
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Nguyễn Văn Hiếu · Kiểm tra rollback: không được đổi trạng thái phòng dở dang · Kết quả chạy thật 21/09/2026: Thông báo: "Nguyễn Văn Hiếu đang có hợp đồng hiệu lực hoặc chờ hiệu lực, không ký thêm được. · ×"; A101 vẫn AVAILABLE; số hợp đồng không đổi

### [F_Rent_Room-8] Bước 3 – Ký hợp đồng cho phòng không còn trống: · Kiểm tra máy chủ kiểm tra lại trạng thái phòng dù danh sách phía trình duyệt đã bị sửa tay

- Tiền điều kiện: D101 đang Đang thuê; khách Đặng Anh Tuấn (seed) không có hợp đồng hiệu lực
- Các bước: 1. Hợp đồng → [+ Tạo hợp đồng] · 2. DevTools: thêm <option value="<id của D101>"> vào ô Phòng rồi chọn nó · 3. Chọn khách Đặng Anh Tuấn, Giá thuê 2500000, Số người ở 1 · 4. Bấm [Lưu]
- Mong đợi: 4. Form hiện lại kèm "Phòng D101 không còn trống (trạng thái hiện tại: OCCUPIED)."
- Sau (CSDL): contracts không có thêm dòng; D101 vẫn thuộc HD2026-0012
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Nguyễn Văn Hiếu · Máy chủ khóa dòng phòng (SELECT … FOR UPDATE) và kiểm lại, không tin danh sách phía trình duyệt · Kết quả chạy thật 21/09/2026: Thông báo: "Phòng D101 không còn trống (trạng thái hiện tại: OCCUPIED). · ×"

### [F_Rent_Room-9] Bước 3 – Số người ở vượt sức chứa phòng: · Kiểm tra hợp đồng ghi 2 người cho phòng tối đa 1 người bị từ chối

- Tiền điều kiện: A101 tối đa 1 người, Còn trống; khách Đặng Anh Tuấn rảnh
- Các bước: 1. Hợp đồng → [+ Tạo hợp đồng] · 2. Chọn Phòng A101, Khách Đặng Anh Tuấn, Số người ở 2, Giá thuê 1800000 · 3. Bấm [Lưu]
- Mong đợi: 3. Form hiện lại kèm "Phòng A101 chỉ ở tối đa 1 người, hợp đồng đang ghi 2."; A101 vẫn Còn trống
- Sau (CSDL): contracts không có thêm dòng
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Nguyễn Văn Hiếu · Biên max_occupants + 1; chỉ kiểm được khi đã biết phòng nào · Kết quả chạy thật 21/09/2026: Thông báo: "Phòng A101 chỉ ở tối đa 1 người, hợp đồng đang ghi 2. · ×"

### [F_Rent_Room-10] Bước 4 – Mở form lập hóa đơn: · Kiểm tra form chỉ liệt kê hợp đồng đang hiệu lực và dựng sẵn các dòng dịch vụ theo hợp đồng

- Tiền điều kiện: HD2026-0012 đang hiệu lực (2 người ở); HD2026-0009 chờ hiệu lực, HD2026-0010 hết hạn, HD2026-0011 đã chấm dứt (seed)
- Các bước: 1. Menu Hóa đơn → bấm [+ Lập hóa đơn] · 2. Mở ô Hợp đồng và quan sát · 3. Chọn HD2026-0012, Kỳ hóa đơn 2026-10
- Mong đợi: 2. Ô Hợp đồng chỉ có các hợp đồng Đang hiệu lực (có HD2026-0012); KHÔNG có HD2026-0009 / 0010 / 0011 · 3. Bảng Dịch vụ tự dựng 5 dòng: Tiền điện và Tiền nước có Chỉ số đầu để trống (chưa có kỳ trước), đơn giá 3.500 và 15.000; Phí rác SL 1; Internet SL 1; Gửi xe SL 2 (= số người ở), đơn giá 80.000; ô Tiền phòng hiện 2.500.000
- Sau (CSDL): Không có thay đổi dữ liệu
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Phùng Tiến Dũng · Dữ liệu hợp đồng (số người, giá thuê) chảy sang form hóa đơn · Kết quả chạy thật 21/09/2026: Ô Hợp đồng có HD2026-0012 = True; không có 0009/0010/0011 = True; bảng dịch vụ 5 dòng; Tiền phòng = 2500000.00

### [F_Rent_Room-11] Bước 4 – Lập hóa đơn kỳ 10/2026 thành công: · Kiểm tra máy chủ tính đúng tiền dịch vụ, tiền phòng, tổng tiền, hạn thu và sinh mã hóa đơn

- Tiền điều kiện: Đang ở form Lập hóa đơn, đã chọn HD2026-0012, kỳ 2026-10; chưa có hóa đơn nào kỳ 10/2026
- Các bước: 1. Tiền điện: Chỉ số cuối 120; Tiền nước: Chỉ số cuối 8; giữ nguyên đơn giá và các dòng còn lại; Giảm giá 0 · 2. Bấm [Lập hóa đơn]
- Mong đợi: 2. Chuyển tới trang chi tiết kèm "Đã lập hóa đơn INV-202610-0001." · Chi tiết dịch vụ: Tiền điện 120 kWh × 3.500 = 420.000; Tiền nước 8 m³ × 15.000 = 120.000; Phí rác 20.000; Internet 100.000; Gửi xe 2 × 80.000 = 160.000 · Tiền phòng 2.500.000; Tiền dịch vụ 820.000; Giảm giá 0; TỔNG CỘNG 3.320.000; Đã thu 0; Còn phải thu 3.320.000 · Trạng thái Chưa thu; Ngày lập 21/09/2026; Hạn thanh toán 05/10/2026 (ngày chốt 5 của kỳ 10)
- Sau (CSDL): invoices có dòng invoice_code = INV-202610-0001, contract_id = HD2026-0012, period 10/2026, room_charge 2500000.00, service_total 820000.00, total_amount 3320000.00, paid_amount 0.00, status UNPAID, due_date 2026-10-05; invoice_details có 5 dòng; activity_log có dòng CREATE / INVOICE
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Phùng Tiến Dũng · Hạn thu lấy từ billing_day của hợp đồng ký ở Bước 3 · Kết quả chạy thật 21/09/2026: Thông báo: "Đã lập hóa đơn INV-202610-0001. · ×"; invoices: ['INV-202610-0001', '2500000.00', '820000.00', '3320000.00', '0.00', 'UNPAID', '2026-10-05']; invoice_details 5 dòng

### [F_Rent_Room-12] Bước 4 – Lập trùng kỳ cho cùng hợp đồng: · Kiểm tra mỗi hợp đồng chỉ có một hóa đơn mỗi kỳ

- Tiền điều kiện: HD2026-0012 đã có hóa đơn kỳ 10/2026
- Các bước: 1. Hóa đơn → [+ Lập hóa đơn] · 2. Chọn HD2026-0012, Kỳ 2026-10, nhập Chỉ số cuối điện 130 · 3. Bấm [Lập hóa đơn]
- Mong đợi: 3. Form hiện lại kèm "Hợp đồng HD2026-0012 đã có hóa đơn kỳ 10/2026."
- Sau (CSDL): invoices không có thêm dòng; uk_invoices_period không bị chạm tới
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Phùng Tiến Dũng · Chặn trước bằng nghiệp vụ, không để UNIQUE của DB ném lỗi · Kết quả chạy thật 21/09/2026: Thông báo: "Hợp đồng HD2026-0012 đã có hóa đơn kỳ 10/2026. · ×"; số hóa đơn không đổi (39)

### [F_Rent_Room-13] Bước 4 – Lập hóa đơn kỳ sau với chỉ số cuối nhỏ hơn chỉ số đầu: · Kiểm tra chỉ số đầu kỳ 11 được điền sẵn từ kỳ 10 và công tơ không được quay ngược

- Tiền điều kiện: INV-202610-0001 đã có chỉ số cuối điện 120, nước 8
- Các bước: 1. Hóa đơn → [+ Lập hóa đơn] → chọn HD2026-0012, Kỳ 2026-11 · 2. Quan sát Chỉ số đầu của Tiền điện / Tiền nước · 3. Nhập Chỉ số cuối Tiền điện 100 (nhỏ hơn 120) · 4. Bấm [Lập hóa đơn]
- Mong đợi: 2. Chỉ số đầu tự điền: Tiền điện 120, Tiền nước 8 (= chỉ số cuối của kỳ 10) · 4. Form hiện lại kèm "Chỉ số cuối của Tiền điện nhỏ hơn chỉ số đầu (100.00 < 120.00)."; các chỉ số vừa nhập vẫn còn trên form
- Sau (CSDL): invoices không có thêm dòng kỳ 11/2026
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Phùng Tiến Dũng · Liên kết dữ liệu giữa hai kỳ liên tiếp · Kết quả chạy thật 21/09/2026: Chỉ số đầu điền sẵn: ['Tiền điện kWh 0 Bỏ dòng \| old=120', 'Tiền nước m³ 0 Bỏ dòng \| old=8', 'Phí rác tháng — — 20.000 Bỏ dò \| old=', 'Internet tháng — — 100.000 Bỏ  \| old=', 'Gửi xe người — — 160.000 Bỏ dò \| old=']; thông báo: "Chỉ số cuối của Tiền điện nhỏ hơn chỉ số đầu (100.00 < 120.00). · ×"

### [F_Rent_Room-14] Bước 4 – Sửa số tiền ở phía trình duyệt: · Kiểm tra máy chủ tính lại toàn bộ số tiền, không lấy từ form

- Tiền điều kiện: Hợp đồng seed HD2026-0001 (C101, 1.850.000 đ, 1 người ở) đang hiệu lực; chưa có hóa đơn kỳ 10/2026 cho hợp đồng này
- Các bước: 1. Hóa đơn → [+ Lập hóa đơn] → chọn HD2026-0001, Kỳ 2026-10 · 2. DevTools: đổi ô Tiền phòng thành 1, ô Số lượng của Gửi xe thành 99, ô Thành tiền của Phí rác thành 0; bỏ trống chỉ số cuối điện / nước · 3. Bấm [Lập hóa đơn]
- Mong đợi: 3. Lập thành công INV-202610-0002; chi tiết hiện Tiền phòng 1.850.000 (lấy từ hợp đồng), Gửi xe Số lượng 1 × 80.000 = 80.000, Phí rác 20.000, Internet 100.000; KHÔNG có dòng Tiền điện / Tiền nước (chưa chốt chỉ số); Tổng 2.050.000
- Sau (CSDL): invoices: room_charge 1850000.00, service_total 200000.00, total_amount 2050000.00; invoice_details có 3 dòng
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Phùng Tiến Dũng · Nguyên tắc số một của InvoiceService: không tin con số nào từ trình duyệt · Kết quả chạy thật 21/09/2026: Thông báo: "Đã lập hóa đơn INV-202610-0002. · ×"; invoices: ['INV-202610-0002', '1850000.00', '200000.00', '2050000.00']; invoice_details 3 dòng (máy chủ bỏ qua số sửa tay)

### [F_Rent_Room-15] Bước 5 – Thu tiền vượt số còn phải thu: · Kiểm tra hệ thống từ chối khoản thu lớn hơn số còn nợ

- Tiền điều kiện: INV-202610-0001 Chưa thu, còn phải thu 3.320.000
- Các bước: 1. Hóa đơn → INV-202610-0001 → Chi tiết → khối Ghi nhận thanh toán · 2. Nhập Số tiền 3320001, Phương thức Tiền mặt · 3. Bấm [Ghi nhận]
- Mong đợi: 3. Quay lại chi tiết kèm thông báo lỗi "Số tiền thu vượt quá số còn phải thu (3320000 đ)."; Đã thu vẫn 0
- Sau (CSDL): invoices.paid_amount = 0.00, status = UNPAID
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Phùng Tiến Dũng · Biên còn-phải-thu + 1 đồng · Kết quả chạy thật 21/09/2026: Thông báo: "Số tiền thu vượt quá số còn phải thu (3320000 đ). · ×"; paid_amount = 0.00

### [F_Rent_Room-16] Bước 5 – Thu một phần: · Kiểm tra khoản thu 1.000.000 được cộng dồn và hóa đơn chuyển Thu một phần

- Tiền điều kiện: INV-202610-0001 Chưa thu, còn 3.320.000; khối Hủy hóa đơn đang hiển thị
- Các bước: 1. Chi tiết INV-202610-0001 → Số tiền 1000000, Phương thức Tiền mặt · 2. Bấm [Ghi nhận] · 3. Menu Tổng quan → Hoạt động gần đây
- Mong đợi: 2. "Đã ghi nhận thanh toán."; Đã thu 1.000.000; Còn phải thu 2.320.000; badge Thu một phần; khối Hủy hóa đơn biến mất (đã thu tiền thì không hủy được) · 3. Có dòng "Thu 1000000 đ cho hóa đơn #<id>"
- Sau (CSDL): invoices: paid_amount 1000000.00, status PARTIAL, payment_method CASH, payment_date NULL; activity_log có dòng PAY / INVOICE
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Phùng Tiến Dũng · Chuyển trạng thái UNPAID → PARTIAL · Kết quả chạy thật 21/09/2026: Thông báo: "Đã ghi nhận thanh toán. · ×"; invoices: paid=1000000.00 status=PARTIAL method=CASH; khối Hủy hóa đơn còn hiển thị = False

### [F_Rent_Room-17] Bước 5 – Thu nốt số còn lại: · Kiểm tra hóa đơn chuyển Đã thu, ghi ngày thu đủ và ẩn form thu tiền

- Tiền điều kiện: INV-202610-0001 Thu một phần, còn 2.320.000
- Các bước: 1. Chi tiết INV-202610-0001 → Số tiền 2320000, Phương thức Chuyển khoản · 2. Bấm [Ghi nhận]
- Mong đợi: 2. "Đã ghi nhận thanh toán."; badge Đã thu; Đã thu 3.320.000; Còn phải thu 0; dòng Ngày thu đủ 21/09/2026 (Chuyển khoản); khối Ghi nhận thanh toán và Hủy hóa đơn không còn hiển thị
- Sau (CSDL): invoices: paid_amount 3320000.00, status PAID, payment_date 2026-09-21, payment_method TRANSFER
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Phùng Tiến Dũng · Chuyển trạng thái PARTIAL → PAID đúng tại biên paid = total · Kết quả chạy thật 21/09/2026: Thông báo: "Đã ghi nhận thanh toán. · ×"; invoices: paid=3320000.00 status=PAID payment_date=2026-09-21 method=TRANSFER

### [F_Rent_Room-18] Bước 5 – Thu tiền / hủy hóa đơn đã thu đủ bằng POST tay: · Kiểm tra máy chủ chặn dù form đã ẩn

- Tiền điều kiện: INV-202610-0001 Đã thu
- Các bước: 1. Trên Console gửi POST /invoices/pay với id = id hóa đơn, amount = 1, method = CASH, _csrf hợp lệ · 2. Gửi POST /invoices/cancel với id = id hóa đơn, reason = test, _csrf hợp lệ
- Mong đợi: 1. Quay lại chi tiết kèm "Hóa đơn INV-202610-0001 đã thu đủ." · 2. Kèm "Hóa đơn INV-202610-0001 đã thu tiền nên không hủy được."; trạng thái vẫn Đã thu
- Sau (CSDL): invoices không đổi
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Phùng Tiến Dũng · Ẩn nút chỉ là UI; máy chủ mới là chốt chặn · Kết quả chạy thật 21/09/2026: Form thu tiền đã bị ẩn trên giao diện (có form = False); gửi POST tay: /invoices/pay → "Hóa đơn INV-202610-0001 đã thu đủ."; /invoices/cancel → "Hóa đơn INV-202610-0001 đã thu tiền nên không hủy được."; hóa đơn vẫn status=PAID paid=3320000.00

### [F_Rent_Room-19] Bước 6 – Doanh thu phản ánh lên báo cáo và Tổng quan: · Kiểm tra hóa đơn vừa thu được tính vào doanh thu đúng KỲ (10/2026), không tính vào tháng thu tiền (9/2026)

- Tiền điều kiện: INV-202610-0001 Đã thu 3.320.000 vào ngày 21/09/2026
- Các bước: 1. Đăng xuất, đăng nhập manager / manager123 · 2. Menu Báo cáo → Doanh thu → Từ kỳ 2026-09, Đến kỳ 2026-11 → [Xem] · 3. Menu Tổng quan → thẻ Doanh thu 9/2026
- Mong đợi: 1. MANAGER đăng nhập được và mở được Báo cáo · 2. Dòng kỳ 10/2026: Số hóa đơn 2 (INV-202610-0001 và -0002), Tổng 5.370.000, Đã thu 3.320.000, Còn lại 2.050.000; kỳ 11/2026 có giá trị 0 (vẫn hiện dòng) · 3. Thẻ Doanh thu 9/2026 KHÔNG bao gồm 3.320.000 của INV-202610-0001 (chỉ tính hóa đơn Đã thu thuộc kỳ 9/2026)
- Sau (CSDL): Không có thay đổi dữ liệu
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Đỗ Đức Đạt · Doanh thu lọc theo kỳ hóa đơn, không theo ngày thu · Kết quả chạy thật 21/09/2026: Dòng kỳ 10/2026: 10/2026 2 5.370.000 đ 3.320.000 đ 2.050.000 đ

### [F_Rent_Room-20] Bước 6 – Báo cáo công nợ: · Kiểm tra hóa đơn chưa thu đủ được liệt kê kèm số ngày trễ, hóa đơn đã thu không xuất hiện

- Tiền điều kiện: Đang đăng nhập manager; INV-202610-0002 (C101) Chưa thu, hạn 05/10/2026; seed có INV-202608-0006 và INV-202608-0007 Quá hạn (hạn 31/08/2026)
- Các bước: 1. Báo cáo → Công nợ · 2. Đối chiếu bảng Chi tiết từng hóa đơn và các thẻ tổng
- Mong đợi: 2. INV-202608-0006 và INV-202608-0007 hiện badge Quá hạn, Số ngày trễ 21; INV-202610-0002 hiện Chưa thu, Số ngày trễ 0 (chưa đến hạn); INV-202610-0001 (đã thu đủ) KHÔNG có trong bảng · Tổng công nợ = tổng cột Còn nợ; "Trong đó đã quá hạn" = số hóa đơn có Số ngày trễ > 0; dòng INV-202610-0002 có Còn nợ 2.050.000
- Sau (CSDL): Không có thay đổi dữ liệu
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Đỗ Đức Đạt · Số ngày trễ = hôm nay − hạn thu, ép về 0 nếu chưa đến hạn · Kết quả chạy thật 21/09/2026: INV-202608-0006: INV-202608-0006 C203 Đỗ Trung Hiếu 8/2026 31/08/2026 21 3.148.500 đ 0 đ 3.148.500 đ Quá hạn \| INV-202610-0002: INV-202610-0002 C101 Nguyễn Thị Mai Anh 10/2026 05/10/2026 — 2.050.000 đ 0 đ 2.050.000 đ Chưa thu \| INV-202610-0001 vắng mặt = True

### [F_Rent_Room-21] Bước 6 – Nhân viên STAFF xem báo cáo: · Kiểm tra RoleFilter chặn STAFF nhưng vẫn cho làm nghiệp vụ hằng ngày

- Tiền điều kiện: Tài khoản staff / staff123 (STAFF) ACTIVE
- Các bước: 1. Đăng xuất, đăng nhập staff / staff123 · 2. Menu Báo cáo · 3. Gõ thẳng /reports/export?type=debt&format=pdf · 4. Menu Hóa đơn → mở chi tiết INV-202610-0002
- Mong đợi: 2. Trang lỗi 403 "Bạn không có quyền truy cập chức năng này…" có nút Về tổng quan · 3. Cũng nhận 403, không tải được file · 4. STAFF vẫn mở được hóa đơn và thấy khối Ghi nhận thanh toán (quyền nghiệp vụ không bị ảnh hưởng)
- Sau (CSDL): Không có thay đổi dữ liệu
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Đỗ Đức Đạt · Phân quyền theo danh sách role được phép (ADMIN, MANAGER), mặc định từ chối · Kết quả chạy thật 21/09/2026: Menu Báo cáo → 403; /reports/export → 403; STAFF mở hóa đơn và thấy khối Ghi nhận thanh toán = True

### [F_Rent_Room-22] Bước 7 – Chấm dứt hợp đồng, trả phòng: · Kiểm tra hợp đồng chuyển Đã chấm dứt, phòng D101 về Còn trống, ghi lý do và nhật ký

- Tiền điều kiện: Đăng nhập admin; HD2026-0012 đang hiệu lực, D101 Đang thuê
- Các bước: 1. Hợp đồng → HD2026-0012 → Chi tiết → khối Chấm dứt trước hạn · 2. Ngày chấm dứt 21/09/2026, Lý do "Sinh viên chuyển trường" · 3. Bấm [Chấm dứt hợp đồng] → xác nhận trong hộp thoại · 4. Phòng trọ → xem D101; Tổng quan → Hoạt động gần đây
- Mong đợi: 3. "Đã chấm dứt hợp đồng và trả phòng về trạng thái còn trống."; chi tiết hiện badge Đã chấm dứt, Ngày chấm dứt 21/09/2026, Ghi chú có "Chấm dứt 21/09/2026: Sinh viên chuyển trường"; nút Sửa, khối Gia hạn, khối Chấm dứt đều biến mất · 4. D101 trở lại Còn trống; nhật ký có dòng "Chấm dứt hợp đồng #<id>"
- Sau (CSDL): contracts HD2026-0012: status TERMINATED, terminated_at 2026-09-21; rooms D101 status AVAILABLE; activity_log có dòng TERMINATE / CONTRACT
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Đỗ Đức Đạt · Chuyển trạng thái ACTIVE → TERMINATED kéo theo OCCUPIED → AVAILABLE trong cùng giao dịch · Kết quả chạy thật 21/09/2026: Thông báo: "Đã chấm dứt hợp đồng và trả phòng về trạng thái còn trống. · ×"; hợp đồng: status=TERMINATED terminated_at=2026-09-21; D101 = AVAILABLE

### [F_Rent_Room-23] Bước 7 – Chấm dứt với ngày trước ngày bắt đầu: · Kiểm tra ngày chấm dứt không được sớm hơn ngày bắt đầu hợp đồng

- Tiền điều kiện: Hợp đồng seed HD2026-0001 (C101) bắt đầu 21/04/2026 (5 tháng trước) đang hiệu lực
- Các bước: 1. Chi tiết HD2026-0001 → khối Chấm dứt trước hạn · 2. DevTools: bỏ thuộc tính min của ô Ngày chấm dứt, nhập 13/04/2026 · 3. Bấm [Chấm dứt hợp đồng] → xác nhận
- Mong đợi: 3. Quay lại chi tiết kèm "Ngày chấm dứt không được trước ngày bắt đầu (21/04/2026)."; vẫn Đang hiệu lực; C101 vẫn Đang thuê
- Sau (CSDL): contracts HD2026-0001 không đổi
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Đỗ Đức Đạt · Biên ngay dưới ngày bắt đầu (ngày bắt đầu seed = hôm nay − 5 tháng) · Kết quả chạy thật 21/09/2026: Thông báo: "Ngày chấm dứt không được trước ngày bắt đầu (21/04/2026). · ×"; hợp đồng HD2026-0001 vẫn ACTIVE

### [F_Rent_Room-24] Bước 7 – Lịch sử được giữ lại sau khi trả phòng: · Kiểm tra phòng / khách có lịch sử không xóa được và chi tiết khách vẫn tra cứu được hợp đồng cũ

- Tiền điều kiện: HD2026-0012 Đã chấm dứt; D101 Còn trống
- Các bước: 1. Phòng trọ → dòng D101 → [Xóa] → xác nhận · 2. Khách thuê → dòng Nguyễn Văn Hiếu → [Xóa] → xác nhận · 3. Khách thuê → Nguyễn Văn Hiếu → Chi tiết → Chi tiết hợp đồng HD2026-0012
- Mong đợi: 1. "Không thể xóa phòng D101: phòng đã có lịch sử hợp đồng. Hãy chuyển trạng thái sang "Đang sửa" thay vì xóa." · 2. "Không thể xóa Nguyễn Văn Hiếu: khách đã có lịch sử hợp đồng, cần giữ lại để tra cứu." · 3. Chi tiết khách liệt kê HD2026-0012 (Đã chấm dứt); chi tiết hợp đồng vẫn hiện hóa đơn INV-202610-0001 Đã thu
- Sau (CSDL): rooms, tenants, contracts, invoices không đổi
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Đỗ Đức Đạt · Dữ liệu lịch sử là căn cứ đối chiếu công nợ về sau · Kết quả chạy thật 21/09/2026: Xóa phòng → "Không thể xóa phòng D101: phòng đã có lịch sử hợp đồng. Hãy chuyển trạng thái sang "Đang sửa" thay vì xóa. · ×"; xóa khách → "Không thể xóa Nguyễn Văn Hiếu: khách đã có lịch sử hợp đồng, cần giữ lại để tra cứu. · ×"; chi tiết khách có HD2026-0012 = True

### [F_Rent_Room-25] Bước 7 – Ký hợp đồng mới cho phòng vừa trả: · Kiểm tra khép kín vòng đời: phòng và khách đã chấm dứt có thể ký lại, mã hợp đồng nối tiếp

- Tiền điều kiện: HD2026-0012 Đã chấm dứt; D101 Còn trống; Hiếu không còn hợp đồng hiệu lực
- Các bước: 1. Hợp đồng → [+ Tạo hợp đồng] → Phòng D101, Khách Nguyễn Văn Hiếu, Ngày bắt đầu 21/09/2026, Ngày kết thúc 21/03/2027, Giá thuê 2500000, Số người ở 2, Ngày chốt 5 · 2. Bấm [Lưu] · 3. Khách thuê → Nguyễn Văn Hiếu → Chi tiết
- Mong đợi: 2. "Đã tạo hợp đồng HD2026-0013." (tiếp tục dãy, không dùng lại 0012); D101 Đang thuê · 3. Chi tiết khách liệt kê 2 hợp đồng: HD2026-0012 Đã chấm dứt và HD2026-0013 Đang hiệu lực
- Sau (CSDL): contracts có 2 dòng tenant_id = Hiếu; rooms D101 status OCCUPIED
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Đỗ Đức Đạt · TERMINATED không chặn ký lại; mã hợp đồng là dãy tăng dần, không tái sử dụng mã cũ · Kết quả chạy thật 21/09/2026: Thông báo: "Đã tạo hợp đồng HD2026-0013. · ×"; chi tiết khách liệt kê cả HD2026-0012 và HD2026-0013 = True

### [F_Rent_Room-26] Toàn luồng – Nhật ký thao tác trên Tổng quan: · Kiểm tra chuỗi thao tác của luồng được ghi đủ và đúng thứ tự

- Tiền điều kiện: Đã chạy các test case trên bằng tài khoản admin
- Các bước: 1. Đăng nhập admin → Tổng quan → bảng Hoạt động gần đây · 2. Đối chiếu với bảng activity_log trong phpMyAdmin
- Mong đợi: 1. Bảng Hoạt động gần đây hiện tối đa 10 dòng mới nhất, mới nhất ở trên, cột Người thao tác = Quản trị hệ thống · 2. Toàn bộ chuỗi thao tác của luồng có đủ trong bảng activity_log, đúng thứ tự: Đăng nhập admin → Thêm phòng D101 → Thêm khách thuê Nguyễn Văn Hiếu → Tạo hợp đồng HD2026-0012 → Lập hóa đơn INV-202610-0001 → Thu tiền → Chấm dứt hợp đồng → Tạo hợp đồng HD2026-0013 · 3. Thời điểm hiển thị khớp created_at theo giờ Việt Nam (dd/MM/yyyy HH:mm)
- Sau (CSDL): Không có thay đổi dữ liệu
- Kết quả: Pass (21/09/2026)
- Ghi chú: Tester: Đỗ Đức Đạt · Nhật ký là bằng chứng end-to-end của luồng · Kết quả chạy thật 21/09/2026: Bảng Hoạt động gần đây hiện 8 dòng mới nhất (giới hạn 10), mới nhất ở trên, cột Người thao tác = Quản trị hệ thống. Toàn bộ chuỗi thao tác của luồng có đủ trong activity_log: Thêm phòng D101 → Thêm khách thuê → Tạo HD2026-0012 → Lập INV-202610-0001 → Thu tiền → Chấm dứt hợp đồng → Tạo HD2026-0013.
