package kpk.dev.actionbarfragmentbackstacklib.states;

import android.os.Bundle;

import kpk.dev.actionbarfragmentbackstacklib.listeners.NavigationEventListener;
import kpk.dev.actionbarfragmentbackstacklib.ui.fragments.BaseFragment;

/**
 * Created by krasimir.karamazov on 2/5/14.
 */
public abstract class BaseState {
    private NavigationEventListener mStateChangeListener;
    private Bundle mArgs;

    public BaseState(NavigationEventListener receiver, Bundle args) {
        this(args);
        mStateChangeListener = receiver;
    }

    public BaseState(Bundle args) {
        mArgs = args;
    }

    protected final void setStateChangeListener(NavigationEventListener receiver){
        mStateChangeListener = receiver;
    }
    protected final Bundle getArgs() {
        return mArgs;
    }

    public final BaseFragment getFragment(){
        final BaseFragment fragment = getConcreteFragment();
        fragment.setState(this);
        fragment.setArguments(mArgs);
        return fragment;
    }

    protected abstract BaseFragment getConcreteFragment();

    public final void gotoState(BaseState state) {
        state.setStateChangeListener(mStateChangeListener);
        mStateChangeListener.onChangeState(state);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName());
        if(mArgs != null)
            builder.append(mArgs.size());
        return builder.toString();
    }
}
