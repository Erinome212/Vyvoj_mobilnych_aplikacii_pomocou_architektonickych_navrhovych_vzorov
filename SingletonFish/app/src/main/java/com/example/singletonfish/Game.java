package com.example.singletonfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Game extends View {

    private Bitmap fish[] = new Bitmap[2];
    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    private boolean touch = false;

    private int canvasWidth, canvasHeight;
    private int yellowX, yellowY, yellowSpeed = 16;
    private Paint yellowPaint = new Paint();
    private int greenX, greenY, greenSpeed = 12;
    private Paint greenPaint = new Paint();
    private int redX, redY, redSpeed = 25;
    private Paint redPaint = new Paint();
    private int lifeCounter;
    private Fish fishSingleton;

    private int score;

    public Game(Context context) {
        super(context);

        fishSingleton = Fish.getInstance();
        fishSingleton.setPositionx(10);
        fishSingleton.setPositiony(500);
        fishSingleton.setSpeed(10);

        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.BLUE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        score = 0;
        lifeCounter = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(backgroundImage, 0, 0, null);
        canvas.drawText("Score : ", 20, 60, scorePaint);

        // fish
        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;
        fishSingleton.setPositiony(fishSingleton.getPositiony() + fishSingleton.getSpeed());

        if (fishSingleton.getPositiony() < minFishY) {
            fishSingleton.setPositiony(minFishY);
        }

        if (fishSingleton.getPositiony() > maxFishY) {
            fishSingleton.setPositiony(maxFishY);
        }

        fishSingleton.setSpeed(fishSingleton.getSpeed() + 2);

        if (touch) {
            canvas.drawBitmap(fish[1], fishSingleton.getPositionx(), fishSingleton.getPositiony(), null);
            touch = false;
        } else {
            canvas.drawBitmap(fish[0], fishSingleton.getPositionx(), fishSingleton.getPositiony(), null);
        }

        //yellow ball
        yellowX = yellowX - yellowSpeed;

        if (hitBallChecker(yellowX, yellowY)) {
            score = score + 10;
            yellowX = -100;
        }

        if (yellowX < 0) {
            yellowX = canvasWidth + 24;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }
        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);

        // green ball
        greenX = greenX - greenSpeed;
        if (hitBallChecker(greenX, greenY)) {
            score = score + 20;
            greenX = -100;
        }

        if (greenX < 0) {
            greenX = canvasWidth + 24;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }
        canvas.drawCircle(greenX, greenY, 15, greenPaint);

        // red ball
        redX = redX - redSpeed;
        if (hitBallChecker(redX, redY)) {
            redX = -100;
            lifeCounter--;
            if (lifeCounter == 0) {
                Toast.makeText(getContext(), " Game Over", Toast.LENGTH_SHORT).show();
                Intent gameOver = new Intent(getContext(), GameOver.class);
                gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOver.putExtra("score", score);
                getContext().startActivity(gameOver);
            }
        }

        if (redX < 0) {
            redX = canvasWidth + 24;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }
        canvas.drawCircle(redX, redY, 30, redPaint);
        canvas.drawText("Score : " + score, 20, 60, scorePaint);

        for (int i = 0; i < 3; i++) {
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < lifeCounter) {
                canvas.drawBitmap(life[0], x, y, null);
            } else {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }
    }

    public boolean hitBallChecker(int x, int y) {
        if (fishSingleton.getPositionx() < x && x < (fishSingleton.getPositionx() + fish[0].getWidth()) && fishSingleton.getPositiony() < y && y < (fishSingleton.getPositiony() + fish[0].getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            fishSingleton.setSpeed(-25);
        }
        return true;
    }
}
