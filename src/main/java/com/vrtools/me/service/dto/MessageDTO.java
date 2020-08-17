package com.vrtools.me.service.dto;

import java.time.LocalDateTime;

public class MessageDTO {

    private String title, text;
    private LocalDateTime time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTime() {
        return LocalDateTime.now();
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
