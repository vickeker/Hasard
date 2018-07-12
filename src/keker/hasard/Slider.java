package keker.hasard;

	import keker.hasard.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.widget.TextView;

	/**
	 * Représente le menu animé.
	 */
	public class Slider extends LinearLayout {
		
		public Slider(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		/* Est-ce que le menu est ouvert ? */
		protected boolean Debut = true;
		/* Le menu à dissimuler */ 
		private Button BStart = null;
		private RelativeLayout menubas = null;
		private RelativeLayout menuhaut = null;
		private TextView TResult = null;

		/* Vitesse désirée pour l'animation */
		protected final static int SPEED = 300;

		/* Listener pour l'animation de fermeture du menu */
		Animation.AnimationListener StartListener = new Animation.AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				//On dissimule le menu
				menubas.setVisibility(View.VISIBLE);
				BStart.setVisibility(View.GONE);
				TResult.setVisibility(View.VISIBLE);
			}
			    
			public void onAnimationRepeat(Animation animation) {
				BStart.setVisibility(View.VISIBLE);
			}
			    
			public void onAnimationStart(Animation animation) {
				menuhaut.setVisibility(View.GONE);
				
							}
		};
		
		/* Listener pour l'animation d'ouverture du menu */
		Animation.AnimationListener ReturnListener = new Animation.AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				menuhaut.setVisibility(View.VISIBLE);
			}
			    
			public void onAnimationRepeat(Animation animation) {
				
			}
			    
			public void onAnimationStart(Animation animation) {
				//On affiche le menu
				menubas.setVisibility(View.GONE);
				BStart.setVisibility(View.VISIBLE);
			}
		};

		Animation.AnimationListener StartAgainListener = new Animation.AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				//On dissimule le menu
				BStart.setVisibility(View.GONE);
				TResult.setVisibility(View.VISIBLE);
			}
			    
			public void onAnimationRepeat(Animation animation) {
				BStart.setVisibility(View.VISIBLE);
			}
			    
			public void onAnimationStart(Animation animation) {
				BStart.setVisibility(View.VISIBLE);
				
							}
		};


		/**
		 * Constructeur utilisé pour l'initialisation en Java.
		 * @param context Le contexte de l'activité.
		 
		public Slider(Context context) {
			super(context);
		}
		
		/**
		 * Constructeur utilisé pour l'initialisation en XML.
		 * @param context Le contexte de l'activité.
		 * @param attrs Les attributs définis dans le XML.
		 *
		*public Slider(Context context, AttributeSet attrs) {
		*	super(context, attrs);
		*}
		*/
		/**
		 * Utilisée pour ouvrir ou fermer le menu.
		 * @return true si le menu est désormais ouvert.
		 */
		public void Choix(Boolean etat, Context c) {
			//Animation de transition.
	Animation rotation = AnimationUtils.loadAnimation(c, R.anim.choix_bouton_rotation);
		    if(etat) {	
		    	rotation.setAnimationListener(StartListener);
			    BStart.startAnimation(rotation);
		}
		    else {
		    	rotation.setAnimationListener(StartAgainListener);
			    BStart.startAnimation(rotation);
		    }
		    }

		
		public void setmenu(RelativeLayout menu1, RelativeLayout menu2) {
			this.menuhaut = menu1;
			this.menubas = menu1;
		}
		public void setBouttonStart(Button Start) {
			this.BStart = Start;
		}
	
		public void setTResult(TextView TResult) {
			this.TResult = TResult;
		}
	
	}

