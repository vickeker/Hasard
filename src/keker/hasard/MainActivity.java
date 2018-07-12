package keker.hasard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import keker.hasard.R;
import keker.hasard.ListeChoix;
import keker.hasard.ThemeDatabaseHelper;
import keker.hasard.Choix_NoParam_DatabaseHelper;
import keker.hasard.Chiffre;
//import keker.hasard.Slider;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button BStart = null;
	private Button BRetour = null;
	private Button BStartAgain = null;
	private Button BSave = null;
	private Button BEdit = null;
	private Button BMeslistes = null;
	private Spinner liste = null;
	private ListView Editlist = null;
	private RelativeLayout menubas = null;
	private RelativeLayout menuparam = null;
	// private Slider slider = null;
	private TextView TResult = null;
	private RelativeLayout menuhaut = null;
	private Spinner S_TpsAnim = null;
	private CheckBox CB_TirExcl = null;
	private String res = null;
	private ArrayList<String> listdejatire = null;
	private Choix_NoParam_DatabaseHelper listgetter = null;
	// private String choix = null;

	protected boolean Debut = true;
	/* Le menu à dissimuler */

	/* Vitesse désirée pour l'animation */
	protected final static int SPEED = 300;
	private ArrayList<String> SelectedList = null;
	private List<String> ListAEditer = null;
	private int Param1 = 0;
	private int Param2 = 1;
	private Chiffre chiffregetter = null;
	private EditText Tparam1 = null;
	private EditText Tparam2 = null;
	private ArrayAdapter<String> adapter = null;
	private int angle_rotation;
	private String Theme = null;
	private Theme SelectedTheme = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		menuhaut = (RelativeLayout) findViewById(R.id.L_menuhaut);

		menubas = (RelativeLayout) findViewById(R.id.L_menubas);
		menuparam = (RelativeLayout) findViewById(R.id.L_menuparam);
		
		BSave = (Button) findViewById(R.id.B_Save);

		S_TpsAnim = (Spinner) findViewById(R.id.S_TpsAnim);
		CB_TirExcl = (CheckBox) findViewById(R.id.CB_TirageExclusif);

		// if (NewlistafterEdit==null) {
		menuhaut.setVisibility(View.VISIBLE);

		TResult = (TextView) findViewById(R.id.T_Result);
		BStart = (Button) findViewById(R.id.B_Start);

		Tparam1 = (EditText) findViewById(R.id.ET_param1);
		Tparam2 = (EditText) findViewById(R.id.ET_param2);

		liste = (Spinner) findViewById(R.id.Sp_liste);
		// Connection à la db et récupération de la liste des thèmes pour
		// remplir le Spinner
		ThemeDatabaseHelper dbCon = new ThemeDatabaseHelper(this, null);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, dbCon.Collect_Theme());
		// Le layout par défaut est
		// android.R.layout.simple_spinner_dropdown_item
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		liste.setAdapter(adapter);

		liste.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				Theme = adapter.getItem(position);
				angle_rotation = 1260;
				if (Theme.equals("Chiffre")) {
					BStart.setBackgroundResource(R.drawable.b_start);
					menuparam.setVisibility(View.VISIBLE);
					BEdit.setEnabled(false);
					CB_TirExcl.setVisibility(View.VISIBLE);
					BSave.setVisibility(View.VISIBLE);
				} else if (Theme.equals("Bouteille")) {
					BStart.setBackgroundResource(R.drawable.b_bouteille);
					menuparam.setVisibility(View.GONE);
					BEdit.setEnabled(false);
					CB_TirExcl.setVisibility(View.GONE);
					BSave.setVisibility(View.GONE);
				} else if (Theme.equals("Sahanah")) {
					BStart.setBackgroundResource(R.drawable.b_love);
					menuparam.setVisibility(View.GONE);
					BEdit.setEnabled(true);
					CB_TirExcl.setVisibility(View.VISIBLE);
					BSave.setVisibility(View.VISIBLE);
				} else if (Theme.equals("Pas de liste à afficher")) {
					BStart.setEnabled(false);
					menuparam.setVisibility(View.GONE);
					BEdit.setEnabled(false);
					CB_TirExcl.setVisibility(View.VISIBLE);
					BSave.setVisibility(View.VISIBLE);
				} else {
					BStart.setBackgroundResource(R.drawable.b_start);
					menuparam.setVisibility(View.GONE);
					BEdit.setEnabled(true);
					CB_TirExcl.setVisibility(View.VISIBLE);
					BSave.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});

		BStart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View vue) {

				Animation.AnimationListener StartListener = new Animation.AnimationListener() {
					public void onAnimationEnd(Animation animation) {
						// On dissimule le menu
						menubas.setVisibility(View.VISIBLE);
						if (!(Theme.equals("Bouteille"))) {
							TResult.setVisibility(View.VISIBLE);
							BStart.setVisibility(View.GONE);
						}

					}

					public void onAnimationRepeat(Animation animation) {
						BStart.setVisibility(View.VISIBLE);
					}

					public void onAnimationStart(Animation animation) {
						TResult.setVisibility(View.GONE);
						menuhaut.setVisibility(View.GONE);
						menuparam.setVisibility(View.GONE);

					}
				};

				ThemeDatabaseHelper dbSelectedTheme = new ThemeDatabaseHelper(
						MainActivity.this, liste.getSelectedItem().toString());
				SelectedTheme = new Theme(MainActivity.this);
				SelectedTheme = dbSelectedTheme.getThemeInfo(MainActivity.this);

				switch (SelectedTheme.Method) {
				case 0:
					switch (Integer.parseInt(SelectedTheme.Table_Name)) {
					case 1:
						// liste de chiffre
						
						if (Tparam1.getText()==null){ Param1=0;}
						else {Param1 = Integer.parseInt(Tparam1.getText().toString());}
						if (Tparam2.getText()==null){ Param2=10;}
						else {Param2 = Integer.parseInt(Tparam2.getText().toString());}
						
						chiffregetter = new Chiffre();
						if (SelectedList != null) {
							SelectedList.clear();
						}
						SelectedList = new ArrayList<String>();
						SelectedList = chiffregetter.getlist(Param1, Param2);
						res = Tirage(SelectedList);
						TResult.setText(res);

						break;
					case 2:
						// Rotation de la bouteille
						listdejatire = null;
						chiffregetter = new Chiffre();
						if (SelectedList != null) {
							SelectedList.clear();
						}
						SelectedList = new ArrayList<String>();
						SelectedList = chiffregetter.getlist(0, 360);
						listdejatire = null;
						res = Tirage(SelectedList);
						angle_rotation = 1080 + Integer.parseInt(res);

						break;
					default:
						if (SelectedList != null) {
							SelectedList.clear();
						}
						SelectedList = new ArrayList<String>();
						SelectedList.add("rentre chez ta mère");
					}
					break;
				case 1:
					listgetter = new Choix_NoParam_DatabaseHelper(
							MainActivity.this, SelectedTheme.Table_Name);
					if (SelectedList != null) {
						SelectedList.clear();
					}
					SelectedList = new ArrayList<String>();
					SelectedList = listgetter.creerlist_choix();
					listdejatire = listgetter.elementtire();
					res = Tirage(SelectedList);
					TResult.setText(res);
					Animation rotation = AnimationUtils.loadAnimation(
							MainActivity.this, R.anim.choix_bouton_rotation);
					rotation.setAnimationListener(StartListener);
					BStart.startAnimation(rotation);
					break;
				case 2:
					if (SelectedList != null) {
						SelectedList.clear();
					}
					SelectedList = new ArrayList<String>();
					SelectedList.add("Choix avec paramètre");
					break;
				default:
					if (SelectedList != null) {
						SelectedList.clear();
					}
					SelectedList = new ArrayList<String>();
					SelectedList.add("Rentre chez ta mère");
				}

				RotateAnimation anim = new RotateAnimation(0, angle_rotation,
						Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				anim.setInterpolator(new LinearInterpolator());
				if (S_TpsAnim.getSelectedItem() != null) {
					anim.setDuration(Integer.parseInt(S_TpsAnim
							.getSelectedItem().toString()) * 1000);
				} else {
					anim.setDuration(3000);
				}
				anim.setFillEnabled(true);
				anim.setAnimationListener(StartListener);
				if (Theme.equals("Bouteille")) {
					anim.setFillAfter(true);
					anim.setFillEnabled(true);
				}
				BStart.startAnimation(anim);

				// Animation rotation =
				// AnimationUtils.loadAnimation(MainActivity.this,
				// R.anim.choix_bouton_rotation);
				// rotation.setAnimationListener(StartListener);
				// BStart.startAnimation(rotation);
			}

		});

		BRetour = (Button) findViewById(R.id.B_Retour);
		BRetour.setOnClickListener(new View.OnClickListener() {
			public void onClick(View vue) {
				if (CB_TirExcl.isChecked()) {
					listdejatire.add(TResult.getText().toString());
				}
				TResult.setVisibility(View.GONE);
				if (Theme.equals("Bouteille")) {
					BStart.setRotation(360 - (angle_rotation - 1080));
				}
				BStart.setVisibility(View.VISIBLE);
				menubas.setVisibility(View.GONE);
				menuhaut.setVisibility(View.VISIBLE);
				if (liste.getSelectedItem().toString().equals("Chiffre")) {
					menuparam.setVisibility(View.VISIBLE);
				}
			}
		});

		BStartAgain = (Button) findViewById(R.id.B_Start2);
		BStartAgain.setOnClickListener(new View.OnClickListener() {
			public void onClick(View vue) {

				Animation.AnimationListener StartAgainListener = new Animation.AnimationListener() {
					public void onAnimationEnd(Animation animation) {
						// On dissimule le menu
						if (!(Theme.equals("Bouteille"))) {
							TResult.setVisibility(View.VISIBLE);
							BStart.setVisibility(View.GONE);
						}
					}

					public void onAnimationRepeat(Animation animation) {
						BStart.setVisibility(View.VISIBLE);
					}

					public void onAnimationStart(Animation animation) {
						BStart.setVisibility(View.VISIBLE);
						TResult.setVisibility(View.GONE);
					}
				};

				TResult.setVisibility(View.GONE);
				BStart.setVisibility(View.VISIBLE);

				if (Theme.equals("Bouteille")) {
					listdejatire = null;
				}

				res = Tirage(SelectedList);
				TResult.setText(res);

				if (Theme.equals("Bouteille")) {
					angle_rotation = 1080 + Integer.parseInt(res);
				}

				RotateAnimation anim = new RotateAnimation(0, angle_rotation,
						Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				anim.setInterpolator(new LinearInterpolator());
				if (S_TpsAnim.getSelectedItem() != null) {
					anim.setDuration(Integer.parseInt(S_TpsAnim
							.getSelectedItem().toString()) * 1000);
				} else {
					anim.setDuration(3000);
				}
				anim.setFillEnabled(true);
				anim.setAnimationListener(StartAgainListener);

				if (Theme.equals("Bouteille")) {
					anim.setFillAfter(true);
					anim.setFillEnabled(true);
				}

				BStart.startAnimation(anim);
				// Animation rotation =
				// AnimationUtils.loadAnimation(MainActivity.this,
				// R.anim.choix_bouton_rotation);
				// rotation.setAnimationListener(StartAgainListener);
				// BStart.startAnimation(rotation);

			}
		});

		BSave = (Button) findViewById(R.id.B_Save);
		BSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View vue) {
				if(!SelectedTheme.equals("Chiffre")){
				listgetter.saveresult(TResult.getText().toString());
				}
				listdejatire.add(TResult.getText().toString());
			}
		});

		BEdit = (Button) findViewById(R.id.B_Edit);
		BEdit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View vue) {

				Intent intent = new Intent(MainActivity.this,
						Edit_Activity.class);
				intent.putExtra("selectedtheme", liste.getSelectedItem()
						.toString());
				intent.putStringArrayListExtra("listdejatire", listdejatire);
				intent.putExtra("Caller", "MainActivity");
				startActivityForResult(intent, 0);
			}

		});

		BMeslistes = (Button) findViewById(R.id.B_MesListes);
		BMeslistes.setOnClickListener(new View.OnClickListener() {
			public void onClick(View vue) {
				Intent intent = new Intent(MainActivity.this,
						Meslistes_Activity.class);

				startActivityForResult(intent, 0);

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		ThemeDatabaseHelper dbCon = new ThemeDatabaseHelper(this, null);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, dbCon.Collect_Theme());
		// Le layout par défaut est
		// android.R.layout.simple_spinner_dropdown_item
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		liste.setAdapter(adapter);

		switch (resultCode) {

		case 2:

			Bundle b = data.getExtras();

			ItemList itemlistresult = b.getParcelable("newlist");
			if (SelectedList != null) {
				SelectedList.clear();
			}
			SelectedList = new ArrayList<String>();
			for (int i = 0; i <= itemlistresult.size() - 1; i++) {
				SelectedList.add(i, itemlistresult.get(i).getName());
			}
			/**
			 * NewlistafterEdit=new ArrayList<String>(); for (int i=0;
			 * i<=itemlistresult.size()-1; i++){ NewlistafterEdit.add(i,
			 * itemlistresult.get(i).getName()); }
			 */

			Animation.AnimationListener StartListener = new Animation.AnimationListener() {
				public void onAnimationEnd(Animation animation) {
					// On dissimule le menu
					menubas.setVisibility(View.VISIBLE);
					if (!(Theme.equals("Bouteille"))) {
						TResult.setVisibility(View.VISIBLE);
						BStart.setVisibility(View.GONE);
					}

				}

				public void onAnimationRepeat(Animation animation) {
					BStart.setVisibility(View.VISIBLE);
				}

				public void onAnimationStart(Animation animation) {
					TResult.setVisibility(View.GONE);
					menuhaut.setVisibility(View.GONE);
					menuparam.setVisibility(View.GONE);

				}
			};

			res = Tirage(SelectedList);
			TResult.setText(res);

			RotateAnimation anim = new RotateAnimation(0, angle_rotation,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			anim.setInterpolator(new LinearInterpolator());
			if (S_TpsAnim.getSelectedItem() != null) {
				anim.setDuration(Integer.parseInt(S_TpsAnim.getSelectedItem()
						.toString()) * 1000);
			} else {
				anim.setDuration(3000);
			}
			anim.setFillEnabled(true);
			anim.setAnimationListener(StartListener);
			if (Theme.equals("Bouteille")) {
				anim.setFillAfter(true);
				anim.setFillEnabled(true);
			}
			BStart.startAnimation(anim);
			break;

		default:

		}
	}

	public String Tirage(ArrayList<String> Listdechoix) {
		String Choix;
		if (Listdechoix.size() == 0) {
			Choix = "La liste est vide, tous les éléments ont été tirés!";
		} else {
			Random random = new Random();
			if (listdejatire != null && CB_TirExcl.isChecked()) {
				for (int i = 0; i <= listdejatire.size() - 1; i++) {
					Listdechoix.remove(listdejatire.get(i));
					Log.i("test1", "taille: " + Listdechoix.size());
				}
			}
			int n = Listdechoix.size();
			int random_number;
			do {
				random_number = random.nextInt(n);
				Choix = Listdechoix.get(random_number);
			} while (random_number == 0);
		}
		return Choix;
	}

}