import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class
Auto implements Serializable {

    private static final long serialVersionUID = 1L;
    protected GregorianCalendar boughtOn;
    protected GregorianCalendar soldOn;
    protected String autoName;
    protected String nameOfBuyer;
    protected double boughtCost;
    protected double soldPrice;
    protected String trim;
    protected int daysOverDue;

    protected int month;
    protected int day;
    protected int year;

    Calendar cal = Calendar.getInstance();
    protected int currentMonth = (cal.get(Calendar.MONTH))+1;
    protected int currentDay = cal.get(Calendar.DAY_OF_MONTH);
    protected int currentYear = cal.get(Calendar.YEAR);

    public Auto() {
    }

    public abstract double getCost();

    public Auto(GregorianCalendar boughtOn, String name, String nameOfBuyer) {
        this.boughtOn = boughtOn;
        this.autoName = name;
        this.nameOfBuyer = nameOfBuyer;
    }

    public Auto(GregorianCalendar boughtOn, String name, int boughtCost) {
        this.boughtOn = boughtOn;
        this.autoName = name;
        this.boughtCost = boughtCost;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public GregorianCalendar getBoughtOn() {
        return boughtOn;
    }

    public void setBoughtOn(GregorianCalendar boughtOn) {
        this.boughtOn = boughtOn;
    }

    public GregorianCalendar getSoldOn() {
        return soldOn;
    }

    public void setSoldOn(GregorianCalendar soldOn) {
        this.soldOn = soldOn;
    }

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }

    public String getAutoName() {
        return autoName;
    }

    public void setAutoName(String autoName) {
        this.autoName = autoName;
    }

    public String getNameOfBuyer() {
        return nameOfBuyer;
    }

    public void setNameOfBuyer(String nameOfBuyer) {
        this.nameOfBuyer = nameOfBuyer;
    }

    public double getBoughtCost() {
        return boughtCost;
    }

    public void setBoughtCost(double boughtCost) {
        this.boughtCost = boughtCost;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    abstract double getSoldBoughtCost(double buyPrice, double soldPrice);


    public int getOverDueDays(){
        String newDate = DateFormat.getDateInstance(DateFormat.SHORT).format(this.getBoughtOn().getTime());
        String[] splitDates = newDate.split("/", 3);
        month = Integer.parseInt(splitDates[0]);
        day = Integer.parseInt(splitDates[1]);
        year = 2000 + Integer.parseInt(splitDates[2]);
        daysOverDue = daysToGo();
        return daysOverDue;
    }

    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    public static int daysInMonth(int month, int year){
        if (month == 2 && isLeapYear(year))
            return 29;
        else
            return DAYS_IN_MONTH[month];
    }

    private static final int[] DAYS_IN_MONTH = {0, 31, 28, 31, 30, 31, 30, 31,
            31, 30, 31, 30, 31};

    public void inc(){
        this.day++;
        if (this.day > daysInMonth(this.month, this.year)){
            this.day = 1;
            this.month++;
            if(this.month > 12){
                this.month = 1;
                this.year++;
            }
        }
    }

    public int daysToGo() {
        int daysToGo = 0;
        while(!((currentYear == this.year) && (currentMonth == this.month) && (currentDay == this.day))){
            inc();
            daysToGo++;
        }
        return daysToGo;
    }
}