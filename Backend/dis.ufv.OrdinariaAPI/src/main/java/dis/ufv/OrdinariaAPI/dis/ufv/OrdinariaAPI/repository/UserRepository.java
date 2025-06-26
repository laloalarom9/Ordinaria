package dis.ufv.OrdinariaAPI.dis.ufv.OrdinariaAPI.repository;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dis.ufv.OrdinariaAPI.dis.ufv.OrdinariaAPI.model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository {

    private static final String RUTA_JSON = "data/usuarios.json";

    private final List<User> usuarios;

    public UserRepository() {
        this.usuarios = cargarUsuarios();
    }

    private List<User> cargarUsuarios() {
        try (FileReader reader = new FileReader(RUTA_JSON)) {
            Type listType = new TypeToken<List<User>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Lista vacía si falla
        }
    }

    private void guardarUsuariosEnJson() {
        try (FileWriter writer = new FileWriter(RUTA_JSON)) {
            new Gson().toJson(usuarios, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsuarios() {
        return usuarios;
    }

    public void addUsuario(User user) {
        usuarios.add(user);
        guardarUsuariosEnJson(); // ✅ Guarda cambios
    }

    public void updateUsuario(UUID id, User userActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(id)) {
                usuarios.set(i, userActualizado);
                guardarUsuariosEnJson(); // ✅ Guarda cambios
                return;
            }
        }
    }

    public byte[] generatePdfFromUsuarios() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Lista de Usuarios:"));
            for (User user : usuarios) {
                document.add(new Paragraph(
                        "ID: " + user.getId() +
                                ", Nombre: " + user.getNombre() +
                                ", Email: " + user.getEmail()
                ));
            }

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
