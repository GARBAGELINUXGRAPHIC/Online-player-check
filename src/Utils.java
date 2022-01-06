package OnlinePlayerCheck;

import java.text.*;
import java.io.InputStreamReader;
import java.util.*;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import net.md_5.bungee.BungeeCord;

public class Utils {
	public static void log(String Chinese,String Chinese_tail,String English,String English_tail) {
		if(logmode > 0) {
			String output="&dOCB > &f";
			if(Utils.language == 1) {
				output += Chinese;
			} else {
				output += English;
			}
			if(Utils.logmode >= 3) {
				output += " | ";
				if(Utils.language == 1) {
					output += Chinese_tail;
				} else {
					output += English_tail;
				}
			}
			BungeeCord.getInstance().getLogger().info(Utils.Chat(output));
		}
		return;
	}
	
	public static void error(String Chinese,String Chinese_tail,String English,String English_tail) {
		String output="&dOCB > &4";
		if(Utils.language == 1) {
			output += Chinese;
		} else {
			output += English;
		}
		if(Utils.logmode >= 3) {
			output += " | &c";
			if(Utils.language == 1) {
				output += Chinese_tail;
			} else {
				output += English_tail;
			}
		}
		BungeeCord.getInstance().getLogger().warning(Utils.Chat(output));
		return;
	}
	
	public static String translate(String Chinese,String English) {
		if(language == 1) {
			return Chinese;
		} else {
			return English;
		}
	}
	
	public static String Chat(String a) {
		return a.replaceAll("&", "§");
	}
	
	public static String noperm() {
		if(language == 1) {
			return "§4你没有权限执行这个命令";
		} else {
			return "§4You must be operater to run this command.";
		}
	}
	
