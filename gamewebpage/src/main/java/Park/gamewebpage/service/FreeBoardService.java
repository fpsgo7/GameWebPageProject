package Park.gamewebpage.service;

import Park.gamewebpage.domain.FreeBoard;
import Park.gamewebpage.dto.freeboard.api.CreateFreeBoardDTO;
import Park.gamewebpage.dto.freeboard.api.UpdateFreeBoardDTO;
import Park.gamewebpage.repository.IFreeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public FreeBoard createFreeBoard(CreateFreeBoardDTO  createFreeBoardDTO, String userName){
        return iFreeBoardRepository
                .save(createFreeBoardDTO.toEntity(userName));
    }

    /**
     * 자유 게시판 리스트를
     * 가져온다.
     * @return FreeBoard 전체 조회 결과
     */
    public List<FreeBoard> getListFreeBoard(){
        return iFreeBoardRepository.findAll();
    }

    /**
     * 자유 게시판 글을
     * 아이디로 찾아 반환한다.
     * @param id
     * @return 아이디가 있다면 해당 id를 가진 FreeBoard 반환
     * 없다면 IllegalArgumentException 예외 반환
     */
    public FreeBoard getFreeBoard(Long id){
        return iFreeBoardRepository.findById(id)
                .orElseThrow(()
                -> new IllegalArgumentException("not found: "+id));
    }

    /**
     * 자유 게시판 글 삭제
     * 메서드
     * @param id
     */
    public void deleteFreeBoard(long id){
        FreeBoard freeBoard = iFreeBoardRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("not found:"+id));
        checkWriterId(freeBoard);
        iFreeBoardRepository.deleteById(id);

    }

    /**
     * 업데이트를  위한 메서드
     * Article 객체를 id로 찾아 얻어온후
     * 값을 수정하여 반환한다.
     * @param id
     * @param updateFreeBoardDTO
     * @return id 로 찾은 freeBoard
     */
    @Transactional
    public FreeBoard updateFreeBoard(long id
            , UpdateFreeBoardDTO updateFreeBoardDTO){
        FreeBoard freeBoard = iFreeBoardRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found:"+id));

        checkWriterId(freeBoard);

        freeBoard.setTitle(updateFreeBoardDTO.getTitle());
        freeBoard.setContent(updateFreeBoardDTO.getContent());

        return  freeBoard;
    }

    /**
     * 게시글을 작성한 유저인지 확인해준다.
     */
    private static void checkWriterId(FreeBoard freeBoard){
        String userName = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        if(!freeBoard.getWriterId().equals(userName)){
            throw new IllegalArgumentException("not authorized");
        }
    }
}
