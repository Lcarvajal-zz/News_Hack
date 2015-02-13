package lcarvajal.news_hack;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/*
*New York Times home page
* Lukas Carvajal
 */

public class NYThome extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nyt_home);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);

        //dynamic buttons
        Button[] nytButtons = new Button[5];
        LinearLayout srcViewButLay = (LinearLayout)findViewById(R.id.button_layout);


        String NEWSTYPE = new String();

        for(int i = 0; i < 5; i++)
        {
            //initialize button
            nytButtons[i] = new Button(this);
            srcViewButLay.addView(nytButtons[i]);   //add button to layout

            //button names
            switch(i)
            {
                case 0: NEWSTYPE = "World News";
                    break;
                case 1: NEWSTYPE = "US News";
                    break;
                case 2: NEWSTYPE = "Politics";
                    break;
                case 3: NEWSTYPE = "Business";
                    break;
                case 4: NEWSTYPE = "Technology";
                    break;
            }

            //give button text
            nytButtons[i].setText(NEWSTYPE);
            nytButtons[i].setBackgroundResource(R.drawable.standard_button);
            nytButtons[i].setTextColor(Color.parseColor("#FFFFFF"));

            final int a = i;
            //button actions
            nytButtons[i].setOnClickListener(new View.OnClickListener() {
                Intent nyt;

                public void onClick(View v) {
                    switch (a)
                    {
                        case 0: nyt = new Intent(NYThome.this, NYTworldnews.class);
                            break;
                        case 1: nyt = new Intent(NYThome.this, NYTusnews.class);
                            break;
                        case 2: nyt = new Intent(NYThome.this, NYTpolitics.class);
                            break;
                        case 3: nyt = new Intent(NYThome.this, NYTbusiness.class);
                            break;
                        case 4: nyt = new Intent(NYThome.this, NYTtech.class);
                            break;
                    }

                    startActivity(nyt);
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nythome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
