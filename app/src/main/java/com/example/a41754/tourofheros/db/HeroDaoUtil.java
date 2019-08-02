package com.example.a41754.tourofheros.db;

import java.util.List;

public class HeroDaoUtil {

    public static List<Hero> getAllData() {
        return getDaoInstance().queryBuilder().list();
    }

    public static List<Hero> getHeroListFromName(String name) {
        return getDaoInstance().queryBuilder().where(HeroDao.Properties.Name.eq(name)).list();
    }

    public static Hero getHeroUniqueFromId(Long id) {
        return getDaoInstance().queryBuilder().where(HeroDao.Properties._id.eq(id)).unique();
    }

    public static HeroDao getDaoInstance() {
        return DaoManager.getInstance().getDaoSession().getHeroDao();
    }

    public static void delete(Hero entity) {
        getDaoInstance().delete(entity);
    }

    public static void insert(Hero entity) {
        getDaoInstance().insert(entity);
    }
}
