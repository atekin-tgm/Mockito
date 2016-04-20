package Tekin;

import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.Test;

/* 	Wir lernen Mockito!
 * 	@author		Abdurrahim Burak TEKIN 
 * 	@version	20.04.2016
 */

public class MockitoTest {

	@Test
	public void testBehaviour() {
		//Let's import Mockito statically so that the code looks clearer


		 //mock creation
		 List mockedList = mock(List.class);

		 //using mock object
		 mockedList.add("one");
		 mockedList.clear();

		 //verification
		 verify(mockedList).add("one");
		 verify(mockedList).clear();
	}
	
}