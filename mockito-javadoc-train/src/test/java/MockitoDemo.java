import org.omg.CORBA.StringHolder;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MockitoDemo {

    public static void main(String[] args) {

        function1();
        function2();

    }

    public static void function1() {
        List mockedList = mock(List.class);

        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    public static void function2() {

        LinkedList mockedLinkedList = mock(LinkedList.class);

        when(mockedLinkedList.get(0)).thenReturn("fist");
        when(mockedLinkedList.get(0)).thenReturn("second");
        when(mockedLinkedList.get(1)).thenThrow(new RuntimeException());

        System.out.println(mockedLinkedList.get(0));

//        System.out.println(mockedLinkedList.get(1));

        System.out.println(mockedLinkedList.get(999));

        verify(mockedLinkedList).get(0);
        verify(mockedLinkedList).get(0);

    }

    public static void function3(){

        LinkedList mockedLinkedList = mock(LinkedList.class);

        when(mockedLinkedList.get(anyInt())).thenReturn("element");
//        当前版本没有isValid()的api
//        when(mockedLinkedList.contains(argThat(isValid()))).thenReturn("element");

        System.out.println(mockedLinkedList.get(999));

        verify(mockedLinkedList).get(anyInt());

//        verify(mockedLinkedList).add(argThat(someString -> someString.length() > 5));

    }
}
