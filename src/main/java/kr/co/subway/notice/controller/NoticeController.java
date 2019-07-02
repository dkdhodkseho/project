package kr.co.subway.notice.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.co.subway.notice.service.NoticeService;
import kr.co.subway.notice.vo.Notice;
import kr.co.subway.notice.vo.PageNaviData;
import kr.co.subway.notice.vo.Qna;

@Controller
public class NoticeController {
	@Autowired
	@Qualifier(value = "noticeService")
	private NoticeService noticeService;

	@RequestMapping("/notice.do")
	public ModelAndView noticeSelectAll(@RequestParam int currentPage) {
		PageNaviData pd = noticeService.noticeSelectPaging(currentPage);
		ArrayList<Notice> noticeList = pd.getNoticeList();
		String pageNavi = pd.getPageNavi();
		ModelAndView mav = new ModelAndView();
		if (!noticeList.isEmpty()) {
			mav.addObject("noticeList", noticeList); // view에서 사용할 객체 추가
			mav.addObject("pageNavi", pageNavi);
			mav.setViewName("notice/notice");
		} else {
			mav.setViewName("common/error");
		}

		return mav;
	}

	@RequestMapping("/noticeOne.do")
	public ModelAndView noticeSelectOne(@RequestParam int noticeNo) {
		ArrayList<Notice> noticeList = noticeService.noticeSelectAll();
		ModelAndView mav = new ModelAndView();
		 if(!noticeList.isEmpty()) {
		      mav.addObject("noticeNo",noticeNo);
	          mav.addObject("noticeList",noticeList);   //view에서 사용할 객체 추가
	          mav.setViewName("notice/noticeOne");
	       }else {
	          mav.setViewName("common/error");
	       }
		
		return mav;
	}
	
	@RequestMapping("/moveNoticeInsert.do")
	public String moveNoticeInsert() {
		return "/notice/noticeInsert";
	}
	
