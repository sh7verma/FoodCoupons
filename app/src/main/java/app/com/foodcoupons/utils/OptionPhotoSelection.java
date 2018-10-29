package app.com.foodcoupons.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.com.foodcoupons.BuildConfig;
import app.com.foodcoupons.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.soundcloud.android.crop.Crop.REQUEST_CROP;

/**
 * Created by app on 9/19/2016.
 */
public class OptionPhotoSelection extends Activity {

    private static final int MULTIPLE_PERMISSIONS = 3;
    private static final int GALLERY_PERMISSION = 12;
    final int CAMERA_INTENT = 4;
    final int GALLERY_INTENT = 5;
    final int WRITE_EXTERNAL_ID = 1;
    final int READ_EXTERNAL_ID = 2;
    String type;
    Utils utils;
    int mScreenwidth, mScreenheight;
    String[] permissions = new String[]{
            WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    @BindView(R.id.view_one)
    View viewOne;
    @BindView(R.id.txt_view_photo)
    TextView txtViewPhoto;
    @BindView(R.id.view_two)
    View viewTwo;
    @BindView(R.id.txt_take_photo)
    TextView txtTakePhoto;
    @BindView(R.id.txt_option_gallery)
    TextView txtOptionGallery;
    @BindView(R.id.txt_cancel)
    TextView txtCancel;
    MarshMallowPermission mMarshMallowPermission;
    private long mSystemTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = this.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        this.setFinishOnTouchOutside(true);
        setContentView(R.layout.dialog_photoselection);
        getDefaults();
        getWindow().setLayout((int) (mScreenwidth * 0.95), (int) (mScreenheight * 0.5));
        ButterKnife.bind(this);
        initUI();
        mMarshMallowPermission = new MarshMallowPermission(this);
    }

