package com.example.a41754.tourofheros.fragment;

import android.widget.Toast;

import com.example.a41754.tourofheros.adpters.TopAdapter;
import com.example.a41754.tourofheros.bean.HeroBean;
import com.example.a41754.tourofheros.db.Hero;
import com.jaydenxiao.common.commonutils.ToastUitl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ToastUitl.class)
public class DashFragmentTest {
    DashFragment dashFragment;
    @Mock
    TopAdapter topAdapter;
    @Mock
    ArrayList list1;
    @Mock
    ArrayList list2;
    @Mock
    Hero hero;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ToastUitl.class);
        Mockito.when(list1.get(Mockito.anyInt())).thenReturn(hero);
        Mockito.when(hero.getName()).thenReturn("aName");
        dashFragment = Mockito.spy(DashFragment.class);

    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void queryAndSetDataTestEmptyList() {
        Mockito.when(list1.size()).thenReturn(0);

        dashFragment.queryAndSetData(list1, list2, topAdapter);

        Mockito.verify(topAdapter, Mockito.times(0))
                .setNewData(Mockito.any());
        PowerMockito.verifyStatic(ToastUitl.class);
    }

    @Test
    public void queryAndSetDataTestListNotEmpty() {

        Mockito.when(list1.size()).thenReturn(5);


        dashFragment.queryAndSetData(list1, list2, topAdapter);

        Mockito.verify(topAdapter)
                .setNewData(Mockito.any());
    }
}