package com.fxl.hbase.HBaseApps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.type.StandardBasicTypes;

public class ProjectDao{
	
	public static List<ProjectInfo> queryAll() {
		Configuration cfg = new Configuration();  
        cfg.configure();  
        ServiceRegistry  sr = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();   
        SessionFactory  sf = cfg.buildSessionFactory(sr);  
        Session s = sf.openSession();  
        Transaction tx = s.beginTransaction();
        
        String hql = "select project_id,msg_id,req from msgs";
		Query query = s.createSQLQuery(hql);
		List<Object> list = ((SQLQuery) query)
				.addScalar("project_id",StandardBasicTypes.STRING) 
                .addScalar("msg_id", StandardBasicTypes.INTEGER) 
                .addScalar("req",StandardBasicTypes.STRING).list();
		List<ProjectInfo> projectList=new ArrayList<ProjectInfo>();
		for(Iterator iterator = list.iterator();iterator.hasNext();){ 
            //每个集合元素都是一个数组，数组元素师person_id,person_name,person_age三列值 
			ProjectInfo projectInfo=new ProjectInfo();
            Object[] objects = (Object[]) iterator.next();
            projectInfo.setProject_id(objects[0].toString());
            projectInfo.setMsg_id((Integer) objects[1]);
            projectInfo.setReq(objects[2].toString());
            projectList.add(projectInfo);
//            System.out.println("project_id="+objects[0].toString()); 
//            System.out.println("name="+objects[1]); 
//            System.out.println("age="+objects[2]); 
//            System.out.println("----------------------------"); 
        } 
        tx.commit();  
        s.close();  
        sf.close();
		return projectList;
	}
	
	public static void main(String[] args) {
		ProjectDao.queryAll();
	}


}