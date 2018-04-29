package servidor;
import lists.*;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;

public class TratadorDeConexao extends Thread
{
	protected Salas				  salas;
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
           
           Comando cmd = this.usuario.recebe();

           System.out.println("CMD: " + cmd.getCmd());
           
    	   if (cmd.getCmd().equals("USUARIO_SAI_SALA")) {
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
				usuario.envia(new Comando("LISTA_DE_SALAS", this.salas.getInfoSalas()));
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
    		   this.sala = null;
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
           
           else if (cmd.getCmd().equals("L_USUARIO_SAI")) {
        	   this.pare();
           }
           else if (cmd.getCmd().equals("NICK_USUARIO")) {
        	   try {
        		   this.setNick((String) cmd.getComplementos()[0]);
        	   }
        	   catch (IllegalArgumentException iae) {
        		   try {
					usuario.envia(new Comando("SERVIDOR_NICK_INVALIDO", null));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	   }
           }
       
           else
    	   if (cmd.getCmd().equals("L_ENTRAR_SALA")) {
    		   String nome_sala = (String)cmd.getComplementos()[0];
    		   
    		   try {
    			   this.sala = salas.getSala(nome_sala);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		   
    		   this.sala.entra(this.usuario);
    		   
    		   try {
				this.sala.enviarMensagemServidor(new Comando("SERVIDOR_ENTROU_USUARIO", new Serializable[] {this.usuario.getNick()}));
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		   
    		   try {
				this.usuario.envia(new Comando ("LISTA_DE_NICKS", new Serializable[] {this.sala.getNicks_S()}));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		   
    	   }
    	   else if (cmd.getCmd().equals("L_CRIAR_SALA")) {
    		   String nome_sala = (String)cmd.getComplementos()[0];
    		   
    		   this.salas.novaSala(nome_sala);
    		   
    		   try {
    			   this.sala = salas.getSala(nome_sala);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	   }
    	   else if (cmd.getCmd().equals("L_ATUALIZAR_LISTA_SALAS")) {
    		   try {
    				usuario.envia(new Comando ("LISTA_DE_SALAS", salas.getInfoSalas()));
    			} catch (IllegalArgumentException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	   }
           
           
        }
    }

    public TratadorDeConexao (Salas salas, Socket conexao)
    {
    	this.salas = salas;
    	
        try {
			this.usuario = new Usuario (conexao);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			usuario.envia(new Comando ("LISTA_DE_SALAS", salas.getInfoSalas()));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        /*try {
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
		}*/
    }
    
    public void setNick(String nick) throws IllegalArgumentException {
		if (nick == null) {
			throw new IllegalArgumentException("Argumento nulo");
		}
		
		
		String[] nicks = this.sala.getNicks_S();
		
		if (nicks == null) {
			this.usuario.setNick(nick);
			return;
		}
		
		for (String n : nicks) {
			if (n.equals(nick))
				throw new IllegalArgumentException ("Nick existente na sala");
		}
		
		this.usuario.setNick(nick);
	}
    
}