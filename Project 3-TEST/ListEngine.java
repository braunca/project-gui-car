import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;

/*********************************************************************
 * A car dealership program that allows you to purchase vehicles for
 * your inventory and sell to customers. Data is presented in
 * sorted lists across three main screens.
 ********************************************************************/

public class ListEngine extends AbstractTableModel {

     // Static variable defining the bought screen screen
    private static final int defaultScrn = -1;

    // Static variable defining the bought screen screen
    private static final int boughtScrn = 0;

     // Static variable defining the sold screen screen
    private static final int soldScrn = 1;

     //Static variable defining the "90 days overdue screen" screen
    private static final int overDueScrn = 2;


     // Array list used to store vehicle information objects
    private ArrayList<Auto> listAutos;
 
     // Array list containing vehicle information that will be sorted
    private ArrayList<Auto> filteredListAutos;

     //Sets display value to the default
    private int display = defaultScrn;

    // String array containing the headers for the bought screen
    private String[] columnNamesBought = {"Auto Name", "Bought Cost",
            "Bought Date", "Trim Package", "Four by Four", "Turbo"};

     // String array containing the headers for the sold screen
    private String[] columnNamesSold = {"Auto Name", "Bought Cost", "Bought Date", "Buyer's Name",
            "Sold For", "Sold On"};

     // String array containing the headers for the 90 days overdue screen
    private String[] columnNamesOverDue = {"Auto Name", "Bought Cost", "Bought Date", "Days overDue"};

    /*********************************************************************
     * Gets the column header using one of the columnNames string arrays
     * and a column number
     *
     * @param col The column for which to pull the header for.
     * @return columnNamesSold Returns column names for the sold screen
     * if display is set to soldScrn.
     * @return columnNamesOverDue Returns column names for the sold screen
     * if display is set to overDueScrn.
     * @return columnNamesBought Returns column names for the sold screen
     * if display is set to neither soldScrn or overDueScrn.
     ********************************************************************/
    @Override
    public String getColumnName(int col) {
        switch (display) {
            case soldScrn:
                return columnNamesSold[col];
            case overDueScrn:
                return columnNamesOverDue[col];
            default:
                return columnNamesBought[col];
        }
    }

    /*********************************************************************
     * Constructor initializes array lists listAutos and filteredListAutos
     * and creates the table.
     *
     * @return None.
     ********************************************************************/
    public ListEngine() {
        super();
        listAutos = new ArrayList<Auto>();
        filteredListAutos = new ArrayList<Auto>();
        createList();
    }

    /*********************************************************************
     * Sets variable display to boughtScrn, soldScrn, or overDueScrn
     * respective to the user's selection in the file menu.
     *
     * @param selected The screen selected by the user.
     * @return None
     ********************************************************************/
    public void setDisplay(int selected) {
        display = selected;
        updateScreen();
    }

    /*********************************************************************
     * Gets the display.
     * @return display The current value of display.
     ********************************************************************/
    public int getDisplay() {
        return display;
    }

    /*********************************************************************
     * Removes a vehicle from the list.
     * @return null Replaces vehicle in list with null.
     ********************************************************************/
    public Auto remove(int i) {
        return null;
    }

    /*********************************************************************
     * Adds vehicle to listAutos array list if display is set to
     * the default and adds to filteredListAutos if a screen has been selected.
     *
     * @param a Object with all vehicle information.
     ********************************************************************/
    public void add(Auto a) {
        switch (display) {
            case soldScrn:
                filteredListAutos.add(a);
            case overDueScrn:
                filteredListAutos.add(a);
            case boughtScrn:
                filteredListAutos.add(a);
            default:
                listAutos.add(a);
        }
        fireTableDataChanged();
    }

    /*********************************************************************
     * Gets vehicle information.
     *
     * @param i Index of vehicle to retrieve.
     * @return listAutos if display is set to default value.
     * @return filteredListAutos if user has selected a screen.
     ********************************************************************/
    public Auto get(int i) {
        switch (display) {
            case soldScrn:
                return filteredListAutos.get(i);
            case overDueScrn:
                return filteredListAutos.get(i);
            case boughtScrn:
                return filteredListAutos.get(i);
            default:
                return listAutos.get(i);
        }
    }

