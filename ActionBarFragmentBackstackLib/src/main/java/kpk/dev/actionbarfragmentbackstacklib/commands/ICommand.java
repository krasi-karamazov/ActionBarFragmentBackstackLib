package kpk.dev.actionbarfragmentbackstacklib.commands;

/**
 * Created by krasimir.karamazov on 2/5/14.
 */
public interface ICommand {
    public abstract void execute();
    public abstract void undo();
}
