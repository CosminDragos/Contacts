package ro.project.contacts.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cosmin on 07.07.2017.
 */

public abstract class BaseActivity<PRESENTER extends BaseContract.ContractPresenter> extends AppCompatActivity {

    private Unbinder unbinder;
    private PRESENTER presenter;


    public abstract PRESENTER initPresenter();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        unbinder = ButterKnife.bind(this);

    }

    public abstract int getLayoutID();

    public PRESENTER getPresenter(){
        if(presenter == null){
            presenter = initPresenter();
        }
        return presenter;
    }

    @Override
    protected void onDestroy() {
        if(unbinder != null){
            unbinder.unbind();
        }
        super.onDestroy();
    }

}
