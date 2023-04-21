package com.spring.helloworld.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.helloworld.member.dao.MemberDAO;
import com.spring.helloworld.member.vo.MemberVO;

@Service("memberService")
@Transactional(propagation = Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDAO memberDAO;
	
	//�α���
	@Override
	public MemberVO login(Map loginMap) throws Exception {
		return memberDAO.login(loginMap);
	}
	
	//ȸ������
	@Override
	public void addMember(MemberVO memberVO) throws Exception{
		memberDAO.insertNewMember(memberVO);
	}
	
	//���̵� �ߺ� Ȯ��
	@Override
	public String overlapped(String id) throws Exception{
		return memberDAO.selectOverlappedID(id);
	}
	
	//ȸ��Ż��
	@Override
	public void unregister(String id) throws Exception {
		memberDAO.unregister(id);
		
	}
	
	//ȸ����ȸ(���̵� ã��)
	@Override
	public MemberVO selectMember(Map selectMap)throws Exception{
		return memberDAO.selectMember(selectMap);
	}
	
	//ȸ����ȸ(��й�ȣ ã��)
	@Override
	public MemberVO selectMemberID(String id) throws Exception{
		return memberDAO.selectMemberID(id);
	}
	//������ ȸ����ȸ(��ü)
	@Override
	public List<MemberVO> listMember()throws Exception{
		List<MemberVO> memList =  memberDAO.selectAllMember();
		return memList;
	}
	//������ ȸ��Ż��
	@Override
	public int unregisterAdmin(String id) throws Exception {
		int result = memberDAO.unregisterAdmin(id);
		return result;
		
	}
	
}