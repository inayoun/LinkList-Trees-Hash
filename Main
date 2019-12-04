import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class customerService {
	public static void main (String [] args) {
		if (args.length !=2) { //if input is not valid
			System.out.println ("Missing input file name");
			return;
		}		
		
		String cf = args[0].toString(); //convert arguments to String files
		String qf = args[1].toString();
		
		LinkList c_list = reader.custReader(cf); //read customer text file
				
		String [] quer = reader.queReader(qf); //read query text file
		
		int [] answ = new int [quer.length]; //array of answers to later match with queries
	
		queryAnswers qa = new queryAnswers(c_list); //instantiate queryAnswers with LinkList created from customer text file. methods in queryAnswers will be used to answer queries
		
		for (int i = 0; i<quer.length; i++) { //for each query item, figure out which method to call for answer
			if (quer[i].equals("NUMBER-OF-CUSTOMERS-SERVED")){
				answ[i] = qa.numServed();
			}
			else if (quer[i].equals("LONGEST-BREAK-LENGTH")){
				answ[i] = qa.longestBreak();
			}
			else if (quer[i].equals("TOTAL-IDLE-TIME")) {
				answ[i] = qa.totalIdle();
			}
			else if (quer[i].equals("MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME")) {
				answ[i] = qa.queue();
			}
			else if (quer[i].substring(0, 15).equals("WAITING-TIME-OF")) { //substring excluding integer
				String ID = quer[i].substring(quer[i].length() -1); //last character, which is the customer number
				int cID = Integer.parseInt(ID); //convert to integer
				answ[i] = qa.waitTime(cID);
			}
			else {
				System.out.println ("Invalid Query");
			}
		}
		//create output file
		try {
			FileWriter fw = new FileWriter ("output.txt"); //create new file called output
			BufferedWriter bw = new BufferedWriter (fw); //write to this file
			
			for (int i = 0; i<quer.length; i++) { 
				bw.write(quer[i] + ":" + answ[i]); //print query:answer
				bw.newLine();
			}
			
			bw.close();
			
		}
		catch (IOException ex) {
			System.out.println ("Error writing file");
		}
		
	}
	
}
