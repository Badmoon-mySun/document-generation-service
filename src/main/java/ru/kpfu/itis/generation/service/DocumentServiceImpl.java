package ru.kpfu.itis.generation.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import com.itextpdf.layout.property.*;
import ru.kpfu.itis.generation.utils.PdfPageEnumerationEventHandler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.stream.Stream;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public class DocumentServiceImpl implements DocumentService {
    private static String imageDocumentLogoPath = "/home/badmoon/dev/document-generation-service/src/main/resources/static/img/kpfu-logo.png";


    public static void generateReport() {
        try {
            // Creating a PdfWriter
            String dest = "/home/badmoon/dev/document-generation-service/src/main/resources/static/img/rotatingImage.pdf";
            FileOutputStream fileOutputStream = new FileOutputStream(dest);

            Document document = setUpPdfDocument(fileOutputStream);


            // Создаем объект Image
            Image logo = new Image(ImageDataFactory.create(imageDocumentLogoPath));

            // Указываем размеры и расположение
            logo.setFixedPosition(10, 750);
            logo.setHeight(80);

            // Добовляем в документ
            document.add(logo);

            // Создаем блок для текста с номерами данных по которым подготовлен документ
            Div documentHeader = new Div();
            documentHeader.setMinHeight(90);


            // Создаем параграф для блока
            Paragraph headerParagraph = new Paragraph("Подготовленный отчет по данным по № ");
            // Создаем текст с жирным шрифтом
            Text num = new Text("10323010/250920/0007140");
            setTextBold(num);
            headerParagraph.add(num);

            // Задаем размеры и расположение текста параграфа
            headerParagraph.setWidth(170);
            headerParagraph.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            headerParagraph.setTextAlignment(TextAlignment.LEFT);

            // Добавляем заголовок в документ
            documentHeader.add(headerParagraph);
            document.add(documentHeader);

            // Добавляем в документ информацию о отчете
            setReportInfo(document);

            // Добавляем в документ обзац с участниками конференции
            Paragraph participantsParagraph = new Paragraph("Перечень участников конференции ");
            participantsParagraph.add((new Date(System.currentTimeMillis())).toString());
            participantsParagraph.setMarginTop(15);

            document.add(participantsParagraph);

            // Создаем таблицу с участниками
            Table participantsTable = new Table(new float[]{65f, 60f, 60f, 135f, 175f, 70f});
            participantsTable.setMarginTop(10);

            // Добавляем шапку таблицы
            addTableHeader(participantsTable);
            // Заполняем таблицу данными
            addRowsTable(participantsTable);

            // Добавляем таблицу в документ
            document.add(participantsTable);

            // Добавляем примечание в документ
            Paragraph noteParagraph = new Paragraph("Примечание: ");
            noteParagraph.add("время указано в часовом поясе MSK (UTC+3) в соответствии с системными часами сервера или АРМ.");
            noteParagraph.setFontSize(10);
            document.add(noteParagraph);

            // Закрываем документ
            document.close();
        } catch (FileNotFoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public static Document setUpPdfDocument(OutputStream outputStream) {
        PdfWriter writer = new PdfWriter(outputStream);

        // Создаем pdf документ
        PdfDocument pdfDoc = new PdfDocument(writer);
        PdfPageEnumerationEventHandler headerHandler = new PdfPageEnumerationEventHandler();

        // Добавляем событие для нумерации страниц
        pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, headerHandler);

        // Создаем окумент
        Document document = new Document(pdfDoc);

        // Создаем и добавляем шрифт в документ для поддержки русской кирилицы
        FontSet fontSet  = new FontSet();
        fontSet.addFont("/home/badmoon/dev/document-generation-service/src/main/resources/static/font/Roboto/Roboto-Regular.ttf");
        document.setFontProvider(new FontProvider(fontSet));
        document.setProperty(Property.FONT, new String[]{"Roboto"});
        document.setFontSize(10);

        // Указываем отступы документа по умолчанию
        document.setMargins(20, 15, 20,15);

        return document;
    }

    private static void setReportInfo(Document document) {
        Table table = new Table(2);

        table.addCell(getCellWithOutBorder().add(new Paragraph("Институт:")));
        table.addCell(getCellWithOutBorder().add(new Paragraph("ИТИС (ИНН: 5038093740)")));

        table.addCell(getCellWithOutBorder().add(new Paragraph("Логин:")));
        table.addCell(getCellWithOutBorder().add(new Paragraph("Login")));

        table.addCell(getCellWithOutBorder().add(new Paragraph("Количество студентов:")));
        table.addCell(getCellWithOutBorder().add(new Paragraph("65")));

        table.addCell(getCellWithOutBorder().add(new Paragraph("Номер отчёта:")));
        table.addCell(getCellWithOutBorder().add(new Paragraph("32223")));

        table.addCell(getCellWithOutBorder().add(new Paragraph("Тип отчёта:")));
        table.addCell(getCellWithOutBorder().add(new Paragraph("ТТ")));

        document.add(table);
    }

    private static Cell getCellWithOutBorder() {
        return new Cell().setBorder(Border.NO_BORDER);
    }

    private static void addTableHeader(Table table) {
        Stream.of("Сформирован", "Оформлен", "Зачислил", "Комментарий", "ФИО, Должность", "IP-адрес")
                .forEach(columnTitle -> {
                    Cell header = getFormatTableCell();
                    header.setHeight(30);

                    Text text = new Text(columnTitle);
                    text.setFontSize(9);
                    setTextBold(text);

                    header.add(new Paragraph(text));
                    table.addHeaderCell(header);
                });
    }

    private static void addRowsTable(Table table) {
        for (int i = 0; i < 30; i++) {
            table.addCell(getFormatTableCell().add(new Paragraph("25-09-2020 13:46:25").setFontSize(8)));
            table.addCell(getFormatTableCell().add(new Paragraph("25-09-2020 13:46:26").setFontSize(8)));
            table.addCell(getFormatTableCell().add(new Paragraph("25-09-2020 13:46:41").setFontSize(8)));
            table.addCell(getFormatTableCell().add(new Paragraph("Какой-нибудь текст, Какой-нибудь длинный, а может быть не очень длинный текст, главное чтобы верстка не полетела Версия").setFontSize(8)));
            Cell cell = getFormatTableCell();
            cell.add(new Paragraph("Иванов Иван Иванович").setFontSize(8));
            cell.add(new Paragraph("Иванов Иван Иванович").setFontSize(8));
            cell.add(new Paragraph("Иванов Иван Иванович").setFontSize(8));
            cell.add(new Paragraph("Иванов Иван Иванович").setFontSize(8));
            table.addCell(cell);

            table.addCell(getFormatTableCell().add(new Paragraph("192.168.0.15").setFontSize(8)));
        }
    }

    private static Cell getFormatTableCell() {
        Cell cell = new Cell().setTextAlignment(TextAlignment.CENTER)
                            .setVerticalAlignment(VerticalAlignment.MIDDLE);
        Border border = new SolidBorder(.8f);
        cell.setBorder(border);
        return cell;
    }

    private static void setTextBold(Text text) {
        text.setStrokeWidth(0.2f)
                .setTextRenderingMode(PdfCanvasConstants.TextRenderingMode.FILL_STROKE)
                .setStrokeWidth(0.2f)
                .setStrokeColor(DeviceGray.BLACK);
    }
}
