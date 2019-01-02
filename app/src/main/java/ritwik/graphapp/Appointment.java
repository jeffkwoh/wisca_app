package ritwik.graphapp;

public class Appointment {
    public final int id;
    public final String date;
    public final String weight;
    public final String reason;

    public Appointment(int id, String date, String weight, String reason) {
        this.id = id;
        this.date = date;
        this.weight = weight;
        this.reason = reason;
    }

}
