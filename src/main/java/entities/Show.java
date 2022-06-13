package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
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
    @Column(name = "duration")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date duration;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "location")
    private String location;

    @Basic(optional = false)
    @NotNull
    @Column(name = "start_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date startDateTime;

    @JoinTable(name = "shows_guests", joinColumns = {
            @JoinColumn(name = "show_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "guest_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Guest> guestList = new ArrayList<>();

    public Show() {}

    public Show(String name, Date duration, String location, Date startDateTime) {
        this.name = name;
        this.duration = duration;
        this.location = location;
        this.startDateTime = startDateTime;
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public Date getDuration() {return duration;}
    public String getLocation() {return location;}
    public Date getStartDateTime() {return startDateTime;}
    public List<Guest> getGuestList() {return guestList;}

    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setDuration(Date duration) {this.duration = duration;}
    public void setLocation(String location) {this.location = location;}
    public void setStartDateTime(Date startDateTime) {this.startDateTime = startDateTime;}
    public void setGuestList(List<Guest> guestList) {this.guestList = guestList;}
    public void addGuest(Guest g) {this.guestList.add(g);}
}
