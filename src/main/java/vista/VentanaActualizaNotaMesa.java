package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;

import controlador.Coordinador;
import modelo.AlumnomesaDAO;
import modelo.MesaVO;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;

public class VentanaActualizaNotaMesa extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JPanel panelSur;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private Coordinador miCoordinador;
	private JScrollPane scrollPane;
	private JPanel panelNorte;
	private JLabel lblMateria;
	private JLabel lblFecha;
	private JLabel lblLibro;
	private JTextField txtLibro;
	private JLabel lblFolio;
	private JTextField txtFolio;
	private JLabel lblLlamado;
	private JLabel lblSituacin;
	private JLabel lblNomMat;
	private JLabel lblfechamesa;
	private JLabel lblLlamadoMesa;
	private JLabel lblSituacionMesa;
	
	private int codMesa;
	
	MaskFormatter mascara;
	private JLabel labelTurno;
	private JLabel lblTurno;
	private JLabel label;
	private JLabel label_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaActualizaNotaMesa frame = new VentanaActualizaNotaMesa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**  
	 * Create the frame.
	 */
	public VentanaActualizaNotaMesa() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 575, 532);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardarCambiosEnDAO();
				dispose();
			}
		});
		panelSur.add(btnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		panelSur.add(btnCancelar);
		
		panelNorte = new JPanel();
		panelNorte.setBorder(new TitledBorder(null, "Datos mesa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new GridLayout(0, 4, 10, 0));
		
		lblMateria = new JLabel("Materia:");
		lblMateria.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNorte.add(lblMateria);
		
		lblNomMat = new JLabel("");
		panelNorte.add(lblNomMat);
		
		lblFecha = new JLabel("Fecha:");
		lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNorte.add(lblFecha);
		
		lblfechamesa = new JLabel("");
		panelNorte.add(lblfechamesa);
		
		lblLlamado = new JLabel("Llamado:");
		lblLlamado.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNorte.add(lblLlamado);
		
		lblLlamadoMesa = new JLabel("");
		panelNorte.add(lblLlamadoMesa);
		
		lblSituacin = new JLabel("Situación:");
		lblSituacin.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNorte.add(lblSituacin);
		
		lblSituacionMesa = new JLabel("");
		panelNorte.add(lblSituacionMesa);
		
		labelTurno = new JLabel("Turno:");
		labelTurno.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNorte.add(labelTurno);
		
		lblTurno = new JLabel("");
		panelNorte.add(lblTurno);
		
		label = new JLabel("");
		panelNorte.add(label);
		
		label_1 = new JLabel("");
		panelNorte.add(label_1);
		
		lblLibro = new JLabel("Libro:");
		lblLibro.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNorte.add(lblLibro);
		
		txtLibro = new JTextField();
		txtLibro.setHorizontalAlignment(SwingConstants.LEFT);
		panelNorte.add(txtLibro);
		txtLibro.setColumns(10);
		
		lblFolio = new JLabel("Folio:");
		lblFolio.setHorizontalAlignment(SwingConstants.RIGHT);
		panelNorte.add(lblFolio);
		
		txtFolio = new JTextField();
		txtFolio.setHorizontalAlignment(SwingConstants.LEFT);
		panelNorte.add(txtFolio);
		txtFolio.setColumns(10);
		panelNorte.add(txtFolio);
		
		
		
	}
	/**
	 * Crea la tabla para poder agregar un campo ComboBox
	 */
	private void iniciarTabla() {
		table = new JTable();
		DefaultTableModel modeloNotas = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"DNI", "Apellido y nombre", "Nota", "Cod mesa"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		
		table.setModel(modeloNotas);
		
		//Para centrar los valores en las columnas
		DefaultTableCellRenderer alinear = new DefaultTableCellRenderer();

		alinear.setHorizontalAlignment(SwingConstants.CENTER);
		
		//columna DNI
		table.getColumnModel().getColumn(0).setMaxWidth(80);
		table.getColumnModel().getColumn(0).setMinWidth(80);
		table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(80);
		table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(80);
		table.getColumnModel().getColumn(0).setCellRenderer(alinear);
		
		//columna nota
		table.getColumnModel().getColumn(2).setMaxWidth(80);
		table.getColumnModel().getColumn(2).setMinWidth(80);
		table.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(80);
		table.getTableHeader().getColumnModel().getColumn(2).setMinWidth(80);
		table.getColumnModel().getColumn(2).setCellRenderer(alinear);
		
		//columna codmesa
		table.getColumnModel().getColumn(3).setMaxWidth(0);
		table.getColumnModel().getColumn(3).setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(3).setMinWidth(0);
		
		//especifica cual es la columna que va a tener el renderizado (el combobox)
		agregarEditor(table.getColumnModel().getColumn(2));
		
		scrollPane.setViewportView(table);
	}
	/**
	 * Actualiza las tablas:
	 * 		Alumnomesa siempre, con la nota que obtuvo ese día.
	 * 		Alumnomateria solo si la nota es >= 4 (nota, libro, folio y fecha)
	 * 		Mesa con el libro y folio
	 */
	protected void guardarCambiosEnDAO() {
		String codMat=lblNomMat.getText().trim();
		String fecha=lblfechamesa.getText();
		int libro;
		int folio;
		if (!txtLibro.getText().isEmpty())
			libro=Integer.valueOf(txtLibro.getText().trim());
		else
			libro=0;
		if (!txtFolio.getText().isEmpty())
			folio=Integer.valueOf(txtFolio.getText().trim());
		else
			folio=0;
		
		AlumnomesaDAO miAlumnoMesaDAO = new AlumnomesaDAO();
		miAlumnoMesaDAO.actualizaNotasMesa(table,codMat,fecha,libro,folio,codMesa);
	
	}
	/**
	 *  Establece el editor para la columna de la tabla
	 * @param columna
	 */
	public void agregarEditor(TableColumn columna) {
        JComboBox<String> cbNotas = new JComboBox<String>();
        cbNotas.addItem("AUSENTE");
        cbNotas.addItem("10");
        cbNotas.addItem("9");
        cbNotas.addItem("8");
        cbNotas.addItem("7");
        cbNotas.addItem("6");
        cbNotas.addItem("5");
        cbNotas.addItem("4");
        cbNotas.addItem("3");
        cbNotas.addItem("2");
        cbNotas.addItem("1");
        cbNotas.addItem("0");
        columna.setCellEditor(new DefaultCellEditor(cbNotas));
	}
	/**
	 * Arma y muestra la tabla con los alumnos que se anotaron en una mesa
	 * @param miMesaVO
	 */
	public void mostrarTabla(MesaVO miMesaVO) {
		codMesa=miMesaVO.getCodmesa();
		lblNomMat.setText(miMesaVO.getCodmat());
		lblfechamesa.setText(miMesaVO.getMesafecha());
		lblSituacionMesa.setText(miMesaVO.getMesasituacion());
		lblLlamadoMesa.setText(String.valueOf(miMesaVO.getMesallamado()));
		if (miMesaVO.getMesalibro()==0)
			txtLibro.setText("");
		else
			txtLibro.setText(String.valueOf(miMesaVO.getMesalibro()));
		if (miMesaVO.getMesafolio()==0)
			txtFolio.setText("");
		else
			txtFolio.setText(String.valueOf(miMesaVO.getMesafolio()));
		iniciarTabla();
		AlumnomesaDAO miAlumnoMesa = new AlumnomesaDAO();
		miAlumnoMesa.buscarNotas(table.getModel(),codMesa);
	}
	/**
	 * Método setCoordinador
	 * @param miCoordinador
	 */
	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
		
	}
	public JLabel getTxtTurno() {
		return lblTurno;
	}
}
