package lcarvajal.news_hack;

import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class DisplayArticle extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_article);
        //actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();


        if(b!=null)
        {
            String HACKED =(String) b.get("HACKED_URL");
            WebView myWebView = (WebView) findViewById(R.id.webview);

            myWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });

            myWebView.loadUrl(HACKED);
        }
    }
}
