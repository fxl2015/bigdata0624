package com.fxl.hbase.HBaseApps;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseDao {
    private static Configuration conf = HBaseConfiguration.create();
    static {
//        conf.set("hbase.rootdir", "hdfs://cc/hbase");
        // 设置Zookeeper,直接设置IP地址
//        conf.set("hbase.zookeeper.quorum", "192.168.1.160,192.168.1.162,192.168.1.163");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.zookeeper.quorum", "192.168.56.128");
        conf.set("hbase.master", "192.168.56.128:9000");
    }

    // 创建表
    public static void createTable(String tablename, String[] columnFamilys) throws Exception {
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();

        TableName tableNameObj = TableName.valueOf(tablename);

        if (admin.tableExists(tableNameObj)) {
            System.out.println("Table exists!");
            admin.disableTable(TableName.valueOf(tablename));
            admin.deleteTable(TableName.valueOf(tablename));
        } 
            HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tablename));
            for (int i = 0; i < columnFamilys.length; i++) {
            	tableDesc.addFamily(new HColumnDescriptor(columnFamilys[i]).setMaxVersions(10000));
			}
         
//            tableDesc.addFamily(new HColumnDescriptor(columnFamily));
            admin.createTable(tableDesc);
            System.out.println("create table success!");
       
        admin.close();
        connection.close();
    }
    
    public static void createTableWithSplit(String tablename, String columnFamily,byte[][] splits) throws Exception {
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();

        TableName tableNameObj = TableName.valueOf(tablename);

        if (admin.tableExists(tableNameObj)) {
            System.out.println("Table exists!");
            System.exit(0);
        } else {
            HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tablename));
            HColumnDescriptor hcd=new HColumnDescriptor(Bytes.toBytes(columnFamily));
            hcd.setBlockCacheEnabled(true);
            hcd.setMaxVersions(5);
            tableDesc.addFamily(new HColumnDescriptor(columnFamily));
            admin.createTable(tableDesc,splits);
            System.out.println("create table success!");
        }
        admin.close();
        connection.close();
    }
    

    // 删除表
    public static void deleteTable(String tableName) {
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            Admin admin = connection.getAdmin();
            TableName table = TableName.valueOf(tableName);
            admin.disableTable(table);
            admin.deleteTable(table);
            System.out.println("delete table " + tableName + " ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 插入一行记录
    public static void addRecord(String tableName, String rowKey, String family, String qualifier, String value){
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            Table table = connection.getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
            table.put(put);
            table.close();
            connection.close();
            System.out.println("insert recored " + rowKey + " to table " + tableName + " ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //批量插入
    public static void addRecords(String tableName, String rowKey,List<ProjectInfo> projectInfos){
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            Table table = connection.getTable(TableName.valueOf(tableName));
            List<Put> puts=new LinkedList<Put>();
            for (ProjectInfo projectInfo : projectInfos) {
            	String str_temp="000000"+String.valueOf(projectInfo.getMsg_id());
            	str_temp = str_temp.substring(str_temp.length()-6,str_temp.length());
            	System.out.println("str_temp="+str_temp);
                Put put = new Put(Bytes.toBytes(projectInfo.getProject_id()+"_"+str_temp));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("msg_id"), Bytes.toBytes(projectInfo.getMsg_id()));
                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("req"), Bytes.toBytes(projectInfo.getReq()));
//                put.setId(String.valueOf(projectInfo.getMsg_id()));
                
                puts.add(put);
			}
            table.put(puts);
            table.close();
            connection.close();
            System.out.println("insert recoreds success.");
        } catch (IOException e) {
            e.printStackTrace();
    }
}

    public static void main(String[] args) throws Exception {
    	String[] columns={"info"};
    	HbaseDao.createTable("tb_projects_temp",columns);
//    	List<ProjectInfo> projectInfos=ProjectDao.queryAll();
//    	System.out.println("projectInfos.size()="+projectInfos.size());
//    	HbaseDao.addRecords("tb_projects","", projectInfos);
    	
//    	String[] families={"info"};
//    	HbaseDao.createTable("tb_test",families);
//    	HbaseDao.addRecord("tb_test", "001", "info", "age", "20");
    	
//    	HashChoreWoker worker=new HashChoreWoker(1000000, 10);
//    	byte[][] splitKeys=worker.calcSplitKeys();
//    	HbaseDao.createTableWithSplit("hash_split_table","info",splitKeys);
//    	String[] columns={"info"};
//        HbaseDao.createTable("tb_projects",columns);
//    	HashRowKeyGenerator hrk=new HashRowKeyGenerator();
//    	for (int i = 0; i < 1000; i++) {
//        	HbaseDao.addRecord("hash_split_table",new String(hrk.nextId()), "info", "name", "zhangsan");
//		}
//        HbaseDao.addRecord("testTb", "001", "info", "age", "20");
        //HbaseDao.deleteTable("testTb");
    }
}