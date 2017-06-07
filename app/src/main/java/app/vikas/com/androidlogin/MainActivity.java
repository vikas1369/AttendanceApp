package app.vikas.com.androidlogin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

import android.app.Dialog;
import android.app.ProgressDialog;

public class MainActivity extends AppCompatActivity {
    EditText username,password;
    String user,pass;
    public static final String USER_NAME = "USERNAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=(EditText)findViewById(R.id.editText1);
        password=(EditText)findViewById(R.id.editText2);

    }
    public void onLogin(View view){
        user=username.getText().toString();
        pass=password.getText().toString();
        String type="login";
        //BackgroundWorker backgroundWorker=new BackgroundWorker(this);
        //backgroundWorker.execute(type,user,pass);
        login(type,user,pass);

    }
public void login(String type, String username,String password){
    class BackgroundWorker extends AsyncTask<String,Void,String> {
        Context context;
        AlertDialog alertDialog;
        BackgroundWorker(Context ctx){
            context=ctx;
        }
        @Override
        protected String doInBackground(String... params) {
            String type=params[0];
            String login_url="http://192.168.43.169/AndroidL/login.php";
            if(type.equals("login")){
                try{
                    String username=params[1];
                    String password=params[2];
                    URL url=new URL(login_url);
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String post_data= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                            +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String result=new String();
                    String line;
                    while((line=bufferedReader.readLine())!=null){
                        result+=line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                }
                catch (MalformedURLException e){
                    e.printStackTrace();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }
        private Dialog loadingDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog=new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Login Status");
            loadingDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Loading...");
        }

        @Override
        protected void onPostExecute(String result) {
            loadingDialog.dismiss();
            if(result.equalsIgnoreCase("success")){
                Intent intent = new Intent(context, Home.class);
                intent.putExtra(USER_NAME, user);
                //finish();
                Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }else {
                alertDialog.setMessage(result);
                alertDialog.show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    BackgroundWorker backgroundWorker=new BackgroundWorker(this);
    backgroundWorker.execute(type,username,password);
}
}
