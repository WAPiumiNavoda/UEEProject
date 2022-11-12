package com.example.ueefoodprotectionapp.data;

public class Meeting {
        private String meetupDate;
        private String venue;
        private String meeingDescription;
        private int meetRating;
        private double meetingId;
        private boolean isMeetCompleted;
        private String userId;
        private String title;
        private String otherParty;


    public Meeting(){}

    public Meeting(String meetupDate, String venue, String meeingDescription, int meetRating, double meetingId, boolean isMeetCompleted, String userId) {
        this.meetupDate = meetupDate;
        this.venue = venue;
        this.meeingDescription = meeingDescription;
        this.meetRating = meetRating;
        this.meetingId = meetingId;
        this.isMeetCompleted = isMeetCompleted;
        this.userId = userId;
    }

    public Meeting(String meetupDate, String venue, String meeingDescription, String userId, String title, String otherParty) {
        this.meetupDate = meetupDate;
        this.venue = venue;
        this.meeingDescription = meeingDescription;
        this.userId = userId;
        this.title = title;
        this.otherParty = otherParty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOtherParty() {
        return otherParty;
    }

    public void setOtherParty(String otherParty) {
        this.otherParty = otherParty;
    }


    public double getMeetingId() {
        return meetingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMeetingId(double meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetupDate() {
        return meetupDate;
    }

    public void setMeetupDate(String meetupDate) {
        this.meetupDate = meetupDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getMeeingDescription() {
        return meeingDescription;
    }

    public void setMeeingDescription(String meeingDescription) {
        this.meeingDescription = meeingDescription;
    }

    public int getMeetRating() {
        return meetRating;
    }

    public void setMeetRating(int meetRating) {
        this.meetRating = meetRating;
    }

    public boolean isMeetCompleted() {
        return isMeetCompleted;
    }

    public void setMeetCompleted(boolean meetCompleted) {
        isMeetCompleted = meetCompleted;
    }
}
