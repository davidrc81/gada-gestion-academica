package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controlador.Coordinador;
import modelo.AlumnomateriaDAO;
import modelo.AlumnomesaDAO;
import modelo.CalculaAi;
import modelo.Conexion;
import modelo.MateriaCorrelativaDAO;
import modelo.MateriaDAO;
import modelo.MesaDAO;
import modelo.MesaVO;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
/**
 * Programa VentanaAlumnoMesa
 * Paquete Vista
 * Crea mesas, asigna alumnos y carga notas
 * @author Edgardo
 *
 */
public class VentanaAlumnoMesa extends JFrame {

	private JPanel contentPane;
	private JButton btnImprimeActa;
	private JComboBox <String> cbMaterias;
	static DefaultComboBoxModel<String> modeloComboMaterias;
	private Coordinador miCoordinador;
	private JScrollPane scrollPane;
	private JList<String> listaAlFinal;
	private DefaultListModel<String> modeloLista;
	private String codMat;
	private int codMesa;	
	private JTextField txtFecha;
	private JTextField txtLlamado;
	private JTextField txtSituacion;
	private JScrollPane scrollPaneActa;
	private JList<String> listaActa;
	private DefaultListModel<String> modeloActa;
	private JButton btnActaVolante;
	private JButton btnArmaMesa;
	private JButton btnCancelar;
	private JButton btnNotasMesa;
	private JButton btnCreaMesa;
	private JButton btnModificarMesa;
	
	private boolean modifica=false;
	private boolean quita=false;
	private JButton btnAlCondicionales;
	private JTextField txtTurno;
	private JComboBox<String> cbPlan;
	static DefaultComboBoxModel<String> modeloComboPlan;
	
	private boolean primeraCarga = true; // Bandera para determinar si es la primera carga del cbMaterias
	private int cantItem=0;
	private boolean primerPasada = true; // flag para ver si es la primera pasada
	
	private Map<String, String> materiaPlan = new HashMap<>();
	private Map<String, String> materiaNom = new HashMap<>();
	
	private boolean condicional=false;
	
