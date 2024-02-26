/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package method;
import form.form_buku;
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
 * @author 04
 */
public class menthod_buku {
    ResultSet rs = null;
    Statement stat = null;
    Connection con = new apk_perpus.Apk_perpus().getCon();
    private String sql ="";
    public void bersih(form_buku b){
        b.kdbuku.setText(null);
         b.penerbit.setText(null);
          b.pengarang.setText(null);
           b.tahun.setText(null);
            b.buku.setText(null);
             b.cari.setText(null);
    }
    public void kunci(form_buku k){
          k.kdbuku.setEnabled(false);
        k.buku.setEnabled(false);
        k.penerbit.setEnabled(false);
         k.tahun.setEnabled(false);
        k.cari.setEnabled(false);
        k.pengarang.setEnabled(false);
        k.simpann.setEnabled(false);
        k.ubahh.setEnabled(false);
        k.hapus.setEnabled(false);
           
    }
     public void buka_kunci(form_buku bk){
         bk.kdbuku.setEnabled(true);
        bk.buku.setEnabled(true);
        bk.penerbit.setEnabled(true);
        bk.tahun.setEnabled(true);
        bk.cari.setEnabled(true);
        bk.pengarang.setEnabled(true);
        bk.ubahh.setEnabled(true);
        bk.hapus.setEnabled(true);
      
         bk.simpann.setEnabled(true);
     }
     public void tampil_data(form_buku t){
        DefaultTableModel tbpeminjam=new DefaultTableModel();
        tbpeminjam.addColumn("KD BUKU");
        tbpeminjam.addColumn("JUDUL BUKU");
          tbpeminjam.addColumn("PENGARANG");
           tbpeminjam.addColumn("TAHUN TERBIT");
         tbpeminjam.addColumn("PENERBIT");
         try {
             String sql = "select*from tbbuku";
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
      public void klik_table(form_buku k){
        int row = k.jTable1.getSelectedRow();
        k.kdbuku.setText(k.jTable1.getModel().getValueAt(row,0).toString());
        k.buku.setText(k.jTable1.getModel().getValueAt(row,1).toString());
        k.tahun.setText(k.jTable1.getModel().getValueAt(row,2).toString());
        k.pengarang.setText(k.jTable1.getModel().getValueAt(row,3).toString());
        k.penerbit.setText(k.jTable1.getModel().getValueAt(row,4).toString());
        k.simpann.setEnabled(true);
        k.ubahh.setEnabled(true);
        k.hapus.setEnabled(true);
        k.tambah.setEnabled(true);
     }
        public void simpan(form_buku s){
        if (s.kdbuku.getText().isEmpty() || s.buku.getText().isEmpty() ||
                s.pengarang.getText().isEmpty() || s.tahun.getText().equals(s) || s.penerbit.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Lengkapi data","warning",JOptionPane.INFORMATION_MESSAGE);
        } else{
            String kdbuku=s.kdbuku.getText();
            String buku=s.buku.getText();
            String pengarang=s.pengarang.getText();
            String tahun=s.tahun.getText();
            String penerbit=s.penerbit.getText();
            try {
                sql=("insert into tbbuku( kd_buku,judul_buku,pengarang, tahun_terbit, penerbit)value"
                        + "('" + kdbuku + "','" + buku + "','" + pengarang + "','" + 
                        tahun + "','" + penerbit + "')");
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
     public void ubah(form_buku u){
    try{
        sql = "update tbbuku set buku='"+u.buku.getText()+"',tahun_terbit='"+u.tahun.getText()+"',penerbit='"+u.penerbit.getText()+"',pengarang='"+u.pengarang.getText()+"' where kdbuku='"+u.kdbuku.getText()+"'";
        stat = con.createStatement();
        stat.executeUpdate(sql);
        JOptionPane.showMessageDialog(null, "Berhasil Diubah");
        bersih(u);
        kunci(u);
        tampil_data(u);
    }catch(Exception e){
        e.printStackTrace();
    }
}
     public void hapus(form_buku h){
  try{
      int pesan =  JOptionPane.showConfirmDialog(null, "Yakin ingin hapus?","Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      
      if(pesan == JOptionPane.YES_OPTION){
          Statement stat = con.createStatement();
          stat.executeUpdate("delete*from tb_barang where kd_barang='"+h.kdbuku.getText()+"'");
      }
      bersih(h);
      tampil_data(h);
  }catch(Exception e){
      JOptionPane.showMessageDialog(null, "Gagal"+e);
  }
}
     public void cari(form_buku c){
    DefaultTableModel tbbarang = new DefaultTableModel();
    tbbarang.addColumn("kd_buku");
    tbbarang.addColumn("judul_buku");
    tbbarang.addColumn("pengarang");
    tbbarang.addColumn(" tahun_terbit");
    tbbarang.addColumn("penerbit");
         try {  String cari = c.cari.getText();
      stat = con.createStatement();
      sql = "select*from tbbuku like'%"+cari+"%'";
      ResultSet res =  stat.executeQuery(sql);
      while(res.next()){
          tbbarang.addRow(new Object[]{
              res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5)
          });
      }
      c.jTable1.setModel(tbbarang);
             
         } catch (Exception e) {
             //      JOptionPane.showMessageDialog(null, "Data gagal tampil");
      e.printStackTrace();
         
         }
     }
}

    



