package com.example.admin.notesapp.AddNewNotePackage;


import android.app.Dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerDialog extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {

    ReturnTimeCallBack returnTimeCallBack;

    interface ReturnTimeCallBack{
        void returnTime(int hours, int minutes);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int minutes = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        return new android.app.TimePickerDialog(getActivity(), this, hour, minutes, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        returnTimeCallBack = (ReturnTimeCallBack) getActivity();
        returnTimeCallBack.returnTime(hourOfDay, minute);
    }
}
