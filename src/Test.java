
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
        CustomerArrayList<Customer> orderMembersList = database.getCustomerList();
        String msg = String.format("%s\n", BORDER);
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   #   ORDER MEMBERS DETAILS   #\n");
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        msg += String.format("%s\n", BORDER);
        System.out.print(msg);
        System.out.printf("%s %20s %28s %27s %50s %67s\n", "No.", "AccountID", "Buyer Name", "Gender", "Address", "Phone Number");
        System.out.printf("%s %20s %28s %27s %77s %40s\n", "---", "---------", "----------", "------", "--------------------------------------------------------", "------------");

        for (int position = 1; position <= orderMembersList.getMemberAmount(); position++) {
            Customer member = orderMembersList.getMember(position);
            System.out.printf("%d. %20s %28s %25c %80s %40s\n", position, member.getId(), member.getName(), member.getGender(), member.getAddress(), member.getPhoneNumber());
            System.out.printf("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }

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
