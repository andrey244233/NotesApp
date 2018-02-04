package com.example.admin.notesapp.MainActivityPackage;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.notesapp.Data.Note;

import com.example.admin.notesapp.MainActivityPackage.MainActivityInterface;
import com.example.admin.notesapp.MainActivityPackage.MainActivityPresenter;
import com.example.admin.notesapp.NoteAdapter;
import com.example.admin.notesapp.R;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH;

public class MainActivity extends AppCompatActivity implements NoteAdapter.ItemClickCallBack, NoteAdapter.ContextMenuClickCallBack, MainActivityInterface {

    @BindView(R.id.note_recyclre_view)
    RecyclerView noteRecyclerView;
    @BindView(R.id.fb_open_settings)
    com.getbase.floatingactionbutton.FloatingActionButton fabOpenSettings;
    @BindView(R.id.fb_exit)
    com.getbase.floatingactionbutton.FloatingActionButton fabExit;
    @BindView(R.id.multiple_actions)
    com.getbase.floatingactionbutton.FloatingActionsMenu floatingActionsMenu;

    int adapterPosition;
    MainActivityPresenter mainActivityPresenter;
    ArrayList<Note> notes = new ArrayList<>();
    NoteAdapter noteAdapter;
    public static final int CONTEXT_MENU_EDIT = 0;
    public static final int CONTEXT_MENU_DELETE = 1;
    public static final int REQ_CODE_SPEECH_INPUT = 100;
    SpeechRecognizer sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);
        //setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //    floatingActionsMenu = findViewById(R.id.multiple_actions);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("ggggggg");
        //беру данные из реалма и показываю их в ресайклвью
        mainActivityPresenter = new MainActivityPresenter(this);
        mainActivityPresenter.getNotesFromRealm();

        noteAdapter = new NoteAdapter(this, notes);
        noteAdapter.setItemClickCallBack(this);

        noteAdapter.setContextManuClickCallBack(this);
        noteRecyclerView.setAdapter(noteAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        noteRecyclerView.setLayoutManager(linearLayoutManager);
        // makeFloatActionButtonHideAndShow();
        registerForContextMenu(noteRecyclerView);
        checkNotes();

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
                    floatingActionsMenu.collapse();
                    floatingActionsMenu.setVisibility(View.VISIBLE);
                } else if (dy > 0) {
                    floatingActionsMenu.collapse();
                    floatingActionsMenu.setVisibility(View.INVISIBLE);
                    //fab.hide();
                }
            }
        });
    }

    @Override
    public void onItemClick(int position, View v) {
        switch (v.getId()) {
            case R.id.cardView:
                Log.v("tag", "cardViewClicked");
                break;
            case R.id.switchNotification:
                Log.v("tag", "swich nitif");
                break;
            case R.id.btn_delete:
                Log.v("tag", "btnDeletE clicked, position = " + position);
                break;

        }
    }

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
        createToast("Заметка удалена успешно", getResources().getDrawable(R.drawable.add_png));
    }

    public void addNewNote(String text) {
        mainActivityPresenter.addNewNote(MainActivity.this, text);
    }

//    public void addNewNoteByVoice() {
//        mainActivityPresenter.addNewNoteByVoice(this);
//    }

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


    public void floatButtonsOnClick(View v) {
        switch (v.getId()) {
            case R.id.fb_add_by_text:
                Log.v("tag", "on click text)");
                addNewNote();
                break;
            case R.id.fb_add_by_voice:
                Log.v("tag", "on click)");
                mainActivityPresenter.addNewNoteByVoice(this);
                break;
            case R.id.fb_more:
                fabOpenSettings.setVisibility(fabOpenSettings.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                fabExit.setVisibility(fabExit.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                break;
            case R.id.fb_open_settings:
                Log.v("tag", "open settings");
                break;
            case R.id.fb_exit:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                Log.v("tag", "on activity result, request code = "  + requestCode);
                if (resultCode == RESULT_OK && null != data) {
                   // String text = data.getStringExtra(RecognizerIntent.EXTRA_RESULTS);
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.v("tag", "text =" + result);
                    mainActivityPresenter.addNewNote(this, result.get(0));
                    //addNewNote(text);

                }
            }
            break;
        }
    }

    private void createToast(String text, Drawable image){

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));


        View v = getLayoutInflater().inflate(R.layout.toast_add, linearLayout, true);
        TextView textView = v.findViewById(R.id.toast_text_view);
        textView.setText(text);
        ImageView imageView = v.findViewById(R.id.toast_image_view);
        imageView.setImageDrawable(image);

        Toast customToast = new Toast(this);
        customToast.setGravity(Gravity.CENTER, 0, 0);
        customToast.setView(v);
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                noteAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                noteAdapter.filter(newText);
                return true;
            }
        };
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
