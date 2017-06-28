package com.demo;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.coordination.ZKSplitLogManagerCoordination.GetDataAsyncCallback;
import org.apache.hadoop.hbase.util.Bytes;

public class Demo01 {
	
	static Configuration config=null;
	static Connection connection =null;
	static Table table =null;
	
	static{
		config = HBaseConfiguration.create();
	    config.set("hbase.zookeeper.quorum", "192.168.56.11");
		try {
			connection = ConnectionFactory.createConnection(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    try {
			table = connection.getTable(TableName.valueOf("users"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		}

	public static void main(String[] args) throws Exception {
		
		//添加数据
//		addData();
		//修改数据
		//updateData();
		//读数据
//		GetData();
		//删除数据
//		deleteData();
		//找回密码
		getPassword();

	}

	private static void getPassword() throws Exception {
        Get get=new Get(Bytes.toBytes("TheRealMT"));
        get.addFamily(Bytes.toBytes("info"));
        Result result=table.get(get);
        List<KeyValue> passwords=result.getColumn(Bytes.toBytes("info"), Bytes.toBytes("password"));
        byte[] bytes=passwords.get(0).getValue();
        System.out.println(""+Bytes.toString(bytes));
	}

	private static void deleteData() throws Exception {
	    try {
		      try {
		        Delete d = new Delete(Bytes.toBytes("TheRealMT"));
		        d.deleteColumns(Bytes.toBytes("info"), Bytes.toBytes("name"));
		        table.delete(d);

		       } finally {
		         if (table != null) table.close();
		       }
		     } finally {
		       connection.close();
		     }
	}

	private static void GetData() throws Exception {
	    try {
		      try {
		        Get get=new Get(Bytes.toBytes("TheRealMT"));
		        get.addFamily(Bytes.toBytes("info"));
		        Result result=table.get(get);
		        byte[] name_bytes=result.getValue(Bytes.toBytes("info"), Bytes.toBytes("name"));
		        byte[] password_bytes=result.getValue(Bytes.toBytes("info"), Bytes.toBytes("password"));
		        byte[] email_bytes=result.getValue(Bytes.toBytes("info"), Bytes.toBytes("email"));
		        String name=Bytes.toString(name_bytes);
		        String password=Bytes.toString(password_bytes);
		        String email=Bytes.toString(email_bytes);
		        
		        System.out.println("name="+name);
		        System.out.println("password="+password);
		        System.out.println("email="+email);
		        
		        
//		        get.addColumn(Bytes.toBytes("email"), Bytes.toBytes("password"));
//		        Result result=table.get(get);
//		        byte[] bytes=result.getValue(Bytes.toBytes("info"), Bytes.toBytes("email"));
//		        System.out.println(bytes.toString());
		       } finally {
		         if (table != null) table.close();
		       }
		     } finally {
		       connection.close();
		     }
	}

	private static void updateData() throws Exception{
	    try {
	      try {
	        Put p = new Put(Bytes.toBytes("TheRealMT"));
	        p.add(Bytes.toBytes("info"), Bytes.toBytes("password"),Bytes.toBytes("abc123"));
	        table.put(p);
	       } finally {
	         if (table != null) table.close();
	       }
	     } finally {
	       connection.close();
	     }
	}

	private static void addData() throws Exception{
		
	    try {
	    	
	      try {

	        Put p = new Put(Bytes.toBytes("TheRealMT"));
	        p.add(Bytes.toBytes("info"), Bytes.toBytes("name"),Bytes.toBytes("Mark Twain"));
	        p.add(Bytes.toBytes("info"), Bytes.toBytes("email"),Bytes.toBytes("samuel@celmens.org"));
	        p.add(Bytes.toBytes("info"), Bytes.toBytes("password"),Bytes.toBytes("Langhorne"));
	        
	        table.put(p);

	       } finally {
	         if (table != null) table.close();
	       }
	     } finally {
	       connection.close();
	     }
	}
}
