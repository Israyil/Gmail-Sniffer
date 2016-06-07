package model;

import java.util.*;

public class OgrenciList {
	
	public static List<Ogrenci> ogrenciList=new ArrayList<Ogrenci>();
	
	public static void  AddOgrenciList(Ogrenci o){
		ogrenciList.add(o);
	}

	public static List<Ogrenci> getOgrenciList() {
		return ogrenciList;
	}

	public static void setOgrenciList(List<Ogrenci> ogrenciList) {
		OgrenciList.ogrenciList = ogrenciList;
	}
	
	public static void clearList(){
		ogrenciList=new ArrayList<Ogrenci>();
	}
	
}
