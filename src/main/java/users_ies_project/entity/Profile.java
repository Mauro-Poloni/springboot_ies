package users_ies_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "profile")
public class Profile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profile")
    private Integer idProfile;
    
    @NotBlank(message = "El campo nombre no puede estar vacío")
    @Size(max = 45, message = "El campo nombre no puede tener más de 45 caracteres")
    @Column(length = 45, nullable = false)
    private String name;
    
    @NotNull(message = "El campo administrador no puede estar vacío")
    @Column(nullable = false)
    private Boolean admin = false;
    
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

    // Getters y Setters
    public Integer getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(Integer idProfile) {
        this.idProfile = idProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
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