package com.spring.helloworld.goods.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.spring.helloworld.goods.vo.GoodsAirApiVO;
import com.spring.helloworld.goods.vo.GoodsStayVO;
import com.spring.helloworld.goods.vo.ImageFileVO;

public interface GoodsDAO {
	public List goodsStayList(String keywordAddress,String keywordSort,String keywordName) throws DataAccessException;
	public List goodsAirList() throws DataAccessException;
	//0317
	public GoodsStayVO selectGoodsStayDetail(String goods_id) throws DataAccessException;
	public List<ImageFileVO> selectGoodsDetailImage(String goods_id) throws DataAccessException;
	// airapi ������ 0328
	public List<GoodsAirApiVO>selectNewGoodsAirApiList(Map airMap) throws DataAccessException;
	//�̺�Ʈ ��ǰ �˻�(������)
	public List selectAllStay() throws DataAccessException;
	//�̺�Ʈ ��ǰ �˻�(�����)
	public List selectAllStayBusiness(String busi_id) throws DataAccessException;
	//��ǰ ����(�����)
	public void goodsUnregist(String goods_stay_id) throws DataAccessException;
	//��ǰ ��ȸ(Ư�� ��ǰ)
	public GoodsStayVO selectGoods(String goods_stay_id) throws DataAccessException;
	//��ǰ ���� (�����)
	public void goodsEdit(Map goodsEditMap) throws DataAccessException;
	//�̺�Ʈ ���(�����)
	public void goodsEventRegist(Map goodsEventMap) throws DataAccessException;
}
