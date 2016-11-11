package com.sudichina.ftwl.view;

import android.content.Context;

/**
 * Created by mccccccmike on 2016/8/24.
 */
public interface IQuickLoginView {
    String getPhoneNo();

    String getIdentifyingCode();

    void showToast(String msg);

    void showGetIdentifyingCodeState();

    Context getActivity();

    void toMainActivity();
}