    /*********************************************************************
     * Gets the size.
     *
     * @return display The current value of display.
     ********************************************************************/
    public int getSize() {
        switch (display) {
            case soldScrn:
                return filteredListAutos.size();
            case overDueScrn:
                return filteredListAutos.size();
            case boughtScrn:
                return filteredListAutos.size();
            default:
                return listAutos.size();
        }
    }

    /*********************************************************************
     * Gets the number of how many rows there are in a list.
     *
     * @return listAutos Returns size of listAutos if display is set to default.
     * @return filteredListAutos Returns size of filteredListAutos if
     * user has selected a screen.
     ********************************************************************/
    @Override
    public int getRowCount() {
        switch (display) {
            case soldScrn:
                return filteredListAutos.size();
            case overDueScrn:
                return filteredListAutos.size();
            case boughtScrn:
                return filteredListAutos.size();
            default:
                return listAutos.size();
        }
    }

    /*********************************************************************
     * Gets the number of headers contained in an ColumnNames array.
     *
     * @return columnNamesSold Returns if display is set to soldScrn.
     * @return columnNamesOverDue Returns if display is set to overDueScrn.
     * @return columnNamesBought Returns if display is set to neither
     * soldScrn or overDueScrn.
     ********************************************************************/
    @Override
    public int getColumnCount() {
        switch (display) {
            case soldScrn:
                return columnNamesSold.length;
            case overDueScrn:
                return columnNamesOverDue.length;
            default:
                return columnNamesBought.length;
        }
    }

    /*********************************************************************
     * Gets a certain detail about a specified vehicle .
     *
     * @param col The column for which we are retrieving information for.
     * @param row The index for a vehicle in the list.
     ********************************************************************/
    @Override
    public Object getValueAt(int row, int col) {
        switch (display) {
            case soldScrn:
                switch (col) {
                    case 0:
                        return (filteredListAutos.get(row).getAutoName());

                    case 1:
                        return (filteredListAutos.get(row).getBoughtCost());

                    case 2:
                        return (DateFormat.getDateInstance(DateFormat.SHORT)
                                .format(filteredListAutos.get(row).getBoughtOn().getTime()));

                    case 3:
                        return (filteredListAutos.get(row).getNameOfBuyer());

                    case 4:
                        return (filteredListAutos.get(row).getSoldPrice());
                    case 5:
                        return (DateFormat.getDateInstance(DateFormat.SHORT)
                                .format(filteredListAutos.get(row).getSoldOn().getTime()));

                    default:
                        throw new RuntimeException("JTable row,col out of range: " + row + " " + col);
                }
            case overDueScrn:
                switch (col) {
                    case 0:
                        return (filteredListAutos.get(row).getAutoName());

                    case 1:
                        return (filteredListAutos.get(row).getBoughtCost());

                    case 2:
                        return (DateFormat.getDateInstance(DateFormat.SHORT)
                                .format(filteredListAutos.get(row).getBoughtOn().getTime()));
                    case 3:
                        return (filteredListAutos.get(row).getOverDueDays());
                    default:
                        throw new RuntimeException("JTable row,col out of range: " + row + " " + col);
                }
            case boughtScrn:
                switch (col) {
                    case 0:
                        return (filteredListAutos.get(row).getAutoName());

                    case 1:
                        return (filteredListAutos.get(row).getBoughtCost());

                    case 2:
                        return (DateFormat.getDateInstance(DateFormat.SHORT)
                                .format(filteredListAutos.get(row).getBoughtOn().getTime()));

                    case 3:
                        return (filteredListAutos.get(row).getTrim());

                    case 4:
                    case 5:
                        if (filteredListAutos.get(row) instanceof Truck)
                            if (col == 4)
                                return (((Truck) filteredListAutos.get(row)).isFourByFour());
                            else
                                return "-";

                        else {
                            if (col == 5)
                                return (((Car) filteredListAutos.get(row)).isTurbo());
                            else
                                return "-";
                        }
                    default:
                        throw new RuntimeException("JTable row,col out of range: " + row + " " + col);
                }
            default:
                switch (col) {
                    case 0:
                        return (listAutos.get(row).getAutoName());

                    case 1:
                        return (listAutos.get(row).getBoughtCost());

                    case 2:
                        return (DateFormat.getDateInstance(DateFormat.SHORT)
                                .format(listAutos.get(row).getBoughtOn().getTime()));

                    case 3:
                        return (listAutos.get(row).getTrim());

                    case 4:
                    case 5:
                        if (listAutos.get(row) instanceof Truck)
                            if (col == 4)
                                return (((Truck) listAutos.get(row)).isFourByFour());
                            else
                                return "-";

                        else {
                            if (col == 5)
                                return (((Car) listAutos.get(row)).isTurbo());
                            else
                                return "-";
                        }
                    default:
                        throw new RuntimeException("JTable row,col out of range: " + row + " " + col);
                }
        }
    }

