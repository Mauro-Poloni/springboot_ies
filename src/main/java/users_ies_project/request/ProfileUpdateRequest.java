package users_ies_project.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProfileUpdateRequest {
    @NotNull(message = "El usuario actualizador es obligatorio")
    private Integer idUserUpdate;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 45, message = "El nombre no puede superar 45 caracteres")
    private String name;

    @NotNull(message = "El campo administrador no puede estar vacío")
    @Column(nullable = false)
    private Boolean admin = false;

    public Integer getIdUserUpdate() {
        return idUserUpdate;
    }    

    public void setIdUserUpdate(Integer idUserUpdate) {
        this.idUserUpdate = idUserUpdate;
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
}
