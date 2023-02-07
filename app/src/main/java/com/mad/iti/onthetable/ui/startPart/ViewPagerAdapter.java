package com.mad.iti.onthetable.ui.startPart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.mad.iti.onthetable.R;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;

    int[] animations = {R.raw.foodisready , R.raw.vegetablefood};

    int[] headerText = {R.string.headerOne_onboarding , R.string.headerTwo_onboarding};

    int[] descriptionText = {R.string.descriptionOne_onboarding , R.string.descriptionTwo_onboarding};

    public ViewPagerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return animations.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_onboarding_layout,container,false);

        LottieAnimationView slideLotti = view.findViewById(R.id.splash_lotti);
        TextView slideHeader = view.findViewById(R.id.header_textView);
        TextView slideDescription = view.findViewById(R.id.description_textView);

        slideLotti.setAnimation(animations[position]);
        slideHeader.setText(headerText[position]);
        slideDescription.setText(descriptionText[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ConstraintLayout)object);
    }
}

