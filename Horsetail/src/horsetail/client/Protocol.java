/*
 * Name : Protocol.java
 * Author : 이준형
 * Description : 서버와 클라이언트의 통신에 사용하는 프로토콜을 정의하는 클래스
 */

package horsetail.client;

public class Protocol {
	public static final String REGISTER = "100"; // 회원가입
	
	public static final String IDVALIDCHECK = "110"; // ID중복 REQUEST
	public static final String IDVALIDCHECK_OK = "111"; // ID중복 사용가능 ACK
	public static final String IDVALIDCHECK_NO = "112"; // ID중복 사용불가능 NACK
	
	public static final String LOGIN = "120"; // 로그인 REQUEST
	public static final String LOGIN_OK = "121"; // 로그인 성공 ACK
	public static final String LOGIN_NO = "122"; // 로그인 실패 NACK
	
	public static final String ROOMFULL = "130"; // 싱글룸 정원 초과
	
	public static final String STARTGAME = "200"; // 게임시작 REQUEST
	public static final String STARTGAME_OK = "201"; // 게임시작 성공 ACK
	public static final String STARTGAME_NO = "202"; // 게임시작 실패 NACK
	
	public static final String TOOSMALLUSER = "210"; // 유저가 부족해 게임시작 실패
	
	public static final String SENDMESSAGE = "300"; // 메시지 전송 REQUEST
	public static final String SENDMESSAGE_OK = "301"; // 메시지 전송 성공 ACK
	public static final String SENDMESSAGE_NO = "302"; // 메시지 전송 실패 NACK
	
	public static final String STAT = "310"; // 자신 전적 조회
	public static final String RANKING = "311"; // 랭킹 조회
	
	public static final String SENDWORD = "400"; // 단어전송 REQUEST
	public static final String SENDWORD_OK = "401"; // 단어전송 성공 ACK
	public static final String SENDWORD_NO = "402"; // 단어전송 실패 NACK
	
	public static final String WORDONLYONECHAR = "410"; // 단어가 단 한 글자임
	public static final String WORDNOTCHAIN = "411"; // 이전 단어의 끝말이 아님
	public static final String WORDOVERLAP = "412"; // 단어 중복
	public static final String WORDNOTEXIST = "413"; // 단어가 존재하지 않음
	
	public static final String SENDDEF = "420"; // 의미전송 REQUEST
	public static final String SENDDEF_OK = "421"; // 의미전송 성공 ACK
	public static final String SENDDEF_NO = "422"; // 의미전송 실패 NACK
	
	public static final String TIMEOUT = "430"; // 시간초과
	public static final String TIMEOUT_OK = "431"; // 시간초과 반영 성공 ACK
	public static final String TIMEOUT_NO = "432"; // 시간초과 반영 실패 NACK
	
	public static final String YOURTURN = "440"; // 이번 순서라는걸 알림
	
	public static final String GAMEOUT = "500"; // 게임탈락 REQUEST
	public static final String GAMEOUT_OK = "501"; // 게임탈락 성공 ACK
	public static final String GAMEOUT_NO = "502"; // 게임탈락 실패 NACK
	
	public static final String GAMEEND = "510"; // 게임종료
	public static final String GAMEEND_OK = "511"; // 게임종료 성공 ACK
	public static final String GAMEEND_NO = "512"; // 게임종료 실패 NACK
	
	public static final String EXITPROGRAM = "600"; // 프로그램 종료 REQUEST
	public static final String EXITPROGRAM_OK = "601"; // 프로그램 종료 성공 ACK
	public static final String EXITPROGRAM_NO = "602"; // 프로그램 종료 실패 NACK
};