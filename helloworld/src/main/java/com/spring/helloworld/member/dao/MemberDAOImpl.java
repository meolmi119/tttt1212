package com.spring.helloworld.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.helloworld.member.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO{
	@Autowired
	private SqlSession sqlSession;
	//�α���
	@Override
	public MemberVO login(Map loginMap) throws DataAccessException {
		MemberVO member = (MemberVO)sqlSession.selectOne("mapper.member.login",loginMap);
		return member;
	}
	
	//ȸ������
	//DataAccessException ������ �׼��� �������� ���� ��ó
	@Override
	public void insertNewMember(MemberVO memberVO) throws DataAccessException{
		sqlSession.insert("mapper.member.insertNewMember",memberVO);
	}
	
	//���̵� �ߺ� Ȯ��
	@Override
	public String selectOverlappedID(String id) throws DataAccessException {
		String result =  sqlSession.selectOne("mapper.member.selectOverlappedID",id);
		return result;
	}
	
	//ȸ��Ż��
	@Override
	public void unregister(String id) throws DataAccessException {
		sqlSession.delete("mapper.member.unregisterMember",id);
		
	}
	//ȸ����ȸ(���̵�ã��)
	public MemberVO selectMember(Map selectMap)throws DataAccessException{
		MemberVO memberInfo = (MemberVO)sqlSession.selectOne("mapper.member.selectMember",selectMap);
		return memberInfo;
	}
	
	//ȸ����ȸ(��й�ȣã��)
	@Override
	public MemberVO selectMemberID(String id) throws DataAccessException {
		MemberVO result =  sqlSession.selectOne("mapper.member.selectMemberID",id);
		return result;
	}
	
	//������ ȸ����ȸ(��ü)
	@Override
	public List selectAllMember() throws DataAccessException{
		List<MemberVO> memList = sqlSession.selectList("mapper.member.selectAllMember");
		return memList;
	}
	//ȸ��Ż��
	@Override
	public int unregisterAdmin(String id) throws DataAccessException {
		int result = sqlSession.delete("mapper.member.unregisterMember",id);
		return result;
	}
	
}
