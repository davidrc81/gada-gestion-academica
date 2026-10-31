package modelo;

import java.util.List;
import javax.swing.JOptionPane;
import controlador.Coordinador;

/**
 * Programa Logica - Refactorizado, seguro y desacoplado
 * Paquete modelo
 */
public class Logica {
	
	private Coordinador miCoordinador;

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	// =========================================================================
	// SECCIÓN ALUMNOS
	// =========================================================================

	/**
	 * Valida y registra un nuevo alumno
	 */
	public boolean validarRegistro(AlumnoVO miAlumno) {
		if (miAlumno.getAlapynom() == null || miAlumno.getAlapynom().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "No se ingresó Apellido y Nombre", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		AlumnoDAO miAlumnoDAO = new AlumnoDAO();
		boolean exito = miAlumnoDAO.registrarAlumno(miAlumno);
		
		if (exito) {
			JOptionPane.showMessageDialog(null, "Se ha registrado exitosamente", "Información", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "No se pudo registrar. Verifique si el alumno ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return exito;
	}

	/**
	 * Valida consulta de alumno por DNI exacto
	 */
	public AlumnoVO validarConsulta(String codigoPersona) {
		try {
			int codigo = Integer.parseInt(codigoPersona);	
			if (codigo > 1000000) {
				AlumnoDAO miAlumnoDAO = new AlumnoDAO();
				return miAlumnoDAO.buscarAlumno(codigo);						
			} else {
				JOptionPane.showMessageDialog(null, "El documento no es válido", "Advertencia", JOptionPane.WARNING_MESSAGE);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Debe ingresar un dato numérico", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Se ha presentado un error al consultar", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	/**
	 * Valida consulta de alumno por Apellido exacto
	 */
	public AlumnoVO validarConsultaApellido(String apellidoAlumno) {
		try {
			AlumnoDAO miAlumnoDAO = new AlumnoDAO();
			List<AlumnoVO> resultados = miAlumnoDAO.buscarAlumnosPorApellido(apellidoAlumno);
			if (resultados != null && !resultados.isEmpty()) {
				return resultados.get(0);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Se ha presentado un error al consultar", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	/**
	 * Valida la modificación de un alumno
	 */
	public boolean validarModificacion(AlumnoVO miAlumno) {
		AlumnoDAO miAlumnoDAO = new AlumnoDAO();
		boolean exito = miAlumnoDAO.modificarAlumno(miAlumno);
		
		if (exito) {
			JOptionPane.showMessageDialog(null, "Se ha modificado correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Error al modificar el alumno", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return exito;
	}

	/**
	 * Valida la eliminación de un alumno
	 */
	public boolean validarEliminacion(int codigo) {
		AlumnoDAO miAlumnoDAO = new AlumnoDAO();
		boolean exito = miAlumnoDAO.eliminarAlumno(codigo);
		
		if (exito) {
			JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "No se eliminó. El alumno puede tener materias asignadas.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return exito;
	}
	// -------------------------------------------------------------------------
		// Métodos para la Búsqueda Múltiple de Materias (VentanaMateriaBuscar)
		// -------------------------------------------------------------------------

		public List<MateriaVO> obtenerMateriasPorPlan(String plan) {
			MateriaDAO miMateriaDAO = new MateriaDAO();
			return miMateriaDAO.obtenerMateriasPorPlan(plan);
		}

		public List<MateriaVO> buscarParcialMateriaCodigo(String codMat, String plan) {
			MateriaDAO miMateriaDAO = new MateriaDAO();
			return miMateriaDAO.buscarParcialMateriaCodigo(codMat, plan);
		}

		public List<MateriaVO> buscarParcialMateriaNombre(String nomMat, String plan) {
			MateriaDAO miMateriaDAO = new MateriaDAO();
			return miMateriaDAO.buscarParcialMateriaNombre(nomMat, plan);
		}

	// -------------------------------------------------------------------------
	// Métodos para Búsqueda Múltiple de Alumnos (VentanaAlumnoBuscar)
	// -------------------------------------------------------------------------

	public List<AlumnoVO> obtenerTodosLosAlumnos() {
		AlumnoDAO miAlumnoDAO = new AlumnoDAO();
		return miAlumnoDAO.obtenerTodosLosAlumnos();
	}

	public List<AlumnoVO> buscarAlumnosPorApellido(String ape) {
		AlumnoDAO miAlumnoDAO = new AlumnoDAO();
		return miAlumnoDAO.buscarAlumnosPorApellido(ape);
	}

	public List<AlumnoVO> buscarAlumnosPorDniParcial(int doc) {
		AlumnoDAO miAlumnoDAO = new AlumnoDAO();
		return miAlumnoDAO.buscarAlumnosPorDniParcial(doc);
	}

	// =========================================================================
	// SECCIÓN MATERIAS Y MESAS
	// =========================================================================

	public MateriaVO validarConsultaCodMat(String codigoMateria) {
		try {
			MateriaDAO miMateriaDAO = new MateriaDAO();
			return miMateriaDAO.buscarMateria(codigoMateria);						
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Se ha presentado un Error al consultar materia", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	public void validarRegistroMateria(MateriaVO miMateria) {
		StringBuilder msg = new StringBuilder("Los siguientes errores ocurrieron:");
		boolean hayError = false;
		
		try {
			int curso = miMateria.getAnio();
			int modulos = miMateria.getModulos();
			
			if (miMateria.getCodmat() == null || miMateria.getCodmat().trim().isEmpty()) {
				msg.append("\n- No se ingresó el código de la materia");
				hayError = true;
			}
			if (miMateria.getMatnom() == null || miMateria.getMatnom().trim().isEmpty()) {
				msg.append("\n- No se ingresó el nombre de la materia");
				hayError = true;
			}
			if (curso < 1 || curso > 4) {
				msg.append("\n- El curso no es válido");
				hayError = true;
			}
			if (miMateria.getPlan() == null || miMateria.getPlan().trim().isEmpty()) {
				msg.append("\n- No se ingresó el plan");
				hayError = true;
			}
			if (modulos < 1 || modulos > 6) {
				msg.append("\n- Cantidad incorrecta de módulos");
				hayError = true;
			}
			
			if (hayError) {
				JOptionPane.showMessageDialog(null, msg.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				MateriaDAO miMateriaDAO = new MateriaDAO();
				miMateriaDAO.registrarMateria(miMateria);	
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al validar la materia", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void validarModificacionMateria(MateriaVO miMateria) {
		MateriaDAO miMateriaDAO = new MateriaDAO();
		miMateriaDAO.modificarMateria(miMateria);
	}

	public void validarEliminacionMateria(String codMat) {
		MateriaDAO miMateriaDAO = new MateriaDAO();
		miMateriaDAO.eliminarMateria(codMat);
	}

	public MateriaVO validarConsultaMateriaNombre(String matNom) {
		try {
			MateriaDAO miMateriaDAO = new MateriaDAO();
			return miMateriaDAO.buscarMateriaNombre(matNom);						
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Se ha presentado un Error", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	public void validarModificacionAlumnoMateria(AlumnomateriaVO miAlumnomateria) {
		AlumnomateriaDAO miAlumnomateriaDAO = new AlumnomateriaDAO();
		miAlumnomateriaDAO.modificarAlumnoMateria(miAlumnomateria);	
	}

	// -------------------------------------------------------------------------
	// Validaciones de Fecha Seguras para Mesas
	// -------------------------------------------------------------------------

	public void validarRegistroMesa(MesaVO miMesa) {	
		MesaDAO miMesaDAO = new MesaDAO();
		String fecha = miMesa.getMesafecha();
		
		if (fecha != null && fecha.length() >= 7) {
			try {
				// Reemplazar barra por guión para estandarizar YYYY-MM-DD o YYYY/MM/DD
				String fechaLimpia = fecha.replace("-", "/");
				String[] partes = fechaLimpia.split("/");
				
				if (partes.length >= 2) {
					String anio = partes[0];
					int mes = Integer.parseInt(partes[1]);
					
					if (mes == 2 || mes == 3 || mes == 7 || mes == 8 || mes == 11 || mes == 12) {
						miMesaDAO.validarRegistrarMesa(miMesa, anio, mes);
					} else {
						int respuesta = JOptionPane.showConfirmDialog(null, "Está creando una mesa en un mes irregular, ¿está seguro?", "Confirmación", JOptionPane.YES_NO_OPTION);
						if (respuesta == JOptionPane.YES_OPTION) {
							miMesaDAO.validarRegistrarMesa(miMesa, anio, mes);
						}
					}
					return;
				}
			} catch (Exception e) {
				System.err.println("Error al procesar la fecha de la mesa: " + e.getMessage());
			}
		}
		// Fallback directo si no se pudo parsear el mes exacto
		miMesaDAO.registrarMesa(miMesa);
	}

	public void validarModificacionMesa(MesaVO miMesa) {
		MesaDAO miMesaDAO = new MesaDAO();
		String fecha = miMesa.getMesafecha();
		
		if (fecha != null && fecha.length() >= 7) {
			try {
				String fechaLimpia = fecha.replace("-", "/");
				String[] partes = fechaLimpia.split("/");
				
				if (partes.length >= 2) {
					String anio = partes[0];
					int mes = Integer.parseInt(partes[1]);
					
					if (mes == 2 || mes == 3 || mes == 8 || mes == 11 || mes == 12) {
						miMesaDAO.modificarMesa(miMesa);
					} else {
						int respuesta = JOptionPane.showConfirmDialog(null, "Está asignando una mesa en un mes irregular, ¿está seguro?", "Confirmación", JOptionPane.YES_NO_OPTION);
						if (respuesta == JOptionPane.YES_OPTION) {
							miMesaDAO.modificarMesa(miMesa);
						}
					}
					return;
				}
			} catch (Exception e) {
				System.err.println("Error al procesar la fecha de la mesa: " + e.getMessage());
			}
		}
		// Fallback directo
		miMesaDAO.modificarMesa(miMesa);
	}
}