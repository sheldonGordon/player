package fr.chatelain.player.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
public class PlayerController {
    
	@Value("${directory.videos}")
	private String directoryVideos;

	@RequestMapping(method = RequestMethod.GET, value = "/player/{video}")
	public String player(@PathVariable String video, Model model) {
		model.addAttribute("video", video);
		return "player";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/stream/{name}")
	public StreamingResponseBody stream(@PathVariable String name) throws FileNotFoundException {
		File videoFile = new File(directoryVideos+name);
		final InputStream videoFileStream = new FileInputStream(videoFile);
		return (os) -> {
			readAndWrite(videoFileStream, os);
		};
	}
	private void readAndWrite(final InputStream is, OutputStream os) throws IOException {
		byte[] data = new byte[2048];
		int read = 0;
		while ((read = is.read(data)) > 0) {
			os.write(data, 0, read);
		}
		os.flush();
	}
}
