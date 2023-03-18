package com.itay.roadtobattlefield;

public class Trainee {
    private String fullName;
    private String email;
    private String phoneNum;
    private TraineeLevel traineeLevel;


    public Trainee(String fullName, String email, String phoneNum){
        this.fullName = fullName;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public Trainee(String fullName, String email, String phoneNum, TraineeLevel traineeLevel){
        this.fullName = fullName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.traineeLevel = traineeLevel;
    }

    public Trainee(Trainee trainee){
        this.fullName = trainee.fullName;
        this.email = trainee.email;
        this.phoneNum = trainee.phoneNum;
        this.traineeLevel = trainee.traineeLevel;
    }

    public Trainee(){}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public TraineeLevel getTraineeLevel() {
        return traineeLevel;
    }

    public void setTraineeLevel(TraineeLevel traineeLevel) {
        this.traineeLevel = traineeLevel;
    }

    public void setTraineeLevel(String traineeLevel){
        if(TraineeLevel.Begginer.toString() == traineeLevel)
            this.traineeLevel = TraineeLevel.Begginer;
        else if(TraineeLevel.Advanced.toString() == traineeLevel)
            this.traineeLevel = TraineeLevel.Advanced;
        else if(TraineeLevel.Intermediate.toString() == traineeLevel)
            this.traineeLevel = TraineeLevel.Intermediate;
        else if(TraineeLevel.Hardcore.toString() == traineeLevel)
            this.traineeLevel = TraineeLevel.Hardcore;
    }
}
