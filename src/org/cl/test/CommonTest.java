package org.cl.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.cl.configuration.RegexString;

public class CommonTest {

	public static void test_toCharArray(){
		String str = "我就是龙猫^_^...Heihei";
		char[] c = str.toCharArray();
		for(char a:c)
			System.out.println(a);
	}
	public static void test_StrLength(){
		String str = "我就是龙猫^_^...Heihei~!@#$%^&*()_+-={[]}|\\~`.";
		System.out.println(str.length());
	}
	public static void test_regex_match(){
		//String punc_regex = "[[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]&&[^@\\[\\]]]";//标点符号
		//String acronym_regex = "[0-9]*[a-zA-Z]+[0-9]*[a-zA-Z]+[0-9]*";//acronym
		//String emotion_regex = RegexString.Regex_punt;
		String regex = "<[/]?[[a-zA-Z]]{2,10}>";
		String context = "	<brithday></brithday>";
		//String context = "～｀＝｜￥~~``!#$$%^&*()_++-=={}||\\:;'\",<>.?/·！￥＄＋…（）—【】、：；《，》＜＞。？＇．／＾＿＾｀｜•‥〃―＼～〝〞¡¿＂［］｛｝‘“”′″〔〕〈〉«»･｡「」『』︵︶︹︺ ︻︼︽︾＋－＜＝＞×＊＄％‰＃＆＠※†‡﹉﹊﹍﹎‖︴﹏﹋﹌ ";
		//String context = "" +
		//	"#竞拍iPhone6#谁说买iPhone6非！得！卖！肾！？参与竞拍还能以10%的价格轻松领走iPhone6！而且还免费抽iPad！保肾良方，一般人我不告诉他~ "+
		//"直播开始ing，[撒花]@王昊-王厂长 大驾光临啦！小伙伴们准备嗨皮起来喽！参与直播互动留言 赢“2014 MOTOUR LIVE 魔途音乐节”入场门票！" +
		//"我就是龙猫^_^.(←_←)..Heihei~!@#$%^&*()_+-={[]}|\\~`:;'\",<.>/?~！@#￥%……&*（）——+-=·；：‘“。《。》.435354SA3GF7,hfh.53,[doge][笑Cry][呵呵] [哈哈]";
		Pattern p_at = Pattern.compile(regex);
		Matcher m_at = p_at.matcher(context);
		while(m_at.find()){
			System.out.println(m_at.group());
		}
		String[] ma = context.split(regex);
		System.out.println(ma[0]);
		//System.out.println("\r\n"+m_at.replaceAll("##"));
	}
	public static void test_regex_spilt(){
		String regex = "[{}\\|]";
		String context = "{3760477291615191 | false | 好黑的营业员 | 1891626214 | 2014-09-30 12:25:15}";
		//String regex = "\\|[\\*]{2,3}\\|";
		//String context = "|***|3664158153650110|**|2014-01-07 17:27:23|**|#Baby让红包飞# 时间过得好快啊，又到了我来帮红包飞的时候了，转发评论赞本条微博，就可以立刻领取红包哦！同场加赠，奖品还有我的限量签名明信片哦！大家新年快乐哦！[爱你][爱你]|**|http://ww2.sinaimg.cn/large/61e44b02jw1ecb3ujtj5nj20hs0ovn0n.jpg|**|null|**|101029|**|4012606|**|<a href=http://app.weibo.com/t/feed/3G5oUM rel=nofollow>iPhone 5s</a>|**|null|**|1642351362|***|";
		String[] item = context.split(regex);
		for(String t : item){
			System.out.println(t+",");
		}
	}
	public static void test_regex_replace(){
		//String weiboCon = "我的微博已经开了1715天，一共发了2260条微博，拥有21215位粉丝。总价值2389705元。//@人比酱瓜黑 @李明亮zzu @Joann吴小姐 @晨曦女王 @Anid_J";
		String weiboCon = "Thanks for TimDz and Maker Space!//@北京创客空间: PCB灯笼太有爱了，还会和炮竹互动哦！ //@末末GREr:[心][心]";

		String regex_at = RegexString.Regex_at;
		Pattern p_at = Pattern.compile(regex_at);
		Matcher m_at = p_at.matcher(weiboCon);
		while(m_at.find()){
			System.out.print(m_at.group()+"\t");
		}
		System.out.println(weiboCon.replaceAll(regex_at, ""));
	}
	public static void test_trim(){
		String str = " ";
		System.out.println(str.trim());
		System.out.println("done");
	}
	public static void clear(){
		String punt_compensation = "~`!@#$%^&*()_+-={[}]|\\:;'\"<,>.?/~·！@#￥%……&*（）——-+=【】|、：；《，》。？、！＇，．／：；？＾＿｀｜、。•‥…〃―＼～\"〝〞¡¿＂（）［］｛｝‘'“”′″〔〕〈〉《》«»•¡･｡.•*.「」『』【】<>︵︶︹︺ ︻︼︽︾＋－＜＝＞×~-*&#＊$＄％‰￥＃＆＊＠※→←†‡→←﹉﹊﹍﹎‖︴﹏﹋﹌ ";
		
		//String punt_compensation = "ː¸˛˚˙ˇ˘˝´¨￣~$`^=|<>～｀＄＾＋＝｜＜＞￥×±÷≠≤≥∞∴♂♀∠⊥⌒∂\"∇≡≒≌≦≧∽√∝∵∫∬∈∪∩∧∨￢⇒⇔∀∃∮ξζ∑∏℃ω□ ∮〓╳々∞ㄨ╭╮╰╯╱╲№㏇™℡®§≈々∞卐の℡㊣§∮ミ灬№ωㄨ≮≯∥﹤﹥じ☆□∮〓 ัﻬஐ₪Þ௫ΩжфюЮ✙✉✌✁❦❧❤❃❂❁❀✿ﾟ➳εїз♧●♋✈ぷ¤々ㄨ♩✙✈✉✌✁✰❁♥ ➽〠〄㍿♝♞➴➵㊚㊛㊙℗♯♩♪♫♬♭♮☪♈º₪¤큐™©®℡►◄☼♦◊◘◙♪♫๑ิ☆❊❋❄❅❇✱✲✳✾✺✹✸✶✵✷❖❥❦❧☇☈☉☊☋☌☍☑☒☢☸☹☺☻☼☽☾♠♡♢♣♤♦♧≠๑۩۞๑｡◕☀☁☂☃☄❉☆★●◎◇◆□■△▲▽▼〓◁◀▷▶♤♠♡♧♣⊙◈▣◐◑▒▤▥▨▧▦▩☉●〇◎♨☜☞¶↕↗↙↖↘↑↓↔↕↑↓▁▂▃▄▅▆▇█▇▆▅▄▃▂▁〒¤★☆▓⊕Θ⊙¤★☆◣◢◥◤⊿回□┇┅─│┌┐┘└├┬┤┴┼━┃┏┓┛┗┣┳┫┻╋┠┯┨┷┿┝┰┥┸╂┒¬┑┚┙┖┕┎┍┞┟┡┢┦┧┩┪┭┮┱┲┵┶┹┺┽┾╀╁╃╄╅╆╇╈╉╊╝╚╔╬ ╗ ═ ╓ ╩" ;
		char[] punt_compensation_char = punt_compensation.toCharArray();
		List<Character> char_list = new ArrayList<Character>();
		for(char c : punt_compensation_char){
			if(!char_list.contains(c)){
				char_list.add(c);
			}
		}
		StringBuffer str = new StringBuffer();
		for(char c:char_list){
			str.append(c);
		}
		System.out.println(str.toString());
	}
	
