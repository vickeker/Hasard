package keker.hasard;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Spinner;
import keker.hasard.Theme;

public class ThemeDatabaseHelper extends SQLiteOpenHelper {

	private static String DB_NAME = "themes";
	private final Context myContext;
	private final String SelectedName;
	private SQLiteDatabase db;
	/**
	 * Num&#65533;ro de version de la DB. Si ce num&#65533;ro change, la
	 * fonction onUpgrade est ex&#65533;cut&#65533;e pour effacer le contenu de
	 * la DB et recr&#65533;er la nouvelle version du sch&#65533;ma.
	 */
	private static final int DATABASE_VERSION = 1;
	/**
	 * Nom de la table de la DB (oui une seule table).
	 */
	private static String TABLE_Themes = "Theme";

	public ThemeDatabaseHelper(Context context, String selectedName) {
		super(context, DB_NAME, null, DATABASE_VERSION);
		// db = getWritableDatabase();
		this.myContext = context;
		this.SelectedName = selectedName;
		try {
			createDataBase();
			// System.out.println("ok bd");

		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

			// System.out.println("erreur bd");
		}

	}

	/**
	 * Ex&#65533;cut&#65533; si la DB n'existe pas.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// db.execSQL(CREATE_TABLE_SERIES);
		// Toast.makeText(myContext, "La base est cr&#65533;&#65533;",
		// Toast.LENGTH_SHORT).show();

	}

	/**
	 * Ex&#65533;cut&#65533; chaque fois que le num&#65533;ro de version de DB
	 * change.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERIES);
		// onCreate(db);
	}

	/**
	 * Charge le contenu de la table Theme et retourne ce contenu sous la forme
	 * d'une liste de Nom
	 */
	public ArrayList<HashMap<String, String>> getThemes() {
		// Cr&#65533;ation de la ArrayList qui nous permettra de remplire la
		// listView
		ArrayList<HashMap<String, String>> output = new ArrayList<HashMap<String, String>>();
		// On d&#65533;clare la HashMap qui contiendra les informations pour un
		// item
		HashMap<String, String> map;
		String[] colonnesARecup = new String[] { "_id", "Name" };
		String myPath = myContext.getDatabasePath(DB_NAME).getPath();
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
		// db.execSQL("Select name from themes where type='table'");
		Cursor cursorResults = db.query(TABLE_Themes, colonnesARecup, null,
				null, null, null, "Name asc", null);
		if (null != cursorResults) {
			if (cursorResults.moveToFirst()) {
				do {
					map = new HashMap<String, String>();
					map.put("_id", cursorResults.getString(cursorResults
							.getColumnIndex("_id")));
					map.put("Name", cursorResults.getString(cursorResults
							.getColumnIndex("Name")));
					output.add(map);

				} while (cursorResults.moveToNext());
			} // end&#65533;if
		}
		cursorResults.close();
		db.close();
		return output;
	}

	public Theme getThemeInfo(Context context) {
		Theme SelectedTheme = new Theme(context);
		String[] colonnesARecup = new String[] { "_id", "Name", "Methode",
				"Theme_Table_Name", "Param1", "Param2", "Afficher" };
		String ligneARecup = new String("Name='" + this.SelectedName + "'");
		String myPath = myContext.getDatabasePath(DB_NAME).getPath();
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
		// db.execSQL("Select name from themes where type='table'");
		Cursor cursorResults = db.query(TABLE_Themes, colonnesARecup,
				ligneARecup, null, null, null, "Name asc", null);
		if (null != cursorResults) {
			if (cursorResults.moveToFirst()) {
				do {
					SelectedTheme.id = Integer.parseInt(cursorResults
							.getString(cursorResults.getColumnIndex("_id")));
					SelectedTheme.Method = cursorResults.getInt(cursorResults
							.getColumnIndex("Methode"));
					SelectedTheme.Name = cursorResults.getString(cursorResults
							.getColumnIndex("Name"));
					SelectedTheme.Param1 = cursorResults
							.getString(cursorResults.getColumnIndex("Param1"));
					SelectedTheme.Param2 = cursorResults
							.getString(cursorResults.getColumnIndex("Param2"));
					SelectedTheme.Table_Name = cursorResults
							.getString(cursorResults
									.getColumnIndex("Theme_Table_Name"));
					SelectedTheme.Afficher = cursorResults.getInt(cursorResults
							.getColumnIndex("Afficher"));
				} while (cursorResults.moveToNext());
			} // end&#65533;if
		}
		cursorResults.close();
		db.close();
		return SelectedTheme;
	}

