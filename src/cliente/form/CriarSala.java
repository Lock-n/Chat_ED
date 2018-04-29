package cliente.form;

import cliente.Cliente;
import cliente.ClienteApp;
import cliente.Conexao;
import cliente.Lobby;
import servidor.Comando;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.Conexao;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.awt.event.ActionEvent;
import java.awt.Font;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class CriarSala extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomesala;
	private JButton btnVoltar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CriarSala frame = new CriarSala();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CriarSala() {
		setTitle("Criar sala");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 304, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][98.00][grow][]", "[16.00][][][]"));
		
		btnVoltar = new JButton("<--");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CriarSala.this.dispose();
				Cliente.frm_lobby = new Lobby();
				Cliente.frm_lobby.setVisible(true);
			}
		});
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(btnVoltar, "cell 0 0");
		
		JLabel lblNomeDaSala = new JLabel("Nome da sala:");
		contentPane.add(lblNomeDaSala, "cell 0 1,alignx trailing");
		
		txtNomesala = new JTextField();
		contentPane.add(txtNomesala, "cell 1 1 2 1,growx");
		txtNomesala.setColumns(10);
		
		JButton btnCriar = new JButton("Criar");
		btnCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtNomesala.getText().isEmpty()) {
					String nome_sala = txtNomesala.getText();
					try {
						Conexao.transmissor.writeObject(new Comando("L_CRIAR_SALA", new Serializable[] {nome_sala}));
						Conexao.transmissor.writeObject(new Comando("L_ENTRAR_SALA", new Serializable[] {nome_sala}));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Cliente.frm_ca = new ClienteApp(nome_sala);
					Cliente.frm_ca.setVisible(true);
					CriarSala.this.dispose();
				}
			}
		});
		contentPane.add(btnCriar, "cell 0 3 4 1,alignx center");
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtNomesala, btnVoltar, btnCriar, lblNomeDaSala, contentPane}));
	}

}
