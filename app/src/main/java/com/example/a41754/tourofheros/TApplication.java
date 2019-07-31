package com.example.a41754.tourofheros;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.a41754.tourofheros.db.DaoManager;
import com.example.a41754.tourofheros.db.DaoSession;
import com.example.a41754.tourofheros.db.Hero;
import com.example.a41754.tourofheros.db.HeroDao;
import com.jaydenxiao.common.baseapp.BaseApplication;

import java.util.List;

/**
 * Created by lzz on 2018/4/24.
 */

public class TApplication extends BaseApplication {
    public static TApplication application;
    String TAG = "TAPlication ";


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        HeroDao heroDao = daoSession.getHeroDao();
        initHeros(heroDao);
        initARouter();

    }

    private void initARouter() {
        ARouter.openLog();     // 打印日志
//        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(application);
    }

    /**
     * 若本地无英雄,数据库插入20条数据
     */
    public void initHeros(HeroDao heroDao) {

        List<Hero> list = heroDao.queryBuilder().list();
        if (list.size() == 0) {
            for (int i = 0; i < 20; i++) {
                Hero hero = new Hero();
                hero.setName("hero" + i);
                heroDao.insertOrReplace(hero);
            }

        }

    }


}
