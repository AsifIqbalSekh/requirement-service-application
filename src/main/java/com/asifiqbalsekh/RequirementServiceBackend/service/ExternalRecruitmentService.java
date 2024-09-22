package com.asifiqbalsekh.RequirementServiceBackend.service;

import com.asifiqbalsekh.RequirementServiceBackend.DTO.CandidatePercentageMatchDTO;
import com.asifiqbalsekh.RequirementServiceBackend.model.CandidateProfile;
import com.asifiqbalsekh.RequirementServiceBackend.model.ExternalCandidateProfile;
import com.asifiqbalsekh.RequirementServiceBackend.repository.ExternalCandidateProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExternalRecruitmentService {
    private final ExternalCandidateProfileRepository externalCandidateProfileRepository;

    @Autowired
    public ExternalRecruitmentService(ExternalCandidateProfileRepository externalCandidateProfileRepository) {
        this.externalCandidateProfileRepository = externalCandidateProfileRepository;
    }

    //For Saving & Updating Candidate Details
    public ExternalCandidateProfile addCandidate(ExternalCandidateProfile given_profile) {

        return externalCandidateProfileRepository.save(given_profile);
    }

    //For Deleting Candidate

    public String deleteCandidate(long given_id) {
        if(!externalCandidateProfileRepository.existsById(given_id)) {
            throw new IllegalArgumentException("Given id does not exist");
        }
        externalCandidateProfileRepository.deleteById(given_id);
        return "Employee Deleted!";
    }

    //For Getting all candidate Details
    public List<ExternalCandidateProfile> findAllCandidate() {
        return externalCandidateProfileRepository.findAll();
    }

    //Exact match of skill set of the candidate
    public List<CandidateProfile> getAllcandidateWithExactSkillset(List<String> skillset) {
        List<ExternalCandidateProfile> profiles_found= externalCandidateProfileRepository
                .findByskillSetIn(skillset);
        List<CandidateProfile> new_profile_found= new ArrayList<>();

        for (ExternalCandidateProfile profile : profiles_found) {
            double match_percentage=calculateMatchPercentage(profile.getSkillSet(), skillset);
            if(match_percentage>=100){
                new_profile_found.add(profile);
            }

        }
        return new_profile_found;


    }


    //Search By Skill Set with percentage Matching
    public List<CandidatePercentageMatchDTO> searchBySkillset(List<String> skillset) {
        List<ExternalCandidateProfile> profiles_found= externalCandidateProfileRepository
                                                                    .findByskillSetIn(skillset);

        List<CandidatePercentageMatchDTO> req_candidate = new ArrayList<>();
        for (ExternalCandidateProfile profile : profiles_found) {
            double match_percentage=calculateMatchPercentage(profile.getSkillSet(), skillset);
            req_candidate.add(new CandidatePercentageMatchDTO(profile, match_percentage));

        }
        return req_candidate;
    }
     protected double calculateMatchPercentage(List<String> profileSkills, List<String> requiredSkills) {
        if (profileSkills.isEmpty() || requiredSkills.isEmpty()) {
            return 0;
        }

        // Convert all skills to lower case for case-insensitive comparison
        List<String> normalizedProfileSkills = profileSkills.stream()
                .map(String::toLowerCase)
                .toList();

        List<String> normalizedRequiredSkills = requiredSkills.stream()
                .map(String::toLowerCase)
                .toList();

        long matchingSkillsCount = normalizedProfileSkills.stream()
                .filter(normalizedRequiredSkills::contains)
                .count();

        return (double) matchingSkillsCount / normalizedRequiredSkills.size() * 100;
    }
}
