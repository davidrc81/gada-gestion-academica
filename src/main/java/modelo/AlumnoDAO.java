package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Programa AlumnoDAO - Refactorizado y Desacoplado de la Vista
 * Paquete modelo
 */
public class AlumnoDAO {

    /**
     * Convierte la fila actual del ResultSet en un objeto AlumnoVO
     */
    private AlumnoVO mapearAlumno(ResultSet res) throws SQLException {
        AlumnoVO alumno = new AlumnoVO();
        alumno.setAldni(res.getInt("aldni"));
        alumno.setAlapynom(res.getString("alapynom"));
        alumno.setAlfnac(res.getString("alfnac"));
        alumno.setAldir(res.getString("aldir"));
        alumno.setAlloc(res.getString("alloc"));
        alumno.setAlmail(res.getString("almail"));
        alumno.setAltel(res.getString("altel"));
        alumno.setAlcel(res.getString("alcel"));
        alumno.setAltitulo(res.getString("altitulo"));
        alumno.setAldoc(res.getByte("aldoc"));
        return alumno;
    }

    /**
     * Registra un nuevo alumno
     */
    public boolean registrarAlumno(AlumnoVO miAlumno) {
        String sql = "INSERT INTO alumno (aldni, alapynom, alfnac, aldir, alloc, almail, altel, alcel, altitulo, aldoc) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Conexion conex = new Conexion();
        
        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, miAlumno.getAldni());
            ps.setString(2, miAlumno.getAlapynom());
            ps.setString(3, miAlumno.getAlfnac());
            ps.setString(4, miAlumno.getAldir());
            ps.setString(5, miAlumno.getAlloc());
            ps.setString(6, miAlumno.getAlmail());
            ps.setString(7, miAlumno.getAltel());
            ps.setString(8, miAlumno.getAlcel());
            ps.setString(9, miAlumno.getAltitulo());
            ps.setByte(10, miAlumno.getAldoc());
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar alumno: " + e.getMessage());
            return false;
        } finally {
            conex.desconectar();
        }
    }

    /**
     * Busca un alumno por DNI exacto
     */
    public AlumnoVO buscarAlumno(int dni) {
        String sql = "SELECT * FROM alumno WHERE aldni = ?";
        Conexion conex = new Conexion();
        
        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, dni);
            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    return mapearAlumno(res);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar alumno por DNI: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return null;
    }

    /**
     * Retorna el listado completo de alumnos ordenados por Apellido
     */
    public List<AlumnoVO> obtenerTodosLosAlumnos() {
        List<AlumnoVO> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumno ORDER BY alapynom";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet res = ps.executeQuery()) {

            while (res.next()) {
                lista.add(mapearAlumno(res));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener lista de alumnos: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return lista;
    }

    /**
     * Busca alumnos cuyo apellido coincida parcialmente
     */
    public List<AlumnoVO> buscarAlumnosPorApellido(String apellido) {
        List<AlumnoVO> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumno WHERE alapynom LIKE ? ORDER BY alapynom";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, apellido + "%");
            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    lista.add(mapearAlumno(res));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar alumnos por apellido: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return lista;
    }

    /**
     * Busca alumnos cuyo DNI coincida parcialmente
     */
    public List<AlumnoVO> buscarAlumnosPorDniParcial(int dniParcial) {
        List<AlumnoVO> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumno WHERE CAST(aldni AS CHAR) LIKE ? ORDER BY alapynom";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, dniParcial + "%");
            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    lista.add(mapearAlumno(res));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar alumnos por DNI parcial: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return lista;
    }

    /**
     * Modifica los datos de un alumno
     */
    public boolean modificarAlumno(AlumnoVO miAlumno) {
        String sql = "UPDATE alumno SET aldni = ?, alapynom = ?, alfnac = ?, aldir = ?, alloc = ?, "
                   + "almail = ?, altel = ?, alcel = ?, altitulo = ?, aldoc = ? WHERE aldni = ?";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, miAlumno.getAldni());
            ps.setString(2, miAlumno.getAlapynom());
            ps.setString(3, miAlumno.getAlfnac());
            ps.setString(4, miAlumno.getAldir());
            ps.setString(5, miAlumno.getAlloc());
            ps.setString(6, miAlumno.getAlmail());
            ps.setString(7, miAlumno.getAltel());
            ps.setString(8, miAlumno.getAlcel());
            ps.setString(9, miAlumno.getAltitulo());
            ps.setByte(10, miAlumno.getAldoc());
            ps.setInt(11, miAlumno.getAldni());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al modificar alumno: " + e.getMessage());
            return false;
        } finally {
            conex.desconectar();
        }
    }

    /**
     * Elimina un alumno por su DNI
     */
    public boolean eliminarAlumno(int dni) {
        String sql = "DELETE FROM alumno WHERE aldni = ?";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, dni);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar alumno: " + e.getMessage());
            return false;
        } finally {
            conex.desconectar();
        }
    }

    /**
     * Devuelve el Apellido y Nombre de un alumno según su DNI
     */
    public String darNombre(int dni) {
        String sql = "SELECT alapynom FROM alumno WHERE aldni = ?";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, dni);
            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    return res.getString("alapynom");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener nombre: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return null;
    }
}