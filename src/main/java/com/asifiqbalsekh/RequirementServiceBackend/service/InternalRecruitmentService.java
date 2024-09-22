package com.asifiqbalsekh.RequirementServiceBackend.service;

import com.asifiqbalsekh.RequirementServiceBackend.model.CandidateProfile;
import com.asifiqbalsekh.RequirementServiceBackend.model.InternalCandidateProfile;
import com.asifiqbalsekh.RequirementServiceBackend.repository.InternalCandidateProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
public class InternalRecruitmentService {
    private final InternalCandidateProfileRepository internalCandidateProfileRepository;
    private final ExternalRecruitmentService ers;


    @Autowired
    public InternalRecruitmentService(InternalCandidateProfileRepository internalCandidateProfileRepository,
                                                                        ExternalRecruitmentService ers) {
        this.internalCandidateProfileRepository = internalCandidateProfileRepository;
        this.ers = ers;

    }

    @CachePut(value = "InternalCandidateProfile")
    public InternalCandidateProfile addCandidate(InternalCandidateProfile given_profile) {
        return internalCandidateProfileRepository.save(given_profile);
    }

    @Cacheable(value = "InternalCandidateProfile", key = "#root.methodName",
            condition = "#cacheControl == null || !#cacheControl.equalsIgnoreCase('no-cache')")
            //Implementing no cache control when choose

    public List<InternalCandidateProfile> findAll(String cacheControl) {
        return internalCandidateProfileRepository.findAll();
    }

    @CacheEvict(key ="#given_id",value = "candidateProfile")
    public String deleteById(long given_id) {
        if (!internalCandidateProfileRepository.existsById(given_id)) {
            throw new IllegalArgumentException("Given id does not exist");
        }
        internalCandidateProfileRepository.deleteById(given_id);
        return "Candidate profile deleted successfully";
    }


    @Cacheable(value = "CandidateProfiles", key = "#req_skills + '-' + #opt_skills")
    public List<CandidateProfile> findProfileBySkill(List<String> req_skills,
                                                     List<String> opt_skills) {

        //Merging the req & opt skills
        List<String>merged_list=new ArrayList<>(req_skills);
        merged_list.addAll(opt_skills);
        //remove duplicate
        merged_list=new ArrayList<>(new HashSet<>(merged_list));


        //Checking with merged skill in irs
        List<CandidateProfile> req_candidate=internalCandidateProfileRepository
                .findByskillSetIn(merged_list);
        List<CandidateProfile>merge_candidate=getCandidateProfiles(req_candidate, merged_list);
        //checking is candidate exist & return
        if(!merge_candidate.isEmpty()){
           return merge_candidate;
        }

        // Checking with req skills in IRS
            req_candidate=internalCandidateProfileRepository.findByskillSetIn(req_skills);
         merge_candidate=getCandidateProfiles(req_candidate, req_skills);

        //checking is candidate exist & return
        if(!merge_candidate.isEmpty()){

            return merge_candidate;
        }

        return ers.getAllcandidateWithExactSkillset(merged_list);

    }


    private List<CandidateProfile> getCandidateProfiles(List<CandidateProfile> req_candidate, List<String> skill_set) {
        List<CandidateProfile>new_candidate=new ArrayList<>();
        for (CandidateProfile profile: req_candidate) {
            double percentage=ers.calculateMatchPercentage(profile.getSkillSet(), skill_set);
            if(percentage>=100){
                new_candidate.add(profile);
            }
        }
        return new_candidate;
    }

}
