import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPTAPI {

    public static String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "[api key was here, removed for privacy]"; 
        String model = "gpt-3.5-turbo";
        int maxRetries = 5;
        int retryDelay = 2000; 
    
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                URL obj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + apiKey);
                connection.setRequestProperty("Content-Type", "application/json");
                
                String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
                connection.setDoOutput(true);
                
                try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                    writer.write(body);
                }
    
                int responseCode = connection.getResponseCode();
    
                if (responseCode == 429) {
                    System.out.println("Rate limit exceeded. Retrying in " + retryDelay + "ms...");
                    Thread.sleep(retryDelay);
                    retryDelay *= 2;
                    continue;
                }
    
                if (responseCode != 200) {
                    throw new IOException("Unexpected response code: " + responseCode);
                }
    
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    return extractMessageFromJSONResponse(response.toString());
                }
    
            } catch (IOException | InterruptedException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        return "Failed to get a response after multiple retries.";
    }
    

   public static String extractMessageFromJSONResponse(String response) {
       int start = response.indexOf("content")+ 11;

       int end = response.indexOf("\"", start);

       return response.substring(start, end);

   }

   public static void main(String[] args) {

       System.out.println(chatGPT("Hello ChatGPT, can you see this message?"));

   }
}