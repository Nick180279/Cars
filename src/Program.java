import java.sql.*;

public class Program {
    public static Connection 	conn;
    public static Statement 	stmt;
    public static ResultSet 	rs;

    public static void main (String [] args) throws Exception {
        try
        {
            Class.forName("org.sqlite.JDBC");
            //conn = DriverManager.getConnection("jdbc:sqlite:c:/sqlite/users.db");
            conn = DriverManager.getConnection("jdbc:sqlite:users.db");
            System.out.println("Connected");
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO person (fname, lname,killindex,email,phone) VALUES ('Ostap','Zaytsev',42,'belyak@gmail.com','+79374158792')");
            rs = stmt.executeQuery("SELECT * FROM person");
            while (rs.next()) {
                System.out.println(rs.getInt("id")+ "\t" +rs.getString("fname")+"\t"+rs.getString("phone"));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            rs.close();
            stmt.close();
            conn.close();
        }
    }
}
