package com.asifiqbalsekh.RequirementServiceBackend.repository;

import com.asifiqbalsekh.RequirementServiceBackend.model.ExternalCandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExternalCandidateProfileRepository extends JpaRepository<ExternalCandidateProfile, Long> {
    // Custom query method to find profiles based on skillsets
    List<ExternalCandidateProfile> findByskillSetIn(List<String> req_skillSet);

}


