package main.model;

import lombok.Data;

@Data
public class UserData {
    private long chatId;
    private double weight;
    private double height;
    private String fitnessLevel;
    private String nutritionPlan;
    private String trainingPlan;
} 