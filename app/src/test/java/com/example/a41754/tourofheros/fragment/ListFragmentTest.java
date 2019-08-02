package com.example.a41754.tourofheros.fragment;

import com.example.a41754.tourofheros.PublicMethods;
import com.example.a41754.tourofheros.adpters.ListAdapter;
import com.example.a41754.tourofheros.adpters.TopAdapter;
import com.example.a41754.tourofheros.bean.HeroBean;
import com.example.a41754.tourofheros.db.Hero;
import com.example.a41754.tourofheros.db.HeroDaoUtil;
import com.jaydenxiao.common.commonutils.ToastUitl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.expectation.PowerMockitoStubber;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ToastUitl.class, ListFragment.class, PublicMethods.class, HeroDaoUtil.class})
public class ListFragmentTest {
    ListFragment listFragment;
    @Mock
    ListAdapter listAdapter;
    @Mock
    ArrayList list1;
    @Mock
    ArrayList list2;
    @Mock
    HeroBean heroBean;
    @Mock
    Hero hero;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ToastUitl.class);
        PowerMockito.mockStatic(PublicMethods.class);
        PowerMockito.mockStatic(HeroDaoUtil.class);
        listFragment = Mockito.spy(ListFragment.class);
    }

    @Test
    public void delete() {
        PowerMockito.when(HeroDaoUtil.getHeroUniqueFromId(Mockito.any())).thenReturn(hero);
        PowerMockito.doNothing().when(listFragment).afterDelete(Mockito.any());

        listFragment.delete(listAdapter,2,2l);
        Mockito.verify(listFragment).afterDelete(Mockito.any());
    }

    @Test
    public void addOld() {
        PowerMockito.when(HeroDaoUtil.getHeroListFromName(Mockito.any())).thenReturn(list1);
        Mockito.when(list1.size()).thenReturn(1);
        listFragment.add("aa");

        PowerMockito.verifyStatic(ToastUitl.class);
    }

    @Test
    public void addNew() throws Exception {
        PowerMockito.when(HeroDaoUtil.getHeroListFromName(Mockito.any())).thenReturn(list1);
        Mockito.when(list1.get(Mockito.anyInt())).thenReturn(hero);
        PowerMockito.whenNew(HeroBean.class).withNoArguments().thenReturn(heroBean);
        Mockito.doNothing().when(listFragment).afterAdd(Mockito.any(), Mockito.anyInt(), Mockito.any());

        listFragment.add("aa");

        Mockito.verify(heroBean).setId(Mockito.anyInt());
    }

    @Test
    public void queryAndSetDataEmpty() {
        Mockito.when(list1.size()).thenReturn(0);
        listFragment.queryAndSetData(list1, list2, listAdapter);
        PowerMockito.verifyStatic(ToastUitl.class);

    }

    @Test
    public void queryAndSetDataNotEmpty() throws Exception {
        Mockito.when(list1.size()).thenReturn(5);
        Mockito.when(list1.get(Mockito.anyInt())).thenReturn(hero);
        Mockito.doNothing().when(listAdapter).setNewData(Mockito.any());

        listFragment.queryAndSetData(list1, list2, listAdapter);

        Mockito.verify(listAdapter).setNewData(Mockito.any());
    }
}