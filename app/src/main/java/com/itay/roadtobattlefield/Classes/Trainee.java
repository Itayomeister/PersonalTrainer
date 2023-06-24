package com.itay.roadtobattlefield.Classes;

import com.itay.roadtobattlefield.TraineeLevel;

import java.io.Serializable;
import java.util.ArrayList;

public class Trainee implements Serializable {
    private String fullName = "", email = "", phoneNum = "";
    private int id = 0;
    private double topRunningSpeed = 0, averageRunningSpeed = 0,
            topRunningTime = 0, averageRunningTime = 0,
            topRunningDistance = 0, averageRunningDistance = 0,
            trainingAmount = 0, runningAmount = 0;
    private float rating = 0;
    private TraineeLevel traineeLevel = TraineeLevel.Begginer;

    static public ArrayList<String> checkBoxes = new ArrayList<>();
    static public ArrayList<Boolean> checked = new ArrayList<>();

    public Trainee(String fullName, String email, String phoneNum, TraineeLevel traineeLevel, int id){
        this.fullName = fullName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.traineeLevel = traineeLevel;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTopRunningSpeed() {
        return topRunningSpeed;
    }

    public void setTopRunningSpeed(double topRunningSpeed) {
        this.topRunningSpeed = topRunningSpeed;
    }

    public double getAverageRunningSpeed() {
        return averageRunningSpeed;
    }

    public void setAverageRunningSpeed(double averageRunningSpeed) {
        this.averageRunningSpeed = averageRunningSpeed;
    }

    public double getTopRunningTime() {
        return topRunningTime;
    }

    public void setTopRunningTime(double topRunningTime) {
        this.topRunningTime = topRunningTime;
    }

    public double getAverageRunningTime() {
        return averageRunningTime;
    }

    public void setAverageRunningTime(double averageRunningTime) {
        this.averageRunningTime = averageRunningTime;
    }

    public double getTopRunningDistance() {
        return topRunningDistance;
    }

    public void setTopRunningDistance(double topRunningDistance) {
        this.topRunningDistance = topRunningDistance;
    }

    public double getAverageRunningDistance() {
        return averageRunningDistance;
    }

    public void setAverageRunningDistance(double averageRunningDistance) {
        this.averageRunningDistance = averageRunningDistance;
    }

    public double getTrainingAmount() {
        return trainingAmount;
    }

    public void setTrainingAmount(double trainingAmount) {
        this.trainingAmount = trainingAmount;
    }

    public double getRunningAmount() {
        return runningAmount;
    }

    public void setRunningAmount(double runningAmount) {
        this.runningAmount = runningAmount;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
