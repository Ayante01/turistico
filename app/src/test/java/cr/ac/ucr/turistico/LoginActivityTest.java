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
    private static final String FAKE_STRING = "User or passwword incorrect";

    @Mock
    Context mMockContext;

    @Test
    public void readString_Login(){
        LoginActivity loginTest = new LoginActivity(mMockContext);

        //String result = loginTest.validate("user", "passwod");

        //assertThat(result, is(FAKE_STRING));
    }
}