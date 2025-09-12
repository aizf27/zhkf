package com.example.firstproject.bean;

public class Doctor {
    private String id;          // 工号，主键，对应 doctor_account + doctor_info
    private String name;        // 姓名



    private String gender;         //性别
    private int age;            // 年龄


    private String department;  //科室
    private int patientCount;   // 当前患者数

    public Doctor(String id, String name, String gender,int age, String department,int patientCount) {
        this.id = id;

        this.name = name;
        this.gender = gender;
        this.age = age;
        this.department = department;
        this.patientCount = patientCount;
    }

    // getter & setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getPatientCount() { return patientCount; }
    public void setPatientCount(int patientCount) { this.patientCount = patientCount; }
}
