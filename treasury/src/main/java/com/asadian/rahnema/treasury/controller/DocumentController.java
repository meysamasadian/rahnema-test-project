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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rahnema on 9/6/2017.
 */
@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/api/document")
@Api(value="document api", description="All operations of document that provided in this api")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AccountService accountService;


    @RequestMapping(value = "/issue", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "register new account", response = TreasuryResultContainer.class)
    public Object issueDocument(@RequestBody DocumentDto documentDto) throws BusinessException {
        AccountDto source = accountService.load(documentDto.getSource());
        accountService.validate(accountService.convert(source), documentDto.getOtp());
        String refId = documentService.issueDocument(documentDto);
        String message = MessageFactory.message(DocumentMessage.DOCUMENT_GENERATED,
                String.valueOf(documentDto.getAmount()),
                documentDto.getSource(), documentDto.getDest());
        return TreasuryResultFactory.getResult(refId, message);
    }

    @RequestMapping(value = "/reverse/{refId}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "reverse old document", response = TreasuryResultContainer.class)
    public Object reverseLastDocument(@PathVariable String refId) throws BusinessException {
        String reverseRefId = documentService.reverseDocument(refId);
        String message = MessageFactory.message(DocumentMessage.DOCUMENT_REVERSED, refId);
        return TreasuryResultFactory.getResult(reverseRefId, message);
    }
}
