package de.web.ngthi.palaver;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class JsonMappingTest {

    @Test
    public void testNewUser() {
        JSONObject object = new JSONObject();
        try {
            object.put("MsgType", 1);
            object.put("Info", "Nachricht verschickt");

            JSONObject dateTime = new JSONObject();
            dateTime.put("DateTime", "2016-02-12T17:01:44.6224075+01:00");
            JSONArray array = new JSONArray();
            array.put(dateTime);

            object.put("ServerData", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
