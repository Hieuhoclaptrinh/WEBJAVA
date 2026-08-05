# StudentRent — Quản lý nhà trọ sinh viên

Skeleton dự án Maven cho ứng dụng web quản lý nhà trọ sinh viên.
**Chỉ có cấu trúc + khung class, chưa implement business logic.**

## Stack

| Thành phần | Phiên bản | Ghi chú |
|---|---|---|
| Java | 17 | `maven.compiler.release=17` |
| Jakarta Servlet | 6.0.0 | scope `provided` — Tomcat 10.1 cung cấp |
| JSP + JSTL | 3.0.0 / impl 3.0.1 | Tomcat 10 **không** kèm JSTL nên impl được đóng vào WAR |
| MySQL Connector/J | 8.4.0 | LTS, dùng với MySQL 8 |
| HikariCP | 5.1.0 | connection pool |
| jBCrypt | 0.4 | mã hóa mật khẩu |
| Gson | 2.11.0 | JSON / AJAX |
| iText | 5.5.13 | xuất PDF (license AGPL — xem lưu ý bên dưới) |
| Commons FileUpload | 2.0.0-M5 (`commons-fileupload2-jakarta-servlet6`) | xem lưu ý bên dưới |

## Yêu cầu môi trường

Cần **3 thứ**, giống nhau trên mọi hệ điều hành — Tomcat **không cần cài** (Maven
tự tải Tomcat 10.1 nhúng qua plugin Cargo khi chạy `mvn package cargo:run`):

- **JDK 17+**
- **Apache Maven 3.9+**
- **MySQL 8.0+** (chỉ cần khi implement xong phần truy vấn DB)

### Cài trên Windows

```powershell
winget install Microsoft.OpenJDK.17
winget install Oracle.MySQL
```

Maven: tải zip từ <https://maven.apache.org/download.cgi>, giải nén, thêm `bin` vào PATH.

> **MySQL trên Windows không tự thêm vào PATH.** Bộ cài đặt `mysql.exe` ở
> `C:\Program Files\MySQL\MySQL Server 8.0\bin` — thêm đường dẫn này vào PATH,
> nếu không mọi lệnh `mysql` dưới đây đều báo *command not found*.

### Cài trên macOS (Homebrew)

```bash
brew install --cask temurin@17
brew install maven mysql
brew services start mysql     # BẮT BUỘC: brew cài xong không tự chạy MySQL
```

> `brew install mysql` chỉ đặt file xuống, **không** khởi động server. Bỏ qua
> `brew services start mysql` thì bước tạo database bên dưới sẽ báo
> *Can't connect to local MySQL server*.

### Kiểm tra trước khi build (chạy trên cả hai máy)

```bash
java -version     # phải là 17.x
mvn -v            # xem dòng "Java version:" — phải trỏ vào JDK 17, không phải JDK khác
```

`mvn -v` in ra JDK mà Maven **thực sự** dùng. Nếu máy có nhiều JDK, dòng đó mới là
sự thật, không phải `java -version`. Lệch JDK giữa hai máy là nguyên nhân phổ biến
nhất của kiểu lỗi "máy kia build được, máy này thì không".

> Nếu deploy thủ công thay vì dùng `mvn package cargo:run`: bắt buộc Tomcat
> **10.1.x** trở lên — Tomcat 9 dùng `javax.servlet` nên không chạy được.

## Cấu trúc

```
student-rent-management/
├── pom.xml
├── database/
│   └── schema.sql                  8 bảng + dữ liệu mẫu
└── src/main/
    ├── java/com/studentrent/
    │   ├── controller/             BaseServlet + 8 servlet
    │   ├── model/                  8 POJO ứng với 8 bảng
    │   ├── dao/                    GenericDAO + 8 DAO
    │   ├── service/                7 service nghiệp vụ
    │   ├── util/                   DBConnection, PasswordUtil, DateUtil,
    │   │                           JsonUtil, UploadUtil, PdfExporter,
    │   │                           Constants, AppContextListener
    │   └── filter/                 Encoding / Auth / Role
    ├── resources/
    │   ├── db.properties           cấu hình JDBC + HikariCP (dùng chung, có trên git)
    │   └── db.local.properties.example
    │                               mẫu để copy thành db.local.properties
    │                               (cấu hình riêng từng máy, KHÔNG lên git)
    └── webapp/
        ├── index.jsp               welcome-file → /login
        ├── WEB-INF/
        │   ├── web.xml
        │   └── views/
        │       ├── layout/         header.jsp, footer.jsp
        │       ├── auth/           login.jsp
        │       ├── dashboard/      index.jsp
        │       ├── room/           list.jsp, form.jsp
        │       ├── tenant/         list.jsp, form.jsp
        │       ├── contract/       list.jsp, form.jsp, detail.jsp
        │       ├── invoice/        list.jsp, form.jsp, detail.jsp
        │       ├── report/         revenue.jsp, debt.jsp
        │       └── error/          403.jsp, 404.jsp, 500.jsp
        ├── assets/css/style.css
        ├── assets/js/main.js
        ├── assets/img/
        └── uploads/                đích lưu ảnh upload
```

