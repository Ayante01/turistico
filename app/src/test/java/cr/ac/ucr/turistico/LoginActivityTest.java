package cr.ac.ucr.turistico;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)

public class LoginActivityTest {
    private static final String EXPECTED_RESULT = "Login was successful";
    //private static final String EXPECTED_RESULT = "User or password incorrect";
    //private static final String EXPECTED_RESULT = "User or password incorrect";
    //private static final String EXPECTED_RESULT = "Please enter a password";
    //private static final String EXPECTED_RESULT = "Please enter an user";
    //private static final String EXPECTED_RESULT = "Void";
    //private static final String EXPECTED_RESULT = "Invalid login!";

    @Mock
    Context mMockContext;

    @Test
    public void readString_Login(){
        LoginActivity loginTest = new LoginActivity();

        String result = loginTest.validateUser("user", "password");

        assertThat(result, is(EXPECTED_RESULT));
    }
}