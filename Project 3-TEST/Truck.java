import java.text.DateFormat;
import java.util.GregorianCalendar;

public class Truck extends Auto {

    private boolean FourByFour;

    public Truck() {
        super();
    }

    public double getCost() {
        return getBoughtCost();
    }

    @Override
    double getSoldBoughtCost(double buyPrice, double soldPrice) {
        return soldPrice-buyPrice;
    }

    public Truck(GregorianCalendar boughtOn, String name,
                  String nameOfBuyer, String trimPackage, boolean fourByFour) {
        super(boughtOn, name, nameOfBuyer);
        trim = trimPackage;
        FourByFour = fourByFour;
    }

    public Truck(GregorianCalendar boughtOn, String name,
                 int boughtCost, String trimPackage, boolean fourByFour) {
        super(boughtOn, name, boughtCost);
        trim = trimPackage;
        FourByFour = fourByFour;
    }

    public boolean isFourByFour() {
        return FourByFour;
    }

    public void setFourByFour(boolean fourByFour) {
        FourByFour = fourByFour;
    }

    @Override
    public String toString() {
        return "Truck     " +
                "trim=" + trim + "    " +
                ", FourByFour=" + FourByFour + "     " +
                ", autoName='" + autoName + '\'' + "     " +
                ' ';
    }
}