package com.example.jobhunt.Model;

public class Data {
    String data, id, jobDescription, jobTitle, salary, skillsRequired;
    public Data(){

    }

    public Data(String data, String id, String jobDescription, String jobTitle, String salary, String skillsRequired) {
        this.data = data;
        this.id = id;
        this.jobDescription = jobDescription;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.skillsRequired = skillsRequired;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSkillsRequired() {
        return skillsRequired;
    }

    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }
}
