package users_ies_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import users_ies_project.entity.Profile;
import users_ies_project.info.UserInfoDTO;
import users_ies_project.dto.InfoCreateEditGeneric;
import users_ies_project.repository.ProfileRepository;
import users_ies_project.repository.UserRepository;
import users_ies_project.request.ProfileCreateRequest;
import users_ies_project.request.ProfileUpdateRequest;
import users_ies_project.response.ErrorResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Profile> index() {
        return profileRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {
        Optional<Profile> profile = profileRepository.findById(id);
        if (profile.isPresent()) {
            return ResponseEntity.ok(profile.get());
        }
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró el perfil"));
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody ProfileCreateRequest request, BindingResult result) {        
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

        Profile profile = new Profile();
        profile.setName(request.getName());
        profile.setAdmin(request.getAdmin());
        profile.setUserCreate(userRepository.getReferenceById(request.getIdUserCreate()));
        profile.setDateCreate(new Date());
        profileRepository.save(profile);

        return ResponseEntity.ok("Perfil creada exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,@Valid @RequestBody ProfileUpdateRequest request,BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errores.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }
    
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        if (!optionalProfile.isPresent()) {
            return ResponseEntity.badRequest().body("El perfil ingresada no existe");
        }

        if (!userRepository.existsById(request.getIdUserUpdate())) {
            return ResponseEntity.badRequest().body("El usuario actualizador ingresado no existe");
        }
    
        Profile profile = optionalProfile.get();
        profile.setName(request.getName());
        profile.setAdmin(request.getAdmin());
        profile.setUserUpdate(userRepository.getReferenceById(request.getIdUserUpdate()));
        profile.setDateUpdate(new Date()); 
        profileRepository.save(profile);
    
        return ResponseEntity.ok("Perfil actualizada correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();
            profileRepository.delete(profile);
            return ResponseEntity.ok("Perfil eliminado correctamente");
        }
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró el perfil"));
    }

    @GetMapping("/create")
    public InfoCreateEditGeneric<Profile> getCreateData() {
        InfoCreateEditGeneric<Profile> response = new InfoCreateEditGeneric<>();
        
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
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        if (optionalProfile.isPresent()) {
            InfoCreateEditGeneric<Profile> response = new InfoCreateEditGeneric<>();
            response.setEntity(optionalProfile.get());
            
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
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró el perfil"));
    }
} 