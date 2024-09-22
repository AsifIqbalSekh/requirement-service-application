package com.asifiqbalsekh.RequirementServiceBackend.controller;

import com.asifiqbalsekh.RequirementServiceBackend.DTO.SkillSetDTO;
import com.asifiqbalsekh.RequirementServiceBackend.model.CandidateProfile;
import com.asifiqbalsekh.RequirementServiceBackend.model.InternalCandidateProfile;
import com.asifiqbalsekh.RequirementServiceBackend.service.InternalRecruitmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/internal-recruitment-service")
public class InternalRecruitmentController {

    private final InternalRecruitmentService internalRecruitmentService;


    @Autowired
    InternalRecruitmentController(InternalRecruitmentService internalRecruitmentService) {
        this.internalRecruitmentService = internalRecruitmentService;

    }

    @Operation(summary = "For getting all stored candidate details, supports cache mechanism",
            description = "It will return all Candidate profile details")
    @GetMapping("/profile")
    public List<InternalCandidateProfile> getInternalRecruitmentProfile
                    (@RequestHeader(value = "cache-control", required = false)String cacheControl) {

        return internalRecruitmentService.findAll(cacheControl);
    }

    @Operation(summary = "This end point Store the given profile in database and Respond with the stored candidate details",
            description = "USER Can send data via Request Body, email id should be unique")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/profile")
    public InternalCandidateProfile postCandidate(@RequestBody InternalCandidateProfile given_profile) throws BadRequestException {
        if(given_profile.getSkillSet()==null || given_profile.getSkillSet().isEmpty()) {
            throw new BadRequestException("Skill set is required");
        }
        given_profile.setEmployeeId(0);//For new store
        return internalRecruitmentService.addCandidate(given_profile);
    }


    @Operation(summary = "The end point update the profile and return updated candidate details",
            description = "USER Can send data via  JSON in request body")
    @PutMapping("/profile")
    public InternalCandidateProfile updateCandidate(@RequestBody InternalCandidateProfile given_profile) throws BadRequestException {
        if(given_profile.getSkillSet()==null || given_profile.getSkillSet().isEmpty()) {
            throw new BadRequestException("Skill set is required");
        }
        return internalRecruitmentService.addCandidate(given_profile);
    }

    @Operation(summary = "Delete stored candidate details, required MANAGER role to access",
            description = "Take Candidate id as PathVariable")
    @DeleteMapping("/profile/{employeeId}")
    public HashMap<String, String> deleteCandidate(@PathVariable long employeeId) {

        String msg=internalRecruitmentService.deleteById(employeeId);
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "Success");
        response.put("message",msg);
        LocalDateTime systemTime = LocalDateTime.now();
        response.put("timestamp", systemTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy 'Time' HH:mm:ss")));
        return response;
    }


    //Exact Skill Search

    @Operation(summary = "Return Candidates Based On Skill Set from IRS or ERS",
            description = "Returns a list of all candidates from IRS if (required and optional) or optional skill sets match otherwise returns candidate from ERS based on Required and Optional Skill Set")
    @PostMapping ("/profile/search/exactmatch")
    public List<CandidateProfile> getInternalRecruitmentProfileExactmatch(@RequestBody SkillSetDTO given_skill)
            throws BadRequestException {

        if(given_skill.getRequiredSkill()==null || given_skill.getRequiredSkill().isEmpty()) {
            throw new BadRequestException("Skill set is required");
        }
        if (given_skill.getOptionalSkill()==null){
            given_skill.setOptionalSkill(new ArrayList<>());
        }

        return internalRecruitmentService.findProfileBySkill
                (given_skill.getRequiredSkill(),given_skill.getOptionalSkill());
    }

}
