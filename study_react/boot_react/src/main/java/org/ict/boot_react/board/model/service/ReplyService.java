package org.ict.boot_react.board.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.boot_react.board.jpa.repository.ReplyRepoository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepoository replyRepoository;


}
