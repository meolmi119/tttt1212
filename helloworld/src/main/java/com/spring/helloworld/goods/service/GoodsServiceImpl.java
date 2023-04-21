package com.spring.helloworld.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.helloworld.goods.dao.GoodsDAO;
import com.spring.helloworld.goods.vo.GoodsAirApiVO;
import com.spring.helloworld.goods.vo.GoodsStayVO;
import com.spring.helloworld.goods.vo.ImageFileVO;



@Service("goodsService")
@Transactional(propagation = Propagation.REQUIRED)
public class GoodsServiceImpl implements GoodsService{
	@Autowired
	private GoodsDAO goodsDAO;
	
//	public Map<String, List<GoodsVO>> listGoodsStay() throws Exception {
//		Map<String, List<GoodsVO>> goodsStayMap = new HashMap<String, List<GoodsVO>>();
//		List<GoodsVO> goodsStayList = goodsDAO.selectGoodsStayList("goods)
//	}
	
	@Override
	public List goodsStayList(String keywordAddress,String keywordSort,String keywordName) throws DataAccessException {
		List goodsStayList = null;
		goodsStayList = goodsDAO.goodsStayList(keywordAddress,keywordSort,keywordName);
		return goodsStayList;
	}
	
	@Override
	public List goodsAirList() throws DataAccessException {
		List goodsAirList = null;
		goodsAirList = goodsDAO.goodsAirList();
		return goodsAirList;
	}
	
	//0317 ��
	public Map goodsStayDetail(String _goods_stay_id) throws Exception {
		Map goodsMap=new HashMap();
		GoodsStayVO goodsStayVO = goodsDAO.selectGoodsStayDetail(_goods_stay_id);
		goodsMap.put("goodsStayVO", goodsStayVO);
		List<ImageFileVO> imageList =goodsDAO.selectGoodsDetailImage(_goods_stay_id);
		goodsMap.put("imageList", imageList);
		return goodsMap;
	}
	//�̺�Ʈ ��ǰ �˻�(������)
	@Override
	public List<GoodsStayVO> listStay()throws Exception{
		List<GoodsStayVO> stayList =  goodsDAO.selectAllStay();
		return stayList;
	}
	
	//�̺�Ʈ ��ǰ �˻�(�����)
	@Override
	public List<GoodsStayVO> listStayBusiness(String busi_id)throws Exception{
		List<GoodsStayVO> stayList =  goodsDAO.selectAllStayBusiness(busi_id);
		return stayList;
	}
	//��ǰ ����(�����)
	@Override
	public void goodsUnregist(String goods_stay_id) throws Exception {
		goodsDAO.goodsUnregist(goods_stay_id);
		
	}
	//��ǰ ��ȸ(Ư�� ��ǰ)
	public GoodsStayVO selectGoods(String goods_stay_id) throws Exception{
		return goodsDAO.selectGoods(goods_stay_id);
	}
	
	//��ǰ ����(�����)
	@Override
	public void goodsEdit(Map goodsEditMap) throws Exception {
		goodsDAO.goodsEdit(goodsEditMap);
	}
	
	//�̺�Ʈ ��ǰ ���(�����)
	@Override
	public void goodsEventRegist(Map goodsEventMap) throws Exception{
		goodsDAO.goodsEventRegist(goodsEventMap); 
	}
	
	//airapi ������ 0328
//	@Override
//	public List<GoodsAirApiVO> listNewGoodsAirApi(Map airMap) throws Exception {
//		return goodsDAO.selectNewGoodsAirApiList(airMap);
//	}
}
