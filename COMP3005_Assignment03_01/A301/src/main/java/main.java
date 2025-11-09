import java.sql.*;

public class main {

    // assign info of the database
    static String url =  "jdbc:postgresql://localhost:5432/A3Q1";
    static String user = "postgres";
    static String password = "kutchipatchi";

    public static void main(String[] args) {

        try {

            Class.forName("org.postgresql.Driver");

            // display the current students in the database
            getAllStudents();
            System.out.println(); // divider

            // add students into the database
            //addStudent("Yu", "Narukami", "yu.narukami@example.com", java.sql.Date.valueOf("2011-04-12"));
            //addStudent("Makoto", "Yuki", "makoto.yuki@example.com", java.sql.Date.valueOf("2009-04-12"));
            //addStudent("Gustave", "Gust", "gustave.gustave@example.com", java.sql.Date.valueOf("2033-03-03"));
            //getAllStudents(); // display the current students

            // update the emails of the students (yu narukami and gustave gust)
            //updateStudentEmail(4, "yu.naru@example.com");
            //updateStudentEmail(6, "gustave.gust@example.com");
            //getAllStudents(); // display the students

            // delete students (makoto yuki and gustave gust)
            deleteStudent(4);
            deleteStudent(5);
            deleteStudent(6);
            getAllStudents(); // display the students

            System.out.println("--- End ---");

        }
        catch (Exception e) { System.out.println(e); }

    }

    // function to display all the students in the database
    public static void getAllStudents() throws SQLException {

        System.out.println("--- Printing Current Students ---");

        // create the connection object
        Connection connection = DriverManager.getConnection(url, user, password);

        // create the statement and execute the select statement
        Statement statement = connection.createStatement();
        statement.executeQuery("SELECT * FROM students");

        // store the result of the statement into a variable
        ResultSet resultSet = statement.getResultSet();

        // print out the student's information for each row
        while(resultSet.next()) {
            System.out.print(resultSet.getString("student_id") + " \t");
            System.out.print(resultSet.getString("first_name") + " \t");
            System.out.print(resultSet.getString("last_name") + " \t");
            System.out.print(resultSet.getString("email") + " \t");
            System.out.println(resultSet.getString("enrollment_date"));
        }

        // close connections
        resultSet.close();
        statement.close();
        connection.close();

    }

    // function to add a student to the database
    public static void addStudent(String fn, String ln, String e, java.sql.Date ed) throws SQLException {

        System.out.println("--- Adding New Student ---");

        // create the connection
        Connection connection = DriverManager.getConnection(url, user, password);

        // store the sql query to insert a student into a variable
        String sql = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";

        // create the prepared statement with the sql query
        PreparedStatement queryStatement = connection.prepareStatement(sql);

        // set the values of the question marks to the incoming variables
        queryStatement.setString(1, fn);
        queryStatement.setString(2, ln);
        queryStatement.setString(3, e);
        queryStatement.setDate(4, ed);

        // execute the update
        queryStatement.executeUpdate();

        System.out.println("Student Added Successfully!");

        // close connection
        connection.close();

    }

    // function to update a student email
    public static void updateStudentEmail(int id, String e) throws SQLException {

        System.out.println("--- Updating Student Email ---");

        // create the connection
        Connection connection = DriverManager.getConnection(url, user, password);

        // store the sql query to update a student's email into a variable
        String sql = "UPDATE students SET email = ? WHERE student_id = ?";

        // create the prepared statement with the sql query
        PreparedStatement queryStatement = connection.prepareStatement(sql);

        // set the values of the question marks to the incoming variables
        queryStatement.setString(1, e);
        queryStatement.setInt(2, id);

        // execute the update
        queryStatement.executeUpdate();

        System.out.println("Student Email Updated Successfully!");

        // close connection
        connection.close();

    }

    public static void deleteStudent(int id) throws SQLException {

        System.out.println("--- Deleting Student ---");

        // create the connection
        Connection connection = DriverManager.getConnection(url, user, password);

        // store the sql query to delete a student with a specific id into a variable
        String sql = "DELETE FROM students WHERE student_id = ?";

        // create the prepared statement with the sql query
        PreparedStatement queryStatement = connection.prepareStatement(sql);

        // set the value of the question mark to the incoming variable
        queryStatement.setInt(1, id);

        // execute the update
        queryStatement.executeUpdate();

        System.out.println("Student Deleted Successfully!");

        // close the connection
        connection.close();

    }

}
