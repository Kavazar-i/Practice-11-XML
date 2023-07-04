import java.sql.Date;
import java.text.SimpleDateFormat;

class Result {
    private String login;
    private String test;
    private Date date;
    private Double mark;

    private final static String DELIMITER = ";";

    public Result() {
        login = "";
        test = "";
        date = new Date(0);
        mark = 0.0;
    }

    public Result(String currentLogin, String currentTestName, Date currentDate, String mark) {
        login = currentLogin;
        test = currentTestName;
        date = currentDate;
        this.mark = Double.parseDouble(mark);
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return login + DELIMITER + test + DELIMITER + dateFormat.format(date) + ";" + mark;
    }

    public void setLogin(String string) {
        login = string;
    }

    public void setTest(String string) {
        test = string;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }
}