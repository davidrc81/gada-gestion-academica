package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class AlumnomateriaDAO {

	public AlumnomateriaVO buscarMateriaAlumno(int dni, String cod) {
		Conexion conex= new Conexion();
		AlumnomateriaVO miAlumnoMateria = new AlumnomateriaVO();
		boolean existe=false;
		
		try {
			String consulta = "SELECT * FROM alumnomateria where aldni2 = ? and codmat2 = ? ";
			PreparedStatement estatuto = conex.getConnection().prepareStatement(consulta);
			estatuto.setInt(1,dni);
			estatuto.setString(2,cod);
			ResultSet res = estatuto.executeQuery();
			
			while(res.next()){
				
				existe=true;
				
				//se debe escribir el nombre de la columna de la tabla
				miAlumnoMateria.setAldni2(res.getInt("aldni2"));
				miAlumnoMateria.setCodalumnomateria(Integer.parseInt(res.getString("codalumnomateria")));
				miAlumnoMateria.setCodmat2(res.getString("codmat2"));
				miAlumnoMateria.setFechaDeCursada(Integer.parseInt(res.getString("fechadecursada")));
				miAlumnoMateria.setParcial1(res.getString("parcial1"));
				miAlumnoMateria.setParcial2(res.getString("parcial2"));
				miAlumnoMateria.setRecup1(res.getString("recup1"));
				miAlumnoMateria.setRecup2(res.getString("recup2"));
				miAlumnoMateria.setRecup3(res.getString("recup3"));
				miAlumnoMateria.setSituacion(res.getString("situacion"));
				String nota = res.getString("notafinal");
				if (nota != null) {
				
					miAlumnoMateria.setNotafinal(Integer.parseInt(res.getString("notafinal")));
					miAlumnoMateria.setLibro(Integer.parseInt(res.getString("libro")));
					miAlumnoMateria.setFolio(Integer.parseInt(res.getString("folio")));
				}
			
				miAlumnoMateria.setFecha(res.getString("fecha"));
			  	miAlumnoMateria.setTurno(res.getString("turno"));
				
			 }
			res.close();
			conex.desconectar();
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			} catch (Exception e) {
				System.out.println("Entre al catch");
					e.printStackTrace();
			}
		//finally{
			if (existe) {
				return miAlumnoMateria;
			}
			else {
				return null;				
			}
	//	}
	}
 
	public void cargarComboMateriasAlumno(DefaultComboBoxModel<String> modeloComboMateria, int dniAlum) {
		System.out.println("Entre a cargarComboMateriasAlumnno en AlumnoMateriaDAO dni: "+dniAlum);
		try{
			Conexion conex = new Conexion();
			String consulta = "SELECT codmat2 FROM alumnomateria where aldni2 = ? order by codalumnomateria";
			PreparedStatement estatutoMatAl = conex.getConnection().prepareStatement(consulta);
			estatutoMatAl.setInt(1,dniAlum);
			ResultSet resMatAl = estatutoMatAl.executeQuery();
			
			modeloComboMateria.removeAllElements();
			while (resMatAl.next()){
	
				modeloComboMateria.addElement(resMatAl.getString("codmat2"));				
			}
			resMatAl.close();
			estatutoMatAl.close();
			conex.desconectar();
			
		}		catch (SQLException e){
					JOptionPane.showMessageDialog(null, "Error al consultar materias","Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void registrarAlumnoMateria(AlumnomateriaVO miAlumnomateria, int cantMat) {
			boolean matNueva=false;
			Conexion conex= new Conexion();
			
			String fecha=miAlumnomateria.getFecha();
			
			if (fecha!=null){
			
			try {
				Statement estatuto = conex.getConnection().createStatement();
				
				estatuto.executeUpdate("INSERT INTO alumnomateria VALUES ('"+miAlumnomateria.getCodalumnomateria()
					+"','"+miAlumnomateria.getAldni2()+"', '"+miAlumnomateria.getCodmat2()+"', '"+miAlumnomateria.getParcial1()
					+"', '"+miAlumnomateria.getParcial2()+"', '"+miAlumnomateria.getRecup1()+"', '"+miAlumnomateria.getRecup2()
					+"', '"+miAlumnomateria.getRecup3()+"', '"+miAlumnomateria.getSituacion()+"', '"+miAlumnomateria.getNotafinal()
					+"', '"+miAlumnomateria.getLibro()+"', '"+miAlumnomateria.getFolio()+"', '"+miAlumnomateria.getFecha()+"', '"+
					miAlumnomateria.getFechaDeCursada()+"', '"+miAlumnomateria.getTurno()+"')");
	
				JOptionPane.showMessageDialog(null, "Se ha registrado Exitosamente","Información",JOptionPane.INFORMATION_MESSAGE);
			//	estatuto.close();
			  //  conex.desconectar();
				
			} catch (SQLException e) {
	            System.out.println(e.getMessage());
				JOptionPane.showMessageDialog(null, "No se Registro");
			}
			}else{
				//fecha vacia
				try {
					matNueva=true;
					
					Statement estatuto = conex.getConnection().createStatement();
				estatuto.executeUpdate("INSERT INTO alumnomateria (`codalumnomateria`,`aldni2`,`codmat2`,`parcial1`,`parcial2`,`recup1`,`recup2`,"
				+ "`recup3`,`situacion`,`fechadecursada`,`turno`) VALUES ('"+miAlumnomateria.getCodalumnomateria()
				+"','"+miAlumnomateria.getAldni2()+"', '"+miAlumnomateria.getCodmat2()+"', '"+miAlumnomateria.getParcial1()
				+"', '"+miAlumnomateria.getParcial2()+"', '"+miAlumnomateria.getRecup1()+"', '"+miAlumnomateria.getRecup2()
				+"', '"+miAlumnomateria.getRecup3()+"', '"+miAlumnomateria.getSituacion()+"', '"+miAlumnomateria.getFechaDeCursada()+"', '"+miAlumnomateria.getTurno()+"')");
			
					
				if (cantMat==0){
					JOptionPane.showMessageDialog(null, "Se ha registrado Exitosamente","Información",JOptionPane.INFORMATION_MESSAGE);
					estatuto.close();
					conex.desconectar();
				}
				
			
		} catch (SQLException e) {
            System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "No se Registro");
		}
	}
	}

	public void modificarAlumnoMateria(AlumnomateriaVO miAlumnomateria) {

			
			Conexion conex= new Conexion();
			
            String fecha=miAlumnomateria.getFecha();
          
			if (fecha!=null){
			
			try {
				
				String consulta="UPDATE alumnomateria SET parcial1= ? ,parcial2 = ? , recup1=? , recup2=? , recup3= ?, situacion=? ,"
						+ "notafinal= ?,libro= ?,folio= ?,fecha= ?,turno= ? WHERE aldni2= ? and codmat2=? ";
				PreparedStatement estatuto = conex.getConnection().prepareStatement(consulta);
			
				estatuto.setString(1, miAlumnomateria.getParcial1());
	            estatuto.setString(2, miAlumnomateria.getParcial2());
	            estatuto.setString(3, miAlumnomateria.getRecup1());
	            estatuto.setString(4, miAlumnomateria.getRecup2());
	            estatuto.setString(5, miAlumnomateria.getRecup3());
	            estatuto.setString(6, miAlumnomateria.getSituacion());
	            estatuto.setInt(7, miAlumnomateria.getNotafinal());
	            estatuto.setInt(8, miAlumnomateria.getLibro());
	            estatuto.setInt(9, miAlumnomateria.getFolio());
	            estatuto.setString(10, miAlumnomateria.getFecha());
	            estatuto.setString(11,miAlumnomateria.getTurno());
	            estatuto.setInt(12, miAlumnomateria.getAldni2());
	            estatuto.setString(13, miAlumnomateria.getCodmat2());
				
	            estatuto.executeUpdate();
				
		          JOptionPane.showMessageDialog(null, " Se ha Modificado Correctamente ","Confirmación",JOptionPane.INFORMATION_MESSAGE);
		         
		        }catch(SQLException	 e){

		            System.out.println(e);
		            JOptionPane.showMessageDialog(null, "Error al Modificar","Error",JOptionPane.ERROR_MESSAGE);
		        }
			
			}else{
				try{
					
				String consulta="UPDATE alumnomateria SET parcial1= ? ,parcial2 = ? , recup1=? , recup2=? , recup3= ?, situacion=?"
						+ ",fechadecursada=?,turno= ?  WHERE aldni2= ? and codmat2=?";
				PreparedStatement estatuto = conex.getConnection().prepareStatement(consulta);
			
				estatuto.setString(1, miAlumnomateria.getParcial1());
	            estatuto.setString(2, miAlumnomateria.getParcial2());
	            estatuto.setString(3, miAlumnomateria.getRecup1());
	            estatuto.setString(4, miAlumnomateria.getRecup2());
	            estatuto.setString(5, miAlumnomateria.getRecup3());
	            estatuto.setString(6, miAlumnomateria.getSituacion());
	            estatuto.setInt(7, miAlumnomateria.getFechaDeCursada());
	            estatuto.setString(8, miAlumnomateria.getTurno());
	            estatuto.setInt(9, miAlumnomateria.getAldni2());
	            estatuto.setString(10, miAlumnomateria.getCodmat2());
				
	            estatuto.executeUpdate();
				
		          JOptionPane.showMessageDialog(null, " Se ha Modificado Correctamente ","Confirmación",JOptionPane.INFORMATION_MESSAGE);
		         
		        }catch(SQLException	 e){

		            System.out.println(e);
		            JOptionPane.showMessageDialog(null, "Error al Modificar","Error",JOptionPane.ERROR_MESSAGE);
		        }		
	}
			
	}

	public void eliminarAlumnoMateria(int numAiBaja) {
	 	Conexion conex= new Conexion();
		try {
			Statement estatuto = conex.getConnection().createStatement();
			estatuto.executeUpdate("DELETE FROM alumnomateria WHERE codalumnomateria='"+numAiBaja+"'");
            JOptionPane.showMessageDialog(null, " Se ha Eliminado Correctamente","Información",JOptionPane.INFORMATION_MESSAGE);
			estatuto.close();
			conex.desconectar();
			
		} catch (SQLException e) {
            System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "No se Elimino");
		}		
	}

	public void buscarAlumnosParaMesa(DefaultListModel<String> modelo, String codMateria, String[] vecCorr, String situacion, String turno) {
	
		try{
			Conexion conex = new Conexion();
		
			String encabezado= "SELECT am.fechadecursada,am.situacion,am.aldni2,a.alapynom"
					+ " from alumnomateria AS am, alumno AS a where am.aldni2=a.aldni and codmat2= ? "
					+ "and situacion=? and turno=? and (fechadecursada+5) >= year(curdate()) and notafinal is null ";
		String repite1 = " and aldni in (SELECT aldni2 from alumnomateria where codmat2='";
		String repite2 ="' and notafinal>='4'";
		String unico =" and am.aldni2 not in(select alumnomesa.aldni from alumnomesa,mesa where "
					+ "alumnomesa.codmesa=mesa.codmesa and mesa.codmat= ? and mesanota is null)";
		String parentesis = ")";
		String pie = " order by alapynom";
		
		String consulta=encabezado;
		for (int i=0;i<(vecCorr.length);i++){
			consulta+=repite1+vecCorr[i]+repite2;
		}
		consulta+= unico;
		for (int i=0;i<vecCorr.length;i++){
			consulta+= parentesis;
		}
		consulta+= pie;
	
		System.out.println(consulta);
		
	PreparedStatement estatuto = conex.getConnection().prepareStatement(consulta);
	estatuto.setString(1,codMateria);
	estatuto.setString(2,situacion);
	estatuto.setString(3, turno);
	estatuto.setString(4, codMateria);
	
	/*if (vecCorr.length > 0){
	estatuto.setString(3, vecCorr[vecCorr.length-1]);
	estatuto.setString(3, codMateria);
	} else {
		estatuto.setString(3, codMateria);
	}*/
	
	ResultSet res = estatuto.executeQuery();
			
	while (res.next()){
		String cadena="";
		for (int i=0; i<4;i++)
			cadena+=res.getObject(i+1)+"  ";
		modelo.addElement(cadena);
	}	
	res.close();
	estatuto.close();
	conex.desconectar();
}		catch (SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al consultar por las materias correlativas","Error",JOptionPane.ERROR_MESSAGE);
}				
	}

	public void cargarNotasParciales(TableModel model, String codigo, int anio) {
			
			Conexion conex = new Conexion();
			try{
				String consulta = "SELECT alapynom,parcial1,recup1,parcial2,recup2,recup3,situacion,notafinal,"
						+ "libro,folio,codalumnomateria FROM alumnomateria INNER JOIN alumno ON alumnomateria.aldni2=alumno.aldni "
						+ "where codmat2=? and fechadecursada=? order by alapynom ";
		
				PreparedStatement estatuto = conex.getConnection().prepareStatement(consulta);
				estatuto.setString(1,codigo);
				estatuto.setInt(2, anio);
				ResultSet res = estatuto.executeQuery();
						
				while (res.next()){
					Object fila[]= new Object[11];
					for (int i=0; i<11;i++)
						fila[i]=res.getObject(i+1);
					((DefaultTableModel) model).addRow(fila);
				}
				
				res.close();
				estatuto.close();
				conex.desconectar();
			}		catch (SQLException e){
						JOptionPane.showMessageDialog(null, "Error al consultar","Error",JOptionPane.ERROR_MESSAGE);
			}			
			
		}

	public void actualizaNotasParciales(JTable tablaNotasParciales) {
		int fila=tablaNotasParciales.getRowCount();
		boolean grabo=false;
		String parcial1, recu1, parcial2, recu2, recu3, situacion;
		int codAlMat;
		
		try {
			Conexion conex = new Conexion();
		
		
		for (int i=0; i<fila;i++){
			parcial1=tablaNotasParciales.getValueAt(i, 1).toString();
			recu1=tablaNotasParciales.getValueAt(i, 2).toString();
			parcial2=tablaNotasParciales.getValueAt(i, 3).toString();
			recu2=tablaNotasParciales.getValueAt(i, 4).toString();
			recu3=tablaNotasParciales.getValueAt(i, 5).toString();
			situacion=tablaNotasParciales.getValueAt(i, 6).toString();
			codAlMat=Integer.valueOf(tablaNotasParciales.getValueAt(i, 10).toString());
			
			// actualiza la nota del alumno en la tabla alumnomateria
			String consul="UPDATE alumnomateria SET  parcial1= ?,parcial2 = ? , recup1=? , recup2=? , recup3= ?, situacion=?"
					+ " WHERE codalumnomateria= ? ";
			PreparedStatement estatuto = conex.getConnection().prepareStatement(consul);
			
            estatuto.setString(1, parcial1);
            estatuto.setString(2, parcial2);
            estatuto.setString(3, recu1);
            estatuto.setString(4, recu2);
            estatuto.setString(5, recu3);
            estatuto.setString(6, situacion);
            estatuto.setInt(7, codAlMat);
            
            estatuto.executeUpdate();
            grabo=true;
		}// sale del for
		
		} catch (SQLException e){
			JOptionPane.showMessageDialog(null, "Error al actualizar tabla","Error",JOptionPane.ERROR_MESSAGE);
		}
		if (grabo)
			JOptionPane.showMessageDialog(null, "Se guardó correctamente","Información",JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void buscarAlumnosCondicionalesParaMesa(DefaultListModel<String> modelo, String codMateria,
			String[] vecCorr, String situacion, String turno) {
		try{
			Conexion conex = new Conexion();
			
		String encabezado1="SELECT am.fechadecursada,am.situacion,am.aldni2,a.alapynom"
					+ " from alumnomateria AS am, alumno AS a where am.aldni2=a.aldni and codmat2= ? "
					+ "and situacion=? and turno=? and (fechadecursada+5) >= year(curdate()) and notafinal is null "
					+ "and am.aldni2 not in(SELECT ame.aldni from alumnomesa AS ame, mesa AS me "
					+ "where ame.codmesa=me.codmesa and me.codmat= ? and ame.mesanota is null) "
					+ "and am.aldni2 not in (";
			
		String encabezado= "SELECT am.aldni2"
				+ " from alumnomateria AS am, alumno AS a where am.aldni2=a.aldni and codmat2= ? "
				+ "and situacion=? and (fechadecursada+5) >= year(curdate()) and notafinal is null ";
		
		String repite1 = " and aldni in (SELECT aldni2 from alumnomateria where codmat2='";
		String repite2 ="' and notafinal>='4'";
		
		String unico =" and am.aldni2 not in(select alumnomesa.aldni from alumnomesa,mesa where "
					+ "alumnomesa.codmesa=mesa.codmesa and mesa.codmat= ? and mesanota is null)";
		String parentesis = ")";
		String pie = " order by alapynom";
		
		String consulta=encabezado1+encabezado;
		
		for (int i=0;i<(vecCorr.length);i++){
			consulta+=repite1+vecCorr[i]+repite2;
		}
		consulta+= unico;
		for (int i=0;i<vecCorr.length;i++){
			consulta+= parentesis;
		}
		consulta+=")";
		consulta+= pie;
		
		//System.out.println(consulta); //muestra la consulta armada por consola
		
		
	PreparedStatement estatuto = conex.getConnection().prepareStatement(consulta);
	estatuto.setString(1,codMateria);
	estatuto.setString(2, situacion);
	estatuto.setString(3, turno);
	estatuto.setString(4,codMateria);
	estatuto.setString(5, codMateria);
	estatuto.setString(6, situacion);
	estatuto.setString(7, codMateria);
	
	ResultSet res = estatuto.executeQuery();
			
	while (res.next()){
		String cadena="";
		for (int i=0; i<4;i++)
			cadena+=res.getObject(i+1)+"  ";
		modelo.addElement(cadena+" * ");
	}	
	res.close();
	estatuto.close();
	conex.desconectar();
}		catch (SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al consultar alumnos condicionales","Error",JOptionPane.ERROR_MESSAGE);
}				
	}
	
	}	

