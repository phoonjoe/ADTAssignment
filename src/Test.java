
import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Joe Phoon
 */
public class Test {

    private static final String DATE_FORMAT = ("dd-MM-yyyy HH:mm");
    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
    private static final String line = "==========================================================";
    private static final String BORDER = "========================================================================================================================================================================================================";

    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        console.printf("Testing password%n");
        char[] passwordArray = console.readPassword("Enter your secret password: ");
        console.printf("Password entered was: %s%n", new String(passwordArray));
    }

    public static void calculateRemainTime(Date createDate, Date endDate) {

        long diffInMillies = endDate.getTime() - createDate.getTime();
        long diffence_in_minute = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        System.out.println(diffence_in_minute);

    }

    public static String getCurrentDateInFormat(Date creatDate) {

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String dateInString = format.format(creatDate.getTime());

        return dateInString;
    }
}