	public ArrayList<HashMap<String, String>> getAllThemeInfo(Context context) {
		ArrayList<HashMap<String, String>> Themes = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;
		String[] colonnesARecup = new String[] { "Name", "Methode",
				"Theme_Table_Name", "Param1", "Param2", "Afficher" };
		String myPath = myContext.getDatabasePath(DB_NAME).getPath();
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
		// db.execSQL("Select name from themes where type='table'");
		Cursor cursorResults = db.query(TABLE_Themes, colonnesARecup, null,
				null, null, null, "Name asc", null);
		if (null != cursorResults) {
			if (cursorResults.moveToFirst()) {
				do {
					map = new HashMap<String, String>();
					map.put("Name", cursorResults.getString(cursorResults
							.getColumnIndex("Name")));
					map.put("Methode", cursorResults.getString(cursorResults
							.getColumnIndex("Methode")));
					map.put("Theme_Table", cursorResults
							.getString(cursorResults
									.getColumnIndex("Theme_Table_Name")));
					map.put("Param1", cursorResults.getString(cursorResults
							.getColumnIndex("Param1")));
					map.put("Param2", cursorResults.getString(cursorResults
							.getColumnIndex("Param2")));
					map.put("Afficher", cursorResults.getString(cursorResults
							.getColumnIndex("Afficher")));
					Themes.add(map);

				} while (cursorResults.moveToNext());
			} // end&#65533;if
		}
		cursorResults.close();
		db.close();
		return Themes;
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = myContext.getDatabasePath(DB_NAME).getPath();
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// database does't exist yet.
			Log.i("BDBUZZ", "Erreur Bd" + e);
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	public void openDataBase() throws SQLException {
		// Open the database
		String myPath = myContext.getDatabasePath(DB_NAME).getPath();
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	public void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = myContext.getDatabasePath(DB_NAME).getPath();
		Log.i("myApp", myContext.getDatabasePath(DB_NAME).getPath());
		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();

		Log.i("BDBUZZ", "Checkdatabase " + dbExist);

		String DEBUG_TAG = "BDBUZZ";
		if (dbExist) {
			Log.i(DEBUG_TAG, "createDataBase -> La base existe");
			// do nothing - database already exist
		} else {
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			Log.i("BDBUZZ", "Avant getreadable");

			this.getReadableDatabase();
			Log.i("BDBUZZ", "Apres getreadable");
			Log.i(DEBUG_TAG, "else, db existiert nicht 1");
			try {
				copyDataBase();
				Log.i(DEBUG_TAG, "nach copydatabase");
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	public void Afficher(Boolean Aafficher, String SelectedTheme) {
		String myPath = myContext.getDatabasePath(DB_NAME).getPath();
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
		String TABLE_SERIE = "Theme";
		String colname = "Name";
		String strFilter = colname + "='" + SelectedTheme + "'";
		ContentValues args = new ContentValues();
		if (Aafficher) {
			args.put("Afficher", "1");
		} else {
			args.put("Afficher", "0");
		}
		db.update(TABLE_SERIE, args, strFilter, null);
		db.close();
	}

	public void SuppTheme(String ThemeToDelete) {
		String myPath = myContext.getDatabasePath(DB_NAME).getPath();
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
		db.execSQL("DELETE FROM Theme WHERE Name='" + ThemeToDelete + "'");
		db.close();
	}

	public List<String> Collect_Theme() {
		List<String> liste = new ArrayList<String>();
		HashMap<String, String> map;
		ArrayList<HashMap<String, String>> output = new ArrayList<HashMap<String, String>>();
		String[] colonnesARecup = new String[] { "Name", "Afficher" };
		String myPath = myContext.getDatabasePath(DB_NAME).getPath();
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
		// db.execSQL("Select name from themes where type='table'");
		String selection = "Afficher=1";
		Cursor cursorResults = db.query(TABLE_Themes, colonnesARecup,
				selection, null, null, null, "Name asc", null);
		if (null != cursorResults) {
			if (cursorResults.moveToFirst()) {
				do {
					map = new HashMap<String, String>();
					map.put("Afficher", cursorResults.getString(cursorResults
							.getColumnIndex("Afficher")));
					map.put("Name", cursorResults.getString(cursorResults
							.getColumnIndex("Name")));
					output.add(map);
					liste.add(map.get("Name"));
				} while (cursorResults.moveToNext());
			} // end&#65533;if
		}
		cursorResults.close();
		db.close();
		if(liste.isEmpty()){
			liste.add("Pas de liste à afficher");
		}
		return liste;
	}

}