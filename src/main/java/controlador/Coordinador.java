package controlador;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import modelo.AlumnoVO;
import modelo.AlumnomateriaDAO;
import modelo.AlumnomateriaVO;
import modelo.AlumnomesaDAO;
import modelo.Logica;
import modelo.MateriaCorrelativaDAO;
import modelo.MateriaCorrelativaVO;
import modelo.MateriaDAO;
import modelo.MateriaVO;
import modelo.MesaDAO;
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

public class Coordinador {

	private Logica miLogica;
	private VentanaPrincipal miVentanaPrincipal;
	private VentanaAlumno miVentanaAlumno;
	private VentanaAlumnoBuscar miVentanaAlumnoBuscar;
	private Reportes miReporte;
	private VentanaMateria miVentanaMateria;
	private VentanaMateriaBuscar miVentanaMateriaBuscar;
	private VentanaAlumnoMateria miVentanaAlumnoMateria;
	private VentanaAlumnoMateriaNueva miVentanaAlumnoMateriaNueva;
	private VentanaAlumnoMesa miVentanaAlumnoMesa;
	private VentanaMesa miVentanaMesa;
	private VentanaMesaBuscar miVentanaMesaBuscar;
	private VentanaActualizaNotaMesa miVentanaActualizaNotaMesa;
	private VentanaReportes miVentanaReportes;
	private VentanaReporteListaAlumnos miVentanaReporteListaAlumnos;
	private VentanaReportePlanillasParciales miVentanaReportePlanillasParciales;
	private VentanaAlumnoParciales miVentanaAlumnoParciales;
	private VentanaMateriaNueva miVentanaMateriaNueva;

	// =========================================================================
	// GETTERS Y SETTERS DE VENTANAS Y COMPONENTES
	// =========================================================================

