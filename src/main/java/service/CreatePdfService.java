package service;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.PdfPageSize;
import com.spire.pdf.graphics.*;
import com.spire.pdf.tables.PdfHeaderSource;
import com.spire.pdf.tables.PdfTable;
import entity.IncludedOption;
import entity.IncludedOptionToTariff;
import entity.Service;
import entity.Tariff;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreatePdfService {
    private static final IncludedOptionToTariffService includedOptionToTariffService = ServiceFactory.getIncludedOptionToTariffService();
    private static final IncludedOptionService includedOptionService = ServiceFactory.getIncludedOptionService();

    private CreatePdfService() {
    }

    public static CreatePdfService getInstance() {
        return CreatePdfService.Singleton.INSTANCE;
    }
    private static class Singleton {
        private final static CreatePdfService INSTANCE = new CreatePdfService();
    }

    public void createServiceInPdf() {
//Create a PdfDocument instance

        PdfDocument doc = new PdfDocument();

        //Set margins

        PdfUnitConvertor unitCvtr = new PdfUnitConvertor();

        PdfMargins margin = new PdfMargins();

        margin.setTop(unitCvtr.convertUnits(2.54f, PdfGraphicsUnit.Centimeter, PdfGraphicsUnit.Point));

        margin.setBottom(margin.getTop());

        margin.setLeft(unitCvtr.convertUnits(3.17f, PdfGraphicsUnit.Centimeter, PdfGraphicsUnit.Point));

        margin.setRight(margin.getLeft());

        //Add one page
        ServiceForService serviceService = ServiceFactory.getServiceService();

        for (Service service :
                serviceService.findAllService()) {


            PdfPageBase page = doc.getPages().add(PdfPageSize.A4, margin);

            float y = 10;

            //Draw title
            PdfBrush brush = PdfBrushes.getBlack();
            PdfTrueTypeFont tableFont= new PdfTrueTypeFont(new Font("Arial", 0, 10));
            PdfTrueTypeFont headerFont= new PdfTrueTypeFont(new Font("Arial", Font.BOLD, 11));
            PdfBrush brush1 = PdfBrushes.getBlack();
            PdfTrueTypeFont font1 = new PdfTrueTypeFont(new Font("Arial", Font.BOLD, 16));

            PdfStringFormat format1 = new PdfStringFormat(PdfTextAlignment.Center);

            page.getCanvas().drawString(service.getServiceName(), font1, brush1, page.getCanvas().getClientSize().getWidth() / 2, y, format1);

            y = y + (float) font1.measureString(service.getServiceName(), format1).getHeight();

            y = y + 5;

            //Data source to create table
            List<String> dataList = new ArrayList<>();
            dataList.add("Tariff;Description;Cost");
            for (Tariff tariff : service.getTariffs()) {
                StringBuilder option = new StringBuilder();
                for (IncludedOptionToTariff includedOptionToTariff : includedOptionToTariffService.findIncludedOptionToTariffByNumber(tariff.getId())) {
                    Optional<IncludedOption> includedOption = includedOptionService.findIncludedOptionByNumber(includedOptionToTariff.getOptionId());
                    includedOption.ifPresent(value -> option.append(value.getDefinition()).append("\n"));
                }
                dataList.add(tariff.getTariffName() + ";" + option.toString().trim() + ";" + tariff.getCost());
            }
            String[] data = dataList.toArray(new String[0]);

            String[][] dataSource = new String[data.length][];

            for (int i = 0; i < data.length; i++) {

                dataSource[i] = data[i].split("[;]", -1);

            }

            //Create a PdfTable instance and set data source

            PdfTable table = new PdfTable();
//set the default cell style and row style
            table.getStyle().setCellPadding(2);
            table.getStyle().setBorderPen(new PdfPen(brush, 0.75f));
            table.getStyle().getDefaultStyle().setBackgroundBrush(PdfBrushes.getWhite());
            table.getStyle().getDefaultStyle().setFont(tableFont);
            table.getStyle().getDefaultStyle().setStringFormat(new PdfStringFormat(PdfTextAlignment.Center));
            table.getStyle().getAlternateStyle().setBackgroundBrush(PdfBrushes.getLightGray());
            table.getStyle().getAlternateStyle().setFont(tableFont);
            table.getStyle().getAlternateStyle().setStringFormat(new PdfStringFormat(PdfTextAlignment.Center));

            //set the header style
            table.getStyle().setHeaderSource(PdfHeaderSource.Column_Captions);
            table.getStyle().getHeaderStyle().setBackgroundBrush(PdfBrushes.getPurple());
            table.getStyle().getHeaderStyle().setFont(headerFont);
            table.getStyle().getHeaderStyle().setTextBrush(PdfBrushes.getWhite());
            table.getStyle().getHeaderStyle().setStringFormat(new PdfStringFormat(PdfTextAlignment.Center));

            //show header at every page
            table.getStyle().setShowHeader(true);
            table.getStyle().setCellPadding(2);

            table.getStyle().setHeaderSource(PdfHeaderSource.Rows);

            table.getStyle().setHeaderRowCount(1);

            table.getStyle().setShowHeader(true);

            table.setDataSource(dataSource);

            //Draw table to the page

            PdfLayoutResult result = table.draw(page, new Point2D.Float(0, y));

            y = y + (float) result.getBounds().getHeight() + 5;

            //Draw string below table

            PdfBrush brush2 = PdfBrushes.getGray();

            PdfTrueTypeFont font2 = new PdfTrueTypeFont(new Font("Arial", 0, 9));

            page.getCanvas().drawString(String.format("* %1$s countries in the list.", data.length - 1), font2, brush2, 5, y);

            //Save the file
        }
        doc.saveToFile("output/Table.pdf");

    }

}
