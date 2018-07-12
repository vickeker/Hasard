package keker.hasard;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import keker.hasard.ItemList;
import keker.hasard.Item;
import keker.hasard.Choix;
import keker.hasard.ListeChoix;

public class Edit_Activity extends Activity{
	
private ListView Editlist=null;	
private List<String> ListAEditer=null;
private RelativeLayout menubas=null;
private Button BStartAgain=null;
private Button BRetour=null;
private Button Badditem=null;
private Button Bdeleteitem=null;
private Button Bannuler=null;
private Button BSave=null;
private TextView TV_Message=null;
private EditText NewItem=null;
private String SelectedTheme=null;
private List<String> NewList=null;
private Theme NewListChoix=null;
private String NomListeUser=null;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editlist);
        
        Intent intent = getIntent();
        
        
        SelectedTheme=intent.getStringExtra("selectedtheme");
        NewItem=(EditText)findViewById(R.id.ET_newitem);
        Badditem=(Button)findViewById(R.id.B_additem);
        Bdeleteitem=(Button)findViewById(R.id.B_deleteitem);
        Editlist=(ListView)findViewById(R.id.editlist);
        menubas=(RelativeLayout)findViewById(R.id.L_menubas2);
        TV_Message=(TextView)findViewById(R.id.TV_Message);
          
               
        if (intent != null) {    
        
        ThemeDatabaseHelper dbSelectedTheme = new ThemeDatabaseHelper(this, SelectedTheme);
		Theme SelectedTheme=new Theme(this);
		SelectedTheme=dbSelectedTheme.getThemeInfo(this);
		if (SelectedTheme.Method==1){
			Choix_NoParam_DatabaseHelper listgetter=new Choix_NoParam_DatabaseHelper(this, SelectedTheme.Table_Name);
			ListAEditer=listgetter.creerlistAediter();
		}
ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, ListAEditer);
Editlist.setAdapter(adapter);

NewList = new ArrayList<String>(ListAEditer);

        }
        
        
        
        Badditem.setOnClickListener(new View.OnClickListener() {
  				public void onClick(View vue) {
  					
  					if (!(NewItem.getText().toString().equals(""))){
  						NewList.add(NewItem.getText().toString());
  						ArrayAdapter<String> adapter = new ArrayAdapter<String>(Edit_Activity.this, android.R.layout.simple_list_item_multiple_choice, NewList);
  						Editlist.setAdapter(adapter);
  						NewItem.setText("");
  					}
  					
  				}
        });
        
        Bdeleteitem.setOnClickListener(new View.OnClickListener() {
				public void onClick(View vue) {
					int b=NewList.size();
					int a=0;
				for (int i = 0; i <= b-1; i++) {
				if (Editlist.getCheckedItemPositions().get(i)){
					NewList.remove(i-a);
					a=a+1;
				}
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(Edit_Activity.this, android.R.layout.simple_list_item_multiple_choice, NewList);
					Editlist.setAdapter(adapter);				
				}
    });
        
        Bannuler = (Button) findViewById(R.id.B_Annuler);
        Bannuler.setOnClickListener(new View.OnClickListener() {
  				public void onClick(View vue) {
  					ArrayAdapter<String> adapter = new ArrayAdapter<String>(Edit_Activity.this, android.R.layout.simple_list_item_multiple_choice, ListAEditer);
  					Editlist.setAdapter(adapter);
  					NewList = new ArrayList<String>(ListAEditer);
  					  				}
        });
  
        BRetour = (Button) findViewById(R.id.B_Retour2);
        BRetour.setOnClickListener(new View.OnClickListener() {
  				public void onClick(View vue) {
  					Intent i = getIntent();
					i.putExtra("selectedtheme", SelectedTheme);
	setResult(3, i);
	finish();
  							}
        });	

        BStartAgain = (Button) findViewById(R.id.B_Start2);
        if (intent.getStringExtra("Caller").equals("MesListes")){
        	BStartAgain.setVisibility(View.GONE);
        } 
        
       BStartAgain.setOnClickListener(new View.OnClickListener() {
  				public void onClick(View vue) {
  				ItemList NewItemList = new ItemList();
  					for (int i = 0; i <= NewList.size()-1; i++) {
  					Item item=new Item(NewList.get(i));	
  					NewItemList.add(i, item);
  						}
  	  					
  					Intent intent = getIntent();
  					 intent.putExtra("newlist", (Parcelable)NewItemList);
  					 setResult(2, intent);
  					finish();
  					    	}
       });
  					    
       BSave = (Button) findViewById(R.id.B_Save2);
       BSave.setOnClickListener(new View.OnClickListener(){
    		public void onClick(View vue) {
    			
    			AlertDialog.Builder alert = new AlertDialog.Builder(Edit_Activity.this);
    			alert.setTitle("Nom de la nouvelle liste");
    				alert.setMessage("Voulez vous créer une nouvelle liste ou écraser la liste existante?");
    			// Set an EditText view to get user input 
    			alert.setPositiveButton("Nouvelle liste", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int whichButton) {
    				Intent intent = new Intent(Edit_Activity.this, CreerListe_Activity.class);
    				intent.putExtra("AncienNom", SelectedTheme);
    			startActivityForResult(intent, 0);
    			    			}
    			});
    			alert.setNegativeButton("Ecraser", new DialogInterface.OnClickListener() {
    			  public void onClick(DialogInterface dialog, int whichButton) {
    				  NewListChoix =new Theme(Edit_Activity.this, SelectedTheme, null, null, 1, null, null);
    				    List<Choix> ListeChoix = new ArrayList<Choix>();
    	    			for (int i=0; i<=NewList.size()-1; i++){
    	    				Choix choix=new Choix(Edit_Activity.this, NewListChoix.Name, NewList.get(i), null, null, 1);
    	    				ListeChoix.add(choix);
    	    				}
    	    			NewListChoix.ListChoix=ListeChoix;
    	    			NewListChoix.save();
    	    			TV_Message.setText(SelectedTheme+" a été remplacée avec succès");
    	    			TV_Message.setVisibility(View.VISIBLE);
    			  }
    			});
    			alert.show();
    			
    		}
              });
       
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        
        // resultCode=2 : Sauvegarde d'une nouvelle liste 
        if(resultCode==2){
        Bundle b    = data.getExtras();
        
       String NewName = b.getString("name");
       String NewDesc = b.getString("description");
       
		  NewListChoix =new Theme(Edit_Activity.this, NewName, NewDesc, null, 1, null, null);
	    List<Choix> ListeChoix = new ArrayList<Choix>();
		for (int i=0; i<=NewList.size()-1; i++){
			Choix choix=new Choix(Edit_Activity.this, NewListChoix.Name, NewList.get(i), null, null,1);
			ListeChoix.add(choix); 
		}
		if (NewListChoix.test_table_exist()){
		   	AlertDialog.Builder alert = new AlertDialog.Builder(Edit_Activity.this);
 	   		alert.setTitle(NewListChoix.Name+"existe déjà!");
 	   			alert.setMessage("Merci de donner un autre nom");
 	   		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
 	   		public void onClick(DialogInterface dialog, int whichButton) {
 	   		     	   		}
 	   		});
 	   		alert.show();
		}
		else{
		NewListChoix.create_table();	
		NewListChoix.ListChoix=ListeChoix;
		NewListChoix.save();
		TV_Message.setText(NewName+" a été créée avec succès");
		TV_Message.setVisibility(View.VISIBLE);
		}}
		
    }
    
    } 

    