	public VentanaMateriaNueva getMiVentanaMateriaNueva() {
		return miVentanaMateriaNueva;
	}
	public void setMiVentanaMateriaNueva(VentanaMateriaNueva miVentanaMateriaNueva) {
		this.miVentanaMateriaNueva = miVentanaMateriaNueva;
	}
	public VentanaAlumnoParciales getMiVentanaAlumnoParciales() {
		return miVentanaAlumnoParciales;
	}
	public void setMiVentanaAlumnoParciales(VentanaAlumnoParciales miVentanaAlumnoParciales) {
		this.miVentanaAlumnoParciales = miVentanaAlumnoParciales;
	}
	public VentanaReportePlanillasParciales getMiVentanaReportePlanillasParciales() {
		return miVentanaReportePlanillasParciales;
	}
	public void setMiVentanaReportePlanillasParciales(VentanaReportePlanillasParciales miVentanaReportePlanillasParciales) {
		this.miVentanaReportePlanillasParciales = miVentanaReportePlanillasParciales;
	}
	public VentanaReporteListaAlumnos getMiVentanaReporteListaAlumnos() {
		return miVentanaReporteListaAlumnos;
	}
	public void setMiVentanaReporteListaAlumnos(VentanaReporteListaAlumnos miVentanaReporteListaAlumnos) {
		this.miVentanaReporteListaAlumnos = miVentanaReporteListaAlumnos;
	}
	public VentanaReportes getMiVentanaReportes() {
		return miVentanaReportes;
	}
	public void setMiVentanaReportes(VentanaReportes miVentanaReportes) {
		this.miVentanaReportes = miVentanaReportes;
	}
	public VentanaActualizaNotaMesa getMiVentanaActualizaNotaMesa() {
		return miVentanaActualizaNotaMesa;
	}
	public void setMiVentanaActualizaNotaMesa(VentanaActualizaNotaMesa miVentanaActualizaNotaMesa) {
		this.miVentanaActualizaNotaMesa = miVentanaActualizaNotaMesa;
	}
	public VentanaPrincipal getMiVentanaPrincipal() {
		return miVentanaPrincipal;
	}
	public void setMiVentanaPrincipal(VentanaPrincipal miVentanaPrincipal) {
		this.miVentanaPrincipal = miVentanaPrincipal;
	}
	public VentanaAlumno getMiVentanaAlumno() {
		return miVentanaAlumno;
	}
	public void setMiVentanaAlumno(VentanaAlumno miVentanaAlumno) {
		this.miVentanaAlumno = miVentanaAlumno;
	}
	public VentanaAlumnoBuscar getMiVentanaAlumnoBuscar() {
		return miVentanaAlumnoBuscar;
	}
	public void setMiVentanaAlumnoBuscar(VentanaAlumnoBuscar miVentanaAlumnoBuscar) {
		this.miVentanaAlumnoBuscar = miVentanaAlumnoBuscar;
	}
	public Logica getMiLogica() {
		return miLogica;
	}
	public void setMiLogica(Logica miLogica) {
		this.miLogica = miLogica;
	}
	public Reportes getMiReporteAlumnos() {
		return miReporte;
	}
	public void setMiReporteAlumnos(Reportes miReporteAlumnos) {
		this.miReporte = miReporteAlumnos;
	}
	public VentanaMateria getMiVentanaMateria() {
		return miVentanaMateria;
	}
	public void setMiVentanaMateria(VentanaMateria miVentanaMateria) {
		this.miVentanaMateria = miVentanaMateria;
	}
	public VentanaMateriaBuscar getMiVentanaMateriaBuscar() {
		return miVentanaMateriaBuscar;
	}
	public void setMiVentanaMateriaBuscar(VentanaMateriaBuscar miVentanaMateriaBuscar) {
		this.miVentanaMateriaBuscar = miVentanaMateriaBuscar;
	}
	public VentanaAlumnoMateria getMiVentanaAlumnoMateria() {
		return miVentanaAlumnoMateria;
	}
	public void setMiVentanaAlumnoMateria(VentanaAlumnoMateria miVentanaAlumnoMateria) {
		this.miVentanaAlumnoMateria = miVentanaAlumnoMateria;
	}
	public VentanaAlumnoMateriaNueva getMiVentanaAlumnoMateriaNueva() {
		return miVentanaAlumnoMateriaNueva;
	}
	public void setMiVentanaAlumnoMateriaNueva(VentanaAlumnoMateriaNueva miVentanaAlumnoMateriaNueva) {
		this.miVentanaAlumnoMateriaNueva = miVentanaAlumnoMateriaNueva;
	}
	public void setMiVentanaAlumnoMesa(VentanaAlumnoMesa miVentanaAlumnoMesa) {
		this.miVentanaAlumnoMesa = miVentanaAlumnoMesa;		
	}
	public VentanaAlumnoMesa getMiVentanaAlumnoMesa(){
		return miVentanaAlumnoMesa;
	}
	public void setMiVentanaMesa(VentanaMesa miVentanaMesa) {
		this.miVentanaMesa = miVentanaMesa;
	}
	public VentanaMesa getMiVentanaMesa(){
		return miVentanaMesa;
	}
	public VentanaMesaBuscar getMiVentanaMesaBuscar() {
		return miVentanaMesaBuscar;
	}
	public void setMiVentanaMesaBuscar(VentanaMesaBuscar miVentanaMesaBuscar) {
		this.miVentanaMesaBuscar = miVentanaMesaBuscar;
	}

	// =========================================================================
	// NAVEGACIÓN Y APERTURA DE VENTANAS
	// =========================================================================

	public void mostrarVentanaAlumno() {
		miVentanaAlumno.setVisible(true);
		miVentanaAlumno.limpiar();
	}
	
	public void mostrarVentanaAlumnoBuscar(int btn, int doc, String ape, int ventana) {
		miVentanaAlumnoBuscar.setVisible(true);
		miVentanaAlumnoBuscar.mostrarDatosConTableModel(btn, doc, ape, ventana);
	}
	
	public void mostrarVentanaMateria() {
		miVentanaMateria.setVisible(true);
		miVentanaMateria.limpiar();	
	}
	
	public void mostrarVentanaMateriaBuscar(int btn, String codMat, String nomMat, String plan) {
		miVentanaMateriaBuscar.setVisible(true);
		miVentanaMateriaBuscar.mostrarDatosMateriaConTableModel(btn, codMat, nomMat, plan);
	}
	
