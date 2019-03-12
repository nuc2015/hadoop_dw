package cn.edu.llxy.dw.dss.md.ctx;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.edu.llxy.dw.dss.vo.sys.UserVo;

public class SecurityContext {
	private static final String SECURITY_KEY = "__security_key";
	
	private static final String SECURITY_SUBSYSTEM = "__security_subsystem";
	
	private static ThreadLocal contextHolder = new ThreadLocal();

   

    public static UserVo getUser() {
        if (contextHolder.get() == null) {
            return null;
        }
        return (UserVo) contextHolder.get();
    }

    static void setUser(UserVo u) {
        contextHolder.set(u);
    }
    
    static void clearContext() {
        contextHolder.set(null);
    }
    
    
    /** 仅允许 {@link SecurityContextFilter}调用
     * @param request
     * @param response
     * @return
     */
    public static UserVo getUserFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null)
			return null;

		Object o = session.getAttribute(SECURITY_KEY);

		if (o == null || !(o instanceof UserVo))
			return null;

		return (UserVo) o;
	}

    
	/** 把用户信息保存到session，仅允许 {@link LoginAction}调用
	 * @param user
	 * @param request
	 * @param response
	 */
	public static void saveUserToSeesion(UserVo user, HttpServletRequest request) {
		saveUserToSeesion(user,request.getSession());
	}
	
	/**
	 * 获取用户所能访问子系统
	 * @param request
	 * @param response
	 * @return
	 */
	static List getSubSystemsFromSession(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);

		if (session == null)
			return null;

		Object o = session.getAttribute(SECURITY_SUBSYSTEM);

		if (o == null || !(o instanceof UserVo))
			return null;

		return (List) o;
	}
	
	public static void saveSubSystemToSession(List subSystems, HttpServletRequest request, HttpServletResponse response){
		saveSubSystemToSeesion(subSystems, request.getSession());
	}
	
	static void saveSubSystemToSeesion(List subSystems, HttpSession sessoin) {
		sessoin.setAttribute(SECURITY_SUBSYSTEM, subSystems);
	}
	
	public static void logout(HttpServletRequest request) {
		HttpSession session =request.getSession();
		session.removeAttribute(SECURITY_KEY);
		session.removeAttribute(SECURITY_SUBSYSTEM);
		session.invalidate();
		session =request.getSession(true);
		clearContext();
	}
	
	static void saveUserToSeesion(UserVo user, HttpSession sessoin) {
		if(user!=null){
			sessoin.setAttribute(SECURITY_KEY, user);
			setUser(user);
		}
	}
	
	public static void saveObjectToSession(String key, Object obj, HttpServletRequest request){
		if(obj != null){
			HttpSession session = request.getSession();
			session.setAttribute(key, obj);
		}
	}
	
	public static Object getObject(String key, HttpServletRequest request){
		HttpSession session = request.getSession();
		return request.getAttribute(key);
	}
}