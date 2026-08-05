# 8 Prompt triển khai StudentRent (bản đã sửa cho Windows + macOS)

Bản gốc của các prompt này được viết mà chưa đối chiếu với `database/schema.sql` và code
đã có trong repo. Hệ quả: **phần lớn tên field trong prompt không tồn tại trong DB thật**,
nên code sinh ra sẽ không compile hoặc ném `SQLException` ngay khi chạy — chuyện này xảy
ra trên **cả hai** hệ điều hành, không liên quan gì tới Mac/Win.

Bản dưới đây sửa hai nhóm vấn đề:

1. **Khớp lại với repo thật** — tên bảng/cột, enum, URL servlet, tên file đã tồn tại.
2. **Chốt các điểm sẽ lệch giữa Mac và Win** — đường dẫn upload, font PDF tiếng Việt,
   múi giờ, locale số, chữ hoa/thường của tên bảng và tên file.

## Cách dùng

Paste **PHẦN 0** trước, rồi paste **một** phase. Đừng gộp nhiều phase.
Sau mỗi phase, chạy `mvn clean package` và mở lại app trước khi sang phase tiếp theo.

---

# PHẦN 0 — Dán trước mỗi phase

```text
NGỮ CẢNH DỰ ÁN (đọc trước khi viết code)

Repo StudentRent: Java 17, Jakarta Servlet 6, JSP + JSTL 3, MySQL 8, HikariCP.
Chạy bằng Tomcat 10.1 nhúng qua Cargo: `mvn package cargo:run`
(BẮT BUỘC có `package` trước `cargo:run` — Cargo chỉ deploy file WAR có trong
target/, không tự build nó).

ĐỌC TRƯỚC KHI VIẾT DÒNG CODE ĐẦU TIÊN:
- database/schema.sql — đây là NGUỒN SỰ THẬT về tên bảng và tên cột.
- src/main/webapp/WEB-INF/web.xml
- src/main/java/com/studentrent/util/Constants.java
Nếu yêu cầu bên dưới mâu thuẫn với schema.sql, hãy theo schema.sql và nói rõ chỗ lệch.

NHỮNG THỨ ĐÃ CÓ SẴN — KHÔNG tạo lại, KHÔNG khai báo trùng:
- web.xml đã khai báo 3 filter theo thứ tự EncodingFilter -> AuthFilter -> RoleFilter,
  AuthFilter đã map /*, RoleFilter đã map /reports. ĐỪNG thêm filter-mapping mới cho
  chúng — thêm nữa là filter chạy hai lần.
- web.xml đã có error-page cho 403, 404, 500 và cho java.lang.Throwable.
- web.xml đã ghim locale vi_VN qua jakarta.servlet.jsp.jstl.fmt.locale.
- com.studentrent.util.AppContextListener đã là @WebListener. Sửa nó, ĐỪNG tạo
  thêm ContextListener khác — hai listener cùng khởi tạo pool sẽ xung đột.
- DBConnection đã hoàn chỉnh (HikariCP, đọc db.properties < db.local.properties < env).
  Dùng DBConnection.getConnection(), đừng gọi DriverManager.
- PasswordUtil đã hoàn chỉnh (BCrypt 12 rounds), có hash() và verify().
- JsonUtil đã hoàn chỉnh để ghi JSON response.
- Constants.java đã định nghĩa, PHẢI dùng lại thay vì hardcode chuỗi:
    SESSION_USER = "currentUser"
    ATTR_ERROR = "errorMessage", ATTR_SUCCESS = "successMessage"
    VIEW_PREFIX = "/WEB-INF/views/", VIEW_SUFFIX = ".jsp", helper view(String)
    DEFAULT_PAGE_SIZE = 10
    UPLOAD_DIR = "uploads"
    ALLOWED_IMAGE_EXTENSIONS = {"jpg","jpeg","png","webp"}
    ROLE_ADMIN = "ADMIN", ROLE_MANAGER = "MANAGER", ROLE_STAFF = "STAFF"

RÀNG BUỘC BẮT BUỘC:
- web.xml đặt <scripting-invalid>true</scripting-invalid> cho *.jsp.
  Scriptlet <% %> trong JSP sẽ làm JSP KHÔNG COMPILE. Chỉ dùng EL + JSTL.
- File CSS của dự án là assets/css/style.css. KHÔNG có main.css.
- URL servlet đã có, dùng dạng SỐ NHIỀU. Giữ nguyên, đừng đổi sang số ít:
    /login  /logout  /dashboard
    /rooms  /rooms/*      /tenants  /tenants/*
    /contracts  /contracts/*   /invoices  /invoices/*   /reports  /reports/*
  Đổi thành /room, /tenant... sẽ làm mọi link trong JSP hiện có 404, và khai báo
  trùng urlPattern làm Tomcat deploy thất bại.
- Mọi link/asset trong JSP dùng ${pageContext.request.contextPath}, không viết
  "/student-rent/..." cứng.

QUY TẮC ĐỂ CODE CHẠY GIỐNG NHAU TRÊN macOS VÀ WINDOWS — áp dụng cho mọi phase:

1. MÚI GIỜ. Không bao giờ gọi LocalDate.now() / LocalDateTime.now() không tham số.
   Máy đặt timezone khác nhau sẽ cho ra "hôm nay" khác nhau, làm hóa đơn quá hạn
   và hợp đồng hết hạn tính sai. Luôn dùng:
       public static final ZoneId ZONE = ZoneId.of("Asia/Ho_Chi_Minh");
       LocalDate.now(ZONE)
   Đặt hằng ZONE này trong Constants.java và dùng ở mọi nơi.

2. LOCALE. Không bao giờ dùng String.format / NumberFormat / parseDouble phụ thuộc
   locale mặc định của máy. Locale mặc định khác nhau đổi cả dấu phân cách thập phân
   (1.5 vs 1,5) nên tiền nhập vào bị đọc sai. Khi đọc số tiền người dùng gõ, chuẩn
   hóa chuỗi rồi parse; khi format thì truyền Locale tường minh:
       new Locale("vi", "VN")
   Trong JSP thì đã có locale vi_VN ghim ở web.xml, cứ dùng <fmt:formatNumber>.

3. TÊN BẢNG VÀ CỘT TRONG SQL: viết chữ thường 100%, đúng như schema.sql.
   MySQL có lower_case_table_names mặc định khác nhau theo hệ điều hành (Windows 1,
   macOS 2, Linux 0), nên `SELECT * FROM Rooms` chạy trên Windows nhưng lỗi
   "table doesn't exist" trên Linux/CI.

4. ĐƯỜNG DẪN FILE: dùng java.nio.file.Path và Path.resolve().
   Không nối chuỗi với "\" hay "/". Không hardcode đường dẫn tuyệt đối.
   Đường dẫn LƯU VÀO DB luôn dùng dấu "/", không bao giờ chứa File.separator của
   Windows — nếu không thì ảnh lưu ở máy Win sẽ không hiển thị được ở máy Mac.

5. TÊN FILE: chữ hoa/thường phải khớp CHÍNH XÁC với file trên đĩa.
   macOS mặc định không phân biệt hoa thường nên href sai hoa thường vẫn chạy trên
   Mac rồi 404 trên Linux/CI — loại bug này ẩn mình trên máy Mac.

6. FILE SINH RA: UTF-8 không BOM, kết thúc dòng LF. Không dùng
   System.lineSeparator() cho nội dung file/response.

7. VERSION DEPENDENCY: mọi dependency mới phải khai báo version qua property trong
   <properties> của pom.xml, không viết version trực tiếp trong <dependency>.

8. KHÔNG hardcode đường dẫn của bất kỳ máy nào vào code, config hay tài liệu.
```

