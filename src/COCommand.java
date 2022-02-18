package OnlinePlayerCheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class COCommand extends Command{
	public COCommand(Main pl) {
		super("ocb", null, "onlinecheck");
	}
	
	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args) {
		boolean status=false;
    	if(args.length == 0) {
        		sender.sendMessage(Utils.Chat("&a" + Utils.translate("在线玩家", "Online players") + ": &9" + Utils.get_online_player_count()));
        		sender.sendMessage(Utils.Chat("&a" + Utils.translate("在线列表", "Online list") + ": &9" + BungeeCord.getInstance().getPlayers()));
        		if(sender.hasPermission("ocb.operate")) {
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb write <all/basic/pro/graph/log> | " + Utils.translate("重写 <所有网页/","rewrite <all htmls/") + "online_basic.html/online_pro.html/online_graph.html/online_log.html>"));
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb addlog <weak/normal/strong> <content> | " + Utils.translate("增加log <弱/中/强(log显示的强弱)> <内容>","Add log <weak/normal/strong> <content>")));
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb changetheme <theme> | " + Utils.translate("更改主题为<主题>, 主题不存在会提示存在的主题","Change theme to <theme>, if the theme is not exist it will tip themes available")));
            		sender.sendMessage(Utils.Chat("&6[OP]/ocb reload | " + Utils.translate("重载","reload ocb")));
        		}
    	} else if(args[0].equalsIgnoreCase("write") || args[0].equalsIgnoreCase("addlog") || args[0].equalsIgnoreCase("changetheme")) {
    		if(sender.hasPermission("ocb.operate")) {
    			String CommandLog="/ocb";
    			for(int i=0;i<args.length;++i) {
    			    CommandLog += " " + args[i];
    			}
    			Utils.add_online_log_normal("管理员 " + sender + " 执行了 " + CommandLog
    					,"Operater " + sender + " run command " + CommandLog);
    			switch(args[0]) {
    			case "write":
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
        			break;
    			case "addlog":
    				args[2]=Utils.PreventHtmlInject(args[2]);
    				switch(args[1]) {
    				case "weak":
    					status = Utils.add_online_log_weak(args[2],args[2]);
    					break;
    				case "normal":
    					status = Utils.add_online_log_normal(args[2], args[2]);
    					break;
    				case "strong":
    					status = Utils.add_online_log_strong(args[2], args[2]);
    					break;
    				default:
    					sender.sendMessage(Utils.Chat("&dOCB>&c" + Utils.translate("参数args[1] = ", "Command args[1] = &f") + args[1] + "&c" + Utils.translate("错误(无法找到命令)", "error(can not find the command)")));
    					break;
    				}
    				String statusString="";
    				if(status == true) {
    					statusString=Utils.Chat("&3") + Utils.translate("成功", "success");
    				} else {
    					statusString=Utils.Chat("&c") + Utils.translate("失败", "failed");
    				}
    				sender.sendMessage("&dOCB>&9" + Utils.translate("增加log ","Add log &f") + args[2] + statusString);
    				break;
    			case "changetheme":
    				String s = "",DirCommand="dir " + Utils.localpath + "themes /A:D /B";
    				DirCommand=DirCommand.replaceAll("\\\\\\\\","\\\\");//替换所有\\成\
    				//sender.sendMessage(DirCommand); DEBUG
    				try {
						s = get_commandline_results(DirCommand); //获得dir结果
					} catch (IOException e) {
						sender.sendMessage(Utils.translate("运行dir时出现错误", "Error occured when running program dir") + e.toString());
						Utils.add_online_log_weak("管理员" + sender.getName() + "复制主题" + args[1] + "时出现错误" + e.toString(), "Operator " + sender.getName() + " caused an error when copying theme \"" + args[1] + "\", error:" + e.toString());
					} catch (InterruptedException e) {
						sender.sendMessage(Utils.translate("运行dir时出现错误", "Error occured when running program dir") + e.toString());
						Utils.add_online_log_weak("管理员" + sender.getName() + "复制主题" + args[1] + "时出现错误" + e.toString(), "Operator " + sender.getName() + " caused an error when copying theme \"" + args[1] + "\", error:" + e.toString());
					}
    				sender.sendMessage(Utils.Chat("&dOCB>&b" + Utils.translate("找到以下主题", "Find themes available") + " &f" + s));
    				try {
						Runtime.getRuntime().exec("cmd.exe /C xcopy " + Utils.localpath + "themes\\" + args[1] + "\\* " + Utils.localpath + "online\\* /E /C /I /Q /G /Y");
						sender.sendMessage(Utils.Chat("&dOCB>&3" + Utils.translate("更换主题成功", "Success") + " " + Utils.onlinepath + "online/index.html"));
					} catch (IOException e) {
						sender.sendMessage(Utils.Chat(Utils.translate("&dOCB>&c复制时出现错误:", "&dOCB>&cError when copying:") + e.toString()));
						Utils.add_online_log_weak("管理员" + sender.getName() + "复制主题" + args[1] + "时出现错误" + e.toString(), "Operator " + sender.getName() + " caused an error when copying theme \"" + args[1] + "\", error:" + e.toString());
					}
    				//获取online graph配置
    				sender.sendMessage(COtip(sender.getName(),"Reading ocb.cfg...",Utils.readcfg(),true));
    				//重写所有
       				sender.sendMessage(COtip(sender.getName(),"online_pro.html",Utils.write_online_pro(),true));
       				sender.sendMessage(COtip(sender.getName(),"online_log.html",Utils.write_online_log(),true));
       				sender.sendMessage(COtip(sender.getName(),"online_graph.html",Utils.write_online_graph(),true));
       				sender.sendMessage(COtip(sender.getName(),"online_basic.html",Utils.write_online_basic(),true));
    				break;
    			case "reload":
    				sender.sendMessage(COtip(sender.getName(),"Reading ocb.cfg",Utils.readcfg(),true));
    				sender.sendMessage(COtip(sender.getName(),"NOTE: Switch between low and high performance modes won't work immediately",true,false));
    				break;
    			default:
    				sender.sendMessage(Utils.Chat("&dOCB>&9" + Utils.translate("参数args[0] = ", "Command args[0] = ") + args[0] + Utils.translate("错误(无法找到命令)", "error(can not find the command)")));
    				break;
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
	
	public static String get_commandline_results(String cmd) throws IOException, InterruptedException {
        String result = "";
        final Process p = Runtime.getRuntime().
            exec(String.format("cmd /c %s", cmd));
        final ProcessResultReader stderr = new ProcessResultReader(
                p.getErrorStream(), "STDERR");
        final ProcessResultReader stdout = new ProcessResultReader(
                p.getInputStream(), "STDOUT");
        stderr.start();
        stdout.start();
        final int exitValue = p.waitFor();
        if (exitValue == 0){
            result = stdout.toString();
        }
        else{
            result = stderr.toString();
        }
        return result;
    }
}

class ProcessResultReader extends Thread{
    final InputStream is;
    final String type;
    final StringBuilder sb;

    ProcessResultReader(final InputStream is, String type){
        this.is = is;
        this.type = type;
        this.sb = new StringBuilder();
    }

    public void run()
    {
        try{
            final InputStreamReader isr = new InputStreamReader(is);
            final BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null)
            {
                this.sb.append(line).append("\n");
            }
        }
        catch (final IOException ioe)
        {
            System.err.println(ioe.getMessage());
            throw new RuntimeException(ioe);
        }
    }
    @Override
    public String toString()
    {
        return this.sb.toString();
    }
}