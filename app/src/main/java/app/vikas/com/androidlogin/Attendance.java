package app.vikas.com.androidlogin;

/**
 * Created by VIKAS on 6/6/2017.
 */

public class Attendance {
    private String subject;
    private int total,outof;
    private double percent;
    public Attendance(String subject,int total,int outof,double percent){
        this.setSubject(subject);
        this.setTotal(total);
        this.setOutof(outof);
        this.setPercent(percent);
    }
    public String getSubject(){
        return subject;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getOutof() {
        return outof;
    }

    public void setOutof(int outof) {
        this.outof = outof;
    }
}
