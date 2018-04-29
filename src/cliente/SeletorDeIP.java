package cliente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class SeletorDeIP extends JFrame{

	//JFrame frmChat;
	private JButton btnHostear;
	private JTextField txtIp;
	private Boolean hostear;
	static String IP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SeletorDeIP window = new SeletorDeIP();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Boolean getHostear() {
		return this.hostear;
	}
	
	/**
	 * Create the application.
	 */
	public SeletorDeIP() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setResizable(false);
		setTitle("Chat");
		setBounds(100, 100, 236, 160);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[14px][183px][]", "[20px][23px][24px][23px][]"));
		
		JLabel lblIp = new JLabel("IP:");
		getContentPane().add(lblIp, "cell 0 0,alignx right,aligny center");
		
		txtIp = new JTextField();
		getContentPane().add(txtIp, "cell 1 0,growx,aligny top");
		txtIp.setColumns(10);
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtIp.getText().isEmpty()) {
					SeletorDeIP.this.hostear = false;
					SeletorDeIP.IP = txtIp.getText();
					/*try {
						Conexao.conexao = new Socket(IP, 12344);
						Conexao.transmissor = new ObjectOutputStream(Conexao.conexao.getOutputStream());
						Conexao.receptor = new ObjectInputStream(Conexao.conexao.getInputStream());
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//Lobby.main(new String[] {IP});
					new Lobby().setVisible(true);
					SeletorDeIP.this.dispose();*/
				}
			}
		});
		getContentPane().add(btnConectar, "cell 1 1,growx,aligny top");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 1 2,grow");
		
		JLabel lblOu = new JLabel("OU");
		panel.add(lblOu);
		
		btnHostear = new JButton("Hostear");
		btnHostear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SeletorDeIP.this.hostear = true;
				try {
					SeletorDeIP.IP = (java.net.Inet4Address.getLocalHost().getHostAddress());
					//System.out.println(java.net.InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*new Thread() {
					public void run() {
						servidor.Servidor.main(null);
					}
				}.start();
				
				try {
					Conexao.conexao = new Socket(IP, 12344);
					Conexao.transmissor = new ObjectOutputStream(Conexao.conexao.getOutputStream());
					Conexao.receptor = new ObjectInputStream(Conexao.conexao.getInputStream());
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				SeletorDeIP.this.setVisible(false);
				Controle.seletor_de_nick_fechado = false;
				SeletorDeIP.this.dispose();
				
				SeletorDeNick sn = new SeletorDeNick();
				sn.setVisible(true);
				
				new Lobby().setVisible(true);*/
				
			}
		});
		getContentPane().add(btnHostear, "cell 1 3,growx,aligny top");
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtIp, btnConectar, btnHostear, getContentPane(), lblIp, panel, lblOu}));
	}

}
