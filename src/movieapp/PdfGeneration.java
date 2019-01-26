package movieapp;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javafx.collections.ObservableList;
import com.itextpdf.text.pdf.PdfPTable;

public class PdfGeneration {
    
    public void pdfGeneration (String fileName, ObservableList<Movie> data) {
        Document document = new Document();
        
        try {
//            Logó
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();
            com.itextpdf.text.Image image1 = com.itextpdf.text.Image.getInstance(getClass().getResource("/logo.jpg"));
            image1.scaleToFit(400, 172);
            image1.setAbsolutePosition(160f, 650f);
            document.add(image1);
            
//            document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n" + text, FontFactory.getFont("betutipus", BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
        
              
            document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n"));
//            Táblázat
              float[] columnWidths = {1, 5, 2, 2, 2};
              PdfPTable table = new PdfPTable(columnWidths);
              table.setWidthPercentage(100);
              PdfPCell cell = new PdfPCell(new Phrase("Megnézett filmek"));
              cell.setBackgroundColor(GrayColor.GRAYWHITE);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER);
              cell.setColspan(5);
              table.addCell(cell);
              
              table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
              table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
              table.addCell("Sorsz.");
              table.addCell("Cím");
              table.addCell("Hossz");
              table.addCell("Nyelv");
              table.addCell("Dátum");
              table.setHeaderRows(1);
              
              table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
              table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
              
              for (int c = 1; c<=data.size();c++) {
                  Movie actMovie = data.get(c-1);
                  table.addCell(String.valueOf(c));
                  table.addCell(actMovie.getTitle());
                  table.addCell(actMovie.getLength());
                  table.addCell(actMovie.getLanguage());
                  table.addCell(actMovie.getDate());
              }
              
              document.add(table);

//            Aláírás
            Chunk signature = new Chunk("\n\n Generálva a MovieApp segítségével.");
//            c.setBackground(BaseColor.BLUE);
            Paragraph base = new Paragraph(signature);
            document.add(base);
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }
}

