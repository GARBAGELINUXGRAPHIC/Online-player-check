package OnlinePlayerCheck;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeUnit.*;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class Main extends Plugin implements Listener{
	public void onEnable() {
		getLogger().info("§dOnlinePlayerCheck(BungeeCordVer) Enabled");
		
		maininfo("Read OCB config",Utils.readcfg(),"");
		maininfo("DEBUG CONFIG[INT]" + "VERSION" + Utils.version +" LANGUAGE" + Utils.language + " LOGMODE" + Utils.logmode,true,"");
		maininfo("DEBUG CONFIG[STRING]" + "LOCAL PATH" + Utils.ocbpath +" ONLINE PATH" + Utils.onlinepath,true,"");
		maininfo("DEBUG CONFIG[BOOL1]" + "rewritelog " + Utils.rewritelog + " writeonlinebasic " + Utils.writeonlinebasic + " addonlinelog " + Utils.addonlinelog,true,"");
		
		SimpleDateFormat time=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Date now=new Date();
		
			if(Utils.language == 1) {
				if(Utils.logmode == 1) {
					maininfo("服务器已开启，正在重写html",true,"");
					Utils.write_online_basic(0);
					Utils.write_online_pro();
					Utils.write_online_log();
					Utils.add_online_log_strong("服务器已开启");
				} else {
					if(Utils.writeonlinebasic == true) {
						maininfo("重写online_basic"
								,Utils.write_online_basic(0)
								,"玩家数:0");
					}
					if(Utils.writeonlinepro == true) {
						maininfo("重写online_pro"
								,Utils.write_online_pro()
								,"当前玩家数:" + Utils.get_online_player_count() + 
										";最大玩家数:" + Utils.get_max_player_count() + 
										";平均玩家数:" + Utils.get_average_player_count() +
										";玩家序列:" + BungeeCord.getInstance().getPlayers()	   );
					}
					if(Utils.rewritelog == true) {
						maininfo("重写log",Utils.write_online_log(),"");
					}
					if(Utils.writeonlinebasic == true) {
						maininfo("增加log"
								,Utils.add_online_log_strong("服务器已开启"),
								"内容:" + time.format(now) + " 服务器已开启");
					}
				}
			} else {
				if(Utils.logmode == 1) {
					maininfo("Rewrite html...",true,"");
					Utils.write_online_basic(0);
					Utils.write_online_pro();
					Utils.write_online_log();
					Utils.add_online_log_strong("Server enabled");
				} else {
					if(Utils.writeonlinebasic == true) {
						maininfo("Rewrite online_basic"
								,Utils.write_online_basic(0)
								,"Current player count:0");
					}
					if(Utils.writeonlinepro == true) {
						maininfo("Rewrite online_pro"
								,Utils.write_online_pro()
								,"Current player count:" + Utils.get_online_player_count() + 
										";max player count:" + Utils.get_max_player_count() + 
										";average player count:" + Utils.get_average_player_count() +
										";player list:" + BungeeCord.getInstance().getPlayers()	   );
					}
					if(Utils.rewritelog == true) {
						maininfo("Rewrite log",Utils.write_online_log(),"");
					}
					if(Utils.writeonlinebasic == true) {
						maininfo("Add log"
								,Utils.add_online_log_strong("Server enabled"),
								"Content:" + time.format(now) + " Server enabled");
					}
				}
			}
			
			if(Utils.lowperformance == true) {
				if(Utils.language == 1) {
					maininfo("当前为低性能模式,功能将受限。在%appdata%\\ocb.cfg中修改",true,"");
				} else {
					maininfo("Low performance mode will limit functions. Change it in %appdata%\\ocb.cfg",true,"");
				}
				lowperformanceloop();
			}			
		
			if(Utils.writeonlinegraph == true) {
				hourloop();
			}
		BungeeCord.getInstance().getPluginManager().registerCommand(this, new COCommand(this));
		BungeeCord.getInstance().getPluginManager().registerListener(this, this);
	}
	
	public void onDisable() {
		
		SimpleDateFormat time=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Date now=new Date();
		
			if(Utils.language == 1) {
				if(Utils.logmode == 1) {
					maininfo("服务器已关闭，正在重写html",true,"");
					Utils.write_online_basic(0);
					Utils.write_online_pro();
					Utils.add_online_log_strong("服务器已关闭");
				} else {
					if(Utils.writeonlinebasic == true) {
						maininfo("重写online_basic"
								,Utils.write_online_basic(0)
								,"玩家数:0");
					}
					if(Utils.writeonlinepro == true) {
						maininfo("重写online_pro"
								,Utils.write_online_pro()
								,"当前玩家数:" + Utils.get_online_player_count() + 
										";最大玩家数:" + Utils.get_max_player_count() + 
										";平均玩家数:" + Utils.get_average_player_count() +
										";玩家序列:" + BungeeCord.getInstance().getPlayers()	   );
					}
					if(Utils.rewritelog == true) {
						maininfo("重写log",Utils.write_online_log(),"");
					}
					if(Utils.writeonlinebasic == true) {
						maininfo("增加log"
								,Utils.add_online_log_strong("服务器已关闭"),
								"内容:" + time.format(now) + " 服务器已关闭");
					}
				}
			} else {
				if(Utils.logmode == 1) {
					maininfo("Rewrite html...",true,"");
					Utils.write_online_basic(0);
					Utils.write_online_pro();
					Utils.add_online_log_strong("Server disabled");
				} else {
					if(Utils.writeonlinebasic == true) {
						maininfo("Rewrite online_basic"
								,Utils.write_online_basic(0)
								,"Current player count:0");
					}
					if(Utils.writeonlinepro == true) {
						maininfo("Rewrite online_pro"
								,Utils.write_online_pro()
								,"Current player count:" + Utils.get_online_player_count() + 
										";max player count:" + Utils.get_max_player_count() + 
										";average player count:" + Utils.get_average_player_count() +
										";player list:" + BungeeCord.getInstance().getPlayers()	   );
					}
					if(Utils.rewritelog == true) {
						maininfo("Rewrite log",Utils.write_online_log(),"");
					}
					if(Utils.writeonlinebasic == true) {
						maininfo("Add log"
								,Utils.add_online_log_strong("Server disabled"),
								"Content:" + time.format(now) + " Server disabled");
					}
				}
			}
		
		
		
		getLogger().info("§dOnlinePlayerCheck(BungeeCordVer) Disabled");
		
	}
	
	@EventHandler
    public void onConnect(ServerConnectEvent e) {
		BungeeCord.getInstance().getScheduler().schedule(this, new Runnable() {
			@Override
			public void run() {
				if(Utils.lowperformance == false) {
					if(Utils.logmode == 1) {
						if(Utils.language == 1) {
							maininfo("玩家加入,写入各项数据",true,"");
							Utils.write_online_basic(Utils.get_online_player_count());
							Utils.add_online_log_player(e.getPlayer().getName(),true,e.getTarget().getName());
							Utils.write_online_pro();
							if(Utils.get_max_player_count() < Utils.get_online_player_count() && Utils.writeonlinegraph == true) {
								getLogger().info(Utils.Chat("&dOCB>&a服务器玩家新数量: " + Utils.get_online_player_count() + " 人!"));
								maininfo("重写新最高玩家数",Utils.write_max_player_count(Utils.get_online_player_count()),"");
								maininfo("重写online_pro"
										,Utils.write_online_pro()
										,"");
							}
						} else {
							maininfo("Player joined. Writing data.",true,"");
							Utils.write_online_basic(Utils.get_online_player_count());
							Utils.add_online_log_player(e.getPlayer().getName(),true,e.getTarget().getName());
							Utils.write_online_pro();
							if(Utils.get_max_player_count() < Utils.get_online_player_count() && Utils.writeonlinegraph == true) {
								getLogger().info(Utils.Chat("&dOCB>&aNew max player count: " + Utils.get_online_player_count() + " !"));
								maininfo("Rewrite max player count",Utils.write_max_player_count(Utils.get_online_player_count()),"");
								maininfo("Rewrite online_pro"
										,Utils.write_online_pro()
										,"");
							}
						}
					} else {
						if(Utils.language == 1) {
							maininfo("玩家加入,重写online_basic.html"
									,Utils.write_online_basic(Utils.get_online_player_count())
									,"当前玩家数:"+Utils.get_online_player_count());
	
							SimpleDateFormat time=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
							Date now=new Date();
							maininfo("玩家加入,增加log"
									,Utils.add_online_log_player(e.getPlayer().getName(),
																		true, 
																		e.getTarget().getName())
									,"内容:" + time.format(now) + 
									" " + e.getPlayer().getName() + 
									" joined server " + e.getTarget().getName());
							
							maininfo("玩家加入,重写online_pro"
									,Utils.write_online_pro()
									,"当前玩家数:" + Utils.get_online_player_count() + 
											";最大玩家数:" + Utils.get_max_player_count() + 
											";平均玩家数:" + Utils.get_average_player_count() +
											";玩家序列:" + BungeeCord.getInstance().getPlayers()	   );
							
							//判断最大玩家数
							if(Utils.get_max_player_count() < Utils.get_online_player_count() && Utils.writeonlinegraph == true) {
								getLogger().info(Utils.Chat("&dOCB>&a服务器玩家新数量: " + Utils.get_online_player_count() + " 人!"));
								maininfo("重写新最高玩家数",Utils.write_max_player_count(Utils.get_online_player_count()),Utils.get_online_player_count()+"");
								maininfo("重写online_pro"
										,Utils.write_online_pro()
										,"当前玩家数:" + Utils.get_online_player_count() + 
												";最大玩家数:" + Utils.get_max_player_count() + 
												";平均玩家数:" + Utils.get_average_player_count() +
												";玩家序列:" + BungeeCord.getInstance().getPlayers()	   );
							}
						} else {
							maininfo("Player joined. Rewrite online_basic.html"
									,Utils.write_online_basic(Utils.get_online_player_count())
									,"Current player count:"+Utils.get_online_player_count());
	
							SimpleDateFormat time=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
							Date now=new Date();
							maininfo("Player joined. Add log"
									,Utils.add_online_log_player(e.getPlayer().getName(),
																		true, 
																		e.getTarget().getName())
									,"Content:" + time.format(now) + 
									" " + e.getPlayer().getName() + 
									" joined server " + e.getTarget().getName());
							
							maininfo("Player joined. Rewrite online_pro.html"
									,Utils.write_online_pro()
									,"Current player count:" + Utils.get_online_player_count() + 
											";Max player count:" + Utils.get_max_player_count() + 
											";Average player count:" + Utils.get_average_player_count() +
											";Player list:" + BungeeCord.getInstance().getPlayers()	   );
							
							//判断最大玩家数
							if(Utils.get_max_player_count() < Utils.get_online_player_count() && Utils.writeonlinegraph == true) {
								getLogger().info(Utils.Chat("&dOCB>&aNew server online player count: " + Utils.get_online_player_count() + " !"));
								maininfo("Rewrite max player count",Utils.write_max_player_count(Utils.get_online_player_count()),Utils.get_online_player_count()+"");
								maininfo("Player joined. Rewrite online_pro.html"
										,Utils.write_online_pro()
										,"Current player count:" + Utils.get_online_player_count() + 
												";Max player count:" + Utils.get_max_player_count() + 
												";Average player count:" + Utils.get_average_player_count() +
												";Player list:" + BungeeCord.getInstance().getPlayers()	   );
							}
						}
					}
				}
			}
		}, 1L, TimeUnit.SECONDS);
	}
	
	@EventHandler
    public void onDisconnect(PlayerDisconnectEvent e) {
		BungeeCord.getInstance().getScheduler().schedule(this, new Runnable() {
			@Override
			public void run() {
				if(Utils.lowperformance == false) {
					if(Utils.logmode == 1) {
						if(Utils.language == 1) {
							maininfo("玩家离开,写入各项数据",true,"");
							Utils.write_online_basic(Utils.get_online_player_count());
							Utils.add_online_log_player(e.getPlayer().getName(),false,"");
							Utils.write_online_pro();
						} else {
							maininfo("Player left. Writing data.",true,"");
							Utils.write_online_basic(Utils.get_online_player_count());
							Utils.add_online_log_player(e.getPlayer().getName(),false,"");
							Utils.write_online_pro();
						}
					} else {
						if(Utils.language == 1) {
							maininfo("玩家离开,重写online_basic.html"
									,Utils.write_online_basic(Utils.get_online_player_count())
									,"当前玩家数:"+Utils.get_online_player_count());
							SimpleDateFormat time=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
							Date now=new Date();
							maininfo("玩家离开,增加log"
									,Utils.add_online_log_player(e.getPlayer().getName(),
																		false, 
																		"")
									,"内容:" + time.format(now) + 
									" " + e.getPlayer().getName() + 
									" left");
							
							maininfo("玩家离开,重写online_pro"
									,Utils.write_online_pro()
									,"当前玩家数:" + Utils.get_online_player_count() + 
											";最大玩家数:" + Utils.get_max_player_count() + 
											";平均玩家数:" + Utils.get_average_player_count() +
											";玩家序列:" + BungeeCord.getInstance().getPlayers()	   );
							
						} else {
							maininfo("Player left. Rewrite online_basic.html"
									,Utils.write_online_basic(Utils.get_online_player_count())
									,"Current player count:"+Utils.get_online_player_count());
	
							SimpleDateFormat time=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
							Date now=new Date();
							maininfo("Player left. Add log"
									,Utils.add_online_log_player(e.getPlayer().getName(),
																		false, 
																		"")
									,"Content:" + time.format(now) + 
									" " + e.getPlayer().getName() + 
									" left");
							
							maininfo("Player left. Rewrite online_pro.html"
									,Utils.write_online_pro()
									,"Current player count:" + Utils.get_online_player_count() + 
											";Max player count:" + Utils.get_max_player_count() + 
											";Average player count:" + Utils.get_average_player_count() +
											";Player list:" + BungeeCord.getInstance().getPlayers()	   );
							
							
						}
					}
				}
			}
		}, 1L, TimeUnit.SECONDS);
		
	}
	
	public void hourloop() {
		Calendar c = Calendar.getInstance();
		int m = c.get(Calendar.MINUTE);
		BungeeCord.getInstance().getScheduler().schedule(this, new Runnable() {
			@Override
			public void run() {
				while(true) {
					if(Utils.language == 1) {
						getLogger().info(Utils.Chat("&dOCB>正在进行每小时玩家统计..."));
					} else {
						getLogger().info(Utils.Chat("&dOCB>Counting players..."));
					}
					int h = c.get(Calendar.HOUR_OF_DAY);
					if(Utils.language == 1) {
						maininfo("写入历史记录...",Utils.write_history_player_count(h),":" + h + ".txt");
					} else {
						maininfo("Write history player count...",Utils.write_history_player_count(h),":" + h + ".txt");
					}
					sleep(1000);
					
					if(Utils.language == 1) {
						maininfo("写入online_graph.html"
							,Utils.write_online_graph(Utils.L(0),Utils.L(1),Utils.L(2),Utils.L(3),Utils.L(4),Utils.L(5),Utils.L(6),Utils.L(7),Utils.L(8),Utils.L(9),Utils.L(10),Utils.L(11),Utils.L(12),Utils.L(13),Utils.L(14),Utils.L(15),Utils.L(16),Utils.L(17),Utils.L(18),Utils.L(19),Utils.L(20),Utils.L(21),Utils.L(22),Utils.L(23))
							,"数据:" + Utils.L(0) + " " + Utils.L(1) + " " + Utils.L(2) + " " + Utils.L(3) + " " + Utils.L(4) + " " + Utils.L(5) + " " + Utils.L(6) + " " + Utils.L(7) + " " + Utils.L(8) + " " + Utils.L(9) + " " + Utils.L(10) + " " + Utils.L(11) + " " + Utils.L(12) + " " + Utils.L(13) + " " + Utils.L(14) + " " + Utils.L(15) + " " + Utils.L(16) + " " + Utils.L(17) + " " + Utils.L(18) + " " + Utils.L(19) + " " + Utils.L(20) + " " + Utils.L(21) + " " + Utils.L(22) + " " + Utils.L(23));
					} else {
						maininfo("Writing online_graph.html"
								,Utils.write_online_graph(Utils.L(0),Utils.L(1),Utils.L(2),Utils.L(3),Utils.L(4),Utils.L(5),Utils.L(6),Utils.L(7),Utils.L(8),Utils.L(9),Utils.L(10),Utils.L(11),Utils.L(12),Utils.L(13),Utils.L(14),Utils.L(15),Utils.L(16),Utils.L(17),Utils.L(18),Utils.L(19),Utils.L(20),Utils.L(21),Utils.L(22),Utils.L(23))
								,"data" + Utils.L(0) + " " + Utils.L(1) + " " + Utils.L(2) + " " + Utils.L(3) + " " + Utils.L(4) + " " + Utils.L(5) + " " + Utils.L(6) + " " + Utils.L(7) + " " + Utils.L(8) + " " + Utils.L(9) + " " + Utils.L(10) + " " + Utils.L(11) + " " + Utils.L(12) + " " + Utils.L(13) + " " + Utils.L(14) + " " + Utils.L(15) + " " + Utils.L(16) + " " + Utils.L(17) + " " + Utils.L(18) + " " + Utils.L(19) + " " + Utils.L(20) + " " + Utils.L(21) + " " + Utils.L(22) + " " + Utils.L(23));
					}
					sleep(1000*60*60-1000);
				}
			}
		}, (60-m), TimeUnit.MINUTES);
	}
	
	public boolean maininfo(String name,boolean status,String tail) {
		//maininfo(sender.getName(),"",Utils,);
		if(Utils.logmode <= 2) {tail = null;}
		if(Utils.language == 1) {
			if(status == true) 
				 {getLogger().info(Utils.Chat("&dOCB>&a"+name+"成功&2"+" | "+tail));}
			else {getLogger().info(Utils.Chat("&dOCB>&4"+name+"失败&c"+tail+" | "));}
		} else {
			if(status == true) 
			 	 {getLogger().info(Utils.Chat("&dOCB>&a"+name+" completed&2"+" | "+tail));}
			else {getLogger().info(Utils.Chat("&dOCB>&4"+name+" failed&c"+tail+" | "));}
		}
		
		return true;
	}
	public void lowperformanceloop() {
		BungeeCord.getInstance().getScheduler().schedule(this, new Runnable() {
			@Override
			public void run() {
				while(true) {
					if(Utils.logmode > 1) {
						if(Utils.language == 1) {
							maininfo("正在进行每分钟低性能统计",true,"");
						} else {
							maininfo("Low performance mode:writing stats",true,"");
						}
					}
					
					Utils.write_online_basic(Utils.get_online_player_count());
					Utils.write_online_pro();
					if(Utils.get_max_player_count() < Utils.get_online_player_count() && Utils.writeonlinegraph == true) {
						if(Utils.language == 1) {
							getLogger().info(Utils.Chat("&dOCB>&a服务器玩家新数量: " + Utils.get_online_player_count() + " 人!"));
							maininfo("重写新最高玩家数",Utils.write_max_player_count(Utils.get_online_player_count()),"");
						} else {
							getLogger().info(Utils.Chat("&dOCB>&aNew record! Server player count: " + Utils.get_online_player_count() + " 人!"));
							maininfo("Rewrite max player count",Utils.write_max_player_count(Utils.get_online_player_count()),"");
						}
					}
					sleep(1000*60);
				}
			}
		},1L, TimeUnit.SECONDS);
	}
	public void sleep(int time) {
		try {Object lock=new Object();synchronized(lock) {lock.wait(time);}}catch(InterruptedException e){e.printStackTrace();}
		return;
	}
	
}
