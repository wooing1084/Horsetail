/*
 * Name : Util.Protocol.java
 * Author : 이준형
 * Description : 서버와 클라이언트의 통신에 사용하는 프로토콜을 정의하는 클래스
 */

package Util;

public class Protocol {
	//[Util.Protocol]
	//tag//messages (%로 구분)
	//일반적으로 ACK과 NACK의 경우 tag만 전송
	//태그 위에 메세지 양식 없는경우 태그만 전송한다.
	
	//100//ID%PASSWORD
	public static final String REGISTER = "100"; // 회원가입
	public static final String REGISTER_OK = "101"; //회원가입 성공
	public static final String REGISTER_NO = "102"; // 회원가입 실패

	//110//ID
	public static final String IDVALIDCHECK = "110"; // ID중복 REQUEST
	public static final String IDVALIDCHECK_OK = "111"; // ID중복 사용가능 ACK
	public static final String IDVALIDCHECK_NO = "112"; // ID중복 사용불가능 NACK

	//120//ID%PASSWORD
	public static final String LOGIN = "120"; // 로그인 REQUEST
	//121//ID%PASSWORD%NICKNAME%RATING%WINS%LOSES
	public static final String LOGIN_OK = "121"; // 로그인 성공 ACK
	public static final String LOGIN_NO = "122"; // 로그인 실패 NACK


	public static final String ROOMS = "130"; // 방 목록 요청
	//131//Room1_id%room1_name//room2_info...//room3....//...
	public static final String ROOMS_OK = "131"; // 방 목록 요청 ACK
	public static final String ROOMS_NO = "132"; //방 목록 요청 NACK



	public static final String ROOMCREATE = "150"; // 멀티룸 방 생성
	public static final String ROOMCREATE_OK = "151"; // 멀티룸 방 생성 성공
	public static final String ROOMCREATE_NO = "152"; //멀티룸 방 생성 실패

	//160//ROOM_ID
	public static final String JOINROOM = "160"; //방 참가
	public static final String JOINROOM_OK = "161";	//방 참가 성공
	public static final String JOINROOM_NO ="162"; //방 참가 실패
	public static final String ROOMFULL = "163"; // 정원 초과
	//164//user_id%user_name%user_rating%user_wins%user_loss
	public static final String JOINED_USER = "164"; // 유저 방에 들어옴

	
	public static final String ROOMUSERS = "170"; //방의 유저 목록 요청
	//171//user1_id%user1_name%user1_rating%user1_wins%user1_loss//user2 info....
	public static final String ROOMUSERS_OK = "171"; //방 유저 목록 요청 ACK
	public static final String ROOMUSERS_NAK = "172"; //방 유저 목록 요청 NACK


	public static final String STARTGAME = "200"; // 게임시작 REQUEST
	public static final String STARTGAME_OK = "201"; // 게임시작 성공 ACK
	public static final String STARTGAME_NO = "202"; // 게임시작 실패 NACK

	public static final String TOOSMALLUSER = "210"; // 유저가 부족해 게임시작 실패

	//300//MESSAGE
	public static final String SENDMESSAGE = "300"; // 메시지 전송 REQUEST
	public static final String SENDMESSAGE_OK = "301"; // 메시지 전송 성공 ACK
	public static final String SENDMESSAGE_NO = "302"; // 메시지 전송 실패 NACK

	public static final String STAT = "310"; // 자신 전적 조회
	public static final String STAT_OK = "311"; // 자신 전적 조회
	public static final String STAT_NO = "312"; // 자신 전적 조회
	
	public static final String RANKING = "320"; // 랭킹 조회
	public static final String RANKING_OK = "321"; // 랭킹 조회
	public static final String RANKING_NO = "322"; // 랭킹 조회

	//400//WORD
	public static final String SENDWORD = "400"; // 단어전송 REQUEST
	public static final String SENDWORD_OK = "401"; // 단어전송 성공 ACK
	public static final String SENDWORD_NO = "402"; // 단어전송 실패 NACK

	//410//word
	public static final String WORDONLYONECHAR = "410"; // 단어가 단 한 글자임
	//411//word
	public static final String WORDNOTCHAIN = "411"; // 이전 단어의 끝말이 아님
	//412//word
	public static final String WORDOVERLAP = "412"; // 단어 중복
	//413//word
	public static final String WORDNOTEXIST = "413"; // 단어가 존재하지 않음
	
	//420//WORD//DEFINITION
	public static final String SENDDEF = "420"; // 의미전송 REQUEST
	public static final String SENDDEF_OK = "421"; // 의미전송 성공 ACK
	public static final String SENDDEF_NO = "422"; // 의미전송 실패 NACK
	
	public static final String TIMEOUT = "430"; // 시간초과
	public static final String TIMEOUT_OK = "431"; // 시간초과 반영 성공 ACK
	public static final String TIMEOUT_NO = "432"; // 시간초과 반영 실패 NACK
	
	public static final String YOURTURN = "440"; // 이번 순서라는걸 알림
	//450//user_id
	public static final String ANOTHERTURN = "450"; //누군가의 턴임
	
	public static final String GAMEOUT = "500"; // 게임탈락 REQUEST
	public static final String GAMEOUT_OK = "501"; // 게임탈락 성공 ACK
	public static final String GAMEOUT_NO = "502"; // 게임탈락 실패 NACK
	
	public static final String GAMEEND = "510"; // 게임종료
	public static final String GAMEEND_OK = "511"; // 게임종료 성공 ACK
	public static final String GAMEEND_NO = "512"; // 게임종료 실패 NACK
	
	public static final String EXITPROGRAM = "600"; // 프로그램 종료 REQUEST
	public static final String EXITPROGRAM_OK = "601"; // 프로그램 종료 성공 ACK
	public static final String EXITPROGRAM_NO = "602"; // 프로그램 종료 실패 NACK

	public static final String INVALIDTAG = "999";	//잘못된 태그 ACK
};