package sosentoski.mcctimer.listeners;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResetListener extends AbstractTimerListener implements View.OnClickListener
{
    private TextView textView;
    private Button startButton;
    private Button resetButton;

    public ResetListener(TextView textView, final Button startButton, final Button resetButton)
    {
        this.textView = textView;
        this.startButton = startButton;
        this.resetButton = resetButton;
    }

    @Override
    public void onClick(View view)
    {
        resetTimer(textView, startButton, resetButton);
    }
}
