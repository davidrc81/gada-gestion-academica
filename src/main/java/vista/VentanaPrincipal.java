package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.Coordinador;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Coordinador miCoordinador;

	// Paleta de Colores Institucionales
	private final Color COLOR_HEADER = new Color(33, 37, 41);
	private final Color COLOR_TEXTO_HEADER = new Color(248, 249, 250);
	private final Color COLOR_FONDO = new Color(210, 215, 222);
	private final Color VERDE_INSTITUCIONAL = new Color(34, 139, 34);
	private final Color ROJO_INSTITUCIONAL = new Color(178, 34, 34);

	public VentanaPrincipal() {
		setTitle("GAdA v1.1 - Gestión Académica de Alumnos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 520);
		setLocationRelativeTo(null); 

		contentPane = new JPanel();
		contentPane.setBackground(COLOR_FONDO);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// ====================================================================
		// 1. HEADER / ENCABEZADO
		// ====================================================================
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(COLOR_HEADER);
		panelHeader.setLayout(new BorderLayout(15, 0));
		panelHeader.setBorder(new EmptyBorder(12, 20, 12, 20));

		JLabel lblLogo = new JLabel();
		try {
			URL logoUrl = VentanaPrincipal.class.getResource("/imagenes/Is44 logo.JPG");
			if (logoUrl != null) {
				ImageIcon originalIcon = new ImageIcon(logoUrl);
				Image imgEscalada = originalIcon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
				lblLogo.setIcon(new ImageIcon(imgEscalada));
			}
		} catch (Exception e) {}
		panelHeader.add(lblLogo, BorderLayout.WEST);

		JPanel panelTitulos = new JPanel(new GridLayout(2, 1));
		panelTitulos.setOpaque(false);

		JLabel lblTitulo = new JLabel("ISFTyD N° 44 - General Las Heras");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTitulo.setForeground(COLOR_TEXTO_HEADER);

		JLabel lblSubtitulo = new JLabel("Sistema de Gestión Académica y Mesas de Examen");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblSubtitulo.setForeground(new Color(173, 181, 189));

		panelTitulos.add(lblTitulo);
		panelTitulos.add(lblSubtitulo);
		panelHeader.add(panelTitulos, BorderLayout.CENTER);

		contentPane.add(panelHeader, BorderLayout.NORTH);

		// ====================================================================
		// 2. PANEL CENTRAL (Tarjetas de Accesos Rápidos)
		// ====================================================================
		JPanel panelCentro = new JPanel();
		panelCentro.setOpaque(false);
		panelCentro.setBorder(new EmptyBorder(30, 40, 30, 40));
		panelCentro.setLayout(new GridLayout(2, 3, 20, 20)); 

		// --- Tarjeta 1: Alumnos ---
		JButton btnAlumno = crearBotonSeccion("Alumnos", "Gestión de legajos y datos", VERDE_INSTITUCIONAL);
		btnAlumno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (miCoordinador != null) miCoordinador.mostrarVentanaAlumno();
			}
		});
		panelCentro.add(envolverEnPanel(btnAlumno)); // <-- ACA ESTA EL TRUCO

		// --- Tarjeta 2: Materias ---
		JButton btnMateria = crearBotonSeccion("Materias", "Planes de estudio y carreras", VERDE_INSTITUCIONAL);
		btnMateria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (miCoordinador != null) miCoordinador.mostrarVentanaMateria();
			}
		});
		panelCentro.add(envolverEnPanel(btnMateria));

		// --- Tarjeta 3: Cursadas (Alumno - Materia) ---
		JButton btnAlumnoMateria = crearBotonSeccion("Inscripciones", "Inscribir alumnos a materias", VERDE_INSTITUCIONAL);
		btnAlumnoMateria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (miCoordinador != null) miCoordinador.mostrarVentanaAlumnoMateria();
			}
		});
		panelCentro.add(envolverEnPanel(btnAlumnoMateria));

		// --- Tarjeta 4: Mesas de Examen ---
		JButton btnMesas = crearBotonSeccion("Mesas de Examen", "Actas y llamados de examen", ROJO_INSTITUCIONAL);
		btnMesas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (miCoordinador != null) miCoordinador.mostrarVentanaAlumnoMesa();
			}
		});
		panelCentro.add(envolverEnPanel(btnMesas));

		// --- Tarjeta 5: Parciales ---
		JButton btnCargarParciales = crearBotonSeccion("Notas Parciales", "Carga de calificaciones", ROJO_INSTITUCIONAL);
		btnCargarParciales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (miCoordinador != null) miCoordinador.mostrarVentanaAlumnoParciales();
			}
		});
		panelCentro.add(envolverEnPanel(btnCargarParciales));

		// --- Tarjeta 6: Reportes ---
		JButton btnReportes = crearBotonSeccion("Reportes", "Planillas e informes PDF", COLOR_HEADER);
		btnReportes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (miCoordinador != null) miCoordinador.mostrarVentanaReportes();
			}
		});
		panelCentro.add(envolverEnPanel(btnReportes));

		contentPane.add(panelCentro, BorderLayout.CENTER);

		// ====================================================================
		// 3. FOOTER / BARRA DE ESTADO
		// ====================================================================
		JPanel panelFooter = new JPanel(new BorderLayout());
		panelFooter.setBackground(new Color(222, 226, 230));
		panelFooter.setBorder(new EmptyBorder(6, 15, 6, 15));

		JLabel lblEstado = new JLabel("Estado: Conectado a la base de datos");
		lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblEstado.setForeground(new Color(73, 80, 87));

		JLabel lblVersion = new JLabel("GAdA Sistema Académico v1.1");
		lblVersion.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblVersion.setForeground(new Color(73, 80, 87));

		panelFooter.add(lblEstado, BorderLayout.WEST);
		panelFooter.add(lblVersion, BorderLayout.EAST);

		contentPane.add(panelFooter, BorderLayout.SOUTH);
	}

	/**
	 * Método auxiliar para construir botones estilizados con formato de tarjeta
	 * @wbp.factory
	 */
	private JButton crearBotonSeccion(String titulo, String subtitulo, Color colorAcento) {
		String htmlTexto = "<html><center>"
				+ "<font size='4'><b>" + titulo + "</b></font><br>"
				+ "<font size='2' color='#6c757d'>" + subtitulo + "</font>"
				+ "</center></html>";

		JButton boton = new JButton(htmlTexto);
		boton.setPreferredSize(new Dimension(220, 80));
		boton.setFocusPainted(false);
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		boton.setHorizontalAlignment(SwingConstants.CENTER);
		boton.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 4, 0, 0, colorAcento), 
				BorderFactory.createEmptyBorder(10, 10, 10, 10)
		));

		return boton;
	}

	/**
	 * TRUCO PARA WINDOWBUILDER:
	 * Envuelve el botón en un JPanel transparente para que el GridLayout
	 * no colapse por culpa del código HTML dentro del texto del botón.
	 */
	private JPanel envolverEnPanel(JButton boton) {
		JPanel wrapper = new JPanel(new BorderLayout());
		wrapper.setOpaque(false); // Transparente para que no tape el fondo
		wrapper.add(boton, BorderLayout.CENTER);
		return wrapper;
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}
}