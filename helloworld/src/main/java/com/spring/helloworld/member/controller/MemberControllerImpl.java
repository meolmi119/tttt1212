package com.spring.helloworld.member.controller;

import org.springframework.http.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.helloworld.goods.service.GoodsService;
import com.spring.helloworld.goods.vo.GoodsStayVO;
import com.spring.helloworld.member.service.MemberService;
import com.spring.helloworld.member.vo.MemberVO;

@Controller("memberController")
public class MemberControllerImpl implements MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class);
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberVO memberVO;
	
	@Autowired
	private GoodsStayVO goodsStayVO;
	
	@Autowired
	private GoodsService goodsService;
	
	//����������
	@RequestMapping(value= {"/main.do"}, method=RequestMethod.GET) 
	private ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	//Form.do ������ �̵�
	@RequestMapping(value= "/member/*Form.do", method=RequestMethod.GET)
	private ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
		
	//�α���
	@Override
	@RequestMapping(value = "/member/login.do",method = RequestMethod.POST)
	public ModelAndView login(@RequestParam Map<String, String>loginMap,
		HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView mav = new ModelAndView();
		memberVO = memberService.login(loginMap);
		if(memberVO!=null && memberVO.getMem_id()!=null) {
			HttpSession session = request.getSession();
			session = request.getSession();
			session.setAttribute("isLogOn", true);
			session.setAttribute("memberInfo", memberVO);
			
			//������ ������� �ʴ� �ڵ�(������������ �ƴ� �ٸ����������� �α��� â���� �̵��� �α��� �ϴ� ���)
			String action = (String)session.getAttribute("action");
			if(action!=null && action.equals("/order/orderEachGoods.do")) {
				mav.setViewName("forward:"+action);
			} else {
				mav.setViewName("redirect:/main.do");
			}
			
		}else {
			String message = "���̵� ��й�ȣ�� Ʋ���ϴ�. �ٽ� �α������ּ���";
			mav.addObject("message",message);
			mav.setViewName("/member/loginForm");
		}
		return mav;
	}
	
	//�α׾ƿ�
	@Override
	@RequestMapping(value="/member/logout.do" ,method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session=request.getSession();
		session.setAttribute("isLogOn", false);
		session.removeAttribute("memberInfo");
		mav.setViewName("redirect:/main.do");
		return mav;
	}
	
	//ȸ������
	//@ModelAttribute ������̼��� ���� �����͸� �ڵ����� ���ε��� �� ����(Form�� ���� vo�� ���ε�)
	@Override
	@RequestMapping(value="/member/addMember.do" ,method = RequestMethod.POST)
	public ResponseEntity addMember(@ModelAttribute("memberVO") MemberVO _memberVO,
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
		    memberService.addMember(_memberVO);
		    message  = "<script>";
		    message +=" alert('ȸ�� ������ ���ƽ��ϴ�.�α���â���� �̵��մϴ�.');";
		    message += " location.href='"+request.getContextPath()+"/member/loginForm.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('�۾� �� ������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');";
		    message += " location.href='"+request.getContextPath()+"/member/memberForm.do';";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	//ȸ��Ż��
	@Override
	@RequestMapping(value="/member/unregister.do" ,method = RequestMethod.POST)
	public void unregister(@RequestParam("mem_id") String mem_id,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();
		session.setAttribute("isLogOn", false);
		session.removeAttribute("memberInfo");
		
		memberService.unregister(mem_id);
		
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("location.href='" + request.getContextPath() + "/member/unregistEndForm.do';");
		out.println("</script>");
		out.flush();	
	}

	//���̵� �ߺ�Ȯ��
	//ResponseEntity ��ü�� ��ȯ�ϸ� http�������� ��ȯ�Ͽ� ����
	//HttpStatus.OK ���������� ��ȯ ������ ���(200�ڵ�)
	@Override
	@RequestMapping(value="/member/overlapped.do" ,method = RequestMethod.POST)
	public ResponseEntity overlapped(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseEntity resEntity = null; 
		String result = memberService.overlapped(id);
		resEntity =new ResponseEntity(result, HttpStatus.OK);
		return resEntity;
	}
	
	//ȸ�� ��ȸ(���̵� ã��)
	@RequestMapping(value = "/member/selectMember.do",method = RequestMethod.POST)
	public ModelAndView selectMember(@RequestParam Map<String, String>selectMap,
	    HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView mav = new ModelAndView();
		memberVO = memberService.selectMember(selectMap);
		if(memberVO!=null && memberVO.getMem_name()!=null && memberVO.getMem_id()!=null && memberVO.getMem_email1()!=null && memberVO.getMem_email2()!=null){
			HttpSession session = request.getSession();
			session = request.getSession();
			session.setAttribute("memberIDFind", memberVO);
			MemberVO memberInfo = (MemberVO) session.getAttribute("memberIDFind");
			String message = "ȸ������ ���̵�� "+memberInfo.getMem_id()+" �Դϴ�.";
			mav.addObject("message",message);
			mav.setViewName("/member/loginForm");
			
		}else {
			String message = "�̸� �Ǵ� �̸����� ��ġ���� �ʽ��ϴ�. �ٽ� �Է��� �ּ���";
			mav.addObject("message",message);
			mav.setViewName("/member/findIDForm");
		}
		return mav;
	}
	
	//��й�ȣã�� ������ȣ(����) 
	@RequestMapping(value= "/member/findCerti.do", method=RequestMethod.POST)
	private ModelAndView findCerti(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer numObject = (Integer) session.getAttribute("num");
		int num = numObject.intValue();
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.addObject("num",num);
		mav.setViewName(viewName);
		return mav;
	}
}