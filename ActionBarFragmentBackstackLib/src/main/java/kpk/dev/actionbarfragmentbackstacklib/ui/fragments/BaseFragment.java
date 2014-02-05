package kpk.dev.actionbarfragmentbackstacklib.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kpk.dev.actionbarfragmentbackstacklib.commands.ICommand;
import kpk.dev.actionbarfragmentbackstacklib.listeners.NavigationEventListener;
import kpk.dev.actionbarfragmentbackstacklib.states.BaseState;

/**
 * Created by krasimir.karamazov on 2/5/14.
 */
public abstract class BaseFragment extends Fragment {
    private BaseState mState;
    private ICommand mLastCommand;
    private NavigationEventListener mNavigationChangeListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(!(activity instanceof NavigationEventListener)) {
            throw new IllegalStateException("Activity must implement NavigationEventListener");
        }
        mNavigationChangeListener = (NavigationEventListener)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        initUI(rootView);
        return rootView;
    }

    public void setState(BaseState state) {
        mState = state;
    }

    public final BaseState getState() {
        return mState;
    }

    protected abstract void initUI(View rootView);

    protected abstract int getLayoutId();

    protected final void addCommand(ICommand command){
        command.execute();
        mLastCommand = command;
    }

    protected final void undoLastCommand(){
        if(mLastCommand != null) {
            mLastCommand.undo();
            mLastCommand = null;
        }
    }
}
