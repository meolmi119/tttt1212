package com.spring.helloworld.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.helloworld.business.vo.BusinessVO;

@Repository("businessDAO")
public class BusinessDAOImpl implements BusinessDAO{
	@Autowired
	private SqlSession sqlSession;	
	//�α���
	@Override
	public BusinessVO login(Map loginMap) throws DataAccessException{
		BusinessVO member=(BusinessVO)sqlSession.selectOne("mapper.business.busilogin",loginMap);
	   return member;
	}
	//ȸ������
	@Override
	public void insertNewMember(BusinessVO businessVO) throws DataAccessException{
		sqlSession.insert("mapper.business.insertNewBusi",businessVO);
	}
	//���̵� �ߺ��˻�
	@Override
	public String selectOverlappedID(String id) throws DataAccessException {
		String result =  sqlSession.selectOne("mapper.business.selectOverlappedbusiID",id);
		return result;
	}
	//ȸ��Ż��
	@Override
	public void unregister(String id) throws DataAccessException {
		sqlSession.delete("mapper.business.unregisterBusiness",id);
	}
	//ȸ����ȸ(���̵�ã��)
	@Override
	public BusinessVO selectMember(Map selectMap) throws DataAccessException{
		BusinessVO memberInfo = (BusinessVO)sqlSession.selectOne("mapper.business.selectMember",selectMap);
		return memberInfo;
	}
	//ȸ����ȸ(��й�ȣã��)
	@Override
	public BusinessVO selectMemberID(String id) throws DataAccessException {
		BusinessVO result =  sqlSession.selectOne("mapper.business.selectMemberID",id);
		return result;
	}
	//������ �������ȸ(��ü)
		@Override
		public List selectAllBusiness() throws DataAccessException{
			List<BusinessVO> busiList = sqlSession.selectList("mapper.business.selectAllBusiness");
			return busiList;
		}

}
