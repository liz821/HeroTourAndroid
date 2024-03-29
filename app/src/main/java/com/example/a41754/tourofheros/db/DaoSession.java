package com.example.a41754.tourofheros.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.a41754.tourofheros.db.Hero;

import com.example.a41754.tourofheros.db.HeroDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig heroDaoConfig;

    private final HeroDao heroDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        heroDaoConfig = daoConfigMap.get(HeroDao.class).clone();
        heroDaoConfig.initIdentityScope(type);

        heroDao = new HeroDao(heroDaoConfig, this);

        registerDao(Hero.class, heroDao);
    }
    
    public void clear() {
        heroDaoConfig.clearIdentityScope();
    }

    public HeroDao getHeroDao() {
        return heroDao;
    }

}
