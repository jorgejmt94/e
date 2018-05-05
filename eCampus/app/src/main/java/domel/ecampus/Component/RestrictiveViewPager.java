package domel.ecampus.Component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import domel.ecampus.Adapters.SmartFragmentStatePagerAdapter;
import domel.ecampus.Fragment.AddSubjectFirstStepFragment;
import domel.ecampus.R;


public class RestrictiveViewPager extends ViewPager {
    
    private float last_position = -1;
    private float position = -1;
    private static final int SWIPE_RIGHT_TRIGGER_MARGIN = 150;
    private float slop;
    private boolean not_procesed;
    private boolean validated;

    public RestrictiveViewPager(Context context) {
        super(context);
        slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public RestrictiveViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //do not allow swipping if allow_swipe is set to false
        Log.d("touchevent", "ontouch");
        if(this.getCurrentItem() != 0) return super.onTouchEvent(event);
        Log.d("ontouchaction", Integer.toString(event.getAction()));
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                last_position = event.getX();
                not_procesed = true;
                validated = false;
                Log.d("ontouch", "down");

                super.onTouchEvent(event);
                return true;

            case MotionEvent.ACTION_MOVE:
                Log.d("pointdiff", Float.toString(last_position - position));
                if (Math.abs(last_position - event.getX()) > slop) {
                    if(not_procesed){
                        not_procesed = false;
                        AddSubjectFirstStepFragment f = (AddSubjectFirstStepFragment) ((SmartFragmentStatePagerAdapter)(this.getAdapter())).getRegisteredFragment(0);
                        AppCompatEditText name = (AppCompatEditText) f.getView().findViewById(R.id.subject_name);
                        AppCompatEditText description = (AppCompatEditText) f.getView().findViewById(R.id.subject_description);

                        if (name.getText().length() == 0) {
                            name.setError(getContext().getString(R.string.error_field_required));
                            name.requestFocus();
                            Log.d("touchevent", "1");
                            return false;
                        } else if (description.getText().length() == 0) {
                            description.setError(getContext().getString(R.string.error_field_required));
                            description.requestFocus();
                            Log.d("touchevent", "2");
                            return false;
                        } else {
                            Log.d("touchevent", "3");
                            validated = true;
                            return super.onTouchEvent(event);
                        }

                    }else if(validated){
                        return super.onTouchEvent(event);
                    }else{
                        return false;
                    }
                }
                break;

            default:
                return super.onTouchEvent(event);
        }

        return false;

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        Log.d("intercepttouchevent", Integer.toString(event.getAction()));
        if(this.getCurrentItem() != 0) return super.onInterceptTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                last_position = event.getX();
                not_procesed = true;
                validated = false;
                super.onInterceptTouchEvent(event);
                return false;

            case MotionEvent.ACTION_MOVE:
                Log.d("intercept", "move");
                //Log.d("pointdiff", Float.toString(last_position - position));
                if (Math.abs(last_position - event.getX()) > slop) {
                    if(not_procesed){
                        not_procesed = false;
                        AddSubjectFirstStepFragment f = (AddSubjectFirstStepFragment) ((SmartFragmentStatePagerAdapter)(this.getAdapter())).getRegisteredFragment(0);
                        AppCompatEditText name = (AppCompatEditText) f.getView().findViewById(R.id.subject_name);
                        AppCompatEditText description = (AppCompatEditText) f.getView().findViewById(R.id.subject_description);

                        if (name.getText().length() == 0) {
                            name.setError(getContext().getString(R.string.error_field_required));
                            name.requestFocus();
                  //          Log.d("touchevent", "1");
                            return true;
                        } else if (description.getText().length() == 0) {
                            description.setError(getContext().getString(R.string.error_field_required));
                            description.requestFocus();
                    //        Log.d("touchevent", "2");
                            return true;
                        } else {
                      //      Log.d("touchevent", "3");
                            validated = true;
                            return super.onInterceptTouchEvent(event);
                        }

                    }else if(validated){
                        return super.onInterceptTouchEvent(event);
                    }else{
                        return false;
                    }
                }
                break;

            default:
                Log.d("ontouch", "default");
                super.onInterceptTouchEvent(event);
                return false;
        }


        return false;
    }
}
