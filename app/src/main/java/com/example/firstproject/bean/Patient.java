package com.example.firstproject.bean;

public class Patient {
    private String account;  // 账号（手机号）
    private int id;              // 患者ID（数据库主键）
    private String name;         // 姓名
    private int age;             // 年龄
    private String gender;       // 性别
    private String physicianName;
    private String physicianCode;
    private String diagnosis;    // 诊断/病情描述
    private String stage;        // 康复阶段（如：软瘫期/痉挛期/恢复期）
    private int progress;        // 康复进度（0-100）
    private String aiResult;     // AI 分析结果
    private boolean hasAlert;    // 是否有异常预警
    private String lastTrainingDate; // 最近训练日期

    // 构造函数（全参）
    public Patient(int id, String name, int age, String gender,String physicianName,String physicianCode, String diagnosis,
                   String stage, int progress, String aiResult,
                   boolean hasAlert, String lastTrainingDate) {

        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.physicianName = physicianName;
        this.physicianCode = physicianCode;
        this.diagnosis = diagnosis;
        this.stage = stage;
        this.progress = progress;
        this.aiResult = aiResult;
        this.hasAlert = hasAlert;
        this.lastTrainingDate = lastTrainingDate;
    }

    // 简化构造函数（只需要基本信息时用）
    public Patient(String name, String gender,int age,String physicianName,String physicianCode) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.physicianName = physicianName;
        this.physicianCode = physicianCode;

    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public String getAccount() {
        return account;
    }

    public void setAccount(String Account) {
        this.account = Account;
    }


    public String getName() { return name; }
    public void setName(String Name) { this.name = Name; }

    public int getAge() { return age; }
    public void setAge(int Age) { this.age = Age; }

    public String getGender() { return gender; }
    public void setGender(String Gender) { this.gender = Gender; }



    public String getPhysicianName() { return physicianName; }
    public void setPhysicianName(String physicianName) { this.physicianName = physicianName; }

    public String getPhysicianCode() { return physicianCode; }
    public void setPhysicianCode(String physicianCode) { this.physicianCode = physicianCode; }

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
