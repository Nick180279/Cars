import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
public class Runner {
    public static void main (String [] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Car> market;
        DBHandler dbh = null;
        char ch = '1';
        String mark;
        String model;
        int years;
        float price;

        System.out.println("Hello and welcome.");
        System.out.println("Here we have some exercise from EPAM book");
        System.out.println("We have some database with cars");
        System.out.println("Ya, it is mad indie-code");
        System.out.println("My little Sqlite base lays in c:\\sqlite\\market.db");


        do {

            System.out.println("Enter your choice :");
            System.out.println("l - load and show all base");
            System.out.println("m - show you all cars with specified Mark");
            System.out.println("e - show you all cars and specified Model which usage time more than entered");
            System.out.println("y - show you all cars with specified Production year and greater price");
            System.out.println("q - for quit");
            // we read string and get first char. And convert it to lowercase. Just for fun
            ch = scan.next().toLowerCase().charAt(0);
            switch (ch){
                case 'l':
                    System.out.println("Load base");
                    try {
                        dbh = DBHandler.getInstance();
                        market = dbh.getAllCars();
                        for (Car car : market)
                            System.out.println(car);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case 'e':
                    System.out.println("Enter model :");
                    model = scan.next();
                    model = model.substring(0,1).toUpperCase() + model.substring(1).toLowerCase();
                    do {
                        System.out.println("Enter number of usage years : ");
                        years = scan.nextInt();
                    }while ((years < 0) || (years > 30));

                    try {
                        dbh = DBHandler.getInstance();
                        // We want to find cars which usage time more than "years"
                        // Than if we subtract from current year amount of usage years
                        // result number must be less than production year of car in db table
                        market = dbh.getExpModel(model,LocalDate.now().getYear() - years);
                        if (!market.isEmpty()) {
                            for(Car car: market)
                                System.out.println(car);
                        }
                        else System.out.println("Nothing to show for model " + model + " and usage time " + years);
                    } catch (SQLException ex){
                        ex.printStackTrace();
                    }
                    break;
                case 'm':
                    System.out.println("We want to find in database all cars with pointed mark");
                    try {
                        System.out.println("Enter mark : ");
                        //Here i try to convert random input into string with first upper letter, and low other letters
                        mark = scan.next();
                        mark = mark.substring(0,1).toUpperCase() + mark.substring(1).toLowerCase();
                        //may be there are some other variants but i do not know them
                        dbh = DBHandler.getInstance();
                        market = dbh.getMark(mark);
                        if (!market.isEmpty()) {
                            for (Car car : market)
                                System.out.println(car);
                        } else System.out.println("I can not find cars with mark " + mark);
                    } catch (SQLException ex){
                        ex.printStackTrace();
                    }
                    break;
                case 'y':
                    System.out.println("We want to find cars with pointed production year and price which more than entered");
                    System.out.println("Please, enter production year of car :");
                    years = scan.nextInt();
                    System.out.println("Now enter minimal price of car :");
                    price = scan.nextFloat();
                    try {
                        dbh = DBHandler.getInstance();
                        market = dbh.getYearPrice(years, price);
                        if (!market.isEmpty()){
                            for (Car car : market)
                                System.out.println(car);
                        } else System.out.println("I can not find cars with production year " + years + " and price more than " + price);
                    } catch (SQLException ex){
                        ex.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("I can not show you anything. Try something else.");
            }
        } while (ch != 'q');
        System.out.println("Work ends. Bye-bye.");
    }
}
