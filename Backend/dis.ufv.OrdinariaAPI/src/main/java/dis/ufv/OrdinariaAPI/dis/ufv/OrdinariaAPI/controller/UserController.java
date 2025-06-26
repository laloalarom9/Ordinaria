package dis.ufv.OrdinariaAPI.dis.ufv.OrdinariaAPI.controller;

import dis.ufv.OrdinariaAPI.dis.ufv.OrdinariaAPI.model.User;
import dis.ufv.OrdinariaAPI.dis.ufv.OrdinariaAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    // GET /api/usuarios
    @GetMapping
    public List<User> obtenerUsuarios() {
        return userService.getAllUsers();
    }

    // POST /api/usuarios
    @PostMapping
    public void agregarUsuario(@RequestBody User user) {
        userService.addUser(user);
    }

    // PUT /api/usuarios
    @PutMapping("/{id}")
    public void actualizarUsuario(@PathVariable UUID id, @RequestBody User user) {
        userService.updateUser(id, user);
    }


    // GET /api/usuarios/pdf
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generarPdfUsuarios() {
        byte[] pdfBytes = userService.generatePdf();
        if (pdfBytes == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=usuarios.pdf");
        headers.add("Content-Type", "application/pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