> `layout/` và `error/` không nằm trong danh sách thư mục bạn yêu cầu nhưng cần
> thiết: `error/` để `<error-page>` trong web.xml trỏ tới, `layout/` để các trang
> không phải lặp lại header/sidebar.

## Đồng bộ giữa hai máy (Windows ↔ MacBook)

Dự án đi giữa hai máy **bằng git, không bằng copy thư mục**. Đây không phải chuyện
tiện tay: `.gitattributes` khóa line ending về LF và `.editorconfig` khóa UTF-8,
nhưng **cả hai chỉ có tác dụng khi file đi qua git**. Copy bằng USB / Drive / zip
thì không có gì chuẩn hóa, và bạn sẽ nhận đủ bộ: diff giả "cả file thay đổi",
tiếng Việt thành ký tự lạ, và hai bản code lệch nhau không có cách nào gộp lại.

**Lần đầu, trên máy đang có code (Windows):**

```bash
git init
git branch -M main
git add .
git commit -m "init: skeleton du an StudentRent"
git remote add origin https://github.com/Hieuhoclaptrinh/WEBJAVA.git
git push -u origin main
```

**Trên MacBook, lấy code về:**

```bash
git clone https://github.com/Hieuhoclaptrinh/WEBJAVA.git
cd WEBJAVA
```

Rồi làm tiếp mục *Chạy dự án* bên dưới — bước 1 (tạo database) và bước 2
(`db.local.properties`) phải làm **một lần trên mỗi máy**, vì database và mật khẩu
MySQL là của riêng từng máy, không đi theo git.

**Vòng lặp hằng ngày** — trước khi bắt đầu code, và sau khi code xong:

```bash
git pull            # LUÔN pull trước khi sửa, để không phải gộp thủ công
# ... sửa code ...
git add .
git commit -m "mo ta thay doi"
git push            # push trước khi rời máy, nếu không máy kia sẽ pull ra code cũ
```

> Thói quen duy nhất cần giữ: **push trước khi đóng máy, pull trước khi mở việc.**
> Quên push rồi sang máy kia sửa cùng file là tình huống duy nhất phải gộp tay.

Những thứ **không** lên git (đúng như thiết kế, `.gitignore` đã lo):
`target/`, `*.war`, `src/main/resources/db.local.properties`,
`.claude/settings.local.json`, ảnh trong `uploads/`, `.DS_Store`, `Thumbs.db`.
Nghĩa là sau khi clone, máy mới **phải** tự tạo `db.local.properties` và tự chạy
`schema.sql` — không có hai thứ đó thì app chạy lên được nhưng không nối được DB.

## Làm việc nhóm

Khi có thêm thành viên, đọc **[CONTRIBUTING.md](CONTRIBUTING.md)** — quy trình
nhánh + pull request, danh sách những gì không được commit và vì sao, cách xử lý
xung đột, và hướng dẫn deploy.

Mỗi push và mỗi pull request đều chạy CI
([.github/workflows/build.yml](.github/workflows/build.yml)) trên **Ubuntu**. Chạy
trên Linux là có chủ đích: Linux phân biệt chữ hoa/thường trong tên file, còn
macOS và Windows thì không. Nhờ vậy CI bắt được lỗi sai hoa/thường ở đường dẫn —
loại lỗi chạy êm trên máy bạn rồi 404 trên máy người khác. CI cũng kiểm build
sạch bằng JDK 17, kiểm `schema.sql` áp được lên MySQL 8 thật, và chặn file CRLF
lọt vào repo.

## Chạy dự án

**1. Tạo database** *(một lần trên mỗi máy)*

