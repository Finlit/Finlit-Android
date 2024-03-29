package com.app.finlit.utils.notifications;

public class NotificationEvent {
    private String title;
    private String subject;
    private String body;
    private long eventDate;

    public NotificationEvent() {
    }

    public NotificationEvent(String title, String body, String subject) {
        this.title = title;
        this.body = body;
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public long getEventDate() {
        return eventDate;
    }

    public void setEventDate(long eventDate)
    {
        this.eventDate = eventDate;
    }
}
