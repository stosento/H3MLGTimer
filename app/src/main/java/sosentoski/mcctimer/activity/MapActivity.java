package sosentoski.mcctimer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import sosentoski.mcctimer.MapInformation;
import sosentoski.mcctimer.R;
import sosentoski.mcctimer.enums.GameMap;
import sosentoski.mcctimer.enums.Gamemode;
import sosentoski.mcctimer.enums.Gametype;
import sosentoski.mcctimer.enums.Powerup;
import sosentoski.mcctimer.constants.MapConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MapActivity extends AppCompatActivity
{
    GameMap map;
    List<Gametype> gametypes = new ArrayList<>();
    List<Gamemode> gamemodes = Arrays.asList(Gamemode.MATCHMAKING, Gamemode.CUSTOM_GAME);
    List<Powerup> powerups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        RelativeLayout layout = findViewById(R.id.map_layout);

        for (int i=0; i < layout.getChildCount(); i++)
        {
            View child = layout.getChildAt(i);
            if (child instanceof Button)
            {
                Button button = (Button) child;
                button.setOnClickListener(new MapButtonListener());
            }
        }
    }

    private class MapButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Button button = (Button) view;
            String buttonText = button.getText().toString();
            setMapAttributes(buttonText);
            if (skipOptionsActivity())
                openTimerActivity(gametypes.get(0), Gamemode.MATCHMAKING);
            else
                openOptionsActivity();
        }
    }

    private void setMapAttributes(String buttonText)
    {
        if (buttonText.equalsIgnoreCase(MapConstants.CONSTRUCT)) {
            map = GameMap.CONSTRUCT;
            gametypes = Arrays.asList(Gametype.KING_OF_THE_HILL, Gametype.SLAYER);
            powerups = Arrays.asList(Powerup.CAMO, Powerup.OVERSHIELD, Powerup.ROCKETS, Powerup.SNIPER);
        } else if (buttonText.equalsIgnoreCase(MapConstants.NARROWS)) {
            map = GameMap.NARROWS;
            gametypes = Arrays.asList(Gametype.CAPTURE_THE_FLAG, Gametype.SLAYER);
            powerups = Arrays.asList(Powerup.ROCKETS, Powerup.SNIPER);
        } else if (buttonText.equalsIgnoreCase(MapConstants.GUARDIAN)) {
            map = GameMap.GUARDIAN;
            gametypes = Arrays.asList(Gametype.ODDBALL);
            powerups = Arrays.asList(Powerup.CAMO, Powerup.SNIPER);
        } else if (buttonText.equalsIgnoreCase(MapConstants.THE_PIT)) {
            map = GameMap.THE_PIT;
            gametypes = Arrays.asList(Gametype.CAPTURE_THE_FLAG, Gametype.SLAYER);
            powerups = Arrays.asList(Powerup.ROCKETS, Powerup.SNIPER, Powerup.OVERSHIELD);
        } else if (buttonText.equalsIgnoreCase(MapConstants.HERETIC)) {
            map = GameMap.HERETIC;
            gametypes = Arrays.asList(Gametype.CAPTURE_THE_FLAG, Gametype.SLAYER);
            powerups = null;
        } else if (buttonText.equalsIgnoreCase(MapConstants.ONSLAUGHT)) {
            map = GameMap.ONSLAUGHT;
            gametypes = Collections.singletonList(Gametype.CAPTURE_THE_FLAG);
            powerups = null;
        } else if (buttonText.equalsIgnoreCase(MapConstants.AMPLIFIED)) {
            map = GameMap.AMPLIFIED;
            gametypes = Collections.singletonList(Gametype.SLAYER);
            powerups = null;
        }
    }

    private void openOptionsActivity()
    {
        OptionsActivity optionsActivity = new OptionsActivity();
        MapInformation mapInformation = new MapInformation(map, gametypes, gamemodes, powerups);
        String mapInformationJSON = mapInformation.toJson();

        Intent intent = new Intent(this, optionsActivity.getClass());
        intent.putExtra("mapInformation", mapInformationJSON);

        startActivity(intent);
    }

    private void openTimerActivity(Gametype gametype, Gamemode gamemode)
    {
        TimerActivity timerActivity = new TimerActivity(gametype, gamemode, powerups);
        Intent intent = new Intent(this, timerActivity.getClass());
        startActivity(intent);
    }

    private boolean skipOptionsActivity()
    {
        return (powerups.isEmpty() || (gametypes != null && gametypes.size() == 1 && gametypes.get(0) == Gametype.SLAYER));
    }
}
