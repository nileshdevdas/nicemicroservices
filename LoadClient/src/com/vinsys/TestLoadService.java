package com.vinsys;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestLoadService {

	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(1000);
		for (int a = 0; a < 100; a++) {
			for (int i = 0; i < 1000; i++) {
				service.execute(new Runnable() {
					public void run() {
						try {
							URL url = new URL("http://localhost:7081/clientversion");
							HttpURLConnection connection = (HttpURLConnection) url.openConnection();
							InputStream is = connection.getInputStream();
							System.out.println("Response Code " + connection.getResponseCode());
							byte b[] = new byte[is.available()];
							is.read(b);
							System.out.println(new String(b));
							is.close();
							connection.disconnect();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		}
	}

}
