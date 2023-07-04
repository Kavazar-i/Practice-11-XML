import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ResultHandler extends DefaultHandler {

    private final List<Result> results = new ArrayList<>();
    private String currentLogin;
    private String currentTestName;
    private String currentDate;
    private String currentMark;

    private StringBuilder dataBuffer;

    public List<Result> getResults() {
        return results;
    }

    public void parseXML(String xmlFilePath) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(xmlFilePath, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        if (qName.equalsIgnoreCase("login")) {
            dataBuffer = new StringBuilder();
        }

        currentTestName = attributes.getValue("name");
        currentDate = attributes.getValue("date");
        currentMark = attributes.getValue("mark");
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (dataBuffer != null) {
            dataBuffer.append(ch, start, length);
            currentLogin = dataBuffer.toString().trim();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (currentLogin != null && currentTestName != null && currentDate != null && currentMark != null) {
                Date date = new Date(format.parse(currentDate).getTime());
                Result result = new Result(currentLogin, currentTestName, date, currentMark);
                results.add(result);
            }

            currentLogin = null;
            currentTestName = null;
            currentDate = null;
            currentMark = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ResultHandler handler = new ResultHandler();
        handler.parseXML("src/main/java/results.xml");

        List<Result> results = handler.getResults();

        for (Result result : results) {
            System.out.println(result);
        }
    }
}