	public static void test_regex_emotion(){
		String emotion_regex = "[[\\p{P}+]|[ː¸˛˚˙ˇ˘˝´¨￣~$`^=\\|<>～｀＄＾＋＝｜＜＞￥×±÷≠≤≥∞∴♂♀ゝ∠⊥⌒∂\"∇Σ≡≒≌≦≧∽√∝∵∫∬∈∪∩∧∨￢⇒⇔∀∃∮ξζ∑∏℃ωっ□ 〓╳々ㄨヾ彡乂눈〆╭╮╰╯╱╲№㏇™℡ఠ ൠ®✧§≈卐≖Ψのㅂ㊣ミツ灬ρ≮≯∥﹤﹥じゞ☆ัლﻬ☄ஐ₪Þ௫ΩжфюЮ✙✉ಥ✌و✁❦❧❤❃❂❁❀✿ﾟ➳εїз♧●♋✈ぷ¤♩✰♥➽〠〄㍿♝♞➴➵㊚㊛㊙℗♯♪♫♬♭♮☪♈º큐©►◄☼♦◊◘◙๑ิ❊❋❄❅❇✱✲✳✾✺✹✸✶✵✷❖❥☇☈☉☊☋☌☍☑☒☢☸☹☺☻☽☾‾◡♠♡♢♣♤۩۞｡◕☀☁☂☃☄❉★இ◎◇◆■△▲▽▼◁◀▷▶⊙◈▣◐◑▒▤▥▨▧▦▩〇♨☜☞¶↕↗↙↖↘↑↓↔→←▁▂▃▄▅▆▇█〒▓⊕Θ◣◢◥◤⊿┇┅─│┌┐┘└├┬┤┴┼━┃┏┓┛┗┣┳┫┻╋┠┯┨┷┿┝┰┥┸╂┒¬┑┚┙┖┕┎┍┞┟┡┢┦┧┩┪┭┮┱┲┵┶┹┺┽┾╀╁╃╄╅╆╇╈╉╊╝╚╔╬╗═╓╩皿дДづロ▽凸目口人切拜咕囧冏莔表エェ王益艹罒ㄏㄟPqXYㄒTUZzMmvrzuOo0ー]]{2,10}";
		String context = "boring( ˊ̱˂˃ˋ̱ ) ";
		//String context = "0(#‵′)0?_?6~_~6$_$0;-0*^_^*0:-0;-)0;-(0-_-#0:-)0-.-0:）0:-(0+_+0@_@0%>_<%0^_^0>_<0::>_<::0=_=0-_-!0-_-0-_-！0-_-||0#^_^#0>,<0>.<0^.^0^_^~0(^.^)0(^_^)0(-_-)0(-.-)0(:0(*^__^*)0(︶︿︶)0(～﹃～)0(>﹏<)0(>_<)0(･_･`)0~^_^~0•﹏•0(#‵′)0⊙﹏⊙0⊙▽⊙0￣▽￣0≧▽≦0╮(╯_╰)╭0(⊙_⊙)0(≧▽≦)/0(∩_∩)0(⊙_⊙)？0(╯▽╰)0〒_〒0╭(╯^╰)╮0╯▽╰0╮(╯▽╰)╭0╯﹏╰0↖(^ω^)↗0~(≧▽≦)/~0(*/ω＼*)0→_→0→.→0(→_→)0←_←0←.←0(←_←)";
		Pattern p_at = Pattern.compile(emotion_regex);
		Matcher m_at = p_at.matcher(context);
		while(m_at.find()){
			System.out.print(m_at.group());
		}
	}
	
