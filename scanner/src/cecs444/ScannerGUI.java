/**
 * Name:		Daniel Suh
 * Class:		CECS 444
 * Assignment:	Scanner
 * Filename:	ScannerGUI.java
 */

package cecs444;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class ScannerGUI extends JFrame implements ActionListener{

	private JTextField filename, reservedFilename;
	private JLabel fileInput;
	private JButton runScan, getReservedList;
	private JTextArea input, result, reservedList, symbolList;
	int r;

	Map<String, Integer> reservedWords = new TreeMap<String, Integer>();
	Map<String, Integer> symbols = new TreeMap<String, Integer>();

	Scanner_Class list = new Scanner_Class();

	public ScannerGUI()
	{
		setTitle("Scanner");
		setSize(1350,700);
		setLayout(new FlowLayout(FlowLayout.CENTER));

		input = new JTextArea(20,40);
		input.setEditable(false);
		JScrollPane scroller = new JScrollPane(input);
		add(scroller);

		fileInput = new JLabel("Enter file names");
		add(fileInput);

		reservedFilename = new JTextField(10);
		add(reservedFilename);

		getReservedList = new JButton("Get reserved words");
		getReservedList.addActionListener(this);
		add(getReservedList);

		filename = new JTextField(10);
		add(filename);

		runScan = new JButton("Scan");
		runScan.addActionListener(this);
		runScan.setEnabled(true);
		add(runScan);

		result = new JTextArea(20,50);
		result.setEditable(false);
		JScrollPane scroller2 = new JScrollPane(result);
		add(scroller2);

		reservedList = new JTextArea(20,20);
		reservedList.setEditable(false);
		JScrollPane scroller3 = new JScrollPane(reservedList);
		add(scroller3);

		symbolList = new JTextArea(20,25);
		symbolList.setEditable(false);
		JScrollPane scroller4 = new JScrollPane(symbolList);
		add(scroller4);

	}

	public void actionPerformed(ActionEvent e)
	{
		Object s = e.getSource();

		if(s == runScan)
		{
			try
			{
				list.read_characters(filename.getText());
				File file = new File(filename.getText());
				InputStream in = new FileInputStream(file);
				Reader reader = new InputStreamReader(in);

				input.append("ORIGINAL FILE:\n\n");

				int r;

				while((r = reader.read()) != -1)
					input.append(String.valueOf((char) r));

				result.append("Result:\n\n" + list.getResult());

				symbols = list.getSymbols();
				Set<String> keyset = symbols.keySet();
				System.out.println();

				symbolList.append("Symbol\t\t# of occurences\n\n");

				for(String key:keyset)
					symbolList.append(key + "\t\t" + symbols.get(key) + "\n");
			}//end try
			catch (IOException e1){}

			runScan.setEnabled(false);
		}//end if
		else if(s == getReservedList)
		{
			boolean going = true;
			File file = new File(reservedFilename.getText());
			InputStream in;
			reservedList.append("Reserved Words\n\n");

			try{
				in = new FileInputStream(file);
				Reader reader = new InputStreamReader(in);
				String temp = "";
				while(going)
				{
					r = reader.read();
					if(r == -1)
						going = false;

					if((char)r =='\n' ||(char)r=='r'|| r==-1)
					{
						reservedWords.put(temp, 0);
						reservedList.append(temp + "\n");
						temp = "";
					}
					else
						temp+=(char)r;


				}
			} catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
			} catch(IOException g)
			{
				g.printStackTrace();
			}

			getReservedList.setEnabled(false);
			runScan.setEnabled(true);
			list.setReservedWordList(reservedWords);
		}//end elseif
	}//end actionPerformed

}
