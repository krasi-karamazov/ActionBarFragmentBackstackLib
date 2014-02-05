package kpk.dev.actionbarfragmentbackstacklib.models;

import kpk.dev.actionbarfragmentbackstacklib.states.BaseState;

/**
 * Created by krasimir.karamazov on 2/5/14.
 */
public class ActionBarNavigationModel {
    private String mTitle;
    private int mTag;
    private int mIconId;
    private BaseState mState;

    public ActionBarNavigationModel(String title, int tag, int iconId) {
        mTitle = title;
        mTag = tag;
        mIconId = iconId;
    }

    public ActionBarNavigationModel() {this(null, -1, -1);}

    public void setTag(int tag) {
        mTag = tag;
    }

    public int getTag() {
        return mTag;
    }

    public void setIconId(int iconId) {
        mIconId = iconId;
    }

    public int getIconId() {
        return mIconId;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setState(BaseState state) {
        mState = state;
    }

    public BaseState getState() {
        return mState;
    }
}
