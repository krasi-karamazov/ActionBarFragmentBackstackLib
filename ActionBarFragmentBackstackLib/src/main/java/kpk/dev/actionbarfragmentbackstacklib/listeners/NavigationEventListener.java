package kpk.dev.actionbarfragmentbackstacklib.listeners;

import kpk.dev.actionbarfragmentbackstacklib.states.BaseState;
import kpk.dev.actionbarfragmentbackstacklib.ui.fragments.BaseFragment;

/**
 * Created by krasimir.karamazov on 2/5/14.
 */
public interface NavigationEventListener {
    public void onChangeState(BaseState state);
    public void onItemSelected(BaseFragment fragment);
}
