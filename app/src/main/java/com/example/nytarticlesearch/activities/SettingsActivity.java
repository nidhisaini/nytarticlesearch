package com.example.nytarticlesearch.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nytarticlesearch.R;

/*Spinner
*http://guides.codepath.com/android/Working-with-Input-Views#spinners
*https://developer.android.com/guide/topics/ui/controls/spinner.html
*http://pulse7.net/android/android-spinner-dropdown-list-example/
* escape sequence, \", on the interior quotes.
* https://docs.oracle.com/javase/tutorial/java/data/characters.html
* */
public class SettingsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Spinner spSortOrder;
    Button btnSave;
    ArrayAdapter<CharSequence> adapter;
    String itemSortOrder;
    CheckBox cbArt;
    CheckBox cbFashionStyle;
    CheckBox cbSports;
    EditText etBeginDate;

    CompoundButton.OnCheckedChangeListener checkListener;
    StringBuffer checkboxString = new StringBuffer() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        etBeginDate =(EditText) findViewById(R.id.etBeginDate);
        btnSave = (Button) findViewById(R.id.btnSave);
        spSortOrder = (Spinner) findViewById(R.id.spSortOrder);

        //code for adding the checkboxes
        cbArt = (CheckBox) findViewById(R.id.cbArt);
        cbArt.setChecked(false);
        cbArt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbArt.setChecked(true);
                    checkboxString = checkboxString.append("\"Art\" ");
                } else {
                    cbArt.setChecked(false);
                }
            }
        });
        cbFashionStyle = (CheckBox) findViewById(R.id.cbFashionStyle);
        cbFashionStyle.setChecked(false);
        cbFashionStyle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbFashionStyle.setChecked(true);
                    checkboxString = checkboxString.append("\"Fashion & Style\" ");
                } else {
                    cbFashionStyle.setChecked(false);
                }
            }
        });
        cbSports = (CheckBox) findViewById(R.id.cbSports);
        cbSports.setChecked(false);
        cbSports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbSports.setChecked(true);
                    checkboxString = checkboxString.append("\"Sports\"");
                } else {
                    cbSports.setChecked(false);
                }
            }
        });


        //spinner
        adapter =
                ArrayAdapter.createFromResource(this, R.array.sortorder_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSortOrder.setAdapter(adapter);

        spSortOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemSortOrder = adapter.getItem(position).toString();// .getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(), itemSortOrder, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //on save button click
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                i.putExtra("sortOrder", itemSortOrder);
                i.putExtra("checkboxValue", checkboxString.toString());
                Toast.makeText(getApplicationContext(), checkboxString, Toast.LENGTH_LONG).show();
                startActivity(i);
            }
        });



    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        etBeginDate.setText(date);
    }
}