---

# PHASE 1 — Authentication & Filter

```text
Implement module xác thực. Đọc PHẦN 0 trước.

1. Model User.java — PHẢI khớp bảng `users` trong schema.sql:
   id (int), username, passwordHash, fullName, email, phone,
   role (String: ADMIN | MANAGER | STAFF — CÓ BA giá trị, không phải hai),
   status (String: ACTIVE | LOCKED — là ENUM, KHÔNG phải boolean isActive),
   lastLoginAt (LocalDateTime), createdAt, updatedAt (LocalDateTime)

   Ánh xạ cột -> field (bản gốc của tôi ghi sai, dùng bảng này):
     id             -> userId hoặc id
     password_hash  -> passwordHash   (KHÔNG phải "password")
     full_name      -> fullName
     status         -> status         (KHÔNG phải isActive)
     last_login_at  -> lastLoginAt
     created_at     -> createdAt
     updated_at     -> updatedAt

2. UserDAO.java (PreparedStatement, SQL chữ thường):
   - findByUsername(String username): User
   - findById(int id): User
   - updateLastLogin(int userId): void   -- UPDATE users SET last_login_at = ?
     Truyền LocalDateTime.now(Constants.ZONE), KHÔNG dùng NOW() của MySQL và cũng
     không dùng now() không tham số của Java.

3. AuthService.java:
   - login(String username, String password): User
     Lấy user theo username; nếu null trả null. Nếu status != "ACTIVE" thì coi như
     đăng nhập thất bại (tài khoản bị LOCKED). Verify bằng
     PasswordUtil.verify(password, user.getPasswordHash()) — hàm này đã có sẵn,
     đừng gọi BCrypt.checkpw trực tiếp. Thành công thì gọi updateLastLogin().
   - logout(HttpSession session): void
   - isAuthenticated(HttpSession session): boolean
   - getCurrentUser(HttpSession session): User
   Dùng Constants.SESSION_USER làm key session, không viết "currentUser" cứng.

4. AuthFilter.java — file ĐÃ TỒN TẠI với phần redirect bị comment và ghi chú TODO.
   Bỏ comment và hoàn thiện, GIỮ NGUYÊN hàm isPublic() đã có.
   - Cho qua không cần login: /login, /logout, /assets/*, và các trang lỗi.
   - Chưa login mà vào trang khác -> redirect ${contextPath}/login
   - ĐÃ login mà vào /login -> redirect ${contextPath}/dashboard
   KHÔNG thêm <filter-mapping> vào web.xml — AuthFilter đã được map /* sẵn.

5. LoginServlet.java — file ĐÃ TỒN TẠI, @WebServlet("/login") đã đúng, chỉ implement:
   - GET: forward Constants.view("auth/login")
   - POST: gọi AuthService.login().
     Thành công: gọi request.changeSessionId() để chống session fixation TRƯỚC khi
     set attribute, rồi set Constants.SESSION_USER, redirect /dashboard.
     Thất bại: set Constants.ATTR_ERROR vào request, giữ lại username đã gõ,
     forward LẠI login.jsp (forward, không redirect — redirect sẽ mất thông báo lỗi).

6. LogoutServlet.java — ĐÃ TỒN TẠI, @WebServlet("/logout"). Invalidate session,
   redirect /login.

7. login.jsp — ĐÃ TỒN TẠI và đã escape đúng bằng <c:out>. KHÔNG viết lại từ đầu và
   KHÔNG nhúng CSS inline vào nó. Toàn bộ style nằm ở assets/css/style.css (dark
   theme tông tím #6c63ff sẽ làm ở Phase 7). Chỉ sửa nếu cần thêm chỗ hiện lỗi.

8. KHÔNG sửa web.xml ở phase này. Filter mapping và session-timeout 30 phút đã có.

Xong thì chạy `mvn package cargo:run`, mở http://localhost:8080/student-rent/login
và đăng nhập bằng admin / admin123 (hash BCrypt đã có trong seed của schema.sql).
```

---

# PHASE 2 — Quản lý Phòng (CRUD)

