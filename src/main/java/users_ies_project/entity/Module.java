package users_ies_project.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "module")
public class Module {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_module")
    private Integer idModule;
    
    @Column(name = "name", length = 45, nullable = false)
    private String name;
    
    @Column(length = 45, nullable = false)
    private String description;
    
    @Column(length = 45, nullable = false)
    private String security;
    
    @Column(length = 45, nullable = false)
    private String administration;
    
    @Column(length = 45, nullable = false)
    private String commercial;

    @Column(length = 45, nullable = false)
    private String marketing;
    
    @ManyToOne
    @JoinColumn(name = "id_user_create", nullable = true)
    private User userCreate;

    @ManyToOne
    @JoinColumn(name = "id_user_update", nullable = true) 
    private User userUpdate;
    
    @Column(name = "date_create")
    private Date dateCreate;
    
    @Column(name = "date_update")
    private Date dateUpdate;

    public Integer getIdModule() {
        return idModule;
    }

    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getAdministration() {
        return administration;
    }

    public void setAdministration(String administration) {
        this.administration = administration;
    }

    public String getCommercial() {
        return commercial;
    }

    public void setCommercial(String commercial) {
        this.commercial = commercial;
    }

    public String getMarketing() {
        return marketing;
    }

    public void setMarketing(String marketing) {
        this.marketing = marketing;
    }

    public User getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(User userCreate) {
        this.userCreate = userCreate;
    }

    public User getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(User userUpdate) {
        this.userUpdate = userUpdate;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
} 