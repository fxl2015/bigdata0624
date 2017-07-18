package com.fxl.hbase.HBaseApps;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class UserTest {  
    public static void main(String[] args) {  
        User user = new User();  
        user.setId(1);  
        user.setName("zhangsan");  
        user.setAge(24);  
          
        Configuration cfg = new Configuration();  
        cfg.configure();  
        ServiceRegistry  sr = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();   
        SessionFactory  sf = cfg.buildSessionFactory(sr);  
        Session s = sf.openSession();  
        Transaction tx = s.beginTransaction();  
          
        s.save(user);
        
        tx.commit();  
        s.close();  
        sf.close();  
    }  
}