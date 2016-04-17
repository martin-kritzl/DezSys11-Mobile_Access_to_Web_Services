package at.mkritzl.dezsys11.dezsys11.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import at.mkritzl.dezsys11.dezsys11.model.Response;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * This class is used for calling the REST API that was implemented in DezSys09. This
 * includes the login and the registration
 */
public class RestHandler {
    /**
     * Calls the login-REST API from the server
     *
     * @param context The application context
     * @param email The entered email
     * @param password The entered password
     * @return The response from the server
     * @throws RestException When the server is not responding
     */
    public static Response login(Context context, String email, String password) throws RestException {
        return handleResponse(context, email, password, "https://dezsys09-web-service.herokuapp.com/login");
    }

    /**
     * Calls the register-REST API from the server
     *
     * @param context The application context
     * @param email The entered email
     * @param password The entered password
     * @return The response from the server
     * @throws RestException When the server is not responding
     */
    public static Response register(Context context, String email, String password) throws RestException {
        return handleResponse(context, email, password, "https://dezsys09-web-service.herokuapp.com/register");
    }

    /**
     * Calls the rest-api with the given parameter and returns the response
     *
     * @param context The application context
     * @param email The entered email
     * @param password The entered password
     * @return The response from the server
     * @throws RestException When the server is not responding
     */
    private static Response handleResponse(Context context, String email, String password, String url) throws RestException {
        JSONObject jsonParams = null;
        StringEntity entity = null;

        try {
            jsonParams = new JSONObject();
            jsonParams.put("email", email);
            jsonParams.put("password", password);

            entity = new StringEntity(jsonParams.toString());
        } catch (JSONException | UnsupportedEncodingException e) {
            throw new RestException("Email or password false. That should not happened");
        }
        Response response = new Response();
        AsyncHttpClient client = new SyncHttpClient();

        // Long timeout because Heroku sometimes needs a long time for starting the server
        client.setTimeout(50000);
        try {
            client.post(context, url, entity, "application/json", new ResponseHandler(response));
        } catch (Exception e) {
            throw new RestException("The Server is not responding. Make sure you are connected to the internet");
        }
        return response;
    }

    /**
     * Handles the response from Server and maps the received String into a JSON object
     */
    public static class ResponseHandler extends TextHttpResponseHandler {

        private Response response;

        public ResponseHandler(Response response) {
            this.response = response;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {
            this.handleState(statusCode,responseString);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable error) {
            this.handleState(statusCode,responseString);
        }

        /**
         * Used for success and failure to handle the incoming message
         *
         * @param statusCode The statuscode of the response
         * @param responseString The content off the response
         */
        private void handleState(int statusCode, String responseString) {
            try {
                JSONObject obj = new JSONObject(responseString);
                if (obj.has("status") && obj.has("message")) {
                    response.setStatus(obj.getInt("status"));
                    response.setMessage(obj.getString("message"));
                } else {
                    throw new RestException("Response does not contain a status or a message");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(statusCode);
                response.setMessage("The Server is not responding. Make sure you are connected to the internet");
            }
        }
    }
}
