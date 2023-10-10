package Park.gamewebpage.service;

import Park.gamewebpage.repository.IFreeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FreeBoardService {

    private final IFreeBoardRepository iFreeBoardRepository;
}
