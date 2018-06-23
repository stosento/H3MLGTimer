package sosentoski.mcctimer.listeners;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartStopListener extends AbstractTimerListener implements View.OnClickListener
{
    private boolean timerIsRunning;
    private TextView textView;
    private Button startButton;
    private Button resetButton;
    private CountDownTimer timer;

    public StartStopListener(boolean timerIsRunning, TextView textView, Button startButton, Button resetButton, CountDownTimer timer)
    {
        this.timerIsRunning = timerIsRunning;
        this.textView = textView;
        this.startButton = startButton;
        this.resetButton = resetButton;
        this.timer = timer;
    }

    @Override
    public void onClick(View view)
    {
        if (timerIsRunning)
            pauseTimer(timer, startButton, resetButton);
        else
            startTimer(timer, textView, startButton, resetButton);
    }
}