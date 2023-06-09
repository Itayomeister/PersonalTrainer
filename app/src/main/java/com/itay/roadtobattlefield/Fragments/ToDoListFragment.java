package com.itay.roadtobattlefield.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itay.roadtobattlefield.Activities.MainActivity;
import com.itay.roadtobattlefield.Classes.todoRecView_Adapter;
import com.itay.roadtobattlefield.R;

import java.util.ArrayList;
import java.util.Collections;


public class ToDoListFragment extends Fragment {

    FloatingActionButton addGoal;
    EditText newGoal;
    RecyclerView goalsRecView;
    RelativeLayout screen;


    ItemTouchHelper itemTouchHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do_list, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addGoal = view.findViewById(R.id.fab_addGoal);
        newGoal = view.findViewById(R.id.editText_addGoal);
        screen = view.findViewById(R.id.screen);
        goalsRecView = view.findViewById(R.id.goalRecView);
        todoRecView_Adapter adapter = new todoRecView_Adapter();
        adapter.setGoals(MainActivity.trainee.checkBoxes);
        adapter.setChecked(MainActivity.trainee.checked);
        goalsRecView.setAdapter(adapter);
        goalsRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (MainActivity.trainee.checkBoxes.size() == 0) {
            MainActivity.trainee.checkBoxes.add("train");
            MainActivity.trainee.checked.add(false);
            todoRecView_Adapter.itemPos++;
        }

        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newGoal.getText().length() == 0) {
                    Toast.makeText(getContext(), "Make sure to type in a goal!", Toast.LENGTH_SHORT).show();
                } else {
                    new MaterialAlertDialogBuilder(getContext())
                            .setTitle("Add a goal")
                            .setMessage("Are you sure you want to add that goal?")
                            .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    addGoalCheckBox(newGoal.getText().toString());
                                }
                            })
                            .show();
                }
            }
        });

        for (int i = 0; i < MainActivity.trainee.checkBoxes.size(); i++) {
            if (MainActivity.trainee.checked.get(i)) {
            }
            //base on string and add no dup goals func;
        }

        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int draggedPos = viewHolder.getAdapterPosition();
                int targetPos = target.getAdapterPosition();

                adapter.swapId(draggedPos, targetPos);

                Collections.swap(MainActivity.trainee.checked, draggedPos, targetPos);
                Collections.swap(MainActivity.trainee.checkBoxes, draggedPos, targetPos);

                adapter.notifyItemMoved(draggedPos, targetPos);

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        itemTouchHelper.attachToRecyclerView(goalsRecView);

    }

    public void addGoalCheckBox(String goal) {
        goal = fixGoal(goal);
        if (goal.trim().isEmpty()) {
            Toast.makeText(getContext(), "Make sure to type in a goal!", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < MainActivity.trainee.checkBoxes.size(); i++) {
            if (MainActivity.trainee.checkBoxes.get(i).equals(goal)) {
                Toast.makeText(getContext(), "Your goal already exsits!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(getContext(), "New goal!", Toast.LENGTH_SHORT).show();
        MainActivity.trainee.checkBoxes.add(goal);
        MainActivity.trainee.checked.add(false);
        todoRecView_Adapter.itemPos++;
    }

    String fixGoal(String str) {
        if (str == "")
            return str;
        if (str.startsWith(" "))
            return fixGoal(str.substring(1, str.length() - 1));
        if (str.endsWith(" "))
            return fixGoal(str.substring(0, str.length() - 1));
        return str;
    }
}