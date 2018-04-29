package cliente;

import servidor.Servidor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import servidor.Comando;;

public class Cliente {
	public static ClienteApp frm_ca;
	public static Lobby frm_lobby;
	public static Thread servidor;

	public static void main(String[] args) {
		SeletorDeIP sip = new SeletorDeIP();
		sip.setVisible(true);
		
		while (sip.getHostear() == null)
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		Boolean hostear = sip.getHostear();
		sip.dispose();
		
		if (hostear) {
			if (Cliente.servidor == null)
			Cliente.servidor = new Thread() {
				public void run() {
					Servidor.main(null);
				}
			};
			Cliente.servidor.start();
		}
		
		try {
			Conexao.conexao = new Socket(SeletorDeIP.IP, 12345);
			Conexao.transmissor = new ObjectOutputStream(Conexao.conexao.getOutputStream());
			Conexao.receptor = new ObjectInputStream(Conexao.conexao.getInputStream());
			
		Thread t_conexao = new Thread() {
			public void run() {
				for (;;) {
					System.out.println("Esperando comando...");
					
					try {
						Conexao.cmd = (Comando)Conexao.receptor.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("Comando recebido: " + Conexao.cmd.getCmd());
					
					if (Conexao.cmd.getCmd().equals("LISTA_DE_SALAS")) {
						if (Cliente.frm_lobby == null)
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						Object[] info_salas = (Object[])Conexao.cmd.getComplementos();
						DefaultTableModel tblLobby_model = (DefaultTableModel) Cliente.frm_lobby.getTblLobby().getModel();
						
						int rowCount = tblLobby_model.getRowCount();
						//Remove rows one by one from the end of the table
						for (int i = rowCount - 1; i >= 0; i--) {
							tblLobby_model.removeRow(i);
						}
						
						for (Object linha : info_salas) {
							String nome_sala = (String)((Object[])linha)[0];
							int qtd_sala = (int)((Object[])linha)[1];
							tblLobby_model.addRow(new Object[] {nome_sala, qtd_sala});
						}
					}
					else 
					if (Conexao.cmd.getCmd().equals("MENSAGEM_INDIVIDUAL")) {
						String remetente = (String)Conexao.cmd.getComplementos()[0];
				    	String strMsg = (String)Conexao.cmd.getComplementos()[1];
				    	Boolean privado = (Boolean)Conexao.cmd.getComplementos()[2];
				    	
				    	Cliente.frm_ca.mostrarMensagem(remetente, strMsg, privado);
					}
					else
					if (Conexao.cmd.getCmd().equals("LISTA_DE_NICKS")) {
						String[] nicks = (String[])Conexao.cmd.getComplementos()[0];
				    	
						for (String nick : nicks)
							if (!nick.equals(SeletorDeNick.nick))
								Cliente.frm_ca.getComboBox().addItem(nick);
					}
					else
					if (Conexao.cmd.getCmd().equals("SERVIDOR_ENTROU_USUARIO")) {
						if (Cliente.frm_ca == null)
							try {
								Thread.sleep(100);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						
						String nick = (String)Conexao.cmd.getComplementos()[0];
						if (!nick.equals(SeletorDeNick.nick))
							Cliente.frm_ca.getComboBox().addItem(nick);
						Cliente.frm_ca.getTxtrChat().append("//" + nick + " entrou na sala.\n");
					}
					else
					if (Conexao.cmd.getCmd().equals("SERVIDOR_USUARIO_SAIU")) {
						String nick = (String)Conexao.cmd.getComplementos()[0];
						Cliente.frm_ca.getComboBox().removeItem(nick);
						Cliente.frm_ca.getTxtrChat().append("//" + nick + " saiu na sala.\n");
					}
					else
					if (Conexao.cmd.getCmd().equals("SERVIDOR_NICK_INVALIDO")) {
						Controle.seletor_de_nick_fechado = false;
						Cliente.frm_ca.setVisible(false);
						
						SeletorDeNick sn = new SeletorDeNick();
						sn.setVisible(true);
						
						while (!(Controle.seletor_de_nick_fechado)) {
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						try {
							Conexao.transmissor.writeObject(new Comando ("NICK_USUARIO", new Serializable[] {SeletorDeNick.nick}));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						Cliente.frm_ca.setVisible(true);
					}
				}
			}
		};
		
		SeletorDeNick sn = new SeletorDeNick();
		sn.setVisible(true);
		
		while (SeletorDeNick.nick == null)
			Thread.sleep(50);
		
		Conexao.transmissor.writeObject(new Comando ("INFO_USUARIO", new Serializable[] {SeletorDeNick.nick}));
		Cliente.frm_lobby = new Lobby();
		Cliente.frm_lobby.setVisible(true);
		t_conexao.start();
		
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Erro no estabelecimento de conexão:\n" + e1.getMessage(), "Erro de conexão", JOptionPane.ERROR_MESSAGE, null);
			e1.printStackTrace();
			Cliente.frm_ca = null;
			Cliente.frm_lobby = null;
			
			main(null);
		}
	}
}
