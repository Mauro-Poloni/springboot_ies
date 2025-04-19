package users_ies_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import users_ies_project.entity.SecurityModuleUser;
import users_ies_project.info.FormInfoDTO;
import users_ies_project.info.UserInfoDTO;
import users_ies_project.dto.InfoCreateEditSecurityModuleUser;
import users_ies_project.repository.FormRepository;
import users_ies_project.repository.SecurityModuleUserRepository;
import users_ies_project.repository.UserRepository;
import users_ies_project.request.SecurityModuleUserCreateRequest;
import users_ies_project.request.SecurityModuleUserUpdateRequest;
import users_ies_project.response.ErrorResponseDTO;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/security-module-users")
public class SecurityModuleUserController {

    @Autowired
    private SecurityModuleUserRepository securityModuleUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FormRepository formRepository;

    @GetMapping
    public List<SecurityModuleUser> index() {
        return securityModuleUserRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {
        Optional<SecurityModuleUser> securityModuleUser = securityModuleUserRepository.findById(id);
        if (securityModuleUser.isPresent()) {
            return ResponseEntity.ok(securityModuleUser.get());
        }
        ErrorResponseDTO error = new ErrorResponseDTO("No se encontró el módulo de seguridad de usuario");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody SecurityModuleUserCreateRequest request, BindingResult result) {        
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errores.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }

        if (!formRepository.existsById(request.getIdForm())) {
            return ResponseEntity.badRequest().body("El formulario ingresado no existe");
        }
        
        if (!userRepository.existsById(request.getIdUserCreate())) {
            return ResponseEntity.badRequest().body("El usuario creador ingresado no existe");
        }
        
        if (!userRepository.existsById(request.getIdUser())) {
            return ResponseEntity.badRequest().body("El usuario ingresado no existe");
        }

        SecurityModuleUser securityModuleUser = new SecurityModuleUser();
        securityModuleUser.setForm(formRepository.getReferenceById(request.getIdForm())); 
        securityModuleUser.setUser(userRepository.getReferenceById(request.getIdUser())); 
        securityModuleUser.setState(request.getState());
        securityModuleUser.setUserCreate(userRepository.getReferenceById(request.getIdUserCreate()));
        securityModuleUser.setDateCreate(new Date());

        securityModuleUserRepository.save(securityModuleUser);

        return ResponseEntity.ok("Módulo de seguridad de usuario creado exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,@Valid @RequestBody SecurityModuleUserUpdateRequest request,BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errores.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }

        Optional<SecurityModuleUser> optionalSecurityModuleUser = securityModuleUserRepository.findById(id);
        if (!optionalSecurityModuleUser.isPresent()) {
            return ResponseEntity.badRequest().body("El Módulo de seguridad de usuario ingresado no existe");
        }
    
        if (!formRepository.existsById(request.getIdForm())) {
            return ResponseEntity.badRequest().body("El formulario ingresado no existe");
        }
                
        if (!userRepository.existsById(request.getIdUserUpdate())) {
            return ResponseEntity.badRequest().body("El usuario actualizador ingresado no existe");
        }
    
        if (!userRepository.existsById(request.getIdUser())) {
            return ResponseEntity.badRequest().body("El usuario ingresado no existe");
        }
    
        SecurityModuleUser securityModuleUser = optionalSecurityModuleUser.get();
        securityModuleUser.setForm(formRepository.getReferenceById(request.getIdForm()));
        securityModuleUser.setUser(userRepository.getReferenceById(request.getIdUser()));
        securityModuleUser.setState(request.getState());
        securityModuleUser.setUserUpdate(userRepository.getReferenceById(request.getIdUserUpdate()));
        securityModuleUser.setDateUpdate(new Date());
        securityModuleUserRepository.save(securityModuleUser);
    
        return ResponseEntity.ok("Módulo de seguridad de usuario actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<SecurityModuleUser> securityModuleUser = securityModuleUserRepository.findById(id);
        if (securityModuleUser.isPresent()) {
            securityModuleUserRepository.delete(securityModuleUser.get());
            return ResponseEntity.ok("Módulo de seguridad de usuario eliminado correctamente");
        }
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró el módulo de seguridad de usuario"));
    }

    @GetMapping("/create")
    public InfoCreateEditSecurityModuleUser<SecurityModuleUser> getCreateData() {
        InfoCreateEditSecurityModuleUser<SecurityModuleUser> response = new InfoCreateEditSecurityModuleUser<>();
        
        // Obtener la lista de usuarios
        List<UserInfoDTO> users = userRepository.findAll().stream().map(user -> {
            UserInfoDTO dto = new UserInfoDTO();
            dto.setIdUser(user.getIdUser());
            dto.setName(user.getName());
            return dto;
        }).collect(Collectors.toList());

        // Obtener la lista de formularios
        List<FormInfoDTO> forms = formRepository.findAll().stream().map(form -> {
            FormInfoDTO dto = new FormInfoDTO();
            dto.setIdForm(form.getIdForm());
            dto.setName(form.getName());
            return dto;
        }).collect(Collectors.toList());
        
        response.setUsers(users);
        response.setForms(forms);
        return response;
    }

    @GetMapping("/{id}/edit")
    public ResponseEntity<?> getEditData(@PathVariable Integer id) {
        Optional<SecurityModuleUser> optionalSecurityModuleUser = securityModuleUserRepository.findById(id);
        if (optionalSecurityModuleUser.isPresent()) {
            InfoCreateEditSecurityModuleUser<SecurityModuleUser> response = new InfoCreateEditSecurityModuleUser<>();
            response.setEntity(optionalSecurityModuleUser.get());
            
            // Obtener la lista de usuarios
            List<UserInfoDTO> users = userRepository.findAll().stream().map(user -> {
                UserInfoDTO dto = new UserInfoDTO();
                dto.setIdUser(user.getIdUser());
                dto.setName(user.getName());
                return dto;
            }).collect(Collectors.toList());

            // Obtener la lista de formularios
            List<FormInfoDTO> forms = formRepository.findAll().stream().map(form -> {
                FormInfoDTO dto = new FormInfoDTO();
                dto.setIdForm(form.getIdForm());
                dto.setName(form.getName());
                return dto;
            }).collect(Collectors.toList());
            
            response.setUsers(users);
            response.setForms(forms);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró el módulo de seguridad de usuario"));
    }
} 