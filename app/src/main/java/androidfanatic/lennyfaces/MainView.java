package androidfanatic.lennyfaces;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

public interface MainView extends MvpView {
    Context getApplicationContext();

    void initDrawer(List<String> values);
}
