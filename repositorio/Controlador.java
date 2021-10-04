package mintic.edu.cliente.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mintic.edu.cliente.modelo.Cliente;
import mintic.edu.cliente.modelo.ClienteDAO;

public class Controlador extends HttpServlet {
    
    Cliente cliente = new Cliente();
    ClienteDAO clienteDAO = new ClienteDAO();
    String mensaje=null;
    String aviso = null;
    int operacion = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // variable para gestionar la opción del menú --> Clase(menu)
        // variable para laoperación (accion) a procesar
        String menu = request.getParameter("menu");
        String accion = request.getParameter("accion");
        String titulo = "Gestión de Clientes";
        if(menu.equals("Clientes")){
            switch(accion){
                case "Listar":
                    // Creamos la variable que permita recuperar la lista de clientes
                    List<Cliente> clientes = new ArrayList<Cliente>();
                    Cliente clienteEdit = new Cliente();
                    operacion = 1;
                    // Guardamos en la variable el resultado del método creado en el DAO
                    clientes = clienteDAO.getClientes();
                    for(Cliente cli:clientes){
                        System.out.println("Cliente: " + cli.toString());
                    }
                    request.setAttribute("titulo", titulo);
                    request.setAttribute("opcion", "Listado de Clientes");
                    request.setAttribute("operacion", operacion);
                    // Definimos la variable a usar en la vista y su contenido
                    request.setAttribute("clienteEdit", clienteEdit);
                    request.setAttribute("clientes",clientes);
                    break;
                case "Agregar":
                    int cedula = Integer.parseInt(request.getParameter("txtCedula"));
                    String nombre = request.getParameter("txtNombre");
                    String direccion = request.getParameter("txtDireccion");
                    String correo = request.getParameter("txtCorreo");
                    String telefono = request.getParameter("txtTelefono");
                    if(nombre.isEmpty() || direccion.isEmpty() || correo.isEmpty() || telefono.isEmpty()){
                        aviso = "Faltan Datos de Cliente";
                        mensaje = null;
                    }else{
                        cliente.setCedula(cedula);
                        cliente.setNombreCompleto(nombre);
                        cliente.setDireccion(direccion);
                        cliente.setTelefono(telefono);
                        cliente.setCorreo(correo);
                        boolean creado = clienteDAO.agregarCliente(cliente);
                        if(creado){
                            mensaje = "Cliente Creado Exitosamente";
                            aviso = null;
                        }else {
                            aviso = "Faltan Datos del Cliente";
                            mensaje = null;
                        }
                    }
                    request.setAttribute("mensaje", mensaje);
                    request.setAttribute("aviso", aviso);
                    request.getRequestDispatcher("Controlador?menu=Clientes&accion=Listar").forward(request, response);
                    break;
                case "Consultar":
                    int cedulac = Integer.parseInt(request.getParameter("txtCedula"));
                    cliente = clienteDAO.buscarClienteByCedula(cedulac);
                    if(cliente.getNombreCompleto() == null){
                        aviso = "Cliente No Existe";
                        mensaje = null;
                        request.setAttribute("mensaje", mensaje);
                        request.setAttribute("aviso", aviso);
                        request.getRequestDispatcher("Controlador?menu=Clientes&accion=Listar").forward(request, response);
                    }else{
                        aviso = null;
                        mensaje = "Cliente Encontrado";
                    }
                    operacion = 0;
                    request.setAttribute("titulo", titulo);
                    request.setAttribute("opcion", "Edición/Borrado de Clientes");
                    request.setAttribute("mensaje", mensaje);
                    request.setAttribute("aviso", aviso);
                    request.setAttribute("clienteEdit",cliente);
                    break;
                case "Actualizar":
                    int cedulaa = Integer.parseInt(request.getParameter("txtCedula"));
                    String nombrea = request.getParameter("txtNombre");
                    String direcciona = request.getParameter("txtDireccion");
                    String correoa = request.getParameter("txtCorreo");
                    String telefonoa = request.getParameter("txtTelefono");
                    if(nombrea.isEmpty() || direcciona.isEmpty() || correoa.isEmpty() || telefonoa.isEmpty()){
                        aviso = "Faltan Datos de Cliente";
                        mensaje = null;
                    }else{
                        cliente.setCedula(cedulaa);
                        cliente.setNombreCompleto(nombrea);
                        cliente.setDireccion(direcciona);
                        cliente.setTelefono(telefonoa);
                        cliente.setCorreo(correoa);
                        boolean creado = clienteDAO.actualizarCliente(cliente);
                        if(creado){
                            mensaje = "Cliente Actualizado Correctamente";
                            aviso = null;
                        }else {
                            aviso = "Faltan Datos del Cliente";
                            mensaje = null;
                        }
                    }
                    request.setAttribute("mensaje", mensaje);
                    request.setAttribute("aviso", aviso);
                    request.getRequestDispatcher("Controlador?menu=Clientes&accion=Listar").forward(request, response);
                    break;
                case "Eliminar":
                    int cedulae = Integer.parseInt(request.getParameter("txtCedula"));
                    cliente = clienteDAO.buscarClienteByCedula(cedulae);
                    if(cliente.getNombreCompleto() == null){
                        aviso = "Cliente No Existe";
                        mensaje = null;
                        request.setAttribute("mensaje", mensaje);
                        request.setAttribute("aviso", aviso);
                        request.getRequestDispatcher("Controlador?menu=Clientes&accion=Listar").forward(request, response);
                    }else{
                        boolean eliminado = clienteDAO.eliminarCliente(cliente);
                        if(eliminado){
                            aviso = null;
                            mensaje = "Cliente Eliminado Exitosamente";
                        }else{
                            aviso = "Faltan Datos de Cliente";
                            mensaje = null;
                        }
                    }
                    operacion = 1;
                    request.setAttribute("titulo", titulo);
                    request.setAttribute("opcion", "Listado de Clientes");
                    request.setAttribute("mensaje", mensaje);
                    request.setAttribute("aviso", aviso);
                    request.setAttribute("clienteEdit",cliente);
                    request.getRequestDispatcher("Controlador?menu=Clientes&accion=Listar").forward(request, response);
                    break;
                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("jsp/clientes.jsp").forward(request, response);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
