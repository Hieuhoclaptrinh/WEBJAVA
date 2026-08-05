# Làm việc nhóm trên StudentRent

Tài liệu này dành cho **thành viên mới** và cho quy trình làm việc hằng ngày.
Phần cài đặt môi trường chi tiết nằm ở [README.md](README.md) — ở đây chỉ nói
những gì liên quan tới việc **nhiều người cùng làm trên nhiều máy**.

Nguyên tắc gốc: **mỗi người một database, một mật khẩu MySQL, một máy — nhưng
cùng một code.** Mọi thứ thuộc về "máy của tôi" đều không được lên git.

---

## 1. Lần đầu vào dự án (mỗi người làm một lần, trên mỗi máy)

```bash
git clone https://github.com/Hieuhoclaptrinh/WEBJAVA.git
cd WEBJAVA
```

Cài JDK 17 + Maven + MySQL 8 theo [README.md](README.md#yêu-cầu-môi-trường)
(có hướng dẫn riêng cho Windows và macOS).

Rồi làm đúng 2 việc **của riêng máy bạn**:

```bash
# 1) Tạo database + seed data
mysql -u root -p -e "source database/schema.sql"
```

```bash
# 2) Khai báo mật khẩu MySQL của máy bạn
#    copy src/main/resources/db.local.properties.example
#    thành  src/main/resources/db.local.properties
#    rồi sửa dòng jdbc.password cho khớp
```

Kiểm tra chạy được:

```bash
mvn package cargo:run
```

Mở <http://localhost:8080/student-rent/> — đăng nhập `admin` / `admin123`.

> Nếu bước 2 bị bỏ qua, app vẫn khởi động và mở được các trang, nhưng mọi thao
> tác cần DB sẽ lỗi. Đây là dấu hiệu thiếu `db.local.properties`, không phải bug.

---

## 2. Quy trình hằng ngày

**Không ai push trực tiếp vào `main`.** Mỗi việc là một nhánh riêng.

```bash
git switch main
git pull
git switch -c ten-nhanh
```

Sửa code, rồi:

```bash
git add .
git commit -m "mo ta ngan viec da lam"
git push -u origin ten-nhanh
```

Vào GitHub → **Compare & pull request** → mô tả ngắn → **Create pull request**.
Đợi CI (tab **Actions**) xanh, đợi 1 người review, rồi merge.

> **PowerShell (Windows): đừng ghép lệnh bằng `&&`.** PowerShell 5.1 báo
> *"The token '&&' is not a valid statement separator in this version."* và chết
> ngay ở bước parse. Trên zsh/bash của macOS thì `&&` chạy bình thường — nên câu
> lệnh copy trên mạng thường chỉ đúng một nửa. **Mỗi lệnh một dòng** là cách chạy
> được ở cả hai shell. Dùng `;` thì parse được nhưng SAI NGHĨA: nó chạy lệnh sau
> bất kể lệnh trước thành công hay thất bại.

### Tên nhánh

| Loại việc | Tiền tố | Ví dụ |
|---|---|---|
| Tính năng mới | `feat/` | `feat/quan-ly-phong` |
| Sửa lỗi | `fix/` | `fix/loi-tinh-tien-dien` |
| Tài liệu | `docs/` | `docs/huong-dan-deploy` |

Chỉ dùng chữ thường, không dấu, không khoảng trắng — tên nhánh có dấu hoặc chữ
hoa gây rắc rối khi checkout giữa máy Windows và macOS.

---

## 3. Tuyệt đối KHÔNG commit

`.gitignore` đã chặn sẵn, nhưng cần hiểu **tại sao** để đừng dùng `git add -f`
phá vỡ nó:

| File / thư mục | Lý do |
|---|---|
| `src/main/resources/db.local.properties` | Mật khẩu MySQL riêng của mỗi máy. Commit lên là vừa lộ mật khẩu, vừa làm máy người khác nối sai DB. |
| `target/`, `*.war` | Sản phẩm build. Mỗi máy tự build ra được, commit vào chỉ làm repo phình và xung đột liên tục. |
| `.claude/` | Allowlist quyền sinh tự động, nhúng cứng đường dẫn tuyệt đối của một máy. |
| `uploads/` (ảnh) | Dữ liệu người dùng, không phải mã nguồn. |
| `.DS_Store`, `Thumbs.db` | Rác của Finder (macOS) và Explorer (Windows). |

**Đừng sửa `src/main/resources/db.properties`.** File đó được git theo dõi và dùng
chung. Muốn đổi cấu hình cho máy mình thì sửa `db.local.properties`. Sửa vào
`db.properties` là gây xung đột cho tất cả những người còn lại.

---

## 4. CI kiểm gì, và tại sao nó quan trọng với nhóm

Mỗi push và mỗi pull request đều chạy [.github/workflows/build.yml](.github/workflows/build.yml)
trên **Ubuntu**. Bốn thứ nó bắt:

1. **Sai chữ hoa/thường của tên file.** Đây là giá trị lớn nhất. macOS và Windows
   **không** phân biệt hoa thường, Linux thì có. Nên `href="assets/css/Style.css"`
   trong khi file thật là `style.css` sẽ chạy êm trên máy bạn và 404 trên máy
   khác. CI chạy Linux nên nó vỡ ngay tại đây, trước khi tới tay người khác.
2. **Build sạch bằng JDK 17.** Bắt trường hợp code chỉ compile được nhờ thứ gì đó
   còn sót trong `target/` của máy bạn.
3. **`schema.sql` áp được lên MySQL 8 thật**, và seed data vào thật — không phải
   chỉ "không báo lỗi".
4. **Không có file CRLF lọt vào repo.** Nếu có, người khác sẽ thấy diff "cả file
   thay đổi" chỉ vì họ sửa một dòng.

CI đỏ thì **đừng merge**. Mở tab **Actions**, bấm vào job đỏ, đọc log — thường
chỉ ra đúng dòng lỗi.

CI cũng đính kèm file WAR đã build ở mục **Artifacts** của mỗi lần chạy, nên ai
cần bản deploy thì tải về, không phải tự build.

---

## 5. Khi bị xung đột (conflict)

Nguyên nhân gần như luôn là: quên `git pull` trước khi sửa, hoặc hai người sửa
cùng một file.

```bash
git switch main
git pull
git switch ten-nhanh
git merge main
```

Git sẽ đánh dấu chỗ xung đột trong file. Mở file, giữ lại phần đúng, xóa các dòng
`<<<<<<<`, `=======`, `>>>>>>>`, rồi:

```bash
git add .
git commit
```

Thói quen tránh được phần lớn xung đột: **pull trước khi bắt đầu, push trước khi
nghỉ**, và mỗi nhánh làm một việc nhỏ thay vì một nhánh sửa 20 file.

---

## 6. Deploy

CI đã build ra `student-rent.war`. Để chạy trên máy chủ:

```bash
mvn clean package
```

Copy `target/student-rent.war` vào `webapps/` của một Tomcat **10.1.x** (Tomcat 9
dùng `javax.servlet` nên không chạy được), rồi trên máy chủ đó đặt biến môi trường
thay vì sửa file trong WAR:

```bash
export DB_URL="jdbc:mysql://<host>:3306/student_rent?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh"
export DB_USERNAME="<user>"
export DB_PASSWORD="<password>"
```

`DBConnection` ưu tiên biến môi trường cao nhất, trên cả `db.properties` và
`db.local.properties` — nên không cần mở WAR ra sửa gì.

Trên máy chủ thật, nhớ bật `<secure>true</secure>` cho session cookie trong
[web.xml](src/main/webapp/WEB-INF/web.xml) sau khi đã có HTTPS.
