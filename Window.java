package com.cooksys.assessment;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultListModel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JMenu;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import java.awt.Component;
import javax.swing.ImageIcon;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class Window {

	private JFrame frmPcPartPicker;

	/**
	 * Launch the application. The main method is the entry point to a Java application. 
	 * For this assessment, you shouldn't have to add anything to this.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frmPcPartPicker.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application. This is the constructor for this Window class.
	 * All of the code here will be executed as soon as a Window object is made.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. This is where Window Builder
	 * will generate its code.
	 */
	public void initialize() {
		frmPcPartPicker = new JFrame();
		frmPcPartPicker.setTitle("PC Part Picker");
		frmPcPartPicker.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frmPcPartPicker.setBounds(100, 100, 507, 549);
		frmPcPartPicker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final DefaultListModel<String> partListRight = new DefaultListModel<String>();
		JMenuBar menuBar = new JMenuBar();
		frmPcPartPicker.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		//Load Button
		JMenuItem mntmFile = new JMenuItem("Load");
		mntmFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				partListRight.clear();
				try {
					load(partListRight);
				} catch (JAXBException e1) { 
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnNewMenu.add(mntmFile);
		
		//Save Button
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					save(partListRight);
				} catch (JAXBException e1) { 
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnNewMenu.add(mntmSave);
		
		//Exit Button
		JMenuItem Exit = new JMenuItem("Exit");
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0); //simple way to exit
			}
		});
		mnNewMenu.add(Exit);
		
		frmPcPartPicker.getContentPane().setLayout(new BoxLayout(frmPcPartPicker.getContentPane(), BoxLayout.X_AXIS));
		
		//Left panel
		JPanel LeftPanel = new JPanel();
		LeftPanel.setBackground(Color.WHITE);
		LeftPanel.setLayout(new BorderLayout(0, 0));
		
		
		//Left part list never changes so elements are added now
		final DefaultListModel<String> partListLeft = new DefaultListModel<String>();

	    partListLeft.addElement("Case");
	    partListLeft.addElement("Motherboard");
	    partListLeft.addElement("CPU");
	    partListLeft.addElement("GPU");
	    partListLeft.addElement("PSU");
	    partListLeft.addElement("RAM");
	    partListLeft.addElement("HDD");
	    
		final JList<String> LeftList = new JList<String>(partListLeft);
		LeftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //set to single selection to simplify adding and deleting
		LeftPanel.add(LeftList, BorderLayout.CENTER);
		frmPcPartPicker.getContentPane().add(LeftPanel);
		
		//Right panel
		JPanel RightPanel = new JPanel();
		RightPanel.setBackground(Color.WHITE);
		RightPanel.setLayout(new BorderLayout(0, 0));

		//Center Panel
	    JPanel CenterPanel = new JPanel();
		frmPcPartPicker.getContentPane().add(CenterPanel);
		CenterPanel.setLayout(new BoxLayout(CenterPanel, BoxLayout.Y_AXIS));
		
		//Used default images for buttons
		JButton AddButton = new JButton("Add");
		AddButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		AddButton.setIcon(new ImageIcon(Window.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
				
		//If the add button is pushed the selected element of the left list is added to the right list
        AddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				partListRight.addElement(LeftList.getSelectedValue());
			}
		});
		CenterPanel.add(AddButton);
		
		JButton DeleteButton = new JButton("Remove");
		DeleteButton.setIcon(new ImageIcon(Window.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
		DeleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		CenterPanel.add(DeleteButton);
		
		//If the remove button is pushed the selected index is deleted
		final JList<String> RightList = new JList<String>(partListRight);
		DeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedIndex = RightList.getSelectedIndex();
				if(selectedIndex != -1) partListRight.remove(selectedIndex);
			}
		});
		
		RightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		RightPanel.add(RightList, BorderLayout.NORTH);
		RightPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		frmPcPartPicker.getContentPane().add(RightPanel);
	}
	
	
    //These classes handle the save and load buttons. A Parts class is created to be used by the XML file.  
	@XmlRootElement
	@XmlAccessorType(XmlAccessType.FIELD)
	static public class Parts {

	    @XmlElement(name = "part")
	    private List<String> part;

	    public List<String> getPart() { return part; }
	    public void setPart(List<String> part) { this.part = part; }
	}
	/*
	 * Save function takes parts on the right list and saves them to an XML file 
	 * Input: DefaultListModel<String> partListRight 
	 * Output: PartList.xml
	 * Exceptions: JAXBException
	 */
	private void save(DefaultListModel<String> partListRight) throws JAXBException{
		    ArrayList<String> save = new ArrayList<>();
		    for (int i = 0; i < partListRight.size(); i++) {
		        save.add((String)partListRight.getElementAt(i));
		    }
		    Parts parts = new Parts();
		    parts.setPart(save);
		    
		    File file = new File("PartList.xml");

		    JAXBContext context = JAXBContext.newInstance(Parts.class);
		    Marshaller marshaller = context.createMarshaller();
		    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		    marshaller.marshal(parts, file);    
     }
	/*
	 * Load function takes saved file and fills it into partsListRight 
	 * Input: DefaultListModel<String> partListRight 
	 * Output: none
	 * Exceptions: JAXBException
	 */
	private void load(DefaultListModel<String> partListRight) throws JAXBException{
		JAXBContext jaxbContext = JAXBContext.newInstance(Parts.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

	    Parts parts = (Parts) jaxbUnmarshaller.unmarshal(new File("PartList.xml"));
	     
	    for(String part : parts.getPart())
	    {
	        partListRight.addElement(part);
	    }
	}
}
