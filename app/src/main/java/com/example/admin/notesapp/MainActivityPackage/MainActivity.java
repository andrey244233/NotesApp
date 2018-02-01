package com.example.admin.notesapp.MainActivityPackage;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.admin.notesapp.Data.Note;

import com.example.admin.notesapp.MainActivityPackage.MainActivityInterface;
import com.example.admin.notesapp.MainActivityPackage.MainActivityPresenter;
import com.example.admin.notesapp.NoteAdapter;
import com.example.admin.notesapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NoteAdapter.ItemClickCallBack, NoteAdapter.ContextMenuClickCallBack, MainActivityInterface {

    @BindView(R.id.note_recyclre_view)
    RecyclerView noteRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    int adapterPosition;
    MainActivityPresenter mainActivityPresenter;
    ArrayList<Note> notes = new ArrayList<>();
    NoteAdapter noteAdapter;
    //int ID = 0;
    public static final int CONTEXT_MENU_EDIT = 0;
    public static final int CONTEXT_MENU_DELETE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);
        //setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        //беру данные из реалма и показываю их в ресайклвью
        mainActivityPresenter = new MainActivityPresenter(this);
        mainActivityPresenter.getNotesFromRealm();

        noteAdapter = new NoteAdapter(this, notes);
        noteAdapter.setItemClickCallBack(this);

        noteAdapter.setContextManuClickCallBack(this);
        noteRecyclerView.setAdapter(noteAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        noteRecyclerView.setLayoutManager(linearLayoutManager);
        makeFloatActionButtonHideAndShow();
        fab.setOnClickListener(fabOnClickListener);
        registerForContextMenu(noteRecyclerView);

        checkNotes();
        //ID = notes.size();
    }

    //@Override
    public void makeFloatActionButtonHideAndShow() {
        noteRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {
                    fab.show();
                } else if (dy > 0) {
                    fab.hide();
                }
            }
        });
    }

    @Override
    public void onItemClick(int position, View v) {

    }

    View.OnClickListener fabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addNewNote();
        }
    };

    @Override
    public void returnNotesFromRealm(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case CONTEXT_MENU_EDIT:
                editNote();
                break;
            case CONTEXT_MENU_DELETE:
                deleteNote();
                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onContextItemClick(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    private void checkNotes() {
        for (Note n : notes) {
            Log.v("tag", "Note name = " + n.getText() + "note Id =" + n.getId());
        }
    }

    public void addNewNote() {
        mainActivityPresenter.addNewNote(MainActivity.this);
    }

    public void editNote() {
        Note note = notes.get(adapterPosition);
        String id = note.getId();
        String text = note.getText();
        Date date = note.getNotificationTime();
        Boolean notification = note.getNotification();
        mainActivityPresenter.editNoteById(id, text, date, notification, MainActivity.this);
    }

    public void deleteNote() {
        Note note1 = notes.get(adapterPosition);
        mainActivityPresenter.deleteNoteById(note1.getId());
    }

    public void showAllNotes() {
        mainActivityPresenter.getNotesFromRealm();
        noteAdapter.swap(notes);
        noteRecyclerView.setAdapter(noteAdapter); // но это не точно
        checkNotes();
    }


}
