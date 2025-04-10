package model;

import java.util.Date;


public class Meta {
    private long id;
    private String nome;
    private Date dataInicio;
    private Date dataFim;
    private Usuario usuario;


	public Meta() {
        super();
        this.id = 0;
        this.nome = "";
        this.dataInicio = new Date() ;
        this.dataFim = new Date();
        usuario = new Usuario();
    }

    public Meta(long id_meta, String nome, Date dataInicio, Date dataFim, Usuario usuario) {
        this.id = id_meta;
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
    
    public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}

