package users_ies_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import users_ies_project.entity.Module;
import users_ies_project.info.UserInfoDTO;
import users_ies_project.dto.InfoCreateEditGeneric;
import users_ies_project.repository.ModuleRepository;
import users_ies_project.repository.UserRepository;
import users_ies_project.request.ModuleCreateRequest;
import users_ies_project.request.ModuleUpdateRequest;
import users_ies_project.response.ErrorResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Module> index() {
        return moduleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {
        Optional<Module> module = moduleRepository.findById(id);
        if (module.isPresent()) {
            return ResponseEntity.ok(module.get());
        }
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró el módulo"));
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody ModuleCreateRequest request, BindingResult result) {        
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

        Module module = new Module();
        module.setName(request.getName());
        module.setDescription(request.getDescription());
        module.setSecurity(request.getSecurity());
        module.setAdministration(request.getAdministration());
        module.setCommercial(request.getCommercial());
        module.setMarketing(request.getMarketing());
        module.setUserCreate(userRepository.getReferenceById(request.getIdUserCreate()));
        module.setDateCreate(new Date());
        moduleRepository.save(module);

        return ResponseEntity.ok("Formulario creada exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,@Valid @RequestBody ModuleUpdateRequest request,BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errores.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }
    
        Optional<Module> optionalModule = moduleRepository.findById(id);
        if (!optionalModule.isPresent()) {
            return ResponseEntity.badRequest().body("El módulo ingresada no existe");
        }

        if (!userRepository.existsById(request.getIdUserUpdate())) {
            return ResponseEntity.badRequest().body("El usuario actualizador ingresado no existe");
        }
    
        Module module = optionalModule.get();
        module.setName(request.getName());
        module.setDescription(request.getDescription());
        module.setSecurity(request.getSecurity());
        module.setAdministration(request.getAdministration());
        module.setCommercial(request.getCommercial());
        module.setMarketing(request.getMarketing());
        module.setUserUpdate(userRepository.getReferenceById(request.getIdUserUpdate()));
        module.setDateUpdate(new Date()); 
        moduleRepository.save(module);
    
        return ResponseEntity.ok("Formulario actualizada correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Module> optionalModule = moduleRepository.findById(id);
        if(optionalModule.isPresent()) {
            Module module = optionalModule.get();
            moduleRepository.delete(module);
            return ResponseEntity.ok("Módulo eliminado correctamente");
        }
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró el módulo"));
    }

    @GetMapping("/create")
    public InfoCreateEditGeneric<Module> getCreateData() {
        InfoCreateEditGeneric<Module> response = new InfoCreateEditGeneric<>();
        
        // Obtener la lista de usuarios
        List<UserInfoDTO> users = userRepository.findAll().stream().map(user -> {
            UserInfoDTO dto = new UserInfoDTO();
            dto.setIdUser(user.getIdUser());
            dto.setName(user.getName());
            return dto;
        }).collect(Collectors.toList());
        
        response.setUsers(users);
        return response;
    }

    @GetMapping("/{id}/edit")
    public ResponseEntity<?> getEditData(@PathVariable Integer id) {
        Optional<Module> optionalModule = moduleRepository.findById(id);
        if (optionalModule.isPresent()) {
            Module module = optionalModule.get();
            InfoCreateEditGeneric<Module> response = new InfoCreateEditGeneric<>();
            response.setEntity(module);
            
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
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró el módulo"));
    }
} 