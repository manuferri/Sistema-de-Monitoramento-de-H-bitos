package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;

public class UsuarioDAO {

	private ConexaoMysql conexaoMysql;

	public UsuarioDAO() {
		this.conexaoMysql = new ConexaoMysql();
	}

	public Usuario salvar(Usuario usuario) {
		conexaoMysql.abrirConexao();
		String sql = "INSERT INTO usuario VALUES(null, ?, ?, ?);";
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql,
					PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1, usuario.getNome());
			st.setString(2, usuario.getEmail());
			st.setString(3, usuario.getSenha());
			int linhasAfetadas = st.executeUpdate();
			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					long idUsuario = rs.getLong(1);
					usuario.setId(idUsuario);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}


		return usuario;
	}

	public boolean editar(Usuario usuario) {
		conexaoMysql.abrirConexao();
		String sql = "UPDATE usuario SET nome=?, email=?, senha=? WHERE id_usuario=?;";
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setString(1, usuario.getNome());
			st.setString(2, usuario.getEmail());
			st.setString(3, usuario.getSenha());
			st.setLong(4, usuario.getId());
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
		String sql = "DELETE FROM usuario WHERE id_usuario=?";
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
		String sql = "DELETE FROM usuario";
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

	public Usuario buscarPorId(long id) {
		Usuario usuario = null;
		conexaoMysql.abrirConexao();
		String sql = "SELECT * FROM usuario WHERE id_usuario = ?;";
		PreparedStatement st;
		try {
			st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getLong(1));
				usuario.setNome(rs.getString(2));
				usuario.setEmail(rs.getString(3));
				usuario.setSenha(rs.getString(4));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return usuario;
	}
	
	public Usuario buscarPorEmail(String email) {
		Usuario usuario = null;
		conexaoMysql.abrirConexao();
		String sql = "SELECT * FROM usuario WHERE email = ?;";
		PreparedStatement st;
		try {
			st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setString(1, email);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getLong(1));
				usuario.setNome(rs.getString(2));
				usuario.setEmail(rs.getString(3));
				usuario.setSenha(rs.getString(4));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return usuario;
	}
	
	public List<Usuario> buscarTodos() {
		Usuario usuario = null;
		List<Usuario> listaUsuarios = new ArrayList<>();
		conexaoMysql.abrirConexao();
		String sql = "SELECT * FROM usuario;";
		PreparedStatement st;
		try {
			st = conexaoMysql.getConexao().prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getLong(1));
				usuario.setNome(rs.getString(2));
				usuario.setEmail(rs.getString(3));
				usuario.setSenha(rs.getString(4));
				listaUsuarios.add(usuario);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return listaUsuarios;
	}
	
}
