package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controlador.Coordinador;
import modelo.AlumnoVO;

/**
 * Programa VentanaAlumnoBuscar - Estilo Rediseñado y Desacoplado
 * Paquete vista
 */
public class VentanaAlumnoBuscar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private Coordinador miCoordinador;
	private JScrollPane scrollPane;
	private JPanel panelFooter;
	private JButton btnVolver;
	private JLabel lblSubtitulo;

	// Paleta de Colores Estandarizada
	private final Color COLOR_HEADER = new Color(33, 37, 41);         // Gris oscuro
	private final Color COLOR_TEXTO_HEADER = new Color(248, 249, 250); // Blanco suave
	private final Color COLOR_FONDO = new Color(210, 215, 222);        // Gris acero medio
	private final Color COLOR_PANEL = new Color(255, 255, 255);        // Blanco para la tarjeta

	/**
	 * Crea la ventana
	 */
	public VentanaAlumnoBuscar() {
		setTitle("GAdA - Búsqueda de Alumnos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1100, 580);
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

		JLabel lblTitulo = new JLabel("Listado de Resultados de Alumnos");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTitulo.setForeground(COLOR_TEXTO_HEADER);

		lblSubtitulo = new JLabel("Haga doble clic o presione ENTER sobre una fila para seleccionar el alumno");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblSubtitulo.setForeground(new Color(173, 181, 189));

		panelTitulos.add(lblTitulo);
		panelTitulos.add(lblSubtitulo);
		panelHeader.add(panelTitulos, BorderLayout.CENTER);

		contentPane.add(panelHeader, BorderLayout.NORTH);

		// ====================================================================
		// 2. PANEL CENTRAL (TARJETA BLANCA CON TABLA)
		// ====================================================================
		JPanel panelCentro = new JPanel(new BorderLayout());
		panelCentro.setBackground(COLOR_FONDO);
		panelCentro.setBorder(new EmptyBorder(15, 20, 15, 20));

		JPanel tarjetaTabla = new JPanel(new BorderLayout());
		tarjetaTabla.setBackground(COLOR_PANEL);
		tarjetaTabla.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(180, 185, 195), 1),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)
		));

		scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(COLOR_PANEL);
		tarjetaTabla.add(scrollPane, BorderLayout.CENTER);

		panelCentro.add(tarjetaTabla, BorderLayout.CENTER);
		contentPane.add(panelCentro, BorderLayout.CENTER);

		// ====================================================================
		// 3. FOOTER (BOTÓN DE RETORNO)
		// ====================================================================
		panelFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		panelFooter.setBackground(new Color(180, 185, 195));

		btnVolver = new JButton("Volver / Cancelar");
		btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		panelFooter.add(btnVolver);

		contentPane.add(panelFooter, BorderLayout.SOUTH);
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	/**
	 * Carga y muestra el JTable para seleccionar el alumno
	 */
	public void mostrarDatosConTableModel(int btn, int doc, String ape, final int ventana) {
		DefaultTableModel modelo = new DefaultTableModel(
				new Object[][] {},
				new String[] {
					"DNI", "Apellido y nombre", "Fecha Nac.", "Dirección", "Localidad", "Mail", "Teléfono", "Celular", 
					"Título", "Doc."
				}
		) {
			private static final long serialVersionUID = 1L;
			Class<?>[] columnTypes = new Class<?>[] {
				Integer.class, String.class, String.class, String.class, String.class, String.class, String.class, 
				String.class, String.class, Boolean.class
			};

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false, false, false
			};

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		table = new JTable();
		table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		table.setRowHeight(24);
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

		// Evento de Selección por Clic
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = table.getSelectedRow();
				if (fila != -1) {
					seleccionarAlumnoYSalir(fila, ventana);
				}
			}
		});

		// Evento de Selección por Tecla ENTER
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					int fila = table.getSelectedRow();
					if (fila != -1) {
						seleccionarAlumnoYSalir(fila, ventana);
					}
				}
			}
		});

		table.setModel(modelo);
		table.getTableHeader().setReorderingAllowed(false);

		// Alineaciones de celdas
		DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
		centrar.setHorizontalAlignment(SwingConstants.CENTER);

		DefaultTableCellRenderer izquierda = new DefaultTableCellRenderer();
		izquierda.setHorizontalAlignment(SwingConstants.LEFT);

		// Configuración de anchos de columnas
		table.getColumnModel().getColumn(0).setPreferredWidth(80);  // DNI
		table.getColumnModel().getColumn(0).setCellRenderer(centrar);

		table.getColumnModel().getColumn(1).setPreferredWidth(180); // Apellido y Nombre
		table.getColumnModel().getColumn(1).setCellRenderer(izquierda);

		table.getColumnModel().getColumn(2).setPreferredWidth(85);  // Fecha Nac
		table.getColumnModel().getColumn(2).setCellRenderer(centrar);

		table.getColumnModel().getColumn(3).setPreferredWidth(120); // Dirección
		table.getColumnModel().getColumn(3).setCellRenderer(izquierda);

		table.getColumnModel().getColumn(4).setPreferredWidth(100); // Localidad
		table.getColumnModel().getColumn(4).setCellRenderer(izquierda);

		table.getColumnModel().getColumn(5).setPreferredWidth(130); // Mail
		table.getColumnModel().getColumn(5).setCellRenderer(izquierda);

		table.getColumnModel().getColumn(6).setPreferredWidth(90);  // Teléfono
		table.getColumnModel().getColumn(6).setCellRenderer(centrar);

		table.getColumnModel().getColumn(7).setPreferredWidth(90);  // Celular
		table.getColumnModel().getColumn(7).setCellRenderer(centrar);

		table.getColumnModel().getColumn(8).setPreferredWidth(110); // Título
		table.getColumnModel().getColumn(8).setCellRenderer(izquierda);

		// Columna con CheckBox para Documentación
		agregarCheck(table.getColumnModel().getColumn(9));

		// =====================================================================
		// CONSULTA A TRAVÉS DEL COORDINADOR Y POBLADO DE LA TABLA
		// =====================================================================
		List<AlumnoVO> listaAlumnos = miCoordinador.buscarAlumnosPorFiltro(btn, doc, ape);

		for (AlumnoVO al : listaAlumnos) {
			modelo.addRow(new Object[] {
				al.getAldni(),
				al.getAlapynom(),
				al.getAlfnac(),
				al.getAldir(),
				al.getAlloc(),
				al.getAlmail(),
				al.getAltel(),
				al.getAlcel(),
				al.getAltitulo(),
				al.getAldoc() == 1 // Devuelve true o false para el CheckBox
			});
		}

		if (btn == 1) {
			lblSubtitulo.setText("Filtro: Listado completo de Alumnos ordenados por APELLIDO");
		} else if (btn == 2) {
			lblSubtitulo.setText("Filtro: Coincidencias por Apellido ('" + ape + "')");
		} else if (btn == 3) {
			lblSubtitulo.setText("Filtro: Coincidencias por DNI ('" + doc + "')");
		}

		scrollPane.setViewportView(table);
	}

	/**
	 * Método auxiliar para pasar los datos al coordinador y cerrar la ventana
	 */
	private void seleccionarAlumnoYSalir(int fila, int ventana) {
		if (ventana == 1) {
			miCoordinador.pasarDatosAlumno(pasarDatosAlumno(fila));
		} else {
			miCoordinador.pasarDatosAlumnoMateria(pasarDatosAlumno(fila));
		}
		dispose();
	}

	private void agregarCheck(TableColumn columna) {
		JCheckBox chkDoc = new JCheckBox();
		columna.setCellEditor(new DefaultCellEditor(chkDoc));
		columna.setPreferredWidth(45);
	}

	/**
	 * Pasa los datos de la fila del JTable a un objeto AlumnoVO
	 */
	protected AlumnoVO pasarDatosAlumno(int fila) {
		AlumnoVO miAlumno = new AlumnoVO();
		miAlumno.setAldni(Integer.valueOf(table.getValueAt(fila, 0).toString()));
		miAlumno.setAlapynom(table.getValueAt(fila, 1).toString());
		miAlumno.setAlfnac(table.getValueAt(fila, 2).toString());
		miAlumno.setAldir(table.getValueAt(fila, 3).toString());
		miAlumno.setAlloc(table.getValueAt(fila, 4).toString());
		miAlumno.setAlmail(table.getValueAt(fila, 5).toString());
		miAlumno.setAltel(table.getValueAt(fila, 6).toString());
		miAlumno.setAlcel(table.getValueAt(fila, 7).toString());
		miAlumno.setAltitulo(table.getValueAt(fila, 8).toString());

		String estado = table.getValueAt(fila, 9).toString();

		if (estado.equals("false")) {
			miAlumno.setAldoc((byte) 0);
		} else {
			miAlumno.setAldoc((byte) 1);
		}

		return miAlumno;
	}
}