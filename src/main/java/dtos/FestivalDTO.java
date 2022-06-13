package dtos;

import entities.Festival;
import entities.Guest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FestivalDTO {
    private Long id;
    private String name;
    private String city;
    private java.util.Date startDateTime;
    private java.util.Date duration;
    private List<GuestDTO> guestList = new ArrayList<>();

    public FestivalDTO(String name, String city, Date startDateTime, Date duration, List<GuestDTO> guestList) {
        this.name = name;
        this.city = city;
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.guestList = guestList;
    }

    public FestivalDTO(Festival f) {
        if (f.getId() != null) {
            this.id = f.getId();
        }
        this.name = f.getName();
        this.city = f.getCity();
        this.startDateTime = f.getStartDateTime();
        this.duration = f.getDuration();
        for (Guest g : f.getGuestList()) {
            this.guestList.add(new GuestDTO(g));
        }
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getCity() {return city;}
    public Date getStartDateTime() {return startDateTime;}
    public Date getDuration() {return duration;}
    public List<GuestDTO> getGuestList() {return guestList;}

    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setCity(String city) {this.city = city;}
    public void setStartDateTime(Date startDateTime) {this.startDateTime = startDateTime;}
    public void setDuration(Date duration) {this.duration = duration;}
    public void setGuestList(List<GuestDTO> guestList) {this.guestList = guestList;}
    public void addGuest(GuestDTO g) {this.guestList.add(g);}
}
