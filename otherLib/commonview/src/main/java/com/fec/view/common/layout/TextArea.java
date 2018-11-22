package com.fec.view.common.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.fec.view.common.R;
import com.fec.view.common.utils.DisplayHelper;

/**
 * Description :
 *
 * @author XQ Yang
 * @date 7/20/2018  2:07 PM
 */
public class TextArea extends FrameLayout {

    private EditText mEditText;
    private TextView mTextView;

    private int maxNum = 100;
    private int minLines = 5;
    @ColorInt
    private int textColor;
    private float textSize;
    private String mHint;

    public TextArea(@NonNull Context context) {
        this(context,null);
    }

    public TextArea(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_text_area, this, true);
        mEditText =  findViewById(R.id.et);
        mTextView =  findViewById(R.id.tv);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TextArea);
        maxNum = attributes.getInt(R.styleable.TextArea_maxNum, maxNum);
        minLines = attributes.getInt(R.styleable.TextArea_minLines, minLines);
        textColor =  attributes.getColor(R.styleable.TextArea_textColor, Color.parseColor("#666666"));
        textSize = attributes.getDimension(R.styleable.TextArea_textSize, DisplayHelper.sp2px(context, 14));
        mHint = attributes.getString(R.styleable.TextArea_hint);
        attributes.recycle();

        mEditText.setHint(mHint);
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        mEditText.setTextColor(textColor);
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxNum)});
        mEditText.setMinLines(minLines);
        mTextView.setText("0/"+maxNum);


        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTextView.setText(s.length()+"/"+maxNum);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public int getMinLines() {
        return minLines;
    }

    public void setMinLines(int minLines) {
        this.minLines = minLines;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getHint() {
        return mHint == null ? "" : mHint;
    }

    public void setHint(String hint) {
        mHint = hint;
    }

    public String getText() {
        return mEditText.getText().toString().trim();
    }

    public void setText(CharSequence str) {
        mEditText.setText(str);
    }
}
