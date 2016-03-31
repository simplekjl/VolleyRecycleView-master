package company.example.volleyrecycleview.Interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import company.example.volleyrecycleview.MainActivity;

/**
 * Created by admin on 1/28/2016.
 */
public interface DataListener {

    void onDataReceived(ArrayList<HashMap<String, Object>> response);
    void onError(MainActivity resp);

}
