package usw.app.martin.healthapp.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import usw.app.martin.healthapp.R;


public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.action_bmi) {
            intent = new Intent(AboutActivity.this, BmiActivity.class);
        } else if (id == R.id.action_meals) {
            intent = new Intent(AboutActivity.this, MealsActivity.class);
        } else if (id == R.id.action_help) {
            intent = new Intent(AboutActivity.this, HelpActivity.class);
        } else if (id == R.id.action_about) {
            intent = new Intent(AboutActivity.this, AboutActivity.class);
        } else if (id == R.id.action_overview) {
            intent = new Intent(AboutActivity.this, MainActivity.class);
        } else if (id == R.id.action_excercises) {
            intent = new Intent(AboutActivity.this, ExcerciseActivity.class);
        }

        //start new intent
        if (intent != null) {
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
