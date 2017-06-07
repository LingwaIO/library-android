package io.lingwa.android.AsyncTasks;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Christian on 13-May-17.
 */

public class DownloadTask extends AsyncTask<String, Void, DownloadTask.DownloadTaskResponse> {

    private static final int CONNECTION_TIMEOUT_MS = 20000;

    private static final String URL = "http://app.lingwa.io/translations?token=";
    private static final String TAG = "LingwaDownloadTask";

    private OnDownloadRequestCompleted onDownloadRequestCompleted;

    public DownloadTask(OnDownloadRequestCompleted onDownloadRequestCompleted){
        this.onDownloadRequestCompleted = onDownloadRequestCompleted;
    }

    @Override
    protected DownloadTaskResponse doInBackground(String... params) {
        String projectCode = params[0];
        if(projectCode == null){
            return new DownloadTaskResponse(0, "Project code is null");
        }

        try {
            InputStream is = null;

            try {
                URL url = new URL(URL + projectCode);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(CONNECTION_TIMEOUT_MS);  /* milliseconds */
                conn.setConnectTimeout(CONNECTION_TIMEOUT_MS); /* milliseconds */
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int responseCode = conn.getResponseCode();
                is = conn.getInputStream();

                return new DownloadTaskResponse(responseCode, convertInputStreamToString(is));
            } catch (SecurityException e){
                return new DownloadTaskResponse(0, e.getMessage());
            }finally {
                if (is != null) {
                    is.close();
                }
            }
        } catch (SocketTimeoutException e) {
            return new DownloadTaskResponse(0, "Network request timed out");
        } catch (IOException e) {
            return new DownloadTaskResponse(0, e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(DownloadTaskResponse response) {
        if(onDownloadRequestCompleted !=null){
            if(response.getResponseCode()==200){
                onDownloadRequestCompleted.onRequestSuccessful(response.getResult());
            }else {
                onDownloadRequestCompleted.onRequestError(response.getResult());
            }
        }
    }

    public class DownloadTaskResponse{

        private int responseCode;
        private String result;

        public DownloadTaskResponse(int responseCode, String result) {
            this.responseCode = responseCode;
            this.result = result;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public String getResult() {
            return result;
        }
    }

    private String convertInputStreamToString(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public interface OnDownloadRequestCompleted {
        void onRequestSuccessful(String result);
        void onRequestError(String error);
    }
}
