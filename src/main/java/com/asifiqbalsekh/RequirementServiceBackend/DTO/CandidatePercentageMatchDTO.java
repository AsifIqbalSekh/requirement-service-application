package com.asifiqbalsekh.RequirementServiceBackend.DTO;

import com.asifiqbalsekh.RequirementServiceBackend.model.ExternalCandidateProfile;

public class CandidatePercentageMatchDTO {
    private final ExternalCandidateProfile profile;
    private final double matchPercentage;

    public CandidatePercentageMatchDTO(ExternalCandidateProfile profile, double matchPercentage) {
        this.profile = profile;
        this.matchPercentage = matchPercentage;
    }

    public ExternalCandidateProfile getProfile() {
        return profile;
    }

    public double getMatchPercentage() {
        return matchPercentage;
    }
}
