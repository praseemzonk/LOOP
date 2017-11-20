package com.zonk.fbtest.Model;

import java.io.Serializable;

/**
 * Created by Ribbi on 10/29/2017.
 */

public class Skill implements Serializable{
boolean selected;
    int color;
    String skillName, skillIcon;
    int skillId, value;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillIcon() {
        return skillIcon;
    }

    public void setSkillIcon(String skillIcon) {
        this.skillIcon = skillIcon;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
