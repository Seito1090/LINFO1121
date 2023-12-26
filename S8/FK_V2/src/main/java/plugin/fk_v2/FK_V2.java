package plugin.fk_v2;

import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public final class FK_V2 extends JavaPlugin {
    //Instance variable for the plugin itself, might not be needed in the end but still here just in case
    private FK_V2 INSTANCE;
    //---------------------------Variables shared between all game instances---------------------------
    //Config management variables
    private static File customConfigFile;
    private static FileConfiguration customConfig;
    //Map management variables
    private static List<int[][]> differentMaps = new ArrayList<>();
    private static List<String> differentMapNames = new ArrayList<>();
    private static List<String> knownWorlds = new ArrayList<>();
    private static List<String> loadedWorlds = new ArrayList<>();
    //public List<List<UUID>> teams; TODO make this into an instance variable of a game
    //public List<RegionManager> bases, cores; TODO make this into an instance variable of a game
    //Team management variables
    private static String[] admissibleTeams = {"red", "blue", "green", "yellow"};
    private static List<ChatColor> admissibleTeamColours = new ArrayList<>(Arrays.asList(
            ChatColor.RED, ChatColor.BLUE, ChatColor.GREEN, ChatColor.YELLOW
    ));
    private static List<Material> teamWools = new ArrayList<>(Arrays.asList(
            Material.RED_WOOL, Material.BLUE_WOOL, Material.GREEN_WOOL, Material.YELLOW_WOOL
    ));
    //Game management variables
    public static int numberOfUsedTeams; //TODO delete this to make it an instance variable of a game
    public String worldName, worldName_copy; //TODO delete this to make it an instance variable of a game
    //Player management variables
    //public Map<UUID, PlayerReplacement> villagers = new HashMap<>(); TODO make this into an instance variable of a game


    @Override
    public void onLoad(){INSTANCE = this;}

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