	public void mostrarVentanaAlumnoMateria(){
		miVentanaAlumnoMateria.setVisible(true);
		miVentanaAlumnoMateria.limpiar();
	}
	
	public void mostrarVentanaAlumnoMesa() {
		miVentanaAlumnoMesa.setVisible(true);
		miVentanaAlumnoMesa.limpiar();
	}
	
	public void mostrarVentanaMesa(DefaultComboBoxModel<String> modeloComboMaterias) {
		miVentanaMesa.setVisible(true);
		miVentanaMesa.cargarCombo(modeloComboMaterias);
		miVentanaMesa.limpiar();
	}
	
	public void mostrarVentanaMesaBuscar(int btn, String codMat, int ventana){
		miVentanaMesaBuscar.setVisible(true);
		miVentanaMesaBuscar.mostrarDatosMesaConTableModel(btn, codMat, ventana);
	}
	
	public void mostrarVentanaAlumnoMateriaNueva(int doc, String nom) {
		miVentanaAlumnoMateriaNueva.setVisible(true);
		miVentanaAlumnoMateriaNueva.mostrarListaDeMaterias(doc, nom);
	}
	
	public void mostrarVentanaReportes() {
		miVentanaReportes.setVisible(true);
	}
	
	public void mostrarVentanaReporteListaAlumnos() {
		miVentanaReporteListaAlumnos.setVisible(true);
	}
	
	public void mostrarVentanaReportePlanillasParciales() {
		miVentanaReportePlanillasParciales.setVisible(true);
	}
	
	public void mostrarVentanaAlumnoParciales() {
		miVentanaAlumnoParciales.setVisible(true);
	}
	
	public void mostrarVentanaMateriaNueva(String codMat, String nomMat, int anio, String plan, int ven) {
		miVentanaMateriaNueva.setVisible(true);
		miVentanaMateriaNueva.mostrarListadoDeMaterias(codMat, nomMat, anio, plan, ven);
	}

	// =========================================================================
	// OPERACIONES Y LÓGICA DE ALUMNOS
	// =========================================================================

	public boolean registrarAlumno(AlumnoVO miAlumno){
		return miLogica.validarRegistro(miAlumno);
	}
	
	public boolean modificarAlumno(AlumnoVO miAlumno) {
		return miLogica.validarModificacion(miAlumno);
	}

	public boolean eliminarAlumno(int codigo) {
		return miLogica.validarEliminacion(codigo);
	}

	public AlumnoVO buscarAlumno(String codigoAlumno) {
		return miLogica.validarConsulta(codigoAlumno);
	}

	public AlumnoVO buscarAlumnoApellido(String apellidoAlumno) {
		return miLogica.validarConsultaApellido(apellidoAlumno);
	}

	public List<AlumnoVO> buscarAlumnosPorFiltro(int btn, int doc, String ape) {
		if (btn == 1) {
			return miLogica.obtenerTodosLosAlumnos();
		} else if (btn == 2) {
			return miLogica.buscarAlumnosPorApellido(ape);
		} else if (btn == 3) {
			return miLogica.buscarAlumnosPorDniParcial(doc);
		}
		return miLogica.obtenerTodosLosAlumnos();
	}

	public void pasarDatosAlumno(AlumnoVO miAlumno){
		 miVentanaAlumno.muestraAlumno(miAlumno);
	}

	public void pasarDatosAlumnoMateria(AlumnoVO miAlumno){
		miVentanaAlumnoMateria.muestraAlumno(miAlumno);
	}

	// =========================================================================
	// OPERACIONES DE MATERIAS
	// =========================================================================

	public void pasarDatosMateria(MateriaVO miMateria) {
		miVentanaMateria.muestraMateria(miMateria);	
	}

	public MateriaVO buscarMateria(String codigoMateria) {
		return miLogica.validarConsultaCodMat(codigoMateria);
	}

	public void registrarMateria(MateriaVO miMateria) {
		miLogica.validarRegistroMateria(miMateria);
	}

