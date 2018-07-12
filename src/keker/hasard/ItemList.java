package keker.hasard;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemList extends ArrayList<Item> implements Parcelable
{
    public ItemList()
    {
 
    }
 
    public ItemList(Parcel in)
    {
        this.getFromParcel(in);
    }
 
    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public ItemList createFromParcel(Parcel in)
        {
            return new ItemList(in);
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
 
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        //Taille de la liste
        int size = this.size();
        dest.writeInt(size);
        for(int i=0; i < size; i++)
        {
            Item item = this.get(i); //On vient lire chaque objet Item
            dest.writeString(item.getName());
        }
    }
 
    public void getFromParcel(Parcel in)
    {
        // On vide la liste avant tout remplissage
        this.clear();
 
        //Récupération du Namebre d'objet
        int size = in.readInt();
 
        //On repeuple la liste avec de nouveau objet
        for(int i = 0; i < size; i++)
        {
            Item item = new Item();
            item.setName(in.readString());
            this.add(item);
        }
 
    }
}