```text
Implement module Quản lý Phòng. Đọc PHẦN 0 trước.

1. Model — PHẢI khớp schema.sql. Bản gốc của tôi đặt tên field sai gần hết:

   RoomType.java  <- bảng `room_types`
     id, name, basePrice, maxOccupants, defaultArea, description,
     createdAt, updatedAt
     (KHÔNG có typeId/typeName/capacity — dùng id/name/maxOccupants.
      Có thêm defaultArea mà bản gốc bỏ sót.)

   Room.java  <- bảng `rooms`
     id, roomCode, roomTypeId, floorNo, area, price, maxOccupants,
     status, imagePath, description, createdAt, updatedAt
     + field join (không phải cột, đánh dấu rõ trong javadoc): roomTypeName
     (KHÔNG có roomNumber -> là roomCode. KHÔNG có floor -> là floorNo.
      QUAN TRỌNG: rooms có cột `price` RIÊNG, khác base_price của loại phòng.
      Đây mới là giá thuê thật của phòng đó.)

   status của rooms có BỐN giá trị: AVAILABLE | OCCUPIED | RESERVED | MAINTENANCE
   (bản gốc bỏ sót RESERVED — thiếu nó thì dropdown lọc và badge sẽ vỡ khi DB có
    phòng ở trạng thái này).

2. DAO — PreparedStatement, SQL chữ thường, không ORM:
   - RoomTypeDAO: findAll(), findById(int), insert(), update(), delete(int)
   - RoomDAO:
       findAll(String statusFilter, Integer floorNo): List<Room>
         Filter động. floorNo là Integer (nullable) chứ KHÔNG phải int — dùng int
         thì không biểu diễn được "không lọc tầng", 0 lại là giá trị hợp lệ.
         Ghép WHERE bằng danh sách điều kiện + danh sách tham số, mỗi giá trị vẫn
         đi qua PreparedStatement. Tuyệt đối không nối chuỗi giá trị vào SQL.
       findById(int), findAvailable(), insert(Room), update(Room),
       updateStatus(int roomId, String status), delete(int)
       countByStatus(): Map<String,Integer>   -- GROUP BY status, cho dashboard

3. RoomService.java: validate roomCode unique; không cho xóa phòng đang có hợp đồng
   ACTIVE hoặc PENDING (không chỉ ACTIVE — hợp đồng PENDING cũng đã giữ phòng).

4. RoomServlet.java — ĐÃ TỒN TẠI với @WebServlet({"/rooms", "/rooms/*"}).
   GIỮ NGUYÊN urlPatterns đó, đừng đổi sang "/room".
   Điều hướng bằng query param `action`: list | add | edit | delete | detail
   - list: lọc theo status + floor qua query param, phân trang
     Constants.DEFAULT_PAGE_SIZE
   - add/edit: GET trả form, POST ghi
   - delete: chỉ nhận POST, kiểm tra ràng buộc trước khi xóa
   - detail: thông tin phòng + danh sách sinh viên đang ở
   Thông báo dùng session flash với Constants.ATTR_SUCCESS / ATTR_ERROR, xóa
   attribute ngay sau khi đọc để không hiện lại ở request sau.

5. JSP — JSTL, KHÔNG scriptlet (web.xml chặn, JSP sẽ không compile):
   - room/list.jsp và room/form.jsp: ĐÃ TỒN TẠI, sửa tiếp.
   - room/detail.jsp: TẠO MỚI (chưa có).
   Mọi giá trị do người dùng nhập phải bọc <c:out> hoặc fn:escapeXml.
   Tiền dùng <fmt:formatNumber> (locale vi_VN đã ghim ở web.xml).

6. CSS: dùng assets/css/style.css đang có. KHÔNG có main.css và cũng đừng tạo nó —
   Phase 7 sẽ hoàn thiện chính file style.css này. Tên file phải khớp chính xác
   hoa thường, vì href sai hoa thường vẫn chạy trên macOS nhưng 404 trên Linux.
```

---

# PHASE 3 — Quản lý Sinh viên (Tenant)

```text
Implement module Quản lý Sinh viên. Đọc PHẦN 0 trước.

1. Tenant.java <- bảng `tenants`. Ánh xạ đúng (bản gốc sai gần hết tên):
     id, fullName, idCard, dateOfBirth (LocalDate), gender, phone, email,
     hometown, school, studentCode, avatarPath, note, createdAt, updatedAt
   Bảng đối chiếu với bản gốc:
     student_code   -> studentCode    (KHÔNG phải studentId)
     school         -> school         (KHÔNG phải university)
     hometown       -> hometown       (KHÔNG phải address)
     date_of_birth  -> dateOfBirth    (KHÔNG phải dob)
     avatar_path    -> avatarPath     (KHÔNG phải avatarUrl)
     và có thêm cột `note` mà bản gốc bỏ sót.
   gender: MALE | FEMALE | OTHER

2. TenantDAO.java:
   - search(String keyword): List<Tenant>
     LIKE trên full_name, student_code, id_card, phone.
     Tham số LIKE phải bind qua PreparedStatement dạng setString(i, "%"+kw+"%"),
     KHÔNG nối "%" vào chuỗi SQL. Escape ký tự % và _ trong keyword người dùng gõ,
     nếu không thì gõ "%" sẽ trả về toàn bộ bảng.
   - findById(int), insert(Tenant): int (RETURN_GENERATED_KEYS), update, delete(int)
   - findByRoomId(int roomId): List<Tenant>   -- join qua contracts đang ACTIVE
   - existsByStudentCode(String, Integer excludeId): boolean
   - existsByIdCard(String, Integer excludeId): boolean

3. TenantService.java: validate studentCode unique, idCard unique, phone 10 số,
   email hợp lệ.

   UPLOAD ẢNH — đây là chỗ dễ lệch nhất giữa hai máy, làm đúng như sau:

   ĐỪNG lưu vào webapp/uploads/avatars/. Thư mục đó nằm trong bản WAR đã giải nén
   dưới target/, nên `mvn clean package` XÓA SẠCH mọi ảnh đã upload, và đường dẫn
   thật của nó khác nhau giữa hai máy.

   Thay vào đó:
   - Thêm key mới vào db.properties:  app.upload.dir=
     Để trống nghĩa là dùng mặc định: Path.of(System.getProperty("user.home"),
     "studentrent-uploads"). Mỗi máy muốn chỗ khác thì khai báo lại trong
     db.local.properties (file này .gitignore bỏ qua nên không xung đột khi đồng bộ).
   - Đọc key đó lúc khởi động trong AppContextListener, tạo thư mục bằng
     Files.createDirectories(), lưu Path vào ServletContext attribute.
   - Tên file lưu = UUID.randomUUID() + "." + ext (ext lấy chữ thường). TUYỆT ĐỐI
     không dùng lại tên file gốc do người dùng đặt: tên đó có thể chứa ký tự hợp lệ
     trên macOS nhưng bị Windows từ chối (: * ? " < > |), chứa dấu tiếng Việt bị
     mã hóa khác nhau, hoặc chứa ".." để leo thư mục.
   - Validate extension theo Constants.ALLOWED_IMAGE_EXTENSIONS (jpg, jpeg, png,
     webp — có jpeg, bản gốc bỏ sót), và kiểm tra kích thước <= 2MB.
   - Cột avatar_path chỉ lưu tên file hoặc đường dẫn tương đối dùng dấu "/".
     KHÔNG lưu đường dẫn tuyệt đối và không lưu "\" — ảnh upload ở máy Windows sẽ
     không hiển thị được ở máy Mac.
   - Ảnh được phục vụ qua một servlet đọc từ thư mục cấu hình đó, không phải qua
     đường dẫn tĩnh trong webapp.

4. TenantServlet.java — ĐÃ TỒN TẠI với @WebServlet({"/tenants","/tenants/*"}) và
   đã khai báo @MultipartConfig. GIỮ NGUYÊN cả hai, chỉ điều chỉnh maxFileSize
   nếu cần. Dùng request.getPart() của Servlet 6 — KHÔNG dùng commons-fileupload,
   Servlet 6 đã làm được việc này.
   action = list | add | edit | delete | detail | search
   - list: search param, phân trang Constants.DEFAULT_PAGE_SIZE
   - detail: thông tin + lịch sử hợp đồng

5. AJAX search: nếu request có header Accept chứa "application/json" thì trả JSON
   bằng JsonUtil (đã có sẵn, dùng Gson trong pom) thay vì forward JSP.
   Response phải set charset UTF-8 tường minh:
     response.setContentType("application/json;charset=UTF-8")
   Thiếu charset thì tên tiếng Việt sẽ ra ký tự lạ, và mức độ lỗi khác nhau tùy
   locale mặc định của từng máy.

6. JSP: tenant/list.jsp và tenant/form.jsp ĐÃ TỒN TẠI, sửa tiếp.
   tenant/detail.jsp TẠO MỚI. Không scriptlet. Escape mọi input.
```

