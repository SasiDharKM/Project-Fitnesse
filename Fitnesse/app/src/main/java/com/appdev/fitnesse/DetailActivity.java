package com.appdev.fitnesse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_ROUTINE_ID="id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        RoutineDetailFragment RDFrag=(RoutineDetailFragment)getSupportFragmentManager().findFragmentById(R.id.detail_frag);
        int routineId=(int) getIntent().getExtras().get(EXTRA_ROUTINE_ID);
        RDFrag.setRoutineId(routineId);

    }
}
