package heaven7.coordinatorlayoutbehavior.study1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        onInit(this, savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void onInit(Context context, Bundle savedInstanceState);
}
