package TIAB.timebox.service.message;

import TIAB.timebox.dto.FileServiceDtoRes;
import TIAB.timebox.dto.MessageDtoReq;
import TIAB.timebox.dto.MessageDtoRes;
import TIAB.timebox.entity.member.Member;
import TIAB.timebox.entity.message.Message;
import TIAB.timebox.exception.MessageNotFoundException;
import TIAB.timebox.exception.UserNotFoundException;
import TIAB.timebox.repository.MemberRepository;
import TIAB.timebox.repository.MessageRepository;
import TIAB.timebox.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;
    private final FileService fileService;

    @Override
    @Transactional
    public MessageDtoRes save(long id, MessageDtoReq messageDtoReq) throws IOException {
        Member member = memberRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

        FileServiceDtoRes fileServiceDtoRes = fileService.save(messageDtoReq);

        messageDtoReq.setFilename(fileServiceDtoRes.getFilename());
        messageDtoReq.setFileUrl(fileServiceDtoRes.getFileUrl());
        messageDtoReq.setMember(member);
        Message message = dtoToEntity(messageDtoReq);

        member.getMessages().add(message);
        return entityToDto(messageRepository.save(message));
    }

    public MessageDtoRes getByMessageId(long id) {
        return entityToDto(messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException()));
    }
}
