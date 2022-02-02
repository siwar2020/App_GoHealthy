package com.example.gohealthlyapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater mLayoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int images[] = {
            R.drawable.first_image,
            R.drawable.third_image,
            R.drawable.forth_image
    };

    int headings[] = {
            R.string.first_title,
            R.string.third_title,
            R.string.forth_title
    };
    int descriptions[] = {
            R.string.first_desc,
            R.string.third_desc,
            R.string.forth_desc
    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mLayoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=mLayoutInflater.inflate(R.layout.slides_layout,container,false);

        ImageView imageView=view.findViewById(R.id.slider_image);
        TextView heading=view.findViewById(R.id.slider_heading);
        TextView desc=view.findViewById(R.id.slider_desc);

        imageView.setImageResource(images[position]);
        heading.setText(headings[position]);
        desc.setText(descriptions[position]);

        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
