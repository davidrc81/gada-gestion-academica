package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Coordinador;
import modelo.AlumnomateriaDAO;
import modelo.AlumnomesaDAO;
import modelo.Conexion;
import modelo.MateriaDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.toedter.calendar.JYearChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaAlumnoParciales extends JFrame {

	private JPanel contentPane;
	private Coordinador miCoordinador;
	private JComboBox<String> cbMaterias;
	static DefaultComboBoxModel<String> modeloComboMaterias;
	private JPanel panelCentro;
	private JLabel lblMatnom;
	private JLabel label;
	private JLabel label_1;
	private JLabel lblCurso;
	private JYearChooser selectorAnio;
	private JScrollPane scrollNotasParciales;
	private JTable tablaNotasParciales;
	private DefaultTableModel modeloNotasParciales;
	private JButton btnActualizarTabla;
	private JLabel label_3;
	private JLabel labelPlan;
	private JComboBox<String> cbPlan;
	static DefaultComboBoxModel<String> modeloComboPlan;
	private boolean primeraCarga = true; // Bandera para determinar si es la primera carga del cbMaterias
	private int cantItem=0;
	private boolean primerPasada = true;

	public Coordinador getMiCoordinador() {
		return miCoordinador;
	}

	public void setMiCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAlumnoParciales frame = new VentanaAlumnoParciales();
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
	public VentanaAlumnoParciales() {
		setTitle("Ingreso de notas de parciales y recuperatorios");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 10, 1350, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelNorte = new JPanel();
		panelNorte.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		JLabel labelCurso = new JLabel("Curso:");
		panelNorte.add(labelCurso);
		
		lblCurso = new JLabel("   ");
		panelNorte.add(lblCurso);
		
		JLabel label_2 = new JLabel("    ");
		panelNorte.add(label_2);
		
		JLabel lblCdigoDeMateria = new JLabel("Código de materia:");
		panelNorte.add(lblCdigoDeMateria);
		
		cbMaterias = new JComboBox<String>();
		cbMaterias.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				 if (e.getStateChange() == ItemEvent.SELECTED) {
					// verListadoAlumnos();
					 if (!primerPasada) {
						
						 if (modeloNotasParciales != null) {
							 modeloNotasParciales.setRowCount(0);
						 }
					 	lblMatnom.setText("");
					 	lblCurso.setText("");
					 }
					 else {
						 primerPasada = false;
						 
					 }
				 }
			}
		});
		modeloComboMaterias = new DefaultComboBoxModel<String>();
		cbMaterias.setModel(modeloComboMaterias);
		panelNorte.add(cbMaterias);
		
		label_1 = new JLabel("     ");
		panelNorte.add(label_1);
		
		lblMatnom = new JLabel("                                                                                                            ");
		panelNorte.add(lblMatnom);
		
		label = new JLabel("              ");
		panelNorte.add(label);
		
		labelPlan = new JLabel("Plan:");
		panelNorte.add(labelPlan);
		
		cbPlan = new JComboBox<String>();
		cbPlan.setEditable(false);
		cbPlan.addItemListener(new ItemListener() {
			private boolean cargaEnProgreso = false;
			
			public void itemStateChanged(ItemEvent e) {
				
				//elige un plan y actualiza el combo de las materias
				
				 if (e.getStateChange() == ItemEvent.SELECTED) {
			            if (!cargaEnProgreso) {
			                cargaEnProgreso = true;
			                
			                if (!primeraCarga) {
			                  
			                    // contar cuantos items tiene y borrarlos primeros
			                	cantItem=cbMaterias.getItemCount();
			                	
			                } else {
			                    primeraCarga = false; // Marcar que ya no es la primera carga
			                }
			                
			                cambiarMateriasdelCombo();
			                                
			                if (cantItem >0) {
			                	
			                	// Deshabilita temporalmente el ItemListener
				                cbPlan.removeItemListener(this);
				                // aca no seria deshabilitar el cbMaterias?
				                
			                	for (int i=0; i<cantItem; i++) {
			                		if (modeloComboMaterias.getSize()>0) {
			                			modeloComboMaterias.removeElementAt(0);
			                		}
			                	}
			                }
			         
			                // Vuelve a habilitar el ItemListener
			                cbPlan.addItemListener(this);   
			                cargaEnProgreso = false;
			            }		 
			        }
			}		
		});
		modeloComboPlan = new DefaultComboBoxModel<String>();
		cbPlan.setModel(modeloComboPlan);
		panelNorte.add(cbPlan);
		
		cargaComboPlan();
				
		JLabel lblNewLabel = new JLabel("Año de cursada:");
		panelNorte.add(lblNewLabel);
		
		selectorAnio = new JYearChooser();
		panelNorte.add(selectorAnio);
		
		btnActualizarTabla = new JButton("Actualizar tabla");
		btnActualizarTabla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verListadoAlumnos();
			}
		});
		
		label_3 = new JLabel("          ");
		panelNorte.add(label_3);
		panelNorte.add(btnActualizarTabla);
		
		panelCentro = new JPanel();
		contentPane.add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(null);
		
		scrollNotasParciales = new JScrollPane();
		scrollNotasParciales.setBounds(10, 11, 1300, 600);
		panelCentro.add(scrollNotasParciales);
		
		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//guardar datos en materiaalumno
				//ver VentanaActualizaNotaMesa
				guardarCambiosDeNotasParciales();
			}
		});
		panelSur.add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verListadoAlumnos();
				//dispose();
			}
		});
		panelSur.add(btnCancelar);
		
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

	protected void cambiarMateriasdelCombo() {
		/*
		 * Este método actualiza el combo de las materias cuando se cambia un plan desde el otro combo
		 */
		try{
			String plan=(String) cbPlan.getSelectedItem();
			Conexion conex = new Conexion();
			ResultSet resMat = null;
			
			String consulta = "SELECT codmat from materia where plan=? order by anio";
			PreparedStatement estatutoMat = conex.getConnection().prepareStatement(consulta);
			estatutoMat.setString(1,plan);
			
			resMat = estatutoMat.executeQuery();
			
			while (resMat.next()){
				
				modeloComboMaterias.addElement((String) resMat.getObject(1));
	
			}
			resMat.close();
			estatutoMat.close();
			conex.desconectar();
		}		catch (SQLException e){
					JOptionPane.showMessageDialog(null, "Error al consultar materias","Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void guardarCambiosDeNotasParciales() {
		
		AlumnomateriaDAO miAlumnomateriaDAO = new AlumnomateriaDAO();
		miAlumnomateriaDAO.actualizaNotasParciales(tablaNotasParciales);
	}

	protected void verListadoAlumnos() {
		/**
		 * Actualiza la tabla de alumnos y notas parciales cuando se cambia una materia del cbMaterias
		 */
		String codigo=(String) cbMaterias.getSelectedItem();
		MateriaDAO miMateriaDAO = new MateriaDAO();
		lblMatnom.setText(miMateriaDAO.darNombreMateria(codigo));
		lblCurso.setText(String.valueOf(miMateriaDAO.darAnioMateria(codigo))+"°");
		
		
		 if (selectorAnio != null) {
		        int anio = selectorAnio.getValue();
		        iniciarTabla();
		        AlumnomateriaDAO miAlumnomateriaDAO = new AlumnomateriaDAO();
		        miAlumnomateriaDAO.cargarNotasParciales(tablaNotasParciales.getModel(), codigo, anio);
		    } else {
		        // Maneja el caso en que selectorAnio es nulo, por ejemplo, muestra un mensaje de error.
		        JOptionPane.showMessageDialog(this, "Error: selectorAnio no está inicializado.");
		    }
		
	}
	
	private void iniciarTabla() {
		tablaNotasParciales = new JTable();
		modeloNotasParciales = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Apellido y nombre","1° Parc.", "1° Rec.","2° Parc.","2° Rec.","3° Rec.","Situación", "Nota Final",
				"Libro","Folio","codAlMat"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class,String.class, String.class, String.class,String.class, String.class, String.class,
				Integer.class,Integer.class,Integer.class,Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, true, true, true, true, true, false, false, false,false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		
		tablaNotasParciales.setModel(modeloNotasParciales);
		
		//Para centrar los valores en las columnas
		DefaultTableCellRenderer alinear = new DefaultTableCellRenderer();
		alinear.setHorizontalAlignment(SwingConstants.CENTER);
		
		// justificado a izquierda
		DefaultTableCellRenderer izquierda = new DefaultTableCellRenderer();
		izquierda.setHorizontalAlignment(SwingConstants.LEFT);
		
		//columna Apellido y nombre
		tablaNotasParciales.getColumnModel().getColumn(0).setMinWidth(200);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(0).setMinWidth(200);
		tablaNotasParciales.getColumnModel().getColumn(0).setCellRenderer(izquierda);
		
		//columna nota 1° parcial
		tablaNotasParciales.getColumnModel().getColumn(1).setMaxWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(1).setMinWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(1).setMinWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(1).setCellRenderer(alinear);
		
		//columna nota 1° recuperatorio
		tablaNotasParciales.getColumnModel().getColumn(2).setMaxWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(2).setMinWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(2).setMinWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(2).setCellRenderer(alinear);
		
		//columna nota 2° parcial
		tablaNotasParciales.getColumnModel().getColumn(3).setMaxWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(3).setMinWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(3).setMinWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(3).setCellRenderer(alinear);
		
		//columna nota 2° recuperatorio
		tablaNotasParciales.getColumnModel().getColumn(4).setMaxWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(4).setMinWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(4).setMinWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(4).setCellRenderer(alinear);
		
		//columna nota 3° recuperatorio
		tablaNotasParciales.getColumnModel().getColumn(5).setMaxWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(5).setMinWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(5).setMinWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(5).setCellRenderer(alinear);
		
		//columna situación
		tablaNotasParciales.getColumnModel().getColumn(6).setMaxWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(6).setMinWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(6).setMinWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(6).setCellRenderer(alinear);
		
		//columna nota final
		tablaNotasParciales.getColumnModel().getColumn(7).setMaxWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(7).setMinWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(7).setMinWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(7).setCellRenderer(alinear);
		
		//columna libro
		tablaNotasParciales.getColumnModel().getColumn(8).setMaxWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(8).setMinWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(8).setMinWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(8).setCellRenderer(alinear);
		
		//columna folio
		tablaNotasParciales.getColumnModel().getColumn(9).setMaxWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(9).setMinWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(9).setMaxWidth(80);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(9).setMinWidth(80);
		tablaNotasParciales.getColumnModel().getColumn(9).setCellRenderer(alinear);

		//columna codAlMat clave principal de la tabla, está oculta
		tablaNotasParciales.getColumnModel().getColumn(10).setMaxWidth(0);
		tablaNotasParciales.getColumnModel().getColumn(10).setMinWidth(0);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(10).setMaxWidth(0);
		tablaNotasParciales.getTableHeader().getColumnModel().getColumn(10).setMinWidth(0);
		
		//especifica cual es la columna que va a tener el renderizado (el combobox)
		agregarEditor(tablaNotasParciales.getColumnModel().getColumn(1));
		agregarEditor(tablaNotasParciales.getColumnModel().getColumn(2));
		agregarEditor(tablaNotasParciales.getColumnModel().getColumn(3));
		agregarEditor(tablaNotasParciales.getColumnModel().getColumn(4));
		agregarEditor(tablaNotasParciales.getColumnModel().getColumn(5));
		agregarEditor2(tablaNotasParciales.getColumnModel().getColumn(6));
		
		scrollNotasParciales.setViewportView(tablaNotasParciales);
	}
	
	/**
	 *  Establece el editor para la columna de la tabla
	 * @param columna
	 */
	public void agregarEditor(TableColumn columna) {
        JComboBox<String> cbNotas = new JComboBox<String>();
        cbNotas.addItem("    ");
        cbNotas.addItem("AUSENTE");
        cbNotas.addItem(" EQUIV ");
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
	 *  Establece el editor para la columna de la tabla
	 * @param columna
	 */
	public void agregarEditor2(TableColumn columna) {
        JComboBox<String> cbSituacion = new JComboBox<String>();
        cbSituacion.addItem("   ");
        cbSituacion.addItem("A FINAL");
        cbSituacion.addItem("RECURSA");
        columna.setCellEditor(new DefaultCellEditor(cbSituacion));
	}
}
