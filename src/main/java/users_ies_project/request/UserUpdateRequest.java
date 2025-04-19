package users_ies_project.request;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserUpdateRequest {
    @NotBlank(message = "El campo nombre no puede estar vacío")
    @Size(max = 45, message = "El campo nombre no puede tener más de 45 caracteres")
    @Column(length = 45, nullable = false)
    private String name;
    
    @NotBlank(message = "El campo email no puede estar vacío")
    @Size(max = 45, message = "El campo email no puede tener más de 45 caracteres")
    @Column(length = 45, nullable = false)
    private String email;
    
    @NotBlank(message = "El campo contraseña no puede estar vacío")
    @Size(max = 45, message = "El campo contraseña no puede tener más de 45 caracteres")
    @Column(length = 45, nullable = false)
    private String password;
    
    @NotNull(message = "El campo token no puede estar vacío")
    @Column(nullable = false)
    private Integer token;

    @Column(name = "date_token")
    private LocalDate dateToken;

    // Getters y Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getToken() {
        return token;
    }
    public void setToken(Integer token) {
        this.token = token;
    }
    public LocalDate getDateToken() {
        return dateToken;
    }

    public void setDateToken(LocalDate dateToken) {
        this.dateToken = dateToken;
    }
}
