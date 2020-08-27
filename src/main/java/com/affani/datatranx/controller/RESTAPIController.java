package com.affani.datatranx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.affani.datatranx.model.Account;
import com.affani.datatranx.rest.model.AccountTransferRestModel;
import com.affani.datatranx.service.IAccountService;

/**
 * @author Hamza AFFANI
 * @version 1.0
 * 
 *          1. Declarative transaction management.
 * 
 *          2. Programmatic trans. action management => there is two
 *          implementations:
 * 
 *          --> 2.1. using spring transaction manager.
 *          --> 2.2 using spring transaction template.
 */

@RestController
@RequestMapping("/services")
public class RESTAPIController {

	@Autowired
	@Qualifier("declarativeTrxManagementBean")
	private IAccountService declAccountService;

	@Autowired
	@Qualifier("progTrxManagerBean")
	private IAccountService firstProgAccountService;

	@Autowired
	@Qualifier("progTrxTemplateBean")
	private IAccountService secondProgAccountService;

	/**
	 * 
	 * Here are the calls to declarativeTrxManagementBean that will handle
	 * transaction the declarative way.
	 * 
	 */

	@GetMapping("declarative/accounts")
	public ResponseEntity<List<Account>> getAllDeclAccounts() {
		List<Account> list = declAccountService.getAllAccounts();
		return new ResponseEntity<List<Account>>(list, HttpStatus.OK);
	}

	@PostMapping("declarative/transfer")
	public ResponseEntity<Void> decltransferAccntToAccnt(
			@RequestBody AccountTransferRestModel accountTransferRestModel) {

		declAccountService.transferMoney(accountTransferRestModel.getFrom(), accountTransferRestModel.getTo(),
				accountTransferRestModel.getAmount(), 5);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * 
	 * Here is the call to progTrxManagerBean that will handle transaction the
	 * programmatic way.
	 */

	@GetMapping("programmatic/trxmanager/accounts")
	public ResponseEntity<List<Account>> getAllProg1Accounts() {
		List<Account> list = firstProgAccountService.getAllAccounts();
		return new ResponseEntity<List<Account>>(list, HttpStatus.OK);
	}

	@PostMapping("programmatic/trxmanager/transfer")
	public ResponseEntity<Void> prog1TransferAccntToAccnt(
			@RequestBody AccountTransferRestModel accountTransferRestModel) {

		firstProgAccountService.transferMoney(accountTransferRestModel.getFrom(), accountTransferRestModel.getTo(),
				accountTransferRestModel.getAmount(), 5);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * 
	 * Here is the call to progTrxTemplateBean that will handle transaction the
	 * programmatic way.
	 */

	@GetMapping("programmatic/trxtemplate/accounts")
	public ResponseEntity<List<Account>> getAllProg2Accounts() {
		List<Account> list = secondProgAccountService.getAllAccounts();
		return new ResponseEntity<List<Account>>(list, HttpStatus.OK);
	}

	@PostMapping("programmatic/trxtemplate/transfer")
	public ResponseEntity<Void> prog2TransferAccntToAccnt(
			@RequestBody AccountTransferRestModel accountTransferRestModel) {

		secondProgAccountService.transferMoney(accountTransferRestModel.getFrom(), accountTransferRestModel.getTo(),
				accountTransferRestModel.getAmount(), 5);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
