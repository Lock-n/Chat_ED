package cliente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;

import net.miginfocom.swing.MigLayout;
import servidor.Comando;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.Font;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Lobby extends JFrame {

	private JPanel contentPane;
	static String nick;
	static String nome_sala;
	private JTable tblLobby;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Lobby l = new Lobby();
				l.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Lobby() {
		try {
			Conexao.transmissor.writeObject(new Comando("L_ATUALIZAR_LISTA_SALAS", null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				Controle.seletor_de_sala_fechado = true;
			}
		});
		setTitle("Lobby");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 472, 454);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					Conexao.transmissor.writeObject(new Comando("L_USUARIO_SAI", null));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Lobby.this.dispose();
				System.exit(0);
			}
		});
		menuBar.add(mntmSair);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][47.00,grow][][][][][][][][][][][]", "[][grow][]"));
		
		JLabel lblNick = new JLabel("Nick");
		lblNick.setText(SeletorDeNick.nick);
		
		contentPane.add(lblNick, "cell 1 0");
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Conexao.transmissor.writeObject(new Comando("L_ATUALIZAR_LISTA_SALAS", null));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		contentPane.add(btnAtualizar, "cell 13 0,alignx right");
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableModel tm = Lobby.this.tblLobby.getModel();
				String nome__sala = (String)tm.getValueAt(Lobby.this.tblLobby.getSelectedRow(), 0);
				try {
					Conexao.transmissor.writeObject(new Comando("L_ENTRAR_SALA", new Serializable[] {nome__sala}));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//ClienteApp.main(Conexao.transmissor, Conexao.receptor);
				Cliente.frm_ca = new ClienteApp(nome__sala);
				Cliente.frm_ca.setVisible(true);
				//t_conexao.interrupt();
				Cliente.frm_lobby = null;
				Lobby.this.dispose();
			}
		});
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 1 1 13 1,grow");
		
		tblLobby = new JTable();
		tblLobby.setFont(new Font("Trebuchet MS", Font.ITALIC, 13));
		tblLobby.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
			},
			new String[] {
				"Nome", "N� Online"
			}
		));
		tblLobby.getColumnModel().getColumn(0).setPreferredWidth(347);
		scrollPane.setViewportView(tblLobby);
		
		JButton btnCriarNovaSala = new JButton("Criar nova sala");
		btnCriarNovaSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cliente.form.CriarSala cs = new cliente.form.CriarSala();
				cs.setVisible(true);
				Cliente.frm_lobby = null;
				Lobby.this.dispose();
			}
		});
		contentPane.add(btnCriarNovaSala, "cell 11 2 3 1,alignx right");
		
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tblLobby, btnAtualizar, btnEntrar, btnCriarNovaSala, contentPane, lblNick}));
		contentPane.add(btnEntrar, "cell 1 2 2 1");
	}
	
	public void processarComando(Comando cmd) {
		if (cmd.getCmd().equals("LISTA_DE_SALAS")) {
			
			Object[] info_salas = (Object[])cmd.getComplementos();
			DefaultTableModel tblLobby_model = (DefaultTableModel) tblLobby.getModel();
			
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
	}
}
