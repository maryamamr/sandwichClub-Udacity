package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mAlsoKnownAs,mDescription,mOrigin,mIngredients,mAlsoKnownAsLabel,mOriginLabel;
    ImageView  ingredientsIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownAs= findViewById(R.id.also_known_tv);
        mDescription=findViewById(R.id.description_tv);
        mOrigin=findViewById(R.id.origin_tv);
        mIngredients=findViewById(R.id.ingredients_tv);
        mAlsoKnownAsLabel=findViewById(R.id.also_known_label);
        mOriginLabel=findViewById(R.id.origin_label);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        if (sandwich.getAlsoKnownAs()!=null && sandwich.getAlsoKnownAs().size()>0) {
            StringBuilder alsoKnownAsStringBuilder = new StringBuilder();
           // alsoKnownAsStringBuilder.append(sandwich.getAlsoKnownAs().get(0));
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                alsoKnownAsStringBuilder.append("\u2022").append(" ").append(sandwich.getAlsoKnownAs().get(i));
                alsoKnownAsStringBuilder.append("\n");
            }
            mAlsoKnownAs.setText(alsoKnownAsStringBuilder);
        }
        else {
            mAlsoKnownAs.setVisibility(View.GONE);
            mAlsoKnownAsLabel.setVisibility(View.GONE);
        }
        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            mOrigin.setText(sandwich.getPlaceOfOrigin());
        }
        else {
            mOrigin.setVisibility(View.GONE);
            mOriginLabel.setVisibility(View.GONE);
        }
        if (!sandwich.getDescription().isEmpty()){
        mDescription.setText(sandwich.getDescription());
        }
        else {
            mDescription.setVisibility(View.GONE);
        }
        if (sandwich.getIngredients().size()>0 && sandwich.getIngredients()!=null) {
            StringBuilder ingredientsStringBuilder = new StringBuilder();
            for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                ingredientsStringBuilder.append("\u2022").append(" ").append(sandwich.getIngredients().get(i));
                ingredientsStringBuilder.append("\n");
            }
            mIngredients.setText(ingredientsStringBuilder);
        }
        else {
            mIngredients.setVisibility(View.GONE);
        }
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        setTitle(sandwich.getMainName());

    }
}
