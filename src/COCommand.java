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
        		sender.sendMessage(Utils.Chat("&a" + Utils.translate("在线玩家", "Online players") + ": &9" + Utils.get_online_player_count()));
        		sender.sendMessage(Utils.Chat("&a" + Utils.translate("在线列表", "Online list") + ": &9" + BungeeCord.getInstance().getPlayers()));
        		if(sender.hasPermission("ocb.write")) {
        			sender.sendMessage(Utils.Chat("&6[OP]/ocb write all " + Utils.translate("重写以下所有数据","Rewrite all html below")));
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb write basic " + Utils.translate("重写","rewrite") + "online_basic.html"));
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb write pro " + Utils.translate("重写","rewrite") + "online_pro.html"));
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb write log " + Utils.translate("重写","rewrite") + "online_log.html"));
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb write graph " + Utils.translate("重写","rewrite") + "online_graph.html"));
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
    				sender.sendMessage(COtip(sender.getName(),"online_graph.html",Utils.write_online_graph(),true));
    			}
    			if(args[1].equalsIgnoreCase("all") || args[1].equalsIgnoreCase("basic")) {
    				sender.sendMessage(COtip(sender.getName(),"online_basic.html",Utils.write_online_basic(),true));
    			}
    		}
    		else {
    			sender.sendMessage(Utils.noperm());
    		}
    	}
	}
	public String COtip(String sender,String name,boolean status,boolean log) {
		//COtip(sender.getName(),"",Utils,);
		if(log == true) {
			Utils.add_online_log_normal("管理员 " + sender + " 重新写入了 " + name
					,"Operater " + sender + " rewrite " + name);
		}
		if(status == true) {
			return (Utils.Chat("&dOCB>&a" + Utils.translate("写入", "Write") + name + Utils.translate("成功", "complete")));
		} 
		else {
			return(Utils.Chat("&dOCB>&4" + Utils.translate("写入", "Write") + name + Utils.translate("失败","fail")));
		}
	}
}
