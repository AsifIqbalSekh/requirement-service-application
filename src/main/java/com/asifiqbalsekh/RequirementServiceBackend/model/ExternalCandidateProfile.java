package com.asifiqbalsekh.RequirementServiceBackend.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class ExternalCandidateProfile implements CandidateProfile , Serializable {//Using serializable for cache service

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String emailId;

    //Selecting the foreign key

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "external_candidate_skill", joinColumns = @JoinColumn(name = "employee_id"))
    private List<String> skillSet;

    public ExternalCandidateProfile() {}

    public ExternalCandidateProfile(int employeeId, String fullName, String emailId, List<String> skillSet) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.emailId = emailId;
        this.skillSet = skillSet;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getemailId() {
        return emailId;
    }

    public void setemailId(String emailId) {
        this.emailId = emailId;
    }

    public List<String> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(List<String> skillSet) {
        this.skillSet = skillSet;
    }

    @Override
    public String toString() {
        return "ExternalCandidateProfile{" +
                "employeeId=" + employeeId +
                ", fullName='" + fullName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", skillSet=" + skillSet +
                '}';
    }
}
