package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;

import javax.swing.JOptionPane;

public class CalculaAi {
	public int calculaIdAlumnoMateria(){
		int id=1;
		
		
		Conexion con= new Conexion();
		ResultSet resId=null;
		
		try {
			String consulta = "SELECT MAX(codalumnomateria) FROM alumnomateria";
			PreparedStatement estatuto = con.getConnection().prepareStatement(consulta);
			resId = estatuto.executeQuery();
					
			while(resId.next()){
				id=resId.getInt(1)+1;
			}
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
			}
		
		//finally{
			//try {
				//resId.close();
				//con.desconectar();
				//System.out.print("este es el try del finally");
//			} catch (SQLException e) {
	//			e.printStackTrace();
		//		System.out.print("este es el catch del finally");
		//	}			
	//	}		
		return id;
	}
	
	public int calculaIdMesa(){
		int id=1;
		
		
		Conexion conex= new Conexion();
		ResultSet res=null;
		
		try {
			String consulta = "SELECT MAX(codmesa) FROM mesa";
			PreparedStatement estatuto = conex.getConnection().prepareStatement(consulta);
			res = estatuto.executeQuery();
					
			while(res.next()){
				id=res.getInt(1)+1;
			}
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
			}
		finally{
			try {
				res.close();
				conex.desconectar();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}		
		return id;
	}

	public int calculaIdAlumnoMesa() {
		int id=1;
		
		
		Conexion conex= new Conexion();
		ResultSet res=null;
		
		try {
			String consulta = "SELECT MAX(codalumesa) FROM alumnomesa";
			PreparedStatement estatuto = conex.getConnection().prepareStatement(consulta);
			res = estatuto.executeQuery();
					
			while(res.next()){
				id=res.getInt(1)+1;
			}
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
			}
		finally{
			try {
				res.close();
				conex.desconectar();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}		
		return id;	
		
	}
}
