package com.dermutzh.einzelabgabe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    Socket socket;
    BufferedReader inFromUser;
    DataOutputStream outToServer;
    BufferedReader serverResponse;
    String input;
    String resp;

    EditText editTextImmNr;
    TextView textViewAnswer;
    Button btnServerTask;
    CalculationManager calcManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextImmNr = findViewById(R.id.editTextImmNumber);
        textViewAnswer = findViewById(R.id.textViewAnswer);
        btnServerTask = findViewById(R.id.buttonSendToServer);

        calcManager = new CalculationManager();


        editTextImmNr.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    try {
                        input = editTextImmNr.getText().toString();
                        performServerTask();

                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }

    public void performServerTask() throws IOException {

        btnServerTask.setEnabled(false);
        final Handler handler = new Handler();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("se2-isys.aau.at", 53212);
                    outToServer = new DataOutputStream(socket.getOutputStream());

                    serverResponse = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    outToServer.writeBytes(input + "\n");

                    resp = serverResponse.readLine();


                    socket.close();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            final TextView txtView = findViewById(R.id.textViewAnswer);
                            txtView.setText(resp);
                            btnServerTask.setEnabled(true);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(task).start();
    }


    public void connectionTest(View view) throws IOException {
        this.input = editTextImmNr.getText().toString();
        performServerTask();
    }

    public void performCalculationClicked(View view) {
        char [] inputChars = editTextImmNr.getText().toString().toCharArray();
        String sorted = calcManager.performCalculation(inputChars);

        textViewAnswer.setText(String.format("%s: %s", getString(R.string.sorted), sorted));
    }
}
