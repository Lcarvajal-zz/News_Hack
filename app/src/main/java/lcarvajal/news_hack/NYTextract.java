package lcarvajal.news_hack;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lukas on 12-Feb-15.
 */
public class NYTextract extends AsyncTask<Void, Void, Void>
        //pull out titles and urls of wsj home page
{
    int articleLimit;
    String url;
    Context mContext;
    Activity activity;
    ProgressDialog mProgressDialog;

    ArrayList<String> urls = new ArrayList<String>();
    ArrayList<String> titles = new ArrayList<String>();

    public NYTextract(String u, int l, Context c, Activity a)
    {
        url = u;
        mContext = c;
        activity = a;
        articleLimit = l;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("News Hack");
        mProgressDialog.setMessage("Hacking The Wall Street Journal...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();

    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            //counter
            int i = 0;

            //create doc to hold html content
            Document document1 = Jsoup.connect(url).get();

            //take url1 to web page where actual content is
            Elements headings = document1.select("h3");

            String s = new String();

            for(Element heading : headings)
            //loop through articleLimit number of titles
            {
                s = heading.text();
                titles.add(s);

                //stores and manipulates url for hacking
                urls.add(heading.outerHtml());
                urls.set(i, urls.get(i).replace("<h3><a href=\"", ""));
                urls.set(i, urls.get(i).split("\"")[0]);

                i++;    //number of article headings extracted

                if(i == articleLimit)
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    //put title names on buttons and give button function
    {
        //dynamic buttons with seperators
        Button[] nytButtons = new Button[titles.size()];
        LinearLayout srcViewButLay = (LinearLayout) activity.findViewById(R.id.button_layout);

        for (int i = 0; i < titles.size(); i++) {
            //BUTTONS
            //initialize button
            nytButtons[i] = new Button(mContext);

            //add button to layout
            srcViewButLay.addView(nytButtons[i]);

            //give button text
            nytButtons[i].setText(titles.get(i));
            nytButtons[i].setTextColor(Color.parseColor("#FFFFFF"));

            //give buttons look
            nytButtons[i].setBackgroundResource(R.drawable.standard_button);

            //needed for onClick
            final int a = i;
            final String titled = titles.get(i);
            final String tempUrl = urls.get(i);



            //button actions
            nytButtons[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                //send url to DisplayArticle so content is displayed within app
                {
                    //use url to access hacked article
                    new WSJaccess(tempUrl, titled, mContext, activity).execute();
                }
            });
        }
        mProgressDialog.dismiss();
        super.onPostExecute(result);
    }
}