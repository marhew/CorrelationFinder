import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class CorrelationFinder {

  private String inputFile;
  static private int COLAGE = 1;
  static private int COLHEIGHT = 2;
  static private int COLWEIGHT = 3;
  static private int COLBLOODPRESSURE = 4;
  static private int COLMA12 = 16;
  boolean isDifference = false;

  public void setInputFile(String inputFile) {
    this.inputFile = inputFile;
  }

  public void read(String str1, String str2) throws IOException  {
    File inputWorkbook = new File(inputFile);
    Workbook w;
    try {
      w = Workbook.getWorkbook(inputWorkbook);
      // Get the first sheet
      Sheet sheet = w.getSheet(0);
      // Loop over first 10 column and lines
      
      int column1 = 0;
      //find column for MA
      for(int j = 0; j < sheet.getColumns() ; j++){
    	  Cell cell = sheet.getCell(j, 0);
    	  if(cell.getContents().equals(str1)){
    		  column1 = j;
    	  }
      }
      
      int column2 = 0;
      //find column for MA
      for(int j = 0; j < sheet.getColumns() ; j++){
    	  Cell cell = sheet.getCell(j, 0);
    	  if(cell.getContents().equals(str2)){
    		  column2 = j;
    	  }
      }
      
      Vector<Double> vector1 = new Vector<Double>();
      Vector<Double> vector2 = new Vector<Double>();
      String category = "";

      
      // ********** Age **********
      for(int i = 1; i < sheet.getRows(); i++){
    	  if(MAExam(sheet, column1, i)){
    		  Cell cell = sheet.getCell(COLAGE, i);
    		  vector1.add(Double.parseDouble(cell.getContents()));
    	  }
      }
      for(int i = 1; i < sheet.getRows(); i++){
    	  if(MAExam(sheet, column2, i)){
    		  Cell cell = sheet.getCell(COLAGE, i);
    		  vector2.add(Double.parseDouble(cell.getContents()));
    	  }
      }
      
      category = "Age";
      ttest(vector1, vector2, category, str1, str2, vector1.size(), vector2.size());
      
      vector1.clear();
      vector2.clear();
      
      
      // ********** Height **********
      for(int i = 1; i < sheet.getRows(); i++){
    	  if(MAExam(sheet, column1, i)){
    		  Cell cell = sheet.getCell(COLHEIGHT, i);
    		  vector1.add(Double.parseDouble(cell.getContents()));
    	  }
      }
      for(int i = 1; i < sheet.getRows(); i++){
    	  if(MAExam(sheet, column2, i)){
    		  Cell cell = sheet.getCell(COLHEIGHT, i);
    		  vector2.add(Double.parseDouble(cell.getContents()));
    	  }
      }
      
      category = "Height";
      ttest(vector1, vector2, category, str1, str2, vector1.size(), vector2.size());
      
      vector1.clear();
      vector2.clear();
      
      
      // ********** Weight **********
      for(int i = 1; i < sheet.getRows(); i++){
    	  if(MAExam(sheet, column1, i)){
    		  Cell cell = sheet.getCell(COLWEIGHT, i);
    		  vector1.add(Double.parseDouble(cell.getContents()));
    	  }
      }
      for(int i = 1; i < sheet.getRows(); i++){
    	  if(MAExam(sheet, column2, i)){
    		  Cell cell = sheet.getCell(COLWEIGHT, i);
    		  vector2.add(Double.parseDouble(cell.getContents()));
    	  }
      }
      
      category = "Weight";
      ttest(vector1, vector2, category, str1, str2, vector1.size(), vector2.size());
      
      vector1.clear();
      vector2.clear();
      
      if(!isDifference) {
    	  System.out.println("no significant difference found");
    	  System.out.println();
      }
      
      
    } catch (BiffException e) {
      e.printStackTrace();
    }
  }
  
  public void readForResult(String str1, String str2) throws IOException  {
	    File inputWorkbook = new File(inputFile);
	    Workbook w;
	    try {
	      w = Workbook.getWorkbook(inputWorkbook);
	      // Get the first sheet
	      Sheet sheet = w.getSheet(0);
	      // Loop over first 10 column and lines
	      
	      int column1 = 0;
	      //find column for MA
	      for(int j = 0; j < sheet.getColumns() ; j++){
	    	  Cell cell = sheet.getCell(j, 0);
	    	  if(cell.getContents().equals(str1)){
	    		  column1 = j;
	    	  }
	      }
	      
	      int column2 = 0;
	      //find column for MA
	      for(int j = 0; j < sheet.getColumns() ; j++){
	    	  Cell cell = sheet.getCell(j, 0);
	    	  if(cell.getContents().equals(str2)){
	    		  column2 = j;
	    	  }
	      }
	      
	      int totalNumCol1 = 0;
	      int hitNumCol1 = 0;
	      int totalNumCol2 = 0;
	      int hitNumCol2 = 0;
	      
	      for(int i = 1; i < sheet.getRows(); i++){
	    	  if(MAExam(sheet, column1, i)){
	    		  Cell cell = sheet.getCell(COLMA12, i);
	    		  if(cell.getContents().equals("re-hospitalization")) hitNumCol1++;
	    		  totalNumCol1++;
	    	  }
	      }
	      for(int i = 1; i < sheet.getRows(); i++){
	    	  if(MAExam(sheet, column2, i)){
	    		  Cell cell = sheet.getCell(COLMA12, i);
	    		  if(cell.getContents().equals("re-hospitalization")) hitNumCol2++;
	    		  totalNumCol2++;
	    	  }
	      }
	      
	      double perctCol1 = 100 * (double)hitNumCol1/(double)totalNumCol1;
	      double perctCol2 = 100 * (double)hitNumCol2/(double)totalNumCol2;
	      DecimalFormat df = new DecimalFormat("#.#");
	      	      
	      System.out.println( df.format(perctCol1) + "% of " + str1 + ", " + df.format(perctCol2) + "% of " + str2);
	      System.out.println("led to re-hospitalization respectively");
	      System.out.println();
	      
	    } catch (BiffException e) {
	      e.printStackTrace();
	    }
	  }
  
  public void read(String str1, String str2, double value, double width) throws IOException  {
	    File inputWorkbook = new File(inputFile);
	    Workbook w;
	    try {
	      w = Workbook.getWorkbook(inputWorkbook);
	      // Get the first sheet
	      Sheet sheet = w.getSheet(0);
	      // Loop over first 10 column and lines
	      
	      int column1 = 0;
	      //find column for MA
	      for(int j = 0; j < sheet.getColumns() ; j++){
	    	  Cell cell = sheet.getCell(j, 0);
	    	  if(cell.getContents().equals(str1)){
	    		  column1 = j;
	    	  }
	      }
	      
	      int column2 = 0;
	      //find column for MA
	      for(int j = 0; j < sheet.getColumns() ; j++){
	    	  Cell cell = sheet.getCell(j, 0);
	    	  if(cell.getContents().equals(str2)){
	    		  column2 = j;
	    	  }
	      }
	      
	      Vector<Double> vector1 = new Vector<Double>();
	      Vector<Double> vector2 = new Vector<Double>();
	      String category = "";

	      
	      // ********** Age **********
	      for(int i = 1; i < sheet.getRows(); i++){
	    	  if(MAExam(sheet, column1, i)){
	    		  
	    		  Cell cell2 = sheet.getCell(COLWEIGHT, i);
	    		  double temp = Double.parseDouble(cell2.getContents());
	    		  if(value-width <= temp && temp <= value+width){
	    			  Cell cell = sheet.getCell(COLAGE, i);
		    		  vector1.add(Double.parseDouble(cell.getContents()));
	    		  }

	    	  }
	      }
	      for(int i = 1; i < sheet.getRows(); i++){
	    	  if(MAExam(sheet, column2, i)){
	    		  
	    		  Cell cell2 = sheet.getCell(COLWEIGHT, i);
	    		  double temp = Double.parseDouble(cell2.getContents());
	    		  if(value-width <= temp && temp <= value+width){
	    			  Cell cell = sheet.getCell(COLAGE, i);
		    		  vector2.add(Double.parseDouble(cell.getContents()));
	    		  }
	    		  
	    	  }
	      }
	      
	      category = "Age";
	      ttest(vector1, vector2, category, str1, str2, vector1.size(), vector2.size());
	      
	      vector1.clear();
	      vector2.clear();
	      
	      
	      // ********** Height **********
	      for(int i = 1; i < sheet.getRows(); i++){
	    	  if(MAExam(sheet, column1, i)){
	    		  Cell cell2 = sheet.getCell(COLWEIGHT, i);
	    		  double temp = Double.parseDouble(cell2.getContents());
	    		  if(value-width <= temp && temp <= value+width){
	    			  Cell cell = sheet.getCell(COLHEIGHT, i);
		    		  vector1.add(Double.parseDouble(cell.getContents()));
	    		  }
	    	  }
	      }
	      for(int i = 1; i < sheet.getRows(); i++){
	    	  if(MAExam(sheet, column2, i)){
	    		  Cell cell2 = sheet.getCell(COLWEIGHT, i);
	    		  double temp = Double.parseDouble(cell2.getContents());
	    		  if(value-width <= temp && temp <= value+width){
	    			  Cell cell = sheet.getCell(COLHEIGHT, i);
		    		  vector2.add(Double.parseDouble(cell.getContents()));
	    		  }
	    	  }
	      }
	      
	      category = "Height";
	      ttest(vector1, vector2, category, str1, str2, vector1.size(), vector2.size());
	      
	      vector1.clear();
	      vector2.clear();
	      
	      
	      // ********** Blood Pressure **********
	      for(int i = 1; i < sheet.getRows(); i++){
	    	  if(MAExam(sheet, column1, i)){
	    		  Cell cell2 = sheet.getCell(COLWEIGHT, i);
	    		  double temp = Double.parseDouble(cell2.getContents());
	    		  if(value-width <= temp && temp <= value+width){
	    			  Cell cell = sheet.getCell(COLBLOODPRESSURE, i);
		    		  vector1.add(Double.parseDouble(cell.getContents()));
	    		  }
	    	  }
	      }
	      for(int i = 1; i < sheet.getRows(); i++){
	    	  if(MAExam(sheet, column2, i)){
	    		  Cell cell2 = sheet.getCell(COLWEIGHT, i);
	    		  double temp = Double.parseDouble(cell2.getContents());
	    		  if(value-width <= temp && temp <= value+width){
	    			  Cell cell = sheet.getCell(COLBLOODPRESSURE, i);
		    		  vector2.add(Double.parseDouble(cell.getContents()));
	    		  }
	    	  }
	      }
	      
	      category = "Blood Pressure";
	      ttest(vector1, vector2, category, str1, str2, vector1.size(), vector2.size());
	      
	      vector1.clear();
	      vector2.clear();
	      
	      
	      if(!isDifference) {
	    	  System.out.println("no significant difference found");
	    	  System.out.println();
	      }
	      
	      
	    } catch (BiffException e) {
	      e.printStackTrace();
	    }
	  }
  
  // test o or - 
  private boolean MAExam(Sheet sheet, int column, int row){

	  Cell cell = sheet.getCell(column, row);
	  if(cell.getContents().equals("o")) return true;
	  else return false;

  }
  
  private void ttest(Vector<Double> vector1, Vector<Double> vector2, String category, String str1, String str2, int size1, int size2){
	  
	  ArrayList<Double> list = new ArrayList<Double>();
	  
	  TTest testTool = new TTest();
	  list = testTool.runTTest(vector1, vector2);
	  
	  DecimalFormat df = new DecimalFormat("#.##");
        	  
	  if(list.get(0) == 1.0){
		  System.out.println("==========" + category + " significantly different!" + " ==========");
		  System.out.println(str1 + "> " + "average: " + df.format(list.get(1)) + ", " + "data size: " +size1);
		  System.out.println(str2 + "> " + "average: " + df.format(list.get(2)) + ", " + "data size: " +size2);
		  System.out.println();
		  isDifference = true;
	  }
	  	  	  
  }
  

} 