package lcarvajal.news_hack;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lukas on 11-Feb-15.
 * accesses hacked url for viewing web page
 */
public class WSJaccess extends AsyncTask<Void, Void, Void>
//access WSJ article info using url
{
    String url;
    String title;
    Context mContext;
    Activity activity;
    ProgressDialog mProgressDialog;

    public WSJaccess(String u, String t, Context c, Activity a)
    {
        url = u;
        title = t;
        mContext = c;
        activity = a;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Free Reader");
        mProgressDialog.setMessage("Opening " + title + "...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            //create hacking url
            url = "http://translate.google.com/translate?sl=ja&tl=en&u=" + url;

            //access hacked web page
            // appear to be coming from a browser with .referrer
            Document document2 =
                    Jsoup.connect(url).referrer("http://www.google.com").get();
            Element link = document2.select("iframe").first();

            //extract hacked url
            url = link.attr("abs:src");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    //put title names on buttons
    {
       //Send hacked url to DisplayArticle
       Intent intent = new Intent(mContext, DisplayArticle.class);
       intent.putExtra("HACKED_URL", url);
        
        mContext.startActivity(intent);

       mProgressDialog.dismiss();
       super.onPostExecute(result);
    }
}
//END OF PULL OUT TITLES AND URLS, MANIPULATE WSJ URLS, SET ONCLICK FUNCTIONS