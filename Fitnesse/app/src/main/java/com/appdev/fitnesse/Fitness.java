package com.appdev.fitnesse;

/**
 * Created by SasiDKM on 15-07-2016.
 */
public class Fitness {
    private String name,routine;

    public Fitness(String name, String routine) {
        this.name = name;
        this.routine = routine;
    }


    public static final Fitness[] routines={
            new Fitness("HOME","This is an app for getting to know \nwhat sets of routines are to be done\nto maintain Fitness with Finesse\nHence the name Fitnesse \n "),
            new Fitness("Starters","15 Jumping Jacks\n10 Step-Up Onto Chair\n3 Push Ups\n10 Abdominal Crunches\n10s Plank"),
            new Fitness("Intermediate","15 Jumping Jacks\n10 Push Ups\n15 Squats\n15 Curtsy Lunge\n15 Cross Arm Crunches\n15s Right Side Plank\n15 Knee Push Ups\n15s Left Side Plank\n15 Long Arm Crunches\n20s Planks"),
            new Fitness("Hard","25 Jumping Jacks\n10 Push Ups\n15s Right Side Plank\n20 Long Arm Crunches\n20 Squats\n20 Curtsy Lunges\n30s Plank\n15 Knee Push Ups\n20 Cross Arm Crunches\n15s Left Side Plank"),
            new Fitness("Killer","12 Knee Push Ups\n30 High Knees\n12 Tricep Dips\n70s Right Side Plank\n70s Left SIde Plank\n25 Reverse Crunches\n30 Jumping Jacks\n35 Russian Twist\n35 Step-Up Onto Chair\n12 Push Ups\n35 Backward Lunge with Front Kick\n"),
            new Fitness("STOPWATCH",""),
            new Fitness("START JOGGING",""),
            new Fitness("SET WORKOUT REMINDER",""),
            new Fitness("VIEW JOG HISTORY",""),
            new Fitness("INSTRUCTIONS","Click on the add alarm button to set a remainder\nClick on the run button when you are ready to jog\nand please turn on the GPS"),
            new Fitness("TRACK WALK","")
    };

    public String getName() {
        return name;
    }

    public String getRoutine() {
        return routine;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

