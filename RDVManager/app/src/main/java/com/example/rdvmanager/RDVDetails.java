package com.example.rdvmanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class RDVDetails extends AppCompatActivity {
    private DatabaseHelper myHelper;
    EditText etTitle;
    EditText etDate;

    EditText etAddress;
    EditText etContact;
    EditText etphoneNumber;
    EditText etisDone;
    EditText etTime;
    boolean fromAdd;

    TextView tvId;

    public void onCancelClick(View v) {
        //Intent intent = new Intent(this,MainActivity.class);
        // startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv_details);


        tvId = (TextView) findViewById(R.id.tvId);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etDate = (EditText) findViewById(R.id.etDate);
        etTime = (EditText) findViewById(R.id.etTime);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etContact = (EditText) findViewById(R.id.etContact);
        etphoneNumber = (EditText) findViewById(R.id.etphoneNumber);
        etisDone = (EditText) findViewById(R.id.etisDone);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");

            }
        });

        myHelper = new DatabaseHelper(this);
        myHelper.open();
        Intent intent = getIntent();
        fromAdd = intent.getBooleanExtra("fromAdd", false);
        if(!fromAdd){
            Bundle b= intent.getExtras();
            if (b != null) {

                RDV SelectedRDV = b.getParcelable("SelectedRDV");
                tvId.setText(String.valueOf(SelectedRDV.getId()));
                etTitle.setText(SelectedRDV.getTitle());
                etDate.setText(SelectedRDV.getDate());
                etTime.setText(SelectedRDV.getTime());
                etContact.setText(SelectedRDV.getContact());
                etphoneNumber.setText(SelectedRDV.getPhoneNumber());
                etAddress.setText(SelectedRDV.getAddress());
                //etisDone.setText(SelectedRDV.isDone());

            }
        }

    }

    public void saveRDV(View view) {


        String title = etTitle.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phoneNumber = etphoneNumber.getText().toString().trim();
        String rdvIsDone = etisDone.getText().toString().trim();

        if(fromAdd) {

            if (title.isEmpty() || date.isEmpty() || time.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Please enter values for all required fields", Toast.LENGTH_SHORT).show();
            return;
            }
            else {
                RDV rdv = new RDV(title, date, time, contact, address, phoneNumber, false);
                myHelper.add(rdv);

                Intent main = new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
        }
        }
        else {

            long id = Long.parseLong(tvId.getText().toString());
            RDV rdv = new RDV(id, title, date, time, contact, phoneNumber, address, rdvIsDone.equals("1"));
            int n = myHelper.update(rdv);
            if (n > 0) {
                Toast.makeText(this, "RDV updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to update RDV", Toast.LENGTH_SHORT).show();
            }

            Intent main = new Intent(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }

    }
}
