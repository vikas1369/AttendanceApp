package app.vikas.com.androidlogin;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VIKAS on 6/6/2017.
 */

public class AttendanceAdapter extends ArrayAdapter{
    List list=new ArrayList();
    public AttendanceAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Attendance object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        AttendanceHolder attendanceHolder;
        if(row==null){
            LayoutInflater layoutInflater=(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout,parent,false);
            attendanceHolder=new AttendanceHolder();
            attendanceHolder.tx_subject=(TextView) row.findViewById(R.id.tx_subject);
            attendanceHolder.tx_total=(TextView) row.findViewById(R.id.tx_total);
            row.setTag(attendanceHolder);
        }
        else{
            attendanceHolder=(AttendanceHolder) row.getTag();
        }
        Attendance attendance=(Attendance) this.getItem(position);
        attendanceHolder.tx_subject.setText(attendance.getSubject());
        attendanceHolder.tx_total.setText(Integer.toString(attendance.getTotal()));
        return row;
    }
    static class AttendanceHolder{
        TextView tx_subject,tx_total;
    }
}
