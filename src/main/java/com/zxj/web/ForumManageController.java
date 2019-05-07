package com.zxj.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.zxj.domain.Board;
import com.zxj.domain.User;
import com.zxj.service.ForumService;
import com.zxj.service.UserService;

/**
 * 处理论坛管理员所使用的各项操作功能：创建论坛版块、指定版块管理员、锁定/解锁用户等；
 * 
 * @author zxj
 *
 */
@Controller
public class ForumManageController extends BaseController {
	private ForumService forumService;
	private UserService userService;

	@Autowired
	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 列出所有的论坛版块；
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView listAllBoards() {
		ModelAndView mv = new ModelAndView();
		List<Board> boards = forumService.getAllBoards();
		mv.addObject("boards", boards);
		mv.setViewName("forum/listAllBoards");
		return mv;
	}
	/**
	 * 添加一个主题帖的页面；
	 * 
	 * @return
	 */
	@RequestMapping(value = "/forum/addBoardPage", method = RequestMethod.GET)
	public String addBoardPage() {
		return "forum/addBoard";
	}

	/**
	 * 添加一个主题帖；
	 * 
	 * @param board
	 * @return
	 */
	@RequestMapping(value = "/forum/addBoard", method = RequestMethod.PUT)
//	@RequestMapping(value = "/forum/addBoard")
	public String addBoard(Board board) {
		forumService.addBoard(board);
		return "forum/addBoardSuccess";
	}

	/**
	 * 指定论坛管理的页面；
	 * 
	 * @return
	 */
	@RequestMapping(value = "/forum/setBoardManagerPage", method = RequestMethod.GET)
	public ModelAndView setBoardManagerPage() {
		ModelAndView mv = new ModelAndView();
		List<Board> boards = forumService.getAllBoards();
		List<User> users = userService.getAllUsers();
		mv.addObject("boards", boards);
		mv.addObject("users", users);
		mv.setViewName("forum/setBoardManager");
		return mv;
	}

	/**
	 * 设置版块管理员；
	 * 
	 * @param userName
	 * @param boardId
	 * @return
	 */
	@RequestMapping(value = "/forum/setBoardManager", method = RequestMethod.POST)
	public ModelAndView setBoardManager(@RequestParam("userName") String userName,
			@RequestParam("boardId") String boardId) {
		ModelAndView mv = new ModelAndView();
		User user = userService.getUserByUserName(userName);
		if (user == null) {
			mv.addObject("errorMsg", "用户名（" + userName + "）不存在！");
			mv.setViewName("fail");
		} else {
			Board board = forumService.getBoardByBoardId(Integer.parseInt(boardId));
			user.getManBoards().add(board);
			userService.update(user);
			mv.setViewName("success");
		}
		return mv;
	}

	/**
	 * 用户锁定及解锁 的管理页面；
	 * 
	 * @return
	 */
	@RequestMapping(value = "/forum/userLockManagePage", method = RequestMethod.GET)
	public ModelAndView userLockManagePage() {
		ModelAndView mv = new ModelAndView();
		List<User> users = userService.getAllUsers();
		mv.addObject("users", users);
		mv.setViewName("forum/userLockManage");
		return mv;
	}

	/**
	 * 用户锁定及解锁；
	 * 
	 * @param userName
	 * @param lock
	 * @return
	 */
	@RequestMapping(value = "/forum/userLockManage", method = RequestMethod.POST)
	public ModelAndView userLockManage(@RequestParam("userName") String userName, @RequestParam("locked") String locked) {
		ModelAndView mv = new ModelAndView();
		User user = userService.getUserByUserName(userName);
		if (user == null) {
			mv.addObject("errorMsg", "用户名（" + userName + "）不存在！");
			mv.setViewName("fail");
		} else {
			user.setLocked(Integer.parseInt(locked));
			userService.update(user);
			mv.setViewName("success");
		}
		return mv;
	}
}
