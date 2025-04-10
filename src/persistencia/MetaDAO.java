package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Meta;
import model.Usuario;

public class MetaDAO {
	private ConexaoMysql conexaoMysql;
	
	public MetaDAO() {
		this.conexaoMysql = new ConexaoMysql();
	}
	
	public Meta salvar(Meta meta) {
		conexaoMysql.abrirConexao();
		
		String sql = "INSERT INTO meta VALUES(null, ?, ?, ?, ?);";
		
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql,
					PreparedStatement.RETURN_GENERATED_KEYS);
			java.sql.Date dataInicio = new java.sql.Date(meta.getDataInicio().getTime());
		    java.sql.Date dataFim = new java.sql.Date(meta.getDataFim().getTime());

			
			st.setString(1, meta.getNome());
			st.setDate(2, dataInicio);
			st.setDate(3, dataFim);
			st.setLong(4, meta.getUsuario().getId());
			
			int linhasAfetadas = st.executeUpdate();
	        if (linhasAfetadas > 0) {
	            ResultSet rs = st.getGeneratedKeys();
	            if (rs.next()) {
	                meta.setId(rs.getLong(1));
	            }
	        }
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		
		return meta;
	}
	
	public boolean editar(Meta meta) {
		conexaoMysql.abrirConexao();
		
		String sql = "UPDATE meta SET nome = ?, dataInicio = ?, dataFim = ?, id_usuario = ? WHERE id_meta = ?;";
		
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			
			java.sql.Date dataInicio = new java.sql.Date(meta.getDataInicio().getTime());
		    java.sql.Date dataFim = new java.sql.Date(meta.getDataFim().getTime());
			
			st.setString(1, meta.getNome());
			st.setDate(2, dataInicio);
			st.setDate(3, dataFim);
			st.setLong(4, meta.getUsuario().getId());
			st.setLong(5, meta.getId());
			
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
		
		String sql = "DELETE FROM meta WHERE id_meta = ?;";
		
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
	
	public boolean excluirPorUsuario(long idUsuario) {
		conexaoMysql.abrirConexao();
		
		String sql = "DELETE FROM meta WHERE id_usuario = ?;";
		
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setLong(1, idUsuario);
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
	
	public boolean excluirPorNome(String nome) {
		conexaoMysql.abrirConexao();
		
		String sql = "DELETE FROM meta WHERE nome = ?;";
		
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setString(1, nome);
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
		
		String sql = "DELETE FROM meta;";
		
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
	
	public Meta buscarPorId(long id) {
		Meta meta = null;
		conexaoMysql.abrirConexao();
		
		String sql = "SELECT * FROM meta WHERE id_meta = ?;";
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				meta = new Meta();

				meta.setId(rs.getLong("id_meta"));
				meta.setNome(rs.getString("nome"));
				meta.setDataInicio(rs.getDate("dataInicio"));
				meta.setDataFim(rs.getDate("dataFim"));
				UsuarioDAO uDAO = new UsuarioDAO();
				Usuario usuario = uDAO.buscarPorId(rs.getLong("id_usuario"));
				meta.setUsuario(usuario);
				
			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return meta;
	}
	
	public Meta buscarPorNome(long idUsuario, String nome) {
		Meta meta = null;
		conexaoMysql.abrirConexao();
		
		String sql = "SELECT * FROM meta WHERE id_usuario = ? AND nome = ?;";
		try {
			PreparedStatement st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setLong(1, idUsuario);
			st.setString(2,nome);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				meta = new Meta();

				meta.setId(rs.getLong("id_meta"));
				meta.setNome(rs.getString("nome"));
				meta.setDataInicio(rs.getDate("dataInicio"));
				meta.setDataFim(rs.getDate("dataFim"));
				UsuarioDAO uDAO = new UsuarioDAO();
				Usuario usuario = uDAO.buscarPorId(rs.getLong("id_usuario"));
				meta.setUsuario(usuario);
				
			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return meta;
	}
	
	public List<Meta> buscarTodos(long idUsuario) {
		Meta meta = null;
		
		List<Meta> listaMetas = new ArrayList<>();
	
		conexaoMysql.abrirConexao();
		
		String sql = "SELECT * FROM meta WHERE id_usuario = ?;";
		PreparedStatement st;
		
		try {
			st = conexaoMysql.getConexao().prepareStatement(sql);
			st.setLong(1, idUsuario);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				meta = new Meta();
		
				meta.setId(rs.getLong("id_meta"));
				meta.setNome(rs.getString("nome"));
				meta.setDataInicio(rs.getDate("dataInicio"));
				meta.setDataFim(rs.getDate("dataFim"));
				UsuarioDAO uDAO = new UsuarioDAO();
				Usuario usuario = uDAO.buscarPorId(rs.getLong("id_usuario"));
				meta.setUsuario(usuario);
				listaMetas.add(meta);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoMysql.fecharConexao();
		}
		return listaMetas;
	}

	
}
