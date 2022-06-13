package dtos;

import entities.Guest;
import entities.Show;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowDTO {
    private Long id;
    private String name;
    private java.util.Date duration;
    private String location;
    private java.util.Date startDateTime;
    private List<GuestDTO> guestList = new ArrayList<>();

    public ShowDTO(String name, Date duration, String location, Date startDateTime, List<GuestDTO> guestList) {
        this.name = name;
        this.duration = duration;
        this.location = location;
        this.startDateTime = startDateTime;
        this.guestList = guestList;
    }

    public ShowDTO(Show s) {
        if (s.getId() != null) {
            this.id = s.getId();
        }
        this.name = s.getName();
        this.duration = s.getDuration();
        this.location = s.getLocation();
        this.startDateTime = s.getStartDateTime();
        for (Guest g : s.getGuestList()) {
            this.guestList.add(new GuestDTO(g));
        }
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public Date getDuration() {return duration;}
    public String getLocation() {return location;}
    public Date getStartDateTime() {return startDateTime;}
    public List<GuestDTO> getGuestList() {return guestList;}

    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setDuration(Date duration) {this.duration = duration;}
    public void setLocation(String location) {this.location = location;}
    public void setStartDateTime(Date startDateTime) {this.startDateTime = startDateTime;}
    public void setGuestList(List<GuestDTO> guestList) {this.guestList = guestList;}
    public void addGuest(GuestDTO g) {this.guestList.add(g);}
}
