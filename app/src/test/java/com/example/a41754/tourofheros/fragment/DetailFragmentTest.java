package com.example.a41754.tourofheros.fragment;

import com.example.a41754.tourofheros.db.Hero;
import com.example.a41754.tourofheros.db.HeroDao;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DetailFragmentTest {
    @Mock
    HeroDao heroDao;
    @Mock
    Hero hero;
    @Mock
    private
    QueryBuilder queryBuilder;
    @Mock
    private
    WhereCondition whereCondition;
    @Spy
    DetailFragment detailFragment;

    /**
     * 验证存储顺序
     * Assert: 先设置名称后存储
     */
    @Test
    public void save() {
        Mockito.when(heroDao.queryBuilder()).thenReturn(queryBuilder);
        Mockito.when(queryBuilder.where(Mockito.any())).thenReturn(queryBuilder);
        Mockito.when(queryBuilder.unique()).thenReturn(hero);
        detailFragment.save(1, "a1", heroDao);
        InOrder inOrder = Mockito.inOrder(hero, heroDao);
        inOrder.verify(hero).setName(Mockito.anyString());
        inOrder.verify(heroDao).update(Mockito.any());

    }
}