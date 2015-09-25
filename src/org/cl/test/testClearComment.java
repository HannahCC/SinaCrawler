package org.cl.test;

import java.io.IOException;

import org.cl.run.ClearComment;

public class testClearComment {

	public static void main(String args[]) throws IOException{
		String uid = "1568384951";
		ClearComment cc = new ClearComment(uid);
		cc.run();
	}
}
