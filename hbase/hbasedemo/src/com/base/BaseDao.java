package com.base;

import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

public interface BaseDao {

    /**
     * 创建表
     * @param tableDescriptor
     */
    public void createTable(HTableDescriptor tableDescriptor) throws Exception;

    /**
     *  新增数据
     * @param putData
     *  @param tableName
     */
    public void putData(Put putData,String tableName) throws Exception;

    /**
     * 删除数据
     * @param delData
     *  @param tableName
     */
    public void delData(Delete delData,String tableName) throws Exception;

    /**
     * 查询数据
     * @param scan
     * @param tableName
     * @return
     */
    public ResultScanner scanData(Scan scan,String tableName) throws Exception;

    /**
     * 查询数据
     * @param get
     * @param tableName
     * @return
     */
    public Result getData(Get get,String tableName) throws Exception;
}