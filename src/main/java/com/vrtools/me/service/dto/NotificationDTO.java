package com.vrtools.me.service.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class NotificationDTO {

    @NotNull
    private String introduction, about, conclusion;
    private LocalDateTime sentAt;

    public NotificationDTO(@NotNull String introduction, @NotNull String about, @NotNull String conclusion) {
        this.introduction = introduction;
        this.about = about;
        this.conclusion = conclusion;
        this.sentAt = LocalDateTime.now();
    }

    public NotificationDTO() {
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
