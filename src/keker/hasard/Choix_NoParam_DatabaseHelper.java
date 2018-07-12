package keker.hasard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Choix_NoParam_DatabaseHelper extends SQLiteOpenHelper {

	   private static String DB_NAME = "themes";
	   private final Context myContext;
	   private SQLiteDatabase db;
	   private static final int DATABASE_VERSION = 1;
	  private String TABLE_SERIE = null;	
	
	   
public Choix_NoParam_DatabaseHelper(Context context, String Table_serie){
	 super(context, DB_NAME, null, DATABASE_VERSION);
     //db = getWritableDatabase();
     this.myContext = context;
     this.TABLE_SERIE=Table_serie;
   
}
	


/**
 * Ex&#65533;cut&#65533; si la DB n'existe pas.
 */
@Override
public void onCreate(SQLiteDatabase db) {
    //db.execSQL(CREATE_TABLE_SERIES);
    //Toast.makeText(myContext, "La base est cr&#65533;&#65533;", Toast.LENGTH_SHORT).show();
}

/**
 * Ex&#65533;cut&#65533; chaque fois que le num&#65533;ro de version de DB change.
 */
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERIES);
    //onCreate(db);
}


/**
* Charge le contenu de la table du theme sélectionné
* et retourne ce contenu sous la forme d'un résultat aléatoire
*/
public ArrayList<HashMap<String, String>> getSeries() {
   //Cr&#65533;ation de la ArrayList qui nous permettra de remplire la listView
   ArrayList<HashMap<String, String>> output = new ArrayList<HashMap<String, String>>();
   //On d&#65533;clare la HashMap qui contiendra les informations pour un item
          HashMap<String, String> map;
   String[] colonnesARecup = new String[] { "Tirable", "Name"};
   String myPath = myContext.getDatabasePath(DB_NAME).getPath();
       db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
  // db.execSQL("Select name from themes where type='table'");
   Cursor cursorResults = db.query(TABLE_SERIE, colonnesARecup, null,
           null, null, null, "Name asc", null);
   if (null != cursorResults) {
       if (cursorResults.moveToFirst()) {
           do {
           	map = new HashMap<String, String>();
           	map.put("Tirable", cursorResults.getString(cursorResults.getColumnIndex("Tirable")));
           	map.put("Name", cursorResults.getString(cursorResults.getColumnIndex("Name")));
               output.add(map);

           } while (cursorResults.moveToNext());
       } // end&#65533;if
   }
   cursorResults.close();
   db.close();
   return output;
}
public List<String> creerlistAediter(){
	List<String> liste = new ArrayList<String>();
    ArrayList<HashMap<String, String>> map = this.getSeries();
    int totalItem = map.size();
    for (int i = 0; i <= totalItem - 1; i++) {
    	String name = map.get(i).get("Name").toString();
    	String tirable= map.get(i).get("Tirable").toString();
    	String tirable_message="";
    	if (tirable.equals("0")){
    		tirable_message="- tiré";
    	}
    		 liste.add(name+" "+tirable_message);   
    }
return liste;
   	}

public ArrayList<String> elementtire(){
	  ArrayList<String> listreturn = new ArrayList<String>();
	
	   String[] colonnesARecup = new String[] { "Name"};
	   String myPath = myContext.getDatabasePath(DB_NAME).getPath();
	   String Condition="Tirable = 0";
	   db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
	   Cursor cursorResults = db.query(TABLE_SERIE, colonnesARecup, Condition,
	           null, null, null, "Name asc", null);
	   if (null != cursorResults) {
	       if (cursorResults.moveToFirst()) {
	           do {
	           	listreturn.add(cursorResults.getString(cursorResults.getColumnIndex("Name")));
	           } while (cursorResults.moveToNext());
	       } // end&#65533;if
	   }
	   cursorResults.close();
	   db.close();
	   
	return listreturn;
}

public void saveresult(String result){
	 String myPath = myContext.getDatabasePath(DB_NAME).getPath();
     db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
   
    // String Update_Row="UPDATE "+TABLE_SERIE+" SET Tirable = '0' WHERE Name = "+result+";";
    // db.execSQL(Update_Row);
     String colname="Name";
     String strFilter = colname+"='"+result+"'";
     ContentValues args = new ContentValues();
     args.put("Tirable", "0");
     db.update(TABLE_SERIE, args, strFilter, null);
     db.close();
}

public ArrayList<String> creerlist_choix(){
	 	   ArrayList<String> liste = new ArrayList<String>();
	    ArrayList<HashMap<String, String>> map = this.getSeries();
	    int totalItem = map.size();
	    for (int i = 0; i <= totalItem - 1; i++) {
	    	String name = map.get(i).get("Name").toString(); 
	    		 liste.add(name);   
	    }
	return liste;
	   	}
}