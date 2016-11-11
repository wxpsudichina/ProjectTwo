package com.sudichina.ftwl.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sudichina.ftwl.R;
import com.sudichina.ftwl.utils.DialogUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout drawer_layout;
    private FrameLayout content_frame;
    private ListView left_drawer;
    private int[] icons = {
            R.mipmap.order_form, R.mipmap.wallet, R.mipmap.id,
            R.mipmap.vehicle_management, R.mipmap.faq, R.mipmap.about_us,
            R.mipmap.setup
    };
    private int[] icons_selected = {
            R.mipmap.order_form_selected, R.mipmap.wallet, R.mipmap.id,
            R.mipmap.vehicle_management, R.mipmap.faq, R.mipmap.faq,
            R.mipmap.setup_selected
    };
    private String[] text = {
            "我的订单",
            "我的钱包", "身份验证", "车辆管理",
            "帮助说明", "关于我们", "设置",
    };
    private BaseAdapter mAdapter;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, new HomeFragment(), "home").commit();

        initData();
        initView();
        initEvent();

        left_drawer.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mInflater = getLayoutInflater();

        mAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return icons.length;
            }

            @Override
            public Object getItem(int i) {
                return icons[i];
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null) {
                    view = mInflater.inflate(R.layout.item_drawer, null);
                }

                ImageView iv_avatar = (ImageView) view.findViewById(R.id.iv_icon);
                iv_avatar.setImageResource(icons[i]);
                TextView tv_text = (TextView) view.findViewById(R.id.tv_text);
                tv_text.setText(text[i]);

                return view;
            }
        };

    }

    @Override
    protected void initView() {
        content_frame = (FrameLayout) findViewById(R.id.content_frame);
        left_drawer = (ListView) findViewById(R.id.left_drawer);
        left_drawer.addHeaderView(getLayoutInflater().inflate(R.layout.header, null));
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.setScrimColor(0x7f35363a);
        drawer_layout.setScrimColor(Color.TRANSPARENT);
//        drawer_layout.setDrawerElevation(100);
        ColorDrawable drawable = new ColorDrawable(0x44ff0000);
        drawable.setBounds(0, 0, 10, 400);
//        drawer_layout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//        drawer_layout.setDrawerElevation(50);
//        drawer_layout.setFitsSystemWindows(false);
        drawer_layout.setStatusBarBackgroundColor(0x44ff0000);
    }

    @Override
    protected void initEvent() {
        left_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) return;

                resetItemStyle();
                ImageView iv = (ImageView) view.findViewById(R.id.iv_icon);
                TextView tv = (TextView) view.findViewById(R.id.tv_text);
                iv.setImageResource(icons_selected[i - 1]);
                tv.setTextColor(0xff477acc);
                view.setBackgroundColor(0xff3a3c41);

                Fragment fragment = null;
                switch (i) {
                    case 1:
                        DialogUtils.showDialog(MainActivity.this, R.layout.dialog_me, R.style.diyDialog);
//                        fragment = new PaymentFragment();
                        break;
                    case 2:
//                        fragment = new OrderFormFragment();
                        break;
                    case 3:
                        //身份验证
                        toAty(IdVerificationActivity.class);
                        break;
                    case 4:
                        //车辆管理
                        toAty(MyCarsActivity.class);
                        break;
                    case 5:
//                        fragment = new ShareFragment();
                        break;
                    case 6:
//                        fragment = new MessageFragment();
                        break;
                    case 7:
//                        fragment = new HelpFragment();
                        break;
                    default:
//                        fragment = new PaymentFragment();
                        break;
                }

//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                drawer_layout.closeDrawer(left_drawer);

            }
        });

    }

    private void resetItemStyle() {
        int count = left_drawer.getChildCount() - 1;
        for (int i = 0; i < count; i++) {
            View v = left_drawer.getChildAt(i + 1);
            ImageView iv = (ImageView) v.findViewById(R.id.iv_icon);
            TextView tv = (TextView) v.findViewById(R.id.tv_text);
            iv.setImageResource(icons[i]);
            tv.setTextColor(0xffffffff);
            v.setBackgroundColor(0xff35363a);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    public DrawerLayout getDrawer_layout() {
        return drawer_layout;
    }
}
