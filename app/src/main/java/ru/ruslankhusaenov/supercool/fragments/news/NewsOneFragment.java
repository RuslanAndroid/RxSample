package ru.ruslankhusaenov.supercool.fragments.news;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.ruslankhusaenov.supercool.R;
import ru.ruslankhusaenov.supercool.models.NewsItem;

/**
 * Created by Ruslan on 11.05.2017.
 */

public class NewsOneFragment extends Fragment {

    public static final int     LAYOUT  = R.layout.fragment_news_one;

    private                     NewsItem newsOneObj;

    @BindView(R.id.news_one_title)
    TextView title;
    @BindView(R.id.news_one_date)
    TextView date;
    @BindView(R.id.news_one_text)
    TextView text;
    @BindView(R.id.news_one_photo)
    ImageView photo;
    @BindView(R.id.news_one_mask)
    ImageView mask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsOneObj = (NewsItem) getArguments().getSerializable("info");
    }

    public static NewsOneFragment getInstance(NewsItem newsOneResponse){
        Bundle bundle = new Bundle();
        NewsOneFragment newsOneFragment = new NewsOneFragment();
        bundle.putSerializable("info",newsOneResponse);
        newsOneFragment.setArguments(bundle);
        return newsOneFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( LAYOUT , container , false );
        ButterKnife.bind(this, view);

        if(newsOneObj != null){
            init();
        }
        return view;
    }
    private void init(){
        float screenHeight = getScreenHeight();

        photo   .getLayoutParams().height = (int)(screenHeight/2.5);
        mask    .getLayoutParams().height = (int)(screenHeight/2.45);

        if(newsOneObj.description != null) {
            text.setText(newsOneObj.description);
        }

        if(newsOneObj.publishedAt != null){
            date.setText(newsOneObj.publishedAt.substring(0,10));
        }

        if(newsOneObj.title != null){
            title.setText(newsOneObj.title);
        }
        if(newsOneObj.urlToImage != null) {

            Picasso.with(getActivity())
                    .load(newsOneObj.urlToImage)
                    .fit()
                    .centerCrop()
                    .into(photo);
        }
    }

    private float getScreenHeight(){

        Display     display = getActivity().getWindowManager().getDefaultDisplay();
        Point       size    = new Point();
        Resources   r       = getResources();

        display.getSize(size);
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, size.y, r.getDisplayMetrics());

    }


}
