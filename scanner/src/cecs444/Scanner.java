/**
 * Name:		Daniel Suh
 * Class:		CECS 444
 * Assignment:	Scanner
 * Filename:	Scanner.java
 */

package cecs444;

import java.io.*;
import javax.swing.JFrame;

public class Scanner {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException 
	{
		JFrame f = new ScannerGUI();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
