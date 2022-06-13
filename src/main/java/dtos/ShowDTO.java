package dtos;

import entities.Guest;
import entities.Show;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowDTO {
    private Long id;
    private String name;
    private String location;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<GuestDTO> guestList = new ArrayList<>();

    public ShowDTO(String name, String location, LocalDateTime startDateTime, LocalDateTime endDateTime, List<GuestDTO> guestList) {
        this.name = name;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.guestList = guestList;
    }

    public ShowDTO(Show s) {
        if (s.getId() != null) {
            this.id = s.getId();
        }
        this.name = s.getName();
        this.location = s.getLocation();
        this.startDateTime = s.getStartDateTime();
        this.endDateTime = s.getEndDateTime();
        for (Guest g : s.getGuestList()) {
            this.guestList.add(new GuestDTO(g));
        }
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getLocation() {return location;}
    public LocalDateTime getStartDateTime() {return startDateTime;}
    public LocalDateTime getEndDateTime() {return endDateTime;}
    public List<GuestDTO> getGuestList() {return guestList;}

    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setLocation(String location) {this.location = location;}
    public void setStartDateTime(LocalDateTime startDateTime) {this.startDateTime = startDateTime;}
    public void setEndDateTime(LocalDateTime endDateTime) {this.endDateTime = endDateTime;}
    public void setGuestList(List<GuestDTO> guestList) {this.guestList = guestList;}
    public void addGuest(GuestDTO g) {this.guestList.add(g);}
}
