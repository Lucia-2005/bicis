package t8_alquilerBicis;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.awt.Color;

public class Alquilar {

	private JFrame frame;
	private JTextField txtnombre;
	private JTextField txtedad;
	private JTextField txtbici;
	private JTextField txtbanco;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Alquilar window = new Alquilar();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Alquilar() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(239, 189, 228));
		frame.setTitle("alquiler bicicletas");
		frame.setBounds(100, 100, 633, 393);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		//COMBOBOX
		JComboBox comboBoxAlqBic = new JComboBox();
		comboBoxAlqBic.setBounds(257, 75, 81, 24);
		frame.getContentPane().add(comboBoxAlqBic);
			
			try {
				Connection con=Conexion.getConnection();
				Statement stmtBici=con.createStatement();
				ResultSet rsBici=stmtBici.executeQuery("SELECT * FROM bici WHERE cod_user IS NULL");
				
				while(rsBici.next()) {
					int codigo=Integer.parseInt(rsBici.getString("codigo"));
					comboBoxAlqBic.addItem(codigo);
				}
				
				rsBici.close();
				stmtBici.close();
				con.close();
			}catch(SQLException sqle) {
				sqle.printStackTrace();
				JOptionPane.showMessageDialog(null, "error en la base de datos");
			}
		
		JComboBox comboBoxAlqUs = new JComboBox();
		comboBoxAlqUs.setBounds(257, 39, 81, 24);
		frame.getContentPane().add(comboBoxAlqUs);
		
			try {
				Connection con=Conexion.getConnection();
				Statement stmtUser=con.createStatement();
				ResultSet rsUser=stmtUser.executeQuery("SELECT * FROM usuario WHERE codigo !=1");
				
				while(rsUser.next()) {
					int cod=rsUser.getInt("codigo");
					comboBoxAlqUs.addItem(cod);
				}
				
				rsUser.close();
				stmtUser.close();
				con.close();
			}catch(SQLException sqle) {
				sqle.printStackTrace();
				JOptionPane.showMessageDialog(null, "error en la base de datos");
			}
		
		JComboBox comboBoxDevBic = new JComboBox();
		comboBoxDevBic.setBounds(244, 247, 86, 24);
		frame.getContentPane().add(comboBoxDevBic);
		
		try {
			Connection con=Conexion.getConnection();
			Statement stmtBici=con.createStatement();
			ResultSet rsBici=stmtBici.executeQuery("SELECT * FROM bici WHERE cod_user IS NOT NULL");
			
			while(rsBici.next()) {
				int codigo=Integer.parseInt(rsBici.getString("codigo"));
				comboBoxDevBic.addItem(codigo);
			}
			
			rsBici.close();
			stmtBici.close();
			con.close();
		}catch(SQLException sqle) {
			sqle.printStackTrace();
			JOptionPane.showMessageDialog(null, "error en la base de datos");
		}
		
		
		
		//LABEL
		JLabel lblAadUsuario = new JLabel("AÑADIR USUARIO");
		lblAadUsuario.setBounds(22, 12, 125, 15);
		frame.getContentPane().add(lblAadUsuario);
		
		JLabel lblAadBici = new JLabel("AÑADIR BICI");
		lblAadBici.setBounds(22, 225, 81, 15);
		frame.getContentPane().add(lblAadBici);
		
		JLabel lblAlquilarBici = new JLabel("ALQUILAR BICI");
		lblAlquilarBici.setBounds(234, 12, 114, 15);
		frame.getContentPane().add(lblAlquilarBici);
		
		JLabel lblUsuario = new JLabel("usuario");
		lblUsuario.setBounds(199, 44, 70, 15);
		frame.getContentPane().add(lblUsuario);
		
		JLabel lblBici = new JLabel("bici");
		lblBici.setBounds(199, 76, 70, 15);
		frame.getContentPane().add(lblBici);
		
		JLabel lblDevolverBici = new JLabel("DEVOLVER BICI");
		lblDevolverBici.setBounds(234, 202, 132, 15);
		frame.getContentPane().add(lblDevolverBici);
		
		JLabel lblNombre = new JLabel("nombre");
		lblNombre.setBounds(12, 39, 70, 15);
		frame.getContentPane().add(lblNombre);
		
		JLabel lblEdad = new JLabel("edad");
		lblEdad.setBounds(12, 76, 70, 15);
		frame.getContentPane().add(lblEdad);
		
		JLabel lblCuentaBancaria = new JLabel("Cuenta bancaria");
		lblCuentaBancaria.setBounds(12, 97, 135, 15);
		frame.getContentPane().add(lblCuentaBancaria);
		
		JLabel lblNumeroBici = new JLabel("numero");
		lblNumeroBici.setBounds(12, 252, 59, 15);
		frame.getContentPane().add(lblNumeroBici);
		
		//TABLA
		/**
		 * CREAR LA TABLA, CON SUS 3 PARTES, EL MODELO, LA TABLA, Y LA SCROLLBAR
		 */
			//tabla 1
		DefaultTableModel modeloUser = new DefaultTableModel();
		modeloUser.addColumn("codigo");
		modeloUser.addColumn("nombre");
		modeloUser.addColumn("edad");
		modeloUser.addColumn("cuenta bancaria");
		
		try {
			Connection con=Conexion.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM usuario");
			modeloUser.setRowCount(0);
			while(rs.next()) {
				 Object[] row = new Object[4];
				 row[0] = rs.getInt("codigo");
				 row[1] = rs.getString("nombre");
				 row[2] = rs.getInt("edad");
				 row[3] = rs.getString("cuentaBancaria");
				 modeloUser.addRow(row);
			}
			stmt.close();
			rs.close();
		}catch(SQLException sqle) {
			sqle.printStackTrace();
			JOptionPane.showMessageDialog(null, "error en la base de datos");
		}
		JTable tableUser = new JTable(modeloUser);
		tableUser.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		JScrollPane scrollPaneUser = new JScrollPane(tableUser);
		scrollPaneUser.setBounds(410, 31, 186, 117);
		frame.getContentPane().add(scrollPaneUser);
		
		
			//tabla 2
		DefaultTableModel modeloBici=new DefaultTableModel();
		modeloBici.addColumn("codigo bici");
		modeloBici.addColumn("Codigo usuario");
		
		try {
			Connection con=Conexion.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM bici");
			modeloBici.setRowCount(0);
			while(rs.next()) {
				 Object[] row = new Object[2];
				 row[0] = rs.getInt("codigo");
				 row[1] = rs.getInt("cod_user");
				 modeloBici.addRow(row);
			}
			rs.close();
			stmt.close();
		}catch(SQLException sqle) {
			sqle.printStackTrace();
			JOptionPane.showMessageDialog(null, "error en la base de datos");
		}
		
		JTable tableBici = new JTable(modeloBici);
		tableBici.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		JScrollPane scrollPaneBici = new JScrollPane(tableBici);
		scrollPaneBici.setBounds(410, 178, 186, 117);
		frame.getContentPane().add(scrollPaneBici);
		
		//TEXTFIELD
		txtnombre = new JTextField();
		txtnombre.setBounds(76, 39, 81, 19);
		frame.getContentPane().add(txtnombre);
		txtnombre.setColumns(10);
		
		txtedad = new JTextField();
		txtedad.setBounds(76, 74, 80, 19);
		frame.getContentPane().add(txtedad);
		txtedad.setColumns(10);
		
		txtbanco = new JTextField();
		txtbanco.setBounds(22, 124, 114, 19);
		frame.getContentPane().add(txtbanco);
		txtbanco.setColumns(10);
		
		txtbici = new JTextField();
		txtbici.setBounds(76, 252, 59, 19);
		frame.getContentPane().add(txtbici);
		txtbici.setColumns(10);
		
		
		
		//BOTONES
			//AÑADIR CLIENTE
		JButton btnAadUsu = new JButton("Añadir");
		btnAadUsu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Connection con=Conexion.getConnection();
					PreparedStatement pstmt=con.prepareStatement("INSERT INTO usuario (nombre, edad, cuentaBancaria) VALUES (?, ?, ?)");
					String nombre=txtnombre.getText();
					int edad= Integer.parseInt(txtedad.getText());
					String cuenta=txtbanco.getText();
					
					pstmt.setString(1, nombre);
					pstmt.setInt(2, edad);
					pstmt.setString(3, cuenta);
					pstmt.executeUpdate();
					
					txtnombre.setText("");
					txtedad.setText("");
					txtbanco.setText("");
					pstmt.close();
					
					//actualizar datos
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM usuario");
					modeloUser.setRowCount(0);
					while(rs.next()) {
						 Object[] row = new Object[4];
						 row[0] = rs.getInt("codigo");
						 row[1] = rs.getString("nombre");
						 row[2] = rs.getInt("edad");
						 row[3] = rs.getString("cuentaBancaria");
						 modeloUser.addRow(row);
					}
					rs.close();
					stmt.close();
				}catch(SQLException sqle) {
					sqle.printStackTrace();
					JOptionPane.showMessageDialog(null, "error en la base de datos");
				}
			}
		});
		btnAadUsu.setBounds(22, 160, 86, 25);
		frame.getContentPane().add(btnAadUsu);
		
		
		
			//ALQUILAR BICI
		JButton btnAlquilar = new JButton("Alquilar");
		btnAlquilar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Connection con=Conexion.getConnection();
					PreparedStatement pstmt=con.prepareStatement("UPDATE bici SET cod_user=? WHERE codigo=?");
					
					int codUser= (int) comboBoxAlqUs.getSelectedItem();
					pstmt.setInt(1, codUser);
					int codBici=(int)comboBoxAlqBic.getSelectedItem();
					pstmt.setInt(2, codBici);
					pstmt.executeUpdate();
					pstmt.close();
					
					//actualizar datos
					
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM bici");
					modeloBici.setRowCount(0);
					while(rs.next()) {
						 Object[] row = new Object[2];
						 row[0] = rs.getInt("codigo");
						 row[1] = rs.getInt("cod_user");
						 modeloBici.addRow(row);
					}
					rs.close();
					stmt.close();
					
					
					
				}catch(SQLException sqle) {
					sqle.printStackTrace();
					JOptionPane.showMessageDialog(null, "error en la base de datos");
				}
			}
		});
		btnAlquilar.setBounds(224, 123, 95, 25);
		frame.getContentPane().add(btnAlquilar);
		
		
		
			//DEVOLVER BICI
		JButton btnDevolver = new JButton("Devolver");
		btnDevolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Connection con=Conexion.getConnection();
					PreparedStatement pstmt=con.prepareStatement("UPDATE bici SET cod_user=NULL WHERE codigo=?");
					
					int codBici=(int)comboBoxDevBic.getSelectedItem();
					pstmt.setInt(1, codBici);
					pstmt.executeUpdate();
					pstmt.close();
					
					//actualizar datos
					
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM bici");
					modeloBici.setRowCount(0);
					while(rs.next()) {
						 Object[] row = new Object[2];
						 row[0] = rs.getInt("codigo");
						 row[1] = rs.getInt("cod_user");
						 modeloBici.addRow(row);
					}
					rs.close();
					stmt.close();
				}catch(SQLException sqle) {
					sqle.printStackTrace();
					JOptionPane.showMessageDialog(null, "error en la base de datos");
				}
				
				
			}
		});
		btnDevolver.setBounds(234, 297, 95, 25);
		frame.getContentPane().add(btnDevolver);
		
		
		
		
			//AÑADIR BICI
		JButton btnAadBic = new JButton("Añadir");
		btnAadBic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Connection con=Conexion.getConnection();
					PreparedStatement pstmt=con.prepareStatement("INSERT INTO bici(codigo) VALUES(?)");
					int codigo=Integer.parseInt(txtbici.getText());
					
					pstmt.setInt(1, codigo);
					pstmt.executeUpdate();
					txtbici.setText("");
					pstmt.close();
					
					//actualizar datos
					
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM bici");
					modeloBici.setRowCount(0);
					while(rs.next()) {
						 Object[] row = new Object[2];
						 row[0] = rs.getInt("codigo");
						 row[1] = rs.getInt("cod_user");
						 modeloBici.addRow(row);
					}
					rs.close();
					stmt.close();
					
				}catch(SQLException sqle) {
					sqle.printStackTrace();
					JOptionPane.showMessageDialog(null, "error en la base de datos");
				}
			}
		});
		btnAadBic.setBounds(22, 297, 117, 25);
		frame.getContentPane().add(btnAadBic);
	}
}
