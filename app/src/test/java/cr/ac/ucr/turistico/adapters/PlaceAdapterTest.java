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

    /**
     * Con esta validación se busca comprobar el funcionamiento del boton de like
     * le pasamos un true si queremos simular que existe un lugar con like
     * le pasamos false si queremos simular que no existe un like
     * **/
    @Test
    public void readValidateLike(){
        PlaceAdapter placeAdapter = new PlaceAdapter();

        /**existe like**/
        String result = placeAdapter.likeValidate(true);

        /**no existe like**/
        //String result = placeAdapter.likeValidate(false);

        assertThat(result, is(EXPECTED_RESULT));
    }

}