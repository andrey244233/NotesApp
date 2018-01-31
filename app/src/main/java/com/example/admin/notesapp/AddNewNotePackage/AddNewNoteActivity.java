package com.example.admin.notesapp.AddNewNotePackage;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.admin.notesapp.R;
import com.example.admin.notesapp.ReturnDataCallBack;


import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.admin.notesapp.MainActivityPackage.MainActivityPresenter.NOTE_DATE;
import static com.example.admin.notesapp.MainActivityPackage.MainActivityPresenter.NOTE_TEXT;

public class AddNewNoteActivity extends AppCompatActivity implements ReturnDataCallBack {

    @BindView(R.id.btn_set_notification)
    Button btnSetNotification;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.ed_text)
    EditText edText;
    @BindView(R.id.tv_time_and_date)
    TextView tvTimeDate;
    Calendar calendar;
    String text;
    Date date = new Date();
    Long time;
    public static final int DATE_PICKER_DIALOG = 1;
    public static final int TIME_PICKER_DIALOG = 2;

    private int yearToSave;
    private int monthToSave;
    private int dayToSave;
    private int hour;
    private int minutes;
    private Callback callback;

    public interface Callback{
        void callingBack(String s);
    }

//    int year;
//    int month;
//    int


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        ButterKnife.bind(this);
        btnSetNotification.setOnClickListener(notificationOnclickListener);
        btnSubmit.setOnClickListener(submmitOnClickListener);
        calendar = Calendar.getInstance();

        Intent intent = getIntent();
        Log.v("tag", "get intent not null");
        text = intent.getStringExtra(NOTE_TEXT);
        Log.v("tag", "tetx" + text);
        date = intent.getParcelableExtra(NOTE_DATE);
        Log.v("tag", "date" + date);
        tvTimeDate.setText((CharSequence) date);
        edText.setText(text);

    }

    View.OnClickListener notificationOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setNotification();
        }
    };

    View.OnClickListener submmitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            submit();
        }
    };

    private void submit() {
       // Date date = new Date(1, 1,1);
    callback.callingBack(edText.getText().toString());
    }

    private void setNotification() {
        showDialog(DATE_PICKER_DIALOG);
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_DIALOG:
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showDialog(TIME_PICKER_DIALOG);
                    }
                });
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismissDialog(DATE_PICKER_DIALOG);
                        Log.v("tag", "negative button");
                    }
                });
                return datePickerDialog;

            case TIME_PICKER_DIALOG:
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewNoteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int time = hourOfDay;
                        int minutes = minute;
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvTimeDate.setText(time.toString() + minutes);
                    }
                });
                timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismissDialog(TIME_PICKER_DIALOG);
                        showDialog(DATE_PICKER_DIALOG);
                    }
                });
                return timePickerDialog;
        }
        return super.onCreateDialog(id);
    }

    @Override
    public void returnData() {

    }
}
