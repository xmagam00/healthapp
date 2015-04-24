package usw.app.martin.healthapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        Intent intent = null;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_bmi) {
            intent = new Intent(AboutActivity.this, BmiActivity.class);
        } else if (id == R.id.action_meals){
            intent = new Intent(AboutActivity.this, MealsActivity.class);
        } else if (id == R.id.action_progress){
            intent = new Intent(AboutActivity.this, ProgressActivity.class);
        } else if (id == R.id.action_help){
            intent = new Intent(AboutActivity.this, HelpActivity.class);
        } else if (id == R.id.action_about){
            intent = new Intent(AboutActivity.this, AboutActivity.class);
        } else if (id == R.id.action_overview){
            intent = new Intent(AboutActivity.this, MainActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
