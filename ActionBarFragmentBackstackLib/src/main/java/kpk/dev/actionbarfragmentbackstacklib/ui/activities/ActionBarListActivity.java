package kpk.dev.actionbarfragmentbackstacklib.ui.activities;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.List;
import kpk.dev.actionbarfragmentbackstacklib.models.ActionBarNavigationModel;

/**
 * Created by krasimir.karamazov on 2/5/14.
 */
public abstract class ActionBarListActivity extends BaseActionBarBackStackActivity {

    @Override
    protected int getNavigationMode() {
        return ActionBar.NAVIGATION_MODE_LIST;
    }

    @Override
    protected void setupNavigation() {
        getSupportActionBar().setListNavigationCallbacks(new NavigationAdapter(this, android.R.layout.simple_spinner_dropdown_item, mMainNavigationList), getNavigationListener());
    }

    private ActionBar.OnNavigationListener getNavigationListener() {
        return new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ActionBarListActivity.this.onNavigationItemSelected(mMainNavigationList.get(itemPosition).getTag(), ft);
                ft.commit();
                return true;
            }
        };
    }

    private class NavigationAdapter extends ArrayAdapter<ActionBarNavigationModel>{
        public NavigationAdapter(Context context, int textViewResourceId, List<ActionBarNavigationModel> objects) {
            super(context, textViewResourceId, objects);
        }

        private View getCustomView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if(row == null){
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(getSpinnerItemLayoutId(), parent, false);
            }

            handleSpinnerItemDisplay(row, getItem(position));

            return row;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
    }

    @Override
    protected int getLastFragmentTag() {
        int tag = mMainNavigationList.get(getSupportActionBar().getSelectedNavigationIndex()).getTag();
        return tag;
    }

    protected abstract int getSpinnerItemLayoutId();
    protected abstract void handleSpinnerItemDisplay(View row, ActionBarNavigationModel navModel);

}
