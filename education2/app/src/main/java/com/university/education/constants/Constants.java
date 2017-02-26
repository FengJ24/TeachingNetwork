package com.university.education.constants;

/**
 * Created by jian on 2016/12/25.
 */

public interface Constants {

    //登录Viewtate
    String LOGIN_VIEWSTATE = "dDwxODI0OTM5NjI1Ozs+ErNwwEBfve9YGjMA8xEN6zdawEw=";

    //登录后返回的ViewState
    String AFTER_LOGIN = "dDwtMTEwMTY2MTQ4MTs7PrFWVyEhXOZcNdHi9PPCoisXJSgZ";

    //Referer
    String BASE_REFERER = "http://218.25.35.27:8080/(2jnaey55511qpi45sivoke45)/";

    //用户名
    String USERNAME = "username";

    //密码
    String PASSWORD = "password";

    //个人主页
    String FIRST_DEATIL = "xs_main.aspx?xh=";

    //学生个人课表
    String CLASS_TABLE = "xskbcx.aspx?";
    String CLASS_TABLE_ID = "&gnmkdm=N121603";

    //个人成绩查询
    String STUDENT_GRADE = "xscjcx.aspx?";
    String STUDENT_GRADE_ID = "&gnmkdm=N121605";

    //等级考试查询
    String LEVEL_EXAM = "xsdjkscx.aspx?";
    String LEVEL_EXAM_ID = "&gnmkdm=N121606";

    //教学计划查询
    String TEACH_PLAN = "pyjh.aspx?";
    String TEACH_PLAN_ID = "&gnmkdm=N121607";

    //学号和姓名
    String NAME = "name";
    String XUEHAO = "xuehao";

    //点击个人成绩所返的ViewState
    String STUDENG_GRADE_VIEWSTATE = "studeng_grade_viewstate";

    //登录界面返回,Event发出的类型
    String LOGIN_BACK = "login_back";

    //日志文件存储路径
    public static final String LOG_SAVE_PATH = "/education/log";

    //登录成功
    String LOGIN_SUCCESS = "login_success";

    //点击课程表所返的ViewState
    String CLASS_TABLE_VIEWSTATE = "class_table_viewstate";
    String WEEK = "week";

    String SUBJECT = "subject";
    //教务通知
    String TEACH_NOTIFICATION_URL = "teach_notification_url";

    //文件存储路径
    public static final String FILE_SAVE_PATH = "/university/files/";
}
