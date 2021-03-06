package com.gmail.nkigumnov.visualgol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gmail.nkigumnov.visualgol.util.Constants;

public class BST extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private ImageView imageView;
    private TextView TvDelay;
    private int curSpeed = Constants.SPEED;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bst);

        //Toast.makeText(this, ""+cur, Toast.LENGTH_SHORT).show();

        imageView = findViewById(R.id.imageView);

        TvDelay = findViewById(R.id.TvDelay);
        TvDelay.setText(R.string.sec);

        ((SeekBar) findViewById(R.id.SbDelay)).setOnSeekBarChangeListener(this);
        findViewById(R.id.bst).setOnClickListener(this);
        findViewById(R.id.bst2).setOnClickListener(this);

        contestSet();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bst) {
            handler.removeCallbacksAndMessages(null);
            contestSet();
            bst_ins();
        } else if (id == R.id.bst2) {
            handler.removeCallbacksAndMessages(null);
            contestSet();
            bst_del();
        }
    }

    public void contestSet() {
        //DFS == 0, BFS == 1
        imageView.setBackgroundResource(R.drawable.bst);
    }

    int[] bst_ins = {R.drawable.bstins1, R.drawable.bstins2, R.drawable.bstins3, R.drawable.bstins4, R.drawable.bstins5};
    int[] bst_del = {R.drawable.bstdel1, R.drawable.bstdel2, R.drawable.bstdel3, R.drawable.bstdel4, R.drawable.bstdel5};

    public void bst_ins() {
        animation_bst_ins();
    }

    public void animation_bst_ins() {
        for (int i = 0; i < bst_ins.length; ++i) {
            final int j = i;
            handler.postDelayed(() -> imageView.setBackgroundResource(bst_ins[j]), curSpeed * (i + 1));
        }
    }

    public void bst_del() {
        animation_bst_del();
    }

    public void animation_bst_del() {
        for (int i = 0; i < bst_del.length; ++i) {
            final int j = i;
            handler.postDelayed(() -> imageView.setBackgroundResource(bst_del[j]), curSpeed * i);
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