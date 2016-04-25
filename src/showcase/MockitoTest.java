package showcase;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.mockito.InOrder;

/* 	Wir lernen Mockito!
 * 	@author		Abdurrahim Burak TEKIN 
 * 	@version	20.04.2016
 */

public class MockitoTest {

	LinkedList mockedList;
	
	
	//Let's verify some behaviour
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
	
	//How about some stubbing?
	@Test(expected=RuntimeException.class)
	public void testStubbing() {
		//You can mock concrete classes, not just interfaces
		mockedList = mock(LinkedList.class);

		 //stubbing
		 when(mockedList.get(0)).thenReturn("first");
		 when(mockedList.get(1)).thenThrow(new RuntimeException());

		 //following prints "first"
		 System.out.println(mockedList.get(0));

		 //following throws runtime exception
		 System.out.println(mockedList.get(1));

		 //following prints "null" because get(999) was not stubbed
		 System.out.println(mockedList.get(999));

		 //Although it is possible to verify a stubbed invocation, usually it's just redundant
		 //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
		 //If your code doesn't care what get(0) returns, then it should not be stubbed. Not convinced? See here.
		 verify(mockedList).get(0);
	}
	
	//Argument matchers
	@Test
	public void testArgumentMather() {
		
		mockedList = mock(LinkedList.class);
		
		//stubbing using built-in anyInt() argument matcher
		 when(mockedList.get(anyInt())).thenReturn("element");

		 //stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
		 // when(mockedList.contains(argThat(isValid()))).thenReturn("element");

		 //following prints "element"
		 System.out.println(mockedList.get(999));

		 //you can also verify using an argument matcher
		 verify(mockedList).get(anyInt());
	}
	
	//Verifying exact number of invocations /at least x / never
	@Test
	public void testNumberOfInvocations() {
		//using mock
		 mockedList.add("once");

		 mockedList.add("twice");
		 mockedList.add("twice");

		 mockedList.add("three times");
		 mockedList.add("three times");
		 mockedList.add("three times");

		 //following two verifications work exactly the same - times(1) is used by default
		 verify(mockedList).add("once");
		 verify(mockedList, times(1)).add("once");

		 //exact number of invocations verification
		 verify(mockedList, times(2)).add("twice");
		 verify(mockedList, times(3)).add("three times");

		 //verification using never(). never() is an alias to times(0)
		 verify(mockedList, never()).add("never happened");

		 //verification using atLeast()/atMost()
		 verify(mockedList, atLeastOnce()).add("three times");
		 verify(mockedList, atLeast(2)).add("twice");
		 verify(mockedList, atMost(5)).add("three times");
	}
	
	//Stubbing void methods with exceptions
	@Test
	public void testStubbingWithExceptions() {
		//RuntimeException wenn "mockedList" -> leer!
		doThrow(new RuntimeException()).when(mockedList).clear();

		   //following throws RuntimeException:
		   mockedList.clear();
	}
	
	//Verification in order
	@Test
	public void testOrder() {
		// A. Single mock whose methods must be invoked in a particular order
		 List singleMock = mock(List.class);

		 //using a single mock
		 singleMock.add("was added first");
		 singleMock.add("was added second");

		 //create an inOrder verifier for a single mock
		 InOrder inOrder = inOrder(singleMock);

		 //following will make sure that add is first called with "was added first, then with "was added second"
		 inOrder.verify(singleMock).add("was added first");
		 inOrder.verify(singleMock).add("was added second");

		 // B. Multiple mocks that must be used in a particular order
		 List firstMock = mock(List.class);
		 List secondMock = mock(List.class);

		 //using mocks
		 firstMock.add("was called first");
		 secondMock.add("was called second");

		 //create inOrder object passing any mocks that need to be verified in order
		 InOrder inOrder1 = inOrder(firstMock, secondMock);

		 //following will make sure that firstMock was called before secondMock
		 inOrder1.verify(firstMock).add("was called first");
		 inOrder1.verify(secondMock).add("was called second");

		 // Oh, and A + B can be mixed together at will
	}
	
	//Making sure interactions never happened on mock
	@Test
	public void testInteractionsOnMock() {
		
	}
}