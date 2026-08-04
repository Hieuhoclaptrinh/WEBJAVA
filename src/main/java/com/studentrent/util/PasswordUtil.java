package com.studentrent.util;

import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Ma hoa / kiem tra mat khau bang BCrypt.
 *
 * <p>Chay tu CLI de sinh hash cho du lieu mau:</p>
 * <pre>
 * mvn -q compile exec:java -Dexec.mainClass=com.studentrent.util.PasswordUtil -Dexec.args="matkhau"
 * </pre>
 */
public final class PasswordUtil {

    /** Chi phi bam BCrypt (2^12 vong) - can bang giua an toan va toc do. */
    private static final int LOG_ROUNDS = 12;

    private PasswordUtil() {
    }

    /** Bam mat khau plaintext. Moi lan goi cho ra hash khac nhau (salt ngau nhien). */
    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(LOG_ROUNDS));
    }

    /** So khop mat khau plaintext voi hash da luu trong DB. */
    public static boolean verify(String plainPassword, String storedHash) {
        if (plainPassword == null || storedHash == null || storedHash.isBlank()) {
            return false;
        }
        return BCrypt.checkpw(plainPassword, storedHash);
    }

    public static void main(String[] args) {
        String password = args.length > 0 ? args[0] : readPassword();
        String hashed = hash(password);
        System.out.println("Password : " + password);
        System.out.println("Hash     : " + hashed);
        System.out.println("Verify   : " + verify(password, hashed));
        warnIfNonAscii(password, args.length > 0);
    }

    /**
     * Doc mat khau tu ban phim khi khong truyen tham so.
     *
     * <p>Uu tien {@link Console#readPassword()} - khong hien mat khau ra terminal
     * va giai ma dung theo code page cua console. Khi khong co console (vd chay
     * trong output window cua IDE) thi doc stdin bang UTF-8 tuong minh.</p>
     */
    private static String readPassword() {
        System.out.print("Nhap mat khau can hash: ");
        System.out.flush();

        Console console = System.console();
        if (console != null) {
            char[] typed = console.readPassword();
            return typed != null ? new String(typed) : "";
        }
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String line = in.readLine();
            return line != null ? line : "";
        } catch (IOException e) {
            throw new IllegalStateException("Khong doc duoc mat khau tu stdin", e);
        }
    }

    /**
     * Canh bao khi mat khau co ky tu ngoai ASCII va duoc truyen qua tham so dong lenh.
     *
     * <p>Tham so dong lenh duoc JVM giai ma bang {@code sun.jnu.encoding}, thu nay
     * KHAC NHAU theo he dieu hanh: UTF-8 tren macOS, nhung la code page he thong
     * tren Windows (vd windows-1258). Cung mot mat khau tieng Viet go tren hai may
     * se thanh hai chuoi byte khac nhau, nen hash sinh o may nay khong verify duoc
     * mat khau go o may kia - loi rat kho doan ra. Chay khong tham so de go vao
     * tu ban phim thi khong bi van de nay.</p>
     */
    private static void warnIfNonAscii(String password, boolean fromArgv) {
        if (password.chars().allMatch(c -> c < 128)) {
            return;
        }
        System.out.println();
        System.out.println("CANH BAO: mat khau co ky tu ngoai ASCII.");
        System.out.println("  sun.jnu.encoding = " + System.getProperty("sun.jnu.encoding")
                + " | file.encoding = " + System.getProperty("file.encoding"));
        System.out.println("  Byte UTF-8       = " + toHex(password));
        if (fromArgv) {
            System.out.println("  Truyen qua -Dexec.args tren Windows co the bi bien dang.");
            System.out.println("  Hay chay lai KHONG kem tham so va go mat khau tu ban phim,");
            System.out.println("  hoac dung mat khau chi gom ky tu ASCII cho du lieu mau.");
        }
    }

    private static String toHex(String s) {
        StringBuilder sb = new StringBuilder();
        for (byte b : s.getBytes(StandardCharsets.UTF_8)) {
            sb.append(String.format("%02x ", b));
        }
        return sb.toString().trim();
    }
}
