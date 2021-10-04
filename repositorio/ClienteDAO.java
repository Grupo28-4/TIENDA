package mintic.edu.cliente.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Beltrán
 */
public class ClienteDAO implements IClienteDAO{
    // Definir los Atributos. Capa de Datos. Se comunica con la BDs
    Connection con = null; // Hacer la conexion a la BDs
    Conexion cn = new Conexion();
    Statement stm = null; // Separa el espacio para construir un comando SQL
    ResultSet res = null; // Guarda el resultado de la consulta
    PreparedStatement ps = null;

    @Override
    public boolean agregarCliente(Cliente cliente) {
        boolean registrar = false; // Permite identificar si ya existe el cliente
        boolean encontrado = false; // Encuentra un cliente con el correo Institucional
        String buscar = "SELECT * FROM cliente where cedula = " // Instrucción sql
                + cliente.getCedula(); // Para buscar un registro con el mismo id
        encontrado = buscar(buscar); // Ejecutamos el método con la consulta
        if(!encontrado){
            // La instrucción para insertar el registro
            String sql = "INSERT INTO cliente values (" + cliente.getCedula() + ",'" + cliente.getNombreCompleto()
                    + "','" + cliente.getDireccion() + "','" + cliente.getTelefono()+  "','"
                    + cliente.getCorreo() + "')";
            System.out.println("SQL:" + sql);
            try {
                con = cn.Conexion();
                stm = con.createStatement();
                stm.execute(sql);
                registrar = true;
                stm.close();
                con.close();
            } catch (SQLException e) {
                System.out.println("Error: Clase ClienteDAO, método agregarCliente");
                e.printStackTrace();
            }
        }

        return registrar;
    }

    @Override
    public boolean actualizarCliente(Cliente cliente) {
        boolean encontrado = false;
        boolean actualizar = false;
        String sql = "UPDATE cliente SET nombrecompleto = '" + cliente.getNombreCompleto()
                + "', correo = '"  + cliente.getCorreo() + "', direccion='" + 
                cliente.getDireccion() + "'" + ", telefono = '" + cliente.getTelefono() + "'"
                + " WHERE cedula = " + cliente.getCedula();
        System.out.println(""+sql);
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.executeUpdate(sql);
            actualizar = true;
        } catch (SQLException e) {
            System.out.println("Error: Clase ClienteDAO, método actualizar");
            e.printStackTrace();
        }
        return actualizar;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        boolean encontrado = false;
        boolean eliminar = false;
        String buscar = "SELECT * FROM cliente WHERE cedula=" + cliente.getCedula();
        encontrado = buscar(buscar);
        String sql = "DELETE FROM cliente WHERE cedula = " + cliente.getCedula();
        if(encontrado){
            try {
                con = cn.Conexion();
                ps = con.prepareStatement(sql);
                ps.executeUpdate(sql);
                eliminar = true;
            } catch (SQLException e) {
                System.out.println("Error: Clase ClienteDAO, método eliminar");
                e.printStackTrace();
            }
        }
        return eliminar;
    }

    @Override
    public Cliente buscarClienteByCedula(int cedula) {
        String sql = "SELECT * FROM cliente WHERE cedula=" + cedula;
        Cliente cli = new Cliente();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                cli.setCedula(res.getInt(1));
                cli.setNombreCompleto(res.getString(2));
                cli.setDireccion(res.getString(3));
                cli.setTelefono(res.getString(4));
                cli.setCorreo(res.getString(5));
            }
            System.out.println("Cliente "+cli.toString());
            // cerramos el jdbc
            stm.close();
            res.close();
            con.close();
        } catch (SQLException er) {
            System.err.println("Error:" + er);
        }
        return cli;
    }

    @Override
    public List<Cliente> getClientes() {
        // Ajustar para llevar la lista de clientes
        String sql = "SELECT * FROM cliente";

        List<Cliente> clientes = new ArrayList<>();

        try {
            con = cn.Conexion(); // Conectar a la BDs
            stm = con.createStatement(); 
            res = stm.executeQuery(sql);
            while (res.next()) { // Recorrer todo el ResultSet
                Cliente cli = new Cliente(); // Instanciamos un objeto tipo Usuario
                cli.setCedula(res.getInt(1));
                cli.setNombreCompleto(res.getString(2));
                cli.setDireccion(res.getString(3));
                cli.setTelefono(res.getString(4));
                cli.setCorreo(res.getString(5));
                clientes.add(cli); // Agregarlo al ArrayList
            }
            stm.close(); // Cerrar toda la conexión a la BDs
            res.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("Error:" + e);
        }
        return clientes; // Devuelve el ArrayList clientes
    }

    public boolean buscar(String sql){
        boolean encontrado = false;
        con = cn.Conexion();
        try {
            stm = con.createStatement();
            res = stm.executeQuery(sql);
            while(res.next()){
                encontrado = true;
            }
        } catch (SQLException e) {
                System.out.println("Mensaje:"+e.getMessage());
                System.out.println("Estado:"+e.getSQLState());
                System.out.println("Codigo del error:"+e.getErrorCode());
                System.out.println("Error: Clase UsuarioDao, método BuscarCliente"+e.getMessage());
            }
        return encontrado;
    }
    
}
