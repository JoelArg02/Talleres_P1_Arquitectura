package Test;

import ec.edu.arguello.mavenproject2.db.AccesoDB;
import java.sql.Connection;

public class Test {
    public static void main(String[] args) {
        try {
            Connection cn = AccesoDB.getConnection();
            System.out.println("Ok");
            cn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
