package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import controlador.Coordinador;
import modelo.AlumnoVO;


public class VentanaAlumno extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtAlDni;
	private JTextField txtAlApyNom;
	private JTextField txtAlDir;
	private JTextField txtAlMail;
	private JTextField txtAlTel;
	private JTextField txtAlCel;
	private JTextField txtAlTitulo;

	private Coordinador miCoordinador;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnAgregar;
	private JCheckBox chkAlDoc;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JButton btnBuscarXDni;
	private JButton btnBuscarXApellido;
	
	private int accion;
	private JButton btnBuscar;
	private JDateChooser selectorFecha;
	private JComboBox<String> cbLocalidad;

	// Paleta de Colores Estandarizada
	private final Color COLOR_HEADER = new Color(33, 37, 41);         // Gris oscuro elegante
	private final Color COLOR_TEXTO_HEADER = new Color(248, 249, 250); // Blanco suave
	private final Color COLOR_FONDO = new Color(210, 215, 222);        // Gris acero medio
	private final Color COLOR_PANEL = new Color(255, 255, 255);        // Blanco para la tarjeta
	private final Color VERDE_INSTITUCIONAL = new Color(34, 139, 34);  // Verde acción positiva
	private final Color ROJO_INSTITUCIONAL = new Color(178, 34, 34);   // Rojo eliminación



	/**
	 * Crea la ventana.
	 */
	public VentanaAlumno() {
		setTitle("GAdA - Administración de Alumnos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 720, 560);
		setLocationRelativeTo(null); // Centra la ventana en pantalla

		contentPane = new JPanel();
		contentPane.setBackground(COLOR_FONDO);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// ====================================================================
		// 1. HEADER (ENCABEZADO)
		// ====================================================================
		JPanel panelHeader = new JPanel(new BorderLayout(15, 0));
		panelHeader.setBackground(COLOR_HEADER);
		panelHeader.setBorder(new EmptyBorder(12, 20, 12, 20));

		JPanel panelTitulos = new JPanel(new GridLayout(2, 1));
		panelTitulos.setOpaque(false);

		JLabel lblTitulo = new JLabel("Legajo de Alumnos");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTitulo.setForeground(COLOR_TEXTO_HEADER);

		JLabel lblSubtitulo = new JLabel("Alta, consulta, modificación y baja de datos personales");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblSubtitulo.setForeground(new Color(173, 181, 189));

		panelTitulos.add(lblTitulo);
		panelTitulos.add(lblSubtitulo);
		panelHeader.add(panelTitulos, BorderLayout.CENTER);

		contentPane.add(panelHeader, BorderLayout.NORTH);

		// ====================================================================
		// 2. PANEL CENTRAL (TARJETA BLANCA CON FORMULARIO)
		// ====================================================================
		JPanel panelCentro = new JPanel(new BorderLayout());
		panelCentro.setBackground(COLOR_FONDO);
		panelCentro.setBorder(new EmptyBorder(15, 20, 15, 20));

		// Tarjeta Blanca Contenedora
		JPanel tarjetaFormulario = new JPanel(new GridBagLayout());
		tarjetaFormulario.setBackground(COLOR_PANEL);
		tarjetaFormulario.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(180, 185, 195), 1),
				BorderFactory.createEmptyBorder(15, 20, 15, 20)
		));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// --- Campo 1: DNI ---
		gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setFont(new Font("Segoe UI", Font.BOLD, 12));
		tarjetaFormulario.add(lblDni,(GridBagConstraints) gbc.clone());

		gbc.gridx = 1; gbc.weightx = 1.0;
		txtAlDni = new JTextField();
		txtAlDni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				busquedaParcialDni();
			}
		});
		tarjetaFormulario.add(txtAlDni, (GridBagConstraints) gbc.clone());

		gbc.gridx = 2; gbc.weightx = 0.0;
		btnBuscarXDni = new JButton("Buscar x DNI");
		btnBuscarXDni.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBuscarXDni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				busquedaParcialDni();
			}
		});
		tarjetaFormulario.add(btnBuscarXDni, (GridBagConstraints) gbc.clone());

		// --- Campo 2: Apellido y Nombre ---
		gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
		JLabel lblApyNom = new JLabel("Apellido y Nombre:");
		lblApyNom.setFont(new Font("Segoe UI", Font.BOLD, 12));
		tarjetaFormulario.add(lblApyNom, (GridBagConstraints) gbc.clone());

		gbc.gridx = 1; gbc.weightx = 1.0;
		txtAlApyNom = new JTextField();
		txtAlApyNom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				busquedaParcial();
			}
		});
		tarjetaFormulario.add(txtAlApyNom, (GridBagConstraints) gbc.clone());

		gbc.gridx = 2; gbc.weightx = 0.0;
		btnBuscarXApellido = new JButton("Buscar x Apellido");
		btnBuscarXApellido.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBuscarXApellido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				busquedaParcial();
			}
		});
		tarjetaFormulario.add(btnBuscarXApellido, (GridBagConstraints) gbc.clone());

		// --- Campo 3: Fecha de Nacimiento ---
		gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
		tarjetaFormulario.add(new JLabel("Fecha de Nacimiento:"), (GridBagConstraints) gbc.clone());

		gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
		selectorFecha = new JDateChooser();
		tarjetaFormulario.add(selectorFecha, (GridBagConstraints) gbc.clone());
		gbc.gridwidth = 1; // Reseteamos ancho de columna

		// --- Campo 4: Dirección ---
		gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
		tarjetaFormulario.add(new JLabel("Dirección:"), gbc);

		gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
		txtAlDir = new JTextField();
		tarjetaFormulario.add(txtAlDir,(GridBagConstraints) gbc.clone());
		gbc.gridwidth = 1;

		// --- Campo 5: Localidad ---
		gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0;
		tarjetaFormulario.add(new JLabel("Localidad:"), (GridBagConstraints) gbc.clone());

		gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
		cbLocalidad = new JComboBox<String>();
		cbLocalidad.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Cañuelas", "Gral. Las Heras", "Marcos Paz", "Mariano Acosta", "Merlo", "Navarro"}));
		cbLocalidad.setEditable(true);
		tarjetaFormulario.add(cbLocalidad, (GridBagConstraints) gbc.clone());
		gbc.gridwidth = 1;

		// --- Campo 6: Email ---
		gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.0;
		tarjetaFormulario.add(new JLabel("Email:"), (GridBagConstraints) gbc.clone());

		gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
		txtAlMail = new JTextField();
		tarjetaFormulario.add(txtAlMail, (GridBagConstraints) gbc.clone());
		gbc.gridwidth = 1;

		// --- Campo 7: Teléfono y Celular (En una misma fila para ahorrar espacio) ---
		gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0.0;
		tarjetaFormulario.add(new JLabel("Teléfono / Celular:"), (GridBagConstraints) gbc.clone());

		JPanel panelTelefonos = new JPanel(new GridLayout(1, 2, 10, 0));
		panelTelefonos.setOpaque(false);
		txtAlTel = new JTextField();
		txtAlTel.setToolTipText("Teléfono Fijo");
		txtAlCel = new JTextField();
		txtAlCel.setToolTipText("Teléfono Celular");
		panelTelefonos.add(txtAlTel);
		panelTelefonos.add(txtAlCel);

		gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
		tarjetaFormulario.add(panelTelefonos, (GridBagConstraints) gbc.clone());
		gbc.gridwidth = 1;

		// --- Campo 8: Título Secundario ---
		gbc.gridx = 0; gbc.gridy = 7; gbc.weightx = 0.0;
		tarjetaFormulario.add(new JLabel("Título Secundario:"), (GridBagConstraints) gbc.clone());

		gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
		txtAlTitulo = new JTextField();
		tarjetaFormulario.add(txtAlTitulo, (GridBagConstraints) gbc.clone());
		gbc.gridwidth = 1;

		// --- Campo 9: Documentación Entregada (Checkbox) ---
		gbc.gridx = 1; gbc.gridy = 8; gbc.gridwidth = 2;
		chkAlDoc = new JCheckBox("Documentación completa presentada");
		chkAlDoc.setFont(new Font("Segoe UI", Font.BOLD, 12));
		chkAlDoc.setOpaque(false);
		tarjetaFormulario.add(chkAlDoc,  (GridBagConstraints) gbc.clone());

		panelCentro.add(tarjetaFormulario, BorderLayout.CENTER);
		contentPane.add(panelCentro, BorderLayout.CENTER);

		// ====================================================================
		// 3. FOOTER (BARRA DE BOTONES DE ACCIÓN)
		// ====================================================================
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		panelBotones.setBackground(new Color(180, 185, 195));

		btnAgregar = new JButton("Nuevo Alumno");
		btnAgregar.setBackground(VERDE_INSTITUCIONAL);
	
		btnAgregar.setForeground(Color.WHITE);

		btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accion = 1;
				agregarAlumno();
			}
		});

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBackground(VERDE_INSTITUCIONAL);
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarAlumno();
			}
		});

		btnModificar = new JButton("Modificar");
		btnModificar.setBackground(COLOR_HEADER);
		btnModificar.setForeground(Color.WHITE);
		btnModificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				accion = 2;
				habilita(false, true, true, true, true, true, true, true, true, true, false, false, true, false, false, false, true);
			}
		});

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(ROJO_INSTITUCIONAL);
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eliminarAlumno();
			}
		});

		btnBuscar = new JButton("Buscar Todos");
		btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscarAlumno();
			}
		});

		btnCancelar = new JButton("Cancelar / Volver");
		btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volver();
			}
		});

		// Agregamos los botones al footer en orden lógico de uso
		panelBotones.add(btnBuscar);
		panelBotones.add(btnAgregar);
		panelBotones.add(btnGuardar);
		panelBotones.add(btnModificar);
		panelBotones.add(btnEliminar);
		panelBotones.add(btnCancelar);

		contentPane.add(panelBotones, BorderLayout.SOUTH);
	}

	// ====================================================================
	// MÉTODOS DE LÓGICA DE NEGOCIO (SIN CAMBIOS)
	// ====================================================================

	protected void buscarAlumno() {
		int btn = 1, ventana = 1;
		String ape = txtAlApyNom.getText();
		int doc;
		if (txtAlDni.getText().isEmpty())
			doc = 0;
		else
			doc = Integer.valueOf(txtAlDni.getText());
		miCoordinador.mostrarVentanaAlumnoBuscar(btn, doc, ape, ventana);
	}

	private void guardarAlumno() {
		try {
			Date date = selectorFecha.getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			AlumnoVO miAlumno = new AlumnoVO();
			miAlumno.setAldni(Integer.parseInt(txtAlDni.getText()));
			miAlumno.setAlapynom(txtAlApyNom.getText());
			miAlumno.setAlfnac(sdf.format(date));

			System.out.println("Fecha de nacimiento: " + miAlumno.getAlfnac());
			miAlumno.setAldir(txtAlDir.getText());
			miAlumno.setAlloc((String) cbLocalidad.getSelectedItem());
			miAlumno.setAlmail(txtAlMail.getText());
			miAlumno.setAltel(txtAlTel.getText());
			miAlumno.setAlcel(txtAlCel.getText());
			miAlumno.setAltitulo(txtAlTitulo.getText());
			if (chkAlDoc.isSelected())
				miAlumno.setAldoc((byte) 1);
			else
				miAlumno.setAldoc((byte) 0);

			if (accion == 1) {
				miCoordinador.registrarAlumno(miAlumno);
				limpiar();
			} else {
				miCoordinador.modificarAlumno(miAlumno);
				limpiar();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error en el Ingreso de Datos", "Error",
					JOptionPane.ERROR_MESSAGE);
			limpiar();
		}
	}

	protected void busquedaParcialDni() {
		int btn = 3, ventana = 1;
		String ape = txtAlApyNom.getText();
		int doc;
		if (txtAlDni.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "<html>Ingrese los primeros números del Documento <br>"
					+ "o el número completo </html>", "Información", JOptionPane.WARNING_MESSAGE);
		} else {
			try {
				doc = Integer.valueOf(txtAlDni.getText().trim());
				miCoordinador.mostrarVentanaAlumnoBuscar(btn, doc, ape, ventana);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "<html>Ingrese los primeros números del Documento <br>"
						+ "sin espacios o letras </html>  ", "Información", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	protected void busquedaParcial() {
		int btn = 2, ventana = 1;
		String ape = txtAlApyNom.getText();
		int doc;
		if (txtAlDni.getText().isEmpty()) {
			doc = 0;
		} else
			doc = Integer.valueOf(txtAlDni.getText());
		miCoordinador.mostrarVentanaAlumnoBuscar(btn, doc, ape, ventana);
	}

	public void eliminarAlumno() {
		if (!txtAlDni.getText().equals("")) {
			int respuesta = JOptionPane.showConfirmDialog(null, "Está seguro de eliminar ese Alumno?", "Confirmación", JOptionPane.YES_NO_OPTION);

			if (respuesta == JOptionPane.YES_OPTION) {
				miCoordinador.eliminarAlumno(Integer.parseInt(txtAlDni.getText()));
				limpiar();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Ingrese un número de Documento", "Información", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	private void habilita(boolean dni, boolean nombre, boolean fnac, boolean dir, boolean loc, boolean mail, boolean tel,
			boolean cel, boolean tit, boolean doc, boolean bBuscarxDni, boolean bBuscarxApyNom, boolean bGuardar,
			boolean bAgregar, boolean bModificar, boolean bEliminar, boolean bCancelar) {
		txtAlDni.setEditable(dni);
		txtAlApyNom.setEditable(nombre);
		selectorFecha.setEnabled(fnac);
		txtAlDir.setEditable(dir);
		cbLocalidad.setEnabled(loc);
		txtAlMail.setEditable(mail);
		txtAlTel.setEditable(tel);
		txtAlCel.setEditable(cel);
		txtAlTitulo.setEditable(tit);
		chkAlDoc.setEnabled(doc);
		btnBuscarXDni.setEnabled(bBuscarxDni);
		btnBuscarXApellido.setEnabled(bBuscarxApyNom);
		btnGuardar.setVisible(bGuardar);
		btnAgregar.setEnabled(bAgregar);
		btnModificar.setEnabled(bModificar);
		btnEliminar.setEnabled(bEliminar);
		btnCancelar.setVisible(bCancelar);
		/*Agrego esta linea (btnBuscar.setEnabled(!bGuardar);)para desabilitar el boton "BuscarTodos" cuando este habilitado el boton "Guardar"
		  para que lo desabilite en el momento que cargo datos de alumno nuevo */
		btnBuscar.setEnabled(!bGuardar);
	}

	public void limpiar() {
		txtAlDni.setText("");
		txtAlApyNom.setText("");
		selectorFecha.setCalendar(null);
		txtAlDir.setText("");
		cbLocalidad.setSelectedIndex(0);
		txtAlMail.setText("");
		txtAlTel.setText("");
		txtAlCel.setText("");
		txtAlTitulo.setText("");
		chkAlDoc.setSelected(false);

		habilita(true, true, false, false, false, false, false, false, false, false, true, true, false, true, false,
				false, false);
	}

	private void agregarAlumno() {
		habilita(true, true, true, true, true, true, true, true, true, true, false, false, true, false, false, false,
				true);
	}

	private void volver() {
		limpiar();
	}

	public void muestraAlumno(AlumnoVO miAlumno) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date miDia = new Date();
		GregorianCalendar miGCalendar = new GregorianCalendar();
		try {
			miDia = formato.parse(miAlumno.getAlfnac());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		miGCalendar.setTime(miDia);
		txtAlDni.setText(String.valueOf(miAlumno.getAldni()));
		txtAlApyNom.setText(miAlumno.getAlapynom());
		selectorFecha.setCalendar(miGCalendar);
		txtAlDir.setText(miAlumno.getAldir());
		cbLocalidad.setSelectedItem(miAlumno.getAlloc());
		txtAlMail.setText(miAlumno.getAlmail());
		txtAlTel.setText(miAlumno.getAltel());
		txtAlCel.setText(miAlumno.getAlcel());
		txtAlTitulo.setText(miAlumno.getAltitulo());

		if (miAlumno.getAldoc() == 0)
			chkAlDoc.setSelected(false);
		else
			chkAlDoc.setSelected(true);
			habilita(false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true);
	}
}