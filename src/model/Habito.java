package model;

public class Habito {
	private long id;
	private String nome;
	private String frequencia;
	private Categoria categoria;
	private Usuario usuario;
	

	public Habito() {
		super();
		this.nome = "";
		this.frequencia = "";
		categoria = new Categoria();
	}
	
	public Habito(long id, String nome, String frequencia, Categoria categoria, Usuario usuario ) {
		this.id = id;
		this.nome = nome;
		this.frequencia = frequencia;
		this.categoria = categoria;
		this.usuario = usuario;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}

