package keker.hasard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;

public class Meslistes_Activity extends Activity {

	private GridLayout GL_MesListes = null;
	private Button B_annuler = null;
	private Button B_ok = null;
	private Button B_NewList = null;
	private int rowcount = 1;
	private ArrayList<HashMap<String, String>> Themes;
	private List<TextView> ListTV_theme;
	private List<CheckBox> ListCB_afficher;
	private List<Button> ListB_editer;
	private List<Button> ListB_supp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meslistes_activity);

		GL_MesListes = (GridLayout) findViewById(R.id.GL_meslistes);

		ThemeDatabaseHelper dbCon = new ThemeDatabaseHelper(this, null);
		Themes = new ArrayList<HashMap<String, String>>();
		Themes = dbCon.getAllThemeInfo(this);
		rowcount = Themes.size();
		GL_MesListes.setRowCount(rowcount);
		Log.i("test", "Row " + GL_MesListes.getRowCount());
		Log.i("test", "Col " + GL_MesListes.getColumnCount());

		ListTV_theme = new ArrayList<TextView>();
		ListCB_afficher = new ArrayList<CheckBox>();
		ListB_editer = new ArrayList<Button>();
		ListB_supp = new ArrayList<Button>();

		ListTV_theme.clear();
		ListCB_afficher.clear();
		ListB_editer.clear();
		ListB_supp.clear();

		/**
		 * TextView TV_TitreList = new TextView(Meslistes_Activity.this);
		 * TV_TitreList.setWidth(-2); TV_TitreList.setHeight(-2);
		 * TV_TitreList.setText("Liste"); TextView TV_TitreAff = new
		 * TextView(Meslistes_Activity.this); TV_TitreAff.setWidth(-2);
		 * TV_TitreAff.setHeight(-2); TV_TitreAff.setText("Afficher"); TextView
		 * TV_TitreEd = new TextView(Meslistes_Activity.this);
		 * TV_TitreEd.setWidth(-2); TV_TitreEd.setHeight(-2);
		 * TV_TitreEd.setText(""); TextView TV_TitreSupp = new
		 * TextView(Meslistes_Activity.this); TV_TitreSupp.setWidth(-2);
		 * TV_TitreSupp.setHeight(-2); TV_TitreSupp.setText("");
		 * 
		 * GL_MesListes.addView(TV_TitreList, 0);
		 * GL_MesListes.addView(TV_TitreAff, 1);
		 * GL_MesListes.addView(TV_TitreEd, 2);
		 * GL_MesListes.addView(TV_TitreSupp, 3);
		 */

		for (int i = 0; i <= rowcount - 1; i++) {

			TextView TV_theme = new TextView(Meslistes_Activity.this);
			TV_theme.setClickable(true);
			HashMap<String, String> map = new HashMap<String, String>(
					Themes.get(i));
			String SelectedTheme = map.get("Name");
			TV_theme.setText(SelectedTheme);
			TV_theme.setTextSize(2, 16);
			TV_theme.setMinWidth(100);

			final CheckBox CB_afficher = new CheckBox(Meslistes_Activity.this);

			Boolean afficher = false;
			if (Integer.parseInt(map.get("Afficher")) == 1) {
				afficher = true;
			}
			CB_afficher.setChecked(afficher);
			CB_afficher.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					ThemeDatabaseHelper dbCon = new ThemeDatabaseHelper(
							Meslistes_Activity.this, null);
					int a = ListCB_afficher.indexOf(v);
					String Theme = ListTV_theme.get(a).getText().toString();
					if (CB_afficher.isChecked()) {
						dbCon.Afficher(true, Theme);
					} else {
						dbCon.Afficher(false, Theme);
					}
				}
			});

			Button B_editer = new Button(Meslistes_Activity.this);
			B_editer.setHeight(10);
			B_editer.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.b_edit2));
			B_editer.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// Ouvre l'activity Edit_Activity
					int a = ListB_editer.indexOf(v);
					String Theme = ListTV_theme.get(a).getText().toString();
					Intent intent = new Intent(Meslistes_Activity.this,
							Edit_Activity.class);
					intent.putExtra("selectedtheme", Theme);
					intent.putExtra("Caller", "MesListes");
					startActivityForResult(intent, 1);
				}
			});

			Button B_supp = new Button(Meslistes_Activity.this);
			B_supp.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.b_supp));
			B_supp.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					ThemeDatabaseHelper dbCon = new ThemeDatabaseHelper(
							Meslistes_Activity.this, null);
					int a = ListB_supp.indexOf(v);
					String Theme = ListTV_theme.get(a).getText().toString();
					dbCon.SuppTheme(Theme);
				}
			});

			if (SelectedTheme.equals("Bouteille")
					|| SelectedTheme.equals("Chiffre")) {
				B_editer.setEnabled(false);
				B_editer.setVisibility(View.GONE);
				B_supp.setEnabled(false);
				B_supp.setVisibility(View.GONE);
			}

			int a = i * 4;

			GL_MesListes.addView(TV_theme, a);
			GL_MesListes.addView(CB_afficher, a + 1);
			GL_MesListes.addView(B_editer, a + 2);
			GL_MesListes.addView(B_supp, a + 3);

			ListTV_theme.add(TV_theme);
			ListCB_afficher.add(CB_afficher);
			ListB_editer.add(B_editer);
			ListB_supp.add(B_supp);
		}

		B_ok = (Button) findViewById(R.id.B_OK2);
		B_ok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View vue) {

				// Sauvegarder les modifications faites à MesListes
				// (suppression/ajout de liste) modification de l'affichage

				Intent i = getIntent();
				setResult(5, i);
				finish();
			}
		});

	}

}
