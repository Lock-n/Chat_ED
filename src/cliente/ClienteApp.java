package cliente;

import servidor.Comando;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;

public class ClienteApp extends JFrame {
	private JTextField txtMensagem;
	private JTextArea txtrChat;
	private JComboBox comboBox;
	private JLabel lblNomeDaSala;
	private JLabel lblNick;
	private String nome_sala = "Nome da Sala";
	/*private static Socket conexao;
	private static ObjectOutputStream transmissor;
	private static ObjectInputStream receptor;*/
	private JButton btnSair;
	private JScrollPane scrollPane;
	//private static String IP;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ClienteApp ca = new ClienteApp();
				ca.setVisible(true);
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClienteApp() {
		initialize();
	}
	
	public ClienteApp(String nome_sala) {
		this.nome_sala = nome_sala;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					Conexao.transmissor.writeObject(new Comando ("USUARIO_SAI", null));
					Cliente.frm_lobby = new Lobby();
					Cliente.frm_lobby.setVisible(true);
					Cliente.frm_ca = null;
					ClienteApp.this.dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		setTitle("Chat");
		setBounds(100, 100, 450, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		lblNomeDaSala = new JLabel("Nome da Sala");
		lblNomeDaSala.setText(this.nome_sala);
		panel.add(lblNomeDaSala);
		
		lblNick = new JLabel("Nick - ");
		lblNick.setText(SeletorDeNick.nick + " - ");
		panel.add(lblNick, BorderLayout.WEST);
		
		btnSair = new JButton("Sair");
		panel.add(btnSair, BorderLayout.EAST);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Conexao.transmissor.writeObject(new Comando ("USUARIO_SAI_SALA", null));
					//ClienteApp.conexao.close();
					Cliente.frm_lobby = new Lobby();
					Cliente.frm_lobby.setVisible(true);
					Cliente.frm_ca = null;
					dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JLabel lblMensagem = new JLabel("Mensagem:");
		panel_1.add(lblMensagem);
		
		ActionListener btnEnviar_Click = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtMensagem.getText().isEmpty()) {
					String destinatario = (String)ClienteApp.this.comboBox.getSelectedItem();
					String strMensagem = txtMensagem.getText();
					
					txtMensagem.setText("");
					Comando msg;
					if (destinatario.equals("Todos")) {
						msg = new Comando ("MENSAGEM_ABERTA", new Serializable[] {SeletorDeNick.nick, strMensagem});
						mostrarMensagem(SeletorDeNick.nick, strMensagem, false);
					}
					else {
						msg = new Comando("MENSAGEM_FECHADA", new Serializable[] {SeletorDeNick.nick, destinatario, strMensagem});
						mostrarMensagem(SeletorDeNick.nick, strMensagem, true);
					}
					
					try {
						Conexao.transmissor.writeObject(msg);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		txtMensagem = new JTextField();
		txtMensagem.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == e.VK_ENTER)
					btnEnviar_Click.actionPerformed(null);
			}
		});
		panel_1.add(txtMensagem);
		txtMensagem.setColumns(10);
		
		JLabel lblDestinatrio = new JLabel("Destinat\u00E1rio:");
		panel_1.add(lblDestinatrio);
		
		setComboBox(new JComboBox());
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Todos"}));
		panel_1.add(comboBox);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(btnEnviar_Click);
		panel_1.add(btnEnviar);
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		setTxtrChat(new JTextArea());
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panel, lblNomeDaSala, txtrChat, txtMensagem, comboBox, btnEnviar, getContentPane(), panel_1, lblMensagem, lblDestinatrio}));
		
		/*new Thread(new Runnable() {
			public void run() {
				
				//ClienteApp.IP = args[0];
				
				try {
					//DEFINIR IP_SERVIDOR
					//final String IP_SERVIDOR = ClienteApp.IP;
										
					//ClienteApp.conexao = conexao;//new Socket (IP_SERVIDOR, 12344);
					ClienteApp.transmissor = transmissor;//new ObjectOutputStream(conexao.getOutputStream());
					ClienteApp.receptor = receptor;//new ObjectInputStream(conexao.getInputStream());
					ClienteApp window = new ClienteApp();
					window.setVisible(true);
					
					Thread t_conexao = new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							for (;;) {
								
								System.out.println("Esperando comando...");
								Comando cmd = null;
								try {
									cmd = (Comando)Conexao.receptor.readObject();
								} catch (ClassNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									
								} catch (SocketException se) {
									Thread.currentThread().interrupt();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								System.out.println("Comando recebidoo: " + cmd.getCmd() + (cmd.getCmd().equals("SERVIDOR_ENTROU_USUARIO") ? "SIM" : "NÃO"));
								
								/*if (cmd.getCmd().equals("LISTA_DE_SALAS")) {
									String[] nomeSalas = (String[])cmd.getComplementos()[0];
									
									Controle.seletor_de_sala_fechado = false;
									window.setVisible(false);
									
									SeletorDeSala ss = new SeletorDeSala(nomeSalas);
									ss.setVisible(true);
									
									while (!(Controle.seletor_de_sala_fechado)) {
										try {
											Thread.sleep(50);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									
									
									try {
										transmissor.writeObject(new Comando ("INFO_USUARIO", new Serializable[] {SeletorDeSala.nick, SeletorDeSala.nome_sala}));
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									window.lblNick.setText(SeletorDeSala.nick + " - ");
									window.lblNomeDaSala.setText(SeletorDeSala.nome_sala);
									window.setVisible(true);
								}
								
								else
								if (cmd.getCmd().equals("MENSAGEM_INDIVIDUAL")) {
									String remetente = (String)cmd.getComplementos()[0];
							    	String strMsg = (String)cmd.getComplementos()[1];
							    	Boolean privado = (Boolean)cmd.getComplementos()[2];
							    	
							    	window.mostrarMensagem(remetente, strMsg, privado);
								}
								else
								if (cmd.getCmd().equals("LISTA_NICKS")) {
									String[] nicks = (String[])cmd.getComplementos()[0];
							    	
									for (String nick : nicks)
										if (!nick.equals(SeletorDeNick.nick))
										window.comboBox.addItem(nick);
								}
								else
								if (cmd.getCmd().equals("SERVIDOR_ENTROU_USUARIO")) {
									System.out.println("ENTROU");
									String nick = (String)cmd.getComplementos()[0];
									System.out.println("NICK: " + SeletorDeNick.nick + "nick: " + nick);
									if (!nick.equals(SeletorDeNick.nick))
									window.comboBox.addItem(nick);
									System.out.println("Passou do if");
									window.txtrChat.append("//" + nick + " entrou na sala.\n");
								}
								else
								if (cmd.getCmd().equals("SERVIDOR_USUARIO_SAIU")) {
									String nick = (String)cmd.getComplementos()[0];
									window.comboBox.removeItem(nick);
									window.txtrChat.append("//" + nick + " saiu na sala.\n");
								}
								else
								if (cmd.getCmd().equals("SERVIDOR_NICK_INVALIDO")) {
									Controle.seletor_de_nick_fechado = false;
									window.setVisible(false);
									
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
									
									window.setVisible(true);
								}
								
							}
						}
						
					});
					t_conexao.start();
					
					
				} catch (Exception e) {
					//JOptionPane.showMessageDialog(null, "Erro de conexão:" + e.getMessage());
					JOptionPane.showMessageDialog(null, "Erro de conexão com o servidor");
					e.printStackTrace();
					SeletorDeIP.main(null);
				}
			}
		});*/
	}
	
	void mostrarMensagem(String remetente, String mensagem, Boolean privado) {
		this.txtrChat.append((privado ? "* " : "") + remetente + ": " + mensagem + "\n");
	}

	public void setComboBox(JComboBox comboBox) {
		this.comboBox = comboBox;
	}

	public void setTxtrChat(JTextArea txtrChat) {
		this.txtrChat = txtrChat;
		scrollPane.setViewportView(txtrChat);
		txtrChat.setEditable(false);
	}
	
	public void processarComando(Comando cmd) {
		if (cmd.getCmd().equals("MENSAGEM_INDIVIDUAL")) {
			String remetente = (String)cmd.getComplementos()[0];
	    	String strMsg = (String)cmd.getComplementos()[1];
	    	Boolean privado = (Boolean)cmd.getComplementos()[2];
	    	
	    	this.mostrarMensagem(remetente, strMsg, privado);
		}
		else
		if (cmd.getCmd().equals("LISTA_DE_NICKS")) {
			String[] nicks = (String[])cmd.getComplementos()[0];
	    	
			for (String nick : nicks)
				if (!nick.equals(SeletorDeNick.nick))
					this.comboBox.addItem(nick);
		}
		else
		if (cmd.getCmd().equals("SERVIDOR_ENTROU_USUARIO")) {
			String nick = (String)cmd.getComplementos()[0];
			if (!nick.equals(SeletorDeNick.nick))
				this.comboBox.addItem(nick);
			this.txtrChat.append("//" + nick + " entrou na sala.\n");
		}
		else
		if (cmd.getCmd().equals("SERVIDOR_USUARIO_SAIU")) {
			String nick = (String)cmd.getComplementos()[0];
			this.comboBox.removeItem(nick);
			this.txtrChat.append("//" + nick + " saiu na sala.\n");
		}
		else
		if (cmd.getCmd().equals("SERVIDOR_NICK_INVALIDO")) {
			Controle.seletor_de_nick_fechado = false;
			this.setVisible(false);
			
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
			
			this.setVisible(true);
		}
	}

}