	@RequestMapping(value="/noticeInsert.do", method=RequestMethod.POST)
	public String noticeInsert(HttpServletRequest request, @RequestParam MultipartFile fileName, Notice n ) {
		String fullPath = "";
		if(!fileName.isEmpty()) {
			String savePath = request.getSession().getServletContext().getRealPath("/resources/upload");
			String originName = fileName.getOriginalFilename();
			String onlyFileName = originName.substring(0, originName.indexOf('.'));
			String extension = originName.substring(originName.indexOf('.'));
			String filePath = onlyFileName+"_"+"1"+extension;
			fullPath = savePath+"/"+filePath;
			n.setFilename(originName);
			n.setFilepath(filePath);
		}else {
			n.setFilename("");
			n.setFilepath("");
		}
		int result = noticeService.noticeInsert(n);
		
		if(result>0) {
			System.out.println("작성 성공");
		}else {
			System.out.println("작성 실패");
		}
		if(!fileName.isEmpty()) {
			try {
				byte[] bytes = fileName.getBytes();
				File f = new File(fullPath);
				FileOutputStream fos = new FileOutputStream(f);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				bos.write(bytes);
				bos.close();
				System.out.println("파일 업로드 성공");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "redirect:/notice.do?currentPage=1";
	}
	
	@RequestMapping("/moveNoticeUpdate.do")
	public ModelAndView moveNoticeUpdate(@RequestParam int noticeNo) {
		ArrayList<Notice> noticeList = noticeService.noticeSelectAll();
		ModelAndView mav = new ModelAndView();
		 if(!noticeList.isEmpty()) {
		      mav.addObject("noticeNo",noticeNo);
	          mav.addObject("noticeList",noticeList);   //view에서 사용할 객체 추가
	          mav.setViewName("notice/noticeUpdate");
	       }else {
	          mav.setViewName("common/error");
	       }
		return mav;
	}
	
	@RequestMapping(value="/noticeUpdate.do", method=RequestMethod.POST)
	public String noticeUpdate(HttpServletRequest request, @RequestParam MultipartFile fileName, Notice n,@RequestParam String fileStatus) {
		String fullPath = "";
		if(!fileName.isEmpty()) {
			String savePath = request.getSession().getServletContext().getRealPath("/resources/upload");
			String originName = fileName.getOriginalFilename();
			String onlyFileName = originName.substring(0, originName.indexOf('.'));
			String extension = originName.substring(originName.indexOf('.'));
			String filePath = onlyFileName+"_"+"1"+extension;
			fullPath = savePath+"/"+filePath;
			n.setFilename(originName);
			n.setFilepath(filePath);
		}
		if(fileStatus.equals("1")) {
			n.setFilename("");
			n.setFilepath("");
		}
			
		int result = noticeService.noticeUpdate(n);
		
		if(result>0) {
			System.out.println("수정 성공");
		}else {
			System.out.println("수정 실패");
		}
		if(!fileName.isEmpty()) {
			try {
				byte[] bytes = fileName.getBytes();
				File f = new File(fullPath);
				FileOutputStream fos = new FileOutputStream(f);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				bos.write(bytes);
				bos.close();
				System.out.println("파일 업로드 성공");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "redirect:/notice.do?currentPage=1";
	}
	
	@RequestMapping("/noticeDelete.do")
	public String noticeDelete(@RequestParam int noticeNo) {
		int result = noticeService.noticeDelete(noticeNo);
		
		if(result>0) {
			System.out.println("삭제 성공");
		}else {
			System.out.println("삭제 실패");
		}
		return "redirect:/notice.do?currentPage=1";
	}
	///////////////////////////////////////////////////////////////////////////////////////
	//여기서 부터 QNA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@RequestMapping("/qna.do")
	public ModelAndView qnaSelectAll(@RequestParam int currentPage) {
		PageNaviData pd = noticeService.qnaSelectPaging(currentPage);
		ArrayList<Qna> qnaList = pd.getQnaList();
		String pageNavi = pd.getPageNavi();
		ModelAndView mav = new ModelAndView();
		if (!qnaList.isEmpty()) {
			mav.addObject("qnaList", qnaList); // view에서 사용할 객체 추가
			mav.addObject("pageNavi", pageNavi);
			mav.setViewName("qna/qna");
		} else {
			mav.setViewName("common/error");
		}

		return mav;
	}

	@RequestMapping("/qnaOne.do")
	public ModelAndView qnaSelectOne(@RequestParam int qnaNo) {
		ArrayList<Qna> qnaList = noticeService.qnaSelectAll();
		ModelAndView mav = new ModelAndView();
		 if(!qnaList.isEmpty()) {
		      mav.addObject("qnaNo",qnaNo);
	          mav.addObject("qnaList",qnaList);   //view에서 사용할 객체 추가
	          mav.setViewName("qna/qnaOne");
	       }else {
	          mav.setViewName("common/error");
	       }
		
		return mav;
	}
	
	@RequestMapping("/moveQnaInsert.do")
	public String moveQnaInsert() {
		return "/qna/qnaInsert";
	}
	
	@RequestMapping(value="/qnaInsert.do", method=RequestMethod.POST)
	public String qnaInsert(HttpServletRequest request, @RequestParam MultipartFile fileName, Qna q ) {
		String fullPath = "";
		if(!fileName.isEmpty()) {
			String savePath = request.getSession().getServletContext().getRealPath("/resources/upload");
			String originName = fileName.getOriginalFilename();
			String onlyFileName = originName.substring(0, originName.indexOf('.'));
			String extension = originName.substring(originName.indexOf('.'));
			String filePath = onlyFileName+"_"+"1"+extension;
			fullPath = savePath+"/"+filePath;
			q.setFilename(originName);
			q.setFilepath(filePath);
		}else {
			q.setFilename("");
			q.setFilepath("");
		}
		int result = noticeService.qnaInsert(q);
		
		if(result>0) {
			System.out.println("작성 성공");
		}else {
			System.out.println("작성 실패");
		}
		if(!fileName.isEmpty()) {
			try {
				byte[] bytes = fileName.getBytes();
				File f = new File(fullPath);
				FileOutputStream fos = new FileOutputStream(f);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				bos.write(bytes);
				bos.close();
				System.out.println("파일 업로드 성공");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "redirect:/qna.do?currentPage=1";
	}
	
	@RequestMapping("/moveQnaUpdate.do")
	public ModelAndView moveQnaUpdate(@RequestParam int qnaNo) {
		ArrayList<Qna> qnaList = noticeService.qnaSelectAll();
		ModelAndView mav = new ModelAndView();
		 if(!qnaList.isEmpty()) {
		      mav.addObject("qnaNo",qnaNo);
	          mav.addObject("qnaList",qnaList);   //view에서 사용할 객체 추가
	          mav.setViewName("qna/qnaUpdate");
	       }else {
	          mav.setViewName("common/error");
	       }
		return mav;
	}
	
	@RequestMapping(value="/qnaUpdate.do", method=RequestMethod.POST)
	public String qnaUpdate(HttpServletRequest request, @RequestParam MultipartFile fileName, Qna q, @RequestParam String fileStatus) {
		String fullPath = "";
		if(!fileName.isEmpty()) {
			String savePath = request.getSession().getServletContext().getRealPath("/resources/upload");
			String originName = fileName.getOriginalFilename();
			String onlyFileName = originName.substring(0, originName.indexOf('.'));
			String extension = originName.substring(originName.indexOf('.'));
			String filePath = onlyFileName+"_"+"1"+extension;
			fullPath = savePath+"/"+filePath;
			q.setFilename(originName);
			q.setFilepath(filePath);
		}
		if(fileStatus.equals("1")) {
			q.setFilename("");
			q.setFilepath("");
		}
			
		int result = noticeService.qnaUpdate(q);
		
		if(result>0) {
			System.out.println("수정 성공");
		}else {
			System.out.println("수정 실패");
		}
		if(!fileName.isEmpty()) {
			try {
				byte[] bytes = fileName.getBytes();
				File f = new File(fullPath);
				FileOutputStream fos = new FileOutputStream(f);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				bos.write(bytes);
				bos.close();
				System.out.println("파일 업로드 성공");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "redirect:/qna.do?currentPage=1";
	}
	
	@RequestMapping("/qnaDelete.do")
	public String qnaDelete(@RequestParam int qnaNo) {
		int result = noticeService.qnaDelete(qnaNo);
		
		if(result>0) {
			System.out.println("삭제 성공");
		}else {
			System.out.println("삭제 실패");
		}
		return "redirect:/qna.do?currentPage=1";
	}
}