package keker.hasard;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Choix extends SQLiteOpenHelper{

	public String Theme=null;
	public int id;
	public String Name=null;
	public String Image_path=null;
	public String Description=null;
	public int Tirable=1;
	private final Context myContext;
	 private static String DB_NAME = "themes";
	   private SQLiteDatabase db;
	   private static final int DATABASE_VERSION = 1;
	  private String TABLE_SERIE = null;	
	
	public Choix(Context context, String theme, String name, String description, String image_path, int tirable) {
		super(context, DB_NAME, null, DATABASE_VERSION);
		this.Theme=theme;
		this.Name=name;
		this.Description=description;
		this.Image_path=image_path;
		this.Tirable=tirable;
	    this.myContext = context;
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

	

	

	}

