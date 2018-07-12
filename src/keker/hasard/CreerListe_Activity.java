package keker.hasard;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreerListe_Activity extends Activity {

	private EditText ET_Name;
	private EditText ET_Desc;
	private Button B_Ok;
	private Button B_Cancel;
	private String NomListeUser;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      
        setContentView(R.layout.creerliste_activity);
	
        Intent intent = getIntent();
        
        ET_Name=(EditText)findViewById(R.id.ET_NomNewList);
        ET_Desc=(EditText)findViewById(R.id.ET_DescNewList);
        B_Ok=(Button)findViewById(R.id.B_OKNewList);
        B_Cancel=(Button)findViewById(R.id.B_CancelNewList);
        
        
       B_Ok.setOnClickListener(new View.OnClickListener(){
     		public void onClick(View vue) {
     			NomListeUser=ET_Name.getText().toString();
     	   			
     			if (NomListeUser==null){
     	   		   	AlertDialog.Builder alert = new AlertDialog.Builder(CreerListe_Activity.this);
     	   		alert.setTitle("Création d'une nouvelle liste");
     	   			alert.setMessage("Vous devez entrer un nom de liste");
     	   		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
     	   		public void onClick(DialogInterface dialog, int whichButton) {
     	   		     	   		}
     	   		});
     	   		alert.show();
     	   			}
     	   			else{
     			Intent intent = getIntent();
					 intent.putExtra("name", NomListeUser);
					 intent.putExtra("description", ET_Desc.getText().toString());
					 setResult(2, intent);
					finish();
     	   			}
     		}
       });
     		
       
     	B_Cancel.setOnClickListener(new View.OnClickListener(){
         		public void onClick(View vue) {
         			finish();
         		}
     	});			
     		
     		
	}
        
}
