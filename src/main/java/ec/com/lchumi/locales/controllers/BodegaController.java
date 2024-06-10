package ec.com.lchumi.locales.controllers;

import ec.com.lchumi.locales.services.IBodegaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente/")
@CrossOrigin("*")
@Slf4j
public class BodegaController {
    @Autowired
    private IBodegaService bodegaService;


}
