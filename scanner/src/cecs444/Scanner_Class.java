/**
 * Name:		Daniel Suh
 * Class:		CECS 444
 * Assignment:	Scanner
 * Filename:	Scanner_Class.java
 */

package cecs444;
import java.io.*;
import java.util.*;

public class Scanner_Class {

	int current_read, state;
	String token_under_construction;
	char current_char;
	String result;

	Map<String, Integer> reservedWords = new TreeMap<String, Integer>();
	Map<String, Integer> symbols = new TreeMap<String, Integer>();
	
	/**
	 * Default Constructor
	 */
	Scanner_Class()
	{
		token_under_construction = "";
		current_read = 0;
		state = 0;
		result = "";
	}
	
	/**
	 * Opens the file and finds all of the tokens
	 * @param filename
	 * @throws IOException
	 */
	public void read_characters(String filename) throws IOException
	{
		int r;
		boolean buffered = false;
		boolean going = true;

		File file = new File(filename);
		InputStream in = new FileInputStream(file);
		Reader reader = new InputStreamReader(in);

		while(going)
		{
			r = reader.read();
			char ch = (char) r;

			if(r == -1)
				going = false;
			if((!buffered) || (current_char == '\n') ||(current_char == '\r') ||
					(current_char=='\t'))
				current_char = ch;

			if(Character.isLetter(current_char))
				current_read = 26;
			else if(Character.isDigit(current_char))
				current_read = 27;
			else
			{
				switch(current_char)
				{
				case '<':
					current_read = 0;
					break;
				case '>':
					current_read = 1;
					break;
				case '[':
					current_read = 2;
					break;
				case ']':
					current_read = 3;
					break;
				case'{':
					current_read = 4;
					break;
				case '}':
					current_read = 5;
					break;
				case '@':
					current_read = 6;
					break;
				case '&':
					current_read = 7;
					break;
				case '#':
					current_read = 8;
					break;
				case '!':
					current_read = 9;
					break;
				case '~':
					current_read = 10;
					break;
				case '\'':
					current_read = 11;
					break;
				case '\"':
					current_read = 12;
					break;
				case '$':
					current_read = 13;
					break;
				case ':':
					current_read = 14;
					break;
				case ';':
					current_read = 15;
					break;
				case '.':
					current_read = 16;
					break;
				case ',':
					current_read = 17;
					break;
				case '+':
					current_read = 18;
					break;
				case '-':
					current_read = 19;
					break;
				case '/':
					current_read = 20;
					break;
				case '*':
					current_read = 21;
					break;
				case '=':
					current_read = 22;
					break;
				case '^':
					current_read = 23;
					break;
				case '(':
					current_read = 24;
					break;
				case ')':
					current_read = 25;
					break;
				case '_':
					current_read = 28;
					break;
				case ' ':
					current_read = 29;
					break;
				case '|':
					current_read = 30;
					break;
				case '\\':
					current_read = 31;
					break;
				case '?':
					current_read = 32;
					break;
				case '%':
					current_read = 33;
					break;
				default:
					current_read = 34;
					break;
				}//end switch
			}//end else

			if((next_state(state, current_read) != -1) &&
					(action(state, current_read)==1))
			{
				buffered = false;
				token_under_construction+= current_char;
				state = next_state(state, current_read);
			}
			else if((next_state(state, current_read)==-1) &&
					(action(state, current_read)==2))
			{
				buffered = true;

				switch(look_up(state, current_read))
				{
				case 1:
					result += token_under_construction + "\t\t=> Simple Operator Found " +
							token_under_construction + "\n";
					break;
				case 2:
					result += token_under_construction + "\t\t=> Compound Operator Found " +
							token_under_construction + "\n";
					break;
				case 3:
					result += token_under_construction + "\t\t=> Valid String Literal\n";
					break;
				case 4:
					if(symbols.containsKey(token_under_construction))
					{
						int j = symbols.get(token_under_construction);
						symbols.put(token_under_construction, j+1);
						result += token_under_construction + "\t\t=> Identifier EXISTS in table\n";
					}
					else if (reservedWords.containsKey(
							token_under_construction))
						result += token_under_construction + "\t\t=> Identifier and Reserved Word\n";
					else
					{
						symbols.put(token_under_construction,  1);
						result += token_under_construction + "\t\t=> Identifier placed into table\n";
					}
					break;
				case 5:
					result += token_under_construction + "\t\t=> Valid Integer\n";
					break;
				case 6:
					result += token_under_construction + "\t\t=> Valid Currency\n";
					break;
				case 7:
					result += token_under_construction + "\t\t=> Valid Real\n";
					break;
				case 8:
					result += token_under_construction + "\t\t=> Valid Scientific Number\n";
					break;
				case 9:
					result += token_under_construction + "\t\t=> File Token Found\n";
					break;
				case 10:
					result += token_under_construction + "\t\t=> Comment\n";
					break;
				}
				state = 0;
				token_under_construction = "";
			}//end else if

			//Identifies the current char as the token
			if(buffered)
			{
				if((next_state(state, current_read) != -1) &&
						(action(state, current_read) == 1))
				{
					buffered = false;
					token_under_construction+= current_char;
					state = next_state(state, current_read);
				}
				else if((next_state(state, current_read) == -1) && 
							(action(state, current_read) ==2))
				{
					buffered = true;
					switch(look_up(state, current_read))
					{
					case 1:
						result += token_under_construction + "\t\t=> Simple Operator Found " +
								token_under_construction + "\n";
						break;
					case 2:
						result += token_under_construction + "\t\t=> Compound Operator Found " +
								token_under_construction + "\n";
						break;
					case 3:
						result += token_under_construction + "\t\t=> Valid String Literal\n";
						break;
					case 4:
						if(symbols.containsKey(token_under_construction))
						{
							int j = symbols.get(token_under_construction);
							symbols.put(token_under_construction, j+1);
							result += token_under_construction + "\t\t=> Identifier EXISTS in table\n";
						}
						else if (reservedWords.containsKey(
								token_under_construction))
							result += token_under_construction + "\t\t=> Identifier and Reserved Word\n";
						else
						{
							symbols.put(token_under_construction,  1);
							result += token_under_construction + "\t\t=> Identifier placed into table\n";
						}
						break;
					case 5:
						result += token_under_construction + "\t\t=> Valid Integer\n";
						break;
					case 6:
						result += token_under_construction + "\t\t=> Valid Currency\n";
						break;
					case 7:
						result += token_under_construction + "\t\t=> Valid Real\n";
						break;
					case 8:
						result += token_under_construction + "\t\t=> Valid Scientific Number\n";
						break;
					case 9:
						result += token_under_construction + "\t\t=> File Token Found\n";
						break;
					case 10:
						result += token_under_construction + "\t\t=> Comment\n";
						break;
					}
					state = 0;
					token_under_construction = "";
				}//end else if
				else
				{
					current_char = '\n';
				}
				
			}//end if
		}//end while
		result+= "\nDONE SCANNING";
	}
	
