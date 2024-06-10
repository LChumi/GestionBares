/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package ec.com.lchumi.locales.controllers;

import ec.com.lchumi.locales.models.domain.EmailDto;
import ec.com.lchumi.locales.services.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/email/")
public class MailController {

    @Autowired
    private IEmailService emailService;

    @PostMapping("enviar")
    public ResponseEntity<?> reciveRequestEmail(@RequestBody EmailDto emailDto){
        System.out.println("Mensaje enviado "+emailDto.toString());

        emailService.sendMail(emailDto.toUser(),emailDto.subject(), emailDto.message());

        Map<String ,String> response = new HashMap<>();
        response.put("estado", "Enviado");

        return ResponseEntity.ok(response);
    }
}
