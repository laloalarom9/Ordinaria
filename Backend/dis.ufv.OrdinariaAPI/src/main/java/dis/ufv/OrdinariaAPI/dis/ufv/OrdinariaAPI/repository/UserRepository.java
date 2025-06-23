package dis.ufv.OrdinariaAPI.dis.ufv.OrdinariaAPI.repository;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dis.ufv.OrdinariaAPI.dis.ufv.OrdinariaAPI.model.User;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class UserRepository {

    private final List<User> usuarios;

    public UserRepository() {
        this.usuarios = cargarUsuarios();
    }

    private List<User> cargarUsuarios() {
        try (InputStreamReader reader = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("usuarios.json"))) {

            Type listType = new TypeToken<List<User>>() {}.getType();
            return new Gson().fromJson(reader, listType);

        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // lista vacía si falla
        }
    }

    public List<User> getAllUsuarios() {
        return usuarios;
    }
    public void addUsuario(User user) {
        usuarios.add(user);
        // Aquí puedes guardar los cambios de nuevo al archivo JSON si lo deseas
    }
    public void updateUsuario(User userActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            User u = usuarios.get(i);
            if (u.getId() == userActualizado.getId()) {
                usuarios.set(i, userActualizado);
                return;
            }
        }
        // Si no se encuentra el usuario, podrías lanzar una excepción o ignorar
    }
    public byte[] generatePdfFromUsuarios() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Lista de Usuarios"));
            for (User user : usuarios) {
                document.add(new Paragraph(
                        "ID: " + user.getId() +
                                ", Nombre: " + user.getNombre() +
                                ", Email: " + user.getEmail()
                ));
            }

            document.close();
            return baos.toByteArray(); // contenido PDF
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
