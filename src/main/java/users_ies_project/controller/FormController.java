package users_ies_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import users_ies_project.dto.InfoCreateEditGeneric;
import users_ies_project.entity.Form;
import users_ies_project.info.UserInfoDTO;
import users_ies_project.repository.FormRepository;
import users_ies_project.repository.UserRepository;
import users_ies_project.request.FormCreateRequest;
import users_ies_project.request.FormUpdateRequest;
import users_ies_project.response.ErrorResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/api/forms")
public class FormController {

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Form> index() {
        return formRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {
        Optional<Form> form = formRepository.findById(id);
        if (form.isPresent()) {
            return ResponseEntity.ok(form.get());
        }
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró el formulario"));
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody FormCreateRequest request, BindingResult result) {        
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errores.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }

        if (!userRepository.existsById(request.getIdUserCreate())) {
            return ResponseEntity.badRequest().body("El usuario creador ingresado no existe");
        }

        Form form = new Form();
        form.setName(request.getName());
        form.setUserCreate(userRepository.getReferenceById(request.getIdUserCreate()));
        form.setDateCreate(new Date());
        formRepository.save(form);

        return ResponseEntity.ok("Formulario creada exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,@Valid @RequestBody FormUpdateRequest request,BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errores.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }
    
        Optional<Form> optionalForm = formRepository.findById(id);
        if (!optionalForm.isPresent()) {
            return ResponseEntity.badRequest().body("El formulario ingresada no existe");
        }

        if (!userRepository.existsById(request.getIdUserUpdate())) {
            return ResponseEntity.badRequest().body("El usuario actualizador ingresado no existe");
        }
    
        Form form = optionalForm.get();
        form.setName(request.getName());
        form.setUserUpdate(userRepository.getReferenceById(request.getIdUserUpdate()));
        form.setDateUpdate(new Date()); 
        formRepository.save(form);
    
        return ResponseEntity.ok("Formulario actualizada correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Form> formOptional = formRepository.findById(id);
        if (formOptional.isPresent()) {
            Form form = formOptional.get();
            formRepository.delete(form);
            return ResponseEntity.ok("Formulario eliminado correctamente");
        }
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró el formulario"));
    }

    @GetMapping("/create")
    public ResponseEntity<InfoCreateEditGeneric<Form>> getCreateData() {
        InfoCreateEditGeneric<Form> response = new InfoCreateEditGeneric<>();
        
        // Obtener la lista de usuarios
        List<UserInfoDTO> users = userRepository.findAll().stream().map(user -> {
            UserInfoDTO dto = new UserInfoDTO();
            dto.setIdUser(user.getIdUser());
            dto.setName(user.getName());
            return dto;
        }).collect(Collectors.toList());
        
        response.setUsers(users);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/edit")
    public ResponseEntity<?> getEditData(@PathVariable Integer id) {
        Optional<Form> formOptional = formRepository.findById(id);
        if (formOptional.isPresent()) {
            Form form = formOptional.get();
        
            InfoCreateEditGeneric<Form> response = new InfoCreateEditGeneric<>();
            response.setEntity(form);

            // Obtener la lista de usuarios
            List<UserInfoDTO> users = userRepository.findAll().stream().map(user -> {
                UserInfoDTO dto = new UserInfoDTO();
                dto.setIdUser(user.getIdUser());
                dto.setName(user.getName());
                return dto;
            }).collect(Collectors.toList());
            
            response.setUsers(users);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró el formulario"));
    }
} 