Dùng dạng này — chạy được trên **cả** zsh/bash (macOS) và PowerShell (Windows):

```bash
mysql -u root -p -e "source database/schema.sql"
```

> **Đừng** dùng `mysql -u root -p < database/schema.sql`. PowerShell — shell mặc
> định của Windows và của các task trong `.vscode/` — **không hỗ trợ toán tử `<`**,
> câu lệnh chết ngay ở bước parse với *"The '<' operator is reserved for future use"*
> trước khi `mysql` được gọi. Dạng `<` chỉ đúng trên macOS/Linux.
>
> Cũng **đừng** dùng `Get-Content database/schema.sql | mysql -u root -p` trên
> PowerShell 5.1: pipe sang chương trình native đi qua `$OutputEncoding` (mặc định
> ASCII) nên sẽ làm hỏng mọi ký tự tiếng Việt thêm vào file SQL sau này.

**2. Khai báo mật khẩu MySQL của máy bạn**

Tạo `src/main/resources/db.local.properties` (copy từ `db.local.properties.example`)
và chỉ ghi lại những key khác với mặc định:

```properties
jdbc.password=matkhau_mysql_cua_may_nay
```

File này bị `.gitignore` bỏ qua nên **mỗi máy một mật khẩu mà không bao giờ xung đột
khi đồng bộ**, và không có nguy cơ đẩy mật khẩu lên GitHub.

Thứ tự ưu tiên, tầng sau ghi đè tầng trước:

| Tầng | File / nguồn | Được git theo dõi? | Dùng khi nào |
|---|---|---|---|
| 1 | `db.properties` | Có | giá trị mặc định dùng chung |
| 2 | `db.local.properties` | **Không** | **máy bạn đang ngồi** ← dùng cái này |
| 3 | `DB_URL` / `DB_USERNAME` / `DB_PASSWORD` | — | deploy thật, khỏi sửa file trong WAR |

> **Đừng sửa `db.properties`.** File đó được git theo dõi, sửa vào đó thì lần nào
> đồng bộ hai máy cũng xung đột.
>
> MySQL cài bằng Homebrew trên macOS mặc định **không có mật khẩu root**, nên trên
> Mac thường chỉ cần đúng một dòng `jdbc.password=` (để trống).

**3. Chạy (không cần cài Tomcat)**

```bash
mvn clean package cargo:run
```

Lần đầu Cargo tự tải Tomcat 10.1 (~12 MB) về `~/.m2`. Khi thấy dòng
`Press Ctrl-C to stop the container...` thì mở <http://localhost:8080/student-rent/>.
Lệnh này giống hệt nhau trên Windows / macOS / Linux.

**Cách khác** — build WAR rồi deploy thủ công:

```bash
mvn clean package
```

Copy `target/student-rent.war` vào `$CATALINA_HOME/webapps/` của Tomcat 10.1.

## Chạy trong VS Code

Dự án có sẵn cấu hình trong `.vscode/`:

1. Mở folder dự án — VS Code sẽ gợi ý cài **Extension Pack for Java** và
   **EditorConfig** (chấp nhận cả hai).
2. **Chạy**: `Terminal → Run Task… → Tomcat: run` — build + chạy Tomcat nhúng,
   mở <http://localhost:8080/student-rent/>.
3. **Debug**: chạy task `Tomcat: debug`, đợi Tomcat khởi động, đặt breakpoint
   rồi bấm **F5** (*Attach to Tomcat (port 8000)*).
4. **Build WAR**: `Ctrl/Cmd+Shift+B` (task build mặc định).
5. **Sinh hash BCrypt**: task `BCrypt: sinh hash mat khau`.

## Tính di động (code trên Windows hôm nay, Mac mai sau)

Những gì đã được xử lý để hai máy cho ra kết quả giống nhau:

- **Không có đường dẫn tuyệt đối trong source lẫn cấu hình được commit.** Các task
  VS Code chỉ gọi `mvn`, chạy giống nhau trên mọi OS. Riêng
  `.claude/settings.local.json` có chứa đường dẫn của máy cụ thể, nên nó đã được
  `.gitignore` bỏ qua và không lên git.
- **`.editorconfig` + `.gitattributes`** khóa UTF-8 và line ending LF. Lưu ý: hai
  file này chỉ có hiệu lực khi code đi qua **git** — xem mục *Đồng bộ giữa hai máy*.
