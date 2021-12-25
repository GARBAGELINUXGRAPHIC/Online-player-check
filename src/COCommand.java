package OnlinePlayerCheck;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class COCommand extends Command{
	public COCommand(Main pl) {
		super("ocb", null, "onlinecheck");
	}
	
	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args) {
    	if(args.length == 0) {
    		if(Utils.language == 1) {
        		sender.sendMessage(Utils.Chat("&6/ocb 查看当前在线玩家"));
        		sender.sendMessage(Utils.Chat("&a在线玩家: &9" + Utils.get_online_player_count()));
        		sender.sendMessage(Utils.Chat("&a在线列表: " + BungeeCord.getInstance().getPlayers()));
        		if(sender.hasPermission("ocb.write")) {
        			sender.sendMessage(Utils.Chat("&6[OP]/ocb write all 重写以下所有数据"));
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb write log 重写online_pro.html"));
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb write log 重写online_log.html"));
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb write graph 重写online_graph.html"));
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb write basic 重写online_basic.html"));
        		}
    		} else {
        		sender.sendMessage(Utils.Chat("&6/ocb View all online players"));
        		sender.sendMessage(Utils.Chat("&aOnline players: &9" + Utils.get_online_player_count()));
        		sender.sendMessage(Utils.Chat("&aOnline list: " + BungeeCord.getInstance().getPlayers()));
        		if(sender.hasPermission("ocb.write")) {
        			sender.sendMessage(Utils.Chat("&6[OP]/ocb write all : Rewrite all html below"));
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb write log : Rewrite online_pro.html"));
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb write log : Rewrite online_log.html"));
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb write graph : Rewrite online_graph.html"));
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb write basic : Rewrite online_basic.html"));
        		}
    		}
    	}else if(args[0].equalsIgnoreCase("write")) {
    		if(sender.hasPermission("ocb.write")) {
    			if(args[1].equalsIgnoreCase("all") || args[1].equalsIgnoreCase("pro")) {
    				sender.sendMessage(COtip(sender.getName(),"online_pro.html",Utils.write_online_pro(),true));
    			}
    			if(args[1].equalsIgnoreCase("all") || args[1].equalsIgnoreCase("log")) {
    				sender.sendMessage(COtip(sender.getName(),"online_log.html",Utils.write_online_log(),true));
    			}
    			if(args[1].equalsIgnoreCase("all") || args[1].equalsIgnoreCase("graph")) {
    				sender.sendMessage(COtip(sender.getName(),"online_graph.html",Utils.write_online_graph(Utils.L(0),Utils.L(1),Utils.L(2),Utils.L(3),Utils.L(4),Utils.L(5),Utils.L(6),Utils.L(7),Utils.L(8),Utils.L(9),Utils.L(10),Utils.L(11),Utils.L(12),Utils.L(13),Utils.L(14),Utils.L(15),Utils.L(16),Utils.L(17),Utils.L(18),Utils.L(19),Utils.L(20),Utils.L(21),Utils.L(22),Utils.L(23)),true));
    			}
    			if(args[1].equalsIgnoreCase("all") || args[1].equalsIgnoreCase("basic")) {
    				sender.sendMessage(COtip(sender.getName(),"online_basic.html",Utils.write_online_basic(Utils.get_online_player_count()),true));
    			}
    		}
    		else {
    			sender.sendMessage(Utils.noperm());
    		}
    	}
	}
	public String COtip(String sender,String name,boolean status,boolean log) {
		//tip(sender.getName(),"",Utils,);
		if(log == true) {
			if(Utils.language == 1) {
				Utils.add_online_log_normal("管理员 " + sender + " 重新写入了 " + name);
			} else {
				Utils.add_online_log_normal("Operater " + sender + " rewrite " + name);
			}
		}
		
		if(Utils.language == 1) {
			if(status == true) {return (Utils.Chat("&dOCB>&a写入" + name + "成功"));} 
			else {return(Utils.Chat("&dOCB>&4写入" + name + "失败"));}
		} else {
			if(status == true) {return (Utils.Chat("&dOCB>&aWrite task " + name + "completed successfully."));} 
			else {return(Utils.Chat("&dOCB>&4Write task " + name + "failed"));}
		}
		
	}
	
}
