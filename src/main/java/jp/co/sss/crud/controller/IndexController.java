package jp.co.sss.crud.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.form.LoginForm;
import jp.co.sss.crud.repository.DepartmentRepository;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class IndexController {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;


	@Autowired
	HttpSession session;

	
	//01-ログイン
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String index(@ModelAttribute LoginForm loginForm) {
		
		return "index";
	}
	
	
	//02-ログアウト
	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String logout(@ModelAttribute LoginForm loginForm) {
		session.invalidate();
		return "index";
	}
	
	
	//03-社員一覧表示
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(Model model,LoginForm loginform) {
		
		if( (employeeRepository.findByEmpIdAndEmpPass(loginform.getEmpId(), loginform.getEmpPass())) != null) {
			session.setAttribute("user",employeeRepository.findByEmpIdAndEmpPass(loginform.getEmpId(), loginform.getEmpPass()) );
			model.addAttribute("allemps", employeeRepository.findAllByOrderByEmpId());
			
			return "list/list";
			
		}else {
			model.addAttribute("errMessage", "社員IDまたはパスワードが間違っています。");
			
			return "index";
		}
	}
	
	
	@RequestMapping(path = "/list/all", method = RequestMethod.GET)
	public String alllist(Model model) {
		model.addAttribute("allemps", employeeRepository.findAllByOrderByEmpId());
		//Employee employee = employeeRepository.findById(empForm.getEmpId()).get();
		//session.setAttribute("user", employee);
		//model.addAttribute("emp", employee);
		return "list/list";
	}
	
	
	//04-社員名検索
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
	
	
	@RequestMapping(path = "/regist/reinput", method = RequestMethod.GET)
	public String rein(@ModelAttribute EmployeeForm empForm) {
		//Employee employee = employeeRepository.findById(empForm.getEmpId()).get();
		//model.addAttribute("emp", employee);
		
		return "regist/regist_input";
	}
	
	
	@RequestMapping(path = "/regist/check", method = RequestMethod.POST)
	public String rche(EmployeeForm empForm, Model model) {
		Employee emp = new Employee();
		
		Department department = departmentRepository.findById(empForm.getDeptId()).get();
		
		BeanUtils.copyProperties(empForm, emp);
		
		emp.setDepartment(department);
		
		emp = employeeRepository.save(emp);
		
		
		model.addAttribute("emp",emp);
		session.setAttribute("form", empForm);
		model.addAttribute("errMessage", "もう一度やり直してください。");
		
		return "regist/regist_check";
	}
	
	
	@RequestMapping(path = "/regist/complete", method = RequestMethod.POST)
	public String rcom() {
		
		return "regist/regist_complete";
	}
	
	
	
	
	//07-社員変更
		@RequestMapping(path = "/update/input", method = RequestMethod.GET)
		public String uin(@ModelAttribute EmployeeForm empForm,Model model) {
			
			Employee emp = employeeRepository.findById(empForm.getEmpId()).get();
			
			//BeanUtils.copyProperties(empForm, emp);
			//empForm.setDeptId(emp.getDepartment().getDeptId());
			
			model.addAttribute("emp",emp);
			
			return "update/update_input";
		}
		
		@RequestMapping(path = "/update/back", method = RequestMethod.GET)
		public String uback(EmployeeForm empForm, Model model) {
			
			return "update/update_input";
		}
		
		
		@RequestMapping(path = "/update/check", method = RequestMethod.POST)
		public String uche(EmployeeForm empForm, Model model) {
			Employee emp = new Employee();
			
			Department department = departmentRepository.findById(empForm.getDeptId()).get();
			
			BeanUtils.copyProperties(empForm, emp);
			
			emp.setDepartment(department);
			
			emp = employeeRepository.save(emp);
			
			
			model.addAttribute("emp",emp);
			session.setAttribute("form", empForm);
			model.addAttribute("errMessage", "もう一度やり直してください。");
			return "update/update_check";
		}
		
		@RequestMapping(path = "/update/complete", method = RequestMethod.POST)
		public String ucom() {
			
			return "update/update_complete";
		}
		
	
		
		
		//08-社員削除
		@RequestMapping(path = "/delete/check", method = RequestMethod.GET)
		public String dche(EmployeeForm empForm, Model model) {
			Employee employee = employeeRepository.findById(empForm.getEmpId()).get();
			model.addAttribute("emp", employee);
			
			return "delete/delete_check";
		}
		
		
		@RequestMapping(path = "/delete/complete", method = RequestMethod.POST)
		public String dcom(EmployeeForm empForm) {
			 employeeRepository.deleteById(empForm.getEmpId());
			return "delete/delete_complete";
		}

}
