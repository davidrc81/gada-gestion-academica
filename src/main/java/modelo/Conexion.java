package modelo;


import java.sql.*;


/**
 * Clase que permite conectar con la base de datos
 */
public class Conexion {
   static String bd = "basegada";
   static String login = "root";
   static String password = "admin";
 
   //String url= "jdbc:mysql://localhost:3306/mesaexamen?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
   String url= "jdbc:mysql://localhost:3306/basegada?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Argentina/Buenos_Aires";
   Connection conn = null;

   /** Constructor de DbConnection */
   public Conexion() {
      try{
         //obtenemos el driver de para mysql
         Class.forName("com.mysql.cj.jdbc.Driver");
         //obtenemos la conexi�n
         conn = DriverManager.getConnection(url,login,password);

         if (conn!=null){
            //System.out.println("Conección a base de datos "+bd+" OK");
         }
      }
      catch(SQLException e){
         System.out.println(e);
      }catch(ClassNotFoundException e){
         System.out.println(e);
      }catch(Exception e){
         System.out.println(e);
      }
   }
   /**
    * Permite retornar la conexión
    * */
   public Connection getConnection(){
      return conn;
   }
   /**
    * Finaliza la conexión con la Base de Datos
    */
   public void desconectar(){
      conn = null;
   }

}