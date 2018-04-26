package servidor;
import lists.*;
import java.net.*;

public class Salas
{
    protected int                 qtd = 0;
    protected UnorderedList<Sala> salas;

    public Salas (String[] nomeSalas) throws IllegalArgumentException {
    	if (nomeSalas == null)
    		throw new IllegalArgumentException("Argumento nulo");
    	
    	salas = new UnorderedList<Sala>();
    	
    	for (String nome : nomeSalas) {
    		this.novaSala(nome);
    	}
    	
    }
    
    public void      novaSala        (String nome) throws IllegalArgumentException {
    	if (nome == null)
    		throw new IllegalArgumentException("Argumento nulo");
    	
    	salas.addAsLast(new Sala(nome));
    	qtd++;
    }
    
    public void      novoUsuario     (String nomSal, Usuario usr) throws IllegalArgumentException, NotFoundException {
    	if (usr == null)
    		throw new IllegalArgumentException("Argumento nulo");
    	
    	if (nomSal == null)
    		throw new IllegalArgumentException("Argumento nulo");
    	
    	for (int i = 0; i <= qtd-1; i++) {
    		Sala s = salas.get(i);
    		if (s.getNome().equals(nomSal)) {
    			s.entra(usr);
    			return;
    		}
    	}
    	
    	throw new NotFoundException("Sala não encontrada");
    	
    }

    public Sala      getSala         (String nome) throws IllegalArgumentException, NotFoundException {
    	if (nome == null)
    		throw new IllegalArgumentException("Argumento nulo");
    	
    	for (int i = 0; i <= qtd-1; i++) {
    		Sala s = salas.get(i);
    		if (s.getNome().equals(nome))
    			return s;
    	}
    	
    	throw new NotFoundException("Sala não encontrada");
    }
    
    public String [] getNomesSalas   () {
    	
    	if (qtd > 0) {
    	String[] ret = new String[qtd];
    	
    	for (int i = 0; i <= qtd-1; i++) {
    		Sala s = salas.get(i);
    		ret[i] = s.getNome();
    	}
    	
    	return ret;
    	}
    	
    	return null;
    }
    
    public Usuario[] getUsuariosSala (String nomSal) throws IllegalArgumentException, NotFoundException {
    	if (nomSal == null)
    		throw new IllegalArgumentException("Argumento nulo");
    	
    	for (int i = 0; i <= qtd-1; i++) {
    		Sala s = salas.get(i);
    		if (s.getNome().equals(nomSal)) {
    			return s.getUsuarios();
    		}
    	}
    	
    	throw new NotFoundException("Sala não encontrada");
    }
    
    public Usuario getUsuario (String nomSal, String nicUsr) throws IllegalArgumentException, NotFoundException {
    	if (nicUsr == null)
    		throw new IllegalArgumentException("Argumento nulo");
    	
    	if (nomSal == null)
    		throw new IllegalArgumentException("Argumento nulo");
    	
    	for (int i = 0; i <= qtd-1; i++) {
    		Sala s = salas.get(i);
    		if (s.getNome().equals(nomSal)) {
    			return s.getUsuario(nicUsr);
    		}
    	}
    	
    	throw new NotFoundException("Usuário não encontrado");
    	
    }

    public void      tiraUsuario     (String nomSal, String nicUsr) throws IllegalArgumentException, NotFoundException {
    	if (nicUsr == null)
    		throw new IllegalArgumentException("Argumento nulo");
    	
    	if (nomSal == null)
    		throw new IllegalArgumentException("Argumento nulo");
    	
    	for (int i = 0; i <= qtd-1; i++) {
    		Sala s = salas.get(i);
    		if (s.getNome().equals(nomSal)) {
    			s.sai(nicUsr);
    		}
    	}
    	
    	throw new NotFoundException("Sala não encontrada");
    }
    
    public void      fechaSala       (String nome) throws IllegalArgumentException, NotFoundException {
    	if (nome == null)
    		throw new IllegalArgumentException("Argumento nulo");
    	
    	for (int i = 0; i <= qtd-1; i++) {
    		Sala s = salas.get(i);
    		if (s.getNome().equals(nome)) {
    			salas.remove(i);
    			qtd--;
    			return;
    		}
    	}
    	
    	throw new NotFoundException("Sala não encontrada");
    }

	@Override
	public int hashCode() {
		final int prime = 11;
		int result = 1;
		result = prime * result + qtd;
		result = prime * result + ((salas == null) ? 0 : salas.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Salas other = (Salas) obj;
		if (qtd != other.qtd)
			return false;
		if (salas == null) {
			if (other.salas != null)
				return false;
		} else if (!salas.equals(other.salas))
			return false;
		return true;
	}

    // metodos obrigatorios, claro
}