package NetworkAvailability;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NetworkUtils {

	public boolean isInternetAvailable() {
		try {
			URL url = new URL("https://www.google.com");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			connection.disconnect();
			return true;
		} catch (IOException e) {
			log.error("Exception occurred while checking for network connectivity: {}", e.getMessage());
			return false;
		}
	}
}
