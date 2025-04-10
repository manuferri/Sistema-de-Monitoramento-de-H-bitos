package persistencia;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Habito;
import model.Usuario;

public class HabitoDAO {

	private ConexaoMysql conexaoMysql;

	public HabitoDAO() {
		this.conexaoMysql = new ConexaoMysql();
	}

	public Habito salvar(Habito habito) {
		conexaoMysql.abrirConexao();
		
		String sql = "INSERT INTO habito VALUES(null, ?, ?, ?, ?);";
		
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1, habito.getNome());
			st.setString(2, habito.getFrequencia());
			st.setLong(3, habito.getCategoria().getId());
			st.setLong(4, habito.getUsuario().getId());
			int linhasAfetadas = st.executeUpdate();
			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					habito.setId(rs.getLong(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return habito;
	}

	public boolean editar(Habito habito) {
		conexaoMysql.abrirConexao();
		
		String sql = "UPDATE habito SET nome=?, frequencia=?, id_categoria=? WHERE id_habito=?";
		
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setString(1, habito.getNome());
			st.setString(2, habito.getFrequencia());
			st.setLong(3, habito.getCategoria().getId());
			st.setLong(4, habito.getId());
			int linhasAfetadas = st.executeUpdate();
			return linhasAfetadas > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return false;
	}

	public boolean excluirPorId(long id) {
		conexaoMysql.abrirConexao();
		String sql = "DELETE FROM habito WHERE id_habito=?";
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setLong(1, id);
			int linhasAfetadas = st.executeUpdate();
			return linhasAfetadas > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return false;
	}
	

	public boolean excluirPorUsuario(long idUsuario) {
		conexaoMysql.abrirConexao();
		String sql = "DELETE FROM habito WHERE id_usuario=?";
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setLong(1, idUsuario);
			int linhasAfetadas = st.executeUpdate();
			return linhasAfetadas > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return false;
	}

	public boolean excluirTodos() {
		conexaoMysql.abrirConexao();
		
		String sql = "DELETE FROM habito";
		
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			int linhasAfetadas = st.executeUpdate();
			return linhasAfetadas > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return false;
	}

	public Habito buscarPorId(long id) {
		Habito habito = null;
		
		conexaoMysql.abrirConexao();
		
		String sql = "SELECT * FROM habito WHERE id_habito = ?;";
		
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				habito = new Habito();
				habito.setId(rs.getLong("id_habito"));
				habito.setNome(rs.getString("nome"));
				habito.setFrequencia(rs.getString("frequencia"));
				CategoriaDAO cDAO = new CategoriaDAO();
				Categoria categoria = cDAO.buscarPorId(rs.getLong("id_categoria"));
				habito.setCategoria(categoria);
				UsuarioDAO uDAO = new UsuarioDAO();
				Usuario usuario = uDAO.buscarPorId(rs.getLong("id_usuario"));
				habito.setUsuario(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return habito;
	}
	
	public Habito buscarPorNome(long idUsuario, String nome) {
		Habito habito = null;
		
		conexaoMysql.abrirConexao();
		
		String sql = "SELECT * FROM habito WHERE id_usuario = ? AND nome = ?;";
		
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setLong(1, idUsuario);
			st.setString(2, nome);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				habito = new Habito();
				habito.setId(rs.getLong("id_habito"));
				habito.setNome(rs.getString("nome"));
				habito.setFrequencia(rs.getString("frequencia"));
				CategoriaDAO cDAO = new CategoriaDAO();
				Categoria categoria = cDAO.buscarPorId(rs.getLong("id_categoria"));
				habito.setCategoria(categoria);
				UsuarioDAO uDAO = new UsuarioDAO();
				Usuario usuario = uDAO.buscarPorId(rs.getLong("id_usuario"));
				habito.setUsuario(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return habito;
	}

	public List<Habito> buscarTodos(long idUsuario) {
		List<Habito> listaHabitos = new ArrayList<>();
		
		conexaoMysql.abrirConexao();
		
		String sql = "SELECT * FROM habito WHERE id_usuario = ?;";
		
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setLong(1, idUsuario);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Habito habito = new Habito();
				habito.setId(rs.getLong("id_habito"));
				habito.setNome(rs.getString("nome"));
				habito.setFrequencia(rs.getString("frequencia"));
				CategoriaDAO cDAO = new CategoriaDAO();
				Categoria categoria = cDAO.buscarPorId(rs.getLong("id_categoria"));
				habito.setCategoria(categoria);
				UsuarioDAO uDAO = new UsuarioDAO();
				Usuario usuario = uDAO.buscarPorId(rs.getLong("id_usuario"));
				habito.setUsuario(usuario);
				listaHabitos.add(habito);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return listaHabitos;
	}
}
