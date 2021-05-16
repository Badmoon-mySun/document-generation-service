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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.kpfu.itis.generation.dto.ConferencesReportDto;
import ru.kpfu.itis.generation.utils.PdfPageEnumerationEventHandler;

import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    public void generateReport(List<ConferencesReportDto> reportDtoList, OutputStream outputStream) {
        PdfPageEnumerationEventHandler enumerationEventHandler = new PdfPageEnumerationEventHandler();

        // Создаем документ
        Document document = setUpPdfDocument(outputStream, enumerationEventHandler);

        // Проходим по всем отчетам
        reportDtoList.forEach(reportDto -> {
            // Создаем объект Image
            Image logo;
            try {
                logo = new Image(ImageDataFactory.create("classpath:/static/img/kpfu-logo.png"));
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException(e);
            }

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
            Text num = new Text(String.join("/", reportDto.getAccordingTo()));
            setTextBold(num);
            headerParagraph.add(num);

            // Задаем размеры и расположение текста параграфа
            headerParagraph.setWidth(195);
            headerParagraph.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            headerParagraph.setTextAlignment(TextAlignment.LEFT);

            // Добавляем заголовок в документ
            documentHeader.add(headerParagraph);
            document.add(documentHeader);

            // Добавляем в документ информацию о отчете
            setReportInfo(document, reportDto);

            // Добавляем в документ обзац с участниками конференции
            Paragraph participantsParagraph = new Paragraph("Перечень участников конференции ");
            DateFormat participantsDateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
            participantsParagraph.add(participantsDateFormat.format(reportDto.getConferenceTime()));
            participantsParagraph.add(":");
            participantsParagraph.setMarginTop(15);

            document.add(participantsParagraph);

            // Создаем таблицу с участниками
            Table participantsTable = new Table(new float[]{65f, 60f, 60f, 135f, 175f, 70f});
            participantsTable.setMarginTop(10);

            // Добавляем шапку таблицы
            addTableHeader(participantsTable);
            // Заполняем таблицу данными
            addRowsTable(participantsTable, reportDto);

            // Добавляем таблицу в документ
            document.add(participantsTable);

            // Если есть, то добавляем примечание
            if (StringUtils.hasText(reportDto.getNote())) {
                Paragraph noteParagraph = new Paragraph("Примечание: ");
                noteParagraph.add(reportDto.getNote());
                document.add(noteParagraph);
            }

            // Начать нумерацию заново
            enumerationEventHandler.startNewEnumeration();

            // Добавляем следующую страницу если это не последнй элемент
            System.out.println(reportDtoList.indexOf(reportDto));
            if (reportDtoList.indexOf(reportDto) != reportDtoList.size() - 1) {
                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            }
        });

        // Закрываем документ
        document.close();
    }


    public Document setUpPdfDocument(OutputStream outputStream,
                                     PdfPageEnumerationEventHandler enumerationEventHandler) {
        PdfWriter writer = new PdfWriter(outputStream);

        // Создаем pdf документ
        PdfDocument pdfDoc = new PdfDocument(writer);

        // Добавляем событие для нумерации страниц
        pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, enumerationEventHandler);

        // Создаем окумент
        Document document = new Document(pdfDoc);

        // Создаем и добавляем шрифт в документ для поддержки русской кирилицы
        FontSet fontSet = new FontSet();
        fontSet.addFont("static/font/Roboto/Roboto-Regular.ttf");
        document.setFontProvider(new FontProvider(fontSet));
        document.setProperty(Property.FONT, new String[]{"Roboto"});
        document.setFontSize(10);

        // Указываем отступы документа по умолчанию
        document.setMargins(20, 15, 20, 15);

        return document;
    }

    // Создаем таблицу и добавляем поля
    private void setReportInfo(Document document, ConferencesReportDto reportDto) {
        Table table = new Table(2);

        table.addCell(getCellWithOutBorder().add(new Paragraph("Институт:")));
        table.addCell(getCellWithOutBorder().add(new Paragraph(reportDto.getInstitute().toString())));

        table.addCell(getCellWithOutBorder().add(new Paragraph("Логин:")));
        table.addCell(getCellWithOutBorder().add(new Paragraph(reportDto.getLogin())));

        table.addCell(getCellWithOutBorder().add(new Paragraph("Количество студентов:")));
        table.addCell(getCellWithOutBorder().add(new Paragraph(String.valueOf(reportDto.getStudentsCount()))));

        table.addCell(getCellWithOutBorder().add(new Paragraph("Номер отчёта:")));
        table.addCell(getCellWithOutBorder().add(new Paragraph(String.valueOf(reportDto.getId()))));

        table.addCell(getCellWithOutBorder().add(new Paragraph("Тип отчёта:")));
        table.addCell(getCellWithOutBorder().add(new Paragraph(reportDto.getType())));

        document.add(table);
    }

    private static Cell getCellWithOutBorder() {
        return new Cell().setBorder(Border.NO_BORDER);
    }

    // Добавляем в таблицу шапку
    private static void addTableHeader(Table table) {
        Stream.of("Сформирован", "Оформлен", "Зачислил", "Комментарий", "ФИО, Должность", "IP-адрес")
                .forEach(columnTitle -> {
                    Text text = new Text(columnTitle);
                    text.setFontSize(9);
                    setTextBold(text);

                    Cell header = getFormatTableCell(text);
                    header.setHeight(30);

                    table.addHeaderCell(header);
                });
    }

    // Добавляем в Таблицу строки
    private static void addRowsTable(Table table, ConferencesReportDto reportDto) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        reportDto.getParticipants().forEach(participant -> {
            table.addCell(getFormatTableCell(new Text(dateFormat.format(participant.getFormed())).setFontSize(8)));
            table.addCell(getFormatTableCell(new Text(dateFormat.format(participant.getDecorated())).setFontSize(8)));
            table.addCell(getFormatTableCell(new Text(dateFormat.format(participant.getEnrolled())).setFontSize(8)));

            table.addCell(getFormatTableCell(new Text(participant.getComment()).setFontSize(8)));

            String nameAndPosition = participant.getName() + "\n" + participant.getPosition();
            table.addCell(getFormatTableCell(new Text(nameAndPosition).setFontSize(8)));

            table.addCell(getFormatTableCell(new Text(participant.getIpAddress()).setFontSize(8)));
        });
    }

    // Возвращает отформатированную ячейку таблицы
    private static Cell getFormatTableCell(Text text) {
        Cell cell = new Cell().setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        Border border = new SolidBorder(.8f);
        cell.setBorder(border);

        cell.add(new Paragraph(text));

        return cell;
    }

    // Устанавливает жирный текст
    private static void setTextBold(Text text) {
        text.setStrokeWidth(0.2f)
                .setTextRenderingMode(PdfCanvasConstants.TextRenderingMode.FILL_STROKE)
                .setStrokeWidth(0.2f)
                .setStrokeColor(DeviceGray.BLACK);
    }
}
