package entities;

import dtos.FestivalDTO;
import dtos.GuestDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "festivals")
public class Festival implements Serializable {
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
    @Column(name = "city")
    private String city;

    @Basic(optional = false)
    @NotNull
    @Column(name = "start_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date startDateTime;

    @Basic(optional = false)
    @NotNull
    @Column(name = "duration")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date duration;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="festival")
    private List<Guest> guestList = new ArrayList<>();

    public Festival() {}

    public Festival(String name, String city, Date startDateTime, Date duration) {
        this.name = name;
        this.city = city;
        this.startDateTime = startDateTime;
        this.duration = duration;
    }

    public Festival(FestivalDTO f) {
        if (f.getId() != null) {
            this.id = f.getId();
        }
        this.name = f.getName();
        this.city = f.getCity();
        this.startDateTime = f.getStartDateTime();
        this.duration = f.getDuration();
        for (GuestDTO g : f.getGuestList()) {
            this.guestList.add(new Guest(g));
        }
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getCity() {return city;}
    public Date getStartDateTime() {return startDateTime;}
    public Date getDuration() {return duration;}
    public List<Guest> getGuestList() {return guestList;}

    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setCity(String city) {this.city = city;}
    public void setStartDateTime(Date startDateTime) {this.startDateTime = startDateTime;}
    public void setDuration(Date duration) {this.duration = duration;}
    public void setGuestList(List<Guest> guestList) {this.guestList = guestList;}
    public void addGuest(Guest g) {this.guestList.add(g);}
}
