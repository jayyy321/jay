/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apk_perpus;
import form.form_buku;
import form.form_login;
import form.form_user;
import form.form_peminjam;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.Statement;



/**
 *
 * @author 09
 */
public class Apk_perpus {
    public Connection con;
    public Statement stat;
     Connection connect=null;
     
     public Connection getCon(){
         try {
             connect=DriverManager.getConnection("jdbc:mysql://localhost/dbperpustakaan","root","");
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"gagal koneksi");
         }
         return connect;
     }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      new form.form_buku().setVisible(true);
      new form_peminjam().setVisible(true);
new form_user().setVisible(true);
new form_login().setVisible(true);// TODO code application logic here
    }
    
}
