package keker.hasard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

public class Alertdialogparam extends Activity {

private int param1 = 0;
private int param2 = 0;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        
	        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
	        dlgAlert.setMessage("entrer les paramètres");
	        dlgAlert.setTitle("Configurer la liste");
	        final EditText input = new EditText(this);
	        final EditText input2=new EditText(this);
	        input.setInputType(InputType.TYPE_CLASS_NUMBER);
	        input2.setInputType(InputType.TYPE_CLASS_NUMBER);
	        dlgAlert.setPositiveButton("Ok",
	        	    new DialogInterface.OnClickListener() {
	        	        public void onClick(DialogInterface dialog, int which) {
	        	          //dismiss the dialog  
	        	        }
	        	    });
	        dlgAlert.setCancelable(true);
	        dlgAlert.show();

	     setParam1(Integer.parseInt(input.getText().toString()));
	     setParam2(Integer.parseInt(input2.getText().toString()));
	    }

		public int getParam1() {
			return param1;
		}

		public void setParam1(int param1) {
			this.param1 = param1;
		}
		public int getParam2() {
			return param2;
		}

		public void setParam2(int param2) {
			this.param2 = param2;
		}
/**	    public int getParam2() {
	        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
	        dlgAlert.setMessage("dernier chiffre");
	        dlgAlert.setTitle("Configurer la liste");
	        final EditText input = new EditText(this);
	        input.setInputType(InputType.TYPE_CLASS_NUMBER);
	        dlgAlert.setPositiveButton("Ok",
	        	    new DialogInterface.OnClickListener() {
	        	        public void onClick(DialogInterface dialog, int which) {
	        	          //dismiss the dialog  
	        	        }
	        	    });
	        dlgAlert.setCancelable(true);
	        dlgAlert.show();
	        return Integer.parseInt(input.getText().toString());
	    }*/
	    }
	
