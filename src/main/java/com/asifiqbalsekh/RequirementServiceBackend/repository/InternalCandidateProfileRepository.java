package com.asifiqbalsekh.RequirementServiceBackend.repository;

import com.asifiqbalsekh.RequirementServiceBackend.model.CandidateProfile;
import com.asifiqbalsekh.RequirementServiceBackend.model.InternalCandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternalCandidateProfileRepository extends JpaRepository<InternalCandidateProfile,Long> {
    public List<CandidateProfile> findByskillSetIn(List<String> skillSet);
}
