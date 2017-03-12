package com.example.diceout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Field to hold roll result text
    TextView rollResult;

    //Field to hold the roll button
    Button rollButton;

    //Field to hold the score
    int score;

    //Field to hold dice values
    int die1;
    int die2;
    int die3;

    //ArrayList to hold all three dice values
    ArrayList<Integer> dice;

    //ArrayList to hold all three dice Images
    ArrayList<ImageView> diceImageViews;

    //Field to hold random number generator
    Random rand;

    //Field to hold the score text
    TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });

        //Set initial score
        score=0;

        rollResult= (TextView) findViewById(R.id.rollResult);
        //rollButton= (Button) findViewById(R.id.rollButton);
        scoreText= (TextView) findViewById(R.id.scoreText);

        //Field to hold random number generator
        rand = new Random();

        ImageView die1Image = (ImageView) findViewById(R.id.die1Image);
        ImageView die2Image = (ImageView) findViewById(R.id.die2Image);
        ImageView die3Image = (ImageView) findViewById(R.id.die3Image);

        diceImageViews = new ArrayList<ImageView>();
        diceImageViews.add(die1Image);
        diceImageViews.add(die2Image);
        diceImageViews.add(die3Image);

        //Create ArrayList container for the dice values
        dice= new ArrayList<Integer>();

        //Create greeting
        Toast.makeText(getApplicationContext(),"Welcome to DiceOut!", Toast.LENGTH_SHORT).show();
    }

    public void rollDice(View v){
        rollResult.setText("Clicked!");

        //Roll dice
        die1=rand.nextInt(6)+1;
        die2=rand.nextInt(6)+1;
        die3=rand.nextInt(6)+1;

        //Set dice values in ArrayList
        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);

        for(int dieOfSet=0; dieOfSet<3; dieOfSet++){
            String imageName= "die_" + dice.get(dieOfSet)+".png";

            try{
                InputStream stream= getAssets().open(imageName);
                Drawable d= Drawable.createFromStream(stream,null);
                diceImageViews.get(dieOfSet).setImageDrawable(d);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        //Build message with the result
        //String msg="You rolled a "+ die1 + " a " + die2 +", and a "+ die3;

        String msg;

        if(die1==die2 && die1==die3){
            int scoreDelta= die1*100;
            msg= "You rolled a tripple "+  die1 +" !You scored " + scoreDelta +" points!";
            score+=scoreDelta;
        }else if(die1==die2 || die1==die3 || die2==die3){
            score+=50;
            msg="You scored a doubles for 50 points!";
        }else{
            msg="You didn't score this roll. Try again!";
        }

        //Update the app to display the result message
        rollResult.setText(msg);
        scoreText.setText("Score :"+ score);
        /*int num=rand.nextInt(6)+1;
        Toast.makeText(getApplicationContext(),"Number generated "+num,Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
