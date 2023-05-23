package com.itay.roadtobattlefield.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.itay.roadtobattlefield.Classes.DAO;
import com.itay.roadtobattlefield.Classes.Trainee;
import com.itay.roadtobattlefield.DAOtype;
import com.itay.roadtobattlefield.R;

import java.util.ArrayList;


public class LeaderboardFragment extends Fragment {


    ListView listView;
    ArrayList<String> trainers;
    ArrayAdapter<String> arrayAdapter;
    DAO dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listView_trainers);


        dao = new DAO(DAOtype.Trainee);
        loadData();

    }

    private void loadData() {
        dao.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trainers = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()){
                    trainers.add(data.getValue(Trainee.class).getFullName());
                }
                arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, trainers);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}