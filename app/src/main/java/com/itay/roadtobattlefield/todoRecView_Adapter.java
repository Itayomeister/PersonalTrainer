package com.itay.roadtobattlefield;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Collections;

public class todoRecView_Adapter extends RecyclerView.Adapter<todoRecView_Adapter.ViewHolder> {

    private ArrayList<String> goals = new ArrayList<>();
    private ArrayList<Boolean> checked = new ArrayList<>();
    private ArrayList<ViewHolder> viewHolders = new ArrayList<>();
    static public int itemPos = -1;

    public todoRecView_Adapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        viewHolders.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.todo_item.setText(goals.get(position));
        holder.todo_item.setChecked(checked.get(position));
        holder.todo_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.todo_item.isChecked()){
                    checked.set(holder.id, true);
                }
                else{
                    checked.set(holder.id, false);
                }
            }
        });
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(view.getContext())
                        .setTitle("Add a goal")
                        .setMessage("Are you sure you want to delete " + holder.todo_item.getText().toString().toUpperCase() + " as a goal?")
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                goals.remove(holder.id);
                                checked.remove(holder.id);
                                itemPos--;
                                setChecked(checked);
                                setGoals(goals);
                            }
                        })
                        .show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public void setGoals(ArrayList<String> goals) {
        this.goals = goals;
        notifyDataSetChanged();
    }

    public void setChecked(ArrayList<Boolean> checked) {
        this.checked = checked;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CheckBox todo_item;
        private ImageButton deleteItem;
        public int id = itemPos;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            todo_item = itemView.findViewById(R.id.todoItem);
            deleteItem = itemView.findViewById(R.id.deleteGoal);
        }
    }

    public void swapId(int pos1, int pos2){
        viewHolders.get(pos1).id = pos2;
        viewHolders.get(pos2).id = pos1;
    }

    public void swapId(ViewHolder view1, ViewHolder view2){
        int holder = view2.id;
        view2.id = view1.id;
        view1.id = holder;
    }
}
