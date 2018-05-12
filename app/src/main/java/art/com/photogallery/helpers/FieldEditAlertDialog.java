package art.com.photogallery.helpers;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import art.com.photogallery.Params.Params;
import art.com.photogallery.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FieldEditAlertDialog {
    final private Context ctx;
    private Callback callback;
    private final ViewHolder holder;
    private final View view;
    private AlertDialog dialog;
    private String title;
    private String hint;

    public interface Callback{
        void getText(String text);
    }

    public FieldEditAlertDialog(Callback callback, Context ctx, String title, String hint) {
        this.ctx = ctx;
        this.callback = callback;
        this.title = title;
        this.hint = hint;
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view  = inflater.inflate(R.layout.dialog_field_edit, null);
        holder = new FieldEditAlertDialog.ViewHolder(view);
    }

    public void initAlert(boolean cancelable) {
        initLabel();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
        alertDialog.setView(this.view);
        alertDialog.setCancelable(cancelable);
        dialog = alertDialog.show();
    }

    private void initLabel(){
        holder.label.setText(title);
        holder.editBox.setHint(hint);
    }

    public class ViewHolder {
        @BindView(R.id.viewCleanSearchBox)
        ImageView cleanSearchButton;
        @BindView(R.id.imageViewApprove)
        ImageView approveButton;
        @BindView(R.id.editTextBox)
        EditText editBox;
        @BindView(R.id.textViewLabel)
        TextView label;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
            editBox.setSelectAllOnFocus(true);
        }

        @OnClick({R.id.viewCleanSearchBox, R.id.imageViewApprove})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.viewCleanSearchBox:
                    editBox.setText(Params.EMPTY_VALUE);
                    break;
                case R.id.imageViewApprove:
                    callback.getText(String.valueOf(editBox.getText()));
                    dialog.dismiss();
                    break;
            }
        }

    }
}
