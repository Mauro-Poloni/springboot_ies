package users_ies_project.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProfileCreateRequest {
    @NotNull(message = "El usuario creador es obligatorio")
    private Integer idUserCreate;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 45, message = "El nombre no puede superar 45 caracteres")
    private String name;

    @NotNull(message = "El campo administrador no puede estar vacío")
    @Column(nullable = false)
    private Boolean admin = false;

    public Integer getIdUserCreate() {
        return idUserCreate;
    }    

    public void setIdUserCreate(Integer idUserCreate) {
        this.idUserCreate = idUserCreate;
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
