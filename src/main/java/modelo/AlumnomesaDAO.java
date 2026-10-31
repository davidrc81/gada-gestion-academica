package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class AlumnomesaDAO {

	public void altaRegistroAlumnoMesa(int numAi, int doc, int codMesa, boolean condicional) {
			
		try {
			Conexion conex= new Conexion();
			Statement estatuto = conex.getConnection().createStatement();
		
			if (condicional) {			
				estatuto.executeUpdate("INSERT INTO alumnomesa (`codalumesa`,`aldni`,`condicionado`,`codmesa`) VALUES ('"+numAi+"','"+doc+"', '*', '"+codMesa+"')");
			}else {				
				estatuto.executeUpdate("INSERT INTO alumnomesa (`codalumesa`,`aldni`,`codmesa`) VALUES ('"+numAi+"','"+doc+"', '"+codMesa+"')");
			}
			
			estatuto.close();
		    conex.desconectar();
			
		} catch (SQLException e) {
            System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "No se Registro");
		}
}

	public void buscarNotas(TableModel tableModel, int codMesa) {
		
		Conexion conex = new Conexion();
		try{
			String consulta = "SELECT alumno.aldni,alapynom,mesanota,codalumesa FROM alumnomesa INNER JOIN alumno"
					+ " ON alumnomesa.aldni=alumno.aldni where codmesa=? order by alapynom ";
	
			PreparedStatement estatuto = conex.getConnection().prepareStatement(consulta);
			estatuto.setInt(1,codMesa);
			ResultSet res = estatuto.executeQuery();
					
			while (res.next()){
				Object fila[]= new Object[4];
				for (int i=0; i<4;i++)
					fila[i]=res.getObject(i+1);
				((DefaultTableModel) tableModel).addRow(fila);
			}
			
			res.close();
			estatuto.close();
			conex.desconectar();
		}		catch (SQLException e){
					JOptionPane.showMessageDialog(null, "Error al consultar","Error",JOptionPane.ERROR_MESSAGE);
		}			
		
	}
	
	
	public void actualizaNotasMesa(JTable table, String codMat, String fecha, int libro, int folio, int codMesa) {
		String nota;
		int fila=table.getRowCount();
		int codAluMesa;
		int dni;
		boolean grabo=false;
	/////
	//if (libro<=0 || folio<=0)
		try{
			Conexion conex = new Conexion();
			
			for (int i=0; i<fila;i++){
				nota= table.getValueAt(i, 2).toString();
				codAluMesa=Integer.valueOf(table.getValueAt(i,3).toString());
				dni=Integer.valueOf(table.getValueAt(i, 0).toString());
				// actualiza la nota del alumno en la tabla alumnomesa cualquiera sea la nota
				String consul="UPDATE alumnomesa SET  mesanota= ? WHERE codalumesa= ? ";
				PreparedStatement estatuto = conex.getConnection().prepareStatement(consul);
				
	            estatuto.setString(1, nota);
	            estatuto.setInt(2, codAluMesa);
	            
	            estatuto.executeUpdate();
	            grabo=true;
	           
	            if (!nota.equals("AUSENTE")){  
	            	int notaFinal=Integer.valueOf(nota.trim());
	            	if (notaFinal>=4){
	            	// actualiza la nota y demas en alumnomateria solo si aprobo el final
	            	String sql="UPDATE alumnomateria SET notafinal=?, libro=?, folio=?, fecha=? where "
	            			+ "aldni2=? and codmat2=?";
	            	
	            	PreparedStatement estatuto2 = conex.getConnection().prepareStatement(sql);
	            	
	            	estatuto2.setInt(1, notaFinal);
	            	estatuto2.setInt(2, libro);
	            	estatuto2.setInt(3, folio);
	            	estatuto2.setString(4, fecha);
	            	estatuto2.setInt(5, dni);
	            	estatuto2.setString(6, codMat);
	            	
	            	estatuto2.executeUpdate();
	            }
	            }
			}//sale del for
			
			//actualiza libro y folio de la tabla mesa
			String sql2="UPDATE mesa SET mesalibro=?, mesafolio=? where codmesa=?";
			
			PreparedStatement estatuto3 = conex.getConnection().prepareStatement(sql2);
			
			estatuto3.setInt(1, libro);
			estatuto3.setInt(2, folio);
			estatuto3.setInt(3, codMesa);
			
			estatuto3.executeUpdate();
			
			estatuto3.close();
			conex.desconectar();
			
		}catch (SQLException e){
			JOptionPane.showMessageDialog(null, "Error al actualizar tabla","Error",JOptionPane.ERROR_MESSAGE);
		}catch (NumberFormatException n){
			JOptionPane.showMessageDialog(null, "Error, falta ingresar un dato","Error",JOptionPane.ERROR_MESSAGE);
		}
		if (grabo)
			JOptionPane.showMessageDialog(null, "Se guardó correctamente","Información",JOptionPane.INFORMATION_MESSAGE);
	}

	public void buscarAlumnosMesaQuitar(DefaultListModel<String> modeloActa, int codMesa) {
		
		try{
			Conexion conex = new Conexion();
			String consulta = "SELECT alumno.aldni,alapynom FROM alumnomesa INNER JOIN alumno"
					+ " ON alumnomesa.aldni=alumno.aldni where codmesa=? order by alapynom ";
	
			PreparedStatement estatuto = conex.getConnection().prepareStatement(consulta);
			estatuto.setInt(1,codMesa);
			ResultSet res = estatuto.executeQuery();
					
			while (res.next()){
				String cadena="";
				for (int i=0; i<2;i++)
					cadena+=res.getObject(i+1)+"  ";
			modeloActa.addElement(cadena);
			}	
			
			res.close();
			estatuto.close();
			conex.desconectar();
		}		catch (SQLException e){
					JOptionPane.showMessageDialog(null, "Error al consultar","Error",JOptionPane.ERROR_MESSAGE);
		}			
		
	}

	public void bajaRegistroAlumnoMesa(int doc, int codMesa) {
		Conexion conex= new Conexion();
		try {
			Statement estatuto = conex.getConnection().createStatement();
			estatuto.executeUpdate("DELETE FROM alumnomesa WHERE aldni='"+doc+"' and codmesa='"+codMesa+"'" );
            //JOptionPane.showMessageDialog(null, " Se ha Eliminado Correctamente","Información",JOptionPane.INFORMATION_MESSAGE);
			estatuto.close();
			conex.desconectar();
			
		} catch (SQLException e) {
            System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "No se Elimino");
		}	
		
	}

	public int contarAnotadosMesa(int codigoMesa) {
		int cantidadAnotados = 0; // Variable para almacenar la cantidad de anotados
		try {
			Conexion conex = new Conexion();
			String consulta = "SELECT alumnomesa.codmesa,COUNT(*) FROM alumnomesa "
					+ "where codmesa=? ";
	
			PreparedStatement estatuto = conex.getConnection().prepareStatement(consulta);
			estatuto.setInt(1,codigoMesa);
			ResultSet res = estatuto.executeQuery();
			
			 // Verificar si hay resultados
	        if (res.next()) {
	            cantidadAnotados = res.getInt(2); // Obtener el valor del contador (segunda columna)
	        }
			
			res.close();
			estatuto.close();
			conex.desconectar();
		} catch (SQLException e) {
		 System.out.println(e.getMessage());
		JOptionPane.showMessageDialog(null, "No se encontró");
	}
		return cantidadAnotados;
	}
	}
