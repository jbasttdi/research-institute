package com.heaven7.vida.research.sample;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.heaven7.vida.research.R;
import com.heaven7.vida.research.widget.DiscView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heaven7 on 2018/4/21 0021.
 */

public class TestDiscViewActivity extends AppCompatActivity {

    private DiscView mDiscView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_test_disc_view);
        mDiscView = findViewById(R.id.discView);

        setData();
    }

    private void setData() {
        mDiscView.setDiscProvider(new DiscView.DiscProvider() {
            @Override
            public float provideDegree(DiscView.Item item, Paint p) {
                DiscView.Row row = item.rows.get(0);
                p.setTextSize(row.textSize);
                return row.text.length() * 6;
            }
            @Override
            public float provideVOffset(DiscView.Disc disc, int index, float radius) {
                return radius / 6;
            }
        });
        int step = getResources().getDimensionPixelSize(R.dimen.disc_step);
        float maxTextSize = getResources().getDimensionPixelSize(R.dimen.disc_max_text_size);

        List<DiscView.Disc> discs = new ArrayList<>();
        //1
        DiscView.Disc disc = new DiscView.Disc();
        disc.step = step * 2;
        disc.selectTextColor = Color.BLACK;
        disc.textColor = Color.parseColor("#d0d0d0");

        DiscView.Item item = new DiscView.Item();
        disc.addItem(item);
        item.addRow(createRow("全世界", maxTextSize));

        item = new DiscView.Item();
        disc.addItem(item);
        item.addRow(createRow("你的全世界", maxTextSize));

        item = new DiscView.Item();
        disc.addItem(item);
        item.addRow(createRow("从你的全世界", maxTextSize));

        item = new DiscView.Item();
        disc.addItem(item);
        item.addRow(createRow("从你的全世界路过", maxTextSize));

        discs.add(disc);

        //2
        disc = new DiscView.Disc();
        disc.step = step;
        disc.selectTextColor = Color.BLACK;
        disc.textColor = Color.parseColor("#d0d0d0");
        maxTextSize -= 30;

        item = new DiscView.Item();
        disc.addItem(item);
        item.addRow(createRow("java", maxTextSize));

        item = new DiscView.Item();
        disc.addItem(item);
        item.addRow(createRow("c/c++", maxTextSize));

        item = new DiscView.Item();
        disc.addItem(item);
        item.addRow(createRow("python", maxTextSize));

        item = new DiscView.Item();
        disc.addItem(item);
        item.addRow(createRow("kotlin", maxTextSize));

        item = new DiscView.Item();
        disc.addItem(item);
        item.addRow(createRow("Lisp", maxTextSize));

        discs.add(disc);
          //3
        disc = new DiscView.Disc();
        disc.step = step;
        disc.selectTextColor = Color.BLACK;
        disc.textColor = Color.parseColor("#d0d0d0");
        maxTextSize -= 30;
        addItems(disc, 10, maxTextSize);

        discs.add(disc);
        mDiscView.setDiscs(discs);
    }

    private void addItems(DiscView.Disc disc, int count , float textSize) {
        StringBuilder sb = new StringBuilder();
        sb.append("rect_");
        for(int i = 0 ; i < count ; i ++){
            sb.append(i);

            DiscView.Item item = new DiscView.Item();
            item.addRow(createRow(sb.toString(), textSize));
            disc.addItem(item);
        }
    }

    private DiscView.Row createRow(String text, float textSize) {
        DiscView.Row row = new DiscView.Row();
        row.text = text;
        //row.textColor = Color.RED;
        row.textSize = textSize;
        return row;
    }
}