---

# PHASE 4 — Hợp đồng (Contract)

```text
Implement module Hợp đồng. Đọc PHẦN 0 trước.

1. Contract.java <- bảng `contracts`:
     id, contractCode, roomId, tenantId, startDate (LocalDate),
     endDate (LocalDate), deposit, monthlyPrice, numOccupants, billingDay,
     status, terminatedAt (LocalDateTime), note, createdBy,
     createdAt, updatedAt
     + field join: roomCode, tenantName
   Sửa so với bản gốc:
     monthly_price -> monthlyPrice   (KHÔNG phải monthlyRent)
     và bổ sung 3 cột bản gốc bỏ sót: num_occupants, billing_day, terminated_at
   status có BỐN giá trị: PENDING | ACTIVE | EXPIRED | TERMINATED
     (bản gốc bỏ sót PENDING)
   Ràng buộc DB đã có, phải validate trước khi insert kẻo vỡ constraint:
     end_date > start_date, và billing_day trong khoảng 1..28

2. ContractDAO.java:
   - findAll(String statusFilter), findById(int), findByTenantId(int)
   - findByRoomId(int): Contract (hợp đồng ACTIVE hiện tại)
   - findExpiringSoon(int days): List<Contract>
     So sánh với LocalDate.now(Constants.ZONE), truyền ngày vào PreparedStatement.
     KHÔNG dùng CURDATE() của MySQL — server MySQL và JVM có thể ở hai múi giờ khác
     nhau, và hai máy của bạn cũng vậy, nên kết quả sẽ lệch.
   - insert(Contract): int
   - updateStatus(int id, String status): boolean
   - generateContractCode(): String, định dạng HD-yyyyMMdd-XXXX
     XXXX là số thứ tự trong ngày, đếm bằng COUNT các contract_code cùng tiền tố
     rồi +1, thực hiện TRONG cùng transaction với insert để tránh trùng mã.
     Ngày lấy từ LocalDate.now(Constants.ZONE) và format bằng DateTimeFormatter
     có Locale.ROOT — DateTimeFormatter không truyền locale sẽ sinh chữ số khác
     nhau ở một số locale.

3. ContractService.java:
   - createContract(Contract c):
       * kiểm tra phòng đang AVAILABLE
       * kiểm tra tenant chưa có hợp đồng ACTIVE hoặc PENDING nào khác
       * insert contract
       * update rooms.status -> OCCUPIED
     Cả 4 bước trong MỘT transaction: lấy 1 Connection, setAutoCommit(false),
     commit ở cuối, rollback trong catch, khôi phục setAutoCommit(true) trong
     finally trước khi close. Các DAO phải có overload nhận Connection từ ngoài,
     nếu mỗi DAO tự mở connection riêng thì không có transaction nào cả.
   - terminateContract(int id, String note): status -> TERMINATED,
     set terminated_at = LocalDateTime.now(Constants.ZONE),
     rooms.status -> AVAILABLE. Cùng một transaction.
   - checkAndExpireContracts(): ACTIVE -> EXPIRED nếu end_date < LocalDate.now(ZONE)

4. ContractServlet.java — ĐÃ TỒN TẠI, @WebServlet({"/contracts","/contracts/*"}).
   GIỮ NGUYÊN. action = list | add | detail | terminate | renew

5. JSP: contract/list.jsp, form.jsp, detail.jsp đều ĐÃ TỒN TẠI, sửa tiếp.
   - list.jsp: highlight vàng nếu còn <= 30 ngày, đỏ nếu EXPIRED.
     Tính số ngày còn lại ở SERVLET rồi đưa xuống JSP, đừng tính trong JSP.
   - form.jsp: dropdown chỉ phòng AVAILABLE; chọn tenant qua AJAX;
     input type="date" (giá trị luôn ở dạng ISO yyyy-MM-dd, không phụ thuộc locale).
     Tự điền monthlyPrice từ `rooms.price` của phòng được chọn — KHÔNG phải từ
     room_types.base_price. base_price chỉ là giá tham chiếu của loại phòng; giá
     thật nằm ở từng phòng (ví dụ trong seed, A201 giá 2.600.000 trong khi loại
     phòng đôi base_price là 2.500.000).

6. Ngày tháng: dùng LocalDate / LocalDateTime, không dùng java.util.Date.
   Parse bằng DateTimeFormatter.ISO_LOCAL_DATE. Đọc/ghi DB qua
   ResultSet.getObject(col, LocalDate.class) và PreparedStatement.setObject(),
   không qua java.sql.Date (nó áp timezone mặc định của JVM vào giá trị).
```

