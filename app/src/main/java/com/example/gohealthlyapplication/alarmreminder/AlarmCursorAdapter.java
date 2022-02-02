package com.example.gohealthlyapplication.alarmreminder;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.gohealthlyapplication.R;
import com.example.gohealthlyapplication.alarmreminder.data.AlarmReminderContract;



public class  AlarmCursorAdapter extends CursorAdapter {

    private TextView mTitleText, mTimeText,mDoseText,mDateText;
    private ImageView mActiveImage ;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;


    public AlarmCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.medicine_items_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        mTitleText = (TextView) view.findViewById(R.id.recycle_title);
        mTimeText = (TextView) view.findViewById(R.id.recycle_time);
        mDateText = (TextView) view.findViewById(R.id.recycle_date);
        mDoseText = (TextView) view.findViewById(R.id.recycle_dose_info);
        mActiveImage = (ImageView) view.findViewById(R.id.active_image);

        int titleColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TITLE);
        int dateColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_DATE);
        int timeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TIME);
        int doseColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_DOSE);
        int activeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE);

        String title = cursor.getString(titleColumnIndex);
        String date = cursor.getString(dateColumnIndex);
        String time = cursor.getString(timeColumnIndex);
        String dose = cursor.getString(doseColumnIndex);
        String active = cursor.getString(activeColumnIndex);



        setReminderTitle(title);
        setReminderTime(time);
        setReminderDate(date);
        setReminderDose(dose);
        setActiveImage(active);

    }

    public void setReminderTitle(String title) {
        mTitleText.setText(title);
    }
    public void setReminderDose(String dose) {
        mDoseText.setText(dose);
    }

    public void setReminderDate(String datetime) {
        mDateText.setText(datetime);
    }
    public void setReminderTime(String datetime) {
        mTimeText.setText(datetime);
    }


    public void setActiveImage(String active){
        if(active.equals("true")){
            mActiveImage.setImageResource(R.drawable.ic_notifications_on_white_24dp);
        }else if (active.equals("false")) {
            mActiveImage.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
        }
    }
}
