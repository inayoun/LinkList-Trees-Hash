import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class reader {
	public static LinkList custReader (String fileName) {
		//use FileReader to read text file
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);

			//The following line will read the first line, which is the constant service time
			String line = br.readLine();
			int SERVICE = Integer.parseInt(line);
			
			//create linklist with the constant service time
			LinkList c_list = new LinkList (SERVICE);
			
			while ((line = br.readLine()) != null) { //read remaining customer information
				if (line.trim().equals("")) { //we don't want to read empty lines, so skip over them
					continue;
				}
				String line2 = br.readLine(); //line1 contains the ID information. Line2 (the line after that) contains arrival time
				
				int id = Integer.parseInt(line.substring(10).trim()); //we only want the integer portion of string, so take substring and convert to int
				String a = line2.substring(14).trim(); //only want time portion, so take substring
				int arr = timeConv(a); //method that converts hr:min:sec to number of seconds
				
				c_list.add(id, arr); //add customer with id and arrival time to linklist
				
				}
			br.close();
			
			return c_list;
			}
		
		
		//catch possible exceptions
		catch (FileNotFoundException ex) {
			System.out.println("Unable to open file");
			ex.printStackTrace();
			return null;
		}
			    
		catch (IOException ex) {
			System.out.println ("Error reading file");
			ex.printStackTrace();
			return null;
		}
	}
	
	public static String [] queReader (String fileName) {
		try {
			//use file reader to read text file
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			
			//create arraylist to store query line by line
			List <String> list = new ArrayList <String>();
			String line;
			while ((line = br.readLine()) != null) {
				list.add(line.trim()); //trim to get rid of white space
			}
			//convert arraylist to array so each element can be accessed more easily
			String [] query = list.toArray(new String[0]);
			
			br.close();
			
			return query;
		}
		//catch possible exceptions
		catch (FileNotFoundException ex) {
			System.out.println("Unable to open file");
			ex.printStackTrace();
			return null;
		}
					    
		catch (IOException ex) {
			System.out.println ("Error reading file");
			ex.printStackTrace();
			return null;
		}
		
	}
	
	public static int timeConv (String time) { //method that converts String hour:min:sec to integer number of seconds
		String [] t = time.split(":"); //split time into hour, min, sec
		int hr = Integer.parseInt(t[0]); //convert to integer
		int min = Integer.parseInt(t[1]);
		int sec = Integer.parseInt(t[2]);
		
		if (hr<=6 && hr>=1) { //change to military time to prevent confusion
			hr += 12;
		}
		
		int seconds = (hr*3600) + (min*60) + (sec);
		
		return seconds;
		
	}
		

}
