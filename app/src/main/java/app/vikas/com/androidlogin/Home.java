package app.vikas.com.androidlogin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class Home extends AppCompatActivity {
    String JSON_STRING;
    String stuid;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView textView6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView6=(TextView)findViewById(R.id.textView6);
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.STU_NAME);
        stuid=intent.getStringExtra(MainActivity.STU_ID);
        //System.out.println(username);
        final TextView textView = (TextView) findViewById(R.id.textView3);

        textView.setText(name);
        class BackgroundTask extends AsyncTask<Void,Void,String>{
            String json_url;
            @Override
            protected void onPreExecute(){
                json_url="http://192.168.43.169/AndroidL/json_get_data.php";
            }
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url=new URL(json_url);
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    System.out.println(stuid);
                    String post_data= URLEncoder.encode("studentid","UTF-8")+"="+URLEncoder.encode(stuid,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    StringBuilder stringBuilder=new StringBuilder();
                    while((JSON_STRING=bufferedReader.readLine())!=null){
                        stringBuilder.append(JSON_STRING+"\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch(IOException e){
                    e.printStackTrace();
                }
                return null;
            }
            protected void onPostExecute(String result){
                //TextView textView=(TextView)findViewById(R.id.textView4);
                //textView.setText(result);
                JSONObject jsonObject;
                JSONArray jsonArray;
                AttendanceAdapter attendanceAdapter=new AttendanceAdapter(Home.this,R.layout.row_layout);
                ListView listView=(ListView)findViewById(R.id.ListView);
                listView.setAdapter(attendanceAdapter);
                try {
                    jsonObject=new JSONObject(result);
                    jsonArray=jsonObject.getJSONArray("server_response");
                    System.out.println(jsonArray.length());
                    if(jsonArray.length()==0){
                        textView6.setText("No Attendance data to show");
                    }
                    else{
                        textView6.setText("");
                    }
                    int count=0;
                    String subject;
                    int total;
                    while(count<jsonArray.length()){
                            JSONObject jo=jsonArray.getJSONObject(count);
                            subject=jo.getString("Coursename");
                            total=jo.getInt("Total");
                        System.out.println("Printing Subject with Total"+subject+total);
                        Attendance attendance=new Attendance(subject,total);
                        count++;
                        attendanceAdapter.add(attendance);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }
        final BackgroundTask backgroundTask=new BackgroundTask();
        backgroundTask.execute();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);//Swipe
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);//Swipe
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new BackgroundTask().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
