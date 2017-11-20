package com.zonk.fbtest.Utils;


import android.util.Log;

import com.zonk.fbtest.Model.Friends;
import com.zonk.fbtest.Model.Message;
import com.zonk.fbtest.Model.Shout;
import com.zonk.fbtest.Model.Skill;
import com.zonk.fbtest.Model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by poorva on 29/9/14.
 */
public class DataMapParser {


    public static String parseAuthToken(Map<String, Object> dataMap) {
        return (String) dataMap.get("auth_token");
    }

    private static final String LOG_TAG = "DataMapParser";



    public static List<Shout> parseShouts(Map<String, Object> dataMap) {
        List<Shout> shouts= new ArrayList();
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) dataMap.get("shoutouts");

        for (Map<String, Object> map : mapList) {
            shouts.add(parseShout(map));
        }
        return shouts;
    }

    public static List<Skill> parseSkills(Map<String, Object> dataMap) {
        List<Skill> offersList = new ArrayList();
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) dataMap.get("skills");

        for (Map<String, Object> map : mapList) {
            offersList.add(parseSkill(map));
        }
        return offersList;
    }



    public static boolean parseshoutAvailable(Map<String, Object> dataMap) {
        return (boolean) dataMap.get("shoutout");
    }


    public static Shout parseShout(Map<String, Object> dataMap) {
        Shout shout = new Shout();
        Skill skill= new Skill();
        List<Message> messages;

        messages= new ArrayList<>();
        shout.setWorkingOn(dataMap.get("workingOn")+"");
        //shout.setSkill(dataMap.get("workingOn")+"");
        shout.setProfilePic(dataMap.get("profilePic")+"");

        shout.set_id(dataMap.get("_id")+"");
        shout.setPersonName(dataMap.get("personName")+"");
        shout.setLon(Double.parseDouble(dataMap.get("lon")+""));
        shout.setLoginId((dataMap.get("loginId")+""));
        shout.setLat(Double.parseDouble(dataMap.get("lat")+""));
        shout.setDate(dataMap.get("dateCreated")+"");
        shout.setTime(dataMap.get("timeCreated")+"");


        shout.setSkill(parseSkill((Map<String, Object>) dataMap.get("skill")));

        try {
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) dataMap.get("messages");

            for (Map<String, Object> map : mapList) {
                messages.add(parseMessage(map));
            }

            shout.setMessages(messages);
        }
        catch (Exception e){
            e.printStackTrace(); }
        return shout;

    }




    public static Message parseMessage(Map<String, Object> dataMap) {
        Message  message = new Message();
        message.setLoginId(dataMap.get("loginId") + "");

        message.setProfilePic(dataMap.get("profilePic") + "");
        message.setMessage(dataMap.get("message") + "");
        message.setName(dataMap.get("name") + "");

                return message;
    }
    public static Skill parseSkill(Map<String, Object> dataMap) {
        Skill skill = new Skill();
        skill.setSkillName(dataMap.get("skillName") + "");
        skill.setSkillIcon(dataMap.get("skillIcon") + "");
        skill.setSkillId(Integer.parseInt(dataMap.get("skillId") + ""));
        try {
            skill.setValue(Integer.parseInt(dataMap.get("value") + ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skill;
    }

    public static List<Friends> parseFriends(Map<String, Object> dataMap) {
        List<Friends> offersList = new ArrayList();
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) dataMap.get("friendsList");

        for (Map<String, Object> map : mapList) {
            offersList.add(parseFriend(map));
        }
        return offersList;
    }
    public static List<User> parseUsers(Map<String, Object> dataMap) {
        List<User> offersList = new ArrayList();
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) dataMap.get("contacts");

        for (Map<String, Object> map : mapList) {
            offersList.add(parseUser(map));
        }
        return offersList;
    }

    public static Friends parseFriend(Map<String, Object> dataMap) {
        Friends user= new Friends();
        try {

            user.setHeadline(dataMap.get("headline") + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            user.setDummy(Boolean.parseBoolean(dataMap.get("dummy") + ""));

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            user.setFriend(Boolean.parseBoolean(dataMap.get("friend") + ""));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            user.setConnect(Boolean.parseBoolean(dataMap.get("connect") + ""));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            user.setTitle(dataMap.get("title") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            user.setCity(dataMap.get("city") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            user.setIndustry(dataMap.get("industry") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user.setEmail(dataMap.get("email") + "");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            user.setLat(Double.parseDouble(dataMap.get("lat") + ""));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            user.setLoginId(dataMap.get("loginId") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user.setName(dataMap.get("name") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            user.setPhNo(dataMap.get("phNo") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user.setProfilePic(dataMap.get("profilePic") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user.setLon(Double.parseDouble(dataMap.get("lon") + ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user.setSummary(dataMap.get("summary") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }


    public static User parseUser(Map<String, Object> dataMap) {
        User user= new User();
        List<Skill> skillList;
        List<Shout> shoutList;

        skillList = new ArrayList<>();

        shoutList = new ArrayList<>();

        try{
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) dataMap.get("skills");
            for (Map<String, Object> map : mapList) {
                skillList.add(parseSkill(map));
            }
            user.setSkills(skillList);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) dataMap.get("shoutouts");
            for (Map<String, Object> map : mapList) {
                shoutList.add(parseShout(map));
            }
            user.setShouts(shoutList);
        }
        catch (Exception e){
            e.printStackTrace();
        }


        try {

            user.setFriend(Boolean.parseBoolean(dataMap.get("friend") + ""));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            user.setConnect(Boolean.parseBoolean(dataMap.get("connect") + ""));

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            user.setHeadline(dataMap.get("headline") + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            user.setTitle(dataMap.get("title") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            user.setCity(dataMap.get("city") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            user.setIndustry(dataMap.get("industry") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user.setEmail(dataMap.get("email") + "");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            user.setLat(Double.parseDouble(dataMap.get("lat") + ""));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            user.setLoginId(dataMap.get("loginId") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user.setName(dataMap.get("name") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            user.setPhNo(dataMap.get("phNo") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user.setProfilePic(dataMap.get("profilePic") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user.setLon(Double.parseDouble(dataMap.get("lon") + ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user.setSummary(dataMap.get("summary") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }


}
