package pl.pw.mini.Schoolify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.pw.mini.Schoolify.services.SchoolService;

@RestController
public class OwnerController {
	
	@Autowired
	SchoolService ss;
	
	// Wywołanie odfiltruje Ci niektore rekordy smieci w bazie danych i doliczy współrzędne uniwersytetów.
	@GetMapping("/calculate")
	public void calc() {
		ss.calcateMissingCoords();
//		ss.fixFuckup();
//		ss.fixLinks();
	}
}
