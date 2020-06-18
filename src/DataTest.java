import java.time.LocalDate;
import java.time.LocalDateTime;


public class DataTest {
    public static void main(String [] args){
        String test = "CaMeLcAsEdSoMeThInGStRanGe";
        String first;
        LocalDate ldt = LocalDate.now();

        LocalDate birthday = LocalDate.of(1979,2,18);

        System.out.println("Current year is :");
        System.out.println(ldt.getYear());
        test = test.toLowerCase();
        System.out.println(test);
        first = test.substring(0,1);
        first = first.toUpperCase();
        test = first + test.substring(1,test.length());
        System.out.println(test);
        System.out.println("My birthday is : ");
        System.out.println(birthday);

    }

}
