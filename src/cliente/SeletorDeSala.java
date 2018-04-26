package cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.JList;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SeletorDeSala extends JFrame {

	private JPanel contentPane;
	private JTextField txtNick;
	static String nick;
	static String nome_sala;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SeletorDeSala frame = new SeletorDeSala(null);
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
	public SeletorDeSala(String[] nomeSalas) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				Controle.seletor_de_sala_fechado = true;
			}
		});
		setTitle("Informa\u00E7\u00F5es");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblDefinaSuasInformaes = new JLabel("Defina suas informa\u00E7\u00F5es");
		panel.add(lblDefinaSuasInformaes);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblNick = new JLabel("Nick:");
		lblNick.setBounds(41, 25, 68, 14);
		panel_1.add(lblNick);
		
		txtNick = new JTextField();
		txtNick.setBounds(119, 22, 169, 20);
		txtNick.setText("nick");
		panel_1.add(txtNick);
		txtNick.setColumns(10);
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		if (nomeSalas != null)
		for (String nome : nomeSalas) {
			model.addElement(nome);
		}
		JList lstSalas = new JList(model);
		
		lstSalas.setBounds(41, 57, 247, 261);
		panel_1.add(lstSalas);
		
		JButton btnDefinir = new JButton("Definir");
		btnDefinir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if ((txtNick.getText() != null)) {
					
					SeletorDeSala.nick = txtNick.getText();
					
					if (lstSalas.getSelectedIndex() < 0) lstSalas.setSelectedIndex(0);
					
					SeletorDeSala.nome_sala = (String)lstSalas.getSelectedValue();
					
					Controle.seletor_de_sala_fechado = true;
					SeletorDeSala.this.dispose();
				}
			}
		});
		btnDefinir.setBounds(119, 329, 89, 23);
		panel_1.add(btnDefinir);
	}
}
