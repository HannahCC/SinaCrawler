package org.cl.test;

import java.io.IOException;
import org.cl.run.GetWeiboCon;

public class testWeiboCon {
	public static void main(String args[]) throws IOException{
		String uid = "1014427510";
		GetWeiboCon getWeiboCon=new GetWeiboCon(uid);
		getWeiboCon.run();
	}
}
