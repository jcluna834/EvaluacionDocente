package co.edu.unicauca.simcav2.modelo.evaluaciondocente;


public class Usuario {
	
	private String usuario;
	private String pass;
	private int privilegio;
	private String id;
	
	public Usuario() {
	}
	
	public Usuario(String usuario, String pass, int privilegio, String id) {
		super();
		this.usuario = usuario;
		this.pass = pass;
		this.privilegio = privilegio;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getPrivilegio() {
		return privilegio;
	}

	public void setPrivilegio(int privilegio) {
		this.privilegio = privilegio;
	}
	
	
	
}
