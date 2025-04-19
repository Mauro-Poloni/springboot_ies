package users_ies_project.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SecurityModuleUserUpdateRequest {
    @NotNull(message = "El formulario es obligatorio")
    private Integer idForm;

    @NotNull(message = "El usuario es obligatorio")
    private Integer idUser;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 45, message = "El nombre no puede superar 45 caracteres")
    private String name;

    @NotNull(message = "El usuario actualizador es obligatorio")
    private Integer idUserUpdate;


    public Integer getIdForm() {
        return idForm;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public void setIdForm(Integer idForm) {
        this.idForm = idForm;
    }

    public String getState() {
        return name;
    }

    public void setState(String name) {
        this.name = name;
    }
    public Integer getIdUserUpdate() {
        return idUserUpdate;
    }
    public void setIdUserUpdate(Integer idUserUpdate) {
        this.idUserUpdate = idUserUpdate;
    }
}
