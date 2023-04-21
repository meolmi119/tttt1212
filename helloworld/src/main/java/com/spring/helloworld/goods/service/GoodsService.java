package com.spring.helloworld.goods.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.spring.helloworld.goods.vo.GoodsStayVO;

public interface GoodsService {
	public List goodsStayList(String keywordAddress,String keywordSort,String keywordName) throws DataAccessException;
	public List goodsAirList() throws DataAccessException;
	
	//0317
	public Map goodsStayDetail(String _goods_stay_id) throws Exception;
	//airapi ������ 0328
//	public List<GoodsAirApiVO> listNewGoodsAirApi(Map airMap) throws Exception;
	//�̺�Ʈ ��ǰ �˻�(������)
	public List<GoodsStayVO> listStay() throws Exception;
	//�̺�Ʈ ��ǰ �˻�(�����)
	public List<GoodsStayVO> listStayBusiness(String busi_id) throws Exception;
	//��ǰ ����(�����)
	public void goodsUnregist(String goods_stay_id) throws Exception;
	//��ǰ ��ȸ(Ư�� ��ǰ)
	public GoodsStayVO selectGoods(String goods_stay_id) throws Exception;
	//��ǰ ����(�����)
	public void goodsEdit(Map goodsEditMap) throws Exception;
	//�̺�Ʈ ���(�����)
	public void goodsEventRegist(Map goodsEventMap) throws Exception;
}
