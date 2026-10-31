package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.Coordinador;
import modelo.MateriaVO;

public class VentanaMateriaBuscar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Coordinador miCoordinador;
	private JScrollPane scrollPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMateriaBuscar frame = new VentanaMateriaBuscar();
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
	public VentanaMateriaBuscar() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panelBoton = new JPanel();
		contentPane.add(panelBoton, BorderLayout.SOUTH);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panelBoton.add(btnVolver);
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}
	
	public void mostrarDatosMateriaConTableModel(int btn, String codMat, String nomMat, String plan) {
		DefaultTableModel modeloMateria = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hace que las celdas de la tabla sean de solo lectura
			}
		};
		
		table = new JTable();
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*
				 * Pasa los datos a la VentanaMateria a través del Coordinador
				 */
				if (miCoordinador != null) {
					miCoordinador.pasarDatosMateria(pasarDatosMateria(e));
				}
			}
		});
		
		table.setModel(modeloMateria);
		modeloMateria.addColumn("Código");
		modeloMateria.addColumn("Materia");
		modeloMateria.addColumn("Curso");
		modeloMateria.addColumn("Plan");
		modeloMateria.addColumn("Módulos");
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setReorderingAllowed(false);
		
		// Establecer título de la ventana según el botón presionado
		if (btn == 1) {
			setTitle("Materias ordenadas por CURSO");
		} else if (btn == 2) {
			setTitle("Materias ordenadas por CÓDIGO");
		} else if (btn == 3) {
			setTitle("Materias ordenadas por NOMBRE");
		}
		
		// Solicitud de la lista de materias al Coordinador y llenado de la tabla
		if (miCoordinador != null) {
			List<MateriaVO> listaMaterias = miCoordinador.buscarMateriasPorFiltro(btn, codMat, nomMat, plan);
			if (listaMaterias != null) {
				for (MateriaVO mat : listaMaterias) {
					modeloMateria.addRow(new Object[] {
						mat.getCodmat(),
						mat.getMatnom(),
						mat.getAnio(),
						mat.getPlan(),
						mat.getModulos()
					});
				}
			}
		}
		
		scrollPane.setViewportView(table);
	}

	protected MateriaVO pasarDatosMateria(MouseEvent e) {
		MateriaVO miMateria = new MateriaVO();
		int row = table.rowAtPoint(e.getPoint());
		if (row != -1) {
			miMateria.setCodmat(table.getValueAt(row, 0).toString());
			miMateria.setMatnom(table.getValueAt(row, 1).toString());
			miMateria.setAnio(Integer.parseInt(table.getValueAt(row, 2).toString()));
			miMateria.setPlan(table.getValueAt(row, 3).toString());
			miMateria.setModulos(Integer.parseInt(table.getValueAt(row, 4).toString()));
		}
		return miMateria;
	}
}