package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import util.BDConfigs;

public class ConexaoMysql {

	private Connection conexao;

	public ConexaoMysql() {
		
	}

	public Connection getConexao() {
		return conexao;
	}

	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}

	public void abrirConexao() {
		try {
			String url = "jdbc:mysql://"+BDConfigs.IP+":"+BDConfigs.PORTA+"/"+BDConfigs.NOME_BD;
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.conexao = DriverManager.getConnection(url, BDConfigs.USUARIO, BDConfigs.SENHA);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void fecharConexao() {
		if (this.conexao != null) {
			try {
				this.conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}