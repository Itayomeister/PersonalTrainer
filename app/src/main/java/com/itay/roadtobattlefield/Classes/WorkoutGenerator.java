package com.itay.roadtobattlefield.Classes;

import com.itay.roadtobattlefield.Activities.MainActivity;
import com.itay.roadtobattlefield.Classes.StrengthExercises.GeneralExercise;
import com.itay.roadtobattlefield.Classes.StrengthExercises.MuscleGroups;
import com.itay.roadtobattlefield.DAOtype;
import com.itay.roadtobattlefield.TraineeLevel;

public class WorkoutGenerator {

    private TraineeLevel traineeLevel;
    private GeneralExercise[] exercises;


    public WorkoutGenerator() {
        this.traineeLevel = MainActivity.trainee.getTraineeLevel();
        if (traineeLevel == TraineeLevel.Hardcore)
            exercises = new GeneralExercise[]{pullUp(), rows(), pushUp(), dips(), squats(), deadlifts(), sitUps(), russianTwists(), dragonFly()};
        else if (traineeLevel == TraineeLevel.Advanced)
            exercises = new GeneralExercise[]{pullUp(), pushUp(), squats(), deadlifts(), sitUps(), russianTwists(), dragonFly()};
        else if (traineeLevel == TraineeLevel.Intermediate)
            exercises = new GeneralExercise[]{rows(), pushUp(), squats(), sitUps(), russianTwists(), dragonFly()};
        else
            exercises = new GeneralExercise[]{rows(), pushUp(), squats(), sitUps(), dragonFly()};

        MainActivity.workoutPlan = exercises;
    }

    public GeneralExercise[] getPlan() {
        return exercises;
    }


    private GeneralExercise pullUp() {
        GeneralExercise temp = new GeneralExercise(4, 7, 3, 0);
        temp.setNAME("Pull Ups");
        temp.setINFO("Pull Ups - A pulling exercise for the Back and biceps");
        temp.setMUSCLE_GROUP(MuscleGroups.Back);
        temp.setSec_MUSCLE_GROUPS(new MuscleGroups[]{MuscleGroups.RearDelts, MuscleGroups.Biceps});
        return temp;
    }

    private GeneralExercise rows() {
        GeneralExercise temp = new GeneralExercise(3, 10, 2, 0);
        temp.setNAME("Rows");
        temp.setINFO("Rows - A pulling exercise for the Back and biceps");
        temp.setMUSCLE_GROUP(MuscleGroups.Back);
        temp.setSec_MUSCLE_GROUPS(new MuscleGroups[]{MuscleGroups.RearDelts, MuscleGroups.Biceps});
        return temp;
    }

    private GeneralExercise pushUp() {
        GeneralExercise temp = new GeneralExercise(4, 20, 3, 0);
        temp.setNAME("Push Ups");
        temp.setINFO("Push Ups - A pushing exercise for the chest and triceps");
        temp.setMUSCLE_GROUP(MuscleGroups.Chest);
        temp.setSec_MUSCLE_GROUPS(new MuscleGroups[]{MuscleGroups.FrontDelts, MuscleGroups.Triceps});
        return temp;
    }

    private GeneralExercise dips() {
        GeneralExercise temp = new GeneralExercise(3, 8, 2, 0);
        temp.setNAME("Dips");
        temp.setINFO("Dips - A pushing exercise for the chest and triceps");
        temp.setMUSCLE_GROUP(MuscleGroups.Chest);
        temp.setSec_MUSCLE_GROUPS(new MuscleGroups[]{MuscleGroups.FrontDelts, MuscleGroups.Triceps});
        return temp;
    }

    private GeneralExercise squats() {
        GeneralExercise temp = new GeneralExercise(4, 15, 3, 0);
        temp.setNAME("Squats");
        temp.setINFO("Squats - A leg exercise for the quads");
        temp.setMUSCLE_GROUP(MuscleGroups.Quads);
        return temp;
    }

    private GeneralExercise deadlifts() {
        GeneralExercise temp = new GeneralExercise(4, 8, 3, 0);
        temp.setNAME("Deadlifts");
        temp.setINFO("Deadlifts - A leg exercise for the hamstrings");
        temp.setMUSCLE_GROUP(MuscleGroups.Hamstrings);
        return temp;
    }

    private GeneralExercise dragonFly() {
        GeneralExercise temp = new GeneralExercise(3, 10, 1, 30);
        temp.setNAME("Dragon Fly");
        temp.setINFO("Dragon Fly - one of the hardest abs' exercises there is");
        temp.setMUSCLE_GROUP(MuscleGroups.LowerAbs);
        return temp;
    }

    private GeneralExercise sitUps() {
        GeneralExercise temp = new GeneralExercise(3, 20, 1, 30);
        temp.setNAME("Sit ups");
        temp.setINFO("Sit ups - one of the most popular abs exercises there is for the upper abs");
        temp.setMUSCLE_GROUP(MuscleGroups.UpperAbs);
        return temp;
    }

    private GeneralExercise russianTwists() {
        GeneralExercise temp = new GeneralExercise(3, 10, 1, 30);
        temp.setNAME("Russian twists");
        temp.setINFO("Russian twists - one of the hardest abs' exercises there is for the obliques");
        temp.setMUSCLE_GROUP(MuscleGroups.Obliques);
        return temp;
    }

}
