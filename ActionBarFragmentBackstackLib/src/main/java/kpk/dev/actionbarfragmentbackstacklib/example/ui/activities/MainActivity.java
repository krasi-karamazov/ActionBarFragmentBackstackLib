package kpk.dev.actionbarfragmentbackstacklib.example.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import kpk.dev.actionbarfragmentbackstacklib.R;
import kpk.dev.actionbarfragmentbackstacklib.example.states.StateImpl;
import kpk.dev.actionbarfragmentbackstacklib.example.ui.fragments.FragmentImpl;
import kpk.dev.actionbarfragmentbackstacklib.models.ActionBarNavigationModel;
import kpk.dev.actionbarfragmentbackstacklib.ui.activities.ActionBarListActivity;
import kpk.dev.actionbarfragmentbackstacklib.ui.activities.ActionBarTabActivity;

public class MainActivity extends ActionBarTabActivity {

    @Override
    protected List<ActionBarNavigationModel> getMainNavigationItems() {
        final List<ActionBarNavigationModel> navModels = new ArrayList<ActionBarNavigationModel>();
        for(int i = 0; i < 4; i++) {
            ActionBarNavigationModel model  = new ActionBarNavigationModel("Nav model " + i, i, R.drawable.ic_launcher);
            Bundle bundle = new Bundle();
            bundle.putString(FragmentImpl.FRAGMENT_DISPLAY_TXT_ARGS_KEY, "Fragment " + i);
            model.setState(new StateImpl(this, bundle));
            navModels.add(model);
        }
        return navModels;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected int getMainFragmentContainer() {
        return R.id.container;
    }

    /*@Override
    protected int getSpinnerItemLayoutId() {
        return R.layout.layout_list_nav_item;
    }*/

    @Override
    protected boolean getSupportForIndeterminateProgressBarInActionBar() {
        return true;
    }

    @Override
    protected boolean getShowHomeEnabled() {
        return true;
    }

    @Override
    protected boolean getDisplayHomeAsUp() {
        return false;
    }

    @Override
    protected boolean getHomeButtonEnabled() {
        return false;
    }

    /*@Override
    protected void handleSpinnerItemDisplay(View row,  ActionBarNavigationModel navModel) {
        TextView tv = (TextView) row.findViewById(R.id.tv_nav_item);
        ImageView iv = (ImageView) row.findViewById(R.id.iv_nav_item);
        tv.setText(navModel.getTitle());
        if(navModel.getIconId() > -1){
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(navModel.getIconId());
        }else{
            iv.setVisibility(View.GONE);
        }
    }*/
}
