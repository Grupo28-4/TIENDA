package mintic.edu.cliente.modelo;

/**
 *
 * @author Carlos Beltrán
 */
public class Cliente {
    
    private int cedula;
    private String nombreCompleto;
    private String direccion;
    private String telefono;
    private String correo;
    
    // Agregar Constructores

    public Cliente() {
    }

    public Cliente(int cedula, String nombreCompleto, String direccion, String telefono, String correo) {
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
    }
    
    // Agregar setter y getter

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    // toString

    @Override
    public String toString() {
        return "Cliente{" + "cedula=" + cedula + ", nombreCompleto=" + nombreCompleto + ", direccion=" + direccion + ", telefono=" + telefono + ", correo=" + correo + '}';
    }
    
    
    
}
