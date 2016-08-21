package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Shape;

import java.sql.*;

import static util.Constants.DB_CONNECTION;
import static util.Constants.DB_DRIVER;

/**
 * Created by moolerian on 8/18/16.
 */
public class DBUtils {


    public static ObservableList<Shape> getAllShapes() throws SQLException, ClassNotFoundException {
        ObservableList<Shape> shapes = FXCollections.observableArrayList();
        PreparedStatement preparedStatement;

        String selectSQL = " SELECT * FROM shape ";
        preparedStatement = getDBConnection().prepareStatement(selectSQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            Integer id = resultSet.getInt("id");
            String displayName = resultSet.getString("displayName");
            String image = resultSet.getString("image");

            Shape shape = new Shape();
            shape.setId(id);
            shape.setDisplayName(displayName);
            shape.setImage(image);
            shapes.add(shape);

        }

        return shapes;
    }

    private static Connection getDBConnection() throws ClassNotFoundException, SQLException {
        Connection dbConnection;
        Class.forName(DB_DRIVER);
        dbConnection = DriverManager.getConnection(DB_CONNECTION);
        return dbConnection;
    }
}
