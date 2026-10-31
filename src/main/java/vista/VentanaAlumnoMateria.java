package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Coordinador;
import modelo.AlumnoVO;
import modelo.AlumnomateriaDAO;
import modelo.AlumnomateriaVO;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.awt.event.ItemEvent;
import com.toedter.calendar.JYearChooser;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class VentanaAlumnoMateria extends JFrame {

	private JPanel contentPane;
	private JTextField txtAlApyNom;
	private JTextField txtDni;
	private JTextField txtNota1;
	private JTextField txtNota2;
	private JTextField txtRecu1;
	private JTextField txtRecu2;
	private JTextField txtRecu3;
	private JComboBox<String> cbSituacion;
	private JComboBox<String> cbNotaFinal;
	private JTextField txtLibro;
	private JTextField txtFolio;
	private JTextField txtFecha;
	private Coordinador miCoordinador;
	
	
	private int numAiBaja;
	private int accion; //si es 1 es porque presione boton agregar
	private int numAi;
	private int btn=0;  //si es 2 es porque es una busqueda parcial del apellido
	private int ventana; //si es 1 muestra la VentanaAlumno
	                     //si es 2 muestra la VentanaAlumnoMateria
	private int dniAlum;
	
	private JButton btnBuscar;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JButton btnAgregar;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnReporte;
	private JLabel label_2;
	private JComboBox<String> cbMateria;
	//static DefaultComboBoxModel<String> modeloComboMaterias;
	DefaultComboBoxModel<String> modeloComboMaterias;
	private JLabel lblAoDeCursada;
	private JYearChooser selectorAnio;
	private JLabel lblCantidad;
	private JLabel lbl1;
	private JRadioButton rbTm;
	private JRadioButton rbTt;
	private JRadioButton rbTv;
	private JLabel label;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	AlumnomateriaDAO miAlumnoMateriaDAO = new AlumnomateriaDAO();

	private String txDni;
	
	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAlumnoMateria frame = new VentanaAlumnoMateria();					
					frame.setVisible(true);
					frame.limpiar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

*/
	/**
	 * Create the frame.
	 */
	public VentanaAlumnoMateria() {
		setTitle("Materias cursadas por alumno");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 709, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelEncabezado = new JPanel();
		panelEncabezado.setBorder(new TitledBorder(null, "Alumno", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panelEncabezado, BorderLayout.NORTH);
		panelEncabezado.setLayout(new GridLayout(3, 2, 10, 10));
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setHorizontalAlignment(SwingConstants.RIGHT);
		panelEncabezado.add(lblApellido);
		
		txtAlApyNom = new JTextField();
		txtAlApyNom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscarParcialApellido();
			}
		});
		panelEncabezado.add(txtAlApyNom);
		txtAlApyNom.setColumns(10);
		
		lblCantidad = new JLabel("");
		panelEncabezado.add(lblCantidad);
		
		JLabel lblDni = new JLabel("Dni:");
		lblDni.setHorizontalAlignment(SwingConstants.RIGHT);
		panelEncabezado.add(lblDni);
		
		txtDni = new JTextField(" ");
		panelEncabezado.add(txtDni);
		txtDni.setColumns(10);
		
		lbl1 = new JLabel("");
		panelEncabezado.add(lbl1);
		
		JLabel lblNombreMat = new JLabel("");
		panelEncabezado.add(lblNombreMat);
		
		JPanel panelBotones = new JPanel();
		contentPane.add(panelBotones, BorderLayout.SOUTH);
		panelBotones.setLayout(new FlowLayout());
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			guardarAlumnoMateria();	
			}
		});
		panelBotones.add(btnGuardar);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				agregarAlumnoMateria();
			}
		});
		panelBotones.add(btnAgregar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modificarAlumnoMateria();
			}
		});
		panelBotones.add(btnModificar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eliminarAlumnoMateria();
			}
		});
		panelBotones.add(btnEliminar);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscarParcialApellido();
			}
		});
		panelBotones.add(btnBuscar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpiar();
				//volver();
			}
		});
		panelBotones.add(btnCancelar);
		
		btnReporte = new JButton("Reporte");
		btnReporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panelBotones.add(btnReporte);
		
		JPanel panelNotas = new JPanel();
		panelNotas.setBorder(new TitledBorder(null, "Situaci\u00F3n acad\u00E9mica", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panelNotas, BorderLayout.CENTER);
		panelNotas.setLayout(new GridLayout(0, 4, 10, 10));
		
		label_2 = new JLabel("Codigo de Materia:");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNotas.add(label_2);
		
		cbMateria = new JComboBox<String>();
		
		cbMateria.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				//int dni=Integer.valueOf(txtDni.getText());
				verMateriaDelComboSinDni(miAlumnoMateriaDAO);
			}
		});
		cbMateria.setEditable(false);
		//modeloComboMaterias  = new DefaultComboBoxModel<String>(new String [] {});
		modeloComboMaterias  = new DefaultComboBoxModel<String>();
		cbMateria.setModel(modeloComboMaterias);
		panelNotas.add(cbMateria);
		
		lblAoDeCursada = new JLabel("Año de cursada:");
		lblAoDeCursada.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNotas.add(lblAoDeCursada);
		
		Calendar cal = Calendar.getInstance();
		int anioActual = cal.get(Calendar.YEAR);
		
		selectorAnio = new JYearChooser();
		selectorAnio.setValue(anioActual);
		panelNotas.add(selectorAnio);
		
		JLabel lblNotaParcial = new JLabel("Nota 1° parcial:");
		lblNotaParcial.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNotas.add(lblNotaParcial);
		
		txtNota1 = new JTextField();
		panelNotas.add(txtNota1);
		txtNota1.setColumns(10);
		
		JLabel lblRecuperatorioParcial = new JLabel("Recuperatorio 1° Parcial:");
		lblRecuperatorioParcial.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNotas.add(lblRecuperatorioParcial);
		
		txtRecu1 = new JTextField();
		panelNotas.add(txtRecu1);
		txtRecu1.setColumns(10);
		
		JLabel lblNotaParcial_1 = new JLabel("Nota 2° parcial:");
		lblNotaParcial_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNotas.add(lblNotaParcial_1);
		
		txtNota2 = new JTextField();
		panelNotas.add(txtNota2);
		txtNota2.setColumns(10);
		
		JLabel lblRecuperatorioParcial_1 = new JLabel("Recuperatorio 2° Parcial:");
		lblRecuperatorioParcial_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNotas.add(lblRecuperatorioParcial_1);
		
		txtRecu2 = new JTextField();
		panelNotas.add(txtRecu2);
		txtRecu2.setColumns(10);
		
		JLabel lblRecuperatorio = new JLabel("3° Recuperatorio:");
		lblRecuperatorio.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNotas.add(lblRecuperatorio);
		
		txtRecu3 = new JTextField();
		panelNotas.add(txtRecu3);
		txtRecu3.setColumns(10);
		
		JLabel lblSituacin = new JLabel("Situación:");
		lblSituacin.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNotas.add(lblSituacin);
		
		cbSituacion = new JComboBox<String>();
		cbSituacion.setModel(new DefaultComboBoxModel<String>(new String[] {"", "A FINAL", "RECURSA"}));
		panelNotas.add(cbSituacion);
		
		JLabel lblNotaFinal = new JLabel("Nota Final:");
		lblNotaFinal.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNotas.add(lblNotaFinal);
		
		cbNotaFinal = new JComboBox<String>();
		cbNotaFinal.setModel(new DefaultComboBoxModel<String>(new String[] {"", "10", "9", "8", "7", "6", "5", "4"}));
		panelNotas.add(cbNotaFinal);
		
		JLabel lblLibro = new JLabel("Libro:");
		lblLibro.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNotas.add(lblLibro);
		
		txtLibro = new JTextField();
		panelNotas.add(txtLibro);
		txtLibro.setColumns(10);
		
		JLabel lblFolio = new JLabel("Folio:");
		lblFolio.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNotas.add(lblFolio);
		
		txtFolio = new JTextField();
		panelNotas.add(txtFolio);
		txtFolio.setColumns(10);
		
		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNotas.add(lblFecha);
		
		txtFecha = new JTextField();
		panelNotas.add(txtFecha);
		txtFecha.setColumns(10);
		
		label = new JLabel("");
		panelNotas.add(label);
		
		rbTm = new JRadioButton("Turno mañana");
		buttonGroup.add(rbTm);
		panelNotas.add(rbTm);
		
		rbTt = new JRadioButton("Turno tarde");
		buttonGroup.add(rbTt);
		panelNotas.add(rbTt);
		
		rbTv = new JRadioButton("Turno vespertino");
		buttonGroup.add(rbTv);
		panelNotas.add(rbTv);
	}
	
	protected void eliminarAlumnoMateria() {
		System.out.println("entre al eliminarAlumnoMateria");
		int num=cbMateria.getItemCount();
		if (cbMateria.getItemCount()!=0)
		{
			System.out.println("tiene    "+num);
			int respuesta=JOptionPane.showConfirmDialog(null, "Está seguro de eliminar esa Materia? El alumno dejará de cursarla", "Confirmación", 
				JOptionPane.YES_NO_OPTION);		
		
				if (respuesta == JOptionPane.YES_NO_OPTION)
					{
					System.out.println("el autoincremental es   :"+numAiBaja);
					miCoordinador.eliminarAlumnoMateria(numAiBaja);
					limpiar();
					}
		}
		else{
			System.out.println("tiene   "+num);
			JOptionPane.showMessageDialog(null, "No tiene materias asignadas", "Información",JOptionPane.WARNING_MESSAGE);
		}
	}
	/**
	 * Permite completar notas de parciales y finales
	 */
	protected void modificarAlumnoMateria() {
		accion=2;
		//int num=cbMateria.getItemCount();
		if (cbMateria.getItemCount()!=0){
			habilita(false, false, false,true, true, true, true, true,true, true,true,true,true, true, true, true, true, true,
					false, false, false, false, true,false);
		}
		else{
			JOptionPane.showMessageDialog(null, "No tiene materias asignadas", "Información",JOptionPane.WARNING_MESSAGE);
		}
	}
	/**
	 * Registra una nueva relación Alumno-Materia
	 */
	//@SuppressWarnings("null")
	protected void guardarAlumnoMateria() {
	
		try{
			AlumnomateriaVO miAlumnomateriaVO = new AlumnomateriaVO();
			miAlumnomateriaVO.setCodalumnomateria(numAi);			
			miAlumnomateriaVO.setAldni2(Integer.valueOf(txtDni.getText()));
			miAlumnomateriaVO.setCodmat2((String) cbMateria.getSelectedItem());
			miAlumnomateriaVO.setFechaDeCursada(selectorAnio.getValue());
			miAlumnomateriaVO.setParcial1(txtNota1.getText());
			miAlumnomateriaVO.setParcial2(txtNota2.getText());
			miAlumnomateriaVO.setRecup1(txtRecu1.getText());
			miAlumnomateriaVO.setRecup2(txtRecu2.getText());
			miAlumnomateriaVO.setRecup3(txtRecu3.getText());
			miAlumnomateriaVO.setSituacion((String) cbSituacion.getSelectedItem());
			if (!cbNotaFinal.getSelectedItem().equals("")){
				miAlumnomateriaVO.setNotafinal(Integer.valueOf((String) cbNotaFinal.getSelectedItem()));
			}
			if (!txtLibro.getText().isEmpty()){
			miAlumnomateriaVO.setLibro(Integer.valueOf(txtLibro.getText()));
			}
			if (!txtFolio.getText().isEmpty()){
			miAlumnomateriaVO.setFolio(Integer.valueOf(txtFolio.getText()));
			}
			if (!txtFecha.getText().isEmpty()){
			miAlumnomateriaVO.setFecha(txtFecha.getText());
			}
			if (rbTm.isSelected())
				miAlumnomateriaVO.setTurno("TM");
			if (rbTt.isSelected())
				miAlumnomateriaVO.setTurno("TT");
			if (rbTv.isSelected())
				miAlumnomateriaVO.setTurno("TV");
			
			if (accion!=1){
				miCoordinador.modificarAlumnoMateria(miAlumnomateriaVO);
				limpiar();
			}
		}catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error en el Ingreso de Datos que faltan", "Error",
					JOptionPane.ERROR_MESSAGE);
		
			limpiar();
		}
	}

	/*protected void volver() {
		//this.dispose();	
		limpiar();
	}*/

	/*
	 * Probar hacer 2 métodos verMateriaDelComboConDni y verMateriaDelComboSinDni
	 */
	protected void verMateriaDelCombo(AlumnomateriaDAO miAlumnoMateriaDAO, int dniAlum) {
		
		String cod = (String) cbMateria.getSelectedItem();
		System.out.println("Ver materia del combo en VentanaAlumnomateria el codigo de la mat es: "+cod);
		try{
		//txDni=txtDni.getText();	
		System.out.println("Huracan :"+dniAlum);
		//int dni=Integer.valueOf(txDni.trim());
		//System.out.println("DNI en verMateriadelCombo  VentanaAlumnoMateria "+dni);
		//AlumnomateriaDAO miAlumnoMateria2 = new AlumnomateriaDAO();
		//AlumnomateriaVO alu;
		
		if (accion !=1){	
		//alu=miAlumnoMateria2.buscarMateriaAlumno(dni, cod);
			//alu=miAlumnoMateriaDAO.buscarMateriaAlumno(dni, cod);
		//System.out.println("AlumnoMateriaVO: "+alu.getAldni2()+"   turno "+alu.getTurno());
		
			muestraAlumnoMateria(miAlumnoMateriaDAO.buscarMateriaAlumno(dniAlum, cod));
		//muestraAlumnoMateria(alu);
		
		}
		
		}catch (NumberFormatException e){
			System.out.println("error al intentar mostrar la materia de hoy");
			e.printStackTrace();
		}
	}