---

# PHASE 5 — Hóa đơn & Thu tiền

```text
Implement module Hóa đơn. Đọc PHẦN 0 trước. Đây là phase phức tạp nhất.

1. Invoice.java <- bảng `invoices`. CHÚ Ý: bảng KHÔNG có cột `billing_month`.
   Kỳ hóa đơn được lưu thành HAI cột số nguyên: period_month và period_year.
     id, invoiceCode, contractId, periodMonth (int), periodYear (int),
     issueDate (LocalDate), dueDate (LocalDate), roomCharge, serviceTotal,
     discount, totalAmount, paidAmount, status, paymentDate, paymentMethod,
     note, createdBy, createdAt, updatedAt
     + field join: roomCode, tenantName, tenantPhone
   Sửa so với bản gốc:
     billingMonth (YearMonth) -> periodMonth + periodYear (HAI cột int)
     rentAmount   -> roomCharge
     totalService -> serviceTotal
     paidAt       -> paymentDate
     và bổ sung 3 cột bản gốc bỏ sót: issue_date, discount, payment_method
   status có NĂM giá trị: UNPAID | PARTIAL | PAID | OVERDUE | CANCELLED
     (bản gốc bỏ sót CANCELLED)
   paymentMethod: CASH | TRANSFER | MOMO | OTHER (nullable)
   Ràng buộc DB: period_month trong 1..12, paid_amount >= 0

   Ở tầng Java vẫn được dùng YearMonth cho tiện, nhưng DAO phải tách ra thành
   period_month / period_year khi ghi và ghép lại khi đọc.

2. InvoiceDetail.java <- bảng `invoice_details`:
     id, invoiceId, serviceId, oldIndex, newIndex, quantity, unitPrice,
     amount, note
     + field join: serviceName, unit  (KHÔNG phải cột của bảng này)
   Sửa: oldReading -> oldIndex, newReading -> newIndex

3. Service.java <- bảng `services`:
     id, code, name, unit, unitPrice, type, status, createdAt, updatedAt
   Sửa so với bản gốc: serviceName -> name; isActive (boolean) -> status
   (ENUM ACTIVE|INACTIVE); và bản gốc BỎ SÓT hai cột quan trọng nhất:

     `code`  — mã dịch vụ (ELEC, WATER, TRASH, WIFI, PARK trong seed)
     `type`  — FIXED | METERED | PER_PERSON

   `type` là cột quyết định CÁCH TÍNH một dòng dịch vụ, thiếu nó thì không thể
   tính hóa đơn đúng:
     METERED     -> quantity = newIndex - oldIndex   (điện, nước)
     FIXED       -> quantity = 1                      (phí rác, wifi)
     PER_PERSON  -> quantity = contracts.num_occupants (gửi xe)
   amount = quantity * unitPrice trong cả ba trường hợp.
   Với FIXED và PER_PERSON thì oldIndex/newIndex để null, và form không được
   bắt buộc nhập chỉ số.

4. DAO:
   - ServiceDAO: findAll(), findActive()  -- status = 'ACTIVE'
   - InvoiceDAO:
       findAll(String statusFilter, Integer month, Integer year)
         (thay cho monthFilter dạng String — lọc trên period_month/period_year)
       findById(int), findWithDetails(int), findByContractId(int), findOverdue()
       insert(Invoice, List<InvoiceDetail>): int   -- MỘT transaction
       recordPayment(int invoiceId, BigDecimal amount): boolean
       generateInvoiceCode(): String, định dạng INV-yyyyMM-XXXX
         Dùng tiền tố INV-, KHÔNG dùng HD-. Bản gốc đặt cả mã hợp đồng và mã hóa
         đơn đều là "HD-" nên không phân biệt được hai loại mã, và query đếm số
         thứ tự theo tiền tố sẽ đếm lẫn sang bảng kia.
       checkDuplicate(int contractId, int month, int year): boolean

   TIỀN: dùng BigDecimal cho mọi số tiền, không dùng double. Cột DB là DECIMAL;
   double sẽ làm lệch tổng khi cộng nhiều dòng, và cách làm tròn khi in ra còn
   phụ thuộc locale.

5. InvoiceService.java:
   - createInvoice(int contractId, YearMonth period, List<InvoiceDetail> details):
       * kiểm tra contract ACTIVE
       * kiểm tra chưa có hóa đơn cho kỳ đó
       * roomCharge lấy từ contracts.monthly_price của hợp đồng
       * serviceTotal = tổng amount các dòng chi tiết
       * totalAmount = roomCharge + serviceTotal - discount
       * issueDate = LocalDate.now(Constants.ZONE)
       * dueDate: lấy ngày contracts.billing_day của hợp đồng đó trong kỳ, KHÔNG
         cố định ngày 15. Bảng contracts có cột billing_day riêng cho từng hợp
         đồng (ràng buộc 1..28) — cố định 15 là bỏ qua dữ liệu đã có.
         Dùng period.atDay(billingDay).
       * transaction: insert invoice + toàn bộ details
   - recordPayment(int invoiceId, BigDecimal amount):
       validate amount > 0 và <= (totalAmount - paidAmount);
       paidAmount += amount; status = PAID nếu bằng totalAmount, ngược lại PARTIAL;
       nếu PAID thì payment_date = LocalDate.now(Constants.ZONE)
   - markOverdue(): UNPAID hoặc PARTIAL -> OVERDUE nếu due_date <
     LocalDate.now(Constants.ZONE). Không dùng CURDATE().

6. InvoiceServlet.java — ĐÃ TỒN TẠI, @WebServlet({"/invoices","/invoices/*"}).
   GIỮ NGUYÊN. action = list | create | detail | pay | export

7. XUẤT PDF (iText 5.5.13, đã có trong pom) — CHỖ NÀY PHẢI LÀM ĐÚNG, nếu không
   sẽ chạy trên Windows và vỡ trên Mac:

   KHÔNG dùng Encoding CP1252 — bảng mã đó KHÔNG BIỂU DIỄN ĐƯỢC tiếng Việt có
   dấu, chữ sẽ ra dấu hỏi hoặc ô vuông trên MỌI hệ điều hành.
   KHÔNG trỏ tới font hệ thống như "Arial Unicode": trên Windows nó ở
   C:\Windows\Fonts\ARIALUNI.TTF, còn macOS thì hoàn toàn không có font đó, nên
   code sẽ ném IOException khi chạy trên Mac.

   Cách duy nhất chạy giống nhau ở mọi máy — NHÚNG font vào chính dự án:
   - Tải một font TTF Unicode có giấy phép tự do (DejaVuSans.ttf hoặc
     NotoSans-Regular.ttf) và đặt vào src/main/resources/fonts/
   - .gitattributes đã có sẵn dòng "*.ttf binary" nên git sẽ không "chuẩn hóa"
     line ending làm hỏng file font. Không cần sửa gì thêm.
   - Nạp font từ CLASSPATH, không từ đường dẫn ổ đĩa:
       byte[] fontBytes = <đọc hết resource "/fonts/DejaVuSans.ttf">;
       BaseFont bf = BaseFont.createFont("DejaVuSans.ttf", BaseFont.IDENTITY_H,
                                          BaseFont.EMBEDDED, true, fontBytes, null);
     IDENTITY_H là encoding bắt buộc cho Unicode; EMBEDDED nhúng font vào PDF nên
     file mở đúng cả trên máy không cài font đó.
   - Nội dung: header tên nhà trọ + địa chỉ; thông tin hóa đơn (mã, kỳ, phòng,
     sinh viên); bảng chi tiết (Tên DV / Đơn vị / Chỉ số cũ / Chỉ số mới / Số lượng
     / Đơn giá / Thành tiền); tiền phòng + tiền dịch vụ + tổng cộng.
   - Format số tiền trong PDF bằng NumberFormat với Locale("vi","VN") tường minh.
   - response.setContentType("application/pdf") và
     Content-Disposition với filename ASCII (tên file có dấu sẽ bị mã hóa khác
     nhau giữa các trình duyệt/OS); nếu cần tên có dấu thì dùng filename*=UTF-8''

8. JSP: invoice/list.jsp, form.jsp, detail.jsp ĐÃ TỒN TẠI, sửa tiếp.
   - form.jsp: thêm/xóa dòng dịch vụ bằng JS; ẩn cột chỉ số khi service.type
     không phải METERED; tự tính amount mỗi dòng và tổng live.
     JS tính toán chỉ để hiển thị — SERVER phải tính lại toàn bộ trước khi lưu,
     không tin số tiền do client gửi lên.
   - list.jsp: badge màu theo status (đủ 5 trạng thái); lọc kỳ bằng
     <input type="month"> (trả về "yyyy-MM", parse ra period_year + period_month).
```

