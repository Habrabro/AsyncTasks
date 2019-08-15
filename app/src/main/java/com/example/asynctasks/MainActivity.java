package com.example.asynctasks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText editText;
    ProgressBar progressBar;
    TextView textView;
    ProgressTask progressTask = new ProgressTask();
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
                if (progressTask.getStatus() != AsyncTask.Status.RUNNING)
                {
                    progressTask = new ProgressTask();
                    progressTask.execute(editText.getText().toString());
                }
                break;
            case R.id.btnStop:
                progressTask.cancel(true);
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
                if (isCancelled())
                {
                    return null;
                }
                publishProgress(i);
                SystemClock.sleep(20);
            }
            return getResources().getString(R.string.str_requestURL) + strings[0];
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            progressBar.setProgress(values[0]);
            textView.setText(values[0] + "%");
        }

        @Override
        protected void onCancelled()
        {
            progressBar.setProgress(0);
            textView.setText("0%");
        }

        @Override
        protected void onPostExecute(String s)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
            startActivity(browserIntent);
            progressBar.setProgress(0);
            textView.setText("0%");
        }
    }
}
