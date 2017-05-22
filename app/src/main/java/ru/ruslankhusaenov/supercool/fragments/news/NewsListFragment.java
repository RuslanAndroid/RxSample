package ru.ruslankhusaenov.supercool.fragments.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.ruslankhusaenov.supercool.App;
import ru.ruslankhusaenov.supercool.R;
import ru.ruslankhusaenov.supercool.models.NewsItem;

import ru.ruslankhusaenov.supercool.repository.DomainService;

/**
 * Created by Ruslan on 05.05.2017.
 */

public class NewsListFragment extends Fragment implements NewsView {
    public static final String  TAG     = "fragment_app_product_item_comments_list";
    public static final int     LAYOUT  = R.layout.fragment_news_list;

    NewsPresenter       presenter;
    FragmentManager     manager;
    FragmentTransaction transaction;

    @Inject
    public      DomainService service;


    @BindView   (R.id.news_list)
    RecyclerView list;

    @BindView   (R.id.progress)
    AVLoadingIndicatorView progressBar;

    Toast       mToast;
    private     NewsListAdapter adapter;

    public static NewsListFragment getInstance(){
        NewsListFragment newsListFragment = new NewsListFragment();
        return newsListFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.getDeps().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT,container,false);
        ButterKnife.bind(this, view);
        renderView();

        presenter   = new NewsPresenter(service, this);
        manager     = getActivity().getSupportFragmentManager();
        return view;
    }

    public  void renderView(){
        adapter     = new NewsListAdapter(getActivity());

        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);

        adapter.setOnClickListener(new NewsListAdapter.ItemclickListener() {
            @Override
            public void onClick(View view, int position) {
                openNews(adapter.getItem(position));
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if( presenter != null ) {
            presenter.onStop();
        }
    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }


    public void openNews(NewsItem newsOneResponse) {
        transaction = manager.beginTransaction();
        transaction.add(R.id.container_main, NewsOneFragment.getInstance(newsOneResponse));
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onFailure(String appErrorMessage) {
        if(mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(getActivity(),appErrorMessage,Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void onLoad(ArrayList<NewsItem> cityListResponse) {
        adapter.setInfo(cityListResponse);
    }

    @Override
    public void onLoadMore(ArrayList<NewsItem> cityListResponse) {
        adapter.addInfo(cityListResponse);
    }

}
