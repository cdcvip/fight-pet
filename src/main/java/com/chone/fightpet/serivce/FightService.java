package com.chone.fightpet.serivce;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.chone.fightpet.common.ConfigUtils;
import com.chone.fightpet.common.HttpUtils;
import com.chone.fightpet.pojo.*;
import com.chone.fightpet.selenium.LoginChrome;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Create 2021-01-05 14:23
 *
 * @author chone
 */
@Service
public class FightService extends HttpUtils {

    /**
     * 共有url前缀
     */
    private final static String PET_PK_CMD = "http://fight.pet.qq.com/cgi-bin/petpk?cmd=";
    private final static String PHONE_PK = "https://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?";
    /**
     * 是否隐藏详情
     */
    private static boolean HIDE_DETAILS = true;

    /**
     * 全局cookie
     */
    private static String GLOBAL_COOKIE;
//    private static final String GLOBAL_COOKIE = LoginChrome.loginQQ();
    /**
     * qq,s-key,pt4_token,ts_uid
     */
    private static String GLOBAL_QQ = "1282722653";
    private static String GLOBAL_S_KEY;
    private static String GLOBAL_PT4_TOKEN;
//    private static String GLOBAL_TS_UID;

    /**
     * 账号参数
     */
    private static String GLOBAL_LOG;
    /**
     * 等级、活力、体力、好友列表、帮派成员
     */
    private static String GLOBAL_LEVEL = "59";
    /**
     * 活力
     */
    private static String GLOBAL_ENERGY = "10";
    /**
     * 体力
     */
    private static Integer GLOBAL_PHYSICAL_STRENGTH = 10;
    /**
     * 好友列表、帮派成员
     */
    private static List<Uin> GLOBAL_FRIENDS;
    private static List<Uin> GLOBAL_MEMBER;

//    private static List<Map> GLOBAL_TU_DI;
    /**
     * 斗友列表
     */
    private static List<Uin> GLOBAL_STRANGER;
    /**
     * 好友_NPC、帮派_NPC
     */
    private static Map<String, String> GLOBAL_FRIENDS_NPC;

