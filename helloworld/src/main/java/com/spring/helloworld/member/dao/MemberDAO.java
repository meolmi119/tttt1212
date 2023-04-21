package com.spring.helloworld.member.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.spring.helloworld.member.vo.MemberVO;

public interface MemberDAO {
	
	//�α���
	public MemberVO login(Map loginMap) throws DataAccessException;
	
	//ȸ������
	public void insertNewMember(MemberVO memberVO) throws DataAccessException;
	
	//���̵� �ߺ��˻�
	public String selectOverlappedID(String id) throws DataAccessException;
	
	//Ż��
	public void unregister(String id) throws DataAccessException;
	
	//ȸ����ȸ(���̵� ã��)
	public MemberVO selectMember(Map selectMap)throws DataAccessException;
	
	//ȸ����ȸ(��й�ȣ ã��)
	public MemberVO selectMemberID(String id) throws DataAccessException;
	
	//������ ȸ����ȸ(��ü)
	public List selectAllMember() throws DataAccessException;
	
	//������ Ż��
	public int unregisterAdmin(String id) throws DataAccessException;
}
