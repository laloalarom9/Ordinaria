package dis.ufv.OrdinariaAPI.dis.ufv.OrdinariaAPI.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
            return new ArrayList<>();
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
        guardarUsuariosEnJson();
    }

    public void updateUsuario(UUID id, User userActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(id)) {
                usuarios.set(i, userActualizado);
                guardarUsuariosEnJson();
                return;
            }
        }
    }

    public byte[] generatePdfFromUsuarios() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            Document doc = new Document();
            PdfWriter writer = PdfWriter.getInstance(doc, baos);
            doc.open();

            doc.add(new Paragraph("Listado de Usuarios:\n\n"));

            for (User user : cargarUsuarios()) {
                StringBuilder linea = new StringBuilder();
                linea.append("ID: ").append(user.getId()).append("\n")
                        .append("Nombre: ").append(user.getNombre()).append("\n")
                        .append("Apellidos: ").append(user.getApellidos()).append("\n")
                        .append("Email: ").append(user.getEmail()).append("\n");

                if (user.getDireccion() != null)
                    linea.append("Ciudad: ").append(user.getDireccion().getCiudad()).append("\n");

                if (user.getMetodoPago() != null)
                    linea.append("Titular tarjeta: ").append(user.getMetodoPago().getNombreAsociado()).append("\n");

                linea.append("-----------------------------\n");

                doc.add(new Paragraph(linea.toString()));
            }

            doc.close();
            writer.close(); // aunque no es obligatorio, por limpieza
            baos.flush();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
