package sg.edu.np.mad.madpractical;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataBaseHelper extends SQLiteOpenHelper {

    //////// refactored value for multiple uses/////////////////


    public static final String ID = "ID";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String FOLLOWED = "FOLLOWED";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String NAME = "NAME";


    public DataBaseHelper(@Nullable Context context)

    {
        super(context, "user.db", null, 1);
    }

    //this is called the first time a database is accessed.There should be in here to create a new database.//
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement= "CREATE TABLE " + USER_TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                FOLLOWED + " INTEGER" +")";

        db.execSQL(createTableStatement);



    }
    //this is called if the database version number changes .It prevents previous users apps from breaking when you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists "+USER_TABLE);
    }

    //////////this method gets back a Cart item and adds it to the database///////////////
    public boolean addOne(User user){

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(NAME ,user.getMsg());
        cv.put(DESCRIPTION ,user.getDesc());
        cv.put(FOLLOWED, user.isFollowed() ? 1 : 0);

        long insert = db.insert(USER_TABLE , null, cv);
        if(insert==-1){
            return false;
        }
        else {
            return true;
        }

    }




    //////method deletes the item in the cart frm the database///////////
//    public boolean deleteOne(Cart_item itemModel){
//        //find itemModel in the database . if it is found , delete it and return true.
//        //if it not found ,return false
//        SQLiteDatabase db=this.getWritableDatabase();
//        String queryString="DELETE FROM "+ CART_ITEM_TABLE  +" WHERE "+ ID  +" = "+  itemModel.getId();
//        Cursor cursor = db.rawQuery(queryString, null);
//
//        if(cursor.moveToFirst()){
//            return true;
//        }
//        else {
//            return false;
//        }
//    }



    ////////////////method is used to retrive all the information in the cart and return a checkout_cart_details item which has an arraylist and a double as parameters . the array list has all the cart items and the double stores the total price of all the items in the cart///////////////
    public ArrayList<User> getUsers(){
        ArrayList<User> returnList =new ArrayList<>();
        User user=new User();

        //GET DATA FROM DATABASE

        String queryString="SELECT * FROM "+USER_TABLE;
        SQLiteDatabase db =this.getReadableDatabase();
        //getwritabledatabase locks the data file so other processes may not access it
        Cursor cursor = db.rawQuery(queryString,null);
        //cursor is the result set from SQL statement

        if(cursor.moveToFirst()){
            //loop through the cursor (result set) and create new customer objects.Put them into the return list.
            do {
                int ID=cursor.getInt(0);
                String Name=cursor.getString(1);
                String Description=cursor.getString(2);
                int Followed=cursor.getInt(3);

                if (Followed==1){
                     user =new User(ID,Name,Description,false);
                } else if (Followed==0) {
                     user =new User(ID,Name,Description,true);
                }



                returnList.add(user);
            }while(cursor.moveToNext());

        }
        else{
            //failure .do not add anything to the list.

        }
        cursor.close();
        db.close();
        return returnList;

    };

    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, user.getMsg());
        cv.put(DESCRIPTION, user.getDesc());
        cv.put(FOLLOWED, user.isFollowed() ? 1 : 0);

        int rowsAffected = db.update(USER_TABLE, cv, ID + "=?", new String[]{String.valueOf(user.getID())});
        db.close();
        return rowsAffected > 0;
    }


    public User findUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {ID, NAME, DESCRIPTION, FOLLOWED};
        String selection = ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(USER_TABLE, projection, selection, selectionArgs, null, null, null);

        User user = null;

        if (cursor.moveToFirst()) {
            int userID = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(NAME));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION));
            int followed = cursor.getInt(cursor.getColumnIndexOrThrow(FOLLOWED));

            boolean isFollowed = (followed == 1);

            user = new User(userID, name, description, isFollowed);
        }

        cursor.close();
        db.close();

        return user;
    }
}