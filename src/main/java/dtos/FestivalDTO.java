package dtos;

import entities.Festival;
import entities.Guest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FestivalDTO {
    private Long id;
    private String name;
    private String city;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
//    private List<GuestDTO> guestList = new ArrayList<>();

    public FestivalDTO(String name, String city, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.name = name;
        this.city = city;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
//        this.guestList = guestList;
    }

    public FestivalDTO(Festival f) {
        if (f.getId() != null) {
            this.id = f.getId();
        }
        this.name = f.getName();
        this.city = f.getCity();
        this.startDateTime = f.getStartDateTime();
        this.endDateTime = f.getEndDateTime();
//        for (Guest g : f.getGuestList()) {
//            this.guestList.add(new GuestDTO(g));
//        }
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getCity() {return city;}
    public LocalDateTime getStartDateTime() {return startDateTime;}
    public LocalDateTime getEndDateTime() {return endDateTime;}
//    public List<GuestDTO> getGuestList() {return guestList;}

    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setCity(String city) {this.city = city;}
    public void setStartDateTime(LocalDateTime startDateTime) {this.startDateTime = startDateTime;}
    public void setEndDateTime(LocalDateTime endDateTime) {this.endDateTime = endDateTime;}
//    public void setGuestList(List<GuestDTO> guestList) {this.guestList = guestList;}
//    public void addGuest(GuestDTO g) {this.guestList.add(g);}
}
