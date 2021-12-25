package OnlinePlayerCheck;

import java.text.*;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
import java.io.IOException;

import net.md_5.bungee.BungeeCord;

public class Utils {
	public static String Chat(String a) {
		return a.replaceAll("&", "§");
	}
	
	public static String noperm() {
		return "§4你没有权限执行这个命令";
	}
	
	//CFG var
	public static int version=0,language=0,logmode=0;
	public static boolean rewritelog=true,writeonlinebasic=true,addonlinelog=true,writeonlinegraph=true,writeonlinepro=true,lowperformance=false;
	public static String ocbpath=null,onlinepath=null;
	public static boolean readcfg() {
		//Stage 1:Create or read config
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\ocb.cfg")), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\ocb.cfg")), "ANSI"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				if(clear_file("%appdata%\\ocb.cfg") == false) {
					return false;
				}
				else {
					readcfg();
				}
				return false;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(clear_file("%appdata%\\ocb.cfg") == false) {
				return false;
			}
			else {
				readcfg();
			}
			return false;
		}
		//Stage 2.1 read version of config
		String STRversion=null;
		try {
			STRversion = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		version = Integer.valueOf(STRversion);
		if(version==1) {//CORRECT VERSION
			//Stage 2.2 read language
			String STRlanguage=null;
			try {
				STRlanguage = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			language=Integer.valueOf(STRlanguage);
			//Stage 2.3 read log mode
			String STRlogmode=null;
			try {
				STRlogmode = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logmode=Integer.valueOf(STRlogmode);
			//Stage 2.4 read file path
			try {
				ocbpath = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			//Stage 2.5 read url path
			try {
				onlinepath = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			//Stage 2.6 read rewrite log
			String STRrewritelog=null;
			try {
				STRrewritelog = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int INTlowperformance=0;
			INTlowperformance = Integer.valueOf(STRlowperformance);
			if(INTlowperformance==1) {
				lowperformance=true;
			} else if (INTlowperformance==2) {
				lowperformance=false;
			}
			//READ CONFIG DONE
			return true;
		} else if(version<1) {
			return false;//impossible
		} else if(version>1) {
			return false;//future config
		} else {
			return false;
		}
		
	}
	
	
	
	
	public static int get_online_player_count() {
		return BungeeCord.getInstance().getOnlineCount();
	}
	
	public static boolean write_online_basic(int playercount) {
		if(writeonlinebasic == true) {
		try(/*FileWriter fw = new FileWriter("C:\\phpstudy_pro\\WWW\\online\\online_basic.html", false)*/
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(ocbpath + "online\\online_basic.html", false),"UTF-8");){
	        fw.write( "<!AUTO GENERATED BY OCB>\r\n"
	        		+ "<!SHARKMC.CN>\r\n"
	        		+ "<!DOCTYPE html>\n"
	        		+ "<html lang=\"en\">\n"
	        		+ "<head>\n"
	        		+ "    <meta charset=\"UTF-8\">\n"
	        		+ "    <title>Online player count basic</title>\n"
	        		+ "    <style>\n"
	        		+ "        body {\n"
	        		+ "            background-color: Transparent;\n"
	        		+ "        }\n"
	        		+ "    </style>\n"
	        		+ "</head>\n"
	        		+ "<body>\n"
	        		+ "<a href=\"" + onlinepath + "online/\" target=\"_blank\" style=\"text-decoration: none\"><strong style=\"color: pink; text-align: center\">Online players: " + playercount + "</strong></a>\n"
	        		+ "</body>\n"
	        		+ "</html>");
	        //fw.write(System.getProperty("line.separator"));
	        fw.flush();
	        return true;
	    }catch(IOException e) {
	        e.printStackTrace();
	    }
		return false;
		} else {
			return false;
		}
	}

	public static boolean write_online_pro() {
		if(writeonlinepro == true) {
		try(/*FileWriter fw = new FileWriter("C:\\phpstudy_pro\\WWW\\online\\online_pro.html", false)*/
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(ocbpath + "online\\online_pro.html", false),"UTF-8");){
			if(onlinepath.equalsIgnoreCase("http://sharkmc.cn/")) { // PRIVATE MODDED WRITER
				fw.write(""
		        		+ "<!AUTO GENERATED BY OCB>\r\n"
		        		+ "<!SHARKMC.CN>\r\n"
		        		+ "<!DOCTYPE html>\r\n"
		        		+ "<html lang=\"zh-cn\" xmlns=\"http://www.w3.org/1999/html\">\r\n"
		        		+ "<head>\r\n"
		        		+ "    <meta charset=\"UTF-8\">\r\n"
		        		+ "    <title>Online Player Check Pro</title>\r\n"
		        		+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\r\n"
		        		+ "    <link rel=\"icon\" href=\"http://sharkmc.cn/img/shark.png\" type=\"image/x-icon\">\r\n"
		        		+ "    <meta name=\"referrer\" content=\"no-referrer\">\r\n"
		        		+ "    <style>\r\n"
		        		+ "        body {\r\n"
		        		+ "            background: black;\r\n"
		        		+ "        }\r\n"
		        		+ "        p,h1,h2,h3,h4,h5,h6,a {\r\n"
		        		+ "            font-family: \"Arial\", serif;\r\n"
		        		+ "            color: white;\r\n"
		        		+ "        }\r\n"
		        		+ "    </style>\r\n"
		        		+ "\r\n"
		        		+ "\r\n"
		        		+ "</head>\r\n"
		        		+ "<body>\r\n"
		        		+ "<iframe src=\"http://sharkmc.cn/top.html\" style=\"height: 120px;width: 101%;margin-left: -10px;background: #4257b2;position: fixed;top: 0;margin-bottom: 5px;\" scrolling=\"no\" frameborder=\"0\"></iframe>\r\n"
		        		+ "\r\n"
		        		+ "<p>\r\n"
		        		+ "    <br>\r\n"
		        		+ "    <br>\r\n"
		        		+ "    <br>\r\n"
		        		+ "    <br>\r\n"
		        		+ "    <br>\r\n"
		        		+ "</p>\r\n"
		        		+ "<!MAIN><center>\r\n"
		        		+ "    <div id=\"box\" style=\"width: 500px;margin-right: 1000px;position: relative\">\r\n"
		        		+ "        <div id=\"onlinenow\" style=\"height: 100px;width: 500px;text-align: center;background-color: #222222;border-radius:10px;\">\r\n"
		        		+ "            <h1 style=\"padding-top: 10px;\">" + Utils.get_online_player_count() + "</h1>\r\n"
		        		+ "            <p>当前在线人数</p>\r\n"
		        		+ "        </div>\r\n"
		        		+ "        <div id=\"onlinemax\" style=\"height: 100px;width: 500px;text-align: center;background-color: #222222;border-radius:10px;\">\r\n"
		        		+ "            <h1 style=\"padding-top: 10px;\">" + Utils.get_max_player_count() + "</h1>\r\n"
		        		+ "            <p>历史最大人数</p>\r\n"
		        		+ "        </div>\r\n"
		        		+ "        <div id=\"onlineaverage\" style=\"height: 100px;width: 500px;text-align: center;background-color: #222222;border-radius:10px;\">\r\n"
		        		+ "            <h1 style=\"padding-top: 10px;\">" + Utils.get_average_player_count() + "</h1>\r\n"
		        		+ "            <p>平均在线人数</p>\r\n"
		        		+ "        </div>\r\n"
		        		+ "        <div id=\"onlinelog\" style=\"width: 980px;height: 342.88px;text-align: left;background-color: #222222;border-radius:10px;position: absolute;left:520px;top:0;\">\r\n"
		        		+ "            <iframe src=\"online_log.html\" width=\"100%\" height=\"100%\" frameborder=\"0\" allowtransparency=\"true\" style=\"border-radius:10px;height: 342.88px\"></iframe>\r\n"
		        		+ "        </div>\r\n"
		        		+ "    </div>\r\n"
		        		+ "\r\n"
		        		+ "    <br>\r\n"
		        		+ "    <div id=\"onlinegraph\">\r\n"
		        		+ "        <iframe src=\"online_graph.html\" style=\"text-decoration: none;width: 1500px;height: 260px;border-radius:10px;\" scrolling=\"no\" frameborder=\"0\" ></iframe>\r\n"
		        		+ "    </div>\r\n"
		        		+ "    <br>\r\n"
		        		+ "    <div style=\"width: 1500px;height: auto;color: white;background-color: #222222;border-radius:10px;padding-top: 1px;padding-bottom: 1px;\" id=\"onlinelist\">\r\n"
		        		+ "        <p style=\"word-wrap: break-word;font-size: 20px;\"><strong style=\"color: #00aaff\">在线玩家: </strong> " + BungeeCord.getInstance().getPlayers() + "</p>\r\n"
		        		+ "    </div>\r\n"
		        		+ "    <br>\r\n"
		        		+ "    <div style=\"width: 1500px;height: auto;color: white;background-color: #222222;border-radius:10px;margin-top:5px;padding-top: 10px;padding-bottom: 10px;font-size: 14px;\" id=\"onlinelist\">\r\n"
		        		+ "        <b style=\"color: #cccccc;\">Powered by </b><a href=\"http://sharkmc.cn/online/description.html\" style=\"color: #00aaff;text-decoration:none\"><b style=\"color: #00aaff;\">Online Check(BungeeCordVer)</b></a>\r\n"
		        		+ "        <b style=\"color: rgb(255, 255, 255);\"> by </b><a href=\"http://Sharkmc.cn\" style=\"text-decoration: none;\">\r\n"
		        		+ "        <b style=\"color: #90c4ff;\">Sharkmc.cn</b></a>\r\n"
		        		+ "        \r\n"
		        		+ "        <br>\r\n"
		        		+ "        \r\n"
		        		+ "        <b style=\"color: #cccccc;\">Try </b><b style=\"color: white;\">/ocb </b>\r\n"
		        		+ "        <b style=\"color: #cccccc;\">in game!</b>\r\n"
		        		+ "    </div>\r\n"
		        		+ "</center>\r\n"
		        		+ "<!MAIN>\r\n"
		        		+ "</body>\r\n"
		        		+ "</html>"
		        		+ "");
			} else { // PUBLIC WRITER
				if(language == 1) {
					fw.write(""
			        		+ "<!AUTO GENERATED BY OCB>\r\n"
			        		+ "<!SHARKMC.CN>\r\n"
			        		+ "<!DOCTYPE html>\r\n"
			        		+ "<html lang=\"zh-cn\" xmlns=\"http://www.w3.org/1999/html\">\r\n"
			        		+ "<head>\r\n"
			        		+ "    <meta charset=\"UTF-8\">\r\n"
			        		+ "    <title>Online Player Check Pro</title>\r\n"
			        		+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\r\n"
			        		+ "    <link rel=\"icon\" href=\"http://sharkmc.cn/img/shark.png\" type=\"image/x-icon\">\r\n"
			        		+ "    <meta name=\"referrer\" content=\"no-referrer\">\r\n"
			        		+ "    <style>\r\n"
			        		+ "        body {\r\n"
			        		+ "            background: black;\r\n"
			        		+ "        }\r\n"
			        		+ "        p,h1,h2,h3,h4,h5,h6,a {\r\n"
			        		+ "            font-family: \"Arial\", serif;\r\n"
			        		+ "            color: white;\r\n"
			        		+ "        }\r\n"
			        		+ "    </style>\r\n"
			        		+ "\r\n"
			        		+ "\r\n"
			        		+ "</head>\r\n"
			        		+ "<body>\r\n"
			        		+ "<!MAIN><center>\r\n"
			        		+ "    <div id=\"box\" style=\"width: 500px;margin-right: 1000px;position: relative\">\r\n"
			        		+ "        <div id=\"onlinenow\" style=\"height: 100px;width: 500px;text-align: center;background-color: #222222;border-radius:10px;\">\r\n"
			        		+ "            <h1 style=\"padding-top: 10px;\">" + Utils.get_online_player_count() + "</h1>\r\n"
			        		+ "            <p>当前在线人数</p>\r\n"
			        		+ "        </div>\r\n"
			        		+ "        <div id=\"onlinemax\" style=\"height: 100px;width: 500px;text-align: center;background-color: #222222;border-radius:10px;\">\r\n"
			        		+ "            <h1 style=\"padding-top: 10px;\">" + Utils.get_max_player_count() + "</h1>\r\n"
			        		+ "            <p>历史最大人数</p>\r\n"
			        		+ "        </div>\r\n"
			        		+ "        <div id=\"onlineaverage\" style=\"height: 100px;width: 500px;text-align: center;background-color: #222222;border-radius:10px;\">\r\n"
			        		+ "            <h1 style=\"padding-top: 10px;\">" + Utils.get_average_player_count() + "</h1>\r\n"
			        		+ "            <p>平均在线人数</p>\r\n"
			        		+ "        </div>\r\n"
			        		+ "        <div id=\"onlinelog\" style=\"width: 980px;height: 342.88px;text-align: left;background-color: #222222;border-radius:10px;position: absolute;left:520px;top:0;\">\r\n"
			        		+ "            <iframe src=\"online_log.html\" width=\"100%\" height=\"100%\" frameborder=\"0\" allowtransparency=\"true\" style=\"border-radius:10px;height: 342.88px\"></iframe>\r\n"
			        		+ "        </div>\r\n"
			        		+ "    </div>\r\n"
			        		+ "\r\n"
			        		+ "    <br>\r\n"
			        		+ "    <div id=\"onlinegraph\">\r\n"
			        		+ "        <iframe src=\"online_graph.html\" style=\"text-decoration: none;width: 1500px;height: 260px;border-radius:10px;\" scrolling=\"no\" frameborder=\"0\" ></iframe>\r\n"
			        		+ "    </div>\r\n"
			        		+ "    <br>\r\n"
			        		+ "    <div style=\"width: 1500px;height: auto;color: white;background-color: #222222;border-radius:10px;padding-top: 1px;padding-bottom: 1px;\" id=\"onlinelist\">\r\n"
			        		+ "        <p style=\"word-wrap: break-word;font-size: 20px;\"><strong style=\"color: #00aaff\">在线玩家: </strong> " + BungeeCord.getInstance().getPlayers() + "</p>\r\n"
			        		+ "    </div>\r\n"
			        		+ "    <br>\r\n"
			        		+ "    <div style=\"width: 1500px;height: auto;color: white;background-color: #222222;border-radius:10px;margin-top:5px;padding-top: 10px;padding-bottom: 10px;font-size: 14px;\" id=\"onlinelist\">\r\n"
			        		+ "        <b style=\"color: #cccccc;\">Powered by </b><a href=\"http://sharkmc.cn/online/description.html\" style=\"color: #00aaff;text-decoration:none\"><b style=\"color: #00aaff;\">Online Check(BungeeCordVer)</b></a>\r\n"
			        		+ "        <b style=\"color: rgb(255, 255, 255);\"> by </b><a href=\"http://Sharkmc.cn\" style=\"text-decoration: none;\">\r\n"
			        		+ "        <b style=\"color: #90c4ff;\">Sharkmc.cn</b></a>\r\n"
			        		+ "        \r\n"
			        		+ "        <br>\r\n"
			        		+ "        \r\n"
			        		+ "        <b style=\"color: #cccccc;\">Try </b><b style=\"color: white;\">/ocb </b>\r\n"
			        		+ "        <b style=\"color: #cccccc;\">in game!</b>\r\n"
			        		+ "    </div>\r\n"
			        		+ "</center>\r\n"
			        		+ "<!MAIN>\r\n"
			        		+ "</body>\r\n"
			        		+ "</html>"
			        		+ "");
				} else {
					fw.write(""
			        		+ "<!AUTO GENERATED BY OCB>\r\n"
			        		+ "<!SHARKMC.CN>\r\n"
			        		+ "<!DOCTYPE html>\r\n"
			        		+ "<html lang=\"zh-cn\" xmlns=\"http://www.w3.org/1999/html\">\r\n"
			        		+ "<head>\r\n"
			        		+ "    <meta charset=\"UTF-8\">\r\n"
			        		+ "    <title>Online Player Check Pro</title>\r\n"
			        		+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\r\n"
			        		+ "    <link rel=\"icon\" href=\"http://sharkmc.cn/img/shark.png\" type=\"image/x-icon\">\r\n"
			        		+ "    <meta name=\"referrer\" content=\"no-referrer\">\r\n"
			        		+ "    <style>\r\n"
			        		+ "        body {\r\n"
			        		+ "            background: black;\r\n"
			        		+ "        }\r\n"
			        		+ "        p,h1,h2,h3,h4,h5,h6,a {\r\n"
			        		+ "            font-family: \"Arial\", serif;\r\n"
			        		+ "            color: white;\r\n"
			        		+ "        }\r\n"
			        		+ "    </style>\r\n"
			        		+ "\r\n"
			        		+ "\r\n"
			        		+ "</head>\r\n"
			        		+ "<body>\r\n"
			        		+ "<!MAIN><center>\r\n"
			        		+ "    <div id=\"box\" style=\"width: 500px;margin-right: 1000px;position: relative\">\r\n"
			        		+ "        <div id=\"onlinenow\" style=\"height: 100px;width: 500px;text-align: center;background-color: #222222;border-radius:10px;\">\r\n"
			        		+ "            <h1 style=\"padding-top: 10px;\">" + Utils.get_online_player_count() + "</h1>\r\n"
			        		+ "            <p>Current</p>\r\n"
			        		+ "        </div>\r\n"
			        		+ "        <div id=\"onlinemax\" style=\"height: 100px;width: 500px;text-align: center;background-color: #222222;border-radius:10px;\">\r\n"
			        		+ "            <h1 style=\"padding-top: 10px;\">" + Utils.get_max_player_count() + "</h1>\r\n"
			        		+ "            <p>Max</p>\r\n"
			        		+ "        </div>\r\n"
			        		+ "        <div id=\"onlineaverage\" style=\"height: 100px;width: 500px;text-align: center;background-color: #222222;border-radius:10px;\">\r\n"
			        		+ "            <h1 style=\"padding-top: 10px;\">" + Utils.get_average_player_count() + "</h1>\r\n"
			        		+ "            <p>Average</p>\r\n"
			        		+ "        </div>\r\n"
			        		+ "        <div id=\"onlinelog\" style=\"width: 980px;height: 342.88px;text-align: left;background-color: #222222;border-radius:10px;position: absolute;left:520px;top:0;\">\r\n"
			        		+ "            <iframe src=\"online_log.html\" width=\"100%\" height=\"100%\" frameborder=\"0\" allowtransparency=\"true\" style=\"border-radius:10px;height: 342.88px\"></iframe>\r\n"
			        		+ "        </div>\r\n"
			        		+ "    </div>\r\n"
			        		+ "\r\n"
			        		+ "    <br>\r\n"
			        		+ "    <div id=\"onlinegraph\">\r\n"
			        		+ "        <iframe src=\"online_graph.html\" style=\"text-decoration: none;width: 1500px;height: 260px;border-radius:10px;\" scrolling=\"no\" frameborder=\"0\" ></iframe>\r\n"
			        		+ "    </div>\r\n"
			        		+ "    <br>\r\n"
			        		+ "    <div style=\"width: 1500px;height: auto;color: white;background-color: #222222;border-radius:10px;padding-top: 1px;padding-bottom: 1px;\" id=\"onlinelist\">\r\n"
			        		+ "        <p style=\"word-wrap: break-word;font-size: 20px;\"><strong style=\"color: #00aaff\">Online list: </strong> " + BungeeCord.getInstance().getPlayers() + "</p>\r\n"
			        		+ "    </div>\r\n"
			        		+ "    <br>\r\n"
			        		+ "    <div style=\"width: 1500px;height: auto;color: white;background-color: #222222;border-radius:10px;margin-top:5px;padding-top: 10px;padding-bottom: 10px;font-size: 14px;\" id=\"onlinelist\">\r\n"
			        		+ "        <b style=\"color: #cccccc;\">Powered by </b><a href=\"http://sharkmc.cn/online/description.html\" style=\"color: #00aaff;text-decoration:none\"><b style=\"color: #00aaff;\">Online Check(BungeeCordVer)</b></a>\r\n"
			        		+ "        <b style=\"color: rgb(255, 255, 255);\"> by </b><a href=\"http://Sharkmc.cn\" style=\"text-decoration: none;\">\r\n"
			        		+ "        <b style=\"color: #90c4ff;\">Sharkmc.cn</b></a>\r\n"
			        		+ "        \r\n"
			        		+ "        <br>\r\n"
			        		+ "        \r\n"
			        		+ "        <b style=\"color: #cccccc;\">Try </b><b style=\"color: white;\">/ocb </b>\r\n"
			        		+ "        <b style=\"color: #cccccc;\">in game!</b>\r\n"
			        		+ "    </div>\r\n"
			        		+ "</center>\r\n"
			        		+ "<!MAIN>\r\n"
			        		+ "</body>\r\n"
			        		+ "</html>"
			        		+ "");
				}
			}
			
			//fw.write(System.getProperty("line.separator"));
	        fw.flush();
	        return true;
	    }catch(IOException e) {
	        e.printStackTrace();
	    }
		return false;
		} else {
			return false;
		}
	}
	
	public static double get_average_player_count() {
		double avg = 0;
		int a = 0;
		a = L(0) + L(1) + L(2) + L(3) + L(4) + L(5) + L(6) + L(7) + L(8) + L(9) + L(10) + L(11) + L(12) + L(13) + L(14) + L(15) + L(16) + L(17) + L(18) + L(19) + L(20) + L(21) + L(22) + L(23);
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
				e.printStackTrace();
				return false;
			}
        } else {
        	try {
				file.createNewFile();
	        	return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
        }
	}
	
	public static boolean verify_file(String path) {
		File file=new File(path);
        if(file.isFile()) {
            return true;
        } else {
        	try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
        }
	}
	
	public static boolean write_online_log() {
		if(rewritelog == true) {
		try(
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(ocbpath + "online\\online_log.html", false),"UTF-8");){
	        fw.write( "<!DOCTYPE html>\r\n"
	        		+ "<!AUTO GENERATED BY OCB>\r\n"
	        		+ "<!SHARKMC.CN>\r\n"
	        		+ "<html lang=\"zh-cn\">\r\n"
	        		+ "<head>\r\n"
	        		+ "    <meta charset=\"UTF-8\">\r\n"
	        		+ "    <title>Online player log</title>\r\n"
	        		+ "    <style>\r\n"
	        		+ "      body {\r\n"
	        		+ "        background-color: #222222;\r\n"
	        		+ "      }\r\n"
	        		+ "    </style>\r\n"
	        		+ "</head>\r\n"
	        		+ "<body>");
	        //fw.write(System.getProperty("line.separator"));
	        fw.flush();
	        return true;
	    }catch(IOException e) {
	        e.printStackTrace();
	    }
		return false;
		} else {
			return false;
		}
	}
	
	public static boolean add_online_log_strong(String info) {
		if(addonlinelog == true) {
		try(/*FileWriter fw = new FileWriter("C:\\phpstudy_pro\\WWW\\online\\online_log.html", true)*/
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(ocbpath + "online\\online_log.html", true),"UTF-8");){
			SimpleDateFormat time=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			Date now=new Date();
			fw.write("<p style=\"color: #00de0a\"><strong style=\"color: mediumpurple\">" + time.format(now) +  "</strong> <strong style=\"color:gold;\">" + info + "</strong></p>");
			return true;
		}   catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		} else {
			return false;			
		}
	}
	
	public static boolean add_online_log_normal(String info) {
		if(addonlinelog == true) {
		try(/*FileWriter fw = new FileWriter("C:\\phpstudy_pro\\WWW\\online\\online_log.html", true)*/
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(ocbpath + "online\\online_log.html", true),"UTF-8");){
			SimpleDateFormat time=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			Date now=new Date();
			fw.write("<p style=\"color: #00de0a\"><strong style=\"color: mediumpurple\">" + time.format(now) +  "</strong> <strong style=\"color:white;\">" + info + "</strong></p>");
			return true;
		}   catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		} else {
			return false;
		}
	}
	
	public static boolean add_online_log_weak(String info) {
		if(addonlinelog == true) {
		try(/*FileWriter fw = new FileWriter("C:\\phpstudy_pro\\WWW\\online\\online_log.html", true)*/
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(ocbpath + "online\\online_log.html", true),"UTF-8");){
			SimpleDateFormat time=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			Date now=new Date();
			fw.write("<p style=\"color: #00de0a\"><strong style=\"color: mediumpurple\">" + time.format(now) +  "</strong> <strong style=\"color:#999999;\">" + info + "</strong></p>");
			return true;
		}   catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		} else {
			return false;
		}
	}
	
	public static boolean add_online_log_player(String playername,boolean joinstatus,String target) {
		if(addonlinelog == true) {
		try(/*FileWriter fw = new FileWriter("C:\\phpstudy_pro\\WWW\\online\\online_log.html", true)*/
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(ocbpath + "online\\online_log.html", true),"UTF-8");){
			SimpleDateFormat time=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			Date now=new Date();
			if(joinstatus == true)
				fw.write("<p style=\"color: #00de0a\"><strong style=\"color: mediumpurple\">" + time.format(now) + " </strong> <strong style=\"color:#00aaff;\">" + playername + "</strong> joined <b style=\"color:whitesmoke\">server</b> <b style=\"color:white\">" + target + "</b></p>\r\n");
			else
				fw.write("<p style=\"color: #de0000\"><strong style=\"color: mediumpurple\">" + time.format(now) + " </strong> <strong style=\"color:#00aaff;\">" + playername + "</strong> left</p>\r\n");
			return true;
		}   catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		} else {
			return false;
		}
	}
	
	public static boolean write_online_graph(int p0,int p1,int p2,int p3,int p4,int p5,int p6,int p7,int p8,int p9,int p10,int p11,int p12,int p13,int p14,int p15,int p16,int p17,int p18,int p19,int p20,int p21,int p22,int p23) {
		if(writeonlinegraph==true) {
		Calendar c = Calendar.getInstance();
		int h = c.get(Calendar.HOUR_OF_DAY);
		try(/*FileWriter fw = new FileWriter("C:\\phpstudy_pro\\WWW\\online\\online_graph.html", false)*/
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(ocbpath + "online\\online_graph.html", true),"UTF-8");){
			fw.write("<!DOCTYPE html>\r\n"
					+ "<!AUTO GENERATED BY OCB>\r\n"
	        		+ "<!SHARKMC.CN>\r\n"
					+ "<html lang=\"zh-cn\">\r\n"
					+ "<head>\r\n"
					+ "    <meta charset=\"UTF-8\">\r\n"
					+ "    <title>Online Graph</title>\r\n"
					+ "</head>\r\n"
					+ "<body style=\"background-color: black;margin-left: 0;margin-top: 0;\">\r\n"
					+ "<canvas id=\"og\" width=\"1500px\" height=\"260px\" style=\"background-color: #222222;\"></canvas>\r\n"
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
					+ "    ctx.font=\"20px MicroSoft YaHei\";\r\n"
					+ "    ctx.fillStyle = ('#00aaff')\r\n"
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
					+ "            var p0=" + p0 + ";\r\n"
					+ "            var p1=" + p1 + ";\r\n"
					+ "            var p2=" + p2 + ";\r\n"
					+ "            var p3=" + p3 + ";\r\n"
					+ "            var p4=" + p4 + ";\r\n"
					+ "            var p5=" + p5 + ";\r\n"
					+ "            var p6=" + p6 + ";\r\n"
					+ "            var p7=" + p7 + ";\r\n"
					+ "            var p8=" + p8 + ";\r\n"
					+ "            var p9=" + p9 + ";\r\n"
					+ "            var p10=" + p10 + ";\r\n"
					+ "            var p11=" + p11 + ";\r\n"
					+ "            var p12=" + p12 + ";\r\n"
					+ "            var p13=" + p13 + ";\r\n"
					+ "            var p14=" + p14 + ";\r\n"
					+ "            var p15=" + p15 + ";\r\n"
					+ "            var p16=" + p16 + ";\r\n"
					+ "            var p17=" + p17 + ";\r\n"
					+ "            var p18=" + p18 + ";\r\n"
					+ "            var p19=" + p19 + ";\r\n"
					+ "            var p20=" + p20 + ";\r\n"
					+ "            var p21=" + p21 + ";\r\n"
					+ "            var p22=" + p22 + ";\r\n"
					+ "            var p23=" + p23 + ";\r\n"
					+ "            var p24=" + p0 + ";\r\n"
					+ "    ctx.lineWidth=1.0;\r\n"
					+ "    t=native_t;r=0;\r\n"
					+ "    ctx.fillStyle = ('#ffffff')\r\n"
					+ "    ctx.font=\"15px Arial\";\r\n"
					+ "    ctx.strokeStyle = ('#cccccc')\r\n"
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
		}   catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		} else {
			return false;
		}
	}
	
	public static int L/*Get Old Player Count.It appears too many time so i rename it L.*/(int t/*p0,p1,p2... "0,1,2..." is t*/) {
		if(writeonlinegraph == true) {
		try {
			String fileName =ocbpath + "ocb\\" + t + ".txt";
	        FileReader fileReader = new FileReader(fileName);

	        BufferedReader bufferedReader = new BufferedReader(fileReader);

	        String line =bufferedReader.readLine();
	        
	        bufferedReader.close();
	        fileReader.close();
	        return Integer.valueOf(line);
		} catch (IOException e) {
			File file = new File(ocbpath + "ocb\\" + t + ".txt");
			try {
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				writer.write("0");
				writer.flush();
				writer.close();
				return 0;
			} catch (IOException e1) {
				e1.printStackTrace();
				return 0;
			}
		}
		} else {
			return 0;
		}
	}
	
	public static int get_max_player_count() {
		if(writeonlinepro == true) {
		try {
			String fileName =ocbpath + "ocb\\max.txt";
	        FileReader fileReader = new FileReader(fileName);

	        BufferedReader bufferedReader = new BufferedReader(fileReader);

	        String line = bufferedReader.readLine();
	        
	        bufferedReader.close();
	        fileReader.close();
	        return Integer.valueOf(line);
		} catch (IOException e) {
			e.printStackTrace();
			File file = new File(ocbpath + "ocb\\max.txt");
			try {
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				writer.write("0");
				writer.flush();
				writer.close();
				return 0;
			} catch (IOException e1) {
				e1.printStackTrace();
				return 0;
			}
		}
		} else {
			return 0;
		}
	}
	
	public static boolean write_history_player_count(int h) {
		if(writeonlinegraph == true) {
		try(FileWriter fw = new FileWriter(ocbpath + "ocb\\" + h + ".txt", false)){
	        fw.write(String.valueOf(Utils.get_online_player_count()));
	        //fw.write(System.getProperty("line.separator"));
	        fw.flush();
	        return true;
	    }catch(IOException e) {
	        e.printStackTrace();
	        return false;
	    }
		} else {
			return false;
		}
	}
	
	public static boolean write_max_player_count(int count) {
		if(writeonlinepro == true) {
		try(FileWriter fw = new FileWriter(ocbpath + "ocb\\max.txt", false)){
	        fw.write(String.valueOf(count));
	        //fw.write(System.getProperty("line.separator"));
	        fw.flush();
	        return true;
	    }catch(IOException e) {
	        e.printStackTrace();
	        return false;
	    }
		} else {
			return false;
		}
	}
}
