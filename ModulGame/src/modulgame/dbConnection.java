/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulgame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Fauzan
 */
public class dbConnection {
    public static Connection con;
    public static Statement stm;
    
    public void connect(){//untuk membuka koneksi ke database
        try {
            String url ="jdbc:mysql://localhost/db_gamepbo";
            String user="root";
            String pass="";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,user,pass);
            stm = con.createStatement();
            System.out.println("koneksi berhasil;");
        } catch (Exception e) {
            System.err.println("koneksi gagal" +e.getMessage());
        }
    }
    
    public DefaultTableModel readTable(){
        
        DefaultTableModel dataTabel = null;
        try{
            Object[] column = {"No", "Username", "Score", "Time"};
            connect();
            dataTabel = new DefaultTableModel(null, column);
            String sql = "Select * from highscore order by Score DESC";
            ResultSet res = stm.executeQuery(sql);
            int no = 1;
            while(res.next()){
                Object[] hasil = new Object[4];
                hasil[0] = no;
                hasil[1] = res.getString("Username");
                hasil[2] = res.getString("Score");
                hasil[3] = res.getString("Time");

                no++;
                dataTabel.addRow(hasil);
            }
        }catch(Exception e){
            System.err.println("Read gagal " +e.getMessage());
        }
        
        return dataTabel;
    }
   
    
    public void insertScore(String username,int score, int time){
        connect();
        String sql = "Select * from highscore";
        PreparedStatement PreparedStmt = null;
        int checkUsername = 0;
        try{    
            ResultSet res = stm.executeQuery(sql);
            while(res.next()){
                if(res.getString("Username").equals(username)){
                       checkUsername = 1;
                    if(score > res.getInt("Score")){
                       sql = "UPDATE `highscore` SET `Score` = ?, `Time` = ? WHERE `highscore`.`Username` = ? ";
                       PreparedStmt = con.prepareStatement(sql);
                       PreparedStmt.setInt(1,score);                       
                       PreparedStmt.setInt(2,time);
                       PreparedStmt.setString(3,username);
                       PreparedStmt.executeUpdate();
                       break;
                    }else{
                       break;                    
                    }
                }
            }
            if(checkUsername == 0){
                sql = "INSERT INTO `highscore` (`id`, `Username`, `Score`, `Time`) VALUES (NULL, ?, ?, ?)";
                PreparedStmt = con.prepareStatement(sql);
                PreparedStmt.setString(1,username);
                PreparedStmt.setInt(2,score);
                PreparedStmt.setInt(3,time);
                PreparedStmt.executeUpdate();
            }
        }catch(Exception e){
            System.err.println("Read gagal " +e.getMessage());
        }
    }
}
