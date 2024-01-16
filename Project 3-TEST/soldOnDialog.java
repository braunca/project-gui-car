import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class soldOnDialog extends JDialog implements ActionListener {

    private JTextField txtName;
    private JTextField txtDate;
    private JTextField txtCost;

    private JButton okButton;
    private JButton cancelButton;
    private int closeStatus;
    private Auto auto;
    static final int OK = 0;
    static final int CANCEL = 1;

    /*********************************************************
     Instantiate a Custom Dialog as 'modal' and wait for the
     user to provide data and click on a button.
     @param parent reference to the JFrame application
     @param auto an instantiated object to be filled with data
     *********************************************************/

    public soldOnDialog(JFrame parent, Auto auto) {
        // call parent and create a 'modal' dialog
        super(parent, true);

        this.auto = auto;
        setTitle("Sold to dialog box");
        closeStatus = CANCEL;
        setSize(400,200);

        // prevent user from closing window
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String stringDate = df.format(date);

        // instantiate and display two text fields
        txtName = new JTextField("Joe",30);
        txtDate = new JTextField(15);
        txtCost = new JTextField("14000.00",15);
        txtDate.setText(stringDate);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(4,2));
        textPanel.add(new JLabel("Name of Buyer: "));
        textPanel.add(txtName);
        textPanel.add(new JLabel("Sold on Date: "));
        textPanel.add(txtDate);
        textPanel.add(new JLabel("Sold for ($): "));
        textPanel.add(txtCost);
        getContentPane().add(textPanel, BorderLayout.CENTER);

        // Instantiate and display two buttons
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        setVisible (true);

    }

    /**************************************************************
     Respond to either button clicks
     @param e the action event that was just fired
     **************************************************************/
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();

        // if OK clicked the fill the object
        if (button == okButton) {
            closeStatus = OK;
            GregorianCalendar temp = new GregorianCalendar();
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            df.setLenient(false);

            Date d;
            boolean validDate = false;
            int count = 0;
            while(!validDate) {
                try {
                    temp = new GregorianCalendar();
                    d = new Date();
                    df = new SimpleDateFormat("MM/dd/yyyy");
                    d = df.parse(this.txtDate.getText());
                    temp.setTime(d);
                    if (temp.compareTo(this.auto.getBoughtOn()) < 0)
                        throw new Exception();
                    validDate = true;
                } catch (Exception e1) {
                    count++;
                    this.txtDate.setText(df.format(Calendar.getInstance().getTime()));
                    JOptionPane.showMessageDialog(null, "Invalid date. Setting date to current date.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    if(count == 5){
                        validDate = true;
                    }
                }
            }

            double temp2 = 5000.0;
            boolean validPrice = false;
            int count2 = 0;
            while(!validPrice) {
                try {
                    temp2 = Double.parseDouble(txtCost.getText());
                    validPrice = true;
                } catch (Exception e1) {
                    count2++;
                    txtCost.setText("5000.0");
                    JOptionPane.showMessageDialog(null, "Invalid price. Setting price to default of $5000.0", "ERROR", JOptionPane.ERROR_MESSAGE);
                    if(count2 == 5){
                        validPrice = true;
                    }
                }
            }
            auto.setSoldOn(temp);
            auto.setNameOfBuyer(txtName.getText());
            auto.setSoldPrice(temp2);
        }

        if(button == cancelButton) {
            dispose();
        }
        dispose();
    }

    /**************************************************************
     Return a String to let the caller know which button
     was clicked
     @return an int representing the option OK or CANCEL
     **************************************************************/
    public int getCloseStatus(){
        return closeStatus;
    }
}