package com.example.admin.notesapp.AddNewNotePackage;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import com.example.admin.notesapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.example.admin.notesapp.MainActivityPackage.MainActivityPresenter.NOTE_DATE;
import static com.example.admin.notesapp.MainActivityPackage.MainActivityPresenter.NOTE_ID;
import static com.example.admin.notesapp.MainActivityPackage.MainActivityPresenter.NOTE_NOTIFICATION_ON;
import static com.example.admin.notesapp.MainActivityPackage.MainActivityPresenter.NOTE_TEXT;

public class AddNewNoteActivity extends AppCompatActivity implements DatePickerFragment.ReturnDateCallback, TimePickerDialog.ReturnTimeCallBack {

    @BindView(R.id.ed_text)
    EditText edText;
    @BindView(R.id.tv_time_and_date)
    TextView tvTimeDate;
    @BindView(R.id.switch_notification)
    Switch switchNotification;
    @BindView(R.id.btn_set_notification)
    Button btnNotification;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    Calendar calendar;
    String text;
    Date date = new Date();
    String id = "";
    Boolean notificationOn = false;

    AddEditNoteActivityPresenter addEditNoteActivityPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        ButterKnife.bind(this);
        switchNotification.setOnCheckedChangeListener(switchNotificationListener);
        calendar = Calendar.getInstance();
        setTitle("Создать заметку");
        addEditNoteActivityPresenter = new AddEditNoteActivityPresenter();

        if (getIntent().hasExtra(NOTE_TEXT)) {
            setTitle("Редактировать заметку");
            btnSubmit.setText("Редактировать заметку");
            id = getIntent().getStringExtra(NOTE_ID);
            text = getIntent().getStringExtra(NOTE_TEXT);
            date = (Date) getIntent().getSerializableExtra(NOTE_DATE);
            notificationOn = getIntent().getBooleanExtra(NOTE_NOTIFICATION_ON, false);
            edText.setText(text);
            tvTimeDate.setText(date.toString());
            Log.v("tag", "hasExtra");
            Log.v("tag", "text" + text + " id" + id + " date " + date.toString() + notificationOn + notificationOn.toString() );
        }

        if(!notificationOn){
            Log.v("tag", "make it visible");
            tvTimeDate.setVisibility(View.GONE);
            btnNotification.setVisibility(View.GONE);
        }
        Log.v("tag", " don't hasExtra");
        Log.v("tag", "text" + text + " id" + id + " date " + date.toString() + notificationOn + notificationOn.toString() );
    }

    CompoundButton.OnCheckedChangeListener switchNotificationListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  if(isChecked){
                      tvTimeDate.setVisibility(View.VISIBLE);
                      btnNotification.setVisibility(View.VISIBLE);
                      notificationOn = true;
                      Log.v("tag", " is checked notification =" + notificationOn);
                  }else{
                      tvTimeDate.setVisibility(View.GONE);
                      btnNotification.setVisibility(View.GONE);
                      notificationOn = false;
                      Log.v("tag", " is checked notification =" + notificationOn);
                  }
        }
    };

    private void openDatePickerDialog() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    private void openTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog();
        timePickerDialog.show(getSupportFragmentManager(), "TimePicker");
    }

    public void submitNote(View view) {
        if(id.isEmpty()){
            Log.v("tag", "id is empty =" + id + "text =" + text);
            addEditNoteActivityPresenter.addNewNoteToRealm(edText.getText().toString(), date, notificationOn, this);
        }else{
            Log.v("tag", "id is not empty=" + id + "text =" + text);
            addEditNoteActivityPresenter.editNoteInRealm(id, edText.getText().toString(), date, notificationOn, this);
        }
    }

    public void setNotification(View view) {
        openDatePickerDialog();
    }

    @Override
    public void returnDate(Date date) {
        this.date = date;
        openTimePickerDialog();
    }

    @Override
    public void returnTime(int hours, int minutes) {
        date.setHours(hours);
        date.setMinutes(minutes);
        SimpleDateFormat sdf = new SimpleDateFormat( "Дата: dd'/'MM'/'yy 'Время:' HH:mm ");
        tvTimeDate.setText(sdf.format(date));
    }
}