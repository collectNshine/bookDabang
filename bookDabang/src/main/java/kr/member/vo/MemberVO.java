package kr.member.vo;

import java.sql.Date;

import kr.util.Encrypt;

public class MemberVO {
	//[member 테이블]
	private int mem_num; //유저 고유 번호 
	private String id; //아이디 
	private int auth; // 회원 등급 :  0 탈퇴회원, 1 일반회원, 9 관리자회원 
	private int state; //계정상태(개인정보 저장위치) :0 활성계정(bmember_detail),
	//											 1 휴면계정(bmember_sleep),
	//[member_detail 테이블]						 2 가입이력은 있으나 DB에 남아있지 않는 계정(X)  
	private String name; //이름
	private String salt;//salt
	private String passwd;//비밀번호
	private int sex;//성별
	private String birthday;//생일
	private String phone;//전화번호
	private String photo;//사진
	private String zipcode;//우편번호
	private String address1;//주소
	private String address2;//상세주소
	private String email;//이메일
	private Date reg_date;//계정 생성일
	private Date latest_login;//최근 로그인
	private int try_login;//로그인 시도 횟수
	//[member_sleep 테이블]
	private Date ssleep_date;
	
	//비밀번호 일치 여부 체크
	public boolean isCheckedPassword(
			            String Passwd) {
		//회원 등급 :  0 탈퇴회원, 1 일반회원, 9 관리자회원 
		//db에서 받아온 salt와 이용자가 입력한 pw를 해싱 한 값과 DB에 저장된 값을 비교한다. 
		String tmpPasswd = Encrypt.getEncrypt(Passwd, salt);
		if(auth > 0 && passwd.equals(tmpPasswd)) {
			return true;
		}
		return false;
	}
	
	//getter와 setter 생성
	public int getMem_num() {
		return mem_num;
	}

	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public Date getLatest_login() {
		return latest_login;
	}

	public void setLatest_login(Date latest_login) {
		this.latest_login = latest_login;
	}

	public int getTry_login() {
		return try_login;
	}

	public void setTry_login(int try_login) {
		this.try_login = try_login;
	}

	public Date getSsleep_date() {
		return ssleep_date;
	}

	public void setSsleep_date(Date ssleep_date) {
		this.ssleep_date = ssleep_date;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}



}