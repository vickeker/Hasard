package keker.hasard;

import android.os.Parcel;
import android.os.Parcelable;



public class Item implements Parcelable {
	 private String Name;
	 
	 
	    public Item(){}
	    
	    public Item(String Name)
	    {
	        this.Name = Name;
	       
	    }
	 
	    public void setName(String Name){ this.Name = Name;}

	 
	    public String getName(){return this.Name;}

	    
    public Item(Parcel in)
    {
        this.getFromParcel(in);
    }
 
    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public Item createFromParcel(Parcel in)
        {
            return new Item(in);
        }
 
        @Override
        public Object[] newArray(int size) {
            return null;
        }
    };
 
    @Override
    public int describeContents() {
        return 0;
    }
 
    //On ecrit dans le parcel les données de notre objet
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.Name);
  
    }
 
    //On va ici hydrater notre objet à partir du Parcel
    public void getFromParcel(Parcel in)
    {
        this.setName(in.readString());
  
    }
}