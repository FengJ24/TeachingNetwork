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
    //点击等级考试返回的ViewState
    String TEACH_PLAN_VIEWSTATE = "teach_plan_viewstate";

    //登录界面返回,Event发出的类型
    String LOGIN_BACK = "login_back";

    //日志文件存储路径
    public static final String LOG_SAVE_PATH = "/university/log";

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
    public final static int CLASS_TABLE_DIF = 1993;
    //webview分享
    public static final String WEBVIEW_SHARE = "webview_share";
    //教学网分享
    public static final String JIAOXUEWANG_SHARE = "jiaoxuewang_share";


    /**
     * handler常用的msg.what
     */
    public class HandlerConstent {
        public final static int HTTP_SUCCESS = 1;
        public final static int HTTP_ERROR = 2;
        public final static int HTTP_FAIL = 3;
        public final static int CONNECT_AGAIN = 4;
        public final static int COUNT_DOWN = 5;
        public final static int LOGIN_AGAIN = 9;
        public final static int HTTP_START = 6;
        public final static int LOGIN_UNUSED = 7;
        public final static int DELETE = 8;
        public final static int HEADIMAGE_UPLOAD_SUCCESS = 9;
        public final static int HEADIMAGE_UPLOAD_FAILD = 10;
        /**
         * viewpager自动翻页
         */
        public final static int MSG_CHANGE_PHOTO = 11;
        public final static int CHOOSE_DATE_FINISH = 12;
        /**
         * 退出登录
         */
        public final static int EXIT_LOGIN = 13;
        /**
         * 退出到登录
         */
        public final static int EXIT_TO_LOGIN = 14;
        /**
         * 双按钮弹出框第一个按钮点击事件
         */
        public static final int FIRST_BUTTON_CLICK = 15;
        public static final int APP_UPDATE = 16;
        public static final int MUST_UPDATE = 17;

        /**
         * 不需要更新
         */
        public static final int NOT_APP_UPDATE = 18;
        /**
         * 更新包下载完成
         */
        public static final int DOWNLOAD_NEW_VERSION_FINISH = 19;
        /**
         * 执行更新
         */
        public static final int START_UPDATE = 20;
        /**
         * 下载成功
         */
        public static final int DOWNLOAD_SUCCESS = 21;
        /**
         * 下载失败
         */
        public static final int DOWNLOAD_FAIL = 22;
        /**
         * 取消下载
         */
        public static final int CANCEL_DOWNLOAD = 23;
        /**
         * 下载中
         */
        public static final int DOWNLOADING = 24;
        /**
         * 安装APK
         */
        public static final int INSTALL_APK = 25;
        /**
         * 取消强制更新
         */
        public static final int CANCEL_MUST_UPDATE = 26;
    }
}
