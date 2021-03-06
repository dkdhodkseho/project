package kr.co.subway.customerOrder.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.subway.customerOrder.dao.CusOrderDao;
import kr.co.subway.customerOrder.vo.Bucket;
import kr.co.subway.customerOrder.vo.CusOrder;
import kr.co.subway.customerOrder.vo.CusOrderPageBound;
import kr.co.subway.customerOrder.vo.CusOrderPageData;
import kr.co.subway.customerOrder.vo.MyMenu;
import kr.co.subway.customerOrder.vo.UpdateQuantity;
import kr.co.subway.ingreManage.vo.IngreVo;
import kr.co.subway.manager.vo.Mgr;

@Service("cusOrderService")
public class CusOrderService {
	
	@Autowired
	private CusOrderDao cusOrderDao;
	
	public ArrayList<IngreVo> ingreSelectAll(){
		return (ArrayList<IngreVo>)cusOrderDao.ingreSelectAll();
	}
	 
	@Transactional(value="transactionManager")
	public int insertCusOrder(CusOrder cuso) {
		int result = cusOrderDao.insertCusOrder(cuso);
		return result;
		
	}

	public ArrayList<Bucket> loadBucketList(Bucket bVO) {
		List<Bucket> list = cusOrderDao.loadBucketList(bVO);
		return (ArrayList<Bucket>)list;
	}

	public IngreVo selectCostMain(IngreVo ingre) {
		IngreVo mainIngre = cusOrderDao.selectCostMain(ingre);
		return mainIngre;
	}
	
	@Transactional
	public int tempOrderInsert(Bucket b) {
		return cusOrderDao.tempOrderInsert(b); 
	}
	
	public int tempOrderSelect() {
		return cusOrderDao.tempOrderSelect(); 
	}
	
	@Transactional
	public int tempOrderDelete(int idx) {
		return cusOrderDao.tempOrderDelete(idx); 
	}
	
	public Mgr mgrSelectOne(int mgrNo) {
		return cusOrderDao.mgrSelectOne(mgrNo); 
	}
	
	@Transactional
	public int updateQuantity(UpdateQuantity uq) {
		return cusOrderDao.updateQuantity(uq);
	}

	@Transactional
	public int updateOrder(Bucket b) {
		return cusOrderDao.updateOrder(b);
		
	}
	
	@Transactional
	public int insertMyMenu(MyMenu mm) {
		return cusOrderDao.insertMyMenu(mm);
	}
	
	//주문 목록 가져오기
	public CusOrderPageData cusOrderList(int currentPage1,Mgr mgr){
		String pageNavi = "";
		int numPerPage = 10;
		int pageNaviSize = 5;
		int totalCount = cusOrderDao.totalCount(mgr);
		int totalPage = (totalCount%numPerPage==0)?(totalCount/numPerPage):(totalCount/numPerPage)+1;
		int start = (currentPage1-1)*numPerPage+1;
		int end = currentPage1*numPerPage;
		CusOrderPageBound pb = new CusOrderPageBound(start,end,null,null);
		ArrayList<CusOrder> list = (ArrayList<CusOrder>) cusOrderDao.cusOrderList(pb,mgr);
		int pageNo = ((currentPage1-1)/pageNaviSize)*pageNaviSize+1;
		if(currentPage1 != 1) {
			pageNavi += "<a href='/cusOrderList.do?currentPage="+(currentPage1-1)+"'>이전</a>&nbsp;";
		}
		int i = 1;
		while(!(i++>pageNaviSize||pageNo>totalPage)) {
			if(currentPage1 == pageNo) {
				pageNavi += "&nbsp;<span style='color:black;font-size:20px;'>"+pageNo+"</span>&nbsp;";
			}else {
				pageNavi += "&nbsp;<a href='/cusOrderList.do?currentPage="+pageNo+"'>"+pageNo+"<a/>&nbsp;";
			}
			pageNo++;
		}
		if(currentPage1 < totalPage) {
			pageNavi += "&nbsp;<a href='/cusOrderList.do?currentPage="+(currentPage1+1)+"'>다음</a>";
		}
		return new CusOrderPageData(list,pageNavi,totalCount);
	}
	
