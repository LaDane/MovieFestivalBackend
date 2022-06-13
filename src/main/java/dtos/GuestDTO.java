package dtos;

import entities.Guest;
import entities.Show;

import java.util.ArrayList;
import java.util.List;

public class GuestDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String status;
    private List<ShowDTO> showList = new ArrayList<>();
    private FestivalDTO festival;

    public GuestDTO(String name, String phone, String email, String status, List<ShowDTO> showList, FestivalDTO festival) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.showList = showList;
        this.festival = festival;
    }

    public GuestDTO(Guest g) {
        if (g.getId() != null) {
            this.id = g.getId();
        }
        this.name = g.getName();
        this.phone = g.getPhone();
        this.email = g.getEmail();
        this.status = g.getStatus();
        for (Show s : g.getShowList()) {
            this.showList.add(new ShowDTO(s));
        }
        this.festival = new FestivalDTO(g.getFestival());
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getPhone() {return phone;}
    public String getEmail() {return email;}
    public String getStatus() {return status;}
    public List<ShowDTO> getShowList() {return showList;}
    public FestivalDTO getFestival() {return festival;}

    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setPhone(String phone) {this.phone = phone;}
    public void setEmail(String email) {this.email = email;}
    public void setStatus(String status) {this.status = status;}
    public void setShowList(List<ShowDTO> showList) {this.showList = showList;}
    public void addShow(ShowDTO s) {this.showList.add(s);}
    public void setFestival(FestivalDTO festival) {this.festival = festival;}
}
