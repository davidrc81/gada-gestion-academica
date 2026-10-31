package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class MateriaCorrelativaDAO {

	public void registrarCorrelativa(MateriaCorrelativaVO miMateriaCorrelativaVO, int cantMat) {			
			
			try {
				Conexion conex= new Conexion();
				Statement estatuto = conex.getConnection().createStatement();
				estatuto.executeUpdate("INSERT INTO materiacorrelativa VALUES"
						+ " ('"+miMateriaCorrelativaVO.getCodCorrelativa()+"', '"
						+miMateriaCorrelativaVO.getCodMateria()+"')");
				
				if (cantMat == 0){
				JOptionPane.showMessageDialog(null, "Se ha registrado Exitosamente",
						"Información",JOptionPane.INFORMATION_MESSAGE);
				estatuto.close();
				conex.desconectar();
				}
				
			} catch (SQLException e) {
	            System.out.println(e.getMessage());
				JOptionPane.showMessageDialog(null, "No se Registro");
			}
		
	}

	public String[] obtenerCorrelativas(String codMateria) {
		
		try{
			/*
			 * Hay que crear 2 Resulset porque cuando recorremos para contar la cantidad de
			 * registros que trae queda al final, y si hacemos res.first() se queda en el 
			 * segundo y nos perdemos uno.
			 */
			Conexion conex = new Conexion();
			String consulta = "SELECT codmat1 FROM materiacorrelativa where codmatcorr = ? ";
			PreparedStatement est = conex.getConnection().prepareStatement(consulta);
			est.setString(1,codMateria);
			ResultSet resCant = est.executeQuery();
			
			int cant=0;
			while (resCant.next()){
				cant++;
			}
			
			PreparedStatement estatuto = conex.getConnection().prepareStatement(consulta);
			estatuto.setString(1,codMateria);
			ResultSet res = estatuto.executeQuery();
			
			// crea un vector de longitud igual a la cantidad de correlativas		
			String fila[]= new String [cant];
		
			int i=0;
			while (res.next()){
					fila[i]=(String) res.getObject(1);
					i++;
			}
			
			res.close();
			estatuto.close();			
			conex.desconectar();
			return fila;
		}		catch (SQLException e){
					JOptionPane.showMessageDialog(null, "Error al consultar","Error",JOptionPane.ERROR_MESSAGE);
		}		
		return null;
	}

	public void eliminarCorrelativa(MateriaCorrelativaVO miMateriaCorrelativaVO, int cantMat) {
		Conexion conex= new Conexion();
		try {
			Statement estatuto = conex.getConnection().createStatement();
			estatuto.executeUpdate("DELETE FROM materiacorrelativa WHERE codmatcorr='"+miMateriaCorrelativaVO.getCodCorrelativa()
			+"' and codmat1='"+miMateriaCorrelativaVO.getCodMateria()+"'"); 
			if (cantMat==0){
            JOptionPane.showMessageDialog(null, " Se ha Eliminado Correctamente","Información",JOptionPane.INFORMATION_MESSAGE);
			estatuto.close();
			conex.desconectar();
			}
			
		} catch (SQLException e) {
            System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "<html>No se Elimino, puede tener alumnos anotados</b> o correlativas asignadas");
		}
		
		
	}

}
