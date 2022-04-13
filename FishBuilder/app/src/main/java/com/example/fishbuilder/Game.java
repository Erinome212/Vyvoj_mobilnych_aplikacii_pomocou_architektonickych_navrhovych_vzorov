package com.example.fishbuilder;

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

    private Fish fish;

    private int yellowX, yellowY, yellowSpeed = 16;
    private int greenX, greenY, greenSpeed = 12;
    private int redX, redY, redSpeed = 25;

    private boolean touch = false;

    public Game(Context context) {
        super(context);

        fish = new Fish.FishBuilder(10)
                .setPositiony(500)
                .setLife(3)
                .setScore(0)
                .build();

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

        fish.setPositionx(10);
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

        fish.setPositiony(fish.getPositiony() + fish.getSpeed());
        if (fish.getPositiony() < minFishY) {
            fish.setPositiony(minFishY);
        }

        if (fish.getPositiony() > maxFishY) {
            fish.setPositiony(maxFishY);
        }

        fish.setSpeed(fish.getSpeed() + 2);

        if (touch) {
            canvas.drawBitmap(fishanim[1], fish.getPositionx(), fish.getPositiony(), null);
            touch = false;
        } else {
            canvas.drawBitmap(fishanim[0], fish.getPositionx(), fish.getPositiony(), null);
        }

        //yellow ball
        yellowX = yellowX - yellowSpeed;

        if (hitBallChecker(yellowX, yellowY)) {
            fish.setScore(fish.getScore() + 10);
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
            //score = score + 20;
            fish.setScore(fish.getScore() + 20);
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
            fish.setLife(fish.getLife() - 1);
            if (fish.getLife() == 0) {
                Toast.makeText(getContext(), " Game Over", Toast.LENGTH_SHORT).show();
                Intent gameOver = new Intent(getContext(), GameOver.class);
                gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOver.putExtra("score", fish.getScore());
                getContext().startActivity(gameOver);
            }
        }

        if (redX < 0) {
            redX = canvasWidth + 24;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }
        canvas.drawCircle(redX, redY, 30, redPaint);
        canvas.drawText("Score : " + fish.getScore(), 20, 60, scorePaint);

        for (int i = 0; i < 3; i++) {
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < fish.getLife()) {
                canvas.drawBitmap(life[0], x, y, null);
            } else {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }
    }

    public boolean hitBallChecker(int x, int y) {
        if (fish.getPositionx() < x && x < (fish.getPositionx() + fishanim[0].getWidth()) && fish.getPositiony() < y && y < (fish.getPositiony() + fishanim[0].getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            fish.setSpeed(-25);
        }
        return true;
    }
}