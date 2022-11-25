package com.negd.umangwebview.utils;

import android.graphics.Bitmap;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.request.RequestOptions;
import com.negd.umangwebview.R;
import com.negd.umangwebview.data.model.jp.DeviceData;
import com.negd.umangwebview.ui.jeevan_pramaan.device_select_screen.JPDeviceSelectAdapter;

import java.util.List;


public final class BindingUtils {

    private BindingUtils() {
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({"device_select_adapter"})
    public static void setDeviceSelectAdapter(RecyclerView recyclerView, List<DeviceData> optionList) {
        JPDeviceSelectAdapter adapter = (JPDeviceSelectAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(optionList);
        }
    }
    @BindingAdapter({"image_url"})
    public static void setImageUrl(AppCompatImageView imageView, String imageUrl){
        if(imageUrl !=null && imageUrl.length()>0 && imageUrl.contains("http")){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions=requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            requestOptions=requestOptions.encodeFormat(Bitmap.CompressFormat.PNG);
            requestOptions=requestOptions.transform(new CenterInside());

            Glide.with(imageView.getContext()).load(imageUrl).apply(requestOptions).into(imageView);
        }else if(imageUrl !=null){
            switch (imageUrl){
                case "OTHER_DEVICE":
                    Glide.with(imageView.getContext()).load(R.drawable.fingerprint_acc_setting).into(imageView);
                    break;
                case "BBPS":
                    Glide.with(imageView.getContext()).load(R.drawable.ic_bbps_latest).into(imageView);
                    break;
            }
        }
    }
}
