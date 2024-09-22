package com.asifiqbalsekh.RequirementServiceBackend.DTO;

import java.util.List;

public class SkillSetDTO {
    private List<String> requiredSkill;
    private List<String> optionalSkill;

    public SkillSetDTO() {

    }

    public SkillSetDTO(List<String> requiredSkill, List<String> optionalSkill) {
        this.requiredSkill = requiredSkill;
        this.optionalSkill = optionalSkill;
    }

    public List<String> getRequiredSkill() {
        return requiredSkill;
    }

    public void setRequiredSkill(List<String> requiredSkill) {
        this.requiredSkill = requiredSkill;
    }

    public List<String> getOptionalSkill() {
        return optionalSkill;
    }

    public void setOptionalSkill(List<String> optionalSkill) {
        this.optionalSkill = optionalSkill;
    }
}