	public void modificarMateria(MateriaVO miMateria) {
		miLogica.validarModificacionMateria(miMateria);
	}

	public void eliminarMateria(String codMat) {
		miLogica.validarEliminacionMateria(codMat);
	}

	public MateriaVO buscarMateriaNombre(String matNom) {		
		return miLogica.validarConsultaMateriaNombre(matNom);
	}	

	public List<MateriaVO> buscarMateriasPorFiltro(int btn, String codMat, String nomMat, String plan) {
		if (btn == 1) {
			return miLogica.obtenerMateriasPorPlan(plan);
		} else if (btn == 2) {
			return miLogica.buscarParcialMateriaCodigo(codMat, plan);
		} else if (btn == 3) {
			return miLogica.buscarParcialMateriaNombre(nomMat, plan);
		}
		return miLogica.obtenerMateriasPorPlan(plan);
	}

	public void cargarComboMaterias(DefaultComboBoxModel<String> modeloComboMateria) {	
		MateriaDAO miMateriaDAO = new MateriaDAO();
		miMateriaDAO.cargarComboMaterias(modeloComboMateria);
	}

	public void cargarComboPlanes(DefaultComboBoxModel<String> modeloComboPlan) {
		MateriaDAO miMateriaDAO = new MateriaDAO();
		miMateriaDAO.cargarComboPlanes(modeloComboPlan);
	}

	public void cargarListadoVentanaMateria(DefaultListModel<String> modeCorre, String codMat) {
		MateriaDAO miMateriaDAO = new MateriaDAO();
		miMateriaDAO.cargarListadoVentanaMateria(modeCorre, codMat);
	}

	public void cargarTodasLasMaterias(DefaultListModel<String> modeloTotal, String plan) {
		MateriaDAO miMateriaDAO = new MateriaDAO();
		miMateriaDAO.cargarTodasLasMaterias(modeloTotal, plan);
	}

	public void cargarListadoCorrelativas(DefaultListModel<String> modeloTotal, DefaultListModel<String> modeloCorre, String codMat, int anio, String plan, int ven) {
		MateriaDAO miMateriaDAO = new MateriaDAO();
		miMateriaDAO.cargarListadoCorrelativas(modeloTotal, modeloCorre, codMat, anio, plan, ven);
	}

	// =========================================================================
	// OPERACIONES DE ALUMNOMATERIA Y CORRELATIVAS
	// =========================================================================

	public void registrarAlumnoMateria(AlumnomateriaVO miAlumnoMateriaVO, int cantMat) {
		AlumnomateriaDAO miAlumnoMateriaDAO = new AlumnomateriaDAO();
		miAlumnoMateriaDAO.registrarAlumnoMateria(miAlumnoMateriaVO, cantMat);
	}

	public void cargarComboMateriasAlumno(DefaultComboBoxModel<String> modeloComboMateria, int dniAlum, String nomAlum) {	
		AlumnomateriaDAO miAlumnoMateriaDAO = new AlumnomateriaDAO();
		miAlumnoMateriaDAO.cargarComboMateriasAlumno(modeloComboMateria, dniAlum);
	}

	public void cargarComboMateriasNuevasAlumno(DefaultComboBoxModel<String> modeloComboMateria, int dniAlum, String nomAlum) {
		MateriaDAO miMateriaDAO = new MateriaDAO();
		miMateriaDAO.cargarComboMateriasNuevasAlumno(modeloComboMateria, dniAlum, nomAlum);	
	}

	public void cargarListadoMateriasNuevasAlumno(DefaultListModel<String> modeloTotal, int doc, String plan) {
		MateriaDAO miMateriaDAO = new MateriaDAO();
		miMateriaDAO.cargarListadoMateriasNuevasAlumno(modeloTotal, doc, plan);
	}

	public void modificarAlumnoMateria(AlumnomateriaVO miAlumnomateria) {
		miLogica.validarModificacionAlumnoMateria(miAlumnomateria);
	}

