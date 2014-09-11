package com.yj.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by yj1990 on 14-9-11.
 */
public class CodeEditText extends EditText {
    private static final int N = 4;
    private int maxContinuousNumbers = N;
    private int maxNum = N * 3 + 2;

    public CodeEditText(Context context) {
        super(context);
        init();
    }

    public CodeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CodeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        addTextChangedListener(new TextWatcher() {
            private String last;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                last = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > maxNum) {
                    setText(last);
                    setSelection(start);
                    return;
                }
                if (needChange(s.toString())) {
                    // 这是是考虑系统键盘输入的情况
                    boolean isAdd = last.length() < s.length();
                    int selection = getNewSelection(last, isAdd ? start : start + 1, isAdd);
                    String text = getShowText(getRealNumber(s.toString()));
                    setText(text);
                    setSelection(selection);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void deleteNumber() {
        deleteNumber(getSelectionStart());
    }

    public void deleteNumber(int start) {
        if (start < 1) {
            return;
        }
        String origin = getText().toString();
        int selection = getNewSelection(origin, start, false);
        String newString = origin.substring(0, start - 1) + origin.substring(start, origin.length());
        String showText = getShowText(getRealNumber(newString));
        setText(showText);
        setSelection(selection);
    }

    public void addNumber(int c) {
        addNumber(c, getSelectionStart());
    }

    public void addNumber(int c, int start) {
        if (c < 0 || c > 10) {
            return;
        }
        String origin = getText().toString();
        int selection = getNewSelection(origin, start, true);

        String newString = origin.substring(0, start) + String.valueOf(c) + origin.substring(start, origin.length());
        String realNumber = getRealNumber(newString);
        if (realNumber.length() > maxNum) {
            return;
        }
        String showText = getShowText(realNumber);

        setText(showText);
        setSelection(selection);
    }

    private int getNewSelection(String origin, int position, boolean isAdd) {
        String left = origin.substring(0, position);
        int rightNumLength = 0; // left右边连续数字的个数
        for (int i = left.length() - 1; i >= 0; i--) {
            if (left.charAt(i) == ' ') {
                break;
            } else {
                rightNumLength++;
            }
        }
        if (isAdd) {
            if (rightNumLength == maxContinuousNumbers) {
                return position + 2;
            } else {
                return position + 1;
            }
        } else {
            if (rightNumLength == 1) {
                return Math.max(0, position - 2);
            } else {
                return Math.max(0, position - 1);
            }
        }


    }

    private boolean needChange(String showText) {
        int continuousLength = 0;
        for (int i = 0, size = showText.length(); i < size; i++) {
            if (showText.charAt(i) != ' ') {
                continuousLength++;
                if (continuousLength > maxContinuousNumbers) {
                    return true;
                }
            } else {
                if (continuousLength != maxContinuousNumbers) {
                    return true;
                }
                if (i == size - 1) {
                    // 如果最后一项是空格也是不行的
                    return true;
                }
                continuousLength = 0;
            }

        }
        return false;
    }


    private String getShowText(String realNumber) {
        StringBuilder showText = new StringBuilder();
        for (int i = 0, length = realNumber.length(); i < length; i++) {
            if (i > 0 && i % maxContinuousNumbers == 0) {
                showText.append(" ");
            }
            showText.append(realNumber.charAt(i));
        }
        return showText.toString();
    }

    private String getRealNumber(String showText) {
        return showText.replaceAll(" ", "");
    }

    public int getMaxContinuousNumbers() {
        return maxContinuousNumbers;
    }

    public void setMaxContinuousNumbers(int maxContinuousNumbers) {
        this.maxContinuousNumbers = maxContinuousNumbers;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }
}
