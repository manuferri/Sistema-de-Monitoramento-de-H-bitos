package model;

public class Categoria {
	private long id;
	private String nome;
	
	public Categoria() {
		super();
		this.nome = "";
	}
    
	public Categoria(long id, String nome) {
		this.id = id;
		this.nome = nome; 
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id_categoria) {
		this.id = id_categoria;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}