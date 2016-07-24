package com.appdev.fitnesse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class RoutineDetailFragment extends Fragment {
    private long routineId;

    public RoutineDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            routineId=savedInstanceState.getLong("routineId");
        }else if(routineId!=0&&routineId!=9){
            FragmentTransaction ft= getChildFragmentManager().beginTransaction();
            StopWatchFragment stopWatchFragment= new StopWatchFragment();
            ft.replace(R.id.stopwatch_container,stopWatchFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.commit();
        }
        else{
            FragmentTransaction ft= getChildFragmentManager().beginTransaction();
            //StopWatchFragment stopWatchFragment= new StopWatchFragment();
            //ft.replace(R.id.stopwatch_container,stopWatchFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.commit();

        }
        return inflater.inflate(R.layout.fragment_routine_detail, container, false);
    }

    public void setRoutineId(long routineId) {
        this.routineId = routineId;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong("routineId",routineId);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view=getView();
        if(view!=null){
            Fitness fitness=Fitness.routines[(int)routineId];
            TextView rTitle=(TextView)view.findViewById(R.id.routineTitle);
            TextView rDesc=(TextView)view.findViewById(R.id.routine);
            rTitle.setText(fitness.getName());
            rDesc.setText(fitness.getRoutine());

        }
    }
}
