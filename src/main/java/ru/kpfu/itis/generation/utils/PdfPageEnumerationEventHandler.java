package ru.kpfu.itis.generation.utils;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Data
@AllArgsConstructor
@Component
public class PdfPageEnumerationEventHandler implements IEventHandler {
    private int startPageNumber;

    public PdfPageEnumerationEventHandler() {
        this.startPageNumber = 0;
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdf = docEvent.getDocument();

        PdfPage page = docEvent.getPage();
        int pageNumber = pdf.getPageNumber(page) - this.startPageNumber;

        Rectangle pageSize = page.getPageSize();
        PdfCanvas pdfCanvas = new PdfCanvas(
                page.newContentStreamBefore(), page.getResources(), pdf);

        Canvas canvas = new Canvas(pdfCanvas, pageSize);

        Paragraph p = new Paragraph(String.valueOf(pageNumber));
        p.setFontSize(7);

        canvas.showTextAligned(p, 580, 13, TextAlignment.RIGHT);
        pdfCanvas.release();
    }
}
