package sosentoski.mcctimer;

import com.google.gson.Gson;
import sosentoski.mcctimer.enums.GameMap;
import sosentoski.mcctimer.enums.Gamemode;
import sosentoski.mcctimer.enums.Gametype;
import sosentoski.mcctimer.enums.Powerup;

import java.util.List;

public class GameInformation
{
    private GameMap map;
    private Gametype gametype;
    private Gamemode gamemode;
    private List<Powerup> powerups;

    public GameInformation(GameMap gameMap, Gametype gametype, Gamemode gamemode, List<Powerup> powerups)
    {
        this.map = gameMap;
        this.gametype = gametype;
        this.gamemode = gamemode;
        this.powerups = powerups;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static GameInformation fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GameInformation.class);
    }

    public GameMap getMap()
    {
        return map;
    }

    public Gametype getGametype()
    {
        return gametype;
    }

    public Gamemode getGamemode()
    {
        return gamemode;
    }

    public List<Powerup> getPowerups()
    {
        return powerups;
    }
}
