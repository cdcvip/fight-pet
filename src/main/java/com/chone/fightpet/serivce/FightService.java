package com.chone.fightpet.serivce;

import com.alibaba.fastjson.JSONObject;
import com.chone.fightpet.common.HttpUtils;
import com.chone.fightpet.pojo.ResultMsg;
import com.chone.fightpet.pojo.Uin;
import com.chone.fightpet.selenium.LoginChrome;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import us.codecraft.xsoup.Xsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create 2021-01-05 14:23
 *
 * @author chone
 */
@Service
@Slf4j
public class FightService extends HttpUtils {

    /**
     * 共有url前缀
     */
    private final static String PET_PK_CMD = "http://fight.pet.qq.com/cgi-bin/petpk?cmd=";

    /**
     * 全局cookie
     */
    private final static String GLOBAL_COOKIE = "_qpsvr_localtk=0.046588027542936006; uin=o1282722653; skey=@2oi2gPkZf; RK=uvQtAiwbUw; ptcz=cb93de5fea8d2c2e830d8a1961e86eeea5cc7d7a3a6272bb07adcd16ce4468bd";
//       private static final String GLOBAL_COOKIE = LoginChrome.loginQQ();
    /**
     * qq,s-key,pt4_token,ts_uid
     */
    private static String GLOBAL_QQ;
    private static String GLOBAL_S_KEY;
    private static String GLOBAL_PT4_TOKEN;
    private static String GLOBAL_TS_UID;

    /**
     * 正式驱动
     *
     * @param args x
     */
    public static void mainZ(String[] args) {
        // 解析cookie参数
        parseCookieParameters();

        //手Q游戏一键加速
        oneClickAccelerationForMobileQQGames();

        // 登录游戏
        loginGames();
        // 领取登录礼包
        receiveLoginPackage();
        // 幸运企鹅
        luckyGoosePackage();
        // 斗友召回
        fightingFriendsRecall();
        // 获取好友列表并挑战[乐斗]
        getFriendsList();
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
        // 今日活跃礼包
        todaySActivityPack();
        // 历程分享
        historySharing();

    }

    /**
     * 测试驱动
     *
     * @param args x
     */
    public static void main(String[] args) {
        // 解析cookie参数
//        parseCookieParameters();

//        getGameInformation();
        // 佣兵
//           experienceEarningMercenaries();

        // 领取登录礼包
        //    receiveLoginPackage();

        //https://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?cmd=index

        String html = doGet("https://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?cmd=index", GLOBAL_COOKIE);
        Document doc = Jsoup.parse(html);
//        System.out.println("html = " + html);
        //*[@id="id"]/p[1]/text()[51]
//        String info = Xsoup.compile("//*[@id=\"id\"]/p[1]/text()").evaluate(doc).get();

        String info = doc.getElementById("id")
                .getElementsByTag("p")
                .get(0).text();

//        info= info.replace("|","").replaceAll("\\s*","");
//
//        // 斗豆:
//        String[] one = info.split("斗豆:");
//        String[] two = one[1].split("斗币:");
//        String douDou = two[0];
//        String[] three = two[1].split("鹅币：");
//        // 斗币:
//        String bucketOfCoins = three[0];
//        // 鹅币：
//        String gooseCoin = three[0];


///html/body/div/p[1]/text()[50]
//        Element id = doc.getElementById("id");
//        Element a = id.getElementsByTag("p").get(0);
//        Element aTag = a.getElementsByTag("a").get(76);


//*[@id="id"]/p[1]/text()[50]
        System.out.println(info);
//        System.out.println(a.text());

        ////*[@id="id"]/p[1]/a[76]


//        System.out.println("get = " + html);


    }

    private static void parsingParameters(String info) {
        String[] conf = {"斗豆", "斗币", "鹅币"};

        String[] one = info.split("斗豆:");
    }


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
        GLOBAL_S_KEY = qqInfo.get("skey");
        GLOBAL_PT4_TOKEN = qqInfo.get("pt4_token");
        GLOBAL_TS_UID = qqInfo.get("ts_uid");

