package kr.request.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;


import kr.controller.Action;
import kr.request.dao.RequestDAO;
import kr.request.vo.RequestFavVO;

public class GiveFavAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {
			mapAjax.put("result", "logout");
		}else {
			request.setCharacterEncoding("utf-8");
			int req_num = Integer.parseInt(request.getParameter("req_num"));
			RequestDAO dao = RequestDAO.getInstance();
			RequestFavVO fav = new RequestFavVO();
			fav.setReq_num(req_num);
			fav.setMem_num(user_num);
			
			//추천 눌렀는지 체크
			RequestFavVO db_fav = dao.doyouFavme(fav);
			if(db_fav!=null) {
				//추천이 눌러져있으면 취소 처리
				dao.ByeFav(db_fav.getReq_fav_num());
				mapAjax.put("result","success");
				mapAjax.put("status","noFav");
				mapAjax.put("count",dao.howmanyFav(req_num));
			}else {
				//추천 안눌러져있으면 누를 수 있게
				dao.giveFav(fav);
				mapAjax.put("result", "success");
				mapAjax.put("status", "yesFav");
				mapAjax.put("count", dao.howmanyFav(req_num));
			}		
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
