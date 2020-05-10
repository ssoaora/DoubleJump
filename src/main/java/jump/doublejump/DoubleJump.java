package jump.doublejump;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.block.BlockAir;
import cn.nukkit.permission.DefaultPermissions;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerToggleFlightEvent;
import cn.nukkit.math.Vector3;


public class DoubleJump extends PluginBase implements Listener {
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		if(player.isCreative()) 
			return;
		event.setCancelled(true);
		player.setAllowFlight(false);
		player.setMotion(player.getLocation().getDirectionVector().multiply(2.5D).add(0.0D, 1.5D, 0.0D));
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(!player.isCreative() 
				&& player.isOnGround() 
				&& !player.getAllowFlight())
			player.setAllowFlight(true);
	}
}
