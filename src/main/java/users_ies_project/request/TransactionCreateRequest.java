package users_ies_project.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TransactionCreateRequest {
   
    @NotNull(message = "El usuario es obligatorio")
    private Integer idUser;

    @NotNull(message = "El módulo es obligatorio")
    private Integer idModule;

    @NotNull(message = "El formulario es obligatorio")
    private Integer idForm;

    @NotBlank(message = "El tipo de query es obligatorio")
    @Size(max = 255, message = "El tipo de query no puede superar 255 caracteres")
    private String query_type;

    @NotBlank(message = "El valor antiguo es obligatorio")
    @Size(max = 255, message = "El valor antiguo no puede superar 255 caracteres")
    private String old_value;

    @NotBlank(message = "El nuevo valor es obligatorio")
    @Size(max = 255, message = "El nuevo valor no puede superar 255 caracteres")
    private String new_value;

    @NotNull(message = "El usuario creador es obligatorio")
    private Integer idUserCreate;

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdModule() {
        return idModule;
    }

    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }

    public Integer getIdForm() {
        return idForm;
    }

    public void setIdForm(Integer idForm) {
        this.idForm = idForm;
    }

    public String getQueryType() {
        return query_type;
    }

    public void setQueryType(String query_type) {
        this.query_type = query_type;
    }

    public String getOldValue() {
        return old_value;
    }

    public void setOldValue(String old_value) {
        this.old_value = old_value;
    }

    public String getNewValue() {
        return new_value;
    }

    public void setNewValue(String new_value) {
        this.new_value = new_value;
    }
    public Integer getIdUserCreate() {
        return idUserCreate;
    }

    public void setIdUserCreate(Integer idUserCreate) {
        this.idUserCreate = idUserCreate;
    }
}
