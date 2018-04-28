package servidor;
import lists.*;
import java.net.*;
import java.io.*;

public class Usuario
{
    protected String         nomeSala;
    protected String         nick;
    protected Socket         conexao;
    //protected BufferedReader receptor;
    //protected PrintWriter    transmissor;
    protected ObjectInputStream  receptor;
    protected ObjectOutputStream transmissor;

    // construtor de usuario instancia BufferedReader e PrintWriter, 
    // interage atraves deles com o usuario para enviar a lista de
    // salas e obter a sala onde o usuario quer entrar, bem como o
    // seu nick, inicializando this.sala e this.nick
    public Usuario (String[] nomSls, Socket conexao) throws IOException {
    	transmissor = new ObjectOutputStream(conexao.getOutputStream());
    	receptor = new ObjectInputStream(conexao.getInputStream());
    	
    	transmissor.writeObject(new Comando("LISTA_DE_SALAS", new Serializable[]{nomSls}));
    	
    	while (true) {
	    	Comando info;
			try {
				info = (Comando)receptor.readObject();
				
				while (!info.getCmd().equals("INFO_USUARIO"))
		    		info = (Comando)receptor.readObject();
		    	
		    	this.nick = (String)info.getComplementos()[0];
		    	
		    	this.nomeSala = (String)info.getComplementos()[1];
		    	
		    	break;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    	}
    }

    
    // getters para nomeSala e nick
    public String getNomeSala() {
		return nomeSala;
	}


	public void setNomeSala(String nomeSala) {
		this.nomeSala = nomeSala;
	}


	public String getNick() {
		return nick;
	}


	// metodos para desconectar
	
	// recebe do usuario, usando this.receptor
    public Comando recebe () {
    	while (true)
			try {
				return (Comando)this.receptor.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }

    // envia para o usuario, usando this.transmissor
    public void envia (Comando mensagem) throws IllegalArgumentException, IOException {
    	//Comando msg = new Comando("MENSAGEM", new Object[] {mensagem});
    	if (mensagem == null)
    		throw new IllegalArgumentException("Mensagem inválida");
    	
    	/*if (!mensagem.getCmd().equals("MENSAGEM_INDIVIDUAL") && !mensagem.getCmd().equals("LISTA_NICKS"))
    		throw new IllegalArgumentException("Mensagem inválida");*/
    	
    	transmissor.writeObject(mensagem);
    }


	public void setNick(String nick) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if (nick == null)
    		throw new IllegalArgumentException("Argumento nulo");
		
		this.nick = nick;
	}

    // metodos obrigatorios
}