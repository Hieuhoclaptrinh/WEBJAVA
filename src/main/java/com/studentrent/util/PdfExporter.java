package com.studentrent.util;

import com.studentrent.model.Contract;
import com.studentrent.model.Invoice;

import java.io.OutputStream;

/**
 * Xuat hoa don / hop dong ra PDF bang iText 5.
 *
 * <p>Luu y font: iText 5 mac dinh khong render duoc tieng Viet co dau.
 * Can nhung font TTF Unicode (vd DejaVuSans.ttf hoac Arial) vao
 * src/main/resources/fonts/ va tao BaseFont voi
 * {@code BaseFont.IDENTITY_H, BaseFont.EMBEDDED}.</p>
 *
 * Skeleton - chua implement.
 */
public final class PdfExporter {

    /** Font Unicode dat trong src/main/resources/fonts/. */
    public static final String FONT_RESOURCE = "fonts/DejaVuSans.ttf";

    private PdfExporter() {
    }

    /** Ghi PDF hoa don truc tiep ra output stream cua response. */
    public static void exportInvoice(Invoice invoice, OutputStream out) {
        throw new UnsupportedOperationException("TODO: PdfExporter.exportInvoice");
    }

    /** Ghi PDF hop dong thue. */
    public static void exportContract(Contract contract, OutputStream out) {
        throw new UnsupportedOperationException("TODO: PdfExporter.exportContract");
    }
}
