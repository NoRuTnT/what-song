package com.whatsong.domain.websocket.service.subService;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.whatsong.domain.music.model.Music;
import com.whatsong.domain.music.repository.MusicRepository;
import com.whatsong.domain.websocket.data.PlayType;
import com.whatsong.domain.websocket.dto.gameMessageDto.MusicPlayDto;
import com.whatsong.domain.websocket.dto.gameMessageDto.MusicProblemDto;
import com.whatsong.domain.websocket.dto.gameMessageDto.TimeDto;
import com.whatsong.domain.websocket.model.GameRoom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoundStartService {

	private final MusicRepository musicRepository;

	private static final String SPACE = " ";
	private static final int LOOP_START_INDEX = 0;
	private static final int GAME_PLAY_TIME = 40;


	private final SimpMessageSendingOperations messagingTemplate;

	public void doRoundStart(Integer roomNum, GameRoom room) {

		// 타임 카운트가 5인 경우 (맨 처음 카운트인 경우) 문제 링크를 보냄
		// 라운드마다 변수 초기화를 위해 ""를 담아 보냄
		if (room.getTime() == 5) {
			MusicProblemDto dto = MusicProblemDto.builder()
				.musicUrl(room.getProblems().get(room.getRound() - 1).getUrl())
				.round(room.getRound())
				.build();
			messagingTemplate.convertAndSend("/topic/" + roomNum, dto);

			// 카운트 다운 전송
			TimeDto timeDto = TimeDto.builder()
				.time(room.getTime())
				.message("준비 중")
				.build();
			messagingTemplate.convertAndSend("/topic/" + roomNum, timeDto);

			room.timeDown();
		}
		else if(room.getTime() == 4) {

			// 카운트 다운 전송
			TimeDto timeDto = TimeDto.builder()
				.time(room.getTime())
				.message("준비 중")
				.build();
			messagingTemplate.convertAndSend("/topic/" + roomNum, timeDto);

			room.timeDown();
		}
		// 3, 2, 1 카운트 다운 전송
		else if (room.getTime() > 0) {

			// 카운트 다운 전송
			TimeDto timeDto = TimeDto.builder()
				.time(room.getTime())
				.message(room.getTime() + " 초")
				.build();
			messagingTemplate.convertAndSend("/topic/" + roomNum, timeDto);

			room.timeDown();
		} else {

			// 게임 플레이 타입 변경 및 시간 설정
			room.changePlayType(PlayType.BEFOREANSWER);
			room.setTime(GAME_PLAY_TIME);

			// 음악 플레이 메세지 전송
			MusicPlayDto dto = MusicPlayDto.builder().build();
			messagingTemplate.convertAndSend("/topic/" + roomNum, dto);
		}
	}

	public List<Music> makeProblemList(int musicSetsId) {

		List<Music> musicList = musicRepository.findAllBySetId(musicSetsId);
		Collections.shuffle(musicList, ThreadLocalRandom.current());

		return musicList;
	}


	// /**
	//  * finalMusicList 에서 필요한 값만 빼서 multiModeProblemList 만들기
	//  *
	//  * @param finalMusicList
	//  * @param numberOfProblems
	//  * @return List<Music>
	//  */
	// private List<Problem> makeProblemFromFinalMusicList(
	// 	List<Music> finalMusicList, int numberOfProblems) {
	// 	List<Problem> ProblemList = new ArrayList<>();
	//
	// 	// 랜덤한 int를 numberOfProblems만큼 뽑아서 Set에 추가
	// 	Random random = new Random();
	// 	Set<Integer> indexes = new HashSet<>();
	//
	// 	while(indexes.size() < numberOfProblems) {
	// 		int num = random.nextInt(finalMusicList.size());
	// 		indexes.add(num);
	// 	}
	//
	// 	for (int index : indexes) {
	// 		Music music = finalMusicList.get(index);
	// 		List<Title> titleList = titleRepository.findAllByMusicId(music.getId());
	// 		List<String> answerList = new ArrayList<>();
	// 		for (Title title : titleList) {
	// 			answerList.add(title.getAnswer());
	// 		}
	// 		ProblemList.add(
	// 			Problem.create(music.getTitle(), music.getHint(), music.getSinger(),
	// 				music.getUrl(), answerList));
	// 	}
	//
	// 	return ProblemList;
	// }
	//
	// /**
	//  * musicList에서 중복 제거
	//  *
	//  * @param musicList
	//  * @return List<Music>
	//  */
	// private List<Music> deleteDuplicatedMusic(List<Music> musicList) {
	// 	Set<String> titleSet = new HashSet<>();
	// 	Set<String> singerSet = new HashSet<>();
	// 	List<Music> finalMusicList = new ArrayList<>();
	//
	// 	for (int i = LOOP_START_INDEX; i < musicList.size(); i++) {
	// 		Music nowMusic = musicList.get(i);
	//
	// 		int beforeTitleSetSize = titleSet.size();
	// 		titleSet.add(nowMusic.getTitle());
	// 		int afterTitleSetSize = titleSet.size();
	//
	// 		int beforeSingerSetSize = singerSet.size();
	// 		singerSet.add(nowMusic.getSinger());
	// 		int afterSingerSetSize = singerSet.size();
	//
	// 		if (beforeTitleSetSize == afterTitleSetSize
	// 			&& beforeSingerSetSize == afterSingerSetSize) {
	// 			continue;
	// 		}
	//
	// 		finalMusicList.add(nowMusic);
	// 	}
	//
	// 	return finalMusicList;
	// }
}