- **Tomcat nhúng qua Cargo** — không phụ thuộc Tomcat cài ngoài với đường dẫn
  máy-cụ-thể.
- **Cấu hình DB tách theo máy** qua `db.local.properties` (không lên git) và biến
  môi trường `DB_URL` / `DB_USERNAME` / `DB_PASSWORD`. Không ai phải sửa file đang
  được git theo dõi.
- **Locale `vi_VN` ghim trong `web.xml`** (`jakarta.servlet.jsp.jstl.fmt.locale`).
  Không ghim thì `<fmt:formatNumber>` lấy locale của browser/OS, cùng một số tiền
  sẽ hiện `1.800.000` trên máy này và `1,800,000` trên máy kia.
- **Version plugin Maven được ghim hết**, kể cả plugin lifecycle mặc định
  (clean / resources / surefire / install / deploy) trong `<pluginManagement>` —
  để `mvn clean package` trên hai máy chạy đúng cùng một bộ plugin.
- Khi implement `UploadUtil`, ghép đường dẫn bằng `java.nio.file.Path` (đã ghi
  chú trong javadoc) — không nối chuỗi `\` hay `/` cứng.

Ba chỗ còn khác nhau giữa hai OS mà bạn cần biết:

- **`mysql` trên Windows không có sẵn trong PATH**, trên macOS (Homebrew) thì có.
  Xem mục *Yêu cầu môi trường*.
- **PowerShell không hỗ trợ `<`**, nên mọi lệnh trong tài liệu này dùng dạng
  `mysql -u root -p -e "source ..."` thay vì `mysql ... < file.sql`.
- **PowerShell 5.1 (bản có sẵn trên Windows) không hỗ trợ `&&`.** Ghép lệnh bằng
  `&&` sẽ chết ngay ở bước parse:
  *"The token '&&' is not a valid statement separator in this version."*
  Trên zsh/bash của macOS thì `&&` chạy bình thường — nên câu lệnh copy từ hướng
  dẫn trên mạng thường chỉ đúng một nửa. Cách viết chạy được ở cả hai shell là
  **mỗi lệnh một dòng**:

  ```bash
  git add .
  git commit -m "mo ta thay doi"
  git push
  ```

  Dùng `;` thay `&&` cũng parse được trên PowerShell, nhưng ý nghĩa KHÁC: `;`
  chạy lệnh sau bất kể lệnh trước thành công hay thất bại, còn `&&` chỉ chạy khi
  lệnh trước thành công. Với git thì khác biệt này quan trọng — `git add` lỗi mà
  vẫn `git commit` là commit ra bản thiếu file.

## Trạng thái hiện tại

Đã chạy được (không cần code thêm):

- Build ra WAR, deploy lên Tomcat 10
- Điều hướng giữa các trang, giao diện + CSS hiển thị đầy đủ
- `web.xml`: welcome-file, 4 error-page, thứ tự filter, session 30 phút
- `DBConnection` — pool HikariCP hoàn chỉnh, đọc `db.properties`
- `PasswordUtil` — hash/verify BCrypt hoàn chỉnh
- `JsonUtil` — ghi JSON response hoàn chỉnh

Còn là stub (`throw new UnsupportedOperationException("TODO: ...")`):

- Toàn bộ DAO, `AuthService`, `RoomService`, `TenantService`, `ContractService`,
  `InvoiceService`, `ReportService`, `ServiceCatalogService`
- `DateUtil`, `UploadUtil`, `PdfExporter`
- Controller đã forward đúng view nhưng chưa nạp dữ liệu (xem các comment `TODO`)

### Thứ tự implement gợi ý

1. `UserDAO.findByUsername` + `AuthService.login` → đăng nhập chạy được
2. Bỏ comment phần kiểm tra session trong `AuthFilter` và `RoleFilter`
3. `RoomTypeDAO` → `RoomDAO` → `RoomService` → `RoomServlet`
4. `TenantDAO` → `TenantService` → `TenantServlet`
5. `ContractDAO` + `ContractService.create` (transaction: insert hợp đồng +
   `UPDATE rooms SET status='OCCUPIED'`)
6. `ServiceDAO` → `InvoiceDAO` + `InvoiceDetailDAO` →
   `InvoiceService.createInvoice` (transaction, xem javadoc trong file)
7. `ReportService` → `ReportServlet`
8. `PdfExporter` (nhớ nhúng font Unicode, xem bên dưới)

## Những điểm cần biết

**Tài khoản admin mặc định:** `admin` / `admin123`. Hash trong `schema.sql` được
sinh bằng chính `PasswordUtil` của dự án và đã kiểm tra `checkpw` trả về `true`.
Đổi mật khẩu khác:

```bash
mvn -q compile exec:java -Dexec.mainClass=com.studentrent.util.PasswordUtil -Dexec.args="matkhaumoi"
```

rồi `UPDATE users SET password_hash = '<hash mới>' WHERE username = 'admin';`

> Chạy trong **PowerShell** phải bọc các tham số `-D...` trong nháy đơn
> (`'-Dexec.mainClass=...'`) — PowerShell tách token không quote tại dấu chấm.
> Trong VS Code cứ dùng task `BCrypt: sinh hash mat khau` là an toàn.

> **Mật khẩu có tiếng Việt hoặc ký tự ngoài ASCII: đừng truyền qua `-Dexec.args`.**
> Tham số dòng lệnh được JVM giải mã bằng `sun.jnu.encoding`, mà thứ này là UTF-8
> trên macOS nhưng là code page hệ thống trên Windows. Cùng một mật khẩu gõ trên
> hai máy sẽ thành hai chuỗi byte khác nhau, nên hash sinh ở máy này **không**
> verify được mật khẩu gõ ở máy kia — lỗi cực khó đoán.
>
> Bỏ hẳn `-Dexec.args` để `PasswordUtil` hỏi mật khẩu từ bàn phím (không đi qua
> argv nên không bị biến dạng):
>
> ```bash
> mvn -q compile exec:java -Dexec.mainClass=com.studentrent.util.PasswordUtil
> ```
>
> Nếu vẫn truyền qua tham số, `PasswordUtil` sẽ tự in cảnh báo kèm `sun.jnu.encoding`
> và byte UTF-8 thực tế để bạn đối chiếu giữa hai máy. Với dữ liệu mẫu thì cứ dùng
> mật khẩu chỉ gồm ký tự ASCII là gọn nhất.

**Commons FileUpload.** `commons-fileupload:commons-fileupload:1.5` chỉ hỗ trợ
`javax.servlet` nên **không chạy trên Tomcat 10**. POM dùng
`commons-fileupload2-jakarta-servlet6:2.0.0-M5` — đây là bản mới nhất trên Maven
Central nhưng vẫn là milestone (chưa GA). Servlet 6 đã có sẵn
`@MultipartConfig` + `request.getPart()` làm được việc tương đương mà không cần
thư viện ngoài — `RoomServlet`/`TenantServlet` đã khai báo `@MultipartConfig`
sẵn, nên bạn có thể bỏ hẳn dependency này nếu muốn.

**iText 5 là AGPL.** Dùng cho đồ án/học tập thì được; dùng thương mại phải mua
license hoặc đổi sang OpenPDF (fork LGPL, API gần như y hệt).

**Font tiếng Việt trong PDF.** iText 5 mặc định không render được chữ có dấu.
Cần copy một font TTF Unicode vào `src/main/resources/fonts/DejaVuSans.ttf` rồi
tạo `BaseFont` với `BaseFont.IDENTITY_H, BaseFont.EMBEDDED` (đường dẫn đã khai
báo ở `PdfExporter.FONT_RESOURCE`).

**JSTL 3.0 đổi URI taglib.** Dùng `jakarta.tags.core` / `jakarta.tags.fmt`,
**không** phải `http://java.sun.com/jsp/jstl/core` như JSTL 1.x.

**Scriptlet bị chặn.** `web.xml` đặt `<scripting-invalid>true</scripting-invalid>`
để buộc dùng EL + JSTL. Nếu bạn cần viết `<% %>` trong JSP thì đổi thành `false`.

**Filter khai báo trong `web.xml`, không dùng `@WebFilter`.** Vì `@WebFilter`
không bảo đảm thứ tự chạy, mà `EncodingFilter` bắt buộc phải chạy trước để
tiếng Việt không lỗi font. Đừng thêm lại annotation — filter sẽ bị đăng ký hai lần.

**Ảnh upload mất khi redeploy.** `src/main/webapp/uploads/` bị Tomcat giải nén
lại mỗi lần deploy WAR. Khi làm thật, lưu ra thư mục ngoài webapp.
