package kr.co.suyong.controller;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.suyong.service.BoardService;
import kr.co.suyong.vo.BoardVO;

@Controller
public class BoardController {
	@Autowired
	@Qualifier(value="boardService")
	
	private BoardService boardService;
	//게시판 목록
	@RequestMapping(value="/boardList.do")
	public ModelAndView boardList(BoardVO boardVO) {
		ArrayList<BoardVO> boardList = boardService.boardList(); 
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("boardList",boardList);
		mav.setViewName("board/boardList");
		return mav;
		
	}
	
	@RequestMapping(value="/boardOne.do")
	public ModelAndView boardOne(@RequestParam int bno) {
		BoardVO boardVO = boardService.boardOne(bno);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("boardOne",boardVO);
		mav.setViewName("board/boardOne");
		return mav;
		
	}
	
	@RequestMapping(value="/boardUpdate.do")
	public ModelAndView boardUpdate(@RequestParam int bno) {
		
		System.out.println(bno);
		return null;
		
	}
}
