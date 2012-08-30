package httpClient;

import org.apache.http.HttpResponse;

public interface AfterResponseHandler {

	void handle(HttpResponse response);
}
