/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package ec.com.lchumi.locales.services;

import java.io.File;

public interface IEmailService {

    void sendMail(String[] toUser,String asunto, String message);

    void sendMail(String[] toUser,String asunto,String message, File file);
}
