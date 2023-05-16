package com.example.rdvmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper myHelper;


    ListView lvRDV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHelper = new DatabaseHelper(this);
        myHelper.open();


        lvRDV = findViewById(R.id.lvRDV);
        lvRDV.setEmptyView(findViewById(R.id.tvEmpty));


        chargeData();
        registerForContextMenu(lvRDV);

        lvRDV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String idItem= ((TextView)view.findViewById(R.id.idRDV)).getText().toString();
                String titleItem= ((TextView)view.findViewById(R.id.title)).getText().toString();
                String dateItem= ((TextView)view.findViewById(R.id.date)).getText().toString();
                String timeItem= ((TextView)view.findViewById(R.id.time)).getText().toString();
                String contactItem= ((TextView)view.findViewById(R.id.Contact)).getText().toString();
                String PhonenumberItem= ((TextView)view.findViewById(R.id.Phonenumber)).getText().toString();
                String AddressItem= ((TextView)view.findViewById(R.id.address)).getText().toString();
                String isDoneItem= ((TextView)view.findViewById(R.id.isDone)).getText().toString();
                RDV pRdv= new RDV(timeItem,dateItem,titleItem,contactItem,PhonenumberItem,AddressItem,false);
                Intent intent = new Intent(getApplicationContext(), RDVDetails.class);
                intent.putExtra("SelectedRDV",pRdv);


                intent.putExtra("fromAdd",false);
                startActivity(intent);

            }

        });

    }




    public void chargeData(){
        final String[] from = new String[]{DatabaseHelper._ID,
                DatabaseHelper.TITLE, DatabaseHelper.RDATE, DatabaseHelper.RTIME,DatabaseHelper.CONTACT,  DatabaseHelper.PHONENUMBER, DatabaseHelper.ADDRESS,  DatabaseHelper.ISDONE};
        final int[]to= new int[]{R.id.idRDV,R.id.title,R.id.date,R.id.time,R.id.Contact,R.id.Phonenumber,R.id.address,R.id.isDone};

        Cursor c = myHelper.getAllRDV();
        SimpleCursorAdapter adapter= new SimpleCursorAdapter(this,R.layout.rdv_item_view,c,from,to,0);
        adapter.notifyDataSetChanged();
        lvRDV.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.rdv_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.new_rdv:{
                Intent intent=new Intent(this,RDVDetails.class);
                intent.putExtra("fromAdd", true);
                startActivity(intent);
                return true;

            }
            case R.id.search: {
                Toast.makeText(this, "Search", Toast.LENGTH_LONG).show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rdv_menu,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        if (item.getItemId()==R.id.delete){
            myHelper.delete(info.id);
            myHelper.reset();
            chargeData();
            return true;
        }
        return super.onContextItemSelected(item);
    }
    public void resetID() {
        if(isHelperEmpty()) {
            myHelper.reset(); // Reset the auto-increment counter to 1 for the RDV table
        }

    }
    public boolean isHelperEmpty() {
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_NAME, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count == 0;
    }


}