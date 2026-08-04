package com.studentrent.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Quan ly connection pool HikariCP cho toan bo ung dung.
 *
 * <p>Doc cau hinh tu {@code db.properties} tren classpath
 * (src/main/resources/db.properties), sau do ghi de theo thu tu:</p>
 * <ol>
 *   <li>{@code src/main/resources/db.local.properties} - cau hinh rieng cua may
 *       ban dang ngoi, bi .gitignore bo qua nen khong bao gio len git. Day la
 *       cach nen dung khi lam viec tren nhieu may (Windows + macOS): chi can
 *       khai bao lai key nao khac, vi du {@code jdbc.password=matkhau_may_nay}.</li>
 *   <li>bien moi truong {@code DB_URL} / {@code DB_USERNAME} / {@code DB_PASSWORD}
 *       - uu tien cao nhat, dung khi deploy that de khong phai sua file trong WAR.</li>
 * </ol>
 *
 * <p>Cach dung trong DAO:</p>
 * <pre>{@code
 * try (Connection conn = DBConnection.getConnection();
 *      PreparedStatement ps = conn.prepareStatement(sql)) {
 *     ...
 * }
 * }</pre>
 *
 * <p>Pool duoc dong lai trong {@code AppContextListener} khi ung dung shutdown.</p>
 */
public final class DBConnection {

    private static final String CONFIG_FILE = "db.properties";

    /**
     * Cau hinh rieng cua tung may, khong bat buoc, bi .gitignore bo qua.
     * Key nao co trong file nay se ghi de key cung ten trong {@link #CONFIG_FILE}.
     */
    private static final String LOCAL_CONFIG_FILE = "db.local.properties";

    private static volatile HikariDataSource dataSource;

    private DBConnection() {
        // Utility class - khong khoi tao
    }

    /** Lay mot connection tu pool. Nguoi goi PHAI dong (try-with-resources). */
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    /** Khoi tao lazy + thread-safe (double-checked locking). */
    private static HikariDataSource getDataSource() {
        HikariDataSource ds = dataSource;
        if (ds == null || ds.isClosed()) {
            synchronized (DBConnection.class) {
                ds = dataSource;
                if (ds == null || ds.isClosed()) {
                    dataSource = ds = createDataSource();
                }
            }
        }
        return ds;
    }

    private static HikariDataSource createDataSource() {
        Properties props = loadProperties();

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(props.getProperty("jdbc.driver", "com.mysql.cj.jdbc.Driver"));

        // Bien moi truong uu tien hon file cau hinh
        config.setJdbcUrl(env("DB_URL", props.getProperty("jdbc.url")));
        config.setUsername(env("DB_USERNAME", props.getProperty("jdbc.username")));
        config.setPassword(env("DB_PASSWORD", props.getProperty("jdbc.password", "")));

        config.setMaximumPoolSize(intProp(props, "hikari.maximumPoolSize", 10));
        config.setMinimumIdle(intProp(props, "hikari.minimumIdle", 2));
        config.setConnectionTimeout(longProp(props, "hikari.connectionTimeout", 30_000L));
        config.setIdleTimeout(longProp(props, "hikari.idleTimeout", 600_000L));
        config.setMaxLifetime(longProp(props, "hikari.maxLifetime", 1_800_000L));
        config.setConnectionTestQuery(props.getProperty("hikari.connectionTestQuery", "SELECT 1"));
        config.setPoolName(props.getProperty("hikari.poolName", "StudentRentPool"));
        config.setAutoCommit(Boolean.parseBoolean(props.getProperty("hikari.autoCommit", "true")));

        return new HikariDataSource(config);
    }

    /**
     * Nap cau hinh theo 3 tang, tang sau ghi de tang truoc:
     * <ol>
     *   <li>{@code db.properties} - bat buoc, duoc commit, gia tri mac dinh dung chung</li>
     *   <li>{@code db.local.properties} - tuy chon, .gitignore bo qua, rieng tung may</li>
     *   <li>bien moi truong {@code DB_URL} / {@code DB_USERNAME} / {@code DB_PASSWORD}
     *       (xu ly trong {@link #createDataSource()})</li>
     * </ol>
     * Nho tang 2 ma moi may co the tro vao MySQL cua rieng no ma khong ai phai
     * sua file dang duoc git theo doi - tranh xung dot moi lan dong bo hai may.
     */
    private static Properties loadProperties() {
        Properties props = new Properties();
        load(props, CONFIG_FILE, true);
        load(props, LOCAL_CONFIG_FILE, false);
        return props;
    }

    /**
     * Doc mot file properties tren classpath vao {@code props}.
     * Doc bang UTF-8 (khong dung {@code load(InputStream)} vi ham do giai ma
     * ISO-8859-1, se lam hong mat khau co ky tu ngoai ASCII).
     *
     * @param required nem loi neu khong tim thay file; {@code false} thi bo qua
     */
    private static void load(Properties props, String resource, boolean required) {
        try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream(resource)) {
            if (in == null) {
                if (required) {
                    throw new IllegalStateException(
                            "Khong tim thay " + resource + " tren classpath (src/main/resources)");
                }
                return;
            }
            props.load(new InputStreamReader(in, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IllegalStateException("Loi doc " + resource, e);
        }
    }

    /**
     * Lay gia tri tu bien moi truong, khong co thi dung {@code fallback}.
     *
     * <p>Chi coi la "khong dat" khi bien moi truong khong ton tai. Bien duoc dat
     * thanh chuoi rong VAN duoc ton trong - can thiet vi MySQL cai bang Homebrew
     * tren macOS mac dinh khong co mat khau root, nen {@code DB_PASSWORD=} phai
     * ghi de duoc mat khau co san trong file cau hinh.</p>
     */
    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return value != null ? value : fallback;
    }

    private static int intProp(Properties p, String key, int def) {
        try {
            return Integer.parseInt(p.getProperty(key, String.valueOf(def)).trim());
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static long longProp(Properties p, String key, long def) {
        try {
            return Long.parseLong(p.getProperty(key, String.valueOf(def)).trim());
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /** Dong pool - goi tu ServletContextListener khi undeploy. */
    public static void shutdown() {
        HikariDataSource ds = dataSource;
        if (ds != null && !ds.isClosed()) {
            ds.close();
        }
    }
}
