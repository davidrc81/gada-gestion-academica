package vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Coordinador;

public class VentanaReportes extends JFrame {

	private JPanel contentPane;
	private Coordinador miCoordinador;

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaReportes frame = new VentanaReportes();
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
	public VentanaReportes() {
		setTitle("Reportes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 331);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnListasDeAlumnos = new JButton("Listas de Alumnos");
		btnListasDeAlumnos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				miCoordinador.mostrarVentanaReporteListaAlumnos();
			}
		});
		btnListasDeAlumnos.setBounds(137, 29, 160, 23);
		contentPane.add(btnListasDeAlumnos);
		
		JButton btnAnalticos = new JButton("Analíticos");
		btnAnalticos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String opcion=JOptionPane.showInputDialog("Ingrese el Dni: ");
				//si el usuario no hizo clic en cancelar
				if (opcion!=null) {
					//tratar de convertir String a entero pero validar que este bien ingresado
					try {
						int dni=Integer.valueOf(opcion);
						miCoordinador.crearReporteAnalitico(dni);
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Debe ingresar sólo números", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				
				
			}
		});
		btnAnalticos.setBounds(137, 81, 160, 23);
		contentPane.add(btnAnalticos);
		
		JButton btnMejoresPromediosTecnicaturas = new JButton("Mejores promedios Tecnicaturas");
		btnMejoresPromediosTecnicaturas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Calendar cal= Calendar.getInstance();

				int anioActual= cal.get(Calendar.YEAR);
				int anioDesde=anioActual-2;
				int anioHasta=anioActual-1;
				
				miCoordinador.crearReportePromedioTecnicatura(anioDesde,anioHasta,anioActual);
			}
		});
		btnMejoresPromediosTecnicaturas.setBounds(102, 133, 229, 23);
		contentPane.add(btnMejoresPromediosTecnicaturas);
		
		JButton btnMejoresPromediosProfesorados = new JButton("Mejores promedios Profesorados");
		btnMejoresPromediosProfesorados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calendar cal= Calendar.getInstance();

				int anioActual= cal.get(Calendar.YEAR);
				int anioDesde=anioActual-3;
				int anioHasta=anioActual-1;
				
				miCoordinador.crearReportePromedioProfesorado(anioDesde,anioHasta,anioActual);
			}
		});
		btnMejoresPromediosProfesorados.setBounds(102, 185, 229, 23);
		contentPane.add(btnMejoresPromediosProfesorados);
		
		JButton btnPlanillasParciales = new JButton("Planillas Parciales");
		btnPlanillasParciales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				miCoordinador.mostrarVentanaReportePlanillasParciales();
			}
		});
		btnPlanillasParciales.setBounds(145, 237, 143, 23);
		contentPane.add(btnPlanillasParciales);
	}
}