	//CFG vars
	public static int version=0,language=0,logmode=0;
	public static boolean rewritelog=true,writeonlinebasic=true,addonlinelog=true,writeonlinegraph=true,writeonlinepro=true,lowperformance=false;
	public static String localpath=null,onlinepath=null;
	public static String online_graph_ctx_font_1="20px MicroSoft YaHei",online_graph_ctx_fillStyle_1="#00aaff",
						 online_graph_ctx_fillStyle_2="#ffffff",online_graph_ctx_font_2="15px Arial",online_graph_ctx_strokeStyle_2="#cccccc";
	public static boolean cfgstats=false;
	public static boolean readcfg() {
		//Stage 1:read config
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\ocb.cfg")), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\ocb.cfg")), "ANSI"));
			} catch (UnsupportedEncodingException e1) {
				BungeeCord.getInstance().getLogger().warning(Chat("&dOCB>config>&4Stage 1: Read config FAILED: &cCONFIG ENCODING UNKNOWN! USE UTF-8 OR LEARN HOW TO CONFIG OCB AT https://github.com/GARBAGELINUXGRAPHIC/Online-player-check"));
				e1.printStackTrace();
				cfgstats=false;
				return false;
			} catch (FileNotFoundException e1) {
				BungeeCord.getInstance().getLogger().warning(Chat("&dOCB>config>&4Stage 1: Read config FAILED: &cCONFIG NOT FOUND! LEARN HOW TO CONFIG OCB AT https://github.com/GARBAGELINUXGRAPHIC/Online-player-check"));
				e.printStackTrace();
				cfgstats=false;
				return false;
			}
		} catch (FileNotFoundException e) {
			BungeeCord.getInstance().getLogger().warning(Chat("&dOCB>config>&4Stage 1: Read config FAILED: &cCONFIG NOT FOUND! LEARN HOW TO CONFIG OCB AT https://github.com/GARBAGELINUXGRAPHIC/Online-player-check"));
			e.printStackTrace();
			cfgstats=false;
			return false;
		}
		//Stage 2.1 read version of config
		String STRversion=null;
		try {
			STRversion = br.readLine();
		} catch (IOException e) {
			BungeeCord.getInstance().getLogger().warning(Chat("&dOCB>config>&4Stage 2.1: Read config version FAILED: &cLEARN HOW TO CONFIG OCB AT https://github.com/GARBAGELINUXGRAPHIC/Online-player-check"));
			e.printStackTrace();
			cfgstats=false;
			return false;
		}
		version = Integer.valueOf(STRversion);
		if(version==1) {//CORRECT VERSION
			//Stage 2.2 read language
			String STRlanguage=null;
			try {
				STRlanguage = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				BungeeCord.getInstance().getLogger().warning(Chat("&dOCB>config>&4Stage 2.2 read language failed. &cUsing Default value(ENGLISH)."));
			}
			language=Integer.valueOf(STRlanguage);
			//Stage 2.3 read log mode
			String STRlogmode=null;
			try {
				STRlogmode = br.readLine();
			} catch (IOException e) {
				error("读取log输出模式失败,使用默认值",
						"默认值为" + logmode + ",详细信息: " + e,
					"Read log output mode failed, Using default value",
					"Default value: " + logmode + ",details: " + e);
			}
			logmode=Integer.valueOf(STRlogmode);
			//Stage 2.4 read file path
			try {
				localpath = br.readLine();
			} catch (IOException e) {
				error("读取本地路径失败,致命错误!",
						"详细信息: " + e,
						"Read local file path failed, FATAL!",
						"details: " + e);
				cfgstats=false;
				return false;
			}
			//Stage 2.5 read url path
			try {
				onlinepath = br.readLine();
			} catch (IOException e) {
				error("读取网页路径失败,致命错误!",
						"详细信息: " + e,
						"Read url path failed, FATAL!",
						"details: " + e);
				cfgstats=false;
				return false;
			}
			//Stage 2.6 read rewrite log
			String STRrewritelog=null;
			try {
				STRrewritelog = br.readLine();
			} catch (IOException e) {
				error("读取是否重写log失败,使用默认值",
						"默认值为" + rewritelog + ",详细信息: " + e,
						"Read rewrite log mode failed, Using default value",
						"Default value: " + rewritelog + ",details: " + e);
			}
			int INTrewritelog=0;
			INTrewritelog = Integer.valueOf(STRrewritelog);
			if(INTrewritelog==1) {
				rewritelog=true;
			} else if (INTrewritelog==2) {
				rewritelog=false;
			}
			//Stage 2.7 read write online basic
			String STRwriteonlonebasic=null;
			try {
				STRwriteonlonebasic = br.readLine();
			} catch (IOException e) {
				error("读取是否写入online_basic失败,使用默认值",
					"默认值为" + writeonlinebasic + ",详细信息: " + e,
					"Read write online basic failed, Using default value",
					"Default value: " + writeonlinebasic + ",details: " + e);
			}
			int INTwriteonlonebasic=0;
			INTwriteonlonebasic = Integer.valueOf(STRwriteonlonebasic);
			if(INTwriteonlonebasic==1) {
				writeonlinebasic=true;
			} else if (INTwriteonlonebasic==2) {
				writeonlinebasic=false;
			}
			//Stage 2.8 read add online log
			String STRaddonlinelog=null;
			try {
				STRaddonlinelog = br.readLine();
			} catch (IOException e) {
				error("读取是否允许日志失败，使用默认值" + addonlinelog,
						e.toString(),
						"Read enable add online log failed, using default value" + addonlinelog,
						e.toString());
			}
			int INTaddonlinelog=0;
			INTaddonlinelog = Integer.valueOf(STRaddonlinelog);
			if(INTaddonlinelog==1) {
				addonlinelog=true;
			} else if (INTaddonlinelog==2) {
				addonlinelog=false;
			}
			//Stage 2.9 read write online graph
			String STRwriteonlonegraph=null;
			try {
				STRwriteonlonegraph = br.readLine();
			} catch (IOException e) {
				error("读取是否启用online graph失败，使用默认值" + writeonlinegraph,
						e.toString(),
						"Read enable online graph failed, using default value" + writeonlinegraph,
						e.toString());
			}
			int INTwriteonlonegraph=0;
			INTwriteonlonegraph = Integer.valueOf(STRwriteonlonegraph);
			if(INTwriteonlonegraph==1) {
				writeonlinegraph=true;
			} else if (INTwriteonlonegraph==2) {
				writeonlinegraph=false;
			}
			//Stage 2.10 read write online pro
			String STRwriteonlonepro=null;
			try {
				STRwriteonlonepro = br.readLine();
			} catch (IOException e) {
				error("读取是否启用online pro失败，使用默认值" + writeonlinepro,
						e.toString(),
						"Read enable online pro failed, using default value" + writeonlinepro,
						e.toString());
			}
			int INTwriteonlonepro=0;
			INTwriteonlonepro = Integer.valueOf(STRwriteonlonepro);
			if(INTwriteonlonepro==1) {
				writeonlinepro=true;
			} else if (INTwriteonlonepro==2) {
				writeonlinepro=false;
			}
			//Stage 2.11 read low performance mode
			String STRlowperformance=null;
			try {
				STRlowperformance = br.readLine();
			} catch (IOException e) {
				error("读取是否启用低性能模式失败，使用默认值" + lowperformance,
						e.toString(),
						"Read low performace mode failed, using default value" + lowperformance,
						e.toString());
			}
			int INTlowperformance=0;
			INTlowperformance = Integer.valueOf(STRlowperformance);
			if(INTlowperformance==1) {
				lowperformance=true;
			} else if (INTlowperformance==2) {
				lowperformance=false;
			}
			//READ CONFIG DONE
			//READ ONLINE GRAPH CONFIG
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(localpath + "online\\online_graph_ctx_settings.cfg")), "UTF-8"));
				try {
					online_graph_ctx_font_1 = br.readLine();
				} catch (IOException e) {
					error("读取图表设置失败,使用默认值",
							"online_graph_ctx_font_1" + e,
							"Read online graph settings failed,using default value",
							"online_graph_ctx_font_1" + e);
				}
				try {
					online_graph_ctx_fillStyle_1 = br.readLine();
				} catch (IOException e) {
					error("读取图表设置失败,使用默认值",
							"online_graph_ctx_fillStyle_1" + e,
							"Read online graph settings failed,using default value",
							"online_graph_ctx_fillStyle_1" + e);
				}
				try {
					online_graph_ctx_fillStyle_2 = br.readLine();
				} catch (IOException e) {
					error("读取图表设置失败,使用默认值",
							"online_graph_ctx_fillStyle_2" + e,
							"Read online graph settings failed,using default value",
							"online_graph_ctx_fillStyle_2" + e);
				}
				try {
					online_graph_ctx_font_2 = br.readLine();
				} catch (IOException e) {
					error("读取图表设置失败,使用默认值",
							"online_graph_ctx_font_2" + e,
							"Read online graph settings failed,using default value",
							"online_graph_ctx_font_2" + e);
				}
				try {
					online_graph_ctx_strokeStyle_2 = br.readLine();
				} catch (IOException e) {
					error("读取图表设置失败,使用默认值",
							"online_graph_ctx_font_2" + e,
							"Read online graph settings failed,using default value",
							"online_graph_ctx_font_2" + e);
				}
			} catch (UnsupportedEncodingException e) {
				error("读取图表设置失败",
						"编码不是UTF-8",
						"Read online graph settings failed",
						"Encoding is not UTF-8");
			} catch (FileNotFoundException e) {
				error("读取图表设置失败",
						"无法找到文件" + localpath + "online\\online_graph_ctx_settings.cfg",
						"Read online graph settings failed",
						"Can not find " + localpath + "online\\online_graph_ctx_settings.cfg");
			}
			cfgstats=true;
			return true;
			
		} else if(version<1) {
			BungeeCord.getInstance().getLogger().warning(Chat("&dOCB>config>&4Stage 2.1: Read config version FAILED: &cVERSION IS TOO OLD, https://github.com/GARBAGELINUXGRAPHIC/Online-player-check"));
			cfgstats=false;
			return false;
		} else if(version>1) {
			BungeeCord.getInstance().getLogger().warning(Chat("&dOCB>config>&4Stage 2.1: Read config version FAILED: &cVERSION IS TOO NEW, https://github.com/GARBAGELINUXGRAPHIC/Online-player-check"));
			cfgstats=false;
			return false;
		} else {
			BungeeCord.getInstance().getLogger().warning(Chat("&dOCB>config>&4Stage 2.1: Read config version FAILED: &cVERSION IS UNKNOWN, https://github.com/GARBAGELINUXGRAPHIC/Online-player-check"));
			cfgstats=false;
			return false;
		}
		
		
	}
	
	public static boolean check_max_player_count() {
		if(writeonlinepro == true) {
			if((Utils.get_max_player_count() < Utils.get_online_player_count())&& Utils.writeonlinegraph == true) {
				Utils.log("&a服务器玩家新数量: &9" + Utils.get_online_player_count() + " 人!","&f重写新最高玩家数"
						,"&aNew max player count: &9" + Utils.get_online_player_count() + " players!","&fRewrite new max online count");
				Utils.write_max_player_count();
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	public static int get_online_player_count() {
		return BungeeCord.getInstance().getOnlineCount();
	}
	
	public static boolean write_online_basic() {
		if(writeonlinebasic == true) {
			
		if(logmode >= 2) {
			Utils.log("重写online basic","当前人数:" + Utils.get_online_player_count(),
					"Rewrite online basic","Current online:" + Utils.get_online_player_count());
		}
			
		try(/*FileWriter fw = new FileWriter("C:\\phpstudy_pro\\WWW\\online\\online_basic.html", false)*/
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(localpath + "online\\online_basic.html", false),"UTF-8");){
	        fw.write( "<!AUTO GENERATED BY OCB>\r\n"
	        		+ "<!SHARKMC.CN>\r\n"
	        		+ "<!DOCTYPE html>\n"
	        		+ "<html lang=\"zh-cn\">\n"
	        		+ "<head>\n"
	        		+ "    <meta charset=\"UTF-8\">\n"
	        		+ "    <title>Online count basic</title>\n"
					+ "    <link rel=\"icon\" href=\"http://sharkmc.cn/img/shark.png\" type=\"image/x-icon\">\n"
	        		+ "    <link rel=\"stylesheet\" type=\"text/css\" href=\"online_basic.css\">\n"
	        		+ "</head>\n"
	        		+ "<body>\n"
	        		+ "<a href=\"http://SharkMC.cn/online/\" target=\"_blank\" id=\"link\"><strong id=\"text\">Current online: " + Utils.get_online_player_count() + "</strong></a>\n"
	        		+ "</body>\n"
	        		+ "</html>");
	        
	        fw.flush();
	        return true;
	    }catch(IOException e) {
	        error("写入online basic失败",
	        		e.toString(),
	        		"Write online basic failed",
	        		e.toString());
	    }
		return false;
		} else {
			if(logmode >= 2) {
				log("未重写online basic","ocb.cfg/writeonlinebasic=" + writeonlinebasic,
						"Skip write online basic","ocb.cfg/writeonlinebasic=" + writeonlinebasic);
			}
			return false;
		}
	}

	public static boolean write_online_pro() {
		if(writeonlinepro == true) {
			
		if(logmode >= 2) {
			Utils.log("重写online_pro"
					,"当前玩家数:" + Utils.get_online_player_count() + 
								";最大玩家数:" + Utils.get_max_player_count() + 
								";平均玩家数:" + Utils.get_average_player_count() +
								";玩家序列:" + BungeeCord.getInstance().getPlayers()
					,"Rewrite online pro",
							"Current:" + Utils.get_online_player_count() + 
							";Max:" + Utils.get_max_player_count() + 
							";Average:" + Utils.get_average_player_count() +
							";List:" + BungeeCord.getInstance().getPlayers()						);
		}
			
		try(/*FileWriter fw = new FileWriter("C:\\phpstudy_pro\\WWW\\online\\online_pro.html", false)*/
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(localpath + "online\\online_pro.html", false),"UTF-8");){
			String output="";
			output += "<!AUTO GENERATED BY OCB>\n"
					+ "<!SHARKMC.CN>\n"
					+ "<!DOCTYPE html>\n"
					+ "<html lang=\"zh\" xmlns=\"http://www.w3.org/1999/html\">\n"
					+ "<head>\n"
					+ "    <meta charset=\"UTF-8\">\n"
					+ "    <title>Online count pro</title>\n"
					+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n"
					+ "    <link rel=\"icon\" href=\"http://sharkmc.cn/img/shark.png\" type=\"image/x-icon\">\n"
					+ "    <meta name=\"referrer\" content=\"no-referrer\">\n"
					+ "    <link rel=\"stylesheet\" type=\"text/css\" href=\"online_pro.css\">\n"
					+ "</head>\n"
					+ "<body>\n";
					
					if(onlinepath.equalsIgnoreCase("http://sharkmc.cn/")) {
		    			output += "<iframe src=\"http://sharkmc.cn/top.html\" style=\"height: 120px;width: 101%;margin-left: -10px;background: #4257b2;position: fixed;top: 0;margin-bottom: 5px;\" scrolling=\"no\" frameborder=\"0\"></iframe>\r\n"
		    	        		+ "\r\n"
		    	        		+ "<p>\r\n"
		    	        		+ "    <br>\r\n"
		    	        		+ "    <br>\r\n"
		    	        		+ "    <br>\r\n"
		    	        		+ "    <br>\r\n"
		    	        		+ "    <br>\r\n"
		    	        		+ "</p>\r\n";
		    		}
					
					output += "<!MAIN><center>\n"
					+ "    <div id=\"box\" style=\"width: 500px;margin-right: 1000px;position: relative\">\n"
					+ "        <div id=\"CurrentOnline\">\n"
					+ "            <h1>" + Utils.get_online_player_count() + "</h1>\n"
					+ "            <p>" + translate("当前在线人数","Current") + "</p>\r\n"
					+ "        </div>\n"
					+ "        <div id=\"MaxOnline\">\n"
					+ "            <h1>" + Utils.get_max_player_count() + "</h1>\n"
					+ "            <p>" + translate("历史最大人数","Max") + "</p>\r\n"
					+ "        </div>\n"
					+ "        <div id=\"AverageOnline\">\n"
					+ "            <h1>" + Utils.get_average_player_count() + "</h1>\n"
					+ "            <p>" + translate("平均在线人数","Average") + "</p>\r\n"
					+ "        </div>\n"
					+ "        <div id=\"OnlineLog\">\n"
					+ "            <iframe src=\"online_log.html\" width=\"100%\" height=\"100%\" frameborder=\"0\" allowtransparency=\"true\"></iframe>\n"
					+ "        </div>\n"
					+ "    </div>\n"
					+ "\n"
					+ "    <br>\n"
					+ "    <div id=\"OnlineGraph\">\n"
					+ "        <iframe src=\"online_graph.html\" scrolling=\"no\" frameborder=\"0\" ></iframe>\n"
					+ "    </div>\n"
					+ "    <br>\n"
					+ "    <div id=\"OnlineList\">\n"
					+ "        <p id=\"OnlineListText\"><strong id=\"OnlineListTopic\">" + translate("在线玩家","Online list") + ": </strong> " + BungeeCord.getInstance().getPlayers() + "</p>\r\n"
					+ "    </div>\n"
					+ "    <br>\n"
					+ "    <div id=\"Copyright\">\n"
					+ "        <b id=\"NormalText\">" + translate("插件","Powered by") + " </b><b id=\"PluginName\">Online Check(BungeeCordVer)</b>\n"
					+ "        <b id=\"NormalText\"> " + translate("作者","by") + " </b><a href=\"http://Sharkmc.cn\" style=\"text-decoration: none;\"><b id=\"Link\">Sharkmc.cn</b></a>\n"
					+ "\n"
					+ "        <br>\n"
					+ "        \n"
					+ "        <b id=\"NormalText\">" + translate("尝试","Try") + " </b><b id=\"PluginCommand\">/ocb </b>\n"
					+ "        <b id=\"NormalText\">" + translate("指令!","in game!") + "</b>\n"
					+ "    </div>\n"
					+ "</center>\n"
					+ "<!MAIN>\n"
					+ "</body>\n"
					+ "</html>";
			fw.write(output);
			
	        fw.flush();
	        return true;
	    }catch(IOException e) {
	        error("写入online pro失败",e.toString(),
	        		"Write online pro failed",e.toString());
	    }
		return false;
		} else {
			if(logmode >= 2) {
			log("未重写online pro","ocb.cfg/writeonlinepro=" + writeonlinepro,
					"Skip write online pro","ocb.cfg/writeonlinepro=" + writeonlinepro);
			}
			return false;
		}
	}
	
	public static double get_average_player_count() {
		double avg = 0;
		int a = 0;
		for(int i = 0;i <= 23;i++) {
			a += L(i);
		}
		avg = a;
		avg = avg / 24;
		return avg;
	}
	public static boolean clear_file(String path) {
		File file=new File(path);
        if(file.isFile()) {
            file.delete();
            try {
				file.createNewFile();
	        	return true;
			} catch (IOException e) {
				error("无法重建" + path,e.toString(),
						"Can not create file " + path + " after delete",e.toString());
				return false;
			}
        } else {
        	try {
				file.createNewFile();
	        	return true;
			} catch (IOException e) {
				error("无法找到文件" + path,e.toString(),
						"Can not find file " + path,e.toString());
				return false;
			}
        }
	}
	
	public static boolean verify_file(String path) {
		File file=new File(path);
        if(file.isFile()) {
            return true;
        } else {
			return false;
        }
	}
	
	public static boolean write_online_log() {
		if(rewritelog == true) {
			if(logmode >= 2) {
				Utils.log("重写log","","Rewrite online log","");
			}
		String output="";
		output =  "<!DOCTYPE html>\r\n"
        		+ "<!AUTO GENERATED BY OCB>\r\n"
        		+ "<!SHARKMC.CN>\r\n"
        		+ "<html lang=\"zh-cn\">\r\n"
        		+ "<head>\r\n"
        		+ "    <meta charset=\"UTF-8\">\r\n"
        		+ "    <title>Online log</title>\r\n"
        		+ "    <link rel=\"stylesheet\" type=\"text/css\" href=\"online_log.css\">"
        		+ "</head>\r\n"
        		+ "<body>\r\n";
		if(addonlinelog == false) {
			if(language == 1) {
				output += "<p style=\"color:#ff5656\">Log is currently disabled by admin.</p>\r\n"
						+ "</body></html>";
			}
		}
		try(
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(localpath + "online\\online_log.html", false),"UTF-8");){
	        fw.write(output);
	        
	        fw.flush();
	        return true;
	    }catch(IOException e) {
			error("无法写入online log",e.toString(),
					"Can not write online log",e.toString());
	    }
		return false;
		} else {
			if(logmode >= 2) {
				log("未重写online log","ocb.cfg/rewritelog=" + rewritelog,
						"Skip rewrite online log","ocb.cfg/rewritelog=" + rewritelog);
			}
			return false;
		}
	}
	
	public static boolean add_online_log_strong(String chinese,String english) {
		if(addonlinelog == true) {
		SimpleDateFormat time=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Date now=new Date();
		String info = null;
		if(language == 1) {
			info = chinese;
		} else {
			info = english;
		}
		if(logmode >= 2) {
			Utils.log("增加log","内容:&5" + time.format(now) + "&6 " + info,
					"Add log","Content:&5" + time.format(now) + "&6 " + info);
		}
		try(OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(localpath + "online\\online_log.html", true),"UTF-8");){
			fw.write("<p id=\"strong_default\"><strong id=\"strong_time\">" + time.format(now) + "</strong><strong id=\"strong_text\"> " + info + "</strong></p>");
			return true;
		}   catch(IOException e) {
			error("无法增加online log \"" + info + "\"",e.toString(),
					"Can not add online log \"" + info + "\"",e.toString());
			return false;
		}
		} else {
			if(logmode >= 2) {
				log("未增加online log","ocb.cfg/addonlinelog=" + addonlinelog,
					"Skip add online log","ocb.cfg/addonlinelog=" + addonlinelog);
			}
			return false;			
		}
	}
	
	public static boolean add_online_log_normal(String chinese,String english) {
		if(addonlinelog == true) {
		SimpleDateFormat time=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Date now=new Date();
		String info = null;
		if(language == 1) {
			info = chinese;
		} else {
			info = english;
		}
		if(logmode >= 2) {
			Utils.log("增加log","内容:&5" + time.format(now) + "&f " + info,
					"Add log","Content:&5" + time.format(now) + "&f " + info);
		}
		try(OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(localpath + "online\\online_log.html", true),"UTF-8");){
			fw.write("<p id=\"normal_default\"><strong id=\"normal_time\">" + time.format(now) + "</strong><strong id=\"normal_text\"> " + info + "</strong></p>");
			return true;
		}   catch(IOException e) {
			error("无法增加online log \"" + info + "\"",e.toString(),
					"Can not add online log \"" + info + "\"",e.toString());
			return false;
		}
		} else {
			if(logmode >= 2) {
				log("未增加online log","ocb.cfg/addonlinelog=" + addonlinelog,
					"Skip add online log","ocb.cfg/addonlinelog=" + addonlinelog);
			}
			return false;
		}
	}
	
	public static boolean add_online_log_weak(String chinese,String english) {
		if(addonlinelog == true) {
		SimpleDateFormat time=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Date now=new Date();
		String info = null;
		if(language == 1) {
			info = chinese;
		} else {
			info = english;
		}
		if(logmode >= 2) {
			Utils.log("增加log","内容:&5" + time.format(now) + "&8 " + info,
					"Add log","Content:&5" + time.format(now) + "&8 " + info);
		}
		try(OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(localpath + "online\\online_log.html", true),"UTF-8");){
			fw.write("<p id=\"weak_default\"><strong id=\"weak_time\">" + time.format(now) +  "</strong><strong id=\"weak_text\"> " + info + "</strong></p>");
			return true;
		}   catch(IOException e) {
			error("无法增加online log \"" + info + "\"",e.toString(),
					"Can not add online log \"" + info + "\"",e.toString());
			return false;
		}
		} else {
			if(logmode >= 2) {
				log("未增加online log","ocb.cfg/addonlinelog=" + addonlinelog,
					"Skip add online log","ocb.cfg/addonlinelog=" + addonlinelog);
			}
			return false;
		}
	}
	
	public static boolean add_online_log_player(String playername,boolean joinstatus,String target) {
		if(addonlinelog == true) {
		SimpleDateFormat time=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Date now=new Date();
		if(logmode >= 2) {
			if(joinstatus == true) {
				Utils.log("玩家加入,增加log"
						,"内容:" + time.format(now) + 
						 " " + playername + 
						 " joined server " + target
						,"Player joined, adding log"
						,"Content: " + time.format(now) +
						 " " + playername +
						 " joined server " + target);
			} else {
				Utils.log("玩家离开,增加log"
						,"内容:" + time.format(now) + 
						 " " + playername + 
						 " left"
						,"Player left, add log"
						,"Content: " + time.format(now) +
						 " " + playername +
						 " left");
			}
		}
		try(OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(localpath + "online\\online_log.html", true),"UTF-8");){
			if(joinstatus == true)
				fw.write("<p id=\"true_default\"><strong id=\"true_time\">" + time.format(now) + " </strong> <strong id=\"true_player\">" + playername + "</strong> <b id=\"join_text\">joined</b> <b id=\"server_text\">server</b> <b id=\"server_name\">" + target + "</b></p>\n");
			else
				fw.write("<p id=\"false_default\"><strong id=\"false_time\">" + time.format(now) + " </strong> <strong id=\"false_player\">" + playername + "</strong> <b id=\"left_text\">left</b></p>\n");
			return true;
		}   catch(IOException e) {
			error("无法增加online log",e.toString(),
					"Can not add online log",e.toString());
			return false;
		}
		} else {
			if(logmode >= 2) {
				log("未增加online log","ocb.cfg/addonlinelog=" + addonlinelog,
					"Skip add online log","ocb.cfg/addonlinelog=" + addonlinelog);
			}
			return false;
		}
	}
	
	public static boolean disable_online_graph() {
		if(logmode >= 2) {
			log("正在禁用online graph","C:/ocb.cfg/writeonlinegraph=false"
					,"Disabling online graph","C:/ocb.cfg/writeonlinegraph=false");
		}
		try(OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(localpath + "online\\online_graph.html", false),"UTF-8");){
			fw.write("<!DOCTYPE html>\r\n"
					+ "<!AUTO GENERATED BY OCB>\r\n"
	        		+ "<!SHARKMC.CN>\r\n"
					+ "<html lang=\"zh-cn\">\r\n"
					+ "<head>\r\n"
					+ "    <meta charset=\"UTF-8\">\r\n"
					+ "    <title>Online Graph</title>\r\n"
					+ "</head>\r\n"
					+ "<body style=\"background-color: black;margin-left: 0;margin-top: 0;\">\r\n"
					+ "<p style=\"color:#ff5656\">Graph is currently disabled by admin.</h1>\r\n"
					+ "</body></html\r\n");
			return true;
		} catch(IOException e) {
			error("禁用online graph失败",e.toString(),
					"Disable online graph failed",e.toString());
			return false;
		}
	}
	
	public static boolean write_online_graph() {
		if(logmode >= 2) {
			log("正在写入online graph","C:/ocb.cfg/writeonlinegraph=true"
					,"Writing online graph","C:/ocb.cfg/writeonlinegraph=true");
		}
		if(writeonlinegraph==true) {
		Calendar c = Calendar.getInstance();
		int h = c.get(Calendar.HOUR_OF_DAY);
		try(OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(localpath + "online\\online_graph.html", false),"UTF-8");){
			fw.write("<!DOCTYPE html>\r\n"
					+ "<!AUTO GENERATED BY OCB>\r\n"
	        		+ "<!SHARKMC.CN>\r\n"
					+ "<html lang=\"zh-cn\">\r\n"
					+ "<head>\r\n"
					+ "    <meta charset=\"UTF-8\">\r\n"
					+ "    <title>Online Graph</title>\r\n"
					+ "    <link rel=\"icon\" href=\"http://sharkmc.cn/img/shark.png\" type=\"image/x-icon\">\n"
					+ "    <link rel=\"stylesheet\" type=\"text/css\" href=\"online_graph.css\">"
					+ "</head>\r\n"
					+ "<body>\r\n"
					+ "<canvas id=\"og\" width=\"1500px\" height=\"260px\"></canvas>\r\n"
					+ "<script>\r\n"
					+ "    let c=document.getElementById(\"og\");\r\n"
					+ "    let ctx=c.getContext(\"2d\");\r\n"
					+ "    var native_t=24-" + h + ";var t=native_t;var r=0;\r\n"
					+ "    ctx.strokeStyle = '#aaaaaa';\r\n"
					+ "    ctx.lineWidth = 2.0;\r\n"
					+ "    /*|*/\r\n"
					+ "    ctx.moveTo(30,20);\r\n"
					+ "    ctx.lineTo(30,220);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    /*-*/\r\n"
					+ "    ctx.moveTo(30,220);\r\n"
					+ "    ctx.lineTo(1470,220);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    /*---*/\r\n"
					+ "    ctx.lineWidth = 1.0;\r\n"
					+ "    ctx.moveTo(30,20);\r\n"
					+ "    ctx.lineTo(1470,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(30,60);\r\n"
					+ "    ctx.lineTo(1470,60);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(30,100);\r\n"
					+ "    ctx.lineTo(1470,100);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(30,140);\r\n"
					+ "    ctx.lineTo(1470,140);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(30,180);\r\n"
					+ "    ctx.lineTo(1470,180);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    /*|||*/\r\n"
					+ "    ctx.moveTo(90,220);\r\n"
					+ "    ctx.lineTo(90,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(150,220);\r\n"
					+ "    ctx.lineTo(150,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(210,220);\r\n"
					+ "    ctx.lineTo(210,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(270,220);\r\n"
					+ "    ctx.lineTo(270,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(330,220);\r\n"
					+ "    ctx.lineTo(330,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(390,220);\r\n"
					+ "    ctx.lineTo(390,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(450,220);\r\n"
					+ "    ctx.lineTo(450,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(510,220);\r\n"
					+ "    ctx.lineTo(510,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(570,220);\r\n"
					+ "    ctx.lineTo(570,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(630,220);\r\n"
					+ "    ctx.lineTo(630,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(690,220);\r\n"
					+ "    ctx.lineTo(690,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(750,220);\r\n"
					+ "    ctx.lineTo(750,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(810,220);\r\n"
					+ "    ctx.lineTo(810,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(870,220);\r\n"
					+ "    ctx.lineTo(870,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(930,220);\r\n"
					+ "    ctx.lineTo(930,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(990,220);\r\n"
					+ "    ctx.lineTo(990,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(1050,220);\r\n"
					+ "    ctx.lineTo(1050,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(1110,220);\r\n"
					+ "    ctx.lineTo(1110,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(1170,220);\r\n"
					+ "    ctx.lineTo(1170,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(1230,220);\r\n"
					+ "    ctx.lineTo(1230,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(1290,220);\r\n"
					+ "    ctx.lineTo(1290,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(1350,220);\r\n"
					+ "    ctx.lineTo(1350,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(1410,220);\r\n"
					+ "    ctx.lineTo(1410,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    ctx.moveTo(1470,220);\r\n"
					+ "    ctx.lineTo(1470,20);\r\n"
					+ "    ctx.stroke();\r\n"
					+ "    /**/\r\n"
					+ "    ctx.font=\"" + online_graph_ctx_font_1 + "\";\r\n"
					+ "    ctx.fillStyle = ('" + online_graph_ctx_fillStyle_1 + "')\r\n"
					+ "    /*左侧数字*/\r\n"
					+ "    ctx.fillText(\"20\",1,30);\r\n"
					+ "    ctx.fillRect(31,19,5,3);\r\n"
					+ "    ctx.fillText(\"16\",1,70);\r\n"
					+ "    ctx.fillRect(31,59,5,3);\r\n"
					+ "    ctx.fillText(\"12\",1,110);\r\n"
					+ "    ctx.fillRect(31,99,5,3);\r\n"
					+ "    ctx.fillText(\" 8\",1,150);\r\n"
					+ "    ctx.fillRect(31,139,5,3);\r\n"
					+ "    ctx.fillText(\" 4\",1,190);\r\n"
					+ "    ctx.fillRect(31,179,5,3);\r\n"
					+ "    ctx.fillText(\" 0\",1,230);\r\n"
					+ "    /*下方数字*/\r\n"
					+ "    t = native_t;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"0\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"1\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"2\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"3\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"4\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"5\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"6\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"7\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"8\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"9\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"10\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"11\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"12\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"13\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"14\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"15\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"16\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"17\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"18\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"19\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"20\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"21\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"22\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"23\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    r=t*60;while(1){if(r>1440){r-=1500}else{break;}}ctx.fillText(\"24\",r+25,250);ctx.fillRect(r+29,215,3,5);t++;\r\n"
					+ "    t = native_t;\r\n"
					+ "    /*\r\n"
					+ "    * 30+time*60=time dot(x)\r\n"
					+ "    * 220-people*10=player dot(y)\r\n"
					+ "    * 0,0->30,20\r\n"
					+ "    * max,max->1470,220\r\n"
					+ "    */\r\n"
					+ "    /*LINEDROW*/\r\n"
					+ "            var p0=" + L(0) + ";\r\n"
					+ "            var p1=" + L(1) + ";\r\n"
					+ "            var p2=" + L(2) + ";\r\n"
					+ "            var p3=" + L(3) + ";\r\n"
					+ "            var p4=" + L(4) + ";\r\n"
					+ "            var p5=" + L(5) + ";\r\n"
					+ "            var p6=" + L(6) + ";\r\n"
					+ "            var p7=" + L(7) + ";\r\n"
					+ "            var p8=" + L(8) + ";\r\n"
					+ "            var p9=" + L(9) + ";\r\n"
					+ "            var p10=" + L(10) + ";\r\n"
					+ "            var p11=" + L(11) + ";\r\n"
					+ "            var p12=" + L(12) + ";\r\n"
					+ "            var p13=" + L(13) + ";\r\n"
					+ "            var p14=" + L(14) + ";\r\n"
					+ "            var p15=" + L(15) + ";\r\n"
					+ "            var p16=" + L(16) + ";\r\n"
					+ "            var p17=" + L(17) + ";\r\n"
					+ "            var p18=" + L(18) + ";\r\n"
					+ "            var p19=" + L(19) + ";\r\n"
					+ "            var p20=" + L(20) + ";\r\n"
					+ "            var p21=" + L(21) + ";\r\n"
					+ "            var p22=" + L(22) + ";\r\n"
					+ "            var p23=" + L(23) + ";\r\n"
					+ "            var p24=" + L(0) + ";\r\n"
					+ "    ctx.lineWidth=1.0;\r\n"
					+ "    t=native_t;r=0;\r\n"
					+ "    ctx.fillStyle = ('" + online_graph_ctx_fillStyle_2 + "')\r\n"
					+ "    ctx.font=\"" + online_graph_ctx_font_2 + "\";\r\n"
					+ "    ctx.strokeStyle = ('" + online_graph_ctx_strokeStyle_2 + "')\r\n"
					+ "    var d=0;\r\n"
					+ "    r=t*60;\r\n"
					+ "    if(p0!==-1) {\r\n"
					+ "        if(p0>20) {\r\n"
					+ "            ctx.moveTo(30+r,220-20*10);\r\n"
					+ "            ctx.stroke();\r\n"
					+ "            ctx.fillText(p0.toString(),30+r,220-20*10);\r\n"
					+ "        } else {\r\n"
					+ "            ctx.moveTo(30+r,220-p0*10);\r\n"
					+ "            ctx.stroke();\r\n"
					+ "            ctx.fillText(p0.toString(),30+r,220-p0*10);\r\n"
					+ "        }\r\n"
					+ "    }\r\n"
					+ "    if(t===24) {\r\n"
					+ "        t=0;\r\n"
					+ "    } else {\r\n"
					+ "        t++;\r\n"
					+ "    }\r\n"
					+ "\r\n"
					+ "    r=t*60;\r\n"
					+ "    if(d===1) {\r\n"
					+ "        if(p1!==-1) {\r\n"
					+ "            if(p1>20) {\r\n"
					+ "                ctx.moveTo(30+r,220-20*10);\r\n"
					+ "                ctx.stroke();\r\n"
					+ "                ctx.fillText(p1.toString(),30+r,220-20*10);\r\n"
					+ "            } else {\r\n"
					+ "                ctx.moveTo(30+r,220-p1*10);\r\n"
					+ "                ctx.stroke();\r\n"
					+ "                ctx.fillText(p1.toString(),30+r,220-p1*10);\r\n"
					+ "            }\r\n"
					+ "        }\r\n"
					+ "        d=0;\r\n"
					+ "    } else {\r\n"
					+ "        if(p1!==-1) {\r\n"
					+ "            if(p1>20) {\r\n"
					+ "                ctx.lineTo(30+r,220-20*10);\r\n"
					+ "                ctx.stroke();\r\n"
					+ "                ctx.fillText(p1.toString(),30+r,220-20*10);\r\n"
					+ "            } else {\r\n"
					+ "                ctx.lineTo(30+r,220-p1*10);\r\n"
					+ "                ctx.stroke();\r\n"
					+ "                ctx.fillText(p1.toString(),30+r,220-p1*10);\r\n"
					+ "            }\r\n"
					+ "        }\r\n"
					+ "    }\r\n"
					+ "    if(t===24) {\r\n"
					+ "        t=0;\r\n"
					+ "        d=1;\r\n"
					+ "    } else {\r\n"
					+ "        t++;\r\n"
					+ "    }\r\n"
					+ "    r=t*60;if(d===1){if(p2!==-1){if(p2>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p2.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p2*10);ctx.stroke();ctx.fillText(p2.toString(),30+r,220-p2*10);}}d=0;}else{if(p2!==-1){if(p2>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p2.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p2*10);ctx.stroke();ctx.fillText(p2.toString(),30+r,220-p2*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p3!==-1){if(p3>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p3.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p3*10);ctx.stroke();ctx.fillText(p3.toString(),30+r,220-p3*10);}}d=0;}else{if(p3!==-1){if(p3>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p3.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p3*10);ctx.stroke();ctx.fillText(p3.toString(),30+r,220-p3*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p4!==-1){if(p4>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p4.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p4*10);ctx.stroke();ctx.fillText(p4.toString(),30+r,220-p4*10);}}d=0;}else{if(p4!==-1){if(p4>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p4.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p4*10);ctx.stroke();ctx.fillText(p4.toString(),30+r,220-p4*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p5!==-1){if(p5>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p5.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p5*10);ctx.stroke();ctx.fillText(p5.toString(),30+r,220-p5*10);}}d=0;}else{if(p5!==-1){if(p5>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p5.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p5*10);ctx.stroke();ctx.fillText(p5.toString(),30+r,220-p5*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p6!==-1){if(p6>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p6.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p6*10);ctx.stroke();ctx.fillText(p6.toString(),30+r,220-p6*10);}}d=0;}else{if(p6!==-1){if(p6>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p6.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p6*10);ctx.stroke();ctx.fillText(p6.toString(),30+r,220-p6*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p7!==-1){if(p7>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p7.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p7*10);ctx.stroke();ctx.fillText(p7.toString(),30+r,220-p7*10);}}d=0;}else{if(p7!==-1){if(p7>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p7.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p7*10);ctx.stroke();ctx.fillText(p7.toString(),30+r,220-p7*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p8!==-1){if(p8>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p8.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p8*10);ctx.stroke();ctx.fillText(p8.toString(),30+r,220-p8*10);}}d=0;}else{if(p8!==-1){if(p8>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p8.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p8*10);ctx.stroke();ctx.fillText(p8.toString(),30+r,220-p8*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p9!==-1){if(p9>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p9.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p9*10);ctx.stroke();ctx.fillText(p9.toString(),30+r,220-p9*10);}}d=0;}else{if(p9!==-1){if(p9>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p9.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p9*10);ctx.stroke();ctx.fillText(p9.toString(),30+r,220-p9*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p10!==-1){if(p10>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p10.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p10*10);ctx.stroke();ctx.fillText(p10.toString(),30+r,220-p10*10);}}d=0;}else{if(p10!==-1){if(p10>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p10.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p10*10);ctx.stroke();ctx.fillText(p10.toString(),30+r,220-p10*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p11!==-1){if(p11>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p11.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p11*10);ctx.stroke();ctx.fillText(p11.toString(),30+r,220-p11*10);}}d=0;}else{if(p11!==-1){if(p11>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p11.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p11*10);ctx.stroke();ctx.fillText(p11.toString(),30+r,220-p11*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p12!==-1){if(p12>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p12.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p12*10);ctx.stroke();ctx.fillText(p12.toString(),30+r,220-p12*10);}}d=0;}else{if(p12!==-1){if(p12>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p12.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p12*10);ctx.stroke();ctx.fillText(p12.toString(),30+r,220-p12*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p13!==-1){if(p13>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p13.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p13*10);ctx.stroke();ctx.fillText(p13.toString(),30+r,220-p13*10);}}d=0;}else{if(p13!==-1){if(p13>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p13.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p13*10);ctx.stroke();ctx.fillText(p13.toString(),30+r,220-p13*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p14!==-1){if(p14>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p14.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p14*10);ctx.stroke();ctx.fillText(p14.toString(),30+r,220-p14*10);}}d=0;}else{if(p14!==-1){if(p14>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p14.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p14*10);ctx.stroke();ctx.fillText(p14.toString(),30+r,220-p14*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p15!==-1){if(p15>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p15.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p15*10);ctx.stroke();ctx.fillText(p15.toString(),30+r,220-p15*10);}}d=0;}else{if(p15!==-1){if(p15>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p15.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p15*10);ctx.stroke();ctx.fillText(p15.toString(),30+r,220-p15*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p16!==-1){if(p16>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p16.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p16*10);ctx.stroke();ctx.fillText(p16.toString(),30+r,220-p16*10);}}d=0;}else{if(p16!==-1){if(p16>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p16.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p16*10);ctx.stroke();ctx.fillText(p16.toString(),30+r,220-p16*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p17!==-1){if(p17>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p17.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p17*10);ctx.stroke();ctx.fillText(p17.toString(),30+r,220-p17*10);}}d=0;}else{if(p17!==-1){if(p17>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p17.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p17*10);ctx.stroke();ctx.fillText(p17.toString(),30+r,220-p17*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p18!==-1){if(p18>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p18.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p18*10);ctx.stroke();ctx.fillText(p18.toString(),30+r,220-p18*10);}}d=0;}else{if(p18!==-1){if(p18>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p18.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p18*10);ctx.stroke();ctx.fillText(p18.toString(),30+r,220-p18*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p19!==-1){if(p19>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p19.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p19*10);ctx.stroke();ctx.fillText(p19.toString(),30+r,220-p19*10);}}d=0;}else{if(p19!==-1){if(p19>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p19.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p19*10);ctx.stroke();ctx.fillText(p19.toString(),30+r,220-p19*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p20!==-1){if(p20>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p20.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p20*10);ctx.stroke();ctx.fillText(p20.toString(),30+r,220-p20*10);}}d=0;}else{if(p20!==-1){if(p20>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p20.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p20*10);ctx.stroke();ctx.fillText(p20.toString(),30+r,220-p20*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p21!==-1){if(p21>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p21.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p21*10);ctx.stroke();ctx.fillText(p21.toString(),30+r,220-p21*10);}}d=0;}else{if(p21!==-1){if(p21>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p21.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p21*10);ctx.stroke();ctx.fillText(p21.toString(),30+r,220-p21*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p22!==-1){if(p22>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p22.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p22*10);ctx.stroke();ctx.fillText(p22.toString(),30+r,220-p22*10);}}d=0;}else{if(p22!==-1){if(p22>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p22.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p22*10);ctx.stroke();ctx.fillText(p22.toString(),30+r,220-p22*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p23!==-1){if(p23>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p23.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p23*10);ctx.stroke();ctx.fillText(p23.toString(),30+r,220-p23*10);}}d=0;}else{if(p23!==-1){if(p23>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p23.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p23*10);ctx.stroke();ctx.fillText(p23.toString(),30+r,220-p23*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "    r=t*60;if(d===1){if(p24!==-1){if(p24>20){ctx.moveTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p24.toString(),30+r,220-20*10);}else{ctx.moveTo(30+r,220-p24*10);ctx.stroke();ctx.fillText(p24.toString(),30+r,220-p24*10);}}d=0;}else{if(p24!==-1){if(p24>20){ctx.lineTo(30+r,220-20*10);ctx.stroke();ctx.fillText(p24.toString(),30+r,220-20*10);}else{ctx.lineTo(30+r,220-p24*10);ctx.stroke();ctx.fillText(p24.toString(),30+r,220-p24*10);}}}if(t===24){t=0;d=1;}else{t++;}\r\n"
					+ "\r\n"
					+ "</script>\r\n"
					+ "</body>\r\n"
					+ "</html>");
			return true;
		} catch(IOException e) {
			error("写入online graph时出现问题",e.toString(),
					"Problems occured when writing online graph",e.toString());
			return false;
		}
		} else {
			log("未重写online graph","ocb.cfg/writeonlinegraph=" + writeonlinegraph,
					"Skip write online graph","ocb.cfg/writeonlinegraph=" + writeonlinegraph);
			return false;
		}
	}
	
	public static int L/*Get Old Player Count.*/(int t/*p0,p1,p2... "0,1,2..." is t*/) {
		if(writeonlinegraph == true) {
		try {
			String fileName =localpath + "ocb\\" + t + ".txt";
	        FileReader fileReader = new FileReader(fileName);

	        BufferedReader bufferedReader = new BufferedReader(fileReader);

	        String line =bufferedReader.readLine();
	        
	        bufferedReader.close();
	        fileReader.close();
	        return Integer.valueOf(line);
		} catch (FileNotFoundException e) {
			File file = new File(localpath + "ocb\\" + t + ".txt");
			try {
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				writer.write("0");
				writer.flush();
				writer.close();
				error(t + ".txt历史记录文件缺失，跳过取0",e.toString(),
						t + ".txt is not found, skip and returning 0",e.toString());
				return 0;
			} catch (IOException e1) {
				//It will be a massive mess when using printStackTrace
				error(t + ".txt历史记录文件缺失且ocb无法自行解决该问题",e.toString(),
						t + ".txt is not found and ocb can not create a new file",e.toString());
				return 0;
			}
		} catch (IOException e) {
			error("读取online graph历史数据时出现问题且ocb无法自行解决该问题",e.toString(),
					"Problems occured when reading online graph history data and ocb can not solve it",e.toString());
			return 0;
		}
		} else {
			log("未重写历史记录人数","ocb.cfg/writeonlinegraph=" + writeonlinegraph,
					"Skip history player record","ocb.cfg/writeonlinegraph=" + writeonlinegraph);
			return 0;
		}
	}
	
	public static int get_max_player_count() {
		if(writeonlinepro == true) {
		try {
			String fileName =localpath + "ocb\\max.txt";
	        FileReader fileReader = new FileReader(fileName);

	        BufferedReader bufferedReader = new BufferedReader(fileReader);

	        String line = bufferedReader.readLine();
	        
	        bufferedReader.close();
	        fileReader.close();
	        return Integer.valueOf(line);
		} catch (IOException e) {
			File file = new File(localpath + "ocb\\max.txt");
			try {
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				writer.write("0");
				writer.flush();
				writer.close();
				error("max.txt最大玩家数文件缺失，跳过取0",e.toString(),
						"max.txt is not found, skip and returning 0",e.toString());
				return 0;
			} catch (IOException e1) {
				error("max.txt最大玩家数文件缺失且ocb无法创建新文件",e.toString(),
						"max.txt is not found and ocb can not create a new file",e.toString());
				return 0;
			}
		}
		} else {
			log("未读取online pro/max.txt","ocb.cfg/writeonlinepro=" + writeonlinepro,
					"Skip read online pro/max.txt","ocb.cfg/writeonlinepro=" + writeonlinepro);
			return 0;
		}
	}
	
	public static boolean write_history_player_count() {
		if(writeonlinegraph == true) {
		
			Calendar c = Calendar.getInstance();
			int h = c.get(Calendar.HOUR_OF_DAY);
			if(logmode >= 2) {
				Utils.log("写入历史记录...",h + ".txt"
						,"Write history player count...",":" + h + ".txt");
			}
		
		try(FileWriter fw = new FileWriter(localpath + "ocb\\" + h + ".txt", false)){
	        fw.write(String.valueOf(Utils.get_online_player_count()));
	        
	        fw.flush();
	        return true;
	    } catch(FileNotFoundException e) {
			File file = new File(localpath + "ocb\\" + "h.txt");
			try {
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				writer.write(Utils.get_online_player_count());
				writer.flush();
				writer.close();
				error(h + ".txt历史记录文件缺失，创建文件并写入" + Utils.get_online_player_count(),e.toString(),
						h + ".txt is not found, create and write" + Utils.get_online_player_count(),e.toString());
				return true;
			} catch (IOException e1) {
				error(h + ".txt历史记录文件缺失且ocb无法创建文件入", e.toString() + " | " + e1.toString(),
						h + ".txt is not found and ocb can not create the file",e.toString() + " | " + e1.toString());
				return false;
			}
	    } catch (IOException e) {
			error("写入历史数据" + h + ".txt时出现问题且ocb无法自行解决该问题",e.toString(),
					"Problems occured when writing " + h + ".txt history data and ocb can not solve it",e.toString());
			return false;
	    }
		} else {
			return false;
		}
	}
	
	public static boolean write_max_player_count() {
		if(writeonlinegraph == true) {
		try(FileWriter fw = new FileWriter(localpath + "ocb\\max.txt", false)){
	        fw.write(String.valueOf(Utils.get_online_player_count()));
	        
	        fw.flush();
	        return true;
	    } catch(FileNotFoundException e) {
			File file = new File(localpath + "ocb\\max.txt");
			try {
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				writer.write(Utils.get_online_player_count());
				writer.flush();
				writer.close();
				error("max.txt历史记录文件缺失，创建文件并写入" + Utils.get_online_player_count(),e.toString(),
						"max.txt is not found, create and write" + Utils.get_online_player_count(),e.toString());
				return true;
			} catch (IOException e1) {
				error("max.txt历史记录文件缺失且ocb无法创建文件入", e.toString() + " | " + e1.toString(),
						"max.txt is not found and ocb can not create the file",e.toString() + " | " + e1.toString());
				return false;
			}
	    } catch (IOException e) {
			error("写入历史数据max.txt时出现问题且ocb无法自行解决该问题",e.toString(),
					"Problems occured when writing max.txt history data and ocb can not solve it",e.toString());
			return false;
	    }
		} else {
			log("未重写online pro/max.txt","ocb.cfg/writeonlinepro=" + writeonlinepro,
					"Skip write online pro/max.txt","ocb.cfg/writeonlinepro=" + writeonlinepro);
			return false;
		}
	}

}
