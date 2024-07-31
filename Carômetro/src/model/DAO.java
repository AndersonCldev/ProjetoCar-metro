package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
	private Connection con;
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/dbcadastroCanditado";
	private String user = "root";
	private String password = "";
// Não esqueça de configura a class "DAO" para conseguir ultilizar o programa em sua maquina alterando "url","user" e "password".
	
	public Connection conectar() {
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,password);
			return con;
			
		}catch (Exception e ) {
			
			System.out.println(e);
			return null;
		}
	}
}
