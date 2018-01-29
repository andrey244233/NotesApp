package com.example.admin.notesapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.admin.notesapp.Data.Note;

import java.util.ArrayList;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private Context mContext;
    private ArrayList<Note> listNotes;
    private TextView tvText, tvTimeForNotification;
    private CardView cardView;
    private Switch switchNotification;
    private ItemClickCallBack itemClickCallBack;
    private ContextMenuClickCallBack contextMenuClickCallBack;

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        mContext = context;
        listNotes  = notes;
    }

    //клик по айтему ресайклер вью
    public interface ItemClickCallBack {
        void onItemClick(int position, View v);
    }

    public void setItemClickCallBack(ItemClickCallBack itemClickCallBack) {
        this.itemClickCallBack = itemClickCallBack;
    }


    public interface ContextMenuClickCallBack{
        void onContextItemClick(int adapterPosition);
    }
    public void setContextManuClickCallBack(ContextMenuClickCallBack contextManuClickCallBack){
        this.contextMenuClickCallBack = contextManuClickCallBack;
    }




    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note currentNote = listNotes.get(position);
        tvTimeForNotification.setText(currentNote.getNotificationTime().toString());
        tvText.setText(currentNote.getText());
        boolean notify = currentNote.getNotification();
        switchNotification.setChecked(notify);
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public void setData(ArrayList<Note> listNotes) {
        this.listNotes = listNotes;
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        public NoteViewHolder(View itemView) {
            super(itemView);
            tvTimeForNotification = itemView.findViewById(R.id.tv_notification_time);
            tvText = itemView.findViewById(R.id.tvText);
            switchNotification = itemView.findViewById(R.id.switchNotification);
            cardView = itemView.findViewById(R.id.cardView);
            switchNotification.setOnClickListener(this);
            cardView.setOnClickListener(this);
            cardView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cardView:
                    itemClickCallBack.onItemClick(getAdapterPosition(), v);
                    break;
                case R.id.switchNotification:
                    itemClickCallBack.onItemClick(getAdapterPosition(), v);
                    break;
            }
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Выберите действие");
            menu.add(0, 0, 0,"редактировать");
            menu.add(0, 1, 1,"удалить");
//            menu.add("редактировать");
//            menu.add("удалить");
            contextMenuClickCallBack.onContextItemClick(getAdapterPosition());
        }
    }
}
