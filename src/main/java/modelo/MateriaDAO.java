package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

/**
 * Programa MateriaDAO - Refactorizado y conectado a Coordinador y Swing
 * Paquete modelo
 */
public class MateriaDAO {

    /**
     * Mapea la fila actual del ResultSet a un objeto MateriaVO
     */
    private MateriaVO mapearMateria(ResultSet res) throws SQLException {
        MateriaVO materia = new MateriaVO();
        materia.setCodmat(res.getString("codmat"));
        materia.setMatnom(res.getString("matnom"));
        materia.setAnio(res.getInt("anio"));
        materia.setPlan(res.getString("plan"));
        materia.setModulos(res.getInt("modulos"));
        return materia;
    }

    /**
     * Registra una nueva materia en la BD
     */
    public boolean registrarMateria(MateriaVO miMateria) {
        String sql = "INSERT INTO materia (codmat, matnom, anio, plan, modulos) VALUES (?, ?, ?, ?, ?)";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, miMateria.getCodmat());
            ps.setString(2, miMateria.getMatnom());
            ps.setInt(3, miMateria.getAnio());
            ps.setString(4, miMateria.getPlan());
            ps.setInt(5, miMateria.getModulos());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar materia: " + e.getMessage());
            return false;
        } finally {
            conex.desconectar();
        }
    }

    /**
     * Busca una materia por su código
     */
    public MateriaVO buscarMateria(String codigo) {
        String sql = "SELECT * FROM materia WHERE codmat = ?";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    return mapearMateria(res);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar materia por código: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return null;
    }

    /**
     * Busca una materia por su nombre exacto
     */
    public MateriaVO buscarMateriaNombre(String nombre) {
        String sql = "SELECT * FROM materia WHERE matnom = ?";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    return mapearMateria(res);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar materia por nombre: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return null;
    }

    /**
     * Modifica los datos de una materia existente
     */
    public boolean modificarMateria(MateriaVO miMateria) {
        String sql = "UPDATE materia SET matnom = ?, anio = ?, plan = ?, modulos = ? WHERE codmat = ?";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, miMateria.getMatnom());
            ps.setInt(2, miMateria.getAnio());
            ps.setString(3, miMateria.getPlan());
            ps.setInt(4, miMateria.getModulos());
            ps.setString(5, miMateria.getCodmat());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al modificar materia: " + e.getMessage());
            return false;
        } finally {
            conex.desconectar();
        }
    }

    /**
     * Elimina una materia por su código
     */
    public boolean eliminarMateria(String codigo) {
        String sql = "DELETE FROM materia WHERE codmat = ?";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar materia: " + e.getMessage());
            return false;
        } finally {
            conex.desconectar();
        }
    }

    /**
     * Devuelve todas las materias ordenadas por año
     */
    public List<MateriaVO> obtenerTodasLasMaterias() {
        List<MateriaVO> lista = new ArrayList<>();
        String sql = "SELECT * FROM materia ORDER BY anio, matnom";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet res = ps.executeQuery()) {

            while (res.next()) {
                lista.add(mapearMateria(res));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las materias: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return lista;
    }

    /**
     * Devuelve materias filtradas por plan de estudio
     */
    public List<MateriaVO> obtenerMateriasPorPlan(String plan) {
        List<MateriaVO> lista = new ArrayList<>();
        String sql = "SELECT * FROM materia WHERE plan = ? ORDER BY anio, matnom";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, plan);
            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    lista.add(mapearMateria(res));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener materias por plan: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return lista;
    }

    /**
     * Devuelve sólo los códigos de materias para cargar combos
     */
    public List<String> obtenerCodigosMaterias() {
        List<String> codigos = new ArrayList<>();
        String sql = "SELECT codmat FROM materia ORDER BY anio";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet res = ps.executeQuery()) {

            while (res.next()) {
                codigos.add(res.getString("codmat"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener códigos de materias: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return codigos;
    }

    /**
     * Devuelve los códigos de materias que un alumno aún NO tiene registradas
     */
    public List<String> obtenerMateriasNuevasAlumno(int dniAlum) {
        List<String> codigos = new ArrayList<>();
        String sql = "SELECT codmat FROM materia WHERE codmat NOT IN "
                   + "(SELECT codmat2 FROM alumnomateria WHERE aldni2 = ?) ORDER BY anio";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, dniAlum);
            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    codigos.add(res.getString("codmat"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar materias nuevas para el alumno: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return codigos;
    }

    /**
     * Devuelve lista de materias disponibles para inscribirse según plan y DNI de alumno
     */
    public List<MateriaVO> obtenerMateriasDisponiblesAlumno(int doc, String plan) {
        List<MateriaVO> lista = new ArrayList<>();
        String sql = "SELECT * FROM materia WHERE plan = ? AND codmat NOT IN "
                   + "(SELECT codmat2 FROM alumnomateria WHERE aldni2 = ?) ORDER BY anio";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, plan);
            ps.setInt(2, doc);
            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    lista.add(mapearMateria(res));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar materias disponibles: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return lista;
    }

    /**
     * Busca materias por código parcial y plan
     */
    public List<MateriaVO> buscarParcialMateriaCodigo(String codMat, String plan) {
        List<MateriaVO> lista = new ArrayList<>();
        String sql = "SELECT * FROM materia WHERE codmat LIKE ? AND plan = ? ORDER BY codmat";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, codMat + "%");
            ps.setString(2, plan);
            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    lista.add(mapearMateria(res));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar materias por código parcial: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return lista;
    }

    /**
     * Busca materias por nombre parcial y plan
     */
    public List<MateriaVO> buscarParcialMateriaNombre(String nomMat, String plan) {
        List<MateriaVO> lista = new ArrayList<>();
        String sql = "SELECT * FROM materia WHERE matnom LIKE ? AND plan = ? ORDER BY matnom";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, nomMat + "%");
            ps.setString(2, plan);
            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    lista.add(mapearMateria(res));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar materias por nombre parcial: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return lista;
    }

    /**
     * Obtiene el nombre de la materia dado su código
     */
    public String darNombreMateria(String codigo) {
        String sql = "SELECT matnom FROM materia WHERE codmat = ?";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    return res.getString("matnom");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar nombre de materia: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return null;
    }

    /**
     * Obtiene el año de cursada de la materia
     */
    public int darAnioMateria(String codigo) {
        String sql = "SELECT anio FROM materia WHERE codmat = ?";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    return res.getInt("anio");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar año de materia: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return 0;
    }

    /**
     * Obtiene los nombres de las correlativas asignadas a una materia
     */
    public List<String> obtenerCorrelativas(String codMat) {
        List<String> correlativas = new ArrayList<>();
        String sql = "SELECT m.matnom FROM materiacorrelativa mc "
                   + "JOIN materia m ON mc.codmat1 = m.codmat "
                   + "WHERE mc.codmatcorr = ?";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, codMat);
            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    correlativas.add(res.getString("matnom"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar correlativas: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
        return correlativas;
    }

    // =========================================================================
    // MÉTODOS DE SOPORTE PARA MODELOS DE VISTA (COMBOS Y LISTAS DE SWING)
    // =========================================================================

    public void cargarComboMaterias(DefaultComboBoxModel<String> modeloComboMateria) {
        String sql = "SELECT codmat FROM materia ORDER BY anio, codmat";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet res = ps.executeQuery()) {

            if (modeloComboMateria != null) {
                modeloComboMateria.removeAllElements();
                while (res.next()) {
                    modeloComboMateria.addElement(res.getString("codmat"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar combo materias: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
    }

    public void cargarComboPlanes(DefaultComboBoxModel<String> modeloComboPlan) {
        String sql = "SELECT DISTINCT plan FROM materia ORDER BY plan DESC";
        Conexion conex = new Conexion();

        try (Connection cn = conex.getConnection();
             Statement st = cn.createStatement();
             ResultSet res = st.executeQuery(sql)) {

            if (modeloComboPlan != null) {
                modeloComboPlan.removeAllElements();
                while (res.next()) {
                    modeloComboPlan.addElement(res.getString("plan"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar combo de planes: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
    }

    public void cargarComboMateriasNuevasAlumno(DefaultComboBoxModel<String> modeloComboMateria, int dniAlum, String nomAlum) {
        List<String> materiasNuevas = obtenerMateriasNuevasAlumno(dniAlum);
        if (modeloComboMateria != null) {
            modeloComboMateria.removeAllElements();
            for (String cod : materiasNuevas) {
                modeloComboMateria.addElement(cod);
            }
        }
    }

    public void cargarListadoVentanaMateria(DefaultListModel<String> modeCorre, String codMat) {
        List<String> correlativas = obtenerCorrelativas(codMat);
        if (modeCorre != null) {
            modeCorre.clear();
            for (String mat : correlativas) {
                modeCorre.addElement(mat);
            }
        }
    }

    public void cargarTodasLasMaterias(DefaultListModel<String> modeloTotal, String plan) {
        List<MateriaVO> materias = obtenerMateriasPorPlan(plan);
        if (modeloTotal != null) {
            modeloTotal.clear();
            for (MateriaVO m : materias) {
                modeloTotal.addElement(m.getAnio() + " - " + m.getCodmat() + " - " + m.getMatnom());
            }
        }
    }

    public void cargarListadoCorrelativas(DefaultListModel<String> modeloTotal, DefaultListModel<String> modeloCorre, String codMat, int anio, String plan, int ven) {
        String sql;
        if (ven == 1) {
            sql = "SELECT codmat, matnom, anio FROM materia WHERE plan = ? AND anio < ? AND codmat NOT IN "
                + "(SELECT codmat1 FROM materiacorrelativa WHERE codmatcorr = ?) ORDER BY anio, codmat";
        } else {
            sql = "SELECT codmat, matnom, anio FROM materia WHERE plan = ? AND codmat IN "
                + "(SELECT codmat1 FROM materiacorrelativa WHERE codmatcorr = ?) ORDER BY anio, codmat";
        }

        Conexion conex = new Conexion();
        try (Connection cn = conex.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, plan);
            if (ven == 1) {
                ps.setInt(2, anio);
                ps.setString(3, codMat);
            } else {
                ps.setString(2, codMat);
            }

            try (ResultSet res = ps.executeQuery()) {
                if (modeloTotal != null) {
                    modeloTotal.clear();
                    while (res.next()) {
                        modeloTotal.addElement(res.getInt("anio") + " " + res.getString("codmat") + " - " + res.getString("matnom"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar listado de correlativas: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
    }

    public void cargarListadoMateriasNuevasAlumno(DefaultListModel<String> modeloTotal, int doc, String plan) {
        List<MateriaVO> materias = obtenerMateriasDisponiblesAlumno(doc, plan);
        if (modeloTotal != null) {
            modeloTotal.clear();
            for (MateriaVO m : materias) {
                modeloTotal.addElement(m.getAnio() + " " + m.getCodmat() + " - " + m.getMatnom());
            }
        }
    }
    
}