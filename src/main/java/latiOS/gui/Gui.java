package latiOS.gui;

import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import latiOS.config.Config;
import latiOS.config.ConfigDataTypes;

public class Gui {

	private JDialog FirstTimeSetupWindow;
	private JLabel lblBotToken;
	private JLabel lblBotName;
	private JTextField botName;
	private JLabel lblCommandPrefix;
	private JTextField commandPrefix;
	private JButton btnDone;
	private JTextField botToken;
	@SuppressWarnings("rawtypes")
	private JComboBox LoggingLevel;
	private JLabel lblLoggingLevel;
	private JLabel lblBotOwnerId;
	private JTextField botOwnerID;

	public static void start(String[] args) {
		try {
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					try {
						Gui window = new Gui();
						window.FirstTimeSetupWindow.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		FirstTimeSetupWindow = new JDialog();
		FirstTimeSetupWindow.setFocusTraversalPolicyProvider(true);
		FirstTimeSetupWindow.setResizable(false);
		FirstTimeSetupWindow.setModalityType(ModalityType.APPLICATION_MODAL);
		FirstTimeSetupWindow.setModal(true);
		FirstTimeSetupWindow.setIconImage(Toolkit.getDefaultToolkit()
				.getImage("D:\\Photos\\Pokemon\\LatiBot-blue-eyes-noshine-no-feathered.png"));
		FirstTimeSetupWindow.setTitle("LatiOS Config");
		FirstTimeSetupWindow.setBounds(100, 100, 550, 300);
		FirstTimeSetupWindow.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		FirstTimeSetupWindow.getContentPane()
				.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(10dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("86dlu:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(26dlu;default)"),
				ColumnSpec.decode("max(31dlu;default)"),
				ColumnSpec.decode("max(63dlu;default)"),
				ColumnSpec.decode("max(46dlu;default)"),
				ColumnSpec.decode("max(44dlu;default):grow"),
				ColumnSpec.decode("max(53dlu;default)"),
				ColumnSpec.decode("max(89dlu;default)"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(6dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(10dlu;default)"),
				RowSpec.decode("max(15dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(10dlu;default)"),
				RowSpec.decode("max(15dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(10dlu;default)"),
				RowSpec.decode("max(15dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(10dlu;default)"),
				RowSpec.decode("max(15dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(30dlu;default)"),}));

		lblBotToken = new JLabel("Bot Token");
		lblBotToken.setHorizontalAlignment(SwingConstants.CENTER);
		FirstTimeSetupWindow.getContentPane().add(lblBotToken, "4, 4, left, bottom");
		
		lblLoggingLevel = new JLabel("Logging Level");
		lblLoggingLevel.setHorizontalAlignment(SwingConstants.LEFT);
		lblLoggingLevel.setVerticalAlignment(SwingConstants.BOTTOM);
		FirstTimeSetupWindow.getContentPane().add(lblLoggingLevel, "9, 4, 2, 1");
		
		botToken = new JTextField();
		botToken.setHorizontalAlignment(SwingConstants.LEFT);
		FirstTimeSetupWindow.getContentPane().add(botToken, "4, 5, 4, 1, fill, default");
		botToken.setColumns(10);
		
		LoggingLevel = new JComboBox();
		LoggingLevel.setModel(new DefaultComboBoxModel(new String[] {"All", "Debug", "Warn", "Fatal", "Info", "Trace"}));
		LoggingLevel.setSelectedIndex(4);
		FirstTimeSetupWindow.getContentPane().add(LoggingLevel, "9, 5, 2, 1, fill, default");

		lblBotName = new JLabel("Bot Name");
		lblBotName.setHorizontalAlignment(SwingConstants.CENTER);
		FirstTimeSetupWindow.getContentPane().add(lblBotName, "4, 7, left, bottom");

		botName = new JTextField();
		botName.setHorizontalAlignment(SwingConstants.LEFT);
		lblBotName.setLabelFor(botName);
		botName.setText("LatiBot");
		FirstTimeSetupWindow.getContentPane().add(botName, "4, 8, fill, top");
		botName.setColumns(10);

		lblCommandPrefix = new JLabel("Command Prefix");
		lblCommandPrefix.setHorizontalAlignment(SwingConstants.CENTER);
		FirstTimeSetupWindow.getContentPane().add(lblCommandPrefix, "4, 10, left, bottom");

		commandPrefix = new JTextField();
		commandPrefix.setHorizontalAlignment(SwingConstants.LEFT);
		lblCommandPrefix.setLabelFor(commandPrefix);
		commandPrefix.setText("!");
		FirstTimeSetupWindow.getContentPane().add(commandPrefix, "4, 11, fill, top");
		commandPrefix.setColumns(10);

		btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FirstTimeSetupWindow.dispose();
				Config cfg = new Config();
				try {
					cfg.addValue("botToken", ConfigDataTypes.STRING, "This is the token LatiOS uses to connect to the bot account in your server", false, "null", botToken.getText());
					cfg.addValue("botName", ConfigDataTypes.STRING, "The name of the bot account", false, botName.getText());
					cfg.addValue("commandPrefix", ConfigDataTypes.STRING, "This is the prifix that LatiOS will look for in messages to signify a command", false, "!", commandPrefix.getText());
					cfg.addValue("loggingLevel", ConfigDataTypes.STRING, "The level of logs to show", false, "Info", LoggingLevel.getSelectedItem().toString());
					cfg.addValue("botOwnerID", ConfigDataTypes.STRING, "The Server owner's Discord ID", false, botOwnerID.getText());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		lblBotOwnerId = new JLabel("Bot Owner ID");
		FirstTimeSetupWindow.getContentPane().add(lblBotOwnerId, "4, 13");
		
		botOwnerID = new JTextField();
		FirstTimeSetupWindow.getContentPane().add(botOwnerID, "4, 14, fill, default");
		botOwnerID.setColumns(10);
		btnDone.setToolTipText("Click here to finish inputing the configuration options");
		FirstTimeSetupWindow.getContentPane().add(btnDone, "8, 16, center, center");
	}
}
