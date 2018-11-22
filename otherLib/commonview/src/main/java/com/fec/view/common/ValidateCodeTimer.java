package com.fec.view.common;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/**
 * @author tome
 * @date 2018/7/31  10:16
 * @describe ${短信验证码倒计时控件}
 */
public class ValidateCodeTimer extends AppCompatTextView {
    private Context             mContext;
    private Disposable          mDisposable;
    private TextView            mValidateCode;

    public ValidateCodeTimer(Context context) {
        this(context, null);
    }

    public ValidateCodeTimer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ValidateCodeTimer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setText(R.string.get_verify_code);
    }

    public void showTimer(Context context, TextView textView) {
        mContext = context;
        mValidateCode = textView;
        mValidateCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mValidateCode.setEnabled(false);
                mValidateCode.setTextColor(mContext.getResources().getColor(R.color.colorcc));
                mDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (aLong < 60) {
                            String num = String.valueOf(60 - aLong);
                            SpannableString string = new SpannableString(String.format(mContext.getString(R.string.after_x_second_retry),num));
                            string.setSpan(new ForegroundColorSpan(Color.RED),0,num.length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            mValidateCode.setText(string);
                        } else {
                            mValidateCode.setEnabled(true);
                            mValidateCode.setTextColor(ContextCompat.getColor(mContext,R.color.editColor));
                            mValidateCode.setText(R.string.get_verify_code);
                            mDisposable.dispose();
                        }
                    }
                });
            }
        });
    }

    public void clearDisposable() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