protected void verMateriaDelComboSinDni(AlumnomateriaDAO miAlumnoMateriaDAO) {
		
		String cod = (String) cbMateria.getSelectedItem();
		System.out.println("Ver materia del combo en VentanaAlumnomateria el codigo de la mat es: "+cod);
		try{
		//txDni=txtDni.getText();	
		System.out.println("Huracan :"+dniAlum);
		//int dni=Integer.valueOf(txDni.trim());
		//System.out.println("DNI en verMateriadelCombo  VentanaAlumnoMateria "+dni);
		//AlumnomateriaDAO miAlumnoMateria2 = new AlumnomateriaDAO();
		//AlumnomateriaVO alu;
		
		if (accion !=1){	
		//alu=miAlumnoMateria2.buscarMateriaAlumno(dni, cod);
			//alu=miAlumnoMateriaDAO.buscarMateriaAlumno(dni, cod);
		//System.out.println("AlumnoMateriaVO: "+alu.getAldni2()+"   turno "+alu.getTurno());
		
			muestraAlumnoMateria(miAlumnoMateriaDAO.buscarMateriaAlumno(dniAlum, cod));
		//muestraAlumnoMateria(alu);
		
		}
		
		}catch (NumberFormatException e){
			System.out.println("error al intentar mostrar la materia de hoy");
			e.printStackTrace();
		}
	}
	
	private void muestraAlumnoMateria(AlumnomateriaVO miAlumnomateria) {
		if (miAlumnomateria != null) {
		numAiBaja=miAlumnomateria.getCodalumnomateria();
		selectorAnio.setValue(miAlumnomateria.getFechaDeCursada());
		txtNota1.setText(miAlumnomateria.getParcial1());
		txtNota2.setText(miAlumnomateria.getParcial2());
		txtRecu1.setText(miAlumnomateria.getRecup1());
		txtRecu2.setText(miAlumnomateria.getRecup2());
		txtRecu3.setText(miAlumnomateria.getRecup3());
		cbSituacion.setSelectedItem(miAlumnomateria.getSituacion());
		
		System.out.println("El radioboton: "+miAlumnomateria.getTurno());
		
		if (miAlumnomateria.getTurno().equals("TM"))
			rbTm.setSelected(true);
		
		if (miAlumnomateria.getTurno().equals("TT"))
			rbTt.setSelected(true);
		
		if (miAlumnomateria.getTurno().equals("TV"))
			rbTv.setSelected(true);
		
		if (miAlumnomateria.getNotafinal()==0)
		{
			cbNotaFinal.setSelectedIndex(0);
			
		}
		else{
			cbNotaFinal.setSelectedItem(String.valueOf(miAlumnomateria.getNotafinal()));
		}
		if(miAlumnomateria.getLibro()==0)
			txtLibro.setText("");
		else
			txtLibro.setText(String.valueOf(miAlumnomateria.getLibro()));
		if (miAlumnomateria.getFolio()==0)
			txtFolio.setText("");
		else
			txtFolio.setText(String.valueOf(miAlumnomateria.getFolio()));
		txtFecha.setText(miAlumnomateria.getFecha());
		}
	}
	/**
	 * Agrega un registro con la materia que va a cursar un alumno
	 */
	protected void agregarAlumnoMateria() {
		accion=1;
		habilita(true, false, false,false, false, false,false, false, false,false, false,false,false,false, false,false, false, false,true, false, 
				false, false, true, false);
		buscarParcialApellido();
	} 

	protected void buscarParcialApellido() {
		
		    btn=2;       //busqueda parcial del apellido
		    ventana=2;   //muestra la VentanaAlumnoMateria cuando trae los datos de la base
			String ape=txtAlApyNom.getText();
			int doc=0;
			miCoordinador.mostrarVentanaAlumnoBuscar(btn,doc,ape,ventana);
	}
	
	public void limpiar() {
		
			txtAlApyNom.setText("");
			txtDni.setText("");
		
			cbMateria.removeAllItems();
			txtNota1.setText("");
			txtRecu1.setText("");
			txtNota2.setText("");
			txtRecu2.setText("");
			txtRecu3.setText("");
			cbSituacion.setSelectedIndex(0);
			cbNotaFinal.setSelectedIndex(0);
			txtLibro.setText("");
			txtFolio.setText("");
			txtFecha.setText("");
			btn=0;
			accion=0;
			lblCantidad.setText("");
			
			habilita(true, false, false,false, false, false, false, false,false,false,false, false, false, false, false, false, false, false,true, false,
					false, true, true,	false);
			txtAlApyNom.grabFocus();
	}

	public void habilita(boolean ape, boolean dni, boolean codmat,boolean sele, boolean par1, boolean recu1, boolean par2, boolean recu2,
				boolean recu3, boolean sit, boolean nf, boolean libro, boolean folio,boolean fecha,boolean tm,boolean tt,boolean tv, boolean bGuardar,
				boolean bAgregar, boolean bModificar, boolean bEliminar, boolean bBuscar, boolean bCancelar,boolean bReporte) {
			
			txtAlApyNom.setEditable(ape);
			txtDni.setEditable(dni);
			cbMateria.setEnabled(codmat);
			selectorAnio.setEnabled(sele);
			txtNota1.setEditable(par1);
			txtRecu1.setEditable(recu1);
			txtNota2.setEditable(par2);
			txtRecu2.setEditable(recu2);
			txtRecu3.setEditable(recu3);
			cbSituacion.setEnabled(sit);
			cbNotaFinal.setEnabled(nf);
			txtLibro.setEditable(libro);
			txtFolio.setEditable(folio);
			txtFecha.setEditable(fecha);
			rbTm.setEnabled(tm);
			rbTt.setEnabled(tt);
			rbTv.setEnabled(tv);
			
			btnGuardar.setVisible(bGuardar);
			btnAgregar.setEnabled(bAgregar);
			btnModificar.setEnabled(bModificar);
			btnEliminar.setEnabled(bEliminar);
			btnBuscar.setEnabled(bBuscar);
			btnCancelar.setVisible(bCancelar);
			btnReporte.setEnabled(bReporte);
	}

	public void muestraAlumno(AlumnoVO miAlumno) {
	
			String nomAlum=miAlumno.getAlapynom();
			dniAlum=miAlumno.getAldni();
			
			if (accion==1){
				// porque presione boton agregar entonces va a cargar el combo con las materias que aún no esta cursando
				miCoordinador.mostrarVentanaAlumnoMateriaNueva(dniAlum, nomAlum);
			}else{
				llenaComboMateriasAlumno(modeloComboMaterias,dniAlum,miAlumnoMateriaDAO);
				txtAlApyNom.setText(miAlumno.getAlapynom());
				txtDni.setText(String.valueOf(miAlumno.getAldni()));
				int num=cbMateria.getItemCount();
				lblCantidad.setText("Anotado en "+String.valueOf(num)+" materias");
				habilita(true, false, true, true,true, true, true, true, true, true, true, true, true, true,true,true,true, false,false, true, true,
						false, true, true);
			
				verMateriaDelCombo(miAlumnoMateriaDAO, dniAlum);
			}
			
	}
	/**
	 * Carga el combo con las materias que está cursando el alumno
	 * @param modeloComboMateria
	 * @param dniAlum
	 * @param miAlumnoMateriaDAO 
	 * @param nomAlum
	 */
	public void llenaComboMateriasAlumno(DefaultComboBoxModel<String> modeloComboMateria, int dniAlum, AlumnomateriaDAO miAlumnoMateriaDAO) {
		
		miAlumnoMateriaDAO.cargarComboMateriasAlumno(modeloComboMateria,dniAlum);
	}
	/**
	 * No se usa más, cargaba el combo con las materias que se podía anotar para cursar
	 * Reemplazado por miCoordinador.mostrarVentanaAlumnoMateriaNueva(numAi, dniAlum, nomAlum);
	 * @param modeloComboMateria
	 * @param dniAlum
	 * @param nomAlum
	 */
	public void llenaComboMateriasNuevasAlumno(DefaultComboBoxModel<String> modeloComboMateria, int dniAlum,String nomAlum) {
		miCoordinador.cargarComboMateriasNuevasAlumno(modeloComboMateria, dniAlum, nomAlum);
		
	}

}
