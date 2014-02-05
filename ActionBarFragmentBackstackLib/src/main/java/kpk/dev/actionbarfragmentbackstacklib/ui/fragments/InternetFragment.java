package kpk.dev.actionbarfragmentbackstacklib.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by krasimir.karamazov on 2/5/14.
 * Abstract class for a fragment which will need an internet connection
 */
public abstract class InternetFragment extends BaseFragment {
    private boolean mProgressShown;
    private boolean mProgressInDialog;
    private ProgressDialog mProgressDialog;
    public static int CONNECTION_TIMEOUT_IN_SECONDS = 5;


    @Override
    public final void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isConnectionAvailable()){
            connectionAvailable();
        }else{
            connectionUnavailable();
        }
    }

    /**
     * Callback for handling an active connection
     */
    protected abstract void connectionAvailable();

    /**
     * Callback for handling an inactive connection
     */
    protected abstract void connectionUnavailable();


    /**
     * Convinience method to show a progress (dialog or in the actionbar)
     */
    protected final void showProgress(boolean inDialog){
        if(!mProgressShown){
            mProgressShown = true;
            mProgressInDialog = inDialog;
            if(mProgressInDialog) {
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setCancelable(false);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.show();
                getActivity().setProgressBarIndeterminateVisibility(false);
            }else{
                getActivity().setProgressBarIndeterminateVisibility(true);
                getActivity().setProgressBarIndeterminate(true);
            }
        }
    }

    /**
     * Convinience method to dismiss progress (dialog or in the actionbar)
     */
    protected final void dismissProgress(){
        if(mProgressShown) {
            if(mProgressInDialog){
                if(mProgressDialog != null){
                    mProgressDialog.dismiss();
                }
            }else{
                getActivity().setProgressBarIndeterminateVisibility(false);
            }
            mProgressShown = false;
        }
    }


    private boolean isConnectionAvailable() {
        ConnectivityManager manager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netIfo = manager.getActiveNetworkInfo();
        if(netIfo == null) {
            return false;
        }else{
            if(!netIfo.isConnectedOrConnecting()) {
                return false;
            }else{
                return true;
            }
        }
    }
}
