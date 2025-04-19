package users_ies_project.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "security_profile_group")
public class SecurityProfileGroup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_security_profile_group")
    private Integer idSecurityProfileGroup;

    @Column(length = 45, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_profile", nullable = false)
    private Profile profile;
    
    @ManyToOne
    @JoinColumn(name = "id_module", nullable = false)
    private Module module;
    
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

    public Integer getIdSecurityProfileGroup() {
        return idSecurityProfileGroup;
    }

    public void setIdSecurityProfileGroup(Integer idSecurityProfileGroup) {
        this.idSecurityProfileGroup = idSecurityProfileGroup;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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