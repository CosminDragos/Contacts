package ro.project.contacts.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cosmin on 07.07.2017.
 */

public abstract class BaseFragment<PRESENTER extends BaseContract.ContractPresenter> extends Fragment {

    private PRESENTER presenter;

    public abstract PRESENTER initPresenter();

    protected View loadLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutID(), container, false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return loadLayout(inflater, container);
    }

    public abstract int getLayoutID();

    public PRESENTER getPresenter(){
        if(presenter == null){
            presenter = initPresenter();
        }
        return presenter;
    }
}
