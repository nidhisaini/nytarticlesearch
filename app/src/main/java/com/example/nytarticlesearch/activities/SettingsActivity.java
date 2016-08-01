package com.example.nytarticlesearch.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
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

import com.example.nytarticlesearch.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

/*Spinner
*http://guides.codepath.com/android/Working-with-Input-Views#spinners
*https://developer.android.com/guide/topics/ui/controls/spinner.html
*http://pulse7.net/android/android-spinner-dropdown-list-example/
* escape sequence, \", on the interior quotes.
* https://docs.oracle.com/javase/tutorial/java/data/characters.html
* */
public class SettingsActivity extends AppCompatActivity {
    Spinner spSortOrder;
    Button btnSave;
    ArrayAdapter<CharSequence> adapter;
    String itemSortOrder;
    CheckBox cbArt;
    CheckBox cbFashionStyle;
    CheckBox cbSports;
    EditText etBeginDate;
    EditText etEndDate;


    CompoundButton.OnCheckedChangeListener checkListener;
    StringBuffer checkboxString = new StringBuffer() ;

    int day_x;
    int month_x;
    int year_x;
    static final int DIALOG_BEGIN_DATEID = 0;
    static final int DIALOG_END_DATE_ID = 1;
    String beginDate;
    String endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etEndDate = (EditText) findViewById(R.id.etEndDate);
        etBeginDate =(EditText) findViewById(R.id.etBeginDate);
        btnSave = (Button) findViewById(R.id.btnSave);

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x =cal.get(Calendar.MONTH);
        day_x =cal.get(Calendar.DAY_OF_MONTH);



        etBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialogOnEditButtonClick();
                showDialog(DIALOG_BEGIN_DATEID);
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_END_DATE_ID);
            }
        });


        //code for adding the checkboxes

        spSortOrder = (Spinner) findViewById(R.id.spSortOrder);
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
                itemSortOrder = adapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //on save button click
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SearchArticleActivity.class);
                i.putExtra("sortOrder", itemSortOrder);
                i.putExtra("checkboxValue", checkboxString.toString());
                i.putExtra("beginDate", beginDate);
                i.putExtra("endDate", endDate);
                startActivity(i);
            }
        });

    }


    @Override
    protected Dialog onCreateDialog(int id){
        if(id == DIALOG_BEGIN_DATEID)
            return new DatePickerDialog(this, dpickerListner, year_x, month_x,day_x);
        if(id == DIALOG_END_DATE_ID)
            return new DatePickerDialog(this, dpickerEndListner, year_x, month_x,day_x);
        return null;

    }

    private DatePickerDialog.OnDateSetListener dpickerListner
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String month ="";
            String day="";
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;

            if(month_x <10){
                NumberFormat f = new DecimalFormat("00");
                month = (f.format(month_x));
            }
            else{
                month = String.valueOf(month_x);
            }
            if(day_x <10){
                NumberFormat f = new DecimalFormat("00");
                day = (f.format(day_x));
            }
            else{
                day = String.valueOf(day_x);
            }
            beginDate = String.valueOf(year_x + "" + month + "" + day);

            etBeginDate.setText(beginDate);
                    }
    };


    private DatePickerDialog.OnDateSetListener dpickerEndListner
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String month ="";
            String day="";
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;

            if(month_x <10){
                NumberFormat f = new DecimalFormat("00");
                month = (f.format(month_x));
            }
            else{
                month = String.valueOf(month_x);
            }
            if(day_x <10){
                NumberFormat f = new DecimalFormat("00");
                day = (f.format(day_x));
            }
            else{
                day = String.valueOf(day_x);
            }
            endDate = String.valueOf(year_x + "" + month + "" + day);

            etEndDate.setText(endDate);
        }
    };



}
