package keker.hasard;

import java.util.ArrayList;

public class Chiffre {

	public Chiffre(){
		
	}
	
	
	public ArrayList<String> getlist(int a, int b){
		  ArrayList<String> liste = new ArrayList<String>();
		    for (int i = a; i <= b; i++) {
		    	 		 liste.add(String.valueOf(i));   
		    }
		return liste;
	}
	
	
}
