package servidor;
import java.io.IOException;
import java.io.Serializable;

import lists.*;

public class Sala
{
    protected String                 nome;
    protected int                    qtdUsuarios = 0;
    protected UnorderedList<Usuario> usuarios;

    public Sala (String nome) {
    	this.nome = nome;
    	this.usuarios = new UnorderedList<Usuario>();
    }
    
    public void                entra      (Usuario usr) throws IllegalArgumentException {
    	if (usr == null)
    		throw new IllegalArgumentException("Argumento nulo");
    	
    	usuarios.addAsLast(usr);
    	qtdUsuarios++;
    }
    
    public void sai (Usuario usr) throws IllegalArgumentException, NotFoundException {
    	if (usr == null)
    		throw new IllegalArgumentException("Argumento nulo");
    	
    	for (int i = 0; i <= qtdUsuarios-1; i++) {
        	Usuario u = usuarios.get(i);
        	if (usr.getNick().equals(u.nick)) {
        		usuarios.remove(i);
        		qtdUsuarios--;
        		return;
        	}
    	}
    	
    	throw new NotFoundException("Usuário não existente");
    }
    
    public void sai (String nick_usr) throws IllegalArgumentException, NotFoundException {
    	if (nick_usr == null)
    		throw new IllegalArgumentException("Argumento nulo");
    	
    	for (int i = 0; i <= qtdUsuarios-1; i++) {
        	Usuario u = usuarios.get(i);
        	if (nick_usr.equals(u.nick)) {
        		usuarios.remove(i);
        		qtdUsuarios--;
        		return;
        	}
    	}
    	
    	throw new NotFoundException("Usuário não existente");
    }
    
    public OrderedList<String> getNicks   ()            throws Exception {
    	if (usuarios.empty())
    		throw new Exception("Nada a ser retornado");
    	
    	OrderedList<String> nicks = new OrderedList<String>();
    	
    	for (int i = 0; i <= qtdUsuarios-1; i++) {
        	Usuario u = usuarios.get(i);
        	nicks.add(u.getNick());
    	}
    	
    	return nicks;
    }
    
    public String[] getNicks_S() throws Exception {
    	if (usuarios.empty())
    		throw new Exception("Nada a ser retornado");
    	
    	String[] nicks = new String[qtdUsuarios];
    	
    	for (int i = 0; i <= qtdUsuarios-1; i++) {
        	Usuario u = usuarios.get(i);
        	nicks[i] = u.getNick();
    	}
    	
    	return nicks;
    }
    
    public Usuario             getUsuario (String nick)  throws NotFoundException {
    	for (int i = 0; i <= qtdUsuarios-1; i++) {
        	Usuario u = usuarios.get(i);
        	if (u.getNick().equals(u.nick))
        		return u;
    	}
    	
    	throw new NotFoundException("Usuário não existente");
    }

    // metodos para enviar mensagem para um usuario
    // especifico e metodos para enviar mensagens
    // abertas
    
    public void enviarMensagemAberta (Comando msg) throws IllegalArgumentException, IOException {
    	if (msg == null)
    		throw new IllegalArgumentException("Mensagem inválida");
    	
    	if (!msg.getCmd().equals("MENSAGEM_ABERTA"))
    		throw new IllegalArgumentException("Mensagem inválida");
    	
    	//																											Não é privada
    	msg = new Comando ("MENSAGEM_INDIVIDUAL", new Serializable[] {(String)msg.getComplementos()[0], (String)msg.getComplementos()[1], false}); 
    	for (int i = 0; i <= qtdUsuarios-1; i++) {
    		Usuario u = usuarios.get(i);
    		if (!u.getNick().equals((String)msg.getComplementos()[0]))
    			u.envia(msg);
    	}
    }
    
    public void enviarMensagemServidor (Comando msg) throws IllegalArgumentException, IOException {
    	if (msg == null)
    		throw new IllegalArgumentException("Mensagem inválida");
    	
    	/*if (!msg.getCmd().equals("SERVIDOR_ENTROU_USUARIO"))
    		throw new IllegalArgumentException("Mensagem inválida");*/
    	
    	for (int i = 0; i <= qtdUsuarios-1; i++) {
    		usuarios.get(i).envia(msg);
    	}
    }
    
    
    public void enviarMensagemFechada (Comando mensagem) throws IllegalArgumentException, IOException, NotFoundException {
    	if (mensagem == null)
    		throw new IllegalArgumentException("Mensagem inválida");
    	
    	if (!mensagem.getCmd().equals("MENSAGEM_FECHADA"))
    		throw new IllegalArgumentException("Mensagem inválida");
    	
    	String remetente = (String)mensagem.getComplementos()[0];
    	String destino = (String)mensagem.getComplementos()[1];
    	String strMsg = (String)mensagem.getComplementos()[2];
    	
    	//																				   É privada
    	Comando msg = new Comando ("MENSAGEM_INDIVIDUAL", new Serializable[] {remetente, strMsg, true});
    	
    	boolean enviado = false;
    	for (int i = 0; (i <= qtdUsuarios-1) && (!enviado); i++) {
    		Usuario u = usuarios.get(i);
    		if (u.getNick().equals(destino)) {
    			u.envia(msg);
    			enviado = true;
    		}
    	}
    	
    	if (!enviado)
    		throw new NotFoundException ("Nick não foi encontrado");
    }

	public String getNome() {
		return nome;
	}
	
	public Usuario[] getUsuarios() {
		if (qtdUsuarios <= 0)
			return null;
		
		Usuario[] usrs = new Usuario[qtdUsuarios];
		
		for (int i = 0; i <= qtdUsuarios-1; i++) {
			usrs[i] = usuarios.get(i);
    	}
		
		return usrs;
	}
    
    
}