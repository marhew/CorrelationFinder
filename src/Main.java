import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {

		System.out.println("Correlation Finder initiated...");
		System.out.println();
		System.out.println("Type two mutually exclusive medical activities, for example, \"MA1 MA2\".");
		System.out.println("Add \"previous\" to find corelation between current and previous medical activities.");
		System.out.println("Or add \"result\" to see how the current medical options might affect the result of the entire medical process.");
		
		CorrelationFinder test = new CorrelationFinder();
	    test.setInputFile("MedicalData.xls");
		
		String s = "";
		while (true) {
			System.out.println("Enter query: ");

			try {
				
				test.isDifference = false;
				BufferedReader bufferRead = new BufferedReader(
						new InputStreamReader(System.in));
				s = bufferRead.readLine();
				//System.out.println(s);
				
				if(s.contains("previous")){
					processPrevious(test, s);
				}else if(s.contains("result")){
				    test.readForResult(tokenize(s).get(0), tokenize(s).get(1));
				}else{
				    test.read(tokenize(s).get(0), tokenize(s).get(1));
				}
							    
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	
	private static void processPrevious(CorrelationFinder test, String str) throws IOException{
		String s = "";
		while(true){
			test.isDifference = false;
			System.out.println("Enter the result of previous medical activity. (in this case, weight)");
			System.out.println("Enter value and width, for example, \"75 2\". Then Correlation Finder finds correlations under the condition \"75-2 < weight < 75+2\".");

			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			s = bufferRead.readLine();
			if(s.equals("break")) return;
			
			test.read(tokenize(str).get(0),  tokenize(str).get(1), Double.parseDouble(tokenize(s).get(0)), Double.parseDouble(tokenize(s).get(1)));
			
		}
	}
	
	private static ArrayList<String> tokenize(String str){
		
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer stt = new StringTokenizer(str," ");
		
        while (stt.hasMoreTokens()){
            String token = stt.nextToken();
            list.add(token);
        }
        
        return list;
		
	}
	
}