	/**
	 * Determines the next state based on the current char
	 * This state table creates a 2D-Array from an external .csv file specified by the author(me)
	 * @param new_state
	 * @param new_char
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public int next_state(int new_state, int new_char) throws IOException, FileNotFoundException
	{
		int state_table[][] = new int [999][9999];
		
		//Loads csv file into state table
		BufferedReader stateRdr = new BufferedReader(new FileReader("F://Users/dsuh/Dropbox/CSULB/CECS444/Scanner/state.csv"));
		String line = null;
		int row = 0;
		int col = 0;
		
		//Reads each line of the text file
		while((line = stateRdr.readLine()) != null)
		{
			StringTokenizer st = new StringTokenizer(line, ",");
			while(st.hasMoreTokens())
			{
				//Gets the next token and stores it into the array
				state_table[row][col] = Integer.parseInt(st.nextToken());
				col++;
			}
			row++;
		}
		stateRdr.close();
			
		return state_table[new_state][new_char];
	}

	/**
	 * Determines the next action to take based on the current char
	 * This action table creates a 2D-Array from an external .csv file specified by the author(me)
	 * @param new_state
	 * @param new_char
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public int action(int new_state, int new_char) throws IOException, FileNotFoundException
	{
		int action_table[][] = null;
		
		//Loads csv file into state table
		BufferedReader actionRdr = new BufferedReader(new FileReader("F://Users/dsuh/Dropbox/CSULB/CECS444/Scanner/action.csv"));
		String line = null;
		int row = 0;
		int col = 0;
		
		while((line = actionRdr.readLine()) != null)
		{
			StringTokenizer st2 = new StringTokenizer(line, ",");
			while(st2.hasMoreTokens())
			{
				//Gets the next token and stores it into the array
				action_table[row][col] = Integer.parseInt(st2.nextToken());
				col++;
			}
			row++;
		}
		actionRdr.close();
			
		return action_table[new_state][new_char];
	}//end action
	
	/**
	 * Looks up the next action to take based on the current char
	 * This look_up table creates a 2D-Array from an external .csv file specified by the author(me)
	 * @param new_state
	 * @param new_char
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public int look_up(int new_state, int new_char) throws IOException, FileNotFoundException
	{
		int look_up_table[][] = new int[105][34];
		
		//Loads csv file into state table
		BufferedReader lookRdr = new BufferedReader(new FileReader("F://Users/dsuh/Dropbox/CSULB/CECS444/Scanner/lookup.csv"));
		String line = null;
		int row = 0;
		int col = 0;
		
		while((line = lookRdr.readLine()) != null)
		{
			StringTokenizer st3 = new StringTokenizer(line, ",");
			while(st3.hasMoreTokens())
			{
				//Gets the next token and stores it into the array
				look_up_table[row][col] = Integer.parseInt(st3.nextToken());
				col++;
			}
			row++;
		}
		lookRdr.close();
			
		return look_up_table[new_state][new_char];
	}
	
	/**
	 * Returns all of the tokens that have been identified
	 * @return
	 */
	public String getResult()
	{
		return result;
	}
	
	/**
	 * Stores all the reserved words grabbed from an external text
	 * file into a Map
	 * @param s
	 */
	public void setReservedWordList(Map<String, Integer> s)
	{
		reservedWords = s;
	}
	
	/**
	 * Returns all of the symbols that are stored in a map and also
	 * the number of occurrences
	 * @return
	 */
	public Map<String, Integer> getSymbols()
	{
		return symbols;
	}
}//end class
