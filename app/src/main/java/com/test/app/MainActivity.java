package com.test.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ListAdapter.OnItemClickListener {

    private Toolbar toolbar;

    private FloatingActionButton fab;

    private DBHelper db;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHelper(this);
        setContentView(R.layout.activity_main);
        initializeUI();
        setSupportActionBar(toolbar);
    }

    private void initializeUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        setOnClickListeners();
        initializeList(getAllData());
    }

    private void initializeList(ArrayList<Specialist> data) {
         recyclerView = (RecyclerView) findViewById(R.id.list);
        if (data != null) {
            recyclerView.setAdapter(new ListAdapter(data, this));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }

    private void setOnClickListeners() {
        fab.setOnClickListener(this);
    }

    private ArrayList<Specialist> getAllData() {
        return db.geAllData();
    }

    public void updateRow(Integer id, String name, String surname, Integer yob, String city, String position) {
        db.updateRow(id, name, surname, yob, city, position);
        updateAdapter();
    }

    public void insertRow(String name, String surname, Integer yob, String city, String position) {
        db.insertRow(name, surname, yob, city, position);
        updateAdapter();
    }

    private void updateAdapter() {
        ListAdapter adapter = (ListAdapter) recyclerView.getAdapter();
        adapter.getData().clear();
        adapter.getData().addAll(getAllData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        AddDialogFragment .newInstance(getString(R.string.add), null).show(getSupportFragmentManager(), "tag");
    }

    @Override
    public void onItemClick(Specialist specialist) {
        AddDialogFragment.newInstance(getString(R.string.edit), specialist).show(getSupportFragmentManager(), "tag");
    }
}