	private static int codigoMesa;

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador= miCoordinador;		
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAlumnoMesa frame = new VentanaAlumnoMesa();
					frame.setVisible(true);
					frame.limpiar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaAlumnoMesa() {
		setTitle("Gestión de mesas de examen de: ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1019, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorte = new JPanel();
		panelNorte.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_1 = new JLabel("Código de Materia:");
		panelNorte.add(lblNewLabel_1);
		
		cargaMateriasCache(materiaPlan,materiaNom);
		
		cbMaterias = new JComboBox<String>();
		cbMaterias.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				 if (e.getStateChange() == ItemEvent.SELECTED) {
					tituloConNombreMateria();			
				 }
			}
		});
		cbMaterias.setEditable(false);
		modeloComboMaterias = new DefaultComboBoxModel<String>();
		cbMaterias.setModel(modeloComboMaterias);
		panelNorte.add(cbMaterias);		
		
		
		cbPlan = new JComboBox<String>();
		cbPlan.setEditable(false);
		cbPlan.addItemListener(new ItemListener() {
			private boolean cargaEnProgreso=false;
			public void itemStateChanged(ItemEvent e) {
				//elige un plan y cambia el combo de las materias
				if (e.getStateChange()==ItemEvent.SELECTED) {
			
					if (!cargaEnProgreso) {
						cargaEnProgreso=true;
						if (!primeraCarga) {
							//contar cuantos items tiene y borrar los primeros
							cantItem=cbMaterias.getItemCount();
						}else {
							primeraCarga=false;
							//marcar que ya se hizo la primera carga
						}
											
						cambiarMateriasdelCombo();
						
						if (cantItem>0) {
							//deshabilita temporalmente el Itemlistener
							cbPlan.removeItemListener(this);
							for (int i=0; i<cantItem; i++) {
								if (modeloComboMaterias.getSize()>0) {
									modeloComboMaterias.removeElementAt(0);
								}
							}
						}
						// vuelve a habilitar el itemlistener
						cbPlan.addItemListener(this);
						cargaEnProgreso=false;
					}
				}		
			}
		});
		cbPlan.setEditable(false);
		modeloComboPlan = new DefaultComboBoxModel<String>();
		cbPlan.setModel(modeloComboPlan);
		cargaComboPlan();
		
		JLabel lblCdigoDeMateria = new JLabel("Plan:");
		panelNorte.add(lblCdigoDeMateria);
		panelNorte.add(cbPlan);
	
		JLabel lblNewLabel = new JLabel("     ");
		panelNorte.add(lblNewLabel);
		
		JLabel lblLlamado = new JLabel("Llamado:");
		panelNorte.add(lblLlamado);
		
		txtLlamado = new JTextField();
		panelNorte.add(txtLlamado);
		txtLlamado.setColumns(10);
		
		JLabel lblFecha = new JLabel("Fecha:");
		panelNorte.add(lblFecha);
		
		txtFecha = new JTextField();
		panelNorte.add(txtFecha);
		txtFecha.setColumns(10);
		
		JLabel lblSituacin = new JLabel("Situación:");
		panelNorte.add(lblSituacin);
		
		txtSituacion = new JTextField();
		panelNorte.add(txtSituacion);
		txtSituacion.setColumns(10);
		
		JLabel labelTurno = new JLabel("Turno:");
		panelNorte.add(labelTurno);
		
		txtTurno = new JTextField();
		panelNorte.add(txtTurno);
		txtTurno.setColumns(10);
		
		JLabel label_3 = new JLabel("");
		panelNorte.add(label_3);
		
		JPanel panelSur = new JPanel();
		panelSur.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panelSur, BorderLayout.SOUTH);
		
		btnCreaMesa = new JButton("Crear mesa");
		btnCreaMesa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				altaMesa(modeloComboMaterias);
			}
		});
		
		panelSur.add(btnCreaMesa);
		
		btnArmaMesa = new JButton("Armar mesa");
		btnArmaMesa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modeloLista.clear();
				modeloActa.clear();
				codMat=(String)cbMaterias.getSelectedItem();
				int btn=3,ventana=2;
				habilita(false,false,false,false,true,false,false,true);
				mostrarMesasCargadas(btn,codMat,ventana);		
			}
		});
		panelSur.add(btnArmaMesa);
		
		btnActaVolante = new JButton("Acta volante");
		btnActaVolante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * lo saco de aca porque cuando elimino de una mesa cuenta los que hay 
				 * mas lo que quedan y nunca deja sacar
				 */
			//	if ((cantidadAnotados()+modeloActa.size())<3){
					generarActaVolante();
				//} else {
				//	JOptionPane.showMessageDialog(null, "Debe seleccionar hasta 30 alumnos como máximo",
				//			"Atención!!",JOptionPane.INFORMATION_MESSAGE);
			//	}
			}
		});
		
		btnModificarMesa = new JButton("Modificar mesa");
		btnModificarMesa.setToolTipText("");
		btnModificarMesa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object opciones[]={"Agregar","Quitar","Cancelar"};
				int respuesta=JOptionPane.showOptionDialog(null, "Elija la opción","Agregar o quitar alumnos de mesas" , 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,null, opciones, null);
				
				if (respuesta == 0)
				{
					agregaAlumnosMesa();
				}
				if (respuesta == 1){
					quita=true;
					quitaAlumnosMesa();
				}
				
				
			}
		});
		
		btnAlCondicionales = new JButton("Al. condicionales");
		btnAlCondicionales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				condicional=true;
				agregarAlumnosCondicionales();
				//condicional=false;
			}
		});
		panelSur.add(btnAlCondicionales);
		panelSur.add(btnModificarMesa);
		panelSur.add(btnActaVolante);
		
		btnImprimeActa = new JButton("Imprime Acta");
		btnImprimeActa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				imprimirActa();
			}
		});
		panelSur.add(btnImprimeActa);
		
		btnNotasMesa = new JButton("Notas mesa");
		btnNotasMesa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pasarNotasAlumnomesa();
			}
		});
		panelSur.add(btnNotasMesa);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * llamar al metodo limpiar y borrar fecha, llamado,situacion
				 * los 2 jlist
				 */
				limpiar();
			}
		});
		panelSur.add(btnCancelar);
		
		JLabel label_2 = new JLabel("                   ");
		panelSur.add(label_2);
		
		JPanel panelCentro = new JPanel();
		contentPane.add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setToolTipText("");
		scrollPane.setBounds(0, 5, 417, 274);
		
		panelCentro.add(scrollPane);
		
		modeloLista = new DefaultListModel<String>();
		listaAlFinal = new JList<String>();
		listaAlFinal.setToolTipText("Fecha de cursada   Situación  DNI  Apellido y nombre");
		listaAlFinal.setForeground(Color.BLUE);
		listaAlFinal.setBackground(new Color(255, 250, 240));
		listaAlFinal.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(listaAlFinal);
		
		JButton btnPasar = new JButton(">");
		btnPasar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pasarAlumno();
			}
		});
		btnPasar.setBounds(437, 85, 41, 23);
		panelCentro.add(btnPasar);
		
		JButton btnTraer = new JButton("<");
		btnTraer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				traerAlumno();
			}
		});
		btnTraer.setBounds(437, 156, 41, 23);
		panelCentro.add(btnTraer);
		
		scrollPaneActa = new JScrollPane();
		scrollPaneActa.setBounds(505, 5, 427, 274);
		panelCentro.add(scrollPaneActa);
		
		modeloActa = new DefaultListModel<String>();
		listaActa = new JList<String>();
		listaActa.setForeground(Color.BLACK);
		listaActa.setBackground(new Color(255,250,240));
		scrollPaneActa.setViewportView(listaActa);		
	}
	

	protected int cantidadAnotados() {
		AlumnomesaDAO miAlumnomesaDAO = new AlumnomesaDAO();	
		return miAlumnomesaDAO.contarAnotadosMesa(codigoMesa);
	}

	/**
	 * Carga en un Hashmap(materiaPlan) todas los codigos de materias con su plan
	 * Carga en un Hashmap(materiaNom) todas los codigos de materias con su nombre
	 * @param materiasCache2
	 */
	private void cargaMateriasCache(Map<String, String> materiaPlan, Map<String, String> materiaNom) {
		try {
			Conexion conex=new Conexion();
			
			String consulta = "SELECT codmat, plan, matnom from materia order by matnom";
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet res = estatuto.executeQuery(consulta);
			
			while(res.next()) {
			
				materiaPlan.put(res.getString(1), res.getString(2));
				materiaNom.put(res.getString(1), res.getString(3));
			}
			res.close();
			estatuto.close();
			conex.desconectar();
			
		}catch (Exception e){
			e.printStackTrace();
		}		
	}

	protected void agregarAlumnosCondicionales() {
		modifica=true;
		modeloLista.clear();
		modeloActa.clear();
		codMat=(String)cbMaterias.getSelectedItem();
		int btn=4,ventana=8;
		habilita(false,false,false,false,true,false,false,true);
		mostrarMesasCargadas(btn,codMat,ventana);
		
	}

	protected void quitaAlumnosMesa() {
		modifica=true;
		modeloLista.clear();
		modeloActa.clear();
		codMat=(String)cbMaterias.getSelectedItem();
		int btn=4,ventana=7;
		habilita(false,false,false,false,true,false,false,true);
		mostrarMesasCargadas(btn,codMat,ventana);
		
	}
