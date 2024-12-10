Argument passed to when() is null!
Example of correct stubbing:
    doThrow(new RuntimeException()).when(mock).someMethod();
Also, if you use @Mock annotation don't miss openMocks()
org.mockito.exceptions.misusing.NullInsteadOfMockException: 
Argument passed to when() is null!
Example of correct stubbing:
    doThrow(new RuntimeException()).when(mock).someMethod();
Also, if you use @Mock annotation don't miss openMocks()
	at com.sbi.epay.authentication.service.AuthenticationServiceTest.testGenerationAccessToken(AuthenticationServiceTest.java:36)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
