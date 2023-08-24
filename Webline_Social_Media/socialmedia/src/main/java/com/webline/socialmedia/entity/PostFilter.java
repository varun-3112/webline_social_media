package com.webline.socialmedia.entity;

public class PostFilter {

	private String sortedBy;
	private String sortType;
	private String pageSize;
	private String pageNumber;
	private String postTitle;
	private String postContent;
	private String numberOfLikes;
	private String numberOfDislikes;
	private String createdAt;
	public String getSortedBy() {
		return sortedBy;
	}
	public void setSortedBy(String sortedBy) {
		this.sortedBy = sortedBy;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
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
	public String getNumberOfLikes() {
		return numberOfLikes;
	}
	public void setNumberOfLikes(String numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}
	public String getNumberOfDislikes() {
		return numberOfDislikes;
	}
	public void setNumberOfDislikes(String numberOfDislikes) {
		this.numberOfDislikes = numberOfDislikes;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	private String updatedAt;
}
