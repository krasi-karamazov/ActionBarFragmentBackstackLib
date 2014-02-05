package kpk.dev.actionbarfragmentbackstacklib.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

import kpk.dev.actionbarfragmentbackstacklib.listeners.NavigationEventListener;
import kpk.dev.actionbarfragmentbackstacklib.models.ActionBarNavigationModel;
import kpk.dev.actionbarfragmentbackstacklib.states.BaseState;
import kpk.dev.actionbarfragmentbackstacklib.ui.fragments.BaseFragment;

/**
 * Created by krasimir.karamazov on 2/5/14.
 */
public abstract class BaseActionBarBackStackActivity extends ActionBarActivity implements NavigationEventListener {

    private static final String SELECTED_POSITION_STATE_KEY = "SELECTED_POSITION_KEY";
    private static final String STACKS_STATE_KEY = "STACKS";
    protected List<ActionBarNavigationModel> mMainNavigationList;
    protected HashMap<Integer, Stack<String>> mBackStacks;
    protected Stack<String> mSelectedBackStack;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(getContentView());
        int savedPosition = 0;
        mMainNavigationList = getMainNavigationItems();
        if (savedInstanceState != null) {
            mBackStacks = (HashMap<Integer, Stack<String>>) savedInstanceState.getSerializable(STACKS_STATE_KEY);
            savedPosition = savedInstanceState.getInt(SELECTED_POSITION_STATE_KEY, 0);
        }
        else{
            mBackStacks = new HashMap<Integer, Stack<String>>();
            for(ActionBarNavigationModel model : mMainNavigationList) {
                mBackStacks.put(model.getTag(), new Stack<String>());
            }
        }
        configureActionBar();
        if(savedPosition > 0) {
            if(getSupportActionBar().getNavigationMode() != ActionBar.NAVIGATION_MODE_STANDARD) {
                getSupportActionBar().setSelectedNavigationItem(savedPosition);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int selectedNavIndex = getSupportActionBar().getSelectedNavigationIndex();
        outState.putSerializable(STACKS_STATE_KEY, mBackStacks);
        outState.putSerializable(SELECTED_POSITION_STATE_KEY, selectedNavIndex);
    }

    @Override
    protected final void onResume() {
        super.onResume();
        try{
            Stack<String> backStack = mBackStacks.get(getLastFragmentTag());
            if (! backStack.isEmpty()){
                String tag = backStack.peek();
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment.isDetached()) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.attach(fragment);
                    ft.commit();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected final void onPause() {
        super.onPause();
        Stack<String> backStack = mBackStacks.get(getLastFragmentTag());
        if (! backStack.isEmpty()) {
            String tag = backStack.peek();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            ft.detach(fragment);
            ft.commit();
        }
    }

    protected final void onNavigationItemSelected(int navItemTag, FragmentTransaction ft){
        try{
            Stack<String> backStack = mBackStacks.get(navItemTag);
            if(mSelectedBackStack != null){
                if(!mSelectedBackStack.equals(backStack)){

                    while (mSelectedBackStack.size() > 0){
                        String tag = mSelectedBackStack.pop();
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
                        ft.remove(fragment);
                    }
                }
            }

            if (backStack.isEmpty()) {
                BaseFragment fragment = getSelectedNavItemByTag(navItemTag);
                addFragment(fragment, backStack, ft);
            }
            else{
                showFragment(backStack, ft);
            }
            mSelectedBackStack = backStack;
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected final void onNavigationItemReselected(int navItemTag, FragmentTransaction ft){
        Stack<String> backStack = mBackStacks.get(navItemTag);

        while (backStack.size() > 1){
            String tag = backStack.pop();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            ft.remove(fragment);
        }
        showFragment(backStack, ft);
    }

    protected final void onNavigationItemUnselected(int navItemTag, FragmentTransaction ft){
        if(mBackStacks != null) {
            Stack<String> backStack = mBackStacks.get(navItemTag);
            String tag = backStack.peek();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            ft.detach(fragment);
        }
    }

    public void showFragment(Stack<String> backStack, FragmentTransaction ft) {
        String tag = backStack.peek();
        BaseFragment fragment = (BaseFragment)getSupportFragmentManager().findFragmentByTag(tag);
        ft.attach(fragment);
    }

    private final void configureActionBar(){
        setupNavigation();
        getSupportActionBar().setNavigationMode(getNavigationMode());
        getSupportActionBar().setDisplayShowHomeEnabled(getShowHomeEnabled());
        getSupportActionBar().setDisplayHomeAsUpEnabled(getDisplayHomeAsUp());
        getSupportActionBar().setHomeButtonEnabled(getHomeButtonEnabled());
        setSupportProgressBarIndeterminateVisibility(getSupportForIndeterminateProgressBarInActionBar());
    }

    protected final BaseFragment getSelectedNavItemByTag(int tag) {
        BaseFragment fragment = null;
        for(ActionBarNavigationModel model : mMainNavigationList) {
            if(model.getTag() == tag){
                fragment = model.getState().getFragment();
            }
        }
        return fragment;
    }

    private void addFragment(BaseFragment fragment) {
        ActionBar.Tab tab = getSupportActionBar().getSelectedTab();
        Stack<String> backStack = mBackStacks.get(tab.getTag());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        String tag = backStack.peek();
        Fragment top = getSupportFragmentManager().findFragmentByTag(tag);
        if(top != null){
            ft.detach(top);
        }
        addFragment(fragment, backStack, ft);
        ft.commit();
    }

    protected final void addFragment(BaseFragment fragment, Stack<String> backStack, FragmentTransaction ft) {
        String tag = UUID.randomUUID().toString();
        ft.add(getMainFragmentContainer(), fragment, tag);
        backStack.push(tag);
    }

    @Override
    public void onChangeState(BaseState state) {
        addFragment(state.getFragment());
    }

    @Override
    public void onItemSelected(BaseFragment fragment) {
        addFragment(fragment);
    }

    protected abstract List<ActionBarNavigationModel> getMainNavigationItems();
    protected abstract int getContentView();
    protected abstract int getNavigationMode();
    protected abstract int getMainFragmentContainer();
    protected abstract void setupNavigation();
    protected abstract boolean getSupportForIndeterminateProgressBarInActionBar();
    protected abstract boolean getShowHomeEnabled();
    protected abstract boolean getDisplayHomeAsUp();
    protected abstract boolean getHomeButtonEnabled();
    protected abstract int getLastFragmentTag();
}
