package app.com.foodcoupons.customviews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.io.File;

import app.com.foodcoupons.R;
import app.com.foodcoupons.activities.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class ViewImageActivity extends BaseActivity {

    @BindView(R.id.ll_change_color)
    LinearLayout llChangeColor;
    @BindView(R.id.btn_done)
    Button btnDone;
    @BindView(R.id.img_pic)
    TouchImageView imgPic;

    @Override
    protected int getContentView() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        return R.layout.activity_view_image;
    }

    @Override
    protected void initUI() {
        ColorDrawable[] color = {
                new ColorDrawable(ContextCompat.getColor(this, R.color.black_tint)),
                new ColorDrawable(Color.BLACK)};
        final TransitionDrawable trans = new TransitionDrawable(color);
        llChangeColor.setBackground(trans);
        trans.startTransition(1000);
        ViewCompat.setTransitionName(imgPic, "full_imageview");
    }

    @Override
    protected void onCreateStuff() {
        String pic = getIntent().getExtras().getString("display");
        if (pic.contains("http")) {
            try {
                Picasso.get().load(pic).placeholder
                        (ContextCompat.getDrawable(this,R.drawable.pink_corner_round)).into(imgPic);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            File mFile = new File(pic);
            try {
                Picasso.get().load(mFile).placeholder
                        (ContextCompat.getDrawable(this,R.drawable.pink_corner_round)).into(imgPic);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Context getContext() {
        return ViewImageActivity.this;
    }

    @Override
    public void onClick(View v) {

    }

    @OnClick(R.id.btn_done)
    void closeFrame() {
        onBackPressed();
    }
}
