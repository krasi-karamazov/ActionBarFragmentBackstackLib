package kpk.dev.actionbarfragmentbackstacklib.example.states;

import android.os.Bundle;

import kpk.dev.actionbarfragmentbackstacklib.example.ui.fragments.FragmentImpl;
import kpk.dev.actionbarfragmentbackstacklib.listeners.NavigationEventListener;
import kpk.dev.actionbarfragmentbackstacklib.states.BaseState;
import kpk.dev.actionbarfragmentbackstacklib.ui.fragments.BaseFragment;

/**
 * Created by krasimir.karamazov on 2/5/14.
 */
public class StateImpl extends BaseState {

    public StateImpl(NavigationEventListener receiver, Bundle args) {
        super(receiver, args);
    }

    public StateImpl(Bundle args) {
        super(args);
    }

    @Override
    protected BaseFragment getConcreteFragment() {
        return new FragmentImpl();
    }
}
