package com.asadian.rahnema.treasury.controller;

import com.asadian.rahnema.treasury.dto.AccountDto;
import com.asadian.rahnema.treasury.dto.DocumentContainerFactory;
import com.asadian.rahnema.treasury.dto.DocumentDto;
import com.asadian.rahnema.treasury.exception.BusinessException;
import com.asadian.rahnema.treasury.service.AccountService;
import com.asadian.rahnema.treasury.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

/**
 * Created by rahnema on 9/6/2017.
 */
@RestController
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AccountService accountService;


    @RequestMapping(value = "/issue", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity issueDocument(@RequestBody DocumentDto documentDto) {
        try {
            AccountDto source = accountService.load(documentDto.getSource());
            accountService.validate(accountService.convert(source), documentDto.getOtp());
            String refId = documentService.issueDocument(documentDto);
            String message = String.valueOf(documentDto.getAmount()) + " was transfer from "
                    + documentDto.getSource() + " to " + documentDto.getDest() + " successfully!!";
            return new ResponseEntity(DocumentContainerFactory.newOkInstance(refId,message), HttpStatus.OK);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity(DocumentContainerFactory.newBadInstance(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/reverse/{refId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity reverseLastDocument(@PathVariable String refId) {
        try {
            String reverseRefId = documentService.reverseDocument(refId);
            String message = "Document " + refId + " was reversed successfully!!";
            return new ResponseEntity(DocumentContainerFactory.newOkInstance(reverseRefId, message), HttpStatus.OK);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity(DocumentContainerFactory.newBadInstance(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
