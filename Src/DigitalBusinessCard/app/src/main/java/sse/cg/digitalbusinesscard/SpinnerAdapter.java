package sse.cg.digitalbusinesscard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpinnerAdapter {
    public SpinnerAdapter() {

    }

    public static List<Map<String, Object>> getSpinnerData() {
        List<Map<String, Object>> spinnerData = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("log", R.drawable.texture1);
        map.put("listName", "texture1");
        spinnerData.add(map);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("log", R.drawable.texture2);
        map2.put("listName", "texture2");
        spinnerData.add(map2);

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("log", R.drawable.texture3);
        map3.put("listName", "texture3");
        spinnerData.add(map3);

        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("log", R.drawable.texture4);
        map4.put("listName", "texture4");
        spinnerData.add(map4);

        Map<String, Object> map5 = new HashMap<String, Object>();
        map5.put("log", R.drawable.texture5);
        map5.put("listName", "texture5");
        spinnerData.add(map5);

        Map<String, Object> map6 = new HashMap<String, Object>();
        map6.put("log", R.drawable.texture6);
        map6.put("listName", "texture6");
        spinnerData.add(map6);

        return spinnerData;

    }
}
