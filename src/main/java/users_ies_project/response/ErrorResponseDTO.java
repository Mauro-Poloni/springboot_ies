package users_ies_project.response;

import lombok.Data;

@Data
public class ErrorResponseDTO {
    private String message;
    private boolean success;

    public ErrorResponseDTO(String message) {
        this.message = message;
        this.success = false;
    }
} 