	public void eliminarAlumnoMateria(int numAiBaja) {
		AlumnomateriaDAO miAlumnoMateriaDAO = new AlumnomateriaDAO();
		miAlumnoMateriaDAO.eliminarAlumnoMateria(numAiBaja);
	}

	public void registrarCorrelativa(MateriaCorrelativaVO miMateriaCorrelativaVO, int cantMat) {
		MateriaCorrelativaDAO miMateriaCorrelativaDAO = new MateriaCorrelativaDAO();
		miMateriaCorrelativaDAO.registrarCorrelativa(miMateriaCorrelativaVO, cantMat);
	}

	public void eliminarCorrelativa(MateriaCorrelativaVO miMateriaCorrelativaVO, int cantMat) {
		MateriaCorrelativaDAO miMateriaCorrelativaDAO = new MateriaCorrelativaDAO();
		miMateriaCorrelativaDAO.eliminarCorrelativa(miMateriaCorrelativaVO, cantMat);
	}

	// =========================================================================
	// OPERACIONES DE MESAS
	// =========================================================================

	public void registrarMesa(MesaVO miMesa) {
		miLogica.validarRegistroMesa(miMesa);	
	}

	public void modificarMesa(MesaVO miMesa) {
		miLogica.validarModificacionMesa(miMesa);	
	}

	public void pasarDatosMesa(MesaVO miMesaVO) {
		miVentanaMesa.muestraMesa(miMesaVO);		
	}

	public void eliminarMesa(int codigoDeMesa) {
		MesaDAO miMesaDAO = new MesaDAO();
		miMesaDAO.eliminarMesa(codigoDeMesa);
	}

	public List<MesaVO> buscarMesasPorFiltro(int btn, String codMat) {
		MesaDAO miMesaDAO = new MesaDAO();
		return miMesaDAO.buscarMesasPorFiltro(btn, codMat);
	}

	public void pasarDatosAlumnoMesa(MesaVO miMesaVO) {
		miVentanaAlumnoMesa.muestraMesa(miMesaVO);	
	}

	public void pasarDatosAlumnoMesaQuitar(MesaVO miMesaVO) {
		miVentanaAlumnoMesa.muestraMesaQuitar(miMesaVO);		
	}

	public void pasarDatosNotaAlumnoMesa(MesaVO miMesaVO) {
		miVentanaActualizaNotaMesa.setVisible(true);
		miVentanaActualizaNotaMesa.mostrarTabla(miMesaVO);
	}

	public void pasarDatosAlumnoCondicionalMesa(MesaVO miMesaVO) {		
		miVentanaAlumnoMesa.muestraMesaCondicional(miMesaVO);
	}

	// =========================================================================
	// REPORTES
	// =========================================================================

	public void pasarDatosActaVolante(MesaVO miMesaVO) {
		String codigoMat = miMesaVO.getCodmat();
		MateriaDAO miMateriaDAO = new MateriaDAO();
		String nombreMat = miMateriaDAO.darNombreMateria(codigoMat);
		int anio = miMateriaDAO.darAnioMateria(codigoMat);
		miReporte.reporteActaVolante(nombreMat, miMesaVO, anio);
	}
	
	public void crearReporteMaterias(String plan){
		miReporte.reporteMaterias(plan);
	}

	public void crearReporteListaAlumnos(DefaultListModel<String> modeloImprime, int anioCursada) {		
		miReporte.reporteListaAlumnos(modeloImprime, anioCursada);
	}

	public void crearReporteAnalitico(int dni) {
		miReporte.reporteAnalitico(dni);
	}

	public void crearReportePromedioTecnicatura(int anioDesde, int anioHasta, int anioActual) {
		miReporte.reportePromedioTecnicatura(anioDesde, anioHasta, anioActual);
	}

	public void crearReportePromedioProfesorado(int anioDesde, int anioHasta, int anioActual) {
		miReporte.reportePromedioProfesorado(anioDesde, anioHasta, anioActual);
	}
	
	public void crearReportePlanillasParciales(DefaultListModel<String> modeloImprime, int anioCursada) {
		miReporte.reportePlanillasParciales(modeloImprime, anioCursada);
	}
}