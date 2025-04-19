package users_ies_project.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "security_module_user")
public class SecurityModuleUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_security_module_user")
    private Integer idSecurityModuleUser;

    @Column(length = 45, nullable = false)
    private String state;
    
    @ManyToOne
    @JoinColumn(name = "id_form", nullable = false)
    private Form form;
    
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
    
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

    public Integer getIdSecurityModuleUser() {
        return idSecurityModuleUser;
    }

    public void setIdSecurityModuleUser(Integer idSecurityModuleUser) {
        this.idSecurityModuleUser = idSecurityModuleUser;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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