	public static void test_regex_mstart() {
		String weibocon = "aacc4567bb";
		Pattern p = Pattern.compile("[a-z]{2}");
		Matcher m = p.matcher(weibocon);
		while(m.find()){
			String emo = m.group();
			int start = m.start();
			int end = m.end();
			System.out.println(emo+":"+start+":"+end);
		}
	}
	
	public static boolean isEnterprise() {
		int type = -1;//标记企业用户
		String screen_name = "飞扬博远（北京）公关顾问有限公司官方微博";
		String description = "阿根廷球迷俱乐部";
		
		Set<String> keyword = new HashSet<String>();
		keyword.add("官方微博");keyword.add("官方账号");keyword.add("官方帐号");keyword.add("官方围脖");
		keyword.add("事务所");keyword.add("工作室");keyword.add("俱乐部");
		keyword.add("公司");keyword.add("中心");keyword.add("协会");keyword.add("杂志");
		keyword.add("代购");keyword.add("空间");keyword.add("联盟");keyword.add("社区");
		keyword.add("平台");keyword.add("学院");keyword.add("代理");keyword.add("频道");
		if(type>0&&type<8){return true;}
		else if(contains(keyword,screen_name)){return true;}
		else if(contains(keyword,description)){return true;}
		return false;
	}
	private static boolean contains(Set<String> keyword, String str) {
		for(String key : keyword){
			if(str.contains(key)){
				System.out.println(key);
			}
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public static void formJSON(){
		String json = "{\"comment\":[]}";

		//String json = "{\"id\":\"1744238435\",\"total_number\":353,\"uids\":[\"2709599117\",\"1909830302\"]}";
		JSONObject uidinfo = JSONObject.fromObject(json);
		List<String> uidlist = (List<String>) uidinfo.get("uids");
		System.out.println(uidlist.size());
	}
	public static void main(String args[]) throws NumberFormatException, IOException{
		/*AtUser at = new AtUser();
		at.setUid("1123455");
		at.setAtNum(13);
		at.setReNum(45);
		at.setUname("test");
		String json = JsonUtils.beantojson(at);
		System.out.println(json);*/
		//isEnterprise();
		/*Set<String> keyword = new HashSet<String>();
		keyword.add("模特");keyword.add("演员");keyword.add("饰演");
		String str = "女演员，饰演蓉儿";
		contains(keyword,str);*/
		//formJSON();
		/*String str = "[36.66291,117.069664]";
		str = str.replaceAll("\\[|\\]", "");
		System.out.println(str);*/
		File r=new File("D:\\net_youtube.txt");
		BufferedReader br=new BufferedReader(new FileReader(r));
		File f = new File("D:\\net_youtube_small.txt");
		BufferedWriter w = new BufferedWriter(new FileWriter(f,false));
		String uid="";
		while((uid=br.readLine())!=null)
		{
			String[] items = uid.split("\\s");
			boolean flag = true;
			for(String item : items){
				int i = Integer.parseInt(item);
				if(i>500){
					flag = false;
					break;
				}
			}
			if(flag){
				w.write(uid+"\r\n");
			}
		}
		br.close();
		w.flush();
		w.close();
		
	}
}
