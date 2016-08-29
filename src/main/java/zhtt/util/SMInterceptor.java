package zhtt.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import zhtt.entity.user.User;

public class SMInterceptor implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("afterCompletion");
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("postHandle");
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		User loginUser=(User) request.getSession().getAttribute("loginUser");
		String path=request.getRequestURI();
		String ctx=request.getContextPath();
		if(loginUser==null){
			if(path(path)){
				return true;
			}else{
				System.out.println(" 路径未通过。。ctx： "+ctx+"\t path:"+path);
				request.getRequestDispatcher("/login").forward(request, response);
				return false;
			}
		}
		return true;
	}
	
	private boolean path(String path){
		String[] defaultPath=new String[]{"login"};
		for(String str:defaultPath){
			if(path.indexOf(str)>-1){
				return true;
			}
		}
		return false;
	}

}
