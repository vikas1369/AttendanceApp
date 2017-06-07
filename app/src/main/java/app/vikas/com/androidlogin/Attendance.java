package app.vikas.com.androidlogin;

/**
 * Created by VIKAS on 6/6/2017.
 */

public class Attendance {
    private String subject;
    private int total;
    public Attendance(String subject,int total){
        this.setSubject(subject);
        this.setTotal(total);
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
}
