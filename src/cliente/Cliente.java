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
			new Thread() {
				public void run() {
					Servidor.main(null);
				}
			}.start();
		}
		
		try {
			Socket conexao = new Socket(SeletorDeIP.IP, 12345);
			Conexao.transmissor = new ObjectOutputStream(conexao.getOutputStream());
			Conexao.receptor = new ObjectInputStream(conexao.getInputStream());
			
		Thread t_conexao = new Thread() {
			public void run() {
				for (;;) {
					System.out.println("Esperando comando...");
					Comando cmd = null;
					try {
						cmd = (Comando)Conexao.receptor.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("Comando recebido: " + cmd.getCmd());
					
					if (cmd.getCmd().equals("LISTA_DE_SALAS")) {
						if (Cliente.frm_lobby == null)
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						Cliente.frm_lobby.processarComando(cmd);
					}
					else 
					if (cmd.getCmd().equals("MENSAGEM_INDIVIDUAL") || cmd.getCmd().equals("LISTA_DE_NICKS") ||
						cmd.getCmd().equals("SERVIDOR_ENTROU_USUARIO") || cmd.getCmd().equals("SERVIDOR_USUARIO_SAIU") ||
						cmd.getCmd().equals("SERVIDOR_NICK_INVALIDO")) {
						if (Cliente.frm_ca == null)
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						Cliente.frm_ca.processarComando(cmd);
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
