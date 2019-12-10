package com.durvasa.projectkaya;

//import android.support.v7.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.durvasa.projectkaya.pojo.Measures;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextView t1, genderTv;
    TextInputLayout l1, l2, l3, l4, l5;
    TextInputEditText e1, e2, e3, e4, e5;
    Button btn;
    private String key;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        genderTv = findViewById(R.id.gendertv);
        radioGroup = findViewById(R.id.rgGender);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    Toast.makeText(MainActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                } else if (rb.isChecked()) {
                    Toast.makeText(MainActivity.this, "Select Gender First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        t1 = findViewById(R.id.Headingtv);
        l1 = findViewById(R.id.NameInput);
        l2 = findViewById(R.id.AgeInput);
        l3 = findViewById(R.id.heightInput);
        l4 = findViewById(R.id.weightInput);
        l5 = findViewById(R.id.waistInput);
        e1 = findViewById(R.id.NameEdittext);
        e2 = findViewById(R.id.AgeEdittext);
        e3 = findViewById(R.id.HeightEdittext);
        e4 = findViewById(R.id.WeightEdittext);
        e5 = findViewById(R.id.WaistEdittext);
        btn = findViewById(R.id.btnMeasure);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Users users = new Users();
                users.setName(e1.getText().toString());
                users.setAge(e2.getText().toString());
                users.setHeight(e3.getText().toString());
                users.setWeight(e4.getText().toString());
                users.setWaist(e5.getText().toString());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("Users");
                if (key == null) {
                    DatabaseReference myref = database.getReference("users").push();
                    myref.setValue(users);
                } else {
                    DatabaseReference myref = database.getReference("users").child(key);
                    myref.setValue(users);
                }
                if (e1.getText().toString().trim().length() <= 0 && e2.getText().toString().trim().length() <= 0 && e3.getText().toString().trim().length() <= 0 && e4.getText().toString().trim().length() <= 0) {
                    e1.setError("Required Field!");
                    e2.setError("Required Field!");
                    e3.setError("Required Field!");
                    e4.setError("Required Field!");
                    e5.setError("Required Field!");

                } else {
                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                    Bundle b = new Bundle();

                    b.putString("Name", e1.getText().toString());
                    b.putInt("Age", Integer.parseInt(e2.getText().toString()));
                    b.putDouble("Height", Double.parseDouble(e3.getText().toString()));
                    b.putInt("Weight", Integer.parseInt(e4.getText().toString()));
                    b.putDouble("Waist", Double.parseDouble(e5.getText().toString()));

                    intent.putExtras(b);

                    //Start DisplayActivity
                    startActivity(intent);


                    // ref.setValue(e1);
                    // ref.setValue(e2);
                    // ref.setValue(e3);
                    // ref.setValue(e4);
                    // ref.setValue(e5);

                }


            }
        });

    }

}
