package kr.co.subway.sales.statics.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.subway.sales.statics.vo.SalesStaticsGrpVo;

@Repository("salesStaticsDao")
public class SalesStaticsDao {
	@Autowired
	SqlSessionTemplate sqlSession;

	//전체 매출 가져오기
	public List totalSales() {
		return sqlSession.selectList("sales.getOrder");
	}

	//전체 매출페이지에서 선택한 월의 지점별 매출
	public List monthTotalSales(String orderMonth) {
		return sqlSession.selectList("sales.monthTotalSales",orderMonth);
	}

}
