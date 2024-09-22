package com.asifiqbalsekh.RequirementServiceBackend.controller;

import com.asifiqbalsekh.RequirementServiceBackend.DTO.CandidatePercentageMatchDTO;
import com.asifiqbalsekh.RequirementServiceBackend.model.CandidateProfile;
import com.asifiqbalsekh.RequirementServiceBackend.model.ExternalCandidateProfile;
import com.asifiqbalsekh.RequirementServiceBackend.service.ExternalRecruitmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/external-recruitment-service")
public class ExternalRecruitmentController {

    //Implementing Logging
    private static final Logger logger = LoggerFactory.getLogger(ExternalRecruitmentController.class);

    private final ExternalRecruitmentService externalRecruitmentService;

    @Autowired
    ExternalRecruitmentController(ExternalRecruitmentService externalRecruitmentService) {
        this.externalRecruitmentService = externalRecruitmentService;
    }


    @Operation(summary = "For getting all stored candidate details", description = "It will return all Candidate profile details")
    @GetMapping("/profile")
    public List<ExternalCandidateProfile> getExternalRecruitmentProfile() {
        return externalRecruitmentService.findAllCandidate();
    }

    @Operation(summary = "This end point Store the given profile in database and Respond with the stored candidate details",
            description = "USER Can send data via Request Body, email id should be unique")
    @PostMapping("/profile")
    public ExternalCandidateProfile postCandidate(@RequestBody ExternalCandidateProfile given_profile) throws BadRequestException {
        if(given_profile.getSkillSet()==null || given_profile.getSkillSet().isEmpty()) {
            throw new BadRequestException("Skill set is required");
        }
        given_profile.setEmployeeId(0);//For new store
        logger.info("Add Candidate Details:{} in ERS-Method:{}", given_profile,getMethodName());
        return externalRecruitmentService.addCandidate(given_profile);
    }


    @Operation(summary = "The end point update the profile and return updated candidate details",
            description = "USER Can send data via  JSON in request body")
    @PutMapping("/profile")
    public ExternalCandidateProfile updateCandidate(@RequestBody ExternalCandidateProfile given_profile) throws BadRequestException {
        if(given_profile.getSkillSet()==null || given_profile.getSkillSet().isEmpty()) {
            throw new BadRequestException("Skill set is required");
        }
        return externalRecruitmentService.addCandidate(given_profile);
    }

    @Operation(summary = "Delete stored candidate details, required MANAGER role to access",
            description = "Take Candidate id as PathVariable")
    @DeleteMapping("/profile/{employeeId}")
    public HashMap<String, String> deleteCandidate(@PathVariable long employeeId) {

        String msg=externalRecruitmentService.deleteCandidate(employeeId);
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "Success");
        response.put("message",msg);
        LocalDateTime systemTime = LocalDateTime.now();
        response.put("timestamp", systemTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy 'Time' HH:mm:ss")));
        return response;
    }


    @Operation(summary = "This end point return profiles that matches the given_skill_set with % match",
            description = "User Can send data via REQUEST PARAMETER: given_skill_set separated by comma(,)")
    @GetMapping("/profile/search")
    public List<CandidatePercentageMatchDTO> getExternalRecruitmentProfile(@RequestParam List<String> given_skill_set) throws BadRequestException {
        if(given_skill_set==null || given_skill_set.isEmpty()) {
            throw new BadRequestException("Skill set is required");
        }
        logger.info("Search profile using skills:{} in ERS-Method:{}", given_skill_set,getMethodName());
        return externalRecruitmentService.searchBySkillset(given_skill_set);
    }

    @Operation(summary = "It will returns candidates with Exact Given skill Match or more",
    description = "Takes parameter list of string for skillset")
    @GetMapping("/profile/search/exactmatch")
    public List<CandidateProfile> getExternalRecruitmentProfileExactmatch(@RequestParam List<String> given_skill_set) throws BadRequestException {
        if(given_skill_set==null || given_skill_set.isEmpty()) {
            throw new BadRequestException("Skill set is required");
        }
        return externalRecruitmentService.getAllcandidateWithExactSkillset(given_skill_set);
    }

    @Operation(summary = "For testing the application")
    @GetMapping("/home/test")
    public HashMap<String, String> externalRecruitmentService() {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "up-running");
        LocalDateTime systemTime = LocalDateTime.now();
        response.put("timestamp", systemTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy 'Time' HH:mm:ss")));
        logger.info("This is an info message");
        logger.debug("This is a debug message");
        logger.error("This is an error message");
        return response;
    }
    private String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

}
