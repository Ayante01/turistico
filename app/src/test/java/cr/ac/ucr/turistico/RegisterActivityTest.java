package cr.ac.ucr.turistico;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)

public class RegisterActivityTest {

    //private static final String EXPECTED_RESULT = "Sign up was successful";
    private static final String EXPECTED_RESULT = "Email already exist";
    //private static final String EXPECTED_RESULT = "Password too short";
    //private static final String EXPECTED_RESULT = "Please enter an Email";
    //private static final String EXPECTED_RESULT = "Void";
    //private static final String EXPECTED_RESULT = "Invalid sign up!";

    @Test
    public void readValidateRegister() {

        RegisterActivity registerActivity = new RegisterActivity();

        String result = registerActivity.validateRegister("test@gmail.com","123456");

        assertThat(result, is(EXPECTED_RESULT));

    }
}