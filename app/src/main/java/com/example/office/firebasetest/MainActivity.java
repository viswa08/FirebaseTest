package com.example.office.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    final DatabaseReference temperature = myRef.child("sensor1").child("temperature");
    final DatabaseReference minimumTemperature = myRef.child("minimum temperature").child("value");
    final DatabaseReference maximumTemperature = myRef.child("maximum temperature").child("value");


    TextView temperatureText1;
    EditText minEditTextVal;
    EditText maxEditTextVal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperatureText1 = (TextView)findViewById(R.id.temperatureText1);
        minEditTextVal = (EditText)findViewById(R.id.minEditText);
        maxEditTextVal = (EditText)findViewById(R.id.maxEditText);



        //test the use of edit text (minimum) - get value, store and display as toast
        minEditTextVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String minStr = minEditTextVal.getText().toString();
                int finalMinStr = Integer.parseInt(minStr);
                minimumTemperature.setValue(finalMinStr);
                Toast.makeText(MainActivity.this,minStr,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        //test the use of edit text (maximum) - get value, store and display as toast
        maxEditTextVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String maxStr = maxEditTextVal.getText().toString();
                int finalMaxStr = Integer.parseInt(maxStr);
                maximumTemperature.setValue(finalMaxStr);
                Toast.makeText(MainActivity.this,maxStr,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //Cursor placement


        // Read from the database


        temperature.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Long value = dataSnapshot.getValue(Long.class);
                String strValue = Long.toString(value);
                Log.d("file", "Value is: " +strValue);
                temperatureText1.setText(strValue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("file", "failed to read value");

            }
        });
    }
}
