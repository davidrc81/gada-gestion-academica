package modelo;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import controlador.Coordinador;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Reportes {
		
	private JasperReport report;
	private JasperPrint reportFilled;
	private JasperViewer viewer;
	private Coordinador miCoordinador;
	
	private String rutaReporte= "C:\\Colegios 2022\\Algoritmos III\\Sistema GAdA GitHub\\Repo Git\\Sistema GAdA v1.4"
			+ " con 3 turnos\\src\\main\\java\\reportes\\";
	//C:\Colegios 2022\Algoritmos III\Sistema GAdA GitHub\Repo Git\Sistema GAdA v1.4 con 3 turnos\src\main\java\reportes
	
	public void createReport(String loc){
		Conexion conex= new Conexion();
		Connection con;
		con=conex.getConnection();
		
		Map<String,Object> parametros = new HashMap<>();
		parametros.put("ParamLocalidad", new String(loc));
		/*
		 * En Java, la forma de crear instancias de objetos Boolean utilizando el constructor
		 *  new Boolean(true) se considera obsoleta y ha sido marcada como deprecada. En lugar 
		 *  de eso, se recomienda utilizar la clase estática Boolean.valueOf(boolean) para obtener
		 *   instancias de Boolean. Este enfoque es más eficiente en términos de memoria,
		 *    ya que Boolean.valueOf(boolean) utiliza un caché interno para reutilizar instancias 
		 *    existentes cuando sea posible.
		 */
		//parametros.put("ParamDoc", new Boolean(true));
		parametros.put("ParamDoc", Boolean.valueOf(true));
		
		try {
			report = (JasperReport) JRLoader.loadObjectFromFile(rutaReporte+"ReporteAlumnos.jasper");
			reportFilled = JasperFillManager.fillReport(report, parametros,con);
			showViewer();
		} catch (JRException e) {
			
			e.printStackTrace();
		}
	}	
	/**
	 *  Reporte Acta Volante para mesas
	 *  
	 * @param nomMat
	 * @param miMesaVO
	 * @param anio
	 */
	public void reporteActaVolante(String nomMat, MesaVO miMesaVO, int anio){
		Conexion conex= new Conexion();
		Connection con;
		con=conex.getConnection();
		
		Map<String,Object> parametros = new HashMap<>();
		parametros.put("ParamNomMat", new String(nomMat));
		parametros.put("ParamCodMesa", new Integer(miMesaVO.getCodmesa()));
		parametros.put("ParamFecha", new String(miMesaVO.getMesafecha()));
		parametros.put("ParamLlamado", new Integer(miMesaVO.getMesallamado()));
		parametros.put("ParamSituacion", new String(miMesaVO.getMesasituacion()));
		parametros.put("ParamAnio", new Integer(anio));
		
		try {
			report = (JasperReport) JRLoader.loadObjectFromFile(rutaReporte+"RepoActaVolante.jasper");
			reportFilled = JasperFillManager.fillReport(report, parametros,con);
			showViewer();
		} catch (JRException e) {
			
			e.printStackTrace();
		}
	}	
	/**
	 * Muestra  el reporte
	 */
	public void showViewer(){
		//Agregarle "false" para que al cerrar el reporte no cierre el systema también
		viewer = new JasperViewer(reportFilled,false);
		viewer.setVisible(true);
	}
	/**
	 * Set Coordinador
	 * 
	 * @param miCoordinador
	 */
	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
		
	}
	/**
	 * Reporte de todas las materias de un Plan de estudios
	 * 
	 * @param plan
	 */
	public void reporteMaterias(String plan) {
		Conexion conex= new Conexion();
		Connection con;
		con=conex.getConnection();
		
		Map<String,Object> parametros = new HashMap<>();
		parametros.put("ParamPlan", new String(plan));
		
		try {
			report = (JasperReport) JRLoader.loadObjectFromFile(rutaReporte+"AARepoMaterias.jasper");
			reportFilled = JasperFillManager.fillReport(report, parametros,con);
			showViewer();
		} catch (JRException e) {			
			e.printStackTrace();
		}		
	}
	
	public void reporteListaAlumnos(DefaultListModel<String> modeloImprime, int anioCursada) {
		Conexion conex= new Conexion();
		Connection con;
		con=conex.getConnection();
		
		String fila,codMat;	
			
			if (modeloImprime.size()>0){
				for(int i=0;i<modeloImprime.size();i++){
					codMat="";
					fila=modeloImprime.get(i);	
					//en desde guardo la primer letra del codigo de la materia y con el while recorro hasta tener el 
					//codigo completo
					int desde=3;
					int hasta=4;
					String letra=fila.substring(desde,hasta);
					while (!letra.equals(" ")){
						codMat+=letra;
						desde++;
						hasta++;
						letra=fila.substring(desde,hasta);
					}			
					
					Map<String,Object> parametros = new HashMap<>();
					parametros.put("ParamCodMat", new String(codMat));
					//parametros.put("ParamAnioCursada", new Integer(anioCursada));
					parametros.put("ParamAnioCursada", Integer.valueOf(anioCursada));
					
					try {
						report = (JasperReport) JRLoader.loadObjectFromFile(rutaReporte+"RepoListaAlumnos.jasper");
						reportFilled = JasperFillManager.fillReport(report, parametros,con);
						showViewer();
					} catch (JRException e) {			
						e.printStackTrace();
					}				
				}
				modeloImprime.clear();
			}
			else
				JOptionPane.showMessageDialog(null, "Debe seleccionar materias para imprimir","Error",JOptionPane.ERROR_MESSAGE);	
		}
	
	public void reportePlanillasParciales(DefaultListModel<String> modeloImprime, int anioCursada) {
		Conexion conex= new Conexion();
		Connection con;
		con=conex.getConnection();
		
		String fila,codMat;	
			
			if (modeloImprime.size()>0){
				for(int i=0;i<modeloImprime.size();i++){
					codMat="";
					fila=modeloImprime.get(i);				
					int desde=3;
					int hasta=4;
					String letra=fila.substring(desde,hasta);
					while (!letra.equals(" ")){
						codMat+=letra;
						desde++;
						hasta++;
						letra=fila.substring(desde,hasta);
					}			
					
					Map<String,Object> parametros = new HashMap<>();
					parametros.put("ParamCodMat", new String(codMat));
					parametros.put("ParamAnioCursada", Integer.valueOf(anioCursada));
					
					try {
						report = (JasperReport) JRLoader.loadObjectFromFile(rutaReporte+"RepoPlanillaParciales.jasper");
						reportFilled = JasperFillManager.fillReport(report, parametros,con);
						showViewer();
					} catch (JRException e) {			
						e.printStackTrace();
					}				
				}
				modeloImprime.clear();
			}
			else
				JOptionPane.showMessageDialog(null, "Debe seleccionar materias para imprimir","Error",JOptionPane.ERROR_MESSAGE);	
		}
	
	public void reporteAnalitico(int dni) {
		Conexion conex= new Conexion();
		Connection con;
		con=conex.getConnection();
		
		Map<String,Object> parametros = new HashMap<>();
		//parametros.put("ParamDni", new Integer(dni));
		parametros.put("ParamDni", Integer.valueOf(dni));
		
		try {
			report = (JasperReport) JRLoader.loadObjectFromFile(rutaReporte+"RepoAnaliticoBaseGada.jasper");
			reportFilled = JasperFillManager.fillReport(report, parametros,con);
			showViewer();
		} catch (JRException e) {			
			e.printStackTrace();
		}		
	}
	public void reportePromedioTecnicatura(int anioDesde, int anioHasta, int anioActual) {
		Conexion conex= new Conexion();
		Connection con;
		con=conex.getConnection();
		
		Map<String,Object> parametros = new HashMap<>();
		parametros.put("ParamAnioDesde", Integer.valueOf(anioDesde));
		parametros.put("ParamAnioHasta", Integer.valueOf(anioHasta));
		parametros.put("ParamFechaCursada", Integer.valueOf(anioActual));
		
		try {
			report = (JasperReport) JRLoader.loadObjectFromFile(rutaReporte+"RepoPromedioTecnicatura.jasper");
			reportFilled = JasperFillManager.fillReport(report, parametros,con);
			showViewer();
		} catch (JRException e) {			
			e.printStackTrace();
		}				
	}
	public void reportePromedioProfesorado(int anioDesde, int anioHasta, int anioActual) {
		Conexion conex= new Conexion();
		Connection con;
		con=conex.getConnection();
		
		Map<String,Object> parametros = new HashMap<>();
		parametros.put("ParamAnioDesde", new Integer(anioDesde));
		parametros.put("ParamAnioHasta", new Integer(anioHasta));
		parametros.put("ParamFechaCursada", new Integer(anioActual));
		
		try {
			report = (JasperReport) JRLoader.loadObjectFromFile(rutaReporte+"RepoPromedioProfesorado.jasper");
			reportFilled = JasperFillManager.fillReport(report, parametros,con);
			showViewer();
		} catch (JRException e) {			
			e.printStackTrace();
		}		
	}	
	}
	

