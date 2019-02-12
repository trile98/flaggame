package com.trile.flagv12;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {

    @Mock
    public MainActivity TestClass = mock(MainActivity.class);




    @Test
    public void TestLevelQuery() {

        String expectedResult = "select * from easy";
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                TestClass.easy.performClick();
                return null;
            }
        }).when(TestClass).choose();

        TestClass.choose();

        assertEquals(expectedResult,TestClass.query);
    }

  }