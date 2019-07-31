package com.example.a41754.tourofheros;

import com.example.a41754.tourofheros.db.HeroDao;
import com.jaydenxiao.common.baserx.RxManager;

import org.greenrobot.greendao.query.QueryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class TApplicationTest {
    @Spy
    private
    TApplication tApplication;
    @Mock
    private
    HeroDao heroDao;
    @Mock
    private
    QueryBuilder queryBuilder;


    /**
     * 验证本地无英雄时，初始插入英雄
     * 插入20次,认为成功
     */
    @Test
    public void initHeros() {
        Mockito.when(heroDao.queryBuilder()).thenReturn(queryBuilder);
        Mockito.when(queryBuilder.list()).thenReturn(new ArrayList());
        tApplication.initHeros(heroDao);
        Mockito.verify(heroDao, Mockito
                .times(20))
                .insertOrReplace(Mockito.any());
    }


}