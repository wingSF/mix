package com.wing.lynne.powerMock;

import com.wing.lynne.Rectangle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Rectangle.class})
public class RectangleTest {
    @Test
    public void testGetArea() {
        double expectArea = 100.0D;
        Rectangle rectangle = PowerMockito.mock(Rectangle.class);
        PowerMockito.when(rectangle.getArea()).thenReturn(expectArea);
        double actualArea = rectangle.getArea();
        //delta 精度
        Assert.assertEquals("符合预期", expectArea, actualArea,1E-6D);
    }

    @Test
    public void testGetStatic(){

        String expect = Rectangle.class.getName();

        //对于静态方法的mock
        PowerMockito.mockStatic(Rectangle.class);
        PowerMockito.when(Rectangle.getName()).thenReturn(expect);

        String actual = Rectangle.getName();

        Assert.assertEquals(expect,actual);
    }
}