	//주문 상태 변경
	@Transactional
	public int orderStateUpdate(CusOrder cuso) {
		int result = cusOrderDao.orderStateUpdate(cuso);
		return result;
	}
	//체크박스의 값에 맞는 리스트 가져오기
	public CusOrderPageData checkedCusoOrderList(int currentPage1,String cusoMemberNo,Mgr mgr){
		String pageNavi = "";
		int numPerPage = 10;
		int pageNaviSize = 5;
		int totalCount = cusOrderDao.checkedTotalCount(cusoMemberNo,mgr);
		int totalPage = (totalCount%numPerPage==0)?(totalCount/numPerPage):(totalCount/numPerPage)+1;
		int start = (currentPage1-1)*numPerPage+1;
		int end = currentPage1*numPerPage;
		CusOrderPageBound pb = new CusOrderPageBound(start,end,cusoMemberNo,null);
		ArrayList<CusOrder> list = (ArrayList<CusOrder>) cusOrderDao.checkedCusoOrderList(pb,mgr);
		int pageNo = ((currentPage1-1)/pageNaviSize)*pageNaviSize+1;
		if(currentPage1 != 1) {
			pageNavi += "<a href='/checkedCusoOrderList.do?currentPage="+(currentPage1-1)+"&cusoMemberNo="+cusoMemberNo+"'>이전</a>&nbsp;";
		}
		int i = 1;
		while(!(i++>pageNaviSize||pageNo>totalPage)) {
			if(currentPage1 == pageNo) {
				pageNavi += "&nbsp;<span style='color:black;font-size:20px;'>"+pageNo+"</span>&nbsp;";
			}else {
				pageNavi += "&nbsp;<a href='/checkedCusoOrderList.do?currentPage="+pageNo+"&cusoMemberNo="+cusoMemberNo+"'>"+pageNo+"<a/>&nbsp;";
			}
			pageNo++;
		}
		if(currentPage1 < totalPage) {
			pageNavi += "&nbsp;<a href='/checkedCusoOrderList.do?currentPage="+(currentPage1+1)+"&cusoMemberNo="+cusoMemberNo+"'>다음</a>";
		}
		return new CusOrderPageData(list,pageNavi,totalCount);
	}
	//검색어와 일치하는 리스트
	public CusOrderPageData orderSearchKeyword(int currentPage1,String keyword,Mgr mgr){
		String pageNavi = "";
		int numPerPage = 10;
		int pageNaviSize = 5;
		int totalCount = cusOrderDao.searchKeywordTotalCount(keyword,mgr);
		int totalPage = (totalCount%numPerPage==0)?(totalCount/numPerPage):(totalCount/numPerPage)+1;
		int start = (currentPage1-1)*numPerPage+1;
		int end = currentPage1*numPerPage;
		CusOrderPageBound pb = new CusOrderPageBound(start,end,null,keyword);
		ArrayList<CusOrder> list = (ArrayList<CusOrder>) cusOrderDao.orderSearchKeyword(pb);
		int pageNo = ((currentPage1-1)/pageNaviSize)*pageNaviSize+1;
		if(currentPage1 != 1) {
			pageNavi += "<a href='/orderSearchKeyword.do?currentPage="+(currentPage1-1)+"&keyword="+keyword+"'>이전</a>&nbsp;";
		}
		int i = 1;
		while(!(i++>pageNaviSize||pageNo>totalPage)) {
			if(currentPage1 == pageNo) {
				pageNavi += "&nbsp;<span style='color:black;font-size:20px;'>"+pageNo+"</span>&nbsp;";
			}else {
				pageNavi += "&nbsp;<a href='/orderSearchKeyword.do?currentPage="+pageNo+"&keyword="+keyword+"'>"+pageNo+"<a/>&nbsp;";
			}
			pageNo++;
		}
		if(currentPage1 < totalPage) {
			pageNavi += "&nbsp;<a href='/orderSearchKeyword.do?currentPage="+(currentPage1+1)+"&keyword="+keyword+"'>다음</a>";
		}
		return new CusOrderPageData(list,pageNavi,totalCount);
	}

	public ArrayList<CusOrder> loadOrderList(String customerIdx) {
		return (ArrayList<CusOrder>)cusOrderDao.loadOrderList(customerIdx);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Bucket> loadMenuList(ArrayList<MyMenu> menuList) {
		return (ArrayList<Bucket>) cusOrderDao.loadMenuList(menuList);
	}

	public ArrayList<MyMenu> selectMyMenuList(String customerNo) {
		return (ArrayList<MyMenu>)cusOrderDao.selectMyMenuList(customerNo);
	}

	public CusOrder cusOrderInfo(String no) {
		CusOrder cusOrder = cusOrderDao.cusOrderInfo(no);
		cusOrder.setBucketList((ArrayList<Bucket>)cusOrderDao.cusOrderItem(no));
		return cusOrder;
	}
	
	public int myMemuDelete(int idx) {
		return cusOrderDao.myMenuDelete(idx);
	}

	public boolean checkMM(int bucIdx) {
		boolean result = false;
		MyMenu mm = cusOrderDao.selectOneMenu(bucIdx);
		if( (mm != null) && (mm.getMmBucIdx() == bucIdx) ) {
			result = true;
		}
		return result;
	}

	public int hideFromBucketList(int idx) {
		return cusOrderDao.hideFromBList(idx);
	}

	//주문 하나 찾기(버킷 아님)
	public CusOrder selectOneCusOrder(String cusoOrderNo) {
		return cusOrderDao.selectOneCusOrder(cusoOrderNo);
	}

	public int cancelOrder(String cusOrderNo) {
		return cusOrderDao.cancelOrder(cusOrderNo);
	}

	public ArrayList<Bucket> loadAllBucketList(Bucket bVo) {
		List<Bucket> list = cusOrderDao.loadAllBucketList(bVo);
		return (ArrayList<Bucket>)list;
	}

	public int loadMain15Cost(IngreVo iv) {
		return cusOrderDao.loadMain15Cost(iv);
	}

	public int loadMain30Cost(IngreVo iv) {
		return cusOrderDao.loadMain30Cost(iv);
	}

}
