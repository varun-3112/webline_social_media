package com.webline.socialmedia.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webline.socialmedia.entity.Post;
import com.webline.socialmedia.entity.PostFilter;
import com.webline.socialmedia.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepository;
	
	@Override
	public List<Post> getPostsByUser(String filterFlag, PostFilter postFilter, Long userId) {
		return postRepository.getPostsByUser(filterFlag,postFilter,2L);
	}

	@Override
	public List<Post> getTopPosts(Long userId, Long numberOfPosts, String typeOfPosts) {
		return postRepository.getTopPosts(userId,numberOfPosts,typeOfPosts);
	}

	@Override
	public boolean checkPostExist(Long postId) {
		return postRepository.checkPostExist(postId);
	}

	@Override
	public void likeDislikePost(Long postId, Long userId,String operation) {
		
		boolean isPostLikedDisliked=postRepository.checkIsLikedDisliked(postId,userId);	
		if(isPostLikedDisliked) {
			postRepository.likeDislikePost(postId,userId,operation,"UPDATE");
		}
		else {
			postRepository.likeDislikePost(postId,userId,operation,"ADD");
		}	
	}

	@Override
	public Post addPost(Post post) {
		Long postId=postRepository.checkPostForDuplicate(post);
		return postRepository.AddUpdatePost(post,postId);
	}

	@Override
	public String uploadImage(String imagePath, MultipartFile file,Long userId,String postTitle) {
		
		
		String name=postTitle+"_"+userId+"_"+file.getOriginalFilename();
		
		String filePath=imagePath+"/"+name;
		
		File f=new File(imagePath);
		
		//Create folder if not present
		if(!f.exists()) {
			f.mkdir();
		}
		
		try {
			Files.copy(file.getInputStream(), Paths.get(filePath));
		}catch(FileAlreadyExistsException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			filePath="";
		}
		
		return filePath;
	}
	
	
}
