package com.itay.roadtobattlefield.Classes;

import com.itay.roadtobattlefield.Classes.StrengthExercises.GeneralExercise;
import com.itay.roadtobattlefield.TraineeLevel;

public class WorkoutGenerator {

    public enum Goal {
        betterAerobic,
        betterStrength,
        combination
    }

    String manual;

    private TraineeLevel traineeLevel;
    private GeneralExercise[] exercises;

    public WorkoutGenerator(Goal goal, TraineeLevel traineeLevel) {

    }

}
