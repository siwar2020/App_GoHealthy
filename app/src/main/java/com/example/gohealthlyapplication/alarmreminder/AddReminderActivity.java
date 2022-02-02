package com.example.gohealthlyapplication.alarmreminder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.NavUtils;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.gohealthlyapplication.R;
import com.example.gohealthlyapplication.alarmreminder.data.AlarmReminderContract;
import com.example.gohealthlyapplication.alarmreminder.reminder.AlarmScheduler;
import com.example.gohealthlyapplication.views.RobotoBoldTextView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import android.app.TimePickerDialog;

import com.google.firebase.database.DatabaseReference;


import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;



public class AddReminderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_ALARM_LOADER = 0;

   //Variables :

      private Toolbar mToolbar;
      private FloatingActionButton mFAB1;
      private FloatingActionButton mFAB2;
      private int mYear, mMonth, mDay;
      int mHour;
      int mMinute;
      private String mTime;
      private String mDate;
      private String mActive;
      private Uri mCurrentReminderUri;
      private boolean mAlarmHasChanged = false;
    private Calendar mCalendar;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "AddMedicineFragment";


    @BindView(R.id.edit_med_name)
    EditText editMedName;

    @BindView(R.id.tv_medicine_time)
    RobotoBoldTextView tvMedicineTime;

    @BindView(R.id.tv_dose_quantity)
    EditText tvDoseQuantity;
    private static final String KEY_TITLE = "title_key";
    private static final String KEY_TIME = "time_key";
    private static final String KEY_DATE = "date_key";
    private static final String KEY_DOSE = "dose_key";
    private static final String KEY_ACTIVE = "active_key";


    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mAlarmHasChanged = true;
            return false;
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_alarm);

        Intent intent = getIntent();

        mCurrentReminderUri = intent.getData();
        if (mCurrentReminderUri == null) {
            //New Alarm :
            setTitle(getString(R.string.editor_activity_title_new_reminder));
            invalidateOptionsMenu();
        } else {
            //Modify Alarm :
            setTitle(getString(R.string.editor_activity_title_edit_reminder));
            getLoaderManager().initLoader(EXISTING_ALARM_LOADER, null, this);
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        editMedName = findViewById(R.id.edit_med_name);
        tvMedicineTime = findViewById(R.id.tv_medicine_time);
        tvDoseQuantity = findViewById(R.id.tv_dose_quantity);
        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);
        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;
        tvMedicineTime.setText(mTime);
        mFAB1 = findViewById(R.id.starred1);
        mFAB2 = findViewById(R.id.starred2);
        // Initialize default values
        mActive = "true";
        // Obtain date from date picker
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                mYear = cal.get(Calendar.YEAR);
                mMonth = cal.get(Calendar.MONTH);
                mDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddReminderActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        mYear, mMonth, mDay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                mMonth = month;
                mDay = day;
                mYear = year;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + mMonth + "/" + mDay + "/" + mYear);


                String date = month + "/" + day + "/" + year;
                mDate = date;
                mDisplayDate.setText(date);
            }
        };
        tvMedicineTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        if (mActive.equals("false")) {
            mFAB1.setVisibility(View.VISIBLE);
            mFAB2.setVisibility(View.GONE);

        } else if (mActive.equals("true")) {
            mFAB1.setVisibility(View.GONE);
            mFAB2.setVisibility(View.VISIBLE);
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Reminder");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    public void selectFab1(View v) {
        mFAB1 = (FloatingActionButton) findViewById(R.id.starred1);
        mFAB1.setVisibility(View.GONE);
        mFAB2 = (FloatingActionButton) findViewById(R.id.starred2);
        mFAB2.setVisibility(View.VISIBLE);
        mActive = "true";
    }

    public void selectFab2(View v) {
        mFAB2 = (FloatingActionButton) findViewById(R.id.starred2);
        mFAB2.setVisibility(View.GONE);
        mFAB1 = (FloatingActionButton) findViewById(R.id.starred1);
        mFAB1.setVisibility(View.VISIBLE);
        mActive = "false";
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
        return true;
    }
    private void showTimePicker() {
        Calendar mCurrentTime = Calendar.getInstance();
        mHour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        mMinute = mCurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddReminderActivity.this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mHour = selectedHour;
                mMinute = selectedMinute;
                tvMedicineTime.setText(String.format(Locale.getDefault(), "%d:%d", selectedHour, selectedMinute));
            }
        }, mHour, mMinute, false);//No 24 hour time
        mTimePicker.setTitle("Select Time");

        mTimePicker.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mCurrentReminderUri == null) {
            MenuItem menuItem = menu.findItem(R.id.discard_reminder);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.save_reminder:

                if (editMedName.getText().toString().length() == 0) {
                    editMedName.setError("Reminder Title cannot be blank!");
                    if (mDisplayDate.getText().toString().length() == 0) {
                        mDisplayDate.setError("Reminder Time cannot be blank!");
                    }
                }
                 else {
                    saveReminder();
                    finish();
                }
                return true;

            case R.id.discard_reminder:

                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                if (!mAlarmHasChanged) {
                    NavUtils.navigateUpFromSameTask(AddReminderActivity.this);
                    return true;
                }
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(AddReminderActivity.this);
                            }
                        };
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteReminder();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteReminder() {

        if (mCurrentReminderUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentReminderUri, null, null);
            if (rowsDeleted == 0) {

                Toast.makeText(this, getString(R.string.editor_delete_reminder_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_delete_reminder_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }

    public void saveReminder() {
        ContentValues values = new ContentValues();

        values.put(AlarmReminderContract.AlarmReminderEntry.KEY_TITLE, editMedName.getText().toString());
        values.put(AlarmReminderContract.AlarmReminderEntry.KEY_DATE, mDisplayDate.getText().toString());
        values.put(AlarmReminderContract.AlarmReminderEntry.KEY_TIME, tvMedicineTime.getText().toString());
        values.put(AlarmReminderContract.AlarmReminderEntry.KEY_DOSE, tvDoseQuantity.getText().toString());
        values.put(AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE, mActive);

        Calendar mCalendar = Calendar.getInstance();

        mCalendar.set(Calendar.MONTH, --mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);

        long selectedTimestamp = mCalendar.getTimeInMillis();


        if (mCurrentReminderUri == null) {
            Uri newUri = getContentResolver().insert(AlarmReminderContract.AlarmReminderEntry.CONTENT_URI, values);
            if (newUri == null) {
                Toast.makeText(this, getString(R.string.editor_insert_reminder_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_insert_reminder_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {

            int rowsAffected = getContentResolver().update(mCurrentReminderUri, values, null, null);
            if (rowsAffected == 0) {
                Toast.makeText(this, getString(R.string.editor_update_reminder_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_update_reminder_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
        if (mActive.equals("true")) {
            new AlarmScheduler().setAlarm(getApplicationContext(), selectedTimestamp, mCurrentReminderUri);
        }
        Toast.makeText(getApplicationContext(), "Saved",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                AlarmReminderContract.AlarmReminderEntry._ID,
                AlarmReminderContract.AlarmReminderEntry.KEY_TITLE,
                AlarmReminderContract.AlarmReminderEntry.KEY_DATE,
                AlarmReminderContract.AlarmReminderEntry.KEY_TIME,
                AlarmReminderContract.AlarmReminderEntry.KEY_DOSE,
                AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE,
        };

        return new CursorLoader(this,
                mCurrentReminderUri,
                projection,
                null,
                null,
                null);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {
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

            editMedName.setText(title);
            tvMedicineTime.setText(time);
            mDisplayDate.setText(date);
            tvDoseQuantity.setText(dose);

        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {


    }
}
