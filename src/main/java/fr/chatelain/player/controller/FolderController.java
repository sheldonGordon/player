package fr.chatelain.player.controller;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FolderController {
    
	@Value("${directory.videos}")
	private String directoryVideos;
	
    @GetMapping("/folder")
	public String folder(Model model) {
		List<String> files = Stream.of(new File(directoryVideos).listFiles())
								.filter(file -> !file.isDirectory())
								.map(File::getName)
								.collect(Collectors.toList());
		model.addAttribute("files", files);
		return "folder";
	}
}
