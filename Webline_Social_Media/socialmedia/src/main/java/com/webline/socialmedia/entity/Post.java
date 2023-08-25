package com.webline.socialmedia.entity;

import java.sql.Timestamp;
import java.util.List;

public class Post {
	
	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}
	private Long postId;
	private String postTitle;
	private String postContent;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private Long userId;
	private String imagePath;
	private String errorMessage;
	private Long numberOfLikes;
	private Long numberOfDislikes;
	private List<UserDto> likedByUsers;
	private List<UserDto> dislikedByUsers;
	
	public Post(Long postId, String postTitle, String postContent, Timestamp createdAt, Timestamp updatedAt,
			String imagePath, Long numberOfLikes, Long numberOfDislikes) {
		super();
		this.postId = postId;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.imagePath = imagePath;
		this.numberOfLikes = numberOfLikes;
		this.numberOfDislikes = numberOfDislikes;
	}
	
	public Post(String postTitle, String postContent) {
		super();
		this.postTitle = postTitle;
		this.postContent = postContent;
	}

	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Long getNumberOfLikes() {
		return numberOfLikes;
	}
	public void setNumberOfLikes(Long numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}
	public Long getNumberOfDislikes() {
		return numberOfDislikes;
	}
	public void setNumberOfDislikes(Long numberOfDislikes) {
		this.numberOfDislikes = numberOfDislikes;
	}
	public List<UserDto> getLikedByUsers() {
		return likedByUsers;
	}
	public void setLikedByUsers(List<UserDto> likedByUsers) {
		this.likedByUsers = likedByUsers;
	}
	public List<UserDto> getDislikedByUsers() {
		return dislikedByUsers;
	}
	public void setDislikedByUsers(List<UserDto> dislikedByUsers) {
		this.dislikedByUsers = dislikedByUsers;
	}

}
