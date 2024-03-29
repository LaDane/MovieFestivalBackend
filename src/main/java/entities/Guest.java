package entities;

import dtos.FestivalDTO;
import dtos.GuestDTO;
import dtos.ShowDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guests")
public class Guest implements Serializable {
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
    @Column(name = "phone")
    private String phone;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "email")
    private String email;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "status")
    private String status;

    @ManyToMany(mappedBy = "guestList")
    private List<Show> showList = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @OneToOne(mappedBy = "guest")
    private User user;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "username", referencedColumnName = "username")
//    private User user;

    public Guest() {}

    public Guest(String name, String phone, String email, String status) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public Guest(GuestDTO g) {
        if (g.getId() != null) {
            this.id = g.getId();
        }
        this.name = g.getName();
        this.phone = g.getPhone();
        this.email = g.getEmail();
        this.status = g.getStatus();
//        for (ShowDTO s : g.getShowList()) {
//            this.showList.add(new Show(s));
//        }
        if (g.getFestival() != null) {
            this.festival = new Festival(g.getFestival());
        }
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getPhone() {return phone;}
    public String getEmail() {return email;}
    public String getStatus() {return status;}
    public List<Show> getShowList() {return showList;}
    public Festival getFestival() {return festival;}

    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setPhone(String phone) {this.phone = phone;}
    public void setEmail(String email) {this.email = email;}
    public void setStatus(String status) {this.status = status;}
    public void setShowList(List<Show> showList) {this.showList = showList;}
    public void addShow(Show s) {this.showList.add(s);}
    public void setFestival(Festival festival) {this.festival = festival;}
}
