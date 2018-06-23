package sosentoski.mcctimer.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import sosentoski.mcctimer.MapInformation;
import sosentoski.mcctimer.R;
import sosentoski.mcctimer.enums.GameMap;
import sosentoski.mcctimer.enums.Gamemode;
import sosentoski.mcctimer.enums.Gametype;
import sosentoski.mcctimer.enums.Powerup;

import java.util.ArrayList;
import java.util.List;

public class OptionsActivity extends AppCompatActivity
{
    private GameMap map;
    private List<Gametype> gametypes;
    private List<Gamemode> gamemodes;
    private List<Powerup> powerups;

    private LinearLayout gametypeLayout;
    private LinearLayout gamemodeLayout;
    private LinearLayout powerupLayout;

    private Gametype selectedGametype;
    private Gamemode selectedGamemode;
    private List<Powerup> selectedPowerups;

    OptionsActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        setOptionVariables(getIntent());

        // Set up our layouts
        gametypeLayout = findViewById(R.id.gametype_layout);
        gamemodeLayout = findViewById(R.id.gamemode_layout);
        powerupLayout = findViewById(R.id.powerup_layout);

        if (skipGametypeSelection()) {
            selectedGametype = gametypes.get(0);
            if (skipGamemodeSelection()) {
                loadPowerupLayout();
            } else {
                loadGamemodeLayout();
            }
        } else {
            loadGametypeLayout();
        }
    }

    private void setOptionVariables(Intent intent) {
        Bundle bundle = intent.getExtras();
        String mapInformationJSON = bundle.getString("mapInformation");
        MapInformation mapInformation = MapInformation.fromJson(mapInformationJSON);

        map = mapInformation.getGameMap();
        gametypes = mapInformation.getGametypes();
        gamemodes = mapInformation.getGamemodes();
        powerups = mapInformation.getPowerups();
    }

    private void setViewsDisabled(LinearLayout layout1, LinearLayout layout2) {
        enableDisableView(layout1, false);
        enableDisableView(layout2, false);
    }

    private void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);

        if ( view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup)view;

            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    private boolean skipGametypeSelection() {
        return (gametypes != null && gametypes.size() == 1);
    }

    private boolean skipGamemodeSelection() {
        return selectedGametype == Gametype.SLAYER;
    }

    private void loadGametypeLayout() {
        setViewsDisabled(gamemodeLayout, powerupLayout);
        setGametypeButtons(gametypeLayout, gametypes);
    }

    private void loadGamemodeLayout() {
        setViewsDisabled(gametypeLayout, powerupLayout);
        setGamemodeButtons(gamemodeLayout, gamemodes);
    }

    private void loadPowerupLayout() {
        setViewsDisabled(gametypeLayout, gamemodeLayout);
        selectedPowerups = new ArrayList<>();
        setPowerupButtons(powerupLayout, powerups);
    }

    private void setGametypeButtons(LinearLayout layout, List<Gametype> gametypes) {

        if (gametypes != null) {
            for (final Gametype gametype : gametypes) {
                Button button = new Button(this);
                button.setText(gametype.name());
                layout.addView(button);
                button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        selectedGametype = gametype;

                        if (skipGamemodeSelection()) {
                            loadPowerupLayout();
                        } else {
                            loadGamemodeLayout();
                        }
                    }
                });
            }
        }
    }

    private void setGamemodeButtons(LinearLayout layout, List<Gamemode> gamemodes) {
        for (final Gamemode gamemode : gamemodes) {
            Button button = new Button(this);
            button.setText(gamemode.name());
            layout.addView(button);
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    selectedGamemode = gamemode;
                    loadPowerupLayout();
                }
            });
        }
    }

    private void setPowerupButtons(LinearLayout layout, List<Powerup> powerups) {
        Powerup skipPowerup = getPowerupToSkip();
        final Button goButton = new Button(this);
        goButton.setText("GO");
        goButton.setBackgroundColor(Color.rgb(14, 167, 107));
        goButton.setEnabled(false);

        for (final Powerup powerup : powerups) {
            if (skipPowerup != null && powerup == skipPowerup) {
                continue;
            }
            final CheckBox checkBox = new CheckBox(this);
            checkBox.setText(powerup.name());
            layout.addView(checkBox);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        selectedPowerups.add(powerup);
                    } else {
                        selectedPowerups.remove(powerup);
                    }
                    enableDisableView(goButton, ! selectedPowerups.isEmpty());
                }
            });
        }

        layout.addView(goButton);
    }

    private Powerup getPowerupToSkip() {
        Powerup powerupToSkip = null;

        if (map == GameMap.CONSTRUCT) {
            if (selectedGametype == Gametype.SLAYER) {
                powerupToSkip = Powerup.OVERSHIELD;
            } else if (selectedGametype == Gametype.KING_OF_THE_HILL) {
                powerupToSkip = Powerup.CAMO;
            }
        }

        return powerupToSkip;
    }

    private Button createGoButton() {
        Button goButton = new Button(this);
        goButton.setText("GO");
        goButton.setBackgroundColor(Color.rgb(14, 167, 107));
        goButton.setEnabled(false);
        goButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openMapActivity();
            }
        });

        return goButton;
    }

    private void openMapActivity() {

    }

    private String getTimeFromGamemode(Gamemode gamemode) {
        String time = null;

        if (gamemode == Gamemode.MATCHMAKING) {
            time = "15:00";
        } else if (gamemode == Gamemode.CUSTOM_GAME) {
            time = "30:00";
        }

        return time;
    }
}
