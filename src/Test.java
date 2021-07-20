
import DatabaseClass.Database;
import EntityClass.*;
import ImplementationClass.*;
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
        Database database = new Database();
        OrderArrayList<Order> orderArrayList = database.getOrderList();
        OrderArrayList<Order> newArrayList = new OrderArrayList<>(orderArrayList.size());

        Object accountType = new Order();
        System.out.println(accountType.getClass().equals(Order.class));

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
