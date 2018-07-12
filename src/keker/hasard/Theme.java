package keker.hasard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import keker.hasard.ThemeDatabaseHelper;

public class Theme extends SQLiteOpenHelper {

	public int id;
	public String Name=null;
	public String Image_path=null;
	public String Description=null;
	public int Method;
	public String Table_Name=null;
	public String Param1=null;
	public String Param2=null;
	public int Afficher=1;
	public List<Choix> ListChoix=null; 
	private final Context myContext;
	 private static String DB_NAME = "themes";
	   private SQLiteDatabase db;
	   private static final int DATABASE_VERSION = 1;

 public Theme(Context context){
 super(context, DB_NAME, null, DATABASE_VERSION);
	this.myContext=context;
	}
	
		public Theme(Context context, String name, String desc, String table_name, int method, String param1, String param2){
			super(context, DB_NAME, null, DATABASE_VERSION);
			this.Method=method;
			this.Name=name;
			this.Description=desc;
			this.Param1=param1;
			this.Param2=param2;
			this.Table_Name=table_name;
			if (this.Table_Name==null){
				this.Table_Name=this.Name;		
			}
			this.myContext=context;
				}
	
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

	
	//sauvegarde la liste complète dans la table (écrase les éléments déjà présents)
	public void save(){
		   String myPath = myContext.getDatabasePath(DB_NAME).getPath();
	       db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
	       Log.i("test",myPath);
	       db.execSQL("DELETE FROM "+this.Table_Name);
	       for (int i=0; i<=ListChoix.size()-1;i++){
	     //  db.execSQL("INSERT INTO "+this.Table_Name+"(Name) VALUES ('"+ListChoix.get(i).Name+"');");
	       ContentValues values = new ContentValues();
	       values.put("Name", ListChoix.get(i).Name); 
	       long t=db.insert(this.Table_Name, null, values);
	       }
	       db.close();	  
	       }
	
	
	public void create_table(){
		   String myPath = myContext.getDatabasePath(DB_NAME).getPath();
		   String tableid="_id";
		   String tableDesc="Description";
		   String tableName="Name";
		   String tableimage="Image_path";
		   String tableTir="Tirable";
		   
			   try {
	       db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
	      String Create_Table="CREATE TABLE "+this.Table_Name+" ("+tableid+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "+tableName+" TEXT NOT NULL UNIQUE, "+tableimage+" TEXT, "+tableDesc+" TEXT, "+tableTir+" INTEGER DEFAULT 1);";
	      db.execSQL(Create_Table);
     
	       //String Insert_Row="INSERT INTO Theme ('Name', 'Image_path', 'Description', 'Theme_Table_Name', 'Methode', 'Param1', 'Param2') VALUES ('"+this.Name+"', '"+this.Image_path+"', '"+this.Description+"', '"+this.Table_Name+"', "+this.Method+", '"+this.Param1+"', '"+this.Param2+"');";
	      // db.execSQL(Insert_Row);
	       ContentValues values = new ContentValues();
	       values.put("Name", this.Name); 
	       values.put("Description", this.Description);
	       values.put("Methode", this.Method);
	       values.put("Theme_Table_Name", this.Table_Name);
	       values.put("Afficher", this.Afficher);
	       long t=db.insert("Theme", null, values);
	       
	       db.close();
		   } catch ( Exception e ) {
			      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			      System.exit(0);
			    }	       
	}

	public boolean test_table_exist(){
	boolean exist=false;
	String myPath = myContext.getDatabasePath(DB_NAME).getPath();
    db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    Cursor cursorResults = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", this.Table_Name});
    int count=2;
    if (null != cursorResults) {
    	  if (cursorResults.moveToFirst()) {
              do {
              	count=cursorResults.getInt(0);
              } while (cursorResults.moveToNext());
          } // end&#65533;if
    	  
    }
    	if (count==1){
    		exist=true;
    	}
    
    cursorResults.close();
    db.close();
	return exist;
	}
	
	
}
