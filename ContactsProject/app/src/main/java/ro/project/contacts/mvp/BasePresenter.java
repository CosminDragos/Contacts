package ro.project.contacts.mvp;

/**
 * Created by cosmin on 07.07.2017.
 */

public class BasePresenter<VIEW extends BaseContract.ContractView> {

    private final VIEW view;


    public BasePresenter(VIEW view) {
        this.view = view;
    }


    public VIEW getView() {
        return view;
    }


}
