/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package method;
import form.form_peminjam;
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
public class method_peminjam {
    public ResultSet res=null;
    public Statement stat=null;
    private String sql=null;
    Connection con=new apk_perpus.Apk_perpus().getCon();
    
    public void bersih(form_peminjam b){
        b.jtnis.setText(null);
        b.jtnama.setText(null);
        b.jtalamat.setText(null);
       b.jtkelas.setText(null);
       b.jtcari.setText(null);
    }
    public void kunci(form_peminjam k){
        k.jtnis.setEnabled(false);
        k.jtnama.setEnabled(false);
        k.jtalamat.setEnabled(false);
        k.jcjenis.setEnabled(false);
        k.jtkelas.setEnabled(false);
        k.jbsimpan.setEnabled(false);
        k.jbubah.setEnabled(false);
        k.jbhapus.setEnabled(false);
        k.jbtambah.setEnabled(false);
    }
    public void buka_kunci(form_peminjam bk){
        bk.jtnis.setEnabled(true);
        bk.jtnama.setEnabled(true);
        bk.jtalamat.setEnabled(true);
        bk.jcjenis.setEnabled(true);
        bk.jtkelas.setEnabled(true);
        bk.jbsimpan.setEnabled(true);
        bk.jbubah.setEnabled(true);
        bk.jbhapus.setEnabled(true);
        bk.jbtambah.setEnabled(true);
    }
    public void simpan(form_peminjam s){
        if (s.jtnis.getText().isEmpty() || s.jtnama.getText().isEmpty() ||
                s.jtalamat.getText().isEmpty() || s.jcjenis.getSelectedItem().equals(s) || s.jtkelas.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Lengkapi data","warning",JOptionPane.INFORMATION_MESSAGE);
        } else{
            String nis=s.jtnis.getText();
            String nama_lengkap=s.jtnama.getText();
            String alamat=s.jtalamat.getText();
            String jenis_kelamin=(String)s.jcjenis.getSelectedItem();
            String kelas=s.jtkelas.getText();
            try {
                sql=("insert into tbpeminjam(nis,nama_lengkap,alamat,jenis_klamin,kelas)value"
                        + "('" + nis + "','" + nama_lengkap + "','" + alamat + "','" + 
                        jenis_kelamin + "','" + kelas + "')");
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
     public void tampil_data(form_peminjam t){
        DefaultTableModel tbpeminjam=new DefaultTableModel();
        tbpeminjam.addColumn("nis");
        tbpeminjam.addColumn("nama_lengkap");
          tbpeminjam.addColumn("alamat");
           tbpeminjam.addColumn("jenis_kelamin");
         tbpeminjam.addColumn("kelas");
         try {
             String sql = "select*from tbpeminjam";
            Statement stat=con.createStatement();
            ResultSet res=stat.executeQuery(sql);
            while (res.next()){
                tbpeminjam.addRow(new Object[]{
                    res.getString(1),res.getString(2),
                    res.getString(3),res.getString(4),
                    res.getString(5)});
            }
            t.jTable1.setModel(tbpeminjam);
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"data gagal tampil");
         }
         }
     public void ubah(form_peminjam u){
         try {
             Statement stat=con.createStatement();
            String sql="update tbpeminjam set nama_lengkap='" + u.jtnama.getText()
                    + "'," + "alamat='" + u.jtalamat.getText()
                    + "'," + "jenis_klamin='" + u.jcjenis.getSelectedItem()
                    + "'," + "kelas='" + u.jtkelas.getText()
                    + "'" + "where nis ='" + u.jtnis.getText()+ "'";
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
      public void hapus(form_peminjam h){
         try {
            int pesan=JOptionPane.showConfirmDialog(null, "Yakin ingin dihapus??","Konfirmasi",
                    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if (pesan==JOptionPane.YES_OPTION){
            stat=con.createStatement();
            stat.executeUpdate("delete from tbpeminjam where nis='"+h.jtnis.getText()+"'");
                
                bersih(h);
                kunci(h);
                JOptionPane.showMessageDialog(null, "Data Dihapus");
                tampil_data(h);
                h.jbhapus.setEnabled(false);
                h.jbubah.setEnabled(false);
                h.jbtambah.setEnabled(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal","Error",
                    JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
        }
    
}
     public void klik_table(form_peminjam k){
        int row = k.jTable1.getSelectedRow();
        k.jtnis.setText(k.jTable1.getModel().getValueAt(row,0).toString());
        k.jtnama.setText(k.jTable1.getModel().getValueAt(row,1).toString());
        k.jtalamat.setText(k.jTable1.getModel().getValueAt(row,2).toString());
        k.jcjenis.setSelectedItem(k.jTable1.getModel().getValueAt(row,3).toString());
        k.jtkelas.setText(k.jTable1.getModel().getValueAt(row,4).toString());
        k.jbsimpan.setEnabled(true);
        k.jbubah.setEnabled(true);
        k.jbhapus.setEnabled(true);
        k.jbtambah.setEnabled(true);
     }
     public void cari_data(form_peminjam cd){
        DefaultTableModel tbpeminjam=new DefaultTableModel();
        tbpeminjam.addColumn("nis");
        tbpeminjam.addColumn("nama_lengkap");
        tbpeminjam.addColumn("alamat");
        tbpeminjam.addColumn("jenis_kelamin");
        tbpeminjam.addColumn("kelas");
         try {
             String cari=cd.jtcari.getText();
            Statement stat=this.con.createStatement();
            String Sql="select*from tbpeminjam where nis like '%" + cari + "%'";
            ResultSet res=stat.executeQuery(Sql);
            while (res.next()){
                tbpeminjam.addRow(new Object[]{
                    res.getString(1),res.getString(2),
                    res.getString(3),res.getString(4),
                    res.getString(5)});
            }
            cd.jTable1.setModel(tbpeminjam);
         } catch (Exception e) {
         }
     }
}
