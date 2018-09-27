package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name= jsonObject.getJSONObject("name");
            String mainName=name.getString("mainName");
            JSONArray JSONArrayAlsoKnownAs = name.getJSONArray("alsoKnownAs");
            List<String>alsoKnownAs= convertToList(JSONArrayAlsoKnownAs);
            String placeOfOrigin = jsonObject.optString("placeOfOrigin");
            String description=jsonObject.getString("description");
            String image=jsonObject.getString("image");
            JSONArray JSONArrayIngredients=jsonObject.getJSONArray("ingredients");
            List<String>ingredients=convertToList(JSONArrayIngredients);

            return new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    private static List<String> convertToList(JSONArray jsonArray) {
        List<String>list=new ArrayList<>(jsonArray.length());
        for (int i=0;i<jsonArray.length();i++){
            try {
                list.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

return list;

    }
}
