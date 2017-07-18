package com.fxl.hbase.HBaseApps;

import java.util.Iterator;
import java.util.TreeSet;

import org.apache.hadoop.hbase.util.Bytes;

public class HashChoreWoker implements SplitKeysCalculator{
	
	private int baseRecord;
	private RowKeyGenerator rkGen;
	private int splitKeysBase;
	private int splitKeysNumber;
	private byte[][] splitKeys;
	
	public HashChoreWoker(int baseRecord,int prepareRegions) {
		this.baseRecord=baseRecord;
		rkGen=new HashRowKeyGenerator();
		splitKeysNumber=prepareRegions-1;
		splitKeysBase=baseRecord/prepareRegions;
	}
	
	public byte[][] calcSplitKeys() {
		splitKeys=new byte[splitKeysNumber][];
		TreeSet<byte[]> rows=new TreeSet<byte[]>(Bytes.BYTES_COMPARATOR);
		for (int i = 0; i < baseRecord; i++) {
			rows.add(rkGen.nextId());
		}
		int pointer=1;
		Iterator<byte[]> rowKeyItor=rows.iterator();
		int index=0;
		while (rowKeyItor.hasNext()) {
			byte[] tempRow=rowKeyItor.next();
			rowKeyItor.remove();
			if ((pointer!=0)&&(pointer%splitKeysBase==0)) {
				if (index<splitKeysNumber) {
					splitKeys[index]=tempRow;
					index++;
				}
			}
			pointer++;
		}
		rows.clear();
		rows=null;
		return splitKeys;
	}

}
