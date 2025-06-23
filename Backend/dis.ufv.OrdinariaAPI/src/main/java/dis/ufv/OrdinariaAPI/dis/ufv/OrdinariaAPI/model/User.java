package dis.ufv.OrdinariaAPI.dis.ufv.OrdinariaAPI.model;
import java.util.UUID;

public class User {
    private UUID id;
    private String nombre;
    private String apellidos;
    private String nif;
    private Direccion direccion;
    private String email;
    private MetodoPago metodoPago;

    // Getters y setters

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }

    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
}