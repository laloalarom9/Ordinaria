package dis.ufv.OrdinariaAPI.dis.ufv.OrdinariaAPI.controller;

import dis.ufv.OrdinariaAPI.dis.ufv.OrdinariaAPI.model.User;
import dis.ufv.OrdinariaAPI.dis.ufv.OrdinariaAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        byte[] pdf = userService.generatePdf();

        if (pdf == null || pdf.length == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "usuarios_" + System.currentTimeMillis() + ".pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }




}