---

# PHASE 6 — Dashboard & Báo cáo

```text
Implement Dashboard và Báo cáo. Đọc PHẦN 0 trước.

0. TRƯỚC TIÊN — bảng `activity_log` KHÔNG tồn tại trong schema.sql. Thêm vào cuối
   file schema.sql (và cả file seed riêng nếu có), tên bảng/cột chữ thường:

     CREATE TABLE activity_log (
         id          BIGINT AUTO_INCREMENT PRIMARY KEY,
         user_id     INT NULL,
         action      VARCHAR(50)  NOT NULL,
         entity_type VARCHAR(50)  NOT NULL,
         entity_id   INT NULL,
         description VARCHAR(255) NOT NULL,
         created_at  DATETIME     NOT NULL,
         CONSTRAINT fk_activity_user FOREIGN KEY (user_id) REFERENCES users(id),
         INDEX idx_activity_created (created_at)
     ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

   created_at ghi bằng LocalDateTime.now(Constants.ZONE) từ Java, không dùng NOW().

1. DashboardServlet.java — ĐÃ TỒN TẠI, @WebServlet("/dashboard"). Thu thập:
   - Map<String,Integer> theo trạng thái phòng từ RoomDAO.countByStatus()
   - Tỷ lệ lấp đầy = OCCUPIED / tổng * 100 (tính bằng BigDecimal, tránh chia 0)
   - Hợp đồng sắp hết hạn 30 ngày
   - Doanh thu tháng hiện tại: tổng total_amount các invoice PAID có
     period_month/period_year bằng tháng/năm hiện tại theo Constants.ZONE
   - Hóa đơn chưa thanh toán: UNPAID + PARTIAL + OVERDUE
   - Top 5 sinh viên mới nhất
   - 10 dòng activity_log gần nhất
   - Doanh thu 12 kỳ gần nhất cho biểu đồ

2. ReportServlet.java — ĐÃ TỒN TẠI, @WebServlet({"/reports","/reports/*"}).
   GIỮ NGUYÊN urlPatterns (không đổi thành "/report"), vì RoleFilter trong web.xml
   đã map đúng /reports và /reports/* để chỉ ADMIN và MANAGER vào được.

   action=revenue:
     SQL trong bản gốc của tôi SAI — cột `billing_month` không tồn tại. Dùng:
       SELECT i.period_year, i.period_month,
              SUM(i.total_amount) AS total,
              SUM(i.paid_amount)  AS paid
       FROM invoices i
       JOIN contracts c ON c.id = i.contract_id
       WHERE (i.period_year * 100 + i.period_month) BETWEEN ? AND ?
       GROUP BY i.period_year, i.period_month
       ORDER BY i.period_year, i.period_month
     Tham số truyền dạng yyyyMM (ví dụ 202601). Lọc theo kỳ chứ đừng lọc theo
     fromDate/toDate, vì hóa đơn gắn với KỲ chứ không gắn với một ngày.
   action=occupancy: số phòng bình quân đang thuê theo tháng, tỷ lệ lấp đầy,
     danh sách phòng trống kèm số ngày trống.
   action=debt: hóa đơn UNPAID + PARTIAL + OVERDUE, tổng công nợ theo phòng và
     theo sinh viên, xuất PDF (dùng lại đúng cách nạp font ở Phase 5).

3. Xuất Excel: thêm Apache POI vào pom.xml THEO ĐÚNG QUY ƯỚC của dự án — version
   khai báo qua property, không viết trực tiếp trong <dependency>:
     <properties>  <poi.version>5.2.5</poi.version>  </properties>
     <dependency>
       <groupId>org.apache.poi</groupId>
       <artifactId>poi-ooxml</artifactId>
       <version>${poi.version}</version>
     </dependency>
   Ghi ra ServletOutputStream, content type
   application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
   Định dạng số/ngày trong sheet dùng CellStyle với DataFormat tường minh, không
   để POI lấy locale mặc định của máy.

4. Biểu đồ — ĐỪNG nạp Chart.js từ CDN cdnjs.cloudflare.com.
   Máy đang offline hoặc mạng chặn CDN sẽ ra trang trắng không có biểu đồ, và bạn
   sẽ tưởng là code lỗi. Tải chart.umd.min.js về đặt tại
   assets/js/vendor/chart.umd.min.js rồi tham chiếu qua
   ${pageContext.request.contextPath}. Như vậy chạy giống nhau ở mọi máy, mọi mạng.

5. Truyền dữ liệu biểu đồ: servlet serialize sang JSON bằng JsonUtil/Gson rồi đặt
   vào request attribute. Trong JSP nhúng bằng
   <script type="application/json" id="chartData">${chartData}</script>
   rồi JS đọc JSON.parse(document.getElementById('chartData').textContent).
   ĐỪNG nhúng thẳng vào chuỗi JS bằng ${...} — dữ liệu có dấu nháy hoặc ký tự
   tiếng Việt sẽ làm vỡ script.

6. JSP: dashboard/index.jsp, report/revenue.jsp, report/debt.jsp ĐÃ TỒN TẠI.
   report/occupancy.jsp TẠO MỚI. Không scriptlet.
```

