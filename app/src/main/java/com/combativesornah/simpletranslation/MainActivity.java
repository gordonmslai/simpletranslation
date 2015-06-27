package com.combativesornah.simpletranslation;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;


public class MainActivity extends ActionBarActivity {
    Spinner spinner;
    Spinner spinner2;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter2;
    Activity thisActivity = this;
    String[] ids = {"_eng", "_zh", "_esp"};
    boolean phrase_chosen = false;
    String curr_phrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        set_spinners();
    }

    public void set_spinners() {
        spinner = (Spinner) findViewById(R.id.in_lang);
        spinner2 = (Spinner) findViewById(R.id.out_lang);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.lang_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter2 = ArrayAdapter.createFromResource(this,
                R.array.lang_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerListener());
        spinner2.setOnItemSelectedListener(new Spinner2Listener());
    }

    public class SpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            int header_id = getResources().getIdentifier("header" + ids[pos], "string", getPackageName());
            TextView header = (TextView) findViewById(R.id.header);
            header.setText(header_id);

            String in_lang = ids[pos];
            int pos2 = spinner2.getSelectedItemPosition();
            String out_lang = ids[pos2];
            TextView in_text = (TextView) findViewById(R.id.in_text);
            TextView out_text = (TextView) findViewById(R.id.out_text);
            int in_text_id;
            int out_text_id;
            if (!phrase_chosen) {
                in_text_id = getResources().getIdentifier("def_text" + ids[pos], "string", getPackageName());
                out_text_id = getResources().getIdentifier("def_text2" + ids[pos], "string", getPackageName());

                in_text.setText(in_text_id);
                out_text.setText(out_text_id);
            } else {
                in_text_id = getResources().getIdentifier(curr_phrase + in_lang, "string", getPackageName());
                in_text.setText(in_text_id);
            }
            CharSequence[][] out_lang_choices = {{"English", "中文 (Chinese)", "Español (Spanish)"},
                    {"English (英语)", "中文", "Español (西班牙语)"},
                    {"English (Inglés)", "中文 (Chino)", "Español"}};
            adapter2 = new ArrayAdapter<CharSequence>(thisActivity, android.R.layout.simple_spinner_item, Arrays.asList(out_lang_choices[pos]));
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            spinner2.setSelection(pos2);
            for (int i = 1; i < 6; i++) {
                int phrase_id = getResources().getIdentifier("phrase" + String.valueOf(i), "id", getPackageName());
                TextView in_phrase = (TextView) findViewById(phrase_id);
                int phrase_text_id = getResources().getIdentifier("phrase" + String.valueOf(i) + ids[pos], "string", getPackageName());
                in_phrase.setText(phrase_text_id);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    public class Spinner2Listener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String out_lang = ids[pos];
            TextView out_text = (TextView) findViewById(R.id.out_text);
            int out_text_id;

            String curr_in_text_id = getResources().getResourceEntryName(out_text.getId());
            if (phrase_chosen) {
                out_text_id = getResources().getIdentifier(curr_phrase + out_lang, "string", getPackageName());
                out_text.setText(out_text_id);

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }
    public void inPhraseClick(View view) {
        phrase_chosen = true;
        String id = getResources().getResourceEntryName(view.getId());
        curr_phrase = id;

        String lang = ids[spinner.getSelectedItemPosition()];
        String lang2 = ids[spinner2.getSelectedItemPosition()];

        TextView in_text = (TextView) findViewById(R.id.in_text);
        TextView out_text = (TextView) findViewById(R.id.out_text);

        int text_id = getResources().getIdentifier(id + lang, "string", getPackageName());
        int text2_id = getResources().getIdentifier(id + lang2, "string", getPackageName());

        in_text.setText(text_id);
        out_text.setText(text2_id);

        RelativeLayout phrase_options = (RelativeLayout) findViewById(R.id.phrase_options);
        phrase_options.setVisibility(View.INVISIBLE);
    }

    public void inTextClick(View view) {
        RelativeLayout phrase_options = (RelativeLayout) findViewById(R.id.phrase_options);
        if (phrase_options.getVisibility() == View.INVISIBLE) {
            phrase_options.setVisibility(View.VISIBLE);
        }
        else {
            phrase_options.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
