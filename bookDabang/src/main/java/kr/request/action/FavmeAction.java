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

public class FavmeAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int req_num = Integer.parseInt(request.getParameter("req_num"));
		Map<String,Object> mapAjax = new HashMap <String,Object>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		RequestDAO dao = RequestDAO.getInstance();
		
		if(user_num==null) {
			mapAjax.put("status", "noFav");
			mapAjax.put("count", dao.howmanyFav(req_num));
		}else {
			RequestFavVO fav = dao.doyouFavme(new RequestFavVO(req_num,user_num));
			if(fav!=null) {
				mapAjax.put("status", "yesFav");
				mapAjax.put("count",dao.howmanyFav(req_num));
			}else {
				mapAjax.put("status", "noFav");
				mapAjax.put("count", dao.howmanyFav(req_num));
			}
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
