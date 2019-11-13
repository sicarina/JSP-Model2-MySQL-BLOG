package com.sample.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sample.action.Action;
import com.sample.dao.UserDao;
import com.sample.model.User;
import com.sample.util.Script;

public class UserProfileAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 파일 받아올 때 사용할 변수들 (저장위치, 최대크기, 인코딩)
		String path = request.getSession().getServletContext().getRealPath("uploadFiles");
		int maxFileSize = 2 * 1024 * 1024; // 2MB
		String encoding = "UTF-8";
		
		// DefaultFileRenamePolicy() : 파일명 중복 처리 -> 동일한 파일명일 경우 파일명 뒤에 숫자 추가
		MultipartRequest multi;
		
		try{
			multi = new MultipartRequest(request, path, maxFileSize, encoding, new DefaultFileRenamePolicy());
			
			// 요청에 같이 넘어온 데이터
			String id = multi.getParameter("id");
			String fileName = multi.getFilesystemName("profile");	// 파일명 중복처리 후 변경된 파일명
			//String originFileName = multi.getOriginalFileName("profile");	// 원래 파일명 : DB저장 필요시 사용
			
			// 넘어온 데이터가 null 또는 공백일 경우 return
			if(id == null || id.equals("")) {
				return;
			} else if(fileName == null || fileName.equals("")) {
				return;
			}

			// 파일 저장을 위한 contextPath 가져오기
			String contextPath = request.getSession().getServletContext().getContextPath();
			//String downloadPath = request.getSession().getServletContext().getRealPath("uploadFiles");	// 실제 서버의 파일 위치

			// 파일이 저장되는 위치 저장
			String filePath = contextPath + "/uploadFiles/" + fileName;
			
			// DB에 저장하기 위해 user모델에 변수 저장
			User user = new User();
			user.setId(Integer.parseInt(id));
			user.setProfile(filePath);
			
			// DB에 저장하고 실행결과 값을 받음
			UserDao dao = UserDao.getInstance();
			int resultCnt = dao.updateProfile(user);
			
			// 정상적으로 저장되었다면 메인페이지로 이동, 안되었으면 알림창과 함께 이전페이지로 이동
			if(resultCnt == 1) {
				// 세션을 수정한 정보로 변경
				HttpSession session = request.getSession();
				User userSession = (User) session.getAttribute("user");
				userSession.setProfile(filePath);
				session.setAttribute("user", userSession);
				
				// 메인페이지로 이동
				Script.location(response, "프로필 사진이 변경되었습니다.", "/blogNew/index.jsp");
			} else {
				Script.back(response, "프로필 사진 수정에 실패하였습니다.\\n다시 시도해 주세요.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Script.back(response, "프로필 사진 수정에 실패하였습니다.\\n다시 시도해 주세요.");
		}
		
	}

}
