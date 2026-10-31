package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import com.toedter.calendar.JYearChooser;

import controlador.Coordinador;

public class VentanaReporteListaAlumnos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Coordinador miCoordinador;
	private JScrollPane scrollPaneTotal;
	private JList<String> listaTotal;
	private DefaultListModel<String> modeloTotal;
	private JScrollPane scrollPaneImprime;
	private JList<String> listaImprime;
	private DefaultListModel<String> modeloImprime;
	private JYearChooser selectorAnio;
	private JComboBox<String> cbPlan;
	private static DefaultComboBoxModel<String> modeloComboPlan;
	private boolean primeraCarga = false; // Bandera para determinar si es la primera carga del cbPlan

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaReporteListaAlumnos frame = new VentanaReporteListaAlumnos();
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
	public VentanaReporteListaAlumnos() {
		setTitle("Imprime listas de asistencia de alumnos por curso");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 740, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelNorte = new JPanel();
		panelNorte.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		JLabel lblAoDeCursada = new JLabel("Año de cursada:");
		panelNorte.add(lblAoDeCursada);
		
		selectorAnio = new JYearChooser();
		panelNorte.add(selectorAnio);
		
		JLabel lblNewLabel_1 = new JLabel("                          ");
		panelNorte.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Plan:");
		panelNorte.add(lblNewLabel);
		
		cbPlan = new JComboBox<String>();
		cbPlan.setEditable(false);
		cbPlan.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (!primeraCarga) {
						primeraCarga = true;				
					} else {
						mostrarTodasLasMaterias();
					}		
				}
			}
		});
		
		modeloComboPlan = new DefaultComboBoxModel<String>();
		cbPlan.setModel(modeloComboPlan);
		panelNorte.add(cbPlan);
		
		JPanel panelCentro = new JPanel();
		contentPane.add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(null);
		
		scrollPaneTotal = new JScrollPane();
		scrollPaneTotal.setBounds(0, 11, 320, 272);
		panelCentro.add(scrollPaneTotal);
		
		modeloTotal = new DefaultListModel<String>();
		listaTotal = new JList<String>();
		listaTotal.setForeground(Color.BLUE);
		listaTotal.setBackground(new Color(255, 250, 240));
		listaTotal.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPaneTotal.setViewportView(listaTotal);
		
		scrollPaneImprime = new JScrollPane();
		scrollPaneImprime.setBounds(382, 11, 320, 272);
		panelCentro.add(scrollPaneImprime);
		
		modeloImprime = new DefaultListModel<String>();
		listaImprime = new JList<String>();
		listaImprime.setForeground(Color.BLACK);
		listaImprime.setBackground(new Color(255, 250, 240));
		scrollPaneImprime.setViewportView(listaImprime);
		
		JButton btnPasarUno = new JButton(">");
		btnPasarUno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pasarMateria();
			}
		});
		btnPasarUno.setBounds(325, 107, 51, 23);
		panelCentro.add(btnPasarUno);
		
		JButton btnTraerUno = new JButton("<");
		btnTraerUno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				traerMateria();
			}
		});
		btnTraerUno.setBounds(325, 157, 51, 23);
		panelCentro.add(btnTraerUno);
		
		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearRepoListaAlumnos();
			}
		});
		panelSur.add(btnImprimir);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panelSur.add(btnCancelar);
	}

	protected void crearRepoListaAlumnos() {
		int anioCursada = selectorAnio.getValue();
		if (miCoordinador != null) {
			miCoordinador.crearReporteListaAlumnos(modeloImprime, anioCursada);
		}
	}

	protected void traerMateria() {
		if (listaImprime.getSelectedValue() != null) {
			modeloTotal.addElement(listaImprime.getSelectedValue());
			listaTotal.setModel(modeloTotal);
			modeloImprime.removeElement(listaImprime.getSelectedValue());
		}
	}

	protected void pasarMateria() {
		List<String> seleccion = listaTotal.getSelectedValuesList();
		Iterator<String> i = seleccion.iterator();
		int a = 0;
		while (i.hasNext()) {
			modeloImprime.addElement(i.next());
			modeloTotal.removeElement(seleccion.get(a));
			a++;
		}
		listaImprime.setModel(modeloImprime);
	}
	
	public void mostrarTodasLasMaterias() {
		if (cbPlan.getSelectedItem() != null && miCoordinador != null) {
			String plan = cbPlan.getSelectedItem().toString();
			modeloTotal.clear();
			modeloImprime.clear();
			miCoordinador.cargarTodasLasMaterias(modeloTotal, plan);
			listaTotal.setModel(modeloTotal);
		}
	}

	public void setMiCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
		if (this.miCoordinador != null) {
			this.miCoordinador.cargarComboPlanes(modeloComboPlan);
			mostrarTodasLasMaterias();
		}
	}
}