    void getDefaults() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenwidth = dm.widthPixels;
        mScreenheight = dm.heightPixels;
        utils = new Utils(OptionPhotoSelection.this);
        type = getIntent().getStringExtra("type");
    }

    void initUI() {
        if (type.equals("1")) {
            txtViewPhoto.setVisibility(View.GONE);
            viewOne.setVisibility(View.GONE);
            viewTwo.setVisibility(View.GONE);
        } else {
            txtViewPhoto.setVisibility(View.VISIBLE);
            viewOne.setVisibility(View.VISIBLE);
            viewTwo.setVisibility(View.VISIBLE);
        }


        txtViewPhoto.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mScreenwidth * 0.04));
        txtViewPhoto.setPadding(0, mScreenwidth / 28, 0, mScreenwidth / 28);

        txtTakePhoto.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mScreenwidth * 0.04));
        txtTakePhoto.setPadding(0, mScreenwidth / 28, 0, mScreenwidth / 28);

        txtOptionGallery.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mScreenwidth * 0.04));
        txtOptionGallery.setPadding(0, mScreenwidth / 28, 0, mScreenwidth / 28);

        txtCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mScreenwidth * 0.04));
        txtCancel.setPadding(0, mScreenwidth / 28, 0, mScreenwidth / 28);

    }


    @OnClick(R.id.txt_view_photo)
    void onViewPhoto() {
        Intent in = new Intent();
        in.putExtra("filePath", "show");
        setResult(RESULT_OK, in);
        finish();
    }

    @OnClick(R.id.txt_take_photo)
    void onCamera() {
        if (checkPermissions()) {
            startCameraActivity();
        }
    }

    @OnClick(R.id.txt_option_gallery)
    void onGallery() {
        if (checkPermissions()) {
            showGallery();
        }
    }

    @OnClick(R.id.txt_cancel)
    void onCancel() {
        finish();
    }

    protected void startCameraActivity() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mSystemTime = System.currentTimeMillis();
        File f = new File(Environment.getExternalStorageDirectory(), "FoodCoupon" + mSystemTime + ".png");
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            Uri photoURI = FileProvider.getUriForFile(OptionPhotoSelection.this,
                    BuildConfig.APPLICATION_ID + ".provider", f);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        } else {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        }
        cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startActivityForResult(cameraIntent, CAMERA_INTENT);
    }

    void showGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_INTENT:
                if (resultCode == RESULT_OK) {
                    File dir = Environment.getExternalStorageDirectory();
                    File f = new File(dir, "FoodCoupon" + mSystemTime + ".png");
                    Log.e("camera photo", "is " + f.getAbsolutePath());
                    Uri rr = Uri.fromFile(new File(f.getAbsolutePath()));
                    beginCrop(rr);
                }
                break;
            case GALLERY_INTENT:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri uri = data.getData();
                        final String path = FileUtils.getPath(this, uri);
                        File f = new File(path);
                        Uri rr = Uri.fromFile(new File(f.getAbsolutePath()));
                        beginCrop(rr);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(OptionPhotoSelection.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    handleCrop(data);
                } else if (resultCode == Crop.RESULT_ERROR) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(Intent result) {
        try {
            Uri ur = Crop.getOutput(result);
            String picturePath = FileUtils.getPath(OptionPhotoSelection.this, ur);
            Intent in = new Intent();
            in.putExtra("filePath", getRealPathFromURI(getImageUri(OptionPhotoSelection.this, getBitmap(picturePath))));
            setResult(RESULT_OK, in);
            finish();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Toast.makeText(OptionPhotoSelection.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public Bitmap getBitmap(String path) {
        int rotation = getImageOrientation(path);
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap sourceBitmap = BitmapFactory.decodeFile(path, options);

        Bitmap imgBm = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix,
                true);

        return imgBm;
    }

    public int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(OptionPhotoSelection.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
//            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            mMarshMallowPermission.requestPermission(permissions, mMarshMallowPermission.SP_CAMERA_PERMISSION, MULTIPLE_PERMISSIONS, R.string.camera_permission);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case GALLERY_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    openFacebookGallery();
                }
                break;

            case WRITE_EXTERNAL_ID:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    startCameraActivity();
                break;
            case READ_EXTERNAL_ID:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    showGallery();
                break;
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length == 2) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        // permissions granted.
                        startCameraActivity();
                    }
                } else if (grantResults.length == 1) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permissions granted.
                        startCameraActivity();
                    }
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    public void copy(String urls, long dst) throws IOException {
//        URL url = new URL(urls);
//        InputStream input = url.openStream();
//        try {
//            File storagePath = Environment.getExternalStorageDirectory();
//            OutputStream output = new FileOutputStream(storagePath + "/" + dst + ".png");
//            try {
//                byte[] buffer = new byte[1024];
//                int bytesRead = 0;
//                while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
//                    output.write(buffer, 0, bytesRead);
//                }
//            } finally {
//                output.close();
//            }
//        } finally {
//            input.close();
//        }
//
//    }
//
//    public class DownloadFileFromURL extends AsyncTask<String, String, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... f_url) {
//            int count;
//            String path = null;
//            try {
//                URL url = new URL(f_url[0]);
//                URLConnection conection = url.openConnection();
//                conection.connect();
//                int lenghtOfFile = conection.getContentLength();
//                String pathRoot = generateNoMedia();
//                InputStream input = new BufferedInputStream(url.openStream(), 8192);
//                File storagePath = Environment.getExternalStorageDirectory();
//                path = pathRoot + "/" + System.currentTimeMillis() + ".png";
//                OutputStream output = new FileOutputStream(path);
//                byte data[] = new byte[1024];
//                long total = 0;
//
//                while ((count = input.read(data)) != -1) {
//                    total += count;
//                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
//                    output.write(data, 0, count);
//                }
//
//                output.flush();
//                output.close();
//                input.close();
//
//            } catch (Exception e) {
//                Log.e("Error: ", e.getMessage());
//            }
//
//            return path;
//        }
//
//        /**
//         * Updating progress bar
//         */
//        protected void onProgressUpdate(String... progress) {
//            Log.e("ima", "post progress " + progress);
//        }
//
//        /**
//         * After completing background task
//         * Dismiss the progress dialog
//         **/
//        @Override
//        protected void onPostExecute(String file_url) {
//            File file = new File(file_url);
//            Uri uri = Uri.fromFile(file);
//            Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
//            Crop.of(uri, destination).asSquare().start(OptionPhotoSelection.this);
//            Log.e("ima", "post " + file_url);
//        }
//
//    }
//
//    public String generateNoMedia() {
//        String root = Environment.getExternalStorageDirectory()
//                .toString();
//        File myDir = new File(root + "/Oryxre/fbGallery");
//        if (!myDir.exists())
//            myDir.mkdirs();
//        String fileName = ".nomedia";
//        File ff = new File(myDir, fileName);
//        if (!ff.exists()) {
//            try {
//                ff.createNewFile();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return myDir.getAbsolutePath();
//    }
}