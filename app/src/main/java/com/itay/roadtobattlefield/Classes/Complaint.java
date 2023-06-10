package com.itay.roadtobattlefield.Classes;

public class Complaint {
    private String complaint;
    private Trainee trainee;

    public Complaint(Trainee trainee, String complaint) {
        this.trainee = trainee;
        this.complaint = complaint;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public Trainee getTrainee() {
        return trainee;
    }

    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
    }

}
