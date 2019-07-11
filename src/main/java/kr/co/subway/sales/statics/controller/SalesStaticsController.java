package kr.co.subway.sales.statics.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import kr.co.subway.sales.statics.service.SalesStaticsService;
import kr.co.subway.sales.statics.vo.SalesStaticsGrpVo;

@Controller
@RequestMapping("/salesStatics")
public class SalesStaticsController {
	@Autowired
	private SalesStaticsService service;
	
	@RequestMapping("/goTotalSales.do")
	public String goTotalSales() {
		return "admin/salesStatics/totalSales";
	}

	//전체 매출 가져오기
	@ResponseBody
	@RequestMapping("/totalSales.do")
	public void totalSales(HttpServletResponse response) throws JsonIOException, IOException {
		ArrayList<SalesStaticsGrpVo> list = service.totalSales();
		/*Map<String,String> map = H
		for(int i=0;i<list.size();i++) {
			if()
			list.get(i).getOrderMonth()
		}*/
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		new Gson().toJson(list,response.getWriter());
	}
	
	//전체 매출페이지에서 선택한 월의 지점별 매출
	@ResponseBody
	@RequestMapping("/monthTotalSales.do")
	public void monthTotalSales(String month,HttpServletResponse response) throws JsonIOException, IOException {
		String orderMonth = month.substring(0,month.length()-1);
		System.out.println(orderMonth);
		ArrayList<SalesStaticsGrpVo> list = service.monthTotalSales(orderMonth);
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		new Gson().toJson(list,response.getWriter());
	}
	
	//지점별 매출통계 페이지로 가기
	@RequestMapping("/goBranchSales.do")
	public String goBranchSales() {
		return "admin/salesStatics/branchSales";
	}
	
	
	//매출 가져오기
	/*@RequestMapping("/totalSales.do")
	public ModelAndView totalSales(ModelAndView mav, HttpServletRequest request) {
		ArrayList<SalesStaticsGrpVo> list = service.getSales();
		if(!list.isEmpty()) {
			mav.addObject("salesList",list);
			mav.setViewName("admin/salesStatics/totalSales");
		}else {
			mav.setViewName("common/error");
		}
		return mav;
	}*/
}
