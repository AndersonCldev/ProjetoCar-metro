package View;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class Sobre extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Sobre() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/cisCell.png")));
		setTitle("Sobre o Carônetro");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Projeto Carômetro");
		lblNewLabel.setBounds(33, 23, 131, 21);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("@author Anderson Cleiton ");
		lblNewLabel_1.setBounds(33, 67, 153, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Sobre a licença AND");
		lblNewLabel_2.setBounds(33, 103, 141, 14);
		getContentPane().add(lblNewLabel_2);

		JButton btnGithub = new JButton("");
		btnGithub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				link("https://github.com/AndersonCldev/ProjetctCar-metro");
			}
		});
		btnGithub.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGithub.setContentAreaFilled(false);
		btnGithub.setBorderPainted(false);
		btnGithub.setIcon(new ImageIcon(Sobre.class.getResource("/img/github (2).png")));
		btnGithub.setBounds(33, 140, 64, 63);
		getContentPane().add(btnGithub);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setBounds(309, 180, 89, 23);
		getContentPane().add(btnOk);

	}

	private void link(String url) {
		Desktop desktop = Desktop.getDesktop();
		try {
			URI uri = new URI(url);
			desktop.browse(uri);

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
