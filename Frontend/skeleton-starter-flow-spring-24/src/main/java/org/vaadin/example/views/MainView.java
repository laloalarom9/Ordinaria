package org.vaadin.example.views;
import java.time.LocalDate;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.vaadin.example.client.ApiClient;
import org.vaadin.example.model.DireccionFront;
import org.vaadin.example.model.MetodoPagoFront;
import org.vaadin.example.model.UserFront;
import com.vaadin.flow.component.dialog.Dialog;

import java.util.List;
import java.util.UUID;
@Route("")
public class MainView extends VerticalLayout {

    private Grid<UserFront> grid = new Grid<>(UserFront.class, false);

    public MainView() {
        setPadding(true);
        setSpacing(true);

        // Cargar y mostrar usuarios
        mostrarUsuarios();

        // Campos del formulario
        TextField nombre = new TextField("Nombre");
        TextField apellidos = new TextField("Apellidos");
        TextField email = new TextField("Email");
        TextField ciudad = new TextField("Ciudad");
        TextField titular = new TextField("Titular tarjeta");

        // Botón para guardar usuario
        Button guardar = new Button("Guardar usuario", e -> {
            UserFront nuevo = new UserFront();
            nuevo.setId(UUID.randomUUID());
            nuevo.setNombre(nombre.getValue());
            nuevo.setApellidos(apellidos.getValue());
            nuevo.setEmail(email.getValue());

            DireccionFront dir = new DireccionFront();
            dir.setCiudad(ciudad.getValue());
            nuevo.setDireccion(dir);

            MetodoPagoFront pago = new MetodoPagoFront();
            pago.setNombreAsociado(titular.getValue());
            nuevo.setMetodoPago(pago);

            ApiClient.addUsuario(nuevo);
            Notification.show("Usuario guardado");
            mostrarUsuarios(); // refrescar grid
        });

        // Botón para descargar PDF


        Button descargarPdf = new Button("Descargar PDF", event -> {
            byte[] pdf = ApiClient.generatePdf();
            if (pdf != null) {
                String fecha = LocalDate.now().toString(); // Formato: 2025-06-23
                String base64 = java.util.Base64.getEncoder().encodeToString(pdf);
                String filename = "usuarios_" + fecha + ".pdf";

                getUI().ifPresent(ui ->
                        ui.getPage().open("data:application/pdf;base64," + base64 +
                                "#toolbar=0", "_blank")
                );
            } else {
                Notification.show("No se pudo generar el PDF");
            }
        });
// Layouts organizados
HorizontalLayout campos = new HorizontalLayout(nombre, apellidos, email, titular);



        // Layouts organizados
        HorizontalLayout botones = new HorizontalLayout(guardar, descargarPdf);
add(campos, ciudad, botones, grid);

    }

    private void mostrarUsuarios() {
        List<UserFront> usuarios = ApiClient.obtenerUsuarios();
        grid.setItems(usuarios);
        grid.removeAllColumns();
        grid.addColumn(UserFront::getNombre).setHeader("Nombre");
        grid.addColumn(UserFront::getApellidos).setHeader("Apellidos");
        grid.addColumn(UserFront::getEmail).setHeader("Email");
        grid.addColumn(user -> user.getDireccion() != null ? user.getDireccion().getCiudad() : "").setHeader("Ciudad");
        grid.addColumn(user -> user.getMetodoPago() != null ? user.getMetodoPago().getNombreAsociado() : "").setHeader("Titular Tarjeta");
        grid.addComponentColumn(user -> {
            Button editarBtn = new Button("Editar");
            Registration registration = editarBtn.addClickListener(e -> mostrarDialogoEdicion(user));
            return editarBtn;
        }).setHeader("Editar");
        grid.addItemDoubleClickListener(event -> {
            UserFront usuario = event.getItem();
            mostrarDialogoDetalles(usuario);
        });


    }
    private void mostrarDialogoEdicion(UserFront user) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Editar Usuario");

        TextField nombre = new TextField("Nombre", user.getNombre());
        TextField apellidos = new TextField("Apellidos", user.getApellidos());
        TextField email = new TextField("Email", user.getEmail());
        TextField ciudad = new TextField("Ciudad", user.getDireccion() != null ? user.getDireccion().getCiudad() : "");
        TextField titular = new TextField("Titular tarjeta", user.getMetodoPago() != null ? user.getMetodoPago().getNombreAsociado() : "");

        Button guardar = new Button("Guardar cambios", ev -> {
            user.setNombre(nombre.getValue());
            user.setApellidos(apellidos.getValue());
            user.setEmail(email.getValue());

            DireccionFront dir = new DireccionFront();
            dir.setCiudad(ciudad.getValue());
            user.setDireccion(dir);

            MetodoPagoFront metodo = new MetodoPagoFront();
            metodo.setNombreAsociado(titular.getValue());
            user.setMetodoPago(metodo);

            ApiClient.actualizarUsuario(user.getId(), user); // PUT /api/usuarios/{id}
            dialog.close();
            mostrarUsuarios(); // refresca la tabla
            Notification.show("Usuario actualizado");
        });

        Button cancelar = new Button("Cancelar", e -> dialog.close());

        dialog.add(new VerticalLayout(nombre, apellidos, email, ciudad, titular));
        dialog.getFooter().add(new HorizontalLayout(cancelar, guardar));
        dialog.open();
    }

    private void mostrarDialogoDetalles(UserFront user) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Detalles del Usuario");

        VerticalLayout layout = new VerticalLayout();
        layout.add(new TextField("Nombre", user.getNombre()));
        layout.add(new TextField("Apellidos", user.getApellidos()));
        layout.add(new TextField("Email", user.getEmail()));
        layout.add(new TextField("Ciudad", user.getDireccion() != null ? user.getDireccion().getCiudad() : ""));
        layout.add(new TextField("Titular tarjeta", user.getMetodoPago() != null ? user.getMetodoPago().getNombreAsociado() : ""));

        Button cerrar = new Button("Cerrar", e -> dialog.close());
        dialog.getFooter().add(cerrar);
        dialog.add(layout);
        dialog.open();
    }

}
