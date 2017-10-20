package latiOS.gui;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import latiOS.config.Config;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gui {

	private JFrame FirstTimeSetupWindow;
	private JLabel lblBotToken;
	private JPasswordField botToken;
	private JLabel lblBotName;
	private JTextField botName;
	private JLabel lblCommandPrefix;
	private JTextField commandPrefix;
	private JLabel lblNewLabel;
	private JComboBox comboBox;
	private JButton btnDone;

	/**
	 * Launch the application.
	 */
	public static void start(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.FirstTimeSetupWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		FirstTimeSetupWindow = new JFrame();
		FirstTimeSetupWindow.setIconImage(Toolkit.getDefaultToolkit()
				.getImage("D:\\Photos\\Pokemon\\LatiBot-blue-eyes-noshine-no-feathered.png"));
		FirstTimeSetupWindow.setTitle("LatiOS Config");
		FirstTimeSetupWindow.setBounds(100, 100, 661, 307);
		FirstTimeSetupWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FirstTimeSetupWindow.getContentPane()
				.setLayout(new FormLayout(
						new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(10dlu;default)"),
								FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("86dlu"),
								FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(26dlu;default)"),
								ColumnSpec.decode("max(32dlu;default)"), ColumnSpec.decode("max(41dlu;default)"),
								ColumnSpec.decode("max(46dlu;default)"), FormSpecs.DEFAULT_COLSPEC,
								FormSpecs.DEFAULT_COLSPEC, ColumnSpec.decode("max(89dlu;default)"), },
						new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("max(6dlu;default)"),
								FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("max(10dlu;default)"),
								RowSpec.decode("max(15dlu;default)"), FormSpecs.RELATED_GAP_ROWSPEC,
								RowSpec.decode("max(10dlu;default)"), RowSpec.decode("max(15dlu;default)"),
								FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("max(10dlu;default)"),
								RowSpec.decode("max(15dlu;default)"), FormSpecs.RELATED_GAP_ROWSPEC,
								RowSpec.decode("max(10dlu;default)"), RowSpec.decode("max(15dlu;default)"),
								FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("max(30dlu;default)"), }));

		lblBotToken = new JLabel("Bot Token");
		lblBotToken.setHorizontalAlignment(SwingConstants.CENTER);
		FirstTimeSetupWindow.getContentPane().add(lblBotToken, "4, 4, left, bottom");

		botToken = new JPasswordField();
		lblBotToken.setLabelFor(botToken);
		botToken.setHorizontalAlignment(SwingConstants.CENTER);
		FirstTimeSetupWindow.getContentPane().add(botToken, "4, 5, 4, 1, fill, top");

		lblBotName = new JLabel("Bot Name");
		lblBotName.setHorizontalAlignment(SwingConstants.CENTER);
		FirstTimeSetupWindow.getContentPane().add(lblBotName, "4, 7, left, bottom");

		botName = new JTextField();
		lblBotName.setLabelFor(botName);
		botName.setText("LatiBot");
		FirstTimeSetupWindow.getContentPane().add(botName, "4, 8, fill, top");
		botName.setColumns(10);

		lblCommandPrefix = new JLabel("Command Prefix");
		lblCommandPrefix.setHorizontalAlignment(SwingConstants.CENTER);
		FirstTimeSetupWindow.getContentPane().add(lblCommandPrefix, "4, 10, left, bottom");

		commandPrefix = new JTextField();
		lblCommandPrefix.setLabelFor(commandPrefix);
		commandPrefix.setText("!");
		FirstTimeSetupWindow.getContentPane().add(commandPrefix, "4, 11, fill, top");
		commandPrefix.setColumns(10);

		lblNewLabel = new JLabel("Log to Text Channel");
		lblNewLabel.setToolTipText("");
		FirstTimeSetupWindow.getContentPane().add(lblNewLabel, "4, 13");

		comboBox = new JComboBox();
		comboBox.setToolTipText(
				"LatiOS will create a text channel that is only accessable by the owner and itself to log all events");
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Enabled", "Disabled" }));
		comboBox.setSelectedIndex(0);
		comboBox.setMaximumRowCount(2);
		FirstTimeSetupWindow.getContentPane().add(comboBox, "4, 14, fill, top");

		btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Config cfg = new Config();
				cfg.release(); 
			}
		});
		btnDone.setToolTipText("Click here to finish inputing the configuration options");
		FirstTimeSetupWindow.getContentPane().add(btnDone, "8, 16, center, center");
	}
}
