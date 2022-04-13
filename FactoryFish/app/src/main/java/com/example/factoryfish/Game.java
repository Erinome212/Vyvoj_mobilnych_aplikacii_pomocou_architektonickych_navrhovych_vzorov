package com.example.factoryfish;

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
    private Bitmap fishanim[] = new Bitmap[2];
    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    private int canvasWidth, canvasHeight;

    private Paint yellowPaint = new Paint();
    private Paint greenPaint = new Paint();
    private Paint redPaint = new Paint();
    private int lifeCounter;
    private Entity fish, yellow, green, red;

    private int score;

    private boolean touch = false;

    public Game(Context context) {
        super(context);

        fish = EntityFactory.getEntity("fish");
        yellow = EntityFactory.getEntity("yellow");
        red = EntityFactory.getEntity("red");
        green = EntityFactory.getEntity("green");

        fishanim[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fishanim[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

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
        canvasWidth = getWidth();
        canvasHeight = getHeight();

        canvas.drawBitmap(backgroundImage, 0, 0, null);
        canvas.drawText("Score : ", 20, 60, scorePaint);

        int minFishY = fishanim[0].getHeight();
        int maxFishY = canvasHeight - fishanim[0].getHeight() * 3;

        fish.positiony = fish.positiony + fish.speed;
        if (fish.positiony < minFishY) {
            fish.positiony = minFishY;
        }

        if (fish.positiony > maxFishY) {
            fish.positiony = maxFishY;
        }

        fish.speed = fish.speed + 2;

        if (touch) {
            canvas.drawBitmap(fishanim[1], fish.positionx, fish.positiony, null);
            touch = false;
        } else {
            canvas.drawBitmap(fishanim[0], fish.positionx, fish.positiony, null);
        }

        //yellow ball
        yellow.positionx = yellow.positionx - yellow.speed;

        if (hitBallChecker(yellow.positionx, yellow.positiony)) {
            score = score + 10;
            yellow.positionx = -100;
        }

        if (yellow.positionx < 0) {
            yellow.positionx = canvasWidth + 24;
            yellow.positiony = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }
        canvas.drawCircle(yellow.positionx, yellow.positiony, 25, yellowPaint);

        // green ball
        green.positionx = green.positionx - green.speed;
        if (hitBallChecker(green.positionx, green.positiony)) {
            score = score + 20;
            green.positionx = -100;
        }

        if (green.positionx < 0) {
            green.positionx = canvasWidth + 24;
            green.positiony = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }
        canvas.drawCircle(green.positionx, green.positiony, 15, greenPaint);

        // red ball
        red.positionx = red.positionx - red.speed;
        if (hitBallChecker(red.positionx, red.positiony)) {
            red.positionx = -100;
            lifeCounter--;
            if (lifeCounter == 0) {
                Toast.makeText(getContext(), " Game Over", Toast.LENGTH_SHORT).show();
                Intent gameOver = new Intent(getContext(), GameOver.class);
                gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOver.putExtra("score", score);
                getContext().startActivity(gameOver);
            }
        }
        if (red.positionx < 0) {
            red.positionx = canvasWidth + 24;
            red.positiony = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }
        canvas.drawCircle(red.positionx, red.positiony, 30, redPaint);
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
        if (fish.positionx < x && x < (fish.positionx + fishanim[0].getWidth()) && fish.positiony < y && y < (fish.positiony + fishanim[0].getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            fish.speed = -25;
        }
        return true;
    }
}