    private static Writer LOG_OUT;
    private static FileOutputStream LOG_FW;
    /**
     * config
     */
    private static final ConfigUtils CONFIG_UTILS = new ConfigUtils();
    private static final Properties CONFIG_PROPERTIES = CONFIG_UTILS.load();

    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String format = dateFormat.format(new Date());
        //写好输出位置文件
        String logPath = "C:\\app\\log\\" + format + ".log";
        File writeFile;
        try {
            writeFile = new File(logPath);
            // 如果文本文件不存在则创建它
            if (!writeFile.exists()) {
                File path = writeFile.getParentFile();
                if (!path.exists()) {
                    if (path.mkdirs()) {
                        System.out.println("The log path does not exist and has been created");
                    }
                }
                if (writeFile.createNewFile()) {
                    // 重新实例化
                    writeFile = new File(logPath);
                }
            }
            LOG_FW = new FileOutputStream(writeFile, true);
            LOG_OUT = new OutputStreamWriter(LOG_FW, StandardCharsets.UTF_8);

            // config
            String cookie = CONFIG_PROPERTIES.getProperty("cookie", "null");
            log("Load cookie", cookie);
            GLOBAL_COOKIE = cookie;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 正式驱动
     */
    public static void startUp() {
        // 是否显示日志
//        SHOW_DETAILS = showDetails;
        // 获取账号资源
        getAccountResources();
        // 解析cookie参数
        parseCookieParameters();
        // 武林大会
        martialArtsConference();
        // 斗神塔
        towerFight();
        // 画卷迷踪
        scrollDungeon();
        // 镖行天下
        cargo();
        // 习练兵法
        studyTheArtOfWar();
        // 使用经验木简
        useExperienceWoodenSlip();
        // 帮派-供奉守护神
        enshrineThePatronSaint();
        // 获取好友列表[1好友;3斗友]
        getFriendsList(1);
        getFriendsList(3);
        // 获取帮友
        viewMember();
        // 每日任务
        dailyTask();

        //手Q游戏一键加速
        oneClickAccelerationForMobileQQGames();
        // wzRy
        iwanKingOfGlory();

        // 登录游戏
        loginGames();
        // 领取登录礼包
        receiveLoginPackage();
        // 幸运企鹅
        luckyGoosePackage();
        // 斗友召回
        fightingFriendsRecall();

        // 好友挑战[乐斗]
        happyFriends();

        // 历练-获取佣兵
        experienceEarningMercenaries();
        // 领取徒弟礼包
        receiveApprenticeReward();

        // 梦之旅游
        dreamTrip();
        // 幻境
        mistyFantasy();

        // 门派-金顶切磋
        goldenSummit();
        // 门派-同门切磋
        learnFromTheSameDoor();
        // 门派-免费木桩
        freeStakes();
        // 门派-上香
        shangXianG();

        // 帮派-矿洞挑战
        mineChallenge();
        // 帮派-活跃150
        gangTotalActivePackage();
        // 历程分享
        historySharing();
        // 每日宝箱
        openTreasureChest();
        // 每日任务
        dailyTask();
        // 今日活跃礼包
        todaySActivityPack();

        // 获取账号资源
        getAccountResources();
        // 日志完成
        logClose();
    }

    /**
     * 测试驱动
     *
     * @param args x
     */
    public static void mainC(String[] args) {
        // 不隐藏详情
        HIDE_DETAILS = false;
        // 获取账号资源
        getAccountResources();
        // 解析cookie参数
        parseCookieParameters();

        // 获取好友列表[1好友;3斗友]
        getFriendsList(1);
        getFriendsList(3);

//        getGameInformation();
        // 佣兵
//        experienceEarningMercenaries();

        // 领取登录礼包
        //    receiveLoginPackage();
//        getAccountResources();
//        String html = doGet("https://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?cmd=index", GLOBAL_COOKIE);
        // 武林大会
        //https://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?zapp_uin=&B_UID=0&sid=&channel=0&g_ut=1&cmd=fastSignWulin&ifFirstSign=1

//        String petPkCmd = getPetPkCmd("dailychest&type=0&op=open");

//        System.out.println("petPkCmd = " + petPkCmd);

        //获取账号参数

        // getAccountResources();

        // 每日任务

        dailyTask();

        //佣兵

//        experienceEarningMercenaries();


        // 获取好友列表
//          getFriendsList();

        //画卷迷踪
//        scrollDungeon();

        // 镖行天下
//        cargo();

        // wzRy
//        iwanKingOfGlory();

//        startUp();

//        Set<String> strings = GLOBAL_FRIENDS_NPC.keySet();
//
//        for (String string : strings) {
//
//            String uinId = GLOBAL_FRIENDS_NPC.get(string);
//            String uinName = string;
//            System.out.println(uinId + " = " + uinName);
//        }

        //使用经验木简
//        useExperienceWoodenSlip();
        //帮派-供奉守护神
//        enshrineThePatronSaint();
        // 研习兵法
//        studyTheArtOfWar();

        //今日活跃度礼包
//        todaySActivityPack();


        // 日志完成
        logClose();
    }


    public static void mainF(String[] args) {
        // 天界十二宫
        String base_str = "zodiacdungeon&op=%s";
        // 查询=query
        ResultMsg result = getPetPkCmdResult(base_str, "query");
        Scene scene = result.getScene();
//        scene.


    }


    /**
     * 画卷迷踪
     * https://fight.pet.qq.com/cgi-bin/petpk?cmd=scroll_dungeon&op=fight&buff=0
     * 2：BOSS强削减选择该BUFF后,BOSS的血量削减50%
     * 3：BOSS勇削减选择该BUFF后,BOSS的血量削减30%
     */
    public static void scrollDungeon() {
        String baseStr = "scroll_dungeon&op=fight&buff=%d";
        String msg;
        int buffMark = 6;
        do {
            ResultMsg resultMsg = getPetPkCmdResult(String.format(baseStr, buffMark <= 0 ? 0 : buffMark--));
            //repid: "1282722653_9_6920168967944314302"
            String repId = resultMsg.getRepid();
            if (repId == null) {
                break;
            }
            int index = repId.lastIndexOf("_");
            repId = repId.substring(GLOBAL_QQ.length() + 1, index);
            msg = resultMsg.getMsg();
            log(String.format("画卷迷踪[%s]", repId), msg);
        } while (!msg.contains("弱爆了"));
    }

    /**
     * 武林大会
     * https://fight.pet.qq.com/cgi-bin/petpk?cmd=signup&ifFirstSign=1&id=35500309
     * 50↓ 60↓ 70↓ 80↓ 90↓ 100↓ 110↓ 120↓ 130↓ 140↓ 150↓ 160↓ 无限制
     * https://fight.pet.qq.com/cgi-bin/petpk?cmd=showwulin&level=3
     */
    private static void martialArtsConference() {
        String level = GLOBAL_LEVEL.substring(0, GLOBAL_LEVEL.length() - 1);
        int levelInt = Integer.parseInt(level);
        String levelStr = String.format("showwulin&level=%d", (levelInt - 3));
        String json = getPetPkCmd(levelStr);
        ResultMsg result = JSONObject.parseObject(json, ResultMsg.class);
        Uin uin = result.getInfo().get(2);
        String id = uin.getId();
        getPetPkCmd("signup&ifFirstSign=1&id=" + id, String.format("武林大会[%s]", id));
    }

    /**
     * 每日宝箱
     * [铜]0 [银]1 [金]2
     * 钥匙碎片不足哦！[需要钥匙碎片]
     */
    private static void openTreasureChest() {
        Map<Integer, String> hashMap = new HashMap<>(3);
        hashMap.put(0, "铜");
        hashMap.put(1, "银");
        hashMap.put(2, "金");
        String baseStr = "dailychest&type=%d&op=open";
        for (Integer key : hashMap.keySet()) {
            getPetPkCmd(String.format(baseStr, key),
                    String.format("每日宝箱[%s]", hashMap.get(key)));
        }
    }

    /**
     * 获取账号资源
     */
    public static void getAccountResources() {
        boolean flag = false;
        do {
            if (flag) {
                GLOBAL_COOKIE = LoginChrome.loginQQ();
            }
            String echo = getPetPkCmd(String.format("visit&puin=%s&kind=1", GLOBAL_QQ), "获取游戏信息");
            flag = echo.contains("失败");

        } while (flag);

        String html = getPhonePk("cmd=index");
        Document doc = Jsoup.parse(html);
        if (doc == null) {
            log("DOC", "IS NULL");
            return;
        }

        String info = doc.getElementById("id")
                .getElementsByTag("p")
                .get(0).text();
//        System.out.println(info);
        parsingParameters(info);
    }

    public static void loadInformation() {
        Map<String, String> result;
        boolean flag = false;
        do {
            if (flag) {
                GLOBAL_COOKIE = LoginChrome.loginQQ();
            }
            String json = getPetPkCmd(String.format("visit&puin=%s&kind=1", GLOBAL_QQ), "获取游戏信息");
//            @SuppressWarnings("unchecked")
            result = JSONObject.parseObject(json, Map.class);
            String msg = result.get("msg");
            flag = msg.contains("失败");
        } while (flag);

//    基础信息
        String base_info = result.get("baseinfo");
        @SuppressWarnings("unchecked")
        Map<String, String> info = JSONObject.parseObject(base_info, Map.class);
        // 徒弟
        String tuDi = result.get("tudi");
        GLOBAL_LEVEL = info.get("lilian");
        GLOBAL_ENERGY = info.get("energy");
        GLOBAL_PHYSICAL_STRENGTH = Integer.parseInt(info.get("sp"));

    }


    /**
     * 解析账号信息
     *
     * @param origin data
     */
    private static void parsingParameters(String origin) {
        String[] conf = {"斗豆", "斗币", "鹅币", "今日活跃度", "等级", "体力", "活力", "阅历", "战斗力"};
//        Map<String, String> hashMap = new HashMap<>();
        StringBuilder one = new StringBuilder();
        String text = origin;
        for (String str : conf) {
//            System.out.println("text = " + text);
            int index;
            if ("斗豆".equals(str)) {
                index = text.lastIndexOf(str);
            } else {
                index = text.indexOf(str);
            }
            index += str.length() + 1;
            text = text.substring(index);
            String value = text.split(" ")[0];
            String other = text.split("\\(")[0];
            if (value.length() > other.length()) {
                value = other;
            }
//            hashMap.put(str, value);
            // start
            switch (str) {
                case "体力":
                    GLOBAL_PHYSICAL_STRENGTH = Integer.parseInt(value.split("/")[0]);
                    break;
                case "活力":
                    GLOBAL_ENERGY = value.split("/")[0];
                    break;
                case "等级":
                    GLOBAL_LEVEL = value;
                    break;
            }

            String format = String.format("%s[%s] ", str, value);
//            System.out.print(format);
            one.append(format);
        }
        if (!StringUtils.hasLength(GLOBAL_LOG)) {
            GLOBAL_LOG = "x" + one.toString();
        } else {
            log("", GLOBAL_LOG);
        }
        log("", "o" + one.toString());

    }

    /**
     * 斗神塔
     * 结束：https://fight.pet.qq.com/cgi-bin/petpk?cmd=towerfight&type=7
     * {"result":"0","msg":"您结束了斗神塔的挑战"}
     * 开始：https://fight.pet.qq.com/cgi-bin/petpk?cmd=towerfight&type=0
     * ....
     * 自动：https://fight.pet.qq.com/cgi-bin/petpk?cmd=towerfight&type=1
     * {"result":"0","msg":"挑战将在1分钟后自动开始"}
     */
    public static void towerFight() {
        String baseStr = "towerfight&type=%d";
        // 结束
        getPetPkCmd(String.format(baseStr, 7), "斗神塔");
        // 开始
        getPetPkCmd(String.format(baseStr, 0));
        // 自动
        getPetPkCmd(String.format(baseStr, 1), "斗神塔");
    }

    /**
     * 镖行天下
     */
    public static void cargo() {
        String baseStr = "cargo&op=%d";
        String msgStr = "押镖次数不足";
        // 镖行结算
        getPetPkCmdResult(String.format(baseStr, 16), "镖行结算");
        // 开始押镖[0]
//        ResultMsg on =
        getPetPkCmdResult(String.format(baseStr, 7));
//        String npcId = "0";//= on.getNpc_id();
        for (int i = 0; i < 3; i++) {
            // 刷新镖师
            ResultMsg refresh = getPetPkCmdResult(String.format(baseStr, 8));
            String msg = refresh.getMsg();
            if (msg.contains(msgStr)) {
                return;
            }
            String npcId = refresh.getNpc_id();
            log(String.format("刷新镖师[%s]", npcId), msg);
            if (!"0".equals(npcId)) {
                break;
            }
        }
        // 护送镖车
        ResultMsg start = getPetPkCmdResult(String.format(baseStr, 6));
        log("镖行天下", start.getMsg());

    }


    /**
     * 兵法研习
     * {"base_id":"2544","level":"5","progress":"21","cost":"2","current_desc":"生命总共增加12点","next_desc":"生命总共增加16点"},
     * {"base_id":"2570","level":"10","progress":"75","cost":"4","current_desc":"第一回合释放，造成36点伤害","next_desc":"第一回合释放，造成42点伤害"},
     * {"base_id":"21001","level":"7","progress":"81","cost":"4","current_desc":"暴击几率增加7%","next_desc":"暴击几率增加8%"},
     * {"base_id":"21032","level":"6","progress":"26","cost":"4","current_desc":"受到暴击几率降低6%","next_desc":"受到暴击几率降低7%"}
     */
    public static void studyTheArtOfWar() {
        // 获取兵法详情
        //  ResultMsg resultMsg = getPetPkCmdResult("brofight&subtype=5");
        //  List<Skill> skills = resultMsg.getSkills();
        //  for (Skill skill : skills) {
        //      System.out.println(skill.getBase_id()+" = " + skill.getLevel());
        //  }

        String[] ids = {"2570", "2544"};
        for (String id : ids) {
            String param = String.format("brofight&skill_base_id=%s&subtype=4", id);
            String json = getPetPkCmd(param);
            //{"result":"0","msg":"2","artOf_war_count":"374","wumu_yishu_count":"577","levelup":"0"}
            //{"result":"0","msg":"2","art_of_war_count":"374","wumu_yishu_count":"573","levelup":"0"}
            log(String.format("兵法研习[%s]", id), json);
        }

    }

    /**
     * 每日任务
     */
    private static void dailyTask() {
        String json = getPetPkCmd("task&sub=1&page=0&selfuin=" + GLOBAL_QQ);
        ResultMsg result = JSONObject.parseObject(json, ResultMsg.class);
        List<Task> taskList = result.getTasklist();

        for (Task task : taskList) {
            String id = task.getId();
            int status = Integer.parseInt(task.getStatus());
            String desc = task.getDesc();
            Set<String> npcNameS = GLOBAL_FRIENDS_NPC.keySet();
            if (2 == status) {
                // 读取任务-内容[乐斗任务]

                //与[俊猴王]乐斗并战胜他！
                for (String npcName : npcNameS) {
                    String name;
                    if (npcName.contains(NPC_SIGN)){
//                    System.out.println("npcName = " + npcName);
                     name  = npcName.split(NPC_SIGN)[1];
                     name = name.replaceAll("乐斗", "");
                    }else {
                        name=npcName;
                    }
                    if (desc.contains(name)) {
//                        System.out.println("—————————斗乐—————————");
                        String npcId = GLOBAL_FRIENDS_NPC.get(npcName);
                        happyFriend(npcId, npcName);
                        status = 3;
                    }
                }
                // 挑战三次陌生人！
                if (desc.contains("挑战三次陌生人")) {
                    // 帮友 GLOBAL_STRANGER
                    int size = GLOBAL_STRANGER.size();
                    int stats = 3;
                    for (int i = 1; i <= stats; i++) {
                        Uin uin = GLOBAL_STRANGER.get(size - i);
                        String uinId = uin.getUin();
//                    String uinName = uin.getName();
//                    System.out.println(uinId + " = " + uinName);
                        String echo = happyFriend(uinId, null);
                        if (GLOBAL_QQ.equals(echo)) {
                            stats++;
                        }
                    }
                    status = 3;
                }
                //[118]研习任意兵法一次。 [2]
                // 已转为每次执行
                // if (desc.contains("兵法")) {
                //     studyTheArtOfWar();
                // }


                // todo 每日任务-代码需优化
            }

            if (3 == status) {
                String taskJson = getPetPkCmd(String.format("task&sub=4&id=%s&selfuin=%s", id, GLOBAL_QQ));
                ResultMsg taskResult = JSONObject.parseObject(taskJson, ResultMsg.class);
                log(String.format("%s[%s]", desc, id), taskResult.getAward());
            } else if (2 == status) {
                // 任务详情
                log("", String.format("[%s]%s %d", id, desc, status));
            }
        }

    }

    //todo 传功
    /*
    传功-get
    https://fight.pet.qq.com/cgi-bin/petpk？cmd=intfmerid&sub=4&master=9004
    {"result":"0","cgf":"3557","mark":"0","next_master":"9004","free_msg":"","id":"119",
    "intf_type":"2","intf_name":"天耳通","level":"2","intf_effect":"中型穿透|+2.0%","cur_cult":"90",
    "min_cult":"90","max_cult":"270","intf_attr_array":[{"attr_type":"36","attr_value":"20"}]}
    收取-https://fight.pet.qq.com/cgi-bin/petpk?cmd=intfmerid
     */

    /**
     * 解析Cookie参数
     */
    private static void parseCookieParameters() {
        String[] keyValues = GLOBAL_COOKIE.split(";");
        Map<String, String> qqInfo = new HashMap<>(keyValues.length);
        for (String keyValue : keyValues) {
            String[] str = keyValue.split("=");
            qqInfo.put(str[0].trim(), str[1]);
        }
        GLOBAL_QQ = qqInfo.get("uin").substring(1);
        if (!"1282722653".equals(GLOBAL_QQ)) {
            log("确认过眼神不是正确的人", GLOBAL_QQ);
            System.exit(0);
        }
        GLOBAL_S_KEY = qqInfo.get("skey");
        GLOBAL_PT4_TOKEN = qqInfo.get("pt4_token");
//        GLOBAL_TS_UID = qqInfo.get("ts_uid");

        log("GlobalQQ", GLOBAL_QQ);
        getGameInformation();

    }

    /**
     * 领取王者荣耀礼包
     */
    public static void iwanKingOfGlory() {
        String url = "https://iwan.qq.com/api/v5/exchange?gid=15747%2C15748%2C15745%2C15744%2C15742%2C15741%2C15740%2C15746&cdkey=&shopid=&adtag=&plat=1&device=&guid=&fsq=1&sPlat=1&sArea=1&serverId=1050&sRoleId=2F6F2FF3BBE8250BEEB977EEE9A700F5&extFields=&platformid=5&platformId=5&token=779236298&acctype=qq&_=1612344440351";
        String json = doGet(url, GLOBAL_COOKIE);
        ResultMsg resultMsg = JSONObject.parseObject(json, ResultMsg.class);
        List<WzRyData> wzRyData = resultMsg.getData();
        for (WzRyData wzRyDatum : wzRyData) {
            if (wzRyDatum.getCode() <= 100011) {
                log(String.format("王者荣耀礼包[%d]", wzRyDatum.getCode()),
                        String.format("[%s]%s", wzRyDatum.getGIntro(), wzRyDatum.getMsg()));
            } else {
                break;
            }
        }
    }


    /**
     * 手Q游戏一键加速
     * 0.2天活跃度
     */
    public static void oneClickAccelerationForMobileQQGames() {
        String url = "https://api.uomg.com/api/qq.game?qq="
                + GLOBAL_QQ + "&skey=" + GLOBAL_S_KEY + "&pt4_token=" + GLOBAL_PT4_TOKEN;
        System.out.println("url = " + url);
        getPetPkCmd(url, "手Q游戏一键加速");

    }

    /**
     * 获取帮友列表
     */
    public static void viewMember() {
        getMembers(viewMember, 8);
    }

    /**
     * 获取帮友列表参数
     */
    private static final String viewMember = "viewmember";
    /**
     * 是否为好友列表NPC标记
     */
    private static final String NPC_SIGN = "p:";

    /**
     * 解析成员
     *
     * @param arg   好友/帮派
     * @param count npc个数
     */
    public static void getMembers(String arg, int count) {
        String json = getPetPkCmd(arg);
        ResultMsg result = JSONObject.parseObject(json, ResultMsg.class);
        // 是否为帮友列表
        boolean isMember = viewMember.equals(arg);
        // 缓存成员
        List<Uin> uinList;
        // 为好友列表做标记
        String sign = "";
        if (isMember) {
            uinList = result.getList();
        } else {
            uinList = result.getInfo();
            sign = NPC_SIGN;
        }
        // 缓存NPC
        Map<String, String> npcMap = new HashMap<>(count);
        for (int i = 0; i < count; i++) {
            Uin uin = uinList.remove(0);
            npcMap.put(sign + uin.getName(), uin.getUin());
//            System.out.println(uin.getName() + " = " + uin.getUin());
        }
        // 将对应的数据存储
        if (isMember) {
            GLOBAL_MEMBER = uinList;
        } else {
            if (count != 0) {
                GLOBAL_FRIENDS = uinList;
            } else {
                GLOBAL_STRANGER = uinList;
            }
        }
        // 将好友与帮友NPC合并
        if (GLOBAL_FRIENDS_NPC == null) {
            GLOBAL_FRIENDS_NPC = npcMap;
        } else {
            GLOBAL_FRIENDS_NPC.putAll(npcMap);
        }
    }

    /**
     * 获取好友列表
     * sub=1[1好友;3斗友]
     *
     * @param sub [1好友;3斗友]
     */
    public static void getFriendsList(int sub) {
        String str = String.format("view&kind=1&sub=%d&selfuin=%s", sub, GLOBAL_QQ);
        if (sub == 1) {
            getMembers(str, 7);
        } else {
            getMembers(str, 0);
        }
    }

    /**
     * 好友乐斗
     *
     * @param uin  qq
     * @param name 昵称[非必填]
     * @return echo [self]qq
     */
    public static String happyFriend(String uin, String name) {
        if (!StringUtils.hasLength(uin)) {
            throw new NullPointerException("uin not is null");
        }
//        String param;
        StringBuilder param = new StringBuilder("fight&");

        if (uin.equals(GLOBAL_QQ)) {
            return GLOBAL_QQ;
        }
        // fight&uin=4&puin[好友]/uin[帮友]
        // fight&type=4&puin=1842567252
        if (name == null) {
            param.append("type=4&puin=");
        } else if (name.startsWith(NPC_SIGN)) {
            param.append("puin=");
        } else {
            param.append("uin=");
        }
        param.append(uin);
        return getPetPkCmd(param.toString(), String.format("乐斗[%s]", name == null ? uin : name));
    }

    /**
     * 好友乐斗列表
     */
    public static void happyFriends() {
        // 不斗列表
        List<String> noFighting = new ArrayList<>();
        // 不斗自己
        noFighting.add(GLOBAL_QQ);
        noFighting.add("1060277949");
        // 您已经太累了
        String endStr = "您已经太累了";
        int physical = GLOBAL_PHYSICAL_STRENGTH / 10;
//        physical += 9;
        // 前7个都是NPC
        for (int i = 0; i < physical; i++) {
            Uin uin = GLOBAL_FRIENDS.get(i);
            String qq = uin.getUin();
            // 不和这Q打
            if (noFighting.contains(qq)) {
                physical++;
                continue;
            }
            // System.out.printf("QQ[%s] [%s] 力量[%s]%n", qq, uin.getName(),uin.getLilian());
            String echo = happyFriend(qq, uin.getName());
            if (echo.contains("请明天再来挑战吧")) {
                physical++;
                continue;
            }
            if (echo.contains(endStr)) {
                break;
            }
            GLOBAL_PHYSICAL_STRENGTH -= 10;
        }
    }

    /**
     * 历练-获取佣兵 [取值区间=>20±]
     * 6194[赵  敏]√
     * 6174[韦一笑]
     * 6154[鹤笔翁]
     * 6134[扫地僧]√
     * 6114[韦小宝]√
     * 6094[小龙女]√
     * 6074[丘处机]
     * 6054[丁春秋]√
     * 6034[令狐冲]x
     */
    public static void experienceEarningMercenaries() {
        //该NPC今日挑战次数已完，请明日再来挑战该NPC。
        //乐斗助手帮你使用了活力药水，你的活力增加了你使用了活力药水，活力增加了30！!
        int start = 6094;
        String param = "mappush&type=1&npcid=%d";
        String endStr = "该NPC";
//        String resStr;
        int i = Integer.parseInt(GLOBAL_ENERGY) / 10;
        do {
            String resStr = getPetPkCmd(String.format(param, start),
                    String.format("历练-获取佣兵[%d]", start));
            if (resStr.contains(endStr)) {
                start -= 20;
            } else {
                i--;
            }
        } while (i > 0);

    }

    /**
     * 获取游戏信息
     * https://fight.pet.qq.com/cgi-bin/petpk?cmd=ledouvip
     * {"result":"0","msg":"","lvl":"7","score":"13555","speed":"15","day":"102"..}
     */
    public static void getGameInformation() {
        String json = getPetPkCmd("ledouvip");
        ResultMsg result = JSONObject.parseObject(json, ResultMsg.class);
        log("",
                String.format("vip-[%s] score[%s][↑ %s/day] last[%s day]"
                        , result.getLvl(), result.getScore(), result.getSpeed(), result.getDay()));
    }

    /**
     * 斗友召回
     */
    public static void fightingFriendsRecall() {
        String base = "callback&subtype=4&opuin=%s";
        String parameterLimit = "召唤3次";
        String json = getPetPkCmd(String.format(base, GLOBAL_QQ));
        ResultMsg result = JSONObject.parseObject(json, ResultMsg.class);
        List<Uin> uinList = result.getCanbecall();
        for (Uin uin : uinList) {
            String msg = getPetPkCmd(String.format(base, uin.getUin()),
                    String.format("斗友召回[%s]", uin.getName()));
            //System.out.println("uin = " + uin.getUin());
            if (msg.contains(parameterLimit)) {
                break;
            }

        }
    }

    /**
     * 历程分享
     */
    public static void historySharing() {
        String parma = "shareinfo&subtype=1&shareinfo=%d";
        int sharingParameters = 10;
        for (int i = 1; i <= sharingParameters; i++) {
            getPetPkCmd(String.format(parma, i), String.format("历程分享[%d]", i));
        }
    }

    /**
     * 梦想之旅
     * sub=1: 普通旅游 2 未知
     */
    public static void dreamTrip() {
        getPetPkCmd("dreamtrip&sub=1&smapid=0", "梦想之旅");
    }

    /**
     * 飘渺幻境
     */
    public static void mistyFantasy() {
        String base = "misty&op=%s";
        String timesHaveBeenUsedUp = "次数已用完";
        String startStr = getPetPkCmd(String.format(base, "start&stage_id=1"), "");
        if (startStr.contains(timesHaveBeenUsedUp)) {
            return;
        }
        for (int i = 0; i < 5; i++) {
            getPetPkCmd(String.format(base, "fight"), "飘渺幻境-挑战");
        }
        getPetPkCmd(String.format(base, "return"));

    }

    /**
     * 门派-金顶切磋
     */
    public static void goldenSummit() {
        String parma = "sect&op=trainingwithcouncil&pos=%d&rank=%d";
        String str = "门派-金顶切磋[%d][%d]";
        for (int i = 1; i <= 3; i++) {
            for (int l = 1; l <= i; l++) {
                getPetPkCmd(String.format(parma, l, i), String.format(str, i, l));
                if (l == 3) {
                    l++;
                    getPetPkCmd(String.format(parma, l, i), String.format(str, i, l));
                }
            }
        }

    }

    /**
     * 领取徒弟奖励
     */
    public static void receiveApprenticeReward() {
        getPetPkCmd("getexp", "领取徒弟奖励");
    }

    /**
     * 门派-同门切磋
     */
    public static void learnFromTheSameDoor() {
        getPetPkCmd("sect&op=trainwithmember", "门派-同门切磋");
    }

    /**
     * 帮派-总活跃礼包
     * 达到150活跃值才行
     */
    public static void gangTotalActivePackage() {
        getPetPkCmd("factionOp&subtype=4", "帮派-总活跃礼包");
    }

    /**
     * 供奉守护神[物品id]
     * {"result":"0","msg":"供奉成功，守护神获得1.6点饥饿度。同时获得4贡献度"}
     */
    public static void enshrineThePatronSaint() {
        getPetPkCmd("feeddemo&id=3038", "帮派-供奉守护神");
    }

    /**
     * 使用经验木简[物品id]
     * cmd=use&selfuin=1282722653&id=3178
     * {"result":"0","msg":"使用经验木简成功","goods_dt":[{"id":"3178","num":"-1"}]}
     */
    public static void useExperienceWoodenSlip() {
        String param = String.format("use&selfuin=%s&id=3178", GLOBAL_QQ);
        for (int i = 1; i <= 10; i++) {
            String echo = getPetPkCmd(param, String.format("使用经验木简[%d]", i));
            //该物品今天已经不能再使用了
            if (echo.contains("不能再使用了")) {
                break;
            }
        }
    }


    /**
     * 帮派-矿洞挑战
     */
    public static void mineChallenge() {
        // 获取战况
        String baseStr = "factionmine";
        ResultMsg battle = getPetPkCmdResult(baseStr);
        String rewardMessage = battle.getReward_message();
        // 战斗没结算没有消息内容
        if (StringUtils.hasLength(rewardMessage)) {
            // 副本通关 领取奖励成功！{"result":"0","msg":"领取奖励成功！"}
            getPetPkCmdResult(baseStr + "&op=reward");
        }
        // 可挑战3次
        int numberOfChallenges = 3;
        for (int i = 0; i < numberOfChallenges; i++) {
            ResultMsg result = getPetPkCmdResult(baseStr + "&op=fight");
            String resultStr = result.getResult();
            if ("0".equals(resultStr)) {
                resultStr = "Succeed";
            }
            log(String.format("矿洞挑战[%d]", i), resultStr);
        }

    }

    /**
     * 门派-免费木桩
     */
    public static void freeStakes() {
        getPetPkCmd("sect&op=trainwithnpc", "门派-免费木桩");
    }

    /**
     * 门派-上香
     */
    public static void shangXianG() {
        String param = "sect&type=free&op=fumigate";
        getPetPkCmd(param, "门派-上香");

    }

    /**
     * 领取登录礼包
     */
    public static void receiveLoginPackage() {
        String[] str = {"login", "meridian", "daren", "wuzitianshu"};
        for (String text : str) {
            String param = "dailygift&op=draw&key=" + text;
            getPetPkCmd(param, String.format("领取登录礼包[%s]", text));

        }

    }

    /**
     * 登陆游戏
     */
    public static void loginGames() {
        String login = "登陆校验失败";
        String res = getPetPkCmd("limit&op=login", "登陆游戏");
        if (login.equals(res)) {
            throw new RuntimeException(login);
        }
    }

    /**
     * 今日活跃度礼包
     * 1,2,3,4(对应活跃度20，50，80，115)
     */
    public static void todaySActivityPack() {
        int activityLevel = 4;
        String incompatible = "不符合条件";
        for (int i = 1; i <= activityLevel; i++) {
            String param = "liveness&giftbagid=" + i + "&action=1";
            String msg = getPetPkCmd(param, "今日活跃度礼包");
            if (msg.contains(incompatible)) {
                break;
            }
        }
    }

    //周周礼包    http://fight.pet.qq.com/cgi-bin/petpk?cmd=weekgiftbag&sub=1&id=
    // 1-7天（个）可能会成功一个
    public static void weeklyPackage() {
        int weekInt = 7;
        //String notTimeStr = "不在领奖";
        String url = "weekgiftbag&sub=1&id=";
        for (int i = 1; i <= weekInt; i++) {
            String msg = getPetPkCmd(url + i, "周周礼包");
            log("msg", msg);
        }

    }

    /**
     * 幸运鹅礼包
     * {"result":"-1","msg":"礼包已经领取过了！",...}
     */
    public static void luckyGoosePackage() {
        int weekInt = 7;
        String notTimeStr = "不在领奖";
        String notDaysStr = "天数不够";
        String url = "betogoaway&gb_id=";
        for (int i = 1; i <= weekInt; i++) {
            String msg = getPetPkCmd(url + i, String.format("幸运鹅礼包[%d]", i));
            // 不在领奖时间内！
            if (msg.contains(notTimeStr)
                    || msg.contains(notDaysStr)) {
                break;
            }
            //System.out.println("==>幸运鹅礼包 = " + resultStr);
        }
    }

    /********************************************************************
     * 基础请求扩展Msg
     *
     * @param param 参数
     * @param info  详情
     * @return msg
     */
    private static String getPetPkCmd(String param, String info) {
        String json = getPetPkCmd(param);
//        System.out.println("json = " + json);
        ResultMsg resultMsg = null;
        try {
            resultMsg = JSONObject.parseObject(json, ResultMsg.class);
        } catch (JSONException e) {
            System.out.println("fastjson-failure");
        }
        String msg;
        if (resultMsg != null) {
            msg = resultMsg.getMsg();
//            System.out.println("msg = " + msg);
            // msg 消息为空就使用源
            if (!StringUtils.hasLength(msg)) {
                msg = resultMsg.getDrop();
                if (!StringUtils.hasLength(msg)) {
                    if (HIDE_DETAILS && json.length() > 50) {
                        msg = "Succeed.[Omit]";
                    } else {
                        msg = json;
                    }
                }
            }
        } else {
            msg = "error";
        }
        if (!"".equals(info)) {
            log(info, msg);
        }
        return msg;
    }

    /**
     * 基础请求扩展-result
     *
     * @param param 参数
     * @param info  详情
     * @return ResultMsg
     */
    private static ResultMsg getPetPkCmdResult(String param, String info) {
        ResultMsg resultMsg = getPetPkCmdResult(param);
        if (!"".equals(info)) {
            log(info, resultMsg.getMsg());
        }
        return resultMsg;
    }

    private static ResultMsg getPetPkCmdResult(String param) {
        String json = getPetPkCmd(param);
        return JSONObject.parseObject(json, ResultMsg.class);
    }

    /**
     * 基础请求-origin
     *
     * @param param 参数
     * @return origin
     */
    private static String getPetPkCmd(String param) {
        String url = PET_PK_CMD + param;
        if (param.startsWith("http")) {
            url = param;
            //  cookie = "mode=test";
        }
        //  System.out.printf("URL[%s]%n", url);
        return doGet(url, GLOBAL_COOKIE);
    }

    /**
     * 文字版
     *
     * @param param 参数
     * @return res
     */
    public static String getPhonePk(String param) {
        String url = PHONE_PK + param;
        return doGet(url, GLOBAL_COOKIE);
    }

    private static void log(String head, String detail) {
        String text = head + " => " + detail;
        System.out.println(text);
        try {
            LOG_OUT.write(text);
            //写入换行
            LOG_OUT.write(System.getProperty("line.separator"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logClose() {
        try {
            LOG_OUT.close();
            LOG_FW.flush();
            LOG_FW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 保存配置
        CONFIG_PROPERTIES.setProperty("cookie", GLOBAL_COOKIE);
        CONFIG_UTILS.store(CONFIG_PROPERTIES);

    }

}
