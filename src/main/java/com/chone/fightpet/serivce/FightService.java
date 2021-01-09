package com.chone.fightpet.serivce;

import com.alibaba.fastjson.JSONObject;
import com.chone.fightpet.common.HttpUtils;
import com.chone.fightpet.pojo.ResultMsg;
import com.chone.fightpet.pojo.Uin;
import com.chone.fightpet.selenium.LoginChrome;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
     // private static String GLOBAL_COOKIE = "_qpsvr_localtk=0.29191153239897183; uin=o1282722653; skey=@XnddeODdm; RK=XvRlRgwqUQ; ptcz=a5fba08b941e8702282c3912ed4faac44a4f7ee9ec636a761c0ea6a35c682353; p_uin=o1282722653; pt4_token=LIvpVsIMaWc8VjzfkAw9LsI6DMOB*ttghCVxtcttD4s_; p_skey=ssE0Eh5dN6ZRgK25WpLGgWhVWACM-Agn5aH0t4e7cKc_; pgv_info=ssid=s8072740864; ts_last=id.qq.com/index.html; ts_refer=xui.ptlogin2.qq.com/cgi-bin/xlogin%3Fappid%3D1006102%26s_url%3Dhttps%3A//id.qq.com/index.html%2523info; pgv_pvid=7432704060; ts_uid=7651527990";
    private static final String GLOBAL_COOKIE = LoginChrome.loginQQ();
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
    public static void main(String[] args) {
        // 打开chrome login
       // GLOBAL_COOKIE = LoginChrome.loginQQ();

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
    public static void mainT(String[] args) {
        // 解析cookie参数
        parseCookieParameters();

        //手Q游戏一键加速
        //oneClickAccelerationForMobileQQGames();
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

        // System.out.printf("GLOBAL_QQ[%s],GLOBAL_S_KEY[%s],GLOBAL_PT4_TOKEN[%s]", GLOBAL_QQ, GLOBAL_S_KEY, GLOBAL_PT4_TOKEN);
        System.out.printf("GLOBAL_QQ[%s]%n", GLOBAL_QQ);

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
        String startStr = getPetPkCmd(String.format(base, "start&stage_id=1"), "飘渺幻境-开始");//
        if (startStr.contains(timesHaveBeenUsedUp)) {
            return;
        }
        for (int i = 0; i < 5; i++) {
            getPetPkCmd(String.format(base, "fight"), "飘渺幻境-挑战");
        }
        getPetPkCmd(String.format(base, "return"), "飘渺幻境-返回");

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
            getPetPkCmd(param, "领取登录礼包");

        }

    }

    /**
     * 登陆游戏
     */
    public static void loginGames() {
        getPetPkCmd("limit&op=login", "登陆游戏");
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
        String url = "betogoaway&gb_id=";
        for (int i = 1; i <= weekInt; i++) {
            String msg = getPetPkCmd(url + i, String.format("幸运鹅礼包[%d]", i));
            // 不在领奖时间内！
            if (msg.contains(notTimeStr)) {
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
        ResultMsg resultMsg = JSONObject.parseObject(json, ResultMsg.class);
        String msg = resultMsg.getMsg();
        // msg 消息为空就使用源
        if (!StringUtils.hasLength(msg)) {
            msg = json;
        }
        System.out.println(info + " = " + msg);
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



