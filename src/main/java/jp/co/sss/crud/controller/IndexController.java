package jp.co.sss.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.form.EmployeeForm;
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
	
	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String logout(@ModelAttribute LoginForm loginForm) {
		session.invalidate();
		return "index";
	}
	
	
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(Model model,LoginForm loginform) {
		
		if( (employeeRepository.findByEmpIdAndEmpPass(loginform.getEmpId(), loginform.getEmpPass())) != null) {
			session.setAttribute("user",employeeRepository.findByEmpIdAndEmpPass(loginform.getEmpId(), loginform.getEmpPass()) );
			model.addAttribute("allemps", employeeRepository.findAll());
			
			return "list/list";
			
		}else {
			model.addAttribute("errMessage", "社員IDまたはパスワードが間違っています。");
			
			return "index";
		}
	}
	
	
	@RequestMapping(path = "/list/empName", method = RequestMethod.GET)
	public String nameserch(Model model, EmployeeForm empForm) {
		model.addAttribute("allemps", employeeRepository.findByEmpNameContaining(empForm.getEmpName()));
		
		return "list/list";
	}
	
	
	//05-部署名検索
	@RequestMapping(path = "/list/deptId", method = RequestMethod.GET)
	public String deptIdserch(Model model, EmployeeForm empForm) {
		Department department = new Department();
		department.setDeptId(empForm.getDeptId());
		
		model.addAttribute("allemps", employeeRepository.findByDepartment(department));
		
		return "list/list";
	}
	
	
	//06-社員登録
	@RequestMapping(path = "/regist/input", method = RequestMethod.GET)
	public String rin(@ModelAttribute EmployeeForm empForm) {
		
		return "regist/regist_input";
	}
	
	
	@RequestMapping(path = "/regist/check", method = RequestMethod.POST)
	public String rche() {
		
		return "regist/regist_input";
	}

}
