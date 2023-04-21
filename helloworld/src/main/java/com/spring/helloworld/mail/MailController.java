package com.spring.helloworld.mail;

import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.helloworld.business.service.BusinessService;
import com.spring.helloworld.business.vo.BusinessVO;
import com.spring.helloworld.member.service.MemberService;
import com.spring.helloworld.member.vo.MemberVO;

@Controller
@EnableAsync
public class MailController {
	@Autowired
	private MailService mailService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BusinessService businessService;
	
	//���� ������(�Ϲ� ȸ������)
		@RequestMapping(value = "/sendMemberMail.do", method = RequestMethod.POST)
		public ResponseEntity<Void> sendMemberMail(@RequestParam("mem_email1") String mem_email1, @RequestParam("mem_email2") String mem_email2, @RequestParam("certi_num")
		String num, HttpServletRequest request, HttpServletResponse response)throws Exception{
			
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
				
				StringBuffer sb = new StringBuffer();
					sb.append("<html><body>");
						sb.append("<meta http-equiv='Content-Type' content='text/html;charset=euc-kr'>");
						sb.append("<h1>"+"ȸ������"+"</h1><br>");
						sb.append("ȸ������(�̸���) ������ȣ��"+num+"�Դϴ�.<br><br>");
						sb.append("���� �̸��������� ��û���� �����̴ٸ� ��� <br>�����Ϳ� �����Ͽ� ���� ȸ�������� ������ �ּ���.");
						sb.append("</body></html>");
						String str = sb.toString();
				//������, ����, ����
				mailService.sendMail(mem_email1+"@"+mem_email2, "[helloWorld]ȸ������ ������ȣ", str);
				//���� ���� ��û�̶�� ���¸� �������� (200ok)
	     		return ResponseEntity.ok().build();
		}
		
		//���� ������(����� ȸ������)
				@RequestMapping(value = "/sendBusinessMail.do", method = RequestMethod.POST)
				public ResponseEntity<Void> sendBusinessMail(@RequestParam("busi_email1") String busi_email1, @RequestParam("busi_email2") String busi_email2, @RequestParam("certi_num")
				String num, HttpServletRequest request, HttpServletResponse response)throws Exception{
					
					request.setCharacterEncoding("utf-8");
					response.setContentType("text/html;charset=utf-8");
						
						StringBuffer sb = new StringBuffer();
							sb.append("<html><body>");
								sb.append("<meta http-equiv='Content-Type' content='text/html;charset=euc-kr'>");
								sb.append("<h1>"+"ȸ������"+"</h1><br>");
								sb.append("ȸ������(�̸���) ������ȣ��"+num+"�Դϴ�.<br><br>");
								sb.append("���� �̸��������� ��û���� �����̴ٸ� ��� <br>�����Ϳ� �����Ͽ� ���� ȸ�������� ������ �ּ���.");
								sb.append("</body></html>");
								String str = sb.toString();
						//������, ����, ����
						mailService.sendMail(busi_email1+"@"+busi_email2, "[helloWorld]ȸ������ ������ȣ", str);
						//���� ���� ��û�̶�� ���¸� �������� (200ok)
			     		return ResponseEntity.ok().build();
				}
				
	//���� ������(�Ϲ�ȸ�� ��й�ȣ ã��)
	@RequestMapping(value = "/sendMail.do", method = RequestMethod.POST)
	public ModelAndView sendMemberMail(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String mem_id = (String)request.getParameter("mem_id");
		String mem_email1 = (String)request.getParameter("mem_email1");
		String mem_email2 = (String)request.getParameter("mem_email2");


		MemberVO vo = memberService.selectMemberID(mem_id);
		String email1 = vo.getMem_email1();
		String email2 = vo.getMem_email2();
		if(vo != null && email1.equals(mem_email1) && email2.equals(mem_email2)) {
			SecureRandom r = new SecureRandom();
			int num = r.nextInt(888888)+111111; // ������������
			
	
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			StringBuffer sb = new StringBuffer();
				sb.append("<html><body>");
					sb.append("<meta http-equiv='Content-Type' content='text/html;charset=euc-kr'>");
					sb.append("<h1>"+"��й�ȣã��"+"</h1><br>");
					sb.append("��й�ȣã��(����) ������ȣ��"+num+"�Դϴ�.<br><br>");
					sb.append("���� ��й�ȣã�� �ڵ带 ��û���� �����̴ٸ� ��� <br>��й�ȣ�� �����Ͽ� ���� �α����� ������ �ּ���.");
					sb.append("</body></html>");
					String str = sb.toString();
			//������, ����, ����
			mailService.sendMail(email1+"@"+email2, "[helloWorld]��й�ȣã�� ������ȣ", str);
			ModelAndView mav = new ModelAndView();
			HttpSession session = request.getSession();
			session = request.getSession();
			session.setAttribute("memberPwFind", vo);
			session.setAttribute("num", num);
			mav.setViewName("forward:/member/findCerti.do");
     		return mav;
		}else {
			ModelAndView mav = new ModelAndView();
			String message = "���̵� �Ǵ� �̸����� ��ġ���� �ʽ��ϴ�. �ٽ� �Է��� �ּ���";
			mav.addObject("message",message);
			mav.setViewName("/member/findPwForm");
			return mav;
		}
	}
	
	//���� ������(����� ��й�ȣ ã��)
		@RequestMapping(value = "/sendBusiMail.do", method = RequestMethod.POST)
		public ModelAndView sendBusiMail(HttpServletRequest request, HttpServletResponse response)throws Exception{
			String busi_id = (String)request.getParameter("busi_id");
			String busi_email1 = (String)request.getParameter("busi_email1");
			String busi_email2 = (String)request.getParameter("busi_email2");
			String busi_brn = (String)request.getParameter("busi_brn");


			BusinessVO vo = businessService.selectMemberID(busi_id);
			String email1 = vo.getBusi_email1();
			String email2 = vo.getBusi_email2();
			String brn = vo.getBusi_brn();
			if(vo != null && email1.equals(busi_email1) && email2.equals(busi_email2) && brn.equals(busi_brn)) {
				SecureRandom r = new SecureRandom();
				int num = r.nextInt(888888)+111111; // ������������
				
		
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				
				StringBuffer sb = new StringBuffer();
					sb.append("<html><body>");
						sb.append("<meta http-equiv='Content-Type' content='text/html;charset=euc-kr'>");
						sb.append("<h1>"+"��й�ȣã��"+"</h1><br>");
						sb.append("��й�ȣã��(����) ������ȣ��"+num+"�Դϴ�.<br><br>");
						sb.append("���� ��й�ȣã�� �ڵ带 ��û���� �����̴ٸ� ��� <br>��й�ȣ�� �����Ͽ� ���� �α����� ������ �ּ���.");
						sb.append("</body></html>");
						String str = sb.toString();
				//������, ����, ����
				mailService.sendMail(email1+"@"+email2, "[helloWorld]��й�ȣã�� ������ȣ", str);
				ModelAndView mav = new ModelAndView();
				HttpSession session = request.getSession();
				session = request.getSession();
				session.setAttribute("businessPwFind", vo);
				session.setAttribute("num", num);
				mav.setViewName("forward:/member/findCerti.do");
	     		return mav;
			}else {
				ModelAndView mav = new ModelAndView();
				String message = "���̵� �Ǵ� �̸��� �Ǵ� ����ڹ�ȣ�� ��ġ���� �ʽ��ϴ�. �ٽ� �Է��� �ּ���";
				mav.addObject("message",message);
				mav.setViewName("/member/findPwForm");
				return mav;
			}
		}
	
}

