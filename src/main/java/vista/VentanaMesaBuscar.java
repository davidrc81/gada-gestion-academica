package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controlador.Coordinador;
import modelo.MesaDAO;
import modelo.MesaVO;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class VentanaMesaBuscar extends JFrame {

	private JPanel contentPane;
	private Coordinador miCoordinador;
	private JScrollPane scrollPane;
	private JTable tablaMesa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMesaBuscar frame = new VentanaMesaBuscar();
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
	public VentanaMesaBuscar() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnVolver);
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	public void mostrarDatosMesaConTableModel(int btn, String codMat, int ventana) {
		DefaultTableModel modeloMesa = new DefaultTableModel();
		tablaMesa = new JTable();
		
		tablaMesa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (ventana==1)
					miCoordinador.pasarDatosMesa(pasarDatosMesa(e));  //  pasa los datos a la VentanaMesa 
				if (ventana==2)
					miCoordinador.pasarDatosAlumnoMesa(pasarDatosMesa(e)); // pasa los datos a VentanaAlumnoMesa para armar mesas
				if (ventana==5)
					miCoordinador.pasarDatosNotaAlumnoMesa(pasarDatosMesa(e)); // pasa a VentanaActualizaNotaMesa
				if (ventana==6)
					miCoordinador.pasarDatosActaVolante(pasarDatosMesa(e)); // pasa a VentanaAlumnoMesa para hacer reporte Acta volante
				if (ventana==7)
					miCoordinador.pasarDatosAlumnoMesaQuitar(pasarDatosMesa(e)); //pasa los datos a VentanaAlumnoMesa para quitar alumnos de una mesa
				if (ventana==8)
					miCoordinador.pasarDatosAlumnoCondicionalMesa(pasarDatosMesa(e)); 
				// pasa los datos a VentanaAlumnoMesa para agregar los alumnos condicionales
			}
		});
		tablaMesa.setModel(modeloMesa);
		modeloMesa.addColumn("Materia");
		modeloMesa.addColumn("Fecha");
		modeloMesa.addColumn("Llamado");
		modeloMesa.addColumn("Libro");
		modeloMesa.addColumn("Folio");
		modeloMesa.addColumn("Situación");
		modeloMesa.addColumn("CodigoMesa");
		modeloMesa.addColumn("Estado");
		modeloMesa.addColumn("Turno");
		
		//tablaMesa.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		//tablaMesa.getTableHeader().setReorderingAllowed(false);
		
		//Para centrar los valores en las columnas
		DefaultTableCellRenderer alinear = new DefaultTableCellRenderer();

		alinear.setHorizontalAlignment(SwingConstants.CENTER);
				
		//columna Fecha
		tablaMesa.getColumnModel().getColumn(1).setMaxWidth(80);
		tablaMesa.getColumnModel().getColumn(1).setMinWidth(80);
		tablaMesa.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(80);
		tablaMesa.getTableHeader().getColumnModel().getColumn(1).setMinWidth(80);
		tablaMesa.getColumnModel().getColumn(1).setCellRenderer(alinear);
		
		tablaMesa.getColumnModel().getColumn(6).setMaxWidth(0);
		tablaMesa.getColumnModel().getColumn(6).setMinWidth(0);
		tablaMesa.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
		tablaMesa.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
		
		MesaDAO miMesaDAO = new MesaDAO();
		if (btn==1){
			miMesaDAO.buscarMesas(modeloMesa);
			setTitle("Mesas ordenadas por Fecha");
		}
		if (btn==2){
			miMesaDAO.buscarMesasMateria(modeloMesa,codMat);
			setTitle("Mesas por Materia");
		}
		if (btn==3){
			miMesaDAO.buscarMesasNuevasMateria(modeloMesa, codMat);
			setTitle("Mesas disponibles por Materia");
		}
		if (btn==4){
			miMesaDAO.buscarMesasCerradasMateria(modeloMesa, codMat);
			setTitle("Mesas cerradas por Materia");
		}
		if (btn==5){
			miMesaDAO.buscarMesasParaActaVolante(modeloMesa,codMat);
			setTitle("Mesas para imprimir Acta volante");
		}
		scrollPane.setViewportView(tablaMesa);
	}

	protected MesaVO pasarDatosMesa(MouseEvent e) {
		
		MesaVO miMesaVO = new MesaVO();
		int row=tablaMesa.rowAtPoint(e.getPoint());
		miMesaVO.setCodmat(tablaMesa.getValueAt(row, 0).toString());
		miMesaVO.setMesafecha(tablaMesa.getValueAt(row, 1).toString());
		miMesaVO.setMesallamado(Integer.valueOf(tablaMesa.getValueAt(row, 2).toString()));
		miMesaVO.setMesalibro(Integer.valueOf(tablaMesa.getValueAt(row, 3).toString()));
		miMesaVO.setMesafolio(Integer.valueOf(tablaMesa.getValueAt(row, 4).toString()));
		miMesaVO.setMesasituacion(tablaMesa.getValueAt(row, 5).toString());
		miMesaVO.setCodmesa(Integer.valueOf(tablaMesa.getValueAt(row, 6).toString()));
		miMesaVO.setTurno(tablaMesa.getValueAt(row, 8).toString());
		return miMesaVO;
	}

}

