/**
 * Image Adapter
 *
 * @author  Daniela Alarcón
 * @version 1.0
 * @since   2020-10-17
 */
package cr.ac.ucr.turistico.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import cr.ac.ucr.turistico.R;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    public Integer[] images = {
            R.drawable.nauyaca,
            R.drawable.nauyaca,
            R.drawable.nauyaca,
            R.drawable.nauyaca,
            R.drawable.nauyaca,
            R.drawable.nauyaca,
    };

    public ImageAdapter(Context c){
        context = c;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(images[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
        return imageView;
    }
}
