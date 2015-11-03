package com.example.MyWeibo.fragments;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.MyWeibo.ActionBarCastActivity;
import com.example.MyWeibo.R;
import com.example.MyWeibo.lib.kits.NavigationDrawerManger;
import com.example.MyWeibo.lib.kits.PrefKit;
import com.example.MyWeibo.lib.kits.UIKits;
import com.facebook.drawee.view.SimpleDraweeView;


/**
 * Created by wanglu on 15/4/9.
 */
public class SlideMenuFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private TextView mUserName;
    private Button mActionSet;
    private SimpleDraweeView mUserImg;
    private ListView mSideMenuListView;
    private RelativeLayout mDrawerHeader;
    private int[] mImgDrawables;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean mUserLeanredDrawer;
    private boolean mFromSavedInstanceState;
    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;
    private NavigationDrawerCallbacks mCallBacks;
    private int mCurrentSelectionPos;
    private ColorStateList mColorStateList;

    private static NavigationDrawerManger manager = new NavigationDrawerManger();

    static {
        manager.registerFragment("全部资讯", new AllNewsFragment());
        manager.registerFragment("人气推荐", new HotNewsListFragment());
        manager.registerFragment("精彩评论", new HotComentFragment());
        manager.registerFragment("资讯主题", new SubscribeHostFragment());
        manager.registerFragment("收藏列表", new DiscoveryFragment());
        manager.registerFragment("偏好设置", new DiscoveryFragment());
    }

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            mCallBacks = (NavigationDrawerCallbacks) activity;
        } else {
            throw new ClassCastException("......");
        }
        TypedArray array = activity.getTheme().obtainStyledAttributes(new int[]{R.attr.textSelectColor, android.R.attr.textColor});
        mColorStateList = UIKits.createStateList(
                array.getColor(1, getResources().getColor(R.color.textColor)),
                array.getColor(0, getResources().getColor(R.color.toolbarColor))
        );

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLeanredDrawer = PrefKit.getBoolean(getActivity(), PREF_USER_LEARNED_DRAWER, false);
        if (savedInstanceState != null) {
            mCurrentSelectionPos = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }
        //setSelectItem(mCurrentSelectionPos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drawer_layout, null);
        mUserName = (TextView) view.findViewById(R.id.user_name);
        mUserImg = (SimpleDraweeView) view.findViewById(R.id.user_img);
        mSideMenuListView = (ListView) view.findViewById(R.id.drawer_list);
        mDrawerHeader = (RelativeLayout) view.findViewById(R.id.drawer_header);

        mSideMenuListView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, manager.getTitles()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view.findViewById(android.R.id.text1)).setTextColor(mColorStateList);
                return view;
            }
        });
        mSideMenuListView.setOnItemClickListener(this);

        mActionSet = (Button) view.findViewById(R.id.action_set);
        mActionSet.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        //初始化 listview position
        //mSideMenuListView.setItemChecked(mCurrentSelectionPos, true);
        setSelectItem(mCurrentSelectionPos);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setSelectItem(position);
    }

    @Override
    public void onClick(View v) {
    }


    /**
     * 初始化drawerlayout
     *
     * @param fragmentId
     * @param drawerLayout
     */
    public void setup(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        ActionBar actionBar = getActionbar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }
                if (!mUserLeanredDrawer) {
                    mUserLeanredDrawer = true;
                    PrefKit.writeBoolean(getActivity(), PREF_USER_LEARNED_DRAWER, true);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }
                if (!mUserLeanredDrawer) {
                    mUserLeanredDrawer = true;
                    PrefKit.writeBoolean(getActivity(), PREF_USER_LEARNED_DRAWER, true);
                }
            }
        };

        drawerLayout.setDrawerListener(mDrawerToggle);
        if (!mUserLeanredDrawer && !mFromSavedInstanceState) {
            openDrawerLayout();
        }
        // Defer code dependent on restoration of previous instance state.
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    private ActionBar getActionbar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }


    public void openDrawerLayout() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public interface NavigationDrawerCallbacks {
        void onNavigationItemsSelected(Fragment fragmeng, int pos);
    }

    private void setSelectItem(int pos) {
        mCurrentSelectionPos = pos;
        if (mSideMenuListView != null) {
            getActionbar().setTitle(manager.getTitle(pos));
            mSideMenuListView.setItemChecked(pos, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallBacks != null) {
            mCallBacks.onNavigationItemsSelected(manager.getFragment(pos), pos);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectionPos);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBacks = null;
    }
}
