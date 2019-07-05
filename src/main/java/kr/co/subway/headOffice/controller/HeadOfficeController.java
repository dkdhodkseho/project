package kr.co.subway.headOffice.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import kr.co.subway.headOffice.service.ApplyService;
import kr.co.subway.headOffice.service.MenuService;
import kr.co.subway.headOffice.vo.Apply;
import kr.co.subway.headOffice.vo.Menu;
import net.sf.json.JSONObject;

@Controller
public class HeadOfficeController {
	@Resource(name="applyService")
	private ApplyService applyService;
	@Resource(name="menuService")
	private MenuService menuservice;
	
	//메뉴 목록 출력 및 할인 적용 페이지 이동
	@RequestMapping(value="/promotionSelect.do")
	public ModelAndView promotion() {
		ArrayList<Menu> list = (ArrayList<Menu>) menuservice.menuList();
		ModelAndView mav = new ModelAndView();
		mav.addObject("list",list);
		mav.setViewName("headOffice/SelectPromotion");
		return mav;
	}
	//할인 및 할인율 수정
	@RequestMapping(value="/selectPromotion.do")
	public String selectPromotion(@RequestParam int menuNo, @RequestParam String menuName, @RequestParam int menuBasePrice, @RequestParam int menuDiscntPrice, @RequestParam double menuDiscntRate2) {
		//정수로 변환
		int menuDiscntRate = (int)Math.floor(100-(menuDiscntRate2*100));
		//index라 0부터 오기 때문에 +1
		Menu menu = new Menu(menuNo+1, menuName, menuBasePrice, menuDiscntPrice, menuDiscntRate);
		int result = menuservice.updateMenu(menu);
		System.out.println(menuName+" : "+menuBasePrice+"원");
		System.out.println("할인율 : "+(int)Math.floor(100-(menuDiscntRate2*100))+"%");
		System.out.println("할인후 가격 : "+(int)Math.floor(menuBasePrice*menuDiscntRate2)+"원");
		String view = "";
		if(result>0) {
			view = "headOffice/SelectPromotion";
		}else {
			view = "redirect:/index.jsp";
		}
		return view;
	}
	//가맹점 신청목록
	@RequestMapping(value="/managerApply.do")
	public ModelAndView applyList() {
		ArrayList<Apply> list = (ArrayList<Apply>) applyService.applyList();
		ModelAndView mav = new ModelAndView();
		if(!list.isEmpty()) {
			mav.addObject("list",list);
			mav.setViewName("headOffice/managerApply");
		}else {
			mav.setViewName("redirect:/");
		}
		return mav;
	}
	//가맹점 신청 거절 ajax로 처리
	@ResponseBody
	@RequestMapping(value="/apply.do",produces="text/html;charset=utf-8")
	public String applyManager(@RequestParam String applyName, @RequestParam int applyStatus) {
		JSONObject obj = new JSONObject();
		if(applyName!=null) {
			if(applyStatus == 2) {
				//거절(status 2일 때)
				applyService.rejectManagerUpdate(applyName,applyStatus);
				obj.put("result", 0);
			}
//			else{
				//승인(status 1일 때)
//				applyService.applyManagerUpdate(applyName,applyStatus);
//				obj.put("result", 0);
//			}
		}else {
			obj.put("result", 1);
		}
		return new Gson().toJson(obj);
	}	
	//가맹점 신청 글 상세보기 
	@RequestMapping(value="/applyView.do")
	public String applyView(@RequestParam int applyNo,Model model) {
		Apply ap = applyService.applyView(applyNo);
		if(ap != null) {
			model.addAttribute("ap",ap);
		}
		return "headOffice/applyView";
	}
	
	// 가맹신청/문의 페이지 이동
	@RequestMapping(value="/applyPage.do")
	public String applyPage() {
		return "headOffice/applyWrite";
	}
	
	//가맹신청/문의 등록
	@RequestMapping(value="/insertApply.do",method=RequestMethod.POST)
	public String insertApply(HttpServletRequest request, Apply applyVo, @RequestParam MultipartFile applyfilename) {
		System.out.println(applyfilename);
		String savePath = request.getSession().getServletContext().getRealPath("/resources/upload");
		
		String originName = applyfilename.getOriginalFilename();
		
		String onlyFileName = originName.substring(0, originName.indexOf('.'));
		
		String extension = originName.substring(originName.indexOf('.'));
		
		String filePath = onlyFileName + "_" + "1" + extension;
		
		String fullPath = savePath+"/"+filePath;
		
		applyVo.setApplyFilename(originName);
		applyVo.setApplyFilepath(filePath);
		
		int result = applyService.insertApply(applyVo);
		
		if(!applyfilename.isEmpty()) {
			try {
				byte [] bytes = applyfilename.getBytes();
				File f = new File(fullPath);
				FileOutputStream fos = new FileOutputStream(f);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				bos.write(bytes);
				bos.close();
				System.out.println("파일업로드 성공했어!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(result>0) {
			return "headOffice/insertSuccess";
		}else {
			return "headOffice/insertFailed";
		}
	}
	
	// 매장찾기페이지이동
	@RequestMapping(value="/shopSearch.do")
	public String shopSearchPage() {
		return "map/map";
	}
	
	
	
	
	

	
}