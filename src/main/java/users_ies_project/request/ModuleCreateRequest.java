package users_ies_project.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ModuleCreateRequest {
    @NotNull(message = "El usuario creador es obligatorio")
    private Integer idUserCreate;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 45, message = "El nombre no puede superar 45 caracteres")
    private String name;

    @NotBlank(message = "El description es obligatorio")
    @Size(max = 45, message = "El description no puede superar 45 caracteres")
    private String description;

    @NotBlank(message = "El security es obligatorio")
    @Size(max = 45, message = "El security no puede superar 45 caracteres")
    private String security;

    @NotBlank(message = "El administration es obligatorio")
    @Size(max = 45, message = "El administration no puede superar 45 caracteres")
    private String administration;

    @NotBlank(message = "El commercial es obligatorio")
    @Size(max = 45, message = "El commercial no puede superar 45 caracteres")
    private String commercial;

    @NotBlank(message = "El marketing es obligatorio")
    @Size(max = 45, message = "El marketing no puede superar 45 caracteres")
    private String marketing;

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
}
