package com.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;


class User{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setInfo(int age){}
	public void setInfo(String age){}
}

public class Test {

	public static void main(String[] args) {
		
		List<User> list=new ArrayList<User>();
		System.out.println(list.size());
		list=null;
//		Iterator<User> itr=list.iterator();
//		while (itr.hasNext()) {
//			itr.next();
//			
//		}
		for (User user : list) {
			System.out.println(user);
		}
		
//		System.out.println(Integer.MAX_VALUE);
		
//		String str1="abc";
//		String str2="abc";
//		String str3=new String("abc");
//		System.out.println(str1==str2);
//		System.out.println(str1.equals(str2));
//		System.out.println(str1==str3);
//		System.out.println(str1.equals(str3));
//		
		
//		User u1=new User();
//		User u2=new User();
//		User u3=u2;
//		String str1=new String("abc");
//		String str2=new String("abc");
//		System.out.println(u3.equals(u2));
//		System.out.println(u3==u2);
//		System.out.println(str1==str2);
//		System.out.println(str1.equals(str2));
		
//		int[] arr={1,2,6,32,45,4,8,1,3,6,99,25};
//		java.util.Arrays.sort(arr);
//		for (int i = 0; i < arr.length; i++) {
//			System.out.print(arr[i]+" ");
//		}
		
		
//		func1(1,2,3,4,5,5,43,222,1);
//		
//		String str1="";
//		String str2=null;
//		System.out.println(str1.equals(str2));
//		System.out.println(str1==str2);
//		
//		int[] arr={1333,2,6,32,45,4,8,1,3,6,99,25};
//		java.util.Arrays.sort(arr);
//		for(int i:arr){
//			System.out.println("i="+i);
//		}
//		
//		new User().setInfo(1);
		
		
		
		

	}

	private static void func1(int ... arr) {
		System.out.println(arr.length);
	}

}
