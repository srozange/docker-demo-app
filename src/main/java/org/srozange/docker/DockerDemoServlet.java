package org.srozange.docker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DockerDemoServlet extends HttpServlet {

	private static final long serialVersionUID = 1215808248839324279L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("<h1>Docker Demo App</h1>");
		out.print(getConfiguration().get("welcomeMessage"));
		out.println("</body></html>");
	}

	private Properties getConfiguration() {
		return getConfigurationFromFileSystem().orElse(getDefaultConfiguration());
	}

	private Optional<Properties> getConfigurationFromFileSystem() {
		File configurationFile = Paths.get(System.getenv("CONFIG_PATH"), "config.properties").toFile();
		if (configurationFile.exists()) {
			return Optional.of(loadPropertyFile(configurationFile));
		}
		return Optional.empty();
	}

	private Properties getDefaultConfiguration() {
		return loadPropertyFromResource("org/srozange/docker/config.properties");
	}

	private Properties loadPropertyFile(File configurationFile) {
		Properties properties = new Properties();
		try (FileInputStream fi = new FileInputStream(configurationFile)) {
			properties.load(fi);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return properties;
	}

	private Properties loadPropertyFromResource(String resourceName) {
		Properties properties = new Properties();
		try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
			properties.load(in);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return properties;
	}

}