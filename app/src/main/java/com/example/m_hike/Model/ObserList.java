package com.example.m_hike.Model;

public class ObserList {
    private int obsId;
    private int hikeId;
    private String observation;
    private String day;
    private String time;
    private String comment;

    public ObserList(int obsId, int hikeId, String observation, String day, String time, String comment) {
        this.obsId = obsId;
        this.hikeId = hikeId;
        this.observation = observation;
        this.day = day;
        this.time = time;
        this.comment = comment;
    }

    public int getObserId() { return obsId; }
    public int getHikeId() { return hikeId; }
    public String getObservation() { return observation; }
    public String getDay() { return day; }
    public String getTime() { return time; }
    public String getComment() { return comment; }

}
