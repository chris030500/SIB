package ahorrosbienestar.sib.std.fichero.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.stereotype.Service;

@Service
public class PdfBoxKeyWordPosition extends PDFTextStripper {

    private static final Logger logger = Logger.getLogger(PdfBoxKeyWordPosition.class);

    private char[] key;
    private String pdfPath;
    private List<float[]> list = new ArrayList<float[]>();
    private List<float[]> pagelist = new ArrayList<float[]>();

    public PdfBoxKeyWordPosition() throws IOException {
        super();
    }

    public PdfBoxKeyWordPosition(String keyWords, String pdfPath) throws IOException {
        super();
        super.setSortByPosition(true);
        this.pdfPath = pdfPath;

        char[] key = new char[keyWords.length()];
        for (int i = 0; i < keyWords.length(); i++) {
            key[i] = keyWords.charAt(i);
        }
        this.key = key;
    }

    public List<float[]> getCoordinate() throws IOException {
        try {
            document = PDDocument.load(new File(pdfPath));
            int pages = document.getNumberOfPages();

            for (int i = 1; i <= pages; i++) {
                pagelist.clear();
                super.setSortByPosition(true);
                super.setStartPage(i);
                super.setEndPage(i);

                Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
                super.writeText(document, dummy);

                for (float[] li : pagelist) {
                    li[2] = i;
                }

                list.addAll(pagelist);
            }

            return list;

        } catch (Exception e) {
            logger.error("Error al obtener coordenadas del PDF.", e);
        } finally {
            if (document != null) {
                document.close();
            }
        }

        return list;
    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
        for (int i = 0; i < textPositions.size(); i++) {

            String str = textPositions.get(i).getUnicode();

            if (str.equals(key[0] + "")) {
                int count = 0;

                for (int j = 1; j < key.length; j++) {
                    String s = "";
                    try {
                        s = textPositions.get(i + j).getUnicode();
                    } catch (Exception e) {
                        logger.debug("No fue posible leer la posición del texto.", e);
                    }

                    if (s.equals(key[j] + "")) {
                        count++;
                    }
                }

                if (count == key.length - 1) {
                    float[] idx = new float[3];
                    idx[0] = textPositions.get(i).getY() - 2 * textPositions.get(i).getHeight();
                    idx[1] = textPositions.get(i).getX();
                    pagelist.add(idx);
                }
            }
        }
    }
}