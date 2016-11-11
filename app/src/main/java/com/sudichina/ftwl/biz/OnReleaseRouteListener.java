package com.sudichina.ftwl.biz;

/**
 * Created by mccccccmike on 2016/10/8.
 */

public interface OnReleaseRouteListener extends OnRequestListener {
    void onReleaseRouteSuccess();

    void onReleaseRouteFailed();
}
