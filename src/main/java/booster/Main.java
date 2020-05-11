package booster;

import cn.nukkit.scheduler.Task;
import cn.nukkit.Server;
import cn.nukkit.event.player.PlayerKickEvent;
import cn.nukkit.event.EventHandler;
import cn.nukkit.block.Block;
import cn.nukkit.math.Vector3;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Sound;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.Player;
import java.util.HashSet;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase implements Listener
{
    public static HashSet<Player> boosting;
    
    static {
        Main.boosting = new HashSet<Player>();
    }
    
    public void onEnable() {
        this.getLogger().notice("This plugin is made by Nexux Server");
        this.getLogger().info("§eSelling this plugin for commercial use is prohibited");
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
    }
    
    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getPlayer().getLevel().getBlock(new Vector3((double)(int)Math.round(event.getPlayer().x - 0.5), (double)(int)Math.round(event.getPlayer().y - 1.0), (double)(int)Math.round(event.getPlayer().z - 0.5)));
        if (block.getId() == 42 | block.getId() == 41 | block.getId() == 57) {
            switch (block.getId()) {
                case 42: {
                    player.setMotion(this.getShotting(player, 1));
                    this.setFall(player);
                }
                case 41: {
                    player.setMotion(this.getShotting(player, 2));
                    this.setFall(player);
                }
                case 57: {
                    player.setMotion(this.getShotting(player, 3));
                    this.setFall(player);
                }
            }
        }
    }
    
    @EventHandler
    public void onKick(final PlayerKickEvent event) {
        if (event.getReasonEnum() == PlayerKickEvent.Reason.FLYING_DISABLED && Main.boosting.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
    
    public Vector3 getShotting(final Player player, final int xx) {
    	Level level = player.getLevel();
		level.addSound(player.getLocation(), Sound.FIREWORK_LAUNCH);
        final double x = -Math.sin(player.yaw / 180.0 * 3.141592653589793) * Math.cos(player.pitch / 180.0 * 3.141592653589793);
        final double y = -Math.sin(player.pitch / 180.0 * 3.141592653589793);
        final double z = Math.cos(player.yaw / 180.0 * 3.141592653589793) * Math.cos(player.pitch / 180.0 * 3.141592653589793);
        return new Vector3(x * xx, y * xx, z * xx);
    }
    
    public void setFall(final Player player) {
        Main.boosting.add(player);
        Server.getInstance().getScheduler().scheduleDelayedTask((Task)new Task() {
            public void onRun(final int currentTick) {
                if (Main.boosting.contains(player)) {
                    Main.boosting.remove(player);
                }
            }
        }, 200);
    }
}
