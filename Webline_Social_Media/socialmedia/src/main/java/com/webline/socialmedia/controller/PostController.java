package com.webline.socialmedia.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.webline.socialmedia.entity.Post;
import com.webline.socialmedia.entity.PostFilter;
import com.webline.socialmedia.entity.User;
import com.webline.socialmedia.entity.UserDto;
import com.webline.socialmedia.repository.PostRepository;
import com.webline.socialmedia.service.PostService;
import com.webline.socialmedia.service.ValidationService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ValidationService validationService;
	
	
	
	@PostMapping("/addPost")
	public ResponseEntity<Post> addProduct(@RequestParam("post") String postJson,@RequestParam("file") MultipartFile file,Authentication authentication){
		
		Post post=new Post();
		//Validate input post object
		if(postJson!=null && !postJson.isEmpty()) {
			post=gson.fromJson(postJson, Post.class);
		}
		
		String errorMessage=validationService.validatePostRequest(post);
		
		Post savedPost=null;
		
		if(errorMessage!=null && !errorMessage.isEmpty()) {
			savedPost=new Post();
			savedPost.setErrorMessage(errorMessage);
			return new ResponseEntity<>(savedPost,HttpStatus.BAD_REQUEST);
		}
		
		User user=(User) authentication.getPrincipal();
		
		//File upload
		if(!file.isEmpty()) {
			post.setImagePath(postService.uploadImage(env.getProperty("imagePath"), file,user.getUserId(),post.getPostTitle()));
		}
		
		post.setUserId(user.getUserId());
		post.setCreatedAt(new Timestamp(new Date().getTime()));
		post.setUpdatedAt(new Timestamp(new Date().getTime()));
		
		savedPost=postService.addPost(post);
		return new ResponseEntity<>(savedPost,HttpStatus.CREATED);
	}
	
	@GetMapping("/getPostsByUser")
	public ResponseEntity<List<Post>> getPostsByUser(@RequestParam(value="filterFlag", defaultValue = "false",required = false) String filterFlag,@RequestParam(value="postFilterJson", defaultValue = "",required = false) String postFilterJson,Authentication authentication){
		
		PostFilter postFilter=new PostFilter();
		if(filterFlag.equals("true")) {
			postFilter=gson.fromJson(postFilterJson, PostFilter.class);
		}
		
		postFilter=setDefaultValuesForPostFilter(postFilter);
		
		User user=(User) authentication.getPrincipal();
		
		List<Post> posts= postService.getPostsByUser(filterFlag,postFilter,user.getUserId());
		
		for(Post post:posts) {
			post.setLikedByUsers(postRepository.getPostLikedByUsers(post.getPostId()));
			post.setDislikedByUsers(postRepository.getPostDisLikedByUsers(post.getPostId()));
		}
		
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/getTopPosts")
	public ResponseEntity<List<Post>> getTopPosts(@RequestParam(value="userId", defaultValue = "0",required = false) Long userId,@RequestParam(value="numberOfPosts", defaultValue = "10",required = false) Long numberOfPosts,@RequestParam(value="typeOfPosts", defaultValue = "likes",required = false) String typeOfPosts){
		
		List<Post> posts= postService.getTopPosts(userId,numberOfPosts,typeOfPosts);
		
		for(Post post:posts) {
			post.setLikedByUsers(postRepository.getPostLikedByUsers(post.getPostId()));
			post.setDislikedByUsers(postRepository.getPostDisLikedByUsers(post.getPostId()));
		}
		
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}
	
	@PostMapping("/like")
	public ResponseEntity<String> likePost(@RequestParam("postId") Long postId,Authentication authentication){
		
		boolean postExist=postService.checkPostExist(postId);
		if(!postExist) {
			return new ResponseEntity<>("Post does not exist",HttpStatus.BAD_REQUEST);
		}
		
		User user=(User) authentication.getPrincipal();
		
		try {
			postService.likeDislikePost(postId,user.getUserId(),"like");
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error in liking the post",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>("User "+user.getUserName()+" has liked the post",HttpStatus.OK);
	}
	
	@PostMapping("/dislike")
	public ResponseEntity<String> dislikePost(@RequestParam("postId") Long postId,Authentication authentication){
		
		boolean postExist=postService.checkPostExist(postId);
		if(!postExist) {
			return new ResponseEntity<>("Post does not exist",HttpStatus.BAD_REQUEST);
		}

		User user=(User) authentication.getPrincipal();
		
		try {
			postService.likeDislikePost(postId,user.getUserId(),"dislike");
		}catch(Exception e) {
			return new ResponseEntity<>("Error in disliking the post",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("User "+user.getUserName()+" has disliked the post",HttpStatus.OK);
	}
	
	
	public PostFilter setDefaultValuesForPostFilter(PostFilter postFilter) {
		if(postFilter.getPageNumber()==null || postFilter.getPageNumber().isEmpty()){
			postFilter.setPageNumber(env.getProperty("post_pageNumber"));
		}
		if(postFilter.getPageSize()==null || postFilter.getPageSize().isEmpty()){
			postFilter.setPageSize(env.getProperty("post_pageSize"));
		}
		if(postFilter.getSortedBy()==null || postFilter.getSortedBy().isEmpty()){
			postFilter.setSortedBy(env.getProperty("post_sortedBy"));
		}
		if(postFilter.getSortType()==null || postFilter.getSortType().isEmpty()){
			postFilter.setSortType(env.getProperty("post_sortType"));
		}
		return postFilter;
	}

}
