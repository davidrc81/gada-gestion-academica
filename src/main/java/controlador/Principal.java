package controlador;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import java.awt.Color;

import modelo.Conexion;
import modelo.Logica;
import modelo.MesaVO;
import modelo.Reportes;
import vista.VentanaActualizaNotaMesa;
import vista.VentanaAlumno;
import vista.VentanaAlumnoBuscar;
import vista.VentanaAlumnoMateria;
import vista.VentanaAlumnoMateriaNueva;
import vista.VentanaAlumnoMesa;
import vista.VentanaAlumnoParciales;
import vista.VentanaMateria;
import vista.VentanaMateriaBuscar;
import vista.VentanaMateriaNueva;
import vista.VentanaMesa;
import vista.VentanaMesaBuscar;
import vista.VentanaPrincipal;
import vista.VentanaReporteListaAlumnos;
import vista.VentanaReportePlanillasParciales;
import vista.VentanaReportes;
/**
 * Sistema de Gestión académica de alumnos
 * modificado para n correlativas y 3 turnos
 * 
 * @author ASI
 *
 */
public class Principal {

	VentanaPrincipal miVentanaPrincipal;
	VentanaAlumno miVentanaAlumno;
	VentanaAlumnoBuscar miVentanaAlumnoBuscar;
	VentanaMateria miVentanaMateria;
	VentanaMateriaBuscar miVentanaMateriaBuscar;
	VentanaAlumnoMateria miVentanaAlumnoMateria;
	VentanaAlumnoMateriaNueva miVentanaAlumnoMateriaNueva;
	VentanaAlumnoMesa miVentanaAlumnoMesa;
	VentanaMesa miVentanaMesa;
	VentanaMesaBuscar miVentanaMesaBuscar;
	VentanaActualizaNotaMesa miVentanaActualizaNotaMesa;
	VentanaReportes miVentanaReportes;
	VentanaReporteListaAlumnos miVentanaReporteListaAlumnos;
	VentanaReportePlanillasParciales miVentanaReportePlanillasParciales;
	VentanaAlumnoParciales miVentanaAlumnoParciales;
	VentanaMateriaNueva miVentanaMateriaNueva;
	Logica miLogica;
	Coordinador miCoordinador;	
	Reportes miReporte;
	
	
	public static void main(String[] args) {
	    try {
	        
	       
	        
	        // 1. Definimos nuestra paleta de colores personalizada para el ISFTyD N° 44
	        // Grises para fondos y bordes
	        UIManager.put("Panel.background", new Color(245, 246, 248)); // Gris muy claro de fondo
	        UIManager.put("Control.background", new Color(255, 255, 255)); // Fondo de inputs y combos

	        // Colores de Acento (Verde y Rojo institucional)
	        Color verdeInstitucional = new Color(34, 139, 34);  // Verde bosque / institucional
	        Color rojoInstitucional  = new Color(178, 34, 34);  // Rojo carmesí / institucional

	        // Color primario de selección y foco en campos de texto
	        UIManager.put("Component.focusColor", verdeInstitucional);
	        UIManager.put("Component.innerFocusWidth", 1);
	        UIManager.put("Button.focusedBorderColor", verdeInstitucional);
	     // 2. Inicializamos FlatLaf Claro
	        FlatLightLaf.setup();

	    } catch (Exception e) {
	        System.err.println("Error al aplicar el tema: " + e.getMessage());
	    }

	    Principal miPrincipal = new Principal();
	    miPrincipal.iniciar();
	}

	/**
	 * Permite instanciar todas las clases con las que trabaja
	 * el sistema
	 */

