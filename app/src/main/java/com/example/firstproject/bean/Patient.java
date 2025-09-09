package com.example.firstproject.bean;

public class Patient {
    private String name;
    private String stage;
    private int progress;
    private String aiResult;
    private boolean hasAlert;

    public Patient(String name, String stage, int progress, String aiResult, boolean hasAlert) {
        this.name = name;
        this.stage = stage;
        this.progress = progress;
        this.aiResult = aiResult;
        this.hasAlert = hasAlert;
    }

    public String getName() { return name; }
    public String getStage() { return stage; }
    public int getProgress() { return progress; }
    public String getAiResult() { return aiResult; }
    public boolean isHasAlert() { return hasAlert; }
}