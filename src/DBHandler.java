import org.mariadb.jdbc.Driver;


import  java.sql.*;
import java.util.ArrayList;


/*
    Currently i am not so good in programming patterns,
    but it is Singleton pattern i think
 */
public class DBHandler {
    private static DBHandler instance = null;
    private Connection connection;

    public static synchronized DBHandler getInstance() throws SQLException{
        if (instance == null)
            instance = new DBHandler();
        return instance;
    }

    private DBHandler() throws SQLException {
        DriverManager.registerDriver(new Driver());
        //once upon a time i put all my data in local instace of MariaDB
        //next commented string for this connection
        //this.connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/market","root","");
        //This string is for connection to SQLite base in file c:\SQLite\market.db
        this.connection = DriverManager.getConnection("jdbc:sqlite:C:/SQLite/market.db");
    }

    public ArrayList<Car> getAllCars(){
        try(Statement statement = this.connection.createStatement()){
            ArrayList<Car> cars = new ArrayList<Car>();
            ResultSet resultset  = statement.executeQuery("SELECT * FROM cars");
            while (resultset.next()){
                cars.add(new Car(resultset.getInt("id"),resultset.getString("mark"),resultset.getString("model"),resultset.getInt("year"),
                        resultset.getString("color"),resultset.getFloat("price"),resultset.getString("regnum")));
            }
            return cars;
        } catch (SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<Car> getMark(String mark) {
        ArrayList<Car> cars = new ArrayList<Car>();

        try (PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM cars WHERE mark = ?")){
            preparedStatement.setObject(1,mark);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(new Car(resultSet.getInt("id"),resultSet.getString("mark"),resultSet.getString("model"),resultSet.getInt("year"),
                        resultSet.getString("color"),resultSet.getFloat("price"),resultSet.getString("regnum")));
            }
            return cars;
        } catch (SQLException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Car> getExpModel(String model, int expTime){
      ArrayList<Car> cars = new ArrayList<Car>();
      try(PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM cars WHERE model = ? AND year < ?")){
          preparedStatement.setObject(1, model);
          preparedStatement.setObject(2,expTime);
          ResultSet resultSet = preparedStatement.executeQuery();
          while (resultSet.next()) {
              cars.add(new Car(resultSet.getInt("id"),resultSet.getString("mark"),resultSet.getString("model"),resultSet.getInt("year"),
                      resultSet.getString("color"),resultSet.getFloat("price"),resultSet.getString("regnum")));
          }
          return cars;
      } catch (SQLException ex){
          ex.printStackTrace();
          return null;
      }


    }

    public ArrayList<Car> getYearPrice(int year, float price){
        ArrayList<Car> cars = new ArrayList<Car>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM cars WHERE year = ? AND price > ?")){
            preparedStatement.setObject(1,year);
            preparedStatement.setObject(2,price);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(new Car(resultSet.getInt("id"),resultSet.getString("mark"),resultSet.getString("model"),resultSet.getInt("year"),
                        resultSet.getString("color"),resultSet.getFloat("price"),resultSet.getString("regnum")));
            }
            return cars;
        }catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }


}
