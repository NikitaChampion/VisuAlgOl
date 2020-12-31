package com.gmail.nkigumnov.visualgol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.EditText;

import java.util.Random;

public class QuickSort extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    Button q_sort;
    Button generate;
    Button btnSave;
    EditText edit_text;
    SeekBar SbDelay;
    TextView TvDelay;
    private int childPosition = 0, groupPosition = 0;
    private int curSpeed = Constants.SPEED;
    private TextView[] txt_num;
    private TextView[] ind;
    final Random random = new Random();
    private final int[] numbers = new int[8];
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_sort);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            childPosition = arguments.getInt("num", 0);
            groupPosition = arguments.getInt("num_2", 0);
        }

        txt_num = new TextView[8];
        txt_num[0] = findViewById(R.id.txt_num1);
        txt_num[1] = findViewById(R.id.txt_num2);
        txt_num[2] = findViewById(R.id.txt_num3);
        txt_num[3] = findViewById(R.id.txt_num4);
        txt_num[4] = findViewById(R.id.txt_num5);
        txt_num[5] = findViewById(R.id.txt_num6);
        txt_num[6] = findViewById(R.id.txt_num7);
        txt_num[7] = findViewById(R.id.txt_num8);

        ind = new TextView[8];
        ind[0] = findViewById(R.id.ind1);
        ind[1] = findViewById(R.id.ind2);
        ind[2] = findViewById(R.id.ind3);
        ind[3] = findViewById(R.id.ind4);
        ind[4] = findViewById(R.id.ind5);
        ind[5] = findViewById(R.id.ind6);
        ind[6] = findViewById(R.id.ind7);
        ind[7] = findViewById(R.id.ind8);

        for (int i = 0; i < numbers.length; ++i)
            numbers[i] = random.nextInt() % 10;

        for (int i = 0; i < ind.length; ++i)
            ind[i].setText(String.valueOf(i + 1));

        TvDelay = findViewById(R.id.TvDelay);
        TvDelay.setText(R.string.sec);

        SbDelay = findViewById(R.id.SbDelay);
        SbDelay.setOnSeekBarChangeListener(this);

        q_sort = findViewById(R.id.q_sort);
        q_sort.setOnClickListener(this);

        generate = findViewById(R.id.generate);
        generate.setOnClickListener(this);

        edit_text = findViewById(R.id.edit_text);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        contestSet();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.q_sort) {
            handler.removeCallbacksAndMessages(null);
            contestSet();
            quick_sort();
        } else if (id == R.id.generate) {
            handler.removeCallbacksAndMessages(null);
            for (int i = 0; i < numbers.length; ++i)
                numbers[i] = random.nextInt() % 10;
            contestSet();
        } else if (id == R.id.btnSave) {
            if (edit_text.getText().toString().equals("1 3 2 4")) {
                Util.saveText(this, '1', groupPosition + childPosition);
            } else {
                Util.saveText(this, '0', groupPosition + childPosition);
            }
        }
    }

    public void contestSet() {
        for (int i = 0; i < numbers.length; ++i) {
            txt_num[i].setText(String.valueOf(numbers[i]));
            txt_num[i].setBackgroundResource(R.drawable.rectangle_gray);
        }
    }

    public void quick_sort() {
        animation_quick(0, numbers.length - 1);
    }

    long current = 1;

    public void animation_quick(int a, int b) {
        int l = a, r = b, mainer = numbers[(l + r) / 2];
        final int mid = (l + r) / 2;
        while (l <= r) {
            while (numbers[l] < mainer) ++l;
            while (numbers[r] > mainer) --r;
            if (l <= r) {
                final int ll = l, rr = r;
                int temp = numbers[l];
                numbers[l++] = numbers[r];
                numbers[r--] = temp;
                handler.postDelayed(() -> {
                    txt_num[ll].setBackgroundResource(R.drawable.rectangle_red);
                    txt_num[rr].setBackgroundResource(R.drawable.rectangle_red);
                    ind[mid].setBackgroundResource(R.drawable.rectangle_purple);
                }, curSpeed * current);
                ++current;
                handler.postDelayed(() -> {
                    String temp1 = txt_num[ll].getText().toString();
                    txt_num[ll].setText(txt_num[rr].getText().toString());
                    txt_num[rr].setText(temp1);
                }, curSpeed * current);
                ++current;
                handler.postDelayed(() -> {
                    txt_num[ll].setBackgroundResource(R.drawable.rectangle_gray);
                    txt_num[rr].setBackgroundResource(R.drawable.rectangle_gray);
                }, curSpeed * current);
            }
        }
        ++current;
        final int aa = a, rr = r;
        final int ll = l, bb = b;
        if (a < r) {
            handler.postDelayed(() -> {
                for (int i = aa; i <= bb; ++i) {
                    if (i <= rr)
                        txt_num[i].setBackgroundResource(R.drawable.rectangle_gray);
                    else
                        txt_num[i].setBackgroundResource(R.drawable.rectangle_2_gray);
                    ind[i].setBackgroundResource(R.drawable.rectangle_white);
                }
            }, curSpeed * current);
            ++current;
            animation_quick(a, r);
        }
        if (b > l) {
            handler.postDelayed(() -> {
                for (int i = aa; i <= bb; ++i) {
                    if (i >= ll)
                        txt_num[i].setBackgroundResource(R.drawable.rectangle_gray);
                    else
                        txt_num[i].setBackgroundResource(R.drawable.rectangle_2_gray);
                    ind[i].setBackgroundResource(R.drawable.rectangle_white);
                }
            }, curSpeed * current);
            ++current;
            animation_quick(l, b);
        }
        handler.postDelayed(() -> {
            for (int i = aa; i <= bb; ++i)
                ind[i].setBackgroundResource(R.drawable.rectangle_white);
        }, curSpeed * current);
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