package au.org.paperminer.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import au.org.paperminer.bean.HistoryFacade;

@Controller
@RequestMapping("api")
public class WebController {
	
	@RequestMapping(value="simplehistory")
	public @ResponseBody String add(@RequestParam("keywords")String keywords) {
		//historyFacade.addSimpleHistory(keywords);
		
		return new String();
	}
}
