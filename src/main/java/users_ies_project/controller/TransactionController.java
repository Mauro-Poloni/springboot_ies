package users_ies_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import users_ies_project.entity.Transaction;
import users_ies_project.info.FormInfoDTO;
import users_ies_project.info.ModuleInfoDTO;
import users_ies_project.info.UserInfoDTO;
import users_ies_project.dto.InfoCreateEditTransaction;
import users_ies_project.repository.FormRepository;
import users_ies_project.repository.ModuleRepository;
import users_ies_project.repository.TransactionRepository;
import users_ies_project.repository.UserRepository;
import users_ies_project.request.TransactionCreateRequest;
import users_ies_project.request.TransactionUpdateRequest;
import users_ies_project.response.ErrorResponseDTO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private FormRepository formRepository;

    @GetMapping
    public List<Transaction> index() {
        return transactionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            return ResponseEntity.ok(transaction.get());
        }
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró la Transacción"));
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody TransactionCreateRequest request, BindingResult result) {        
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errores.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }

        if (!userRepository.existsById(request.getIdUser())) {
            return ResponseEntity.badRequest().body("El usuario ingresado no existe");
        }

        if (!userRepository.existsById(request.getIdUserCreate())) {
            return ResponseEntity.badRequest().body("El usuario creador ingresado no existe");
        }

        if (!moduleRepository.existsById(request.getIdModule())) {
            return ResponseEntity.badRequest().body("El módulo ingresado no existe");
        }

        if (!formRepository.existsById(request.getIdForm())) {
            return ResponseEntity.badRequest().body("El formulario ingresado no existe");
        }

        Transaction transaction = new Transaction();
        transaction.setUser(userRepository.getReferenceById(request.getIdUser())); 
        transaction.setModule(moduleRepository.getReferenceById(request.getIdModule())); 
        transaction.setForm(formRepository.getReferenceById(request.getIdModule())); 
        transaction.setQueryType(request.getQueryType());
        transaction.setOldValue(request.getOldValue());
        transaction.setNewValue(request.getNewValue());
        transaction.setUserCreate(userRepository.getReferenceById(request.getIdUserCreate()));
        transaction.setDateCreate(new Date());

        transactionRepository.save(transaction);

        return ResponseEntity.ok("Transacción creada exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update( @PathVariable Integer id,@Valid @RequestBody TransactionUpdateRequest request,BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errores.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }
    
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (!optionalTransaction.isPresent()) {
            return ResponseEntity.badRequest().body("La transacción ingresada no existe");
        }
    
        if (!userRepository.existsById(request.getIdUser())) {
            return ResponseEntity.badRequest().body("El usuario ingresado no existe");
        }

        if (!userRepository.existsById(request.getIdUserUpdate())) {
            return ResponseEntity.badRequest().body("El usuario actualizador ingresado no existe");
        }
    
        if (!moduleRepository.existsById(request.getIdModule())) {
            return ResponseEntity.badRequest().body("El módulo ingresado no existe");
        }
    
        if (!formRepository.existsById(request.getIdForm())) {
            return ResponseEntity.badRequest().body("El formulario ingresado no existe");
        }
    
        Transaction transaction = optionalTransaction.get();
    
        transaction.setUser(userRepository.getReferenceById(request.getIdUser()));
        transaction.setModule(moduleRepository.getReferenceById(request.getIdModule()));
        transaction.setForm(formRepository.getReferenceById(request.getIdForm()));
        transaction.setQueryType(request.getQueryType());
        transaction.setOldValue(request.getOldValue());
        transaction.setNewValue(request.getNewValue());
        transaction.setUserUpdate(userRepository.getReferenceById(request.getIdUserUpdate()));
        transaction.setDateUpdate(new Date()); 
        transactionRepository.save(transaction);
    
        return ResponseEntity.ok("Transacción actualizada correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            transactionRepository.delete(transaction.get());
            return ResponseEntity.ok("Transacción eliminada correctamente");
        }
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró la Transacción"));
    }

    @GetMapping("/create")
    public InfoCreateEditTransaction<Transaction> getCreateData() {
        InfoCreateEditTransaction<Transaction> response = new InfoCreateEditTransaction<>();
        
        // Obtener la lista de usuarios
        List<UserInfoDTO> users = userRepository.findAll().stream().map(user -> {
            UserInfoDTO dto = new UserInfoDTO();
            dto.setIdUser(user.getIdUser());
            dto.setName(user.getName());
            return dto;
        }).collect(Collectors.toList());

        // Obtener la lista de módulos
        List<ModuleInfoDTO> modules = moduleRepository.findAll().stream().map(module -> {
            ModuleInfoDTO dto = new ModuleInfoDTO();
            dto.setIdModule(module.getIdModule());
            dto.setName(module.getName());
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
        response.setModules(modules);
        response.setForms(forms);
        return response;
    }

    @GetMapping("/{id}/edit")
    public ResponseEntity<?> getEditData(@PathVariable Integer id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            InfoCreateEditTransaction<Transaction> response = new InfoCreateEditTransaction<>();
            response.setEntity(optionalTransaction.get());
            
            // Obtener la lista de usuarios
            List<UserInfoDTO> users = userRepository.findAll().stream().map(user -> {
                UserInfoDTO dto = new UserInfoDTO();
                dto.setIdUser(user.getIdUser());
                dto.setName(user.getName());
                return dto;
            }).collect(Collectors.toList());

            // Obtener la lista de módulos
            List<ModuleInfoDTO> modules = moduleRepository.findAll().stream().map(module -> {
                ModuleInfoDTO dto = new ModuleInfoDTO();
                dto.setIdModule(module.getIdModule());
                dto.setName(module.getName());
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
            response.setModules(modules);
            response.setForms(forms);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(new ErrorResponseDTO("No se encontró la Transacción"));
    }
} 