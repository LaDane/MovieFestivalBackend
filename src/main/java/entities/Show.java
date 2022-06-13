package entities;

import dtos.GuestDTO;
import dtos.ShowDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shows")
public class Show implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "location")
    private String location;

    @Basic(optional = false)
    @NotNull
    @Column(name = "start_datetime")
    private LocalDateTime startDateTime;
//    @Temporal(TemporalType.TIMESTAMP)
//    private java.util.Date startDateTime;

    @Basic(optional = false)
    @NotNull
    @Column(name = "end_datetime")
    private LocalDateTime endDateTime;
//    @Temporal(TemporalType.TIMESTAMP)
//    private java.util.Date endDateTime;

    @JoinTable(name = "shows_guests", joinColumns = {
            @JoinColumn(name = "show_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "guest_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Guest> guestList = new ArrayList<>();

    public Show() {}

    public Show(String name, String location, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.name = name;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Show(ShowDTO s) {
        if (s.getId() != null) {
            this.id = s.getId();
        }
        this.name = s.getName();
        this.location = s.getLocation();
        this.startDateTime = s.getStartDateTime();
        this.endDateTime = s.getEndDateTime();
        for (GuestDTO g : s.getGuestList()) {
            this.guestList.add(new Guest(g));
        }
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getLocation() {return location;}
    public LocalDateTime getStartDateTime() {return startDateTime;}
    public LocalDateTime getEndDateTime() {return endDateTime;}
    public List<Guest> getGuestList() {return guestList;}

    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setLocation(String location) {this.location = location;}
    public void setStartDateTime(LocalDateTime startDateTime) {this.startDateTime = startDateTime;}
    public void setEndDateTime(LocalDateTime endDateTime) {this.endDateTime = endDateTime;}
    public void setGuestList(List<Guest> guestList) {this.guestList = guestList;}
    public void addGuest(Guest g) {this.guestList.add(g);}
}
