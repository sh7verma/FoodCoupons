package app.com.foodcoupons.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import app.com.foodcoupons.R;

/**
 * Created by dev on 27/9/18.
 */

public class Dialogs {


//    davidrabada01+50@gmail.com

    public interface DialogClick {
        void yes(DialogInterface dialog);

        void no(DialogInterface dialog);
    }

    public interface DialogTryAgainClick {
        void tryAgain(DialogInterface dialog);
    }

    public static void tryAgainDialog(Context mContext, String buttonMess, String message, final DialogTryAgainClick tryAgainClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.CustomDialog);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(buttonMess, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        tryAgainClick.tryAgain(dialog);
                    }
                });
        builder.create().show();
    }

    public static void confirmYesNoDialog(Context mContext, String message, final DialogClick dialogClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.CustomDialog);
        builder.setMessage(message)
                .setCancelable(true)
                .setPositiveButton(mContext.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialogClick.yes(dialog);
                    }
                })
                .setNegativeButton(mContext.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogClick.no(dialog);
                    }
                });

        builder.create().show();
        /*final Dialog dialog = new Dialog(this, R.style.DialogScaleAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(this).inflate(R.layout.delete_confirm_dilog, null);
        dialog.setContentView(view);
        DilogView dilogView = new DilogView();
        ButterKnife.bind(dilogView, view);
        dilogView.txtMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));
        dilogView.txtMessage.setPadding(mWidth / 22, mWidth / 9, mWidth / 22, mWidth / 9);
        dilogView.txtDelete.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        dilogView.txtDelete.setPadding(mWidth / 22, mWidth / 32, mWidth / 22, mWidth / 22);
        dilogView.txtNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        dilogView.txtNo.setPadding(mWidth / 22, mWidth / 32, mWidth / 12, mWidth / 22);
        dilogView.txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dilogView.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db.removeCard();
            }
        });
        dialog.show();*/
    }
    public static void confirmCancelDialog(Context mContext, String message, final DialogClick dialogClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.CustomDialog);
        builder.setMessage(message)
                .setCancelable(true)
                .setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialogClick.yes(dialog);
                    }
                })
                .setNegativeButton(mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogClick.no(dialog);
                    }
                });
        builder.create().show();
    }

    public interface DialogTwoButonClick {
        void left(Dialog dialog);
        void right(Dialog dialog);
    }

    // The method that displays the popup.
    public static void showPopup(Context context, View view, String mess) {
        Point p = new Point();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        p.x = location[0];
        p.y = location[1];

//        Drawable drawable = context.getResources().getDrawable(R.drawable.pink_solid_round_corner);        // Inflate the popup_layout.xml
//        GradientDrawable gradientDrawable = (GradientDrawable) drawable;
//        gradientDrawable.setCornerRadius(5);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_layout, null);
        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y = -100;
        // Clear the default translucent background
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
        LinearLayout layout1 = layout.findViewById(R.id.ll_main);
//        layout1.setBackground(gradientDrawable);
        TextView txtmess = layout.findViewById(R.id.txt_edit);
        txtmess.setText(mess);
        Animations.scaleUpView(popup.getContentView(), 0, 1, 200);

    }
    /*
    public static class DilogViewOk {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_message)
        TextView txtMess;
        @BindView(R.id.txt_title1)
        TextView txtTitle1;
        @BindView(R.id.txt_message1)
        TextView txtMess1;
        @BindView(R.id.txt_left)
        TextView txtLeft;
        @BindView(R.id.txt_right)
        TextView txtRight;
    }
    public static void showTwoButtonDialog(Context mContext, int call, int title_color, String title, String mess, String leftButtonText, String rightButtonText, int mWidth, final DialogTwoButonClick okClick) {
        final Dialog dialog = new Dialog(mContext, R.style.DialogSlideBottomUp);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM;
        dialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_call_cancel, null, false);
        dialog.setContentView(view);
        dialog.getWindow().setLayout((int) (mWidth * 0.90), ViewGroup.LayoutParams.WRAP_CONTENT);
         DilogViewOk dilogView = new  DilogViewOk();
        ButterKnife.bind(dilogView, view);
        if (call == 0){
            dilogView.txtTitle.setVisibility(View.GONE);
            dilogView.txtMess.setVisibility(View.GONE);
            dilogView.txtTitle1.setVisibility(View.VISIBLE);
            dilogView.txtMess1.setVisibility(View.VISIBLE);
            dilogView.txtTitle1.setText(title);
            dilogView.txtTitle1.setTextColor(mContext.getResources().getColor(title_color));
            dilogView.txtMess1.setText(mess);
        }
        else {
            dilogView.txtTitle.setVisibility(View.VISIBLE);
            dilogView.txtMess.setVisibility(View.VISIBLE);
            dilogView.txtTitle1.setVisibility(View.GONE);
            dilogView.txtMess1.setVisibility(View.GONE);
            dilogView.txtTitle.setText(title);
            dilogView.txtTitle.setTextColor(mContext.getResources().getColor(title_color));
            dilogView.txtMess.setText(mess);
        }
        dilogView.txtLeft.setText(leftButtonText);
        dilogView.txtRight.setText(rightButtonText);
        dilogView.txtTitle.setText(title);
        dilogView.txtTitle.setTextColor(mContext.getResources().getColor(title_color));
        dilogView.txtMess.setText(mess);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return false;
            }
        });
        dilogView.txtLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okClick.left(dialog);
            }
        });
        dilogView.txtRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okClick.right(dialog);
            }
        });
        dialog.show();
    }*/
   /* public static class DilogViewUpdate {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_mess)
        TextView txtMess;
        @BindView(R.id.txt_force_update)
        TextView txtForceUpdate;
        @BindView(R.id.txt_update)
        TextView txtUpdate;
        @BindView(R.id.txt_later)
        TextView txtLater;
        @BindView(R.id.ll_main_update)
        LinearLayout llMainUpdate;
    }
    public static void showUpdateDialog(Context mContext, int mWidth,String mess,String force, final DialogClick okClick) {
        final Dialog dialog = new Dialog(mContext, R.style.DialogScaleAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        dialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.update_layout, null, false);
        dialog.setContentView(view);
        dialog.getWindow().setLayout((int) (mWidth * 0.90), ViewGroup.LayoutParams.WRAP_CONTENT);
        final DilogViewUpdate dilogView = new  DilogViewUpdate();
        ButterKnife.bind(dilogView, view);
        dilogView.txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));
        dilogView.txtTitle.setPadding(mWidth / 32, mWidth / 22, mWidth / 32, 0);
        dilogView.txtMess.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        dilogView.txtMess.setPadding(mWidth / 32, mWidth / 22, mWidth / 32, 0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (mWidth * 0.3), ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.setMargins(mWidth / 32, mWidth / 32, mWidth / 64, mWidth / 32);
        dilogView.txtUpdate.setLayoutParams(params);
        dilogView.txtUpdate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        dilogView.txtUpdate.setPadding(0, mWidth / 32, 0, mWidth / 32);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.setMargins(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        dilogView.txtForceUpdate.setLayoutParams(params2);
        dilogView.txtForceUpdate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        dilogView.txtForceUpdate.setPadding(0, mWidth / 32, 0, mWidth / 32);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams((int) (mWidth * 0.3), ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.weight = 1;
        params1.setMargins(mWidth / 64, mWidth / 32, mWidth / 32, mWidth / 32);
        dilogView.txtLater.setLayoutParams(params1);
        dilogView.txtLater.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        dilogView.txtLater.setPadding(0, mWidth / 32, 0, mWidth / 32);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                }
                return false;
            }
        });
        dialog.show();
        if (android.text.TextUtils.isEmpty(force)){
            dilogView.llMainUpdate.setVisibility(View.VISIBLE);
            dilogView.txtForceUpdate.setVisibility(View.GONE);
        }
        else {
            dilogView.llMainUpdate.setVisibility(View.GONE);
            dilogView.txtForceUpdate.setVisibility(View.VISIBLE);
        }
        dilogView.txtMess.setText(mess);
        dilogView.txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okClick.yes(dialog);
            }
        });
        dilogView.txtForceUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okClick.yes(dialog);
            }
        });
        dilogView.txtLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okClick.no(dialog);
            }
        });
    }*/
}