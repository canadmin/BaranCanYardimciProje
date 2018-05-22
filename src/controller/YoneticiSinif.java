/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.awt.Event;
import static java.util.Collections.list;
import model.Ogretimuyesi;
import java.util.List;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author can yard
 */
public class YoneticiSinif  {
    
    private JTable ElemanTablosu;
    private final static String SORGU_KALIP="from Ogretimuyesi m";
    private  Session session;
        private Vector<String> sutunlar = new Vector<>();
    private Vector<Object> satir;
    private DefaultTableModel model;
    
 
public YoneticiSinif(JTable ElemanTablosu){
        this.ElemanTablosu=ElemanTablosu;
    sutunlar.add("İd");
    sutunlar.add("Sicil No");
    sutunlar.add("Ad Soyad");
    sutunlar.add("Sehir");
    sutunlar.add("Tel no");
    model=(DefaultTableModel)ElemanTablosu.getModel();
    model.setColumnIdentifiers(sutunlar);
   
    
}
public void ac(){
            session = NewHibernateUtil.getSessionFactory().openSession();

}
public void kapat() {
        session.close();
    }
public void ogretimElemaniGetir(String aranan,String filtre){
    String sorgu="";
    if(filtre.equalsIgnoreCase("ad")){
                    sorgu = SORGU_KALIP + " where m.adSoyad like '%" + aranan + "%'";

    }else if(filtre.equalsIgnoreCase("sicilno")){
                    sorgu = SORGU_KALIP + " where m.sicilno like '%" + aranan + "%'";

    }

        session.beginTransaction();

        List uyelist = session.createQuery(sorgu).list();
        session.getTransaction().commit();
        musteriGoster(uyelist);

}

    private void musteriGoster(List<Ogretimuyesi> uyelisList) {
                model.getDataVector().removeAllElements();
 for (Ogretimuyesi eleman : uyelisList){
       satir=new Vector();
            satir.add(eleman.getOgretimuyesiid());
            satir.add(eleman.getSicilno());
            satir.add(eleman.getAdSoyad());
            satir.add(eleman.getSehir());
            satir.add(eleman.getTelno());
            model.addRow(satir);
         
 }
        
        
    }
       
    public  void insert(Ogretimuyesi ex){
        
        session=NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx=session.beginTransaction();
        session.save(ex);
                tx.commit();

    }
    public void Update(String filtre,String yenideger,int id){
 Ogretimuyesi ogretimuyesi = null;
		session=NewHibernateUtil.getSessionFactory().openSession();
			ogretimuyesi = session.get(Ogretimuyesi.class, id);
			System.out.println(ogretimuyesi);
		
                if(filtre.equalsIgnoreCase("sicilno"))
		ogretimuyesi.setSicilno(yenideger);
                else if(filtre.equalsIgnoreCase("adsoyad"))
                ogretimuyesi.setAdSoyad(yenideger);
                else if(filtre.equalsIgnoreCase("sehir"))
                ogretimuyesi.setSehir(yenideger);
                else if(filtre.equalsIgnoreCase("telno"))
                ogretimuyesi.setTelno(yenideger);
			session.beginTransaction();
			Ogretimuyesi ogretimuyesi1 = session.get(Ogretimuyesi.class, id);
			session.merge(ogretimuyesi);
			session.getTransaction().commit();
		
    }
    
      public void delete(int silinecekİtem){
          session=NewHibernateUtil.getSessionFactory().openSession();
          Ogretimuyesi ogretimuyesi=session.get(Ogretimuyesi.class,silinecekİtem);
          if(ogretimuyesi!= null){
	    		session.beginTransaction();
	    		session.delete(ogretimuyesi);
	    		session.getTransaction().commit();
	    	}
          
      } 
      
      public void listele(){
          String sorgu="";
                      sorgu = SORGU_KALIP;
         session.beginTransaction();
        List tamliste = session.createQuery(sorgu).list();
        session.getTransaction().commit();
        musteritamGoster(tamliste);

        
      }
      public void musteritamGoster(List<Ogretimuyesi> tamliste){
              DefaultTableModel model=(DefaultTableModel)ElemanTablosu.getModel();
        model.getDataVector().removeAllElements();  
          
          for (Ogretimuyesi eleman : tamliste) {
            satir=new Vector();
          satir.add(eleman.getOgretimuyesiid());
            satir.add(eleman.getSicilno());
            satir.add(eleman.getAdSoyad());
            satir.add(eleman.getSehir());
            satir.add(eleman.getTelno());
            model.addRow(satir);
         
        }
          
      }
      
      
    
}
