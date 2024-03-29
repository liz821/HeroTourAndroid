package com.example.a41754.tourofheros.fragment;

import com.example.a41754.tourofheros.PublicMethods;
import com.example.a41754.tourofheros.db.Hero;
import com.example.a41754.tourofheros.db.HeroDao;
import com.example.a41754.tourofheros.db.HeroDaoUtil;
import com.jaydenxiao.common.commonutils.ToastUitl;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HeroDaoUtil.class)
public class DetailFragmentTest {
    DetailFragment fragment;
    @Mock
    HeroDao dao;
    @Mock
    Hero hero;
    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(HeroDaoUtil.class);
        fragment = Mockito.spy(DetailFragment.class);
    }
   @Test
    public  void save(){
       PowerMockito.when(HeroDaoUtil.getHeroUniqueFromId(Mockito.any())).thenReturn(hero);

       fragment.save(1l,"22",dao);

       Mockito.verify(dao).update(Mockito.any());
   }
}