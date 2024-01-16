import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;

/*****************************************************************
 * Maintains the GUI1024 for the red box rental store
 *****************************************************************/
public class GUICarDealer extends JFrame implements ActionListener{
	/** Holds menu bar */
	private JMenuBar menus;

	/** menus in the menu bar */
	private JMenu fileMenu;
	private JMenu actionMenu;

	/** menu items in each of the menus */
	private JMenuItem openSerItem;
	private JMenuItem saveSerItem;
	private JMenuItem saveTextItem;
	private JMenuItem openTextItem;
	private JMenuItem exitItem;
    private JMenuItem soldScreen;
    private JMenuItem boughtScreen;
    private JMenuItem overDueScreen;

	private JMenuItem boughtCarItem;
	private JMenuItem boughtTruckItem;
	private JMenuItem soldItem;

	/** Holds the list engine */
	private ListEngine DList;
	private JPanel panel;

	/** Holds JListArea */
	private JTable jListArea;

	/*****************************************************************
	 * A constructor that starts a new GUI1024 for the rental store
	 *****************************************************************/
	public GUICarDealer(){
		//adding menu bar and menu items
		menus = new JMenuBar();
		fileMenu = new JMenu("File");
		actionMenu = new JMenu("Action");

		openSerItem = new JMenuItem("Open File");
		saveSerItem = new JMenuItem("Save File");
		saveTextItem = new JMenuItem("Save Text");
		openTextItem = new JMenuItem("Open Text");
		exitItem = new JMenuItem("Exit");
        soldScreen = new JCheckBoxMenuItem("Sold Screen");
        boughtScreen = new JCheckBoxMenuItem("Bought Screen");
        overDueScreen = new JCheckBoxMenuItem("90 Days OverDue Screen");
		boughtTruckItem = new JMenuItem("Bought a Truck");
		boughtCarItem = new JMenuItem("Bought a Car");
		soldItem = new JMenuItem("Sold Car or Truck");

		//adding items to bar
		fileMenu.add(openSerItem);
		fileMenu.add(saveSerItem);
		fileMenu.add(openTextItem);
		fileMenu.add(saveTextItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		fileMenu.addSeparator();
        fileMenu.add(soldScreen);
        fileMenu.add(boughtScreen);
        fileMenu.add(overDueScreen);
		actionMenu.add(boughtTruckItem);
		actionMenu.add(boughtCarItem);
		actionMenu.addSeparator();
		actionMenu.add(soldItem);

		menus.add(fileMenu);
		menus.add(actionMenu);

		//adding actionListener
		openSerItem.addActionListener(this);
		saveSerItem.addActionListener(this);
		openTextItem.addActionListener(this);
		saveTextItem.addActionListener(this);
		exitItem.addActionListener(this);
        soldScreen.addActionListener(this);
        boughtScreen.addActionListener(this);
        overDueScreen.addActionListener(this);
		boughtTruckItem.addActionListener(this);
		boughtCarItem.addActionListener(this);
		soldItem.addActionListener(this);

		setJMenuBar(menus);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		DList = new ListEngine();
		jListArea = new JTable(DList);
		JScrollPane scrollList = new JScrollPane(jListArea);
		scrollList.setPreferredSize(new Dimension(800,300));
		panel.add(scrollList);

		add(panel, BorderLayout.CENTER);

		setVisible(true);
		setSize(950,450);

		jListArea.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DList.updateHeader(jListArea.getColumnName(jListArea.columnAtPoint(e.getPoint())));
				repaint();
			}
		});
	}

	/*****************************************************************
	 * This method handles event-handling code for the GUI1024
	 * @param e - Holds the action event parameter
	 *****************************************************************/
	public void actionPerformed(ActionEvent e) {

		Object comp = e.getSource();

		if (saveSerItem == comp || saveTextItem == comp) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showSaveDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile().getAbsolutePath();
				if (saveSerItem == e.getSource()) {
					DList.saveDatabase(filename);
					System.out.println("Saved serialized");
				}
				if(saveTextItem == e.getSource()) {
					DList.saveAsText(filename);
					System.out.println("Saved by text");
				}
			}
		}

		if (openSerItem == comp || openTextItem == comp) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile().getAbsolutePath();
				if (openSerItem == e.getSource()) {
					DList.loadDatabase(filename);
					System.out.println("Open serialized");
					DList.setDisplay(0);
				}
				if(openTextItem == e.getSource()) {
					DList.loadFromText(filename);
					System.out.println("Opened by text");
					DList.setDisplay(0);
				}
			}
		}

        if(comp == exitItem){
            System.exit(0);
        }

        if(comp == boughtScreen){
            DList.setDisplay(0);
            repaint();
            soldScreen.setSelected(false);
            overDueScreen.setSelected(false);
            soldItem.setEnabled(true);
			boughtCarItem.setEnabled(true);
			boughtTruckItem.setEnabled(true);
        }

        if(comp == soldScreen){
            DList.setDisplay(1);
            repaint();
			boughtScreen.setSelected(false);
			overDueScreen.setSelected(false);
            soldItem.setEnabled(false);
			boughtCarItem.setEnabled(false);
			boughtTruckItem.setEnabled(false);
        }

        if(comp == overDueScreen){
            DList.setDisplay(2);
            repaint();
			soldScreen.setSelected(false);
			boughtScreen.setSelected(false);
            soldItem.setEnabled(false);
			boughtCarItem.setEnabled(false);
			boughtTruckItem.setEnabled(false);
        }

		//MenuBar options
		if(e.getSource() == boughtTruckItem){
			Auto auto = new Truck();
			boughtTruckDialog dialog = new boughtTruckDialog(this, auto);
			if(dialog.getCloseStatus() == boughtTruckDialog.OK){
				DList.add(auto);
			}
		}

		if(e.getSource() == boughtCarItem){
			Auto auto = new Car();
			boughtCarDialog dialog = new boughtCarDialog(this, auto);
			if(dialog.getCloseStatus() == boughtCarDialog.OK){
				DList.add(auto);
			}
		}

		if (soldItem == e.getSource()) {
			int index = jListArea.getSelectedRow();
			if(index > -1) {
				Auto unit = DList.get(index);
				soldOnDialog dialog = new soldOnDialog(this, unit);
				if(unit.getSoldOn() == null){
					JOptionPane.showMessageDialog(null, "No car was sold.");
				} else {
					JOptionPane.showMessageDialog(null, "Be sure to thank " + unit.getNameOfBuyer() +
							" for buying the " + unit.getAutoName() + ". The difference in price was $" + unit.getSoldBoughtCost(unit.getBoughtCost(), unit.getSoldPrice()));
				}
				DList.setDisplay(0);
				repaint();
			}
			else
				JOptionPane.showMessageDialog(null, "Please select a vehicle");
		}
	}


	public static void main(String[] args) {
		new GUICarDealer();
	}
}