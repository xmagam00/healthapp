package usw.app.martin.healthapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MealsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meals, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.action_bmi) {
            intent = new Intent(MealsActivity.this, BmiActivity.class);
        } else if (id == R.id.action_meals){
            intent = new Intent(MealsActivity.this, MealsActivity.class);
        } else if (id == R.id.action_progress){
            intent = new Intent(MealsActivity.this, ProgressActivity.class);
        } else if (id == R.id.action_help){
            intent = new Intent(MealsActivity.this, HelpActivity.class);
        } else if (id == R.id.action_about){
            intent = new Intent(MealsActivity.this, AboutActivity.class);
        } else if (id == R.id.action_overview){
            intent = new Intent(MealsActivity.this, MainActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
