/**
 * FullImage Activity
 *
 * @author  Daniela Alarcón
 * @version 1.0
 * @since   2020-10-17
 */
package cr.ac.ucr.turistico;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cr.ac.ucr.turistico.adapters.ImageAdapter;

public class FullImageActivity extends AppCompatActivity {

    private Toolbar tToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        tToolbar = findViewById(R.id.t_toolbar);
        tToolbar.setTitle("");

        setSupportActionBar(tToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int position = intent.getExtras().getInt("id");
        ImageAdapter adapter = new ImageAdapter(this);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(adapter.images[position]);
    }
}