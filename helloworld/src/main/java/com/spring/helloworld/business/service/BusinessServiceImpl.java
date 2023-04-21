package com.spring.helloworld.business.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.helloworld.business.dao.BusinessDAO;
import com.spring.helloworld.business.vo.BusinessVO;

@Service("businessService")
@Transactional(propagation = Propagation.REQUIRED)
public class BusinessServiceImpl implements BusinessService{
	@Autowired
	private BusinessDAO businessDAO;
	//�α���
	@Override
	public BusinessVO login(Map loginMap) throws Exception {
		return businessDAO.login(loginMap);
	}
	//ȸ������
	@Override
	public void addMember(BusinessVO businessVO) throws Exception{
		businessDAO.insertNewMember(businessVO);
	}
	//���̵� �ߺ��˻�
	@Override
	public String overlapped(String id) throws Exception{
		return businessDAO.selectOverlappedID(id);
	}
	//ȸ��Ż��
	@Override
	public void unregister(String id) throws Exception {
		businessDAO.unregister(id);
	}
	//ȸ����ȸ(���̵�ã��)
	@Override
	public BusinessVO selectMember(Map selectMap)throws Exception{
		return businessDAO.selectMember(selectMap);
	}
	
	//ȸ����ȸ(��й�ȣ ã��)
	@Override
	public BusinessVO selectMemberID(String id) throws Exception{
		return businessDAO.selectMemberID(id);
	}
	//������ �������ȸ(��ü)
	@Override
	public List<BusinessVO> listBusiness()throws Exception{
		List<BusinessVO> busiList =  businessDAO.selectAllBusiness();
		return busiList;
	}
}
