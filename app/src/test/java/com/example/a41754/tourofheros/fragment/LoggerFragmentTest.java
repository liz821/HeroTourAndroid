package com.example.a41754.tourofheros.fragment;

import android.widget.LinearLayout;

import com.example.a41754.tourofheros.PublicMethods;
import com.example.a41754.tourofheros.adpters.LoggerAdapter;
import com.example.a41754.tourofheros.db.HeroDao;
import com.example.a41754.tourofheros.db.HeroDaoUtil;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.orhanobut.logger.LogAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFragment.class)
public class LoggerFragmentTest {
    @Mock
    LinearLayout linearLayout;
    @Mock
    LoggerAdapter adapter;
    LoggerFragment fragment;


    @Before
    public void setUP() {
        fragment = Mockito.spy(LoggerFragment.class);

    }


    @Test
    public void clean() {
        fragment.clean(adapter,linearLayout);
        Mockito.verify(adapter).setNewData(Mockito.any());

    }
}