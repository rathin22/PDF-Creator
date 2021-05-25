package pdfcreatorpackage;

import java.io.*;
import java.util.*;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

public class App {
   static PdfFont boldFont, italicFont, BoldandItalicFont;

    public static void main(String args[]) throws Exception {
        // Getting input file
        File inputFile= new File(System.getProperty("user.dir")+"\\commands.txt");
        Scanner fileReader= new Scanner(inputFile);
        String content="";

        // Creating a PdfWriter       
        String dest = System.getProperty("user.dir") + "\\sample.pdf";      
        PdfWriter writer = new PdfWriter(dest);           
        
        // Creating a PdfDocument       
        PdfDocument pdf = new PdfDocument(writer);              
        
        // Creating a Document
        Document document = new Document(pdf, PageSize.A4, false);
        document.setTopMargin(50);

        //Creating initial paragraph
        Paragraph para= new Paragraph().setMarginBottom(15);

        //Initializing all font variables
        boldFont= PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        italicFont= PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);
        BoldandItalicFont= PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLDOBLIQUE);
        boolean isBold=false;
        boolean isItalic= false;
        boolean isLarge= false;
        boolean isFill= false;
        int indent= 0;

        while(fileReader.hasNextLine()){
            content= fileReader.nextLine();

            if(content.charAt(0)=='.'){ //If current line is a command
                //Switch case for all the commands
                switch(content){

                    case ".paragraph":
                        addCompletedParagraph(document, para, isFill, indent);
                        para= new Paragraph().setMarginBottom(15);  //New paragraph created
                    break;

                    case ".bold":
                        isBold=true;
                    break;

                    case ".italic":
                        isItalic= true;
                    break;

                    case ".regular":
                        isBold=false;
                        isItalic= false;
                    break;

                    case ".large":
                        isLarge= true;
                    break;
                    
                    case ".normal":
                        isLarge= false;
                    break;

                    case ".fill":
                        isFill= true;
                    break;

                    case ".newline":
                        para.add("\n");
                    break;

                    case ".nofill":
                        if(isFill==true)
                            para.setTextAlignment(TextAlignment.JUSTIFIED);
                        isFill= false;
                    break;

                    default:
                        if(content.substring(1, 7).equals("indent")){
                            addCompletedParagraph(document, para, isFill, indent);
                            para= new Paragraph().setMarginBottom(15);

                            indent= indent + Integer.parseInt(content.substring(8));
                        }
                        else{
                            System.out.println("Something went wrong with " +content);
                        }
                }
            }
            //If current line is not a command
            else {
                //Checking if a line starts with a back slash
                if(content.charAt(0)=='\\'){
                    content= content.substring(1);
                }
                //Creating new text object
                Text text= new Text(content);

                //Modify Text based on the values of the boolean font variables
                modifyText(isBold, isItalic, isLarge, text);

                //Adding text to paragrah
                para.add(text);
            }
        }
        
        //Adding the last paragraph of the file to pdf
        addCompletedParagraph(document, para, isFill, indent);

        //Adding Pagination
        addPagination(pdf, document);

        // Closing the document 
        document.flush();      
        document.close();
        fileReader.close();
        System.out.println("Document Created");
     }

    static void addCompletedParagraph(Document document, Paragraph para, boolean isFill, int indent) {
        if(para.isEmpty()==false){
            para.setMarginLeft(20*indent);    //Sets indentation of paragraph
            if(isFill==true)
              {para.setTextAlignment(TextAlignment.JUSTIFIED); }    //Sets justified alignment if isFill= true

            document.add(para);     //Paragraph is added to the document
        }
    }

    static void modifyText(boolean isBold, boolean isItalic, boolean isLarge, Text text) {
        if(isBold==true){
            text.setFont(boldFont);     //Sets font to bold
        }
        if(isItalic==true){
            if(isBold==true){
                text.setFont(BoldandItalicFont);    //Sets font to bold and italic
            }
            else{
                text.setFont(italicFont);       //Sets font to italic
            }
        }
        if(isLarge==true){
            text.setFontSize(24);       //Sets font size to 24
        }
    }

    static void addPagination(PdfDocument pdf, Document document) {
        int numberOfPages = pdf.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {

        // Display the page numbers at the specified point on the page
        document.showTextAligned(new Paragraph(String.format("Page %s of %s", i, numberOfPages)),
                559, 820, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
        }
    } 
}