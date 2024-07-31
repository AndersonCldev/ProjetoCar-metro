
package View;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.DAO;
import utils.Validador;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

public class Carometro extends JFrame {

	// instanciar objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	// instanciar objeto para fluxo de bytes
	private FileInputStream fis;
	// Variável global para armazenar o tamanho da imagem (Bytes)
	private int tamanho;
	// variável usada na correção do BUG
	private boolean fotoCarregada = false;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JLabel lblData;
	private JTextField txtRA;
	private JLabel lblNewLabel_1;
	private JTextField txtNome;
	private JLabel lblFoto;
	private JScrollPane scrollPaneLista;
	private JList listNomes;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnBuscar;
	private JButton btnCarregar;
	private JButton btnReset;
	private JButton btnAdicionar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Carometro frame = new Carometro();
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
	public Carometro() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
				setarData();
			}
		});
		
		
		setTitle("Carôretro");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Carometro.class.getResource("/img/cisCell.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPaneLista = new JScrollPane();
		scrollPaneLista.setBorder(null);
		scrollPaneLista.setVisible(false);
		scrollPaneLista.setBounds(78, 85, 229, 80);
		contentPane.add(scrollPaneLista);

		listNomes = new JList();
		listNomes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarNome();
			}
		});
		listNomes.setBorder(null);
		scrollPaneLista.setViewportView(listNomes);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 279, 624, 42);
		contentPane.add(panel);
		panel.setLayout(null);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Carometro.class.getResource("/img/conectado2.png")));
		lblStatus.setBounds(582, 0, 32, 43);
		panel.add(lblStatus);

		lblData = new JLabel("");
		lblData.setBackground(new Color(0, 0, 0));
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblData.setBounds(24, 0, 257, 43);
		panel.add(lblData);

		JLabel lblNewLabel = new JLabel("RA");
		lblNewLabel.setBounds(22, 27, 46, 14);
		contentPane.add(lblNewLabel);

		txtRA = new JTextField();
		txtRA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarRA();
			}
		});
		txtRA.addKeyListener(new KeyAdapter() {

			@Override

			// Aqui inserimos os RA aceitos
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});

		txtRA.setBounds(81, 25, 86, 20);
		contentPane.add(txtRA);
		txtRA.setColumns(10);
		// Uso do PlainDocument para limitar os campos
		txtRA.setDocument(new Validador(6));
		// Atenção

		lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(22, 66, 46, 14);
		contentPane.add(lblNewLabel_1);

		txtNome = new JTextField();
		txtNome.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarNomes();
			}

			
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					scrollPaneLista.setVisible(false);
					int confirma = JOptionPane.showConfirmDialog(null,
							"Aluno não cadastrado\nDeseja cadastra este aluno?", "Aviso", JOptionPane.YES_OPTION);
					if (confirma == JOptionPane.YES_OPTION) {
						txtRA.setEditable(false);
						btnBuscar.setEnabled(false);
						btnCarregar.setEnabled(true);
						btnAdicionar.setEnabled(true);

					}
				}
			}
		});
		txtNome.setBounds(78, 63, 229, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		// Uso do PlainDocument para limitar os campos
		txtNome.setDocument(new Validador(30));

		lblFoto = new JLabel("");
		lblFoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblFoto.setIcon(new ImageIcon(Carometro.class.getResource("/img/image-gallery.png")));
		lblFoto.setBounds(362, 12, 256, 256);
		contentPane.add(lblFoto);

		btnCarregar = new JButton("Carregar Foto");
		btnCarregar.setEnabled(false);
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto();

			}
		});
		btnCarregar.setForeground(new Color(0, 128, 64));
		btnCarregar.setBounds(22, 111, 101, 23);
		contentPane.add(btnCarregar);

		btnAdicionar = new JButton("");
		btnAdicionar.setEnabled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(Carometro.class.getResource("/img/add-button.png")));
		btnAdicionar.setBounds(288, 185, 64, 64);
		contentPane.add(btnAdicionar);

		btnReset = new JButton("");
		btnReset.setEnabled(false);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		btnReset.setIcon(new ImageIcon(Carometro.class.getResource("/img/eraser.png")));
		btnReset.setToolTipText("Limpar Campo");
		btnReset.setBounds(96, 185, 64, 64);
		contentPane.add(btnReset);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setToolTipText("Buscar RA");
		btnBuscar.setForeground(new Color(0, 128, 64));
		btnBuscar.setBounds(194, 24, 78, 20);
		contentPane.add(btnBuscar);

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setIcon(new ImageIcon(Carometro.class.getResource("/img/write (2).png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.setBounds(214, 185, 64, 64);
		contentPane.add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Carometro.class.getResource("/img/delete.png")));
		btnExcluir.setToolTipText("Apagar dados");
		btnExcluir.setBounds(22, 185, 64, 64);
		contentPane.add(btnExcluir);
		
				JButton btnSobre = new JButton("");
				btnSobre.setBounds(317, 111, 32, 34);
				contentPane.add(btnSobre);
				btnSobre.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Sobre sobre = new Sobre();
						sobre.setVisible(true);
					}
				});
				btnSobre.setIcon(new ImageIcon(Carometro.class.getResource("/img/information-button.png")));
				btnSobre.setContentAreaFilled(false);
				btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnSobre.setBorderPainted(false);
		
		// Esse código abaixo força execução do botão Buscar em RA
		btnBuscar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        buscarRA();
		    }
		});
		
		// Atenção
		this.setLocationRelativeTo(null);
	}// Fim contrutor

	// Neste metodo criamos o status da conexão caso consiga conectar ou não.
	private void status() {
		try {
			con = dao.conectar();
			if (con == null) {
				// System.out.println("Erro de conexão");
				lblStatus.setIcon(new ImageIcon(Carometro.class.getResource("/img/NãoConectado.png")));
			} else {
				// System.out.println("Banco de dados Concectado");
				lblStatus.setIcon(new ImageIcon(Carometro.class.getResource("/img/conectado2.png")));
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}
	}

	private void setarData() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblData.setText(formatador.format(data));
	}

	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Seleione o arquivo");
		;
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de Imgens (*.PNJ, *JPG, *JPEG)", "png", "jpg", "jpeg"));
		int resultado = jfc.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {
				fis = new FileInputStream(jfc.getSelectedFile());
				tamanho = (int) jfc.getSelectedFile().length();
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(),
						lblFoto.getHeight(), Image.SCALE_SMOOTH);
				lblFoto.setIcon(new ImageIcon(foto));
				fotoCarregada = true;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	// Metodo "Botão Verde" que adiciona nome e foto no banco de dados
	private void adicionar() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome.");
			txtNome.requestFocus();
		} else if (tamanho == 0) {
			JOptionPane.showMessageDialog(null, "Selecione a foto");

		} else {

			String insert = "insert into alunos(nome,foto) values(?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(insert);
				pst.setString(1, txtNome.getText());
				pst.setBlob(2, fis, tamanho);
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso.");
					reset();
				} else {
					JOptionPane.showMessageDialog(null, "ERRO! Aluno não cadastrado");

				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void buscarRA() {
		if (txtRA.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o RA");
			txtRA.requestFocus();
		} else {
			String readRA = "SELECT * FROM alunos WHERE ra = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readRA);
				pst.setString(1, txtRA.getText());
				rs = pst.executeQuery();

				if (rs.next()) {
					txtNome.setText(rs.getString("nome"));

					// Carregar a imagem, se existir
					Blob blob = rs.getBlob("foto");
					if (blob != null) {
						byte[] img = blob.getBytes(1, (int) blob.length());
						BufferedImage imagem = ImageIO.read(new ByteArrayInputStream(img));
						ImageIcon icone = new ImageIcon(
								imagem.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH));
						lblFoto.setIcon(icone);
					} else {
						lblFoto.setIcon(new ImageIcon(Carometro.class.getResource("/img/image-gallery.png")));
					}

					// Habilitar/desabilitar componentes
					txtRA.setEditable(false);
					btnBuscar.setEnabled(false);
					btnCarregar.setEnabled(true);
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
				} else {
					// Se não encontrar o aluno, oferecer opção de cadastrar
					int confirma = JOptionPane.showConfirmDialog(null,
							"Aluno não cadastrado\nDeseja iniciar um novo cadastro?", "Aviso", JOptionPane.YES_OPTION);
					if (confirma == JOptionPane.YES_OPTION) {
						txtRA.setEditable(false);
						txtRA.setText(null);
						btnBuscar.setEnabled(false);
						txtNome.requestFocus();
						btnCarregar.setEnabled(true);
						btnAdicionar.setEnabled(true);
					}
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao buscar aluno: " + e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (pst != null)
						pst.close();
					if (con != null)
						con.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	// Metodo faz com que ao começar a digitar letras, comece a aparecer nome de
	// alunos que ja estão cadastrados no banco de dados
	private void listarNomes() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listNomes.setModel(modelo);
		String readLista = "select * from alunos where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPaneLista.setVisible(true);
				modelo.addElement(rs.getString(2));
				;
				if (txtNome.getText().isEmpty()) {
					scrollPaneLista.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarNome() {
		int linha = listNomes.getSelectedIndex();
		if (linha >= 0) {
			String readNome = "select * from alunos where nome like '" + txtNome.getText() + "%'"
					+ "order by nome limit " + (linha) + ", 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readNome);
				rs = pst.executeQuery();
				while (rs.next()) {
					scrollPaneLista.setVisible(false);
					txtRA.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					// Para trazer as imagens pesquisadas no campo nome do nosso banco de dados
					Blob blob = (Blob) rs.getBlob(3);
					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e) {
						System.out.println(e);
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
							lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);
					lblFoto.setIcon(foto);
					txtRA.setEditable(false);
					btnBuscar.setEnabled(false);
					btnCarregar.setEnabled(true);
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);

					// Até aqui é o codigo para trazer as fotos do banco de dados

				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPaneLista.setVisible(false);
		}
	}

	
	private void editar() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome.");
			txtNome.requestFocus();
		} else {
			if (fotoCarregada == true) {
				String update = "update alunos set nome =?,foto =? where ra =?";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(update);
					pst.setString(1, txtNome.getText());
					pst.setBlob(2, fis, tamanho);
					pst.setString(3, txtRA.getText());
					int confirma = pst.executeUpdate();
					if (confirma == 1) {
						JOptionPane.showMessageDialog(null, "Dados do aluno alterados.");
						reset();
					} else {
						JOptionPane.showMessageDialog(null, "ERRO! Dados não alterados.");

					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				String update = "update alunos set nome =? where ra =?";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(update);
					pst.setString(1, txtNome.getText());
					pst.setString(2, txtRA.getText());
					int confirma = pst.executeUpdate();
					if (confirma == 1) {
						JOptionPane.showMessageDialog(null, "Dados do aluno alterados.");
						reset();
					} else {
						JOptionPane.showMessageDialog(null, "ERRO! Dados não alterados.");

					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}

		}

	}

	private void excluir() {
		int confirmarExcluir = JOptionPane.showConfirmDialog(null, "Confirma exlusão deste Aluno?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirmarExcluir == JOptionPane.YES_NO_OPTION) {

			String delete = "delete from alunos where ra=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtRA.getText());
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					reset();
					JOptionPane.showMessageDialog(null, "Aluno excluido!");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	// Metodo para deletar imagem nome e foto
	private void reset() {
		scrollPaneLista.setVisible(false);
		txtRA.setText(null);
		txtNome.setText(null);
		;
		lblFoto.setIcon(new ImageIcon(Carometro.class.getResource("/img/image-gallery.png")));
		txtNome.requestFocus();
		fotoCarregada = false;
		tamanho = 0;
		txtRA.setEditable(false);
		txtRA.setText(null);
		btnBuscar.setEnabled(true);
		btnCarregar.setEnabled(false);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);

	}
}