---

# PHASE 7 — UI Hoàn thiện

```text
Hoàn thiện giao diện. Đọc PHẦN 0 trước.

1. Hoàn thiện assets/css/style.css — file NÀY đã tồn tại và các JSP đang link tới
   nó. KHÔNG tạo main.css, KHÔNG đổi tên file. Đổi tên sẽ làm mọi JSP hiện có 404,
   và trên macOS có thể vẫn chạy do không phân biệt hoa thường nên bạn sẽ không
   phát hiện ra lỗi cho tới khi mở trên máy khác.

2. THIẾT KẾ: dark theme background #0f1117, surface #1a1d27, card #21253a;
   accent #6c63ff, secondary #22c55e, warning #f59e0b, danger #ef4444.
   Khai báo qua CSS custom properties trong :root để dùng lại.
   Bán kính: card 14px, button 8px, input 8px, badge 20px.

   FONT: font-family: system-ui, -apple-system, "Segoe UI", Roboto, sans-serif
   Thứ tự này quan trọng — system-ui và -apple-system cho macOS, "Segoe UI" cho
   Windows. Đừng chỉ ghi một font của một OS. KHÔNG nạp webfont từ Google Fonts
   (cùng lý do offline như Chart.js); nếu cần webfont thì đặt file vào
   assets/fonts/ và khai báo @font-face trỏ đường dẫn tương đối.

3. COMPONENT (CSS thuần, không Bootstrap): sidebar 240px sticky có nav group +
   user info; topbar sticky (breadcrumb + search + avatar); CSS Grid cho stat card
   (repeat(auto-fit, minmax(220px, 1fr))); table hover + sọc + sticky header;
   form label trên input, focus ring accent, error state đỏ; modal overlay confirm
   xóa; toast góc phải trên tự tắt sau 4 giây; pagination; badge theo status
   (đủ mọi giá trị enum: rooms 4, contracts 4, invoices 5); loading spinner.

4. assets/js/main.js — file ĐÃ TỒN TẠI, mở rộng nó:
   initToast(), initConfirmDelete() cho form[data-confirm], initAvatarPreview()
   FileReader, initLiveSearch() debounce 300ms, initCharts() wrapper Chart.js
   dark theme, initFormValidation().
   Mọi fetch() phải build URL từ context path lấy qua một data-attribute trên
   <body>, đừng hardcode "/student-rent".

5. layout/base.jsp — dự án hiện có layout/header.jsp và layout/footer.jsp mà mọi
   JSP đang <jsp:include>. Có HAI cách, chọn MỘT và làm nhất quán:
   (a) Giữ header/footer, chỉ hoàn thiện chúng — ít rủi ro nhất, KHUYẾN NGHỊ.
   (b) Tạo base.jsp rồi sửa toàn bộ 20 JSP.
   Đừng làm nửa vời: để lẫn cả hai cơ chế sẽ sinh ra HTML có hai lần <html> hoặc
   hai sidebar. Nếu chọn (a) thì bỏ mục base.jsp trong bản gốc của tôi.
   Active state của sidebar tính từ request URI, so sánh sau khi bỏ context path.
   title động qua ${pageTitle} với giá trị mặc định nếu chưa set.

6. Responsive tại 768px: sidebar thu thành hamburger.

7. Mọi file CSS/JS/JSP tạo hoặc sửa: UTF-8 không BOM, kết thúc dòng LF
   (.editorconfig và .gitattributes của dự án đã quy định). BOM trong JSP sẽ đẩy
   ký tự lạ ra trước <!DOCTYPE và làm layout lệch.
```

---

# PHASE 8 — Kiểm thử & Triển khai

