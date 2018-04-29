package cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class SeletorDeNick extends JFrame {
	static String nick;
	private JPanel contentPane;
	private JTextField txtNick;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SeletorDeNick frame = new SeletorDeNick();
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
	public SeletorDeNick() {
		setTitle("Selecione um nick");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 406, 128);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][216.00,grow][]", "[8.00][][]"));
		
		JLabel lblSeEstaJanela = new JLabel("Se esta janela est\u00E1 sempre abrindo, tente usar um nick diferente");
		lblSeEstaJanela.setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentPane.add(lblSeEstaJanela, "cell 2 0");
		
		JLabel lblNick = new JLabel("Nick:");
		contentPane.add(lblNick, "cell 1 1,alignx trailing");
		
		txtNick = new JTextField();
		contentPane.add(txtNick, "cell 2 1,growx");
		txtNick.setColumns(10);
		
		JButton btnDefinir = new JButton("Definir");
		btnDefinir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controle.seletor_de_nick_fechado = true;
				SeletorDeNick.nick = txtNick.getText();
				SeletorDeNick.this.dispose();
			}
		});
		contentPane.add(btnDefinir, "cell 2 2,alignx center");
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtNick, btnDefinir, lblNick, lblSeEstaJanela, contentPane}));
	}

}
