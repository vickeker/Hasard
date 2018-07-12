package keker.hasard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

//import android.graphics.drawable.Drawable;

public class ListeChoix {


/**
	 * 
	 */

//private Drawable illustration = null;
private int n;
private int random_number;
private String Choix = null;
private ArrayList<String> Listdechoix = null;

//private List<String> Newlist = null;


public void setListSelec(ArrayList<String> ListSelec) {
	this.Listdechoix = ListSelec;
}

public String Tirage() {
		Random random = new Random();
n=Listdechoix.size();
	random_number = random.nextInt(n);
	Choix = Listdechoix.get(random_number);
	return Choix;
}



}
