package jump.doublejump;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.block.BlockAir;
import cn.nukkit.permission.DefaultPermissions;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerToggleFlightEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;


public class DoubleJump extends PluginBase implements Listener {
	
	List<String> toggled = new ArrayList<String>();
	
	public void onEnable() {
		this.getLogger().notice("§eThis Plugin Is Made By aesoppppp");
        this.getLogger().info("§dDoubleJump v0.0.3 Is Enabled!");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("djump")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("§cYou must be a player to run this command!");
				return false;
			}
			Player player = (Player) sender;
			if(toggled.contains(player.getName())) {
				player.sendMessage("§cYou are not Double Jumping!");
				toggled.remove(player.getName());
				return true;
			}
			player.sendMessage("§7[§6!§7] §fYou are now Double Jumping!");
			toggled.add(player.getName());
			return true;
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		if(player.isCreative() && !toggled.contains(player.getName()))
			return;
		event.setCancelled(true);
		player.setAllowFlight(false);
		if (toggled.contains(player.getName())) {
		player.setMotion(player.getLocation().getDirectionVector().multiply(2.5D).add(0.0D, 1.5D, 0.0D));
		Level level = player.getLevel();
		level.addSound(player.getLocation(), Sound.MOB_ENDERDRAGON_FLAP);
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(!player.isCreative() 
				&& toggled.contains(player.getName())
				&& player.isOnGround() 
				&& !player.getAllowFlight())
			player.setAllowFlight(true);
	}
}
