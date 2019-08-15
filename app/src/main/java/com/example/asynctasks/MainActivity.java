package com.example.asynctasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText editText;
    ProgressBar progressBar;
    TextView textView;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.etRequestField);
        progressBar = findViewById(R.id.pbProgressBar);
        textView = findViewById(R.id.tvProgressBarText);

        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnStart:
                new ProgressTask().execute(editText.getText().toString());
                break;
            case R.id.btnStop:
                break;
        }
    }

    class ProgressTask extends AsyncTask<String, Integer, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            for (int i = 0; i < 100; i++)
            {
                publishProgress(i);
                SystemClock.sleep(40);
            }
            return "https://yandex.ru/search/?text=" + strings[0];
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            progressBar.setProgress(values[0]);
            textView.setText(values[0] + "%");
        }

        @Override
        protected void onPostExecute(String s)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
            startActivity(browserIntent);
        }
    }
}