        System.out.printf("GlobalQQ[%s] ", GLOBAL_QQ);
        getGameInformation();

    }

    /**
     * 手Q游戏一键加速
     * 0.2天活跃度
     */
    public static void oneClickAccelerationForMobileQQGames() {
        String url = "https://api.uomg.com/api/qq.game?qq="
                + GLOBAL_QQ + "&skey=" + GLOBAL_S_KEY + "&pt4_token=" + GLOBAL_PT4_TOKEN;
        getPetPkCmd(url, "手Q游戏一键加速");

    }

    /**
     * QQ等级一键加速
     * 0.4天活跃度（运动，游戏）
     */
    public static void qqLevelOneKeyAcceleration() {
        //https://api.uomg.com/api/qq.level?uid=o
        // 0774740085
        // &superkey=N1uRqz44AvvbugGATkpv4mQJubx4-p19DuFC5AFGMAU_&supertoken=1660483393

        String url = "https://api.uomg.com/api/qq.level?uid=o";
        url += GLOBAL_QQ;
        url += "&qq=" + GLOBAL_QQ;
        url += "&skey=" + GLOBAL_S_KEY;
        url += "&pt4_token=" + GLOBAL_PT4_TOKEN;
        url += "&supertoken=" + GLOBAL_PT4_TOKEN;
        url += "&superkey=" + GLOBAL_S_KEY;
        url += "&p_skey=" + GLOBAL_TS_UID;

        System.out.println("url = " + "https://api.uomg.com/api/qq.level?uid=o0774740085&superkey=N1uRqz44AvvbugGATkpv4mQJubx4-p19DuFC5AFGMAU_&supertoken=1660483393");
        System.out.println("url = " + url);
        String doGet = doGet(url, "mode=test");
        System.out.println("doGet = " + doGet);

    }

    /**
     * 获取好友列表
     * https://fight.pet.qq.com/cgi-bin/petpk?cmd=view&kind=1&sub=1&selfuin=我的QQ
     * {"result":"0","msg":"","pullflag":"0",
     * "info":[{"uin":"33","sect":"0","petflag":"0","qzoneflag":"0",
     * "qqvipflag":"0","qgameflag":"0","name":"金毛鹅王","lilian":"69",
     * "enable":"1","sex":"1","factionid":"0"},....]}
     * 乐斗好友
     * https://fight.pet.qq.com/cgi-bin/petpk?cmd=fight&puin=对方QQ[uin]
     */

    public static void getFriendsList() {
        String json = getPetPkCmd("view&kind=1&sub=1&selfuin=" + GLOBAL_QQ);
        ResultMsg result = JSONObject.parseObject(json, ResultMsg.class);
        List<Uin> info = result.getInfo();
        // 不斗列表
        List<String> noFighting = new ArrayList<>();
        // 不斗自己
        noFighting.add(GLOBAL_QQ);
        noFighting.add("1060277949");

        // 您已经太累了
        String endStr = "您已经太累了";
        // 前7个都是NPC
        for (int i = 9; i <= 20; i++) {
            Uin uin = info.get(i);
            String qq = uin.getUin();
            // 不和这个人打
            if (noFighting.contains(qq)) {
                continue;
            }
            // System.out.printf("QQ[%s] [%s] 力量[%s]%n",  qq, uin.getName(),uin.getLilian());
            String echo = happyFriends(qq, uin.getName());
            if (echo.contains(endStr)) {
                break;
            }
        }
    }

    /**
     * 好友乐斗
     *
     * @param uin  qq
     * @param name 昵称[非必填]
     * @return res
     */
    public static String happyFriends(String uin, String name) {
        String param = "fight&puin=" + uin;
        return getPetPkCmd(param, String.format("乐斗好友[%s]", name));
    }

    //https://fight.pet.qq.com/cgi-bin/petpk?cmd=mappush&type=1&npcid=

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
        int start = 6054;
        String startStr = "活力增加了";
        String endStr = "该NPC今日";
        String resStr;
        int i = 3;
        do {
            resStr = getMercenaries(start);
            if (resStr.contains(endStr)) {
                start -= 20;
                resStr = getMercenaries(start);
            }
            if (resStr.contains(startStr)) {
                String count = resStr.split(
                        "你的活力增加了你使用了活力药水，活力增加了"
                )[1].split("0")[0];
                i = Integer.parseInt(count);
            }
            i--;
        } while (!resStr.contains(endStr) || i == 0);

    }

    public static String getMercenaries(int npcId) {
        String param = "mappush&type=1&npcid=%d";
        return getPetPkCmd(String.format(param, npcId), String.format("历练-获取佣兵[%d]", npcId));
    }

    /**
     * 获取游戏信息
     * https://fight.pet.qq.com/cgi-bin/petpk?cmd=ledouvip
     * {"result":"0","msg":"","lvl":"7","score":"13555","speed":"15","day":"102"..}
     */
    public static void getGameInformation() {
        String json = getPetPkCmd("ledouvip");
        ResultMsg result = JSONObject.parseObject(json, ResultMsg.class);

        System.out.printf("vip-[%s] score[%s][↑ %s/day] last[%s day]%n"
                , result.getLvl(), result.getScore(), result.getSpeed(), result.getDay());
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
        String startStr = getPetPkCmd(String.format(base, "start&stage_id=1"), "");//
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
     * 帮派-矿洞挑战
     */
    public static void mineChallenge() {
        // 可挑战3次
        int numberOfChallenges = 3;
        for (int i = 0; i < numberOfChallenges; i++) {
            String json = getPetPkCmd("factionmine&op=fight");
            ResultMsg result = JSONObject.parseObject(json, ResultMsg.class);
            String resultStr = result.getResult();
            if ("0".equals(resultStr)) {
                resultStr = "Succeed";
            }
            System.out.printf("矿洞挑战[%d] = %s%n", i, resultStr);
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
            System.out.println("msg = " + msg);

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
        //System.out.println("json = " + json);
        ResultMsg resultMsg = JSONObject.parseObject(json, ResultMsg.class);
        String msg = resultMsg.getMsg();
        // msg 消息为空就使用源
        if (!StringUtils.hasLength(msg)) {
            msg = resultMsg.getDrop();
            if (!StringUtils.hasLength(msg)) {
                msg = json;
            }
        }
        if (!"".equals(info)) {
            System.out.println(info + " = " + msg);
        }
        return msg;
    }

    /**
     * 基础请求-origin
     *
     * @param param 参数
     * @return origin
     */
    private static String getPetPkCmd(String param) {
        String http = "http";
        String url = PET_PK_CMD + param;
        String cookie = GLOBAL_COOKIE;
        if (param.startsWith(http)) {
            url = param;
            cookie = "mode=test";
        }
        //  System.out.printf("URL[%s]%n", url);
        return doGet(url, cookie);
    }

}



