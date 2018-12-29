package com.example.rlu.mygrid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;

public class GridGameAdapter extends RecyclerView.Adapter<GridGameAdapter.ViewHolder> {

    private static int DEFAULT_ELEMENTS = 16;
    private boolean [] mSquares;
    private int mElements, mWinningNumber;
    private Random mGenerator;

    public GridGameAdapter () throws IllegalAccessException {
        this(DEFAULT_ELEMENTS);
    }

    public GridGameAdapter (int elements) throws IllegalAccessException {
        if(elements % Math.sqrt(elements) == 0) {
            mSquares = new boolean[elements];
            mElements = elements;
            mGenerator = new Random();
            startGame();
        }
        else {
            throw new IllegalAccessException("number of squares must create a perfect square board");
        }
    }

    private void startGame() {
       startGameWith(mGenerator.nextInt(mElements));
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from (parent.getContext ()).inflate (
                R.layout.rv_item, parent, false);
        return new ViewHolder (itemLayoutView);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        holder.mButton.setText (Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        return mSquares.length;
    }

    public int getWinningNumber () {
        return mWinningNumber;
    }

    public boolean isWinner (int elementNumber) {
        return mSquares[elementNumber];
    }

    public void startNewGame()
    {
        endCurrentGame();
        startGame ();
    }

    public void overWriteWinningNumber(int newWinningNumber) throws IllegalAccessException {
        if(newWinningNumber >= 0 && newWinningNumber< mSquares.length) {
            endCurrentGame();
            startGameWith(newWinningNumber);
        }
        else {
            throw new IllegalAccessException("This number is not a valid winning number");
        }
    }

    private void startGameWith(int newWinningNumber) {
        mWinningNumber = newWinningNumber;
        mSquares[mWinningNumber] = true;
    }

    private void endCurrentGame() {
        mSquares[mWinningNumber] = false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final Button mButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.button);
        }
    }
}
