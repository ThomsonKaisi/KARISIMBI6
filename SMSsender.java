import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

public class SMSsender {

    private static final String SMS_TOKEN = "your_sms_token_here";
    private static final String SMS_URL = "your_sms_url_here";

    public static boolean sendSMS(String recipientNumber, String message) {
        try {
            URL url = new URL(SMS_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", SMS_TOKEN);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            Map<String, String> payload = new HashMap<>();
            payload.put("to", recipientNumber);
            payload.put("message", message);

            Gson gson = new Gson();
            String jsonPayload = gson.toJson(payload);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
