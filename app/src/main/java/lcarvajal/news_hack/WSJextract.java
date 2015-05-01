package lcarvajal.news_hack;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lukas on 10-Feb-15.
 * WSJextract pulls out unhacked urls and titles of articles
 */
public class WSJextract extends AsyncTask<Void, Void, Void>
        //pull out titles and urls of wsj home page
{
    int articleLimit;
    String url;
    Context mContext;
    Activity activity;
    ProgressDialog mProgressDialog;

    ArrayList<String> urls = new ArrayList<String>();
    ArrayList<String> titles = new ArrayList<String>();

    public WSJextract(String u, int l, Context c, Activity a)
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
            // Connect to the wsj, appear to be coming from a browser with .referrer
            Document document = Jsoup.connect(url).referrer("http://www.google.com").get();


            //counter
            int i = 0;

            //stores all headlines
            Elements headline_elems = document.select(".wsj-headline-link");

            for (Element headline_elem : headline_elems)
            //loop extracting titles and urls
            {
                //stores article title
                titles.add(headline_elem.text());

                //stores and manipulates url for hacking
                urls.add(headline_elem.outerHtml());
                urls.set(i, urls.get(i).replace("<a class=\"wsj-headline-link\" href=\"", ""));
                urls.set(i, urls.get(i).split("\"")[0]);

                i++;

                if (i == articleLimit) //extract 15 titles and urls
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
        Button[] wsjButtons = new Button[urls.size()];
        LinearLayout srcViewButLay = (LinearLayout) activity.findViewById(R.id.button_layout);

        for (int i = 0; i < urls.size(); i++) {
            //BUTTONS
            //initialize button
            wsjButtons[i] = new Button(mContext);

            //add button to layout
            srcViewButLay.addView(wsjButtons[i]);

            //give button text
            wsjButtons[i].setText(titles.get(i));
            wsjButtons[i].setTextColor(Color.parseColor("#000000"));

            //give buttons look
            wsjButtons[i].setBackgroundResource(R.drawable.standard_button);

            //needed for onClick
            final int a = i;
            final String tempUrl = urls.get(i);
            final String titled = titles.get(i);



            //button actions
            wsjButtons[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                //send url to DisplayArticle so content is displayed within app
                {
                    //use url to access hacked article
                    new WSJaccess(tempUrl, titled, mContext, activity).execute();
                }
            });

            //create a distance between buttons
            LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)wsjButtons[i].getLayoutParams();
            lp.topMargin = 10;
            lp.bottomMargin = 10;
        }
        mProgressDialog.dismiss();
        super.onPostExecute(result);
    }
}