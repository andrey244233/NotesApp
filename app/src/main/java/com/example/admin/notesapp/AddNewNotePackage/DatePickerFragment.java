package com.example.admin.notesapp.AddNewNotePackage;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

    ReturnDateCallback returnDateCallback;

    interface ReturnDateCallback{
        void returnDate(Date date);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new android.app.DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Date date = new Date();
        date.setYear(year);
        date.setMonth(month);
        date.setDate(dayOfMonth);
        returnDateCallback = (ReturnDateCallback) getActivity();
        returnDateCallback.returnDate(date);
    }
}
