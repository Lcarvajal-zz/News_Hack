package lcarvajal.news_hack;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void wsjClickMethod(View v)
    //Wall Street Journal Button
    {
        Intent wsj = new Intent(MainActivity.this, WSJhome.class);
        startActivity(wsj);
    }

    public void nytClickMethod(View v)
    //New York Times Button
    {
        Intent nyt = new Intent(MainActivity.this, NYThome.class);
        startActivity(nyt);
    }
}
