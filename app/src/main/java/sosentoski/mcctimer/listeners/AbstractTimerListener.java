package sosentoski.mcctimer.listeners;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import sosentoski.mcctimer.constants.TimerConstants;

import java.util.Locale;

public class AbstractTimerListener
{
    private static final long START_TIME_IN_MILLIS = 600000;
    private static final long ONE_SECOND_IN_MILLIS = 1000;

    private long timeLeftInMilliseconds = START_TIME_IN_MILLIS;
    private boolean timerIsRunning;

    public void startTimer(CountDownTimer timer, final TextView textView, final Button startButton, final Button resetButton)
    {
        timer = new CountDownTimer(timeLeftInMilliseconds, TimerConstants.ONE_SECOND_IN_MILLIS) {
            @Override
            public void onTick(long l)
            {
                timeLeftInMilliseconds = l;
                updateTimerText(textView);
            }

            @Override
            public void onFinish()
            {
                timerIsRunning = false;
                startButton.setText("start");
                startButton.setVisibility(View.INVISIBLE);
                resetButton.setVisibility(View.VISIBLE);
            }
        }.start();

        timerIsRunning = true;
        startButton.setText("pause");
        resetButton.setVisibility(View.INVISIBLE);
    }

    public void updateTimerText(TextView textView) {
        int minutes = (int) (timeLeftInMilliseconds / ONE_SECOND_IN_MILLIS) / 60;
        int seconds = (int) (timeLeftInMilliseconds / ONE_SECOND_IN_MILLIS) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textView.setText(timeLeftFormatted);
    }

    public void pauseTimer(CountDownTimer timer, final Button startButton, final Button resetButton) {
        timerIsRunning = false;
        timer.cancel();
        startButton.setText("start");
        resetButton.setVisibility(View.VISIBLE);
    }

    public void resetTimer(TextView textView, final Button startButton, final Button resetButton) {
        timeLeftInMilliseconds = START_TIME_IN_MILLIS;
        updateTimerText(textView);;
        resetButton.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);
    }
}
