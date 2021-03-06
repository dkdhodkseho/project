<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- Header --%>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
	#mainLink {
		color:white;
	}
</style>
<script type="text/javascript" src="/resources/js/notice.js"></script><!-- notice.js -->
<c:set var="addr" value="${mgrAddr }"/>
<c:set var="subStr" value="${fn:substringAfter(addr, ' ')}"/>
<c:choose>
	<c:when test="${fn:endsWith(subStr,'구') }">
		<c:set var="subAddr" value="${fn:replace(subStr, '구', '')}"/>
	</c:when>
	<c:when test="${fn:endsWith(subStr,'군') }">
		<c:set var="subAddr" value="${fn:replace(subStr, '군', '')}"/>
	</c:when>
	<c:when test="${fn:endsWith(subStr,'시') }">
		<c:set var="subAddr" value="${fn:replace(subStr, '시', '')}"/>
	</c:when>
</c:choose>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
	function searchAddr(mgrAddr,index){
		var addr = mgrAddr;
		var i = index;
	    new daum.Postcode({
	        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
            // 예제를 참고하여 다양한 활용법을 확인해 보세요.
            if(data.userSelectedType == "R"){//도로명
	            $("[name=mgrAddr]").val(data.roadAddress);
            }else{//지번
			    $("[name=mgrAddr]").val(data.jibunAddress);
            }
			    $("[name=mgrPost]").val(data.zonecode);
            	$("[name=mgrAddrCode]").val(data.sigunguCode);
            	$("[name=mgrAddrCode]").val(data.sigunguCode);
            	$("[name=mgrId]").val('manager'+data.sigunguCode+i);
            	$("[name=mgrName]").val('SabWay '+data.bname+" "+data.roadname+" "+i+'호점');
	        }
	    }).open({
		    popupName : 'postCodePopup',//중복 생성 방지
		    q: addr
		});
	}
</script>


<%-- Content --%>
<section id="content-wrapper">
	<div class="area">
		<!-- 가맹점 신청 승인 시  -->
		<div class="common-tbl-box">
			<h1 class="comm-content-tit">신청 목록</h1>
			<form action="/mgrEnroll.do" method="post">
				<input type="hidden" name="mgrStatus" value="1"><!-- 영업상태: 준비중(Default) -->
				<input type="hidden" name="mgrLevel" value="0"><!-- 0: 가맹점 고정 -->
				<input type="hidden" name="applyName" value="${applyName }"><!-- 신청자 이름 전달용 -->
				<input type="hidden" name="applyNo" value="${applyNo }"><!-- 신청 번호 전달용 -->
				<input type="hidden" name="mgrAddrCode">
				<table class="comm-tbl">
					<tr>
						<th>이름</th>
						<td><input type="text" name="mgrBossName" value="${applyName }" readonly></td>
					</tr>
					<tr>
						<th>아이디</th>
						<!-- 아이디 뒤에 정해놓은 지역코드 + -->
						<td><input type="text" name="mgrId" readonly></td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<!-- 비밀번호 기본값 1234 생성 후 각자 변경 -->
						<td><input type="password" name="mgrPw" value="1234" readonly></td>
					</tr>
					<tr>
						<th>가맹점 이름</th>
						<td><input type="text" name="mgrName" readonly></td>
					</tr>
					<tr>
						<th>전화번호</th>
						<!-- sabway + 받아온 동 + 도로명 + 1부터 중복마다 1씩증가 -->
						<td><input type="text" name="mgrTel" value="${mgrTel }" readonly></td>
					</tr>
					<tr>
						<th>
							우편번호<br><br><br>
							주소
						</th>
						<td>
							<label onclick="searchAddr('${mgrAddr }','${i }');">
								<input type="text" name="mgrPost" style="width:40px" required="required"><br><br>
								<input type="text" name="mgrAddr" readonly>
							</label>
						</td>
					</tr>
				</table>
				<div class="common-tbl-btn-group">
					<button class="btn-style2" type="submit">가맹점 등록</button>
					<button class="btn-style2" type="button"><a href="/admin.do" id="mainLink">메인으로</a></button>
				</div>
			</form>
		</div>
	</div>
</section>
<%-- Footer --%>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />