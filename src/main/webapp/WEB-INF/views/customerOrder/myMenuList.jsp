<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Header --%>
<script src="http://code.jquery.com/jquery-3.4.0.js"></script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script> <!-- 아임포트 api 추가 -->
<script type="text/javascript" src="/resources/js/bucket.js"></script>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/sub.jsp" /><!-- 서브메뉴 추가했습니다 -->
<%-- content --%>
<section id="content-wrapper">
	<div class="area">
		<!-- <strong style="font-size:40px;">나만의 메뉴 </strong> -->
		<h1 class="board-tit">나만의 메뉴</h1><!-- 이거 바꿈 -->
		<br><br>
		<c:forEach items="${list }" var="bucket" varStatus="status">
			<div class="hiddenInfo"><!-- 정보 로드 -->
				<input type="hidden" class="hiddenBucIdx" value="${bucket.bucIdx }">
				<input type="hidden" class="hiddenCost" value="${bucket.bucCost }">
				<input type="hidden" class="hiddenBucCustomerIdx" name="mmCustomerNo" value="${bucket.bucCustomerIdx }">
 				<input type="hidden" class="hiddenBread" name="mmBread" value="${bucket.bucBread }">
				<input type="hidden" class="hiddenMain" name="mmMain" value="${bucket.bucMain }">
				<input type="hidden" class="hiddenVegi" name="mmVegi" value="${bucket.bucVegi }">
				<input type="hidden" class="hiddenCheese" name="mmCheese" value="${bucket.bucCheese }">
				<input type="hidden" class="hiddenTopping" name="mmTopping" value="${bucket.bucTopping }">
				<input type="hidden" class="hiddenSource" name="mmSource" value="${bucket.bucSource }">
				<input type="hidden" class="hiddenIsSalad" name="mmIsSalad" value="${bucket.bucIsSalad }">
				<input type="hidden" class="hiddenIsOvened" name="mmIsOvened" value="${bucket.bucIsOvened }">
				<input type="hidden" class="hiddenSet" name="mmSet" value="${bucket.bucSet }">
				<input type="hidden" class="hiddenSide" name="mmSide" value="${bucket.bucSide }">
				<input type="hidden" class="hiddenKcal" name="mmKcal" value="${bucket.bucKcal }">
				<input type="hidden" class="hiddenQuantity" name="mmQuantity" value="${bucket.bucQuantity }">
			</div>
		</c:forEach>
		<div class="beforeInsertOrder">
			<form action="/insertBucFromMyMenu.do" class="myMenu" method="post">
				<input type="hidden" class="cost" name="cost" value="">
				<input type="hidden" class="branchName" name="branchName" value="">
				<input type="hidden" class="customerIdx" name="cNo" value="">
 				<input type="hidden" class="bread" name="bread" value="">
				<input type="hidden" class="main" name="main" value="">
				<input type="hidden" class="vegi" name="vegi" value="">
				<input type="hidden" class="cheese" name="cheese" value="">
				<input type="hidden" class="topping" name="topping" value="">
				<input type="hidden" class="source" name="source" value="">
				<input type="hidden" class="isSalad" name="isSalad" value="">
				<input type="hidden" class="isOvened" name="isOvened" value="">
				<input type="hidden" class="set" name="set" value="">
				<input type="hidden" class="side" name="side" value="">
				<input type="hidden" class="kcal" name="kcal" value="">
				<input type="hidden" class="quantity" name="quantity" value="">
				<input type="submit" id="mmBuc" style="display:none;">
			</form> 
		</div>
<%-- 		<div class="hiddenOrder">
			<form action="/myMenuOrder.do" method="post">
				<input type="hidden" name="cusoTotalCost" value="">
				<input type="hidden" name="cusoPhone" value="">
				<input type="hidden" name="cusoMemberNo" value="${bucket.bucCustomerIdx }">
				<input type="hidden" name="cusoOrderNo" value="">
				<input type="hidden" name="cusoBranchName" value="">
				<input type="hidden" name="cusoCallBy" value="">
				<input type="submit" id="insertOrder" style="display:none;">
			</form>
		</div>	 --%>
		<strong style="font-size:20px; float:left;">* '나만의 메뉴'는 '온라인 주문'에서 만드실 수 있습니다</strong><br><br>
		<table class="comm-tbl type2">
			<tr>
				<th>메뉴</th><th>수량</th><th>가격</th><th>주문하기</th><th>메뉴삭제</th>
			</tr>
			<c:forEach items="${list }" var="bucket" varStatus="status">
			<tr>
				<td class="bucketOption">
						<span id="bread">빵 : ${bucket.bucBread }</span><br>
						<span id="main">메인재료 : ${bucket.bucMain }&nbsp;${bucket.bucIsSalad }</span><br>
						<span id="cheese">치즈 : ${bucket.bucCheese }</span><br>
						<span id="topping"></span><br>
						<span id="oven"></span><br>
						<span id="vegi"></span><br>
						<span id="source"></span><br>
						<span id="set">세트종류 : ${bucket.bucSet }</span><br>
						<span id="side"></span>
				</td>
				<td>수량 : ${bucket.bucQuantity }</td>
				<td><span class="menuCost">${bucket.bucCost }</span>원</td>
				<td><button type="button" class="orderMyMenu" style="font-size:15px; font-weight:bolder; color:#009233;">카트에 담기</button></td>
				<td><button type="button" class="deleteMyMenu"  style="font-size:15px; font-weight:bolder; color:red;">나만의메뉴 삭제하기</button></td>
			</tr>
			</c:forEach>
		</table>
		<input type="hidden" id="sessionPhone" value="${sessionScope.customer.phone }">
		<input type="hidden" id="sessionId" value="${sessionScope.customer.customerId }">
	</div>
</section>

<%-- Footer --%>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />