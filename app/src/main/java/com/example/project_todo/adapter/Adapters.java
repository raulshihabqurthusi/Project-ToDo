package com.example.project_todo.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_todo.MainActivity;
import com.example.project_todo.R;
import com.example.project_todo.ViewActivity;
import com.example.project_todo.database.Database;

import java.util.ArrayList;

public class Adapters extends RecyclerView.Adapter<Adapters.AdaptersViewHolders> {
    private Context context;
    private Activity activity;
    private ArrayList id, title, desc;
    private Database database;

    public Adapters(Context context, Activity activity, ArrayList id, ArrayList title, ArrayList desc) {
        this.context = context;
        this.activity = activity;
        this.id = id;
        this.title = title;
        this.desc = desc;
    }

    @NonNull
    @Override
    public AdaptersViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        return new AdaptersViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptersViewHolders holder, @SuppressLint("RecyclerView") int position) {
        holder.textTitle.setText(String.valueOf(title.get(position)));
        holder.textDesc.setText(String.valueOf(desc.get(position)));
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(context).
                        setTitle("Alert Message").setMessage("Are you sure to change data?").setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Kirim data sekarang yang nantinya user akan update jika klik button edit
                                Intent intent = new Intent(context, ViewActivity.class);
                                intent.putExtra("id", String.valueOf(id.get(position)));
                                intent.putExtra("title", String.valueOf(title.get(position)));
                                intent.putExtra("desc", String.valueOf(desc.get(position)));
                                activity.startActivityForResult(intent, 1);
                            }
                        })
                        .setNeutralButton("Cancel", null)
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, MainActivity.class);
                                database = new Database(context);
                                database.deleteData(String.valueOf(id.get(position)));
                                activity.startActivityForResult(intent, 1);
                            }
                        }).create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class AdaptersViewHolders extends RecyclerView.ViewHolder {
        TextView textTitle, textDesc;
        ConstraintLayout constraintLayout;

        public AdaptersViewHolders(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDesc);
            constraintLayout = itemView.findViewById(R.id.rowItem);
        }

    }
}