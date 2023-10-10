package Park.gamewebpage.service;

import Park.gamewebpage.domain.FreeBoard;
import Park.gamewebpage.dto.CreateFreeBoardDTO;
import Park.gamewebpage.repository.IFreeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FreeBoardService {

    private final IFreeBoardRepository iFreeBoardRepository;

    /**
     * 자유 게시판 글 객체가 생성된다.
     * CreateFreeBoardDTO 를 받아 엔티티 객체화 시킨후
     * FreeBoard 객체화 된 객체를 반환한다.
     * @param createFreeBoardDTO
     * @return FreeBoard 저장소에 저장되는 객체가 반환된다.
     */
    public FreeBoard createFreeBoard(CreateFreeBoardDTO createFreeBoardDTO){
        return iFreeBoardRepository
                .save(createFreeBoardDTO.toEntity());
    }

    /**
     * 자유 게시판 리스트를
     * 가져온다.
     * @return FreeBoard 전체 조회 결과
     */
    public List<FreeBoard> getListFreeBoard(){
        return iFreeBoardRepository.findAll();
    }
}
