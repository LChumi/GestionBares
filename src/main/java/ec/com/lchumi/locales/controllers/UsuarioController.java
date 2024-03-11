package ec.com.lchumi.locales.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
@CrossOrigin("*")
public class UsuarioController {

    @GetMapping("/list")
    public String mostrar(){
        return "hola ";
    }
}
