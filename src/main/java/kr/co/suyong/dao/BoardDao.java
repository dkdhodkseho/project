package kr.co.suyong.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.suyong.vo.BoardVO;

@Repository("boardDao")
public class BoardDao {
	@Autowired
	SqlSessionTemplate sqlSession;
	public List boardList(){
		return sqlSession.selectList("board.boardList");
	}
	public BoardVO boardOne(int bno) {
		return sqlSession.selectOne("board.boardOne",bno);
	}
}
