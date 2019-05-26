package com.example.monkeyfied;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class Main3Activity extends AppCompatActivity {
    String id,name,sci,loc,info;
    ImageView imageview;
    TextView textname;
    TextView textid;
    TextView textsci;
    TextView textloc;
    TextView textinfo;
    Button button;
    Cursor c=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        textname=(TextView) findViewById(R.id.text_name);
        textid=(TextView) findViewById(R.id.text_id);
        textsci=(TextView) findViewById(R.id.text_sci);
        textloc=(TextView) findViewById(R.id.text_loc);
        textinfo=(TextView) findViewById(R.id.text_info);
        imageview=(ImageView) findViewById(R.id.image_view);
        button=(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAgain();
            }
        });
        String monkey_type = getIntent().getStringExtra("type");
        String image_name=monkey_type.replaceAll(" ","").toLowerCase();
        imageview.setImageResource(getResources().getIdentifier(image_name, "drawable", getPackageName()));
        textname.setText(monkey_type);
        Log.i("name",monkey_type);
//        myDatabase.execSQL("DROP TABLE IF EXISTS tbl_monkey");
//        myDatabase.execSQL("DROP TABLE IF EXISTS tbl_monkey1");
//        Log.i("delt","delted");
//        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS tbl_monkey(id VARCHAR,name VARCHAR,sci VARCHAR,loc VARCHAR,info VARCHAR)");
//        Log.i("creat","created");
//        myDatabase.execSQL("INSERT INTO tbl_monkey (id,name,sci,loc,info) VALUES('101','Mantled Howler','Alouatta palliata','Costa Rica','golden-mantled howling monkey, is a species of howler monkey, a type of New World monkey, from Central and South America. It is one of the monkey species most often seen and heard in the wild in Central America')");

//        myDatabase.execSQL("INSERT INTO tbl_monkey (id,name,sci,loc,info) VALUES('103','Bald Uakari','Cacajao calvus','Brazil','The bald uakari (Cacajao calvus) or bald-headed uakari is a small New World monkey characterized by a very short tail')");
//        myDatabase.execSQL("INSERT INTO tbl_monkey (id,name,sci,loc,info) VALUES('104','Japanese Macaque','Macaca fuscata','Japan','The Japanese macaque (/məˈkɑːk/;[3] Macaca fuscata), also known as the snow monkey, ')");
//        myDatabase.execSQL("INSERT INTO tbl_monkey (id,name,sci,loc,info) VALUES('105','Pygmy Marmoset','Cebuella pygmaea','Amazon','The pygmy marmoset (Cebuella pygmaea) is a small species')");
//        myDatabase.execSQL("INSERT INTO tbl_monkey (id,name,sci,loc,info) VALUES('106','White Capuchin','Cebus imitator','North America','The Panamanian white-faced capuchin (Cebus imitator), also known as the Panamanian white-headed capuchin or Central American white-faced capuchin, ')");
//        myDatabase.execSQL("INSERT INTO tbl_monkey (id,name,sci,loc,info) VALUES('107','Silvery Marmoset','Mico argentatus','Brazil','The silvery marmoset (Mico argentatus) is a New World monkey that lives in the eastern Amazon Rainforest in Brazil .')");
//        myDatabase.execSQL("INSERT INTO tbl_monkey (id,name,sci,loc,info) VALUES('108','Squirrel Monkey','Saimiri oerstedii','Costa Rica','The Central American squirrel monkey or (Saimiri oerstedii),')");
//        myDatabase.execSQL("INSERT INTO tbl_monkey (id,name,sci,loc,info) VALUES('109','Black Monkey','Aotus nigriceps','Peru','The black-headed night monkey is a night monkey species from South America. It is found in Bolivia, Brazil and Peru.')");
//        myDatabase.execSQL("INSERT INTO tbl_monkey (id,name,sci,loc,info) VALUES('110','Nilgiri langur','Semnopithecus johnii','Karnataka','The Nilgiri langur (Semnopithecus johnii)[3] is a langur (a type of Old World monkey) found in the Nilgiri Hills of the Western Ghats in South India.')");
//        Log.i("inert","Inserted");
        SQLiteDatabase myDatabase = this.openOrCreateDatabase("Monkey",MODE_PRIVATE, null);
//        myDatabase.execSQL("INSERT INTO tbl_monkey (id,name,sci,loc,info) VALUES('102','Patas Monkey','Erythrocebus patas','Africa','The patas monkey (Erythrocebus patas), also known as the wadi monkey or hussar monkey, is a ground-dwelling monkey distributed over semi-arid areas of West Africa, and into East Africa. pecies, was resurrected in 2018.')");
        Log.i("monkey",monkey_type);
        Log.i("query","SELECT * FROM tbl_monkey WHERE TRIM(name)='" + monkey_type.trim() +"'");
        c=myDatabase.rawQuery("SELECT * FROM tbl_monkey WHERE TRIM(name)='" + monkey_type.trim() +"'",null);
//        if (c!=null && c.getCount()>0){
//            if(c.moveToFirst())
//            {
//                id=c.getString(c.getColumnIndex("id"));
//                name=c.getString(c.getColumnIndex("name"));
//                sci=c.getString(c.getColumnIndex("sci"));
//                loc=c.getString(c.getColumnIndex("loc"));
//                info=c.getString(c.getColumnIndex("info"));
//                Log.i("sci",sci);
//            }
//            c.close();
//        }
        c.moveToFirst();
        id = c.getString(c.getColumnIndex("id"));
        name = c.getString(c.getColumnIndex("name"));
        sci = c.getString(c.getColumnIndex("sci"));
        loc = c.getString(c.getColumnIndex("loc"));
        info = c.getString(c.getColumnIndex("info"));
        textid.setText(id);
        textsci.setText(sci);
        textloc.setText(loc);
        textinfo.setText(info);
    }
    public void searchAgain()
    {
        Intent myIntent=new Intent(this,Main2Activity.class);
        startActivity(myIntent);
    }

}
