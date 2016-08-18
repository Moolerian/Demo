package fx.sample;

import java.sql.*;

public class SQLiteJDBC
{

    public static void main( String args[] )
    {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/resource/Demo");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM shape;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("displayName");
                String image  = rs.getString("image");


                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "IMAGE = " + image);


                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
}