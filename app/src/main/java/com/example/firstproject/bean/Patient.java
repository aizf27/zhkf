package com.example.firstproject.bean;

public class Patient {
    private int id;              // 患者ID（数据库主键）
    private String name;         // 姓名
    private int age;             // 年龄
    private String gender;       // 性别
    private String MyPhysician;  // 主治医师
    private String diagnosis;    // 诊断/病情描述
    private String stage;        // 康复阶段（如：软瘫期/痉挛期/恢复期）
    private int progress;        // 康复进度（0-100）
    private String aiResult;     // AI 分析结果
    private boolean hasAlert;    // 是否有异常预警
    private String lastTrainingDate; // 最近训练日期

    // 构造函数（全参）
    public Patient(int id, String name, int age, String gender,String myPhysician, String diagnosis,
                   String stage, int progress, String aiResult,
                   boolean hasAlert, String lastTrainingDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.diagnosis = diagnosis;
        this.stage = stage;
        this.progress = progress;
        this.aiResult = aiResult;
        this.hasAlert = hasAlert;
        this.lastTrainingDate = lastTrainingDate;
    }

    // 简化构造函数（只需要基本信息时用）
    public Patient(String name, String stage, int progress) {
        this.name = name;
        this.stage = stage;
        this.progress = progress;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getMyPhysician() {    return MyPhysician;
    }
    public String setMyPhysician(String myPhysician) {
        MyPhysician = myPhysician;
        return MyPhysician;
    }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }

    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }

    public String getAiResult() { return aiResult; }
    public void setAiResult(String aiResult) { this.aiResult = aiResult; }

    public boolean isHasAlert() { return hasAlert; }
    public void setHasAlert(boolean hasAlert) { this.hasAlert = hasAlert; }

    public String getLastTrainingDate() { return lastTrainingDate; }
    public void setLastTrainingDate(String lastTrainingDate) { this.lastTrainingDate = lastTrainingDate; }


}
