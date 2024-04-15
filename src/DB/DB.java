package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    private static Connection conn = null;

    public static Connection getConnection(){
        if(conn == null){
            try {
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/estudos_java", "postgres", "3003");
                if(conn != null){
                    System.out.println("Connection established");
                } else {
                    System.out.println("Connection Failed");
                }
            }catch ( Exception e){
                System.out.println(e);
            }
        }
        return conn;
    }

    public static void closeConnection(){
        if(conn != null){
            try {
                conn.close();
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public static void closeStatement(Statement st){
        if(st != null){
            try{
                st.close();
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs){
        if(rs != null){
            try{
                rs.close();
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
}