/**
 * Agrega alumnos a una mesa que ya estaba cerrada
 * Hay que verificaar que no superen los 30 alumnos
 */
	protected void agregaAlumnosMesa() {
		modifica=true;
		modeloLista.clear();
		modeloActa.clear();
		codMat=(String)cbMaterias.getSelectedItem();
		int btn=4,ventana=2;
		habilita(false,false,false,false,true,false,false,true);
		mostrarMesasCargadas(btn,codMat,ventana);
		
	}

	/**
	 * Muestra la VentanaMesaBuscar
	 * para elegir una mesa y luego imprimir el acta volante
	 * solo muestra las que están cerradas y libro y folio == 0
	 * 
	 */
	protected void imprimirActa() {
		codMat=(String)cbMaterias.getSelectedItem();
		int btn=5;  // btn == 5 para que seleccione mesas por codigo de materia pero que esten cerradas
					// y ademas con libro y folio igual a 0
		int ventana=6; // para que llame al reporte
		
		miCoordinador.mostrarVentanaMesaBuscar(btn, codMat,ventana);
	}

	/**
	 * Muestra la VentanaMesaBuscar
	 * para elegir una mesa y luego guardar las notas de los alumnos de esa mesa
	 * 
	 */
	protected void pasarNotasAlumnomesa() {
		codMat=(String)cbMaterias.getSelectedItem();
		int btn=4;  // btn == 4 para que seleccione mesas por codigo de materia pero que esten cerradas
		int ventana=5;
		
		miCoordinador.mostrarVentanaMesaBuscar(btn, codMat,ventana);
	}
	
	protected void generarActaVolante() {
		String fila,ape;
		int doc;
		
		if(quita){
			// aca elimina alumnos de la mesa
			quita=false;
			if (modeloLista.size()>0){
				for (int i=0; i<modeloLista.size();i++){
					fila=modeloLista.get(i);
					doc=Integer.valueOf(fila.substring(0, 8));
			
					AlumnomesaDAO miAlumnoMesaDAO = new AlumnomesaDAO();
					miAlumnoMesaDAO.bajaRegistroAlumnoMesa(doc,codMesa);
				}
				JOptionPane.showMessageDialog(null, " La Mesa se modificó correctamente ","Información",JOptionPane.INFORMATION_MESSAGE);
				limpiar();
			}else
				JOptionPane.showMessageDialog(null, "Debe seleccionar alumnos para quitar de la mesa","Error",JOptionPane.ERROR_MESSAGE);	
		}else{
		
			//if (modeloActa.size()>0){
			if ((cantidadAnotados()+modeloActa.size()>0) && (cantidadAnotados()+modeloActa.size()<3)){
				for (int i=0; i< modeloActa.size();i++){
					fila= modeloActa.get(i);
					doc=Integer.valueOf(fila.substring(15, 23));
					ape=fila.substring(25,(fila.length()-2));

					CalculaAi cai = new CalculaAi();
					int numAi = cai.calculaIdAlumnoMesa();
					AlumnomesaDAO miAlumnoMesaDAO = new AlumnomesaDAO();
					miAlumnoMesaDAO.altaRegistroAlumnoMesa(numAi,doc,codMesa,condicional);
					//condicional=false;
				}
				if (!modifica){
					MesaDAO miMesa=new MesaDAO();
					miMesa.cerrarMesa(codMesa);
					limpiar();
				}else{
					JOptionPane.showMessageDialog(null, " La Mesa se modificó correctamente ","Información",JOptionPane.INFORMATION_MESSAGE);
					limpiar();
				}		
			}
			else
				if (modeloActa.size()<=0)
				JOptionPane.showMessageDialog(null, "Debe seleccionar alumnos para la mesa","Error",JOptionPane.ERROR_MESSAGE);	
				else
					JOptionPane.showMessageDialog(null, "Debe seleccionar hasta 30 alumnos como máximo",
						"Atención!!",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	protected void traerAlumno() {
		modeloLista.addElement(listaActa.getSelectedValue());
		listaAlFinal.setModel(modeloLista);
		modeloActa.removeElement(listaActa.getSelectedValue());
	}

	protected void pasarAlumno() {
		
		List<String> seleccion =listaAlFinal.getSelectedValuesList();
		Iterator i=seleccion.iterator();
		int a =0;
		while (i.hasNext()){
			modeloActa.addElement((String) i.next());
			modeloLista.removeElement(seleccion.get(a));
			a++;
		}
		listaActa.setModel(modeloActa);
	}

	protected void mostrarMesasCargadas(int btn,String codMat, int ventana) {
	
		miCoordinador.mostrarVentanaMesaBuscar(btn, codMat,ventana);
	}

	protected void altaMesa(DefaultComboBoxModel<String> modeloComboMaterias) {
		miCoordinador.mostrarVentanaMesa(modeloComboMaterias);
	}
		
	public void limpiar() {
	cbMaterias.setSelectedItem(0);
	txtFecha.setText("");
	txtLlamado.setText("");
	txtSituacion.setText("");
	modeloActa.clear();
	modeloLista.clear();
	modifica=false;
	condicional=false;
	actualizaComboMaterias();
	
	habilita(true,true,true,true,false,true,true,true);
}

	private void actualizaComboMaterias() {
		try{
			
			Conexion conex = new Conexion();
			String plan=(String) cbPlan.getSelectedItem();
			ResultSet resMat = null;
			
			String consulta = "SELECT codmat from materia where plan=? order by anio";
			PreparedStatement estatutoMat = conex.getConnection().prepareStatement(consulta);
			estatutoMat.setString(1,plan);
			
		if (resMat != null) {	
			//cbMaterias.removeAllItems();
			while (resMat.next()){
				
				modeloComboMaterias.addElement((String) resMat.getObject(1));
			}
			resMat.close();
			estatutoMat.close();
			conex.desconectar();
		}
		}		catch (SQLException e){
					JOptionPane.showMessageDialog(null, "Error al consultar materias","Error",JOptionPane.ERROR_MESSAGE);
		}

		
	}

	protected void cambiarMateriasdelCombo() {
		/*
		 * Este método actualiza el combo de las materias cuando se cambia un plan desde el otro combo
		 */
		try {
		    String plan = (String) cbPlan.getSelectedItem();
		    
		    // Crear una lista para almacenar los códigos de materia que coinciden con el plan seleccionado
		    List<String> codigosMateria = new ArrayList<>();

		    // Obtener los códigos de materia que coinciden con el plan y agregarlos a la lista
		    for (String codigo : materiaPlan.keySet()) {
		        String planMat = materiaPlan.get(codigo);
		        if (planMat.equals(plan)) {
		            codigosMateria.add(codigo);
		        }
		    }

		    // Ordenar la lista alfabéticamente
		    Collections.sort(codigosMateria);

		    // Agregar los códigos de materia ordenados al JComboBox
		    for (String codigo : codigosMateria) {
		        cbMaterias.addItem(codigo);
		    }
		} catch (Exception e) {
		    JOptionPane.showMessageDialog(null, "Error al consultar materias", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	public void muestraMesa(MesaVO miMesaVO) {
		
		txtLlamado.setText(String.valueOf(miMesaVO.getMesallamado()));
		txtFecha.setText(miMesaVO.getMesafecha());		
		txtSituacion.setText(miMesaVO.getMesasituacion());
		codMesa=miMesaVO.getCodmesa();
		
		if (miMesaVO.getTurno().equals("TM"))
			txtTurno.setText("Mañana");
		if (miMesaVO.getTurno().equals("TT"))
			txtTurno.setText("Tarde");
		if (miMesaVO.getTurno().equals("TV"))
			txtTurno.setText("Vespertino");
		
		codigoMesa=miMesaVO.getCodmesa();
		listarAlumnos(miMesaVO);
		
	}
	
	private void cargaComboPlan() {
		// TODO Auto-generated method stub
		// Esto es para que al crear el formulario se cargue el combobox de planes de estudio
				try{
					Conexion conex = new Conexion();
					ResultSet resMat = null;
					Statement estatutoMat = conex.getConnection().createStatement();
					resMat = estatutoMat.executeQuery("SELECT distinct plan from basegada.materia order by plan desc");
					
					while (resMat.next()){
						
						modeloComboPlan.addElement((String) resMat.getObject(1));
					}
					resMat.close();
					estatutoMat.close();
					conex.desconectar();
				}		catch (SQLException e){
							JOptionPane.showMessageDialog(null, "Error al consultar materias","Error",JOptionPane.ERROR_MESSAGE);
				}
	}
	
	private void listarAlumnos(MesaVO miMesaVO) {
		modeloLista.clear();
		// saco todas las correlativas
		//MateriaDAO miMateriaDAO = new MateriaDAO();
		MateriaCorrelativaDAO miCorrelativaDAO = new MateriaCorrelativaDAO();
		String codMateria = miMesaVO.getCodmat();
		String situacion = miMesaVO.getMesasituacion();
		String situ="";
		if (situacion.equals("regulares"))
			situ="A FINAL";
		else
			situ="Recursa";
		//String vecCorr[] = miMateriaDAO.obtenerCorrelativas(codMateria);
		// después borrar el método
		String vecCorr[] = miCorrelativaDAO.obtenerCorrelativas(codMateria);
		
		for (int i=0;i<vecCorr.length;i++){
			System.out.println("codigo de materia correlativa "+vecCorr[i]);
		}
		
		String turno =miMesaVO.getTurno();
		
		AlumnomateriaDAO miAlumnoMateriaDAO = new AlumnomateriaDAO();	
	
		miAlumnoMateriaDAO.buscarAlumnosParaMesa(modeloLista,codMateria,vecCorr,situ,turno);
		listaAlFinal.setModel(modeloLista);
	
	}

	public void muestraMesaQuitar(MesaVO miMesaVO) {
		txtLlamado.setText(String.valueOf(miMesaVO.getMesallamado()));
		txtFecha.setText(miMesaVO.getMesafecha());		
		txtSituacion.setText(miMesaVO.getMesasituacion());
		codMesa=miMesaVO.getCodmesa();
		listarAlumnosQuitar(miMesaVO);
	}

	private void listarAlumnosQuitar(MesaVO miMesaVO) {
		modeloLista.clear();
		modeloActa.clear();
		int codMesa=miMesaVO.getCodmesa();
		AlumnomesaDAO miAlumnoMesaDAO = new AlumnomesaDAO();
		miAlumnoMesaDAO.buscarAlumnosMesaQuitar(modeloActa,codMesa);
		listaActa.setModel(modeloActa);
	}
	
	private void habilita(boolean btnCrea, boolean btnArma, boolean btnCondicion, boolean btnModificar, boolean btnActa, boolean btnImprime, boolean btnNotas, boolean btnCancela){
		btnCreaMesa.setEnabled(btnCrea);
		btnArmaMesa.setEnabled(btnArma);
		btnAlCondicionales.setEnabled(btnCondicion);
		btnModificarMesa.setEnabled(btnModificar);
		btnActaVolante.setEnabled(btnActa);
		btnImprimeActa.setEnabled(btnImprime);
		btnNotasMesa.setEnabled(btnNotas);
		btnCancelar.setEnabled(btnCancela);
	}
	
	private void tituloConNombreMateria(){
		String codigo=(String) cbMaterias.getSelectedItem();
		setTitle("Gestión de mesas de examen de:    "+materiaNom.get(codigo));
	}

	public void muestraMesaCondicional(MesaVO miMesaVO) {
			txtLlamado.setText(String.valueOf(miMesaVO.getMesallamado()));
			txtFecha.setText(miMesaVO.getMesafecha());		
			txtSituacion.setText(miMesaVO.getMesasituacion());
			codMesa=miMesaVO.getCodmesa();
			listarAlumnosCondicionales(miMesaVO);
		}
		
		private void listarAlumnosCondicionales(MesaVO miMesaVO) {
			modeloLista.clear();
			// saco todas las correlativas
			//MateriaDAO miMateriaDAO = new MateriaDAO();
			MateriaCorrelativaDAO miCorrelativaDAO = new MateriaCorrelativaDAO();
			String codMateria = miMesaVO.getCodmat();
			String situacion = miMesaVO.getMesasituacion();
			String situ="";
			if (situacion.equals("regulares"))
				situ="A FINAL";
			else
				situ="Recursa";
			//String vecCorr[] = miMateriaDAO.obtenerCorrelativas(codMateria);
			// después borrar el método
			String vecCorr[] = miCorrelativaDAO.obtenerCorrelativas(codMateria);
			String turno = miMesaVO.getTurno();
			
			AlumnomateriaDAO miAlumnoMateriaDAO = new AlumnomateriaDAO();	
		
			miAlumnoMateriaDAO.buscarAlumnosCondicionalesParaMesa(modeloLista,codMateria,vecCorr,situ,turno);
			
			listaAlFinal.setModel(modeloLista);
			
		}
		
	}

