/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setani.koneksi;

//import com.sun.jdi.connect.spi.Connection;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author user
 */
public class koneksi {
      private static Connection conn;
    private static final String DB_DRIVER ="com.mysql.jdbc.Driver";
    private static final String DB_NAME ="db_setani";
    private static final String DB_URL ="jdbc:mysql://127.0.0.1:3306/"+ DB_NAME;
    private static final String DB_UNAME ="root";
    private static final String DB_PASS="";
    

    public static Connection bukaKoneksi(){
        if(conn == null){
            try{
            Class.forName("com.mysql.jdbc.Driver");
            conn=(Connection) DriverManager.getConnection(DB_URL, DB_UNAME, DB_PASS);
        } catch(ClassNotFoundException e){
            System.err.format("Class not found");
        } catch(SQLException e){
            System.err.format("SQL State: %s\n%s",e.getSQLState(), e.getMessage());
        } catch(Exception e){
            e.printStackTrace();
        }
        }
        return conn;
    }

    void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
