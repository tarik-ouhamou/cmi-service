package com.app.services;

import com.app.dao.PaymentOpRespository;
import com.app.entity.PaymentOp;
import com.app.payments_service.BillInfo;
import com.app.utils.AddCreditRequest;
import com.app.utils.ClassExchanger;
import com.app.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired private PaymentOpRespository repo;
    @Autowired private AccountService accountService;
    @Autowired private AuthService authService;
    @Autowired private ClassExchanger exchanger;

    public void paymentRequest(BillInfo billInfo,String accountID){
        try{
            String token="Bearer "+authService.getAccessToken();
            AddCreditRequest request=new AddCreditRequest(accountID,billInfo.getAmount());
            accountService.addCredit(token,request);
            PaymentOp paymentOp=new PaymentOp(null,exchanger.generateBill(billInfo),accountID);
            repo.save(paymentOp);
        }
        catch(Exception e){
            System.out.println("Error");
        }
    }


}