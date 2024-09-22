package com.asifiqbalsekh.RequirementServiceBackend.model;

import jakarta.persistence.*;



import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class InternalCandidateProfile implements CandidateProfile, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;

    @Column(nullable=false)
    private String fullName;

    @Column(nullable=false, unique = true)
    private String emailId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "internal_candidate_skill", joinColumns = @JoinColumn(name = "employee_id"))
    private List<String> skillSet;

    public InternalCandidateProfile() {}

    public InternalCandidateProfile(int employeeId, String fullName, String emailId, List<String> skillSet) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.emailId = emailId;
        this.skillSet = skillSet;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeID) {
        this.employeeId = employeeID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String email) {
        this.emailId = email;
    }

    public List<String> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(List<String> skillSet) {
        this.skillSet = skillSet;
    }

    @Override
    public String toString() {
        return "InternalCandidateProfile{" +
                "employeeID=" + employeeId +
                ", fullName='" + fullName + '\'' +
                ", email='" + emailId + '\'' +
                ", skillSet=" + skillSet +
                '}';
    }
}
