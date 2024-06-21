package ec.com.lchumi.locales.controllers;

import ec.com.lchumi.locales.models.auth.UserRequest;
import ec.com.lchumi.locales.services.IUsuarioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
@CrossOrigin("*")
@Slf4j
public class AutController {

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("login")
    public ResponseEntity<UserRequest> login(@Valid @RequestBody UserRequest userRequest){
        try {
            UserRequest usuario= usuarioService.login(userRequest);
            if (usuario== null ){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.ok(usuario);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
