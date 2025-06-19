package jp.co.sss.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.form.LoginForm;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class IndexController {
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	HttpSession session;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String index(@ModelAttribute LoginForm loginForm) {
		session.invalidate();
		return "index";
	}
	
	
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(Model model,LoginForm loginform) {
		
		session.setAttribute("emps",employeeRepository.findByEmpIdAndEmpPass(loginform.getEmpId(), loginform.getEmpPass()) );
		model.addAttribute("allemps", employeeRepository.findAll());
		
		if( (employeeRepository.findByEmpIdAndEmpPass(loginform.getEmpId(), loginform.getEmpPass())).size() == 1) {
			
			return "list/list";
			
		}else {
			model.addAttribute("errMessage", "社員IDまたはパスワードが間違っています。");
			
			return "index";
		}
		
	
	}

}
