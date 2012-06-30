/**
 * 
 */
package test;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author ASUS
 *
 */
public class TP {
	public static void main(String[] args) {
		TP test = new TP();
		Date d1 = new Date(2012,3,1);
		Date d2 = new Date(2012,4,18);
		int r = test.getPeakDays(d1, d2);
	}
	
	public ArrayList<Integer> periodlength(){
		Date d1 = new Date(2011,12,15);
		Date d2 = new Date(2012,2,15);
		Date d3 = new Date(2012,3,25);
		Date d4 = new Date(2012,4,14);
		Date d5 = new Date(2012,7,1);
		Date d6 = new Date(2012,7,20);
		Date d7 = new Date(2012,9,20);
		Date d8 = new Date(2012,10,10);
		Date d9 = new Date(2013,12,15);
//		long p0 = (d2.getTime() - d1.getTime())/(1000*3600*24);
//		long p1 = (d3.getTime() - d2.getTime())/(1000*3600*24);
//		long p2 = (d4.getTime() - d3.getTime())/(1000*3600*24);
//		long p3 = (d5.getTime() - d4.getTime())/(1000*3600*24);
//		long p4 = (d6.getTime() - d5.getTime())/(1000*3600*24);
//		long p5 = (d7.getTime() - d6.getTime())/(1000*3600*24);
//		long p6 = (d8.getTime() - d7.getTime())/(1000*3600*24);
//		long p7 = (d9.getTime() - d8.getTime())/(1000*3600*24);
		int p0 = (int) ((d2.getTime() - d1.getTime())/(1000*3600*24));
		int p1 = (int) ((d3.getTime() - d2.getTime())/(1000*3600*24));
		int p2 = (int) ((d4.getTime() - d3.getTime())/(1000*3600*24));
		int p3 = (int) ((d5.getTime() - d4.getTime())/(1000*3600*24));
		int p4 = (int) ((d6.getTime() - d5.getTime())/(1000*3600*24));
		int p5 = (int) ((d7.getTime() - d6.getTime())/(1000*3600*24));
		int p6 = (int) ((d8.getTime() - d7.getTime())/(1000*3600*24));
		int p7 = (int) ((d9.getTime() - d8.getTime())/(1000*3600*24));
		
		ArrayList<Integer> rs = new ArrayList<Integer>();
		rs.add(p0);
		rs.add(p1);
		rs.add(p2);
		rs.add(p3);
		rs.add(p4);
		rs.add(p5);
		rs.add(p6);
		rs.add(p7);
		return rs;
	}
	
	private int getPeakDays(Date in, Date out) {
		int inp = period(in);
		int outp = period(out);
		int pkdays = 0;
		ArrayList<Integer> lengs = periodlength();
		for(int i = inp + 1; i < outp; i++){
			pkdays = lengs.get(i) + toend(in,i-1) + tostart(out,i);
		}
		return pkdays;
	}
	
	public int toend(Date in, int p){
		Date d1 = new Date(2011,12,15);
		Date d2 = new Date(2012,2,15);
		Date d3 = new Date(2012,3,25);
		Date d4 = new Date(2012,4,14);
		Date d5 = new Date(2012,7,1);
		Date d6 = new Date(2012,7,20);
		Date d7 = new Date(2012,9,20);
		Date d8 = new Date(2012,10,10);
		Date d9 = new Date(2013,12,15);
		long r = 0L;
		if(p == 0){
			r = d2.getTime() - in.getTime();
		}else if(p == 1){
			r = d3.getTime() - in.getTime();
		}else if(p == 2){
			r = d4.getTime() - in.getTime();
		}else if(p == 3){
			r = d5.getTime() - in.getTime();
		}else if(p == 4){
			r = d6.getTime() - in.getTime();
		}else if(p == 5){
			r = d7.getTime() - in.getTime();
		}else if(p == 6){
			r = d8.getTime() - in.getTime();
		}else if(p == 7){
			r = d9.getTime() - in.getTime();
		}
		int res = (int)(r/(1000*3600*24));
		return res;
	}
	
	public int tostart(Date out, int p){
		Date d1 = new Date(2011,12,15);
		Date d2 = new Date(2012,2,15);
		Date d3 = new Date(2012,3,25);
		Date d4 = new Date(2012,4,14);
		Date d5 = new Date(2012,7,1);
		Date d6 = new Date(2012,7,20);
		Date d7 = new Date(2012,9,20);
		Date d8 = new Date(2012,10,10);
		Date d9 = new Date(2013,12,15);
		long r = 0L;
		if(p == 0){
			r = d1.getTime() - out.getTime();
		}else if(p == 1){
			r = d2.getTime() - out.getTime();
		}else if(p == 2){
			r = d3.getTime() - out.getTime();
		}else if(p == 3){
			r = d4.getTime() - out.getTime();
		}else if(p == 4){
			r = d5.getTime() - out.getTime();
		}else if(p == 5){
			r = d6.getTime() - out.getTime();
		}else if(p == 6){
			r = d7.getTime() - out.getTime();
		}else if(p == 7){
			r = d8.getTime() - out.getTime();
		}
		int res = (int)(r/(-1*1000*3600*24));
		return res;
	}
	
	//which period
	private int period(Date in) {
		int pkdays = 0;
		Date d1 = new Date(2011,12,15);
		Date d2 = new Date(2012,2,15);
		Date d3 = new Date(2012,3,25);
		Date d4 = new Date(2012,4,14);
		Date d5 = new Date(2012,7,1);
		Date d6 = new Date(2012,7,20);
		Date d7 = new Date(2012,9,20);
		Date d8 = new Date(2012,10,10);
		Date d9 = new Date(2013,12,15);
		//inside period 0
		if(in.compareTo(d1) * in.compareTo(d2) < 0){
			return 0;
		}else if(in.compareTo(d2) * in.compareTo(d3) < 0){
			return 1;
		}else if(in.compareTo(d3) * in.compareTo(d4) < 0){
			return 2;
		}else if(in.compareTo(d4) * in.compareTo(d5) < 0){
			return 3;
		}else if(in.compareTo(d5) * in.compareTo(d6) < 0){
			return 4;
		}else if(in.compareTo(d6) * in.compareTo(d7) < 0){
			return 5;
		}else if(in.compareTo(d7) * in.compareTo(d8) < 0){
			return 6;
		}else if(in.compareTo(d8) * in.compareTo(d9) < 0){
			return 7;
		}
		return -1; //fails;
	}
}