    /*********************************************************************
     * Updates the table with a sorted list based on which display is
     * currently selected.
     ********************************************************************/
    public void updateScreen() {
        switch (display) {
            case soldScrn:
                filteredListAutos = (ArrayList<Auto>) listAutos.stream().filter(arg -> arg.soldOn != null).collect(Collectors.toList());
                Collections.sort(filteredListAutos, Comparator.comparing(arg2 -> arg2.nameOfBuyer));
                break;
            case boughtScrn:
                filteredListAutos = (ArrayList<Auto>) listAutos.stream().filter(arg -> arg.soldOn == null).collect(Collectors.toList());
                Collections.sort(filteredListAutos, Comparator.comparing(arg2 -> arg2.getBoughtOn()));
                break;
            case overDueScrn:
                filteredListAutos = (ArrayList<Auto>) listAutos.stream().filter(arg -> arg.soldOn == null).collect(Collectors.toList());
                filteredListAutos = (ArrayList<Auto>) filteredListAutos.stream().filter(arg -> arg.getOverDueDays() >= 90).collect(Collectors.toList());
                Collections.sort(filteredListAutos, Comparator.comparing(arg2 -> arg2.getOverDueDays()));
                break;
        }
        fireTableDataChanged();
        fireTableStructureChanged();
    }

    /*********************************************************************
     * Sorts column after clicking header.
     ********************************************************************/
    public void updateHeader(String col) {
        System.out.println("Sorting by " + col);
        if (display == soldScrn)
            filteredListAutos = (ArrayList<Auto>) listAutos.stream().filter(arg -> arg.soldOn != null).collect(Collectors.toList());
        else if (display == overDueScrn) {
            filteredListAutos = (ArrayList<Auto>) listAutos.stream().filter(arg -> arg.soldOn == null).collect(Collectors.toList());
            filteredListAutos = (ArrayList<Auto>) filteredListAutos.stream().filter(arg -> arg.getOverDueDays() >= 90).collect(Collectors.toList());
        } else
            filteredListAutos = (ArrayList<Auto>) listAutos.stream().filter(arg -> arg.soldOn == null).collect(Collectors.toList());

        switch (col) {
            case "Auto Name":
                Collections.sort(filteredListAutos, Comparator.comparing(arg2 -> arg2.getAutoName()));
                break;
            case "Bought Cost":
                Collections.sort(filteredListAutos, Comparator.comparing(arg2 -> arg2.getBoughtCost()));
                break;
            case "Bought Date":
                Collections.sort(filteredListAutos, Comparator.comparing(arg2 -> arg2.getBoughtOn()));
                break;
            case "Buyer's Name":
                Collections.sort(filteredListAutos, Comparator.comparing(arg2 -> arg2.getNameOfBuyer()));
                break;
            case "Sold For":
                Collections.sort(filteredListAutos, Comparator.comparing(arg2 -> arg2.getSoldPrice()));
                break;
            case "Sold On":
                Collections.sort(filteredListAutos, Comparator.comparing(arg2 -> arg2.getSoldOn()));
                break;
            case "Trim Package":
                Collections.sort(filteredListAutos, Comparator.comparing(arg2 -> arg2.getTrim()));
                break;
            case "Days overDue":
                Collections.sort(filteredListAutos, Comparator.comparing(arg2 -> arg2.getOverDueDays()));
                break;
        }
        fireTableDataChanged();
        fireTableStructureChanged();
    }

