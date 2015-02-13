package lcarvajal.news_hack;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Lukas on 09-Feb-15.
 * Wall Street Journal home page
 * this class creates buttons that open up to each news category
 */
public class WSJhome extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wsj_home);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);

        //dynamic buttons
        Button[] wsjButtons = new Button[6];
        LinearLayout srcViewButLay = (LinearLayout)findViewById(R.id.button_layout);


        String NEWSTYPE = new String();

        for(int i = 0; i < 5; i++)
        {
            //initialize button
            wsjButtons[i] = new Button(this);
            srcViewButLay.addView(wsjButtons[i]);   //add button to layout

            //button names
            switch(i)
            {
                case 0: NEWSTYPE = "Headlines";
                    break;
                case 1: NEWSTYPE = "World News";
                    break;
                case 2: NEWSTYPE = "US News";
                    break;
                case 3: NEWSTYPE = "Business";
                    break;
                case 4: NEWSTYPE = "Opinion";
                    break;
            }

            //give button text
            wsjButtons[i].setText(NEWSTYPE);
            wsjButtons[i].setBackgroundResource(R.drawable.standard_button);
            wsjButtons[i].setTextColor(Color.parseColor("#000000"));

            final int a = i;
            //button actions
            wsjButtons[i].setOnClickListener(new View.OnClickListener() {
                Intent wsj;

                public void onClick(View v) {
                    switch (a)
                    {
                        case 0: wsj = new Intent(WSJhome.this, WSJheadlines.class);
                            break;
                        case 1: wsj = new Intent(WSJhome.this, WSJworldnews.class);
                            break;
                        case 2: wsj = new Intent(WSJhome.this, WSJusnews.class);
                            break;
                        case 3: wsj = new Intent(WSJhome.this, WSJbusiness.class);
                            break;
                        case 4: wsj = new Intent(WSJhome.this, WSJopinion.class);
                            break;
                    }

                    startActivity(wsj);
                }
            });

            //create a distance between buttons
            LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)wsjButtons[i].getLayoutParams();
            lp.topMargin = 10;
            lp.bottomMargin = 10;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}