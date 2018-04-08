package info.rohanmorris.quizmaster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final Spinner category = findViewById(R.id.category);
        final Spinner difficulty = findViewById(R.id.difficulty);
        Button btn = findViewById(R.id.save);

        final SharedPreferences prefSettings = getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);

        int lvl, cat;

        lvl = prefSettings.getInt(getString(R.string.game_level), Integer.parseInt(getString(R.string.game_level_default)));
        cat = prefSettings.getInt(getString(R.string.game_category), Integer.parseInt(getString(R.string.game_category_default)));

        difficulty.setAdapter(new Adapter(this, Common.getGameLevelList()));
        category.setAdapter(new Adapter(this, Common.getGameCategoryList()));

        difficulty.setSelection(lvl);
        category.setSelection(cat);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefSettings.edit();
                editor.putInt(getString(R.string.game_level), difficulty.getSelectedItemPosition());
                editor.putInt(getString(R.string.game_category), category.getSelectedItemPosition());
                editor.apply();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public class Adapter extends ArrayAdapter {
        private LayoutInflater inflater;
        private ArrayList<Common.Category> cat = new ArrayList<>();

        Adapter(Context context, ArrayList<Common.Category> c) {
            super(context, R.layout.spinner_layout);
            if (inflater == null) {
                inflater = LayoutInflater.from(context);
            }
            this.cat = c;
        }

        @Override
        public int getCount() {
            return cat.size();
        }

        @Nullable
        @Override
        public Object getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public int getPosition(@Nullable Object item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            @SuppressLint("ViewHolder")
            View rootView   = inflater.inflate(R.layout.spinner_layout, parent, false);
            TextView value  = rootView.findViewById(R.id.value);

            value.setText(Html.fromHtml(cat.get(position).getTitle()));
            return rootView;
        }

        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            return getView(position, convertView, parent);
        }
    }
}
