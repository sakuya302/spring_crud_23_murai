package jp.co.sss.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

}
