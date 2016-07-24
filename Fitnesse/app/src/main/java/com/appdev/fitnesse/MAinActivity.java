package com.appdev.fitnesse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MAinActivity extends AppCompatActivity  {

    private int currentPosition=0;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private RoutineDbHelper route;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        String[] names=new String[Fitness.routines.length];
        for(int i=0;i<names.length;i++){
            names[i]=Fitness.routines[i].getName();
        }

        SharedPreferences Alert_dialog=getSharedPreferences("alert_state",MODE_PRIVATE);
        SharedPreferences.Editor Alert_editor=Alert_dialog.edit();
        Alert_editor.putBoolean("alert_starter",true);
        Alert_editor.putBoolean("alert_intermediate",true);
        Alert_editor.putBoolean("alert_hard",true);
        Alert_editor.putBoolean("alert_killer",true);
        Alert_editor.commit();

        drawerList=(ListView)findViewById(R.id.drawer);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ArrayAdapter<String> adapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_activated_1,names);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        if(savedInstanceState!=null){
            currentPosition=savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        }
        else{
            selectItem(0);
        }

        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open_draw,R.string.close_draw){

            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();

            }
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();

          }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        android.support.v4.app.FragmentManager fragMan=getSupportFragmentManager();
                        Fragment fragment=fragMan.findFragmentByTag("visible_detail");
                        if(fragment instanceof RoutineDetailFragment){
                        setActionBarTitle(0);
                        drawerList.setItemChecked(0,true);
                        }
                    }
                }
        );


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position",currentPosition);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        menu.findItem(R.id.action_start_jog).setVisible(!drawerOpen);
        menu.findItem(R.id.action_reminder).setVisible(!drawerOpen);

        return super.onPrepareOptionsMenu(menu);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);

        }
    }

    private void selectItem(long position){
        currentPosition = (int)position;
        String summa;
        View fragContainer=findViewById(R.id.frag_container);
        if(currentPosition==6){
            Intent intent=new Intent(this,JogActivity.class);
            startActivity(intent);
        }
        else if(currentPosition==7){
            Intent intent=new Intent(this,NotificationActivity.class);
            startActivity(intent);
        }
        else if(currentPosition==8){
            Intent intent =new Intent(this,JogView.class);
            startActivity(intent);
        }
        else if(currentPosition==10){
            Intent intent= new Intent(this,PathActivity.class);
            startActivity(intent);
        }

        else if(fragContainer!=null) {
        RoutineDetailFragment details=new RoutineDetailFragment();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        details.setRoutineId(position);
        ft.replace(R.id.frag_container,details,"visible_detail");
        //ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.commit();

            SharedPreferences Alert_dialog=getSharedPreferences("alert_state",MODE_PRIVATE);
            SharedPreferences.Editor Alert_editor= Alert_dialog.edit();
            summa="none";
            Boolean sate=false;

            if(position==1){
                sate=Alert_dialog.getBoolean("alert_starter",true);
                Alert_editor.putBoolean("alert_starter",false);
                Alert_editor.commit();
                summa="Jumping Jacks: Start from a standing position with legs together,then jump up with your feet apart and hands touching overhead\nStep-Up Onto Chair:Stand before a chair.Then step up and off the chair\nAbdominal Crunches: Lying on your back with knees bent and hands under"
                                 +"/nPLank: Start from the push-up position with the body's weight pressed on elbows and toes";
        }

            else if(position==2){
                sate=Alert_dialog.getBoolean("alert_intermediate",true);
                Alert_editor.putBoolean("alert_intermediate",false);
                Alert_editor.commit();
                summa="Jumping Jacks: Start from a standing position with legs together,then jump up with your feet apart and hands touching overhead\nSquats: Start with feet apart unbent arms forward. Then lower your butt until your thighs is parallel with ground"
                        +"/nPLank: Start from the push-up position with the body's weight pressed on elbows and toes\nCurtsy Lunge: Stand Alright. Step back your left leg to the right,and bending your Knee at the same time.Alternate."
                +"Cross Arm Crunch: Lie Down and bend your knees with feet flat on the floor.Put your arms crossed in front of your chest.Then lift your head and shoulders up to make 30 angle with the ground" +
                        "Knee Push Up: Start with a regular push up with your knees touching the floor while feet shall be off the floor ";
            }

            else if(position==3){
                sate=Alert_dialog.getBoolean("alert_hard",true);
                Alert_editor.putBoolean("alert_hard",false);
                Alert_editor.commit();
                summa="Jumping Jacks: Start from a standing position with legs together,then jump up with your feet apart and hands touching overhead\nSquats: Start with feet apart unbent arms forward. Then lower your butt until your thighs is parallel with ground"
                        +"/nPLank: Start from the push-up position with the body's weight pressed on elbows and toes\nCurtsy Lunge: Stand Alright. Step back your left leg to the right,and bending your Knee at the same time.Alternate."
                        +"Cross Arm Crunch: Lie Down and bend your knees with feet flat on the floor.Put your arms crossed in front of your chest.Then lift your head and shoulders up to make 30 angle with the ground" +
                        "Knee Push Up: Start with a regular push up with your knees touching the floor while feet shall be off the floor ";
            }

            if(position==4){
                sate=Alert_dialog.getBoolean("alert_killer",true);
                Alert_editor.putBoolean("alert_killer",false);
                Alert_editor.commit();
                summa="Jumping Jacks: Start from a standing position with legs together,then jump up with your feet apart and hands touching overhead\nSquats: Start with feet apart unbent arms forward. Then lower your butt until your thighs is parallel with ground"
                        +"/nPLank: Start from the push-up position with the body's weight pressed on elbows and toes\nCurtsy Lunge: Stand Alright. Step back your left leg to the right,and bending your Knee at the same time.Alternate."
                        +"Cross Arm Crunch: Lie Down and bend your knees with feet flat on the floor.Put your arms crossed in front of your chest.Then lift your head and shoulders up to make 30 angle with the ground" +
                        "Knee Push Up: Start with a regular push up with your knees touching the floor while feet shall be off the floor ";
            }


            if(sate)
            { StringBuffer buffer = new StringBuffer();
            buffer.append(summa);
            AlertDialog.Builder help= new AlertDialog.Builder(MAinActivity.this);
            help.setMessage(buffer.toString()).setCancelable(false).setPositiveButton("I Understood", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int chuma) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = help.create();
            alert.setTitle("Instructions");
            alert.show();
            }
        }
        else{
            Intent intent =new Intent(this,DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_ROUTINE_ID,(int)position);
            startActivity(intent);
        }

        setActionBarTitle((int)position);
        drawerLayout.closeDrawer(drawerList);
    }

    private void setActionBarTitle(int position){
        String name;

        if(position==0||position==6||position==7||position==5||position==8){
            name=getResources().getString(R.string.app_name);
        }
        else{
        name=Fitness.routines[position].getName();
        }

        getSupportActionBar().setTitle(name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);

        //MenuItem menuItem= menu.findItem(R.id.action_share);
        //shareActionProvider=(ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        //setIntent("Installation","Fitnesse");

        return super.onCreateOptionsMenu(menu);
    }

    private void setIntent(String Text,String time){
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"I  "+time+" of "+Text+" and it was Awesome");
        String chooserTitle="Share...";
        Intent chosenIntent =Intent.createChooser(intent,chooserTitle);
        startActivity(chosenIntent);
        //shareActionProvider.setShareIntent(chosenIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        switch(item.getItemId()) {
            case R.id.action_start_jog:
                Intent intent=new Intent(this,JogActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_reminder:
                Intent intent2=new Intent(this,NotificationActivity.class);
                startActivity(intent2);
                return true;
            case R.id.action_share:
                setIntent("Installation","Fitnesse");
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
