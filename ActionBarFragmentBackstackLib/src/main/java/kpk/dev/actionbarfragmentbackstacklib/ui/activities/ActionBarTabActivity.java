package kpk.dev.actionbarfragmentbackstacklib.ui.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

/**
 * Created by krasimir.karamazov on 2/5/14.
 */
public abstract class ActionBarTabActivity extends BaseActionBarBackStackActivity {

    @Override
    protected int getNavigationMode() {
        return ActionBar.NAVIGATION_MODE_TABS;
    }

    @Override
    protected void setupNavigation() {
        getSupportActionBar().removeAllTabs();
        for(int i = 0; i < mMainNavigationList.size(); i++){
            ActionBar.Tab tab = getSupportActionBar().newTab();
            tab.setText(mMainNavigationList.get(i).getTitle());
            tab.setIcon((mMainNavigationList.get(i).getIconId() != -1)?mMainNavigationList.get(i).getIconId():0);
            tab.setTabListener(getTabListener());
            tab.setTag(mMainNavigationList.get(i).getTag());
            getSupportActionBar().addTab(tab);
        }
    }

    private ActionBar.TabListener getTabListener() {
        return new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                onNavigationItemSelected((Integer)tab.getTag(), ft);
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                onNavigationItemUnselected((Integer)tab.getTag(), ft);
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                onNavigationItemReselected((Integer) tab.getTag(), ft);
            }
        };
    }

    @Override
    protected int getLastFragmentTag() {
        ActionBar.Tab tab = getSupportActionBar().getSelectedTab();
        return (Integer)tab.getTag();
    }
}
