package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Categoria;


public class CategoriaDAO {
	private ConexaoMysql conexaoMysql;
	
	public CategoriaDAO() {
		this.conexaoMysql = new ConexaoMysql();
	}
	
	public Categoria salvar (Categoria categoria) {
		conexaoMysql.abrirConexao();
		
		String sql = "INSERT INTO categoria VALUES (null, ?);";
		
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql, 
					PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1, categoria.getNome());
			int linhasAfetadas = st.executeUpdate();
			if(linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					long idCategoria = rs.getLong(1);
					categoria.setId(idCategoria);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		
		return categoria;
	}
	
	public boolean editar(Categoria categoria) {
		conexaoMysql.abrirConexao();
		
		String sql = "UPDATE categoria SET nome = ? WHERE id_categoria = ?;";
		
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setString(1, categoria.getNome());
			st.setLong(2, categoria.getId());
			int linhasAfetadas = st.executeUpdate();
			if (linhasAfetadas > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		
		return false;
	
	}
	
	public boolean excluirPorId(long id) {
		conexaoMysql.abrirConexao();
		
		String sql = "DELETE FROM categoria WHERE id_categoria = ?;";
		
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setLong(1, id);
			int linhasAfetadas = st.executeUpdate();
			if (linhasAfetadas > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return false;
	}
	
	public boolean excluirTodos() {
		conexaoMysql.abrirConexao();
		String sql = "DELETE FROM categoria";
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			int linhasAfetadas = st.executeUpdate();
			if (linhasAfetadas > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return false;
	}
	
	public Categoria buscarPorId(long id) {
		Categoria categoria = null;
		
		conexaoMysql.abrirConexao();
		
		String sql = "SELECT * FROM categoria WHERE id_categoria = ?;";
		
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				categoria = new Categoria();
			
				categoria.setId(rs.getLong(1));
				categoria.setNome(rs.getString(2));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return categoria;
	}
	
	public List<Categoria> buscarTodos() {
		Categoria categoria = null;
		
		List<Categoria> listaCategorias = new ArrayList<>();
	
		conexaoMysql.abrirConexao();
		
		String sql = "SELECT * FROM categoria;";
		PreparedStatement st;
		
		try {
			st = conexaoMysql.getConexao().prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				categoria = new Categoria();
		
				categoria.setId(rs.getLong(1));
				categoria.setNome(rs.getString(2));
				listaCategorias.add(categoria);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return listaCategorias;
	}
}
