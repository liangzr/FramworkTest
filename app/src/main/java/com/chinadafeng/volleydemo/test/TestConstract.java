package com.chinadafeng.volleydemo.test;

import com.chinadafeng.volleydemo.BasePresenter;
import com.chinadafeng.volleydemo.BaseView;

/**
 * Created by liangzr on 16-6-13.
 */
public interface TestConstract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
    }
}

