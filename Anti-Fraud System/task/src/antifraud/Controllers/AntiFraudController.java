package antifraud.Controllers;

import antifraud.Entities.StolenCard;
import antifraud.Entities.SuspiciousIP;
import antifraud.Requests.TransactionFeedbackRequest;
import antifraud.Requests.TransactionRequest;
import antifraud.Responses.*;
import antifraud.Services.AntiFraudService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/antifraud")
public class AntiFraudController {

    @Autowired
    AntiFraudService antiFraudService;

    @PostMapping("/transaction")
    public ResponseEntity<TransactionExecutedResponse> processTransaction(
            @RequestBody TransactionRequest request) {
        return antiFraudService.processTransaction(request);
    }

    @PutMapping("/transaction")
    public ResponseEntity<TransactionInfoResponse> addTransactionFeedback(@RequestBody TransactionFeedbackRequest request) {
        return antiFraudService.addTransactionFeedback(request);
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransactionInfoResponse>> getTransactions() {
        return antiFraudService.getTransactions();
    }

    @GetMapping("/history/{number}")
    public ResponseEntity<List<TransactionInfoResponse>> getTransactionsByNumber(@PathVariable String number) {
        return antiFraudService.getTransactionsByNumber(number);
    }

    @DeleteMapping("/suspicious-ip/{ip}")
    public ResponseEntity<SuspiciousIpDeletedResponse> deleteSuspiciousIp(
            @PathVariable String ip) {
        try {
            return antiFraudService.deleteSuspiciousIp(ip);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/suspicious-ip")
    public ResponseEntity<List<SuspiciousIPResponse>> getSuspiciousIps() {
        return antiFraudService.getSuspiciousIps();
    }


    @PostMapping("/suspicious-ip")
    public ResponseEntity<SuspiciousIPResponse> addSuspiciousIp(
            @Valid @RequestBody SuspiciousIP suspiciousIP,
            BindingResult bindingResult) {
        return antiFraudService.addSuspiciousIp(suspiciousIP, bindingResult);
    }

    @DeleteMapping("/stolencard/{number}")
    public ResponseEntity<StolenCardDeletedResponse> deleteStolenCard(@PathVariable String number) {
        try {
            return antiFraudService.deleteStolenCard(number);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/stolencard")
    public ResponseEntity<List<StolenCardResponse>> getStolenCards() {
        return antiFraudService.getStolenCards();
    }

    @PostMapping("/stolencard")
    public ResponseEntity<StolenCardResponse> addStolenCard(
            @Valid @RequestBody StolenCard stolenCard,
            BindingResult bindingResult) {
        return antiFraudService.addStolenCard(stolenCard, bindingResult);
    }
}
