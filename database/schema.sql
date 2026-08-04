-- =====================================================================
--  QUAN LY NHA TRO SINH VIEN - SCHEMA
--  MySQL 8.0+ / InnoDB / utf8mb4
--
--  Chay (dung tren ca zsh/bash cua macOS va PowerShell cua Windows):
--      mysql -u root -p -e "source database/schema.sql"
--
--  KHONG dung  mysql -u root -p < database/schema.sql
--  PowerShell khong ho tro toan tu '<', se bao
--  "The '<' operator is reserved for future use." va khong chay gi ca.
--
--  CANH BAO: dong DROP DATABASE ngay duoi day XOA SACH database student_rent.
--  Chi chay file nay khi tao database lan dau, hoac khi that su muon reset
--  toan bo du lieu. Dung chay lai tren may dang co du lieu can giu.
-- =====================================================================

DROP DATABASE IF EXISTS student_rent;
CREATE DATABASE student_rent
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE student_rent;

-- =====================================================================
-- 1. users - tai khoan quan tri / nhan vien
-- =====================================================================
CREATE TABLE users (
    id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    username      VARCHAR(50)     NOT NULL,
    password_hash VARCHAR(60)     NOT NULL COMMENT 'BCrypt hash - luon dai 60 ky tu',
    full_name     VARCHAR(150)    NOT NULL,
    email         VARCHAR(150)         NULL,
    phone         VARCHAR(20)          NULL,
    role          ENUM('ADMIN','MANAGER','STAFF') NOT NULL DEFAULT 'STAFF',
    status        ENUM('ACTIVE','LOCKED')         NOT NULL DEFAULT 'ACTIVE',
    last_login_at DATETIME             NULL,
    created_at    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_users_username (username),
    UNIQUE KEY uk_users_email (email),
    KEY idx_users_role (role)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- =====================================================================
-- 2. room_types - loai phong (don, doi, VIP...)
-- =====================================================================
CREATE TABLE room_types (
    id             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name           VARCHAR(100)    NOT NULL,
    base_price     DECIMAL(12,2)   NOT NULL DEFAULT 0 COMMENT 'Gia niem yet / thang (VND)',
    max_occupants  INT UNSIGNED    NOT NULL DEFAULT 1,
    default_area   DECIMAL(6,2)         NULL COMMENT 'Dien tich m2',
    description    VARCHAR(500)         NULL,
    created_at     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_room_types_name (name),
    CONSTRAINT chk_room_types_price CHECK (base_price >= 0),
    CONSTRAINT chk_room_types_occ   CHECK (max_occupants >= 1)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- =====================================================================
-- 3. rooms - phong tro
-- =====================================================================
CREATE TABLE rooms (
    id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    room_code     VARCHAR(20)     NOT NULL COMMENT 'VD: A101',
    room_type_id  BIGINT UNSIGNED NOT NULL,
    floor_no      INT             NOT NULL DEFAULT 1,
    area          DECIMAL(6,2)         NULL,
    price         DECIMAL(12,2)   NOT NULL COMMENT 'Gia thuc te cua phong (co the khac base_price)',
    max_occupants INT UNSIGNED    NOT NULL DEFAULT 1,
    status        ENUM('AVAILABLE','OCCUPIED','RESERVED','MAINTENANCE')
                  NOT NULL DEFAULT 'AVAILABLE',
    image_path    VARCHAR(255)         NULL COMMENT 'Duong dan tuong doi trong /uploads',
    description   VARCHAR(500)         NULL,
    created_at    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_rooms_code (room_code),
    KEY idx_rooms_status (status),
    KEY idx_rooms_type (room_type_id),
    CONSTRAINT fk_rooms_room_type FOREIGN KEY (room_type_id)
        REFERENCES room_types (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_rooms_price CHECK (price >= 0)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- =====================================================================
-- 4. tenants - khach thue (sinh vien)
-- =====================================================================
CREATE TABLE tenants (
    id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    full_name     VARCHAR(150)    NOT NULL,
    id_card       VARCHAR(20)     NOT NULL COMMENT 'CMND / CCCD',
    date_of_birth DATE                 NULL,
    gender        ENUM('MALE','FEMALE','OTHER') NOT NULL DEFAULT 'OTHER',
    phone         VARCHAR(20)     NOT NULL,
    email         VARCHAR(150)         NULL,
    hometown      VARCHAR(255)         NULL COMMENT 'Dia chi thuong tru',
    school        VARCHAR(200)         NULL,
    student_code  VARCHAR(50)          NULL,
    avatar_path   VARCHAR(255)         NULL,
    note          VARCHAR(500)         NULL,
    created_at    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_tenants_id_card (id_card),
    KEY idx_tenants_name (full_name),
    KEY idx_tenants_phone (phone)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- =====================================================================
-- 5. contracts - hop dong thue
-- =====================================================================
CREATE TABLE contracts (
    id             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    contract_code  VARCHAR(30)     NOT NULL COMMENT 'VD: HD2026-0001',
    room_id        BIGINT UNSIGNED NOT NULL,
    tenant_id      BIGINT UNSIGNED NOT NULL COMMENT 'Nguoi dai dien ky hop dong',
    start_date     DATE            NOT NULL,
    end_date       DATE            NOT NULL,
    deposit        DECIMAL(12,2)   NOT NULL DEFAULT 0 COMMENT 'Tien coc',
    monthly_price  DECIMAL(12,2)   NOT NULL COMMENT 'Gia chot trong hop dong',
    num_occupants  INT UNSIGNED    NOT NULL DEFAULT 1,
    billing_day    TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT 'Ngay chot hoa don hang thang (1-28)',
    status         ENUM('PENDING','ACTIVE','EXPIRED','TERMINATED')
                   NOT NULL DEFAULT 'ACTIVE',
    terminated_at  DATE                 NULL,
    note           VARCHAR(500)         NULL,
    created_by     BIGINT UNSIGNED      NULL,
    created_at     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_contracts_code (contract_code),
    KEY idx_contracts_room (room_id),
    KEY idx_contracts_tenant (tenant_id),
    KEY idx_contracts_status (status),
    KEY idx_contracts_period (start_date, end_date),
    CONSTRAINT fk_contracts_room FOREIGN KEY (room_id)
        REFERENCES rooms (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_contracts_tenant FOREIGN KEY (tenant_id)
        REFERENCES tenants (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_contracts_user FOREIGN KEY (created_by)
        REFERENCES users (id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT chk_contracts_dates CHECK (end_date > start_date),
    CONSTRAINT chk_contracts_billing_day CHECK (billing_day BETWEEN 1 AND 28)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- =====================================================================
-- 6. services - dich vu (dien, nuoc, rac, wifi, gui xe...)
-- =====================================================================
CREATE TABLE services (
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    code       VARCHAR(30)     NOT NULL COMMENT 'VD: ELEC, WATER, WIFI',
    name       VARCHAR(100)    NOT NULL,
    unit       VARCHAR(30)     NOT NULL DEFAULT 'thang' COMMENT 'kWh, m3, thang, nguoi...',
    unit_price DECIMAL(12,2)   NOT NULL DEFAULT 0,
    type       ENUM('FIXED','METERED','PER_PERSON') NOT NULL DEFAULT 'FIXED'
               COMMENT 'FIXED: khoan; METERED: theo chi so; PER_PERSON: theo dau nguoi',
    status     ENUM('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_services_code (code),
    CONSTRAINT chk_services_price CHECK (unit_price >= 0)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- =====================================================================
-- 7. invoices - hoa don thang
-- =====================================================================
CREATE TABLE invoices (
    id             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    invoice_code   VARCHAR(30)     NOT NULL COMMENT 'VD: HD2026080001',
    contract_id    BIGINT UNSIGNED NOT NULL,
    period_month   TINYINT UNSIGNED NOT NULL,
    period_year    SMALLINT UNSIGNED NOT NULL,
    issue_date     DATE            NOT NULL,
    due_date       DATE            NOT NULL,
    room_charge    DECIMAL(12,2)   NOT NULL DEFAULT 0 COMMENT 'Tien phong',
    service_total  DECIMAL(12,2)   NOT NULL DEFAULT 0 COMMENT 'Tong tien dich vu (= SUM invoice_details.amount)',
    discount       DECIMAL(12,2)   NOT NULL DEFAULT 0,
    total_amount   DECIMAL(12,2)   NOT NULL DEFAULT 0 COMMENT 'room_charge + service_total - discount',
    paid_amount    DECIMAL(12,2)   NOT NULL DEFAULT 0,
    status         ENUM('UNPAID','PARTIAL','PAID','OVERDUE','CANCELLED')
                   NOT NULL DEFAULT 'UNPAID',
    payment_date   DATE                 NULL,
    payment_method ENUM('CASH','TRANSFER','MOMO','OTHER') NULL,
    note           VARCHAR(500)         NULL,
    created_by     BIGINT UNSIGNED      NULL,
    created_at     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_invoices_code (invoice_code),
    UNIQUE KEY uk_invoices_period (contract_id, period_year, period_month)
        COMMENT 'Moi hop dong chi 1 hoa don / thang',
    KEY idx_invoices_status (status),
    KEY idx_invoices_period (period_year, period_month),
    KEY idx_invoices_due (due_date),
    CONSTRAINT fk_invoices_contract FOREIGN KEY (contract_id)
        REFERENCES contracts (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_invoices_user FOREIGN KEY (created_by)
        REFERENCES users (id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT chk_invoices_month CHECK (period_month BETWEEN 1 AND 12),
    CONSTRAINT chk_invoices_paid  CHECK (paid_amount >= 0)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- =====================================================================
-- 8. invoice_details - chi tiet dich vu tren hoa don
-- =====================================================================
CREATE TABLE invoice_details (
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    invoice_id BIGINT UNSIGNED NOT NULL,
    service_id BIGINT UNSIGNED NOT NULL,
    old_index  DECIMAL(12,2)        NULL COMMENT 'Chi so dau (dich vu METERED)',
    new_index  DECIMAL(12,2)        NULL COMMENT 'Chi so cuoi (dich vu METERED)',
    quantity   DECIMAL(12,2)   NOT NULL DEFAULT 1 COMMENT 'METERED: new_index - old_index',
    unit_price DECIMAL(12,2)   NOT NULL DEFAULT 0 COMMENT 'Snapshot gia luc lap hoa don',
    amount     DECIMAL(12,2)   NOT NULL DEFAULT 0 COMMENT 'quantity * unit_price',
    note       VARCHAR(255)         NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_invoice_details (invoice_id, service_id),
    KEY idx_invoice_details_service (service_id),
    CONSTRAINT fk_details_invoice FOREIGN KEY (invoice_id)
        REFERENCES invoices (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_details_service FOREIGN KEY (service_id)
        REFERENCES services (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_details_qty CHECK (quantity >= 0)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;


-- =====================================================================
--  DU LIEU MAU
-- =====================================================================

-- Tai khoan admin. Mat khau: admin123
-- Hash duoi day duoc sinh bang chinh PasswordUtil cua du an (BCrypt, 12 rounds)
-- va da kiem tra checkpw("admin123", hash) == true.
--
-- Doi sang mat khau khac:
--   mvn -q compile exec:java -Dexec.mainClass=com.studentrent.util.PasswordUtil -Dexec.args="matkhaumoi"
--   UPDATE users SET password_hash = '<hash moi>' WHERE username = 'admin';
INSERT INTO users (username, password_hash, full_name, email, phone, role) VALUES
('admin', '$2a$12$53hYbzcjjksZwf2gKS8zv.Hm7Qj9n.jpTqB72/1C2ZZM/KmPH.NG2',
 'Quan tri he thong', 'admin@studentrent.local', '0900000000', 'ADMIN');

INSERT INTO room_types (name, base_price, max_occupants, default_area, description) VALUES
('Phong don',      1800000.00, 1, 12.00, 'Phong 1 nguoi, khep kin'),
('Phong doi',      2500000.00, 2, 18.00, 'Phong 2 nguoi, khep kin'),
('Phong ghep 4',   3600000.00, 4, 28.00, 'Phong ghep 4 nguoi'),
('Phong VIP',      4500000.00, 2, 25.00, 'Ban cong, dieu hoa, nong lanh');

INSERT INTO services (code, name, unit, unit_price, type) VALUES
('ELEC',  'Tien dien',   'kWh',   3500.00,  'METERED'),
('WATER', 'Tien nuoc',   'm3',    15000.00, 'METERED'),
('TRASH', 'Phi rac',     'thang', 20000.00, 'FIXED'),
('WIFI',  'Internet',    'thang', 100000.00,'FIXED'),
('PARK',  'Gui xe',      'nguoi', 80000.00, 'PER_PERSON');

INSERT INTO rooms (room_code, room_type_id, floor_no, area, price, max_occupants, status) VALUES
('A101', 1, 1, 12.00, 1800000.00, 1, 'AVAILABLE'),
('A102', 2, 1, 18.00, 2500000.00, 2, 'AVAILABLE'),
('A201', 2, 2, 18.00, 2600000.00, 2, 'AVAILABLE'),
('A202', 3, 2, 28.00, 3600000.00, 4, 'MAINTENANCE'),
('B301', 4, 3, 25.00, 4500000.00, 2, 'AVAILABLE');
