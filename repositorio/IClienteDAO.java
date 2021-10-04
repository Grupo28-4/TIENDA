package mintic.edu.cliente.modelo;

import java.util.List;

/**
 *
 * @author Carlos Beltrán
 */
public interface IClienteDAO {
    
    // Definir las signature de los métodos a utilizar
    // Agregar Clientes
    // Actualizar un Cliente
    // Eliminar un Cliente
    // Buscar un Cliente
    // Recupere todos registros de Clientes
    
    public boolean agregarCliente(Cliente cliente); // save
    public boolean actualizarCliente(Cliente cliente);
    public boolean eliminarCliente(Cliente cliente);
    public Cliente buscarClienteByCedula(int cedula);
    public List<Cliente> getClientes(); // findAll
    
}
