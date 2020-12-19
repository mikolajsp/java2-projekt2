package pl.pw.mini.Schoolify.modules;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContentCrossExaminer {
	
	public HashMap<Integer,List<String>> bad_words;
		
	public void badWords(){
		if(bad_words != null) {
			return;
		}else {
			bad_words = new HashMap<>();
		BufferedReader objReader = null;
		  try {
		   String strCurrentLine;
		   objReader = new BufferedReader(new FileReader("badwords"));

		   while ((strCurrentLine = objReader.readLine()) != null) {
			   String newString = strCurrentLine.replaceAll("[,']","");
			   String[] badLine = newString.split(" ");
			   for(String s:badLine){
				   if(!bad_words.containsKey(s.length())) {
					   List<String> newel = new ArrayList<String>();
					   newel.add(s);
					   bad_words.put(s.length(),newel);
				   }else {
					   List<String> vals = bad_words.get(s.length());
					   vals.add(s);
					   bad_words.put(s.length(), vals);
				   }			   }
		   }

		  } catch (IOException e) {

		   e.printStackTrace();

		}
	}		
}
	public boolean checkBadWords(String input) {
		String[] values = input.replaceAll("^[.,\\s]+", "").split("[.,\\s]+");
		badWords();
		for(String s:values) {
			if(bad_words.containsKey(s.length())){
				if(bad_words.get(s.length()).contains(s.toLowerCase())){
					return false;
				}
			}
		}
		return true;
	}
	
	
}