    /*********************************************************************
     * Saves listAutos to a file as a serializable.
     *
     * @param filename Name of the file where the data is being saved to.
     ********************************************************************/
    public void saveDatabase(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(listAutos);
            os.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error in saving db");

        }
    }

    /*********************************************************************
     * Loads listAutos as a serializable from a file.
     *
     * @param filename Name of the file where the data is being loaded from.
     ********************************************************************/
    public void loadDatabase(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream is = new ObjectInputStream(fis);

            listAutos = (ArrayList<Auto>) is.readObject();
            fireTableDataChanged();
            is.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error in loading db");
        }
    }

    /*****************************************************************
     * Saves listAutos as text in a file.
     *
     * @param filename Name of the file where the data is being loaded from
     ******************************************************************/
    public void saveAsText(String filename) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)));

            Auto auto;
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            pw.println(listAutos.size());

            for (int i = 0; i < listAutos.size(); i++) {
                auto = listAutos.get(i);

                if (auto instanceof Car) {
                    pw.println("Car");
                    pw.println(((Car) auto).isTurbo());
                } else {
                    pw.println("Truck");
                    pw.println(((Truck) auto).isFourByFour());
                }

                pw.println(auto.getAutoName());
                pw.println(auto.getBoughtCost());

                GregorianCalendar tempBoughtDate;
                tempBoughtDate = auto.getBoughtOn();

                pw.println(df.format(tempBoughtDate.getTime()));

                pw.println(auto.getTrim());

                if (auto.getNameOfBuyer() != null)
                    pw.println(auto.getNameOfBuyer());
                else
                    pw.println("No buyer's name");

                pw.println(auto.getSoldPrice());

                GregorianCalendar tempSoldDate;
                tempSoldDate = auto.getSoldOn();

                if (tempSoldDate != null) {
                    pw.println(df.format(tempSoldDate.getTime()));
                } else
                    pw.println("No sold date");
            }
            pw.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error in saving as text");
        }
    }

    /*****************************************************************
     * Loads listAutos as text from a file.
     *
     * @param filename Name of the file where the data is being loaded from.
     ******************************************************************/
    public void loadFromText(String filename) {
        try {
            listAutos.clear();
            Scanner fileReader = new Scanner(new File(filename));

            int numOfAutos = Integer.parseInt(fileReader.nextLine());

            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            Car tempCar;
            Truck tempTruck;

            for (int i = 0; i < numOfAutos; i++) {
                String autoType = fileReader.nextLine();
                boolean boolAutoValue = Boolean.parseBoolean(fileReader.nextLine());
                String autoName = fileReader.nextLine();
                double autoBoughtPrice = Double.parseDouble(fileReader.nextLine());

                GregorianCalendar tempBoughtDate = new GregorianCalendar();
                Date d = df.parse(fileReader.nextLine());
                tempBoughtDate.setTime(d);

                String autoTrim = fileReader.nextLine();

                String autoBuyerName = fileReader.nextLine();
                if (autoBuyerName.equals("No buyer's name"))
                    autoBuyerName = null;

                double autoSoldPrice = Double.parseDouble(fileReader.nextLine());

                GregorianCalendar tempSoldDate = new GregorianCalendar();
                String checkSoldDate = fileReader.nextLine();
                if (checkSoldDate.equals("No sold date")) {
                    tempSoldDate = null;

                } else {
                    Date d2 = df.parse(checkSoldDate);
                    tempSoldDate.setTime(d2);
                }
                if (autoType.equals("Car")) {
                    tempCar = new Car(tempBoughtDate, autoName, autoBuyerName, autoTrim, boolAutoValue);
                    tempCar.setBoughtCost(autoBoughtPrice);
                    tempCar.setSoldOn(tempSoldDate);
                    tempCar.setSoldPrice(autoSoldPrice);
                    listAutos.add(tempCar);
                }
                if (autoType.equals("Truck")) {
                    tempTruck = new Truck(tempBoughtDate, autoName, autoBuyerName, autoTrim, boolAutoValue);
                    tempTruck.setBoughtCost(autoBoughtPrice);
                    tempTruck.setSoldOn(tempSoldDate);
                    tempTruck.setSoldPrice(autoSoldPrice);
                    listAutos.add(tempTruck);
                }
            }
            fireTableDataChanged();
            fileReader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in loading from text");
        }
    }

    /*****************************************************************
     * Saves listAutos as text in a file.
     ******************************************************************/
    public void createList() {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        GregorianCalendar temp1 = new GregorianCalendar();
        GregorianCalendar temp2 = new GregorianCalendar();
        GregorianCalendar temp3 = new GregorianCalendar();
        GregorianCalendar temp4 = new GregorianCalendar();
        GregorianCalendar temp5 = new GregorianCalendar();
        GregorianCalendar temp6 = new GregorianCalendar();
        try {
            Date d1 = df.parse("3/20/2019");
            temp1.setTime(d1);
            Date d2 = df.parse("9/20/2019");
            temp2.setTime(d2);
            Date d3 = df.parse("12/20/2018");
            temp3.setTime(d3);
            Date d4 = df.parse("9/20/2019");
            temp4.setTime(d4);
            Date d5 = df.parse("1/20/2010");
            temp5.setTime(d5);
            Date d6 = df.parse("10/20/2019");
            temp6.setTime(d6);
            Car Car1 = new Car(temp1, "Outback", 18000, "LX", false);
            Car Car2 = new Car(temp2, "Chevy", 11000, "EX", false);
            Car Car3 = new Car(temp3, "Focus", 19000, "EX", true);
            Truck Truck1 = new Truck(temp4, "F150", 12000, "Tow", false);
            Truck Truck2 = new Truck(temp5, "F250", 42000, "NA", false);
            Truck Truck3 = new Truck(temp1, "F350", 2000, "Turbo", true);
            add(Car1);
            add(Car2);
            add(Car3);
            add(Truck1);
            add(Truck2);
            add(Truck3);
        } catch (ParseException e) {
            throw new RuntimeException("Error in testing, creation of list");
        }
        setDisplay(0);

//        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//        GregorianCalendar temp1 = new GregorianCalendar();
//        GregorianCalendar temp2 = new GregorianCalendar();
//        GregorianCalendar temp3 = new GregorianCalendar();
//        GregorianCalendar temp4 = new GregorianCalendar();
//        GregorianCalendar temp5 = new GregorianCalendar();
//        GregorianCalendar temp6 = new GregorianCalendar();
//
//        try {
//            Date d1 = df.parse("3/20/2019");
//            temp1.setTime(d1);
//            Date d2 = df.parse("9/20/2019");
//            temp2.setTime(d2);
//            Date d3 = df.parse("12/20/2018");
//            temp3.setTime(d3);
//            Date d4 = df.parse("9/20/2019");
//            temp4.setTime(d4);
//            Date d5 = df.parse("1/20/2010");
//            temp5.setTime(d5);
//            Date d6 = df.parse("10/20/2019");
//            temp6.setTime(d6);
//
//            Car Car1 = new Car(temp3, "Outback", "Buyer1", "LX", false);
//            Car Car2 = new Car(temp2, "Chevy", "Buyer2", "EX", false);
//            Car Car3 = new Car(temp6, "Focus", "Buyer3", "EX", true);
//            Truck Truck1 = new Truck(temp4, "F150", "BuyerA", "LX", false);
//            Truck Truck2 = new Truck(temp1, "F250", "BuyerB", "LX", false);
//            Truck Truck3 = new Truck(temp5, "F350", "BuyerC", "EX", true);
//
//            add(Car1);
//            add(Car2);
//            add(Car3);
//            add(Truck1);
//            add(Truck2);
//            add(Truck3);
//        } catch (ParseException e) {
//            throw new RuntimeException("Error in testing, creation of list");
//        }
    }
}