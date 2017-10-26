package com.asadian.rahnema.treasury.controller;

import com.asadian.rahnema.treasury.dto.AccountDto;
import com.asadian.rahnema.treasury.dto.DocumentDto;
import com.asadian.rahnema.treasury.dto.TreasuryResultContainer;
import com.asadian.rahnema.treasury.dto.TreasuryResultFactory;
import com.asadian.rahnema.treasury.exception.BusinessException;
import com.asadian.rahnema.treasury.message.DocumentMessage;
import com.asadian.rahnema.treasury.message.MessageFactory;
import com.asadian.rahnema.treasury.service.AccountService;
import com.asadian.rahnema.treasury.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rahnema on 9/6/2017.
 */
@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AccountService accountService;


    @RequestMapping(value = "/issue", method = RequestMethod.POST)
    @ResponseBody
    public Object issueDocument(@RequestBody DocumentDto documentDto) {
        try {
            AccountDto source = accountService.load(documentDto.getSource());
            accountService.validate(accountService.convert(source), documentDto.getOtp());
            String refId = documentService.issueDocument(documentDto);
            String message = MessageFactory.message(DocumentMessage.DOCUMENT_GENERATED,
                    String.valueOf(documentDto.getAmount()),
                    documentDto.getSource(), documentDto.getDest());
            return TreasuryResultFactory.getResult(refId, message);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(TreasuryResultFactory.getResult("", e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "/reverse/{refId}", method = RequestMethod.POST)
    @ResponseBody
    public Object reverseLastDocument(@PathVariable String refId) {
        try {
            String reverseRefId = documentService.reverseDocument(refId);
            String message = MessageFactory.message(DocumentMessage.DOCUMENT_REVERSED, refId);
            return TreasuryResultFactory.getResult(reverseRefId, message);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(TreasuryResultFactory.getResult("", e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
