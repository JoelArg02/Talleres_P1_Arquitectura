package ec.edu.arguello.mavenproject2.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class AccesoDB {

    private AccesoDB() {
    }

    public static Connection getConnection() throws SQLException {
        Connection cn = null;
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/eurekabank";
            String user = "eureka";
            String pass = "root";
            Class.forName(driver).newInstance();
            cn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw new SQLException("ERROR, no se encuentra el driver.");
        } catch (Exception e) {
            throw new SQLException("ERROR, no se tiene acceso al servidor.");
        }
        return cn;
    }
}
