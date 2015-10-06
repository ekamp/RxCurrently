package ekamp.currently.view.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ekamp.currently.R;

/**
 * Custom {@link DialogFragment} that is used to display an error message to the user.
 *
 * @author Erik Kamp
 * @since 10/6/15.
 */
public class ErrorDialog extends DialogFragment {

    private String message, title, confirmationText;
    private ErrorDialogClickListener errorDialogClickListener;
    private static final String TAG = "ErrorDialog";

    @Bind(R.id.confirmation_button)
    Button confirmationButton;

    @Bind(R.id.message)
    TextView messageTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View returnedView = inflater.inflate(R.layout.fragment_error_dialog, container, false);
        ButterKnife.bind(this, returnedView);
        return returnedView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageTextView.setText(message);
        confirmationButton.setText(confirmationText);
        getDialog().setTitle(title);
    }

    /**
     * Creates a new {@link ErrorDialog} instance.
     *
     * @param message          message to be displayed within the Dialog
     * @param title            title to appear at the top of the Dialog
     * @param confirmationText text to appear within the confirmation button
     * @return new instance of {@link ErrorDialog}
     */
    public static ErrorDialog newInstance(String message, String title, String confirmationText) {
        ErrorDialog newErrorDialog = new ErrorDialog();
        newErrorDialog.setMessage(message);
        newErrorDialog.setTitle(title);
        newErrorDialog.setConfirmationText(confirmationText);
        return newErrorDialog;
    }

    /**
     * Creates a new {@link ErrorDialog} instance.
     *
     * @param errorDialogClickListener used to listen to when the {@link ErrorDialog} has been dismissed.
     * @param message                  message to be displayed within the Dialog
     * @param title                    title to appear at the top of the Dialog
     * @param confirmationText         text to appear within the confirmation button
     * @return new instance of {@link ErrorDialog}
     */
    public static ErrorDialog newInstance(ErrorDialogClickListener errorDialogClickListener,
                                          String message, String title, String confirmationText) {
        ErrorDialog newErrorDialog = new ErrorDialog();
        newErrorDialog.setMessage(message);
        newErrorDialog.setTitle(title);
        newErrorDialog.setConfirmationText(confirmationText);
        newErrorDialog.setErrorDialogClickListener(errorDialogClickListener);
        return newErrorDialog;
    }

    @OnClick(R.id.confirmation_button)
    public void onConfirmationClick() {
        errorDialogClickListener.onDismiss();
        dismiss();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setConfirmationText(String confirmationText) {
        this.confirmationText = confirmationText;
    }

    public void setErrorDialogClickListener(ErrorDialogClickListener errorDialogClickListener) {
        this.errorDialogClickListener = errorDialogClickListener;
    }

    public void showDialog(FragmentManager fragmentManager) {
        show(fragmentManager, TAG);
    }

    /**
     * ClickListener used to notify the view upon click action of the confirmation button.
     */
    public interface ErrorDialogClickListener {
        void onDismiss();
    }
}
