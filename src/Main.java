package OnlinePlayerCheck;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class Main extends Plugin implements Listener{
	public void onEnable() {
		/*getLogger().info("===DEBUG===");
		
		getLogger().info("===DEBUG===");*/
		getLogger().info("§dOnlinePlayerCheck(BungeeCordVer) Enabled");
		Utils.readcfg();
		if(Utils.cfgstats == false) {
			BungeeCord.getInstance().getLogger().warning("&4OCB FATAL ERROR:FAIL TO READ CONFIG");
			sleep(3*1000);
		} else {
			Utils.log("服务器已开启，正在重写html",""
					,"Server enabled, rewriting html","");
			Utils.write_online_basic();
			Utils.write_online_pro();
			Utils.write_online_log();
			Utils.add_online_log_strong("服务器已开启","Server enabled");
			
			if(Utils.lowperformance == true) {
				Utils.log("当前为低性能模式,功能将受限。","在%appdata%\\ocb.cfg中修改"
						,"Low performance mode will limit functions.","Change it in %appdata%\\ocb.cfg");
				lowperformanceloop();
			}
			
			if(Utils.writeonlinegraph == true) {
				hourloop();
			} else {
				Utils.disable_online_graph();
			}
			BungeeCord.getInstance().getPluginManager().registerCommand(this, new COCommand(this));
			BungeeCord.getInstance().getPluginManager().registerListener(this, this);
		}
	}
	
	public void onDisable() {
		if(Utils.cfgstats == true) {
			Utils.log("服务器已关闭，正在重写html",""
					,"Server disabled, rewriting html","");
			Utils.write_online_basic();
			Utils.write_online_pro();
			Utils.write_online_log();
			Utils.add_online_log_strong("服务器已关闭","Server disabled");
		}
		getLogger().info("§dOnlinePlayerCheck(BungeeCordVer) Disabled");
	}
	
	@EventHandler
    public void onConnect(ServerConnectEvent e) {
		if(Utils.cfgstats == true && Utils.lowperformance == false) {
			BungeeCord.getInstance().getScheduler().schedule(this, new Runnable() {
				@Override
				public void run() {
					if(Utils.logmode >= 2) {
						Utils.log("玩家加入,写入各项数据","","Player joined. Writing data.","");
					}
					Utils.write_online_basic();
					Utils.write_online_pro();
					Utils.add_online_log_player(e.getPlayer().getName(),true,e.getTarget().getName());
					Utils.check_max_player_count();
				}
			}, 1L, TimeUnit.SECONDS);
		}
	}
	
	@EventHandler
    public void onDisconnect(PlayerDisconnectEvent e) {
		if(Utils.cfgstats == true && Utils.lowperformance == false) {
			BungeeCord.getInstance().getScheduler().schedule(this, new Runnable() {
				@Override
				public void run() {
					if(Utils.logmode >= 2) {
						Utils.log("玩家离开,写入各项数据","","Player left. Writing data.","");
					}
					Utils.write_online_basic();
					Utils.write_online_pro();
					Utils.add_online_log_player(e.getPlayer().getName(),false,"");
				}
			}, 1L, TimeUnit.SECONDS);
		}
	}
	
	public void hourloop() {
		Calendar c = Calendar.getInstance();
		int m = c.get(Calendar.MINUTE);
		BungeeCord.getInstance().getScheduler().schedule(this, new Runnable() {
			@Override
			public void run() {
				while(true) {
					if(Utils.lowperformance == true) {
						Utils.log("已从实时模式切换为低性能模式","",
								"Switch from high-performance-mode to low-performance-mode.","");
						break;
					}
					Utils.write_history_player_count();
					sleep(1000);
					Utils.write_online_graph();
					sleep(1000*60*60-1000);
				}
				lowperformanceloop();
			}
		}, (60-m+1), TimeUnit.MINUTES);
	}
	public void lowperformanceloop() {
		Calendar c = Calendar.getInstance();
		int s = c.get(Calendar.SECOND);
		BungeeCord.getInstance().getScheduler().schedule(this, new Runnable() {
			@Override
			public void run() {
				while(true) {
					if(Utils.lowperformance == false) {
						Utils.log("已从低性能模式切换为实时模式","",
								"Switch from low-performance-mode to high-performance-mode.","");
						break;
					}
					if(Utils.logmode >= 3) {
						Utils.log("正在进行低性能每分钟统计",""
								,"Low performance mode:writing stats","");
					}
					Utils.write_online_basic();
					Utils.write_online_pro();
					Utils.check_max_player_count();
					sleep(1000*60);
				}
				hourloop();
			}
		},(60-s+1), TimeUnit.SECONDS);
	}
	public void sleep(int time) {
		try {
			Object lock=new Object();
			synchronized(lock) {
				lock.wait(time);
			}
		} catch(InterruptedException e) {
			Utils.error("暂停被打断",e.toString()
					,"Pause interrupt",e.toString());
		}
		return;
	}
	
}
