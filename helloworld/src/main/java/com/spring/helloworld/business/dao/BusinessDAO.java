package com.spring.helloworld.business.dao;

import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import com.spring.helloworld.business.vo.BusinessVO;
import com.spring.helloworld.member.vo.MemberVO;

public interface BusinessDAO {
	
	//�α���
	public BusinessVO login(Map loginMap) throws DataAccessException;
	
	//����ڰ���
	public void insertNewMember(BusinessVO biBusinessVO) throws DataAccessException;
	
	//���̵� �ߺ��˻�
	public String selectOverlappedID(String id) throws DataAccessException;
	
	//Ż��
	public void unregister(String id) throws DataAccessException;
	
	//�������ȸ(���̵� ã��)
	public BusinessVO selectMember(Map selectMap)throws DataAccessException;
	
	//�������ȸ(��й�ȣ ã��)
	public BusinessVO selectMemberID(String id) throws DataAccessException;
	
	//������ �������ȸ(��ü)
	public List selectAllBusiness() throws DataAccessException;
}
