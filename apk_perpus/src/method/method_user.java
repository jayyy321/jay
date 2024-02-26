/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package method;
import form.form_user;
import form.form_login;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import apk_perpus.Apk_perpus;
/**
 *
 * @author 09
 */
public class method_user {
    public ResultSet res=null;
    public Statement stat=null;
    private String sql=null;
    Connection con=new apk_perpus.Apk_perpus().getCon();
    
    public void bersih(form_user b){
        b.jtkduser.setText(null);
        b.jtnama.setText(null);
        b.jtusername.setText(null);
        b.jppassword.setText(null);
        b.jtcari.setText(null);  
    }
    public void kunci(form_user k){
        k.jtkduser.setEnabled(false);
        k.jtnama.setEnabled(false);
        k.jckelamin.setEnabled(false);
        k.jtusername.setEnabled(false);
        k.jppassword.setEnabled(false);
        k.jbsimpan.setEnabled(false);
        k.jbubah.setEnabled(false);
        k.jbhapus.setEnabled(false);
        k.jbtambah.setEnabled(false);
    }
    public void buka_kunci(form_user bk){
        bk.jtkduser.setEnabled(true);
        bk.jtnama.setEnabled(true);
        bk.jckelamin.setEnabled(true);
        bk.jtusername.setEnabled(true);
        bk.jppassword.setEnabled(true);
        bk.jbsimpan.setEnabled(true);
        bk.jbubah.setEnabled(true);
        bk.jbhapus.setEnabled(true);
        bk.jbtambah.setEnabled(true);
    }
    public void simpan(form_user s){
        if (s.jtkduser.getText().isEmpty() || s.jtnama.getText().isEmpty() ||
                s.jckelamin.getSelectedItem().equals(s) || s.jtusername.getText().isEmpty() || s.jppassword.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Lengkapi data","warning",JOptionPane.INFORMATION_MESSAGE);
        }else{
            String kd_user=s.jtkduser.getText();
            String nama_lengkap=s.jtnama.getText();
            String jenis_kelamin=(String)s.jckelamin.getSelectedItem();
            String username=s.jtusername.getText();
            String password=s.jppassword.getText();
            try {
                sql=("insert into tbuser(kd_user,nama_lengkap,jenis_klamin,username,password)value"
                        + "('" + kd_user + "','" + nama_lengkap + "','" + jenis_kelamin + "','" + 
                        username + "','" + password + "')");
                stat=con.createStatement();
                stat.execute(sql);
                bersih(s);
                JOptionPane.showMessageDialog(null,"Data Tersimpan");
                tampil_data(s);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Proses penyimpanan gagal atau cek koneksi","error",
                        JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
            }
            }
    }
    public void ubah(form_user u){
        try {
            Statement stat=con.createStatement();
            String sql="update tbuser set nama_lengkap='" + u.jtnama.getText()
                    + "'," + "jenis_kelamin='" + u.jckelamin.getSelectedItem()
                    + "'," + "username='" + u.jtusername.getText()
                    + "'," + "password='" + u.jppassword.getText()
                    + "'" + "where kd_user ='" + u.jtkduser.getText()+ "'";
            stat.executeUpdate(sql);
            bersih(u);
            kunci(u);
            buka_kunci(u);
            JOptionPane.showMessageDialog(null,"data telah diubah");
            tampil_data(u);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"data gagal diubah",
                    "error",JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
        }
    }
    public void tampil_data(form_user t){
        DefaultTableModel tbuser=new DefaultTableModel();
        tbuser.addColumn("kd_user");
        tbuser.addColumn("nama_lengkap");
          tbuser.addColumn("jenis_kelamin");
           tbuser.addColumn("username");
            tbuser.addColumn("password");
            try {
             String sql = "select*from tbuser";
            Statement stat=con.createStatement();
            ResultSet res=stat.executeQuery(sql);
            while (res.next()){
                tbuser.addRow(new Object[]{
                    res.getString(1),res.getString(2),
                    res.getString(3),res.getString(4),
                    res.getString(5)});
                }
            t.jTable1.setModel(tbuser);
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"data gagal tampil");
        }
        }
    public void klik_table(form_user k){
        int row = k.jTable1.getSelectedRow();
        k.jtkduser.setText(k.jTable1.getModel().getValueAt(row,0).toString());
        k.jtnama.setText(k.jTable1.getModel().getValueAt(row,1).toString());
        k.jckelamin.setSelectedItem(k.jTable1.getModel().getValueAt(row,2).toString());
        k.jtusername.setText(k.jTable1.getModel().getValueAt(row,3).toString());
        k.jppassword.setText(k.jTable1.getModel().getValueAt(row,4).toString());
        k.jbsimpan.setEnabled(true);
        k.jbubah.setEnabled(true);
        k.jbhapus.setEnabled(true);
        k.jbtambah.setEnabled(true);
    }
    public void hapus(form_user h){
        try {
            int pesan=JOptionPane.showConfirmDialog(null,"yakin ingin dihapus??",
             "konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if (pesan == JOptionPane.YES_OPTION){
                Statement stat=con.createStatement();
                stat.executeUpdate("delete from tbuser where kd_user='" + h.jtkduser.getText()+
                "'");
            }
            bersih(h);
            tampil_data(h);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"gagal"+ e);
        }
    }
    public void cari_data(form_user cd){
        DefaultTableModel tbuser= new DefaultTableModel();
        tbuser.addColumn("kd_user");
        tbuser.addColumn("nama_lengkap");
        tbuser.addColumn("jenis_kelamin");
        tbuser.addColumn("username");
        tbuser.addColumn("password");
        try {
            String cari=cd.jtcari.getText();
            Statement stat=this.con.createStatement();
            String sql="select*from tbuser where kd_user like '%" + cari + "%'";
            ResultSet res=stat.executeQuery(sql);
            while(res.next()){
                tbuser.addRow(new Object[]{
                    res.getString(1),res.getString(2),
                    res.getString(3),res.getString(4),
                    res.getString(5)});
                
            }
            cd.jTable1.setModel(tbuser);
        } catch (Exception e) {
        }
    }
}
