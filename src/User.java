import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {

    private Connection connection;
    private Scanner scanner;

    public User(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void register(){
        scanner.nextLine();
        System.out.print("Full Name: ");
        String full_name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (user_exist(email))
        {
            System.out.println("User Already exists for this email Address!!");
            return;
        }

        String registerQuery = "INSERT INTO user(full_name, email, password) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(registerQuery);
            preparedStatement.setString(1,full_name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);

            // we are adding data into the database so we use preparedStatement.executeUpdate();
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0){
                System.out.println("Register Successfully !!");
            }
            else {
                System.out.println("Failed to Register user !!");
            }


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public String login(){
        scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        String login_query = "SELECT * FROM user WHERE email = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return email;
            }
            else {
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public boolean user_exist(String email){
        String query = "SELECT * FROM user WHERE email = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
