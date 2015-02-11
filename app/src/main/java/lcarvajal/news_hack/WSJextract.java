package lcarvajal.news_hack;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lukas on 10-Feb-15.
 */
//PULL OUT TITLES AND URLS, MANIPULATE WSJ URLS, SET ONCLICK FUNCTIONS
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
        mProgressDialog.setTitle("Free Reader");
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
            Elements headline_elems = document.select(".tipTarget");

            for (Element headline_elem : headline_elems)
            //loop extracting titles and urls
            {
                //stores article title
                titles.add(headline_elem.text());

                //stores and manipulates url for hacking
                urls.add(headline_elem.outerHtml());
                urls.set(i, urls.get(i).replace("<h2 class=\"tipTarget\"><a href=\"", ""));
                urls.set(i, urls.get(i).split("\"")[0]);

                i++;

                if (i == articleLimit) //extract 15 titles and urls
                    break;
            }

            String preURL = "http://translate.google.com/translate?sl=ja&tl=en&u=";

            for (i = 0; i < urls.size(); i++) {
                //hacking from "translated" url
                urls.set(i, (preURL + urls.get(i)));
                //pull out new url and store in urls(i)
                // appear to be coming from a browser with .referrer
                Document document2 =
                        Jsoup.connect(urls.get(i)).referrer("http://www.google.com").get();
                Element link = document2.select("iframe").first();
                urls.set(i, link.attr("abs:src"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    //put title names on buttons
    {
        //dynamic buttons
        Button[] wsjButtons = new Button[urls.size()];
        LinearLayout srcViewButLay = (LinearLayout) activity.findViewById(R.id.button_layout);

        for (int i = 0; i < urls.size(); i++) {
            //initialize button
            wsjButtons[i] = new Button(mContext);
            //add button to layout
            srcViewButLay.addView(wsjButtons[i]);

            //give button text
            wsjButtons[i].setText(titles.get(i));

            //give buttons look
            wsjButtons[i].setBackgroundResource(R.drawable.standard_button);

            //needed for onClick
            final int a = i;

            //button actions
            wsjButtons[i].setOnClickListener(new View.OnClickListener() {
                Intent wsj;

                public void onClick(View v) {
                    //open browser with hacked url
                    Intent webPageIntent = new Intent(Intent.ACTION_VIEW);
                    webPageIntent.setData((Uri.parse(urls.get(a))));

                    try {
                        activity.startActivity(webPageIntent);
                    } catch (ActivityNotFoundException ex) {
                    }
                }
            });
        }
        mProgressDialog.dismiss();
        super.onPostExecute(result);
    }
}
//END OF PULL OUT TITLES AND URLS, MANIPULATE WSJ URLS, SET ONCLICK FUNCTIONS