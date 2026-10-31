package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import controlador.Coordinador;
import modelo.CalculaAi;
import modelo.MesaVO;

public class VentanaMesa extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNumLibro;
	private JTextField txtNumFolio;
	private Coordinador miCoordinador;
	private JComboBox<String> cbMaterias;
	private int accion;
	private JDateChooser selectorFecha;
	private JComboBox<String> cbLlamado;
	private JComboBox<String> cbSituacion;
	private JButton btnGuardar;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnCancelar;
	private JButton btnBuscar;
	private JButton btnAgregar;
	
	private int numAi;
	private int codigoDeMesa;
	private JButton btnBuscarXCodigo;
	private JLabel lblNewLabel;
	private JRadioButton rbTm;
	private JRadioButton rbTt;
	private JRadioButton rbTv;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMesa frame = new VentanaMesa();
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
	public VentanaMesa() {
		setTitle("Mesa de examen");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 368);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelNorte = new JPanel();
		contentPane.add(panelNorte, BorderLayout.CENTER);
		panelNorte.setLayout(new GridLayout(7, 3, 5, 5));
		
		JLabel lblCdigoDeMateria = new JLabel("Código de Materia:");
		lblCdigoDeMateria.setHorizontalAlignment(SwingConstants.LEFT);
		panelNorte.add(lblCdigoDeMateria);
		
		cbMaterias = new JComboBox<String>();
		cbMaterias.setEditable(true);
		panelNorte.add(cbMaterias);
		
		btnBuscarXCodigo = new JButton("Buscar x código");
		btnBuscarXCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscarMesasCodigo();
			}
		});
		panelNorte.add(btnBuscarXCodigo);
		
		JLabel lblFechaDeLa = new JLabel("Fecha de la mesa:");
		lblFechaDeLa.setHorizontalAlignment(SwingConstants.LEFT);
		panelNorte.add(lblFechaDeLa);
		
		selectorFecha = new JDateChooser();
		panelNorte.add(selectorFecha);
		
		JLabel label = new JLabel("");
		panelNorte.add(label);
		
		JLabel lblOrdenDeLlamado = new JLabel("Orden de llamado:");
		lblOrdenDeLlamado.setHorizontalAlignment(SwingConstants.LEFT);
		panelNorte.add(lblOrdenDeLlamado);
		
		cbLlamado = new JComboBox<String>();
		cbLlamado.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2"}));
		cbLlamado.setEditable(true);
		panelNorte.add(cbLlamado);
		
		JLabel label_1 = new JLabel("");
		panelNorte.add(label_1);
		
		JLabel lblLibroN = new JLabel("Libro N°:");
		lblLibroN.setHorizontalAlignment(SwingConstants.LEFT);
		panelNorte.add(lblLibroN);
		
		txtNumLibro = new JTextField();
		panelNorte.add(txtNumLibro);
		txtNumLibro.setColumns(10);
		
		JLabel label_2 = new JLabel("");
		panelNorte.add(label_2);
		
		JLabel lblFolioN = new JLabel("Folio N°:");
		lblFolioN.setHorizontalAlignment(SwingConstants.LEFT);
		panelNorte.add(lblFolioN);
		
		txtNumFolio = new JTextField();
		panelNorte.add(txtNumFolio);
		txtNumFolio.setColumns(10);
		
		JLabel label_3 = new JLabel("");
		panelNorte.add(label_3);
		
		JLabel lblSituacinDeAlumnos = new JLabel("Situación de Alumnos:");
		lblSituacinDeAlumnos.setHorizontalAlignment(SwingConstants.LEFT);
		panelNorte.add(lblSituacinDeAlumnos);
		
		cbSituacion = new JComboBox<String>();
		cbSituacion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cbSituacion.setEditable(true);
		cbSituacion.setModel(new DefaultComboBoxModel<String>(new String[] {"regulares", "libres"}));
		panelNorte.add(cbSituacion);
		
		lblNewLabel = new JLabel("");
		panelNorte.add(lblNewLabel);
		
		rbTm = new JRadioButton("Turno mañana");
		buttonGroup.add(rbTm);
		panelNorte.add(rbTm);
		
		rbTt = new JRadioButton("Turno tarde");
		buttonGroup.add(rbTt);
		panelNorte.add(rbTt);
		
		rbTv = new JRadioButton("Turno vespertino");
		buttonGroup.add(rbTv);
		panelNorte.add(rbTv);
		
		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardarMesa();
			}
		});
		panelSur.add(btnGuardar);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				accion = 1;
				agregarMesa();
			}
		});
		panelSur.add(btnAgregar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modificarMesa();
			}
		});
		panelSur.add(btnModificar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eliminarMesa();
			}
		});
		panelSur.add(btnEliminar);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarMesas();
			}
		});
		panelSur.add(btnBuscar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volver();
			}
		});
		panelSur.add(btnCancelar);
	}

	/**
	 * Llama al método eliminarMesa
	 */
	protected void eliminarMesa() {
		int respuesta = JOptionPane.showConfirmDialog(null, "Está seguro de eliminar esa Mesa?", "Confirmación", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			if (miCoordinador != null) {
				miCoordinador.eliminarMesa(codigoDeMesa);
			}
			limpiar();
		}
	}

	/**
	 * Limpia los campos y habilita campos y botones
	 */
	protected void volver() {
		limpiar();	
	}

	/**
	 * Muestra todas las mesas en una ventana (ordenadas por fecha)
	 */
	protected void buscarMesas() {
		int btn = 1, ventana = 1;
		String codMat = null;
		if (miCoordinador != null) {
			miCoordinador.mostrarVentanaMesaBuscar(btn, codMat, ventana);
		}
	}

	/**
	 * Muestra todas las mesas de una misma materia (ordenadas por fecha)
	 */
	protected void buscarMesasCodigo() {
		int btn = 2, ventana = 1;
		String codMat = (String) cbMaterias.getSelectedItem();
		if (miCoordinador != null) {
			miCoordinador.mostrarVentanaMesaBuscar(btn, codMat, ventana);
		}
	}

	/**
	 * Permite modificar fecha, libro, folio y situación de una mesa
	 */
	protected void modificarMesa() {
		accion = 2;
		habilita(false, true, false, true, true, true, false, true, true, true, true, false, false, false, true, true);	
	}

	/**
	 * Registra una mesa
	 */
	protected void guardarMesa() {
		try {
			Date date = selectorFecha.getDate();
			if (date == null) {
				JOptionPane.showMessageDialog(null, "Debe seleccionar una fecha", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Formato estándar SQL
			MesaVO miMesa = new MesaVO();
			miMesa.setCodmesa(numAi);
			miMesa.setCodmat((String) cbMaterias.getSelectedItem());
			miMesa.setMesafecha(sdf.format(date));
			miMesa.setMesallamado(Integer.valueOf((String) cbLlamado.getSelectedItem()));
			
			if (!txtNumLibro.getText().isEmpty()) {
				miMesa.setMesalibro(Integer.valueOf(txtNumLibro.getText()));
			} else {
				miMesa.setMesalibro(0);
			}
			
			if (!txtNumFolio.getText().isEmpty()) {
				miMesa.setMesafolio(Integer.valueOf(txtNumFolio.getText()));
			} else {
				miMesa.setMesafolio(0);
			}
			
			miMesa.setMesasituacion((String) cbSituacion.getSelectedItem());
			miMesa.setMesaarmada("abierta");
			
			if (rbTm.isSelected()) miMesa.setTurno("TM");
			if (rbTt.isSelected()) miMesa.setTurno("TT");
			if (rbTv.isSelected()) miMesa.setTurno("TV");
			
			if (miCoordinador != null) {
				if (accion == 1) {
					miCoordinador.registrarMesa(miMesa);
				} else { 
					miMesa.setCodmesa(codigoDeMesa);
					miCoordinador.modificarMesa(miMesa);
				}
			}
			limpiar();
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error en el Ingreso de Datos", "Error", JOptionPane.ERROR_MESSAGE);
			limpiar();
		}		
	}

	/**
	 * Calcula el autoincremental y habilita campos para crear una mesa nueva
	 */
	protected void agregarMesa() {
		CalculaAi cai = new CalculaAi();
		numAi = cai.calculaIdMesa();
		accion = 1;
		rbTm.setSelected(true);
		habilita(true, true, true, true, true, true, true, true, true, true, true, false, false, false, true, true);		
	}

	/**
	 * Método del Coordinador
	 * @param miCoordinador
	 */
	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;		
	}

	/**
	 * Carga el combo con todas las materias cuando se muestra VentanaMesa
	 * @param modeloComboMaterias
	 */
	public void cargarCombo(DefaultComboBoxModel<String> modeloComboMaterias) {
		cbMaterias.setModel(modeloComboMaterias);
	}

	/**
	 * Limpia los campos de la VentanaMesa y habilita campos y botones
	 */
	public void limpiar() {
		selectorFecha.setCalendar(null);
		cbLlamado.setSelectedIndex(0);
		txtNumLibro.setText("");
		txtNumFolio.setText("");
		cbSituacion.setSelectedIndex(0);
		rbTm.setSelected(false);
		rbTt.setSelected(false);
		rbTv.setSelected(false);

		habilita(true, false, false, false, false, false, true, false, false, false, false, true, false, false, true, true);
	}

	/**
	 * Muestra la mesa seleccionada de la VentanaMesaBuscar
	 * @param miMesaVO
	 */
	public void muestraMesa(MesaVO miMesaVO) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date miDia = new Date();
		GregorianCalendar miGCalendar = new GregorianCalendar();
		
		try {
			if (miMesaVO.getMesafecha() != null) {
				miDia = formato.parse(miMesaVO.getMesafecha());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		miGCalendar.setTime(miDia);
		cbMaterias.setSelectedItem(miMesaVO.getCodmat());
		selectorFecha.setCalendar(miGCalendar);
		cbLlamado.setSelectedItem(String.valueOf(miMesaVO.getMesallamado()));
		
		if (miMesaVO.getMesalibro() == 0) {
			txtNumLibro.setText("");
		} else {
			txtNumLibro.setText(String.valueOf(miMesaVO.getMesalibro()));
		}
			
		if (miMesaVO.getMesafolio() == 0) {
			txtNumFolio.setText("");
		} else {
			txtNumFolio.setText(String.valueOf(miMesaVO.getMesafolio()));
		}
			
		cbSituacion.setSelectedItem(miMesaVO.getMesasituacion());
		codigoDeMesa = miMesaVO.getCodmesa();
		
		if ("TM".equals(miMesaVO.getTurno())) rbTm.setSelected(true);
		if ("TT".equals(miMesaVO.getTurno())) rbTt.setSelected(true);
		if ("TV".equals(miMesaVO.getTurno())) rbTv.setSelected(true);
		
		habilita(false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true);
	}

	/**
	 * Habilita botones y campos de la VentanaMesa
	 */
	public void habilita(boolean cod, boolean fecha, boolean llama, boolean libro, boolean folio, boolean situa,
			boolean bBuscarxCod, boolean tm, boolean tt, boolean tv, boolean bGuardar, boolean bAgregar, boolean bModificar, 
			boolean bEliminar, boolean bBuscar, boolean bCancelar) {
		
		cbMaterias.setEnabled(cod);
		selectorFecha.setEnabled(fecha);
		cbLlamado.setEnabled(llama);
		txtNumLibro.setEditable(libro);
		txtNumFolio.setEditable(folio);
		cbSituacion.setEnabled(situa);
		
		rbTm.setEnabled(tm);
		rbTt.setEnabled(tt);
		rbTv.setEnabled(tv);
		
		btnBuscarXCodigo.setEnabled(bBuscarxCod);
		btnGuardar.setVisible(bGuardar);
		btnAgregar.setEnabled(bAgregar);
		btnModificar.setEnabled(bModificar);
		btnEliminar.setEnabled(bEliminar);
		btnBuscar.setVisible(bBuscar);
		btnCancelar.setVisible(bCancelar);
	}
}