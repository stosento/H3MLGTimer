package sosentoski.mcctimer.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import sosentoski.mcctimer.R;
import sosentoski.mcctimer.enums.Gamemode;
import sosentoski.mcctimer.enums.Gametype;
import sosentoski.mcctimer.enums.Powerup;
import sosentoski.mcctimer.listeners.ResetListener;
import sosentoski.mcctimer.listeners.StartStopListener;

import java.util.List;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity
{
    private static final long START_TIME_IN_MILLIS = 600000;
    private static final long ONE_SECOND_IN_MILLIS = 1000;

    private TextView sniperCountDownText;
    private Button sniperButtonStartPause;
    private Button sniperButtonReset;
    private CountDownTimer sniperCountDownTimer;

    private boolean timerIsRunning;
    private long timeLeftInMilliseconds = START_TIME_IN_MILLIS;

    private Gametype gametype;
    private Gamemode gamemode;
    private List<Powerup> powerups;

    TimerActivity(Gametype gametype, Gamemode gamemode, List<Powerup> powerups)
    {
        this.gametype = gametype;
        this.gamemode = gamemode;
        this.powerups = powerups;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        sniperCountDownText = findViewById(R.id.sniper_textView);
        sniperButtonStartPause = findViewById(R.id.sniper_button_start_pause);
        sniperButtonReset = findViewById(R.id.sniper_button_reset);

        sniperButtonStartPause.setOnClickListener(new StartStopListener(timerIsRunning, sniperCountDownText, sniperButtonStartPause, sniperButtonReset, sniperCountDownTimer));
        sniperButtonReset.setOnClickListener(new ResetListener(sniperCountDownText, sniperButtonStartPause, sniperButtonReset));

        updateTimerText();
    }

    public void updateTimerText() {
        int minutes = (int) (timeLeftInMilliseconds / ONE_SECOND_IN_MILLIS) / 60;
        int seconds = (int) (timeLeftInMilliseconds / ONE_SECOND_IN_MILLIS) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        sniperCountDownText.setText(timeLeftFormatted);
    }


}
