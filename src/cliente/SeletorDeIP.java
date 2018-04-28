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
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;

public class SeletorDeIP {

	private JFrame frmChat;
	private JTextField txtIp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SeletorDeIP window = new SeletorDeIP();
					window.frmChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		frmChat = new JFrame();
		frmChat.setResizable(false);
		frmChat.setTitle("Chat");
		frmChat.setBounds(100, 100, 236, 160);
		frmChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChat.getContentPane().setLayout(new MigLayout("", "[14px][183px][]", "[20px][23px][24px][23px][]"));
		
		JLabel lblIp = new JLabel("IP:");
		frmChat.getContentPane().add(lblIp, "cell 0 0,alignx right,aligny center");
		
		txtIp = new JTextField();
		frmChat.getContentPane().add(txtIp, "cell 1 0,growx,aligny top");
		txtIp.setColumns(10);
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtIp.getText().isEmpty()) {
					ClienteApp.main(new String[] {txtIp.getText()});
					SeletorDeIP.this.frmChat.dispose();
				}
			}
		});
		frmChat.getContentPane().add(btnConectar, "cell 1 1,growx,aligny top");
		
		JPanel panel = new JPanel();
		frmChat.getContentPane().add(panel, "cell 1 2,grow");
		
		JLabel lblOu = new JLabel("OU");
		panel.add(lblOu);
		
		JButton btnHostear = new JButton("Hostear");
		btnHostear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String IP = null;
				try {
					IP = (java.net.Inet4Address.getLocalHost().getHostAddress());
					//System.out.println(java.net.InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Thread() {
					public void run() {
						servidor.Servidor.main(null);
					}
				}.start();
				ClienteApp.main(new String[] {IP});
				SeletorDeIP.this.frmChat.dispose();
			}
		});
		frmChat.getContentPane().add(btnHostear, "cell 1 3,growx,aligny top");
	}

}
