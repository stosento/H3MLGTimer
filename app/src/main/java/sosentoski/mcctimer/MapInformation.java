package sosentoski.mcctimer;

import com.google.gson.Gson;
import sosentoski.mcctimer.enums.GameMap;
import sosentoski.mcctimer.enums.Gamemode;
import sosentoski.mcctimer.enums.Gametype;
import sosentoski.mcctimer.enums.Powerup;

import java.util.List;

public class MapInformation
{
    private GameMap gameMap;
    private List<Gametype> gametypes;
    private List<Gamemode> gamemodes;
    private List<Powerup> powerups;

    public MapInformation(GameMap gameMap, List<Gametype> gametypes, List<Gamemode> gamemodes, List<Powerup> powerups)
    {
        this.gameMap = gameMap;
        this.gametypes = gametypes;
        this.gamemodes = gamemodes;
        this.powerups = powerups;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static MapInformation fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, MapInformation.class);
    }

    public GameMap getGameMap()
    {
        return gameMap;
    }
    public List<Gametype> getGametypes()
    {
        return gametypes;
    }
    public List<Gamemode> getGamemodes()
    {
        return gamemodes;
    }
    public List<Powerup> getPowerups()
    {
        return powerups;
    }
}
