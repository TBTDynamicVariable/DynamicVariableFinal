package com.wordpress.mentalhealthmonitor.dynamicvariable;


import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.PopupWindow;
import java.io.IOException;
import android.widget.Toast;



public class Game extends AppCompatActivity {

    ImageView TerrorView;
    int counter; //Number of correct answers after timer ends
    private Button control; //Pauses or starts game
    TextView equation; //Text that diplays equation
    EditText input; //User input answer
    static final int REQUEST_IMAGE_CAPTURE = 1; // Used to take a picture



    int answer;
    int result;

    TextView timerTextView;
    private CountDownTimer countDownTimer;
    private long timeRemaining = 30000; //90 sec;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        input = findViewById(R.id.editText);
        equation  = findViewById(R.id.equationView);
        timerTextView = findViewById(R.id.timertextView);
        TerrorView = findViewById(R.id.terrorview);

        control = findViewById(R.id.StartPause); //For timer
        TerrorView.setVisibility(View.INVISIBLE);
        //control.setText("START");


        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateEq();
                startStop();
            }
        });


       updateTimer();
       EnterValue();
    }


    public void startStop()
    {
        if (timerRunning){
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer() {
        countDownTimer=new CountDownTimer(timeRemaining,1000) {
            @Override
            public void onTick(long l) {
                timeRemaining=l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                equation.setText("");
                input.setFocusable(false);
                input.setEnabled(false);
                input.setCursorVisible(false);
                input.setKeyListener(null);
                input.setBackgroundColor(Color.TRANSPARENT);
                TerrorView.setVisibility(View.INVISIBLE);
                showWinners();
            }
        }.start();

        //control.setText("PAUSE");
        timerRunning=true;
    }

    public void stopTimer(){
       // control.setText("START");
        countDownTimer.cancel();
        timerRunning=false;
    }

    public void updateTimer(){
        int minutes = (int)timeRemaining/60000;
        int seconds = (int) timeRemaining%60000 /1000;

        String timeLeft;

        timeLeft = "" + minutes;
        timeLeft += ":";

        if (seconds<10) timeLeft+="0";
        timeLeft+=seconds;

        timerTextView.setText(timeLeft);
    }


    private void EnterValue() {
        input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {


                    String ans = input.getText().toString();
                    answer = Integer.parseInt(ans);

                    if (answer==result){
                        //t2.setText("CORRECT");
                        input.setText(null);
                        TerrorView.setImageDrawable(getResources().getDrawable(R.drawable.startview));
                        TerrorView.setVisibility(View.VISIBLE);
                        GenerateEq();
                        counter++;
                    }

                    else {

                        //t2.setText("INCORRECT! Try Again");
                        input.setText(null);
                        TerrorView.setVisibility(View.VISIBLE);
                        TerrorView.setImageDrawable(getResources().getDrawable(R.drawable.wrong));
                        GenerateEq();
                        // Here is where we will do the picture, and go from there
                    }
                    return true;
                }

                return false;
            }
        });

    }

    private void GenerateEq() {

        int first=0, finale;
        float check, rnd, check1, rnd1;
        char[] operations = {'+', '-', '*', '/'};

        Random rand = new Random();
        int num;
        int num1;
        int num2;

        int op;
        int op1;

        do {

            num = rand.nextInt(12) + 1;
            num1 = rand.nextInt(12) + 1;
            num2 = rand.nextInt(12) + 1;

            op = rand.nextInt(4);
            op1 = rand.nextInt(4);

            if (op1 <= op) { //FIRST OPERATOR PREFERENCE


                if (op == 3) {
                    check = (float) num / num1;
                    rnd = Math.round(check);

                    while ((check != rnd) || (num1 == 0)) {
                        num = rand.nextInt(10) + 1;
                        num1 = rand.nextInt(10) + 1;
                        check = (float) num / num1;
                        rnd = Math.round(check);
                    }
                }

                switch (op) {
                    case 0:
                        first = num + num1;
                        break;
                    case 1:
                        first = num - num1;
                        break;
                    case 2:
                        first = num * num1;
                        break;
                    case 3:
                        first = num / num1;
                        break;
                }

                if (op1 == 3) {
                    check = (float) first / num2;
                    rnd = Math.round(check);

                    while (check != rnd) {
                        num2 = rand.nextInt(10) + 1;
                        check = (float) first / num2;
                        rnd = Math.round(check);
                    }
                }

                switch (op1) {
                    case 0:
                        result = first + num2;
                        break;
                    case 1:
                        result = first - num2;
                        break;
                    case 2:
                        result = first * num2;
                        break;
                    case 3:
                        result = first / num2;
                        break;
                }
            }

            else if (op1 > op) //SECOND OPERATOR PREFERENCE
            {

                if (op1 == 3) {

                    check1 = (float) num1 / num2;
                    rnd1 = Math.round(check1);

                    while (check1 != rnd1) {
                        num1 = rand.nextInt(10) + 1;
                        num2 = rand.nextInt(10) + 1;
                        check1 = (float) num1 / num2;
                        rnd1 = Math.round(check1);
                    }
                }


                switch (op1) {
                    case 0:
                        first = num1 + num2;
                        break;
                    case 1:
                        first = num1 - num2;
                        break;
                    case 2:
                        first = num1 * num2;
                        break;
                    case 3:
                        first = num1 / num2;
                        break;
                }

                if (op == 3) {

                    check1 = (float) num / first;
                    rnd1 = Math.round(check1);

                    while (check1 != rnd1) {
                        num = rand.nextInt(10) + 1;
                        check1 = (float) num / first;
                        rnd1 = Math.round(check1);
                    }
                }

                switch (op) {
                    case 0:
                        result = num + first;
                        break;
                    case 1:
                        result = num - first;
                        break;
                    case 2:
                        result = num * first;
                        break;
                    case 3:
                        result = num / first;
                        break;
                }
            }

        } while (result<0);

        String myString = String.valueOf(num);
        String myString1 = String.valueOf(num1);
        String myString2 = String.valueOf(num2);

        equation.setText(myString + operations[op] + myString1 + operations[op1]+ myString2 + "=");

    }


    private void showWinners(){
        //We need to get the instance of the LayoutInflater, use the context of this activity
        LayoutInflater inflater = (LayoutInflater) Game.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout (no need for root id, using entire layout)
        View layout = inflater.inflate(R.layout.winners,null);
        //load results
        ((TextView)layout.findViewById(R.id.ScoreView)).setText("Your Score:");
         //Text that diplays equation
        ((TextView)layout.findViewById(R.id.yourscore)).setText(String.valueOf(counter));

        //Get the devices screen density to calculate correct pixel sizes
        float density=Game.this.getResources().getDisplayMetrics().density;
        // create a focusable PopupWindow with the given layout and correct size
        final PopupWindow pw = new PopupWindow(layout, (int)density*400, (int)density*500, true);
        //final PopupWindow pw = new PopupWindow(layout, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        //Button to close the pop-up
        //pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // display the pop-up in the center

        pw.setFocusable(true);
        pw.update();
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
        layout.findViewById(R.id.menubutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             pw.dismiss();
                finish();
            }
        });




    }


    public void writefile(){
        String score = String.valueOf(counter);
        try {
            FileOutputStream  fileOutputStream  = openFileOutput("HighScoreL.txt", MODE_PRIVATE);
            fileOutputStream.write(score.getBytes());
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(), "Score Saved", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }


    }


}
