package bloodbank;

import java.awt.HeadlessException;
import java.sql.*;
import javax.swing.*;

public class DatabaseConnection {
    
     Connection conn;
    public static Connection db_connect(){
        try{
            Connection conn  =  DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Aashiq\\Documents\\NetBeansProjects\\BloodBank\\ApponFreeBloodDonor.sqlite");
//            JOptionPane.showMessageDialog(null,"Connection Established" );
            return conn;
            
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, e);     
       return null;
        }  
    }
}
