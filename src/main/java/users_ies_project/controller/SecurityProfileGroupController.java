package users_ies_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import users_ies_project.entity.SecurityProfileGroup;
import users_ies_project.info.ModuleInfoDTO;
import users_ies_project.info.ProfileInfoDTO;
import users_ies_project.info.UserInfoDTO;
import users_ies_project.dto.InfoCreateEditSecurityProfileGroup;
import users_ies_project.repository.ModuleRepository;
import users_ies_project.repository.ProfileRepository;
import users_ies_project.repository.SecurityProfileGroupRepository;
import users_ies_project.repository.UserRepository;
import users_ies_project.request.SecurityProfileGroupCreateRequest;
import users_ies_project.request.SecurityProfileGroupUpdateRequest;
import users_ies_project.response.ErrorResponseDTO;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/security-profile-groups")
public class SecurityProfileGroupController {

    @Autowired
    private SecurityProfileGroupRepository securityProfileGroupRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<SecurityProfileGroup> index() {
        return securityProfileGroupRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {
        Optional<SecurityProfileGroup> securityProfileGroup = securityProfileGroupRepository.findById(id);
        if (securityProfileGroup.isPresent()) {
            return ResponseEntity.ok(securityProfileGroup.get());
        }
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró el grupo de perfil de seguridad"));
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody SecurityProfileGroupCreateRequest request, BindingResult result) {        
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errores.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }

        if (!profileRepository.existsById(request.getIdProfile())) {
            return ResponseEntity.badRequest().body("El perfil ingresado no existe");
        }

        if (!userRepository.existsById(request.getIdUserCreate())) {
            return ResponseEntity.badRequest().body("El usuario creador ingresado no existe");
        }

        if (!moduleRepository.existsById(request.getIdModule())) {
            return ResponseEntity.badRequest().body("El módulo ingresado no existe");
        }

        SecurityProfileGroup securityProfileGroup = new SecurityProfileGroup();
        securityProfileGroup.setProfile(profileRepository.getReferenceById(request.getIdProfile())); 
        securityProfileGroup.setModule(moduleRepository.getReferenceById(request.getIdModule())); 
        securityProfileGroup.setName(request.getName());
        securityProfileGroup.setUserCreate(userRepository.getReferenceById(request.getIdUserCreate()));
        securityProfileGroup.setDateCreate(new Date());

        securityProfileGroupRepository.save(securityProfileGroup);

        return ResponseEntity.ok("Grupo de perfil de seguridad creado exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,@Valid @RequestBody SecurityProfileGroupUpdateRequest request,BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errores.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }
    
        Optional<SecurityProfileGroup> optionalGroup = securityProfileGroupRepository.findById(id);
        if (!optionalGroup.isPresent()) {
            return ResponseEntity.badRequest().body("No se encontró el grupo de perfil de seguridad");
        }
    
        if (!profileRepository.existsById(request.getIdProfile())) {
            return ResponseEntity.badRequest().body("El perfil ingresado no existe");
        }
        
        if (!userRepository.existsById(request.getIdUserUpdate())) {
            return ResponseEntity.badRequest().body("El usuario actualizador ingresado no existe");
        }
    
        if (!moduleRepository.existsById(request.getIdModule())) {
            return ResponseEntity.badRequest().body("El módulo ingresado no existe");
        }
    
        SecurityProfileGroup securityProfileGroup = optionalGroup.get();
        securityProfileGroup.setProfile(profileRepository.getReferenceById(request.getIdProfile()));
        securityProfileGroup.setModule(moduleRepository.getReferenceById(request.getIdModule()));
        securityProfileGroup.setName(request.getName());
        securityProfileGroup.setUserUpdate(userRepository.getReferenceById(request.getIdUserUpdate()));
        securityProfileGroup.setDateUpdate(new Date());
    
        securityProfileGroupRepository.save(securityProfileGroup);
    
        return ResponseEntity.ok("Grupo de perfil de seguridad actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<SecurityProfileGroup> securityProfileGroup = securityProfileGroupRepository.findById(id);
        if (securityProfileGroup.isPresent()) {
            securityProfileGroupRepository.delete(securityProfileGroup.get());
            return ResponseEntity.ok("Grupo de perfil de seguridad eliminado correctamente");
        }
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró el grupo de perfil de seguridad"));
    }

    @GetMapping("/create")
    public InfoCreateEditSecurityProfileGroup<SecurityProfileGroup> getCreateData() {
        InfoCreateEditSecurityProfileGroup<SecurityProfileGroup> response = new InfoCreateEditSecurityProfileGroup<>();
        
        // Obtener la lista de usuarios
        List<UserInfoDTO> users = userRepository.findAll().stream().map(user -> {
            UserInfoDTO dto = new UserInfoDTO();
            dto.setIdUser(user.getIdUser());
            dto.setName(user.getName());
            return dto;
        }).collect(Collectors.toList());

        // Obtener la lista de perfiles
        List<ProfileInfoDTO> profiles = profileRepository.findAll().stream().map(profile -> {
            ProfileInfoDTO dto = new ProfileInfoDTO();
            dto.setIdProfile(profile.getIdProfile());
            dto.setName(profile.getName());
            return dto;
        }).collect(Collectors.toList());

        // Obtener la lista de módulos
        List<ModuleInfoDTO> modules = moduleRepository.findAll().stream().map(module -> {
            ModuleInfoDTO dto = new ModuleInfoDTO();
            dto.setIdModule(module.getIdModule());
            dto.setName(module.getName());
            return dto;
        }).collect(Collectors.toList());
        
        response.setUsers(users);
        response.setProfiles(profiles);
        response.setModules(modules);
        return response;
    }

    @GetMapping("/{id}/edit")
    public ResponseEntity<?> getEditData(@PathVariable Integer id) {
        Optional<SecurityProfileGroup> optionalSecurityProfileGroup = securityProfileGroupRepository.findById(id);
        if (optionalSecurityProfileGroup.isPresent()) {
            InfoCreateEditSecurityProfileGroup<SecurityProfileGroup> response = new InfoCreateEditSecurityProfileGroup<>();
            response.setEntity(optionalSecurityProfileGroup.get());
            
            // Obtener la lista de usuarios
            List<UserInfoDTO> users = userRepository.findAll().stream().map(user -> {
                UserInfoDTO dto = new UserInfoDTO();
                dto.setIdUser(user.getIdUser());
                dto.setName(user.getName());
                return dto;
            }).collect(Collectors.toList());

            // Obtener la lista de perfiles
            List<ProfileInfoDTO> profiles = profileRepository.findAll().stream().map(profile -> {
                ProfileInfoDTO dto = new ProfileInfoDTO();
                dto.setIdProfile(profile.getIdProfile());
                dto.setName(profile.getName());
                return dto;
            }).collect(Collectors.toList());

            // Obtener la lista de módulos
            List<ModuleInfoDTO> modules = moduleRepository.findAll().stream().map(module -> {
                ModuleInfoDTO dto = new ModuleInfoDTO();
                dto.setIdModule(module.getIdModule());
                dto.setName(module.getName());
                return dto;
            }).collect(Collectors.toList());
            
            response.setUsers(users);
            response.setProfiles(profiles);
            response.setModules(modules);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró el grupo de perfil de seguridad"));
    }
} 