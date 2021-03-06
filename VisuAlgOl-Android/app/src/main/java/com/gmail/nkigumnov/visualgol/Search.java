package com.gmail.nkigumnov.visualgol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gmail.nkigumnov.visualgol.util.Constants;
import com.gmail.nkigumnov.visualgol.util.Util;

import java.util.Random;

import static java.lang.Math.abs;

public class Search extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private TextView title;
    private TextView Search;
    private TextView task;
    private EditText editText;
    private TextView TvDelay;
    private int childPosition = 0, groupPosition = 0;
    private int curSpeed = Constants.SPEED;
    private TextView[] txt_num;
    private TextView txt_num_find;
    private final Random random = new Random();
    private final int[] numbers = new int[8];
    private final int[] numbers_2 = {1, 2, 3, 4, 5, 6, 7, 8};
    private int WhatToFind;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            childPosition = arguments.getInt("num", 0);
            groupPosition = arguments.getInt("num_2", 0);
        }

        //Toast.makeText(this, ""+cur, Toast.LENGTH_SHORT).show();

        title = findViewById(R.id.title);

        txt_num = new TextView[8];
        txt_num[0] = findViewById(R.id.txt_num1);
        txt_num[1] = findViewById(R.id.txt_num2);
        txt_num[2] = findViewById(R.id.txt_num3);
        txt_num[3] = findViewById(R.id.txt_num4);
        txt_num[4] = findViewById(R.id.txt_num5);
        txt_num[5] = findViewById(R.id.txt_num6);
        txt_num[6] = findViewById(R.id.txt_num7);
        txt_num[7] = findViewById(R.id.txt_num8);
        txt_num_find = findViewById(R.id.txt_num10);

        TvDelay = findViewById(R.id.TvDelay);
        TvDelay.setText(R.string.sec);

        ((SeekBar) findViewById(R.id.SbDelay)).setOnSeekBarChangeListener(this);

        Search = findViewById(R.id.Search);

        task = findViewById(R.id.task);

        for (int i = 0; i < numbers.length; ++i)
            numbers[i] = random.nextInt() % 10;
        if (childPosition == 0)
            WhatToFind = numbers[abs(random.nextInt()) % numbers.length];
        else
            WhatToFind = numbers_2[abs(random.nextInt()) % numbers_2.length];

        findViewById(R.id.search).setOnClickListener(this);
        findViewById(R.id.generate).setOnClickListener(this);
        findViewById(R.id.btnSave).setOnClickListener(this);

        editText = findViewById(R.id.edit_text);

        contestSet();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.search) {
            handler.removeCallbacksAndMessages(null);
            contestSet();
            if (childPosition == 0)
                linearSearch();
            else
                binarySearch();
        } else if (id == R.id.generate) {
            handler.removeCallbacksAndMessages(null);
            for (int i = 0; i < numbers.length; ++i)
                numbers[i] = random.nextInt() % 10;
            if (childPosition == 0)
                WhatToFind = numbers[abs(random.nextInt()) % numbers.length];
            else
                WhatToFind = numbers_2[abs(random.nextInt()) % numbers_2.length];
            contestSet();
        } else if (id == R.id.btnSave) {
            if (childPosition == 0 && editText.getText().toString().equals("5")) {
                Util.saveText(this, '1', groupPosition + childPosition);
            } else if (childPosition == 1 && editText.getText().toString().equals("4")) {
                Util.saveText(this, '1', groupPosition + childPosition);
            } else {
                Util.saveText(this, '0', groupPosition + childPosition);
            }
        }
    }

    public void contestSet() {
        //linear search == 0, binary search == 1
        if (childPosition == 0) {
            title.setText(R.string.lin_search);
            for (int i = 0; i < numbers.length; ++i) {
                txt_num[i].setText(String.valueOf(numbers[i]));
                txt_num[i].setBackgroundResource(R.drawable.rectangle_search_1);
            }
            Search.setText(R.string.lin_s);
            task.setText(R.string.task8);
        } else {
            title.setText(R.string.bin_search);
            for (int i = 0; i < numbers_2.length; ++i) {
                txt_num[i].setText(String.valueOf(numbers_2[i]));
                txt_num[i].setBackgroundResource(R.drawable.rectangle_search_1);
            }
            Search.setText(R.string.bin_s);
            task.setText(R.string.task9);
        }
        txt_num_find.setText(String.valueOf(WhatToFind));
        txt_num_find.setBackgroundResource(R.drawable.rectangle_search_1);
    }

    public void linearSearch() {
        animationLinear();
    }

    public void animationLinear() {
        for (int i = 0; i < numbers.length; ++i) {
            final int x = i;
            handler.postDelayed(() -> {
                txt_num[x].setBackgroundResource(R.drawable.rectangle_search_3);
                txt_num_find.setBackgroundResource(R.drawable.rectangle_search_4);
            }, curSpeed * (2 * i + 1));
            handler.postDelayed(() -> {
                if (numbers[x] == WhatToFind)
                    txt_num[x].setBackgroundResource(R.drawable.rectangle_search_4);
                else {
                    txt_num[x].setBackgroundResource(R.drawable.rectangle_search_2);
                    txt_num_find.setBackgroundResource(R.drawable.rectangle_search_1);
                }
            }, curSpeed * (2 * i + 2));
            if (numbers[i] == WhatToFind) return;
        }
    }

    public void binarySearch() {
        animationBinary();
    }

    public void animationBinary() {
        long current = 1;
        int l = -1, r = numbers_2.length, mid;
        while (l < r - 1) {
            mid = (l + r) / 2;
            final int x = mid;
            handler.postDelayed(() -> {
                txt_num[x].setBackgroundResource(R.drawable.rectangle_search_3);
                txt_num_find.setBackgroundResource(R.drawable.rectangle_search_4);
            }, curSpeed * current);
            ++current;
            handler.postDelayed(() -> {
                if (numbers_2[x] < WhatToFind) {
                    for (int i = 0; i <= x; ++i) {
                        txt_num[i].setBackgroundResource(R.drawable.rectangle_search_2);
                    }
                    txt_num_find.setBackgroundResource(R.drawable.rectangle_search_1);
                } else if (numbers_2[x] > WhatToFind) {
                    for (int i = txt_num.length - 1; i >= x; --i) {
                        txt_num[i].setBackgroundResource(R.drawable.rectangle_search_2);
                    }
                    txt_num_find.setBackgroundResource(R.drawable.rectangle_search_1);
                } else
                    txt_num[x].setBackgroundResource(R.drawable.rectangle_search_4);
            }, curSpeed * current);
            if (numbers_2[mid] == WhatToFind) return;
            ++current;
            if (numbers_2[mid] < WhatToFind) l = mid;
            else r = mid;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        curSpeed = progress;
        String s = progress / 1000. + " sec";
        TvDelay.setText(s);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}