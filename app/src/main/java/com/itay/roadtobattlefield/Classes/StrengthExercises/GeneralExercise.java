package com.itay.roadtobattlefield.Classes.StrengthExercises;

public class GeneralExercise {

    private String NAME = "General";
    private MuscleGroups MUSCLE_GROUP;
    private MuscleGroups[] sec_MUSCLE_GROUPS;
    private String INFO = "basic info about the exercise";

    private int number_of_sets = 3;
    private int reps_per_set = 8;
    
    private int rest_time_minutes = 3;
    private int rest_time_seconds = 0;


    // Constructors
    public GeneralExercise() {}

    public GeneralExercise(int number_of_sets) {
        this.number_of_sets = number_of_sets;
    }

    public GeneralExercise(int number_of_sets, int reps_per_set) {
        this.number_of_sets = number_of_sets;
        this.reps_per_set = reps_per_set;
    }

    public GeneralExercise(int number_of_sets, int reps_per_set, int rest_time_minutes) {
        this.number_of_sets = number_of_sets;
        this.reps_per_set = reps_per_set;
        this.rest_time_minutes = rest_time_minutes;
    }

    public GeneralExercise(int number_of_sets, int reps_per_set, int rest_time_minutes, int rest_time_seconds) {
        this.number_of_sets = number_of_sets;
        this.reps_per_set = reps_per_set;
        this.rest_time_minutes = rest_time_minutes;
        this.rest_time_seconds = rest_time_seconds;
    }


    // Basic Get & Set
    public int getNumber_of_sets() {
        return number_of_sets;
    }

    public void setNumber_of_sets(int number_of_sets) {
        this.number_of_sets = number_of_sets;
    }

    public int getReps_per_set() {
        return reps_per_set;
    }

    public void setReps_per_set(int reps_per_set) {
        this.reps_per_set = reps_per_set;
    }

    public int getRest_time_minutes() {
        return rest_time_minutes;
    }

    public void setRest_time_minutes(int rest_time_minutes) {
        this.rest_time_minutes = rest_time_minutes;
    }

    public int getRest_time_seconds() {
        return rest_time_seconds;
    }

    public void setRest_time_seconds(int rest_time_seconds) {
        this.rest_time_seconds = rest_time_seconds;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public MuscleGroups getMUSCLE_GROUP() {
        return MUSCLE_GROUP;
    }

    public void setMUSCLE_GROUP(MuscleGroups MUSCLE_GROUP) {
        this.MUSCLE_GROUP = MUSCLE_GROUP;
    }

    public MuscleGroups[] getSec_MUSCLE_GROUPS() {
        return sec_MUSCLE_GROUPS;
    }

    public void setSec_MUSCLE_GROUPS(MuscleGroups[] sec_MUSCLE_GROUPS) {
        this.sec_MUSCLE_GROUPS = sec_MUSCLE_GROUPS;
    }

    public String getINFO() {
        return INFO;
    }

    public void setINFO(String INFO) {
        this.INFO = INFO;
    }
}
