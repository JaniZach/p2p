package invest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前台投资相关的控制器
 * @author Jani
 */
@Controller
public class InvestController {
	@RequestMapping("invest")
	public String invest(){
		return "";
	}
}