```text
Kiểm tra và hoàn tất. Đọc PHẦN 0 trước.

1. REVIEW toàn bộ code:
   - SQL Injection: 100% query qua PreparedStatement, kể cả filter động và LIKE.
     Kiểm riêng RoomDAO.findAll và TenantDAO.search — hai chỗ ghép WHERE động.
   - XSS: mọi giá trị người dùng nhập, khi in ra JSP phải qua <c:out> hoặc
     fn:escapeXml. login.jsp đã làm đúng, dùng nó làm mẫu.
   - CSRF: dự án hiện KHÔNG có cơ chế nào — đây là việc mới hoàn toàn. Sinh token
     bằng SecureRandom, lưu trong session, nhúng vào mọi form POST dưới dạng
     hidden input, so sánh bằng MessageDigest.isEqual() (so sánh hằng thời gian).
     Đặt phần kiểm tra trong một filter chạy SAU AuthFilter, và khai báo nó trong
     web.xml đúng thứ tự — thứ tự filter do <filter-mapping> quyết định.
   - Session fixation: đã xử lý ở Phase 1 bằng request.changeSessionId() (API của
     Servlet 3.1+, đúng cho Servlet 6). Đừng dùng cách invalidate rồi tạo session
     mới thủ công.
   - Phân quyền: RoleFilter ĐÃ TỒN TẠI, đã map /reports và /reports/* trong
     web.xml, phần kiểm tra đang bị comment kèm TODO — bỏ comment và hoàn thiện,
     cho phép ADMIN và MANAGER. Nhớ role có BA giá trị, không phải hai.

2. KHÔNG tạo error page mới — web.xml đã có 403, 404, 500 và java.lang.Throwable,
   và 3 file JSP tương ứng đã tồn tại trong views/error/.

3. KHÔNG tạo ContextListener.java mới — AppContextListener đã là @WebListener.
   Sửa chính nó:
   - contextInitialized: đọc app.upload.dir, Files.createDirectories(), lưu Path
     vào ServletContext; nạp cache RoomType + Service; gọi
     ContractService.checkAndExpireContracts() và InvoiceService.markOverdue().
     Bọc mỗi việc trong try/catch riêng và chỉ log khi lỗi — DB chưa sẵn sàng thì
     app vẫn phải khởi động được, nếu không thì máy mới clone về sẽ không mở nổi
     trang nào để xem lỗi là gì.
   - contextDestroyed: DBConnection.shutdown() (đã có sẵn).

4. Hiệu năng:
   - HikariCP maximumPoolSize=10, minimumIdle=2, connectionTimeout=30000 — ĐÃ
     đúng như vậy trong db.properties, không cần sửa.
   - Cache RoomType và Service trong ServletContext: dùng
     List.copyOf() / Map.copyOf() để cache là immutable. ServletContext được mọi
     thread dùng chung, cache mutable là lỗi thread-safety.
   - Index MySQL: mục này trong bản gốc của tôi gần như VÔ NGHĨA — schema.sql đã
     khai báo sẵn hầu hết index ngay trong CREATE TABLE. Đã có:
       idx_users_role, idx_rooms_status, idx_rooms_type,
       idx_tenants_name, idx_tenants_phone,
       idx_contracts_room, idx_contracts_tenant, idx_contracts_status,
       idx_contracts_period (start_date, end_date),
       idx_invoices_status, idx_invoices_period (period_year, period_month),
       idx_invoices_due (due_date), idx_invoice_details_service
     ĐỪNG tạo lại chúng (MySQL sẽ báo lỗi trùng tên index).
     Chỉ CÓ MỘT index đáng thêm thật:
       CREATE INDEX idx_contracts_end ON contracts(end_date);
     Lý do: findExpiringSoon() lọc riêng trên end_date, nhưng index composite
     idx_contracts_period bắt đầu bằng start_date nên MySQL không dùng được nó
     cho điều kiện chỉ có end_date.
     Cũng lưu ý bản gốc ghi "invoices.billing_month" — cột đó KHÔNG tồn tại, kỳ
     hóa đơn nằm ở period_year + period_month và đã được index sẵn.

5. KHÔNG viết lại README.md từ đầu. File hiện tại đã có mục cài đặt cho cả Windows
   và macOS, mục đồng bộ hai máy bằng git, và bảng 3 tầng cấu hình DB. Chỉ BỔ SUNG
   phần cho các module mới.
   Đặc biệt: đừng hướng dẫn người đọc "cấu hình db.properties" — cách đúng của dự
   án này là tạo db.local.properties (file này .gitignore bỏ qua). Sửa
   db.properties sẽ gây xung đột mỗi lần đồng bộ hai máy.

6. Seed data — schema.sql ĐÃ insert sẵn 1 admin, 4 loại phòng, 5 dịch vụ, 5 phòng,
   và bắt đầu bằng DROP DATABASE IF EXISTS.
   ĐỪNG thêm seed mới vào schema.sql (chạy lại sẽ xóa sạch dữ liệu đang có).
   Tạo file RIÊNG database/seed-demo.sql, chỉ INSERT, không DROP, không CREATE:
   - 3 loại phòng và 20 phòng 4 tầng: thêm mới, tránh trùng room_code với A101,
     A102, A201, A202, B301 đã có trong seed.
   - 10 sinh viên, 8 hợp đồng ACTIVE, hóa đơn 2 kỳ đủ trạng thái.
   - 1 user staff (role STAFF), hash BCrypt sinh bằng PasswordUtil.
   Mật khẩu seed chỉ dùng ký tự ASCII.
   Chạy được trên cả hai shell:
       mysql -u root -p student_rent -e "source database/seed-demo.sql"
   (KHÔNG dùng dạng "< file.sql" — PowerShell không hỗ trợ toán tử <)

7. Kiểm thử cuối:
       mvn clean package        -> phải BUILD SUCCESS
       mvn package cargo:run    -> mở http://localhost:8080/student-rent/
   KHÔNG cần cài Tomcat và đừng copy WAR thủ công — Cargo tự tải Tomcat 10.1.
   Smoke test: login -> dashboard -> CRUD phòng -> CRUD sinh viên -> tạo hợp đồng
   -> tạo hóa đơn -> thu tiền -> xuất PDF -> xuất Excel -> báo cáo -> logout.
   Kiểm riêng: trang có tiếng Việt hiện đúng dấu, PDF xuất ra có dấu đúng, và ảnh
   upload vẫn còn sau khi chạy lại mvn clean package.
```
