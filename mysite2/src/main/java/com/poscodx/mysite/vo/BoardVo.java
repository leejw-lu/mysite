package com.poscodx.mysite.vo;

public class BoardVo {
	private Long no;
	private String title;
	private String contents;
	private int hit;
	private String regDate;
	private int gNo;
	private int oNo;
	private int depth;
	
	private Long userNo;
	private String userName;

	public BoardVo() {
	}
		
	/* BoardList 게시글 여러개 view 생성자 */
	public BoardVo(String userName, Long no, String title, String contents, int hit, String regDate, int gNo, int oNo, int depth) {
		this.userName = userName;
		this.no = no;
		this.title = title;
		this.contents = contents;
		this.hit = hit;
		this.regDate = regDate;
		this.gNo = gNo;
		this.oNo = oNo;
		this.depth = depth;
	}

	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getgNo() {
		return gNo;
	}
	public void setgNo(int gNo) {
		this.gNo = gNo;
	}
	public int getoNo() {
		return oNo;
	}
	public void setoNo(int oNo) {
		this.oNo = oNo;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	
}
