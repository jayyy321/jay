/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package method;
import form.form_login;
import form.menu_utama;
import method.method_user;
import method.method_login;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import apk_perpus.Apk_perpus;
/**
 *
 * @author 09
 */
public class method_login {
    Connection con=new apk_perpus.Apk_perpus().getCon();
    Statement stat=null;
    ResultSet res=null;
    String sql;
    
    public void bersih(form_login b){
        b.jtuser.setText(null);
        b.jppassword.setText(null);
        b.jtuser.requestFocus();
    }
    public void login(form_login l){
        try {
            sql="select*from tbuser where username='" + l.jtuser.getText()+"'";
            Statement stat=this.con.createStatement();
            ResultSet res=stat.executeQuery(sql);
            if (res.next()){
                if(l.jppassword.getText().equals( res.getString("password"))){
                    new menu_utama().setVisible(true);
                    l.dispose();
                } else {
                    int pesan = JOptionPane.showConfirmDialog(null,"password atau username salah,ulangi?'","konfirmasi",0,3);
                    if (pesan == 0){
                        l.jtuser.setText("");
                        l.jtuser.requestFocus();
                    } else if(pesan==1){
                        System.exit(0);
                    }
                        }
            } else {
                int pesan =JOptionPane.showConfirmDialog(null,"Password atau username salah,ulangi?","konfirmasi",0,3);
                if (pesan == 0){
                    l.jtuser.setText("");
                    l.jppassword.setText("");
                    l.jtuser.requestFocus();
                } else if(pesan== 1){
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Ada Kesalahan!!");
            l.jtuser.setText("");
            l.jppassword.setText("");
            l.jtuser.requestFocus();
        }
    }
}
