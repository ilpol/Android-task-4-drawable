package com.example.hw4_drawables;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    boolean gameActive = true;

    int userCount = 0;
    int aiCount = 0;
    int gameCount = 0;


    TextView userCountElem;
    TextView aiCountElem;


    // 0 - X
    // 1 - O
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    Set<Integer> NextStepsAI = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));

    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    public static int counter = 0;

    public void playerTap(View view) {

        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());

        TextView userCountElem = findViewById(R.id.userCount);
        TextView aiCountElem = findViewById(R.id.aiCount);

        if (!gameActive) {
            gameReset(view);
        }

        for (Iterator<Integer> iterator = NextStepsAI.iterator(); iterator.hasNext();) {
            Integer position =  iterator.next();
            if (position == tappedImage) {
                iterator.remove();
            }
        }


        if (gameState[tappedImage] == 2) {
            counter++;

            if (counter == 9) {
                gameActive = false;
            }

            gameState[tappedImage] = activePlayer;

            img.setTranslationY(-1000f);

            if (activePlayer == 0) {
                img.setImageResource(R.drawable.x_vector);
                activePlayer = 1;
                TextView status = findViewById(R.id.status);

            } else {
                img.setImageResource(R.drawable.o_shape_layer_list);
                activePlayer = 0;
                TextView status = findViewById(R.id.status);

            }
            img.animate().translationYBy(1000f).setDuration(300);
        }
        int flag = 0;
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {
                flag = 1;

                // Кто-то выиграл
                String winnerStr;

                gameActive = false;
                if (gameState[winPosition[0]] == 0) {
                    winnerStr = "Вы выиграли";
                    userCount += 1;
                } else {
                    winnerStr = "Вы проиграли";
                    aiCount += 1;
                }
                gameCount += 1;
                TextView status = findViewById(R.id.status);
                status.setText(winnerStr);

                userCountElem.setText(String.valueOf(userCount));
                aiCountElem.setText(String.valueOf(aiCount));
            }
        }

        if (counter == 9 && flag == 0) {
            TextView status = findViewById(R.id.status);
            status.setText("Ничья");
        }

        // AI

        if (gameActive && NextStepsAI.size() > 0) {

            int size = NextStepsAI.size();
            int item = new Random().nextInt(size);

            int i = 0;
            int nextStepAI = -1;
            for(Integer step : NextStepsAI)
            {
                if (i == item)
                    nextStepAI = step;
                i++;
            }
            for (Iterator<Integer> iterator = NextStepsAI.iterator(); iterator.hasNext();) {
                Integer position =  iterator.next();
                if (position == nextStepAI) {
                    iterator.remove();
                }
            }

            counter++;

            if (counter == 9) {
                gameActive = false;
            }

            gameState[nextStepAI] = activePlayer;

            String idAIImgString = "imageView" + nextStepAI;

            int idAIImg = getResources().getIdentifier(idAIImgString, "id", getPackageName());

            ImageView imgAI = findViewById(idAIImg);

            imgAI.setTranslationY(-1000f);

            if (activePlayer == 0) {
                imgAI.setImageResource(R.drawable.x_vector);
                activePlayer = 1;
                TextView status = findViewById(R.id.status);

            } else {
                imgAI.setImageResource(R.drawable.o_shape_layer_list);
                activePlayer = 0;
                TextView status = findViewById(R.id.status);

            }
            imgAI.animate().translationYBy(1000f).setDuration(300);


            flag = 0;
            for (int[] winPosition : winPositions) {
                if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                        gameState[winPosition[1]] == gameState[winPosition[2]] &&
                        gameState[winPosition[0]] != 2) {
                    flag = 1;

                    // Кто-то выиграл
                    String winnerStr;

                    gameActive = false;
                    if (gameState[winPosition[0]] == 0) {
                        winnerStr = "Вы выиграли";
                        userCount += 1;
                    } else {
                        winnerStr = "Вы проиграли";
                        aiCount += 1;
                    }

                    gameCount += 1;
                    TextView status = findViewById(R.id.status);
                    status.setText(winnerStr);
                    userCountElem.setText(String.valueOf(userCount));
                    aiCountElem.setText(String.valueOf(aiCount));
                }
            }
            if (counter == 9 && flag == 0) {
                TextView status = findViewById(R.id.status);
                status.setText("Ничья");
            }
        }

        if (gameCount == 3) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Конец игры!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void gameReset(View view) {
        NextStepsAI = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));

        gameActive = true;
        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        ((ImageView) findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);

        TextView status = findViewById(R.id.status);
        status.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView userCountElem = findViewById(R.id.userCount);
        TextView aiCountElem = findViewById(R.id.aiCount);

        userCountElem.setText(String.valueOf(userCount));
        aiCountElem.setText(String.valueOf(aiCount));

        ImageView imgBattery = findViewById(R.id.imageViewBattery);
        imgBattery.setImageLevel(5000);
    }
}