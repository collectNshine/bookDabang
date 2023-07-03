<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <style>
    #footer{
    background-color:white;
    font-size: 10pt !important;
    box-sizing: none;
    }
    #footer_box{
    margin:auto;
     display:flex;
     text-align:center;
   	 width:100%;
   	 padding : 3rem;
     
    }
    #footer_box_box{
     color:gray !important;
    	margin:auto;
        width: 60%;
        display: inline-block;
        text-align: center;
    }
    #cscenter_box{
     color:gray !important;
        width:200px;
    padding : 2rem;
	}
	#cscenter_box address span{
 	color:gray !important;
	}
	#footer_footer div span{
     color:gray !important;
    padding:0rem  5px 0rem 5px;
    font-style:normal !important;
    }
</style>
<div id="footer">
	<hr size="1px" width="100%" noshade>
    <div id="footer_info">
        <div id="footer_box">
            <div>
                <img src="${pageContext.request.contextPath}/images/greylogo.png" style="width:200px;top:50px;">
            </div>
                <div id="footer_box_box" >
                <h3>(주)북다방</h3>
                <hr size="1px" width="100%" noshade>
                <address id="footer_footer">
                    <div>
                    <span>대표이사 북다방</span>
                    <span>고객정보보호 책임자 북다방</span>
                    <span>사업자등록 123-45-678</span>
                    <span>통신판매업신고 중구101010호</span><br>
                    <span>이메일 bookdabang.manager@gmail.com</span>
                    <span>호스팅 제공자 북다방커뮤니케이션</span>
                    <span>(본사) 서울시 강남구 역삼동 테헤란로</span>
                    </div>
                    <div style="color:grey !important;">ⓒ Bookdabang Communication. All Rights Reserved.</div>
                </address>
            </div>
            <div id="cscenter_box" style="color:grey !important;">
                <h5>고객센터 1544-1544 (발신자 부담)</h5>
                <address>
                    <span>서울시 강남구 역삼동 테헤란로</span>
                    <span>Fax 02-0000-0000</span>
                </address>
            </div>
        </div>
    </div>
</div>
