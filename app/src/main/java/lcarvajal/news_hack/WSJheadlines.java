package lcarvajal.news_hack;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class WSJheadlines extends ActionBarActivity {

    ArrayList<String> urls = new ArrayList<String>();
    ArrayList<String> titles = new ArrayList<String>();
    ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wsjheadlines);

        new Title().execute();              //extract titles
        new GoogleTranslated().execute();   //get hacked urls and create buttons


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wsjheadlines, menu);
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

    //PULL OUT TITLES
    protected class Title extends AsyncTask<Void, Void, Void>
    //pull out titles and urls of wsj home page
    {
        //Wall Street Journal home page
        String url = "http://wsj.com/home-page";

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                // Connect to the wsj, appear to be coming from a browser with .referrer
                Document document = Jsoup.connect(url).referrer("http://www.google.com").get();

                //counter
                int i = 0;

                String tempUrl;

                //stores all headlines
                Elements headline_elems = document.select(".tipTarget");

                for(Element headline_elem : headline_elems)
                //loop extracting titles and urls
                {
                    //stores article title
                    titles.add(headline_elem.text());

                    //stores and manipulates url for hacking
                    urls.add(headline_elem.outerHtml());
                    urls.set(i, urls.get(i).replace("<h2 class=\"tipTarget\"><a href=\"", ""));
                    urls.set(i, urls.get(i).split("\"")[0]);

                    i++;

                    if(i == 15) //extract 15 titles and urls
                        break;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;


        }

        @Override
        protected void onPostExecute(Void result)
        //put title names on buttons
        {
            super.onPostExecute(result);

        }
    }
    //END OF PULL OUT TITLES

    private class GoogleTranslated extends AsyncTask<String, Void, String>
    //access link from iframe in Google translated page
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(WSJheadlines.this);
            mProgressDialog.setTitle("Free Reader");
            mProgressDialog.setMessage("Hacking The Wall Street Journal...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();

        }

        protected String doInBackground(String... params)
        {
            try {
                String preURL = "http://translate.google.com/translate?sl=ja&tl=en&u=";

                for(int i = 0; i < urls.size(); i++)
                {
                    //hacking from "translated" url
                    urls.set(i, (preURL + urls.get(i)));
                    //pull out new url and store in urls(i)
                    // appear to be coming from a browser with .referrer
                    Document document =
                            Jsoup.connect(urls.get(i)).referrer("http://www.google.com").get();
                    Element link = document.select("iframe").first();
                    urls.set(i, link.attr("abs:src"));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return "Done";
        }

        @Override
        protected void onPostExecute(String result) {

            //dynamic buttons
            Button[] wsjButtons = new Button[urls.size()];
            LinearLayout srcViewButLay = (LinearLayout)findViewById(R.id.button_layout);

            String TITLE = new String();

            for(int i = 0; i < urls.size(); i++)
            {
                //initialize button
                wsjButtons[i] = new Button(WSJheadlines.this);
                //add button to layout
                srcViewButLay.addView(wsjButtons[i]);

                //give button text
                wsjButtons[i].setText(titles.get(i));

                //needed for onClick
                final int a = i;

                //button actions
                wsjButtons[i].setOnClickListener(new View.OnClickListener() {
                    Intent wsj;

                    public void onClick(View v) {
                        //open browser with hacked url
                        Intent webPageIntent = new Intent(Intent.ACTION_VIEW);
                        webPageIntent.setData((Uri.parse(urls.get(a))));

                        try
                        {
                            startActivity(webPageIntent);
                        }
                        catch (ActivityNotFoundException ex)
                        {
                        }
                    }
                });
            }

            mProgressDialog.dismiss();
            super.onPostExecute(result);
        }
    }
}
