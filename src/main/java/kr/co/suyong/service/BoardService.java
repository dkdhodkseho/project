package kr.co.suyong.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.co.suyong.dao.BoardDao;
import kr.co.suyong.vo.BoardVO;

@Service("boardService")
public class BoardService {
	@Autowired
	@Qualifier(value="boardDao")
	
	private BoardDao boardDao;
	//리스트
	public ArrayList<BoardVO> boardList() {
		
		return (ArrayList<BoardVO>)boardDao.boardList();
		
	}
	// 해당게시물 조회
	public BoardVO boardOne(int bno) {
		return boardDao.boardOne(bno);
		
	}
}
