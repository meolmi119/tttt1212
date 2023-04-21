package com.spring.helloworld.kakaoLogin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.helloworld.kakaoLogin.service.KakaoAPI;
import com.spring.helloworld.member.service.MemberService;
import com.spring.helloworld.member.vo.MemberVO;
import com.spring.helloworld.mypage.service.MyPageService;


@Controller
public class KakaoController {
	@Autowired
	private SqlSession sqlSession;
	
    @Autowired
    private KakaoAPI kakao;
    
    @Autowired
    private MemberVO memberVO;
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private MyPageService myPageService;
    
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 20;
    //�� �������� SecureRandom Ŭ������ ����Ͽ� CHARACTERS ���ڿ����� �������� ���ڸ� �����ϰ�, StringBuilder�� �߰�
    //charAt() ���ڿ����� �ش� �ε����� �����ϴ� ���ڸ� ��ȯ
    //���� ��й�ȣ ����
    public static String generateRandomString() {
	    SecureRandom random = new SecureRandom();
	    StringBuilder sb = new StringBuilder(LENGTH);
	    for (int i = 0; i < LENGTH; i++) {
	        int randomIndex = random.nextInt(CHARACTERS.length());
	        char randomChar = CHARACTERS.charAt(randomIndex);
	        sb.append(randomChar);
	    }
	    return sb.toString();
    }
    
    @RequestMapping(value="/")
    public String index() {
        return "index";
    }

	@RequestMapping(value= "/index.do", method=RequestMethod.GET)
	private ModelAndView indexs(@RequestParam(value="result", required=false) String result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.addObject("result", result);
		mav.setViewName(viewName);
		return mav;
	}
	//īī�� �α���
	@RequestMapping(value = "/member/kakaoLogin.do")
	public ModelAndView kakaoLogin(@RequestParam("code") String code, HttpSession session) throws Exception{
	    String access_Token = kakao.getAccessToken(code);
	    HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
	    System.out.println("login Controller : " + userInfo);
	    String info = "0";
	    String ran = generateRandomString();
	    String email = (String) userInfo.get("email");
	    String[] emailParts = email.split("@");
	    // @ ���� �κ�
	    String email1 = emailParts[0];
	    // @ ���� �κ�
	    String email2 = emailParts[1]; 
	    String overlappedID = memberService.overlapped(email);
	    ModelAndView mav = new ModelAndView("redirect:/main.do");
	    // ���� ���̵� �������� �ʴ°�� = false
	    if(overlappedID.equals("false")) {
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("mem_id", email);
	    paramMap.put("mem_pw", ran);
	    paramMap.put("mem_name", email1);
	    paramMap.put("mem_rrn", info);
	    paramMap.put("mem_tel1", info);
	    paramMap.put("mem_tel2", info);
	    paramMap.put("mem_tel3", info);
	    paramMap.put("mem_email1", email1);
	    paramMap.put("mem_email2", email2);
	    paramMap.put("mem_roadAddress", info);
	    paramMap.put("mem_jibunAddress", info);
	    paramMap.put("mem_namujiAddress", info);
	    paramMap.put("mem_zipcode", info);
	    // ������ �����͸� �̿��Ͽ� INSERT ���� ����
	    sqlSession.insert("mapper.member.insertKakao", paramMap);
	    session.setAttribute("userId", userInfo.get("email"));
        session.setAttribute("access_Token", access_Token);
        memberVO = memberService.selectMemberID(email);
		session.setAttribute("memberInfo", memberVO);
        session.setAttribute("isLogOn", true);
        mav.setViewName("redirect:/member/registKakaoForm.do");
    	}else {
    		memberVO = memberService.selectMemberID(email);
    		session.setAttribute("memberInfo", memberVO);
    		session.setAttribute("isLogOn", true);
    		session.setAttribute("userId", userInfo.get("email"));
	        session.setAttribute("access_Token", access_Token); 
    	}
	    
	    System.out.println("controller access_token : " + access_Token);
	    return mav;
	    
	}
	
	//īī�� ���� �α���
	@RequestMapping(value="/member/kakaoFirstLogin.do" ,method = RequestMethod.POST)
	public ModelAndView kakaoFirstLogin(@RequestParam Map<String, String>memberMapKakao,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session=request.getSession();
		memberVO=(MemberVO)session.getAttribute("memberInfo");
		String mem_id=memberVO.getMem_id();
		memberMapKakao.put("mem_id",mem_id);
		
		//������ ������ ���ǿ� ����
		memberVO=(MemberVO)myPageService.modifyKakaoInfo(memberMapKakao);
		session.removeAttribute("memberInfo");
		session.setAttribute("memberInfo", memberVO);
		mav.setViewName("redirect:/main.do");
		return mav;
	}
	
	//īī�� �α׾ƿ�
	@RequestMapping(value = "/member/kakaoLogout.do")
	public String kakaoLogout(HttpSession session) throws Exception{
	    // ���� ������� Access Token ��������
	    String access_Token = (String)session.getAttribute("access_Token");

	    // īī�� API�� ����Ͽ� �α׾ƿ� ��û ������
	    //HttpURLConnection = HTTP ���������� ����Ͽ� �� ������ ���
	    String requestUrl = "https://kapi.kakao.com/v1/user/logout";
	    HttpURLConnection conn = null;
	    try {
	        URL url = new URL(requestUrl);
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        //Authorization ���� ���� ����, Bearer ���� ��ū ���� , access_Token Bearer�� �־��� ��ū ��
	        conn.setRequestProperty("Authorization", "Bearer " + access_Token);

	        int responseCode = conn.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            System.out.println("īī�� �α׾ƿ� ����");
	        } else {
	            System.out.println("īī�� �α׾ƿ� ����");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        conn.disconnect();
	    }

	    // ���ǿ��� �α��� ���� ����
	    session.removeAttribute("memberInfo");
	    session.removeAttribute("access_Token");
	    session.removeAttribute("userId");
	    session.setAttribute("isLogOn", false);
	    

	    return "/member/loginForm";
	}
	
	//īī�� ȸ��Ż��
	@RequestMapping(value="/member/unregisterKakao.do" ,method = RequestMethod.POST)
	public void unregisterKakao(@RequestParam("mem_id") String mem_id,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();
		session.setAttribute("isLogOn", false);
		session.removeAttribute("memberInfo");
		kakaoLogout(session);

		memberService.unregister(mem_id);
		
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("location.href='" + request.getContextPath() + "/member/unregistEndForm.do';");
		out.println("</script>");
		out.flush();	
	}


}
