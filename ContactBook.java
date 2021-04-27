package GuestBook;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;


public class ContactBook extends JFrame implements ActionListener{	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ArrayList to hold Contact instances
	private ArrayList <Contact> contactList = new ArrayList<>();
	
	//create UI
	private JFrame mainWindow = new JFrame("Contact Book");
	
	private JPanel mainPanel = new JPanel();
	private JPanel inputsLabelPanel = new JPanel();
	private JPanel submitPanel = new JPanel();
	private JPanel inputsEntryPanel = new JPanel();
	
	private JLabel firstNameLabel = new JLabel(" First name" , JLabel.LEFT);
	private JLabel secondNameLabel = new JLabel(" Last name:" , JLabel.LEFT);
	private JLabel addressOneLabel = new JLabel(" Address:" , JLabel.LEFT);  
	private JLabel phoneLabel = new JLabel (" Phone: " , JLabel.LEFT);
	
	private JTextField firstNameText = new JTextField(20);
	private JTextField secondNameText = new JTextField(20);
	private JTextField addressOneText = new JTextField(20);
	private JTextField phoneText = new JTextField(20);
	
	private JButton submit = new JButton("Submit");
	
	private JTextArea scrollText = new JTextArea(50,10);
	private JScrollPane scrollPane = new JScrollPane(scrollText);
	
	private JPanel alertPanel = new JPanel();
	private JFrame alertWindow = new JFrame();
	
	// parameterless constructor
	public ContactBook () {
		
		this.mainWindow.setSize(new Dimension(450,575));
		this.mainWindow.setResizable(false);
		
		this.mainPanel.setLayout(new GridLayout(0,3));
		this.mainPanel.setPreferredSize(new Dimension(600,400));
		
		this.inputsLabelPanel.setLayout(new GridLayout(8,1,0,10));
			
			this.inputsLabelPanel.add(this.firstNameLabel);
			this.inputsLabelPanel.add(this.secondNameLabel);
			this.inputsLabelPanel.add(this.addressOneLabel);
			this.inputsLabelPanel.add(this.phoneLabel);
			
		this.inputsEntryPanel.setLayout(new GridLayout(8,1,0,10));
		
			this.inputsEntryPanel.add(this.firstNameText);
			this.inputsEntryPanel.add(this.secondNameText);
			this.inputsEntryPanel.add(this.addressOneText);
			this.inputsEntryPanel.add(this.phoneText);
		
		// add listener to submit button
		this.submit.addActionListener(this);	
		this.submitPanel.add(this.submit);
			
		this.mainPanel.add(this.inputsLabelPanel);
		this.mainPanel.add(this.inputsEntryPanel);
		this.mainPanel.add(scrollPane);
		this.mainPanel.add(this.submitPanel);
		
		this.mainWindow.add(this.mainPanel);
		this.mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainWindow.setLocationRelativeTo(null);
		this.mainWindow.setVisible(true);
		
		this.alertWindow.add(this.alertPanel);
		this.alertWindow.pack();
		this.alertWindow.setLocationRelativeTo(null);
		this.alertWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.alertWindow.setVisible(false);
		
	}
	
	// populate Contacts list at launch
	private void createDefaultContactList () {
		
			Contact person1 = new Contact ("Joe","Smith","1234 Main Street","617-823-8186");
			Contact person2 = new Contact ("Alma","Strong","1333 Oak Street","312-221-9755");
			
			this.contactList.add(person1);
			this.contactList.add(person2);
	}
	
	private void displayContacts () {
		
		Collections.sort(this.contactList);
		this.scrollText.setText("Contacts \n\n");
		
		for (Contact contact : this.contactList) {
			
			String contactEntry = contact.getFirstName() + " " + contact.getLastName() + "\n" + contact.getAddressOne() + "\n" + contact.getPhoneNumber() + "\n\n";
			
			this.scrollText.setText(this.scrollText.getText() + contactEntry);
		}
	}
	
	// clear inputs after new Contact created
	private void resetInputs () {
		
		this.firstNameText.setText("");
		this.secondNameText.setText("");
		this.addressOneText.setText("");
		this.phoneText.setText("");
	}
	
	
	private JFrame getAlertWindow() {
		
		return this.alertWindow;
	}
	
	
	public static void main(String[] args) {

		//launch app
		ContactBook newBook = new ContactBook();
		newBook.createDefaultContactList();
		newBook.displayContacts();
	
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
					new ContactViewController();
			}
		});

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		// create and validate new Contact
		if (e.getSource().equals(this.submit)) {
			
			try (Contact newContact = new Contact()) {
				
				newContact.validateNameInput(this.firstNameText.getText(), this.secondNameText.getText());
				newContact.setAddressOne(this.addressOneText.getText());
				newContact.validatePhoneInput(this.phoneText.getText());
				newContact.checkForEquals(this.contactList);
				
				this.displayContacts();
				this.resetInputs();
				newContact.close();
				
				
			}
			catch (ContactBookException error) {
				
				JOptionPane.showMessageDialog(this.getAlertWindow(), error.getMessage());
					
			}
			
			catch (Exception error) {
				
				JOptionPane.showMessageDialog(this.getAlertWindow(), error.getMessage());
			}
		}
		
	}
}
