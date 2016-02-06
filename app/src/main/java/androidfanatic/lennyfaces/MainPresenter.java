package androidfanatic.lennyfaces;

import android.content.Context;
import android.content.res.AssetManager;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainPresenter extends MvpBasePresenter<MainView> {

    HashMap<String, List<String>> faces;
    private FaceAdapter faceAdapter;

    @Override public void attachView(MainView view) {
        super.attachView(view);
        init();
    }

    public void init() {
        faceAdapter = new FaceAdapter();
        loadData(getView().getApplicationContext());
    }

    private String capitalize(String str) {
        if (str == null || str.length() < 1) {
            return str;
        } else if (str.length() == 1) {
            return str.toUpperCase();
        } else {
            return String.valueOf(str.charAt(0)).toUpperCase() + str.substring(1).toLowerCase();
        }
    }

    private void loadData(Context applicationContext) {
        faces = new HashMap<>();
        faces.put("All", new ArrayList<String>());
        try {
            AssetManager assetManager = applicationContext.getAssets();
            InputStream inputStream = assetManager.open("faces.json");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            // parsing json
            String json = new String(stringBuffer);
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String faceType = keys.next();
                List<String> faceList = new ArrayList<>();
                JSONArray jsonArray = jsonObject.getJSONArray(faceType);
                for (int i = 0; i < jsonArray.length(); i++) {
                    faceList.add((String) jsonArray.get(i));
                    System.out.print(jsonArray.get(i));
                    System.out.print("\r\n");
                }
                faces.put(faceType, faceList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showFaceType(String faceType) {
        faceType = faceType.toLowerCase();
        if (faceType.equals("all")) {
            showAllFaces();
        } else {
            faceAdapter.setFaces(faces.get(faceType));
        }
    }

    private void showAllFaces() {
        ArrayList<String> tmpFaces = new ArrayList<>();
        for (List<String> face : faces.values()) {
            tmpFaces.addAll(face);
        }
        faceAdapter.setFaces(tmpFaces);
    }

    public FaceAdapter getAdapter() {
        return faceAdapter;
    }

    public void initUI() {
        initDrawer();
        showAllFaces();
    }

    private void initDrawer() {
        List<String> faceTypes = new ArrayList<>(faces.keySet());
        Collections.sort(faceTypes);
        for (int i = 0; i < faceTypes.size(); i++) {
            faceTypes.set(i, capitalize(faceTypes.get(i)));
        }
        getView().initDrawer(faceTypes);
    }
}
