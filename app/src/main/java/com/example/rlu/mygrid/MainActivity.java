package com.example.rlu.mygrid;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private GridGameAdapter mObjGridGameAdapter;
    private int mTurnsTaken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            setupBoard();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            mObjGridGameAdapter.overWriteWinningNumber(savedInstanceState.getInt("WINNING_NUMBER"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mTurnsTaken = savedInstanceState.getInt("CURRENT_GUESSES")-1;
        incrementGuessesCounterAndUpdateStatusBar();
    }

    public void setupBoard() throws IllegalAccessException {
        int squares = 64;
        int rows = (int) (squares/Math.sqrt (squares));

        // Create a reference to our RV and a new instance of a LayoutManager
        // and our Adapter class
        RecyclerView objRecyclerView = (RecyclerView) findViewById (R.id.recycler_view);
        objRecyclerView.setHasFixedSize (true);
        RecyclerView.LayoutManager objLayoutManager = new GridLayoutManager(this, rows); // cols/rows
        objLayoutManager.setAutoMeasureEnabled(true);

        mObjGridGameAdapter = new GridGameAdapter (squares);

        // put all three objects together
        objRecyclerView.setLayoutManager (objLayoutManager);
        objRecyclerView.setAdapter(mObjGridGameAdapter);
    }

   public void buttonHandler (View view)
    {
        showGuessResults((Button) view);
        incrementGuessesCounterAndUpdateStatusBar();
    }

    private void incrementGuessesCounterAndUpdateStatusBar() {
        //update status bar
        TextView tvStatusBar = (TextView) findViewById(R.id.status_bar);

        //pre increment turns taken before it is outputted to the TextView
        tvStatusBar.setText("Guesses taken: " + ++mTurnsTaken);
    }

    private void showGuessResults(View view) {
        View sbContainer = findViewById (R.id.activity_main);
        // If using setTag in the Adapter then you would use .getTag() here instead
        String currentText = ((Button)view).getText().toString ();
        int currentElement = Integer.parseInt (currentText);
        String msg = "You clicked on " + currentText + ".\n";
        msg+= mObjGridGameAdapter.isWinner(currentElement) ?
                "You guessed the winning number!" : "Please try a different number.";
        Snackbar.make (sbContainer, msg, Snackbar.LENGTH_SHORT).show ();
    }


    public void newGame(MenuItem item) {
        mObjGridGameAdapter.startNewGame();

        //reset status bar
        mTurnsTaken=-1;
        incrementGuessesCounterAndUpdateStatusBar();

        View sbContainer = findViewById(R.id.activity_main);
        Snackbar.make(sbContainer, "Welcome to a new game!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("WINNING_NUMBER", mObjGridGameAdapter.getWinningNumber());
        outState.putInt("CURRENT_GUESSES", mTurnsTaken);
    }





}
