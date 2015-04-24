package usw.app.martin.healthapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ProgressActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_progress, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.action_bmi) {
            intent = new Intent(ProgressActivity.this, BmiActivity.class);
        } else if (id == R.id.action_meals){
            intent = new Intent(ProgressActivity.this, MealsActivity.class);
        } else if (id == R.id.action_progress){
            intent = new Intent(ProgressActivity.this, ProgressActivity.class);
        } else if (id == R.id.action_help){
            intent = new Intent(ProgressActivity.this, HelpActivity.class);
        } else if (id == R.id.action_about){
            intent = new Intent(ProgressActivity.this, AboutActivity.class);
        } else if (id == R.id.action_overview){
            intent = new Intent(ProgressActivity.this, MainActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
