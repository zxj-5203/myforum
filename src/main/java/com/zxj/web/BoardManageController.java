package com.zxj.web;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.zxj.constants.CommonConstant;
import com.zxj.dao.Page;
import com.zxj.domain.Board;
import com.zxj.domain.Post;
import com.zxj.domain.Topic;
import com.zxj.domain.User;
import com.zxj.service.ForumService;

/**
 * 论坛板块管理：显示论坛板块列表、显示论坛板块主题列表、发表主题帖子、回复帖子、删除帖子、设置精华帖子等；
 * 
 * @author zxj
 *
 */
@Controller
public class BoardManageController extends BaseController {
	private ForumService forumService;

	@Autowired
	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

	/**
	 * 列出论坛模块下的主题帖子；
	 * 
	 * @param boardId
	 * @param pageNo
	 * @return
	 */
	// @PathVariable 将URL中的占位符参数{boardId}绑定到控制器处理方法的入参boardId中；
	@RequestMapping(value = "/board/listBoardTopics-{boardId}", method = RequestMethod.GET)
	public ModelAndView listBoardTopics(@PathVariable("boardId") Integer boardId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo) {
		ModelAndView mv = new ModelAndView();
		Board board = forumService.getBoardByBoardId(boardId);
		pageNo = pageNo == null ? 1 : pageNo;
		Page pagedTopic = (Page) forumService.getPagedTopics(boardId, pageNo, CommonConstant.PAGE_SIZE);
		mv.addObject("board", board);
		mv.addObject("pagedTopic", pagedTopic);
		mv.setViewName("board/listBoardTopics");
		return mv;
	}

	/**
	 * 添加主题帖页面
	 * 
	 * @param boardId
	 * @return
	 */
	@RequestMapping(value = "/board/addTopicPage-{boardId}", method = RequestMethod.GET)
	public ModelAndView addTopicPage(@PathVariable("boardId") Integer boardId) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("boardId", boardId);
		mv.setViewName("board/addTopic");
		return mv;
	}

	/**
	 * 添加一个主题帖；
	 * 
	 * @param request
	 * @param topic
	 * @return
	 */
	@RequestMapping(value = "/board/addTopic", method = RequestMethod.POST)
	public String addTopic(HttpServletRequest request, Topic topic) {
		User user = getSessionUser(request);
		topic.setUser(user);
		Date now = new Date();
		topic.setCreateTime(now);
		topic.setLastPost(now);
		forumService.addTopic(topic);
		String targetUrl = "listBoardTopics-" + topic.getBoardId() + ".html";
		// 直接使用redirect完成重定向请求，因为目标RUL是动态的，不方便直接定义返回视图；
		return "redirect:" + targetUrl;
	}

	/**
	 * 列出主题的所有帖子；
	 * 
	 * @param topicId
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/board/listTopicPosts-{topicId}", method = RequestMethod.GET)
	public ModelAndView listTopicPosts(@PathVariable("topicId") Integer topicId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo) {
		ModelAndView mv = new ModelAndView();
		Topic topic = forumService.getTopicByTopicId(topicId);
		pageNo = pageNo == null ? 1 : pageNo;
		Page pagedPost = (Page) forumService.getPagedPosts(topicId, pageNo, CommonConstant.PAGE_SIZE);
		mv.addObject("topic", topic);
		mv.addObject("pagedPost", pagedPost);
		mv.setViewName("board/listTopicPosts");
		return mv;
	}

	/**
	 * 回复主题 - 添加回复贴；
	 * 
	 * @param request
	 * @param post
	 * @return
	 */
	@RequestMapping(value = "/board/addPost")
	public String addPost(HttpServletRequest request, Post post) {
		post.setCreateTime(new Date());
		post.setUser(getSessionUser(request));
		Topic topic = new Topic();
		int topicId = Integer.valueOf(request.getParameter("topicId"));
		if (topicId > 0) {
			topic.setTopicId(topicId);
			post.setTopic(topic);
		}
		forumService.addPost(post);
		String targetUrl = "listTopicPosts-" + post.getTopic().getTopicId() + ".html";
		return "redirect:" + targetUrl;
	}

	/**
	 * 删除版块；
	 * 
	 * @param boardIds
	 * @return
	 */
	@RequestMapping(value = "/board/removeBoard", method = RequestMethod.GET)
	public String removeBoard(@RequestParam("boardIds") String boardIds) {
		String[] boardIdsArr = boardIds.split(",");
		for (int i = 0; i < boardIdsArr.length; i++) {
			forumService.removeBoard(new Integer(boardIdsArr[i]));
		}
		String targetUrl = "/index.html";
		return "redirect:" + targetUrl;
	}

	/**
	 * 删除主题Topic；
	 * 
	 * @param topicIds
	 * @param boardId
	 * @return
	 */
	@RequestMapping(value = "/board/removeTopic", method = RequestMethod.GET)
	public String removeTopic(@RequestParam("topicIds") String topicIds, @RequestParam("boardId") String boardId) {
		String[] topicIdsArr = topicIds.split(",");
		for (int i = 0; i < topicIdsArr.length; i++) {
			forumService.removeTopic(new Integer(topicIdsArr[i]));
		}
		String targetUrl = "listBoardTopics-" + boardId + ".html";
		return "redirect:" + targetUrl;
	}

	/**
	 * 设置精华帖；
	 * 
	 * @param topicIds
	 * @param boardId
	 * @return
	 */
	@RequestMapping(value = "/board/makeDigestTopic", method = RequestMethod.GET)
	public String makeDigestTopic(@RequestParam("topicIds") String topicIds, @RequestParam("boardId") String boardId) {
		String[] topicIdsArr = topicIds.split(",");
		for (int i = 0; i < topicIdsArr.length; i++) {
			forumService.makeDigestTopic(new Integer(topicIdsArr[i]));
		}
		String targetUrl = "listBoardTopics-" + boardId + ".html";
		return "redirect:" + targetUrl;
	}
}
