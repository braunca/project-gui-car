import java.text.DateFormat;
import java.util.GregorianCalendar;

public class Car extends Auto {

    private String trim;
    private boolean turbo;
    public Car() {
    }

    public double getCost() {
        return getBoughtCost();
    }

    public Car(GregorianCalendar boughtOn,  String name,
               String nameOfBuyer, String trim, boolean turbo) {
        super(boughtOn, name, nameOfBuyer);
        this.trim = trim;
        this.turbo = turbo;
    }

    public Car(GregorianCalendar boughtOn,  String name,
               int boughtCost, String trim, boolean turbo) {
        super(boughtOn, name, boughtCost);
        this.trim = trim;
        this.turbo = turbo;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    @Override
    double getSoldBoughtCost(double buyPrice, double soldPrice) {
        return soldPrice - buyPrice;
    }

    public boolean isTurbo() {
        return turbo;
    }

    public void testingGithub() {
        System.out.println("this tests github");
    }

    public void setTurbo(boolean turbo) {
        this.turbo = turbo;
    }

    @Override
    public String toString() {
        return "Car    " +
                "trim='" + trim + '\'' +  "    " +
                ", turbo=" + turbo + "    " +
                ", autoName='" + autoName + '\'' + "    " +
                ' ';
    }
}