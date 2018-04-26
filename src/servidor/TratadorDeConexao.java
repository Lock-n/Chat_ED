package servidor;
import lists.*;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;

public class TratadorDeConexao extends Thread
{
    protected Sala                sala;
    protected OrderedList<String> nick = new OrderedList<String>();
    protected Usuario             usuario;
    protected boolean             fim = false;

    public void pare ()
    {
        this.fim = true;
    }

    public void run ()
    {
        while (!fim)
        {
           /*OrderedList<String> nicksAtuais = this.sala.getNicks();
           OrderedList<String> entrou = nicksAtuais.minus(this.nick);
           OrderedList<String> saiu   = this.nick.minus(nicksAtuais);*/
           // para cada string s da lista entrou
           // this.usuario.envia("+") e this.usuario.envia(s)
           // para cada string s da lista saiu
           // this.usuario.envia("-") e this.usuario.envia(s)

           // receber de this.usuario 1 comando do protocolo
           // com complementos (pode ser "SAI", se usuario quer
           // sair, "MSG"+"DESTINATARIO"+"TEXTO" se quer mandar
           // mensagem, ou "MSG"+"REMENTENTE"+"TEXTO"
           Comando cmd = this.usuario.recebe();

           // tratar o "SAI" (parar a thread e tirar da sala) ou
           // (destinatario pode ser 1 especifico ou TODOS) e
           if (cmd.getCmd().equals("USUARIO_SAI")) {
        	   try {
        		   sala.sai(usuario);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	   
        	   try {
				sala.enviarMensagemServidor(new Comando ("SERVIDOR_USUARIO_SAIU", new Serializable[] {this.usuario.getNick()}));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	   this.pare();
           }
           else if (cmd.getCmd().equals("MENSAGEM_ABERTA"))  {
	        	
	            try {
					this.sala.enviarMensagemAberta(cmd);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           
	           // loop para mandar para cada nick i em nicksAtuais
	    	   /*Usuario usr = this.sala.getUsuario (nicksAtuais[i]);
	           usr.envia("msg"); // as letras M, S e G
	           usr.envia(nick do remetente);
	           usr.envia(texto da mensagem);*/
           }
           
               
           else if (cmd.getCmd().equals("MENSAGEM_FECHADA")) {
        	   
	            try {
					sala.enviarMensagemFechada(cmd);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	   /*Usuario usr = this.sala.getUsuario (cmd);
	               usr.envia("msg"); // as letras M, S e G
	               usr.envia(nick do remetente);
	               usr.envia(texto da mensagem);*/
           }
           
           else if (cmd.getCmd().equals("USUARIO_SAI")) {
        	   this.pare();
           }
        }
    }

    public TratadorDeConexao (Salas salas, Socket conexao)
    {
        String[] nomesSalas = salas.getNomesSalas();
        try {
			this.usuario = new Usuario (nomesSalas, conexao);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // construtor de usuario instancia BufferedReader e PrintWriter
        // e interage com o usuario atraves deles para obter o nick
        // e a sala (mande as salas para ele escolher)

        String nomeSala = this.usuario.getNomeSala();
        
        try {
			this.sala       = salas.getSala (nomeSala);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        this.sala.entra (this.usuario);
        
        try {
        	this.sala.enviarMensagemServidor(new Comando ("SERVIDOR_ENTROU_USUARIO", new Serializable[] {this.usuario.getNick()}));
			this.usuario.envia(new Comando("LISTA_NICKS", new Serializable[] {this.sala.getNicks_S()}));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}