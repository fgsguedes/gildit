package br.com.hardcoded.gildit.app.external;

import android.net.http.AndroidHttpClient;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Strings.isNullOrEmpty;

public class Reddit {

  private static final String TAG = Reddit.class.getSimpleName();

  private static final AndroidHttpClient httpClient = AndroidHttpClient.newInstance("gildit-android v0.1 by /u/fgsguedes");

  public static JSONObject submissions(final String subredditName) {
    try {
      String subredditUri = isNullOrEmpty(subredditName) ? "" : "r/" + subredditName;
      HttpResponse response = httpClient.execute(new HttpGet("https://www.reddit.com/" + subredditUri + ".json"));
      try (InputStream inputStream = response.getEntity().getContent()) {
        return new JSONObject(IOUtils.toString(inputStream));
      } catch (JSONException e) {
        Log.e(TAG, "Unable to parse JSON", e);
      }
    } catch (IOException e) {
      Log.e(TAG, "Error", e);
    }
    return new JSONObject();
  }

}