	private void iniciar() {
		/*Se instancian las clases*/
		miVentanaPrincipal = new VentanaPrincipal();
		miVentanaAlumno = new VentanaAlumno();
		miVentanaMateria = new VentanaMateria();
		miVentanaMateriaBuscar = new VentanaMateriaBuscar();
		miVentanaAlumnoMateria = new VentanaAlumnoMateria();
		miVentanaAlumnoMateriaNueva = new VentanaAlumnoMateriaNueva();
		miVentanaAlumnoMesa =  new VentanaAlumnoMesa();
		miVentanaMesa = new VentanaMesa();
		miVentanaMesaBuscar = new VentanaMesaBuscar();
		miLogica = new Logica();
		miVentanaAlumnoBuscar = new VentanaAlumnoBuscar();
		miVentanaActualizaNotaMesa = new VentanaActualizaNotaMesa();
		miVentanaReportes = new VentanaReportes();
		miCoordinador = new Coordinador();
		miReporte = new Reportes();
		miVentanaReporteListaAlumnos = new VentanaReporteListaAlumnos();
		miVentanaReportePlanillasParciales = new VentanaReportePlanillasParciales();
		miVentanaAlumnoParciales = new VentanaAlumnoParciales();
		miVentanaMateriaNueva = new VentanaMateriaNueva();
		
		/*Se establecen las relaciones entre clases*/
		miVentanaPrincipal.setCoordinador(miCoordinador);
		miVentanaAlumno.setCoordinador(miCoordinador);		
		miVentanaAlumnoBuscar.setCoordinador(miCoordinador);
		miVentanaMateria.setCoordinador(miCoordinador);
		miVentanaMateriaBuscar.setCoordinador(miCoordinador);
		miVentanaAlumnoMateria.setCoordinador(miCoordinador);
		miVentanaAlumnoMateriaNueva.setCoordinador(miCoordinador);
		miVentanaAlumnoMesa.setCoordinador(miCoordinador);
		miVentanaMesa.setCoordinador(miCoordinador);
		miVentanaMesaBuscar.setCoordinador(miCoordinador);
		miVentanaActualizaNotaMesa.setCoordinador(miCoordinador);
		miVentanaReportes.setCoordinador(miCoordinador);
		miLogica.setCoordinador(miCoordinador);
		miReporte.setCoordinador(miCoordinador);
		miVentanaReporteListaAlumnos.setMiCoordinador(miCoordinador);
		miVentanaReportePlanillasParciales.setMiCoordinador(miCoordinador);
		miVentanaAlumnoParciales.setMiCoordinador(miCoordinador);
		miVentanaMateriaNueva.setMiCoordinador(miCoordinador);
		
		/*Se establecen relaciones con la clase coordinador*/
		miCoordinador.setMiLogica(miLogica);
		miCoordinador.setMiVentanaPrincipal(miVentanaPrincipal);
		miCoordinador.setMiVentanaAlumno(miVentanaAlumno);
		miCoordinador.setMiVentanaAlumnoBuscar(miVentanaAlumnoBuscar);
		miCoordinador.setMiReporteAlumnos(miReporte);
		miCoordinador.setMiVentanaMateria(miVentanaMateria);
		miCoordinador.setMiVentanaMateriaBuscar(miVentanaMateriaBuscar);
		miCoordinador.setMiVentanaAlumnoMateria(miVentanaAlumnoMateria);
		miCoordinador.setMiVentanaAlumnoMateriaNueva(miVentanaAlumnoMateriaNueva);
		miCoordinador.setMiVentanaAlumnoMesa(miVentanaAlumnoMesa);
		miCoordinador.setMiVentanaMesa(miVentanaMesa);
		miCoordinador.setMiVentanaMesaBuscar(miVentanaMesaBuscar);
		miCoordinador.setMiVentanaActualizaNotaMesa(miVentanaActualizaNotaMesa);
		miCoordinador.setMiVentanaReportes(miVentanaReportes);
		miCoordinador.setMiVentanaReporteListaAlumnos(miVentanaReporteListaAlumnos);
		miCoordinador.setMiVentanaReportePlanillasParciales(miVentanaReportePlanillasParciales);
		miCoordinador.setMiVentanaAlumnoParciales(miVentanaAlumnoParciales);
		miCoordinador.setMiVentanaMateriaNueva(miVentanaMateriaNueva);
		
		miVentanaPrincipal.setVisible(true);
	}

	/**
		 * Busca mesas en la BD según el tipo de filtro y devuelve una lista de MesaVO
		 */
		public java.util.List<MesaVO> buscarMesasPorFiltro(int btn, String codMat) {
			java.util.List<MesaVO> lista = new java.util.ArrayList<>();
			String sql = "";
	
			if (btn == 1) {
				sql = "SELECT * FROM mesa ORDER BY mesafecha DESC, codmat1";
			} else if (btn == 2) {
				sql = "SELECT * FROM mesa WHERE codmat1 = ? ORDER BY mesafecha DESC";
			} else if (btn == 3) {
				sql = "SELECT * FROM mesa WHERE codmat1 = ? AND mesaarmada = 'abierta' ORDER BY mesafecha DESC";
			} else if (btn == 4) {
				sql = "SELECT * FROM mesa WHERE codmat1 = ? AND mesaarmada = 'cerrada' ORDER BY mesafecha DESC";
			} else if (btn == 5) {
				sql = "SELECT * FROM mesa WHERE codmat1 = ? ORDER BY mesafecha DESC";
			} else {
				sql = "SELECT * FROM mesa ORDER BY mesafecha DESC";
			}
	
			Conexion conex = new Conexion();
	
			try (java.sql.Connection cn = conex.getConnection();
				 java.sql.PreparedStatement ps = cn.prepareStatement(sql)) {
	
				if (btn >= 2 && btn <= 5 && codMat != null) {
					ps.setString(1, codMat);
				}
	
				try (java.sql.ResultSet res = ps.executeQuery()) {
					while (res.next()) {
						MesaVO mesa = new MesaVO();
						mesa.setCodmesa(res.getInt("codmesa"));
						mesa.setCodmat(res.getString("codmat1"));
						mesa.setMesafecha(res.getString("mesafecha"));
						mesa.setMesallamado(res.getInt("mesallamado"));
						mesa.setMesalibro(res.getInt("mesalibro"));
						mesa.setMesafolio(res.getInt("mesafolio"));
						mesa.setMesasituacion(res.getString("mesasituacion"));
						mesa.setMesaarmada(res.getString("mesaarmada"));
						mesa.setTurno(res.getString("turnomesa"));
						lista.add(mesa);
					}
				}
			} catch (java.sql.SQLException e) {
				System.err.println("Error al buscar mesas por filtro: " + e.getMessage());
			} finally {
				conex.desconectar();
			}
			return lista;
		}

}
