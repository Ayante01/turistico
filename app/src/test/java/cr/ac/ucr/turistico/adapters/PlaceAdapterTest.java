package cr.ac.ucr.turistico.adapters;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cr.ac.ucr.turistico.LoginActivity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PlaceAdapterTest {
    private static final String EXPECTED_RESULT = "Add like";
    //private static final String EXPECTED_RESULT = "Delete like";
    //private static final String EXPECTED_RESULT = "Null";

    @Mock
    Context mMockContext;

    @Test
    public void readValidateLike(){
        PlaceAdapter placeAdapter = new PlaceAdapter();

        String result = placeAdapter.likeValidate(true);

        assertThat(result, is(EXPECTED_RESULT));
    }

}