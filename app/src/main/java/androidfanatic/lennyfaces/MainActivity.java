package androidfanatic.lennyfaces;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity
        extends MvpActivity<MainView, MainPresenter>
        implements MainView, Drawer.OnDrawerItemClickListener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.rv_face) RecyclerView faceRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // setup
        faceRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        faceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        faceRecyclerView.setAdapter(getPresenter().getAdapter());

        getPresenter().initUI();
    }

    @NonNull @Override public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override public void initDrawer(List<String> faceTypes) {
        DrawerBuilder builder =
                new DrawerBuilder()
                        .withActivity(this)
                        .withToolbar(toolbar)
                        .withActionBarDrawerToggleAnimated(true)
                        .withRootView(R.id.rootview)
                        .withOnDrawerItemClickListener(this);
        for (String faceType : faceTypes) {
            builder.addDrawerItems(
                    new PrimaryDrawerItem()
                            .withName(faceType)
                            .withTag(faceType)
            );
        }
        builder.build();
    }

    @Override public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
        String faceType = (String) iDrawerItem.getTag();
        getPresenter().showFaceType(faceType);
        return false;
    }
}

