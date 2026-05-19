package model;

public class Usuario {

    protected String username;
    protected String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

	public Usuario(){
		
	}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean autenticar(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return "Usuario{username='" + username + "'}";
    }
}