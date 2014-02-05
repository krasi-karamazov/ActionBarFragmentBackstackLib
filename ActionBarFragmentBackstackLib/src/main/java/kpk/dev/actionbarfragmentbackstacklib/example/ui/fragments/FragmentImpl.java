package kpk.dev.actionbarfragmentbackstacklib.example.ui.fragments;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import kpk.dev.actionbarfragmentbackstacklib.R;
import kpk.dev.actionbarfragmentbackstacklib.ui.fragments.InternetFragment;

/**
 * Created by krasimir.karamazov on 2/5/14.
 */
public class FragmentImpl extends InternetFragment {
    public static final String FRAGMENT_DISPLAY_TXT_ARGS_KEY = "fragment_display";

    @Override
    protected void initUI(View rootView) {
        TextView tv = (TextView)rootView.findViewById(R.id.tv_fragment_display);
        tv.setText((!TextUtils.isEmpty(getArguments().getString(FRAGMENT_DISPLAY_TXT_ARGS_KEY)))?getArguments().getString(FRAGMENT_DISPLAY_TXT_ARGS_KEY):"");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void connectionAvailable() {
        showProgress(false);
    }

    @Override
    protected void connectionUnavailable() {

    }
}
